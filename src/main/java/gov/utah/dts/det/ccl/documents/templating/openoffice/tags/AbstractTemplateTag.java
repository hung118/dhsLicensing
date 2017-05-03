package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.util.Map;

public abstract class AbstractTemplateTag implements TemplateTag {

	protected TemplateContext templateContext;
	protected Map<String, Object> tagContext;
	protected boolean evaluating = true;
	protected TemplateTag tagGeneratingSubTemplate = null;
	
	public AbstractTemplateTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		this.templateContext = templateContext;
		this.tagContext = tagContext;
	}
	
	protected void setRenderingAttributes() {
		TemplateTag parent = templateContext.getTagStack().peek();
		evaluating = parent == null ? true : parent.isEvaluating();
		tagGeneratingSubTemplate = parent == null ? null : parent.tagGeneratingSubTemplate();
	}
	
	@Override
	public TemplateContext getTemplateContext() {
		return templateContext;
	}
	
	@Override
	public Map<String, Object> getTagContext() {
		return tagContext;
	}
	
	@Override
	public boolean isEvaluating() {
		return evaluating;
	}
	
	@Override
	public boolean isGeneratingSubTemplate() {
		return tagGeneratingSubTemplate != null;
	}
	
	@Override
	public TemplateTag tagGeneratingSubTemplate() {
		return tagGeneratingSubTemplate;
	}
}