package gov.utah.dts.det.ccl.documents.templating;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface TemplateService {

	public List<Input> getInputs(String template, Map<String, Object> context) throws TemplateException;
	
	public void render(String template, Map<String, Object> context, FileDescriptor descriptor) throws TemplateException;
	
	public String render(String template, Map<String, Object> context, OutputStream outputStream) throws TemplateException;
}