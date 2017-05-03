package gov.utah.dts.det.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.repository.ApplicationPropertyRepository;
import gov.utah.dts.det.repository.PickListValueRepository;
import gov.utah.dts.det.service.ApplicationService;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class ApplicationServiceImplTest {
	
	private static final String INVALID_DATA_KEY = "INVALID-DATA";
	private static final String NO_DATA_KEY = "NO-DATA";
	private static final String SINGLE_PLV_KEY = "SINGLE-PLV";
	private static final String MULTIPLE_PLV_KEY = "MULTIPLE-PLV";
	
	private PickListValueRepository mockedPlvRepo;
	private PickListValue plvOne;
	private PickListValue plvTwo;
	private PickListValue plvThree;
	private ApplicationPropertyRepository mockedApRepo;
	
	@BeforeClass
	public void createMocks() {
		mockedPlvRepo = mock(PickListValueRepository.class);
		plvOne = mock(PickListValue.class);
		when(plvOne.getId()).thenReturn(1l);
		plvTwo = mock(PickListValue.class);
		when(plvTwo.getId()).thenReturn(2l);
		plvThree = mock(PickListValue.class);
		when(plvThree.getId()).thenReturn(3l);
		
		when(mockedPlvRepo.findOne(1l)).thenReturn(plvOne);
		when(mockedPlvRepo.findOne(2l)).thenReturn(plvTwo);
		when(mockedPlvRepo.findOne(3l)).thenReturn(plvThree);
		
		ApplicationProperty invalidProp = new ApplicationProperty();
		invalidProp.setName(INVALID_DATA_KEY);
		invalidProp.setValue("a,b,c");
		
		ApplicationProperty noDataProp = new ApplicationProperty();
		noDataProp.setName(NO_DATA_KEY);
		
		ApplicationProperty singlePlvProp = new ApplicationProperty();
		singlePlvProp.setName(SINGLE_PLV_KEY);
		singlePlvProp.setValue("1");
		
		ApplicationProperty multiPlvProp = new ApplicationProperty();
		multiPlvProp.setName(MULTIPLE_PLV_KEY);
		multiPlvProp.setValue("1,2,3");
		
		mockedApRepo = mock(ApplicationPropertyRepository.class);
		when(mockedApRepo.findOne(INVALID_DATA_KEY)).thenReturn(invalidProp);
		when(mockedApRepo.findOne(NO_DATA_KEY)).thenReturn(noDataProp);
		when(mockedApRepo.findOne(SINGLE_PLV_KEY)).thenReturn(singlePlvProp);
		when(mockedApRepo.findOne(MULTIPLE_PLV_KEY)).thenReturn(multiPlvProp);
	}
	
	public ApplicationService getNewApplicationService() {
		ApplicationService appService = new ApplicationServiceImpl();
		ReflectionTestUtils.setField(appService, "applicationPropertyRepository", mockedApRepo);
		ReflectionTestUtils.setField(appService, "pickListValueRepository", mockedPlvRepo);
		return appService;
	}
	
	@DataProvider(name = "plvErrorData")
	public Object[][] getPlvErrorData() {
		return new Object[][] {
			{null},{INVALID_DATA_KEY},{"INVALID-KEY"}
		};
	}

	@Test(dataProvider = "plvErrorData", expectedExceptions = {IllegalArgumentException.class, NumberFormatException.class})
	public void testGetPickListValuesForApplicationPropertyIllegalArgumentException(String key) {
		getNewApplicationService().getPickListValuesForApplicationProperty(key);
	}
	
	@Test(dataProvider = "plvErrorData", expectedExceptions = {IllegalArgumentException.class, NumberFormatException.class})
	public void testGetPickListValueForApplicationPropertyIllegalArgumentException(String key) {
		getNewApplicationService().getPickListValueForApplicationProperty(key);	
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testGetPickListValueForApplicationPropertyMultiplePlvs() {
		//should throw an exception when getting a single value but multiple values are returned
		getNewApplicationService().getPickListValueForApplicationProperty(MULTIPLE_PLV_KEY);
	}
	
	@Test
	public void testGetPickListValuesForApplicationProperty() {
		//test with a property that has multiple values
		List<PickListValue> values = getNewApplicationService().getPickListValuesForApplicationProperty(MULTIPLE_PLV_KEY);
		assertEquals(values.size(), 3);
		assertTrue(values.contains(plvOne) && values.contains(plvTwo) && values.contains(plvThree));
		
		//test with a property that has a single value
		values = getNewApplicationService().getPickListValuesForApplicationProperty(SINGLE_PLV_KEY);
		assertEquals(values.size(), 1);
		assertTrue(values.contains(plvOne));
		
		//test with a property that has no value
		values = getNewApplicationService().getPickListValuesForApplicationProperty(NO_DATA_KEY);
		assertNotNull(values);
		assertTrue(values.isEmpty());
	}
	
	@Test
	public void testGetPickListValueForApplicationProperty() {
		PickListValue value = getNewApplicationService().getPickListValueForApplicationProperty(SINGLE_PLV_KEY);
		assertEquals(plvOne, value);
		
		assertNull(getNewApplicationService().getPickListValueForApplicationProperty(NO_DATA_KEY));
	}
	
	@Test
	public void testPropertyContainsPickListValue() {
		ApplicationService appServ = getNewApplicationService();
		assertTrue(appServ.propertyContainsPickListValue(plvOne, MULTIPLE_PLV_KEY));
		assertFalse(appServ.propertyContainsPickListValue(plvTwo, SINGLE_PLV_KEY));
		assertFalse(appServ.propertyContainsPickListValue(plvOne, NO_DATA_KEY));
	}
	
	@DataProvider(name = "containsErrorData")
	public Object[][] getContainsErrorData() {
		return new Object[][] {
			{null, null}, {plvOne, null}, {plvOne, "Invalid property key"}
		};
	}
	
	@Test(dataProvider = "containsErrorData", expectedExceptions = {IllegalArgumentException.class, NumberFormatException.class})
	public void testPropertyContainsPickListValueException(PickListValue plv, String key) {
		ApplicationService appServ = getNewApplicationService();
		appServ.propertyContainsPickListValue(plv, key);
	}
	
	@Test
	public void testSaveApplicationProperty() {
		ApplicationProperty property = new ApplicationProperty();
		getNewApplicationService().saveApplicationProperty(property);
		verify(mockedApRepo).save(property);
	}
	
	@Test
	public void testFindApplicationProperty() {
		getNewApplicationService().findApplicationPropertyByKey("TEST-KEY");
		verify(mockedApRepo).findOne("TEST-KEY");
	}
	
	@Test
	public void testGetApplicationPropertyValue() {
		String value = getNewApplicationService().getApplicationPropertyValue(SINGLE_PLV_KEY);
		assertEquals(value, "1");
		value = getNewApplicationService().getApplicationPropertyValue(NO_DATA_KEY);
		assertNull(value);
		value = getNewApplicationService().getApplicationPropertyValue("NO-KEY");
		assertNull(value);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testGetApplicationPropertyValueException() {
		getNewApplicationService().getApplicationPropertyValue(null);
	}
}