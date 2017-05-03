package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.view.IncidentView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface IncidentDao extends AbstractBaseDao<Incident, Long> {

	public List<IncidentView> getIncidentsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
}