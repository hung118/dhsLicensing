package gov.utah.dts.det.ccl.security.permissions;

import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class NotePermissionEvaluator extends AbstractDomainObjectPermissionEvaluator<Note> implements DomainObjectPermissionEvaluator<Note> {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, String permission) {
		Note note = (Note) targetDomainObject;
		Date cutoffDate = new Date();
		cutoffDate = DateUtils.addDays(cutoffDate, -4);
		if (("save".equals(permission) || "delete".equals(permission)) && ((SecurityUtil.isEntityOwner(authentication, note) && note.getCreationDate().after(cutoffDate))
				|| SecurityUtil.isManagerOfOwner(authentication, note) || SecurityUtil.hasAnyRole(authentication, RoleType.ROLE_SUPER_ADMIN))) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasPermissionObjectId(Authentication authentication, Serializable targetId, String permission) {
		Object obj = genericDao.getEntity(Note.class, targetId);
		return hasPermission(authentication, obj, permission);
	}
}