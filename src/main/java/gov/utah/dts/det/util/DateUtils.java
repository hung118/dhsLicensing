package gov.utah.dts.det.util;

import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.ccl.model.DateRange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	
	private static final Date MIN_DATE;
	private static final Date MAX_DATE;
	
	private static final int[] HOURS = {8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7};
	private static final int[] MINUTES = {0, 15, 30, 45};
	private static final List<KeyValuePair> TIMES;

	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
	
	static {
		Calendar minCal = Calendar.getInstance();
		minCal.set(Calendar.YEAR, 1800);
		minCal = org.apache.commons.lang.time.DateUtils.truncate(minCal, Calendar.YEAR);
		MIN_DATE = minCal.getTime();
		
		Calendar maxCal = Calendar.getInstance();
		maxCal.set(Calendar.YEAR, 2200);
		maxCal = org.apache.commons.lang.time.DateUtils.truncate(maxCal, Calendar.YEAR);
		MAX_DATE = maxCal.getTime();
		
		TIMES = new ArrayList<KeyValuePair>();
		String a = "AM";
		for (int i = 0; i < 2; i++) {
			for (int h : HOURS) {
				for (int m : MINUTES) {
					String key = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + " " + a;
					String value = h + ":" + (m < 10 ? "0" + m : m) + " " + a;
					TIMES.add(new KeyValuePair(key, value));
				}
				if (h == 11) {
					a = (a == "AM" ? "PM" : "AM");
				}
			}
		}
	}
	
	/**
	 * Merges the date and time together taking the date from <code>date</code> and the time from <code>time</code>.
	 * 
	 * @param date the date containing the date portion to merge
	 * @param time the date containing the time portion to merge
	 * @return a <code>Date</code> object containing the merged date and time
	 */
	public static Date mergeDateTime(Date date, Date time) {
		if (date == null) {
			return null;
		} else if (time == null) {
			return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DATE);
		}
		
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		
		Calendar timeCal = Calendar.getInstance();
		timeCal.setTime(time);
		
		dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dateCal = org.apache.commons.lang.time.DateUtils.truncate(dateCal, Calendar.MINUTE);
		return dateCal.getTime();
	}
	
	/**
	 * Checks to see if a date is within the range of two other dates.
	 * 
	 * @param dateToTest The date to perform the range test on
	 * @param startDate The start date of the range to test
	 * @param endDate The end date of the range to test
	 * @return If the endDate is null it will return true if dateToTest is equal to or after startDate.  If dateToTest is equal to or after
	 * 		startDate and dateToTest is equal to or before endDate it will return true; otherwise it will return false.
	 * @throws IllegalArgumentException If dateToTest or startDate are null
	 */
	public static boolean isInDateRange(Date dateToTest, Date startDate, Date endDate) throws IllegalArgumentException {
		if (dateToTest == null) {
			throw new IllegalArgumentException("The date to test must not be null");
		}
		if (endDate == null) {
			return startDate == null || dateToTest.compareTo(startDate) >= 0;
		} else {
			return ((startDate == null || dateToTest.compareTo(startDate) >= 0) && dateToTest.compareTo(endDate) <= 0);
		}
	}
	
	public static boolean isOverlappingDateRanges(Date startDate1, Date endDate1, Date startDate2, Date endDate2) throws IllegalArgumentException {
		Date earlierStart;
		Date earlierEnd;
		Date laterStart;
		Date laterEnd;
		if (startDate1 == null || (startDate2 != null && startDate1.compareTo(startDate2) < 0)) {
			earlierStart = startDate1 == null ? MIN_DATE : startDate1;
			earlierEnd = endDate1 == null ? MAX_DATE : endDate1;
			laterStart = startDate2 == null ? MIN_DATE : startDate2;
			laterEnd = endDate2 == null ? MAX_DATE : endDate2;
		} else {
			earlierStart = startDate2 == null ? MIN_DATE : startDate2;
			earlierEnd = endDate2 == null ? MAX_DATE : endDate2;
			laterStart = startDate1 == null ? MIN_DATE : startDate1;
			laterEnd = endDate1 == null ? MAX_DATE : endDate1;
		}
		
		if (earlierStart.compareTo(laterStart) == 0 || earlierEnd.compareTo(laterEnd) == 0 || earlierStart.compareTo(earlierEnd) > 0
			|| laterStart.compareTo(laterEnd) > 0 || earlierEnd.compareTo(laterStart) >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isActive(DateRange dateRange) {
		Date today = org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.DATE);
		return isInDateRange(today, dateRange.getStartDate(), dateRange.getEndDate());
	}
	
	public static List<KeyValuePair> getTimesFifteenMinuteIncrements() {
		return TIMES;
	}
	
	public static String formatDateDefault(Date date) {
		return DEFAULT_DATE_FORMATTER.format(date);
	}
	
	public static String now() {
		return formatDateDefault(new java.util.Date());
	}
}