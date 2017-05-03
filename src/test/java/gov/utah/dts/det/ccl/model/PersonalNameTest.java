package gov.utah.dts.det.ccl.model;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PersonalNameTest {

	@Test
	public void testFirstAndLastName() {
		PersonalName name = new PersonalName();
		Assert.assertEquals(name.getFirstAndLastName(), "");
		name.setFirstName("John");
		Assert.assertEquals(name.getFirstAndLastName(), "John");
		name.setLastName("Doe");
		Assert.assertEquals(name.getFirstAndLastName(), "John Doe");
		name.setMiddleName("Frank");
		Assert.assertEquals(name.getFirstAndLastName(), "John Doe");
		name = new PersonalName("Jane", "Doe");
		Assert.assertEquals(name.getFirstAndLastName(), "Jane Doe");
		Assert.assertEquals(name.toString(), "Jane Doe");
	}
	
	@Test
	public void testFullName() {
		PersonalName name = new PersonalName();
		Assert.assertEquals(name.getFullName(), "");
		name.setFirstName("John");
		Assert.assertEquals(name.getFullName(), "John");
		name.setLastName("Doe");
		Assert.assertEquals(name.getFullName(), "John Doe");
		name.setMiddleName("Frank");
		Assert.assertEquals(name.getFullName(), "John Frank Doe");
		name = new PersonalName("Jane", "Marie", "Doe");
		Assert.assertEquals(name.getFirstName(), "Jane");
		Assert.assertEquals(name.getMiddleName(), "Marie");
		Assert.assertEquals(name.getLastName(), "Doe");
		Assert.assertEquals(name.getFullName(), "Jane Marie Doe");
		Assert.assertEquals(name.toString(), "Jane Doe");
	}
}