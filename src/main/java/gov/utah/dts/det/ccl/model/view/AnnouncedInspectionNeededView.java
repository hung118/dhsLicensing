package gov.utah.dts.det.ccl.model.view;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_ANNOUN_INSP_NEEDED_VIEW")
@Immutable
public class AnnouncedInspectionNeededView extends BaseInspectionNeededView {

	public AnnouncedInspectionNeededView() {
		
	}
}