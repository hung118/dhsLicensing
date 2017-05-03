package gov.utah.dts.det.ccl.actions.facility.information.staff;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityAttachment;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;
import gov.utah.dts.det.query.ListControls;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "directors-attachments-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "upload_attachment_form.jsp"),
	@Result(name = "delete-attachment", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"}),
	@Result(name = "view", location = "directors_attachments_list.jsp")
})
public class UploadAction extends BaseFacilityAction implements ServletResponseAware, Preparable {
	
	private ListControls lstCtrl = new ListControls();
	
	private FacilityAttachment directorAttachment;
	private File attachment;
	private String attachmentContentType;
	private String attachmentFileName;
	private List<PickListValue> categories;
	private Long attachmentId;
	
	private JsonResponse response;
	private HttpServletResponse httpResponse;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "directors-attachments-list")
	public String doList() {		
		loadFacilityAttachments();
		lstCtrl.setShowControls(true);
		return VIEW;
	}
	
	@Action(value = "upload-attachment")
	public String doForm() {
		loadFacilityAttachments();
		lstCtrl.setShowControls(false);
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadFacilityAttachments();
	}

	@Action(value="save-upload",
			interceptorRefs = {@InterceptorRef(value = "fileUpload", params = {"maximumSize", "5120000"}), 
				@InterceptorRef("cclStack")}
	)
	public String doSave() throws Exception {
		
		byte[] baImage = new byte[(int)attachment.length()];
		FileInputStream fisImage = new FileInputStream(attachment);
		fisImage.read(baImage);
		directorAttachment.setAttachment(baImage);
		fisImage.close();
		
		directorAttachment.setFacility(getFacility());
		directorAttachment.setAttachmentType("Staff");
		directorAttachment.setAttachmentContentType(attachmentContentType);
		directorAttachment.setAttachmentFileName(attachmentFileName);
		directorAttachment.setInsertTimestamp(new Date());

		facilityService.saveBoardMemberAttachment(directorAttachment);
		
		return REDIRECT_VIEW;
	}
	
	@Action(value="view-upload")
	public String doViewAttachment() throws Exception {
		
		directorAttachment = facilityService.loadBoardMemberAttachment(attachmentId);
		ServletOutputStream sos = httpResponse.getOutputStream();
		httpResponse.setContentLength(directorAttachment.getAttachment().length);
		httpResponse.setContentType(directorAttachment.getAttachmentContentType());
		
		sos.write(directorAttachment.getAttachment());		
				
		sos.flush();
		sos.close();
		
		return null;
	}
	
	@Action(value="delete-upload")
	public String doDelete() {
		
		try {
			facilityService.deleteBoardMemberAttachment(attachmentId);
			setResponse(new JsonResponse(200));
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			setResponse(new JsonResponse(400, getActionErrors()));
		} catch (AccessDeniedException ade) {
			setResponse(new JsonResponse(401, ade.getMessage()));
		}
		
		return DELETE_ATTACHMENT;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public FacilityAttachment getDirectorAttachment() {
		return directorAttachment;
	}

	public void setDirectorAttachment(FacilityAttachment directorAttachment) {
		this.directorAttachment = directorAttachment;
	}

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	
	public List<PickListValue> getCategories() {
		if (categories == null) {
			categories = pickListService.getValuesForPickList("Staff Attachment Category", true);
		}
		return categories;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public JsonResponse getResponse() {
		return response;
	}

	public void setResponse(JsonResponse response) {
		this.response = response;
	}

	@Override
	public void setServletResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	
	private void loadFacilityAttachments() {
		lstCtrl.setResults(facilityService.getFacilityAttachments(super.getFacilityId(), "Staff"));
	}

}