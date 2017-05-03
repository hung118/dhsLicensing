package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.view.ComplaintAllegationView;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_ALLEGATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Allegation extends AbstractBaseEntity<Long> implements Serializable {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMPLAINT_ALLEGATION_SEQ")
	@SequenceGenerator(name = "COMPLAINT_ALLEGATION_SEQ", sequenceName = "COMPLAINT_ALLEGATION_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID", insertable = false, updatable = false)
	private ComplaintAllegationView allegationView;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ID", nullable = false, updatable = false)
	private Complaint complaint;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RULE_ID")
	private RuleSubSection rule;
	
	@Column(name = "SUPPORTING_EVIDENCE")
	@Lob
	private String supportingEvidence;
	
	public Allegation() {
		
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
	
	public ComplaintAllegationView getAllegationView() {
		return allegationView;
	}
	
	public void setAllegationView(ComplaintAllegationView allegationView) {
		this.allegationView = allegationView;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

	@RequiredFieldValidator(message = "Rule # is required.")
	public RuleSubSection getRule() {
		return rule;
	}

	public void setRule(RuleSubSection rule) {
		this.rule = rule;
	}

	public String getSupportingEvidence() {
		return supportingEvidence;
	}

	public void setSupportingEvidence(String supportingEvidence) {
		this.supportingEvidence = supportingEvidence;
	}
}