package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeDocument;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeUtil;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.ccl.model.FilesystemFile;
import gov.utah.dts.det.ccl.repository.FilesystemFileRepository;
import gov.utah.dts.det.ccl.service.FileService;
import gov.utah.dts.det.util.spring.AppContext;

import java.util.Map;

import ognl.Ognl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.document.XDocumentInsertable;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;

public class FileTag extends AbstractTemplateTag {
	
	private static final Logger logger = LoggerFactory.getLogger(FileTag.class);

	protected String value;
	
	public FileTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "file";
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In file tag: " + value);
		if (templateContext.isInMetadataSection()) {
			throw new TemplateException("File tag cannot be placed in the metadata section.");
		}
		try {
			setRenderingAttributes();
			if (!isGeneratingSubTemplate() && !templateContext.isScanPhase() && evaluating) {
				logger.debug("Evaluating file tag: " + value);
				//TODO: figure out how to get a hold of the attachment service and template loaders without tying this class to spring
				
				FilesystemFileRepository fileRepo = (FilesystemFileRepository) AppContext.getApplicationContext().getBean("filesystemFileRepository");
				FileService fileService = (FileService) AppContext.getApplicationContext().getBean("fileService");
				Long fileId = (Long) Ognl.getValue(value, templateContext.getExpressionContext(), templateContext.getExpressionRoot());
				FilesystemFile file = fileRepo.findOne(fileId);
				
				OpenOfficeDocument ooDoc = new OpenOfficeDocument(fileService.getInputStream(file), file.getFileType());
				
				XTextCursor cursor = (XTextCursor) UnoRuntime.queryInterface(XTextCursor.class,
						(XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY));
				XDocumentInsertable insertable = (XDocumentInsertable) UnoRuntime.queryInterface(XDocumentInsertable.class, cursor);
				
				OpenOfficeUtil ooUtil = (OpenOfficeUtil) AppContext.getApplicationContext().getBean(OpenOfficeUtil.BEAN_NAME);
				ooUtil.insertDocument(insertable, ooUtil.getDocumentFormat((((OpenOfficeTemplateRenderer) templateContext.getTemplate()).getFileType())), ooDoc);
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate file tag - value: " + value, e);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		//do nothing as the start and end tags are the same for file.
	}
}