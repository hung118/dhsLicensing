 package gov.utah.dts.det.ccl.actions.facility.inspections.reports;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.actions.facility.inspections.viewHelper.ChecklistRowHelper;
import gov.utah.dts.det.ccl.actions.facility.inspections.viewHelper.ChecklistViewHelper;
import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.model.InspectionChecklistHeader;
import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

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

public class InpsectionChecklistReport {
	
	private PickListService pickListService;

	private static final Logger log = LoggerFactory.getLogger(InpsectionChecklistReport.class);

    // Define report fonts
	static final Font mastFont1 = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
	static final Font mastFont2 = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	static final Font mediumfont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    static final Font mediumfontI = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, BaseColor.BLACK);
    static final Font mediumfontB = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    static final Font headfontB = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    static final Font headfontBSmall = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

    public ByteArrayOutputStream generate(InspectionChecklist checklist) {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(checklist, ba);
		} catch(Exception ex) {
			log.error(ex.getMessage());
			ba = null;
		}
        return ba;
	}
	
    private void writePdf(InspectionChecklist checklist, OutputStream ba) throws DocumentException {
		Document document = null;
		document = new Document(PageSize.A4, 8, 8, 20, 10);
        @SuppressWarnings("unused")
		PdfWriter writer = PdfWriter.getInstance(document, ba);
        //SimpleDateFormat df2 = new SimpleDateFormat("MMMM d, yyyy");
		
        document.open();

        boolean b = document.addHeader("headerBase", "DHS - OL");
        
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
        
        for (Iterator<InspectionChecklistHeader> iterator = checklist.getHeaders().iterator(); iterator.hasNext();) {
        	InspectionChecklistHeader header = iterator.next();
        	if (header.getItemName().equals(InspectionChecklistHeader.ADDRESS_1)) {
        		address1[1] = header.getItemLabel(); address1[2] = header.getItemValue();
        	} else if (header.getItemName().equals(InspectionChecklistHeader.ADDRESS_2)) {
        		address2[1] = header.getItemLabel(); address2[2] = header.getItemValue();
        	} else if (header.getItemName().equals(InspectionChecklistHeader.CITY)) {
        		city[1] = header.getItemLabel(); city[2] = header.getItemValue();
        	} else if (header.getItemName().equals(InspectionChecklistHeader.STATE)) {
        		state[1] = header.getItemLabel(); state[2] = header.getItemValue();
        	} else if (header.getItemName().equals(InspectionChecklistHeader.ZIPCODE)) {
        		zip[1] = header.getItemLabel(); zip[2] = header.getItemValue();
        	} else if (header.getItemName().equals(InspectionChecklistHeader.PROGRAM)) {
        		try {
					if (pickListService != null && Long.parseLong(header.getItemValue()) > 0) { 
						PickListValue pv = pickListService.loadPickListValueById(Long.parseLong(header.getItemValue()));
						addHeaderCell(table, header.getItemLabel(), pv.getValue());
					} else {
						addHeaderCell(table, header.getItemLabel(), header.getItemValue());
					}
				} catch (NumberFormatException e) {
					addHeaderCell(table, header.getItemLabel(), header.getItemValue());
				}
        	} else { 
        		addHeaderCell(table, header.getItemLabel(), header.getItemValue());
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
	    
	    ChecklistViewHelper viewHelper = new ChecklistViewHelper(checklist, false);
	    
	    while (viewHelper.hasNext()) {
			ChecklistRowHelper row = (ChecklistRowHelper) viewHelper.next();
			RuleSection section = row.getSection();

			if (row.getSectionOnly()) { 
				addSection(table, section, row.getResults().get(0));
			} else {
				addSection(table, section, null);
			}
	    	
	    	// add any sub-sections
	    	if (!row.getSectionOnly()) {
	    		for (InspectionChecklistResult r : row.getResults()) {
					this.addSubSection(table, r.getSubSection(), r);
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
//		cell.setBorderColorBottom(BaseColor.BLACK);
//		cell.setBorderColorLeft(BaseColor.WHITE);
//		cell.setBorderColorRight(BaseColor.WHITE);
//		cell.setBorderColorTop(BaseColor.WHITE);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(blank(value), mediumfont));
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
	
	private void addSection(PdfPTable table, RuleSection section, InspectionChecklistResult result) {
		
		StringBuilder sb = new StringBuilder();
    	sb.append(section.getRule().getNumber()+"-"+section.getSectionBase()+"-"+section.getNumber()+" ");
    	sb.append(section.getTitle()+" ");
    	sb.append(section.getName());
    	
		PdfPCell cell = new PdfPCell(new Phrase(sb.toString(), headfontB));
		table.addCell(cell);

		if (result != null && InspectionChecklistResult.COMPLIANT.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (result != null && InspectionChecklistResult.NON_COMPLIANT.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (result != null && InspectionChecklistResult.NOT_APPLICABLE.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (result != null && result.getComments() != null) {
			cell = new PdfPCell(new Phrase(result.getComments(), mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);
	}

	private void addSubSection(PdfPTable table, RuleSubSection sub, InspectionChecklistResult result) {

		StringBuilder sb = new StringBuilder();
		sb.append(sub.getNumber()+" "+sub.getRuleContent());

		PdfPCell cell = new PdfPCell(new Phrase(sb.toString(), mediumfont));
		table.addCell(cell);

		if (InspectionChecklistResult.COMPLIANT.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (InspectionChecklistResult.NON_COMPLIANT.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (InspectionChecklistResult.NOT_APPLICABLE.equals(result.getResult())) {
			cell = new PdfPCell(new Phrase("X", mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);

		if (result.getComments() != null) {
			cell = new PdfPCell(new Phrase(result.getComments(), mediumfont));
		} else {
			cell = new PdfPCell(new Phrase(" ", mediumfont));
		}
		table.addCell(cell);
	}
	
	private String blank(String s) {
		if (s == null) 
			return "";
		return s;
	}

	public PickListService getPickListService() {
		return pickListService;
	}

	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}

}
