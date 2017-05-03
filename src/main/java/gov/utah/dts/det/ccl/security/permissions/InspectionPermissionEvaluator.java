package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class InspectionPermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Inspection> implements DomainObjectPermissionEvaluator<Inspection> {
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		
		if ("create".equals(permission) && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_SPECIALIST)) {
			return true;
		}
		
		Inspection inspection = (Inspection) targetDomainObject;
		Inspection.State state = inspection.getState();
		
		if ("save-entry".equals(permission) && ((state == Inspection.State.ENTRY && SecurityUtil.isEntityOwner(authentication, inspection)) ||
				(state.ordinal() < Incident.State.FINALIZED.ordinal() && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		} else if ("complete-entry".equals(permission) && state == Inspection.State.ENTRY && (SecurityUtil.isEntityOwner(authentication, inspection) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN))) {
			return true;
		} else if ("reject-entry".equals(permission) && state == Inspection.State.APPROVAL && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("set-cmp-due-date".equals(permission) && state == Inspection.State.APPROVAL && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("finalize".equals(permission) && ((state == Inspection.State.ENTRY && SecurityUtil.isEntityOwner(authentication, inspection)) || (state != Inspection.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		} else if ("unfinalize".equals(permission) && state == Inspection.State.FINALIZED) {
			if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_MANAGER)
					|| SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSING_DIRECTOR)
					|| SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)
			) { 
				return true;
			}
		} else if ("delete".equals(permission) && ((state == Inspection.State.ENTRY && SecurityUtil.isEntityOwner(authentication, inspection)) || (state != Inspection.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = null;
		if (targetId != null) {
			obj = genericDao.getEntity(Inspection.class, targetId);
		}
		
		return hasPermission(authentication, obj, permission);
	}
}