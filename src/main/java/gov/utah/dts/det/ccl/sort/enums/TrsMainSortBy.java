package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jtorres
 * 
 */
public enum TrsMainSortBy implements SortBy {
  DATE_RECEIVED("Date Received", "dateReceived", SortDirection.ASCENDING, true), CAL_CLEARED_DATE("Cal Cleared Date",
      "calClearedDate", SortDirection.ASCENDING, true), LISA_CLEARED_DATE("Lisa Cleared Date", "lisaClearedDate",
      SortDirection.ASCENDING, true), AMIS_CLEARED_DATE("Amis Cleared Date", "amisClearedDate", SortDirection.ASCENDING, true), OSCAR_CLEARED_DATE(
      "Oscar Cleared Date", "oscarClearedDate", SortDirection.ASCENDING, true)

  ;

  private final String label;
  private final String orderByString;
  private final SortDirection defaultSortDirection;
  private final boolean allTRSMainSort;

  private static List<TrsMainSortBy> allTRSMainSortBys;

  private TrsMainSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean allTRSMainSort) {
    this.label = label;
    this.orderByString = orderByString;
    this.defaultSortDirection = defaultSortDirection;
    this.allTRSMainSort = allTRSMainSort;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String getOrderByString() {
    return orderByString;
  }

  @Override
  public SortDirection getDefaultSortDirection() {
    return defaultSortDirection;
  }

  public static TrsMainSortBy getDefaultSortBy() {
    return DATE_RECEIVED;
  }

  public static List<TrsMainSortBy> getAllTRSMainSortBys() {
    if (allTRSMainSortBys == null) {
      allTRSMainSortBys = new ArrayList<TrsMainSortBy>();
      for (TrsMainSortBy sortBy : TrsMainSortBy.values()) {
        if (sortBy.allTRSMainSort) {
          allTRSMainSortBys.add(sortBy);
        }
      }
    }
    return allTRSMainSortBys;
  }

}
