package gov.utah.dts.det.ccl.actions.reports.generators;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;
import gov.utah.dts.det.reports.PageNumberEventLeft;

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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;

public class FacilityLicenseDetailReport {

	private static final Logger log = LoggerFactory.getLogger(FacilityLicenseDetailReport.class);

	// Define report fonts
	static final Font largefontB = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
	static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
	static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	static final Font smallfont = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
	static final Font smallfontB = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
    
	private static final float largeFixedLeading = 13;
	private static final float fixedLeading = 11;
	private static final Phrase BLANK = new Phrase(" ", smallfont);
	private static final String FIRST_DIRECTOR = "First Director";

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
		Paragraph paragraph;
		Facility facility;
		document = new Document(PageSize.LETTER, 40, 40, 50, 50);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        PageNumberEventLeft pageNumber = new PageNumberEventLeft();
        writer.setPageEvent(pageNumber);

        document.open();
        
        doctable = getDocumentTable(specialist, endDate);
        
        if (licenses != null && licenses.size() > 0) {
            boolean moreLicenses = true;
            boolean facilityIsChanging = true;
            int idx = 0;
            FacilityLicenseView license = licenses.get(idx);
            while (moreLicenses) {
                facility = license.getFacility();
                doctable.getDefaultCell().setPaddingBottom(4);
                doctable.getDefaultCell().setBorderWidthBottom(.5f);
        		if (facilityIsChanging) {
        		    doctable.addCell(populateFacilityInformation(facility));
        		    facilityIsChanging = false;
        		}
        		
        		// Check to see if the next record will change the facility so we can put the
        		// proper bottom border.
        		idx++;
        		if (idx >= licenses.size()) {
        		    // this is the last license
        		    moreLicenses = false;
        		} else {
        		    FacilityLicenseView next = licenses.get(idx);
        		    if (!next.getFacilityId().equals(license.getFacilityId())) {
        		        facilityIsChanging = true;
        		    }
        		}
        		
        		// Set the bottom border based on the previous check findings
        		if (facilityIsChanging || !moreLicenses) {
        		    // Add a large bottom border
        		    doctable.getDefaultCell().setBorderWidthBottom(1.0f);
        		}
        		doctable.addCell(populateLicenseInformation(license));
        		
        		// Get the next license
        		if (moreLicenses) {
        		    license = licenses.get(idx);
        		}
        	}
        } else {
        	// No open applications
        	paragraph = new Paragraph(fixedLeading);
        	paragraph.setSpacingBefore(10);
        	paragraph.setAlignment(Element.ALIGN_CENTER);
        	paragraph.add(new Phrase("No active facility licenses found to display.", smallfont));
        	doctable.addCell(paragraph);
        }

        // Add the document table to the document
        document.add(doctable);
       	
       	document.close();
	}
	
	private static PdfPTable populateFacilityInformation(Facility facility) throws DocumentException {
		Paragraph paragraph;
		PdfPTable datatable = getFacilityDetailTable();

		// Add Facility Name information
		datatable.getDefaultCell().setColspan(4);
		datatable.getDefaultCell().setPaddingLeft(4);
		datatable.getDefaultCell().setPaddingRight(4);
		if (StringUtils.isNotBlank(facility.getName())) {
			datatable.addCell(new Phrase(facility.getName().toUpperCase(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}
		
    	// Add First Director(s) information
    	datatable.getDefaultCell().setColspan(1);
    	datatable.getDefaultCell().setPaddingLeft(4);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
    	datatable.addCell(new Phrase("Director(s):", smallfontB));
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(4);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
    	datatable.getDefaultCell().setColspan(3);
    	sb = new StringBuilder();
    	List<FacilityPerson> directors = facility.getPeopleOfType(FIRST_DIRECTOR, true);
    	if (directors != null && directors.size() > 0) {
    		for (FacilityPerson fp : directors) {
    			if (fp.getPerson() != null && StringUtils.isNotBlank(fp.getPerson().getFirstAndLastName())) {
    				if (sb.length() > 0) {
    					sb.append(", ");
    				}
    				sb.append(fp.getPerson().getFirstAndLastName().toUpperCase());
    			}
    		}
    	}
    	if (sb.length() > 0) {
    		datatable.addCell(new Phrase(sb.toString(), mediumfont));
    	} else {
    		datatable.addCell(BLANK);
    	}
    	
    	// Add Site information
    	datatable.getDefaultCell().setColspan(1);
    	datatable.getDefaultCell().setPaddingLeft(4);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
    	datatable.addCell(new Phrase("Site:", smallfontB));
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(4);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
    	datatable.getDefaultCell().setColspan(3);
    	if (StringUtils.isNotBlank(facility.getSiteName())) {
    		datatable.addCell(new Phrase(facility.getSiteName()));
    	} else {
    		datatable.addCell(BLANK);
    	}
    	
    	// Add Mailing address information
    	datatable.getDefaultCell().setColspan(1);
    	datatable.getDefaultCell().setPaddingLeft(4);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
    	datatable.addCell(new Phrase("Mailing:", smallfontB));
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(4);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
    	datatable.getDefaultCell().setColspan(3);
    	if (facility.getMailingAddress() != null && StringUtils.isNotBlank(facility.getMailingAddress().getAddressOne())) {
    		sb = new StringBuilder();
        	sb.append(facility.getMailingAddress().getAddressOne());
        	if (StringUtils.isNotBlank(facility.getMailingAddress().getAddressTwo())) {
        		sb.append(" "+facility.getMailingAddress().getAddressTwo());
        	}
        	if (StringUtils.isNotBlank(facility.getMailingAddress().getCityStateZip())) {
        		sb.append(", "+facility.getMailingAddress().getCityStateZip());
        	}
        	paragraph = new Paragraph(fixedLeading);
        	paragraph.add(new Phrase(sb.toString().toUpperCase(), smallfont));
        	datatable.addCell(paragraph);
    	} else {
    		datatable.addCell(BLANK);
    	}

    	// Add Location address information
    	datatable.getDefaultCell().setColspan(1);
    	datatable.getDefaultCell().setPaddingLeft(4);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
    	datatable.addCell(new Phrase("Location:", smallfontB));
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(4);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
    	datatable.getDefaultCell().setColspan(3);
    	if (facility.getLocationAddress() != null && StringUtils.isNotBlank(facility.getLocationAddress().getAddressOne())) {
    		sb = new StringBuilder();
    		sb.append(facility.getLocationAddress().getAddressOne());
        	if (StringUtils.isNotBlank(facility.getLocationAddress().getAddressTwo())) {
        		sb.append(" "+facility.getLocationAddress().getAddressTwo());
        	}
        	if (StringUtils.isNotBlank(facility.getLocationAddress().getCityStateZip())) {
        		sb.append(", "+facility.getLocationAddress().getCityStateZip());
        	}
        	paragraph = new Paragraph(fixedLeading);
        	paragraph.add(new Phrase(sb.toString().toUpperCase(), smallfont));
        	datatable.addCell(paragraph);
    	} else {
    		datatable.addCell(BLANK);
    	}
    	
    	// Add Phone information
    	// Primary Phone
    	datatable.getDefaultCell().setColspan(1);
    	datatable.getDefaultCell().setPaddingLeft(4);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
    	datatable.addCell(new Phrase("Phone:", smallfontB));
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
    	if (facility.getPrimaryPhone() != null && StringUtils.isNotBlank(facility.getPrimaryPhone().getFormattedPhoneNumber())) {
    		datatable.addCell(new Phrase(facility.getPrimaryPhone().getFormattedPhoneNumber(), smallfont));
    	} else {
    		datatable.addCell(BLANK);
    	}
    	// Site Phone
    	paragraph = new Paragraph(fixedLeading);
    	paragraph.add(new Phrase("Site Phone: ", smallfontB));
    	if (facility.getAlternatePhone() != null && StringUtils.isNotBlank(facility.getAlternatePhone().getFormattedPhoneNumber())) {
    		paragraph.add(new Phrase(facility.getAlternatePhone().getFormattedPhoneNumber(), smallfont));
    	}
    	datatable.getDefaultCell().setPaddingLeft(2);
    	datatable.getDefaultCell().setPaddingRight(2);
    	datatable.addCell(paragraph);
    	// Fax
    	paragraph = new Paragraph(fixedLeading);
    	paragraph.add(new Phrase("Fax: ", smallfontB));
    	if (facility.getFax() != null && StringUtils.isNotBlank(facility.getFax().getFormattedPhoneNumber())) {
    		paragraph.add(new Phrase(facility.getFax().getFormattedPhoneNumber(), smallfont));
    	}
    	datatable.getDefaultCell().setPaddingRight(4);
    	datatable.addCell(paragraph);

		return datatable;
	}
	
	private static PdfPTable populateLicenseInformation(FacilityLicenseView license) throws DocumentException {
		Paragraph paragraph;
		PdfPTable datatable = getLicenseDetailTable();
		
		// Put License Number
		datatable.getDefaultCell().setPaddingLeft(4);
		datatable.getDefaultCell().setPaddingRight(2);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(new Phrase("License No:", smallfontB));
		datatable.getDefaultCell().setPaddingLeft(4);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if (license.getLicenseNumber() != null) {
			datatable.addCell(new Phrase(license.getLicenseNumber().toString(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}
		
		// Put Service information
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(new Phrase("Service: ", smallfontB));
		datatable.getDefaultCell().setColspan(3);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if (StringUtils.isNotBlank(license.getServiceCodeDesc())) {
			datatable.addCell(new Phrase(license.getServiceCodeDesc(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}

		// Put License Expiration
		paragraph = new Paragraph(fixedLeading);
		paragraph.add(new Phrase("Expires: ", smallfontB));
		if (license.getExpirationDate() != null) {
			paragraph.add(new Phrase(df.format(license.getExpirationDate()), smallfont));
		}
		datatable.getDefaultCell().setColspan(1);
		datatable.getDefaultCell().setPaddingRight(4);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(paragraph);

		// Add the License SubType Information
		datatable.getDefaultCell().setPaddingLeft(4);
		datatable.getDefaultCell().setPaddingRight(2);
		datatable.addCell(new Phrase("Type:", smallfontB));
		datatable.getDefaultCell().setPaddingLeft(2);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if (StringUtils.isNotBlank(license.getSubtype())) {
			datatable.addCell(new Phrase(license.getSubtype(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}
		
		// Add Specific Service
		datatable.getDefaultCell().setPaddingLeft(2);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(new Phrase("Specific Service: ", smallfontB));
		datatable.getDefaultCell().setPaddingRight(4);
		datatable.getDefaultCell().setColspan(4);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if (StringUtils.isNotBlank(license.getSpecificServiceCode())) {
			datatable.addCell(new Phrase(license.getSpecificServiceCode(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}

		// Add License Capacity Information
		datatable.getDefaultCell().setColspan(1);
		datatable.getDefaultCell().setPaddingLeft(4);
		datatable.getDefaultCell().setPaddingRight(2);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(new Phrase("Age Group: ", smallfontB));
		datatable.getDefaultCell().setPaddingLeft(2);
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if (StringUtils.isNotBlank(license.getAgeGroup())) {
			datatable.addCell(new Phrase(license.getAgeGroup(), smallfont));
		} else {
			datatable.addCell(BLANK);
		}

		// Add Age Group total capacity
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		datatable.addCell(new Phrase("Capacity:", smallfontB));
		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		if ("Youth".equalsIgnoreCase(license.getAgeGroup())) {
			// Youth capacity
			if (license.getYouthTotalSlots() != null) {
				datatable.addCell(new Phrase(license.getYouthTotalSlots().toString(), smallfont));
			} else {
				datatable.addCell(BLANK);
			}
		} else {
			// Adult capacity
			if (license.getAdultTotalSlots() != null) {
				datatable.addCell(new Phrase(license.getAdultTotalSlots().toString(), smallfont));
			} else {
				datatable.addCell(BLANK);
			}
		}
		
		// Add Age Group ages
		paragraph = new Paragraph(fixedLeading);
		paragraph.add(new Phrase("Ages: ", smallfontB));
        if (license.getFromAge() != null || license.getToAge() != null) {
    		sb = new StringBuilder();
        	if (license.getFromAge() != null) {
        		sb.append(license.getFromAge().toString());
            	if (license.getToAge() != null) {
            		sb.append(" to "+license.getToAge().toString());
            	} else {
            		sb.append(" and Older");
            	}
        	} else {
        		sb.append("to "+license.getToAge().toString());
        	}
        	paragraph.add(new Phrase(sb.toString(), smallfont));
        }
        datatable.addCell(paragraph);
        
        // Add Age Group Male and Female Counts
        if ("Adult".equalsIgnoreCase(license.getAgeGroup()) || "Youth".equalsIgnoreCase(license.getAgeGroup())) {
        	if ("Adult".equalsIgnoreCase(license.getAgeGroup())) {
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Male: ", smallfontB));
            	if (license.getAdultMaleCount() != null) {
            		paragraph.add(new Phrase(license.getAdultMaleCount().toString(), smallfont));
            	}
            	datatable.addCell(paragraph);
            	
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Female: ", smallfontB));
            	if (license.getAdultFemaleCount() != null) {
            		paragraph.add(new Phrase(license.getAdultFemaleCount().toString(), smallfont));
            	}
            	datatable.getDefaultCell().setPaddingRight(4);
            	datatable.addCell(paragraph);
        	} else {
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Male: ", smallfontB));
            	if (license.getYouthMaleCount() != null) {
            		paragraph.add(new Phrase(license.getYouthMaleCount().toString(), smallfont));
            	}
            	datatable.addCell(paragraph);
            	
            	paragraph = new Paragraph(fixedLeading);
            	paragraph.add(new Phrase("Female: ", smallfontB));
            	if (license.getYouthFemaleCount() != null) {
            		paragraph.add(new Phrase(license.getYouthFemaleCount().toString(), smallfont));
            	}
            	datatable.getDefaultCell().setPaddingRight(4);
            	datatable.addCell(paragraph);
        	}
        } else {
        	datatable.getDefaultCell().setColspan(2);
        	datatable.addCell(BLANK);
        }
		
		// Add Program Code Information
        if (license.getProgramCodeIds() != null && license.getProgramCodeIds().size() > 0) {
        	for (PickListValue prog : license.getProgramCodeIds()) {
        		datatable.getDefaultCell().setColspan(1);
        		datatable.getDefaultCell().setPaddingLeft(4);
        		datatable.getDefaultCell().setPaddingRight(2);
        		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
        		datatable.addCell(new Phrase("Program:", smallfontB));
        		datatable.getDefaultCell().setColspan(6);
        		datatable.getDefaultCell().setPaddingRight(4);
        		datatable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        		String program = getPickListValueDesc(prog);
        		if (StringUtils.isNotBlank(program)) {
        			datatable.addCell(new Phrase(program, smallfont));
        		} else {
        			datatable.addCell(BLANK);
        		}
        	}
        }

        return datatable;
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
        
		PdfPTable table = new PdfPTable(2);
		// format the table
		int headerwidths[] = {80, 20}; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(largeFixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setPaddingLeft(4);
		table.getDefaultCell().setPaddingRight(2);
		if (endDate == null) {
			table.getDefaultCell().setPaddingBottom(17);
		}
		table.getDefaultCell().setBorderWidthLeft(0);
		table.getDefaultCell().setBorderWidthRight(0);
		table.getDefaultCell().setBorderWidthTop(1.0f);
		table.getDefaultCell().setBorderWidthBottom(0);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		
		// Add Page header information
		sb = new StringBuilder();
		sb.append("Facility Licenses for ");
		if (StringUtils.isNotBlank(specialist.getFirstAndLastName())) {
			sb.append(specialist.getFirstAndLastName());
		}
		table.addCell(new Phrase(sb.toString(), largefontB));
		
		// Add report date
		table.getDefaultCell().setPaddingLeft(2);
		table.getDefaultCell().setPaddingRight(4);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
		table.addCell(new Phrase(df.format(today), mediumfontB));
		
		if (endDate != null) {
			// Add expiration end date date
			table.getDefaultCell().setPaddingLeft(8);
			table.getDefaultCell().setPaddingRight(4);
			table.getDefaultCell().setPaddingBottom(0);
			table.getDefaultCell().setBorderWidthTop(0);
			table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			table.getDefaultCell().setColspan(2);
			table.addCell(new Phrase("With Licenses Expiring " + df.format(endDate), mediumfontB));
		}

		return table;
	}
	
	private static PdfPTable getFacilityDetailTable() throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		// format the table
		int headerwidths[] = { 11, 18, 29, 42 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		return table;
	}

	private static PdfPTable getLicenseDetailTable() throws DocumentException {
		PdfPTable table = new PdfPTable(7);
		// format the table
		int headerwidths[] = { 11, 18, 15, 8, 17, 12, 19 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		return table;
	}
	
	private static String getPickListValueDesc(PickListValue plv) {
		String rval = null;
		if (plv != null && StringUtils.isNotBlank(plv.getValue())) {
			String code = plv.getValue();
			int idx = code.indexOf("-");
			if (idx >= 0 && (idx+1 <= code.length())) {
				idx++;
				rval = code.substring(idx).trim();
			}
		}
		return rval;
	}

}
