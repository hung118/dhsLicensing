package gov.utah.dts.det.ccl.actions.admin.documents;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.service.DocumentService;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results(
	@Result(name = "success", location = "list-documents", type = "redirectAction")
)
public class SaveDocumentAction extends EditDocumentAction implements Preparable {

	@Override
	public void prepare() throws Exception {
		loadDocument();
		documentService.evict(document);
	}
	
	@Override
	public String execute() throws Exception {
		setup();
		documentService.saveDocument(document, fileId);
		return SUCCESS;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
}