package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

/**
 * I did this so in the business layer we don't have to repeat common CRUD service operations supported by the DAO layer
 * 
 * @author jtorres
 * 
 */
public interface GenericService<T extends AbstractBaseEntity<PK>, PK extends Serializable> {
  public T save(T entity);

  public T load(PK id);

  public void delete(final PK id);

  public void delete(final T entity);

  public void refresh(final T entity);

  public void evict(final Object entity);

}
