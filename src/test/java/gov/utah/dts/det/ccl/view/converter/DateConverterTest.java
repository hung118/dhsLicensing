package gov.utah.dts.det.ccl.view.converter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.Date;
import java.util.GregorianCalendar;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@Test(groups = {"main"})
public class DateConverterTest {
	
	private Date date = new GregorianCalendar(2011, 0, 1).getTime();
	private String dateString = "01/01/2011";
	
	@DataProvider(name = "exceptionData")
	public Object[][] createExceptionData() {
		return new Object[][] {
			{"parse exception"},{"01/32/2011"},{"15/12/2011"},{"02/29/2011"}
		};
	}
	
	@Test
	public void testConvertFromString() {
		DateConverter dc = new DateConverter();
		String[] dateStr = new String[1];
		assertNull(dc.convertFromString(null, null, null));
		assertNull(dc.convertFromString(null, dateStr, null));
		dateStr[0] = "";
		assertNull(dc.convertFromString(null, dateStr, null));
		dateStr[0] = "__/__/____";
		assertNull(dc.convertFromString(null, dateStr, null));
		dateStr[0] = dateString;
		assertEquals(dc.convertFromString(null, dateStr, null), date);
	}
	
	@Test
	public void testConvertToString() {
		DateConverter dc = new DateConverter();
		assertEquals(dc.convertToString(null, date), this.dateString);
		assertNull(dc.convertToString(null, null));
	}
	
	@Test(expectedExceptions = {TypeConversionException.class}, dataProvider = "exceptionData")
	public void testParseException(String dateStr) {
		String[] dateArr = {dateStr};
		DateConverter dc = new DateConverter();
		dc.convertFromString(null, dateArr, null);
	}
}