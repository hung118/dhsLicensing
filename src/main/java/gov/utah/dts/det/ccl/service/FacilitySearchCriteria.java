package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;

import java.util.Date;
import java.util.List;

public class FacilitySearchCriteria {

	private String facilityName;
	private NameSearchType nameSearchType;
	private String siteName;
	private NameSearchType siteNameSearchType;
	private Long regionId;
    private String licenseeName;
	private String county;
	private String city;
	private String zipCode;
	private String primaryPhone;
	private FacilityType facilityType;
	private FacilityStatus facilityStatus;
	private String ownerName;
	private boolean includeRegulated;
	private List<Long> licenseTypeIds;
	private List<Long> licenseStatusIds;
	private List<Long> licenseServiceCodeIds;
	private Long licensingSpecialistId;
	private Date licenseExpRangeStart;
	private Date licenseExpRangeEnd;
	private boolean conditional;
	private boolean includeExempt;
	private List<Long> exemptionIds;
	private boolean includeInProcess;
	private boolean includeInactive;
	private List<Long> inactiveReasonIds;
	private boolean includeUnlicensed;
	
	public FacilitySearchCriteria() {
		
	}
	
	public FacilitySearchCriteria(String facilityName, NameSearchType nameSearchType,String licenseeName, Long regionId, String county, String city,
			String zipCode, String primaryPhone, FacilityType facilityType, FacilityStatus facilityStatus, String ownerName, boolean includeRegulated,
			List<Long> licenseTypeIds, List<Long> licenseStatusIds, List<Long> licenseServiceCodeIds, Long licensingSpecialistId, Date licenseExpRangeStart,
			Date licenseExpRangeEnd, boolean conditional, boolean includeExempt, List<Long> exemptionIds, boolean includeInProcess,
			boolean includeInactive, List<Long> inactiveReasonIds, boolean includeUnlicensed) 
	{
		this.facilityName = facilityName;
		this.nameSearchType = nameSearchType;
        this.licenseeName = licenseeName;
		this.regionId = regionId;
		this.county = county;
		this.city = city;
		this.zipCode = zipCode;
		this.primaryPhone = primaryPhone;
		this.facilityType = facilityType;
		this.facilityStatus = facilityStatus;
		this.ownerName = ownerName;
		this.includeRegulated = includeRegulated;
		this.licenseTypeIds = licenseTypeIds;
		this.licenseStatusIds = licenseStatusIds;
		this.licenseServiceCodeIds = licenseServiceCodeIds;
		this.licensingSpecialistId = licensingSpecialistId;
		this.licenseExpRangeStart = licenseExpRangeStart;
		this.licenseExpRangeEnd = licenseExpRangeEnd;
		this.conditional = conditional;
		this.includeExempt = includeExempt;
		this.exemptionIds = exemptionIds;
		this.includeInProcess = includeInProcess;
		this.includeInactive = includeInactive;
		this.inactiveReasonIds = inactiveReasonIds;
		this.includeUnlicensed = includeUnlicensed;
	}
	
	public FacilitySearchCriteria(String facilityName, NameSearchType nameSearchType, String siteName, NameSearchType siteNameSearchType, String licenseeName, Long regionId, String county, String city,
			String zipCode, String primaryPhone, FacilityType facilityType, FacilityStatus facilityStatus, String ownerName, boolean includeRegulated,
			List<Long> licenseTypeIds, List<Long> licenseStatusIds, List<Long> licenseServiceCodeIds, Long licensingSpecialistId, Date licenseExpRangeStart,
			Date licenseExpRangeEnd, boolean conditional, boolean includeExempt, List<Long> exemptionIds, boolean includeInProcess,
			boolean includeInactive, List<Long> inactiveReasonIds, boolean includeUnlicensed) 
	{
		this.facilityName = facilityName;
		this.nameSearchType = nameSearchType;
		this.siteName = siteName;
		this.siteNameSearchType = siteNameSearchType;
        this.licenseeName = licenseeName;
		this.regionId = regionId;
		this.county = county;
		this.city = city;
		this.zipCode = zipCode;
		this.primaryPhone = primaryPhone;
		this.facilityType = facilityType;
		this.facilityStatus = facilityStatus;
		this.ownerName = ownerName;
		this.includeRegulated = includeRegulated;
		this.licenseTypeIds = licenseTypeIds;
		this.licenseStatusIds = licenseStatusIds;
		this.licenseServiceCodeIds = licenseServiceCodeIds;
		this.licensingSpecialistId = licensingSpecialistId;
		this.licenseExpRangeStart = licenseExpRangeStart;
		this.licenseExpRangeEnd = licenseExpRangeEnd;
		this.conditional = conditional;
		this.includeExempt = includeExempt;
		this.exemptionIds = exemptionIds;
		this.includeInProcess = includeInProcess;
		this.includeInactive = includeInactive;
		this.inactiveReasonIds = inactiveReasonIds;
		this.includeUnlicensed = includeUnlicensed;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public NameSearchType getNameSearchType() {
		return nameSearchType;
	}
	
	public void setNameSearchType(NameSearchType nameSearchType) {
		this.nameSearchType = nameSearchType;
	}
        
        public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public NameSearchType getSiteNameSearchType() {
		return siteNameSearchType;
	}

	public void setSiteNameSearchType(NameSearchType siteNameSearchType) {
		this.siteNameSearchType = siteNameSearchType;
	}

		public String getLicenseeName() {
		return licenseeName;
	}

	public void setLicenseeName(String licenseeName) {
		this.licenseeName = licenseeName;
	}
        
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	
	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public FacilityStatus getFacilityStatus() {
		return facilityStatus;
	}

	public void setFacilityStatus(FacilityStatus facilityStatus) {
		this.facilityStatus = facilityStatus;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public boolean isIncludeRegulated() {
		return includeRegulated;
	}

	public void setIncludeRegulated(boolean includeRegulated) {
		this.includeRegulated = includeRegulated;
	}

	public List<Long> getLicenseTypeIds() {
		return licenseTypeIds;
	}

	public void setLicenseTypeIds(List<Long> licenseTypeIds) {
		this.licenseTypeIds = licenseTypeIds;
	}

	public List<Long> getLicenseStatusIds() {
		return licenseStatusIds;
	}

	public void setLicenseStatusIds(List<Long> licenseStatusIds) {
		this.licenseStatusIds = licenseStatusIds;
	}

	public List<Long> getLicenseServiceCodeIds() {
		return licenseServiceCodeIds;
	}

	public void setLicenseServiceCodeIds(List<Long> licenseServiceCodeIds) {
		this.licenseServiceCodeIds = licenseServiceCodeIds;
	}

	public Long getLicensingSpecialistId() {
		return licensingSpecialistId;
	}

	public void setLicensingSpecialistId(Long licensingSpecialistId) {
		this.licensingSpecialistId = licensingSpecialistId;
	}
	
	public Date getLicenseExpRangeStart() {
		return licenseExpRangeStart;
	}

	public void setLicenseExpRangeStart(Date licenseExpRangeStart) {
		this.licenseExpRangeStart = licenseExpRangeStart;
	}

	public Date getLicenseExpRangeEnd() {
		return licenseExpRangeEnd;
	}

	public void setLicenseExpRangeEnd(Date licenseExpRangeEnd) {
		this.licenseExpRangeEnd = licenseExpRangeEnd;
	}
	
	public boolean isConditional() {
		return conditional;
	}
	
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}

	public boolean isIncludeExempt() {
		return includeExempt;
	}

	public void setIncludeExempt(boolean includeExempt) {
		this.includeExempt = includeExempt;
	}

	public List<Long> getExemptionIds() {
		return exemptionIds;
	}

	public void setExemptionIds(List<Long> exemptionIds) {
		this.exemptionIds = exemptionIds;
	}
	
	public boolean isIncludeInProcess() {
		return includeInProcess;
	}
	
	public void setIncludeInProcess(boolean includeInProcess) {
		this.includeInProcess = includeInProcess;
	}

	public boolean isIncludeInactive() {
		return includeInactive;
	}

	public void setIncludeInactive(boolean includeInactive) {
		this.includeInactive = includeInactive;
	}

	public List<Long> getInactiveReasonIds() {
		return inactiveReasonIds;
	}

	public void setInactiveReasonIds(List<Long> inactiveReasonIds) {
		this.inactiveReasonIds = inactiveReasonIds;
	}

	public boolean isIncludeUnlicensed() {
		return includeUnlicensed;
	}

	public void setIncludeUnlicensed(boolean includeUnlicensed) {
		this.includeUnlicensed = includeUnlicensed;
	}
	
	public static enum NameSearchType {
		
		STARTS_WITH("Starts with"),
		ANY_PART("Any part of name");
		
		private String displayName;
		
		private NameSearchType(String displayName) {
			this.displayName = displayName;
		}
		
		public String getDisplayName() {
			return displayName;
		}
	}
}