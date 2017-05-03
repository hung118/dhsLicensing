package gov.utah.dts.det.ccl.model;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PersonTest {

	@Test
	public void testGetAge() {
		Person person = new Person();
		Assert.assertNull(person.getBirthday());
		Assert.assertNull(person.getAge());
		
		Date date = new Date();
		date = DateUtils.addYears(date, -25);
		person.setBirthday(date);
		Assert.assertEquals(person.getAge(), new Integer(25));
		
		date = DateUtils.addDays(date, 10);
		Assert.assertEquals(person.getAge(), new Integer(25));
		person.setBirthday(date);
		Assert.assertEquals(person.getAge(), new Integer(24));
	}
}