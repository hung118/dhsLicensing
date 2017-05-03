package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.dao.GenericDao;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDomainObjectPermissionEvaluator<U> implements DomainObjectPermissionEvaluator<U> {
	
	@Autowired
	protected GenericDao genericDao;
}