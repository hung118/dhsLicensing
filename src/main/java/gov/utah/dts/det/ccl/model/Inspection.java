package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.SpecialistType;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "INSPECTION")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Conversion
public class Inspection extends StateObject implements Serializable, Comparable<Inspection>, ModifiableStateObject<Inspection.State, Inspection.StateChange> {
	
	public enum State {ENTRY, APPROVAL, FINALIZED};
	public enum StateChange {CREATED, ENTRY_COMPLETED, ENTRY_REJECTED, FINALIZED, UNFINALIZED};
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LICENSE_ID")
	private License license;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inspectionDate;
	
	@Column(name = "ARRIVAL_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrivalTime;
	
	@Column(name = "DEPARTURE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime;
	
	@Column(name = "HAS_FINDINGS")
	@Type(type = "yes_no")
	private Boolean hasFindings;
	
	@Column(name = "CMP_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date cmpDueDate;
	
	@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVER_ID")
	private Person approver;
	
	@Column(name = "RESETS_COMPLIANCE")
	@Type(type = "yes_no")
	private Boolean resetsCompliance;
	
	@Column(name = "FINDING_CMT")
	private String findingsComment;
	
	@Column(name = "FOLLOWUP_CMT")
	private String followupComment;
	
	@Column(name = "RESOLVED")
	@Type(type = "yes_no")
	private Boolean resolved;
	
	@OneToMany(mappedBy = "primaryKey.inspection", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<InspectionType> types = new HashSet<InspectionType>();
	
	@OneToMany(mappedBy = "primaryKey.inspection", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<InspectionSpecialist> specialists = new HashSet<InspectionSpecialist>();
	
	@OneToMany(mappedBy = "inspection", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Sort(type = SortType.NATURAL)
	private SortedSet<Finding> findings = new TreeSet<Finding>();
	
	@OneToMany(mappedBy = "inspection", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<CmpTransaction> cmpTransactions = new HashSet<CmpTransaction>();
	
	@OneToMany(mappedBy = "inspection", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Sort(type = SortType.UNSORTED)
	@OrderBy("resultDate desc")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<InspectionChecklist> inspectionChecklist = new TreeSet<InspectionChecklist>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "COMPLAINT_INSPECTION",
		joinColumns = @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "ID")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Complaint> complaints = new HashSet<Complaint>();
	
	@ManyToMany(mappedBy = "followUps", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Finding> followUps = new HashSet<Finding>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
	@JoinTable(name = "FINDING_CORRECTION",
		joinColumns = @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "FINDING_ID", referencedColumnName = "ID")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Finding> corrections = new HashSet<Finding>();
		
	public Inspection() {
		
	}
	
	public void save() {
		updateTimes();
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public License getLicense() {
		return license;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Inspection date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Inspection date is required.", shortCircuit = true)
		}
	)
	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	@TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.InspectionTimeConverter")
//	@RequiredFieldValidator(message = "Arrival time is required.")
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.InspectionTimeConverter")
//	@RequiredFieldValidator(message = "Departure time is required.")
	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@RequiredFieldValidator(message = "Please indicate whether or not there were any findings.")
	public Boolean getHasFindings() {
		return hasFindings;
	}
	
	public void setHasFindings(Boolean hasFindings) {
		this.hasFindings = hasFindings;
	}
	
	@ConversionErrorFieldValidator(message = "CMP due date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getCmpDueDate() {
		return cmpDueDate;
	}
	
	public void setCmpDueDate(Date cmpDueDate) {
		this.cmpDueDate = cmpDueDate;
	}
	
	public Person getApprover() {
		return approver;
	}
	
	public void setApprover(Person approver) {
		this.approver = approver;
	}
	
	public Boolean getResetsCompliance() {
		return resetsCompliance;
	}
	
	public void setResetsCompliance(Boolean resetsCompliance) {
		this.resetsCompliance = resetsCompliance;
	}

	public Set<InspectionType> getTypes() {
		return types;
	}
	
	public void setTypes(Set<InspectionType> types) {
		this.types = types;
	}
	
	public PickListValue getPrimaryInspectionType() {
		for (InspectionType type : getTypes()) {
			if (type.isPrimary()) {
				return type.getInspectionType(); 
			}
		}
		return null;
	}
	
	public Set<PickListValue> getNonPrimaryInspectionTypes() {
		Set<PickListValue> nonPrimaryTypes = new HashSet<PickListValue>();
		for (InspectionType type : getTypes()) {
			if (!type.isPrimary()) {
				nonPrimaryTypes.add(type.getInspectionType());
			}
		}
		return nonPrimaryTypes;
	}
	
	public Set<InspectionSpecialist> getSpecialists() {
		return specialists;
	}
	
	public void setSpecialists(Set<InspectionSpecialist> specialists) {
		this.specialists = specialists;
	}
	
	public Person getPrimarySpecialist() {
		for (InspectionSpecialist spec : getSpecialists()) {
			if (spec.getSpecialistType() == SpecialistType.PRIMARY) {
				return spec.getSpecialist();
			}
		}
		return null;
	}
	
	public Person getSecondSpecialist() {
		for (InspectionSpecialist spec : getSpecialists()) {
			if (spec.getSpecialistType() == SpecialistType.SECOND) {
				return spec.getSpecialist();
			}
		}
		return null;
	}
	
	public Person getThirdSpecialist() {
		for (InspectionSpecialist spec : getSpecialists()) {
			if (spec.getSpecialistType() == SpecialistType.THIRD) {
				return spec.getSpecialist();
			}
		}
		return null;	
	}
	
	public SortedSet<Finding> getFindings() {
		return findings;
	}
	
	public void setFindings(SortedSet<Finding> findings) {
		this.findings = findings;
	}
	
	public Finding getFinding(Long id) {
		if (id != null) {
			for (Finding finding : findings) {
				if (finding.getId().equals(id)) {
					return finding;
				}
			}
		}
		
		return null;
	}
	
	public void addFinding(Finding finding) {
		if (finding != null) {
			finding.setInspection(this);
			findings.add(finding);
		}
	}
	
	public void removeFinding(Finding finding) {
		findings.remove(finding);
	}
	
	public Set<CmpTransaction> getCmpTransactions() {
		return cmpTransactions;
	}
	
	public void setCmpTransactions(Set<CmpTransaction> cmpTransactions) {
		this.cmpTransactions = cmpTransactions;
	}
	
	public Set<Complaint> getComplaints() {
		return complaints;
	}
	
	public void setComplaints(Set<Complaint> complaints) {
		this.complaints = complaints;
	}
	
	public Set<Finding> getFollowUps() {
		return followUps;
	}
	
	public void setFollowUps(Set<Finding> followUps) {
		this.followUps = followUps;
	}
	
	public Set<Finding> getCorrections() {
		return corrections;
	}
	
	public void setCorrections(Set<Finding> corrections) {
		this.corrections = corrections;
	}
	
	@Override
	public Inspection.State getState() {
		if (super.getInternalState() == null) {
			return null;
		}
		return Inspection.State.valueOf(super.getInternalState());
	}
	
	@Override
	public void setState(Inspection.State state, Inspection.StateChange changeType, String note) {
		super.setState(state.name(), changeType.name(), note);
	}
	
	public boolean isCmpDueDateRequired() {
		boolean cmpDueDateRequired = false;
		for (Finding finding : findings) {
			if (finding.getCmpAmount() != null && finding.getCmpAmount().longValue() > 0l && finding.getRescindedDate() == null) {
				cmpDueDateRequired = true;
			}
		}
		return cmpDueDateRequired;
	}
	
	/**
	 * Returns whether or not a follow up is still required.  This returns true if any of the findings still require a follow up.  Unlike 
	 * isAllPaperworkOrNoFollowUp() it will return false once all follow ups have been done.
	 * 
	 * @return whether or not a follow up is still required.
	 */
	public boolean isFollowUpRequired() {
		for (Finding finding : findings) {
			if (finding.isFollowUpNeeded()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether or not a follow up is required regardless of whether one has been done yet or not.  It is generated by seeing if all
	 * findings are either paperwork required or were corrected on site and the rule is marked as no follow up required
	 * if corrected on site.  This is primarily used to make the statement of findings easier to generate.
	 * 
	 * @return Whether or not a follow up is required regardless of whether one has been done yet.
	 */
	public boolean isAllPaperworkOrNoFollowUp() {
		for (Finding finding : findings) {
			if (!finding.getRule().isPaperworkRequired() && !(finding.getRule().isNoFollowUp() && finding.isCorrectedOnSite())) {
				return false;
			}
		}
		return true;
	}
	
	@PreUpdate
	public void preUpdate() {
		updateTimes();
	}
	
	public void updateTimes() {
		//we need to set the arrival time and departure time to the same date as the inspection date.
		//hour and minute values will not be changed
		if (inspectionDate != null) {
			Calendar insDateCal = Calendar.getInstance();
			insDateCal.setTime(inspectionDate);
			
			if (arrivalTime != null) {
				Calendar arrivalCal = Calendar.getInstance();
				arrivalCal.setTime(arrivalTime);
				setSameDay(insDateCal, arrivalCal);
				arrivalTime = arrivalCal.getTime();
			}
			
			if (departureTime != null) {
				Calendar departureCal = Calendar.getInstance();
				departureCal.setTime(departureTime);
				setSameDay(insDateCal, departureCal);
				departureTime = departureCal.getTime();
			}
		}
	}
	
	public boolean hasInspectionType(PickListValue inspectionType) {
		if (inspectionType == null) {
			return false;
		}
		for (InspectionType type : getTypes()) {
			if (type.getInspectionType().equals(inspectionType)) {
				return true;
			}
		}
		return false;
	}
	
	private void setSameDay(Calendar source, Calendar target) {
		target.set(Calendar.YEAR, source.get(Calendar.YEAR));
		target.set(Calendar.MONTH, source.get(Calendar.MONTH));
		target.set(Calendar.DATE, source.get(Calendar.DATE));
	}
	
	@Override
	public String toString() {
		return "Inspection " + getId() + ": " + getInspectionDate(); 
	}
	
	@Override
	public int compareTo(Inspection o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getFacility(), o.getFacility(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getInspectionDate(), o.getInspectionDate(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getArrivalTime(), o.getArrivalTime(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getDepartureTime(), o.getDepartureTime(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFacility() == null) ? 0 : (getFacility().getId() == null ? getFacility().hashCode() : getFacility().getId().hashCode()));
		result = prime * result + ((getArrivalTime() == null) ? 0 : getArrivalTime().hashCode());
		result = prime * result + ((getDepartureTime() == null) ? 0 : getDepartureTime().hashCode());
		result = prime * result + ((getInspectionDate() == null) ? 0 : getInspectionDate().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Inspection)) {
			return false;
		}
		Inspection other = (Inspection) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getFacility() == null) {
			if (other.getFacility() != null) {
				return false;
			}
		} else if (!getFacility().equals(other.getFacility())) {
			return false;
		}
		if (getInspectionDate() == null) {
			if (other.getInspectionDate() != null) {
				return false;
			}
		} else if (!getInspectionDate().equals(other.getInspectionDate())) {
			return false;
		}
		if (getArrivalTime() == null) {
			if (other.getArrivalTime() != null) {
				return false;
			}
		} else if (!getArrivalTime().equals(other.getArrivalTime())) {
			return false;
		}
		if (getDepartureTime() == null) {
			if (other.getDepartureTime() != null) {
				return false;
			}
		} else if (!getDepartureTime().equals(other.getDepartureTime())) {
			return false;
		}
		return true;
	}

	@RequiredFieldValidator(message = "Findings are required.")
	public String getFindingsComment() {
		return findingsComment;
	}

	public void setFindingsComment(String findingsComment) {
		this.findingsComment = findingsComment;
	}

	public String getFollowupComment() {
		return followupComment;
	}

	public void setFollowupComment(String followupComment) {
		this.followupComment = followupComment;
	}

	@RequiredFieldValidator(message = "Resolved is required.")
	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public Set<InspectionChecklist> getInspectionChecklist() {
		return inspectionChecklist;
	}

	public void setInspectionChecklist(Set<InspectionChecklist> inspectionChecklist) {
		this.inspectionChecklist = inspectionChecklist;
	}
	
	public InspectionChecklist getCheckList(long checklistId) {
		
		InspectionChecklist checkList = null;
		for (InspectionChecklist _checkList : this.getInspectionChecklist()) {
			if (_checkList.getId().longValue() == checklistId) {
				checkList = _checkList;
				break;
			}
		}
		
		return checkList;
	}

}