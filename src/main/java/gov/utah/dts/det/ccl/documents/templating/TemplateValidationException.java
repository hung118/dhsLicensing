package gov.utah.dts.det.ccl.documents.templating;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class TemplateValidationException extends TemplateException {
	
	private Map<String, String> errors = new LinkedHashMap<String, String>();
	
	public TemplateValidationException(String message, String inputId, String error) {
		super(message);
		errors.put(inputId, error);
	}

	public TemplateValidationException(String message, Map<String, String> errors) {
		super(message);
		this.errors.putAll(errors);
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
}