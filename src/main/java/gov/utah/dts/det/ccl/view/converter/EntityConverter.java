package gov.utah.dts.det.ccl.view.converter;

import gov.utah.dts.det.ccl.dao.GenericDao;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class EntityConverter extends StrutsTypeConverter {

	private static final Logger logger = LoggerFactory.getLogger(EntityConverter.class);
	private static final String NO_ENTITY_STRING = "-1";
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			logger.debug("Object is null");
			return null;
		}
		
		ApplicationContext springContext = AppContext.getApplicationContext();
		GenericDao dao = (GenericDao) springContext.getBean("genericDao");
		
		if (values.length == 1) {
			logger.debug("Converting single object (" + values[0] + ") to class: " + toClass);
			Serializable id = null;
			if (StringUtils.isNotBlank(values[0]) && !values[0].equals(NO_ENTITY_STRING)) {
				try {
					Method getPkMethod = toClass.getMethod("getPk");
					Class pkType = getPkMethod.getReturnType();
					if (pkType == Long.class) {
						id = Long.parseLong(values[0]);
					}
				} catch (Exception e) {
					logger.debug("Entity conversion failed", e);
					throw new TypeConversionException("Unable to convert " + values[0] + " to an entity."); 
				}
				
			}
			if (id != null) {
				return dao.getEntity(toClass, id);
			}
		} else {
			logger.debug("Converting multiple objects (" + values + ") to class: " + toClass);
		}
		
		return null;
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		logger.debug("********* In Entity Converter convertToString *************");
		if (o == null) {
			return null;
		}
		
		AbstractBaseEntity entity = (AbstractBaseEntity) o;
		return entity.getPk().toString();
	}
}