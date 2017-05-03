package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.model.FilesystemFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface DocumentService {
	
	public static final String CONTEXT_PREFIX = "doc-ctx";

	public Document loadDocumentById(Long id);
	
	public Document saveDocument(Document document, Long fileId) throws IOException;
	
	public void deleteDocument(Long documentId);
	
	public List<Input> getInputs(Long documentId, Map<String, Object> context);
	
	public void renderDocument(Long documentId, Map<String, Object> context, FileDescriptor descriptor) throws DocumentException;
	
	public void renderDocument(Long documentId, Map<String, Object> context, OutputStream outputStream) throws DocumentException;
	
	public FilesystemFile renderDocument(Long documentId, Map<String, Object> context) throws DocumentException;
	
	public FilesystemFile renderTemplate(String template, Map<String, Object> context) throws TemplateException;
	
	public List<Document> getDocuments();
	
	public List<Document> getDocumentsInContext(List<String> contexts);
	
	public void evict(final Object entity);
}