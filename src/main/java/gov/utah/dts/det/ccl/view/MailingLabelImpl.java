package gov.utah.dts.det.ccl.view;

public class MailingLabelImpl implements MailingLabel {

	private String name;
	private Address address;
	
	public MailingLabelImpl(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
}