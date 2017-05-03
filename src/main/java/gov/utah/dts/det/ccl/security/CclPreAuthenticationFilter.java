package gov.utah.dts.det.ccl.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.Assert;

public class CclPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private String anonymousUserPrincipal;
	private String principalRequestHeader;
    private String credentialsRequestHeader;
    private boolean exceptionIfHeaderMissing = true;
	protected final Log log = LogFactory.getLog(getClass());

    /**
     * Read and returns the header named by <tt>principalRequestHeader</tt> from the request.
     *
     * @throws PreAuthenticatedCredentialsNotFoundException if the header is missing and <tt>exceptionIfHeaderMissing</tt>
     *          is set to <tt>true</tt>.
     */
	@Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

        String principal = request.getHeader(principalRequestHeader);
        
        if (principal != null && principal.equals(anonymousUserPrincipal)) {
        	return null;
        }
        
        if (principal == null && exceptionIfHeaderMissing) {
            throw new PreAuthenticatedCredentialsNotFoundException(principalRequestHeader + " header not found in request.");
        }

        return principal;
    }

    /**
     * Credentials aren't usually applicable, but if a <tt>credentialsRequestHeader</tt> is set, this
     * will be read and used as the credentials value. Otherwise a dummy value will be used.
     */
	@Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        if (credentialsRequestHeader != null) {
            String credentials = request.getHeader(credentialsRequestHeader);

            return credentials;
        }

        return "N/A";
    }
	
	public void setAnonymousUserPrincipal(String anonymousUserPrincipal) {
		Assert.hasText(anonymousUserPrincipal, "anonymousUserPrincipal must not be empty or null");
		this.anonymousUserPrincipal = anonymousUserPrincipal;
	}

    public void setPrincipalRequestHeader(String principalRequestHeader) {
        Assert.hasText(principalRequestHeader, "principalRequestHeader must not be empty or null");
        this.principalRequestHeader = principalRequestHeader;
    }

    /**
     * Defines whether an exception should be raised if the principal header is missing. Defaults to <tt>true</tt>.
     *
     * @param exceptionIfHeaderMissing set to <tt>false</tt> to override the default behaviour and allow
     *          the request to proceed if no header is found.
     */
    public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
        this.exceptionIfHeaderMissing = exceptionIfHeaderMissing;
    }
}