package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

public interface TemplateTag {
	
	public String getType();
	
	public boolean isEvaluating();
	
	public boolean isGeneratingSubTemplate();
	
	public TemplateTag tagGeneratingSubTemplate();
	
	public void doStart() throws TemplateException;
	
	public void doEnd() throws TemplateException;
	
	public TemplateContext getTemplateContext();
	
	public Map<String, Object> getTagContext();
}