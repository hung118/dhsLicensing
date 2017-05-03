package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_FOOD_PROGRAM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoodProgram implements Serializable {

	@Id
	@Column(name = "FACILITY_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FOOD_PROGRAM_ID")
	private FoodProgramPickListValue foodProgram;
	
	@ManyToOne
	@JoinColumn(name = "FOOD_PROGRAM_STATUS_ID")
	private PickListValue foodProgramStatus;
	
	public FoodProgram() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public FoodProgramPickListValue getFoodProgram() {
		return foodProgram;
	}
	
	public void setFoodProgram(FoodProgramPickListValue foodProgram) {
		this.foodProgram = foodProgram;
	}
	
	public PickListValue getFoodProgramStatus() {
		return foodProgramStatus;
	}
	
	public void setFoodProgramStatus(PickListValue foodProgramStatus) {
		this.foodProgramStatus = foodProgramStatus;
	}
}