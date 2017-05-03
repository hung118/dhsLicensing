/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;

/**
 * @author Dan Olsen
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PLVFOODPROGRAM")
public class FoodProgramPickListValue extends PickListValue {
	
	@Column(name = "PHONENUMBER", insertable = true, updatable = true)
	private String phoneNumber;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "PHONENUMBER", insertable = false, updatable = false))
	private Phone phone;

	public FoodProgramPickListValue() {
		
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@ConversionErrorFieldValidator(message = "Phone number is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
	public Phone getPhone() {
		return phone;
	}
	
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
}