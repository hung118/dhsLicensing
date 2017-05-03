package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Person;

public interface PersonService {

	public Person getPerson(Long id);
	
	public Person savePerson(Person person);
}