package gov.utah.dts.det.ccl.view.converter;

import gov.utah.dts.det.ccl.model.Phone;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test(groups = {"main"})
public class PhoneNumberConverterTest {

	public void testConvertFromString() {
		PhoneNumberConverter pnc = new PhoneNumberConverter();
		String[] phoneStr = new String[1];
		assertNull(pnc.convertFromString(null, null, null));
		assertNull(pnc.convertFromString(null, phoneStr, null));
		phoneStr[0] = "";
		assertNull(pnc.convertFromString(null, phoneStr, null));
	}
	
	public void testConvertToSTring() {
		PhoneNumberConverter pnc = new PhoneNumberConverter();
		assertNull(pnc.convertToString(null, null));
		String number = "8015551212";
		Phone phone = new Phone();
		phone.setPhoneNumber(number);
		assertEquals(pnc.convertToString(null, phone), number);
	}
}