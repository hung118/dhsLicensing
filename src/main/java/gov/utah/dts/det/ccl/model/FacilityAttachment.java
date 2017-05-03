package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class for attachments of FACILITY entity, many-to-one relationship.
 * 
 * @author Hnguyen
 *
 */
@Entity
@Table(name="FACILITY_ATTACHMENT")
public class FacilityAttachment extends AbstractBaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERSON_SEQ")
	@SequenceGenerator(name="PERSON_SEQ", sequenceName="PERSON_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FACILITY_ID")
	private Facility facility;
	
	@Column(name = "ATTACHMENT_TYPE")
	private String attachmentType;
	
	private byte[] attachment;
	
	@Column(name = "ATTACHMENT_FILE_NAME")
	private String attachmentFileName;
	
	@Column(name = "ATTACHMENT_CONTENT_TYPE")
	private String attachmentContentType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private PickListValue category;
	
	@Column(name = "COMMENT_TEXT")
	private String commentText;
	
	@Column(name = "INSERT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertTimestamp;
	
	@Override
	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public PickListValue getCategory() {
		return category;
	}

	public void setCategory(PickListValue category) {
		this.category = category;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Date getInsertTimestamp() {
		return insertTimestamp;
	}

	public void setInsertTimestamp(Date insertTimestamp) {
		this.insertTimestamp = insertTimestamp;
	}

}
