package gov.utah.dts.det.ccl.documents.templating;

import gov.utah.dts.det.ccl.documents.FileDescriptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public abstract class AbstractTemplate implements Template {
	
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss a");
	
	protected String getFileName(Map<String, Object> context) {
		return getTemplateKey() + " " + sdf.format(new Date());
	}
	
	protected void setFileName(Map<String, Object> context, FileDescriptor descriptor) {
		if (descriptor != null) {
			descriptor.setFileName(getFileName(context));
		}
	}
	
	/*protected Object getInputValue(List<Input> inputs, String inputKey) {
		for (Input input : inputs) {
			if (input.getId().equals(inputKey)) {
				return input.getValue();
			}
		}
		return null;
	}*/
}