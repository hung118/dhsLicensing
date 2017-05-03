package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(MetaAttributeTag.class);
	
	protected String name;

	public DataSourceTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "datasource";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In data source tag");
		if (!templateContext.isInMetadataSection()) {
			throw new TemplateException("Data source tag cannot be placed outside of the metadata section.");
		}
		setRenderingAttributes();
		DataSource ds = templateContext.getTemplate().getDataSource(name);
		templateContext.getInputs().addAll(ds.getInputs(templateContext));
		ds.initializeDataSource(templateContext);
	}
	
	@Override
	public void doEnd() throws TemplateException {
		
	}
}