package gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.reports.FloatingLinedPdfPCell;
import gov.utah.dts.det.reports.FloatingPdfPCell;
import gov.utah.dts.det.util.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class LivescanAuthorization {

	private static final Logger log = LoggerFactory.getLogger(LivescanAuthorization.class);

    // Define report fonts
    static final Font smallfontT = FontFactory.getFont("Times-Roman", 8, Font.NORMAL, BaseColor.BLACK);
    static final Font smallfont = FontFactory.getFont(FontFactory.HELVETICA, 8.5f, Font.NORMAL, BaseColor.BLACK);
    static final Font smallfontB = FontFactory.getFont(FontFactory.HELVETICA, 8.5f, Font.BOLD, BaseColor.BLACK);
    static final Font smallfontBU = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8.5f, Font.UNDERLINE, BaseColor.BLACK);
    static final Font mediumfont = FontFactory.getFont(FontFactory.HELVETICA, 9.5f, Font.NORMAL, BaseColor.BLACK);
    static final Font mediumfontB = FontFactory.getFont(FontFactory.HELVETICA, 9.5f, Font.BOLD, BaseColor.BLACK);
    static final Font mediumfontU = FontFactory.getFont(FontFactory.HELVETICA, 9.5f, Font.UNDERLINE, BaseColor.BLACK);
    static final Font largefontB = FontFactory.getFont(FontFactory.HELVETICA, 12.0f, Font.BOLD, BaseColor.BLACK);
    static final Font xlargefontB = FontFactory.getFont(FontFactory.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK);
    
    static final Float fixedLeadingSmall = 10.0f;		// The paragraph line spacing for small fonts
    static final Float fixedLeadingMedium = 11.0f;		// The paragraph line spacing for medium fonts
    static final Float fixedLeadingLarge = 13.0f;		// The paragraph line spacing for large fonts
    static final Float page1ListSpace = 8.0f;
    static final Float page1SeparatorSpace = 9.0f;
    static final Float page2SeparatorSpace = 10.0f;
    static final Float indent = 25.0f;
    
    static final Phrase SMALL_BLANK = new Phrase(" ", smallfont);

    public static ByteArrayOutputStream generate(TrackingRecordScreeningLetter screeningLetter) {
		ByteArrayOutputStream ba = null;
		Document document = null;
		try {
			ba = new ByteArrayOutputStream();
			document = new Document(PageSize.LETTER, 35, 35, 75, 0);
			PdfWriter writer = PdfWriter.getInstance(document, ba);

	        document.open();
	        
	        // LS Authorization Letter Page 1
	        generateDocumentPage1(screeningLetter, document, writer);

	        // Shift to page 2
	        document.newPage();

	        // LS Authorization Letter Page 2
	        generateDocumentPage2(screeningLetter, document, writer);

	        document.close();
		} catch(Exception ex) {
			log.error(ex.getMessage());
			ba = null;
		}
        return ba;
	}
	
	private static void generateDocumentPage1(TrackingRecordScreeningLetter screeningLetter, Document document, PdfWriter writer)
	throws BadElementException, DocumentException, Exception {
		PdfPTable table = null;
		int headerwidths[] = {};
		Paragraph paragraph = null;
        List blist = null;
        ListItem item = null;
        ListItem subItem = null;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        // LS Authorization Letter Page 1
        addLetterIdentifier(screeningLetter, document);
        
        paragraph = new Paragraph(fixedLeadingLarge);
        paragraph.add(new Phrase("Office of Licensing Authorization", largefontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingLarge);
        paragraph.add(new Phrase("for Electronic Background Check", largefontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        
        paragraph = new Paragraph(fixedLeadingLarge);
        paragraph.add(new Phrase(df.format(screeningLetter.getLetterDate()), mediumfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("Office of Licensing Information", mediumfontU));
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Type of Background Check Required and Agency Billing Code:", smallfont));
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        /*
         * Start of Billing information line generation
         */
        table = new PdfPTable(3);
        // format the table
        headerwidths = new int[]{20,45,35}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1SeparatorSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        // Add Billing Code
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null &&
        	screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling() != null &&
        	StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue())) {
        	table.addCell(new Phrase(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue(), xlargefontB));
        } else {
        	table.addCell(new Phrase(" ", xlargefontB));
        }
        
        // Add Fee information
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Search Fee = ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && 
        	screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getSearchFee() != null) 
        {
        	paragraph.add(new Phrase(CommonUtils.fromDoubleToCurrency(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getSearchFee()), smallfontB));
        } else {
        	paragraph.add(SMALL_BLANK);
        }
        paragraph.add(new Phrase(" /  Scan Fee = ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && 
            screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getScanFee() != null) 
        {
        	paragraph.add(new Phrase(CommonUtils.fromDoubleToCurrency(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getScanFee()), smallfontB));
        }
        table.addCell(paragraph);
        
        // Add Facility Type information
        table.addCell(new Phrase("Licensed Treatment Programs", smallfont));
        // Add Billing Information Line Table to document
        document.add(table);
        /*
         * End of Billing information line table generation
         */
        
        // Add Check Number document line
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Check Number  ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getMoNumber())) {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getMoNumber(), smallfontB));
        }
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        /*
         * Start of Check Issuer information line
         */
        table = new PdfPTable(2);
        // format the table
        headerwidths = new int[]{40, 60}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1SeparatorSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        // Add Issuer information
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Issuer  ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getIssuedBy())) {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getIssuedBy(), smallfontB));
        }
        table.addCell(paragraph);
        
        // Add Program Name information
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Program Name  ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getFacility() != null &&
        	StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getFacility().getName()))
        {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFacility().getName(), smallfontB));
        }
        table.addCell(paragraph);
        // Add the Check Issuer table to the document
        document.add(table);
        /*
         * End of Check Issuer information line
         */

        /*
         * Start of Applicant information line generation
         */
        table = new PdfPTable(3);
        // format the table
        headerwidths = new int[]{43,20,37}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1SeparatorSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);

        // Add Applicant name
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Applicant  ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getPerson() != null && StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getFirstAndLastName())) {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFirstAndLastName(), smallfontB));
        }
        table.addCell(paragraph);
        
        // Add Applicant ID
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("ID  ", smallfont));
        if (StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getPersonIdentifier())) {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getPersonIdentifier(), smallfontB));
        }
        table.addCell(paragraph);
        
        // Add DOB information
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("DOB  ", smallfont));
        try {
        	paragraph.add(new Phrase(df.format(screeningLetter.getTrackingRecordScreening().getBirthday()), smallfontB));
        } catch (NullPointerException e) {
        	
        }
        table.addCell(paragraph);
        // Add Applicant Information Line Table to document
        document.add(table);
        /*
         * End of Applicant information line generation
         */
        
        // Add Facility Licensing Specialist Information
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("OL Representative  ", smallfont));
        if (screeningLetter.getTrackingRecordScreening().getFacility() != null &&
        	screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist() != null &&
        	StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist().getFirstAndLastName())) 
        {
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist().getFirstAndLastName(), smallfontB));
        }
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        // Add Distribution Line
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Distribution:  Original for the Applicant to turn in to the Live Scan Operator. Copy retained by Office of Licensing.", smallfont));
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        // Add Program & Applicatant header line
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("Program & Applicant Information and Instructions", smallfontBU));
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        // Add READ THIS CAREFULLY
        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("READ THIS CAREFULLY", mediumfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        /*
         * Start of instructions list section
         */
        blist = new List(false,20);
        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("1.", mediumfont));
        item.add(new Phrase("The Office of Licensing authorizes the applicant to submit her/his fingerprints for an electronic applicant background check ", smallfont));
        item.add(new Phrase("at various sites throughout Utah using the Live Scan system. Please see the enclosed list for site information. Each site ", smallfont));
        item.add(new Phrase("charges a fee for the electronic fingerprint scan. Scanning fees vary from site to site. This is a separate fee from the ", smallfont));
        item.add(new Phrase("one submitted to the Department of Human Services for the actual criminal background search.", smallfont));
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);

        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("2.", mediumfont));
        item.add(new Phrase("Complete electronic fingerprint submission within 15 days of the date of this authorization letter. If unused, ", smallfontB));
        item.add(new Phrase("requests for refunds will not be considered after 30 days. ", smallfontB));
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);
        
        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("",smallfont));
        item.add(new Phrase("Refund requests require a letter of explanation from the licensed program accompanied by this original authorization letter.", smallfontBU));
        item.setSpacingBefore(4.0f);
        blist.add(item);

        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("",smallfont));
        item.add(new Phrase("Failure to complete electronic fingerprint submission within this time will result in the denial of the background screening ", smallfontB));
        item.add(new Phrase("clearance and the applicant will not be permitted to have direct access to children or vulnerable adults, will not be eligible ", smallfontB));
        item.add(new Phrase("to provide services to programs licensed by the Utah Department of Human Services, Office of Licensing, and will not be ", smallfontB));
        item.add(new Phrase("eligible to proceed with foster care or adoption.", smallfontB));
        item.setSpacingBefore(4.0f);
        blist.add(item);

        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("3.", mediumfont));
        paragraph = new Paragraph(fixedLeadingSmall);
        paragraph.add(new Phrase("You will need to take with you:", smallfont));
        item.add(paragraph);
        List subList = new List(false,10);
        subList.setIndentationLeft(10);
        subItem = new ListItem(fixedLeadingSmall);
        subItem.add(new Phrase("This original letter. Photocopies and facsimile (FAX) copies will not be accepted.", smallfont));
        subList.add(subItem);
        subItem = new ListItem(fixedLeadingSmall);
        subItem.add(new Phrase("Photo identification in the form of your driver license or state identification card issued by the Division of Motor Vehicles.", smallfont));
        subList.add(subItem);
        subItem = new ListItem(fixedLeadingSmall);
        subItem.add(new Phrase("Social Security Card", smallfont));
        subList.add(subItem);
        subItem = new ListItem(fixedLeadingSmall);
        subItem.add(new Phrase("Cash or check as required (see site list for acceptable form of payment).", smallfont));
        subList.add(subItem);
        item.add(subList);
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);
        
        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("4.", mediumfont));
        item.add(new Phrase("If the electronically submitted fingerprints are rejected, the Office of Licensing will notify the applicant/licensed program ", smallfont));
        item.add(new Phrase("of additional instructions for completing the nationwide background search.", smallfont));
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);

        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("5.", mediumfont));
        item.add(new Phrase("Legibly print the following information for the scanning operator:", smallfont));
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);

        document.add(blist);

        document.add(getPage1WriteInLine1());
        document.add(getPage1WriteInLine2());
        document.add(getPage1WriteInLine3());
        
        blist = new List(false,20);
        item = new ListItem(fixedLeadingSmall);
        item.setListSymbol(new Chunk("6.", mediumfont));
        item.add(new Phrase("Applicant Signature ___________________________________________________  Date ________________", smallfont));
        item.setSpacingBefore(page1ListSpace);
        blist.add(item);
        document.add(blist);

        paragraph = new Paragraph();
        paragraph.add(new Phrase("A current list of Livescan sites is available at www.hslic.utah.gov/docs/livescan sites.pdf", smallfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(page1SeparatorSpace);
        document.add(paragraph);
        
        if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null &&
        	screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling() != null &&
        	StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue())) 
        {
        	paragraph = new Paragraph();
        	paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue(), xlargefontB));
        	paragraph.setAlignment(Element.ALIGN_RIGHT);
        	paragraph.setSpacingBefore(page1SeparatorSpace);
        	document.add(paragraph);
        }
        /*
         * End of instructions list section
         */
	}

	private static void generateDocumentPage2(TrackingRecordScreeningLetter screeningLetter, Document document, PdfWriter writer)
	throws BadElementException, DocumentException, Exception {
		Paragraph paragraph = null;
		PdfPTable datatable = null;
        List blist = null;
        ListItem item = null;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        addLetterIdentifier(screeningLetter, document);

        // Add confidential header to document
        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("CONFIDENTIAL", mediumfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        // Add Date header
        datatable = getPage2HeaderTable();
        datatable.addCell(new Phrase("Date:", mediumfont));
        datatable.addCell(new Phrase(df.format(screeningLetter.getLetterDate()), mediumfont));
        datatable.setSpacingBefore(20);
        document.add(datatable);

        // Add To header
        datatable = getPage2HeaderTable();
        datatable.addCell(new Phrase("To:", mediumfont));
        datatable.addCell(new Phrase("Director, "+screeningLetter.getTrackingRecordScreening().getFacility().getName(), mediumfont));
        datatable.setSpacingBefore(page2SeparatorSpace);
        document.add(datatable);

        // Add From header
        datatable = getPage2HeaderTable();
        datatable.addCell(new Phrase("From:", mediumfont));
        datatable.addCell(new Phrase(screeningLetter.getCreatedBy().getFirstAndLastName()+", Criminal Background Screening Unit", mediumfont));
        datatable.setSpacingBefore(page2SeparatorSpace);
        document.add(datatable);
        
        // Add Subject Header
        datatable = getPage2HeaderTable();
        datatable.addCell(new Phrase("Re:", mediumfont));
        datatable.addCell(new Phrase("Background Screening Request for "+screeningLetter.getTrackingRecordScreening().getFirstAndLastName()+" ("+screeningLetter.getTrackingRecordScreening().getPersonIdentifier()+")", mediumfont));
        datatable.setSpacingBefore(page2SeparatorSpace);
        document.add(datatable);
        
        // Start adding letter body information
        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("As a follow-up to your request for background screening by the Department of Human Services, Office of Licensing, ", mediumfont));
        paragraph.add(new Phrase("this is to notify you that additional criminal background screening informaiton is needed (UCA62A-2-120 and R501-14). ", mediumfont));
        paragraph.add(new Phrase("The applicant may have an unresolved issue. Please let the applicant know that s/he must resolve this matter before the ", mediumfont));
        paragraph.add(new Phrase("background screening can be conducted by the Office of Licensing. Once the applicant has resolved the issue, the applicant, ", mediumfont));
        paragraph.add(new Phrase("facility representative or foster care licensor must notify the Office of Licensing in writing in order for background ", mediumfont));
        paragraph.add(new Phrase("screening to proceed.", mediumfont));
        paragraph.setSpacingBefore(page2SeparatorSpace);
        document.add(paragraph);
        
        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("This person is not cleared under licensing standards to have direct access to children or vulnerable adults:", mediumfontB));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        blist = new List(false, 10);
        blist.setIndentationLeft(indent);
        blist.setListSymbol(new Chunk(">", mediumfont));
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("If you choose to employ or retain this person, you are assuming full liability and must make sure the individual ", mediumfont));
        item.add(new Phrase("works under direct supervision, under the uninterrupted visual and auditory surveillance of the person doing the supervising.", mediumfont));
        blist.add(item);
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("If the person is to provide foster care services, a license will not be granted until the background screening is complete.", mediumfont));
        blist.add(item);
        document.add(blist);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("Please give a copy of this letter to the applicant and instruct the applicant to resolve the issue.", mediumfontB));
        paragraph.setSpacingBefore(page2SeparatorSpace);
        document.add(paragraph);

        blist = new List(false, 10);
        blist.setIndentationLeft(indent);
        blist.setListSymbol(new Chunk(">", mediumfont));
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("S/he must resolve this matter before the background screening can be completed by the Office of Licensing.", mediumfont));
        blist.add(item);
        document.add(blist);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("The Background Screening Technician must be notified of the resolution of the issue:", mediumfontB));
        paragraph.setSpacingBefore(page2SeparatorSpace);
        document.add(paragraph);

        blist = new List(false, 10);
        blist.setIndentationLeft(indent);
        blist.setListSymbol(new Chunk(">", mediumfont));
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("If I do not receive information within 15 calendar days of the date of this notice regarding resolution of the issue, ", mediumfont));
        item.add(new Phrase("the Office of Licensing will close the background screening request for failure to provide information, and the ", mediumfont));
        item.add(new Phrase("applicant will not be allowed to have direct access to children or vulnerable adults in licensed programs.", mediumfont));
        blist.add(item);
        document.add(blist);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("Issue needing resolution:", mediumfontB));
        paragraph.setSpacingBefore(page2SeparatorSpace);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("[ X ] The Background screening application cannot be processed if the Livescan procedure is not completed.", mediumfontB));
        paragraph.setSpacingBefore(page2SeparatorSpace);
        document.add(paragraph);

        blist = new List(false, 10);
        blist.setIndentationLeft(indent);
        blist.setListSymbol(new Chunk(">", mediumfont));
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("Please have applicant submit to electronic fingerprinting using the Authorization form issued and mailed to your office.", mediumfont));
        blist.add(item);
        item = new ListItem(fixedLeadingMedium);
        item.add(new Phrase("If the applicant is no longer pursuing employment or applying to provide services to your program, please send me a ", mediumfont));
        item.add(new Phrase("statement on letterhead so I may close this file.", mediumfont));
        blist.add(item);
        document.add(blist);

        // Add document footer
        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("*The Office of Licensing will accept a court record faxed by the court to the Office of Licensing at", smallfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(15);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("(801) 538-4669.", smallfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(0);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("Thank you for your attention to providing complete and accurate information necessary for the background screening.", smallfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(0);
        document.add(paragraph);

        paragraph = new Paragraph(fixedLeadingMedium);
        paragraph.add(new Phrase("If you have any questions, please call the Office of Licensing at 801-538-4242.", smallfontB));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingBefore(0);
        document.add(paragraph);
	}

	private static void addLetterIdentifier(TrackingRecordScreeningLetter screeningLetter, Document document) 
	throws BadElementException, DocumentException, Exception {
        // Add letter identifier and revision information
        Paragraph paragraph = new Paragraph(10.0f);
		if (screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null &&
	        screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling() != null &&
	        StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue())) 
		{
	        paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue()+" LS Author", smallfontT));
	    } else {
	        paragraph.add(new Phrase("LS Author", smallfontT));
	    }
        paragraph.setIndentationLeft(415);
        document.add(paragraph);
        paragraph.clear();
        paragraph.add(new Phrase("Rev 2/11", smallfontT));
        paragraph.setSpacingAfter(30);
        document.add(paragraph);
	
	}

	private static PdfPTable getPage1WriteInLine1()
	throws BadElementException, DocumentException, Exception {
    	PdfPTable table = new PdfPTable(2);
    	PdfPTable dtl = null;

        int headerwidths[] = {4,96}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1ListSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Redefine the headerwidths for the detail table
        headerwidths = new int[]{25,25,25,25}; // percentage

		dtl = new PdfPTable(4);
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        dtl.getDefaultCell().setCellEvent(new FloatingLinedPdfPCell());
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(SMALL_BLANK);
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

		dtl = new PdfPTable(4);
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        dtl.getDefaultCell().setCellEvent(new FloatingPdfPCell());
        dtl.addCell(new Phrase("Last Name", smallfont));
        dtl.addCell(new Phrase("Full First Name", smallfont));
        dtl.addCell(new Phrase("Full Middle Name", smallfont));
        dtl.addCell(new Phrase("AKA", smallfont));
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

		dtl = new PdfPTable(4);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        dtl.getDefaultCell().setCellEvent(new FloatingPdfPCell());
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("If no middle name, write NA.", smallfont));
        dtl.addCell(SMALL_BLANK);
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

        return table;
	}

	private static PdfPTable getPage1WriteInLine2()
	throws BadElementException, DocumentException, Exception {
    	PdfPTable table = new PdfPTable(2);
    	PdfPTable dtl = null;
    	PdfPCell cell = null;

        int headerwidths[] = {4,96}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1ListSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		dtl = new PdfPTable(12);
        headerwidths = new int[]{11,5,11,5,11,5,11,5,11,5,11,9}; // percentage
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

		dtl = new PdfPTable(12);
        headerwidths = new int[]{11,5,11,5,11,5,11,5,11,5,11,9}; // percentage
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        dtl.getDefaultCell().setCellEvent(new FloatingPdfPCell());
        dtl.addCell(new Phrase("DOB", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Eye Color", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Hair Color", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Weight", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Height", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Gender", smallfont));
        dtl.addCell(SMALL_BLANK);
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

        return table;
	}

	private static PdfPTable getPage1WriteInLine3()
	throws BadElementException, DocumentException, Exception {
    	PdfPTable table = new PdfPTable(2);
    	PdfPTable dtl = null;
    	PdfPCell cell = null;

        int headerwidths[] = {4,96}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.setSpacingBefore(page1ListSpace);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		dtl = new PdfPTable(7);
        headerwidths = new int[]{24,5,21,5,16,5,24}; // percentage
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        cell = new PdfPCell(dtl.getDefaultCell());
        cell.setCellEvent(new FloatingLinedPdfPCell());
        cell.setPhrase(SMALL_BLANK);
        dtl.addCell(cell);
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

		dtl = new PdfPTable(7);
        headerwidths = new int[]{24,5,21,5,16,5,24}; // percentage
        dtl.setWidths(headerwidths);  // percentage
        dtl.setWidthPercentage(100);
        dtl.getDefaultCell().setLeading(fixedLeadingSmall, 0);
        dtl.getDefaultCell().setPadding(0);
        dtl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        dtl.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        dtl.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        dtl.getDefaultCell().setCellEvent(new FloatingPdfPCell());
        dtl.addCell(new Phrase("Social Security Number", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Place of Birth", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Race", smallfont));
        dtl.addCell(SMALL_BLANK);
        dtl.addCell(new Phrase("Country of Citizenship", smallfont));
        table.addCell(SMALL_BLANK);			// Leave first cell blank
        table.addCell(dtl);

        return table;
	}

	private static PdfPTable getPage2HeaderTable()
	throws BadElementException, DocumentException, Exception {
    	PdfPTable table = new PdfPTable(2);
        // format the tables
        int headerwidths[] = {7,93}; // percentage
        table.setWidths(headerwidths);  // percentage
        table.setWidthPercentage(100);
        table.getDefaultCell().setLeading(fixedLeadingMedium, 0);
        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        
        return table;
	}

}
