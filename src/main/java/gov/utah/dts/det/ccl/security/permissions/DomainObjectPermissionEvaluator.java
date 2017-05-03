package gov.utah.dts.det.ccl.security.permissions;

import java.io.Serializable;

import org.springframework.security.core.Authentication;

public interface DomainObjectPermissionEvaluator<T> {

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission);
	
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission);
}