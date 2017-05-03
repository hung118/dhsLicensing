package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.TransactionType;
import gov.utah.dts.det.model.OwnedEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "INSPECTION_VIEW")
public class InspectionView implements Serializable, OwnedEntity {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;

	@ManyToOne
	@JoinColumn(name = "LICENSE_ID")
	private License license;

	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inspectionDate;
	
	@Column(name = "INS_DURATION")
	private Integer duration;
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private Inspection.State state;
	
	@Column(name = "CREATED_BY_ID")
	private Long createdById;
	
	@ManyToOne
	@JoinColumn(name = "LICENSING_SPECIALIST_ID")
	private Person licensingSpecialist;
	
	@Column(name = "FOLLOW_UP_NEEDED")
	@Type(type = "yes_no")
	private boolean followUpNeeded;
	
	@Column(name = "PRIMARY_INSPECTION_TYPE")
	private String primaryInspectionType;
	
	@Column(name = "PRIMARY_TYPE_ABBREV")
	private String primaryTypeAbbrev;
	
	@Column(name = "OTHER_INSPECTION_TYPES")
	private String otherInspectionTypes;
	
	@Column(name = "OTHER_ITYPES_ABBREV")
	private String otherTypesAbbrev;
	
	@Column(name = "CITED_FINDINGS")
	@Type(type = "yes_no")
	private boolean citedFindings;
	
	@Column(name = "TA_FINDINGS")
	@Type(type = "yes_no")
	private boolean taFindings;
	
	@Column(name = "UNDER_APPEAL")
	@Type(type = "yes_no")
	private boolean underAppeal;
	
	@Column(name = "IS_ANNOUNCED")
	@Type(type = "yes_no")
	private boolean announced;
	
	@Column(name = "CMP_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date cmpDueDate;
	
	@Column(name = "CMP_AMOUNT")
	private Double cmpAmount;
	
	@Column(name = "TOTAL_REDUCTIONS")
	private Double totalReductions;
	
	@Column(name = "TOTAL_PAYMENTS")
	private Double totalPayments;
	
	@Column(name = "OUTSTANDING")
	private Double outstanding;
	
//	@Column(name = "DATEPAIDINFULL")
//	@Temporal(TemporalType.DATE)
//	private Date datePaidInFull;
	
	@OneToMany(mappedBy = "inspectionView", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private Set<CmpTransaction> cmpTransactions = new HashSet<CmpTransaction>();
	
	@Transient
	private List<CmpTransaction> reductions;
	
	@Transient
	private List<CmpTransaction> payments;
	
	public InspectionView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}
	
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public Inspection.State getState() {
		return state;
	}
	
	public void setState(Inspection.State state) {
		this.state = state;
	}
	
	@Override
	public Long getCreatedById() {
		return createdById;
	}
	
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	
	public Person getLicensingSpecialist() {
		return licensingSpecialist;
	}
	
	public void setLicensingSpecialist(Person licensingSpecialist) {
		this.licensingSpecialist = licensingSpecialist;
	}

	public boolean isFollowUpNeeded() {
		return followUpNeeded;
	}

	public void setFollowUpNeeded(boolean followUpNeeded) {
		this.followUpNeeded = followUpNeeded;
	}

	public String getPrimaryInspectionType() {
		return primaryInspectionType;
	}

	public void setPrimaryInspectionType(String primaryInspectionType) {
		this.primaryInspectionType = primaryInspectionType;
	}

	public String getPrimaryTypeAbbrev() {
		return primaryTypeAbbrev;
	}

	public void setPrimaryTypeAbbrev(String primaryTypeAbbrev) {
		this.primaryTypeAbbrev = primaryTypeAbbrev;
	}

	public String getOtherInspectionTypes() {
		return otherInspectionTypes;
	}

	public void setOtherInspectionTypes(String otherInspectionTypes) {
		this.otherInspectionTypes = otherInspectionTypes;
	}

	public String getOtherTypesAbbrev() {
		return otherTypesAbbrev;
	}

	public void setOtherTypesAbbrev(String otherTypesAbbrev) {
		this.otherTypesAbbrev = otherTypesAbbrev;
	}

	public boolean isCitedFindings() {
		return citedFindings;
	}

	public void setCitedFindings(boolean citedFindings) {
		this.citedFindings = citedFindings;
	}

	public boolean isTaFindings() {
		return taFindings;
	}

	public void setTaFindings(boolean taFindings) {
		this.taFindings = taFindings;
	}

	public boolean isUnderAppeal() {
		return underAppeal;
	}

	public void setUnderAppeal(boolean underAppeal) {
		this.underAppeal = underAppeal;
	}

	public boolean isAnnounced() {
		return announced;
	}

	public void setAnnounced(boolean announced) {
		this.announced = announced;
	}
	
	public Date getCmpDueDate() {
		return cmpDueDate;
	}
	
	public void setCmpDueDate(Date cmpDueDate) {
		this.cmpDueDate = cmpDueDate;
	}
	
	public Double getCmpAmount() {
		return cmpAmount;
	}
	
	public void setCmpAmount(Double cmpAmount) {
		this.cmpAmount = cmpAmount;
	}
	
	public Double getTotalReductions() {
		return totalReductions;
	}
	
	public void setTotalReductions(Double totalReductions) {
		this.totalReductions = totalReductions;
	}
	
	public Double getTotalPayments() {
		return totalPayments;
	}
	
	public void setTotalPayments(Double totalPayments) {
		this.totalPayments = totalPayments;
	}
	
	public Double getOutstanding() {
		return outstanding;
	}
	
	public void setOutstanding(Double outstanding) {
		this.outstanding = outstanding;
	}
	
	public Set<CmpTransaction> getCmpTransactions() {
		return cmpTransactions;
	}
	
	public void setCmpTransactions(Set<CmpTransaction> cmpTransactions) {
		this.cmpTransactions = cmpTransactions;
	}
	
	public List<CmpTransaction> getReductions() {
		filterCmpTransactions();
		return reductions;
	}
	
	public List<CmpTransaction> getPayments() {
		filterCmpTransactions();
		return payments;
	}
	
	private void filterCmpTransactions() {
		if (reductions == null || payments == null) {
			reductions = new ArrayList<CmpTransaction>();
			payments = new ArrayList<CmpTransaction>();
			for (CmpTransaction trans : cmpTransactions) {
				if (trans.getType() == TransactionType.REDUCTION) {
					reductions.add(trans);
				} else {
					payments.add(trans);
				}
			}
			Collections.sort(reductions);
			Collections.sort(payments);
		}
	}
}