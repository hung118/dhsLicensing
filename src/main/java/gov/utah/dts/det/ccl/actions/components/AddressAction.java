package gov.utah.dts.det.ccl.actions.components;

import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.service.LocationService;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/components/address")
@InterceptorRefs({
    @InterceptorRef("internalStack")
})
@Results({
	@Result(name = "success", type = "json", params = {"includeProperties", "^locations\\[\\d+\\]\\.city,^locations\\[\\d+\\]\\.state,^locations\\[\\d+\\]\\.zipCode,^locations\\[\\d+\\]\\.county"})
})
public class AddressAction extends ActionSupport {
	
	private LocationService locationService;
	
	private String query;
	private String zipCode;
	
	private List<String> zipCodes;
	private List<Location> locations;
	
	@Action(value = "search-zip-codes")
	public String doSearchZipCode() {
		locations = locationService.searchLocationsByZipCode(query);
		return SUCCESS;
	}
	
	@Action(value = "zip-code-list")
	public String doZipCodeSelect() {
		zipCodes = locationService.getAllZipCodes();
		return null;
	}
	
	@Action(value = "zip-code-detail")
	public String doSelectZipCode() {
		locations = locationService.getLocationsForZipCode(zipCode);
		return SUCCESS;
	}
	
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public List<String> getZipCodes() {
		return zipCodes;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
}