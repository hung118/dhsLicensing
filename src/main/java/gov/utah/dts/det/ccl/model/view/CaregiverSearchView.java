package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Phone;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "CAREGIVER_SEARCH_VIEW")
@Immutable
public class CaregiverSearchView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "STATUS_ID")
	private Long statusId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TYPE_ID")
	private Long typeId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "PERSON_ID")),
		@AttributeOverride(name = "name.firstName", column = @Column(name = "FIRST_NAME")),
		@AttributeOverride(name = "name.middleName", column = @Column(name = "MIDDLE_NAME")),
		@AttributeOverride(name = "name.lastName", column = @Column(name = "LAST_NAME"))
	})
	private EmbeddablePerson person;
	
	@Column(name = "MAIDEN_NAME")
	private String maidenName;
	
	@Column(name = "BIRTHDAY")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(name = "ALIASES")
	private String aliases;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "addressOne", column = @Column(name = "ADDRESS_ONE")),
		@AttributeOverride(name = "addressTwo", column = @Column(name = "ADDRESS_TWO")),
		@AttributeOverride(name = "city", column = @Column(name = "CITY")),
		@AttributeOverride(name = "state", column = @Column(name = "STATE")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE")),
		@AttributeOverride(name = "county", column = @Column(name = "COUNTY"))
	})
	private EmbeddableAddress address;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "PRIMARY_PHONE"))
	private Phone primaryPhone;
	
	public CaregiverSearchView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public EmbeddablePerson getPerson() {
		return person;
	}
	
	public void setPerson(EmbeddablePerson person) {
		this.person = person;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getAliases() {
		return aliases;
	}
	
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public EmbeddableAddress getAddress() {
		return address;
	}

	public void setAddress(EmbeddableAddress address) {
		this.address = address;
	}
	
	public Phone getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
}