package gov.utah.dts.det.ccl.model.view;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "AL_FOLL_UPS_NEEDED_VW")
@Immutable
public class FollowUpsNeededView extends AlertFollowUpsNeededView {
	
	public FollowUpsNeededView() {
		
	}
}