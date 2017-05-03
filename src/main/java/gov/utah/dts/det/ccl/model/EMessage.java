package gov.utah.dts.det.ccl.model;

import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Bills, Byron
 */
public class EMessage {
    private String 			subject;			// Email subject
    private String 			header;				// Text used as email header
    private String			salutation;			// The salutation ('Dear John,')
    private String 			message;			// The message content (may include renderable html tags)
    private boolean 		footer;				// True to include the default 'Please do not reply to this email...' footer when rendering the message html content.
    private DataSource 		headerImage;		// A datasource for adding a header image.
    private String			headerTitle;		// The title string to display when hovering over header image.
    private String			headerUrl;			// The url to use for the header if you wish to link back to a site.
    private List<String>	recipients;			// List of email recipients.
    private List<String>	ccs;				// List of email carbon copy recipients.
    private List<String>	bccs;				// List of email blind carbon copy recipients.
    private List<EmailAttachment> attachments;	//  A list of email attachment datasources.
	
	public EMessage() {
		initLists();
	}
	
	private void initLists() {
		attachments = new ArrayList<EmailAttachment>();
		recipients = new ArrayList<String>();
		ccs = new ArrayList<String>();
		bccs = new ArrayList<String>();
	}

	/**
	 * Add an email attachment.
	 * @param attachment An email attachment object.
	 */
	public void addAttachment(EmailAttachment attachment) {
		if (attachment != null) {
			attachments.add(attachment);
		}
	}

	/**
	 * Add a blind carbon copy recipient.
	 * @param email An email string.
	 */
	public void addBCC(String email) {
		if (email != null) {
			email = email.trim().toLowerCase();
			if (!bccs.contains(email)) {
				bccs.add(email);
			}
		}
	}


	/**
	 * Add a carbon copy recipient.
	 * @param email An email string.
	 */
	public void addCC(String email) {
		if (email != null) {
			email = email.trim().toLowerCase();
			if (!ccs.contains(email)) {
				ccs.add(email);
			}
		}
	}

	/**
	 * Add an email recipient.
	 * @param email An email string.
	 */
	public void addRecipient(String email) {
		if (email != null) {
			email = email.trim().toLowerCase();
			if (!recipients.contains(email)) {
				recipients.add(email);
			}
		}
	}

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	/**
	 * @return A list of blind carbon copy recipient email addresses.
	 */
	public List<String> getBccs() {
		return bccs;
	}

	/**
	 * @return A list of carbon copy recipient email addresses.
	 */
	public List<String> getCcs() {
		return ccs;
	}

	public String getHeader() {
		return header;
	}

	public DataSource getHeaderImage() {
		return headerImage;
	}

	public String getHeaderTitle() {
		return headerTitle;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public String getSalutation() {
		return salutation;
	}

	public String getSubject() {
		return subject;
	}

	public boolean isFooter() {
		return footer;
	}

	/**
	 * Remove an attachment from the attachments list by provided file name.
	 * @param filename The name of the attachment file to remove.
	 */
	public void removeAttachment(String filename) {
		if (filename != null && StringUtils.isNotEmpty(filename) && attachments != null && attachments.size() > 0) {
			for (EmailAttachment attachment: attachments) {
				if (attachment.getFilename().equalsIgnoreCase(filename)) {
					attachments.remove(attachment);
				}
			}
		}
	}

	/**
	 * Remove a recipient email from the blind carbon copy recipients list.
	 * @param email The email address to remove.
	 */
	public void removeBCC(String email) {
		if (StringUtils.isNotBlank(email) && bccs != null && bccs.size() > 0) {
			email = email.trim().toLowerCase();
			if (bccs.contains(email)) {
				bccs.remove(email);
			}
		}
	}

	/**
	 * Remove a recipient email from the carbon copy recipients list.
	 * @param email The email address to remove.
	 */
	public void removeCC(String email) {
		if (StringUtils.isNotBlank(email) && ccs != null && ccs.size() > 0) {
			email = email.trim().toLowerCase();
			if (ccs.contains(email)) {
				ccs.remove(email);
			}
		}
	}

	/**
	 * Remove a recipient email from the recipients list.
	 * @param email The email address to remove.
	 */
	public void removeRecipient(String email) {
		if (StringUtils.isNotBlank(email) && recipients.size() > 0) {
			email = email.trim().toLowerCase();
			if (recipients.contains(email)) {
				recipients.remove(email);
			}
		}
	}

	/**
	 *
	 * @param footer True to include the default 'Please do not reply to this email...' footer when rendering the message html content. (default = false)
	 */
	public void setFooter(boolean footer) {
		this.footer = footer;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setHeaderImage(DataSource headerImage) {
		this.headerImage = headerImage;
	}

	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * @return A rendered html string of the email content.
	 */
	public String toHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=us-ascii\" /></head>");
		sb.append("<body style=\"margin: 4px 8px 1px; font: 12pt Calibri\">");

		if (headerImage != null) {
			sb.append("<h1>");
			if (StringUtils.isNotBlank(headerUrl)) {
				sb.append("<a href=\""+headerUrl+"\" style=\"border:0px; text-decoration: none;\">");
			}
			sb.append("<img src=\"cid:headerImage\"");
			if (StringUtils.isNotBlank(headerTitle)) {
				sb.append(" title=\""+headerTitle+"\"");
			}
			sb.append(" style=\"border:0px;\">");
			if (StringUtils.isNotBlank(headerUrl)) {
				sb.append("</a>");
			}
			sb.append("</h1>");
		}
		
		// Add the email header
		if (StringUtils.isNotBlank(header)) {
			if (StringUtils.isNotBlank(headerUrl)) {
				sb.append("<div style=\"font: 18pt Calibri; font-weight: bold; text-align: center; \">");
				sb.append("<a href=\""+headerUrl+"\" style=\"text-decoration: none;\">");
				sb.append(header);
				sb.append("</a>");
				sb.append("</div>");
			} else {
				sb.append("<h2 style=\"text-align:center;\">");
				sb.append(header);
				sb.append("</h2>");
			}
		}
		
		sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:10px;\">");
		
		// Add the salutation
		if (salutation != null && !"".equals(salutation)) {
			sb.append("<tr><td style=\"padding-bottom:15px;\">");
			sb.append(salutation);
			sb.append("</td></tr>");
		}

		// Add the email message
		if (message != null && !"".equals(message)) {
			sb.append("<tr><td>");
			sb.append(message);
			sb.append("</td></tr>");
		}

		if (footer) {
			sb.append("<tr><td style=\"padding-top:25px;\">");
			sb.append("<font color=\"#999999\">");
			sb.append("Please do not reply to this email.&nbsp;&nbsp;This is an unmonitored address, and replies to this email cannot be responded to or read.");
			sb.append("&nbsp;&nbsp;If you have any questions or comments, please call the Utah Department of Human Services, Office of Licensing at <a href=\"tel:%28801%29%20538-4242\" value=\"+18015384242\" target=\"_blank\">(801) 538-4242</a>.");
			sb.append("</font>");
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

}
