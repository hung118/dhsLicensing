package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.view.Address;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
@Embeddable
public class EmbeddableAddress implements Serializable, Address {

	@Column(name = "ADDRESS_ONE")
	private String addressOne;
	
	@Column(name = "ADDRESS_TWO")
	private String addressTwo;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "COUNTY")
	private String county;
	
	public EmbeddableAddress() {
		
	}

	@Override
	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	@Override
	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	@Override
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(addressOne);
		if (StringUtils.isNotBlank(addressTwo)) {
			sb.append(" ");
			sb.append(addressTwo);
		}
		sb.append(" ").append(city).append(", ").append(state).append(" ").append(zipCode);
		return sb.toString();
	}
}