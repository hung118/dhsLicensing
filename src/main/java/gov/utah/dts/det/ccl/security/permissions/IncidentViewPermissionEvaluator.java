package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.IncidentView;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class IncidentViewPermissionEvaluator implements DomainObjectPermissionEvaluator<IncidentView> {
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		IncidentView incidentView = (IncidentView) targetDomainObject;
		
		if ("delete".equals(permission) && ((incidentView.getState() != Incident.State.FINALIZED && SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) ||
				(incidentView.getState() == Incident.State.ENTRY && SecurityUtil.isEntityOwner(incidentView)))) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		return false;
	}
}