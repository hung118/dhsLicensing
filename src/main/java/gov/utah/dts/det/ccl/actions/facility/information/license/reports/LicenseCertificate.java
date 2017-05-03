package gov.utah.dts.det.ccl.actions.facility.information.license.reports;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.License;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressWarnings("unused")
public class LicenseCertificate {

	private static final Logger log = LoggerFactory.getLogger(LicenseCertificate.class);

    // Define report fonts
    private static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
    private static final Font mediumfontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
    private static final Font largefontB = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
    
    private static final Float noLeading = 0.0f;
    private static final Float fixedLeading = 13.0f;		// The column line spacing
    private static final Float numberLeading = 20.0f;
    
    private static final float LEFT_MARGIN = 75;
    private static final float RIGHT_MARGIN = 697;
    private static final float TOP_MARGIN = 529;
    private static final float BOTTOM_MARGIN = 75;
    private static final float MIDDLE = 386;
    private static final String DV_TREATMENT = "DV Treatment";
    private static final String MENTAL_HEALTH_CODES = "DMH:PMH:";
    private static final String SUBSTANCE_ABUSE_CODES = "PSA:DSA:";
    private static final String DOMESTIC_VIOLENCE = "DOMESTIC VIOLENCE";
    private static final String MENTAL_HEALTH = "MENTAL HEALTH";
    private static final String SUBSTANCE_ABUSE = "SUBSTANCE ABUSE";

     private static final float[][] COLUMNS = {
        { LEFT_MARGIN, 303, RIGHT_MARGIN, (303 + (4 * fixedLeading)) }, 
        { LEFT_MARGIN, 222, RIGHT_MARGIN, (226 + (3 * fixedLeading)) }, 
        { LEFT_MARGIN, 157, MIDDLE - 20, 157 + fixedLeading }, 
        { MIDDLE + 20, 157, RIGHT_MARGIN, 157 + fixedLeading },
        { LEFT_MARGIN + 79, 97, LEFT_MARGIN + 241, 97 + numberLeading }
    };
    
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
	
	private static void writePdf(License license, ByteArrayOutputStream ba, HttpServletRequest request) throws DocumentException, BadElementException {
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
        StringBuilder sb;

        // Retrieve the certificate template to use as watermark
        ServletContext context = request.getSession().getServletContext();
        String templatefile = context.getRealPath("/resources")+"/LicenseCertificateTemplate.pdf";
        PdfReader reader = getTemplateReader(templatefile);
        if (reader != null && reader.getNumberOfPages() > 0) {
        	Phrase phrase;
        	
        	// Create new document using the page size of the certificate template document
        	Document document = new Document(reader.getPageSizeWithRotation(1),0,0,0,0);
        	PdfWriter writer = PdfWriter.getInstance(document, ba);
        	document.open();
        	
        	// Get the writer under content byte for placing of watermark
            PdfContentByte under = writer.getDirectContentUnder();
            PdfContentByte over = writer.getDirectContent();
            
            // Retrieve the first page of the template document and wrap as an Image to use as new document watermark
        	PdfImportedPage page = writer.getImportedPage(reader, 1);
            Image img = Image.getInstance(page);
            // Make sure the image has an absolute page position
            if (!img.hasAbsoluteX() || !img.hasAbsoluteY()) {
            	img.setAbsolutePosition(0, 0);
            }
            
            under.addImage(img);
            
            /*
             * THE FOLLOWING TWO FUNCTION CALLS DISPLAY THE PAGE MARGINS AND TEXT COLUMN BORDERS FOR DEBUGGING TEXT PLACEMENT.
             * UNCOMMENT EACH FUNCTION CALL TO HAVE BORDERS DISPLAYED ON THE OUTPUT DOCUMENT.  THIS WILL HELP WHEN YOU NEED
             * TO SEE WHERE TEXT WILL BE PLACED ON THE CERTIFICATE FORM.
             */
            // Show the page margins
            //showPageMarginBorders(over);
            
            // Show the text column borders
            //showColumnBorders(over);

            ColumnText ct = new ColumnText(over);
            ct.setLeading(fixedLeading);

            // Add Facility Name to column
            String text = license.getFacility().getName();
            if (text != null) {
            	phrase = new Phrase(text.toUpperCase(), mediumfontB);
            	phrase.setLeading(noLeading);
            	ct.addText(phrase);
            	ct.addText(Chunk.NEWLINE);
            }
            // Add Facility Site to column
            text = license.getFacility().getSiteName();
            if (text != null) {
            	phrase = new Phrase(text.toUpperCase(), mediumfontB);
            	phrase.setLeading(noLeading);
            	ct.addText(phrase);
            	ct.addText(Chunk.NEWLINE);
            }
            // Add Facility Address to column
            text = license.getFacility().getLocationAddress().getAddressOne();
            if (text != null) {
            	phrase = new Phrase(text.toUpperCase(), mediumfontB);
            	phrase.setLeading(noLeading);
            	ct.addText(phrase);
            	ct.addText(Chunk.NEWLINE);
            }
            text = license.getFacility().getLocationAddress().getCityStateZip();
            if (text != null) {
            	phrase = new Phrase(text.toUpperCase(), mediumfontB);
            	phrase.setLeading(noLeading);
            	ct.addText(phrase);
            	ct.addText(Chunk.NEWLINE);
            }
            // Write column to document
            ct.setAlignment(Element.ALIGN_CENTER);
            ct.setSimpleColumn(COLUMNS[0][0], COLUMNS[0][1], COLUMNS[0][2], COLUMNS[0][3]);
            ct.go();
            
            // Add Certification Service Code to column
            ct = new ColumnText(over);
            ct.setLeading(fixedLeading);
            String service = "";
            if (license.getSpecificServiceCode() != null && StringUtils.isNotBlank(license.getSpecificServiceCode().getValue()) && DV_TREATMENT.equalsIgnoreCase(license.getSpecificServiceCode().getValue())) {
            	service += DOMESTIC_VIOLENCE;
            }
            if (!license.getProgramCodeIds().isEmpty()) {	// redmine 25410
            	mentalHealthLoop:
            	for (PickListValue pkv : license.getProgramCodeIds()) {
                	String program = pkv.getValue();
                	int idx = program.indexOf(" -");
                	if (idx >= 0) {
                    	String code = program.substring(0, idx);
                    	if (MENTAL_HEALTH_CODES.indexOf(code+":") >= 0) {
                    		if (service.length() > 0) {
                    			service += " / ";
                    		}
                    		service += MENTAL_HEALTH;
                    		break mentalHealthLoop;
                    	}
                	}            		
            	}
            	substanceAbuseLoop:
            	for (PickListValue pkv : license.getProgramCodeIds()) {
            		String program = pkv.getValue();
            		int idx = program.indexOf(" -");
            		if (idx >= 0) {
            			String code = program.substring(0, idx);
            			if (SUBSTANCE_ABUSE_CODES.indexOf(code+":") >= 0) {
                    		if (service.length() > 0) {
                    			service += " / ";
                    		}
            				service += SUBSTANCE_ABUSE;
            				break substanceAbuseLoop;
            			}
            		}
            	}            		
            }
           	if (StringUtils.isNotBlank(license.getServiceCodeDesc())) {
        		if (service.length() > 0) {
        			service += " / ";
        		}
           		service += license.getServiceCodeDesc();
           	}
            if (StringUtils.isNotEmpty(service)) {
	            phrase = new Phrase(service.toUpperCase(), mediumfont);
            	phrase.setLeading(noLeading);
	            ct.addText(phrase);
	            ct.addText(Chunk.NEWLINE);
            }
            
            // Add CLIENTS Info to column
            sb = new StringBuilder("FOR ");
            if (license.getAgeGroup() == null || license.getAgeGroup().getValue().equalsIgnoreCase("Adult & Youth")) {
            	// Adult & Youth
            	if (license.getAdultTotalSlots() != null) {
           			sb.append(license.getAdultTotalSlots().toString());
            	}
        		sb.append(" ADULT AND YOUTH CLIENTS");
            } else 
            if (license.getAgeGroup().getValue().equalsIgnoreCase("Adult")) {
        		// Adult
            	if (license.getAdultTotalSlots() != null) {
            		// Are male or female counts specified?
            		sb.append(license.getAdultTotalSlots().toString());
            		sb.append(" ADULT");
            		if (license.getAdultFemaleCount() != null || license.getAdultMaleCount() != null) {
            			// Does either the male or female count equal the total slot count?
            			if ((license.getAdultFemaleCount() != null && license.getAdultFemaleCount().equals(license.getAdultTotalSlots()))) {
            				sb.append(" FEMALE CLIENTS");
            			} else if (license.getAdultMaleCount() != null && license.getAdultMaleCount().equals(license.getAdultTotalSlots())) {
            				sb.append(" MALE CLIENTS");
            			} else {
            				sb.append(" CLIENTS, ");
            				if (license.getAdultMaleCount() != null) {
            					sb.append(license.getAdultMaleCount().toString()+" MALE");
            				}
            				if (license.getAdultFemaleCount() != null) {
            					if (license.getAdultMaleCount() != null) {
            						sb.append(" AND ");
            					}
            					sb.append(license.getAdultFemaleCount().toString()+" FEMALE");
            				}
            				if (license.getFromAge() != null || license.getToAge() != null) {
            					sb.append(",");
            				}
            			}
            		} else {
            			sb.append(" CLIENTS");
            		}
            	} else {
            		sb.append(" ADULT CLIENTS");
            	}
            } else {
            	// Youth
            	if (license.getYouthTotalSlots() != null) {
            		// Are male or female counts specified?
            		sb.append(license.getYouthTotalSlots().toString());
            		sb.append(" YOUTH");
            		if (license.getYouthFemaleCount() != null || license.getYouthMaleCount() != null) {
            			// Does either the male or female count equal the total slot count?
            			if ((license.getYouthFemaleCount() != null && license.getYouthFemaleCount().equals(license.getYouthTotalSlots()))) {
            				sb.append(" FEMALE CLIENTS");
            			} else if (license.getYouthMaleCount() != null && license.getYouthMaleCount().equals(license.getYouthTotalSlots())) {
            				sb.append(" MALE CLIENTS");
            			} else {
            				sb.append(" CLIENTS, ");
            				if (license.getYouthMaleCount() != null) {
            					sb.append(license.getYouthMaleCount().toString()+" MALE");
            				}
            				if (license.getYouthFemaleCount() != null) {
            					if (license.getYouthMaleCount() != null) {
            						sb.append(" AND ");
            					}
            					sb.append(license.getYouthFemaleCount().toString()+" FEMALE");
            				}
            				if (license.getFromAge() != null || license.getToAge() != null) {
            					sb.append(",");
            				}
            			}
            		} else {
            			sb.append(" CLIENTS");
            		}
            	} else {
            		sb.append(" YOUTH CLIENTS");
            	}
            }
            if (license.getFromAge() != null || license.getToAge() != null) {
            	sb.append(" AGES ");
            	if (license.getFromAge() != null) {
            		sb.append(license.getFromAge().toString());
                	if (license.getToAge() != null) {
                		sb.append(" TO "+license.getToAge().toString());
                	} else {
                		sb.append(" AND OLDER");
                	}
            	} else {
            		sb.append("TO "+license.getToAge().toString());
            	}
            }
            phrase = new Phrase(sb.toString(), mediumfont);
           	phrase.setLeading(noLeading);
            ct.addText(phrase);
            ct.addText(Chunk.NEWLINE);
            
            // Add Certificate Comments
            if (StringUtils.isNotBlank(license.getCertificateComment())) {
                phrase = new Phrase(license.getCertificateComment().toUpperCase(), mediumfont);
                phrase.setLeading(noLeading);
                ct.addText(phrase);
            }
            
            // Write column to document
            ct.setAlignment(Element.ALIGN_CENTER);
            ct.setSimpleColumn(COLUMNS[1][0], COLUMNS[1][1], COLUMNS[1][2], COLUMNS[1][3]);
            ct.go();
            
            // Add Certificate Start Date
            if (license.getStartDate() != null) {
            	phrase = new Phrase(df.format(license.getStartDate()), mediumfont);
            	phrase.setLeading(noLeading);
                ct = new ColumnText(over);
            	ct.setSimpleColumn(phrase, COLUMNS[2][0], COLUMNS[2][1], COLUMNS[2][2], COLUMNS[2][3], fixedLeading, Element.ALIGN_RIGHT);
            	ct.go();
            }

            // Add Certificate End Date
            if (license.getEndDate() != null) {
            	phrase = new Phrase(df.format(license.getEndDate()), mediumfont);
            	phrase.setLeading(noLeading);
                ct = new ColumnText(over);
            	ct.setSimpleColumn(phrase, COLUMNS[3][0], COLUMNS[3][1], COLUMNS[3][2], COLUMNS[3][3], fixedLeading, Element.ALIGN_LEFT);
            	ct.go();
            }

            // Add License Number
            if (license.getLicenseNumber() != null) {
            	phrase = new Phrase(license.getLicenseNumber().toString(), largefontB);
            	phrase.setLeading(noLeading);
                ct = new ColumnText(over);
            	ct.setSimpleColumn(phrase, COLUMNS[4][0], COLUMNS[4][1], COLUMNS[4][2], COLUMNS[4][3], numberLeading, Element.ALIGN_CENTER);
            	ct.go();
            }
            document.close();
        }
	}
	
    private static PdfReader getTemplateReader(String templatefile) {
    	PdfReader reader = null;
    	try {
    		reader = new PdfReader(templatefile);
    	} catch(Exception e) {
    		
    	}
    	return reader;
    }

    // Diagnostic function used to display the page margin borders (for form text placement debugging purposes).
	private static void showPageMarginBorders(PdfContentByte canvas) {
        canvas.moveTo(LEFT_MARGIN, TOP_MARGIN);
        canvas.lineTo(LEFT_MARGIN, BOTTOM_MARGIN);
        canvas.lineTo(RIGHT_MARGIN, BOTTOM_MARGIN);
        canvas.lineTo(RIGHT_MARGIN, TOP_MARGIN);
        canvas.lineTo(LEFT_MARGIN, TOP_MARGIN);
        canvas.stroke();
    }
    
    // Diagnostic function used to display the text column borders (for form text placement debugging purposes).
	private static void showColumnBorders(PdfContentByte canvas) {
    	for (int i=0;i < COLUMNS.length; i++) {
    		canvas.moveTo(COLUMNS[i][0], COLUMNS[i][1]);
    		canvas.lineTo(COLUMNS[i][2], COLUMNS[i][1]);
    		canvas.lineTo(COLUMNS[i][2], COLUMNS[i][3]);
    		canvas.lineTo(COLUMNS[i][0], COLUMNS[i][3]);
    		canvas.lineTo(COLUMNS[i][0], COLUMNS[i][1]);
    		canvas.stroke();
    	}
    }
}
