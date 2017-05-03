package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.service.GenericService;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("genericService")
public abstract class GenericServiceImpl<T extends AbstractBaseEntity<PK>, PK extends Serializable> implements
    GenericService<T, PK> {

  protected AbstractBaseDao<T, PK> dao;

  @Override
  public T save(T entity) {
    return dao.save(entity);
  }

  @Override
  public T load(PK id) {
    return dao.load(id);
  }

  @Override
  public void delete(PK id) {
    dao.delete(id);
  }

  @Override
  public void delete(T entity) {
    dao.delete(entity);
  }

  @Override
  public void refresh(T entity) {
    dao.refresh(entity);
  }

  @Override
  public void evict(Object entity) {
    dao.evict(entity);

  }

  public AbstractBaseDao<T, PK> getDao() {
    return dao;
  }

  public void setDao(AbstractBaseDao<T, PK> dao) {
    this.dao = dao;
  }

}
