package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.enums.RuleCategory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "RULE_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RuleView implements Serializable {

	@Id
	@Column(name = "SUBSECTION_ID")
	private Long id;
	
	@Column(name = "SECTION_ID")
	private Long sectionId;
	
	@Column(name = "RULE_ID")
	private Long ruleId;
	
	@Column(name = "RULE_NUMBER")
	private String ruleNumber;

	@Column(name = "BASE_RULE_NUMBER")
	private String baseRuleNumber;

	@Column(name = "SECTION_NUMBER")
	private String sectionNumber;

	@Column(name = "SUBSECTION_NUMBER")
	private String subSectionNumber;

	@Column(name = "SECTION_ORDER")
	private Double sectionOrder;
	
	@Column(name = "SUBSECTION_ORDER")
	private Double subSectionOrder;
	
	@Column(name = "RULE_ACTIVE")
	@Type(type = "yes_no")
	private boolean ruleActive;
	
	@Column(name = "SECTION_ACTIVE")
	@Type(type = "yes_no")
	private boolean sectionActive;
	
	@Column(name = "SUBSECTION_ACTIVE")
	@Type(type = "yes_no")
	private boolean subSectionActive;
	
	@Column(name = "SECTION_CATEGORY")
	@Type(type = "RuleCategory")
	private RuleCategory sectionCategory;

	@Column(name = "SUBSECTION_CATEGORY")
	@Type(type = "RuleCategory")
	private RuleCategory subSectionCategory;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "RULE_TEXT")
	private String ruleText;
	
	public RuleView() {
		
	}
	
	@JsonIgnore
	public Long getPk() {
		return id;
	}

	public void setPk(Long pk) {
		this.id = pk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public Long getSectionId() {
		return sectionId;
	}
	
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	
	@JsonIgnore
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	@JsonIgnore
	public Double getSectionOrder() {
		return sectionOrder;
	}

	public void setSectionOrder(Double sectionOrder) {
		this.sectionOrder = sectionOrder;
	}

	@JsonIgnore
	public Double getSubSectionOrder() {
		return subSectionOrder;
	}

	public void setSubSectionOrder(Double subSectionOrder) {
		this.subSectionOrder = subSectionOrder;
	}

	@JsonIgnore
	public boolean isRuleActive() {
		return ruleActive;
	}

	public void setRuleActive(boolean ruleActive) {
		this.ruleActive = ruleActive;
	}

	@JsonIgnore
	public boolean isSectionActive() {
		return sectionActive;
	}

	public void setSectionActive(boolean sectionActive) {
		this.sectionActive = sectionActive;
	}

	@JsonIgnore
	public boolean isSubSectionActive() {
		return subSectionActive;
	}

	public void setSubSectionActive(boolean subSectionActive) {
		this.subSectionActive = subSectionActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public String getBaseRuleNumber() {
		return baseRuleNumber;
	}

	public void setBaseRuleNumber(String baseRuleNumber) {
		this.baseRuleNumber = baseRuleNumber;
	}

	@JsonIgnore
	public String getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	@JsonIgnore
	public String getSubSectionNumber() {
		return subSectionNumber;
	}

	public void setSubSectionNumber(String subSectionNumber) {
		this.subSectionNumber = subSectionNumber;
	}

	@JsonIgnore
	public RuleCategory getSectionCategory() {
		return sectionCategory;
	}

	public void setSectionCategory(RuleCategory sectionCategory) {
		this.sectionCategory = sectionCategory;
	}

	@JsonIgnore
	public RuleCategory getSubSectionCategory() {
		return subSectionCategory;
	}

	public void setSubSectionCategory(RuleCategory subSectionCategory) {
		this.subSectionCategory = subSectionCategory;
	}

	public String getRuleText() {
		return ruleText;
	}

	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}

}