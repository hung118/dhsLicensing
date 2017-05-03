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
@Table(name = "ALERT_EXEMPT_VERIFICATION_VIEW")
@Immutable
public class ExemptVerificationView extends BaseFacilityAlertView {

	@Id
	@Column(name = "FACILITY_ID")
	private Long id;
	
	@Column(name = "EXEMPTION")
	private String exemption;
	
	@Column(name = "EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	public ExemptVerificationView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExemption() {
		return exemption;
	}
	
	public void setExemption(String exemption) {
		this.exemption = exemption;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}