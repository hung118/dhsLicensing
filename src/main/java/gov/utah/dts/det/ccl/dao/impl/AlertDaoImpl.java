package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.AlertDao;
import gov.utah.dts.det.ccl.model.Alert;
import gov.utah.dts.det.ccl.view.AlertView;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("alertDao")
public class AlertDaoImpl extends AbstractBaseDaoImpl<Alert, Long> implements AlertDao {

	private static final String ALERT_QUERY = "from AlertView av where av.recipientId = :recipientId order by av.alertDate desc";
	
	@PersistenceContext
	private EntityManager em;
	
	public AlertDaoImpl() {
		super(Alert.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<AlertView> getAlerts(Long userId) {
		Query query = em.createQuery(ALERT_QUERY);
		query.setParameter("recipientId", userId);
		
		return (List<AlertView>) query.getResultList();
	}
}