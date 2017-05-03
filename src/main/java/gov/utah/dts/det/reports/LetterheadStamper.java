package gov.utah.dts.det.reports;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class LetterheadStamper {

    // Define report fonts
    private static final Font largefontB = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
    private static final Font mediumfont = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
    private static final Font smallfont = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
    private static final Font smallfontI = FontFactory.getFont("Times-Roman", 9, Font.ITALIC);

    private static final Float noLeading = 0.0f;
    private static final Float fixedLeading = 11.0f;		// The column line spacing
    private static final Float fixedLeadingMedium = 9.0f;
    private static final Float fixedLeadingSmall = 7.0f;

    private static final String PICKLIST_GOVERNOR = "Governor";
    private static final String PICKLIST_LT_GOVERNOR = "Leutenant Governor";
    private static final String PICKLIST_EXEC_DIRECTOR = "Executive Director";
    private static final String PICKLIST_DIRECTOR = "Director";
    private static final String PICKLIST_FOOTER = "Footer";
    
    private static String GOVERNOR = "";
    private static String LT_GOVERNOR = "";
    private static String EXEC_DIRECTOR = "";
    private static String DIRECTOR = "";
    private static String FOOTER = "";

    public static void stampLetter(PdfWriter writer, HttpServletRequest request) throws IOException, DocumentException, BadElementException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        Document header = new Document(PageSize.LETTER);
		generateHeader(header, ba, request);
		stampHeader(writer, ba);
	}

	private static void generateHeader(Document header, ByteArrayOutputStream ba, HttpServletRequest request) throws IOException, DocumentException, BadElementException {
        Phrase phrase;
        float topMargin = header.getPageSize().getHeight()-25;
        float bottomMargin = topMargin - 145;
        float leftMargin1 = 25;
        float rightMargin1 = 130;
        float leftMargin2 = 153;
        float rightMargin2 = header.getPageSize().getWidth()-leftMargin1;
        float logoLeft = leftMargin1 + ((rightMargin1 - leftMargin1 - 55) / 2);
        float logoBottom = topMargin - 60;
        float[][] COLUMNS = {
           { leftMargin1, topMargin-(75+fixedLeading), rightMargin1, topMargin-75 },
           { leftMargin1, bottomMargin, rightMargin1, bottomMargin+(5*fixedLeadingMedium) },
           { leftMargin2, topMargin-(10+fixedLeading), rightMargin2, topMargin-10 },
           { leftMargin2, topMargin-(10+(2*fixedLeading)+(2*fixedLeadingMedium)), rightMargin2, topMargin-(10+(2*fixedLeading)) },
           { leftMargin2, topMargin-(10+(4*fixedLeading)+(2*fixedLeadingMedium)), rightMargin2, topMargin-(10+(3*fixedLeading)+(2*fixedLeadingMedium)) },
           { leftMargin2, topMargin-(10+(5*fixedLeading)+(4*fixedLeadingMedium)), rightMargin2, topMargin-(10+(5*fixedLeading)+(2*fixedLeadingMedium)) },
           { leftMargin1, 37-fixedLeadingSmall, rightMargin2, 37 }
        };

        // Retrieve the information values to be placed in the header
        retrieveHeaderPicklistValues(request);
        
        PdfWriter writer = PdfWriter.getInstance(header, ba);
        header.open();

        // Get the writer over content byte for adding column information
        PdfContentByte over = writer.getDirectContent();

        // Add vertical line separator
        over.setLineWidth(3.0f);
        BaseColor lineColor = new BaseColor(37,42,103);
        over.setColorStroke(lineColor);
        over.moveTo(140, topMargin);
        over.lineTo(140, bottomMargin);
        over.stroke();

        // Retrieve the state seal image
        Image seal = getStateSeal(request);
        if (seal != null) {
            // Add State Seal to header
            seal.scaleAbsolute(55, 55);
            seal.setAbsolutePosition(logoLeft, logoBottom);
            header.add(seal);
        }

        /*
         * THE FOLLOWING SIX FUNCTION CALLS DISPLAY THE TEXT COLUMN BORDERS FOR DEBUGGING TEXT PLACEMENT.
         * UNCOMMENT EACH FUNCTION CALL TO HAVE BORDERS DISPLAYED ON THE OUTPUT DOCUMENT.  THIS WILL HELP WHEN YOU NEED
         * TO SEE WHERE TEXT WILL BE PLACED ON THE FORM.
         */
        // Show the text column borders
        //showColumnBorders(writer, COLUMNS[0][0], COLUMNS[0][1], COLUMNS[0][2], COLUMNS[0][3]);
        //showColumnBorders(writer, COLUMNS[1][0], COLUMNS[1][1], COLUMNS[1][2], COLUMNS[1][3]);
        //showColumnBorders(writer, COLUMNS[2][0], COLUMNS[2][1], COLUMNS[2][2], COLUMNS[2][3]);
        //showColumnBorders(writer, COLUMNS[3][0], COLUMNS[3][1], COLUMNS[3][2], COLUMNS[3][3]);
        //showColumnBorders(writer, COLUMNS[4][0], COLUMNS[4][1], COLUMNS[4][2], COLUMNS[4][3]);
        //showColumnBorders(writer, COLUMNS[5][0], COLUMNS[5][1], COLUMNS[5][2], COLUMNS[5][3]);
        //showColumnBorders(writer, COLUMNS[6][0], COLUMNS[6][1], COLUMNS[6][2], COLUMNS[6][3]);

        // Populate left column information
        ColumnText ct = new ColumnText(over);
        ct.setLeading(fixedLeading);

        // Add State of Utah line to column
        phrase = new Phrase("State of Utah", largefontB);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        
        // Write column to document
        ct.setAlignment(Element.ALIGN_CENTER);
        ct.setSimpleColumn(COLUMNS[0][0], COLUMNS[0][1], COLUMNS[0][2], COLUMNS[0][3]);
        ct.go();
        
        ct = new ColumnText(over);
        ct.setLeading(fixedLeadingMedium);
        
        // Add Governor information to column
        phrase = new Phrase(GOVERNOR, mediumfont);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        ct.addText(Chunk.NEWLINE);
        phrase = new Phrase(PICKLIST_GOVERNOR, smallfontI);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        ct.addText(Chunk.NEWLINE);
        ct.addText(Chunk.NEWLINE);

        // Add Leutenant Governor information to column
        phrase = new Phrase(LT_GOVERNOR, mediumfont);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        ct.addText(Chunk.NEWLINE);
        phrase = new Phrase(PICKLIST_LT_GOVERNOR, smallfontI);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        
        // Write column to document
        ct.setAlignment(Element.ALIGN_CENTER);
        ct.setSimpleColumn(COLUMNS[1][0], COLUMNS[1][1], COLUMNS[1][2], COLUMNS[1][3]);
        ct.go();

        // Populate right column information
        ct = new ColumnText(over);
        ct.setLeading(fixedLeading);
        
        // Add Department of Human Services line to column
        phrase = new Phrase("Department of Human Services", largefontB);
        phrase.setLeading(noLeading);
        ct.addText(phrase);

        // Write column to document
        ct.setAlignment(Element.ALIGN_LEFT);
        ct.setSimpleColumn(COLUMNS[2][0], COLUMNS[2][1], COLUMNS[2][2], COLUMNS[2][3]);
        ct.go();
        
        ct = new ColumnText(over);
        ct.setLeading(fixedLeadingMedium);
        // Add Executive Director information to column
        phrase = new Phrase(EXEC_DIRECTOR, mediumfont);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        ct.addText(Chunk.NEWLINE);
        phrase = new Phrase(PICKLIST_EXEC_DIRECTOR, smallfontI);
        phrase.setLeading(noLeading);
        ct.addText(phrase);

        // Write column to document
        ct.setAlignment(Element.ALIGN_LEFT);
        ct.setSimpleColumn(COLUMNS[3][0], COLUMNS[3][1], COLUMNS[3][2], COLUMNS[3][3]);
        ct.go();

        ct = new ColumnText(over);
        ct.setLeading(fixedLeading);
        // Add Office of Licensing line to column
        phrase = new Phrase("Office of Licensing", largefontB);
        phrase.setLeading(fixedLeading);
        ct.addText(phrase);

        // Write column to document
        ct.setAlignment(Element.ALIGN_LEFT);
        ct.setSimpleColumn(COLUMNS[4][0], COLUMNS[4][1], COLUMNS[4][2], COLUMNS[4][3]);
        ct.go();

        ct = new ColumnText(over);
        ct.setLeading(fixedLeadingMedium);
        // Add Director information to column
        phrase = new Phrase(DIRECTOR, mediumfont);
        phrase.setLeading(noLeading);
        ct.addText(phrase);
        ct.addText(Chunk.NEWLINE);
        phrase = new Phrase(PICKLIST_EXEC_DIRECTOR, smallfontI);
        phrase.setLeading(noLeading);
        ct.addText(phrase);

        // Write column to document
        ct.setAlignment(Element.ALIGN_LEFT);
        ct.setSimpleColumn(COLUMNS[5][0], COLUMNS[5][1], COLUMNS[5][2], COLUMNS[5][3]);
        ct.go();

        if (StringUtils.isNotBlank(FOOTER)) {
            ct = new ColumnText(over);
            ct.setLeading(fixedLeadingSmall);
            // Add Footer information to column
            phrase = new Phrase(FOOTER, smallfont);
            phrase.setLeading(noLeading);
            ct.addText(phrase);

            // Write column to document
            ct.setAlignment(Element.ALIGN_CENTER);
            ct.setSimpleColumn(COLUMNS[6][0], COLUMNS[6][1], COLUMNS[6][2], COLUMNS[6][3]);
            ct.go();
        }

        header.close();
	}
	
	private static Image getStateSeal(HttpServletRequest request) {
	    Image img;
	    try {
	        ServletContext context = request.getSession().getServletContext();
	        String filename = context.getRealPath("/images")+"/State-Seal-High-Resolution-Color-for-Licensing.jpg";
	        img = Image.getInstance(filename);
	    } catch (IOException iex) {
	        img = null;
	    } catch (BadElementException bex) {
	        img = null;
	    }
        return img;
	}
	
	private static void retrieveHeaderPicklistValues(HttpServletRequest request) {
	    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	    PickListService service = (PickListService) context.getBean(PickListService.class);
	    PickListValue value = service.getDefaultPickListValue(PICKLIST_GOVERNOR);
	    GOVERNOR = (value != null) ? value.getValue() : " ";
	    value = service.getDefaultPickListValue(PICKLIST_LT_GOVERNOR);
	    LT_GOVERNOR = (value != null) ? value.getValue() : " ";
	    value = service.getDefaultPickListValue(PICKLIST_EXEC_DIRECTOR);
	    EXEC_DIRECTOR = (value != null) ? value.getValue() : " ";
	    value = service.getDefaultPickListValue(PICKLIST_DIRECTOR);
	    DIRECTOR = (value != null) ? value.getValue() : " ";
        value = service.getDefaultPickListValue(PICKLIST_FOOTER);
        FOOTER = (value != null) ? value.getValue() : "";
	}
	
	private static void stampHeader(PdfWriter writer, ByteArrayOutputStream ba) throws DocumentException, IOException {
	    PdfReader reader = new PdfReader(ba.toByteArray());
        // Retrieve the first page of the header document and wrap as an Image to use as new document watermark
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        Image img = Image.getInstance(page);
        // Make sure the image has an absolute page position
        img.setAbsolutePosition(0, 0);
        PdfContentByte under = writer.getDirectContentUnder();
        under.addImage(img);
	}

    // Diagnostic function used to display the text column borders (for form text placement debugging purposes).
	@SuppressWarnings("unused")
    private static void showColumnBorders(PdfWriter writer, float left, float bottom, float right, float top) {
	    PdfContentByte canvas = writer.getDirectContent();
        BaseColor lineColor = new BaseColor(255,0,0);
        canvas.setColorStroke(lineColor);
        canvas.setLineWidth(0.5f);
   		canvas.moveTo(left, bottom);
   		canvas.lineTo(right, bottom);
   		canvas.lineTo(right, top);
   		canvas.lineTo(left, top);
   		canvas.lineTo(left, bottom);
   		canvas.stroke();
    }
}
