package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.dao.DataIntegrityViolationException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/regions")
@Results({
	@Result(name = "input", location = "region_form.jsp"),
	@Result(name = "success", location = "regions-list", type = "redirectAction")
})
public class RegionsAction extends ActionSupport implements Preparable {

	private RegionService regionService;
	private UserService userService;

	private Region region;
	private List<Person> formLicensingSpecialists;
	
	private Set<Region> regions;
	private Set<Person> officeSpecialists;
	private List<Person> licensingSpecialists;
	
	@Override
	public void prepare() throws Exception {
		if (region != null && region.getId() != null) {
			region = regionService.loadRegion(region.getId());
		}
	}

	@Action(value = "tab", results = {
		@Result(name = "success", location = "regions_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "regions-list", results = {
		@Result(name = "success", location = "regions_list.jsp")
	})
	public String doList() {
		regions = regionService.getRegions(false);
		
		return SUCCESS;
	}

	@Action(value = "edit-region")
	public String doEditRegion() {
		if (formLicensingSpecialists == null) {
			formLicensingSpecialists = new ArrayList<Person>();
			if (region != null) {
				formLicensingSpecialists.addAll(region.getLicensingSpecialists());
			}
		}
		return INPUT;
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "region", message = "&zwnj;")
		}
	)
	@Action(value = "save-region")
	public String doSave() {
		manageSpecialists();
		try {
			regionService.saveRegion(region);
		} catch (DataIntegrityViolationException dive) {
			addActionError("One or more of the licensing specialists are already selected on another region.");
			return INPUT;
		}
		return SUCCESS;
	}
	
	@Action(value = "delete-region")
	public String doDelete() {
		regionService.deleteRegion(region);
		
		return SUCCESS;
	}
	
	private void manageSpecialists() {
		Map<Long, Person> tempMap = new HashMap<Long, Person>();
		if (formLicensingSpecialists != null) {
			for (Person value : formLicensingSpecialists) {
				tempMap.put(value.getId(), value);
			}
		}
		
		//iterate through all values removing the ones that are not still attached
		for (Iterator<Person> itr = region.getLicensingSpecialists().iterator(); itr.hasNext();) {
			Person value = itr.next();
			Person current = tempMap.remove(value.getId());
			if (current == null) {
				//the item is not in the current list so remove it
				itr.remove();
			}
		}
		
		for (Person value : tempMap.values()) {
			region.getLicensingSpecialists().add(value);
		}
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Set<Region> getRegions() {
		return regions;
	}

	public List<Person> getFormLicensingSpecialists() {
		return formLicensingSpecialists;
	}
	
	public void setFormLicensingSpecialists(List<Person> formLicensingSpecialists) {
		this.formLicensingSpecialists = formLicensingSpecialists;
	}
	
	public Set<Person> getOfficeSpecialists() {
		if (officeSpecialists == null) {
			officeSpecialists = userService.getPeople(RoleType.ROLE_OFFICE_SPECIALIST, true, true, false);
		}
		return officeSpecialists;
	}
	
	public List<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			Long id = null;
			if (region != null && region.getId() != null) {
				id = region.getId();
			}
			licensingSpecialists = regionService.getSpecialistsNotAssignedToRegion(id);
			Collections.sort(licensingSpecialists);
		}
		return licensingSpecialists;
	}
}