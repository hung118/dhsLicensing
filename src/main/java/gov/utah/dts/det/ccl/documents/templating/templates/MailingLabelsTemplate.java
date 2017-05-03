package gov.utah.dts.det.ccl.documents.templating.templates;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.AbstractTemplate;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.view.MailingLabel;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;

@Component
@DependsOn("fontLoader")
public class MailingLabelsTemplate extends AbstractTemplate {
	
	public static final String TEMPLATE_KEY = "mailing-labels";
	public static final String MAILING_LABELS_KEY = "tmpl.mailing-labels";

	//cells - 189 pt x 72 pt
	//left margin - 13.55 pt
	//top margin - 36 pt
	//horizontal pitch (width of label + space between columns) 198 pt (9 pts between columns)
	
	private static final String FONT_NAME = "Arial";
	
	private static final int ROWS = 10;
	private static final int COLUMNS = 3;
	private static final float LABEL_FONT_SIZE = 10f;
	private static final float LABEL_WIDTH = 189f;
	private static final float LABEL_HEIGHT = 72f;
	private static final float COLUMN_SPACING = 9f;
	private static final float CELL_PADDING_X = LABEL_FONT_SIZE;
	private static final float CELL_PADDING_Y = 0f;

	private static final float MARGIN_LEFT = 13.55f;//13.55f; adjusting left and right margin to make all the columns the same size
	private static final float MARGIN_RIGHT = MARGIN_LEFT;
	private static final float MARGIN_TOP = 36f;
	private static final float MARGIN_BOTTOM = MARGIN_TOP;
	
	private static final Font LABEL_FONT = FontFactory.getFont(FONT_NAME, LABEL_FONT_SIZE);
	
	@Override
	public String getTemplateKey() {
		return TEMPLATE_KEY;
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return "Mailing Labels.pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		return new ArrayList<Input>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException {
		List<MailingLabel> labels = (List<MailingLabel>) context.get(MAILING_LABELS_KEY);
		if (labels == null) {
			throw new IllegalArgumentException("No addresses found in context.");
		}
		
		setFileName(context, descriptor);
		
		try {
			Document document = new Document(PageSize.LETTER, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			
			document.open();
			buildLabels(document, writer, labels);
			document.close();
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
	}
	
	private void buildLabels(Document document, PdfWriter writer, List<MailingLabel> labels) throws DocumentException {
		//TEST DATA ONLY
//		Address shortAddr = new Address();
//		shortAddr.setAddressOne("6572 Kentucky Dr.");
//		shortAddr.setCity("West Jordan");
//		shortAddr.setState("UT");
//		shortAddr.setZipCode("84084");
//		Address addrTwoAddr = new Address();
//		addrTwoAddr.setAddressOne("44 Medical Dr.");
//		addrTwoAddr.setAddressTwo("Suite 200");
//		addrTwoAddr.setCity("Salt Lake City");
//		addrTwoAddr.setState("UT");
//		addrTwoAddr.setZipCode("84111");
//		Address longAddr = new Address();
//		longAddr.setAddressOne("12500 Highland Dr.");
//		longAddr.setAddressTwo("Suite B");
//		longAddr.setCity("Duck Creek Village");
//		longAddr.setState("UT");
//		longAddr.setZipCode("84762");
//		Address[] addrs = {shortAddr, addrTwoAddr, longAddr};
//		Random random = new Random();
//		for (int i = 0; i < 30; i++) {
//			String name = "Placeholder Name " + (i + 1);
//			if (random.nextBoolean()) {
//				name = name + " dba a really long provider name";
//			}
//			MailingLabelView labelView = new MailingLabelView(name, addrs[random.nextInt(3)]);
//			mlv.add(labelView);
//		}
		//END TEST DATA
		
		//*/
		ColumnText ct = new ColumnText(writer.getDirectContent());
		for (int i = 0; i < labels.size(); i++) {
			drawLabel(labels.get(i), document, ct, i);
			ct.setText(null);
		}
		/*/
		PdfContentByte contentByte = writer.getDirectContentUnder();
		for (int i = 0; i < mlv.size(); i++) {
			drawLabelOutlines(i, contentByte, document);
		}
		//*/
	}
	
	private void drawLabel(MailingLabel label, Document document, ColumnText ct, int labelNumber) throws DocumentException {
		if (labelNumber > 0 && labelNumber % (ROWS * COLUMNS) == 0) {
			document.newPage();
		}
		int numberOnPage = labelNumber % (ROWS * COLUMNS);
		int row = numberOnPage / COLUMNS;
		int column = (numberOnPage % COLUMNS);
		float left = document.left() + (column * (LABEL_WIDTH + COLUMN_SPACING)) + CELL_PADDING_X;
		float right = document.left() + (column * COLUMN_SPACING) + ((column + 1) * LABEL_WIDTH) - CELL_PADDING_X;
		float top = document.top() - (row * LABEL_HEIGHT) - CELL_PADDING_Y;
		float bottom = document.top() - (((row + 1) * LABEL_HEIGHT)) + CELL_PADDING_Y;
		ct.setSimpleColumn(left, bottom, right, top);
		setColumnText(ct, label);
		int status = ct.go(true); //simulate the write to make sure everything fits
		if (status > 1) {
			//not everything fit.  Truncate something.
			System.out.println("Label didn't fit! " + label.getAddress());
		}
		
		//write the content for real
		ct.setSimpleColumn(left, bottom, right, top);
		ct.setText(null);
		setColumnText(ct, label);
		ct.go();
	}
	
	private void setColumnText(ColumnText ct, MailingLabel label) {
		try {
			addParagraph(ct, label.getName(), true);
			if (label.getAddress() != null) {
				addParagraph(ct, label.getAddress().getAddressOne(), false);
				if (StringUtils.isNotEmpty(label.getAddress().getAddressTwo())) {
					addParagraph(ct, label.getAddress().getAddressTwo(), false);
				}
				addParagraph(ct, label.getAddress().getCity() + ", " + label.getAddress().getState() + " " + label.getAddress().getZipCode(), false);
			}
		} catch (IllegalArgumentException iae) {
			ct.setText(null);
			ct.addElement(new Paragraph("Label for " + label.getName() + " could not be printed: The address is too long", LABEL_FONT));
		}
	}
	
	private void addParagraph(ColumnText ct, String text, boolean truncate) throws IllegalArgumentException {
		Paragraph p = new Paragraph(text, LABEL_FONT);
		p.setAlignment(Element.ALIGN_LEFT);
		float width = ColumnText.getWidth(p);
		float maxWidth = LABEL_WIDTH - (CELL_PADDING_X * 2);
		if (width < maxWidth) { //element will fit
			ct.addElement(p);
			return;
		} else if (truncate) { //element is too long but can be truncated
//			System.out.println("Truncating: " + p.getContent());
			StringBuilder sb = new StringBuilder();
			float currentWidth = 0;
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				float charWidth = LABEL_FONT.getBaseFont().getWidthPoint(c, LABEL_FONT_SIZE);
				if (charWidth + currentWidth > maxWidth) {
					break;
				}
				sb.append(c);
				currentWidth += charWidth;
			}
			p = new Paragraph(sb.toString(), LABEL_FONT);
			p.setAlignment(Element.ALIGN_LEFT);
			ct.addElement(p);
			return;
		}
		throw new IllegalArgumentException("String is too long for label: " + text); //element was too long and could not be truncated
	}
}