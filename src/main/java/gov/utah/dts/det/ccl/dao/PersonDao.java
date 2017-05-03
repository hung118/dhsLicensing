package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.service.PersonSearchCriteria;
import gov.utah.dts.det.dao.AbstractBaseDao;
import java.util.List;

public interface PersonDao extends AbstractBaseDao<Person, Long> {

	public List<Person> getPersonsByCriteria(PersonSearchCriteria criteria);

}