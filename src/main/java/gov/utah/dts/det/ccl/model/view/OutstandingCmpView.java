package gov.utah.dts.det.ccl.model.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_OUTSTANDING_CMP_VIEW")
@Immutable
public class OutstandingCmpView extends BaseFacilityAlertView {

	@Id
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date inspectionDate;
	
	@Column(name = "CMP_AMOUNT")
	private Long cmpAmount;
	
	@Column(name = "OUTSTANDING")
	private Long outstanding;
	
	@Column(name = "CMP_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date cmpDueDate;
	
	public OutstandingCmpView() {
		
	}

	public Long getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public Date getInspectionDate() {
		return inspectionDate;
	}
	
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public Long getCmpAmount() {
		return cmpAmount;
	}

	public void setCmpAmount(Long cmpAmount) {
		this.cmpAmount = cmpAmount;
	}

	public Long getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(Long outstanding) {
		this.outstanding = outstanding;
	}

	public Date getCmpDueDate() {
		return cmpDueDate;
	}

	public void setCmpDueDate(Date cmpDueDate) {
		this.cmpDueDate = cmpDueDate;
	}
}