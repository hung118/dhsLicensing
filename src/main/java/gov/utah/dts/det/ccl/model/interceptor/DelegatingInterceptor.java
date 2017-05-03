package gov.utah.dts.det.ccl.model.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

@SuppressWarnings("serial")
public class DelegatingInterceptor implements Interceptor, Serializable {

	private List<Interceptor> interceptors = new ArrayList<Interceptor>();
	
	public DelegatingInterceptor() {
		
	}
	
	public List<Interceptor> getInterceptors() {
		return interceptors;
	}
	
	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.onDelete(entity, id, state, propertyNames, types);
		}
	}
	
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames,
			Type[] types) throws CallbackException {
		boolean result = false;
		for (Interceptor i : interceptors) {
			if (i.onFlushDirty(entity, id, currentState, previousState, propertyNames, types)) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
		boolean result = false;
		for (Interceptor i : interceptors) {
			if (i.onLoad(entity, id, state, propertyNames, types)) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
		boolean result = false;
		for (Interceptor i : interceptors) {
			if (i.onSave(entity, id, state, propertyNames, types)) {
				result = true;
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void postFlush(Iterator entities) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.postFlush(entities);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void preFlush(Iterator entities) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.preFlush(entities);
		}	
	}
	
	@Override
	public Boolean isTransient(Object entity) {
		return null;
	}
	
	@Override
	public Object instantiate(String entityName, EntityMode entityMode, Serializable id) throws CallbackException {
		return null;
	}
	
	@Override
	public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		return null;
	}
	
	@Override
	public String getEntityName(Object object) throws CallbackException {
		return null;
	}
	
	@Override
	public Object getEntity(String entityName, Serializable id) throws CallbackException {
		return null;
	}
	
	@Override
	public void afterTransactionBegin(Transaction tx) {
		for (Interceptor i : interceptors) {
			i.afterTransactionBegin(tx);
		}
	}
	
	@Override
	public void afterTransactionCompletion(Transaction tx) {
		for (Interceptor i : interceptors) {
			i.afterTransactionCompletion(tx);
		}
	}
	
	@Override
	public void beforeTransactionCompletion(Transaction tx) {
		for (Interceptor i : interceptors) {
			i.beforeTransactionCompletion(tx);
		}
	}
	
	@Override
	public String onPrepareStatement(String sql) {
		return sql;
	}
	
	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.onCollectionRemove(collection, key);
		}
	}
	
	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.onCollectionRecreate(collection, key);
		}
	}
	
	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		for (Interceptor i : interceptors) {
			i.onCollectionUpdate(collection, key);
		}
	}
}