package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.filemanager.model.FileType;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "FS_FILE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FilesystemFile extends AbstractBaseEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FS_FILE_SEQ")
	@SequenceGenerator(name = "FS_FILE_SEQ", sequenceName = "FS_FILE_SEQ")
	private Long id;
	
	@Column(name = "FILE_PATH")
	private String filePath;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "FILE_TYPE")
	@Enumerated(EnumType.STRING)
	private FileType fileType;
	
	@Column(name = "FILE_SIZE")
	private Long fileSize;
	
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private PickListValue type;
	
	public FilesystemFile() {
		
	}
	
	@JsonIgnore
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

	@JsonIgnore
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
	public Long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@JsonIgnore
	public PickListValue getType() {
		return type;
	}
	
	public void setType(PickListValue type) {
		this.type = type;
	}
}