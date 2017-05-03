package gov.utah.dts.det.ccl.actions.reports.generators;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressWarnings("unused")
public class LicenseRenewalLettersReport {

	private static final Logger log = LoggerFactory.getLogger(LicenseRenewalLettersReport.class);

    // Define report fonts
    static final Font mediumfont = FontFactory.getFont(FontFactory.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
    static final Font mediumfontB = FontFactory.getFont(FontFactory.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
    
    static final Float fixedLeading = 13.0f;
    static final Float listItemSpace = 6.0f;
    static final Float listSpace = 12.0f;
    static final Float pageSeparatorSpace = 13.0f;
    static final Float indent = 25.0f;
    static final Float dateIndent = 395.0f;
    static final Float rightIndent = 265.0f;
    
    static final Phrase BLANK = new Phrase(" ", mediumfont);

	static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static ByteArrayOutputStream generate(java.util.List<FacilityLicenseView> licenses) {
		ByteArrayOutputStream ba = null;
		FacilityLicenseView license;
		Document document = null;
        Date today = new Date();
		try {
			ba = new ByteArrayOutputStream();
			document = new Document(PageSize.LETTER, 75, 75, 175, 50);
			PdfWriter writer = PdfWriter.getInstance(document, ba);

	        document.open();
	        
	        if (licenses != null && licenses.size() > 0) {
	        	for (int i = 0; i < licenses.size(); i++) {
	        		license = licenses.get(i);
	        		generateDocumentPage(license, today, document, writer);
	        		// If there is another letter to print add a new page to the document
	        		if (i < licenses.size()-1) {
	        			document.newPage();
	        		}
	        	}
	        } else {
	        	Paragraph paragraph = new Paragraph(fixedLeading);
	        	paragraph.add(new Phrase("No renewal licenses were found to print.", mediumfont));
	        	document.add(paragraph);
	        }

	        document.close();
		} catch(Exception ex) {
			log.error(ex.getMessage());
			ba = null;
		}
        return ba;
	}
	
	private static void generateDocumentPage(FacilityLicenseView license, Date today, Document document, PdfWriter writer)
	throws BadElementException, DocumentException, Exception {
		PdfPTable table = null;
		int headerwidths[] = {};
		Paragraph paragraph = null;
		com.itextpdf.text.List blist = null;
		com.itextpdf.text.List subList = null;
        ListItem item = null;
        ListItem subItem = null;
        StringBuilder sb;
        PdfContentByte over = writer.getDirectContent();
        Facility facility = license.getFacility();
        Person licensingSpecialist = null;
        if (facility != null && facility.getLicensingSpecialist() != null) {
        	licensingSpecialist = facility.getLicensingSpecialist();
        }

        // Add report date
        paragraph = new Paragraph(fixedLeading);
        paragraph.add(new Phrase(df.format(today), mediumfont));
        paragraph.setIndentationLeft(dateIndent);
        document.add(paragraph);

        // Add facility information
        paragraph = new Paragraph(fixedLeading);
        if (facility != null && StringUtils.isNotBlank(facility.getName())) {
        	paragraph.add(new Phrase(facility.getName(), mediumfont));
        } else {
        	paragraph.add(BLANK);
        }
        if (facility != null && facility.getMailingAddress() != null && StringUtils.isNotBlank(facility.getMailingAddress().getAddressOne())) {
            paragraph.add(Chunk.NEWLINE);
        	paragraph.add(new Phrase(facility.getMailingAddress().getAddressOne(), mediumfont));
        	if (StringUtils.isNotBlank(facility.getMailingAddress().getAddressTwo())) {
                paragraph.add(Chunk.NEWLINE);
            	paragraph.add(new Phrase(facility.getMailingAddress().getAddressTwo(), mediumfont));
        	}
        	if (StringUtils.isNotBlank(facility.getMailingAddress().getCityStateZip())) {
                paragraph.add(Chunk.NEWLINE);
            	paragraph.add(new Phrase(facility.getMailingAddress().getCityStateZip(), mediumfont));
        	}
        }
        paragraph.setSpacingBefore(15);
        document.add(paragraph);
        
        // Add salutation
        paragraph = new Paragraph(fixedLeading);
        sb = new StringBuilder();
        sb.append("Dear ");
        if (StringUtils.isNotBlank(facility.getName())) {
        	sb.append(facility.getName());
        }
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(pageSeparatorSpace);
        document.add(paragraph);
        
        // Add due for renewal line
        paragraph = new Paragraph(fixedLeading);
        paragraph.add(new Phrase("Your foster care license is due for renewal on " + df.format(license.getExpirationDate()), mediumfontB));
        paragraph.setSpacingBefore(pageSeparatorSpace);
        document.add(paragraph);

        // Add first paragraph
        paragraph = new Paragraph(fixedLeading);
        sb = new StringBuilder();
        sb.append("The Office of Licensing appreciates the services you have provided for DCFS and to the foster children ");
        sb.append("who have been in your care. We hope that you will continue as foster parents for the next year. ");
        sb.append("To continue licensing please complete the following:");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(pageSeparatorSpace);
        paragraph.setSpacingAfter(pageSeparatorSpace);
        document.add(paragraph);
        
        /*
         * Start of instructions list section
         */
        blist = new com.itextpdf.text.List(false,20);
        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("1.", mediumfont));
        item.add(new Phrase("Complete the enclosed Renewal Resource Family Application.", mediumfont));
        item.setSpacingAfter(listSpace);
        blist.add(item);

        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("2.", mediumfont));
        sb = new StringBuilder();
        sb.append("Complete the Utah Department of Human Services Office of Licensing Background Screening Application form for everyone ");
        sb.append("18 years of age and older living in the home. Each form must have an original signature.");
        item.add(new Phrase(sb.toString(), mediumfont));
        item.setSpacingAfter(listSpace);
        blist.add(item);
        
        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("3.", mediumfont));
        paragraph = new Paragraph(fixedLeading);
        paragraph.add(new Phrase("For everyone 18 years of age or older living in the home please attach:", mediumfont));
        item.add(paragraph);
        subList = new com.itextpdf.text.List(false,18);
        subList.setIndentationLeft(10);
        subItem = new ListItem(fixedLeading);
        subItem.setListSymbol(new Chunk("(a)", mediumfontB));
        subItem.add(new Phrase("A legible copy of a current drivers license or a Utah State I.D. card.", mediumfontB));
        subList.add(subItem);
        subItem = new ListItem(fixedLeading);
        subItem.setListSymbol(new Chunk("(b)", mediumfontB));
        subItem.add(new Phrase("A legible copy of their social security card.", mediumfontB));
        subList.add(subItem);
        item.add(subList);
        item.setSpacingAfter(listSpace);
        blist.add(item);
        
        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("4.", mediumfont));
        item.add(new Phrase("Complete the enclosed Foster Care Renewal Information Form.", mediumfont));
        item.setSpacingAfter(listSpace);
        blist.add(item);

        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("5.", mediumfont));
        item.add(new Phrase("Provide copies of income verification (check stubs/or last year's income tax forms.)", mediumfontB));
        item.setSpacingAfter(listSpace);
        blist.add(item);

        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("6.", mediumfont));
        item.add(new Phrase("All paperwork must be returned 30 days before your license expires to:", mediumfontB));
        subList = new com.itextpdf.text.List(false, 0);
        subList.setListSymbol(new Chunk(" ", mediumfont));
        subList.setIndentationLeft(10);
        subItem = new ListItem(fixedLeading);
        subItem.add(new Phrase("Office of Licensing", mediumfont));
        subItem.setSpacingBefore(listItemSpace);
        subList.add(subItem);
    	subItem = new ListItem(fixedLeading);
        if (licensingSpecialist != null && StringUtils.isNotBlank(licensingSpecialist.getFirstAndLastName())) {
        	subItem.add(new Phrase(licensingSpecialist.getFirstAndLastName(), mediumfont));
        } else {
        	subItem.add(new Phrase("<Facility Licensing Specialist>", mediumfont));
        }
    	subList.add(subItem);
    	if (licensingSpecialist != null && licensingSpecialist.getAddress() != null && StringUtils.isNotBlank(licensingSpecialist.getAddress().getAddressOne())) {
            subItem = new ListItem(fixedLeading);
            subItem.add(new Phrase(licensingSpecialist.getAddress().getAddressOne(), mediumfont));
            subList.add(subItem);
    	}
        if (licensingSpecialist != null && licensingSpecialist.getAddress() != null && StringUtils.isNotBlank(licensingSpecialist.getAddress().getAddressTwo())) {
            subItem = new ListItem(fixedLeading);
            subItem.add(new Phrase(licensingSpecialist.getAddress().getAddressTwo(), mediumfont));
            subList.add(subItem);
        }
        if (licensingSpecialist != null && licensingSpecialist.getAddress() != null && StringUtils.isNotBlank(licensingSpecialist.getAddress().getCityStateZip())) {
            subItem = new ListItem(fixedLeading);
            subItem.add(new Phrase(licensingSpecialist.getAddress().getCityStateZip(), mediumfont));
            subList.add(subItem);
        }
        item.add(subList);
        item.setSpacingAfter(listSpace);
        blist.add(item);

        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("7.", mediumfont));
        item.add(new Phrase("Please call and schedule an appointment with me for your annual health and safety check.", mediumfont));
        item.setSpacingAfter(listSpace);
        blist.add(item);

        item = new ListItem(fixedLeading);
        item.setListSymbol(new Chunk("8.", mediumfont));
        paragraph = new Paragraph(fixedLeading);
        sb = new StringBuilder();
        sb.append("Complete the DCFS required renewal training of 12 hours for the primary provider and 4 hours for a spouse ");
        sb.append("before your license expires. Please send all training to be approved and documented to the Utah Foster Care Foundation.");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        item.add(paragraph);
        paragraph = new Paragraph(fixedLeading);
        sb = new StringBuilder();
        sb.append("The Utah Foster Care Foundation will then provide verification of all completed required training ");
        sb.append("to your local licensor.");
        paragraph.add(new Phrase(sb.toString(), mediumfontB));
        item.add(paragraph);
        blist.add(item);

        document.add(blist);
        /*
         * End of instructions list section
         */
        
        // Add final paragraph
        paragraph = new Paragraph(fixedLeading);
        paragraph.setSpacingBefore(pageSeparatorSpace);
        paragraph.add(new Phrase("Your license will expire if all renewal requirements are not completed by the end of the licensing month. ", mediumfont));
        paragraph.add(new Phrase("A license expired beyond 30 days will require initiation of the background screening process again (including ", mediumfontB));
        paragraph.add(new Phrase("any necessary fingerprinting). ", mediumfontB));
        paragraph.add(new Phrase("If you choose to discontinue providing services or have any questions, please contact me at ", mediumfont));
        if (licensingSpecialist.getWorkPhone() != null && StringUtils.isNotBlank(licensingSpecialist.getWorkPhone().getFormattedPhoneNumber())) {
            paragraph.add(new Phrase(licensingSpecialist.getWorkPhone().getFormattedPhoneNumber(), mediumfontB));
        }
        paragraph.add(new Phrase(".", mediumfontB));
        document.add(paragraph);
        
        // Add closing
        paragraph = new Paragraph(fixedLeading);
        paragraph.setIndentationLeft(rightIndent);
        paragraph.setSpacingBefore(2*pageSeparatorSpace);
        paragraph.add(new Phrase("Sincerely,", mediumfont));
        document.add(paragraph);
        
        paragraph = new Paragraph(fixedLeading);
        paragraph.setIndentationLeft(rightIndent);
        paragraph.setSpacingBefore(3*pageSeparatorSpace);
        if (licensingSpecialist != null && StringUtils.isNotBlank(licensingSpecialist.getFirstAndLastName())) {
        	paragraph.add(new Phrase(licensingSpecialist.getFirstAndLastName(), mediumfont));
        } else {
        	paragraph.add(new Phrase("<Facility Licensing Specialist>", mediumfont));
        }
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("Foster Family Licensing Specialist", mediumfont));
        document.add(paragraph);
        
        // Add Enclosures
        paragraph = new Paragraph(fixedLeading);
        paragraph.setSpacingBefore(pageSeparatorSpace);
        paragraph.add(new Phrase("Enclosures", mediumfont));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(new Phrase("cc: Provider Record", mediumfont));
        document.add(paragraph);
	}

}
