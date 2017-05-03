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
public enum TrsDpsFbiSortBy implements SortBy {
  FIRST_FBI_REQUEST("1st FBI request", "firstFbiRequestDate", SortDirection.ASCENDING, true), SECOND_FBI_REQUEST(
      "2nd FBI request", "secondFbiRequestDate", SortDirection.ASCENDING, true), TO_DPS_FOR_VERIFICATION(
      "To DPS for verifaction", "toDpsForVerificationDate", SortDirection.ASCENDING, true), DPS_VERIFIED("DPS verified",
      "dpsVerifiedDatde", SortDirection.ASCENDING, true), LIVE_SCAN("Live scan date", "livescanDate", SortDirection.ASCENDING,
      true)

  ;

  private final String label;
  private final String orderByString;
  private final SortDirection defaultSortDirection;
  private final boolean allTRSDpsFbiSort;

  private static List<TrsDpsFbiSortBy> allTRSDpsFbiSortBys;

  private TrsDpsFbiSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean allTRSDpsFbiSort) {
    this.label = label;
    this.orderByString = orderByString;
    this.defaultSortDirection = defaultSortDirection;
    this.allTRSDpsFbiSort = allTRSDpsFbiSort;
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

  public static TrsDpsFbiSortBy getDefaultSortBy() {
    return FIRST_FBI_REQUEST;
  }

  public static List<TrsDpsFbiSortBy> getAllTRSDpsFbiSortBys() {
    if (allTRSDpsFbiSortBys == null) {
      allTRSDpsFbiSortBys = new ArrayList<TrsDpsFbiSortBy>();
      for (TrsDpsFbiSortBy sortBy : TrsDpsFbiSortBy.values()) {
        if (sortBy.allTRSDpsFbiSort) {
          allTRSDpsFbiSortBys.add(sortBy);
        }
      }
    }
    return allTRSDpsFbiSortBys;
  }

}
