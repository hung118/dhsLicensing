package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.PersonalName;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class EmbeddablePerson {

	@Column(name = "ID")
	private Long id;
	
	@Embedded
	private PersonalName name;
	
	public EmbeddablePerson() {
		
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
		return getFirstAndLastName();
	}
}