package gov.utah.dts.det.ccl.view;

import java.util.ArrayList;
import java.util.List;


public enum ListRange {
	
	SHOW_ALL("Show All", 0, null),
	SHOW_PAST_12_MONTHS("Show Past 12 Months", 12, RangeType.MONTHS),
	SHOW_PAST_24_MONTHS("Show Past 24 Months", 24, RangeType.MONTHS);

	public enum RangeType{DAYS, MONTHS};
	
	private final String label;
	private final int range;
	private final RangeType rangeType;
	
	private static final List<ListRange> twelveMonthRanges = new ArrayList<ListRange>();
	private static final List<ListRange> twentyFourMonthRanges = new ArrayList<ListRange>();
	
	static {
		twelveMonthRanges.add(SHOW_ALL);
		twelveMonthRanges.add(SHOW_PAST_12_MONTHS);
		
		twentyFourMonthRanges.add(SHOW_ALL);
		twentyFourMonthRanges.add(SHOW_PAST_24_MONTHS);
	}
	
	private ListRange(String label, int range, RangeType rangeType) {
		this.label = label;
		this.range = range;
		this.rangeType = rangeType;
	}
	
	public String getKey() {
		return name();
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getRange() {
		return range;
	}
	
	public RangeType getRangeType() {
		return rangeType;
	}
	
	public static List<ListRange> getTwelveMonthOptions() {
		return twelveMonthRanges;
	}
	
	public static List<ListRange> getTwentyFourMonthOptions() {
		return twentyFourMonthRanges;
	}
}