package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.CompareUtils;
import gov.utah.dts.det.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_TAG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityTag extends AbstractAuditableEntity<Long> implements Serializable, Activatable, DateRange, Comparable<FacilityTag> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITY_SEQ")
	@SequenceGenerator(name = "FACILITY_SEQ", sequenceName = "FACILITY_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID", nullable = false, updatable = false)
	private Facility facility;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_ID", nullable = false)
	private PickListValue tag;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@Column(name = "METADATA")
	private String metadata;
	
	public FacilityTag() {
		
	}
	
	@Override
	public boolean isActive() {
		return DateUtils.isActive(this);
	}

	@Override
	public Date getEndDate() {
		return expirationDate;
	}
	
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Facility getFacility() {
		return facility;
	}
	
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public PickListValue getTag() {
		return tag;
	}
	
	public void setTag(PickListValue tag) {
		this.tag = tag;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Start date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Start date is required.", shortCircuit = true)
		}
	)
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Expiration date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Expiration date is required.", shortCircuit = true)
		}
	)
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public int compareTo(FacilityTag o) {
		if (this == o) {
			return 0;
		}
		int comp = CompareUtils.nullSafeComparableCompare(getFacility(), o.getFacility(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getStartDate(), o.getStartDate(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getExpirationDate(), o.getExpirationDate(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getTag(), o.getTag(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		
		return startDate.compareTo(o.getStartDate());
	}
}