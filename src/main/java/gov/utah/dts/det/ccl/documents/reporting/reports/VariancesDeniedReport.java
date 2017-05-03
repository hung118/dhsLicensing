package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Element;

@Component
@DependsOn("fontLoader")
public class VariancesDeniedReport extends AbstractCclReport {

	public static final String TEMPLATE_KEY = "variances-denied";
	
	private static final String RULE_ID_KEY = "ruleId";
	
	private static final Rectangle PAGE_SIZE = PageSize.LETTER;
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return "Variances Denied Report " + sdf.format(new Date()) + ".pdf";
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
		
		StringBuilder sql = new StringBuilder("select f.facilityname, rv.rule_number, rv.description, v.request_date, v.denial_reason " +
				" from variance v, facility f, rule_view rv " +
				" where v.facility_id = f.id and v.rule_id = rv.subsection_id and v.outcome = 'D' and (v.rule_id = :ruleId or v.rule_id in ( " +
				"     select old_rule_id from rule_cross_reference where new_rule_id = :ruleId " +
				"     union select new_rule_id from rule_cross_reference where old_rule_id = :ruleId " +
				" ))" +
				" order by f.facilityname, rv.rule_number ");
		
		Query query = reportRunner.getNativeQuery(sql.toString());
		query.setParameter("ruleId", ruleId);
		
		List<Object[]> results = (List<Object[]>) query.getResultList();

		setFileName(context, descriptor);
		
		Document document = new Document(PAGE_SIZE, MARGIN, MARGIN, MARGIN, MARGIN);
		try {
			PdfWriter.getInstance(document, outputStream);
			document.open();
			
			StringBuilder sb = new StringBuilder();
			sb.append("Variances Denied Report");
			
			Paragraph heading = new Paragraph(sb.toString(), REPORT_HEADING_FONT);
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			
			PdfPTable table = new PdfPTable(4);
			table.setHeaderRows(1);
			table.setSpacingBefore(FONT_SIZE);
			table.setWidths(new float[] {30f, 25f, 12f, 33f});
			table.setWidthPercentage(100f);
			
			table.addCell(getHeaderCell("Facility Name"));
			table.addCell(getHeaderCell("Rule Number"));
			table.addCell(getHeaderCell("Request Date"));
			table.addCell(getHeaderCell("Denial Reason"));
			
			for (Iterator<Object[]> itr = results.iterator(); itr.hasNext();) {
				Object[] row = itr.next();

				table.addCell(getCell(new Phrase((String) row[0], FONT), Element.ALIGN_LEFT));
				
				clearStringBuilder(sb);
				sb.append((String) row[1]);
				if (row[2] != null) {
					sb.append(" - ");
					sb.append((String) row[2]);
				}
				
				table.addCell(getCell(new Phrase(sb.toString(), FONT), Element.ALIGN_LEFT));
				table.addCell(getCell(new Phrase(DATE_FORMATTER.format((Date) row[3]), FONT), Element.ALIGN_LEFT));
				table.addCell(getCell(new Phrase((String) row[4], FONT), Element.ALIGN_LEFT));
			}
			
			document.add(table);
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
		document.close();
	}
}