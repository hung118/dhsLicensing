package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.VarianceDao;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.view.VarianceAlertView;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.service.VarianceService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("varianceService")
public class VarianceServiceImpl implements VarianceService {

	@Autowired
	private VarianceDao varianceDao;
	
	@Autowired
	private NoteService noteService;
	
	@Override
	public Variance loadById(Long id) {
		return varianceDao.load(id);
	}
	
	@Override
	public Variance saveNewVariance(Variance variance) {
		variance.setCreatedById(SecurityUtil.getUser().getPerson().getId());
		variance.setModifiedById(SecurityUtil.getUser().getPerson().getId());
		
		return varianceDao.save(variance);
	}

	@Override
	public Variance saveVariance(Variance variance) {
		Calendar cal = Calendar.getInstance();
		variance.setModifiedDate(cal.getTime());
		variance.setModifiedById(SecurityUtil.getUser().getPerson().getId());

		// Clear out Licensor outcome
		variance.setLicensorOutcome(null);
		variance.setLicensorResponse(null);
		variance.setLicensorModifiedById(null);
		variance.setLicensorModifiedDate(null);
		// Clear out Licensing Supervisor outcome
		variance.setSupervisorOutcome(null);
		variance.setSupervisorResponse(null);
		variance.setSupervisorModifiedById(null);
		variance.setSupervisorModifiedDate(null);
		// Clear out Director outcome
		variance.setOutcome(null);
		variance.setDirectorResponse(null);
		variance.setStartDate(null);
		variance.setEndDate(null);
		variance.setRevocationDate(null);
		variance.setDirectorModifiedById(null);
		variance.setDirectorModifiedDate(null);

		return varianceDao.save(variance);
	}

	@Override
	public Variance saveVarianceLicensorOutcome(Variance variance) {
		Calendar cal = Calendar.getInstance();
		variance.setLicensorModifiedDate(cal.getTime());
		variance.setLicensorModifiedById(SecurityUtil.getUser().getPerson().getId());

		return varianceDao.save(variance);
	}

	@Override
	public Variance saveVarianceSupervisorOutcome(Variance variance) {
		Calendar cal = Calendar.getInstance();
		variance.setSupervisorModifiedDate(cal.getTime());
		variance.setSupervisorModifiedById(SecurityUtil.getUser().getPerson().getId());

		return varianceDao.save(variance);
	}

	@Override
	public Variance saveVarianceOutcome(Variance variance) {
		if (variance.getOutcome() == VarianceOutcome.NOT_NECESSARY) {
			variance.setStartDate(null);
			variance.setEndDate(null);
		} else 
		if (variance.getOutcome() == VarianceOutcome.DENIED) {
			variance.setStartDate(null);
			variance.setEndDate(null);
		}
		Calendar cal = Calendar.getInstance();
		variance.setDirectorModifiedDate(cal.getTime());
		variance.setDirectorModifiedById(SecurityUtil.getUser().getPerson().getId());
		
		return varianceDao.save(variance);
	}

	@Override
	public Variance saveVarianceRevoke(Variance variance) {
		Calendar cal = Calendar.getInstance();
		variance.setRevokeModifiedDate(cal.getTime());
		variance.setRevokeModifiedById(SecurityUtil.getUser().getPerson().getId());
		
		return varianceDao.save(variance);
	}

	@Override
	public void deleteVariance(Variance variance) {
		List<String> errorCodes = new ArrayList<String>();
		List<Note> notes = noteService.getNotesForObject(variance.getId(), NoteType.VARIANCE, null, null);
		if (!notes.isEmpty()) {
			errorCodes.add("error.variance.delete.notes-not-empty");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to delete variance", errorCodes);
		}
		
		varianceDao.delete(variance);
	}
	
	@Override
	public List<Variance> getVariancesForFacility(Long facilityId, ListRange listRange, SortBy sortBy) {
		return varianceDao.getVariancesForFacility(facilityId, listRange, sortBy);
	}
	
	@Override
	public List<Variance> getVariancesExpiring(Long personId, boolean showWholeRegion, SortBy sortBy) {
		return varianceDao.getVariancesExpiring(personId, showWholeRegion, sortBy);
	}

	@Override
	public List<VarianceAlertView> getLicensorVariances(SortBy sortBy) {
		return varianceDao.getLicensorVariances(sortBy);
	}

	@Override
	public List<VarianceAlertView> getManagerVariances(SortBy sortBy) {
		return varianceDao.getManagerVariances(sortBy);
	}

	@Override
	public List<VarianceAlertView> getDirectorVariances(SortBy sortBy) {
		return varianceDao.getDirectorVariances(sortBy);
	}

	@Override
	public void evict(Object entity) {
		varianceDao.evict(entity);
	}
}