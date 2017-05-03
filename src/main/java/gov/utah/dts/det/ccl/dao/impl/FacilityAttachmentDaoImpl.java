package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.FacilityAttachmentDao;
import gov.utah.dts.det.ccl.model.FacilityAttachment;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Implementation class for FacilityAttachmentDao interface.
 * 
 * @author Hnguyen
 *
 */
@SuppressWarnings("unchecked")
@Repository("facilityAttachmentDao")
public class FacilityAttachmentDaoImpl extends AbstractBaseDaoImpl<FacilityAttachment, Long> implements FacilityAttachmentDao {

	private static final String ATTACHMENT_QUERY = "select fa from FacilityAttachment fa where fa.facility.id = :facilityId and fa.attachmentType = :attachmentType";
			
	@PersistenceContext
	private EntityManager em;
	
	public FacilityAttachmentDaoImpl() {
		super(FacilityAttachment.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<FacilityAttachment> getFacilityAttachments(Long facilityId, String attachmentType) {
		
		Query query = em.createQuery(ATTACHMENT_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("attachmentType", attachmentType);
		
		return (List<FacilityAttachment>) query.getResultList();
	}
}