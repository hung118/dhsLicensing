package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class LicensePermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<License> implements DomainObjectPermissionEvaluator<License> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		if ("save".equals(permission)) {
			if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
				return true;
			} else if (targetDomainObject != null) {
				License license = (License) targetDomainObject;
				if (!license.isFinalized()) {
					if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_MANAGER)) {
						return true;
					} else if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST) &&
								SecurityUtil.getUser().getPerson().getId().equals(license.getFacility().getLicensingSpecialist().getId())) 
					{
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
			obj = genericDao.getEntity(License.class, targetId);
		}
		
		return hasPermission(authentication, obj, permission);
	}
}