package gov.utah.dts.det.ccl.view;

public class AddressImpl implements Address {

	private String addressOne;
	private String addressTwo;
	private String city;
	private String state;
	private String zipCode;
	
	public AddressImpl() {
		
	}
	
	public AddressImpl(String addressOne, String addressTwo, String city, String state, String zipCode) {
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
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
}