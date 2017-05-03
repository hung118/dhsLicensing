package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.ccl.model.FilesystemFile;
import gov.utah.dts.det.ccl.service.FileService;
import gov.utah.dts.det.ccl.view.JsonResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class UploadFileAction extends ActionSupport {

	private FileService fileService;
	
	private File file;
	private String contentType;
	private String filename;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			FilesystemFile newFile = fileService.saveUploadFile(file, filename, contentType);
			Map<String, Object> resp = new HashMap<String, Object>();
			resp.put("success", "true");
			resp.put("file", newFile);
			response = new JsonResponse(200, resp);
		} catch (Exception e) {
			response = new JsonResponse(400, e.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public void setUpload(File file) {
		this.file = file;
	}
	
	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setUploadFileName(String filename) {
		this.filename = filename;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}