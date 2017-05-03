package gov.utah.dts.det.ccl.model;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "UNLICENSED_COMPLAINT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnlicensedComplaint extends Complaint {
	
	@Column(name = "INVESTIGATION_COMPLETED_DATE")
	@Temporal(TemporalType.DATE)
	private Date investigationCompletedDate;
	
	@Column(name = "INVESTIGATION_DETAILS")
	private String investigationDetails;
	
	@Column(name = "SUBSTANTIATED")
	@Type(type = "yes_no")
	private Boolean substantiated = false;
	
	@Column(name = "FOLLOW_UP_NEEDED")
	@Type(type = "yes_no")
	private Boolean followUpNeeded = false;
	
	@Column(name = "ALL_FOLLOW_UPS_COMPLETED_DATE")
	@Temporal(TemporalType.DATE)
	private Date allFollowUpsCompletedDate;
	
	@OneToMany(mappedBy = "complaint", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Sort(type = SortType.NATURAL)
	private SortedSet<UnlicensedComplaintFollowUp> followUps = new TreeSet<UnlicensedComplaintFollowUp>();
	
	public UnlicensedComplaint() {
		
	}
	
	public Date getInvestigationCompletedDate() {
		return investigationCompletedDate;
	}
	
	public void setInvestigationCompletedDate(Date investigationCompletedDate) {
		this.investigationCompletedDate = investigationCompletedDate;
	}
	
	public String getInvestigationDetails() {
		return investigationDetails;
	}
	
	public void setInvestigationDetails(String investigationDetails) {
		this.investigationDetails = investigationDetails;
	}
	
	public Boolean getSubstantiated() {
		return substantiated;
	}
	
	public void setSubstantiated(Boolean substantiated) {
		this.substantiated = substantiated;
	}
	
	public Boolean getFollowUpNeeded() {
		return followUpNeeded;
	}
	
	public void setFollowUpNeeded(Boolean followUpNeeded) {
		this.followUpNeeded = followUpNeeded;
	}
	
	public Date getAllFollowUpsCompletedDate() {
		return allFollowUpsCompletedDate;
	}
	
	public void setAllFollowUpsCompletedDate(Date allFollowUpsCompletedDate) {
		this.allFollowUpsCompletedDate = allFollowUpsCompletedDate;
	}
	
	public SortedSet<UnlicensedComplaintFollowUp> getFollowUps() {
		return followUps;
	}
	
	public void setFollowUps(SortedSet<UnlicensedComplaintFollowUp> followUps) {
		this.followUps = followUps;
	}
	
	public UnlicensedComplaintFollowUp getFollowUp(Long id) {
		if (id != null) {
			for (UnlicensedComplaintFollowUp followUp : getFollowUps()) {
				if (followUp.getId().equals(id)) {
					return followUp;
				}
			}
		}
		return null;
	}
	
	public void addFollowUp(UnlicensedComplaintFollowUp followUp) {
		if (followUp != null) {
			followUp.setComplaint(this);
			getFollowUps().add(followUp);
		}
	}
	
	public void removeFollowUp(Long id) {
		for (Iterator<UnlicensedComplaintFollowUp> itr = getFollowUps().iterator(); itr.hasNext();) {
			UnlicensedComplaintFollowUp followUp = itr.next();
			if (followUp.getId().equals(id)) {
				itr.remove();
				break;
			}
		}
	}
	
	public void removeFollowUp(UnlicensedComplaintFollowUp followUp) {
		getFollowUps().remove(followUp);
	}
}