package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.view.Address;
import gov.utah.dts.det.ccl.view.MailingLabel;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseFacilityAlertView implements Serializable, MailingLabel {

	@ManyToOne
	@JoinColumn(name = "FACILITY_ID", insertable = false, updatable = false)
	private BasicFacilityInformation facility;
	
	public BaseFacilityAlertView() {
		
	}
	
	public BasicFacilityInformation getFacility() {
		return facility;
	}
	
	public void setFacility(BasicFacilityInformation facility) {
		this.facility = facility;
	}
	
	@Override
	public String getName() {
		return facility.getName();
	}
	
	@Override
	public Address getAddress() {
		return facility.getMailingAddress();
	}
}