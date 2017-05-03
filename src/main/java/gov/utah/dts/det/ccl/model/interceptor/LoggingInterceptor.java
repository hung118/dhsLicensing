package gov.utah.dts.det.ccl.model.interceptor;

import gov.utah.dts.det.ccl.model.ChangeLoggedEntity;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.LogService;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@SuppressWarnings("serial")
public class LoggingInterceptor extends EmptyInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames,
			Type[] types) {
		try {
			if (entity instanceof ChangeLoggedEntity) {
				String[] properties = ((ChangeLoggedEntity) entity).getLoggedProperties();
				StringBuilder change = null;
				for (String prop : properties) {
					for (int i = 0; i < propertyNames.length; i++) {
						if (prop.equals(propertyNames[i]) && currentState[i] != null && !currentState[i].equals(previousState[i])) {
							//log the change here
							if (change == null) {
								change = new StringBuilder(entity.getClass().getSimpleName());
								change.append(" - ID: ");
								change.append(((AbstractBaseEntity) entity).getPk().toString());
							}
							change.append(" | ");
							change.append(" Property: ");
							change.append(propertyNames[i]);
							change.append(" Old Value: ");
							change.append(previousState[i]);
							change.append(" New Value: ");
							change.append(currentState[i]);
						}
					}
				}
				if (change != null) {
					ApplicationContext context = AppContext.getApplicationContext();
					if (context.getBean("logService") != null) {
						LogService logService = (LogService) context.getBean("logService");
						logService.writeLog(SecurityUtil.getUser().getPerson().getId().toString(), "RE", "SERVER", change.toString(), null);
					}
				} else {
					logger.debug("LoggingInterceptor: logService not configured!");
				}
			}
		} catch (Throwable t) {
			logger.error("Unable to log object change", t);
		}
		return false; //always return false because we are not going to be changing the state.  Just logging the change.
	}
}