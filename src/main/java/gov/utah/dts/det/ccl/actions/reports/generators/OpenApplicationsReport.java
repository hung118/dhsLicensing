package gov.utah.dts.det.ccl.actions.reports.generators;

import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.view.SortableFacilityView;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
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

public class OpenApplicationsReport {

	private static final Logger log = LoggerFactory.getLogger(OpenApplicationsReport.class);

	// Define report fonts
	static final Font largefontB = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
	static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
	static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	static final Font mediumfontBI = FontFactory.getFont("Times-Roman", 12, Font.BOLDITALIC);
    
	private static final float fixedLeading = 13;
	private static final Phrase BLANK = new Phrase(" ", mediumfont);
	private static final String FIRST_DIRECTOR = "First Director";

	private static StringBuilder sb;
	private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static NumberFormat formatCurrency = NumberFormat.getCurrencyInstance();

    public static ByteArrayOutputStream generate(Person specialist, List<SortableFacilityView> facilities) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(specialist, facilities, ba);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
        return ba;
	}
	
	private static void writePdf(Person specialist, List<SortableFacilityView> facilities, OutputStream ba) throws DocumentException, BadElementException {
		Document document = null;
		PdfPTable doctable;
		PdfPTable datatable;
		Paragraph paragraph;
		Facility facility;
		document = new Document(PageSize.A4, 50, 50, 75, 75);
        @SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, ba);

        document.open();
        
        doctable = getDocumentTable(specialist);
        
        if (facilities != null && facilities.size() > 0) {
        	doctable.getDefaultCell().setPaddingBottom(5);
        	doctable.getDefaultCell().setBorderWidthBottom(.5f);
        	for (SortableFacilityView view : facilities) {
        	    facility = view.getFacility();
        		datatable = getDetailTable();
            	datatable.getDefaultCell().setPaddingLeft(4);
            	datatable.getDefaultCell().setPaddingRight(4);
            	datatable.getDefaultCell().setColspan(3);
            	// Add Facility Name information
            	if (StringUtils.isNotBlank(view.getFacilityName())) {
            		datatable.addCell(new Phrase(view.getFacilityName(), mediumfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add First Director(s) information
            	sb = new StringBuilder();
            	List<FacilityPerson> directors = facility.getPeopleOfType(FIRST_DIRECTOR, true);
            	if (directors != null && directors.size() > 0) {
            		for (FacilityPerson fp : directors) {
            			if (fp.getPerson() != null && StringUtils.isNotBlank(fp.getPerson().getFirstAndLastName())) {
            				if (sb.length() > 0) {
            					sb.append(", ");
            				}
            				sb.append(fp.getPerson().getFirstAndLastName());
            			}
            		}
            	}
            	if (sb.length() > 0) {
            		datatable.addCell(new Phrase(sb.toString(), mediumfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add Mailing Location information
            	sb = new StringBuilder();
            	if (facility.getMailingAddress() != null &&
            		StringUtils.isNotBlank(facility.getMailingAddress().getAddressOne())) 
            	{
            		sb.append(facility.getMailingAddress().getAddressOne());
            		
            		// add address two
            		if (StringUtils.isNotBlank(facility.getMailingAddress().getAddressTwo())) {
            			if (sb.length() > 0) {
            				sb.append(" ");
            			}
            			sb.append(facility.getMailingAddress().getAddressTwo());
            		}
            		
            		// Add city, state, zip
            		if (StringUtils.isNotBlank(facility.getMailingAddress().getCityStateZip())) {
            			if (sb.length() > 0) {
            				sb.append(", ");
            			}
            			sb.append(facility.getMailingAddress().getCityStateZip());
            		}
            	}
            	if (sb.length() > 0) {
            		datatable.addCell(new Phrase(sb.toString().toUpperCase(), mediumfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	
            	// Add the Fee line information
            	//
            	// Add the facility creation date
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Date Created: ", mediumfontB));
            	if (facility.getCreationDate() != null) {
            		paragraph.add(new Phrase(df.format(facility.getCreationDate()), mediumfont));
            	}
            	datatable.getDefaultCell().setColspan(1);
            	datatable.getDefaultCell().setPaddingLeft(4);
            	datatable.getDefaultCell().setPaddingRight(2);
            	datatable.addCell(paragraph);
            	
            	// Add the fee received Yes/No info
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Fee Received: ", mediumfontB));
            	CmpTransaction fee = facility.getFirstNonInspectionFee(true);
            	if (fee != null) {
            		paragraph.add(new Phrase("Yes", mediumfont));
            	} else {
            		paragraph.add(new Phrase("No", mediumfont));
            	}
            	datatable.getDefaultCell().setPaddingLeft(2);
            	datatable.addCell(paragraph);
            	
            	// Add the fee amount info
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Amount: ", mediumfontB));
            	if (fee != null) {
            		if (fee.getAmount() != null) {
                		paragraph.add(new Phrase(formatCurrency.format(fee.getAmount()), mediumfont));
            		} else {
                		paragraph.add(new Phrase("$0.00", mediumfont));
            		}
            	}
            	datatable.getDefaultCell().setPaddingRight(4);
            	datatable.addCell(paragraph);
            	
            	// Add the detail to the document table
            	doctable.addCell(datatable);
        	}
        } else {
        	// No open applications
        	datatable = getDetailTable();
        	datatable.getDefaultCell().setPaddingTop(10);
        	datatable.getDefaultCell().setColspan(3);
        	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        	datatable.addCell(new Phrase("No open applications found to display.", mediumfont));
        	doctable.getDefaultCell().setPaddingBottom(0);
        	doctable.getDefaultCell().setBorderWidthBottom(0);
        	doctable.addCell(datatable);
        }

        // Add the document table to the document
        document.add(doctable);
       	
       	document.close();
	}
	
	private static PdfPTable getDocumentTable(Person specialist) throws DocumentException {
		PdfPTable table = new PdfPTable(1);
		// format the tables
		int headerwidths[] = { 100 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(0, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorderWidthLeft(0);
		table.getDefaultCell().setBorderWidthRight(0);
		table.getDefaultCell().setBorderWidthTop(0);
		table.getDefaultCell().setPaddingBottom(6);
		table.getDefaultCell().setBorderWidthBottom(1.0f);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

		table.addCell(getHeaderTable(specialist));
		table.setHeaderRows(1);

		return table;
		
	}
	
	private static PdfPTable getHeaderTable(Person specialist) throws DocumentException {
        Date today = new Date();
        
		PdfPTable table = new PdfPTable(2);
		// format the table
		int headerwidths[] = { 85, 15 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setColspan(1);
		
		// Add Page header information
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(2);
		table.getDefaultCell().setPaddingBottom(20);
		table.getDefaultCell().setBorderWidthLeft(0);
		table.getDefaultCell().setBorderWidthRight(0);
		table.getDefaultCell().setBorderWidthTop(1.0f);
		table.getDefaultCell().setBorderWidthBottom(0);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		sb = new StringBuilder();
		sb.append("Open Applications for ");
		if (StringUtils.isNotBlank(specialist.getFirstAndLastName())) {
			sb.append(specialist.getFirstAndLastName());
		}
		table.addCell(new Phrase(sb.toString(), largefontB));
		
		// Add report date
		table.getDefaultCell().setPaddingLeft(2);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		table.addCell(new Phrase(df.format(today), mediumfontB));
		
		// Add Contact Information header
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setPaddingBottom(0);
		table.getDefaultCell().setBorderWidthTop(0.5f);
		table.getDefaultCell().setBorderWidthBottom(0);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.getDefaultCell().setColspan(2);
		table.addCell(new Phrase("Contact Information", mediumfontBI));

		return table;
		
	}
	
	private static PdfPTable getDetailTable() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		// format the table
		int headerwidths[] = { 33, 33, 34 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_BOTTOM);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		return table;
	}

}
