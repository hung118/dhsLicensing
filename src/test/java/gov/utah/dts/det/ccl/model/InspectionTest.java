package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class InspectionTest {

	public static RuleSubSection rulePaperworkRequired;
	public static RuleSubSection ruleNoFollowUp;
	public static RuleSubSection ruleNormal;
	
	static {
		Rule rule = new Rule();
		rule.setId(1l);
		rule.setNumber("100");
		
		RuleSection section = new RuleSection();
		section.setId(1l);
		section.setSectionBase(1);
		section.setNumber(1);
		rule.addSection(section);
		
		rulePaperworkRequired = new RuleSubSection();
		rulePaperworkRequired.setId(1l);
		rulePaperworkRequired.setNumber("(a):09");
		rulePaperworkRequired.setNoFollowUp(false);
		rulePaperworkRequired.setPaperworkRequired(true);
		section.addSubSection(rulePaperworkRequired);
		
		ruleNoFollowUp = new RuleSubSection();
		ruleNoFollowUp.setId(2l);
		ruleNoFollowUp.setNumber("(b)-(c):09");
		ruleNoFollowUp.setNoFollowUp(true);
		ruleNoFollowUp.setPaperworkRequired(false);
		section.addSubSection(ruleNoFollowUp);
		
		ruleNormal = new RuleSubSection();
		ruleNormal.setId(3l);
		ruleNormal.setNumber("(d):09");
		ruleNormal.setNoFollowUp(false);
		ruleNormal.setPaperworkRequired(false);
		section.addSubSection(ruleNormal);
	}
	
	@Test
	public void testIsFollowUpRequired() {
		Inspection ins = new Inspection();
		assertFalse(ins.isFollowUpRequired());
		Finding f1 = new Finding();
		f1.setRule(ruleNoFollowUp);
		ins.addFinding(f1);
		assertTrue(ins.isFollowUpRequired());
//		Finding f2 = new Finding();
//		//f1.setFollowUpNeeded(true);
//		f2.setRule(ruleNoFollowUp);
//		ins.addFinding(f2);
//		assertTrue(ins.isFollowUpRequired());
	}
	
	@Test
	public void testIsAllPaperworkOrNoFollowUp() {
		Finding fpr = new Finding();
		fpr.setRule(rulePaperworkRequired);
		Finding fnf = new Finding();
		fnf.setRule(ruleNoFollowUp);
		Finding fn = new Finding();
		fn.setRule(ruleNormal);
		
		Inspection oIns = new Inspection();
		oIns.setId(2l);
		
		Inspection ins = new Inspection();
		ins.setId(1l);
		//return true if there are no findings
		assertTrue(ins.isAllPaperworkOrNoFollowUp());
		
		//return true if there are just paperwork required rules
		ins.addFinding(fpr);
		assertTrue(ins.isAllPaperworkOrNoFollowUp());
		
		//return true if there is a paperwork required finding and a finding that doesn't need a follow up if it has been corrected on site
		//and it has been corrected on site
		fnf.setCorrectedOn(ins);
		ins.addFinding(fnf);
		assertTrue(ins.isAllPaperworkOrNoFollowUp());
		
		//return false if we switch the finding to not having been corrected on site
		fnf.setCorrectedOn(null);
		assertFalse(ins.isAllPaperworkOrNoFollowUp());
		
		//still return false if the finding was corrected on a different inspection
		fnf.setCorrectedOn(oIns);
		assertFalse(ins.isAllPaperworkOrNoFollowUp());
		
		//remove the follow up, leaving only the paperwork required and make sure we are back to true
		ins.removeFinding(fnf);
		assertTrue(ins.isAllPaperworkOrNoFollowUp());
		
		//add a normal rule and we should be back to false
		ins.addFinding(fn);
		assertFalse(ins.isAllPaperworkOrNoFollowUp());
	}
}