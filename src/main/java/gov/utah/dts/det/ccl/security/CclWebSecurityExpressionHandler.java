package gov.utah.dts.det.ccl.security;

import gov.utah.dts.det.ccl.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionHandler;

public class CclWebSecurityExpressionHandler implements WebSecurityExpressionHandler {

	private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private ExpressionParser expressionParser = new SpelExpressionParser();
    private RoleHierarchy roleHierarchy;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private PermissionEvaluator permissionEvaluator;

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    public EvaluationContext createEvaluationContext(Authentication authentication, FilterInvocation fi) {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        SecurityExpressionRoot root = new CclWebSecurityExpressionRoot(authentication, fi, securityService, permissionEvaluator);
        root.setTrustResolver(trustResolver);
        root.setRoleHierarchy(roleHierarchy);
        ctx.setRootObject(root);

        return ctx;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }
    
    public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
    
    public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
		this.permissionEvaluator = permissionEvaluator;
	}
}