package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaAttributeTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(MetaAttributeTag.class);
	
	protected String name;
	protected String value;

	public MetaAttributeTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "metaattr";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In meta attribute tag");
		if (!templateContext.isInMetadataSection()) {
			throw new TemplateException("Meta attribute tag cannot be placed outside of the metadata section.");
		}
		setRenderingAttributes();
		if (templateContext.isScanPhase() && evaluating) {
			templateContext.getMetadata().put(name, value);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		
	}
}