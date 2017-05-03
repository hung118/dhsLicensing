package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.UserDao;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.LogService;
import gov.utah.dts.det.ccl.service.UserSearchCriteria;
import gov.utah.dts.det.ccl.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserDao userDao;
	
	@Autowired (required = false)
	private LogService logService;
	
	@Override
	public User loadById(Long id) {
		return userDao.load(id);
	}
	
	@Override
	public User loadUserByPersonId(Long personId) {
		return userDao.loadByPersonId(personId);
	}
	
	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}
	
	@Override
	public void activateUser(User user) {
		user.setActive(true);
		userDao.save(user);
		
		if (logService != null) {
			logService.writeLog(SecurityUtil.getUser().getPerson().getId().toString(), "RE", "SERVER", 
					"User " + user.getUsername() + " activated by user " + SecurityUtil.getUser().getUsername(), null);
		} else {
			log.debug("UserServiceImpl: logService not configured!");
		}
	}
	
	@Override
	public void deactivateUser(User user) {
		user.setActive(false);
		userDao.save(user);
		
		if (logService != null) {
			logService.writeLog(SecurityUtil.getUser().getPerson().getId().toString(), "RE", "SERVER", 
					"User " + user.getUsername() + " deactivated by user " + SecurityUtil.getUser().getUsername(), null);
		} else {
			log.debug("UserServiceImpl: logService not configured!"); 
		}
	}
	
	@Override
	public User getActiveUserByEmail(String email) {
		return userDao.getActiveUserByEmail(email);
	}
	
	@Override
	public Set<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
	@Override
	public Set<User> getUsersInRole(boolean includeOnlyActive, RoleType... roleTypes) {
		return userDao.getUsersInRole(includeOnlyActive, roleTypes);
	}
	
	@Override
	public List<User> getUsersByPersonName(UserSearchCriteria criteria) {
		return userDao.getUsersByPersonName(criteria);
	}
	
	@Override
	public Set<Person> getPeople(RoleType roleType, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin) {
		List<RoleType> roleTypes = new ArrayList<RoleType>();
		if (roleType != null) {
			roleTypes.add(roleType);
		}
		return getPeople(roleTypes, includeOnlyActive, nameOnly, includeAdditionalJoin);
	}
	
	@Override
	public Set<Person> getPeople(List<RoleType> roleTypes, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin) {
		return userDao.getPeople(roleTypes, includeOnlyActive, nameOnly, includeAdditionalJoin);
	}
	
	@Override
	public void evict(Object entity) {
		userDao.evict(entity);
	}
}