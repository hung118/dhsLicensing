package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.service.FacilitySearchCriteria;
import gov.utah.dts.det.ccl.sort.enums.FacilitySortBy;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/public/facility/search")
@Results({
	@Result(name = "input", location = "publicFacilitySearch", type = "tiles"),
	@Result(name = "success", location = "publicFacilitySearchResults", type = "tiles")
})
public class PublicSearchAction extends BaseSearchAction implements Preparable {

	@Override
	public void prepare() {
		super.prepare();
	}
	
	@Override
	public List<SortBy> getSortBys() {
		List<SortBy> sortBys = new ArrayList<SortBy>();
		sortBys.addAll(FacilitySortBy.getPublicSortBys());
		return sortBys;
	}

	@Action(value = "index")
	public String doIndex() {
		return INPUT;
	}
	
	@Action(value = "search-results")
	public String doSearch() {
		FacilitySearchCriteria criteria = getCriteriaObj();
		lstCtrl.setResults(facilityService.searchFacilities(criteria, FacilitySortBy.valueOf(lstCtrl.getSortBy()),
				lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage()));
		
		lstCtrl.setNumOfResults(facilityService.searchFacilitiesCount(criteria));
		
		return SUCCESS;
	}
	
	private FacilitySearchCriteria getCriteriaObj() {
		return new FacilitySearchCriteria(fName, FacilitySearchCriteria.NameSearchType.ANY_PART,null, null, county, city, zip, null,
				null, null, null, true, licTypeIds, null, null, null, null, null, false, false, null, false, false, null, false);
	}
}