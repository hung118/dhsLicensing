package gov.utah.dts.det.ccl.model.view;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Immutable;
import org.json.JSONException;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_UNANN_INSP_NEEDED_VIEW")
@Immutable
public class UnannouncedInspectionNeededView extends BaseInspectionNeededView {

	private transient AlertType alertType;
	
	public UnannouncedInspectionNeededView() {
		
	}
	
	public AlertType getAlertType() throws JSONException, ParseException {
		if (alertType == null) {
			alertType = AlertType.ALERT;
			Date now = new Date();
			Date orangeDate = DateUtils.addMonths(getLastAnnouncedInspectionDate(), 6);
			if (now.after(orangeDate)) {
				alertType = AlertType.ORANGE_ALERT;

				Date redDate = DateUtils.addMonths(getLastAnnouncedInspectionDate(), 6);
				redDate = DateUtils.addDays(redDate, 15);
				if (now.after(redDate)) {
					alertType = AlertType.RED_ALERT;
				}
			}
		}
		return alertType;
	}
}