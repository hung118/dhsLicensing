package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class InspectionViewPermissionEvaluator implements DomainObjectPermissionEvaluator<InspectionView> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		InspectionView inspectionView = (InspectionView) targetDomainObject;
		
		if ("delete".equals(permission) && ((inspectionView.getState() != Inspection.State.FINALIZED && SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) ||
				(inspectionView.getState() == Inspection.State.ENTRY && SecurityUtil.isEntityOwner(inspectionView)))) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		return false;
	}
}