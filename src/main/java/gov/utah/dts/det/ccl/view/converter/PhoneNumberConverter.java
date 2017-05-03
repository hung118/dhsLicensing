package gov.utah.dts.det.ccl.view.converter;

import gov.utah.dts.det.ccl.model.Phone;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class PhoneNumberConverter extends StrutsTypeConverter {
	
	private static final Logger logger = LoggerFactory.getLogger(PhoneNumberConverter.class);
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		logger.debug("converting " + values + " to a phone number");
		if (values == null || StringUtils.isBlank(values[0])) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values[0].length(); i++) {
			if (Character.isDigit(values[0].charAt(i))) {
				sb.append(values[0].charAt(i));
			}
		}
		
		if (sb.length() == 0) {
			return null;
		} else if (sb.length() == 10) {
			Phone phone = new Phone();
			phone.setPhoneNumber(sb.toString());
			return phone;
		}
		
		throw new TypeConversionException("Unable to convert " + values + " to a phone number.  It is in an invalid format.");
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		Phone phone = (Phone) o;
		return phone.getPhoneNumber();
	}
}