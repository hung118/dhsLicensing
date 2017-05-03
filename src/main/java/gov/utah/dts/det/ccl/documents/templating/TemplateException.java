package gov.utah.dts.det.ccl.documents.templating;

import gov.utah.dts.det.ccl.documents.DocumentException;

@SuppressWarnings("serial")
public class TemplateException extends DocumentException {

	public TemplateException() {
		super();
	}
	
	public TemplateException(String message) {
		super(message);
	}
	
	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TemplateException(Throwable cause) {
		super(cause);
	}
}