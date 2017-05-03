package gov.utah.dts.det.ccl.documents.templating.openoffice;

@SuppressWarnings("serial")
public class OpenOfficeConnectException extends OpenOfficeServiceException {

	public OpenOfficeConnectException() {
		super();
	}
	
	public OpenOfficeConnectException(String message) {
		super(message);
	}
	
	public OpenOfficeConnectException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OpenOfficeConnectException(Throwable cause) {
		super(cause);
	}
}