package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;

import java.util.List;
import java.util.Set;

public interface UserService {
	
	public User loadById(Long id);
	
	public User loadUserByPersonId(Long personId);
	
	public User saveUser(User user);
	
	public void activateUser(User user);
	
	public void deactivateUser(User user);
	
	public User getActiveUserByEmail(String email);
	
	public Set<User> getAllUsers();
	
	public Set<User> getUsersInRole(boolean includeOnlyActive, RoleType... roleTypes);
	
	public List<User> getUsersByPersonName(UserSearchCriteria criteria);
	
	public Set<Person> getPeople(RoleType roleType, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin);
	
	public Set<Person> getPeople(List<RoleType> roleTypes, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin);
	
	public void evict(final Object entity);
}