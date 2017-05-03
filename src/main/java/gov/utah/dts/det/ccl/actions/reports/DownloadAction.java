package gov.utah.dts.det.ccl.actions.reports;

import gov.utah.dts.det.ccl.service.util.FileUtils;
import gov.utah.dts.det.filemanager.model.FileType;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "stream", params = {"contentType", "${contentType}", "contentDisposition", "attachment;filename=\"${fileName}\""})
})
public class DownloadAction extends ActionSupport {
	
	private FileUtils fileUtils;

	private InputStream inputStream;
	private String fileName;
	private String contentType;
	
	public String execute() {
		String type = fileName.substring(fileName.indexOf(".") + 1);
		contentType = FileType.valueOf(type.toUpperCase()).getMimeType();
		
		try {
			inputStream = fileUtils.getInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return SUCCESS;
	}
	
	public void setFileUtils(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getContentType() {
		return contentType;
	}
}