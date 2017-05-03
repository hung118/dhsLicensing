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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;

public class FingerprintCardFeeOnlyLetterFC {

	private static final Logger log = LoggerFactory.getLogger(FingerprintCardFeeOnlyLetterFC.class);

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
		document = new Document(PageSize.LETTER, 50, 50, 125, 0);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");

        document.open();

        LetterheadStamper.stampLetter(writer, request);

        LetterheadStamper.stampLetter(writer, request);

        paragraph = getParagraph(10.0f);
        paragraph.add(new Phrase("FPF FC", smallfont));
        paragraph.setIndentationLeft(415);
        document.add(paragraph);
        paragraph.clear();
        paragraph.add(new Phrase("Rev 2/12", smallfont));
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
        paragraph = new Paragraph("RE: Notice of Criminal History Verification:", mediumfont);
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
        paragraph.add(new Phrase("Dear Applicant:", mediumfont));
        paragraph.setSpacingBefore(18);
        document.add(paragraph);
        
        // Start letter detail
        paragraph = getParagraph();
        paragraph.add(new Phrase("As a follow-up to the request for background screening by the Department of Human Services, Office of Licensing, ", mediumfont));
        paragraph.add(new Phrase("this is to notify you that additional criminal background screening information is needed.  Initial procedures ", mediumfont));
        paragraph.add(new Phrase("indicate the possibility of a criminal record.  Therefore, further review is required (UCA 62A-2-120, and R501-14).", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("You submitted fingerprint cards previously. We have one fingerprint card in your file that we can ", mediumfont));
        paragraph.add(new Phrase("use for the verification. To proceed with the verification, we request a $20 fee in the form of a ", mediumfont));
        paragraph.add(new Phrase("cashier's check, or money order ", mediumfontI));
        paragraph.add(new Phrase("(no personal checks) payable to the ", mediumfont));
        paragraph.add(new Phrase("Department of Human Services. ", mediumfontI));
        paragraph.add(new Phrase("Submit the fee to the Office of Licensing within 5 working days of your receipt of this notice. Please be sure ", mediumfont));
        paragraph.add(new Phrase("the payment references your name and last four digits of your social security number.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Failure to return the fee within 5 working days will result in the background screening application being denied, and you ", mediumfont));
        paragraph.add(new Phrase("will not be eligible to be associated with the licensed program in any capacity or will not be eligible to proceed ", mediumfont));
        paragraph.add(new Phrase("with foster care or adoption until all clearance procedures are completed.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase("Please allow up to 12 weeks for the completion of the clearance process. For assistance or inquiries, please contact the Office of Licensing ", mediumfont));
        paragraph.add(new Phrase("at (801) 538-4242.", mediumfont));
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
}
