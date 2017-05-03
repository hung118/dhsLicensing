package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class IncidentPermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Incident> implements DomainObjectPermissionEvaluator<Incident> {
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		if ("create".equals(permission) && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return true;
		}
		
		Incident incident = (Incident) targetDomainObject;
		Incident.State state = incident.getState();
		
		if ("save-entry".equals(permission) && ((state == Incident.State.ENTRY && SecurityUtil.isEntityOwner(authentication, incident)) ||
				(state.ordinal() < Incident.State.FINALIZED.ordinal() && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		} else if ("complete-entry".equals(permission) && state == Incident.State.ENTRY && (SecurityUtil.isEntityOwner(authentication, incident) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN))) {
			return true;
		} else if ("reject-entry".equals(permission) && state == Incident.State.APPROVAL && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("finalize".equals(permission) && state == Incident.State.APPROVAL && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("unfinalize".equals(permission) && state == Incident.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("delete".equals(permission) && ((state == Incident.State.ENTRY && SecurityUtil.isEntityOwner(authentication, incident)) || (state != Incident.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = null;
		if (targetId != null) {
			obj = genericDao.getEntity(Incident.class, targetId);
		}
		
		return hasPermission(authentication, obj, permission);
	}
}