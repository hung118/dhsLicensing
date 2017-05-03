package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class FacilityPermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Facility> implements DomainObjectPermissionEvaluator<Facility> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		if ("create".equals(permission)) {
			if(SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_SPECIALIST)) {
				return true;
			}
		} else if ("view-record".equals(permission)) {
			if (SecurityUtil.isUserInternal(authentication) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_FACILITY_PROVIDER)) {
				return true;
			}
		} else if ("edit-details".equals(permission)) {
			if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_MANAGER)) {
				return true;
			} else if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST)) {
				if (targetDomainObject != null) {
					Facility facility = (Facility) targetDomainObject;
					if (SecurityUtil.getUser().getPerson().getId().equals(facility.getLicensingSpecialist().getId())) {
						return true;
					}
				}
			}
		} else if ("edit-licenses".equals(permission)) {
			if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_MANAGER)) {
				return true;
			} else if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST)) {
				if (targetDomainObject != null) {
					Facility facility = (Facility) targetDomainObject;
					if (SecurityUtil.getUser().getPerson().getId().equals(facility.getLicensingSpecialist().getId())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = null;
		if (targetId != null) {
			obj = genericDao.getEntity(Facility.class, targetId);
		}
		
		return hasPermission(authentication, obj, permission);
	}
}