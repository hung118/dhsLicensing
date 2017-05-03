package gov.utah.dts.det.validators;

import gov.utah.dts.det.ccl.model.BetweenDateRange;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class DateBetweenValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
		Object value = this.getFieldValue(fieldName, object);
		
		if (!(value instanceof BetweenDateRange)) {
			return;
		}
		
		BetweenDateRange compareRange = (BetweenDateRange) value;
		if (compareRange.getEndDate() == null || 
			compareRange.getStartDate() == null || 
			compareRange.getCompareDate() == null ||
			compareRange.getStartDate().after(compareRange.getCompareDate()) ||
			compareRange.getEndDate().before(compareRange.getCompareDate())) 
		{
			addFieldError(fieldName, object);
			return;
		}
	}
}