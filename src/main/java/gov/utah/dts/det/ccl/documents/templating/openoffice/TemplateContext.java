package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.openoffice.tags.TemplateTag;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateContext {
	
	private OpenOfficeTemplateRenderer template;
	private Map<String, Object> expressionContext;
	private Object expressionRoot;
	private Deque<TemplateTag> tagStack = new ArrayDeque<TemplateTag>();
	private List<Input> inputs = new ArrayList<Input>();
	private Map<String, String> metadata = new HashMap<String, String>();
	
	/**
	 * if true we are scanning for input tags, otherwise we are rendering the document
	 */
	private boolean scanPhase = true;
	
	/**
	 * if we are in the metadata section.  Tags that should only go in the metadata section can throw an exception if they are placed outside that section.
	 */
	private boolean inMetadataSection = false;
	
	public TemplateContext(OpenOfficeTemplateRenderer template, Map<String, Object> expressionContext, Object expressionRoot) {
		this.template = template;
		this.expressionContext = (expressionContext == null ? new HashMap<String, Object>() : expressionContext);
		this.expressionRoot = expressionRoot;
	}
	
	public OpenOfficeTemplateRenderer getTemplate() {
		return template;
	}
	
	public void setTemplate(OpenOfficeTemplateRenderer template) {
		this.template = template;
	}
	
	public Map<String, Object> getExpressionContext() {
		return expressionContext;
	}
	
	public void setExpressionContext(Map<String, Object> expressionContext) {
		this.expressionContext = expressionContext;
	}
	
	public Object getExpressionRoot() {
		return expressionRoot;
	}
	
	public void setExpressionRoot(Object expressionRoot) {
		this.expressionRoot = expressionRoot;
	}
	
	public Deque<TemplateTag> getTagStack() {
		return tagStack;
	}
	
	public void setTagStack(Deque<TemplateTag> tagStack) {
		this.tagStack = tagStack;
	}
	
	public boolean isScanPhase() {
		return scanPhase;
	}
	
	public void setScanPhase(boolean scanPhase) {
		this.scanPhase = scanPhase;
	}
	
	public boolean isInMetadataSection() {
		return inMetadataSection;
	}
	
	public void setInMetadataSection(boolean inMetadataSection) {
		this.inMetadataSection = inMetadataSection;
	}
	
	public List<Input> getInputs() {
		return inputs;
	}
	
	public Map<String, String> getMetadata() {
		return metadata;
	}
}