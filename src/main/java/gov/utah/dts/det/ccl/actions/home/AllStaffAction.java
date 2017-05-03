package gov.utah.dts.det.ccl.actions.home;

import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.home.alerts.BaseAlertAction;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.view.JsonResponse;
import java.util.*;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class AllStaffAction extends BaseAlertAction implements Preparable {

	private RegionService regionService;
	
	private boolean active = true;
	private JsonResponse response;
	
//	private Set<User> staff;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
	}
	
	public String execute() {
		Set<User> users = userService.getAllUsers();
		List<StaffSection> sections = new ArrayList<StaffSection>();
		
		if (!active) {
			StaffSection section = new StaffSection();
			section.setSectionName("Inactive Users");
			for (User u : users) {
				if (!u.isActive()) {
					section.addPerson(u.getPerson());
				}
			}
			sections.add(section);
		} else {
			//create the necessary sections in the order they should be displayed
			StaffSection adminSect = new StaffSection();
			adminSect.setSectionName("Admins");
			sections.add(adminSect);
			
			Map<Long, User> userMap = new HashMap<Long, User>();
			for (User u : users) {
				if (u.isActive()) {
					userMap.put(u.getPerson().getId(), u);
				}
			}
			
			//load regions
			Set<Region> regions = regionService.getRegions(true);
			for (Region reg : regions) {
				StaffSection section = new StaffSection();
				section.setSectionName(reg.getName());
				addPerson(userMap, reg.getOfficeSpecialist(), section);
				for (Person p : reg.getLicensingSpecialists()) {
					addPerson(userMap, p, section);
				}
				sections.add(section);
			}
			
			//load all other roles
			StaffSection bcuSect = new StaffSection();
			bcuSect.setSectionName("BCU");
			sections.add(bcuSect);
			
			StaffSection otherIntSect = new StaffSection();
			otherIntSect.setSectionName("Other CCL Staff");
			sections.add(otherIntSect);
			
			StaffSection otherSect = new StaffSection();
			otherSect.setSectionName("Other Users");
			sections.add(otherSect);
			
			loadPeople(userMap, bcuSect, adminSect, otherIntSect, otherSect);
		}
		
		Map<String, Object> staff = new HashMap<String, Object>();
		staff.put("active", active);
		staff.put("sections", sections);
		
		response = new JsonResponse(200, staff);
		
		return SUCCESS;
	}
	
	/**
	 * Adds a person to the section only if they are in the user map and then removes them from the user map.
	 * 
	 * @param userMap
	 * @param person
	 * @param section
	 */
	private void addPerson(Map<Long, User> userMap, Person person, StaffSection section) {
		User u = userMap.remove(person.getId());
		if (u != null) {
			section.addPerson(person);
		}
	}
	
	private void loadPeople(Map<Long, User> userMap, StaffSection bcuSection, StaffSection adminSection, StaffSection otherInternalStaff,
			StaffSection other) {
		for (User u : userMap.values()) {
			if (u.getRoles().contains(RoleType.ROLE_SUPER_ADMIN)) {
				adminSection.addPerson(u.getPerson());
			} else if (u.getRoles().contains(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER) ||
					u.getRoles().contains(RoleType.ROLE_BACKGROUND_SCREENING)) {
				bcuSection.addPerson(u.getPerson());
			} else {
				boolean internal = false;
				for (RoleType r : u.getRoles()) {
					if (r.isInternal()) {
						internal = true;
						break;
					}
				}
				
				if (internal) {
					otherInternalStaff.addPerson(u.getPerson());
				} else {
					other.addPerson(u.getPerson());
				}
			}
		}
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}