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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;

public class FailureToProvideInformationLetterFC {

	private static final Logger log = LoggerFactory.getLogger(FailureToProvideInformationLetterFC.class);

    // Define report fonts
    static final Font smallfont = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
    static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
    static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
    static final Font mediumfontI = FontFactory.getFont("Times-Roman", 12, Font.ITALIC);
    
    static final Float fixedLeading = 13.0f;		// The paragraph line spacing

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
		document = new Document(PageSize.LETTER, 50, 50, 125, 0);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");

        document.open();

        LetterheadStamper.stampLetter(writer, request);

        paragraph = getParagraph(10.0f);
        paragraph.add(new Phrase("FPI FC", smallfont));
        paragraph.setIndentationLeft(415);
        document.add(paragraph);
        paragraph.clear();
        paragraph.add(new Phrase("Rev 7/12", smallfont));
        paragraph.setSpacingAfter(50);
        document.add(paragraph);
        
        // Add report date
        paragraph = getParagraph();
        paragraph.add(new Phrase(df.format(screeningLetter.getLetterDate()), mediumfont));
        paragraph.setIndentationLeft(350);
        document.add(paragraph);

        // Add facility name and address information
        paragraph = getParagraph();
        try {
            paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFacility().getLicensingSpecialist().getFirstAndLastName().toUpperCase(), mediumfont));
        } catch (Exception e) {
            paragraph.add(new Phrase("(Licensing Specialist Name)", mediumfontB));
        }
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

        // Add salutation
        paragraph = getParagraph();
        paragraph.add(new Phrase("Dear Licensor:", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        // Add subject information
	    paragraph = getParagraph();
        paragraph.add(new Phrase("RE: Notice of Agency Action", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Add Screening Person's Name/ID
        paragraph = getParagraph(16.0f);
        paragraph.add(new Phrase(screeningLetter.getTrackingRecordScreening().getFirstAndLastName()+" ("+screeningLetter.getTrackingRecordScreening().getPersonIdentifier()+")", mediumfont));
        // Indent this line to line up with 'Notice' in subject line
        paragraph.setIndentationLeft(22);
        document.add(paragraph);
        
        // Add FPI Details Line
        paragraph.clear();
        paragraph.add(new Phrase(screeningLetter.getDetails(), mediumfont));
        document.add(paragraph);
        
        // Start letter detail
        paragraph = getParagraph();
        paragraph.add(new Phrase("In accordance with the Utah Administrative Procedures Act, Utah Code Ann. ", mediumfont));
        paragraph.add(new Chunk("\u00A7", mediumfont));
        paragraph.add(new Phrase(" 63-46b-1 et. Seq, Utah Code Ann. ", mediumfont));
        paragraph.add(new Chunk("\u00A7", mediumfont));
        paragraph.add(new Phrase(" 62A-2-101-116, ", mediumfont));
        paragraph.add(new Phrase("Utah Code Ann. ", mediumfont));
        paragraph.add(new Chunk("\u00A7", mediumfont));
        paragraph.add(new Phrase(" 62A-2-120 and Utah Department of Human Services rules, notice is hereby given of an Agency Action to deny the applicant's ", mediumfont));
        paragraph.add(new Phrase("background screening application.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("The named individual failed to provide this office with required information to complete a background clearance by the Department ", mediumfont));
        paragraph.add(new Phrase("of Human Services, and therefore is not eligible to provide services to children, or programs licensed to DHS OL.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Please provide a copy of this letter to the applicant.", mediumfontB));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("If the person is an applicant for adoption or foster care services, no further action can be taken in the licensing ", mediumfont));
        paragraph.add(new Phrase("process unless the denial is reversed after all appeals are final.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Please direct any questions concerning this action to the Office of Licensing, Background Screening Unit, at ", mediumfont));
        paragraph.add(new Phrase("(801) 538-4242, or fax to me at (801) 538-4669. Thank you.", mediumfont));
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
        paragraph.add(new Phrase("Criminal Information Technician", mediumfont));
        document.add(paragraph);

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
}
