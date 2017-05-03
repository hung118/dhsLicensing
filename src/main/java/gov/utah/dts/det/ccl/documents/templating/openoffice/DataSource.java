package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;

import java.util.List;

public interface DataSource {
	
	public static final String DS_PREFIX = "ds";
	
	public String getDataSourceKey();
	
	public List<Input> getInputs(TemplateContext templateContext);

	public void initializeDataSource(TemplateContext templateContext) throws TemplateException;
}