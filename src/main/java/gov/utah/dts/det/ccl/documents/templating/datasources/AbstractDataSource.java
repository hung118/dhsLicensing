package gov.utah.dts.det.ccl.documents.templating.datasources;

import org.apache.commons.lang.StringUtils;

import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

public abstract class AbstractDataSource implements DataSource {
	
	protected Input getInput(TemplateContext templateContext, String key) {
		return new Input(getKey(key), null, getObjectId(templateContext, key).toString(), String.class, true, InputDisplayType.HIDDEN);
	}
	
	protected String getKey(String key) {
		StringBuilder ctxKey = new StringBuilder(DataSource.DS_PREFIX);
		ctxKey.append(".");
		ctxKey.append(key);
		return ctxKey.toString();
	}

	protected Long getObjectId(TemplateContext templateContext, String key) {
		String idStr = (String) templateContext.getExpressionContext().get(getKey(key));
		if (StringUtils.isNotBlank(idStr)) {
			return new Long(idStr);
		}
		return null;
	}
}