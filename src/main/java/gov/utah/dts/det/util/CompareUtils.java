package gov.utah.dts.det.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Iterator;

public class CompareUtils {

	private CompareUtils() {
		
	}
	
	/*public static int nullSafeStringCompare(String s1, String s2, boolean nullsFirst) {
		if (s1 == s2) {
			return 0;
		}
		if (s1 == null ^ s2 == null) {
			return (s1 == null && nullsFirst) ? -1 : 1; //nulls come before non-nulls
		}
		if (s1 == null && s2 == null) {
			return 0;
		}
		
		return s1.compareToIgnoreCase(s2);
	}*/
	
	public static <T extends Comparable<T>> int nullSafeComparableCompare(T c1, T c2, boolean nullsFirst) {
		if (c1 == c2) {
			return 0;
		}
		if (c1 == null ^ c2 == null) {
			return (c1 == null && nullsFirst) || (c2 == null && !nullsFirst) ? -1 : 1; //nulls come before non-nulls
		}
		if (c1 == null && c2 == null) {
			return 0;
		}
		
		return c1.compareTo(c2);
	}
	
	/**
	 * Iterates through a Collection checking the value of the collection class attribute specified by attributeName 
	 * for a match with the input search value.
	 * 
	 * @author Byron Bills
	 * @param searchValue The value to look for in the collection.
	 * @param attributeName The class attribute name to compare value to. <strong>(requires a class getter)</strong>
	 * @param clazz The collection type class.
	 * @param collection The collection of objects to iterate through.
	 * @return Returns true if a match is found.
	 */
	public static boolean collectionContainsAttributeValue(Object searchValue, String attributeName, Class<?> clazz, java.util.Collection<?> collection) {
		boolean rval = false;
		try {
			final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				
				if (pd.getName().equals(attributeName)) {
					Iterator<?> i = collection.iterator();
					while (i.hasNext()) {
						Object obj = clazz.cast(i.next());
						// Invoke the class getter method for this attribute
						Object compareValue = pd.getReadMethod().invoke(obj);
						// Make sure both comparison values have the same class type
						if (compareValue.getClass().equals(searchValue.getClass())) {
							if (compareValue.equals(searchValue)) {
								rval = true;
								break;
							}
						}
					}
					break;
				}
			}
		} catch(Exception e) {
			rval = false;
		}
		return rval;
	}

}