package gov.utah.dts.det.util;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DevSiteminderFilter implements Filter {
	
	public static final String USERNAME_KEY = "devMode.username";
	ResourceBundle appInfo = ResourceBundle.getBundle("application");
	
	private String principalRequestHeader;
	private String anonymousUserPrincipal;
	
//	private static final Logger logger = LoggerFactory.getLogger(DevSiteminderFilter.class);

	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (StringUtils.isNotBlank(appInfo.getString("devMode"))) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			RequestWrapper wrapper = new RequestWrapper(httpRequest, filterConfig);
			
			chain.doFilter(wrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() {
		
	}
	
	public void setPrincipalRequestHeader(String principalRequestHeader) {
		Assert.hasText(principalRequestHeader, "principalRequestHeader must not be empty or null");
		this.principalRequestHeader = principalRequestHeader;
	}
	
	public void setAnonymousUserPrincipal(String anonymousUserPrincipal) {
		Assert.hasText(anonymousUserPrincipal, "anonymousUserPrincipal must not be empty or null");
		this.anonymousUserPrincipal = anonymousUserPrincipal;
	}
	
	class RequestWrapper extends HttpServletRequestWrapper {

		public RequestWrapper(HttpServletRequest request, FilterConfig filterConfig) {
			super(request);
		}

		@Override
		public String getHeader(String name) {
			if (name != null && name.equals(principalRequestHeader)) {
				String username = appInfo.getString("username");
//				if (username != null) {
//					((HttpServletRequest) getRequest()).setAttribute(USERNAME_KEY, username);
//				}
//				if (username == null) {
//					//see if the username is in the request
//					username = (String) ((HttpServletRequest) getRequest()).getAttribute(USERNAME_KEY);
//					//put the username in the session
//					((HttpServletRequest) getRequest()).getSession().setAttribute(USERNAME_KEY, username);
//				}
				if (username == null) {
					username = anonymousUserPrincipal;
				}
				return username;
			}
			return super.getHeader(name);
		}
	}
}