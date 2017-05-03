package gov.utah.dts.det.query;

import java.util.List;

public class ListControls {

	public static final int[] RESULTS_PER_PAGE_OPTIONS = {25, 50, 100, 250};

	protected List<SortBy> sortBys;
	protected int resultsPerPage = RESULTS_PER_PAGE_OPTIONS[RESULTS_PER_PAGE_OPTIONS.length - 1];
	
	protected int numOfResults = 0;
	protected int page = 1;
	protected String sortBy;
	protected boolean showControls = true;
	
	protected List<? extends Object> results = null;
	
	public ListControls() {
		
	}

	public int getPages() {
		return numOfResults == 0 ? 1 : (int) Math.ceil((double) numOfResults / resultsPerPage);
	}

	public List<SortBy> getSortBys() {
		return sortBys;
	}

	public void setSortBys(List<SortBy> sortBys) {
		this.sortBys = sortBys;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		//only set the results per page if it is one of the options
		for (int options : RESULTS_PER_PAGE_OPTIONS) {
			if (resultsPerPage == options) {
				this.resultsPerPage = resultsPerPage;
				break;
			}
		}
	}

	public int getNumOfResults() {
		if (results != null && results.size() > numOfResults) {
			return results.size();
		}
		return numOfResults;
	}

	public void setNumOfResults(int numOfResults) {
		this.numOfResults = numOfResults;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page == 0 ? 1 : page; //never set the page to 0
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public boolean isShowControls() {
		return showControls;
	}
	
	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}
	
	public List<? extends Object> getResults() {
		return results;
	}
	
	public void setResults(List<? extends Object> results) {
		this.results = results;
	}
}