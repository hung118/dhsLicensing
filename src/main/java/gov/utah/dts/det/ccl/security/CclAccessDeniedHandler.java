package gov.utah.dts.det.ccl.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.util.Assert;

public class CclAccessDeniedHandler extends AccessDeniedHandlerImpl {

	public static final String ERROR_TYPE_KEY = "access-denied-error";
	
	private String anonymousUserPrincipal;
	private String loginUrl;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		CclUserDetails userDetails = (CclUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!userDetails.isAnonymous()) {
			//user is authenticated just doesn't have access to the page
			request.setAttribute(ERROR_TYPE_KEY, AccessDeniedType.ACCESS_DENIED);
		} else if (!anonymousUserPrincipal.equalsIgnoreCase(userDetails.getUsername())) {
			//user is authenticated but does not have an account in ccl
			request.setAttribute(ERROR_TYPE_KEY, AccessDeniedType.NO_ACCOUNT);
		} else {
			//user is not authenticated - redirect to the login page
			if (!response.isCommitted()) {
				response.sendRedirect(loginUrl);
			}
			return;
		}
		
		super.handle(request, response, accessDeniedException);
	}
	
	public void setAnonymousUserPrincipal(String anonymousUserPrincipal) {
		Assert.hasText(anonymousUserPrincipal, "anonymousUserPrincipal must not be empty or null");
		this.anonymousUserPrincipal = anonymousUserPrincipal;
	}
	
	public void setLoginUrl(String loginUrl) {
		Assert.hasText(loginUrl, "loginUrl must not be empty or null");
		this.loginUrl = loginUrl;
	}
}