package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.util.CompareUtils;
import java.io.Serializable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class VariancePermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Variance> implements DomainObjectPermissionEvaluator<Variance> {
	private static final RoleType[] SAVE_ROLES = {RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_LICENSOR_SPECIALIST, RoleType.ROLE_LICENSOR_MANAGER, RoleType.ROLE_LICENSING_DIRECTOR};
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		boolean rval = false;
		
		if (targetDomainObject != null) {
			Variance variance = (Variance) targetDomainObject;
	
			if ("view-licensor-outcome".equals(permission)) {
				if (SecurityUtil.isUserInternal(authentication)) {
					rval = true;
				}
			} else 
			if ("view-manager-outcome".equals(permission)) {
				if (SecurityUtil.isUserInternal(authentication)) {
					rval = true;
				}
			} else 
			if ("view-outcome".equals(permission)) {
				if (SecurityUtil.isUserInternal(authentication) || (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_FACILITY_PROVIDER) && variance.isFinalized())) {
					rval = true;
				}
			} else 
			if ("save-new".equals(permission)) {
				// New Variance Request
				if (SecurityUtil.hasAnyRole(authentication, SAVE_ROLES) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_FACILITY_PROVIDER)) {
					rval = true;
				}
			} else
			if ("save-licensor".equals(permission)) {
				// Licensor specialist outcome
				if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_SPECIALIST) && !variance.isFinalized()) {
					rval = true;
				}
			} else
			if ("save-manager".equals(permission)) {
				// Licensing Supervisor outcome
				if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSOR_MANAGER) && !variance.isFinalized()) {
					rval = true;
				}
			} else
			if ("save-outcome".equals(permission)) {
				// Licensing Director outcome
				if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSING_DIRECTOR)) {
					rval = true;
				}
			} else 
			if ("save-revoke".equals(permission)) {
				// Licensing Director outcome
				if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_LICENSING_DIRECTOR) && variance.isFinalized()) {
					rval = true;
				}
			} else 
			if ("save".equals(permission)) {
				if (!variance.isFinalized()) {
					if (SecurityUtil.hasAnyRole(authentication, SAVE_ROLES) && SecurityUtil.isEntityOwner(authentication, variance)) {
						rval = true;
					} else 
					if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_FACILITY_PROVIDER)) {
						rval = CompareUtils.collectionContainsAttributeValue(SecurityUtil.getUser().getPerson().getId(), "personId", FacilityPerson.class, variance.getFacility().getProviders());
					}
				}
			} else
			if (SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN)) {
				rval = true;
			}
		}

		return rval;
	}

	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = genericDao.getEntity(Variance.class, targetId);
		return hasPermission(authentication, obj, permission);
	}
}