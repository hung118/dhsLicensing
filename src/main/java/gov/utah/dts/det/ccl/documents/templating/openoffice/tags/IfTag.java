package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

import ognl.Ognl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;

public class IfTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(IfTag.class);
	
	private static final String TAG_PATTERN = "<[a-zAz]+(\\s+[a-zA-Z]+\\s?=\\s?(\\u201C|\\u201D|\")[^\\u201C|\\u201D|\"]*(\\u201C|\\u201D|\"))*/?>";
	
	protected String test;
	
	public IfTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "if";
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In if tag: " + test);
		try {
			setRenderingAttributes();
			templateContext.getTagStack().push(this);
			if (!isGeneratingSubTemplate() && evaluating) {
				logger.debug("Evaluating if tag: " + test);
				evaluating = (Boolean) Ognl.getValue(test, templateContext.getExpressionContext(), templateContext.getExpressionRoot());	
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate if tag - test: " + test, e);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		try {
			if (!isGeneratingSubTemplate() && !templateContext.isInMetadataSection()) {
				if (evaluating) {
					logger.debug("Expression: " + test + " evaluated to true.  Keeping section");
					
					//hack because out tags were attaching to the if tag when replaced
					XTextRange startRange = (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY);
					String ifStartText = startRange.getString();
					ifStartText = ifStartText.replaceFirst(TAG_PATTERN, "");
					
					((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY)).setString(ifStartText);
//					((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY)).setString(null);
					
					XTextCursor cursor = (XTextCursor) UnoRuntime.queryInterface(XTextCursor.class, (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY));
					if (cursor.goRight((short) 1, true)) {
						String text = cursor.getString();
						if (!Character.isWhitespace(text.charAt(text.length() - 1))) {
							cursor.goLeft((short) 1, true); 
						}
					}
					
					cursor.setString(null);
				} else {
					logger.debug("Expression: " + test + " evaluated to false.  Removing section");
					XTextCursor cursor = (XTextCursor) UnoRuntime.queryInterface(XTextCursor.class, (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY));
					cursor.gotoRange((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY), true);
					
					if (cursor.goRight((short) 1, true)) {
						String text = cursor.getString();
						if (!Character.isWhitespace(text.charAt(text.length() - 1))) {
							cursor.goLeft((short) 1, true); 
						}
					}
					
					cursor.setString(null);
				}
			}
			templateContext.getTagStack().pop();
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate if tag - test: " + test, e);
		}
	}
}