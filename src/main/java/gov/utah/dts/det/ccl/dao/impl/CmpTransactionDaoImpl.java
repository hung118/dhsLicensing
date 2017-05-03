package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.CmpTransactionDao;
import gov.utah.dts.det.ccl.model.CmpTransaction;
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
@Repository("cmpTransactionDao")
public class CmpTransactionDaoImpl extends AbstractBaseDaoImpl<CmpTransaction, Long> implements CmpTransactionDao {
	
	private static final String CMPS_QUERY = "select cmp from CmpTransaction cmp where cmp.facility.id = :facilityId";
	private static final String CMPS_QUERY_ALL = "select cmp from CmpTransaction cmp where cmp.approval is null or cmp.approval = 0";
	private static final String CMPS_WAITING_CLAUSE = " and (cmp.approval is null or cmp.approval = 0)";
	private static final String CMPS_HISTORY_CLAUSE = " and cmp.approval = 1";
	private static final String CMPS_DATE_FIELD = "cmp.date";
	
	@PersistenceContext
	private EntityManager em;
	
	public CmpTransactionDaoImpl() {
		super(CmpTransaction.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
		
	public List<CmpTransaction> getCmpsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean approval) {
		
		if (facilityId == null) {	// for alert section in home page
			return getCmpsForAllFacilities(sortBy);
		}
		
		StringBuilder sb = new StringBuilder(CMPS_QUERY);
		if (approval) {
			sb.append(CMPS_HISTORY_CLAUSE);
		} else {
			sb.append(CMPS_WAITING_CLAUSE);
		}
		ServiceUtils.addIntervalClause(CMPS_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		
		return (List<CmpTransaction>) query.getResultList();
	}
	
	private List<CmpTransaction> getCmpsForAllFacilities(SortBy sortBy) {
		StringBuilder sb =  new StringBuilder(CMPS_QUERY_ALL);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		
		return (List<CmpTransaction>) query.getResultList();
	}

}