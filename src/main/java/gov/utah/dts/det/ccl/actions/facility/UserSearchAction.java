package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.SearchException;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.templating.TemplateService;
import gov.utah.dts.det.ccl.documents.templating.templates.MailingLabelsTemplate;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.FacilitySearchView;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.FacilitySearchCriteria;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.sort.enums.FacilitySortBy;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.query.SortBy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/facility/search")
@Results({
	@Result(name = "input", location = "facilitySearch", type = "tiles"),
	@Result(name = "success", location = "facilitySearchResults", type = "tiles"),
	@Result(name = "print", location = "facilitySearchPrint", type = "tiles"),
	@Result(name = "labels", type = "stream", params = {"contentType", "${descriptor.contentType}", 
			"contentDisposition", "attachment;filename=\"${descriptor.fileName}\"", "contentLength",
			"${descriptor.contentLength}", "inputName", "descriptor.inputStream"}),
	@Result(name = "error", location = "index", type = "redirectAction")
})
public class UserSearchAction extends BaseSearchAction implements Preparable {
	
	private static final Map<Integer, String> MONTHS = new LinkedHashMap<Integer, String>();
	
	static {
		MONTHS.put(1, "January");
		MONTHS.put(2, "February");
		MONTHS.put(3, "March");
		MONTHS.put(4, "April");
		MONTHS.put(5, "May");
		MONTHS.put(6, "June");
		MONTHS.put(7, "July");
		MONTHS.put(8, "August");
		MONTHS.put(9, "September");
		MONTHS.put(10, "October");
		MONTHS.put(11, "November");
		MONTHS.put(12, "December");
	}
	
	private UserService userService;
	private RegionService regionService;
	protected TemplateService templateService;
	
	private FacilitySearchCriteria.NameSearchType nmSrchType = FacilitySearchCriteria.NameSearchType.ANY_PART;
	private FacilitySearchCriteria.NameSearchType siteNmSrchType = FacilitySearchCriteria.NameSearchType.ANY_PART;
	private Long rId;
	private FacilityType facType;
	private FacilityStatus facStatus;
	private List<Long> deactReasIds;
	private boolean incReg = false;
	private List<Long> licStatusIds;
	private List<Long> licServiceCodeIds;
	private Long lsId;
	private Date licExpRangeStart;
	private Date licExpRangeEnd;
	private boolean cond = false;
	private boolean incExe = false;
	private List<Long> exeIds;
	private boolean incInProc = false;
	private boolean incInact = false;
	private List<Long> inactReasIds;
	private boolean incUnlic = false;
	private FileDescriptor descriptor;
	private Set<Region> regions;
	private List<PickListValue> licenseStatuses;
	private List<PickListValue> licenseServiceCodes;
	private Set<Person> licensingSpecialists;
	private List<PickListValue> exemptions;
	private List<PickListValue> inactiveReasons;
	
	@Override
	public void prepare() {
		super.prepare();
	}

	@Action(value = "index")
	public String doIndex() {
		return INPUT;
	}
	
	@Action(value = "search-results")
	public String doSearch() {
		FacilitySearchCriteria criteria = getCriteriaObj();
		//printCriteria(criteria);
		try {
			List<FacilitySearchView> list = facilityService.searchFacilities(criteria, FacilitySortBy.valueOf(lstCtrl.getSortBy()),	lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage());
			lstCtrl.setResults(list);
			
			Integer numResults = facilityService.searchFacilitiesCount(criteria);
			lstCtrl.setNumOfResults(numResults);

		} catch (SearchException se) {
			addActionError(se.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "print-search-results")
	public String doPrint() {
		FacilitySearchCriteria criteria = getCriteriaObj();
		
		try {
			int results = facilityService.searchFacilitiesCount(criteria);
			
			if (results < 1500) {
				lstCtrl.setResults(facilityService.searchFacilities(criteria, FacilitySortBy.valueOf(lstCtrl.getSortBy()), 0, 1500));
			} else {
				addActionError("There are " + results + " results.  Please narrow the search to below 1500 results before printing.");
			}
		} catch (SearchException se) {
				addActionError(se.getMessage());
			return ERROR;
		}
			
		
		return "print";
	}
	
	@Action(value = "print-labels")
	public String doPrintLabels() {
		List<FacilitySearchView> labels = null;
		try {
			labels = facilityService.searchFacilities(getCriteriaObj(), FacilitySortBy.valueOf(lstCtrl.getSortBy()), 0, 20000);
		} catch (SearchException se) {
			addActionError(se.getMessage());
			return ERROR;
		}
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(MailingLabelsTemplate.MAILING_LABELS_KEY, labels);
		descriptor = new FileDescriptor();
		templateService.render(MailingLabelsTemplate.TEMPLATE_KEY, context, descriptor);
		
		return "labels";
	}
	
	private FacilitySearchCriteria getCriteriaObj() {
		return new FacilitySearchCriteria(fName, nmSrchType, siteName, siteNmSrchType, null, rId, county, city, zip, null,
				facType, facStatus, null, incReg, licTypeIds, licStatusIds, licServiceCodeIds, lsId, licExpRangeStart, 
				licExpRangeEnd, cond, incExe, exeIds, incInProc, incInact, inactReasIds, incUnlic);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	public FacilitySearchCriteria.NameSearchType getNmSrchType() {
		return nmSrchType;
	}
	
	public void setNmSrchType(FacilitySearchCriteria.NameSearchType nmSrchType) {
		this.nmSrchType = nmSrchType;
	}
	
	public FacilitySearchCriteria.NameSearchType getSiteNmSrchType() {
		return siteNmSrchType;
	}

	public void setSiteNmSrchType(
			FacilitySearchCriteria.NameSearchType siteNmSrchType) {
		this.siteNmSrchType = siteNmSrchType;
	}

	public Long getRId() {
		return rId;
	}
	
	public void setRId(Long rId) {
		if (rId != null && rId.longValue() == -1l) {
			this.rId = null;
		} else {
			this.rId = rId;
		}
	}
	
	public FacilityType getFacType() {
		return facType;
	}
	
	public void setFacType(FacilityType facType) {
		this.facType = facType;
	}
	
	public FacilityStatus getFacStatus() {
		return facStatus;
	}
	
	public void setFacStatus(FacilityStatus facStatus) {
		this.facStatus = facStatus;
	}
	
	public List<Long> getDeactReasIds() {
		return deactReasIds;
	}
	
	public void setDeactReasIds(List<Long> deactReasIds) {
		this.deactReasIds = deactReasIds;
	}
	
	public boolean isIncReg() {
		return incReg;
	}
	
	public void setIncReg(boolean incReg) {
		this.incReg = incReg;
	}
	
	public List<Long> getLicStatusIds() {
		return licStatusIds;
	}
	
	public void setLicStatusIds(List<Long> licStatusIds) {
		this.licStatusIds = licStatusIds;
	}
	
	public List<Long> getLicServiceCodeIds() {
		return licServiceCodeIds;
	}

	public void setLicServiceCodeIds(List<Long> licServiceCodeIds) {
		this.licServiceCodeIds = licServiceCodeIds;
	}

	public Long getLsId() {
		return lsId;
	}
	
	public void setLsId(Long lsId) {
		if (lsId != null && lsId.longValue() == -1l) {
			this.lsId = null;
		} else {
			this.lsId = lsId;
		}
	}
	
	public Date getLicExpRangeStart() {
		return licExpRangeStart;
	}
	
	public void setLicExpRangeStart(Date licExpRangeStart) {
		this.licExpRangeStart = licExpRangeStart;
	}
	
	public Date getLicExpRangeEnd() {
		return licExpRangeEnd;
	}
	
	public void setLicExpRangeEnd(Date licExpRangeEnd) {
		this.licExpRangeEnd = licExpRangeEnd;
	}
	
	public boolean isCond() {
		return cond;
	}
	
	public void setCond(boolean cond) {
		this.cond = cond;
	}
	
	public boolean isIncExe() {
		return incExe;
	}
	
	public void setIncExe(boolean incExe) {
		this.incExe = incExe;
	}
	
	public List<Long> getExeIds() {
		return exeIds;
	}
	
	public void setExeIds(List<Long> exeIds) {
		this.exeIds = exeIds;
	}
	
	public boolean isIncInProc() {
		return incInProc;
	}
	
	public void setIncInProc(boolean incInProc) {
		this.incInProc = incInProc;
	}
	
	public boolean isIncInact() {
		return incInact;
	}
	
	public void setIncInact(boolean incInact) {
		this.incInact = incInact;
	}
	
	public List<Long> getInactReasIds() {
		return inactReasIds;
	}
	
	public void setInactReasIds(List<Long> inactReasIds) {
		this.inactReasIds = inactReasIds;
	}
	
	public boolean isIncUnlic() {
		return incUnlic;
	}
	
	public void setIncUnlic(boolean incUnlic) {
		this.incUnlic = incUnlic;
	}
	
	public FileDescriptor getDescriptor() {
		return descriptor;
	}
	
	public List<FacilitySearchCriteria.NameSearchType> getNameSearchTypes() {
		return Arrays.asList(FacilitySearchCriteria.NameSearchType.values());
	}
	
	public Set<Region> getRegions() {
		if (regions == null) {
			regions = regionService.getRegions(false);
		}
		return regions;
	}
	
	public Map<Integer, String> getMonths() {
		return MONTHS;
	}
	
	public List<FacilityType> getFacilityTypes() {
		return Arrays.asList(FacilityType.values());
	}

	public List<FacilityStatus> getFacilityStatuses() {
		List<FacilityStatus> statuses = new ArrayList<FacilityStatus>();
		if (SecurityUtil.isUserInternal()) {
			statuses = Arrays.asList(FacilityStatus.values());
		} else {
			statuses.add(FacilityStatus.REGULATED);
		}
		return statuses;
	}

	public List<PickListValue> getLicenseStatuses() {
		if (licenseStatuses == null) {
			licenseStatuses = pickListService.getValuesForPickList("License Status", true);
		}
		return licenseStatuses;
	}

	public List<PickListValue> getLicenseServiceCodes() {
		if (licenseServiceCodes == null) {
			licenseServiceCodes = pickListService.getValuesForPickList("Service Code", true);
		}
		return licenseServiceCodes;
	}

	public Set<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			licensingSpecialists = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, false, true, false);
		}
		return licensingSpecialists;
	}
	
	public List<PickListValue> getExemptions() {
		if (exemptions == null) {
			exemptions = pickListService.getValuesForPickList("Exemption", true);
		}
		return exemptions;
	}
	
	public List<PickListValue> getInactiveReasons() {
		if (inactiveReasons == null) {
			inactiveReasons = pickListService.getValuesForPickList("Facility Deactivation Reasons", false);
		}
		return inactiveReasons;
	}
	
	@Override
	public List<SortBy> getSortBys() {
		//get the sort bys
		List<SortBy> sortBys = new ArrayList<SortBy>();
		if (SecurityUtil.isUserInternal()) {
			sortBys.addAll(Arrays.asList(FacilitySortBy.values()));
		} else if (SecurityUtil.isUserPartner()) {
			sortBys.addAll(FacilitySortBy.getPartnerSortBys());
		}
		return sortBys;
	}
	
	public Map<String, String> getSearchCriteria() {
		Map<String, String> criteria = new LinkedHashMap<String, String>();
		if (StringUtils.isNotBlank(fName)) {
			criteria.put("Name", fName);
			criteria.put("Name Search Type", nmSrchType.getDisplayName());
		}
        if (rId != null && rId.longValue() != -1l) {
			Region r = regionService.loadRegion(rId);
			if (r != null) {
				criteria.put("Region", r.getName());
			}
		}
		if (StringUtils.isNotBlank(county)) {
			criteria.put("County", county);
		}
		if (StringUtils.isNotBlank(city)) {
			criteria.put("City", city);
		}
		if (StringUtils.isNotBlank(zip)) {
			criteria.put("Zip Code", zip);
		}
		if (facType == null) {
			criteria.put("Facility Type", "Center and Home Based");
		} else {
			criteria.put("Facility Type", facType.getDisplayName());
		}
		if (facStatus != null) {
			criteria.put("Facility Status", facStatus.name());
		}
		String deactReasons = getSearchedPickLists(deactReasIds);
		if (StringUtils.isNotBlank(deactReasons)) {
			criteria.put("Deactivation Reasons", deactReasons);
		}
		String licenseTypesString = getSearchedPickLists(licTypeIds);
		if (StringUtils.isNotBlank(licenseTypesString)) {
			criteria.put("License Types", licenseTypesString);
		}
		String licenseStatusesString = getSearchedPickLists(licStatusIds);
		if (StringUtils.isNotBlank(licenseStatusesString)) {
			criteria.put("License Statuses", licenseStatusesString);
		}
		String licenseServiceCodesString = getSearchedPickLists(licServiceCodeIds);
		if (StringUtils.isNotBlank(licenseServiceCodesString)) {
			criteria.put("License Service Codes", licenseServiceCodesString);
		}
		if (lsId != null && lsId.longValue() != -1l) {
			User usr = userService.loadUserByPersonId(lsId);
			if (usr != null) {
				criteria.put("Licensing Specialist", usr.getPerson().getFirstAndLastName());
			}
		}
		if (licExpRangeStart != null && licExpRangeEnd != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/YYYY");
			criteria.put("License Expires Between", sdf.format(licExpRangeStart) + " - " + sdf.format(licExpRangeEnd));
		} else if (licExpRangeStart != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/YYYY");
			criteria.put("License Expires After", sdf.format(licExpRangeStart));
		} else if (licExpRangeEnd != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/YYYY");
			criteria.put("License Expires Before", sdf.format(licExpRangeEnd));
		}
		
		if (cond) {
			criteria.put("Conditional Facilities", "Yes");
		}
		
		String exemptionTypes = getSearchedPickLists(exeIds);
		if (StringUtils.isNotBlank(exemptionTypes)) {
			criteria.put("Exemptions", exemptionTypes);
		}
		
		criteria.put("Include Currently Regulated", incReg == true ? "Yes" : "No");
		criteria.put("Include Currently Exempt", incExe == true ? "Yes" : "No");
		criteria.put("Include Inactive - In Process", incInProc == true ? "Yes" : "No");
		criteria.put("include Inactive", incInact == true ? "Yes" : "No");
		criteria.put("Include Unlicensed", incUnlic == true ? "Yes" : "No");
		
		return criteria;
	}

	@SuppressWarnings("unused")
	private void printCriteria(FacilitySearchCriteria criteria) {
		System.out.println("01 " + criteria.getFacilityName());
		System.out.println("02 " + criteria.getNameSearchType());
		System.out.println("03 " + criteria.getSiteName());
		System.out.println("04 " + criteria.getSiteNameSearchType());
		System.out.println("05 " + criteria.getRegionId());
		System.out.println("06 " + criteria.getCounty());
		System.out.println("07 " + criteria.getCity());
		System.out.println("08 " + criteria.getZipCode());
		System.out.println("09 " + criteria.getFacilityType());
		System.out.println("10 " + criteria.getFacilityStatus());
		System.out.println("11 " + criteria.isIncludeRegulated());
		System.out.println("12 " + criteria.getLicenseTypeIds());
		System.out.println("13 " + criteria.getLicenseStatusIds());
		System.out.println("14 " + criteria.getLicenseServiceCodeIds());
		System.out.println("15 " + criteria.getLicensingSpecialistId());
		System.out.println("16 " + criteria.getLicenseExpRangeStart());
		System.out.println("17 " + criteria.getLicenseExpRangeEnd());
		System.out.println("18 " + criteria.isConditional());
		System.out.println("19 " + criteria.isIncludeExempt());
		System.out.println("20 " + criteria.getExemptionIds());
		System.out.println("21 " + criteria.isIncludeInProcess());
		System.out.println("22 " + criteria.isIncludeInactive());
		System.out.println("23 " + criteria.getInactiveReasonIds());
		System.out.println("24 " + criteria.isIncludeUnlicensed());
	}
}