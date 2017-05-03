package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "INSPECTION_CHECKLIST")
/* CKS: NOT SURE I WANT THESE ...
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Conversion
*/
public class InspectionChecklist extends AbstractBaseEntity<Long> implements Serializable, Comparable<InspectionChecklist> {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "INSP_CHKLST_SEQ")
	@SequenceGenerator(name = "INSP_CHKLST_SEQ", sequenceName = "INSP_CHKLST_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSPECTION_ID")
	private Inspection inspection;

	@OneToMany(mappedBy = "inspectionChecklist", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("sortOrder")
	private Set<InspectionChecklistResult> results = new TreeSet<InspectionChecklistResult>();

	@OneToMany(mappedBy = "inspectionChecklist", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("sortOrder")
	private Set<InspectionChecklistHeader> headers = new TreeSet<InspectionChecklistHeader>();
	
	@OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachment attachment = null;

	@Column(name = "COMMENTS")
	private String comments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column(name = "RESULT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date resultDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public Set<InspectionChecklistResult> getResults() {
		return results;
	}

	public void setResults(SortedSet<InspectionChecklistResult> results) {
		this.results = results;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getResultDate() {
		return resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	public void addResult(InspectionChecklistResult result) {
		result.setInspectionChecklist(this);
		results.add(result);
	}

	public void addHeader(InspectionChecklistHeader header) {
		header.setInspectionChecklist(this);
		header.setSortOrder(headers.size()+1);
		headers.add(header);
	}


	@Override
	public int compareTo(InspectionChecklist o) {
		if (this == o) {
			return 0;
		}
		int comp = 0;
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getComments(), o.getComments(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getResultDate(), o.getResultDate(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getUser().getId(), o.getUser().getId(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getAttachment(), o.getAttachment(), false);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<InspectionChecklistHeader> getHeaders() {
		return headers;
	}

	public List<String[]> getHeadersOnly() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		for (InspectionChecklistHeader header : this.getHeaders()) {
			list.add(new String[]{header.getItemName(), header.getItemLabel()});
		}
		
		return list;
	}

	public void setHeaders(Set<InspectionChecklistHeader> headers) {
		this.headers = headers;
	}
	
	public int getNonCompliantCount() {
		int r = 0;
		
		if (this.getResults() != null) {
			for (InspectionChecklistResult icr : getResults()) {
				if (icr.getResult().equals(InspectionChecklistResult.NON_COMPLIANT)) 
					r++;
			}
		}
		
		return r;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inspection == null) ? 0 : inspection.hashCode());
		result = prime * result
				+ ((resultDate == null) ? 0 : resultDate.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		InspectionChecklist other = (InspectionChecklist) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inspection == null) {
			if (other.inspection != null)
				return false;
		} else if (!inspection.equals(other.inspection))
			return false;
		if (resultDate == null) {
			if (other.resultDate != null)
				return false;
		} else if (!resultDate.equals(other.resultDate))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
