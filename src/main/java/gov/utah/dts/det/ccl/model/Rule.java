package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import javax.persistence.Temporal;

@SuppressWarnings("serial")
@Entity
@Table(name = "CCLRULE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule extends AbstractBaseEntity<Long> implements Serializable, Activatable, Comparable<Rule> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CCLRULE_SEQ")
	@SequenceGenerator(name = "CCLRULE_SEQ", sequenceName = "CCLRULE_SEQ")
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "FACILITYTYPE")
	@Fetch(FetchMode.JOIN)
	private PickListValue facilityType;
	
	@Column(name = "RULENUMBER")
	private String number;
	
	@Column(name = "RULENAME")
	private String name;
	
	@Column(name = "ISACTIVE")
	@Type(type = "yes_no")
	private boolean active = true;
	
	@Column(name = "SORTORDER")
	private Double sortOrder;

	@Column(name = "DOWNLOAD_URL")
	private String downloadUrl;

	@Column(name = "DOWNLOAD_FREQUENCY")
	private String downloadFrequency;
	
	@Column(name = "REFERENCE_URL")
	private String referenceUrl;

	@Column(name = "LAST_DOWNLOAD")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date lastDownload;

	@OneToMany(mappedBy = "rule", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Sort(type = SortType.UNSORTED)
	@OrderBy("number, sortOrder")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<RuleSection> sections = new TreeSet<RuleSection>();
	
	public Rule() {
		
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

	@RequiredFieldValidator(message = "Facility type is required.")
	public PickListValue getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(PickListValue facilityType) {
		this.facilityType = facilityType;
	}

	@RequiredStringValidator(message = "Facility rule number is required.")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@RequiredStringValidator(message = "Facility rule name is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public Set<RuleSection> getSections() {
		return sections;
	}
	
	public void setSections(SortedSet<RuleSection> sections) {
		this.sections = sections;
	}
	
	public void addSection(RuleSection section) {
		if (section != null) {
			section.setRule(this);
			sections.add(section);
		}
	}
	
	@Override
	public String toString() {
		return "Rule: " + getNumber() + " id: " + getId();
	}
	
	@Override
	public int compareTo(Rule o) {
		if (this == o) {
			return 0;
		}
		int comp = 0;
		comp = Boolean.valueOf(isActive()).compareTo(Boolean.valueOf(o.isActive())) * -1; //reverse this because I wan't inactive to be sorted after active
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
		result = prime * result + (isActive() ? 1231 : 1237);
		result = prime * result + ((getFacilityType() == null) ? 0 : getFacilityType().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
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
		if (!(obj instanceof Rule)) {
			return false;
		}
		Rule other = (Rule) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		if (getNumber() == null) {
			if (other.getNumber() != null) {
				return false;
			}
		} else if (!getNumber().equals(other.getNumber())) {
			return false;
		}
		if (isActive() != other.isActive()) {
			return false;
		}
		if (getFacilityType() == null) {
			if (other.getFacilityType() != null) {
				return false;
			}
		} else if (!getFacilityType().equals(other.getFacilityType())) {
			return false;
		}
		return true;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadFrequency() {
		return downloadFrequency;
	}

	public void setDownloadFrequency(String downloadFrequency) {
		this.downloadFrequency = downloadFrequency;
	}

	public Date getLastDownload() {
		return lastDownload;
	}

	public void setLastDownload(Date lastDownload) {
		this.lastDownload = lastDownload;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}
}