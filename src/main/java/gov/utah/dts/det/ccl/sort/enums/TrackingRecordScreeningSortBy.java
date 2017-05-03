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
public enum TrackingRecordScreeningSortBy implements SortBy {
  LAST_NAME("Last Name", "upper(trs.lastName), upper(trs.firstName)", SortDirection.ASCENDING, true), FIRST_NAME("First Name",
      "upper(trs.firstName), upper(trs.lastName)", SortDirection.ASCENDING, true), FACILITY_NAME("Facility Name",
      "upper(trs.facility.name)", SortDirection.ASCENDING, true), BIRTHDATE("Birthdate", "trs.birthdate",
      SortDirection.ASCENDING, true), PERSON_IDENTIFIER("Person Identifier", "upper(trs.personIdentifier)",
      SortDirection.ASCENDING, true);

  private final String label;
  private final String orderByString;
  private final SortDirection defaultSortDirection;
  private final boolean allTRSSort;

  private static List<TrackingRecordScreeningSortBy> allTRSSortBys;

  private TrackingRecordScreeningSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean allTRSSort) {
    this.label = label;
    this.orderByString = orderByString;
    this.defaultSortDirection = defaultSortDirection;
    this.allTRSSort = allTRSSort;
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

  public static TrackingRecordScreeningSortBy getDefaultSortBy() {
    return LAST_NAME;
  }

  public static List<TrackingRecordScreeningSortBy> getAllTRSSortBys() {
    if (allTRSSortBys == null) {
      allTRSSortBys = new ArrayList<TrackingRecordScreeningSortBy>();
      for (TrackingRecordScreeningSortBy sortBy : TrackingRecordScreeningSortBy.values()) {
        if (sortBy.allTRSSort) {
          allTRSSortBys.add(sortBy);
        }
      }
    }
    return allTRSSortBys;
  }
}
