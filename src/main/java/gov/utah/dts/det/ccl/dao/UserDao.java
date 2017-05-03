/**
 * 
 */
package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.UserByRegionView;
import gov.utah.dts.det.ccl.service.UserSearchCriteria;
import gov.utah.dts.det.ccl.view.admin.PersonRelationshipView;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;
import java.util.Set;

public interface UserDao extends AbstractBaseDao<User, Long> {
	
	public User loadByPersonId(Long personId);
    
    public User getActiveUserByEmail(String email);
    
    public List<PersonRelationshipView> getAllPeopleForPickListValue(Long pickListValueId);
    
    public Set<User> getAllUsers();
    
    public Set<User> getUsersInRole(boolean includeOnlyActive, RoleType... roleTypes);
	
	public List<User> getUsersByPersonName(UserSearchCriteria criteria);
    
    public Set<Person> getPeople(List<RoleType> roleTypes, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin);
	
	public List<UserByRegionView> getUsersInRegion(Long regionId, boolean excludeManager);
	
	public List<PickListValue> getRegionsManaging(Long personId);
}