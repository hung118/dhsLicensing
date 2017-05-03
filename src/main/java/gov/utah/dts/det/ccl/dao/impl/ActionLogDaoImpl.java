/**
 * $Rev: 7 $:
 * $LastChangedDate: 2009-02-18 12:32:20 -0700 (Wed, 18 Feb 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.ActionLogDao;
import gov.utah.dts.det.ccl.model.ActionLog;
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
@Repository("actionLogDao")
public class ActionLogDaoImpl extends AbstractBaseDaoImpl<ActionLog, Long> implements ActionLogDao {

	private static final String ACTION_LOG_QUERY = "from ActionLog al join fetch al.modifiedBy where al.facility.id = :facilityId ";
	private static final String ACTION_LOG_ACTION_TYPE_CLAUSE = " and al.actionType.id = :actionTypeId ";
	private static final String ACTION_LOG_DATE_FIELD = "al.actionDate";
	
	@PersistenceContext
	private EntityManager em;

	public ActionLogDaoImpl() {
		super(ActionLog.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<ActionLog> getActionLogsForFacility(Long facilityId, PickListValue actionType, ListRange listRange, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ACTION_LOG_QUERY);
		if (actionType != null && actionType.getId() != null) {
			sb.append(ACTION_LOG_ACTION_TYPE_CLAUSE);
		}
		ServiceUtils.addIntervalClause(ACTION_LOG_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		if (actionType != null && actionType.getId() != null) {
			query.setParameter("actionTypeId", actionType.getId());
		}
		
		return (List<ActionLog>) query.getResultList();
	}
}