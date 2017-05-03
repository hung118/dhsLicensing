package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.tags.TagParser;
import gov.utah.dts.det.filemanager.model.FileType;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.star.beans.XPropertySet;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.util.XSearchDescriptor;
import com.sun.star.util.XSearchable;

public class OpenOfficeTemplateRenderer extends OpenOfficeDocument {
	
	public static final String CONTEXT_DOCUMENT_KEY = "document";
	public static final String CONTEXT_RANGE_KEY = "range";
	public static final String CONTEXT_START_RANGE_KEY = "start-range";
	public static final String CONTEXT_END_RANGE_KEY = "end-range";
	public static final String CONTEXT_PROMPT_KEY = "prompts";
	
	private static final String TAG_REGEX = "(<[a-zA-Z]+([:space:]+[a-zA-Z]+[:space:]?=[:space:]?(\\x201C|\\x201D|\")[^\\x201C|\\x201D|\"]*(\\x201C|\\x201D|\"))*/?>)|(</[a-zA-Z]+>)";
//	private static final String PROMPT_REGEX = "(<prompt(list)?([:space:]+[a-zA-Z]+[:space:]?=[:space:]?(\\x201C|\\x201D|\")[^\\x201C|\\x201D|\"]*(\\x201C|\\x201D|\"))*/?>)|(</prompt(list)?>)";
	
	private DataSourceManager dataSourceManager;
	private TemplateContext templateContext;
	
	private XSearchable search;
	private XSearchDescriptor searchDesc;
	
	/**
	 * Create a new template from the given document.
	 * 
	 * @param document
	 * @param elContext
	 * @param root
	 */
	public OpenOfficeTemplateRenderer(InputStream inputStream, FileType fileType, DataSourceManager dataSourceManager, Map<String, Object> elContext, Object root) throws DocumentException {
		super(inputStream, fileType);
		
		this.dataSourceManager = dataSourceManager;
		
		if (elContext == null) {
			elContext = new HashMap<String, Object>();
		}
		
		templateContext = new TemplateContext(this, elContext, root);
	}
	
	public OpenOfficeTemplateRenderer(XTextDocument xTextDocument, FileType fileType, TemplateContext templateContext) throws DocumentException {
		super(xTextDocument, fileType);
		this.templateContext = templateContext;
	}
	
	public List<Input> getInputs(Map<String, Object> documentContext) throws TemplateException {
//		try {
			templateContext.setScanPhase(true);
			
			//add context variables to the expression context and as hidden inputs
			for (Entry<String, Object> entry : documentContext.entrySet()) {
				if (entry.getKey().startsWith("doc-ctx.")) {
					templateContext.getExpressionContext().put(entry.getKey(), entry.getValue());
					Input input = new Input(entry.getKey(), null, entry.getValue(), String.class, true, InputDisplayType.HIDDEN);
					templateContext.getInputs().add(input);
				}
			}
//			try {
			XTextRange tag = getNextTag(null);
			while (tag != null) {
				Map<String, Object> tagContext = new HashMap<String, Object>();
				tagContext.put(CONTEXT_DOCUMENT_KEY, xTextDocument);
				tagContext.put(CONTEXT_RANGE_KEY, tag);
				
				TagParser.parseTag(templateContext, tagContext, tag.getString());
				if (!templateContext.isScanPhase()) {
					break;
				}
				tag = getNextTag(tag.getEnd());
			}
//			} catch (Exception e) {
//				throw new TemplateException("Unable to render template!", e);
//			}
			
			return templateContext.getInputs();
//		} catch (Exception e) {
//			throw new TemplateException("Unable to get prompt fields from template!", e);
//		}
	}
	
	public void render() throws TemplateException {
		templateContext.setScanPhase(false);
		processTemplate(templateContext);
	}

	public void processTemplate(TemplateContext templateContext) throws TemplateException {
//		try {
		XTextRange tag = getNextTag(null);
		while (tag != null) {
			Map<String, Object> tagContext = new HashMap<String, Object>();
			tagContext.put(CONTEXT_DOCUMENT_KEY, xTextDocument);
			tagContext.put(CONTEXT_RANGE_KEY, tag);
			
			TagParser.parseTag(templateContext, tagContext, tag.getString());
			tag = getNextTag(tag.getEnd());
		}
//		} catch (Exception e) {
//			throw new TemplateException("Unable to render template!", e);
//		}
	}
	
	public XTextRange getNextTag(XTextRange startPosition) throws TemplateException {
		try {
			if (search == null) {
				search = (XSearchable) UnoRuntime.queryInterface(XSearchable.class, xTextDocument);
				searchDesc = search.createSearchDescriptor();
				
				XPropertySet searchProps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, searchDesc);
				searchProps.setPropertyValue("SearchRegularExpression", Boolean.TRUE);
				searchDesc.setSearchString(TAG_REGEX);
			}
			XInterface xInterface = null;
			if (startPosition == null) {
				xInterface = (XInterface) search.findFirst(searchDesc);
			} else {
				xInterface = (XInterface) search.findNext(startPosition, searchDesc);
			}
			if (xInterface != null) {
				return (XTextRange) UnoRuntime.queryInterface(XTextRange.class, xInterface);	
			}
		
			return null;
		} catch (Exception e) {
			throw new TemplateException("Unable to process template tag.", e);
		}
	}
	
	public DataSource getDataSource(String key) {
		return dataSourceManager.getDataSource(key);
	}
}