package gov.utah.dts.det.ccl.documents;

@SuppressWarnings("serial")
public class DocumentException extends RuntimeException {

	public DocumentException() {
		super();
	}
	
	public DocumentException(String message) {
		super(message);
	}
	
	public DocumentException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DocumentException(Throwable cause) {
		super(cause);
	}
}