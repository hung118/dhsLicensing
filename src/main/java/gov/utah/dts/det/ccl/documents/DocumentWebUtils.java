package gov.utah.dts.det.ccl.documents;

import gov.utah.dts.det.ccl.service.DocumentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class DocumentWebUtils {

	private DocumentWebUtils() {
		
	}
	
	public static Map<String, Object> convertParamsToContext(Map<String, String[]> params) {
		Map<String, Object> context = new HashMap<String, Object>();
		
		for (Entry<String, String[]> param : params.entrySet()) {
			String[] val = param.getValue();
			if (val != null) {
				if (val.length == 1 && StringUtils.isNotBlank(val[0])) {
					context.put(param.getKey(), val[0]);
				} else if (val.length > 1) {
					List<String> valLst = new ArrayList<String>();
					for (String str : val) {
						if (StringUtils.isNotBlank(str)) {
							valLst.add(str);
						}
					}
				}
			}
		}
		return context;
	}
	
	public static List<String> getContextNames(List<String> contexts) {
		List<String> contextNames = new ArrayList<String>();
		if (contexts != null) {
			for (String ctx : contexts) {
				contextNames.add(ctx.replaceFirst(DocumentService.CONTEXT_PREFIX + ".", ""));
			}
		}
		return contextNames;
	}
}