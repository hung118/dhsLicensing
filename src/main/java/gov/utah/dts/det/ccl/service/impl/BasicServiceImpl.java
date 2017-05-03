package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.GenericDao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basicService")
public class BasicServiceImpl implements BasicService {

	@Autowired
	protected GenericDao genericDao;
	
	public Object getEntity(Class type, Serializable id) {
		return genericDao.getEntity(type, id);
	}
	
	public Object insert(Object entity) {
		return genericDao.insert(entity);
	}

	public Object update(Object entity) {
		return genericDao.update(entity);
	}

	public boolean delete(Object entity) {
		return genericDao.delete(entity);
	}
	
}
