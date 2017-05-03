package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.RegionDao;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("regionDao")
public class RegionDaoImpl extends AbstractBaseDaoImpl<Region, Long> implements RegionDao {
	
	private static final String REGION_QUERY = "select r from Region r" +
			" left join fetch r.officeSpecialist ospec" +
			" left join fetch r.licensingSpecialists lspecs" +
			" where r.id = :regionId ";
	private static final String REGIONS_QUERY = "from Region r order by r.name ";
	private static final String REGIONS_WITH_PEOPLE_QUERY = "select r from Region r" +
			" left join fetch r.officeSpecialist ospec" +
			" left join fetch r.licensingSpecialists lspecs" +
			" order by r.name, lspecs.name.firstName, lspecs.name.lastName";
	
	@PersistenceContext
	private EntityManager em;
	
	public RegionDaoImpl() {
		super(Region.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public Region load(Long id) {
		Query query = em.createQuery(REGION_QUERY);
		query.setParameter("regionId", id);
		
		return (Region) query.getSingleResult();
	}
	
	@Override
	public Set<Region> getRegions(boolean fetchPeople) {
		String queryStr = null;
		if (fetchPeople) {
			queryStr = 	REGIONS_WITH_PEOPLE_QUERY;
		} else {
			queryStr = REGIONS_QUERY;
		}
		Query query = em.createQuery(queryStr);
		return new LinkedHashSet<Region>(query.getResultList());
	}
	
	@Override
	public void updateRegions() {
		Query query = em.createNativeQuery("call ccl.update_lic_fac_regions()");
		query.executeUpdate();
	}
}