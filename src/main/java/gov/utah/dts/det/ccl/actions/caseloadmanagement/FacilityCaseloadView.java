package gov.utah.dts.det.ccl.actions.caseloadmanagement;


public class FacilityCaseloadView {
	
	private Long id;
	private String name;
	private String type;
	private String city;
	private String zipCode;
	private String status;

	public FacilityCaseloadView(Long id, String name, String type, String city, String zipCode, String status) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.city = city;
		this.zipCode = zipCode;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public String getStatus() {
		return status;
	}
}