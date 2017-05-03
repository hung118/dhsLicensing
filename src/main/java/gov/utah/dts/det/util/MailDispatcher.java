package gov.utah.dts.det.util;

import gov.utah.dts.det.ccl.model.EMessage;
import gov.utah.dts.det.ccl.model.EmailAttachment;
import gov.utah.dts.det.service.ApplicationService;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Bills, Byron
 */
public class MailDispatcher {
    
	private static final Logger log = LoggerFactory.getLogger(MailDispatcher.class);

	private static final String KEY_SMTP_SERVER = "mail.smtp-server";
	private static final String KEY_DEFAULT_FROM_EMAIL = "mail.default-from-email";
	private static final String HEADER_SMTP_SERVER = "mail.smtp.host";
	private static final String DEFAULT_SMTP_SERVER = "send.state.ut.us";
	private static final String DEFAULT_FROM_EMAIL = "dhslicensing@utah.gov";

	private static Session session = getSession();

	@Autowired
	private static ApplicationService appService;
	
    /**
     * Send an email message using the system default from email address.
     * @param message The message to send.
     */
	public static void send(EMessage message) {
		send(null, message);
	}

    /**
     * Send email message to the given recipient using the specified from email address.
     * @param from The email address to use as the originating from address.
     * @param message The message to send.
     */
    private static void send(String from, EMessage message) {
		if (session != null) {
	        try {
				if (StringUtils.isBlank(from)) {
					from = getDefaultSender();
				}
	    		if (StringUtils.isNotBlank(from)) {
		            MimeMessage mm = new MimeMessage(session);
		            mm.setFrom(new InternetAddress(from));
		            if (message.getRecipients() != null && message.getRecipients().size() > 0) {
		            	// Add email recipients
		            	for (String email : message.getRecipients()) {
		            		mm.addRecipient(RecipientType.TO, new InternetAddress(email));
		            	}
			            
			            // Add Email Carbon Copy recipients
			            for (String cc : message.getCcs()) {
			            	mm.addRecipient(RecipientType.CC, new InternetAddress(cc));
			            }

			            // Add Email Blind Copy recipients
			            for (String bcc : message.getBccs()) {
			            	mm.addRecipient(RecipientType.BCC,new InternetAddress(bcc));
			            }

			            // Set the email subject
			            mm.setSubject(message.getSubject());

			            // Create a multi-part content
			        	MimeMultipart multipart = new MimeMultipart("related");

			        	// Add the message html to the multi-part content
			        	BodyPart messageBodyPart = new MimeBodyPart();
		            	messageBodyPart.setContent(message.toHtml(), "text/html");
		            	multipart.addBodyPart(messageBodyPart);

		            	// Add the image to the multi-part content
		            	if (message.getHeaderImage() != null) {
		            		messageBodyPart = new MimeBodyPart();
		            		messageBodyPart.setDataHandler(new DataHandler(message.getHeaderImage()));
		            		messageBodyPart.setHeader("Content-ID", "<headerImage>");
		            		multipart.addBodyPart(messageBodyPart);
		            	}
		            	
		            	// Add any attachments to the multi-part content
		            	if (message.getAttachments() != null && message.getAttachments().size() > 0) {
		            		for (EmailAttachment attachment : message.getAttachments()) {
		            			messageBodyPart = new MimeBodyPart();
		            			if (StringUtils.isNotBlank(attachment.getContentType())) {
			            			messageBodyPart.setContent(attachment.getSource(), attachment.getContentType());
			            			messageBodyPart.setFileName(attachment.getFilename());
		            			} else {
			            			messageBodyPart.setDataHandler(new DataHandler(attachment.getSource()));
			            			messageBodyPart.setFileName(attachment.getFilename());
		            			}
		            			multipart.addBodyPart(messageBodyPart);
		            		}
		            	}
		            	
		            	// Add the multi-part content to message
			            mm.setContent(multipart);
			            mm.saveChanges();
			            Transport.send(mm);
		            }
	    		}
	        } catch(Exception e) {
	        	log.error(e.getMessage());
	        }
    	}
    }

    /**
     * Generates a mailx session to be used for sending messages.
     */
    private static Session getSession() {
    	Properties props = new Properties();
    	String server = null;
    	try {
    		server = appService.getApplicationPropertyValue(KEY_SMTP_SERVER);
    	} catch (Exception e) {
    		// Caught in case database is having issues or if testing standalone
    	}
		if (StringUtils.isNotBlank(server)) {
			props.setProperty(HEADER_SMTP_SERVER, server);
		} else {
			props.setProperty(HEADER_SMTP_SERVER, DEFAULT_SMTP_SERVER);
		}
		// Instantiate a new mailx session
		return Session.getDefaultInstance(props, null);
    }

    /**
     * Sets the system default from email address.
     * @return The default system from email address.
     */
    private static String getDefaultSender() {
		String from = null;
		try {
			from = appService.getApplicationPropertyValue(KEY_DEFAULT_FROM_EMAIL);
		} catch (Exception e) {
			// Caught in case database is having issues or if testing standalone
		}
		if (!StringUtils.isNotBlank(from)) {
			from = DEFAULT_FROM_EMAIL;
		}
		return from;
    }
}
