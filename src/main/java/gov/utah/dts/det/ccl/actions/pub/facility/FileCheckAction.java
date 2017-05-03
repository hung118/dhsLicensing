package gov.utah.dts.det.ccl.actions.pub.facility;

import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.ListControls;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "tiles", location = "public.facility.file-check")
})
public class FileCheckAction extends BasePublicFacilityAction {

	private ListControls lstCtrl = new ListControls();
	
	private boolean conditional;
	
	
	public String execute() {
		try {
			lstCtrl.setResults(facilityService.getFileCheck(getFacility().getId(), ListRange.SHOW_PAST_24_MONTHS));
			List<FacilityTag> condTags = getFacility().getActiveTags(applicationService.getPickListValueForApplicationProperty(
					ApplicationPropertyKey.TAG_CONDITIONAL.getKey()));
			if (!condTags.isEmpty()) {
				FacilityTag condTag = condTags.get(0);
				Date cutoffDate = DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DATE), -30);
				if (condTag.getStartDate().compareTo(cutoffDate) <= 0) {
					conditional = true;
				}
			}
		} catch (AccessDeniedException ade) {
			addActionError(ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public boolean isConditional() {
		return conditional;
	}
}