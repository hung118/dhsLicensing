/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "main", "facility" })
public class FacilityTest {
	
	/*@Test
	public void testGetCurrentLicense() {
		Facility f1 = new Facility();
		
		//null should be returned when the facility has no license
		assertNull(f1.getCurrentLicense());
		
		//null should be returned when the facility has a license but has expired
		License l1 = new License();
		l1.setStartDate(DateUtils.addDays(new Date(), -60));
		l1.setExpirationDate(DateUtils.addDays(new Date(), -30));
		f1.addLicense(l1);
		assertNull(f1.getCurrentLicense());
		
		//check a normal license
		Facility f2 = new Facility();
		
		License l2 = new License();
		l2.setStartDate(DateUtils.addDays(new Date(), -30));
		l2.setExpirationDate(DateUtils.addDays(new Date(), 30));
		f2.addLicense(l2);
		assertEquals(l2, f2.getCurrentLicense());
	}*/
	
	@Test
	public void testGetLatestLicense() {
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		Facility f1 = new Facility();
		
		//null should be returned when the facility has no license
		assertNull(f1.getLatestLicense());
		
		//future licenses should not be returned
		License l2 = new License();
		l2.setStartDate(DateUtils.addDays(now, 30));
		l2.setExpirationDate(DateUtils.addDays(now, 60));
		
		//licenses that have expired should be returned
		License l3 = new License();
		l3.setStartDate(DateUtils.addDays(now, -60));
		l3.setExpirationDate(DateUtils.addDays(now, -30));
		f1.addLicense(l3);
		assertEquals(f1.getLatestLicense(), l3);
		
		//current licenses should be returned
		License l4 = new License();
		l4.setStartDate(DateUtils.addDays(now, -29));
		l4.setExpirationDate(DateUtils.addDays(now, 29));
		f1.addLicense(l4);
		assertEquals(f1.getLatestLicense(), l4);
	}
	
	@Test
	public void testGetActiveLicenses() {
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		Facility f1 = new Facility();
		
		//an empty list should be returned when there are no licenses
		assertTrue(f1.getActiveLicenses().isEmpty());
		
		License l1 = new License();
		l1.setStartDate(DateUtils.addDays(now, -60));
		l1.setExpirationDate(DateUtils.addDays(now, -30));
		f1.addLicense(l1);
		assertTrue(f1.getActiveLicenses().isEmpty());
		
		License l2 = new License();
		l2.setStartDate(DateUtils.addDays(now, -30));
		l2.setExpirationDate(DateUtils.addDays(now, 30));
		f1.addLicense(l2);
		//List<License> test = f1.getActiveLicenses();
		assertTrue(f1.getActiveLicenses().size() == 0);
		//assertEquals(f1.getActiveLicenses().get(0), l2);
	}
	
	@Test
	public void testRemoveLicense() {
		Facility f1 = new Facility();
		assertTrue(f1.getLicenses().isEmpty());
		License lic = new License();
		f1.addLicense(lic);
		Assert.assertFalse(f1.getLicenses().isEmpty());
		f1.removeLicense(lic);
		assertTrue(f1.getLicenses().isEmpty());
	}
	
	@Test
	public void testGetActiveExemptions() {
		Date now = DateUtils.truncate(new Date(), Calendar.DATE);
		Facility f1 = new Facility();
		
		//an empty list should be returned when there are no exemptions
		assertTrue(f1.getActiveExemptions().isEmpty());
		
		Exemption e1 = new Exemption();
		e1.setStartDate(DateUtils.addDays(now, -60));
		e1.setExpirationDate(DateUtils.addDays(now, -30));
		f1.addExemption(e1);
		assertTrue(f1.getActiveLicenses().isEmpty());
		
		Exemption e2 = new Exemption();
		e2.setStartDate(DateUtils.addDays(now, -30));
		e2.setExpirationDate(DateUtils.addDays(now, 30));
		f1.addExemption(e2);
		assertTrue(f1.getActiveExemptions().size() == 1);
		assertEquals(f1.getActiveExemptions().get(0), e2);
	}
	
	@Test
	public void testRemoveExemption() {
		Facility f1 = new Facility();
		assertTrue(f1.getExemptions().isEmpty());
		Exemption ex = new Exemption();
		f1.addExemption(ex);
		Assert.assertFalse(f1.getExemptions().isEmpty());
		f1.removeExemption(ex);
		assertTrue(f1.getExemptions().isEmpty());
	}
}