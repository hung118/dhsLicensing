package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;

public class MetadataTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(MetadataTag.class);
	
	private String dataSource;

	public MetadataTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "metadata";
	}
	
	public String getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In metadata tag");
		if (templateContext.isInMetadataSection()) {
			throw new TemplateException("Cannot nest metadata tag within another metadata tag.");
		}
		setRenderingAttributes();
		templateContext.setInMetadataSection(true);
		templateContext.getTagStack().push(this);
	}
	
	@Override
	public void doEnd() throws TemplateException {
		templateContext.setInMetadataSection(false);
		if (templateContext.isScanPhase()) {
			templateContext.setScanPhase(false);
		} else {
			//if we are rendering we don't want this section included in the render
			XTextRange start = (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY);
			XTextRange end = (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY);
			
			//expand the range to cover the start to the end
			XTextCursor cursor = (XTextCursor) UnoRuntime.queryInterface(XTextCursor.class, start);
			cursor.gotoRange(end, true);
			
			if (cursor.goRight((short) 1, true)) {
				String text = cursor.getString();
				if (!Character.isWhitespace(text.charAt(text.length() - 1))) {
					cursor.goLeft((short) 1, true); 
				}
			}
			
			//remove the range
			cursor.setString(null);
		}
		
		templateContext.getTagStack().pop();
	}
}