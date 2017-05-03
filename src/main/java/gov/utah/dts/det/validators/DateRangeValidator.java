package gov.utah.dts.det.validators;

import gov.utah.dts.det.ccl.model.DateRange;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class DateRangeValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		String fieldName = getFieldName();
		Object value = this.getFieldValue(fieldName, object);
		
		if (!(value instanceof DateRange)) {
			return;
		}
		
		DateRange dateRange = (DateRange) value;
		if (dateRange.getEndDate() != null && dateRange.getStartDate() != null && dateRange.getStartDate().after(dateRange.getEndDate())) {
			addFieldError(fieldName, object);
			return;
		}
	}
}