package gov.utah.dts.det.ccl.view.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class InspectionTimeConverter extends StrutsTypeConverter {
	
	public static final String TIME_FORMAT = "^(([0]?[1-9]|1[0-2]):[0-5][0-9]( )([aApP][mM]))$";

	private static final Logger logger = LoggerFactory.getLogger(InspectionTimeConverter.class);
	private static final Pattern TIME_PATTERN = Pattern.compile(TIME_FORMAT);
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		logger.debug("converting " + values + " to a java date");
		if (values == null || StringUtils.isBlank(values[0]) || values[0].equals("-1")) {
			return null;
		}

		String timeStr = values[0];
		Matcher m = TIME_PATTERN.matcher(timeStr);
		
		if (!m.matches()) {
			throw new TypeConversionException("Unable to convert " + timeStr + " to a date.  It is in an invalid format.");
		}
		
		//format has been validated so the below calls should not throw exceptions
		int colonIndex = timeStr.indexOf(":");
		int amPmIndex = timeStr.indexOf(" ");
		
		String hourStr = timeStr.substring(0, colonIndex);
		String minStr = timeStr.substring(colonIndex + 1, amPmIndex);
		String amPmStr = timeStr.substring(amPmIndex + 1);
		
		int hour = Integer.parseInt(hourStr);
		int minute = Integer.parseInt(minStr);
		int amPm;
		if (amPmStr.equalsIgnoreCase("am")) {
			amPm = Calendar.AM;
		} else {
			amPm = Calendar.PM;
		}
		
		//create the calendar
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, hour == 12 ? 0 : hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.AM_PM, amPm);
		
		Date date = cal.getTime();
		logger.debug("time: " + date);
		return date;
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		if (o == null) {
			return null;
		}
		Date date = (Date) o;
		logger.debug("converting " + date + " to a string");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		StringBuilder sb = new StringBuilder();
		int hour = cal.get(Calendar.HOUR);
		if (hour == 0) {
			hour = 12;
		}
		int minute = cal.get(Calendar.MINUTE);
		int amPm = cal.get(Calendar.AM_PM);
		
		sb.append(hour);
		sb.append(":");
		if (minute < 10) {
			sb.append("0");
		}
		sb.append(minute);
		sb.append(" ");
		sb.append(amPm == Calendar.AM ? "AM" : "PM");
		return sb.toString();
	}
}