package gov.utah.dts.det.ccl.actions.admin;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.service.FileService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "stream", params = {"contentType", "${descriptor.contentType}", 
			"contentDisposition", "filename=\"${descriptor.fileName}\"", "contentLength",
			"${descriptor.contentLength}", "inputName", "descriptor.inputStream"})
})
public class PreviewFileAction extends ActionSupport {
	
	private FileService fileService;

	private Long fileId;
	
	private FileDescriptor descriptor;
	
	@Override
	public String execute() throws Exception {
		descriptor = fileService.previewFile(fileId);
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
	
	public FileDescriptor getDescriptor() {
		return descriptor;
	}
}