package gov.utah.dts.det.dao;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository("abstractBaseDao")
public abstract class AbstractBaseDaoImpl<T extends AbstractBaseEntity<PK>, PK extends Serializable> implements
    AbstractBaseDao<T, PK> {

  private Class<T> clazz;

  /**
   * All Sublasses must implement this method to correctly identify the right entity manager to use when doing CRUD operations.
   */
  public abstract EntityManager getEntityManager();

  public AbstractBaseDaoImpl(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public T save(T entity) {
    if (entity != null) {
      if (entity.getPk() != null) {
    	  entity = getEntityManager().merge(entity);
      } else {
        getEntityManager().persist(entity);
        getEntityManager().flush();
      }
    }

    return entity;
  }

  public T load(PK id) {
    return getEntityManager().find(clazz, id);
  }

  @Transactional(readOnly = false)
  public void delete(final PK id) {
    T loadedEntity = load(id);
    delete(loadedEntity);
  }

  @Transactional(readOnly = false)
  public void delete(final T entity) {
    T entityToRemove = entity;
    if (!getEntityManager().contains(entityToRemove)) {
      entityToRemove = load(entity.getPk());
    }
    getEntityManager().remove(entityToRemove);
    getEntityManager().flush();
  }

  public void refresh(final T entity) {
    getEntityManager().refresh(entity);
  }

  public void evict(final Object entity) {
    getEntityManager().detach(entity);
  }

}