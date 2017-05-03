package gov.utah.dts.det.ccl.actions.docs;

import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.ccl.documents.DocumentWebUtils;
import gov.utah.dts.det.ccl.documents.templating.TemplateValidationException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeServiceException;
import gov.utah.dts.det.ccl.model.FilesystemFile;
import gov.utah.dts.det.ccl.service.DocumentService;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class RenderAction extends ActionSupport implements ParameterAware {

	private DocumentService documentService;
	
	private Map<String, String[]> parameters;
	
	private Long document;
	private String template;
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			FilesystemFile file = null;
			if (document != null) {
				file = documentService.renderDocument(document, DocumentWebUtils.convertParamsToContext(parameters));
			} else if (StringUtils.isNotBlank(template)) {
				file = documentService.renderTemplate(template, DocumentWebUtils.convertParamsToContext(parameters));
			}
			response = new JsonResponse(200, file);
		} catch (OpenOfficeServiceException oose) {
			response = ViewUtils.getErrorResponse(500, oose.getMessage());
		} catch (TemplateValidationException tve) {
			ViewUtils.addFieldErrors(this, tve.getErrors());
			response = ViewUtils.getJsonResponse(this);
		} catch (DocumentException de) {
			response = ViewUtils.getErrorResponse(400, de.getMessage());
		} catch (Exception e) {
			response = ViewUtils.getErrorResponse(500, e.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
	
	public Long getDocument() {
		return document;
	}
	
	public void setDocument(Long document) {
		this.document = document;
	}
	
	public String getTemplate() {
		return template;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}