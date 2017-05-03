/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.view.FacilitySearchView;
import gov.utah.dts.det.model.AbstractBaseEntity;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITYACCREDITATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accreditation extends AbstractBaseEntity<Long> implements Serializable, Activatable, DateRange {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITYACCREDITATION_SEQ")
	@SequenceGenerator(name = "FACILITYACCREDITATION_SEQ", sequenceName = "FACILITYACCREDITATION_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITYID")
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITYID", insertable = false, updatable = false)
	private FacilitySearchView facilitySearchView;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCYID")
	private PickListValue agency;

	@Column(name = "STARTDATE")
	private Date startDate;

	@Column(name = "EXPIRATIONDATE")
	private Date endDate;
	
	public Accreditation() {
		
	}
	
	@Override
	public boolean isActive() {
		return DateUtils.isActive(this);
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
	
	public FacilitySearchView getFacilitySearchView() {
		return facilitySearchView;
	}
	
	public void setFacilitySearchView(FacilitySearchView facilitySearchView) {
		this.facilitySearchView = facilitySearchView;
	}

	@RequiredFieldValidator(message = "Accreditation is required.")
	public PickListValue getAgency() {
		return agency;
	}

	public void setAgency(PickListValue agency) {
		this.agency = agency;
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

	@ConversionErrorFieldValidator(message = "Expiration date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}