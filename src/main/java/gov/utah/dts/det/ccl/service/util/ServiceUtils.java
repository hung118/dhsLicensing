package gov.utah.dts.det.ccl.service.util;

import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

import javax.persistence.Query;

public class ServiceUtils {

	private ServiceUtils() {
		
	}
	
	public static void addIntervalClause(String field, StringBuilder sb, ListRange listRange) {
		if (listRange != null && sb != null && field != null && listRange.getRangeType() != null) {
			sb.append(" and ");
			sb.append(field);
			switch (listRange.getRangeType()) {
				case DAYS:
					sb.append(" >= trunc(current_date() - ");
					sb.append(listRange.getRange());
					sb.append(", 'DD') ");
					break;
				case MONTHS:
					sb.append(" >= trunc(add_months(current_date(), -");
					sb.append(listRange.getRange());
					sb.append("), 'DD') ");
					break;
			}
		}
	}
	
	public static void addSortByClause(StringBuilder sb, SortBy sortBy, SortDirection sortDirection) {
		if (sortBy != null) {
			sb.append(" order by ");
			sb.append(sortBy.getOrderByString());
			sb.append(" ");
			if (sortDirection != null) {
				sb.append(sortDirection.getSortDirectionString());
			} else {
			    if (sortBy.getDefaultSortDirection() != null) {
			        sb.append(sortBy.getDefaultSortDirection().getSortDirectionString());
			    }
			}
			sb.append(" ");
		}
	}
	
	public static void addFirstAndMaxResults(Query query, int resultsPerPage, int page) {
		int maxResults = resultsPerPage == 0 ? 50 : resultsPerPage;
		int firstResult = page < 0 ? 0 : (page * resultsPerPage);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
	}
}