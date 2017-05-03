package gov.utah.dts.det.ccl.documents;

import java.io.InputStream;

public class FileDescriptor {

	private InputStream inputStream;
	private String contentType;
	private String fileName;
	private String contentLength;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getContentLength() {
		return contentLength;
	}
	
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}