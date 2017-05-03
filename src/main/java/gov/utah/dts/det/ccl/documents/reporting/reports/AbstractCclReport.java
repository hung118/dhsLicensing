package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.reporting.ReportRunner;
import gov.utah.dts.det.ccl.documents.templating.AbstractTemplate;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public abstract class AbstractCclReport extends AbstractTemplate {

	protected static final String FONT_NAME = "Arial";
	
	protected static final float MARGIN = 36f;
	protected static final float FONT_SIZE = 10f;
	protected static final float HEADING_FONT_SIZE = 12f;
	protected static final Font FONT = FontFactory.getFont(FONT_NAME, FONT_SIZE);
	protected static final Font REPORT_HEADING_FONT = FontFactory.getFont(FONT_NAME, 14f);
	protected static final Font HEADING_FONT = FontFactory.getFont(FONT_NAME, HEADING_FONT_SIZE, Font.BOLD);
	protected static final Font TABLE_HEADER_FONT = FontFactory.getFont(FONT_NAME, FONT_SIZE, Font.BOLD);
	protected static final float TABLE_CELL_PADDING = 5f;
	protected static final float TABLE_BORDER_SIZE = .5f;
	
	protected static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("MM/dd/yyyy");
	protected static NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	
	@Autowired
	protected ReportRunner reportRunner;
	
	protected PdfPCell getHeaderCell(String text) {
		PdfPCell cell = new PdfPCell();
		setDefaultCellAttributes(cell);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		Phrase phrase = new Phrase(text, TABLE_HEADER_FONT);
		cell.setPhrase(phrase);
		return cell;
	}
	
	protected PdfPCell getRotatedHeaderCell(String text) {
		PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_HEADER_FONT));
		setDefaultCellAttributes(cell);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setRotation(90);
		return cell;
	}
	
	protected PdfPCell getNumberCell(String text) {
		PdfPCell cell = new PdfPCell();
		setDefaultCellAttributes(cell);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		Phrase phrase = new Phrase(text, FONT);
		cell.setPhrase(phrase);
		return cell;
	}
	
	protected PdfPCell getCurrencyCell(String text) {
		PdfPCell cell = new PdfPCell();
		setDefaultCellAttributes(cell);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		Phrase phrase = new Phrase(text, FONT);
		cell.setPhrase(phrase);
		return cell;
	}
	
	protected PdfPCell getCell(String text, int alignment) {
		PdfPCell cell = new PdfPCell();
		setDefaultCellAttributes(cell);
		cell.setHorizontalAlignment(alignment);
		Phrase phrase = new Phrase(text, FONT);
		cell.setPhrase(phrase);
		return cell;
	}
	
	protected PdfPCell getCell(Phrase phrase, int alignment) {
		PdfPCell cell = new PdfPCell(phrase);
		setDefaultCellAttributes(cell);
		cell.setHorizontalAlignment(alignment);
		return cell;
	}
	
	protected void setDefaultCellAttributes(PdfPCell cell) {
		cell.setPadding(TABLE_CELL_PADDING);
	}
	
	
	protected void clearStringBuilder(StringBuilder sb) {
		sb.delete(0, sb.length());
	}
	
	protected float getTextWidth(String text, Font font) {
		Chunk c = new Chunk(text, font);
		return c.getWidthPoint();
	}
	
	protected void writeTable(PdfPTable table, Document document, PdfContentByte canvas, float[] colWidths, float yLine, boolean repeatFirstColumn) {
		List<Page> pages = new ArrayList<Page>();
		
		float yPos = yLine;
		int firstRow = 1;
		int lastRow = 1;
		int rows = table.getRows().size();
		while (lastRow < rows) {
			firstRow = lastRow;
			float height = 0f;
			if (yLine != 0) {
				height = yLine - MARGIN - table.getRowHeight(0);
			} else {
				height = document.getPageSize().getHeight() - (MARGIN * 2) - table.getRowHeight(0);
			}
			while (lastRow < rows && height - table.getRowHeight(lastRow) > 0) {
				height -= table.getRowHeight(lastRow);
				lastRow++;
			}
			
			int firstCol = 0;
			int lastCol = 0;
			int cols = colWidths.length;
			while (lastCol < cols) {
				firstCol = lastCol;
				float width = document.getPageSize().getWidth() - (MARGIN * 2);
				boolean addFirstCol = false;
				if (firstCol > 0 && repeatFirstColumn) {
					width -= colWidths[0];
					addFirstCol = true;
				}
				while (lastCol < cols && width - colWidths[lastCol] > 0) {
					width -= colWidths[lastCol];
					lastCol++;	
				}
				pages.add(new Page(yPos, firstRow, lastRow == rows - 1 ? lastRow + 1 : lastRow, firstCol, lastCol == cols -1 ? lastCol + 1 : lastCol, addFirstCol));
			}
			yPos = 0;
		}
		
		for (int i = 0; i < pages.size(); i++) {
			if (i == 0) {
				writePage(table, document, canvas, pages.get(i), false);
			} else {
				writePage(table, document, canvas, pages.get(i), true);
			}
		}
	}
	
	private void writePage(PdfPTable table, Document document, PdfContentByte canvas, Page page, boolean addPage) {
		if (addPage) {
			document.newPage();
		}
		
		float xPos = document.getPageSize().getLeft(MARGIN);
		float yPos = page.getyLine() == 0 ? document.getPageSize().getTop(MARGIN) : page.getyLine();
		
		//HEADER
		if (page.isAddFirstColumn()) {
			table.writeSelectedRows(0, 1, 0, 1, xPos, yPos, canvas);
			xPos += table.getRow(0).getCells()[0].getWidth();
		}
		
		//always write the column headers
		table.writeSelectedRows(page.getStartColumn(), page.getEndColumn(), 0, 1, xPos, yPos, canvas);
		
		//reset the xPos to the side of the page
		xPos = document.getPageSize().getLeft(MARGIN);
		//set the yPos to the next row after the header
		yPos -= table.getRowHeight(0);
		
		if (page.isAddFirstColumn()) {
			table.writeSelectedRows(0, 1, page.getStartRow(), page.getEndRow(), xPos, yPos, canvas);
			xPos += table.getRow(0).getCells()[0].getWidth();
		}
		
		//DATA		
		table.writeSelectedRows(page.getStartColumn(), page.getEndColumn(), page.getStartRow(), page.getEndRow(), xPos, yPos, canvas);
	}
	
	@SuppressWarnings("serial")
	private static class Page implements Serializable {
		
		private float yLine;
		private int startRow;
		private int endRow;
		private int startColumn;
		private int endColumn;
		private boolean addFirstColumn;
		
		public Page(float yLine, int startRow, int endRow, int startColumn, int endColumn, boolean addFirstColumn) {
			this.yLine = yLine;
			this.startRow = startRow;
			this.endRow = endRow;
			this.startColumn = startColumn;
			this.endColumn = endColumn;
			this.addFirstColumn = addFirstColumn;
		}
		
		public float getyLine() {
			return yLine;
		}
		
		public int getStartRow() {
			return startRow;
		}
		
		public int getEndRow() {
			return endRow;
		}
		
		public int getStartColumn() {
			return startColumn;
		}
		
		public int getEndColumn() {
			return endColumn;
		}
		
		public boolean isAddFirstColumn() {
			return addFirstColumn;
		}
	}
}