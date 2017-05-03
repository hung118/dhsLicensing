package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.IncidentDao;
import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.view.IncidentView;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("incidentDao")
public class IncidentDaoImpl extends AbstractBaseDaoImpl<Incident, Long> implements IncidentDao {
	
	private static final String INCIDENT_QUERY = "from IncidentView iv where iv.facilityId = :facilityId ";
	private static final String INCIDENT_FINALIZED_CLAUSE = " and iv.state = 'FINALIZED' ";
	private static final String INCIDENT_UNFINALIZED_CLAUSE = " and iv.state != 'FINALIZED' ";
	private static final String INCIDENT_DATE_FIELD = "iv.date";

	@PersistenceContext
	private EntityManager em;

	public IncidentDaoImpl() {
		super(Incident.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<IncidentView> getIncidentsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized) {
		StringBuilder sb = new StringBuilder(INCIDENT_QUERY);
		if (finalized) {
			sb.append(INCIDENT_FINALIZED_CLAUSE);
		} else {
			sb.append(INCIDENT_UNFINALIZED_CLAUSE);
		}
		ServiceUtils.addIntervalClause(INCIDENT_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		
		return (List<IncidentView>) query.getResultList();
	}
}