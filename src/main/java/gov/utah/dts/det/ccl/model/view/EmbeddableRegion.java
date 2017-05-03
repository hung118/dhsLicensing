package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Phone;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class EmbeddableRegion implements Serializable {

	@Column(name = "REGION_ID")
	private Long id;
	
	@Column(name = "REGION_NAME")
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "addressOne", column = @Column(name = "REGION_ADDRESS_ONE")),
		@AttributeOverride(name = "addressTwo", column = @Column(name = "REGION_ADDRESS_TWO")),
		@AttributeOverride(name = "city", column = @Column(name = "REGION_CITY")),
		@AttributeOverride(name = "state", column = @Column(name = "REGION_STATE")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "REGION_ZIPCODE")),
		@AttributeOverride(name = "county", column = @Column(name = "REGION_COUNTY"))
	})
	private EmbeddableAddress address;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "REGION_PHONE"))
	private Phone phone;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "REGION_FAX"))
	private Phone fax;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "REGION_MANAGER_PHONE"))
	private Phone managerPhone;
	
	@Column(name = "REGION_MANAGER_EMAIL")
	private String managerEmail;
	
	public EmbeddableRegion() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmbeddableAddress getAddress() {
		return address;
	}

	public void setAddress(EmbeddableAddress address) {
		this.address = address;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Phone getFax() {
		return fax;
	}

	public void setFax(Phone fax) {
		this.fax = fax;
	}

	public Phone getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(Phone managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
}