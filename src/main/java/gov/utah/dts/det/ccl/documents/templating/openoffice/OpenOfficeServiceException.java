package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;

@SuppressWarnings("serial")
public class OpenOfficeServiceException extends TemplateException {

	public OpenOfficeServiceException() {
		super();
	}
	
	public OpenOfficeServiceException(String message) {
		super(message);
	}
	
	public OpenOfficeServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OpenOfficeServiceException(Throwable cause) {
		super(cause);
	}
}