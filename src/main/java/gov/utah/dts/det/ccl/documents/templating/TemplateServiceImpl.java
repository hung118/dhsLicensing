package gov.utah.dts.det.ccl.documents.templating;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
	
	private static final String DATE_PATTERN = "MM/dd/yyyy";
	
	private DateConverter dateConverter = null;
	
	private Map<String, Template> templates = new HashMap<String, Template>();

	@Override
	public List<Input> getInputs(String template, Map<String, Object> context) throws TemplateException {
		Template tmpl = templates.get(template);
		return tmpl.getInputs(context);
	}
	
	@Override
	public void render(String template, Map<String, Object> context, FileDescriptor descriptor) throws TemplateException {
		Template tmpl = templates.get(template);
		validateInputs(tmpl, context);
		
		descriptor.setContentType("application/pdf");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		tmpl.render(context, os, descriptor);
		byte[] bytes = os.toByteArray();
		descriptor.setContentLength(Integer.toString(bytes.length));
		descriptor.setInputStream(new ByteArrayInputStream(bytes));
	}
	
	public String render(String template, java.util.Map<String,Object> context, OutputStream outputStream) throws TemplateException {
		Template tmpl = templates.get(template);
		validateInputs(tmpl, context);
		
		FileDescriptor descriptor = new FileDescriptor();
		tmpl.render(context, outputStream, descriptor);
		return descriptor.getFileName();
	}
	
	private void validateInputs(Template template, Map<String, Object> context) throws TemplateValidationException {
		Map<String, String> errors = new LinkedHashMap<String, String>();
		List<Input> inputs = template.getInputs(context);
		for (Input i : inputs) {
			//validate types and required
			Object value = context.get(i.getId());
			if (i.getType() == Boolean.class && value == null) {
				value = Boolean.FALSE;
			} else if (value == null && i.getValue() != null) {
				value = i.getValue();
			} else if (value == null && i.isRequired()) {
				errors.put(i.getId(), i.getLabel() == null ? i.getId() : i.getLabel() + " is required.");
//				throw new TemplateException(i.getLabel() + " is required.");
			}
			if (value != null) {
			try {
					Object converted = null;
					if (i.getType() == Date.class) {
						converted = getDateConverter().convert(Date.class, value);
					} else {
						converted = ConvertUtils.convert(value, i.getType());
					}
				context.put(i.getId(), converted);
			} catch (ConversionException ce) {
				errors.put(i.getId(), i.getLabel() == null ? i.getId() : i.getLabel() + " is not a valid value.");
//				throw new TemplateException("Unable to convert value " + value + " to the appropriate type");
			}
		}
		}
		if (!errors.isEmpty()) {
			throw new TemplateValidationException("One or more inputs are invalid", errors);
		}
	}
	
	private DateConverter getDateConverter() {
		if (dateConverter == null) {
			dateConverter = new DateConverter();
			dateConverter.setPattern(DATE_PATTERN);
		}
		return dateConverter;
	}

	@Autowired(required = true)
	public void setTemplates(Set<Template> templates) {
		for (Template tmpl : templates) {
			this.templates.put(tmpl.getTemplateKey(), tmpl);
		}
	}
}