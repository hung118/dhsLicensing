package gov.utah.dts.det.ccl.view.facility;

import gov.utah.dts.det.ccl.view.Address;
import gov.utah.dts.det.ccl.view.AddressImpl;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class StatewideUnlicensedComplaintView implements Serializable {

	private Long complaintId;
	private Long facilityId;
	private String facilityName;
	private String ownerNames;
	private Date dateReceived;
	private Address address;
	private Character status;
	
	public StatewideUnlicensedComplaintView(Long complaintId, Long facilityId, String facilityName, String ownerNames, Date dateReceived,
			String addressOne, String addressTwo, String city, String state, String zipCode, Character status) {
		this.complaintId = complaintId;
		this.facilityId = facilityId;
		this.facilityName = facilityName;
		this.ownerNames = ownerNames;
		this.dateReceived = dateReceived;
		this.address = new AddressImpl(addressOne, addressTwo, city, state, zipCode);
		this.status = status;
	}
	
	public Long getComplaintId() {
		return complaintId;
	}
	
	public Long getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getOwnerNames() {
		return ownerNames;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public Address getAddress() {
		return address;
	}

	public Character getStatus() {
		return status;
	}
}