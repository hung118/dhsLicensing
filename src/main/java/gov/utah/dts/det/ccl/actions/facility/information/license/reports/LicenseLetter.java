package gov.utah.dts.det.ccl.actions.facility.information.license.reports;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.reports.LetterheadStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

public class LicenseLetter {

	private static final Logger log = LoggerFactory.getLogger(LicenseLetter.class);

    private static final String DV_TREATMENT = "DV Treatment";
    private static final String MENTAL_HEALTH_CODES = "DMH:PMH:";
    private static final String SUBSTANCE_ABUSE_CODES = "PSA:DSA:";
    private static final String DOMESTIC_VIOLENCE = "DOMESTIC VIOLENCE";
    private static final String MENTAL_HEALTH = "MENTAL HEALTH";
    private static final String SUBSTANCE_ABUSE = "SUBSTANCE ABUSE";
    
    private static final List<String> CC_PROGRAM_CODES = Arrays.asList("DJJS","DSPD","DCFS");
    
    private static final float paragraphSpacing = 13;
    private static final float rightindent = 300;
    private static final float fixedLeading = 13;		// The paragraph line spacing

    // Define report fonts
    static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
    static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
    static final Font mediumfontI = FontFactory.getFont("Times-Roman", 12, Font.ITALIC);
    

    public static ByteArrayOutputStream generate(License license, HttpServletRequest request) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(license, ba, request);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
        return ba;
	}
	
	private static void writePdf(License license, ByteArrayOutputStream ba, HttpServletRequest request) throws DocumentException, BadElementException, IOException {
		Document document = null;
		Paragraph paragraph = null;
		document = new Document(PageSize.LETTER, 50, 50, 190, 75);
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        Date today = new Date();
        StringBuilder sb;

        document.open();
        
        LetterheadStamper.stampLetter(writer, request);
        
        // Add report date
        paragraph = getParagraph();
        paragraph.add(new Phrase(df.format(today), mediumfont));
        paragraph.setIndentationLeft(rightindent);
        document.add(paragraph);

        // Add facility name and address information
        paragraph = getParagraph();
        paragraph.add(new Phrase(license.getFacility().getName().toUpperCase(), mediumfont));
        paragraph.setSpacingBefore(20);
        document.add(paragraph);

        if (StringUtils.isNotBlank(license.getFacility().getSiteName())) {
        	paragraph = getParagraph();
            paragraph.add(new Phrase(license.getFacility().getSiteName().toUpperCase(), mediumfont));
        	document.add(paragraph);
        }

        paragraph = getParagraph();
        try {
            paragraph.add(new Phrase(license.getFacility().getMailingAddress().getAddressOne().toUpperCase() , mediumfont));
        } catch (Exception e) {
            paragraph.add(new Phrase("Mailing Address" , mediumfont));
        }
        document.add(paragraph);

        try {
    	    if (StringUtils.isNotBlank(license.getFacility().getMailingAddress().getAddressTwo())) {
    	    	// Add facility location address two
    	    	paragraph = getParagraph();
    	    	paragraph.add(new Phrase(license.getFacility().getMailingAddress().getAddressTwo().toUpperCase(), mediumfont));
    	        document.add(paragraph);
    	    }
        } catch (Exception e) {
        	// Skip if no address found
        }

        paragraph = getParagraph();
        try {
        	paragraph.add(new Phrase(license.getFacility().getMailingAddress().getCityStateZip().toUpperCase(), mediumfont));
        } catch (Exception e) {
    	    paragraph.add(new Phrase("City, State Zipcode", mediumfont));
        }
        document.add(paragraph);

        // Add subject information
	    paragraph = getParagraph();
	    sb = new StringBuilder();
	    sb.append("SUBJECT: LICENSE APPROVAL");
	    if (license.getSubtype() != null && StringUtils.isNotBlank(license.getSubtype().getValue())) {
	    	sb.append(" - "+license.getSubtype().getValue().toUpperCase());
	    }
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);

        // Add salutation
        paragraph = getParagraph();
        paragraph.add(new Phrase("Dear Director:", mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);
        
        // Start letter detail
        paragraph = getParagraph();
        sb = new StringBuilder();
        sb.append("Your application to provide ");
        
        // Get the Service Code Definition
        String service = "";
        if (license.getSpecificServiceCode() != null && StringUtils.isNotBlank(license.getSpecificServiceCode().getValue()) && DV_TREATMENT.equalsIgnoreCase(license.getSpecificServiceCode().getValue())) {
        	service = DOMESTIC_VIOLENCE;
        }
        if (!license.getProgramCodeIds().isEmpty()) {	// redmine 25410
        	mentalHealthLoop:
        	for (PickListValue pkv : license.getProgramCodeIds()) {
            	String program = pkv.getValue();
            	int idx = program.indexOf(" ");
            	if (idx >= 0) {
                	String code = program.substring(0, idx);
                	if (MENTAL_HEALTH_CODES.indexOf(code+":") >= 0) {
                		if (service.length() > 0) {
                			service += "/";
                		}
                		service += MENTAL_HEALTH;
                		break mentalHealthLoop;
                	}
            	}
        	}
        	substanceAbuseLoop:
        	for (PickListValue pkv : license.getProgramCodeIds()) {
        		String program = pkv.getValue();
        		int idx = program.indexOf(" ");
        		if (idx >= 0) {
        			String code = program.substring(0, idx);
        			if (SUBSTANCE_ABUSE_CODES.indexOf(code+":") >= 0) {
                		if (service.length() > 0) {
                			service += "/";
                		}
        				service += SUBSTANCE_ABUSE;
        				break substanceAbuseLoop;
        			}
        		}
        	}
        }
       	if (license.getServiceCode() != null && StringUtils.isNotBlank(license.getServiceCode().getValue())) {
       		if (service.length() > 0) {
       			service += "/";
       		}
       		String code = license.getServiceCode().getValue();
       		int idx = code.indexOf("-");
       		if (idx > -1) {
       			idx++;
       			code = code.substring(idx).trim();
       		}
       		service += code;
       	}
        sb.append(service);
        sb.append(" for ");
        if (license.getAgeGroup() == null || license.getAgeGroup().getValue().equalsIgnoreCase("Adult & Youth")) {
        	// Adult & Youth
        	if (license.getAdultTotalSlots() != null) {
       			sb.append(license.getAdultTotalSlots().toString());
        	}
    		sb.append(" adult and youth clients");
        } else 
        if (license.getAgeGroup().getValue().equalsIgnoreCase("Adult")) {
    		// Adult
        	if (license.getAdultTotalSlots() != null) {
        		// Are male or female counts specified?
        		sb.append(license.getAdultTotalSlots().toString());
        		sb.append(" adult");
        		if (license.getAdultFemaleCount() != null || license.getAdultMaleCount() != null) {
        			// Does either the male or female count equal the total slot count?
        			if ((license.getAdultFemaleCount() != null && license.getAdultFemaleCount().equals(license.getAdultTotalSlots()))) {
        				sb.append(" female clients");
        			} else if (license.getAdultMaleCount() != null && license.getAdultMaleCount().equals(license.getAdultTotalSlots())) {
        				sb.append(" male clients");
        			} else {
        				sb.append(" clients, ");
        				if (license.getAdultMaleCount() != null) {
        					sb.append(license.getAdultMaleCount().toString()+" male");
        				}
        				if (license.getAdultFemaleCount() != null) {
        					if (license.getAdultMaleCount() != null) {
        						sb.append(" and ");
        					}
        					sb.append(license.getAdultFemaleCount().toString()+" female");
        				}
        				if (license.getFromAge() != null || license.getToAge() != null) {
        					sb.append(",");
        				}
        			}
        		} else {
        			sb.append(" clients");
        		}
        	} else {
        		sb.append(" adult clients");
        	}
        } else {
        	// Youth
        	if (license.getYouthTotalSlots() != null) {
        		// Are male or female counts specified?
        		sb.append(license.getYouthTotalSlots().toString());
        		sb.append(" youth");
        		if (license.getYouthFemaleCount() != null || license.getYouthMaleCount() != null) {
        			// Does either the male or female count equal the total slot count?
        			if ((license.getYouthFemaleCount() != null && license.getYouthFemaleCount().equals(license.getYouthTotalSlots()))) {
        				sb.append(" female clients");
        			} else if (license.getYouthMaleCount() != null && license.getYouthMaleCount().equals(license.getYouthTotalSlots())) {
        				sb.append(" male clients");
        			} else {
        				sb.append(" clients, ");
        				if (license.getYouthMaleCount() != null) {
        					sb.append(license.getYouthMaleCount().toString()+" male");
        				}
        				if (license.getYouthFemaleCount() != null) {
        					if (license.getYouthMaleCount() != null) {
        						sb.append(" and ");
        					}
        					sb.append(license.getYouthFemaleCount().toString()+" female");
        				}
        				if (license.getFromAge() != null || license.getToAge() != null) {
        					sb.append(",");
        				}
        			}
        		} else {
        			sb.append(" clients");
        		}
        	} else {
        		sb.append(" youth clients");
        	}
        }
        if (license.getFromAge() != null || license.getToAge() != null) {
        	sb.append(" ages ");
        	if (license.getFromAge() != null) {
        		sb.append(license.getFromAge().toString());
            	if (license.getToAge() != null) {
            		sb.append(" to "+license.getToAge().toString());
            	} else {
            		sb.append(" and older");
            	}
        	} else {
        		sb.append("to "+license.getToAge().toString());
        	}
        }
        if (StringUtils.isNotBlank(license.getCertificateComment())) {
            sb.append(" ");
            sb.append(license.getCertificateComment());
        }
        sb.append(" has been approved.");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);

        paragraph = getParagraph();
        sb = new StringBuilder();
        sb.append("The license is issued for the period from ");
        sb.append(df2.format(license.getStartDate()));
        sb.append(" to ");
        sb.append(df2.format(license.getEndDate()));
        sb.append(". The enclosed license is subject to revocation for cause; or if there should be any change in the management, ownership, or address of the facility, ");
        sb.append("the license is automatically void and should be returned to our office. The enclosed license must be posted in the facility.");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);

        paragraph = getParagraph();
        sb = new StringBuilder();
        sb.append("The approval of your license is based upon reports submitted to this office by our staff and by the ");
        sb.append("collaberative agencies which show that reasonable standards of care are maintained and the services ");
        sb.append("provided meet the requirements established by our State Standards.");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);

        paragraph = getParagraph();
        sb = new StringBuilder();
        sb.append("During the period for which the license is granted, representatives from this office and other collaborative ");
        sb.append("agencies may make periodic supervisory visits and will be available for consultation. Please feel free to ");
        sb.append("request assistance at any time.");
        paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);

        paragraph = getParagraph();
        paragraph.add(new Phrase(license.getFacility().getLicensingSpecialist().getFirstAndLastName(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
        document.add(paragraph);
        
        paragraph = getParagraph();
        paragraph.add(new Phrase("Licensing Specialist", mediumfont));
        document.add(paragraph);

       	paragraph = getParagraph();
       	sb = new StringBuilder();
       	sb.append("Enclosure: License #");
       	if (license.getLicenseNumber() != null) {
       		sb.append(license.getLicenseNumber().toString());
       	}
       	paragraph.add(new Phrase(sb.toString(), mediumfont));
        paragraph.setSpacingBefore(paragraphSpacing);
       	document.add(paragraph);

       	// Check to see if Program Code starts with 'D'
       	if (!license.getProgramCodeIds().isEmpty()) {	// redmine 25410 multiple program codes
       		sb = new StringBuilder();
       		for (PickListValue pkv : license.getProgramCodeIds()) {
       			if (pkv.getValue() != null && pkv.getValue().length() >= 4) {
       	        	String code = pkv.getValue().substring(0, 4).toUpperCase();
       				if (CC_PROGRAM_CODES.contains(code)) {
       					if (sb.length() > 0) {
       						sb.append(", ");
       					}
       					sb.append(code);
       				}
       			}
       		}
       		if (sb.length() > 0) {
               	paragraph = getParagraph();
            	paragraph.add(new Phrase("cc: "+sb.toString(), mediumfont));
               	document.add(paragraph);
       		}
       	}
       	
       	document.close();
	}
	
    // This method is used to return a new paragraph with a set fixed leading line space for adjusting paragraph line height
    private static Paragraph getParagraph() {
    	return new Paragraph(fixedLeading);
    }
}
