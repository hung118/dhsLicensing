package gov.utah.dts.det.ccl.actions.reports.generators;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;

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

public class LicenseRenewalsListReport {

	private static final Logger log = LoggerFactory.getLogger(LicenseRenewalsListReport.class);

	// Define report fonts
	static final Font largefontB = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
	static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
	static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	static final Font smallfont = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
	static final Font smallfontB = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
    
	private static final float largeFixedLeading = 13;
	private static final float fixedLeading = 11;
	private static final Phrase BLANK = new Phrase(" ", smallfont);

	private static StringBuilder sb;
	private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static ByteArrayOutputStream generate(Person specialist, Date endDate, List<FacilityLicenseView> licenses) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(specialist, endDate, licenses, ba);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
        return ba;
	}
	
	private static void writePdf(Person specialist, Date endDate, List<FacilityLicenseView> licenses, OutputStream ba) throws DocumentException, BadElementException {
		Document document = null;
		PdfPTable doctable;
		PdfPTable datatable;
		PdfPTable factable;
		Paragraph paragraph;
		Facility facility;
		document = new Document(PageSize.A4);
        @SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, ba);

        document.open();
        
        doctable = getDocumentTable(specialist, endDate);
        
        if (licenses != null && licenses.size() > 0) {
        	doctable.getDefaultCell().setPaddingBottom(4);
        	doctable.getDefaultCell().setBorderWidthBottom(.5f);
        	for (FacilityLicenseView license : licenses) {
        	    facility = license.getFacility();
        		datatable = getDetailTable();
            	datatable.getDefaultCell().setPaddingLeft(4);
            	datatable.getDefaultCell().setPaddingRight(2);
            	datatable.getDefaultCell().setColspan(1);
            	// Add Facility Name information
            	if (StringUtils.isNotBlank(license.getFacilityName())) {
            		datatable.addCell(new Phrase(license.getFacilityName(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add License Number information
            	datatable.getDefaultCell().setPaddingLeft(2);
            	if (license.getLicenseNumber() != null) {
            		datatable.addCell(new Phrase(license.getLicenseNumber().toString(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add Service Code information
            	if (StringUtils.isNotBlank(license.getServiceCodeCode())) {
            		datatable.addCell(new Phrase(license.getServiceCodeCode(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add License Type information
            	if (StringUtils.isNotBlank(license.getSubtype())) {
            		datatable.addCell(new Phrase(license.getSubtype(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	// Add License Expiration date information
            	datatable.getDefaultCell().setPaddingRight(4);
            	if (StringUtils.isNotBlank(license.getExpirationDateFormatted())) {
            		datatable.addCell(new Phrase(license.getExpirationDateFormatted(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	
            	// Add Mailing Location information
            	sb = new StringBuilder();
            	if (facility != null && facility.getMailingAddress() != null &&
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
            	datatable.getDefaultCell().setColspan(5);
            	datatable.getDefaultCell().setPaddingLeft(4);
            	datatable.getDefaultCell().setPaddingRight(4);
            	if (sb.length() > 0) {
            		datatable.addCell(new Phrase(sb.toString().toUpperCase(), smallfont));
            	} else {
            		datatable.addCell(BLANK);
            	}
            	
				//
				// Add Facility details information
				// 
            	factable = getFacilityDetailsTable();
            	
            	// Add primary phone information
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Primary Phone: ", smallfontB));
            	if (facility != null && facility.getPrimaryPhone() != null && 
            		StringUtils.isNotBlank(facility.getPrimaryPhone().getFormattedPhoneNumber())) 
            	{
            		paragraph.add(new Phrase(facility.getPrimaryPhone().getFormattedPhoneNumber(), smallfont));
            	}
            	factable.getDefaultCell().setPaddingLeft(4);
            	factable.getDefaultCell().setPaddingRight(2);
            	factable.addCell(paragraph);
            	
            	// Add SAFE Provider ID number
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("SAFE: ", smallfontB));
            	if (facility != null && facility.getSafeProviderId() != null) {
            		paragraph.add(new Phrase(facility.getSafeProviderId().toString(), smallfontB));
            	}
            	factable.getDefaultCell().setColspan(2);
            	factable.addCell(paragraph);
            	
            	// Add the facility information table to the details table
            	datatable.getDefaultCell().setPadding(0);
            	datatable.addCell(factable);
            	// Add the detail to the document table
            	doctable.addCell(datatable);
        	}
        } else {
        	// No open applications
        	datatable = getDetailTable();
        	datatable.getDefaultCell().setPaddingTop(10);
        	datatable.getDefaultCell().setColspan(3);
        	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        	datatable.addCell(new Phrase("No licenses due for renewal were found to display.", smallfont));
        	doctable.getDefaultCell().setPaddingBottom(0);
        	doctable.getDefaultCell().setBorderWidthBottom(0);
        	doctable.addCell(datatable);
        }

        // Add the document table to the document
        document.add(doctable);
       	
       	document.close();
	}
	
	private static PdfPTable getDocumentTable(Person specialist, Date endDate) throws DocumentException {
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
		table.getDefaultCell().setPaddingBottom(4);
		table.getDefaultCell().setBorderWidthBottom(1.0f);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

		table.addCell(getHeaderTable(specialist, endDate));
		table.setHeaderRows(1);

		return table;
	}
	
	private static PdfPTable getHeaderTable(Person specialist, Date endDate) throws DocumentException {
        Date today = new Date();
        
		PdfPTable table = getDetailTable();
		
		// Add Page header information
		table.getDefaultCell().setLeading(largeFixedLeading, 0);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(2);
		table.getDefaultCell().setBorderWidthLeft(0);
		table.getDefaultCell().setBorderWidthRight(0);
		table.getDefaultCell().setBorderWidthTop(1.0f);
		table.getDefaultCell().setBorderWidthBottom(0);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.getDefaultCell().setColspan(3);
		sb = new StringBuilder();
		sb.append("Renewal Licenses for ");
		if (StringUtils.isNotBlank(specialist.getFirstAndLastName())) {
			sb.append(specialist.getFirstAndLastName());
		}
		table.addCell(new Phrase(sb.toString(), largefontB));
		
		// Add report date
		table.getDefaultCell().setPaddingLeft(2);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		table.getDefaultCell().setColspan(2);
		table.addCell(new Phrase(df.format(today), mediumfontB));
		
		// Add expiration end date date
		table.getDefaultCell().setPaddingLeft(8);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setPaddingBottom(4);
		table.getDefaultCell().setBorderWidthTop(0);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.getDefaultCell().setColspan(5);
		table.addCell(new Phrase("With Licenses Expiring " + df.format(endDate), mediumfontB));
		
		// Add Facility Name column header
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(2);
		table.getDefaultCell().setPaddingBottom(0);
		table.getDefaultCell().setBorderWidthTop(0.5f);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_BOTTOM);
		table.getDefaultCell().setColspan(1);
		table.addCell(new Phrase("Facility Name", smallfontB));
		
		// Add License Number column header
		table.getDefaultCell().setPaddingLeft(2);
		table.addCell(new Phrase("License Number", smallfontB));
		
		// Add Service Code column header
		table.addCell(new Phrase("Service", smallfontB));
		
		// Add License Type column header
		table.addCell(new Phrase("License Type", smallfontB));
		
		// Add End Date column header
		table.getDefaultCell().setPaddingRight(4);
		table.addCell(new Phrase("End Date", smallfontB));

		return table;
	}
	
	private static PdfPTable getDetailTable() throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		// format the table
		int headerwidths[] = { 41, 17, 8, 24, 10 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_BOTTOM);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		return table;
	}

	private static PdfPTable getFacilityDetailsTable() throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		// format the table
		int headerwidths[] = { 33, 33, 33 }; // percentage
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
