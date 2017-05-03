package gov.utah.dts.det.query;


public interface SortBy {
	
	public String getKey();
	
	public String getLabel();
	
	public String getOrderByString();
	
	public SortDirection getDefaultSortDirection();
}