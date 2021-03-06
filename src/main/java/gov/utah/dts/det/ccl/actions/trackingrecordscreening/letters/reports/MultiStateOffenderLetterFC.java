package gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.reports.LetterheadStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;

public class MultiStateOffenderLetterFC {

	private static final Logger log = LoggerFactory.getLogger(MultiStateOffenderLetterFC.class);

    // Define report fonts
    static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
    static final Font smallfont = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
    static final Font mediumfontI = FontFactory.getFont("Times-Roman", 12, Font.ITALIC);

    static final Float fixedLeading = 14.0f;		// The paragraph line spacing

    public static ByteArrayOutputStream generate(TrackingRecordScreeningLetter screeningLetter, HttpServletRequest request) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(screeningLetter, ba, request);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
        return ba;
	}
	
	private static void writePdf(TrackingRecordScreeningLetter screeningLetter, OutputStream ba, HttpServletRequest request) throws DocumentException, BadElementException, IOException {
		Document document = null;
		Paragraph paragraph = null;
		ListItem item = null;
		document = new Document(PageSize.LETTER, 50, 50, 125, 0);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        StringBuilder sb = null;
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");

        document.open();

        LetterheadStamper.stampLetter(writer, request);

        paragraph = getParagraph(10.0f);
        paragraph.add(new Phrase("MSO FC", smallfont));
        paragraph.setIndentationLeft(415);
        document.add(paragraph);
        paragraph.clear();
        paragraph.add(new Phrase("Rev 3/12", smallfont));
        paragraph.setSpacingAfter(50);
        document.add(paragraph);
        
        // Add report date
        paragraph = getParagraph();
        paragraph.add(new Phrase(df.format(screeningLetter.getLetterDate()), mediumfont));
        paragraph.setIndentationLeft(350);
        document.add(paragraph);

        // Add applicant name and address information
        paragraph = getParagraph();
        paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFirstAndLastName().toUpperCase(), mediumfont));
        paragraph.setSpacingBefore(5);
        document.add(paragraph);
        if (StringUtils.isNotBlank(screeningLetter.getAddress().getAddressOne())) {
            paragraph = getParagraph();
            paragraph.add(new Phrase(screeningLetter.getAddress().getAddressOne().toUpperCase(), mediumfont));
            document.add(paragraph);
        }
	    if (StringUtils.isNotBlank(screeningLetter.getAddress().getAddressTwo())) {
	    	// Add facility location address two
	        paragraph = getParagraph();
	        paragraph.add(new Phrase(screeningLetter.getAddress().getAddressTwo().toUpperCase(), mediumfont));
	        document.add(paragraph);
	    }
	    if (StringUtils.isNotBlank(screeningLetter.getAddress().getCityStateZip())) {
	    	paragraph = getParagraph();
		    paragraph.add(new Phrase(screeningLetter.getAddress().getCityStateZip().toUpperCase(), mediumfont));
		    document.add(paragraph);
	    }
        
        // Add subject information
        paragraph = getParagraph();
        paragraph.add(new Phrase("RE: Notice of additional Criminal Background Screening information needed:", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Add Screening Person's Name/ID
        paragraph = getParagraph(16.0f);
        paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFirstAndLastName()+" ("+screeningLetter.getTrackingRecordScreening().getPersonIdentifier()+")", mediumfont));
        // Indent this line to line up with 'Notice' in subject line
        paragraph.setIndentationLeft(22);
        document.add(paragraph);
        
        // Add salutation
        paragraph = getParagraph();
        paragraph.add(new Phrase("Dear " + screeningLetter.getTrackingRecordScreening().getFirstAndLastName() + ":", mediumfont));
        paragraph.setSpacingBefore(18);
        document.add(paragraph);
        
        // Start letter detail
        sb = new StringBuilder();
        sb.append("As a follow-up to the request for background screening by the Department of Human Services, Office of Licensing, ");
        sb.append("this is to notify you that additional criminal background screening information is needed.");
        paragraph = getParagraph();
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Enclosed are two fingerprint cards for you to provide complete, accurate and legible identifying information. ", mediumfont));
        paragraph.add(new Phrase("Return the completed fingerprint cards with a $36.50 fee in the form of a ", mediumfont));
        paragraph.add(new Phrase("cashier's check or money order ", mediumfontI));
        paragraph.add(new Phrase("(no personal checks) payable to the ", mediumfont));
        paragraph.add(new Phrase("Department of Human Resources, ", mediumfontI));
        paragraph.add(new Phrase("to the Office of Licensing within 15 calendar days of your receipt of this notice.", mediumfont));
        paragraph.setSpacingBefore(10);
        paragraph.setSpacingAfter(10);
        document.add(paragraph);


        // Add the fingerprint card procedures as an indented bullet list
        List procedure = new List(false, 10);
        procedure.setIndentationLeft(10);
        item = getListItem();
        item.add(new Phrase("Prints should be taken by a local law enforcement office, or an agency approved by law enforcement.", mediumfont));
        procedure.add(item);
        item = getListItem();
        item.add(new Phrase("The FBI will reject a card with any highlighting.", mediumfont));
        procedure.add(item);
        item = getListItem();
        item.add(new Phrase("Use only these cards showing identification from the Office of Licensing. We will be glad to give you replacement cards if requested.", mediumfont));
        procedure.add(item);
        item = getListItem();
        procedure.add(new Phrase("If mailing the cards back to us, return by regular mail (please do not fold the cards).", mediumfont));
        procedure.add(item);
        item = getListItem();
        item.add(new Phrase("Fingerprint cards must be completely filled out or they will be returned for completion.", mediumfont));
        procedure.add(item);
        document.add(procedure);
        
        sb = new StringBuilder();
        sb.append("Failure to return the completed fingerprint cards and fee within 15 calendar days will result in your background screening ");
        sb.append("application being denied, and you will not be eligible to be associated with the licensed program in any capacity ");
        sb.append("or will not be eligible to proceed with foster care or adoption until all clearance procedures are completed.");
        paragraph = getParagraph();
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        sb = new StringBuilder();
        sb.append("Please allow up to 12 weeks for the completion of the clearance process. For assistance or inquiries, you can contact the Office of Licensing ");
        sb.append("at (801) 538-4242.");
        paragraph = getParagraph();
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Sincerely,", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        paragraph = getParagraph();
        paragraph.add(new Phrase(screeningLetter.getCreatedBy().getFirstAndLastName(), mediumfont));
        paragraph.setSpacingBefore(25);
        document.add(paragraph);
        
        paragraph = getParagraph();
        paragraph.add(new Phrase("Background Screening Unit", mediumfont));
        document.add(paragraph);
        
        if (screeningLetter.getTrackingRecordScreening() != null && 
        	screeningLetter.getTrackingRecordScreening().getFacility() != null && 
        	screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist() != null &&
        	StringUtils.isNotBlank(screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist().getIntials())) 
        {
        	paragraph = getParagraph();
        	paragraph.add(new Phrase("CC: " + screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist().getIntials(), mediumfont));
        	paragraph.setSpacingBefore(10);
        	document.add(paragraph);
        }

        document.close();
	}

    // This method is used to return a new paragraph with a set fixed leading line space for adjusting paragraph line height
    private static Paragraph getParagraph() {
    	return new Paragraph(fixedLeading);
    }
    
    // This method is used to return a new paragraph with a set fixed leading that is specified for adjusting paragraph line height
    private static Paragraph getParagraph(Float leading) {
    	return new Paragraph(leading);
    }

    // This method is used to return a new ListItem with a set fixed leading line space for adjusting bullet list line height
    private static ListItem getListItem() {
    	return new ListItem(fixedLeading);
    }
}
