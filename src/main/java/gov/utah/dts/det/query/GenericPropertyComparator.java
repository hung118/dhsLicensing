package gov.utah.dts.det.query;

import java.util.Comparator;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class GenericPropertyComparator<T> implements Comparator<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(GenericPropertyComparator.class);

	private String property;
	
	public GenericPropertyComparator(String property) {
		this.property = property;
	}
	
	@Override
	public int compare(T o1, T o2) {
		if (StringUtils.isBlank(property)) {
			return 0;
		}
		
		try {
//			Method getterMethod = getGetterMethod(o1.getClass());
//			Object val1 = getterMethod.invoke(o1);
//			Object val2 = getterMethod.invoke(o2);
			
			Object val1 = Ognl.getValue(property, o1);
			Object val2 = Ognl.getValue(property, o2);
		
			
			if (val1 instanceof Comparable) {
				return ((Comparable) val1).compareTo((Comparable) val2);
			}
		} catch (Exception e) {
			logger.debug("Unable to compare objects!", e);
		}
		
		return 0;
	}
	
	/*private Method getGetterMethod(Class clazz) throws NoSuchMethodException {
		StringBuilder getter = new StringBuilder("get");
		getter.append(property.substring(0, 1).toUpperCase());
		getter.append(property.substring(1));
		
		return clazz.getMethod(getter.toString());
	}*/
}