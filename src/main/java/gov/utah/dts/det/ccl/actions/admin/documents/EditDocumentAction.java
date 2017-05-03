package gov.utah.dts.det.ccl.actions.admin.documents;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.service.DocumentService;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results(
	@Result(name = "input", location = "document_form.jsp")
)
public class EditDocumentAction extends ActionSupport {

	protected PickListService pickListService;
	protected DocumentService documentService;
	
	protected Long documentId;
	protected Document document;
	protected Long fileId;
	
	private List<PickListValue> categories;
	private List<PickListValue> contexts;
	
	@Override
	public String execute() throws Exception {
		setup();
		loadDocument();
		return INPUT;
	}
	
	protected void setup() {
		categories = pickListService.getValuesForPickList("Document Category", true);
		contexts = pickListService.getValuesForPickList("Document Context", true);
	}
	
	protected void loadDocument() {
		if (documentId != null) {
			document = documentService.loadDocumentById(documentId);
			if (document.getFile() != null) {
				fileId = document.getFile().getId();
			}
		}
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
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
	
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public Long getFileId() {
		return fileId;
	}
	
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	public List<PickListValue> getCategories() {
		return categories;
	}
	
	public List<PickListValue> getContexts() {
		return contexts;
	}
}