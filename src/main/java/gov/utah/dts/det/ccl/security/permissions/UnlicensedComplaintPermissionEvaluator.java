package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UnlicensedComplaintPermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<UnlicensedComplaint> implements DomainObjectPermissionEvaluator<UnlicensedComplaint> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		if ("create".equals(permission) && SecurityUtil.isUserInternal()) {
			return true;
		}
		
		UnlicensedComplaint complaint = (UnlicensedComplaint) targetDomainObject;
		Complaint.State state = complaint.getState();
		
		if ("save-intake".equals(permission) && ((state == Complaint.State.INTAKE) ||
				(state.ordinal() <= Complaint.State.FINALIZED.ordinal() &&
						SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		} else if ("complete-intake".equals(permission) && state == Complaint.State.INTAKE &&
				(SecurityUtil.isEntityOwner(authentication, complaint) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN))) {
		} else if ("finalize".equals(permission) && state == Complaint.State.INTAKE && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
			return true;
		} else if ("delete".equals(permission) && ((state == Complaint.State.INTAKE && SecurityUtil.isEntityOwner(authentication, complaint)) || (state != Complaint.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)))) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = null;
		if (targetId != null) {
			obj = genericDao.getEntity(UnlicensedComplaint.class, targetId);
		}
		return hasPermission(authentication, obj, permission);
	}
}