/**
 * $Rev: 53 $:
 * $LastChangedDate: 2009-03-31 11:05:36 -0600 (Tue, 31 Mar 2009) $:
 * $Author: mhepworth $:
 */
package gov.utah.dts.det.admin.dao.impl;

import gov.utah.dts.det.admin.dao.PickListGroupDao;
import gov.utah.dts.det.admin.model.PickListGroup;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("unchecked")
@Repository("pickListGroupDao")
public class PickListGroupDaoImpl extends AbstractBaseDaoImpl<PickListGroup, Long> implements PickListGroupDao {
	
	private static final String PICK_LIST_GROUPS_QUERY = "from PickListGroup plg order by plg.sortOrder asc, plg.name ";
	
	public PickListGroupDaoImpl() {
		super(PickListGroup.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<PickListGroup> getPickListGroups() {
		Query query = em.createQuery(PICK_LIST_GROUPS_QUERY);
		return (List<PickListGroup>) query.getResultList();
	}
}