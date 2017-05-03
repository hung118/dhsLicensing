package gov.utah.dts.det.ccl.model.view;

import java.math.BigDecimal;


public class MonthlyReportView {

	private Long regionId;
	private String regionName;
	private Long licenseTypeId;
	private String licenseTypeName;
	private Integer count;
	
	public MonthlyReportView() {
		
	}

	public Long getRegionId() {
		return regionId;
	}
	
	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId.longValue();
	}
	
	public String getRegionName() {
		return regionName;
	}
	
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getLicenseTypeId() {
		return licenseTypeId;
	}
	
	public void setLicenseTypeId(BigDecimal licenseTypeId) {
		this.licenseTypeId = licenseTypeId.longValue();
	}
	
	public String getLicenseTypeName() {
		return licenseTypeName;
	}
	
	public void setLicenseTypeName(String licenseTypeName) {
		this.licenseTypeName = licenseTypeName;
	}

	public Integer getCount() {
		return count;
	}
	
	public void setCount(BigDecimal count) {
		this.count = count.intValue();
	}
}