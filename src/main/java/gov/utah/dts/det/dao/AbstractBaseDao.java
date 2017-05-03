package gov.utah.dts.det.dao;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

public interface AbstractBaseDao<T extends AbstractBaseEntity<PK>, PK extends Serializable> {

	public T save(T entity); 

    public T load(PK id);

    public void delete(final PK id);
    
    public void delete(final T entity);
    
    public void refresh(final T entity);
    
    public void evict(final Object entity);
    
}