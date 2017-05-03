package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.RuleCategory;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "CCLRULESECTION")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RuleSection extends AbstractBaseEntity<Long> implements Serializable, Activatable, Comparable<RuleSection> {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CCLRULESECTION_SEQ")
	@SequenceGenerator(name = "CCLRULESECTION_SEQ", sequenceName = "CCLRULESECTION_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "RULEID")
	private Rule rule;
	
	@Column(name = "SECTIONNUMBER")
	private int number;

	@Column(name = "SECTION_BASE")
	private int sectionBase;
	
	@Column(name = "SECTIONNAME")
	private String name;

	@Column(name = "SECTION_TITLE")
	private String title;

	@Column(name = "SECTION_COMMENT")
	private String comment;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CATEGORY")
	@Type(type = "RuleCategory")
	private RuleCategory category;
	
	@Column(name = "VERSIONDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date versionDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "GENERAL_INFO_FILE_ID")
	private FilesystemFile generalInfoFile;
	
	@Column(name = "ISACTIVE")
	@Type(type = "yes_no")
	private boolean active = true;
	
	@Column(name = "SORTORDER")
	private Double sortOrder;
	
	@Column(name = "REFERENCE_URL")
	private String referenceUrl;

	@OneToMany(mappedBy = "section", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = false)
	@Sort(type = SortType.NATURAL)
//	@OrderBy(clause = "ISACTIVE desc, SORTORDER, SUBSECTIONNUMBER")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private SortedSet<RuleSubSection> subSections = new TreeSet<RuleSubSection>();
	
	public RuleSection() {
		
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

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@RequiredFieldValidator(message = "Rule number is required.")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@RequiredStringValidator(message = "Rule name is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ConversionErrorFieldValidator(message = "Version date is not a valid date. (MM/DD/YYYY)")
	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	
	public FilesystemFile getGeneralInfoFile() {
		return generalInfoFile;
	}
	
	public void setGeneralInfoFile(FilesystemFile generalInfoFile) {
		this.generalInfoFile = generalInfoFile;
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
	
	public SortedSet<RuleSubSection> getSubSections() {
		return subSections;
	}
	
	public void setSubSections(SortedSet<RuleSubSection> subSections) {
		this.subSections = subSections;
	}
	
	public void addSubSection(RuleSubSection subSection) {
		if (subSection != null) {
			subSection.setSection(this);
			subSections.add(subSection);
		}
	}
	
	public String toString() {
		return "Rule Section - " + name + " " + number;
	}
	
	@Override
	public int compareTo(RuleSection o) {
		if (this == o) {
			return 0;
		}
		int comp = CompareUtils.nullSafeComparableCompare(getRule(), o.getRule(), false);
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
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((generalInfoFile == null) ? 0 : generalInfoFile.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result	+ ((sortOrder == null) ? 0 : sortOrder.hashCode());
		result = prime * result	+ ((subSections == null) ? 0 : subSections.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleSection other = (RuleSection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		if (subSections == null) {
			if (other.subSections != null)
				return false;
		} else if (!subSections.equals(other.subSections))
			return false;

		return true;
	}

	/**
	 * If the name or number is different this will return true, otherwise false
	 * @param b
	 * @return if the name and/or number is different
	 */
	public boolean isDifferent(RuleSection b) {
		int r = 0;
		if (this.name != null && !this.name.trim().equals(b.name.trim())) r++;
		if (b.name != null && !b.name.trim().equals(this.name.trim())) r++;

		if (this.number != b.number) r++;
		if (this.sectionBase != b.sectionBase) r++;
		
		return (r > 0);
	}
	
	public int getSubSectionCount() {
		if (this.subSections == null)
			return 0;
		return this.subSections.size();
	}

	public int getPendingCount() {
		if (this.subSections == null)
			return 0;
		else {
			int r = 0;
			for (RuleSubSection sub : this.subSections) {
				if (sub.getCategory().equals(RuleCategory.PENDING))
					r++;
			}
			return r;
		}
	}
	
	public boolean getHasPending() {
		return (getPendingCount() > 0);
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public RuleCategory getCategory() {
		return category;
	}

	public void setCategory(RuleCategory category) {
		this.category = category;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@RequiredFieldValidator(message = "Rule section base is required.")
	public int getSectionBase() {
		return sectionBase;
	}

	public void setSectionBase(int sectionBase) {
		this.sectionBase = sectionBase;
	}
}