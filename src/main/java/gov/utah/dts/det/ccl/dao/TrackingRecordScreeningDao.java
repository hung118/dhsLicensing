package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.view.TRSSearchView;
import gov.utah.dts.det.ccl.service.TRSSearchCriteria;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningDao extends AbstractBaseDao<TrackingRecordScreening, Long> {

  /**
   * Retrieves a list of persons in the tracking record screening table or an empty list if none are found
   * 
   * @param criteria
   * @param sortBy
   * @param page
   * @param resultsPerPage
   * @return
   */
  public List<TRSSearchView> searchTrackingRecordScreenings(TRSSearchCriteria criteria, SortBy sortBy, int page, int resultsPerPage);

  /**
   * Go ahead and retrieve how many records match user criteria or zero if none match
   * 
   * @param criteria
   * @return
   */

  public int searchTRSCount(TRSSearchCriteria criteria);

  /**
   * Retrieves screenings records associated with a facility
   * 
   * @param facilityId
   * @param listRange
   * @param sortBy
   * @return
   */
  public List<TrackingRecordScreening> getScreeningsForFacility(Long facilityId, ListRange listRange, SortBy sortBy);
  
  /**
   * Retrieves screening records for facilities with cbs technician technicianId that have a livescan authorization date between startDate and endDate.
   * 
   * @param technicianId
   * @param startDate
   * @param endDate
   * @return
   */
  public List<TrackingRecordScreening> getLivescansIssued(Long technicianId, Date startDate, Date endDate);

}
