package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningLetterDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningLetterService;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningLetterService")
public class TrackingRecordScreeningLetterServiceImpl implements TrackingRecordScreeningLetterService {

	@Autowired
	TrackingRecordScreeningLetterDao dao;

	@Override
	public List<TrackingRecordScreeningLetter> getLettersForScreening(Long screeningId, SortBy sortBy) {
		return dao.getLettersForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningLetter save(TrackingRecordScreeningLetter entity) {
		return dao.save(entity);
	}

	@Override
	public TrackingRecordScreeningLetter load(Long id) {
		return dao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public void delete(TrackingRecordScreeningLetter entity) {
		dao.delete(entity);
	}

	@Override
	public void evict(TrackingRecordScreeningLetter entity) {
		dao.evict(entity);
	}

}
