package gov.utah.dts.det.ccl.actions.docs;

import gov.utah.dts.det.ccl.documents.DocumentWebUtils;
import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.service.DocumentService;
import gov.utah.dts.det.ccl.view.JsonResponse;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class AvailableDocsAction extends ActionSupport {

	private DocumentService documentService;
	
	private List<String> contexts;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<Document> documents = documentService.getDocumentsInContext(DocumentWebUtils.getContextNames(contexts));
			response = new JsonResponse(200, documents);
		} catch (Exception e) {
			response = new JsonResponse(404, e.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public List<String> getContexts() {
		return contexts;
	}
	
	public void setContexts(List<String> contexts) {
		this.contexts = contexts;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}