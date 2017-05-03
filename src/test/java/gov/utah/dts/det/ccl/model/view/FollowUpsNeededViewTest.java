package gov.utah.dts.det.ccl.model.view;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class FollowUpsNeededViewTest {

	@DataProvider(name = "alertTypeData")
	public Object[][] getAlertTypeData() {
		return new Object[][] {
			{13, null, -20, AlertType.RED_ALERT}, {14, null, -20, AlertType.RED_ALERT}, {15, null, -20, AlertType.ORANGE_ALERT},
			{20, null, -20, AlertType.ORANGE_ALERT}, {21, null, -20, AlertType.ORANGE_ALERT}, {22, null, -20, AlertType.ALERT},
			{60, -22, null, AlertType.RED_ALERT}, {60, -21, null, AlertType.RED_ALERT}, {60, -20, null, AlertType.ORANGE_ALERT},
			{60, -15, null, AlertType.ORANGE_ALERT}, {60, -14, null, AlertType.ORANGE_ALERT}, {60, -13, null, AlertType.ALERT},
			{null, null, -30, AlertType.ALERT}, {null, null, -44, AlertType.ORANGE_ALERT}, {null, null, -52, AlertType.RED_ALERT}
		};
	}
	
	/*@Test(dataProvider = "alertTypeData")
	public void testGetAlertType(Integer daysFromExpiration, Integer daysFromDeadline, Integer daysFromInspectionDate, AlertType expectedAlert) throws JSONException, ParseException {
		FollowUpsNeededView v = new FollowUpsNeededView();
		v.setFacility(new BasicFacilityInformation());
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		
		if (daysFromExpiration != null) {
			v.getFacility().setLicenseExpirationDate(DateUtils.addDays(today, daysFromExpiration));
		}
		if (daysFromDeadline != null) {
			v.setCorrectionDate(DateUtils.addDays(today, daysFromDeadline));
		}
		if (daysFromInspectionDate != null) {
			v.setInspectionDate(DateUtils.addDays(today, daysFromInspectionDate));
		}
		
		assertEquals(v.getAlertType(), expectedAlert);
	}*/
}