package gov.utah.dts.det.ccl.model;

import java.util.HashSet;
import java.util.Set;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.struts2.result.PickListValueValueSerializer;
import gov.utah.dts.det.model.AbstractBaseEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@SuppressWarnings("serial")
@Entity
@Table(name = "DOCUMENT")
public class Document extends AbstractBaseEntity<Long> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "DOCUMENT_SEQ")
	@SequenceGenerator(name = "DOCUMENT_SEQ", sequenceName = "DOCUMENT_SEQ")
	private Long id;
	
	@Column(name = "SORT_ORDER")
	private Double sortOrder;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY")
	private PickListValue category;
	
	@ManyToOne
	@JoinColumn(name = "CONTEXT_ID")
	private PickListValue context;
	
	//@Column(name = "TEMPLATE_NAME")
	@Transient
	private String templateName;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "DOCUMENT_ACCESS", joinColumns = @JoinColumn(name = "DOCUMENT_ID"))
	@Column(name = "ROLE_NAME")
	@Enumerated(EnumType.STRING)
	private Set<RoleType> roles = new HashSet<RoleType>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "DOCUMENT_FILESYSTEM_FILE",
		joinColumns = {
			@JoinColumn(name = "DOCUMENT_ID")
		}, inverseJoinColumns = {
			@JoinColumn(name = "FILESYSTEM_FILE_ID")
		}
	)
	private FilesystemFile file;
	
	public Document() {
		
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
	
	public Double getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@JsonSerialize(using = PickListValueValueSerializer.class)
	public PickListValue getCategory() {
		return category;
	}
	
	public void setCategory(PickListValue category) {
		this.category = category;
	}
	
	@JsonSerialize(using = PickListValueValueSerializer.class)
	public PickListValue getContext() {
		return context;
	}
	
	public void setContext(PickListValue context) {
		this.context = context;
	}
	
	@JsonIgnore
	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonIgnore
	public Set<RoleType> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<RoleType> roles) {
		this.roles = roles;
	}
	
	@JsonIgnore
	public FilesystemFile getFile() {
		return file;
	}
	
	public void setFile(FilesystemFile file) {
		this.file = file;
	}
}