package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.RuleSubSection;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_ALLEGATION_VIEW")
@Immutable
public class ComplaintAllegationView implements Serializable, Comparable<ComplaintAllegationView> {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "COMPLAINT_ID")
	private Complaint complaint;

	@ManyToOne
	@JoinColumn(name = "RULE_ID")
	private RuleSubSection rule;
	
	@Column(name = "SUBSTANTIATED")
	@Type(type = "yes_no")
	private Boolean substantiated;
	
	@Column(name = "DECLARATIVE_STATEMENT")
	private String declarativeStatement;

	@Column(name = "ADDITIONAL_INFORMATION")
	private String additionalInformation;
	
	@Column(name = "SUPPORTING_EVIDENCE")
	@Lob
	private String supportingEvidence;
	
	@Column(name = "UNDER_APPEAL")
	@Type(type = "yes_no")
	private Boolean underAppeal;
	
	public ComplaintAllegationView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
	public RuleSubSection getRule() {
		return rule;
	}
	
	public void setRule(RuleSubSection rule) {
		this.rule = rule;
	}

	public Boolean getSubstantiated() {
		return substantiated;
	}

	public void setSubstantiated(Boolean substantiated) {
		this.substantiated = substantiated;
	}
	
	public String getDeclarativeStatement() {
		return declarativeStatement;
	}
	
	public void setDeclarativeStatement(String declarativeStatement) {
		this.declarativeStatement = declarativeStatement;
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	public String getSupportingEvidence() {
		return supportingEvidence;
	}

	public void setSupportingEvidence(String supportingEvidence) {
		this.supportingEvidence = supportingEvidence;
	}
	
	public Boolean getUnderAppeal() {
		return underAppeal;
	}

	public void setUnderAppeal(Boolean underAppeal) {
		this.underAppeal = underAppeal;
	}
	
	@Override
	public int compareTo(ComplaintAllegationView o) {
		return getRule().compareTo(o.getRule());
	}
}