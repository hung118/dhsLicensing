package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class RuleTest {
	
	@Test
	public void testCompareToActive() {
		Rule r1 = new Rule();
		r1.setActive(false);
		Rule r2 = new Rule();
		r2.setActive(true);
		assertTrue(r1.compareTo(r2) > 0);
		assertTrue(r2.compareTo(r1) < 0);
	}
	
	@Test
	public void testCompareToSortOrder() {
		Rule r1 = new Rule();
		r1.setSortOrder(null);
		Rule r2 = new Rule();
		r2.setSortOrder(1.0);
		assertTrue(r1.compareTo(r2) > 0);
		
		r1.setSortOrder(2.0);
		assertTrue(r1.compareTo(r2) > 0);
		
		r2.setSortOrder(null);
		assertTrue(r1.compareTo(r2) < 0);
	}

	@Test
	public void testCompareToRuleNumber() {
		Rule r1 = new Rule();
		Rule r2 = new Rule();
		
		assertTrue(r1.compareTo(r2) == 0);
		r1.setNumber("100");
		assertTrue(r1.compareTo(r2) > 0);
		assertTrue(r2.compareTo(r1) < 0);
		r2.setNumber("100");
		assertTrue(r1.compareTo(r2) == 0);
		r2.setNumber("60");
		assertTrue(r1.compareTo(r2) < 0);
	}
}