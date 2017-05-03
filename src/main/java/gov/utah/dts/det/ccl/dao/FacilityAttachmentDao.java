package gov.utah.dts.det.ccl.dao;

import java.util.List;

import gov.utah.dts.det.ccl.model.FacilityAttachment;
import gov.utah.dts.det.dao.AbstractBaseDao;

/**
 * FacilityAttachment Dao interface.
 * 
 * @author Hnguyen
 *
 */

public interface FacilityAttachmentDao extends AbstractBaseDao<FacilityAttachment, Long> {

	public List<FacilityAttachment> getFacilityAttachments(Long facilityId, String attachmentType);
	
}