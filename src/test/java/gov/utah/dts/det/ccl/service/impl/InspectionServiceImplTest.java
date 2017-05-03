package gov.utah.dts.det.ccl.service.impl;

import static org.mockito.Mockito.mock;
import gov.utah.dts.det.ccl.dao.InspectionDao;
import gov.utah.dts.det.service.ApplicationService;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class InspectionServiceImplTest {

	private InspectionDao mockID;
	private ApplicationService mockAS;
	
	@BeforeClass
	public void createMocks() {
		mockID = mock(InspectionDao.class);
		mockAS = mock(ApplicationService.class);
	}
	
	/*@Test
	public void testUpdateFollowUpFlags() {
		InspectionServiceImpl isi = new InspectionServiceImpl();
		ReflectionTestUtils.setField(isi, "inspectionDao", mockID);
		ReflectionTestUtils.setField(isi, "applicationService", mockAS);
		
		Inspection i1 = new Inspection();
		i1.setId(1l);
		Inspection i2 = new Inspection();
		i2.setId(2l);
		
		when(mockID.save(i1)).thenReturn(i1);
		i1.setCmpDueDate(new Date());
		i1 = isi.saveInspection(i1);
		assertNull(i1.getCmpDueDate());
		
		//test rescinded
		Finding f1 = new Finding();
		f1.setRule(InspectionTest.ruleNormal);
		f1.setRescindedDate(new Date());
		i1.addFinding(f1);
		i1 = isi.saveInspection(i1);
		assertFalse(f1.isFollowUpNeeded());
		
		//test under appeal
//		f1.setFollowUpNeeded(true);
		f1.setRescindedDate(null);
		f1.setUnderAppeal(true);
		i1 = isi.saveInspection(i1);
		assertFalse(f1.isFollowUpNeeded());
		
		//test a normal rule that has not been corrected
//		f1.setFollowUpNeeded(true);
		f1.setUnderAppeal(false);
		i1 = isi.saveInspection(i1);
		assertTrue(f1.isFollowUpNeeded());
		
		//test a normal rule that has been corrected on site
		f1.setCorrectedOn(i1);
//		f1.setFollowUpNeeded(true);
		i1 = isi.saveInspection(i1);
		assertTrue(f1.isFollowUpNeeded());
		
		//test a normal rule that has been corrected on site but has a follow up
		f1.setCorrectedOn(i1);
//		f1.setFollowUpNeeded(true);
		f1.getFollowUps().add(i2);
		i1 = isi.saveInspection(i1);
		assertFalse(f1.isFollowUpNeeded());
		
		//test a normal rule that has been corrected on a different inspection
		f1.setCorrectedOn(i2);
//		f1.setFollowUpNeeded(true);
		i1 = isi.saveInspection(i1);
		assertFalse(f1.isFollowUpNeeded());
		
		//test a no follow up rule that has been corrected on site
		f1.setRule(InspectionTest.ruleNoFollowUp);
		f1.setCorrectedOn(i1);
//		f1.setFollowUpNeeded(true);
		i1 = isi.saveInspection(i1);
		assertFalse(f1.isFollowUpNeeded());
		
		//test a no follow up rule that has not been corrected on site (Bug #12270)
		f1.setCorrectedOn(null);
//		f1.setFollowUpNeeded(true);
		i1 = isi.saveInspection(i1);
		assertTrue(f1.isFollowUpNeeded());
	}*/
}