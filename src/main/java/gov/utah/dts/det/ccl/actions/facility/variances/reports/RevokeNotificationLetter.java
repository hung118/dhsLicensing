package gov.utah.dts.det.ccl.actions.facility.variances.reports;

import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.reports.LetterheadStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class RevokeNotificationLetter {

	private static final Logger log = LoggerFactory.getLogger(RevokeNotificationLetter.class);

    // Define report fonts
    static final Font mediumfont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    static final Font mediumfontB = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    static final Font mediumfontI = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC, BaseColor.BLACK);
    
    private static final float fixedLeading = 13.0f;

    public static ByteArrayOutputStream generate(Variance variance, HttpServletRequest request) {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(variance, ba, request);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			ba = null;
		}
        return ba;
	}
	
	private static void writePdf(Variance variance, OutputStream ba, HttpServletRequest request) throws DocumentException, IOException {
		Document document = null;
		Paragraph paragraph = null;
		document = new Document(PageSize.LETTER, 50, 50, 175, 75);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        StringBuilder sb = null;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM d, yyyy");
		String ruleNumber = "R501-"+variance.getRule().getGeneratedRuleNumber();
		String salutation = "";
		if (!variance.getFacility().getPrimaryContacts().isEmpty()) {
			List<String> names = new ArrayList<String>();
			for(FacilityPerson p : variance.getFacility().getPrimaryContacts()) {
				names.add(p.getPerson().getFirstAndLastName());
			}
			String nameString = org.apache.commons.lang.StringUtils.join(names, "/");
			salutation = ("Dear "+nameString+",");
		} else {
			salutation = "Dear "+variance.getFacility().getName()+",";
		}

        document.open();
        
        LetterheadStamper.stampLetter(writer, request);

        // Add date header to document
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        paragraph = getParagraph();
        paragraph.add(new Phrase(df2.format(today), mediumfont));
        paragraph.setIndentationLeft(325);
        document.add(paragraph);
        
        // Add the facility contact information
        // Add facility name
        paragraph = getParagraph();
        paragraph.add(new Phrase(variance.getFacility().getName(), mediumfont));
        paragraph.setSpacingBefore(20);
        document.add(paragraph);
        
        // Add facility location address one
        paragraph = getParagraph();
        paragraph.add(new Phrase(variance.getFacility().getLocationAddress().getAddressOne(), mediumfont));
        document.add(paragraph);
        
	    if (StringUtils.isNotBlank(variance.getFacility().getLocationAddress().getAddressTwo())) {
	    	// Add facility location address two
	        paragraph = getParagraph();
	    	paragraph.add(new Phrase(variance.getFacility().getLocationAddress().getAddressTwo(), mediumfont));
	    	document.add(paragraph);
	    }

	    if (StringUtils.isNotBlank(variance.getFacility().getLocationAddress().getCity()) &&
	    	StringUtils.isNotBlank(variance.getFacility().getLocationAddress().getState()) &&
	    	StringUtils.isNotBlank(variance.getFacility().getLocationAddress().getZipCode())
	    ) {
	    	// Add the facility location city, state zipcode
		    sb = new StringBuilder();
	    	sb.append(variance.getFacility().getLocationAddress().getCity());
	    	sb.append(", "+variance.getFacility().getLocationAddress().getState());
	    	sb.append(" "+variance.getFacility().getLocationAddress().getZipCode());
	    	paragraph = getParagraph();
	    	paragraph.add(new Phrase(sb.toString(), mediumfont));
	    	document.add(paragraph);
	    }
        
        // Add subject information
	    paragraph = getParagraph();
        paragraph.add(new Phrase("Re: Variance of rule "+ruleNumber+" for "+variance.getClientName(), mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Add salutation
        paragraph = getParagraph();
        paragraph.add(new Phrase(salutation, mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Start letter detail
        paragraph = getParagraph();
        sb = new StringBuilder();
        paragraph.add(new Phrase("The Department of Human Services, Office of Licensing, has ",mediumfont));
        paragraph.add(new Phrase("REVOKED",mediumfontB));
        paragraph.add(new Phrase(" your variance of rule "+ruleNumber,mediumfont));
        paragraph.add(new Phrase(" that was in effect for the period beginning "+df.format(variance.getStartDate())+" and ending "+df.format(variance.getEndDate())+".",mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Start of director's response
        paragraph = getParagraph();
        paragraph.add(new Phrase("The action is based on the following:", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        // Add director's response as an italicized indented paragraph block
        paragraph = getParagraph();
        paragraph.add(new Phrase(variance.getRevokeReason(), mediumfontI));
        paragraph.setSpacingBefore(10);
        paragraph.setIndentationLeft(20);
        paragraph.setIndentationRight(20);
        document.add(paragraph);
        
        // Add revocation start 
        paragraph = getParagraph();
    	paragraph.add(new Phrase("The revocation of this variance will go into effect on "+df.format(variance.getRevocationDate()), mediumfont));
    	paragraph.setSpacingBefore(10);
    	document.add(paragraph);
        
        // Add closing
    	paragraph = getParagraph();
        paragraph.add(new Phrase("If you have any concerns or questions, please call the Utah Department of Human Services, Office of Licensing at (801) 538-4242.", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        paragraph = getParagraph();
        paragraph.add(new Phrase("Sincerely,", mediumfont));
        paragraph.setSpacingBefore(10);
        document.add(paragraph);
        
        paragraph = getParagraph();
        paragraph.add(new Phrase(variance.getRevokeModifiedBy().getFirstAndLastName(), mediumfont));
        paragraph.setSpacingBefore(20);
        document.add(paragraph);
        
        document.close();
	}
	
	public static Paragraph getParagraph() {
	    Paragraph paragraph = new Paragraph();
	    paragraph.setLeading(fixedLeading);
	    return paragraph;
	}

}
