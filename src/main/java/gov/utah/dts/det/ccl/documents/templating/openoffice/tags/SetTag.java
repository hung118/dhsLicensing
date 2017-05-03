package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

import ognl.Ognl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.text.XTextRange;

public class SetTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(SetTag.class);
	
	protected String var;
	protected String value;

	public SetTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "set";
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In set tag");
		try {
			setRenderingAttributes();
			if (!isGeneratingSubTemplate() && evaluating) {
				logger.debug("Evaluating set tag: " + var + " value: " + value);
				Object val = Ognl.getValue(value, templateContext.getExpressionContext(), templateContext.getExpressionRoot());
				templateContext.getExpressionContext().put(var, val);
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY)).setString(null);
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate set tag - var: " + var + " value: " + value, e);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		
	}
}