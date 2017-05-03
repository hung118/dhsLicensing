package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeDocument;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeUtil;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import ognl.Ognl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.datatransfer.XTransferable;
import com.sun.star.datatransfer.XTransferableSupplier;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XController;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextViewCursor;
import com.sun.star.text.XTextViewCursorSupplier;
import com.sun.star.uno.UnoRuntime;

public class LoopTag extends AbstractTemplateTag {

	private static final Logger logger = LoggerFactory.getLogger(LoopTag.class);
		
	private OpenOfficeTemplateRenderer template;
	private OpenOfficeUtil ooUtil;
		
	protected String list;
	protected String var;
	
	public LoopTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
		
		this.template = (OpenOfficeTemplateRenderer) templateContext.getTemplate();
		ooUtil = (OpenOfficeUtil) AppContext.getApplicationContext().getBean(OpenOfficeUtil.BEAN_NAME);
	}
	
	@Override
	public String getType() {
		return "loop";
	}
	
	public String getList() {
		return list;
	}
	
	public void setList(String list) {
		this.list = list;
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In loop tag: " + list);
		if (templateContext.isInMetadataSection()) {
			throw new TemplateException("Loop tag cannot be placed in the metadata section.");
		}
		setRenderingAttributes();
		templateContext.getTagStack().push(this);
		try {
			if (!isGeneratingSubTemplate() && evaluating) {
				logger.debug("Evaluating loop tag: " + list);
				//set that this tag is the tag generating the sub template so that tags are not removed or evaluated
				tagGeneratingSubTemplate = this;
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate loop tag - list: " + list, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doEnd() throws TemplateException {
		try {
			if (tagGeneratingSubTemplate == this && evaluating) {
				tagGeneratingSubTemplate = null;
				//first set the start and end tags to null - this removes the loop start and end tags
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY)).setString(null);
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY)).setString(null);
				
				//expand the range of the tag to cover both the start and end tags
				XTextCursor cursor = (XTextCursor) UnoRuntime.queryInterface(XTextCursor.class, (XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY));
				cursor.gotoRange((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY), true);
				
				//get the list from ognl
				Collection coll = (Collection) Ognl.getValue(list, templateContext.getExpressionContext(), templateContext.getExpressionRoot());
				
				//if the list is empty remove the entire section
				if (coll.isEmpty()) {
					cursor.setString(null);
				} else {
					byte[] tempDoc = copySelectionToTempFile(cursor);
					
					Iterator itr = coll.iterator();
					while (itr.hasNext()) {
						Object obj = itr.next();
						templateContext.getExpressionContext().put(var, obj);
						renderLoopTemplate(tempDoc, cursor);
						cursor.collapseToEnd();
					}
				}
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY)).setString(null);
				((XTextRange) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY)).setString(null);
			}
			templateContext.getTagStack().pop();
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate loop tag - list: " + list, e);
		}
	}
	
	private byte[] copySelectionToTempFile(XTextCursor cursor) throws IOException, IllegalArgumentException, Exception {
		//copy the section off prior to rendering it so that we can paste it for each iteration
		logger.debug("Copying loop to temporary file");
		
		//create a new blank document
		OpenOfficeDocument dstDoc = ooUtil.getNewDocument(ooUtil.getDocumentFormat(template.getFileType()));
		
		XController srcController = ((XTextDocument) tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_DOCUMENT_KEY)).getCurrentController();
		XController dstController = dstDoc.getTextDocument().getCurrentController();
		
		//get the cursor for the source document
		XTextViewCursorSupplier srcCursorSupplier = (XTextViewCursorSupplier) UnoRuntime.queryInterface(
				XTextViewCursorSupplier.class, srcController);
		XTextViewCursor sourceCursor = srcCursorSupplier.getViewCursor();
		sourceCursor.gotoRange(cursor, false);
		
		//get the transferable for the source document
		XTransferableSupplier srcTransSupplier = (XTransferableSupplier) UnoRuntime.queryInterface(
				XTransferableSupplier.class, srcController);
		
		XTransferable transferable = srcTransSupplier.getTransferable();
		
		//get the data supplier for the target document
		XTransferableSupplier dstTransSupplier = (XTransferableSupplier) UnoRuntime.queryInterface(
				XTransferableSupplier.class, dstController);
		
		//get the cursor for the target document
		XTextViewCursorSupplier dstCursorSupplier = (XTextViewCursorSupplier) UnoRuntime.queryInterface(
				XTextViewCursorSupplier.class, dstController);
		XTextViewCursor dstCursor = dstCursorSupplier.getViewCursor();
		dstCursor.gotoEnd(true);
		
		dstTransSupplier.insertTransferable(transferable);
		
		byte[] documentBytes = ooUtil.getDocumentBytes(dstDoc, ooUtil.getDocumentFormat(template.getFileType()));
		return documentBytes;
	}
	
	private void renderLoopTemplate(byte[] doc, XTextCursor cursor) throws IllegalArgumentException, com.sun.star.io.IOException, IOException {
		logger.debug("rendering loop section");
		XTextDocument xTextDocument = ooUtil.loadDocument(doc, ooUtil.getDocumentFormat(template.getFileType()));
		OpenOfficeTemplateRenderer tempTemplate = new OpenOfficeTemplateRenderer(xTextDocument, template.getFileType(), templateContext);
		tempTemplate.processTemplate(templateContext);
		XDocumentInsertable insertable = (XDocumentInsertable) UnoRuntime.queryInterface(XDocumentInsertable.class, cursor);
		ooUtil.insertDocument(insertable, ooUtil.getDocumentFormat(templateContext.getTemplate().getFileType()), tempTemplate);
	}
}