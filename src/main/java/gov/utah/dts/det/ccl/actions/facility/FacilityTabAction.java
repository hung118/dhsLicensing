package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.model.Facility;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "facilityEdit", type = "tiles")
})
public class FacilityTabAction extends BaseFacilityAction {

	private String tab;
	private String act;
	private String ns;
	private Boolean validFacility;
	
	@Action(value = "open-tab")
	public String doTab() {
		return SUCCESS;
	}
	
	public String getTab() {
		return tab;
	}
	
	public void setTab(String tab) {
		this.tab = tab;
	}
	
	public String getAct() {
		return act;
	}
	
	public void setAct(String act) {
		this.act = act;
	}
	
	public String getNs() {
		return ns;
	}
	
	public void setNs(String ns) {
		this.ns = ns;
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public boolean isValidFacility() {
		if (validFacility == null) {
			validFacility = getFacility().isValid();
			if (!validFacility) {
				tab = null;
				act = null;
				ns = null;
			}
		}
		return validFacility;
	}
}