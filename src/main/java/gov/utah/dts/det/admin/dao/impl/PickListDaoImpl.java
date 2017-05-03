/**
 * $Rev: 76 $:
 * $LastChangedDate: 2009-04-30 08:50:19 -0600 (Thu, 30 Apr 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.admin.dao.impl;

import gov.utah.dts.det.admin.dao.PickListDao;
import gov.utah.dts.det.admin.model.PickList;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("unchecked")
@Repository("pickListDao")
public class PickListDaoImpl extends AbstractBaseDaoImpl<PickList, Long> implements PickListDao {

	private static final String PICK_LISTS_BY_GROUP_QUERY = "from PickList pl where pl.pickListGroup.id = :pickListGroupId order by pl.sortOrder asc, pl.name ";
	private static final String PICK_LIST_BY_NAME_QUERY = "from PickList pl where pl.name = :pickListName";
	
	@PersistenceContext
	private EntityManager em;
	
	public PickListDaoImpl() {
		super(PickList.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<PickList> getPickLists(Long pickListGroupId) {
		Query query = em.createQuery(PICK_LISTS_BY_GROUP_QUERY);
		query.setParameter("pickListGroupId", pickListGroupId);
		
		return (List<PickList>) query.getResultList();
	}
	
	@Override
	public PickList loadByName(String name) {
		Query query = em.createQuery(PICK_LIST_BY_NAME_QUERY);
		query.setParameter("pickListName", name);
		try {
			return (PickList) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public String getReportCodes(Long serviceCode, String columnName) {
		String sql = "select " + columnName + " from report_code_lkup where service_code = " + serviceCode;
		Query query = em.createNativeQuery(sql);
		try {
			return (String) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void updateReportCodesDependencies(Long serviceCode, String programCode, String specificServiceCode, String ageGroup) {
		String sql = "update report_code_lkup set " + 
				"program_code = '" + programCode + "', " +
				"specific_service_code = '" + specificServiceCode + "', " +
				"age_group = '" + ageGroup + "' " +
				"where service_code = " + serviceCode;
		Query query = em.createNativeQuery(sql);
		if (query.executeUpdate() == 0) {	// need to insert
			sql = "insert into report_code_lkup (service_code, program_code, specific_service_code, age_group) values " +
					"('" + serviceCode + "', '" + programCode + "', '" + specificServiceCode + "', '" + ageGroup + "')";
			query = em.createNativeQuery(sql);
			query.executeUpdate();
		}
	}

}