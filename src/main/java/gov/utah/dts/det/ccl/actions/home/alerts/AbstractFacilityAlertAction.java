package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.templating.TemplateService;
import gov.utah.dts.det.ccl.documents.templating.templates.MailingLabelsTemplate;
import gov.utah.dts.det.ccl.view.MailingLabel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "labels", type = "stream", params = {"contentType", "${descriptor.contentType}", 
			"contentDisposition", "attachment;filename=\"${descriptor.fileName}\"", "contentLength",
			"${descriptor.contentLength}", "inputName", "descriptor.inputStream"})
})
public abstract class AbstractFacilityAlertAction extends BaseAlertAction {

	protected TemplateService templateService;
	
	private FileDescriptor descriptor;
	
	@Action(value = "print-labels")
	public String doLabels() {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(MailingLabelsTemplate.MAILING_LABELS_KEY, getMailingLabels());
		descriptor = new FileDescriptor();
		templateService.render(MailingLabelsTemplate.TEMPLATE_KEY, context, descriptor);
		
		return "labels";
	}
	
	protected abstract List<? extends MailingLabel> getMailingLabels();
	
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	public FileDescriptor getDescriptor() {
		return descriptor;
	}
}