package gov.utah.dts.det.ccl.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class OnNetworkFilter implements Filter {

	private String onNetworkRequestHeader;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof CclUserDetails) {
			CclUserDetails details = (CclUserDetails) auth.getPrincipal();
			if (((HttpServletRequest) request).getHeader(onNetworkRequestHeader).equals("Y")) {
				details.setOnNetwork(true);
			} else {
				details.setOnNetwork(false);
			}
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
	
	public void setOnNetworkRequestHeader(String onNetworkRequestHeader) {
		Assert.hasText(onNetworkRequestHeader, "onNetworkRequestHeader must not be empty or null");
		this.onNetworkRequestHeader = onNetworkRequestHeader;
	}
}