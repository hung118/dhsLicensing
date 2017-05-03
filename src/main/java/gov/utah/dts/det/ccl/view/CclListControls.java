package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.query.ListControls;

import java.util.ArrayList;
import java.util.List;

public class CclListControls extends ListControls {

	protected List<ListRange> ranges = new ArrayList<ListRange>();
	
	protected ListRange range;
	
	public List<ListRange> getRanges() {
		return ranges;
	}
	
	public void setRanges(List<ListRange> ranges) {
		this.ranges = ranges;
	}
	
	public ListRange getRange() {
		return range;
	}
	
	public void setRange(ListRange range) {
		this.range = range;
	}
}