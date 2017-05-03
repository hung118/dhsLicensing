package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.NoncompliancePointsDao;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;
import gov.utah.dts.det.ccl.model.NoncompliancePointsPk;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("noncompliancePointsDao")
public class NoncompliancePointsDaoImpl extends AbstractBaseDaoImpl<NoncompliancePoints, NoncompliancePointsPk> implements NoncompliancePointsDao {

	private static final String POINTS_QUERY = "from NoncompliancePoints ncp";
	private static final String POINTS_VALUE_QUERY = "select ncp.points from NoncompliancePoints ncp where ncp.primaryKey.noncomplianceLevelId = :ncLevelId and ncp.primaryKey.findingsCategoryId = :findingsCategoryId ";
	
	@PersistenceContext
	private EntityManager em;
	
	public NoncompliancePointsDaoImpl() {
		super(NoncompliancePoints.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<NoncompliancePoints> getAllPoints() {
		Query query = em.createQuery(POINTS_QUERY);
		
		return (List<NoncompliancePoints>) query.getResultList();
	}
	
	@Override
	public Integer getPointsForValues(FindingCategoryPickListValue ncLevel, PickListValue findingsCategory) {
		if (ncLevel == null || findingsCategory == null) {
			return 0;
		}
		Query query = em.createQuery(POINTS_VALUE_QUERY);
		query.setParameter("ncLevelId", ncLevel.getId());
		query.setParameter("findingsCategoryId", findingsCategory.getId());
		
		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		} //swallow exceptions and return null
		return 0;
	}
}