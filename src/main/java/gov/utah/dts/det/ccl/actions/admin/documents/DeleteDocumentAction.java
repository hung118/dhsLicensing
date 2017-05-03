package gov.utah.dts.det.ccl.actions.admin.documents;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import gov.utah.dts.det.ccl.service.DocumentService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results(
	@Result(name = "success", location = "list-documents", type = "redirectAction")
)
public class DeleteDocumentAction extends ActionSupport {

	private DocumentService documentService;
	
	private Long documentId;
	
	@Override
	public String execute() throws Exception {
		documentService.deleteDocument(documentId);
		return SUCCESS;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public Long getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
}