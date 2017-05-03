package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.util.TestUtils;

import java.text.ParseException;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class FindingTest {

	@Test
	public void testIsCorrectedOnSite() {
		Finding f = new Finding();
		RuleSubSection rss = new RuleSubSection();
		rss.setNoFollowUp(false);
		f.setRule(rss);
		Assert.assertFalse(f.isCorrectedOnSite());
		
		Inspection i1 = new Inspection();
		i1.setId(1l);
		Inspection i2 = new Inspection();
		f.setCorrectedOn(i2);
		Assert.assertFalse(f.isCorrectedOnSite());
		f.setInspection(i1);
		Assert.assertFalse(f.isCorrectedOnSite());
		f.setCorrectedOn(i1);
		Assert.assertTrue(f.isCorrectedOnSite());
	}
	
	@Test
	public void testIsFollowUpNeeded() {
		Finding f = new Finding();
		Assert.assertTrue(f.isFollowUpNeeded());
		f.setCorrectionVerification(CorrectionVerificationType.PROVIDER_CLOSED);
		Assert.assertFalse(f.isFollowUpNeeded());
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void testRescindNPE() {
		Finding f = new Finding();
		f.rescind(null);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testRescindIAE() throws ParseException {
		Inspection i = new Inspection();
		i.setInspectionDate(TestUtils.parseDate("01/01/2011"));
		Finding f = new Finding();
		f.setInspection(i);
		f.rescind(TestUtils.parseDate("12/31/2010"));
	}
	
	@Test
	public void testRescind() throws ParseException {
		Inspection i = new Inspection();
		i.setInspectionDate(TestUtils.parseDate("01/01/2011"));
		Finding f = new Finding();
		f.setInspection(i);
		Date rescindDate = TestUtils.parseDate("01/15/2011");
		f.rescind(rescindDate);
		Assert.assertEquals(f.getRescindedDate(), rescindDate);
		Assert.assertEquals(f.getCorrectionVerification(), CorrectionVerificationType.RESCINDED);
	}
	
	@Test
	public void unrescind() throws ParseException {
		Inspection i = new Inspection();
		i.setInspectionDate(TestUtils.parseDate("01/01/2011"));
		Finding f = new Finding();
		f.setInspection(i);
		f.rescind(TestUtils.parseDate("01/15/2011"));
		Assert.assertNotNull(f.getRescindedDate());
		Assert.assertEquals(f.getCorrectionVerification(), CorrectionVerificationType.RESCINDED);
		f.unrescind();
		Assert.assertNull(f.getRescindedDate());
		Assert.assertEquals(f.getCorrectionVerification(), CorrectionVerificationType.VERIFICATION_PENDING);
	}
}