package gov.utah.dts.det.ccl.documents.templating;

import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.FileDescriptor;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface Template {
	
	public String getTemplateKey();

	public List<Input> getInputs(Map<String, Object> context) throws TemplateException;
	
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException;
}