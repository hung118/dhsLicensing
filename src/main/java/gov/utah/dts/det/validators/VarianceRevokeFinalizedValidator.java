package gov.utah.dts.det.validators;

import gov.utah.dts.det.ccl.model.Variance;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class VarianceRevokeFinalizedValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
		Object value = this.getFieldValue(fieldName, object);
		
		if (!(value instanceof Variance)) {
			return;
		}
		
		Variance variance = (Variance) value;
		if (!variance.isRevokeFinalized()) {
			addFieldError(fieldName, object);
			return;
		}
	}
}