package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;

@Component
@DependsOn("fontLoader")
public class TopFindingsReport extends AbstractDimensionReport {
	
	public static final String TEMPLATE_KEY = "top-findings";
	
	private static final Rectangle PAGE_SIZE = PageSize.LETTER;
	
	private static final String REPORT_TYPE_KEY = "rptType";
	
	private static final Map<String, String> REPORT_TYPE_OPTIONS = new HashMap<String, String>();
	
	static {
		for (ReportType type : ReportType.values()) {
			REPORT_TYPE_OPTIONS.put(type.name(), type.getLabel());
		}
	}
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return "Top Findings " + sdf.format(new Date()) + ".pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		List<Input> inputs = new ArrayList<Input>();
		
		inputs.add(getStartDateInput());
		inputs.add(getEndDateInput());
		inputs.add(getLicenseTypeInput(true));
		inputs.add(new Input(REPORT_TYPE_KEY, "Report Type", ReportType.FINDINGS.name(), String.class, true, REPORT_TYPE_OPTIONS, InputDisplayType.SELECT));
		
		return inputs;
	}
	
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor fileDescriptor) throws TemplateException {
		List<Object[]> results = getResults(context);
		
		setFileName(context, fileDescriptor);
		
		Document document = new Document(PAGE_SIZE, 36, 36, 36, 36);
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			
			StringBuilder sb = new StringBuilder((String) context.get(LICENSE_TYPE_KEY));
			sb.append(" Top Findings Report");
			Paragraph heading = new Paragraph(sb.toString(), HEADING_FONT);
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			
			Date startDate = (Date) context.get(DATE_RANGE_START_KEY);
			Date endDate = (Date) context.get(DATE_RANGE_END_KEY);
			ReportType reportType = ReportType.valueOf((String) context.get(REPORT_TYPE_KEY));
			
			clearStringBuilder(sb);
			sb.append(DATE_FORMATTER.format(startDate));
			sb.append(" - ");
			sb.append(DATE_FORMATTER.format(endDate));
			
			Paragraph date = new Paragraph(sb.toString(), FONT);
			date.setAlignment(Element.ALIGN_RIGHT);
			document.add(date);
			
			PdfPTable table = new PdfPTable(2);
			table.setHeaderRows(1);
			table.setSpacingBefore(FONT_SIZE);
			table.setWidthPercentage(100f);
			table.setWidths(new float[] {80f, 20f});
			setDefaultCellAttributes(table.getDefaultCell());
			
			table.addCell(getHeaderCell("Rule Number"));
			table.addCell(getHeaderCell(reportType.getLabel()));
			
			double total = 0;
			for (Iterator<Object[]> itr = results.iterator(); itr.hasNext();) {
				Object[] row = itr.next();
				Paragraph p = new Paragraph();
				p.add(new Paragraph((String) row[0], TABLE_HEADER_FONT));
				p.add(new Paragraph((String) row[1], FONT));
				table.addCell(p);
				double rowTotal = ((BigDecimal) row[2]).doubleValue();
				total += rowTotal;
				if (reportType == ReportType.CMP_AMOUNT) {
					PdfPCell cell = getCurrencyCell(CURRENCY_FORMATTER.format(rowTotal));
					table.addCell(cell);
				} else {
					table.addCell(getNumberCell(Integer.toString((int) rowTotal)));
				}
			}
			
			
			PdfPCell totCell = getHeaderCell("Total");
			totCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(totCell);
			
			if (reportType == ReportType.CMP_AMOUNT) {
				PdfPCell cell = getHeaderCell(CURRENCY_FORMATTER.format(total));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
			} else {
				PdfPCell cell = getHeaderCell(Integer.toString((int) total));
				table.addCell(cell);
			}
			
			document.add(table);
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
		document.close();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getResults(Map<String, Object> context) {
		Date startDate = (Date) context.get(DATE_RANGE_START_KEY);
		Date endDate = (Date) context.get(DATE_RANGE_END_KEY);
		String licenseType = (String) context.get(LICENSE_TYPE_KEY);
		ReportType reportType = ReportType.valueOf((String) context.get(REPORT_TYPE_KEY));
		
		StringBuilder sql = new StringBuilder("select * from (select * from (select ");
		sql.append("rule.rule_number, rv.description, sum(rpt.");
		
		String column = null;
		
		switch (reportType) {
			case FINDINGS:
				column = "num_violations";
				break;
			case CMP_AMOUNT:
				column = "cmp_amount";
				break;
			case CMPS_ASSESSED:
				column = "cmps_issued";
				break;
		}
		
		sql.append(column);
		sql.append(") as total from rpt_fact_rule_violations rpt, rpt_dim_date dt, rpt_dim_license_type lic, rpt_dim_rule rule, rule_view rv " +
				" where rpt.dim_date_id = dt.id and rpt.dim_rule_id = rule.id and rpt.dim_license_type_id = lic.id and rule.rule_number = rv.rule_number " +
				" and dt.calendar_date between :startDate and :endDate and lic.license_type = :licenseType group by rule.rule_number, rv.description) order by " +
				" total desc ) where rownum <= 10 ");
		
		Query query = reportRunner.getNativeQuery(sql.toString());
		query.setParameter("licenseType", licenseType);
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		
		return (List<Object[]>) query.getResultList();
	}
	
	public static enum ReportType {
		
		FINDINGS("Findings"), CMP_AMOUNT("CMP Amount"), CMPS_ASSESSED("CMPs Assessed");
		
		private final String label;
		
		private ReportType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
	}
}