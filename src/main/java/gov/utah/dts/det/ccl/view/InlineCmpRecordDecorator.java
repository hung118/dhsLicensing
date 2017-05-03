package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.ccl.model.view.InspectionView;

import org.displaytag.decorator.TableDecorator;

public class InlineCmpRecordDecorator extends TableDecorator {

	@Override
	public String addRowId() {
		InspectionView iv = (InspectionView) getCurrentRowObject();
		return "cmpHist-ins" + iv.getId();
	}
}