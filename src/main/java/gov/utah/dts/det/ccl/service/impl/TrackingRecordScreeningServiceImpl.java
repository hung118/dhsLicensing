package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.view.TRSSearchView;
import gov.utah.dts.det.ccl.service.TRSSearchCriteria;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningService")
public class TrackingRecordScreeningServiceImpl implements TrackingRecordScreeningService {
  @Autowired
  private TrackingRecordScreeningDao dao;

  @Override
  public List<TRSSearchView> searchTrackingRecordScreenings(TRSSearchCriteria criteria, SortBy sortBy, int page, int resultsPerPage) {
    return dao.searchTrackingRecordScreenings(criteria, sortBy, page, resultsPerPage);
  }

  @Override
  public int searchTRSCount(TRSSearchCriteria criteria) {
    return dao.searchTRSCount(criteria);
  }

  @Override
  public TrackingRecordScreening save(TrackingRecordScreening entity) {
    return dao.save(entity);
  }

  @Override
  public TrackingRecordScreening load(Long id) {
    return dao.load(id);
  }

  public List<TrackingRecordScreening> getScreeningsForFacility(Long facilityId, ListRange listRange, SortBy sortBy) {
    return dao.getScreeningsForFacility(facilityId, listRange, sortBy);
  }
  
  @Override
  public List<TrackingRecordScreening> getLivescansIssued(Long technicianId, Date startDate, Date endDate) {
	  return dao.getLivescansIssued(technicianId, startDate, endDate);
  }

}
