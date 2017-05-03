package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.LocationDao;
import gov.utah.dts.det.ccl.model.Location;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Repository("locationDao")
public class LocationDaoImpl extends AbstractBaseDaoImpl<Location, Long> implements LocationDao {
	
	private static final String LOCATION_QUERY = "from Location loc join fetch loc.region region join fetch loc.rrAgency rrAgency ";
	private static final String LOCATION_COUNT_QUERY = "select count(*) from Location ";
	private static final String ZIP_CODE_QUERY = "select distinct loc.zipCode from Location loc order by loc.zipCode asc";
	private static final String LOCATIONS_FOR_ZIP_CODE_QUERY = "from Location loc where loc.zipCode = :zipCode order by loc.city asc";
	private static final String LOCATIONS_SEARCH_QUERY = "from Location loc where loc.zipCode like :query order by loc.zipCode asc";
	
	@PersistenceContext
	private EntityManager em;
	
	public LocationDaoImpl() {
		super(Location.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<Location> getAllLocations() {
		Query query = em.createQuery("from Location");
		return (List<Location>) query.getResultList();
	}
	
	@Override
	public List<Location> getLocations(SortBy sortBy, int page, int resultsPerPage) {
		StringBuilder sb = new StringBuilder(LOCATION_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		
		int maxResults = resultsPerPage == 0 ? 250 : resultsPerPage;
		int firstResult = page * resultsPerPage;
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		return (List<Location>) query.getResultList();
	}
	
	@Override
	public int getLocationsCount() {
		Query query = em.createQuery(LOCATION_COUNT_QUERY);
		Long results = (Long) query.getSingleResult();
		return results.intValue();
	}
	
	@Override
	public List<String> getAllZipCodes() {
		Query query = em.createQuery(ZIP_CODE_QUERY);
		return (List<String>) query.getResultList();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public List<Location> getLocationsForZipCode(String zipCode) {
		Query query = em.createQuery(LOCATIONS_FOR_ZIP_CODE_QUERY);
		query.setParameter("zipCode", zipCode);
		return (List<Location>) query.getResultList();
	}
	
	@Override
	public List<Location> searchLocationsByZipCode(String query) {
		Query dbQuery = em.createQuery(LOCATIONS_SEARCH_QUERY);
		dbQuery.setParameter("query", query + "%");
		dbQuery.setMaxResults(20);
		
		return (List<Location>) dbQuery.getResultList();
	}
}