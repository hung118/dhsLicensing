package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class RuleSubSectionTest {

	@Test
	public void testCompareToSection() {
		Rule r1 = new Rule();
		RuleSection rs1 = new RuleSection();
		rs1.setSectionBase(1);
		rs1.setNumber(1);
		RuleSection rs2 = new RuleSection();
		rs2.setSectionBase(2);
		rs2.setNumber(2);
		r1.addSection(rs1);
		r1.addSection(rs2);
		
		RuleSubSection rss1 = new RuleSubSection();
		RuleSubSection rss2 = new RuleSubSection();
		
		rs2.addSubSection(rss2);
		assertTrue(rss1.compareTo(rss2) > 0);
		assertTrue(rss2.compareTo(rss1) < 0);
		
		rs1.addSubSection(rss1);
		assertTrue(rss1.compareTo(rss2) < 0);
	}
	
	@Test
	public void testCompareToActive() {
		Rule rule = new Rule();
		RuleSection section = new RuleSection();
		rule.addSection(section);
		
		RuleSubSection rss1 = new RuleSubSection();
		RuleSubSection rss2 = new RuleSubSection();
		section.addSubSection(rss1);
		section.addSubSection(rss2);
		
		rss1.setActive(false);
		rss2.setActive(true);
		
		assertTrue(rss1.compareTo(rss2) > 0);
		assertTrue(rss2.compareTo(rss1) < 0);
	}
	
	@Test
	public void testCompareToSortOrder() {
		Rule rule = new Rule();
		RuleSection section = new RuleSection();
		rule.addSection(section);
		
		RuleSubSection rss1 = new RuleSubSection();
		RuleSubSection rss2 = new RuleSubSection();
		section.addSubSection(rss1);
		section.addSubSection(rss2);
		
		rss1.setSortOrder(null);
		rss2.setSortOrder(1.0);
		assertTrue(rss1.compareTo(rss2) > 0);
		
		rss1.setSortOrder(2.0);
		assertTrue(rss1.compareTo(rss2) > 0);
		
		rss2.setSortOrder(null);
		assertTrue(rss1.compareTo(rss2) < 0);
	}

	@Test
	public void testCompareToRuleNumber() {
		Rule rule = new Rule();
		RuleSection section = new RuleSection();
		rule.addSection(section);
		
		RuleSubSection rss1 = new RuleSubSection();
		RuleSubSection rss2 = new RuleSubSection();
		section.addSubSection(rss1);
		section.addSubSection(rss2);
		
		assertTrue(rss1.compareTo(rss2) == 0);
		rss1.setNumber("100");
		assertTrue(rss1.compareTo(rss2) > 0);
		assertTrue(rss2.compareTo(rss1) < 0);
		rss2.setNumber("100");
		assertTrue(rss1.compareTo(rss2) == 0);
		rss2.setNumber("60");
		assertTrue(rss1.compareTo(rss2) < 0);
	}
}