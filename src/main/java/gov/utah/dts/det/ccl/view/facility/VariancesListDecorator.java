package gov.utah.dts.det.ccl.view.facility;

import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;

import org.displaytag.decorator.TableDecorator;

public class VariancesListDecorator extends TableDecorator {

	@Override
	public String addRowClass() {
		Variance v = (Variance) getCurrentRowObject();
		if (v.getOutcome() == VarianceOutcome.DENIED && v.isFinalized()) {
			return "redrow";
		} else if (!v.isActive() || (v.getOutcome() == VarianceOutcome.NOT_NECESSARY && v.isFinalized())) {
			return "inactive";
		}
		return null;
	}
}