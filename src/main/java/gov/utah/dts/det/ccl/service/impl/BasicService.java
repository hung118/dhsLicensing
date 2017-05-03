package gov.utah.dts.det.ccl.service.impl;

import java.io.Serializable;

public interface BasicService {

	public abstract Object getEntity(Class type, Serializable id);

	public abstract Object insert(Object entity);

	public abstract Object update(Object entity);

	public abstract boolean delete(Object entity);

}