package gov.utah.dts.det.ccl.actions.admin.documents;

import gov.utah.dts.det.ccl.service.DocumentService;
import gov.utah.dts.det.query.ListControls;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results(
	@Result(name = "success", location = "documents_list.jsp")
)
public class ListDocumentsAction extends ActionSupport implements Preparable {

	private DocumentService documentService;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl = new ListControls();
//		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(tBy.values())));
//		lstCtrl.setSortBy(InspectionSortBy.getDefaultSortBy().name());
	}
	
	@Override
	public String execute() throws Exception {
		lstCtrl.setResults(documentService.getDocuments());
		return SUCCESS;
	}
	
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
}