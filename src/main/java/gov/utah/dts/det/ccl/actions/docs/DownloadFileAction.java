package gov.utah.dts.det.ccl.actions.docs;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.service.FileService;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "stream", params = {"contentType", "${descriptor.contentType}", 
			"contentDisposition", "${attach}filename=\"${descriptor.fileName}\"", "contentLength",
			"${descriptor.contentLength}", "inputName", "descriptor.inputStream"})
})
public class DownloadFileAction extends ActionSupport {
	
	private FileService fileService;

	private Long fileId;
	private boolean attachment = true;
	
	private FileDescriptor descriptor;
	
	@Override
	public String execute() throws Exception {
		descriptor = fileService.downloadFile(fileId);
		return SUCCESS;
	}
	
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public Long getFileId() {
		return fileId;
	}
	
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	public String getAttach() {
		if (attachment) {
			return "attachment;";
		} else {
			return "";
		}
	}
	
	public boolean isAttachment() {
		return attachment;
	}
	
	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}
	
	public FileDescriptor getDescriptor() {
		return descriptor;
	}
}