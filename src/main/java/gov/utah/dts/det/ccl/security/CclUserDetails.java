package gov.utah.dts.det.ccl.security;

import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class CclUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	public static final String ROLE_USER = "ROLE_USER";
	
	private User user;
	private String username;
	private Collection<GrantedAuthority> authorities;
	private boolean anonymous = true;
	private boolean authenticated = false;
	private boolean onNetwork = false;
	private boolean internal = false;
	
	public CclUserDetails(User user, String username) {
		this.user = user;
		
		//was using user.getUsername() as username but UMD would pass in something like JDOE@utah.gov but the user was set up as 
		//jdoe@utah.gov in ccl.  When spring security does it's check to make sure the user is the same it doesn't do a case insensitive
		//check.  Therefore it was invalidating the session and creating a new session.  This was not good for the few places we store
		//information in the session.  Changed to store the exact username string that UMD passes in. 
		this.username = username;
		
		authorities = new ArrayList<GrantedAuthority>();
		for (RoleType role : user.getRoles()) {
			authorities.add(new GrantedAuthorityImpl(role.name()));
			if (!internal && role.isInternal()) {
				internal = true;
			}
		}
		if (authorities.size() > 0) {
			authorities.add(new GrantedAuthorityImpl(ROLE_USER));
			anonymous = false;
			authenticated = true;
		}
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return "unknown";
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return user.isActive();
	}

	public User getUser() {
		return user; 
	}
	
	public boolean isAnonymous() {
		return anonymous;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public boolean isOnNetwork() {
		return onNetwork;
	}
	
	public void setOnNetwork(boolean onNetwork) {
		this.onNetwork = onNetwork;
	}
	
	public boolean isInternal() {
		return internal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CclUserDetails) || (obj == null)) {
			return false;
		}
		CclUserDetails user = (CclUserDetails) obj;
		if (user.getAuthorities().size() != this.getAuthorities().size()) {
			return false;
		}
		
		if (!this.getAuthorities().containsAll(user.getAuthorities())) {
			return false;
		}
		
		return this.getUsername().equals(user.getUsername());
	}
	
	@Override
	public int hashCode() {
		int code = 57;
		
		if (this.getAuthorities() != null) {
			code = code * this.getAuthorities().hashCode();
		}
		
		if (this.getUsername() != null) {
			code = code * (this.getUsername().hashCode() % 7);
		}
		
		return code;
	}
}