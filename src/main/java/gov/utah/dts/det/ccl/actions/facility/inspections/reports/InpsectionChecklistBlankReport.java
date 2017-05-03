 package gov.utah.dts.det.ccl.actions.facility.inspections.reports;

import gov.utah.dts.det.ccl.model.InspectionChecklistHeader;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.HeaderFooter;

public class InpsectionChecklistBlankReport {

	private static final Logger log = LoggerFactory.getLogger(InpsectionChecklistBlankReport.class);

    // Define report fonts
	static final Font mastFont1 = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
	static final Font mastFont2 = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	static final Font mediumfont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    static final Font mediumfontI = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, BaseColor.BLACK);
    static final Font mediumfontB = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    static final Font headfontB = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    static final Font headfontBSmall = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
    
    static final Font headAndFootFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);

    public ByteArrayOutputStream generate(List<String[]> checklistHeaders, List<RuleSection> sections) {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(checklistHeaders, sections, ba);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			ba = null;
		}
        return ba;
	}
	
	private void writePdf(List<String[]> checklistHeaders, List<RuleSection> sections, OutputStream ba) throws DocumentException {
		Document document = null;
		document = new Document(PageSize.A4, 8, 8, 14, 14);
        @SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		
        document.open();

        document.addHeader("headerBase", "DHS - OL");
        
        /* ---------------------------
         * Create the mast head
         * ---------------------------
         */
        
        Paragraph p = new Paragraph("State of Utah", mastFont1);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        
        p = new Paragraph("Department of Human Services", mastFont1);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        p = new Paragraph("Office of Licensing", mastFont2);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        
        p = new Paragraph(" ", mastFont2);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        /* ---------------------------
         * Create the report header
         * ---------------------------
         */
        
        float[] widths = { 4, 6 };
        PdfPTable table = new PdfPTable(widths);
        
        String[] address1 = {"?", "?", "?"};
        String[] address2 = {"?", "?", "?"};
        String[] city = {"?", "?", "?"};
        String[] state = {"?", "?", "?"};
        String[] zip = {"?", "?", "?"};

        // Add all the header elements
        for (Iterator<String[]> iterator = checklistHeaders.iterator(); iterator.hasNext();) {
        	String[] header = iterator.next();
        	if (header[0].equals(InspectionChecklistHeader.ADDRESS_1)) {
        		address1 = header;
        	} else if (header[0].equals(InspectionChecklistHeader.ADDRESS_2)) {
        		address2 = header;
        	} else if (header[0].equals(InspectionChecklistHeader.CITY)) {
        		city = header;
        	} else if (header[0].equals(InspectionChecklistHeader.STATE)) {
        		state = header;
        	} else if (header[0].equals(InspectionChecklistHeader.ZIPCODE)) {
        		zip = header;
        	} else { 
        		addHeaderCell(table, header[1], header[2]);
        	}
		}

        StringBuilder address = new StringBuilder("");
        address.append(""+address1[2]+" \n");
        if (address2[2] != null && !"".equals(address2[2])) {
        	address.append(""+address2[2]+" \n");
        }
        address.append(""+city[2]+" "+state[2]+" "+zip[2]+" ");
        addHeaderCell(table, "Address", address.toString());
        		
	    document.add(table);
	    
        /* ---------------------------
         * Create the check list data
         * ---------------------------
         */
	    float[] widths2 = { 20, .7f, .7f, .7f, 10 };
	    table = new PdfPTable(widths2);
	    
	    addHeader(table, "");
	    
	    for (RuleSection section : sections) {
	    	
	    	if (section.isActive()) {

	    		StringBuilder sb = new StringBuilder();
	    		sb.append(section.getRule().getNumber()+"-"+section.getSectionBase()+"-"+section.getNumber()+" ");
	    		sb.append(section.getTitle()+" ");
	    		sb.append(section.getName());

	    		addSection(table, sb.toString());

	    		// add any sub-sections
	    		if (section.getSubSectionCount() > 0) {

	    			for (RuleSubSection sub : section.getSubSections()) {
	    				
	    				if (sub.getCategory().getCharacter() == 'A') {
	    					sb = new StringBuilder();
	    					sb.append(sub.getNumber()+" ");
	    					sb.append(sub.getRuleContent());
	    					addSubSection(table, sb.toString());
	    				}
	    			}

	    		} else {
	    			addSubSection(table, " ");
	    		}
	    	}
	    }
	    
        document.add(table);
        
        document.close();
	}
	
	private void addHeaderCell(PdfPTable table, String label, String value) {
		if (label == null) 
			label = "";
		if (label.length() > 1) {
			label+=": ";
		}
		if (value == null) 
			value = "";
		while (value.length() < 50)
			value += " ";
		PdfPCell cell = new PdfPCell(new Phrase(label, mediumfont));
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", mediumfont));
		cell.setBorder(1);
		cell.setBorderColorBottom(BaseColor.BLACK);
		cell.setBorderColorLeft(BaseColor.WHITE);
		cell.setBorderColorRight(BaseColor.WHITE);
		cell.setBorderColorTop(BaseColor.WHITE);
		
		table.addCell(cell);
	}

	private void addHeader(PdfPTable table, String firstCellContent) {
		
		firstCellContent = "This document is a checklist created for use by" +
		" the Office of Licensing.  It is not an interpretation of the Rules." +
		" It summarizes the licensor's review at the time of this scheduled on site inspection." +
		" Refer to: http://rules.utah.gov for more information.";
		
		PdfPCell cell = new PdfPCell(new Paragraph(firstCellContent, headfontBSmall));
		cell.setBorder(0);
//		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("COMPLIANT", headfontBSmall));
		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("NON-COMPLIANT", headfontBSmall));
		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("NOT-APPLICABLE", headfontBSmall));
		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("COMMENTS", headfontBSmall));
		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
	}
	
	private void addSection(PdfPTable table, String content) {
		if (content == null) 
			content = "";
		PdfPCell cell = new PdfPCell(new Phrase(blank(content), headfontB));
//		cell.setBorder(0);
		//cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", headfontBSmall));
//		cell.setBorder(0);
//		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", headfontBSmall));
//		cell.setBorder(0);
//		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", headfontBSmall));
//		cell.setBorder(0);
//		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", headfontBSmall));
//		cell.setBorder(0);
//		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);
	}

	private void addSubSection(PdfPTable table, String content) {
		PdfPCell cell = new PdfPCell(new Phrase(blank(content), mediumfont));
//		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", mediumfont));
//		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", mediumfont));
//		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", mediumfont));
//		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", mediumfont));
//		cell.setBorder(0);
		cell.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cell);
	}
	
	private String blank(String s) {
		if (s == null) 
			return "";
		return s;
	}

}
