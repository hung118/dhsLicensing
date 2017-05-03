package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningMainDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository("trackingRecordScreeningMainDao")
public class TrackingRecordScreeningMainDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningMain, Long> implements
    TrackingRecordScreeningMainDao {

  public TrackingRecordScreeningMainDaoImpl() {
    super(TrackingRecordScreeningMain.class);
  }

  @PersistenceContext
  private EntityManager em;

  @Override
  public EntityManager getEntityManager() {
    return em;
  }

}
