package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.model.FilesystemFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {
	
	public FilesystemFile saveUploadFile(File file, String filename, String contentType) throws IOException;
	
	public FilesystemFile getFileById(Long fileId);
	
	public FilesystemFile createFile(String name, String filePath, PickListValue type) throws FileNotFoundException;
	
//	public FilesystemFile saveFile(Long fileId, String name, String filePath, PickListValue type) throws FileNotFoundException;
	
	public void deleteFile(Long fileId);
	
	public void deleteFile(FilesystemFile file);

	public InputStream getInputStream(FilesystemFile file) throws FileNotFoundException;
	
	public FileDescriptor previewFile(Long fileId) throws FileNotFoundException;
	
	public FileDescriptor downloadFile(Long fileId) throws IllegalArgumentException, FileNotFoundException;
	
	public void convert(InputStream inputStream, String inputFormat, OutputStream outputStream, String outputFormat);
}