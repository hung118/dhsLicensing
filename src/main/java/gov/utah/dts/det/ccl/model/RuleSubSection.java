package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.RuleCategory;
import gov.utah.dts.det.ccl.model.view.RuleView;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "CCLRULESUBSECTION")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RuleSubSection extends AbstractBaseEntity<Long> implements Serializable, Activatable, Comparable<RuleSubSection> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CCLRULESUBSECTION_SEQ")
	@SequenceGenerator(name = "CCLRULESUBSECTION_SEQ", sequenceName = "CCLRULESUBSECTION_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID", insertable = false, updatable = false)
	private RuleView ruleView;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTIONID")
	private RuleSection section;

	@Column(name = "SUBSECTIONNUMBER")
	private String number;
	
	@Column(name = "SUBSECTIONNAME")
	private String name;
	
	@Column(name = "NOFOLLOWUP")
	@Type(type = "yes_no")
	private boolean noFollowUp = false;
	
	@Column(name = "PAPERWORKREQUIRED")
	@Type(type = "yes_no")
	private boolean paperworkRequired = false;
	
	@Column(name = "DONTISSUEFINDINGS")
	@Type(type = "yes_no")
	private boolean dontIssueFindings = false;
	
	@Column(name = "CATEGORY")
	@Type(type = "RuleCategory")
	private RuleCategory category;
	
	@Column(name = "ISACTIVE")
	@Type(type = "yes_no")
	private boolean active = true;
	
	@Column(name = "SORTORDER")
	private Double sortOrder;
	
	@Column(name = "RULE_CONTENT")
	private String ruleContent;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "VERSION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date versionDate;
	
	@Column(name = "REFERENCE_URL")
	private String referenceUrl;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "TEXT_FILE_ID")
	private FilesystemFile textFile;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RATIONALE_FILE_ID")
	private FilesystemFile rationaleFile;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ENFORCEMENT_FILE_ID")
	private FilesystemFile enforcementFile;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "INFO_FILE_ID")
	private FilesystemFile infoFile;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RULE_TEXT_ID")
	private FilesystemFile ruleText;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "RULE_DETAILS_ID")
	private FilesystemFile ruleDetails;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "CCLRULEFINDING",
		joinColumns = @JoinColumn(name = "RULEID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "FINDINGID", referencedColumnName = "ID")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<FindingCategoryPickListValue> findingCategories = new ArrayList<FindingCategoryPickListValue>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "CCLRULENONCOMLEVEL",
		joinColumns = @JoinColumn(name = "RULEID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "NONCOMLEVELID", referencedColumnName = "ID")
	)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<PickListValue> nonComplianceLevels = new ArrayList<PickListValue>();
	
	private transient String ruleNumber;
	
	public RuleSubSection() {
		
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
	
	public RuleView getRuleView() {
		return ruleView;
	}
	
	public void setRuleView(RuleView ruleView) {
		this.ruleView = ruleView;
	}

	public RuleSection getSection() {
		return section;
	}

	public void setSection(RuleSection section) {
		this.section = section;
	}
	
	public String getGeneratedRuleNumber() {
		if (ruleNumber == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getSection().getSectionBase());
			sb.append("-");
			sb.append(getSection().getNumber());
			sb.append(".");
			sb.append(getNumber());
			ruleNumber = sb.toString();
		}
		return ruleNumber;
	}
	
	public String getRuleNumber() {
		if (ruleView != null) {
			return ruleView.getRuleNumber();
		}
		return null;
	}

	@RequiredStringValidator(message = "Rule number is required.")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@RequiredStringValidator(message = "Rule description is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public FilesystemFile getTextFile() {
		return textFile;
	}
	
	public void setTextFile(FilesystemFile textFile) {
		this.textFile = textFile;
	}
	
	public FilesystemFile getRationaleFile() {
		return rationaleFile;
	}
	
	public void setRationaleFile(FilesystemFile rationaleFile) {
		this.rationaleFile = rationaleFile;
	}
	
	public FilesystemFile getEnforcementFile() {
		return enforcementFile;
	}
	
	public void setEnforcementFile(FilesystemFile enforcementFile) {
		this.enforcementFile = enforcementFile;
	}
	
	public FilesystemFile getInfoFile() {
		return infoFile;
	}
	
	public void setInfoFile(FilesystemFile infoFile) {
		this.infoFile = infoFile;
	}
	
	public FilesystemFile getRuleText() {
		return ruleText;
	}
	
	public void setRuleText(FilesystemFile ruleText) {
		this.ruleText = ruleText;
	}
	
	public FilesystemFile getRuleDetails() {
		return ruleDetails;
	}
	
	public void setRuleDetails(FilesystemFile ruleDetails) {
		this.ruleDetails = ruleDetails;
	}

	public boolean isNoFollowUp() {
		return noFollowUp;
	}

	public void setNoFollowUp(boolean noFollowUp) {
		this.noFollowUp = noFollowUp;
	}
	
	public boolean isPaperworkRequired() {
		return paperworkRequired;
	}
	
	public void setPaperworkRequired(boolean paperworkRequired) {
		this.paperworkRequired = paperworkRequired;
	}
	
	public boolean isDontIssueFindings() {
		return dontIssueFindings;
	}
	
	public void setDontIssueFindings(boolean dontIssueFindings) {
		this.dontIssueFindings = dontIssueFindings;
	}
	
	@RequiredStringValidator(message = "Rule category is required.")
	public RuleCategory getCategory() {
		return category;
	}
	
	public void setCategory(RuleCategory category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public List<FindingCategoryPickListValue> getFindingCategories() {
		return findingCategories;
	}
	
	public void setFindingCategories(List<FindingCategoryPickListValue> findingCategories) {
		this.findingCategories = findingCategories;
	}
	
	public List<PickListValue> getNonComplianceLevels() {
		return nonComplianceLevels;
	}
	
	public void setNonComplianceLevels(List<PickListValue> nonComplianceLevels) {
		this.nonComplianceLevels = nonComplianceLevels;
	}
	
	@Override
	public String toString() {
		return "Rule Subsection: "+number+"\n"+this.ruleContent+"\n###\n";
	}
	
	@Override
	public int compareTo(RuleSubSection o) {
		if (this == o) {
			return 0;
		}
		int comp = CompareUtils.nullSafeComparableCompare(getSection(), o.getSection(), false);
		if (comp == 0) {
			comp = Boolean.valueOf(isActive()).compareTo(Boolean.valueOf(o.isActive())) * -1; //reverse this because I wan't inactive to be sorted after active
		}
		if (comp == 0) {
			//null sort order's should be sorted after ones with a sort order
			comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getNumber(), o.getNumber(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSection() == null) ? 0 : (getSection().getId() == null ? getSection().hashCode() : getSection().getId().hashCode()));
		result = prime * result + (isActive() ? 1231 : 1237);
		result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
		result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
		if (!(obj instanceof RuleSubSection)) {
			return false;
		}
		RuleSubSection other = (RuleSubSection) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getSection() == null) {
			if (other.getSection() != null) {
				return false;
			}
		} else if (!getSection().equals(other.getSection())) {
			return false;
		}
		if (isActive() != other.isActive()) {
			return false;
		}
		if (getSortOrder() == null) {
			if (other.getSortOrder() != null) {
				return false;
			}
		} else if (!getSortOrder().equals(other.getSortOrder())) {
			return false;
		}
		if (getNumber() == null) {
			if (other.getNumber() != null) {
				return false;
			}
		} else if (!getNumber().equals(other.getNumber())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	
	public boolean isDifferent(RuleSubSection b) {
		
		int r = 0;
		if (this.name != null && !this.name.equals(b.name)) r++;
		if (b.name != null && !b.name.equals(this.name)) r++;

		if (this.number != null && !this.number.equals(b.number)) r++;
		if (b.number != null && !b.number.equals(this.number)) r++;
		
		if (this.ruleContent != null && !this.ruleContent.equals(b.ruleContent)) r++;
		if (b.ruleContent != null && !b.ruleContent.equals(this.ruleContent)) r++;
		
		return (r > 0);
	}
	
	public boolean isNewer(RuleSubSection b) {
		return (b.getVersionDate().after(this.getVersionDate()));
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}
}