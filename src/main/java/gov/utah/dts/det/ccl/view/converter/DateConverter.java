package gov.utah.dts.det.ccl.view.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class DateConverter extends StrutsTypeConverter {
	
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	
	private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
	private static final String BLANK_PATTERN = "__/__/____";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	static {
		DATE_FORMATTER.setLenient(false);
	}
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		logger.debug("converting " + values + " to a date");
		if (values == null || StringUtils.isBlank(values[0]) || values[0].equals(BLANK_PATTERN)) {
			return null;
		}
		
		try {
			return DATE_FORMATTER.parse(values[0]);
		} catch (ParseException pe) {
			throw new TypeConversionException("Unable to convert " + values[0] + " to a date.  It is in an invalid format.");
		}
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		Date date = (Date) o;
		return DATE_FORMATTER.format(date);
	}
}