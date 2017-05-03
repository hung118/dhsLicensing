package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLV_NC_LEVEL")
public class NoncomplianceLevelPickListValue extends PickListValue {

	@Column(name = "CMP_AMOUNT")
	private double cmpAmount;
	
	public NoncomplianceLevelPickListValue() {
		
	}
	
	public double getCmpAmount() {
		return cmpAmount;
	}
	
	public void setCmpAmount(double cmpAmount) {
		this.cmpAmount = cmpAmount;
	}
}