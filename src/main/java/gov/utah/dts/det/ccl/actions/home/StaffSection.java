package gov.utah.dts.det.ccl.actions.home;

import gov.utah.dts.det.ccl.model.Person;

import java.util.ArrayList;
import java.util.List;

public class StaffSection {

	private String sectionName;
	private List<PersonWrapper> people = new ArrayList<PersonWrapper>();
	
	public StaffSection() {
		
	}
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public List<PersonWrapper> getPeople() {
		return people;
	}
	
	public void addPerson(Person person) {
		people.add(new PersonWrapper(person));
	}
	
	public static class PersonWrapper {
		
		private Long id;
		private String firstName;
		private String lastName;
		
		public PersonWrapper(Person person) {
			this.id = person.getId();
			this.firstName = person.getFirstName();
			this.lastName = person.getLastName();
		}
		
		public Long getId() {
			return id;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
	}
}