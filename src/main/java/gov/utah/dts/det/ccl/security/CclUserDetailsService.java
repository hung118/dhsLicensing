package gov.utah.dts.det.ccl.security;

import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.service.UserService;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CclUserDetailsService implements UserDetailsService {
	
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = userService.getActiveUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("");
		}			
		return new CclUserDetails(user, username);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}