package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.model.Facility;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "facilityEdit", type = "tiles")
})
public class EditFacilityAction extends BaseFacilityAction {
	
	private Facility facility;
	
	public String execute() {
		if (getFacilityId() == null) {
			setFacilityId(facility.getId());
		}
		this.facility = super.getFacility();
		return SUCCESS;
	}
	
	@Override
	public Facility getFacility() {
		return facility;
	}
	
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
}