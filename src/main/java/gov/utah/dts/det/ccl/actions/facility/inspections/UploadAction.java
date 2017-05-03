package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.model.Attachment;
import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-inspection", type = "redirectAction", params = {"facilityId", "${facilityId}","inspectionId", "${inspectionId}","checklistId", "${checklistId}"}),
	@Result(name = "input", location = "inspection-checklist-view", type = "redirectAction", params = {"facilityId", "${facilityId}","inspectionId", "${inspectionId}","checklistId", "${checklistId}"}),
	@Result(name = "delete-attachment", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"}),
	@Result(name = "view", location = "directors_attachments_list.jsp")
})
public class UploadAction extends BaseInspectionEditAction implements ServletResponseAware, Preparable, ParameterAware, SessionAware {

	private Map<String, Object> responseMap;
	private Map<String, String[]> params;
	private Map<String, Object> session;
	private JsonResponse response;
	private HttpServletResponse httpResponse;
	
	private Attachment attachment;
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	private Long attachmentId;
	
	@Override
	public void prepare() throws Exception {
//		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "directors-attachments-list")
	public String doList() {		
//		loadFacilityAttachments();
//		lstCtrl.setShowControls(true);
		return VIEW;
	}
	
	@Action(value = "upload-attachment")
	public String doForm() {
//		loadFacilityAttachments();
//		lstCtrl.setShowControls(false);
		return INPUT;
	}
	
	public void prepareDoSave() {
		super.loadInspection();
	}

	@Action(value="save-upload",
//			interceptorRefs = {@InterceptorRef(value = "fileUpload", params = {"maximumSize", "5120000", "allowedTypes", "application/pdf"}), 
//			@InterceptorRef("cclStack"), @InterceptorRef("uploadRedirectStack")}
			interceptorRefs = {@InterceptorRef(value = "fileUpload"), 
			@InterceptorRef("cclStack")})
	public String doSave() throws Exception {
		

		Map<String, String> errors = new HashMap<String, String>();
		if (!"application/pdf".equals(fileContentType)) {
			errors.put("file", "Attached file is not a PDF!");
		}
		if (file.length() > 5120000) {
			errors.put("file", "File is too large: Maximum = 5120000");
		}
		if (errors.size() > 0) {
			session.put("checklistFileErrors", errors);
			return super.INPUT;
		}

		loadInspection();
		
		long checklistId = Long.parseLong(params.get("checklistId")[0]);
		
		InspectionChecklist checkList = inspection.getCheckList(checklistId);
		
		attachment = new Attachment();
		
		byte[] baImage = new byte[(int)file.length()];
		FileInputStream fisImage = new FileInputStream(file);
		fisImage.read(baImage);
		attachment.setBinaryData(baImage);
		fisImage.close();
		
		attachment.setFileType(fileContentType);
		attachment.setFileName(fileFileName);
		attachment.setCreationDate(new Date());
		attachment.setFileSize(file.length());
		
		checkList.setAttachment(attachment);

		inspectionService.saveCheckList(checkList);
		
		return REDIRECT_VIEW;
	}

	@SkipValidation
	@Action(value="view-upload")
	public String doViewAttachment() throws Exception {
		
		loadInspection();
		
		long checklistId = Long.parseLong(params.get("checklistId")[0]);
		
		InspectionChecklist checkList = inspection.getCheckList(checklistId);
		
		attachment = checkList.getAttachment();
		ServletOutputStream sos = httpResponse.getOutputStream();
		httpResponse.setContentLength((int) attachment.getFileSize());
		httpResponse.setContentType(attachment.getFileType());
		
		sos.write(attachment.getBinaryData());		
				
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
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.params = parameters;
	}

	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}

	public Map<String, String[]> getParams() {
		return params;
	}

	public void setParams(Map<String, String[]> params) {
		this.params = params;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileName) {
		this.fileFileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Map<String, Object> getSession() {
		return session;
	}

}