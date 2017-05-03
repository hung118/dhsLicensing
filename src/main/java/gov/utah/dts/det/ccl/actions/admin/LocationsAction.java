package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.service.LocationService;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.sort.enums.LocationSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/locations")
@Results({
	@Result(name = "input", location = "location_form.jsp"),
	@Result(name = "success", location = "locations-list", type = "redirectAction")
})
public class LocationsAction extends ActionSupport implements Preparable{
	
	private LocationService locationService;
	private RegionService regionService;
	private PickListService pickListService;
	
	private Location location;
	private ListControls lstCtrl;
	
	private Set<Region> regions;
	private List<PickListValue> rrAgencies;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(LocationSortBy.values())));
		lstCtrl.setSortBy(LocationSortBy.getDefaultSortBy().name());
		lstCtrl.setResultsPerPage(25);
	}
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "locations_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "locations-list", results = {
		@Result(name = "success", location = "locations_list.jsp")
	})
	public String doList() {
		lstCtrl.setResults(locationService.getLocations(LocationSortBy.valueOf(lstCtrl.getSortBy()), lstCtrl.getPage() - 1,
				lstCtrl.getResultsPerPage()));
		
		lstCtrl.setNumOfResults(locationService.getLocationsCount());
		
		return SUCCESS;
	}
	
	@Action(value = "edit-location")
	public String doEdit() {
		loadLocation();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadLocation();
		locationService.evict(location);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "location", message = "&zwnj;")
		}
	)
	@Action(value = "save-location")
	public String doSave() {
		try {
			locationService.saveLocation(location);
		} catch (DataIntegrityViolationException dive) {
			addActionError("The given combination of city, state, zip code, and county already exists.");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "delete-location")
	public String doDelete() {
		locationService.deleteLocation(location);
		
		return SUCCESS;
	}
	
	private void loadLocation() {
		if (location != null && location.getId() != null) {
			location = locationService.loadById(location.getId());
		}
	}
	
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public Set<Region> getRegions() {
		if (regions == null) {
			regions = regionService.getRegions(false);
		}
		return regions;
	}
	
	public List<PickListValue> getRrAgencies() {
		if (rrAgencies== null) {
			rrAgencies = pickListService.getValuesForPickList("CCR and R Agencies", true);
		}
		return rrAgencies;
	}
	
	public List<LocationSortBy> getLocationSortBys() {
		return Arrays.asList(LocationSortBy.values());
	}
}