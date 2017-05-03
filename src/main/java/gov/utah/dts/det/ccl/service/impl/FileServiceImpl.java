package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.model.FilesystemFile;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.repository.FilesystemFileRepository;
import gov.utah.dts.det.ccl.service.FileService;
import gov.utah.dts.det.ccl.service.util.FileUtils;
import gov.utah.dts.det.filemanager.model.FileType;
import gov.utah.dts.det.service.ApplicationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private DocumentConverter documentConverter;
	
	@Autowired
	private DocumentFormatRegistry documentFormatRegistry;
	
	@Autowired
	private FilesystemFileRepository filesystemFileRepository;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private ApplicationService applicationService;
	
	//TODO: add file security.  Look at the type and see what roles can access it.
	
	@Override
	public FilesystemFile saveUploadFile(File file, String fileName, String contentType) throws IOException {
		PickListValue tempFileType = applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.FILE_TYPE_TEMP_FILE.getKey());
		File tempFile = fileUtils.saveTempFile(file, fileName);
		return createFile(fileName, fileUtils.getPathRelativeToRoot(tempFile.getAbsolutePath()), tempFileType);
	}
	
	@Override
	public FilesystemFile getFileById(Long fileId) {
		return filesystemFileRepository.findOne(fileId);
	}
	
	@Override
	public FilesystemFile createFile(String name, String filePath, PickListValue type) throws FileNotFoundException {
		if (!fileUtils.fileExists(filePath)) {
			throw new FileNotFoundException("File at " + filePath + " was not found.");
		}
		
		FilesystemFile file = new FilesystemFile();
		file.setName(name);
		file.setType(type);
		file.setFilePath(filePath);
		File f = fileUtils.getFile(filePath);
		file.setFileSize(f.length());
		file.setFileType(fileUtils.getFileType(filePath));
		return filesystemFileRepository.save(file);
	}
	
	@Override
	public void deleteFile(Long fileId) {
		FilesystemFile file = filesystemFileRepository.findOne(fileId);
		deleteFile(file);
	}
	
	@Override
	public void deleteFile(FilesystemFile file) {
		if (file != null) {
			fileUtils.deleteFile(file.getFilePath());
		}
		filesystemFileRepository.delete(file);
	}
	
	@Override
	public InputStream getInputStream(FilesystemFile file) throws FileNotFoundException {
		return fileUtils.getInputStream(file.getFilePath());
	}
	
	@Override
	public FileDescriptor previewFile(Long fileId) throws FileNotFoundException {
		FilesystemFile file = filesystemFileRepository.findOne(fileId);
		if (file == null) {
			throw new IllegalArgumentException("Unable to find the file requested.");
		}
		
		FileDescriptor descriptor = new FileDescriptor();
		if (file.getName().lastIndexOf(".") == -1) {
			descriptor.setFileName(file.getName() + "." + FileType.PDF.name().toLowerCase());
		} else {
			descriptor.setFileName(file.getName());
		} 
		descriptor.setContentType(FileType.PDF.getMimeType());
		if (file.getFileType() == FileType.PDF) {
			descriptor.setInputStream(getInputStream(file));
			descriptor.setContentLength(file.getFileSize().toString());
		} else {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			convert(getInputStream(file), file.getFileType().name(), os, FileType.PDF.name());
			byte[] converted = os.toByteArray();
			ByteArrayInputStream is = new ByteArrayInputStream(converted);
			descriptor.setContentLength(Integer.toString(converted.length));
			descriptor.setInputStream(is);
		}
		
		return descriptor;
	}
	
	@Override
	public FileDescriptor downloadFile(Long fileId) throws IllegalArgumentException, FileNotFoundException{
		FilesystemFile file = filesystemFileRepository.findOne(fileId);
		if (file == null) {
			throw new IllegalArgumentException("Unable to find the file requested.");
		}
		
		FileDescriptor descriptor = new FileDescriptor();
		String name = file.getName();
		if (file.getName().indexOf(".") == -1) {
			name = name + "." + file.getFileType().name().toLowerCase();
		}
		descriptor.setFileName(name);
		descriptor.setContentType(file.getFileType().getMimeType());
		descriptor.setContentLength(file.getFileSize().toString());
		descriptor.setInputStream(getInputStream(file));
		
		return descriptor;
	}
	
	@Override
	public void convert(InputStream inputStream, String inputFormat, OutputStream outputStream, String outputFormat) {
		DocumentFormat inFormat = documentFormatRegistry.getFormatByFileExtension(inputFormat);
		DocumentFormat outFormat = documentFormatRegistry.getFormatByFileExtension(outputFormat);
		documentConverter.convert(inputStream, inFormat, outputStream, outFormat);
	}
}