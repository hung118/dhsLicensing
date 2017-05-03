package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.PersonDao;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.Secured;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.service.ApplicationService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
	
	private static final String SALT = "C$d87h@if910D!v7";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PersonDao personDao;

	@Override
	public void setEditableFlag(Collection<? extends Secured> secured) {
		setFlags(secured);
	}
	
	@Override
	public void setEditableFlag(Secured secured) {
		List<Secured> securedCollection = new ArrayList<Secured>();
		securedCollection.add(secured);
		setFlags(securedCollection);
	}
	
	private void setFlags(Collection<? extends Secured> securedCollection) {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) {
			//if super admin set everything to editable
			for (Secured secured : securedCollection) {
				secured.setEditable(true);
			}
		} else {
			Long personId = SecurityUtil.getUser().getPerson().getId();
			Date today = new Date();
			
			Set<Long> editableIds = getEditableIdsIfNotAdmin();
			for (Secured secured : securedCollection) {
				//if the object is owned by the current user then they only have a set amount of time
				if (personId.longValue() == secured.getOwnerId().longValue()) {
					Date uneditableDate = DateUtils.addDays(today, -secured.getDaysUntilUneditable());
					if (secured.getSecurityDate() == null || secured.getSecurityDate().after(uneditableDate)) {
						//they can edit if there is no security date or that date is after the uneditable date
						secured.setEditable(true);
					} else {
						secured.setEditable(false);
					}
				} else if (editableIds.contains(secured.getOwnerId())) {
					secured.setEditable(true);
				} else {
					secured.setEditable(false);
				}
			}
		}
	}
	
	private Set<Long> getEditableIdsIfNotAdmin() {
		//get the people ids that are editable by the current user if they are not a super admin
		Set<Long> editableIds = new HashSet<Long>();
		//if the user is a background clearance manager they can edit all bcu users
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER)) {
			Set<Person> bcuPeople = userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING, false, true, false);
			for (Person person : bcuPeople) {
				editableIds.add(person.getId());
			}
		}
				
		//the user may not edit super admins that happen to have rights below them (the background clearance manager should not
		//be able to edit a super admin that is also set up as a background clearance user).
		Set<Person> superAdmins = userService.getPeople(RoleType.ROLE_SUPER_ADMIN, true, true, false);
		//remove all active super admins that happen to be in the set of editable ids
		for (Person p : superAdmins) {
			editableIds.remove(p.getId());
		}
		return editableIds;
	}
	
	@Override
	public boolean isPersonAccessibleByCurrentPerson(Long personId) {
		if (personId == null) {
			throw new IllegalArgumentException("Person id is null");
		}
		
		//admins can access any user's information
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		}
		
		//the user is accessing their own information so they have access
		Person personLoggedIn = SecurityUtil.getUser().getPerson();
		if (personLoggedIn.getId().equals(personId)) {
			return true;
		}
		
		//determine if the logged in user is the manager of the user they are requesting
		Set<Long> editableIds = getEditableIdsIfNotAdmin();
		return editableIds.contains(personId);
	}
	
	@Override
	public Person getSystemPerson() {
		ApplicationProperty prop = applicationService.findApplicationPropertyByKey(ApplicationPropertyKey.SYSTEM_PERSON.getKey());
		return personDao.load(new Long(prop.getValue()));
	}
	
	@Override
	public boolean isManager(Long userId, Long managerId) {
		Set<Person> bcus = userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING, true, true, false);
		Set<Person> bcuMgrs = userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER, true, true, false);
		for (Iterator<Person> itr = bcuMgrs.iterator(); itr.hasNext();) {
			Person mgr = itr.next();
			if (mgr.getId().equals(managerId)) {
				for (Iterator<Person> itr1 = bcus.iterator(); itr1.hasNext();) {
					Person user = itr1.next();
					if (user.getId().equals(userId)) {
						return true;
					}
				}
			}
		}
		
		Set<Region> regions = regionService.getRegions(true);
		for (Region r : regions) {
			if (r.getOfficeSpecialist().getId().equals(userId)) {
				return true;
			}
			for (Person p : r.getLicensingSpecialists()) {
				if (p.getId().equals(userId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String hashValue(String value) {
		String salted = value + SALT;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException("Unable to hash value", nsae);
		}
		md.update(salted.getBytes());
		byte[] digest = md.digest();
		return new String(Hex.encodeHex(digest));
	}
	
	/*public static void main(String[] args) {
		SecurityServiceImpl impl = new SecurityServiceImpl();
		try {
			System.out.println(impl.hashValue("1234"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}