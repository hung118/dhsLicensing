package gov.utah.dts.det.ccl.view.converter;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class JSONObjectConverter extends StrutsTypeConverter {

	private static final String EMPTY_OBJECT = "{}";
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || StringUtils.isBlank(values[0]) || EMPTY_OBJECT.equals(values[0])) {
			return null;
		}
		try {
			return new JSONObject(values[0]);
		} catch (JSONException je) {
			throw new TypeConversionException(je);
		}
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return EMPTY_OBJECT;
		}
		
		return ((JSONObject) o).toString();
	}
}