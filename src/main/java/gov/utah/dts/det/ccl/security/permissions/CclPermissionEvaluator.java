package gov.utah.dts.det.ccl.security.permissions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class CclPermissionEvaluator implements PermissionEvaluator {
	
	private Map<Class<?>, DomainObjectPermissionEvaluator<?>> evaluators;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		DomainObjectPermissionEvaluator<?> evaluator = evaluators.get(targetDomainObject.getClass());
		if (evaluator != null) {
			return evaluator.hasPermission(authentication, targetDomainObject, (String) permission);
		}
		return false;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		try {
			DomainObjectPermissionEvaluator<?> evaluator = evaluators.get(Class.forName("gov.utah.dts.det.ccl.model." + targetType));
			if (evaluator != null) {
				return evaluator.hasPermissionObjectId(authentication, targetId, (String) permission);
			}
		} catch (Exception e) {}
		return false;
	}
	
	@Autowired
	public void setEvaluators(Set<DomainObjectPermissionEvaluator<?>> evaluators) {
		this.evaluators = new HashMap<Class<?>, DomainObjectPermissionEvaluator<?>>();
		for (DomainObjectPermissionEvaluator<?> eval : evaluators) {
			Type[] params = eval.getClass().getGenericInterfaces();
			ParameterizedType param = (ParameterizedType) params[0];
			Type[] actualTypeArgs = param.getActualTypeArguments();
			Class<?> key = (Class<?>) actualTypeArgs[0];
			this.evaluators.put(key, eval);
		}
	}
}