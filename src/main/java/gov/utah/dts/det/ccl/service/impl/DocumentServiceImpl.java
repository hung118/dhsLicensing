package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.GenericDao;
import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.TemplateService;
import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.model.FilesystemFile;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.repository.DocumentRepository;
import gov.utah.dts.det.ccl.service.DocumentService;
import gov.utah.dts.det.ccl.service.FileService;
import gov.utah.dts.det.ccl.service.util.FileUtils;
import gov.utah.dts.det.filemanager.model.FileType;
import gov.utah.dts.det.service.ApplicationService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {
	
	public static final String DOCUMENT_KEY = "document";

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public Document loadDocumentById(Long id) {
		return documentRepository.findOne(id);
	}
	
	@Override
	public Document saveDocument(Document document, Long fileId) throws IOException {
		List<FilesystemFile> deleteFiles = new ArrayList<FilesystemFile>();
		if ((fileId == null && document.getFile() != null) ||
				(fileId != null && document.getFile() != null && !fileId.equals(document.getFile().getId()))) {
			deleteFiles.add(document.getFile());
			document.setFile(null);
		}
		
		if (fileId != null && document.getFile() == null) {
			//transfer the upload file to a permanent location
			FilesystemFile file = fileService.getFileById(fileId);
			file.setType(getFileType(document));
			file.setName(document.getName());
			String newFilePath = fileUtils.moveFile(file.getFilePath(), getPath(document));
			file.setFilePath(newFilePath);
			document.setFile(file);
		} else if (fileId != null && document.getFile() != null && document.getFile().getId().equals(fileId)) {
			//update the file with the new details from the document
			FilesystemFile file = document.getFile();
			PickListValue fileType = getFileType(document);
			if (!fileType.equals(file.getType())) {
				file.setType(fileType);
			}
			if (!document.getName().equals(file.getName())) {
				file.setName(document.getName());
			}
			String path = getPath(document);
			if (!file.getFilePath().startsWith(path)) {
				String newFilePath = fileUtils.moveFile(file.getFilePath(), path);
				file.setFilePath(newFilePath);
			}
		}
		
		document = documentRepository.save(document);
		
		for (FilesystemFile file : deleteFiles) {
			fileService.deleteFile(file);
		}
		
		return document;
	}
	
	private String getPath(Document document) {
		if (StringUtils.isBlank(document.getTemplateName())) {
			return "documents";
		} else {
			return "templates";
		}
	}
	
	private PickListValue getFileType(Document document) {
		if (document.getTemplateName() == null) {
			return applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.FILE_TYPE_DOCUMENT.getKey());
		} else {
			return applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.FILE_TYPE_TEMPLATE.getKey());
		}
	}
	
	@Override
	public void deleteDocument(Long documentId) {
		Document document = loadDocumentById(documentId);
		FilesystemFile file = document.getFile();
		documentRepository.delete(document);
		fileService.deleteFile(file);
	}
	
	@Override
	public List<Input> getInputs(Long documentId, Map<String, Object> context) {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new IllegalArgumentException("Unable to find requested document.");
		}
		if (document.getTemplateName() != null) {
			context.put(DOCUMENT_KEY, document);
			List<Input> inputs = templateService.getInputs(document.getTemplateName(), context);
			inputs.add(new Input("document", null, documentId, Long.class, true, InputDisplayType.HIDDEN));
			return inputs;
		} else {
			return new ArrayList<Input>();
		}
	}
	
	@Override
	public void renderDocument(Long documentId, Map<String, Object> context, FileDescriptor descriptor) throws DocumentException {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new IllegalArgumentException("Unable to find requested document.");
		}
		if (document.getTemplateName() != null) {
			context.put(DOCUMENT_KEY, document);
			templateService.render(document.getTemplateName(), context, descriptor);
		} else {
			descriptor.setFileName(document.getName() + "." + document.getFile().getFileType().name().toLowerCase());
			descriptor.setContentLength(document.getFile().getFileSize().toString());
			descriptor.setContentType(document.getFile().getFileType().getMimeType());
			try {
				descriptor.setInputStream(fileService.getInputStream(document.getFile()));
			} catch (FileNotFoundException fnfe) {
				throw new DocumentException("Unable to render document because the document file was not found.", fnfe);
			}
		}
	}
	
	@Override
	public void renderDocument(Long documentId, Map<String, Object> context, OutputStream outputStream) throws DocumentException {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new IllegalArgumentException("Unable to find requested document.");
		}
		if (document.getTemplateName() != null) {
			context.put(DOCUMENT_KEY, document);
			templateService.render(document.getTemplateName(), context, outputStream);
		} else {
			try {
				InputStream is = null;
				OutputStream os = null;
				
				try {
					is = new BufferedInputStream(fileService.getInputStream(document.getFile()));
					os = new BufferedOutputStream(outputStream);
					
					IOUtils.copy(is, os);
				} finally {
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
				}
			} catch (IOException ioe) {
				throw new DocumentException(ioe);
			}
		}
	}
	
	@Override
	public FilesystemFile renderDocument(Long documentId, Map<String, Object> context) throws DocumentException {
		Document document = documentRepository.findOne(documentId);
		if (document == null) {
			throw new IllegalArgumentException("Unable to find requested document.");
		} else if (document.getTemplateName() == null) {
			return null;
		} else {
			try {
				File f = fileUtils.getTempFile(FileType.PDF);
				FileOutputStream os = new FileOutputStream(f);
				context.put(DOCUMENT_KEY, document);
				String fileName = templateService.render(document.getTemplateName(), context, os);
				PickListValue fileType = applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.FILE_TYPE_DOCUMENT.getKey());
				FilesystemFile file = fileService.createFile(fileName, fileUtils.getPathRelativeToRoot(f.getAbsolutePath()),
						fileType);
				return file;
			} catch (FileNotFoundException fnfe) {
				throw new DocumentException(fnfe);
			}
		}
	}
	
	@Override
	public FilesystemFile renderTemplate(String template, Map<String, Object> context) throws TemplateException {
		if (template == null) {
			throw new IllegalArgumentException("The template to render was not provided.");
		}
		try {
			File f = fileUtils.getTempFile(FileType.PDF);
			FileOutputStream os = new FileOutputStream(f);
			String fileName = templateService.render(template, context, os);
			PickListValue fileType = applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.FILE_TYPE_DOCUMENT.getKey());
			FilesystemFile file = fileService.createFile(fileName, fileUtils.getPathRelativeToRoot(f.getAbsolutePath()), fileType);
			return file;
		} catch (FileNotFoundException fnfe) {
			throw new DocumentException(fnfe);
		}
	}
	
	@Override
	public List<Document> getDocuments() {
		return documentRepository.findAll();
	}
	
	@Override
	public List<Document> getDocumentsInContext(List<String> contexts) {
		return documentRepository.getDocumentsInContexts(contexts);
	}
	
	@Override
	public void evict(Object entity) {
		genericDao.getEntityManager().detach(entity);
	}
}