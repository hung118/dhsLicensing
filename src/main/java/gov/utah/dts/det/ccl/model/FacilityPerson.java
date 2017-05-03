package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITYPERSON")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityPerson extends AbstractBaseEntity<Long> implements Serializable, Activatable, DateRange {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITYPERSON_SEQ")
	@SequenceGenerator(name = "FACILITYPERSON_SEQ", sequenceName = "FACILITYPERSON_SEQ")
	private Long id;
	
	@Column(name = "FACILITYID", insertable = false, updatable = false)
	private Long facilityId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "FACILITYID", insertable = true, updatable = false)
	private Facility facility;

	@Column(name = "PERSONID", insertable = false, updatable = false)
	private Long personId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "PERSONID", insertable = true, updatable = false)
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSONTYPE")
	private PickListValue type;
	
	@Column(name = "STARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(name = "ENDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	public FacilityPerson() {
		
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

	@VisitorFieldValidator(message = "&zwnj;")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PickListValue getType() {
		return type;
	}

	public void setType(PickListValue type) {
		this.type = type;
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

	@ConversionErrorFieldValidator(message = "End date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFacility() == null) ? 0 : (getFacility().getId() == null ? getFacility().hashCode() : getFacility().getId().hashCode()));
		result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
		result = prime * result + ((getPerson() == null) ? 0 : getPerson().hashCode());
		result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
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
		if (!(obj instanceof FacilityPerson)) {
			return false;
		}
		FacilityPerson other = (FacilityPerson) obj;
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
		if (getPerson() == null) {
			if (other.getPerson() != null) {
				return false;
			}
		} else if (!getPerson().equals(other.getPerson())) {
			return false;
		}
		if (getType() == null) {
			if (other.getType() != null) {
				return false;
			}
		} else if (!getType().equals(other.getType())) {
			return false;
		}
		if (getStartDate() == null) {
			if (other.getStartDate() != null) {
				return false;
			}
		} else if (!getStartDate().equals(other.getStartDate())) {
			return false;
		}
		if (getEndDate() == null) {
			if (other.getEndDate() != null) {
				return false;
			}
		} else if (!getEndDate().equals(other.getEndDate())) {
			return false;
		}
		return true;
	}

	public Long getFacilityId() {
		if (facilityId == null) {
			facilityId = getFacility().getId();
		}
		return facilityId;
	}

	public Long getPersonId() {
		if (personId == null) {
			personId = getPerson().getId();
		}
		return personId;
	}
}