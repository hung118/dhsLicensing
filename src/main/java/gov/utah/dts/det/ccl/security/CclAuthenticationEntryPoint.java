package gov.utah.dts.det.ccl.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class CclAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private String loginUrl;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect(loginUrl);
	}
	
	public void setLoginUrl(String loginUrl) {
		Assert.hasText(loginUrl, "loginUrl must not be empty or null");
		this.loginUrl = loginUrl;
	}
}