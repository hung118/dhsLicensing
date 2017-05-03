package gov.utah.dts.det.ccl.view.admin;

import gov.utah.dts.det.ccl.model.Activatable;

import org.displaytag.decorator.TableDecorator;

public class InactiveRecordDecorator extends TableDecorator {

	@Override
	public String addRowClass() {
		return ((Activatable) getCurrentRowObject()).isActive() ? null : "inactive";
	}
}