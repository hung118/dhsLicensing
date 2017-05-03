/**
 *
 */
package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.UserDao;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.UserByRegionView;
import gov.utah.dts.det.ccl.service.UserSearchCriteria;
import gov.utah.dts.det.ccl.view.admin.PersonRelationshipView;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
@Repository("userDao")
public class UserDaoImpl extends AbstractBaseDaoImpl<User, Long> implements UserDao {

	private static final String USER_BY_PERSON_ID_QUERY = "from User u left join fetch u.roles where u.person.id = :personId";
	private static final String USERS_IN_ROLES_QUERY = "select u from User u join fetch u.person left join u.roles role left join fetch u.roles where role in (:roleTypes) ";
	private static final String ACTIVE_USERS_ONLY_CLAUSE = " and u.active = 'Y' ";
	private static final String PEOPLE_SELECT = "select u.person ";
	private static final String PEOPLE_SELECT_NAME_ONLY = "select new gov.utah.dts.det.ccl.model.Person(u.person.id, u.person.name.firstName, u.person.name.lastName) ";
	private static final String PEOPLE_ADDRESS_JOIN = " left join fetch u.person.address ";
	private static final String LICENSOR_SPECIALIST_CHECK = "u.person.id in (Select ls.id from Region r join r.licensingSpecialists ls)";
	private static final String PEOPLE_ROLE_TYPE_JOIN = " left join u.roles role ";
	private static final String PEOPLE_ROLE_TYPE_CHECK = " role.id = '";
	private static final String PEOPLE_ACTIVE_CHECK = " u.active = 'Y' ";
	private static final String PEOPLE_ORDER_BY_CLAUSE = " order by u.active desc, u.person.name.firstName asc, u.person.name.lastName asc ";
	private static final String ALL_USERS_WITH_ROLES_QUERY = "select u from User u left join fetch u.roles left join fetch u.person p left join fetch p.address order by u.active desc, p.name.firstName, p.name.lastName ";
	private static final String ACTIVE_USER_QUERY = "select distinct u from User u left join fetch u.roles left join fetch u.person where upper(u.username) = :email and u.active = true";
	private static final String USER_PERSON_QUERY = "select distinct u from User u left join fetch u.roles left join fetch u.person p where (upper(p.name.firstName) like :name) or (upper(p.name.lastName) like :name)";
	private static final String PEOPLE_FOR_PICK_LIST_VALUE_QUERY = "select new gov.utah.dts.det.ccl.admin.view.PersonRelationshipView(person, rel.primaryKey.type) "
			+ "from PickListValuePersonRelationship rel "
			+ "	join rel.primaryKey.person person "
			+ "where rel.primaryKey.pickListValue.id = :pickListValueId "
			+ "order by person.name.firstName asc, person.name.lastName asc";
	private static final String USERS_IN_REGION_QUERY = "from UserByRegionView ubrv left join fetch ubrv.user user "
			+ " left join fetch user.person person";
	private static final String USERS_IN_REGION_REGION_CLAUSE = " where ubrv.region.id = :regionId ";
	private static final String USERS_IN_REGION_EXCLUDE_MANAGER_CLAUSE = " and ubrv.relationship != 'REGION_MANAGER' ";
	private static final String USERS_IN_REGION_ORDER_BY = " order by ubrv.region.value asc, person.name.firstName asc, "
			+ " person.name.lastName asc ";
	private static final String REGIONS_MANAGING_QUERY = "region.* from PickListValue region, UserByRegionView ubrv "
			+ " where ubrv.managerId = :managerId and region.id = ubrv.region.id order by region.sortOrder asc, region.value asc";

	public UserDaoImpl() {
		super(User.class);
	}
	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public User loadByPersonId(Long personId) {
		Query query = em.createQuery(USER_BY_PERSON_ID_QUERY);
		query.setParameter("personId", personId);

		return (User) query.getSingleResult();
	}

	@Override
	public User getActiveUserByEmail(String email) {
		Query query = em.createQuery(ACTIVE_USER_QUERY);
		query.setParameter("email", email.toUpperCase());

		User user = null;
		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException nre) {
		}
		return user;
	}

	@Override
	public List<User> getUsersByPersonName(UserSearchCriteria criteria) {
		Query query = em.createQuery(USER_PERSON_QUERY);
		query.setParameter("name", criteria.getName().toUpperCase());

		return query.getResultList();
	}

	@Override
	public List<PersonRelationshipView> getAllPeopleForPickListValue(Long pickListValueId) {
		StringBuilder sb = new StringBuilder(PEOPLE_FOR_PICK_LIST_VALUE_QUERY);

		Query query = em.createQuery(sb.toString());
		query.setParameter("pickListValueId", pickListValueId);

		return (List<PersonRelationshipView>) query.getResultList();
	}

	@Override
	public Set<User> getAllUsers() {
		StringBuilder sb = new StringBuilder(ALL_USERS_WITH_ROLES_QUERY);

		Query query = em.createQuery(sb.toString());
		@SuppressWarnings("rawtypes")
		Set set = new LinkedHashSet();
		set.addAll(query.getResultList());
		return set;
	}
	
	@Override
	public Set<User> getUsersInRole(boolean includeOnlyActive, RoleType... roleTypes) {
		StringBuilder sb = new StringBuilder(USERS_IN_ROLES_QUERY);
		if (includeOnlyActive) {
			sb.append(ACTIVE_USERS_ONLY_CLAUSE);
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter("roleTypes", Arrays.asList(roleTypes));
		return new HashSet<User>((List<User>) query.getResultList());
	}

	@Override
	public Set<Person> getPeople(List<RoleType> roleTypes, boolean includeOnlyActive, boolean nameOnly, boolean includeAdditionalJoin) {
		Boolean licensorSpecialist = false;
		StringBuilder sb = new StringBuilder();
		if (nameOnly) {
			sb.append(PEOPLE_SELECT_NAME_ONLY);
		} else {
			sb.append(PEOPLE_SELECT);
		}
		sb.append("from User u ");
		if (!nameOnly) {
			sb.append(PEOPLE_ADDRESS_JOIN);
		}

		boolean addWhere = roleTypes != null && !roleTypes.isEmpty() || includeOnlyActive;
		if (roleTypes != null && !roleTypes.isEmpty()) {
			sb.append(PEOPLE_ROLE_TYPE_JOIN);
		}
		if (addWhere) {
			sb.append(" where ");
		}
		if (roleTypes != null && !roleTypes.isEmpty()) {
			if (roleTypes.size() > 1) {
				sb.append(" (");
			}
			for (Iterator<RoleType> itr = roleTypes.iterator(); itr.hasNext();) {
				RoleType type = itr.next();
				if(RoleType.ROLE_LICENSOR_SPECIALIST.equals(type)) {
					licensorSpecialist = true;
				}
				sb.append(PEOPLE_ROLE_TYPE_CHECK);
				sb.append(type);
				sb.append("'");
				if (itr.hasNext()) {
					sb.append(" or ");
				}
			}
			if (roleTypes.size() > 1) {
				sb.append(") ");
			}
		}
		if (includeOnlyActive) {
			if (roleTypes != null && !roleTypes.isEmpty()) {
				sb.append(" and ");
			}
			sb.append(PEOPLE_ACTIVE_CHECK);
		}
		if(includeAdditionalJoin && licensorSpecialist) {
			/**This ensures they are also in the REGION_LICENSING_SPECIALIST table
			This saves us from errors later that expect the association **/
			sb.append(" and ").append(LICENSOR_SPECIALIST_CHECK);
		}
		
		sb.append(PEOPLE_ORDER_BY_CLAUSE);

		Query query = em.createQuery(sb.toString());

		@SuppressWarnings("rawtypes")
		Set set = new LinkedHashSet();
		set.addAll(query.getResultList());
		return set;
	}

	@Override
	public List<UserByRegionView> getUsersInRegion(Long regionId, boolean excludeManager) {
		StringBuilder sb = new StringBuilder(USERS_IN_REGION_QUERY);
		if (regionId != null) {
			sb.append(USERS_IN_REGION_REGION_CLAUSE);
		}
		if (excludeManager) {
			sb.append(USERS_IN_REGION_EXCLUDE_MANAGER_CLAUSE);
		}
		sb.append(USERS_IN_REGION_ORDER_BY);

		Query query = em.createQuery(sb.toString());
		if (regionId != null) {
			query.setParameter("regionId", regionId);
		}
		return (List<UserByRegionView>) query.getResultList();
	}

	@Override
	public List<PickListValue> getRegionsManaging(Long personId) {
		Query query = em.createQuery(REGIONS_MANAGING_QUERY);
		query.setParameter("managerId", personId);

		return (List<PickListValue>) query.getResultList();
	}
}