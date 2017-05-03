package gov.utah.dts.det.ccl.actions.docs;

import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.ccl.documents.DocumentWebUtils;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeServiceException;
import gov.utah.dts.det.ccl.service.DocumentService;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class InputAction extends ActionSupport implements ParameterAware {

	private DocumentService documentService;
	
	private Map<String, String[]> parameters;
	
	private Long document;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<Input> inputs = documentService.getInputs(document, DocumentWebUtils.convertParamsToContext(parameters));
			response = new JsonResponse(200, inputs);
		} catch (OpenOfficeServiceException oose) {
			response = ViewUtils.getErrorResponse(500, oose.getMessage());
		} catch (DocumentException de) {
			response = ViewUtils.getErrorResponse(400, de.getMessage());
		} catch (Exception e) {
			response = ViewUtils.getErrorResponse(500, e.getMessage());
		}
		
		return SUCCESS;
	}
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public Long getDocument() {
		return document;
	}
	
	public void setDocument(Long document) {
		this.document = document;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}