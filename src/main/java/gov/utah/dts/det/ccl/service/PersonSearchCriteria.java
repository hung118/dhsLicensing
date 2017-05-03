package gov.utah.dts.det.ccl.service;

public class PersonSearchCriteria {

private Long personId;
private String name;
private Boolean active;
	
	public PersonSearchCriteria() {
		
	}
	
	public PersonSearchCriteria(Long personId, String name, Boolean active) {
		this.personId = personId;
		this.name = name;
		this.active = active;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}