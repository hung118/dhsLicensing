package gov.utah.dts.det.ccl.view.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class EnumConverter extends StrutsTypeConverter {

private static final Logger logger = LoggerFactory.getLogger(EnumConverter.class);
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		logger.debug("converting " + values[0] + " to an enum object");
		if (StringUtils.isBlank(values[0])) {
			return null;
		}
		
		try {
			Method valueOfMethod = toClass.getMethod("valueOf", String.class);
			return valueOfMethod.invoke(null, values[0]);
		} catch (NoSuchMethodException nsme) {
			return null;
		} catch (InvocationTargetException ite) {
			return null;
		} catch (IllegalAccessException iae) {
			return null;
		}
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		logger.debug("converting enum object to string");
		if (o == null) {
			return null;
		} else {
			return o.toString();
		}
	}
}