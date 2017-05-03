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
@Table(name = "ALERT_COND_FACILITY_VIEW")
@Immutable
public class ConditionalFacilityView extends BaseFacilityAlertView {

	@Id
	@Column(name = "FACILITY_ID")
	private Long id;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	public ConditionalFacilityView() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}