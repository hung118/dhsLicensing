package gov.utah.dts.det.ccl.actions.reports;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.sort.enums.FacilityLicenseSummarySortBy;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-index", location = "index", type = "redirectAction")
})
public class ReportsAction extends ActionSupport {
	
	private static final String REDIRECT_INDEX = "redirect-index";
	
	private Long specialistId;
	private Long technicianId;
	private Date startDate;
	private Date endDate;
	private Set<Person> licensingSpecialists;
	private Set<Person> cbsTechnicians;
	private boolean validated;
	
	private FacilityLicenseSummarySortBy facLicenseSummarySortBy;

	private UserService userService;

	@Action(value = "open-applications-srch", 
		results = {
			@Result(name = "input", location = "open_applications_search_form.jsp")
		}
	)
	public String doOpenApplicationsSrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return REDIRECT_INDEX;
		}
		loadSpecialistId();
		return INPUT;
	}

	@Action(value = "expired-licenses-srch", 
		results = {
			@Result(name = "input", location = "expired_licenses_search_form.jsp")
		}
	)
	public String doExpiredLicensesSrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return REDIRECT_INDEX;
		}
		loadSpecialistId();
		return INPUT;
	}

	@Action(value = "license-renewals-srch", 
		results = {
			@Result(name = "input", location = "license_renewals_search_form.jsp")
		}
	)
	public String doLicenseRenewalsSrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return REDIRECT_INDEX;
		}
		loadSpecialistId();
		return INPUT;
	}

	@Action(value = "fac-license-summary-srch", 
		results = {
			@Result(name = "input", location = "facility_license_summary_search_form.jsp")
		}
	)
	public String doLicFacilitySummarySrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return REDIRECT_INDEX;
		}
		loadSpecialistId();
		if (facLicenseSummarySortBy == null) {
			facLicenseSummarySortBy = FacilityLicenseSummarySortBy.getDefaultSortBy();
		}
		return INPUT;
	}

	@Action(value = "fac-license-detail-srch", 
		results = {
			@Result(name = "input", location = "facility_license_detail_search_form.jsp")
		}
	)
	public String doLicFacilityDetailSrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return REDIRECT_INDEX;
		}
		loadSpecialistId();
		return INPUT;
	}

	@Action(value = "livescans-issued-srch", 
		results = {
			@Result(name = "input", location = "livescans_issued_search_form.jsp")
		}
	)
	public String doLivescansIssuedSrch() {
		if (!SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_BACKGROUND_SCREENING_MANAGER, RoleType.ROLE_BACKGROUND_SCREENING)) {
			return REDIRECT_INDEX;
		}
		loadTechnicianId();
		return INPUT;
	}

	@Action(value = "validate-livescans-issued", 
		results = {
			@Result(name = "input", location = "livescans_issued_search_form.jsp")
		}
	)
	public String doValidateLivescansIssued() {
		if (technicianId == null) {
			addFieldError("technicianId","CBS Technician is required.");
		}
		if (startDate == null) {
			addFieldError("startDate","Start Date is required.");
		}
		if (endDate == null) {
			addFieldError("endDate","End Date is required.");
		}
		if (!hasFieldErrors()) {
			validated = true;
		}
		return doLivescansIssuedSrch();
	}

	public void loadSpecialistId() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSOR_SPECIALIST) && specialistId == null) {
			specialistId = SecurityUtil.getUser().getPerson().getId();
		}
	}

	public void loadTechnicianId() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING) && technicianId == null) {
			technicianId = SecurityUtil.getUser().getPerson().getId();
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Long getSpecialistId() {
		return specialistId;
	}

	public void setSpecialistId(Long specialistId) {
		this.specialistId = specialistId;
	}

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public boolean isValidated() {
		return validated;
	}
	
	public FacilityLicenseSummarySortBy getFacLicenseSummarySortBy() {
		return facLicenseSummarySortBy;
	}

	public void setFacLicenseSummarySortBy(FacilityLicenseSummarySortBy facLicenseSummarySortBy) {
		this.facLicenseSummarySortBy = facLicenseSummarySortBy;
	}

	public Set<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSING_DIRECTOR, RoleType.ROLE_LICENSOR_MANAGER)) {
				licensingSpecialists = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, true, true, true);
			} else {
				licensingSpecialists = new HashSet<Person>();
				if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSOR_SPECIALIST)) {
					licensingSpecialists.add(SecurityUtil.getUser().getPerson());
				}
			}
			if (licensingSpecialists == null) {
				licensingSpecialists = new HashSet<Person>();
			}
		}
		return licensingSpecialists;
	}

	public Set<Person> getCbsTechnicians() {
		if (cbsTechnicians == null) {
			cbsTechnicians = userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING, true, true, true);
			if (cbsTechnicians == null) {
				cbsTechnicians = new HashSet<Person>();
			}
		}
		return cbsTechnicians;
	}

	public List<FacilityLicenseSummarySortBy> getFacilityLicenseSummarySortBys() {
		return Arrays.asList(FacilityLicenseSummarySortBy.values());
	}
}