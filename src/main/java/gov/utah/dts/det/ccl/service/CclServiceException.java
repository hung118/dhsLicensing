package gov.utah.dts.det.ccl.service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CclServiceException extends RuntimeException {
	
	private List<String> errors = new ArrayList<String>();

	public CclServiceException() {
		super(); 
	}

	public CclServiceException(String message, String error) {
		super(message);
		errors.add(error);
	}

	public CclServiceException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}
	
	public CclServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CclServiceException(String message, Throwable cause, List<String> errors) {
		super(message, cause);
		this.errors = errors;
	}

	public CclServiceException(Throwable cause, List<String> errors) {
		super(cause);
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
}