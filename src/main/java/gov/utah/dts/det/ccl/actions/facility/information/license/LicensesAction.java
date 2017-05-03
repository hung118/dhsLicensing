package gov.utah.dts.det.ccl.actions.facility.information.license;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.CondSanc;
import gov.utah.dts.det.ccl.security.permissions.LicensePermissionEvaluator;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.sort.enums.LicenseSortBy;
import gov.utah.dts.det.ccl.view.KeyValuePair;
import gov.utah.dts.det.ccl.view.ViewUtils;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.query.GenericPropertyComparator;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.service.ApplicationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "licenses-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "license_form.jsp"),
	@Result(name = "view", location = "license_detail.jsp"),
	@Result(name = "list", location = "licenses_list.jsp")
})
public class LicensesAction extends BaseFacilityAction implements Preparable {
	
	protected final Log log = LogFactory.getLog(getClass());
	private ApplicationService applicationService;
	private License license;
	private PickListValue licenseType;
	private ListControls lstCtrl = new ListControls();
	private Map<String, Object> response;
	private PickListValue inProcessType;
	private PickListValue inActiveType;
	private List<PickListValue> licenseTypes;
	private List<PickListValue> licenseSubtypes;
	private List<PickListValue> licenseStatuses;
	private Long serviceCode;
	private List<PickListValue> serviceCodes;
	private List<PickListValue> programCodes;
	private List<PickListValue> specificServiceCodes;
	private List<PickListValue> ageGroups;
	private LicensePermissionEvaluator licEvaluator = new LicensePermissionEvaluator();
	private List<KeyValuePair> previousLicenseNumbers;
	private Boolean isActiveLicenseOnly = null;
	
	@Override
	public void prepare() throws Exception {
	}

	public void prepareDoList() throws Exception {
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(LicenseSortBy.values())));
		if (lstCtrl.getSortBy() == null) {
			lstCtrl.setSortBy(LicenseSortBy.getDefaultSortBy().name());
		}
		lstCtrl.setShowControls(false);
	}

	@SkipValidation
	@Action(value = "licenses-list")
	public String doList() {
		List<License> results = new ArrayList<License>(getFacility().getLicenses());
		Collections.sort(results, new GenericPropertyComparator<License>(lstCtrl.getSortBy() == null ? LicenseSortBy.getDefaultSortBy().getOrderByString() : LicenseSortBy.valueOf(lstCtrl.getSortBy()).getOrderByString()));
		lstCtrl.setResults(results);
		
		if (!isShowNewLicenseButton()) {
			addActionMessage("There is a license in process that must be converted to a regular license before adding any new licenses.");
		}
		lstCtrl.setShowControls(true);
		
		return LIST;
	}
	
	@SkipValidation
	@Action(value = "edit-license")
	public String doEdit() {
		loadLicense();
		
		return INPUT;
	}

	@SkipValidation
	@Action(value = "view-license")
	public String doView() {
		loadLicense();
		
		return VIEW;
	}

	public void prepareDoSave() {
		loadLicense();
		facilityService.evict(license);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "license", message = "&zwnj;")
		}
	)
	@Action(value = "save-license")
	public String doSave() {
		try {
			// Redmine 29236 - set previous license number status to to inactive
			if (license.getPreviousLicenseNumber() != null) {
				License previousLicense = getFacility().getLicense(license.getPreviousLicenseNumber());
				previousLicense.setStatus(getInActiveType());
				facilityService.saveLicense(getFacility(), license);
			}
			
			// When creating a new license, set the license status to In Process if it hasn't been set by the user
			if (license.getId() == null && license.getStatus() == null) {
				license.setStatus(getInProcessType());
			}

			// set to last day of month always for expiration date - Redmine 20472
			if (license.getExpirationDate() != null) {
				Calendar temp = Calendar.getInstance();
				temp.setTime(license.getExpirationDate());
				int maxDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				temp.set(Calendar.DAY_OF_MONTH, maxDay);
				license.setExpirationDate(temp.getTime());
			}

			// RM #25698
			// Make sure adult and youth capacity counts are set appropriately based on age group
			if (applicationService.propertyContainsPickListValue(license.getAgeGroup(), ApplicationPropertyKey.ADULT_YOUTH_LICENSE_AGEGROUP.getKey())) {
				// Adult & Youth
				license.setAdultMaleCount(null);
				license.setAdultFemaleCount(null);
				license.setYouthTotalSlots(null);
				license.setYouthMaleCount(null);
				license.setYouthFemaleCount(null);
			} else
			if (applicationService.propertyContainsPickListValue(license.getAgeGroup(), ApplicationPropertyKey.ADULT_LICENSE_AGEGROUP.getKey())) {
				// Adult
				license.setYouthTotalSlots(null);
				license.setYouthMaleCount(null);
				license.setYouthFemaleCount(null);
			} else
			if (applicationService.propertyContainsPickListValue(license.getAgeGroup(), ApplicationPropertyKey.YOUTH_LICENSE_AGEGROUP.getKey())) {
				// Youth
				license.setAdultTotalSlots(null);
				license.setAdultMaleCount(null);
				license.setAdultFemaleCount(null);
			}
			
			// Redmine 29237
			// Check if license is closed and is the only active license, inactivate the facility.
			if (applicationService.propertyContainsPickListValue(license.getStatus(), ApplicationPropertyKey.CLOSED_LICENSE_STATUS.getKey())) {
				if (getIsActiveLicenseOnly().booleanValue()) {
					getFacility().setStatus(FacilityStatus.INACTIVE);
				}
			}
			
			// Check to see whether facility should be activated when license is activated
			if (applicationService.propertyContainsPickListValue(license.getStatus(), ApplicationPropertyKey.ACTIVE_LICENSE_STATUS.getKey()) &&
				(getFacility().getStatus() == null || !getFacility().getStatus().equals(FacilityStatus.REGULATED))
			) {
				Facility fac = getFacility();
				facilityService.saveLicenseWithFacilityActivation(fac, license);
				facilityService.evict(license);
				facilityService.evict(fac);
			} else {
				facilityService.saveLicense(getFacility(), license);
				facilityService.evict(license);
			}
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			
			return INPUT;
		} catch (Exception e) {
			log.error(e,e);
			//throw new Exception(e.getMessage(),e);
		}

		return REDIRECT_LIST;
	}
	
	@SkipValidation
	@Action(value = "delete-license")
	public String doDelete() {
		facilityService.removeLicense(getFacility().getLicense(license.getId()));
		
		return REDIRECT_LIST;
	}
	
	@Action(value = "change-license-type")
	public String doTypeChange() {
		return REDIRECT_LIST;
	}
	
	/**
	 * Ajax call to get program codes, specific service codes, and age groups with specified service code.
	 * 
	 * @return
	 */
	@SkipValidation
	@Action(value = "report-codes", results = {
		@Result(name = "success", type = "json", params = {"root", "response"})
	})	
	public String doReportCodes() throws Exception {
		List<PickListValue> programCodes = getCodes(getProgramCodes(), pickListService.getReportCodes(serviceCode, "PROGRAM_CODE"));
		List<PickListValue> specificServiceCodes = getCodes(getSpecificServiceCodes(), pickListService.getReportCodes(serviceCode, "SPECIFIC_SERVICE_CODE"));
		List<PickListValue> ageGroups = getCodes(getAgeGroups(), pickListService.getReportCodes(serviceCode, "AGE_GROUP"));
		
		response = new HashMap<String, Object>();
		response.put("programCodes", programCodes);
		response.put("specificServiceCodes", specificServiceCodes);
		response.put("ageGroups", ageGroups);
		
		return SUCCESS;
	}
		
	@Override
	public void validate() {
		if (license.getStatus() != null &&
			!applicationService.propertyContainsPickListValue(license.getStatus(), ApplicationPropertyKey.IN_PROCESS_LICENSE_STATUS.getKey())) 
		{
			if (license.getSubtype() == null) {
				addFieldError("license.subtype", "License type is required.");
			}
			if (license.getServiceCode() == null) {
				addFieldError("license.serviceCode", "Service code is required.");
			}
		}
	}
	
	private void loadLicense() {
		if (license != null && license.getId() != null) {
			license = getFacility().getLicense(license.getId());
		}
	}
	
	public boolean isShowNewLicenseButton() {
		//if there are any licenses that are in process they need to be fixed before adding a new license
		for (Iterator<License> itr = getFacility().getLicenses().iterator(); itr.hasNext();) {
			License l = itr.next();
			if (l.getStatus() == null ||
				applicationService.propertyContainsPickListValue(l.getStatus(), ApplicationPropertyKey.IN_PROCESS_LICENSE_STATUS.getKey())) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isInProcessStatus() {
		if (license != null && license.getStatus() != null && license.getStatus().equals(getInProcessType())) {
			return true;
		}
		return false;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public License getLicense() {
		return license;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}
	
	public PickListValue getLicenseType() {
		return licenseType;
	}
	
	public void setLicenseType(PickListValue licenseType) {
		this.licenseType = licenseType;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}

	public PickListValue getInProcessType() {
		if (inProcessType == null) {
			inProcessType = applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.IN_PROCESS_LICENSE_STATUS.getKey());
		}
		return inProcessType;
	}
	
	public PickListValue getInActiveType() {
		if (inActiveType == null) {
			inActiveType = applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.INACTIVE_LICENSE_STATUS.getKey());
		}
		return inActiveType;
	}
	
	public List<PickListValue> getLicenseTypes() {
		if (licenseTypes == null) {
			licenseTypes = pickListService.getValuesForPickList("Program Type", true);
		}
		return licenseTypes;
	}
	
	public List<PickListValue> getLicenseSubtypes() {
		if (licenseSubtypes == null) {
			licenseSubtypes = pickListService.getValuesForPickList("License Subtype", true);
		}
		return licenseSubtypes;
	}
	
	public List<PickListValue> getLicenseStatuses() {
		if (licenseStatuses == null) {
			licenseStatuses = pickListService.getValuesForPickList("License Status", true);
		}
		return licenseStatuses;
	}
	
	public Long getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(Long serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public List<PickListValue> getServiceCodes() {
		if (serviceCodes == null) {
			serviceCodes = pickListService.getValuesForPickList("Service Code", true);
		}
		return serviceCodes;
	}
	
	public List<PickListValue> getProgramCodes() {
		if (programCodes == null) {
			programCodes = pickListService.getValuesForPickList("Program Code", true);
		}
		return programCodes;
	}
	
	public List<PickListValue> getSpecificServiceCodes() {
		if (specificServiceCodes == null) {
			specificServiceCodes = pickListService.getValuesForPickList("Specific Service Code", true);
		}
		return specificServiceCodes;
	}
	
	public List<PickListValue> getAgeGroups() {
		if (ageGroups == null) {
			ageGroups = pickListService.getValuesForPickList("Age Group", true);
		}
		return ageGroups;
	}
	
	public List<CondSanc> getCondSancs() {
		return Arrays.asList(CondSanc.values());
	}
	
	private List<PickListValue> getCodes(List<PickListValue> list, String codes) {
		List<PickListValue> retList = new ArrayList<PickListValue>();
		if (codes == null) {
			return retList;
		}
		for (PickListValue pkv : list) {
			if (codes.contains(pkv.getId().toString())) {
				retList.add(pkv);
			}
		}
		
		return retList;
	}

	public boolean hasLicenseEntityPermission(String permission, Long id) {
    	boolean rval = false;
    	if (permission != null && id != null) {
    		License lic = getFacility().getLicense(id);
    		if (lic != null) {
    			rval = licEvaluator.hasPermission(getAuthentication(), lic, permission);
    		}
    	}
    	return rval;
    }

	public List<KeyValuePair> getPreviousLicenseNumbers() {
		if (license == null || license.getLicenseNumber() == null) {
			List<License> licenses = new ArrayList<License>(getFacility().getLicenses());
			Collections.sort(licenses, new GenericPropertyComparator<License>("startDate"));
			previousLicenseNumbers = new ArrayList<KeyValuePair>();
			for (int i = 0; i < licenses.size(); i++) {
				License l = licenses.get(i);
				KeyValuePair kvp = new KeyValuePair(l.getId(), l.getId() + " - " + l.getServiceCodeDesc());
				previousLicenseNumbers.add(kvp);
			}
		}
		return previousLicenseNumbers;
	}

	public void setPreviousLicenseNumbers(List<KeyValuePair> previousLicenseNumbers) {
		this.previousLicenseNumbers = previousLicenseNumbers;
	}

	public Boolean getIsActiveLicenseOnly() {
		if (isActiveLicenseOnly == null) {
			isActiveLicenseOnly = new Boolean(false);
			int count = 0;
			if (applicationService.propertyContainsPickListValue(license.getStatus(), ApplicationPropertyKey.ACTIVE_LICENSE_STATUS.getKey())) {
				count++;
			}
			SortedSet<License> licenses = getFacility().getLicenses();
			for (License l : licenses) {
				if (applicationService.propertyContainsPickListValue(l.getStatus(), ApplicationPropertyKey.ACTIVE_LICENSE_STATUS.getKey()) &&
						!l.getId().equals(license.getId())) {
					count++;
				}
			}
			
			if (applicationService.propertyContainsPickListValue(license.getStatus(), ApplicationPropertyKey.ACTIVE_LICENSE_STATUS.getKey()) &&
					count == 1) {
				isActiveLicenseOnly = new Boolean(true);
			}
		}
		
		return isActiveLicenseOnly;
	}

	public void setIsActiveLicenseOnly(Boolean isActiveLicenseOnly) {
		this.isActiveLicenseOnly = isActiveLicenseOnly;
	}

}