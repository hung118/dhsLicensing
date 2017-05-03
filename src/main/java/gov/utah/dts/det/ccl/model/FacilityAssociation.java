/**
 * 
 */
package gov.utah.dts.det.ccl.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
 @Table(name = "FACILITYASSOC")
 @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityAssociation extends AbstractBaseEntity<Long> implements Serializable, Activatable, DateRange {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITYASSOC_SEQ")
	@SequenceGenerator(name = "FACILITYASSOC_SEQ", sequenceName = "FACILITYASSOC_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENTFACID")
	private Facility parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHILDFACID")
	private Facility child;

	@Column(name = "BEGINDATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@Column(name = "ENDDATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	public FacilityAssociation() {
		
	}
	
	@Override
	public boolean isActive() {
		return DateUtils.isActive(this);
	}
	
	@Override
	public Date getStartDate() {
		return beginDate;
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

	public Facility getParent() {
		return parent;
	}

	public void setParent(Facility parent) {
		this.parent = parent;
	}

	@RequiredFieldValidator(message = "Facility is required.")
	public Facility getChild() {
		return child;
	}

	public void setChild(Facility child) {
		this.child = child;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Begin date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Begin date is required.", shortCircuit = true)
		}
	)
	public Date getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@ConversionErrorFieldValidator(message = "End date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}