/*
 * PageNumberEvent.java
 *
 * Created on December 4, 2008, 10:47 AM
 */

package gov.utah.dts.det.reports;

/**
 *
 * @author  bbills
 */
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/* The ReportPageEvents class is used to override the default PdfPageEvent class
   for creating customized page headers and footers.
 */
public class PageNumberEventRight extends PdfPageEventHelper {

    // Class variables
    // This is the contentbyte object of the writer
    PdfContentByte cb;

    // we will put the final number of pages in a template
    PdfTemplate template;

    // this is the BaseFont we are going to use for the header / footer
    BaseFont bf = null;
    
    /* We override the onOpenDocument method to create our BaseFonts, load the PdfContentByte,
       and create a template for the total document pages used in the page footer. */
    public void onOpenDocument(PdfWriter writer, Document document) {
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb = writer.getDirectContent();
            template = cb.createTemplate(70, 20);
        }
        catch(DocumentException de) {
        }
        catch(IOException ioe) {
        }
    }

    /* we override the onEndPage method to write a page footer and a new page header to the document. */
    public void onEndPage(PdfWriter writer, Document document) {
    	float len = 0;
    	float evaluator_len = 0;
        float textXPos;
        float textYPos;
        String text;
        String evaluator;
        Rectangle page = document.getPageSize();
        
        // Write the current page information footer to the document
        cb.beginText();
        int pageN = writer.getPageNumber();
        text = "Page: " + pageN + " of ";
        evaluator = text + "99999";
        evaluator_len = bf.getWidthPoint(evaluator, 10);
        len = bf.getWidthPoint(text, 10);
        cb.setFontAndSize(bf, 10);
        textXPos = page.getWidth();
        if (textXPos > 15) {
        	textXPos -= 15;
        }
        textXPos -= evaluator_len;
        textYPos = 15;
        cb.setTextMatrix(textXPos, textYPos);
        cb.showText(text);
        cb.endText();
        textXPos += len;
        cb.addTemplate(template, textXPos, textYPos);
    }

    /* We override the onCloseDocument method to populate the template created in the onOpenDocument
       method with the total pages in the document */
    public void onCloseDocument(PdfWriter writer, Document document) {
        // Add the 'to' page number to the footer template
        template.beginText();
        template.setFontAndSize(bf, 10);
        template.showText(String.valueOf(writer.getPageNumber() - 1));
        template.endText();
    }
}
