package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;

@Component
@DependsOn("fontLoader")
public class VariancesGrantedReport extends AbstractCclReport {

	public static final String TEMPLATE_KEY = "variances-granted";
	
	private static final String RULE_ID_KEY = "ruleId";
	
	private static final Rectangle PAGE_SIZE = PageSize.LETTER;
	
	private static final Font INACTIVE_FONT = FontFactory.getFont(FONT_NAME, FONT_SIZE, BaseColor.GRAY);
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return "Variances Granted Report " + sdf.format(new Date()) + ".pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		List<Input> inputs = new ArrayList<Input>();
		
		inputs.add(new Input(RULE_ID_KEY, "Rule Number", null, Long.class, true, InputDisplayType.RULE));
		
		return inputs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException {
		Long ruleId = (Long) context.get(RULE_ID_KEY);
		
		StringBuilder sql = new StringBuilder("select f.facilityname, rv.rule_number, rv.description, v.start_date, nvl2(v.revocation_date, v.revocation_date, v.end_date) as end_date" +
				" from variance v, facility f, rule_view rv " +
				" where v.facility_id = f.id and v.rule_id = rv.subsection_id and v.outcome = 'A' and (v.rule_id = :ruleId or v.rule_id in ( " +
				"     select old_rule_id from rule_cross_reference where new_rule_id = :ruleId " +
				"     union select new_rule_id from rule_cross_reference where old_rule_id = :ruleId " +
				" ))" +
				" order by v.start_date desc, f.facilityname ");
		
		Query query = reportRunner.getNativeQuery(sql.toString());
		query.setParameter("ruleId", ruleId);
		
		List<Object[]> results = (List<Object[]>) query.getResultList();

		setFileName(context, descriptor);
		
		Document document = new Document(PAGE_SIZE, 36, 36, 36, 36);
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			
			StringBuilder sb = new StringBuilder();
			sb.append("Variances Granted Report");
			
			Paragraph heading = new Paragraph(sb.toString(), REPORT_HEADING_FONT);
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			
			PdfPTable table = new PdfPTable(4);
			table.setHeaderRows(1);
			table.setSpacingBefore(FONT_SIZE);
			table.setWidths(new float[] {36f, 40f, 12f, 12f});
			table.setWidthPercentage(100f);
			
			table.addCell(getHeaderCell("Facility Name"));
			table.addCell(getHeaderCell("Rule Number"));
			table.addCell(getHeaderCell("Start Date"));
			table.addCell(getHeaderCell("End Date"));
			
			for (Iterator<Object[]> itr = results.iterator(); itr.hasNext();) {
				Object[] row = itr.next();
				Date startDate = (Date) row[3];
				Date endDate = (Date) row[4];

				Date today = DateUtils.truncate(new Date(), Calendar.DATE);
				boolean inactive = startDate.compareTo(today) > 0 || endDate.compareTo(today) < 0;
				
				table.addCell(getCell(new Phrase((String) row[0], inactive ? INACTIVE_FONT : FONT), Element.ALIGN_LEFT));
				
				clearStringBuilder(sb);
				sb.append((String) row[1]);
				if (row[2] != null) {
					sb.append(" - ");
					sb.append((String) row[2]);
				}
				
				table.addCell(getCell(new Phrase(sb.toString(), inactive ? INACTIVE_FONT : FONT), Element.ALIGN_LEFT));
				table.addCell(getCell(new Phrase(DATE_FORMATTER.format(startDate), inactive ? INACTIVE_FONT : FONT), Element.ALIGN_LEFT));
				table.addCell(getCell(new Phrase(DATE_FORMATTER.format(endDate), inactive ? INACTIVE_FONT : FONT), Element.ALIGN_LEFT));
			}
			
			document.add(table);
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
		document.close();
	}
}