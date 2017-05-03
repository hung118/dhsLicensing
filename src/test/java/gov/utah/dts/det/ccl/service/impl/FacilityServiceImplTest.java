package gov.utah.dts.det.ccl.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.FacilityDao;
import gov.utah.dts.det.ccl.model.Address;
import gov.utah.dts.det.ccl.model.Exemption;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.service.ApplicationService;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class FacilityServiceImplTest {
	
	private static final String[] DATE_PATTERN = {"MM/dd/yyyy"};
	
	private PickListValue mockedCenterType;
	private PickListValue mockedHourlyCenterType;
	private PickListValue mockedNoneType;
	private PickListValue mockedInProcessType;
	private ApplicationService mockedAs;
	private RegionService mockedRs;
	
	private Region regionOne;
	private Region regionTwo;
	
	private Address addrInNoR;
	private Address addrInR1;
	
	private Person lSpecNoRegion;
	private Person lSpecRegionOne;
	private Person lSpecRegionTwo;
	
	@BeforeClass
	public void createMocks() {
		mockedCenterType = mock(PickListValue.class);
		when(mockedCenterType.getValue()).thenReturn("Center");
		mockedHourlyCenterType = mock(PickListValue.class);
		when(mockedHourlyCenterType.getValue()).thenReturn("Hourly Center");
		mockedNoneType = mock(PickListValue.class);
		when(mockedNoneType.getValue()).thenReturn("None");
		
		mockedAs = mock(ApplicationService.class);
		
		regionOne = new Region();
		regionOne.setId(1l);
		
		regionTwo = new Region();
		regionTwo.setId(2l);
		
		lSpecNoRegion = new Person();
		lSpecNoRegion.setId(1l);

		lSpecRegionOne = new Person();
		lSpecRegionOne.setId(2l);
		
		lSpecRegionTwo = new Person();
		lSpecRegionTwo.setId(3l);
		
		mockedRs = mock(RegionService.class);
		when(mockedRs.getRegionForSpecialist(lSpecNoRegion.getId())).thenReturn(null);
		when(mockedRs.getRegionForSpecialist(lSpecRegionOne.getId())).thenReturn(regionOne);
		when(mockedRs.getRegionForSpecialist(lSpecRegionTwo.getId())).thenReturn(regionTwo);
		when(mockedRs.getRegionForAddress(addrInNoR)).thenReturn(null);
		when(mockedRs.getRegionForAddress(addrInR1)).thenReturn(regionOne);
	}
	
	private FacilityServiceImpl getNewFacilityServiceImpl(FacilityDao facilityDao) {
		FacilityServiceImpl fsi = new FacilityServiceImpl();
		
		ReflectionTestUtils.setField(fsi, "applicationService", mockedAs);
		ReflectionTestUtils.setField(fsi, "facilityDao", facilityDao == null ? mock(FacilityDao.class) : facilityDao);
		ReflectionTestUtils.setField(fsi, "regionService", mockedRs);
		ReflectionTestUtils.setField(fsi, "complaintService", mock(ComplaintService.class));
		
		return fsi;
	}
		
	private License mockLicense(Long id, PickListValue subType, String startDate, String endDate) throws ParseException {
		License l = mock(License.class);
		when(l.getId()).thenReturn(id);
		when(l.getSubtype()).thenReturn(subType);
		when(l.getStartDate()).thenReturn(DateUtils.parseDate(startDate, DATE_PATTERN));
		when(l.getExpirationDate()).thenReturn(DateUtils.parseDate(endDate, DATE_PATTERN));
		when(l.getEndDate()).thenReturn(DateUtils.parseDate(endDate, DATE_PATTERN));
		return l;
	}
	
	private License createLicense(Long id, Facility facility, PickListValue subType, String startDate, String expirationDate) throws ParseException {
		return createLicense(id, facility, subType, DateUtils.parseDate(startDate, DATE_PATTERN), DateUtils.parseDate(expirationDate, DATE_PATTERN), null, null);
	}
	
	private License createLicense(Long id, Facility facility, PickListValue subType, Date startDate, Date expirationDate, Integer adultSlots, Integer youthSlots) throws ParseException {
		License l = new License();
		l.setId(id);
		l.setFacility(facility);
		l.setSubtype(subType);
		l.setAdultTotalSlots(adultSlots);
		l.setYouthTotalSlots(youthSlots);
		l.setStartDate(startDate);
		l.setExpirationDate(expirationDate);
		return l;
	}
	
	private Date getDate(String date) throws ParseException {
		return DateUtils.parseDate(date, DATE_PATTERN);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testCreateLicensedFacilityNullFacility() {
		getNewFacilityServiceImpl(null).createLicensedFacility(null, null);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testCreateLicensedFacilityNullLSpec() {
		getNewFacilityServiceImpl(null).createLicensedFacility(new Facility(), null);
	}
	
	@Test(expectedExceptions = {CclServiceException.class})
	public void testCreateLicensedFacilityNullLicType() {
		getNewFacilityServiceImpl(null).createLicensedFacility(new Facility(), new Person());
	}
	
	@Test(expectedExceptions = {CclServiceException.class})
	public void testCreateLicensedFacilityNullLicHolder() {
		getNewFacilityServiceImpl(null).createLicensedFacility(new Facility(), new Person());
	}
	
	@Test(expectedExceptions = {CclServiceException.class})
	public void testCreateLicensedFacilityNoRegion() {
		Facility f1 = new Facility();
		FacilityServiceImpl fsi = getNewFacilityServiceImpl(null);
		fsi.createLicensedFacility(f1, lSpecNoRegion);
	}
	
	@Test
	public void testCreateLicensedFacility() {
		Facility f1 = new Facility();
		assertTrue(f1.getLicenses().isEmpty());
		
		FacilityDao mockedFd = mock(FacilityDao.class);
		when(mockedFd.save(f1)).thenReturn(f1);
		FacilityServiceImpl fsi = getNewFacilityServiceImpl(mockedFd);
		f1 = fsi.createLicensedFacility(f1, lSpecRegionOne);
		
		assertEquals(FacilityStatus.IN_PROCESS, f1.getStatus());
		assertFalse(f1.getLicenses().isEmpty());
		assertEquals(lSpecRegionOne, f1.getLicensingSpecialist());
		
		License lic = f1.getLicenses().iterator().next();
		assertEquals(f1, lic.getFacility());
		assertEquals(mockedInProcessType, lic.getSubtype());
		assertEquals(DateUtils.truncate(new Date(), Calendar.DATE), lic.getStartDate());
		assertEquals(License.DEFAULT_LICENSE_END_DATE, lic.getExpirationDate());
		assertFalse(lic.isCalculatesAlerts());
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testCreateUnlicensedFacilityNullFacility() {
		getNewFacilityServiceImpl(null).createUnlicensedFacility(null, null, null);
	}
	
	@Test
	public void testCreateUnlicensedFacility() {
		Facility f1 = new Facility();
		f1.setLocationAddress(addrInR1);
		assertTrue(f1.getPeople().isEmpty());
		
		FacilityDao mockedFd = mock(FacilityDao.class);
		when(mockedFd.save(f1)).thenReturn(f1);
		FacilityServiceImpl fsi = getNewFacilityServiceImpl(mockedFd);
		
		fsi.createUnlicensedFacility(f1, new Person(), new UnlicensedComplaint());
		assertFalse(f1.getPeople().isEmpty());
		assertEquals(FacilityStatus.INACTIVE, f1.getStatus());
		
		assertEquals(regionOne, f1.getRegion());
	}
	
	@DataProvider(name = "licenseValidationData")
	public Object[][] createLicenseValidationExceptionData() throws ParseException {
		//licenses should never overlap each other
		Facility f1 = new Facility();
		f1.getLicenses().add(mockLicense(1l, mockedNoneType, "02/01/2011", "02/29/2011"));
		License l1 = mockLicense(2l, mockedNoneType, "02/01/2011", "02/29/2011");
		
		//licenses should never overlap an exemption
		Exemption e1 = new Exemption();
		e1.setStartDate(getDate("01/01/2011"));
		e1.setExpirationDate(getDate("02/15/2011"));
		
		Facility f2 = new Facility();
		f2.addExemption(e1);
		
		return new Object[][] {
			{f1, l1}, {f2, l1}
		};
	}
	
//	@Test(expectedExceptions = {CclServiceException.class}, dataProvider = "licenseValidationData")
//	public void testLicenseValidationFailure(Facility facility, License license) {
//		FacilityServiceImpl fsi = getNewFacilityServiceImpl(null);
//		
//		fsi.validateLicense(facility, license);
//	}
	
    /*
     * RM #25806. It is now ok to have overlapping licenses so validateLicense call is no longer necessary.
    */
/*
	@Test
	public void testLicenseValidationSuccess() throws ParseException {
		FacilityServiceImpl fsi = getNewFacilityServiceImpl(null);
		
		//valid first license
		Facility f1 = new Facility();
		License l1 = mockLicense(2l, mockedCenterType, mockedRegularType, "01/01/2011", "01/31/2011");
		fsi.validateLicense(f1, l1);
		
		//valid 2nd license
		Facility f2 = new Facility();
		f2.getLicenses().add(mockLicense(1l, mockedCenterType, mockedRegularType, "01/01/2011", "01/31/2011"));
		License l2 = mockLicense(2l, mockedCenterType, mockedRegularType, "02/01/2011", "02/29/2011");
		fsi.validateLicense(f2, l2);
	}
*/	
	@Test
	public void testSetLicensingSpecialist() {
		FacilityServiceImpl fsi = getNewFacilityServiceImpl(null);
		Facility f1 = new Facility();
		
		//no region should be set
		fsi.setLicensingSpecialist(f1, lSpecNoRegion);
		assertNull(f1.getRegion());
		assertEquals(lSpecNoRegion, f1.getLicensingSpecialist());
		
		//specialist and region shouldn't change
		fsi.setLicensingSpecialist(f1, null);
		assertNull(f1.getRegion());
		assertEquals(lSpecNoRegion, f1.getLicensingSpecialist());
		
		//specialist and region should be set
		fsi.setLicensingSpecialist(f1, lSpecRegionOne);
		assertEquals(regionOne, f1.getRegion());
		assertEquals(lSpecRegionOne, f1.getLicensingSpecialist());
		
		//specialist should be set and region should stay the same
		fsi.setLicensingSpecialist(f1, lSpecNoRegion);
		assertEquals(regionOne, f1.getRegion());
		assertEquals(lSpecNoRegion, f1.getLicensingSpecialist());
	}
	
	@DataProvider(name = "licenseExtensionData")
	public Object[][] createLicenseExtensionExceptionData() throws ParseException {
		//throw exception if the license overlaps another license
		Facility f1 = new Facility();
		License l1 = createLicense(1l, f1, mockedNoneType, "01/01/2011", "01/31/2011");
		f1.getLicenses().add(l1);
		f1.getLicenses().add(createLicense(2l, f1, mockedNoneType, "02/01/2011", "02/28/2011"));
		Date t1Date = DateUtils.parseDate("02/15/2011", DATE_PATTERN);
		
		//throw exception if the license extension date is before the current expiration date
		Facility f2 = new Facility();
		License l2 = createLicense(1l, f2, mockedNoneType, "01/01/2011", "03/31/2011");
		f2.getLicenses().add(l2);
		Date t2Date = DateUtils.parseDate("02/28/2011", DATE_PATTERN);
		return new Object[][] {
			{f1, l1, t1Date}, {f2, l2, t2Date}
		};
	}
	
//	@Test(expectedExceptions = {CclServiceException.class}, dataProvider = "licenseExtensionData")
//	public void testLicenseExtensionFailure(Facility facility, License license, Date extensionExpirationDate) {
//		FacilityServiceImpl fsi = getNewFacilityServiceImpl(null);
//		
//		fsi.extendLicense(license, extensionExpirationDate);
//	}
	
	@DataProvider(name = "licenseTypeExceptionData")
	public Object[][] createLicenseTypeChangeExceptionData() throws ParseException {
		//don't allow the new type start date to be out of the license range
		Facility f1 = new Facility();
		License l1 = createLicense(1l, f1, mockedNoneType, getDate("01/01/2011"), getDate("03/31/2011"), 10, 10);
		
		return new Object[][] {
			{l1, getDate("12/01/2010"), mockedHourlyCenterType}, {l1, getDate("04/01/2010"), mockedHourlyCenterType}
		};
	}
	
}