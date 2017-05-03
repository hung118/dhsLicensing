package gov.utah.dts.det.ccl.model;

import javax.activation.DataSource;

public class EmailAttachment {

	private String 		filename;
	private DataSource  source;
	private String		contentType;
	
	/**
	 * Creates an email attachment.
	 */
	public EmailAttachment() {
		
	}
	
	/**
	 * Creates an email attachment.
	 * @param filename The filename of the data source.
	 * @param source The file data source.
	 */
	public EmailAttachment(String filename, DataSource source) {
		this.filename = filename;
		this.source = source;
	}

	/**
	 * Creates an email attachment.
	 * @param filename The filename of the data source.
	 * @param source The file data source.
	 * @param contentType The file content type.
	 */
	public EmailAttachment(String filename, DataSource source, String contentType) {
		this.filename = filename;
		this.source = source;
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public DataSource getSource() {
		return source;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
