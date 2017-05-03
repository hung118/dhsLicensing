package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.model.enums.FindingCategoryType;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;

@Component
@DependsOn("fontLoader")
public class InspectionsConductedReport extends AbstractDimensionReport {

	public static final String TEMPLATE_KEY = "inspections-conducted";

	private static final Rectangle PAGE_SIZE = PageSize.LETTER;
	
	private static final String FINDING_CATEGORY_KEY = "fCat";
	private static final String INSPECTION_TYPE_KEY = "insType";
	private static final String CITY_KEY = "city";
	private static final String ZIP_CODE_KEY = "zipCode";
	private static final String COUNTY_KEY = "county";
	private static final String REGION_KEY = "region";
	private static final String ROW_TYPE_KEY = "rowType";
	
	private static final Map<String, String> ROW_TYPE_OPTIONS = new HashMap<String, String>();
	
	static {
		for (RowType type : RowType.values()) {
			ROW_TYPE_OPTIONS.put(type.name(), type.getLabel());
		}
	}
	
	@Autowired
	private PickListService pickListService;
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return "Inspections Conducted Report " + sdf.format(new Date()) + ".pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		List<Input> inputs = new ArrayList<Input>();
		//inputs needed
		//rule violation
		//the columns are always license type
		//the rows can be selected (city, zip, region)
		Map<String, String> findingCategories = new LinkedHashMap<String, String>();
		findingCategories.put("ALL", "All");
		for (FindingCategoryType fCat : FindingCategoryType.values()) {
			findingCategories.put(fCat.name(), fCat.getDisplayName());
		}
		
		Map<Long, String> inspectionTypes = new LinkedHashMap<Long, String>();
		inspectionTypes.put(-1l, "All");
		List<PickListValue> types = pickListService.getValuesForPickList("Inspection Types", false);
		for (PickListValue type : types) {
			inspectionTypes.put(type.getId(), type.getValue());
		}
		
		inputs.add(new Input(FINDING_CATEGORY_KEY, "Finding Category", null, String.class, false, findingCategories, InputDisplayType.SELECT));
		inputs.add(new Input(INSPECTION_TYPE_KEY, "Inspection Type", null, Long.class, false, inspectionTypes, InputDisplayType.SELECT));
		inputs.add(new Input(CITY_KEY, "City", null, String.class, false, InputDisplayType.TEXTFIELD));
		inputs.add(new Input(ZIP_CODE_KEY, "Zip Code", null, String.class, false, InputDisplayType.TEXTFIELD));
		inputs.add(new Input(COUNTY_KEY, "County", null, String.class, false, InputDisplayType.TEXTFIELD));
		inputs.add(new Input(REGION_KEY, "Region", null, String.class, false, InputDisplayType.TEXTFIELD));
		inputs.add(new Input(DATE_RANGE_START_KEY, "Start Date", DateUtils.truncate(new Date(), Calendar.MONTH), Date.class, true, InputDisplayType.DATE));
		inputs.add(new Input(DATE_RANGE_END_KEY, "End Date", DateUtils.truncate(new Date(), Calendar.DATE), Date.class, true, InputDisplayType.DATE));
		inputs.add(new Input(ROW_TYPE_KEY, "Row Type", RowType.REGION.name(), String.class, true, ROW_TYPE_OPTIONS, InputDisplayType.SELECT));
		
		return inputs;
	}
	
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor fileDescriptor) throws TemplateException {
		FindingCategoryType fCat = getFindingCategory(context);
		PickListValue insType = getInspectionType(context);
		
		List<Object[]> results = getResults(context);
		
		ReportTable<Integer> reportTable = new ReportTable<Integer>();
		
		for (Object[] res : results) {
			String row = (String) res[1];
			String column = (String) res[0];
			Integer val = ((BigDecimal) res[2]).intValue();
			
			reportTable.addRow(row);
			reportTable.addColumn(column);
			reportTable.addTableDataItem(row, column, val);
		}
		
		setFileName(context, fileDescriptor);
		
		Document document = new Document(PAGE_SIZE, 36, 36, 36, 36);
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			
			StringBuilder sb = new StringBuilder();
			if (insType != null) {
				sb.append(insType.getValue());
				sb.append(" ");
			}
			sb.append("Inspections Conducted");
			if (fCat != null) {
				sb.append(" With ");
				sb.append(fCat.getDisplayName());
				sb.append(" Findings");
			}
			
			Paragraph heading = new Paragraph(sb.toString());
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			
			Date startDate = (Date) context.get(DATE_RANGE_START_KEY);
			Date endDate = (Date) context.get(DATE_RANGE_END_KEY);
			
			clearStringBuilder(sb);
			if (startDate.compareTo(endDate) == 0) {
				sb.append("On ");
				sb.append(DATE_FORMATTER.format(startDate));
			} else {
				sb.append(DATE_FORMATTER.format(startDate));
				sb.append(" - ");
				sb.append(DATE_FORMATTER.format(endDate));
			}
			Paragraph date = new Paragraph(sb.toString(), FONT);
			date.setAlignment(Element.ALIGN_RIGHT);
			document.add(date);
			
			PdfPTable table = new PdfPTable(reportTable.getColumns().size() + 2);
			table.setHeaderRows(1);
			table.setSpacingBefore(FONT_SIZE);
			table.setWidthPercentage(100f);
			setDefaultCellAttributes(table.getDefaultCell());
			
			table.addCell("");
			for (Iterator<String> itr = reportTable.getColumns().iterator(); itr.hasNext();) {
				table.addCell(getHeaderCell(itr.next()));
			}
			table.addCell(getHeaderCell("Total"));
			int[] colTotals = new int[reportTable.getColumns().size() + 1];
			for (Iterator<String> rowItr = reportTable.getRows().iterator(); rowItr.hasNext();) {
				String row = rowItr.next();
				table.addCell(getHeaderCell(row));
				int total = 0;
				int idx = 0;
				for (Iterator<String> colItr = reportTable.getColumns().iterator(); colItr.hasNext();) {
					String column = colItr.next();
					Integer value = reportTable.getTableDataItem(row, column);
					if (value == null) {
						table.addCell(getNumberCell("0"));
					} else {
						total += value;
						colTotals[idx] = colTotals[idx] + value;
						table.addCell(getNumberCell(value.toString()));
					}
					idx++;
				}
				table.addCell(getHeaderCell(Integer.toString(total)));
				colTotals[colTotals.length - 1] = colTotals[colTotals.length - 1] + total;
				idx++;
			}
			table.addCell(getHeaderCell("Total"));
			for (int i = 0; i < colTotals.length; i++) {
				table.addCell(getHeaderCell(Integer.toString(colTotals[i])));
			}
			
			document.add(table);
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
		document.close();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getResults(Map<String, Object> context) {
		FindingCategoryType fCat = getFindingCategory(context);
		PickListValue insType = getInspectionType(context);
		
		String rowTypeStr = (String) context.get(ROW_TYPE_KEY);
		RowType rowType = RowType.valueOf(rowTypeStr);
		
		Date startDate = (Date) context.get(DATE_RANGE_START_KEY);
		Date endDate = (Date) context.get(DATE_RANGE_END_KEY);
		String city = (String) context.get(CITY_KEY);
		String zipCode = (String) context.get(ZIP_CODE_KEY);
		String county = (String) context.get(COUNTY_KEY);
		String region = (String) context.get(REGION_KEY);
		
		StringBuilder sql = new StringBuilder("select ");
		sql.append("lic.license_type, ");
		switch (rowType) {
			case CITY:
				sql.append("loc.city");
				break;
			case ZIP_CODE:
				sql.append("loc.zip_code");
				break;
			case COUNTY:
				sql.append("loc.county");
				break;
			case REGION:
				sql.append("loc.region");
				break;
		}
		sql.append(", sum(rpt.num_inspections) as num from ");
		
		if (fCat == null) {
			sql.append("rpt_fact_inspection_type");
		} else if (fCat == FindingCategoryType.CITED) {
			sql.append("rpt_fact_c_inspections");
		} else if (fCat == FindingCategoryType.REPEAT_CITED) {
			sql.append("rpt_fact_rc_inspections");
		} else if (fCat == FindingCategoryType.TA) {
			sql.append("rpt_fact_ta_inspections");
		}
		
		sql.append(" rpt, rpt_dim_date dt, rpt_dim_license_type lic, rpt_dim_location loc, rpt_dim_inspection_type itype ");
		sql.append(" where rpt.dim_date_id = dt.id and rpt.dim_license_type_id = lic.id and rpt.dim_location_id = loc.id and rpt.dim_ins_type_id = itype.id ");
		sql.append(" and dt.calendar_date between :startDate and :endDate ");
		if (insType != null) {
			sql.append(" and rpt.dim_ins_type_id = itype.id and itype.inspection_type = :inspectionType ");
		} else {
			sql.append(" and itype.is_primary = 'Y'");
		}
		if (city != null) {
			sql.append(" and loc.city = :city ");
		}
		if (zipCode != null) {
			sql.append(" and loc.zip_code = :zipCode ");
		}
		if (county != null) {
			sql.append(" and loc.county = :county ");
		}
		if (region != null) {
			sql.append(" and loc.region = :region ");
		}
		
		sql.append(" group by lic.license_type, ");
		String secondColumn = null;
		switch (rowType) {
			case CITY:
				secondColumn = "loc.city";
				break;
			case ZIP_CODE:
				secondColumn = "loc.zip_code";
				break;
			case COUNTY:
				secondColumn = "loc.county";
				break;
			case REGION:
				secondColumn = "loc.region";
				break;
		}
		
		sql.append(secondColumn);
		
		Query query = reportRunner.getNativeQuery(sql.toString());
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		if (insType != null) {
			query.setParameter("inspectionType", insType.getValue());
		}
		if (city != null) {
			query.setParameter("city", city);
		}
		if (zipCode != null) {
			query.setParameter("zipCode", zipCode);
		}
		if (county != null) {
			query.setParameter("county", county);
		}
		if (region != null) {
			query.setParameter("region", region);
		}
		
		return (List<Object[]>) query.getResultList();
	}
	
	private FindingCategoryType getFindingCategory(Map<String, Object> context) {
		FindingCategoryType fCat = null;
		String category = (String) context.get(FINDING_CATEGORY_KEY);
		if (StringUtils.isNotBlank(category) && !category.equals("ALL")) {
			fCat = FindingCategoryType.valueOf(category);
		}
		return fCat;
	}
	
	private PickListValue getInspectionType(Map<String, Object> context) {
		PickListValue type = null;
		Long typeId = (Long) context.get(INSPECTION_TYPE_KEY);
		if (typeId != null && typeId.longValue() != -1l) {
			type = pickListService.loadPickListValueById(typeId);
		}
		return type;
	}
	
	public static enum RowType {
		
		CITY("City"),
		ZIP_CODE("Zip Code"),
		COUNTY("County"),
		REGION("Region");
		
		private final String label;
		
		private RowType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}
}