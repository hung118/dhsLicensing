package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Secured;

import java.util.Collection;

public interface SecurityService {

	public void setEditableFlag(Collection<? extends Secured> secured);
	
	public void setEditableFlag(Secured secured);
	
	public boolean isPersonAccessibleByCurrentPerson(Long personId);
	
	public Person getSystemPerson();
	
	public boolean isManager(Long userId, Long managerId);
	
	public String hashValue(String value);
}