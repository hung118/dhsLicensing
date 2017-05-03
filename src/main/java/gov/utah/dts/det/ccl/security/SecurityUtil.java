package gov.utah.dts.det.ccl.security;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.model.OwnedEntity;
import gov.utah.dts.det.util.spring.AppContext;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	private SecurityUtil() {
		
	}
	
	public static User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object authPrincipal = authentication.getPrincipal();
			if (authPrincipal != null) {
				CclUserDetails principal = (CclUserDetails) authPrincipal;
				if (principal != null && principal.getUser() != null) {
					return principal.getUser();
				}
			}
		}
		return null;
	}
	
	public static Person getSystemPerson() {
		return getSecurityService().getSystemPerson();
	}
	
//	public static boolean isUserInRole(RoleType roleType) {
//		return isUserInRole(roleType, getUser());
//	}
	
	public static boolean hasAnyRole(RoleType... roles) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return hasAnyRole(auth, roles);
	}
	
	public static boolean hasAnyRole(Authentication authentication, RoleType... roles) {
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			for (RoleType role : roles) {
				if (authority.equals(role.name())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasAnyRole(User user, RoleType... roles) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be null");
		}
		for (RoleType roleType : roles) {
			if (user.getRoles().contains(roleType)) {
				return true;
			}
		}
		return false;
	}
	
//	public static boolean isUserInRoles(Set<RoleType> roleTypes) {
//		return isUserInRoles(roleTypes, getUser());
//	}
	
//	public static boolean isUserInRole(RoleType roleType, User user) {
//		Set<RoleType> roleTypes = new HashSet<RoleType>();
//		roleTypes.add(roleType);
//		return isUserInRoles(roleTypes, user);
//	}
	
//	public static boolean isUserInRoles(Set<RoleType> roleTypes, User user) {
//		if (user == null) {
//			throw new IllegalArgumentException("User must not be null");
//		}
//		for (RoleType roleType : roleTypes) {
//			if (user.getRoleTypes().contains(roleType)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public static boolean isUserInternal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return isUserInternal(authentication);
	}
	
	public static boolean isUserInternal(Authentication authentication) {
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			try {
				RoleType role = RoleType.valueOf(authority.getAuthority());
				if (role.isInternal()) {
					return true;
				}
			} catch (IllegalArgumentException iae) {} //swallow this exception because of ROLE_ANONYMOUS and ROLE_USER
		}
		return false;
	}
	
	public static boolean isUserPartner() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return isUserPartner(authentication);
	}
	
	public static boolean isUserPartner(Authentication authentication) {
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			try {
				RoleType role = RoleType.valueOf(authority.getAuthority());
				if (!role.isInternal()) {
					return true;
				}
			} catch (IllegalArgumentException iae) {} //swallow this exception because of ROLE_ANONYMOUS and ROLE_USER
		}
		return false;
	}
	
	public static <E extends OwnedEntity> boolean isEntityOwner(E entity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return isEntityOwner(authentication, entity);
	}
	
	public static <E extends OwnedEntity> boolean isEntityOwner(Authentication authentication, E entity) {
		if (entity.getCreatedById() != null && entity.getCreatedById().equals(((CclUserDetails) authentication.getPrincipal()).getUser().getPerson().getId())) {
			return true;
		}
		
		return false;
	}
	
	public static <E extends AbstractAuditableEntity<?>> boolean isManagerOfOwner(Authentication authentication, E entity) {
		return getSecurityService().isManager(entity.getCreatedById(), ((CclUserDetails) authentication.getPrincipal()).getUser().getPerson().getId());
	}
	
	private static SecurityService getSecurityService() {
		ApplicationContext context = AppContext.getApplicationContext();
		return (SecurityService) context.getBean("securityService");
	}
}