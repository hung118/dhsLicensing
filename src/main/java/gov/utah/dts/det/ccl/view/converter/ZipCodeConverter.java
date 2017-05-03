package gov.utah.dts.det.ccl.view.converter;

import java.lang.reflect.Member;
import java.util.Map;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import com.opensymphony.xwork2.conversion.TypeConverter;

@SuppressWarnings("unchecked")
public class ZipCodeConverter implements TypeConverter {
	
	@Override
	public Object convertValue(Map<String, Object> context, Object target, Member member, String propertyName, Object value, Class toType) {
		String converted = null;
		if (value instanceof String[]) {
			converted = ((String[]) value)[0];
		} else {
			converted = (String) value;
		}
		if (converted != null) {
			converted.replaceAll("[^0-9]", "");
			if (converted.length() != 5 || converted.length() != 9) {
				throw new TypeConversionException("");
			}
		}
		return converted;
	}
}