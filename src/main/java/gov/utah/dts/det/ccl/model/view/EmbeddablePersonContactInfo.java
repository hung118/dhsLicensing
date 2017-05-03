package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.PersonalName;
import gov.utah.dts.det.ccl.model.Phone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class EmbeddablePersonContactInfo {

	@Column(name = "ID")
	private Long id;
	
	@Embedded
	private PersonalName name;
	
	@Embedded
	private Phone workPhone;
	
	@Column(name = "EMAIL")
	private String email;
	
	public EmbeddablePersonContactInfo() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public PersonalName getName() {
		return name;
	}
	
	public void setName(PersonalName name) {
		this.name = name;
	}
	
	public Phone getWorkPhone() {
		return workPhone;
	}
	
	public void setWorkPhone(Phone workPhone) {
		this.workPhone = workPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return name.getFirstName();
	}
	
	public String getMiddleName() {
		return name.getMiddleName();
	}
	
	public String getLastName() {
		return name.getLastName();
	}
	
	public String getFirstAndLastName() {
		return name.getFirstAndLastName();
	}
	
	public String getFullName() {
		return name.getFullName();
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
}