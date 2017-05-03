package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.sort.enums.FacilitySortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.Iterator;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public abstract class BaseSearchAction extends ActionSupport implements Preparable {

	protected PickListService pickListService;
	protected FacilityService facilityService;
	
	protected String fName;
	protected String siteName;
    protected String licname;
	protected String county;
	protected String city;
	protected String zip;
	protected List<Long> licTypeIds;
	
	protected ListControls lstCtrl;
	
	protected List<PickListValue> licenseTypes;
	
	@Override
	public void prepare() {
		//set up the sort component
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(getSortBys());
		lstCtrl.setSortBy(FacilitySortBy.getDefaultSortBy().name());
		lstCtrl.setResultsPerPage(25);
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setFName(String fName) {
		this.fName = fName;
	}
	
	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
        
        public String getLicname() {
		return licname;
	}
	
	public void setLicname(String licname) {
		this.licname = licname;
	}
        

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public List<Long> getLicTypeIds() {
		return licTypeIds;
	}
	
	public void setLicTypeIds(List<Long> licTypeIds) {
		this.licTypeIds = licTypeIds;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<PickListValue> getLicenseTypes() {
		if (licenseTypes == null) {
			licenseTypes = pickListService.getValuesForPickList("License Subtype", true);
		}
		return licenseTypes;
	}
	
	public String getSearchedPickLists(List<Long> pickListIds) {
		StringBuilder sb = new StringBuilder();
		if (pickListIds != null) {
			for (Iterator<Long> itr = pickListIds.iterator(); itr.hasNext();) {
				Long id = itr.next();
				sb.append(pickListService.loadPickListValueById(id).getValue());
				if (itr.hasNext()) {
					sb.append(", ");
				}
			}
		}
			
		return sb.toString();
	}
	
	public abstract List<SortBy> getSortBys();
}