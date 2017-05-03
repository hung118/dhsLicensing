package gov.utah.dts.det.ccl.actions.pub.facility;

import org.springframework.security.access.AccessDeniedException;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;

@SuppressWarnings("serial")
public class BasePublicFacilityAction extends BaseFacilityAction {
	
	public Facility getFacility() {
		if (super.getFacility().getStatus() != FacilityStatus.REGULATED) {
			throw new AccessDeniedException("You do not have access to view the requested facility");
		}
		return super.getFacility();
	}
}