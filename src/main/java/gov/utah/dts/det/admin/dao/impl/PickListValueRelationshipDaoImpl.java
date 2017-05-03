package gov.utah.dts.det.admin.dao.impl;

import gov.utah.dts.det.admin.dao.PickListValueRelationshipDao;
import gov.utah.dts.det.admin.model.PickListValueRelationship;
import gov.utah.dts.det.admin.model.PickListValueRelationshipPk;
import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("pickListValueRelationshipDao")
public class PickListValueRelationshipDaoImpl extends AbstractBaseDaoImpl<PickListValueRelationship, PickListValueRelationshipPk> implements PickListValueRelationshipDao {

	private static final String ALL_CHILD_PICK_LIST_VALUES_QUERY = "from PickListValueRelationship rel join fetch rel.primaryKey.child child join fetch rel.primaryKey.parent where rel.primaryKey.type = :relationshipType";
	private static final String CHILD_PICK_LIST_VALUES_QUERY = "select new gov.utah.dts.det.admin.view.KeyValuePair(child.id, child.value, child.active) from PickListValueRelationship rel join rel.primaryKey.child child where rel.primaryKey.parent.id = :parentId and rel.primaryKey.type = :relationshipType ";
	private static final String CHILD_PICK_LIST_VALUES_ACTIVE_CLAUSE = "and child.active = 'Y' ";
	private static final String CHILD_PICK_LIST_VALUES_ORDER_BY_CLAUSE = "order by child.active desc, child.value asc";
	
	public PickListValueRelationshipDaoImpl() {
		super(PickListValueRelationship.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<PickListValueRelationship> getAllChildPickListValuesInRelationship(String relationshipType) {
		Query query = em.createQuery(ALL_CHILD_PICK_LIST_VALUES_QUERY);
		query.setParameter("relationshipType", relationshipType);
		
		return (List<PickListValueRelationship>) query.getResultList();
	}
	
	@Override
	public List<KeyValuePair> getChildValuesForPickListValueAsKeyValuePairs(Long pickListValueId, String relationshipType,
			boolean includeOnlyActive) {
		StringBuilder sb = new StringBuilder(CHILD_PICK_LIST_VALUES_QUERY);
		if (includeOnlyActive) {
			sb.append(CHILD_PICK_LIST_VALUES_ACTIVE_CLAUSE);
		}
		sb.append(CHILD_PICK_LIST_VALUES_ORDER_BY_CLAUSE);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("parentId", pickListValueId);
		query.setParameter("relationshipType", relationshipType);
		
		return (List<KeyValuePair>) query.getResultList();
	}
}