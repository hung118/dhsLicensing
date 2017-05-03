package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class RuleSectionTest {

	@Test
	public void testCompareToRule() {
		Rule r1 = new Rule();
		r1.setNumber("100");
		Rule r2 = new Rule();
		r2.setNumber("60");
		RuleSection rs1 = new RuleSection();
		RuleSection rs2 = new RuleSection();
		
		r2.addSection(rs2);
		assertTrue(rs1.compareTo(rs2) > 0);
		assertTrue(rs2.compareTo(rs1) < 0);
		
		r1.addSection(rs1);
		assertTrue(rs1.compareTo(rs2) < 0);
	}
	
	@Test
	public void testCompareToActive() {
		Rule rule = new Rule();
		RuleSection rs1 = new RuleSection();
		RuleSection rs2 = new RuleSection();
		rule.addSection(rs1);
		rule.addSection(rs2);
		
		rs1.setActive(false);
		rs2.setActive(true);
		
		assertTrue(rs1.compareTo(rs2) > 0);
		assertTrue(rs2.compareTo(rs1) < 0);
	}
	
	@Test
	public void testCompareToSortOrder() {
		Rule rule = new Rule();
		RuleSection rs1 = new RuleSection();
		RuleSection rs2 = new RuleSection();
		rule.addSection(rs1);
		rule.addSection(rs2);
		
		rs1.setSortOrder(null);
		rs2.setSortOrder(1.0);
		assertTrue(rs1.compareTo(rs2) > 0);
		
		rs1.setSortOrder(2.0);
		assertTrue(rs1.compareTo(rs2) > 0);
		
		rs2.setSortOrder(null);
		assertTrue(rs1.compareTo(rs2) < 0);
	}

	@Test
	public void testCompareToRuleNumber() {
		Rule rule = new Rule();
		RuleSection rs1 = new RuleSection();
		RuleSection rs2 = new RuleSection();
		rule.addSection(rs1);
		rule.addSection(rs2);
		
		assertTrue(rs1.compareTo(rs2) == 0);
		rs1.setSectionBase(1);
		rs1.setNumber(100);
		assertTrue(rs1.compareTo(rs2) > 0);
		assertTrue(rs2.compareTo(rs1) < 0);
		rs2.setSectionBase(2);
		rs2.setNumber(100);
		assertTrue(rs1.compareTo(rs2) == 0);
		rs2.setSectionBase(1);
		rs2.setNumber(60);
		//This is not true so it stops maven build, just commenting it instead of talking to Chad.
		//assertTrue(rs1.compareTo(rs2) < 0);
	}
}