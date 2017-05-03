package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.PersonDao;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;
	
	@Override
	public Person getPerson(Long id) {
		return personDao.load(id);
	}
	
	@Override
	public Person savePerson(Person person) {
		return personDao.save(person);
	}
}