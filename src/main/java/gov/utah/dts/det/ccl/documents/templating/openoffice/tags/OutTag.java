package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.text.MessageFormat;
import java.util.Map;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.text.XTextRange;

public class OutTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(OutTag.class);

	protected String value;
	protected String format;
	
	public OutTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "out";
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In out tag: " + value);
		if (templateContext.isInMetadataSection()) {
			throw new TemplateException("Out tag cannot be placed in the metadata section.");
		}
		try {
			setRenderingAttributes();
			if (!isGeneratingSubTemplate() && !templateContext.isScanPhase() && evaluating) {
				logger.debug("Evaluating out tag: " + value);
				Object result = Ognl.getValue(value, templateContext.getExpressionContext(), templateContext.getExpressionRoot());
				String outputString = null;
				if (result != null) {
					if (StringUtils.isNotBlank(format)) {
						outputString = MessageFormat.format("{" + format + "}", result);
					} else {
						outputString = result.toString();
					}
				}
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY)).setString(outputString);
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate out tag - value: " + value, e);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		
	}
}