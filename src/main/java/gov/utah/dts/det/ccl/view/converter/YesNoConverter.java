package gov.utah.dts.det.ccl.view.converter;

import gov.utah.dts.det.ccl.view.YesNoChoice;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class YesNoConverter extends StrutsTypeConverter {
	
	private static final Logger logger = LoggerFactory.getLogger(YesNoConverter.class);
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (StringUtils.isBlank(values[0])) {
			return null;
		}
		if (values[0].equalsIgnoreCase(YesNoChoice.YES.getDisplayName())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public String convertToString(Map context, Object o) {
		logger.debug("****in YesNoConverter.convertToString()****");
		if (o == null) {
			YesNoChoice.NO.getDisplayName();
		}
		if (((Boolean) o) == Boolean.TRUE) {
			return YesNoChoice.YES.getDisplayName();
		}
		return YesNoChoice.NO.getDisplayName();
	}
}