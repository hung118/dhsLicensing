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

@SuppressWarnings("serial")
@Entity
@Table(name = "INSPECTION_CHECKLIST_RESULT")
/* CKS: NOT SURE I WANT THESE ...
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Conversion
*/
public class InspectionChecklistResult extends AbstractBaseEntity<Long> implements Serializable, Comparable<InspectionChecklistResult> {

	public static final String COMPLIANT = "CO";
	public static final String NON_COMPLIANT = "NC";
	public static final String NOT_APPLICABLE = "NA";
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "INSP_CHKLST_RSLT_SEQ")
	@SequenceGenerator(name = "INSP_CHKLST_RSLT_SEQ", sequenceName = "INSP_CHKLST_RSLT_SEQ")
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "INSPECTION_CHECKLIST_ID")
	private InspectionChecklist inspectionChecklist;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID")
	private RuleSection section;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_SECTION_ID")
	private RuleSubSection subSection;
	
	@Column(name = "RESULT")
	private String result;
	
	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "SORT_ORDER")
	private int sortOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InspectionChecklist getInspectionChecklist() {
		return inspectionChecklist;
	}

	public void setInspectionChecklist(InspectionChecklist inspectionChecklist) {
		this.inspectionChecklist = inspectionChecklist;
	}

	public RuleSection getSection() {
		return section;
	}

	public void setSection(RuleSection section) {
		this.section = section;
	}

	public RuleSubSection getSubSection() {
		return subSection;
	}

	public void setSubSection(RuleSubSection subSection) {
		this.subSection = subSection;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	@Override
	public int compareTo(InspectionChecklistResult o) {
		if (this == o) {
			return 0;
		}
		int comp = 0;
		if (comp == 0) {
			//null sort order's should be sorted after ones with a sort order
			comp = CompareUtils.nullSafeComparableCompare(getComments(), o.getComments(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getResult(), o.getResult(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getSortOrder(), o.getSortOrder(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}

	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime
//				* result
//				+ ((inspectionChecklist == null) ? 0 : inspectionChecklist
//						.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + sortOrder;
		result = prime * result
				+ ((subSection == null) ? 0 : subSection.hashCode());
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
		InspectionChecklistResult other = (InspectionChecklistResult) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inspectionChecklist == null) {
			if (other.inspectionChecklist != null)
				return false;
		} else if (!inspectionChecklist.equals(other.inspectionChecklist))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (sortOrder != other.sortOrder)
			return false;
		if (subSection == null) {
			if (other.subSection != null)
				return false;
		} else if (!subSection.equals(other.subSection))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InspectionChecklistResult [id=" + id + ", inspectionChecklist="
				+ inspectionChecklist + ", section=" + section
				+ ", subSection=" + subSection + ", result=" + result
				+ ", comments=" + comments + ", sortOrder=" + sortOrder + "]";
	}
	
}