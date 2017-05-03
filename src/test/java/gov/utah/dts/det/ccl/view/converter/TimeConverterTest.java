package gov.utah.dts.det.ccl.view.converter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@Test(groups = {"main"})
public class TimeConverterTest {
	
	@DataProvider(name = "exceptionData")
	public Object[][] createExceptionData() {
		return new Object[][] {
				{"asdf"},{"12:30AM"},{"13:30 PM"},{"12:60 AM"},{"12 30 PM"},{":30 PM"},{"12:1 PM"},{"12:30 MP"}
		};
	}
	
	public void testConvertFromString() {
		InspectionTimeConverter itc = new InspectionTimeConverter();
		
		String[] timeStr = new String[1];
		assertNull(itc.convertFromString(null, null, null));
		assertNull(itc.convertFromString(null, timeStr, null));
		timeStr[0] = "";
		assertNull(itc.convertFromString(null, timeStr, null));
		timeStr[0] = "-1";
		assertNull(itc.convertFromString(null, timeStr, null));
		
		for (int i = 0; i < 2; i++) {
			String amPm = i == 0 ? "AM" : "PM";
			for (int j = 0; j < 12; j++) {
				String hour;
				if (j == 0) {
					hour = "12";
				} else {
					hour = Integer.toString(j);
				}
				for (int k = 0; k < 60; k++) {
					StringBuilder time = new StringBuilder();
					time.append(hour);
					time.append(':');
					time.append(k < 10 ? "0" + k : k);
					time.append(' ');
					time.append(amPm);
					
					String[] value = {time.toString()};
					Date date = (Date) itc.convertFromString(null, value, null);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					assertEquals(cal.get(Calendar.HOUR), j);
					assertEquals(cal.get(Calendar.MINUTE), k);
					assertEquals(cal.get(Calendar.AM_PM), i);
				}
			}
		}
	}
	
	@Test
	public void testConvertToString() {
		Date now = new Date();
		InspectionTimeConverter itc = new InspectionTimeConverter();
		assertNull(itc.convertToString(null, null));
		assertTrue(Pattern.compile(InspectionTimeConverter.TIME_FORMAT).matcher(itc.convertToString(null, now)).matches());
		Calendar midnight = Calendar.getInstance();
		midnight.set(Calendar.HOUR, 0);
		assertTrue(Pattern.compile(InspectionTimeConverter.TIME_FORMAT).matcher(itc.convertToString(null, midnight.getTime())).matches());
		
	}
	
	@Test(expectedExceptions = {TypeConversionException.class}, dataProvider = "exceptionData")
	public void testInvalidTimes(String timeStr) {
		String[] timeArr = {timeStr};
		InspectionTimeConverter itc = new InspectionTimeConverter();
		itc.convertFromString(null, timeArr, null);
	}
}