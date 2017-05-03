package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.view.RuleView;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "RULE_CROSS_REFERENCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RuleCrossReference extends AbstractBaseEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "RULE_CROSS_REFERENCE_SEQ")
	@SequenceGenerator(name = "RULE_CROSS_REFERENCE_SEQ", sequenceName = "RULE_CROSS_REFERENCE_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "OLD_RULE_ID")
	private RuleView oldRule;
	
	@ManyToOne
	@JoinColumn(name = "NEW_RULE_ID")
	private RuleView newRule;
	
	public RuleCrossReference() {
		
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
	
	@RequiredFieldValidator(message = "Old rule is required.")
	public RuleView getOldRule() {
		return oldRule;
	}
	
	public void setOldRule(RuleView oldRule) {
		this.oldRule = oldRule;
	}
	
	@RequiredFieldValidator(message = "New rule is required.")
	public RuleView getNewRule() {
		return newRule;
	}
	
	public void setNewRule(RuleView newRule) {
		this.newRule = newRule;
	}
}