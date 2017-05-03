package gov.utah.dts.det.ccl.model;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class PhoneTest {
	
	@Test
	public void testGetterSetters() {
		String phoneNumber = "8015551212";
		Phone phone = new Phone();
		assertNull(phone.getPhoneNumber());
		phone.setPhoneNumber(phoneNumber);
		assertEquals(phone.getPhoneNumber(), phoneNumber);
	}
	
	@Test
	public void testGetFormattedPhoneNumber() {
		Phone phone = new Phone();
		assertTrue(StringUtils.isBlank(phone.getFormattedPhoneNumber()));
		phone.setPhoneNumber("801");
		assertTrue(StringUtils.isBlank(phone.getFormattedPhoneNumber()));
		phone.setPhoneNumber("5551212");
		assertTrue(phone.getFormattedPhoneNumber().equals("555-1212"));
		phone.setPhoneNumber("8015551212");
		assertTrue(phone.getFormattedPhoneNumber().equals("(801) 555-1212"));
		assertTrue(phone.getFormattedPhoneNumber().equals(phone.toString()));
	}

	@Test
	public void testHashCode() {
		Phone phone = new Phone();
		phone.setPhoneNumber("8015551212");
		int hashCode = phone.hashCode();
		assert hashCode != 0;
	}
	
	@Test
	public void testEquals() {
		Phone phone = new Phone();
		assertTrue(phone.equals(phone));
		assertFalse(phone.equals(null));
		assertFalse(phone.equals("8015551212"));
		Phone phone1 = new Phone();
		phone1.setPhoneNumber("8015551212");
		assertFalse(phone.equals(phone1));
		phone.setPhoneNumber("8015551212");
		assertTrue(phone.equals(phone1));
		Phone phone2 = new Phone();
		phone2.setPhoneNumber("8015551234");
		assertFalse(phone.equals(phone2));
	}
	
}