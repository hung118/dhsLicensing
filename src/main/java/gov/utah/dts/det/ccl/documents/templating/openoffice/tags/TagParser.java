package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;

import java.lang.reflect.Method;
import java.util.Deque;
import java.util.Map;

public class TagParser {
	
	@SuppressWarnings("unchecked")
	public static void parseTag(TemplateContext templateContext, Map<String, Object> tagContext, String text) {
		String tagText = text.trim();
		tagText = tagText.replaceAll("\u201C|\u201D|\"", "\"");
		
		//determine the tag type
		boolean isEndTag = false;
		boolean isClosedTag = false;
		
		//check for valid tag syntax
		if (tagText.charAt(0) != '<') {
			throw new TemplateException(text + " is not a valid tag.  It must start with an angle bracket");
		}
		if (tagText.charAt(1) == '/') {
			isEndTag = true;
		}
		
		if (tagText.charAt(tagText.length() - 1) != '>') {
			throw new TemplateException(text + " is not a valid tag.  It must end with an angle bracket");
		}
		if (tagText.charAt(tagText.length() - 2) == '/') {
			isClosedTag = true;
		}
		
		//determine the tag type
		int start = 0;
		int end = 0;
		String tagType = null;
		if (isEndTag) {
			start = 2;
			end = tagText.length() - 1;
		} else {
			start = 1;
			end = tagText.indexOf(" ");
			if (end == -1) {
				end = isClosedTag ? tagText.length() - 2 : tagText.length() - 1;
			}
		}
		tagType = tagText.substring(start, end);
		
		if (isEndTag) {
			//beginning tag should be on the top of the template context stack
			Deque<TemplateTag> stack = templateContext.getTagStack();
			TemplateTag stackTop = stack.peek();
			if (!stackTop.getType().equals(tagType)) {
				throw new TemplateException("Closing " + tagType + " tag does not have an opening tag");
			}
			
			closeTag(stackTop, tagContext);
			
			stackTop.doEnd();
			
			return;
		}
		
		TemplateTag tag = getTagOfType(tagType, templateContext, tagContext);
		
		start = end;
		//get all tag attributes
		while (start < tagText.length() && ('/' != tagText.charAt(start) && '>' != tagText.charAt(start))) {
			end = tagText.indexOf("=", start);
			StringBuilder name = new StringBuilder(tagText.substring(start, end).trim());
			start = end;
			char startChar = tagText.charAt(start + 1);
			start = end + 2;
			end = tagText.indexOf(startChar, start);
			String value = tagText.substring(start, end);
			start = end + 1;
			
			Class clazz = tag.getClass();
			//uppercase first letter so we can construct a getter
			char upperFirstLetter = Character.toUpperCase(name.charAt(0));
			name.replace(0, 1, Character.toString(upperFirstLetter));
			name.insert(0, "set");
			
			try {
				Method setter = clazz.getMethod(name.toString(), String.class);
				setter.invoke(tag, value);
			} catch (NoSuchMethodException nsme) {
				throw new TemplateException("Unknown property " + name.toString() + " of " + tagType + " tag");
			} catch (Exception e) {
				throw new TemplateException("Unable to set property " + name.toString() + " of " + tagType + " tag");
			}
		}

		tag.doStart();
		
		if (isClosedTag) {
			tag.doEnd();
		}
	}
	
	private static TemplateTag getTagOfType(String type, TemplateContext templateContext, Map<String, Object> tagContext) throws TemplateException {
		tagContext.put(OpenOfficeTemplateRenderer.CONTEXT_START_RANGE_KEY, tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_RANGE_KEY));
		TemplateTag tag = null;
		//load the correct tag
		if ("out".equalsIgnoreCase(type)) {
			tag = new OutTag(templateContext, tagContext);
		} else if ("if".equalsIgnoreCase(type)) {
			tag = new IfTag(templateContext, tagContext);
		} else if ("loop".equalsIgnoreCase(type)) {
			tag = new LoopTag(templateContext, tagContext);
		} else if ("file".equalsIgnoreCase(type)) {
			tag = new FileTag(templateContext, tagContext);
		} else if ("input".equalsIgnoreCase(type)) {
			tag = new InputTag(templateContext, tagContext);
		} else if ("set".equalsIgnoreCase(type)) {
			tag = new SetTag(templateContext, tagContext);
		} else if ("metadata".equalsIgnoreCase(type)) {
			tag = new MetadataTag(templateContext, tagContext);
		} else if ("metaattr".equalsIgnoreCase(type)) {
			tag = new MetaAttributeTag(templateContext, tagContext);
		} else if ("datasource".equalsIgnoreCase(type)) {
			tag = new DataSourceTag(templateContext, tagContext);
		}
		if (tag == null) {
			throw new TemplateException("Unsupported tag type: " + type);
		}
		return tag;
	}
	
	private static void closeTag(TemplateTag tag, Map<String, Object> tagContext) throws TemplateException {
		tag.getTagContext().put(OpenOfficeTemplateRenderer.CONTEXT_END_RANGE_KEY, tagContext.get(OpenOfficeTemplateRenderer.CONTEXT_RANGE_KEY));
	}
}