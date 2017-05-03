package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

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
@Table(name = "UNLIC_COMPL_FOLLOW_UP")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnlicensedComplaintFollowUp extends AbstractBaseEntity<Long> implements Serializable, Comparable<UnlicensedComplaintFollowUp> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UNLIC_COMPL_FOLLOW_UP_SEQ")
	@SequenceGenerator(name = "UNLIC_COMPL_FOLLOW_UP_SEQ", sequenceName = "UNLIC_COMPL_FOLLOW_UP_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ID", nullable = false, updatable = false)
	private UnlicensedComplaint complaint;
	
	@Column(name = "FOLLOW_UP_DATE")
	@Temporal(TemporalType.DATE)
	private Date followUpDate;
	
	@Column(name = "DETAILS")
	private String details;
	
	public UnlicensedComplaintFollowUp() {
		
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
	
	public UnlicensedComplaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(UnlicensedComplaint complaint) {
		this.complaint = complaint;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Follow up date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Follow up date is required.", shortCircuit = true)
		}
	)
	public Date getFollowUpDate() {
		return followUpDate;
	}
	
	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}
	
	@RequiredFieldValidator(message = "Follow up details are required.")
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getComplaint() == null ? 0 : (getComplaint().getId() == null ? getComplaint().hashCode() : getComplaint().getId().hashCode()));
		result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
		result = prime * result + ((getFollowUpDate() == null) ? 0 : getFollowUpDate().hashCode());
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
		if (!(obj instanceof UnlicensedComplaintFollowUp)) {
			return false;
		}
		UnlicensedComplaintFollowUp other = (UnlicensedComplaintFollowUp) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getComplaint() == null) {
			if (other.getComplaint() != null) {
				return false;
			}
		} else if (!getComplaint().equals(other.getComplaint())) {
			return false;
		}
		if (getDetails() == null) {
			if (other.getDetails() != null) {
				return false;
			}
		} else if (!getDetails().equals(other.getDetails())) {
			return false;
		}
		if (getFollowUpDate() == null) {
			if (other.getFollowUpDate() != null) {
				return false;
			}
		} else if (!getFollowUpDate().equals(other.getFollowUpDate())) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(UnlicensedComplaintFollowUp o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare((Complaint) getComplaint(), (Complaint) o.getComplaint(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getFollowUpDate(), o.getFollowUpDate(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}
}