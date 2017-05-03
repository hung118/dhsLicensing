package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ComplaintPermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Complaint> implements DomainObjectPermissionEvaluator<Complaint> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		if ("create".equals(permission) && SecurityUtil.isUserInternal()) {
			return true;
		}

		Complaint complaint = (Complaint) targetDomainObject;
		Complaint.State state = complaint.getState();

		if ("save-intake".equals(permission)) {
			if ((state == Complaint.State.INTAKE && SecurityUtil.isEntityOwner(authentication, complaint))
					|| (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_MANAGER)
					|| (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST)
					&& complaint.getFacility().getLicensingSpecialist().getId().equals(SecurityUtil.getUser().getPerson().getId())))) {
				return true;
			}
		} else if ("complete-intake".equals(permission)) {
			if (state == Complaint.State.INTAKE &&
				(SecurityUtil.isEntityOwner(authentication, complaint) || 
						(SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_MANAGER) ||
								(SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST) && 
								 complaint.getFacility().getLicensingSpecialist().getId().equals(SecurityUtil.getUser().getPerson().getId())
								)
						)
				))
			{
				return true;
			}
		} else if ("finalize".equals(permission)) {
				if (SecurityUtil.isEntityOwner(authentication, complaint)
						|| (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSOR_SPECIALIST))) {
					return true;
				}
		} else if ("delete".equals(permission)) {
			if (((state == Complaint.State.INTAKE && SecurityUtil.isEntityOwner(authentication, complaint)) || 
				(state != Complaint.State.FINALIZED && SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_MANAGER)))) 
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = null;
		if (targetId != null) {
			obj = genericDao.getEntity(Complaint.class, targetId);
		}
		return hasPermission(authentication, obj, permission);
	}
}