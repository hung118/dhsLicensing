package gov.utah.dts.det.ccl.view.admin;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.PickListValuePersonRelationshipType;

public class PersonRelationshipView {

	private Person person;
	private PickListValuePersonRelationshipType type;
	
	public PersonRelationshipView(Person person, PickListValuePersonRelationshipType type) {
		this.person = person;
		this.type = type;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public PickListValuePersonRelationshipType getType() {
		return type;
	}
	
	public void setType(PickListValuePersonRelationshipType type) {
		this.type = type;
	}
}