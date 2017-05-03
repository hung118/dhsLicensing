package gov.utah.dts.det.ccl.security;

import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.model.OwnedEntity;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

public class CclWebSecurityExpressionRoot extends WebSecurityExpressionRoot {
	
	private SecurityService securityService;
	private PermissionEvaluator permissionEvaluator;

	public CclWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, SecurityService securityService, PermissionEvaluator permissionEvaluator) {
		super(a, fi);
		this.securityService = securityService;
		this.permissionEvaluator = permissionEvaluator;
	}
	
	public boolean isOwner(String objKey) {
		return isOwner(request.getAttribute(objKey));
	}
	
	public boolean isOwner(Object targetObject) {
		if (targetObject != null && targetObject instanceof OwnedEntity && authentication.getPrincipal() instanceof CclUserDetails) {
			OwnedEntity entity = (OwnedEntity) targetObject;
			CclUserDetails details = (CclUserDetails) authentication.getPrincipal();
			return entity.getCreatedById().equals(details.getUser().getPerson().getId());
		}
			
		return false;
	}
	
	public boolean isManager(String objKey) {
		return isManager(request.getAttribute(objKey));
	}
	
	public boolean isManager(Object targetObject) {
		if (targetObject != null && targetObject instanceof OwnedEntity && authentication.getPrincipal() instanceof CclUserDetails) {
			OwnedEntity entity = (OwnedEntity) targetObject;
			CclUserDetails details = (CclUserDetails) authentication.getPrincipal();
			return securityService.isManager(entity.getCreatedById(), details.getUser().getPerson().getId());
		}
		
		return false;
	}
	
	public boolean hasPermission(String permission, String objKey) {
		return permissionEvaluator.hasPermission(authentication, request.getAttribute(objKey), permission);
	}

	public boolean hasClassPermission(String permission, String className) {
		return permissionEvaluator.hasPermission(authentication, null, className, permission);
	}
}