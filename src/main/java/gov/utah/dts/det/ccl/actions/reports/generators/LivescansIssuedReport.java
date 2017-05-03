package gov.utah.dts.det.ccl.actions.reports.generators;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.reports.PageNumberEventLeft;
import gov.utah.dts.det.util.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;

public class LivescansIssuedReport {

	private static final Logger log = LoggerFactory.getLogger(LivescansIssuedReport.class);

	// Define report fonts
	static final Font largefontB = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
	static final Font mediumfont = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
	static final Font mediumfontB = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
    
	private static final float fixedLeadingLarge = 13.5f;
	private static final float fixedLeadingMedium = 11.5f;
	private static final Phrase BLANK = new Phrase(" ", mediumfont);
	private static final Double ZERO = 0.0;

	private static StringBuilder sb;
	private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static ByteArrayOutputStream generate(Person technician, Date startDate, Date endDate, List<TrackingRecordScreening> screenings) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(technician, startDate, endDate, screenings, ba);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
        return ba;
	}
	
	private static void writePdf(Person technician, Date startDate, Date endDate, List<TrackingRecordScreening> screenings, OutputStream ba) 
	throws DocumentException, BadElementException {
		Document document = null;
		PdfPTable datatable;
		Paragraph paragraph;
		document = new Document(PageSize.LETTER.rotate(), 25, 25, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        PageNumberEventLeft pageNumber = new PageNumberEventLeft();
        writer.setPageEvent(pageNumber);

        document.open();
        
        datatable = getDetailTable();
        datatable.getDefaultCell().setColspan(8);
        datatable.addCell(getHeaderTable(technician, startDate, endDate));
        datatable.setHeaderRows(1);
        
        if (screenings != null && screenings.size() > 0) {
        	boolean moreScreenings = true;
    		boolean facilityIsChanging = true;
        	int idx = 0;
        	Double searchTotal = 0.0;
        	Double scanTotal = 0.0;
        	Double grandTotal = 0.0;
        	// Initialize trs to the first screening
        	TrackingRecordScreening trs = screenings.get(idx);
        	
        	while (moreScreenings) {
        		TrackingRecordScreeningDpsFbi fbi = trs.getTrsDpsFbi();
        		
        		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        		datatable.getDefaultCell().setPaddingBottom(0);
        		datatable.getDefaultCell().setBorderWidthBottom(0);
       		
        		// Check to see if a new facility has been found
        		if (facilityIsChanging) {
        			// Add new facility information to report
        			datatable.getDefaultCell().setColspan(8);
        			datatable.getDefaultCell().setPaddingLeft(4);
        			datatable.getDefaultCell().setPaddingRight(4);
        			
        			paragraph = new Paragraph(fixedLeadingMedium);
        			paragraph.add(new Phrase("Program: ", mediumfontB));
        			if (StringUtils.isNotBlank(trs.getFacility().getName())) {
        				paragraph.add(new Phrase(trs.getFacility().getName().toUpperCase(), mediumfont));
        			}
        			datatable.addCell(paragraph);

        			// Reset the facility change flag
        			facilityIsChanging = false;
        		}
        		
        		// This is where we figure out if the next screening facility will change so we can put a bottom border
        		// Increment the screenings index so we can look at the next screening to see if the facility changes
        		idx++;
        		if (idx >= screenings.size()) {
        			// This is the last screening
        			moreScreenings = false;
        		} else {
        			TrackingRecordScreening next = screenings.get(idx);
        			if (!next.getFacility().getId().equals(trs.getFacility().getId())) {
        				facilityIsChanging = true;
        			}
        		}
        		if (facilityIsChanging) {
        			// Add a light bottom border to separate facilities
        			datatable.getDefaultCell().setPaddingBottom(4);
        			datatable.getDefaultCell().setBorderWidthBottom(.5f);
        		} else 
        		if (!moreScreenings) {
        			// This is last record so put a heavy bottom border
        			datatable.getDefaultCell().setPaddingBottom(4);
        			datatable.getDefaultCell().setBorderWidthBottom(1.0f);
        		}
        		// End bottom border check
        		
        		// Add Applicant name
        		datatable.getDefaultCell().setColspan(1);
        		datatable.getDefaultCell().setPaddingLeft(4);
        		datatable.getDefaultCell().setPaddingRight(1);
        		sb = new StringBuilder();
        		if (StringUtils.isNotBlank(trs.getFirstName())) {
        			sb.append(trs.getFirstName().toUpperCase());
        		}
        		if (StringUtils.isNotBlank(trs.getLastName())) {
        			if (sb.length() > 0) {
        				sb.append(" ");
        			}
        			sb.append(trs.getLastName().toUpperCase());
        		}
        		if (sb.length() > 0) {
        			datatable.addCell(new Phrase(sb.toString(), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}

        		// Add ID
        		datatable.getDefaultCell().setPaddingLeft(1);
        		if (StringUtils.isNotBlank(trs.getPersonIdentifier())) {
        			datatable.addCell(new Phrase(trs.getPersonIdentifier(), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}
        		
        		// Add Livescan date
        		if (fbi != null && fbi.getLivescanDate() != null) {
        			datatable.addCell(new Phrase(df.format(fbi.getLivescanDate()), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}
        		
        		// Add Billing Code
        		if (fbi != null && fbi.getBilling() != null && StringUtils.isNotBlank(fbi.getBilling().getValue())) {
        			datatable.addCell(new Phrase(fbi.getBilling().getValue(), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}

        		// Add Money Order
        		if (fbi != null && StringUtils.isNotBlank(fbi.getMoNumber())) {
        			datatable.addCell(new Phrase(fbi.getMoNumber().toUpperCase(), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}
        		
        		// Add Issued By
        		if (fbi != null && StringUtils.isNotBlank(fbi.getIssuedBy())) {
        			datatable.addCell(new Phrase(fbi.getIssuedBy().toUpperCase(), mediumfont));
        		} else {
        			datatable.addCell(BLANK);
        		}
        		
        		// Add Search Fee
        		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
        		if (fbi != null && fbi.getSearchFee() != null) {
        			// Increment the search fee total
        			searchTotal += fbi.getSearchFee().doubleValue();
        			datatable.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(fbi.getSearchFee()), mediumfont));
        		} else {
        			datatable.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(ZERO), mediumfont));
        		}
        		
        		datatable.getDefaultCell().setPaddingRight(4);
        		if (fbi != null && fbi.getScanFee() != null) {
        			scanTotal += fbi.getScanFee().doubleValue();
        			datatable.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(fbi.getScanFee()), mediumfont));
        		} else {
        			datatable.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(ZERO), mediumfont));
        		}
        		
        		// Fetch next screening
        		if (moreScreenings) {
        			trs = screenings.get(idx);
        		}
        	}
        	
        	// Now add the report totals
        	datatable.getDefaultCell().setColspan(8);
        	datatable.getDefaultCell().setPadding(0);
        	datatable.getDefaultCell().setBorderWidthBottom(0);
        	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        	
        	PdfPTable totals = getTotalsTable();
        	totals.getDefaultCell().setPaddingLeft(4);
        	totals.getDefaultCell().setPaddingRight(2);
        	
        	// Add Total Entries subtotal
        	paragraph = new Paragraph(fixedLeadingMedium);
        	paragraph.add(new Phrase("Total Entries:  ", mediumfontB));
        	paragraph.add(new Phrase(String.valueOf(screenings.size()), mediumfont));
        	totals.addCell(paragraph);
        	
        	// Add Total header
        	totals.getDefaultCell().setPaddingLeft(1);
        	totals.addCell(new Phrase("TOTAL:", mediumfontB));

        	// Add Search fee subtotal
        	totals.getDefaultCell().setPaddingRight(1);
        	totals.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(searchTotal), mediumfont));
        	
        	// Add Scan fee subtotal
        	totals.getDefaultCell().setPaddingRight(4);
        	totals.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(scanTotal), mediumfont));
        	
        	// Add Grand Totals header
        	totals.getDefaultCell().setLeading((fixedLeadingMedium*2), 0);
        	totals.getDefaultCell().setColspan(2);
        	totals.getDefaultCell().setPaddingLeft(4);
        	totals.getDefaultCell().setPaddingRight(2);
        	totals.addCell(new Phrase("GRAND TOTAL:", mediumfontB));
        	
        	// Add grand total
        	totals.getDefaultCell().setPaddingLeft(1);
        	totals.getDefaultCell().setPaddingRight(4);
        	grandTotal = searchTotal + scanTotal;
        	totals.addCell(new Phrase(CommonUtils.fromDoubleToCurrency(grandTotal), mediumfontB));

        	// Add subtotals to report data table
        	datatable.addCell(totals);
        } else {
        	// No open applications
        	datatable.getDefaultCell().setPaddingTop(10);
        	datatable.getDefaultCell().setColspan(8);
        	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        	datatable.addCell(new Phrase("No issued livescans found to report.", mediumfont));
        }

        // Add the document table to the document
        document.add(datatable);
       	
       	document.close();
	}
	
	private static PdfPTable getHeaderTable(Person technician, Date startDate, Date endDate) throws DocumentException {
        Date today = new Date();
        
		PdfPTable table = getDetailTable();
		table.getDefaultCell().setLeading(fixedLeadingLarge, 0);
		
		// Add Page header information
		table.getDefaultCell().setColspan(6);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(1);
		table.getDefaultCell().setBorderWidthLeft(0);
		table.getDefaultCell().setBorderWidthRight(0);
		table.getDefaultCell().setBorderWidthTop(1.0f);
		table.getDefaultCell().setBorderWidthBottom(0);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		sb = new StringBuilder();
		sb.append("Live Scans Issued for ");
		if (StringUtils.isNotBlank(technician.getFirstAndLastName())) {
			sb.append(technician.getFirstAndLastName());
		}
		table.addCell(new Phrase(sb.toString(), largefontB));

		// Add report date
		table.getDefaultCell().setColspan(2);
		table.getDefaultCell().setPaddingLeft(1);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		table.addCell(new Phrase(df.format(today), mediumfontB));
		
		// Add Date Range information
		table.getDefaultCell().setColspan(8);
		table.getDefaultCell().setPaddingLeft(8);
		table.getDefaultCell().setPaddingRight(8);
		table.getDefaultCell().setPaddingBottom(4);
		table.getDefaultCell().setBorderWidthTop(0);
		table.getDefaultCell().setBorderWidthBottom(.5f);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		sb = new StringBuilder();
		sb.append("From ");
		if (startDate != null) {
			sb.append(df.format(startDate)+" ");
		}
		sb.append("to ");
		if (endDate != null) {
			sb.append(df.format(endDate));
		}
		table.addCell(new Phrase(sb.toString(), largefontB));
		
		// Add Report Column headers
		table.getDefaultCell().setColspan(1);
		table.getDefaultCell().setLeading(fixedLeadingMedium, 0);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(1);
		table.getDefaultCell().setPaddingBottom(4);
		table.getDefaultCell().setBorderWidthBottom(1.0f);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_BOTTOM);
		table.addCell(new Phrase("Applicant", mediumfontB));
		table.getDefaultCell().setPaddingLeft(1);
		table.addCell(new Phrase("ID", mediumfontB));
		table.addCell(new Phrase("Authorized", mediumfontB));
		table.addCell(new Phrase("Billing", mediumfontB));
		table.addCell(new Phrase("MO #", mediumfontB));
		table.addCell(new Phrase("Issued By", mediumfontB));
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		table.addCell(new Phrase("Search", mediumfontB));
		table.getDefaultCell().setPaddingRight(4);
		table.addCell(new Phrase("Scan", mediumfontB));

		return table;
		
	}
	
	private static PdfPTable getDetailTable() throws DocumentException {
		PdfPTable table = new PdfPTable(8);
		// format the table
		int headerwidths[] = { 26, 6, 7, 5, 12, 27, 8, 9 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeadingMedium, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		return table;
	}

	private static PdfPTable getTotalsTable() throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		// format the table
		int headerwidths[] = { 73, 10, 8, 9 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeadingMedium, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);

		return table;
	}

}
