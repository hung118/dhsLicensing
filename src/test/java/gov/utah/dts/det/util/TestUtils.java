package gov.utah.dts.det.util;

import java.text.ParseException;
import java.util.Date;

public class TestUtils {

	public static final String[] DATE_PATTERNS = {"MM/dd/yyyy"};
	
	public static Date parseDate(String dateStr) throws ParseException {
		return org.apache.commons.lang.time.DateUtils.parseDate(dateStr, DATE_PATTERNS);
	}
}