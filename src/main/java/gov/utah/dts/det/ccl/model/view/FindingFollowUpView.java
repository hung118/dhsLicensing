package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.RuleSubSection;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "FINDING_FOLLOW_UP_VW")
@Immutable
public class FindingFollowUpView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "RULE_ID")
	private RuleSubSection rule;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CORRECTION_DATE")
	private Date correctionDate;
	
	public FindingFollowUpView() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public RuleSubSection getRule() {
		return rule;
	}
	
	public void setRule(RuleSubSection rule) {
		this.rule = rule;
	}
	
	public Date getCorrectionDate() {
		return correctionDate;
	}
	
	public void setCorrectionDate(Date correctionDate) {
		this.correctionDate = correctionDate;
	}
}