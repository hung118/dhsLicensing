package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class for attachments to InspectionChecklist - one-to-one relationship.
 * 
 * @author CKSmith
 *
 */
@Entity
@Table(name="ATTACHMENT")
public class Attachment extends AbstractBaseEntity<Long> implements Serializable, Comparable<Attachment> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTACHMENT_SEQ")
	@SequenceGenerator(name="ATTACHMENT_SEQ", sequenceName="ATTACHMENT_SEQ")
	private Long id;
	
	@Column(name = "BINARYDATA")
	private byte[] binaryData;
	
	@Column(name = "BINARYPDFDATA")
	private byte[] binaryPdfData;
	
	@Column(name = "FILENAME")
	private String fileName;
	
	@Column(name = "FILESIZE")
	private long fileSize;

	@Column(name = "FILETYPE")
	private String fileType;

	@Column(name = "CREATIONDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "MODIFIEDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "PDFFILESIZE")
	private long pdfFileSize;

	@Override
	public int compareTo(Attachment o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long getPk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPk(Long pk) {
		// TODO Auto-generated method stub
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public byte[] getBinaryPdfData() {
		return binaryPdfData;
	}

	public void setBinaryPdfData(byte[] binaryPdfData) {
		this.binaryPdfData = binaryPdfData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getPdfFileSize() {
		return pdfFileSize;
	}

	public void setPdfFileSize(long pdfFileSize) {
		this.pdfFileSize = pdfFileSize;
	}



}
