package gov.utah.dts.det.ccl.actions.unlicensedcomplaints;

import gov.utah.dts.det.ccl.dao.SearchException;
import gov.utah.dts.det.ccl.model.view.FacilitySearchView;
import gov.utah.dts.det.ccl.service.FacilitySearchCriteria;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.sort.enums.ComplaintFacilitySearchSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "unlicensed-complaints.facility-search", type = "tiles"),
	@Result(name = "success", location = "unlicensed-complaints.facility-search-results", type = "tiles"),
	@Result(name = "error", location = "search-form", type = "redirectAction")
})
public class SearchAction extends ActionSupport implements Preparable {

	private FacilityService facilityService;
	
	private String ownerName;
	private String facName;
	private String address;
	private String city;
	private String county;
	private String zipCode;
	private String phone;

	private CclListControls lstCtrl;
	
	@Override
	public void prepare() {
		//set up the sort component
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ComplaintFacilitySearchSortBy.values())));
		lstCtrl.setSortBy(ComplaintFacilitySearchSortBy.getDefaultSortBy().name());
		lstCtrl.setResultsPerPage(25);
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}

	@Action(value = "search-form")
	public String doSearchForm() {
		return INPUT;
	}
	
	@Action(value = "search-results")
	public String doSearch() {
		FacilitySearchCriteria criteria = getCriteriaObj();
		
		try {
			List<FacilitySearchView> list = facilityService.searchFacilities(criteria, ComplaintFacilitySearchSortBy.valueOf(lstCtrl.getSortBy()), lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage());
			lstCtrl.setResults(list);
			
			lstCtrl.setNumOfResults(list.size());
		} catch (SearchException se) {
			addActionError(se.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	private FacilitySearchCriteria getCriteriaObj() {
		return new FacilitySearchCriteria(facName, FacilitySearchCriteria.NameSearchType.ANY_PART, null, null, county, city, zipCode, phone, 
				null, null, ownerName, true, null, null, null, null, null, null, false, true, null, true, true, null, true);
	}

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getFacName() {
		return facName;
	}
	
	public void setFacName(String facName) {
		this.facName = facName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}