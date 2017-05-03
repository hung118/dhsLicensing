package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.Exemption;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityAttachment;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.model.enums.FacilityEventType;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.AccreditationExpiringView;
import gov.utah.dts.det.ccl.model.view.AlertFollowUpsNeededView;
import gov.utah.dts.det.ccl.model.view.AnnouncedInspectionNeededView;
import gov.utah.dts.det.ccl.model.view.BasicFacilityInformation;
import gov.utah.dts.det.ccl.model.view.ConditionalFacilityView;
import gov.utah.dts.det.ccl.model.view.ExemptVerificationView;
import gov.utah.dts.det.ccl.model.view.ExpiredAndExpiringLicenseView;
import gov.utah.dts.det.ccl.model.view.FacilityCaseloadView;
import gov.utah.dts.det.ccl.model.view.FacilityContactView;
import gov.utah.dts.det.ccl.model.view.FacilityEventView;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;
import gov.utah.dts.det.ccl.model.view.FacilitySearchView;
import gov.utah.dts.det.ccl.model.view.FileCheckView;
import gov.utah.dts.det.ccl.model.view.NewApplicationPendingDeadlineView;
import gov.utah.dts.det.ccl.model.view.OutstandingCmpView;
import gov.utah.dts.det.ccl.model.view.SortableFacilityView;
import gov.utah.dts.det.ccl.model.view.TrackingRecordScreeningApprovalsView;
import gov.utah.dts.det.ccl.model.view.UnannouncedInspectionNeededView;
import gov.utah.dts.det.ccl.model.view.WorkInProgressView;
import gov.utah.dts.det.ccl.view.FacilityResultView;
import gov.utah.dts.det.ccl.view.KeyValuePair;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface FacilityService {

  public Facility loadById(Long id);

  public Facility loadFacilityByIdNumber(String idNumber);

  public License loadLicenseById(Long licenseId);

  public Facility createLicensedFacility(Facility facility, Person licensingSpecialist) throws CclServiceException;

  public Facility createUnlicensedFacility(Facility facility, Person owner, UnlicensedComplaint complaint);

  public Facility saveFacility(Facility facility);

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
  public Facility activateRegulatedFacility(Facility facility, Person licensingSpecialist) throws CclServiceException;

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
  public Facility updateLicensingSpecialist(Facility facility, Person licensingSpecialist);

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
  public Facility updateInitialRegulationDate(Facility facility, Date initialRegulationDate);

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
  public Facility activateExemptFacility(Facility facility) throws CclServiceException;

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
  public Facility setFacilityInProcess(Facility facility, Person licensingSpecialist) throws CclServiceException;

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST','ROLE_BACKGROUND_SCREENING_MANAGER')")
  public Facility deactivateFacility(Facility facility, PickListValue reason, Date effectiveDate) throws CclServiceException;

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST','ROLE_BACKGROUND_SCREENING_MANAGER')")
  public Facility cancelFacilityDeactivation(Facility facility);

  public List<PickListValue> getStatusReasons(boolean active);

  public void saveLicense(Facility facility, License license) throws CclServiceException;
  
  public Facility saveLicenseWithFacilityActivation(Facility facility, License license) throws CclServiceException;

  public void removeLicense(License license) throws CclServiceException;

  public void saveExemption(Facility facility, Exemption exemption) throws CclServiceException;

  public void saveConditionalStatus(Facility facility, FacilityTag status) throws CclServiceException;

  public FacilityPerson loadFacilityPerson(Long facilityId, Long facilityPersonId, Long firstType, Long secondType);
  
  public FacilityPerson loadFacilityBoardMember(Long facilityId, Long facilityPersonId, Long facilityPersonType);

  public List<TrackingRecordScreeningApprovalsView> loadScreenedPeopleForFacility(Long facilityId, boolean excludeDirectors, boolean excludeContacts);

  public TrackingRecordScreeningApprovalsView loadScreenedPersonForFacility(Long facilityId, Long personId);

  public List<FacilityPerson> getDirectors(Long facilityId);

  public PickListValue getSecondaryContactPersonType();
  
  public PickListValue getPrimaryContactPersonType();

  public List<FacilityPerson> getContacts(Long facilityId);
  
  public List<FacilityPerson> getPrimaryContact(Long facilityId);

  public void saveDirector(FacilityPerson director, boolean first);
  
  public void saveContact(FacilityPerson contact);

  public List<FacilityPerson> getOwners(Long facilityId);

  public void saveOwner(FacilityPerson owner, boolean primary);

  public List<FacilityPerson> getBoardMembers(Long facilityId);

  public void saveBoardMember(FacilityPerson boardMember);

  public void saveBoardMemberAttachment(FacilityAttachment attachment);

  public void deleteBoardMemberAttachment(Long id);

  public FacilityAttachment loadBoardMemberAttachment(Long id);

  public List<FacilityAttachment> getFacilityAttachments(Long facilityId, String attachmentType);

  public PickListValue getFirstDirectorPersonType();
  
  public PickListValue getSecondDirectorPersonType();

  public PickListValue getPrimaryOwnerPersonType();
  
  public PickListValue getSecondaryOwnerPersonType();
  
  public PickListValue getBoardMemberPersonType();

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST')")
  public Facility deletePerson(Facility facility, Long personId);

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_MANAGER')")
  public List<BasicFacilityInformation> getCaseload(Person licensingSpecialist);

  public List<FacilityCaseloadView> getUserCaseload(Long specialistId, RoleType roleType, SortBy sortBy);

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_MANAGER')")
  public List<UserCaseloadCount> getUserCaseloadCounts();

  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_MANAGER')")
  public void transferFacilities(Person fromLs, Person toLs, List<Facility> facilities);

  /* SEARCH */

  public List<FacilitySearchView> searchFacilities(FacilitySearchCriteria criteria, SortBy sortBy, int page, int resultsPerPage);

  public int searchFacilitiesCount(FacilitySearchCriteria criteria);

  public FacilityResultView getFacilityResultView(Long facilityId);

  public List<FacilityResultView> searchFacilitiesByName(String name, Long excludeFacilityId);

  public List<FacilityEventView> getFacilityHistory(Long facilityId, ListRange listRange, SortBy sortBy,
      List<FacilityEventType> eventTypes);

  public List<FileCheckView> getFileCheck(Long facilityId, ListRange listRange);

  /* STATUS, TYPE, ECT... */

  public boolean isCertificateType(PickListValue licenseType);

  public boolean isPeopleOwnershipType(PickListValue ownershipType);

  public boolean requiresDirector(PickListValue licenseType);

  public boolean licenseRequiresUnderAgeTwo(PickListValue licenseType);

  /* ALERTS */

  public List<ExpiredAndExpiringLicenseView> getExpiredAndExpiringLicenses(Long personId, boolean showWholeRegion, SortBy sortBy);

  public List<NewApplicationPendingDeadlineView> getNewApplicationPendingDeadlines(Long personId, boolean showWholeRegion,
      SortBy sortBy);

  public List<AccreditationExpiringView> getExpiringAccreditations(Long personId, boolean showWholeRegion, SortBy sortBy);

  public List<ConditionalFacilityView> getFacilitiesOnConditionalLicenses(Long personId, boolean showWholeRegion, SortBy sortBy);

  public List<AnnouncedInspectionNeededView> getAnnouncedInspectionsNeeded(Long personId, boolean showWholeRegion, SortBy sortBy);

  public List<UnannouncedInspectionNeededView> getUnannouncedInspectionsNeeded(Long personId, boolean showWholeRegion,
      SortBy sortBy);

  public List<AlertFollowUpsNeededView> getFollowUpInspectionsNeeded(Long personId, String role, SortBy sortBy);

  public List<WorkInProgressView> getWorkInProgress(Long personId, SortBy sortBy);

  public List<OutstandingCmpView> getOutstandingCmps(Long personId, boolean showWholeRegion);

  public List<ExemptVerificationView> getExemptVerifications(SortBy sortBy);

  public List<Complaint> getComplaintsToBeFinalized(Long personId, boolean showWholeRegion);

  
  public List<SortableFacilityView> getOpenApplicationsBySpecialist(Long specialistId);
  
  public List<FacilityLicenseView> getFacilityLicenseSummary(Long specialistId, Date expDate, SortBy sortBy);

  public List<FacilityLicenseView> getFacilityLicenseDetail(Long specialistId, Date expDate);

  public List<FacilityLicenseView> getExpiringLicensesBySpecialist(Date expDate, Long specialistId);

  public List<FacilityLicenseView> getRenewalLicensesBySpecialist(Date expDate, Long specialistId);

  public List<FacilityLicenseView> getFosterCareRenewalLicensesBySpecialist(Date expDate, Long specialistId);

  public List<FacilityContactView> getContactFacilities(Long personId, SortBy sortBy);

  public void evict(final Object entity);

  /* SCHEDULED TASKS */
  public void deactivateFacilitiesWithExpiredExemptions();

  public void deactivateScheduledFacilities();

  public void sendERepFile() throws Exception;

  public FacilityContactView loadContactFacilityById(Long id);
  
  public List<KeyValuePair> findAllPreviousLicenseNumbers();
  
}