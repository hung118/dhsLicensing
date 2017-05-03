package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.model.PersonalName;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.LocationService;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
@DependsOn("fontLoader")
public class CaseloadReport extends AbstractCclReport {

	public static final String TEMPLATE_KEY = "caseload";

	private static final Rectangle PAGE_SIZE = PageSize.LETTER.rotate();
	
	private static final String COLUMN_TYPE_KEY = "colType";
	private static final String SPECIALIST_TYPE_KEY = "specialistType";
	
	private static final List<Input> inputs;
	
	
	
	static {
		inputs = new ArrayList<Input>();
		
		Map<String, String> COLUMN_TYPE_OPTIONS = new HashMap<String, String>();
		for (ColumnType type : ColumnType.values()) {
			COLUMN_TYPE_OPTIONS.put(type.name(), type.getLabel());
		}
		
		inputs.add(new Input(COLUMN_TYPE_KEY, "Column Type", ColumnType.LICENSE_TYPE.name(), String.class, true, COLUMN_TYPE_OPTIONS, InputDisplayType.SELECT));
		
		Map<String, String> SPECIALIST_TYPE_OPTIONS = new HashMap<String, String>();
		SPECIALIST_TYPE_OPTIONS.put(RoleType.ROLE_LICENSOR_SPECIALIST.name(), RoleType.ROLE_LICENSOR_SPECIALIST.getDisplayName());
		
		inputs.add(new Input(SPECIALIST_TYPE_KEY, "Caseload Type", RoleType.ROLE_LICENSOR_SPECIALIST.name(), String.class, true, SPECIALIST_TYPE_OPTIONS, InputDisplayType.SELECT));
	}
	
	@Autowired
	private LocationService locationService;
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return RoleType.valueOf((String) context.get(SPECIALIST_TYPE_KEY)).getDisplayName() + " Caseloads " + sdf.format(new Date()) + ".pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		return inputs;
	}
	
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException {
		List<Object[]> results = getResults(context);
		
		ColumnType columnType = ColumnType.valueOf((String) context.get(COLUMN_TYPE_KEY));
		RoleType roleType = RoleType.valueOf((String) context.get(SPECIALIST_TYPE_KEY));
		
		ReportTable<Integer> reportTable = new ReportTable<Integer>();
		
		for (Object[] res : results) {
			String column = null;
			switch (columnType) {
				case LICENSE_TYPE:
					column = (String) res[3];
					break;
				case CITY:
					column = (String) res[3];
					break;
				case COUNTY:
					column = locationService.getCounty((String) res[3], (String) res[4], (String) res[5]);
					break;
				case ZIP_CODE:
					String zip = (String) res[5];
					if (zip.length() > 5) {
						zip = zip.substring(0, 5);
					}
					column = zip;
					break;
			}

			if (StringUtils.isBlank(column)) {
				column = "Unknown";
			}
			
			reportTable.addColumn(column);
			
			String specRow = null;
			String specFnm = (String) res[1];
			String specLnm = (String) res[2];
			if (StringUtils.isNotBlank(specFnm) || StringUtils.isNotBlank(specLnm)) {
				PersonalName name = new PersonalName(specFnm, specLnm);
				specRow = name.getFirstAndLastName();
			} else {
				specRow = "Unknown";
			}
			
			reportTable.addRow(specRow);
			
			Integer val = reportTable.getTableDataItem(specRow, column);
			if (val == null) {
				val = new Integer(1);
			} else {
				val = new Integer(val.intValue() + 1);
			}
			reportTable.addTableDataItem(specRow, column, val);
		}
		
		setFileName(context, descriptor);
		
		Document document = new Document(PAGE_SIZE, MARGIN, MARGIN, MARGIN, MARGIN);
		try {
			PdfWriter canvas = PdfWriter.getInstance(document, outputStream);
			document.open();
			
			ColumnText text = new ColumnText(canvas.getDirectContent());
			text.setSimpleColumn(document.getPageSize().getLeft(MARGIN), document.getPageSize().getBottom(MARGIN), document.getPageSize().getRight(MARGIN),
					document.getPageSize().getTop(MARGIN));
			
			StringBuilder sb = new StringBuilder(roleType.getDisplayName());
			sb.append(" Caseloads");
			Paragraph heading = new Paragraph(sb.toString(), REPORT_HEADING_FONT);
			heading.setAlignment(Element.ALIGN_CENTER);
			text.addElement(heading);
			text.go();
			float yLine = text.getYLine();
			yLine -= FONT_SIZE;
			
			int columns = reportTable.getColumns().size() + 2;
			
			String[][] totTab = new String[reportTable.getRows().size()][columns];
			float maxLicWidth = 0f;
			float maxTotWidth = FONT_SIZE;
			int rowIdx = 0;
			for (Iterator<String> rowItr = reportTable.getRows().iterator(); rowItr.hasNext();) {
				String row = rowItr.next();
				totTab[rowIdx][0] = row;
				float licWidth = getTextWidth(row, FONT);
				if (licWidth > maxLicWidth) {
					maxLicWidth = licWidth;
				}
				
				int rowTotal = 0;
				int colIdx = 1;
				for (Iterator<String> colItr = reportTable.getColumns().iterator(); colItr.hasNext();) {
					String col = colItr.next();
					Integer val = reportTable.getTableDataItem(row, col);
					if (val != null) {
						totTab[rowIdx][colIdx] = val.toString();
						rowTotal += val.intValue();
					}
					colIdx++;
				}
				String rowTotalStr = Integer.toString(rowTotal);
				float totWidth = getTextWidth(rowTotalStr, TABLE_HEADER_FONT);
				if (totWidth > maxTotWidth) {
					maxTotWidth = totWidth;
				}
				totTab[rowIdx][columns - 1] = rowTotalStr;
				rowIdx++;
			}
			
			//add border and cell padding to licensor width
			maxLicWidth += (TABLE_CELL_PADDING * 2) + (TABLE_BORDER_SIZE * 2);
			maxTotWidth += (TABLE_CELL_PADDING * 2) + (TABLE_BORDER_SIZE * 2);
			
			float width = PAGE_SIZE.getWidth() - (2 * MARGIN);
			width -= maxLicWidth;
			
			float[] colWidths = new float[columns];
			colWidths[0] = maxLicWidth;
			for (int i = 1; i < columns; i++) {
				colWidths[i] = maxTotWidth;
			}
			
			PdfPTable table = new PdfPTable(reportTable.getColumns().size() + 2);
			table.setHeaderRows(1);
			table.setLockedWidth(true);
			table.setTotalWidth(colWidths);
			table.setSpacingBefore(FONT_SIZE);
			setDefaultCellAttributes(table.getDefaultCell());
			
			PdfPCell cell = null;
			
			cell = new PdfPCell(new Phrase(""));
			cell.setBorderWidthTop(0);
			cell.setBorderWidthLeft(0);
			table.addCell(cell);
			for (Iterator<String> itr = reportTable.getColumns().iterator(); itr.hasNext();) {
				cell = getRotatedHeaderCell(itr.next());
				table.addCell(cell);
			}
			cell = getRotatedHeaderCell("Total");
			table.addCell(cell);
			
			for (String[] row : totTab) {
				for (int i = 0; i < row.length; i++) {
					if (row[i] == null) {
						table.addCell(getNumberCell(""));
					} else {
						table.addCell(getCell(new Phrase(row[i], i == row.length - 1 ? TABLE_HEADER_FONT : FONT), i == 0 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER));
					}
					
				}
			}
			
			writeTable(table, document, canvas.getDirectContent(), colWidths, yLine, true);
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
		document.close();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getResults(Map<String, Object> context) {
		ColumnType columnType = ColumnType.valueOf((String) context.get(COLUMN_TYPE_KEY));
		RoleType roleType = RoleType.valueOf((String) context.get(SPECIALIST_TYPE_KEY));
		
		boolean fetchLicense = columnType == ColumnType.LICENSE_TYPE;
		
		StringBuilder sql = new StringBuilder("select pers.id as pers_id, pers.firstname spec_f_nm, pers.lastname spec_l_nm, ");
		
		if (fetchLicense) {
			sql.append(" lictype.plvvalue ");
		} else {
			sql.append(" addr.city, addr.state, addr.zip_code ");
		}
		
		sql.append(" from facility f, ");
		
		if (roleType == RoleType.ROLE_LICENSOR_SPECIALIST) {
			sql.append(" facility_licensing_specialist ls, ");
		}
		
		sql.append(" person pers, ");
		
		if (fetchLicense) {
			sql.append(" facility_current_license fcl, facility_license lic, picklistvalue lictype ");
		} else {
			sql.append(" address addr ");
		}
		
		sql.append(" where f.status = 'REGULATED' ");
		
		if (roleType == RoleType.ROLE_LICENSOR_SPECIALIST) {
			sql.append(" and f.id = ls.facility_id (+) and ls.specialist_id = pers.id (+) ");
		} else {
			sql.append(" and f.id = pg.facility_id and pg.specialist_id = pers.id ");
		}
		
		if (fetchLicense) {
			sql.append(" and f.id = fcl.facility_id (+) and fcl.license_id = lic.id (+) and lic.license_type_id = lictype.id (+)");
		} else {
			sql.append(" and f.locationaddressid = addr.id (+) ");
		}
		
		Query query = reportRunner.getNativeQuery(sql.toString());
		
		return (List<Object[]>) query.getResultList();
	}
	
	public static enum ColumnType {
		
		LICENSE_TYPE("License Type"), ZIP_CODE("Zip Code"), COUNTY("County"), CITY("City");
		
		private final String label;
		
		private ColumnType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}
}