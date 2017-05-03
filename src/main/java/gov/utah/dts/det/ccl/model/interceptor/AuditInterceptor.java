package gov.utah.dts.det.ccl.model.interceptor;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

@SuppressWarnings({ "unchecked", "serial" })
public class AuditInterceptor extends EmptyInterceptor {
	
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames,
			Type[] types) {
		boolean changed = false;
		if (entity instanceof AbstractAuditableEntity) {
			setValue(currentState, propertyNames, "modifiedDate", new Date());
			setValue(currentState, propertyNames, "modifiedById", getCurrentUser().getId());
			changed = true;
		}
		return changed;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		boolean changed = false;
		if (entity instanceof AbstractAuditableEntity) {
			Date d = new Date();
			setValue(state, propertyNames, "creationDate", d);
			setValue(state, propertyNames, "createdById", getCurrentUser().getId());
			setValue(state, propertyNames, "modifiedDate", d);
			setValue(state, propertyNames, "modifiedById", getCurrentUser().getId());
			changed = true;
		}
		return changed;
	}
	
	private Person getCurrentUser() {
		User user = SecurityUtil.getUser();
		if (user != null) {
			return user.getPerson();
		}
		//if there is no user then return the system user
		return SecurityUtil.getSystemPerson();
	}
	
	private void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value) {
		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyToSet.equals(propertyNames[i])) {
				currentState[i] = value;
			}
		}
	}
}