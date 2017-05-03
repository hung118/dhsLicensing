package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_EXEMPTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exemption extends AbstractAuditableEntity<Long> implements Serializable, Activatable, DateRange {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITY_EXEMPTION_SEQ")
	@SequenceGenerator(name = "FACILITY_EXEMPTION_SEQ", sequenceName = "FACILITY_EXEMPTION_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXEMPTION_ID")
	private PickListValue exemption;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;
	
	public Exemption() {
		
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
	
	@RequiredFieldValidator(message = "Exemption is required.")
	public PickListValue getExemption() {
		return exemption;
	}
	
	public void setExemption(PickListValue exemption) {
		this.exemption = exemption;
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
		this.startDate = org.apache.commons.lang.time.DateUtils.truncate(startDate, Calendar.DATE);
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
		this.expirationDate = org.apache.commons.lang.time.DateUtils.truncate(expirationDate, Calendar.DATE);
	}

	@Override
	public boolean isActive() {
		return DateUtils.isActive(this);
	}

	@Override
	public Date getEndDate() {
		return expirationDate;
	}
}