package gov.utah.dts.det.view.component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.util.ContainUtil;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("unchecked")
@StrutsTag(name = "listcontrols", tldTagClass = "gov.utah.dts.det.view.tags.ListControlsTag",
		description = "Renders a component that provides lists with the ability to sort and page.", allowDynamicAttributes = true)
public class ListControls extends ClosingUIBean {
	
	public static final Logger logger = LoggerFactory.getLogger(ListControls.class);

	public static final String OPEN_TEMPLATE = "listcontrols";
	public static final String TEMPLATE = "listcontrols-close";
	
	protected String action;
	protected String enctype;
	protected String method;
	protected String namespace;
	protected String acceptcharset;
	
	protected String enablePaging;
	protected String maxPagesToShow;
	protected String showSorter;
	protected String useAjax;
	protected String ajaxTarget;
	protected String paramExcludes;
	
	protected boolean throwExceptionOnNullValueAttribute = true;
	protected boolean processingTagBody = false;
	
	protected Map<String, Object> urlParameters = new LinkedHashMap<String, Object>();
	
	public ListControls(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		
		urlParameters.putAll(request.getParameterMap());
	}

	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}

	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

    public boolean usesBody() {
        return true;
    }
	
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();

		if (action != null) {
			addParameter("action", findString(action));
		}

		if (enctype != null) {
			addParameter("enctype", findString(enctype));
		}

		if (method != null) {
			addParameter("method", findString(method));
		}

		if (namespace != null) {
			addParameter("namespace", findString(namespace));
		}

		if (acceptcharset != null) {
			addParameter("acceptcharset", findString(acceptcharset));
		}
		
		if (enablePaging != null) {
			addParameter("enablePaging", findValue(enablePaging, Boolean.class));
		}
		
		if (maxPagesToShow != null) {
			addParameter("maxPagesToShow", findValue(maxPagesToShow, Integer.class));
		}
		
		if (showSorter != null) {
			addParameter("showSorter", findValue(showSorter, Boolean.class));
		}
		
		if (useAjax != null) {
			addParameter("useAjax", findValue(useAjax, Boolean.class));
		}
		
		if (ajaxTarget != null) {
			addParameter("ajaxTarget", findString(ajaxTarget));
		}
		
		if (paramExcludes != null) {
			addParameter("paramExcludes", findString(paramExcludes));
		}
		
//		addParameter("queryParams", request.getParameterMap());
	}

    public boolean contains(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    @Override
	protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }

    @Override
    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        this.processingTagBody = true;
        return result;
    }

    /**
     * Overrides to be able to render body in a template rather than always before the template
     */
    public boolean end(Writer writer, String body) {
        this.processingTagBody = false;
        evaluateParams();
        try {
            addParameter("body", body);
            addParameter("params", encodeParameters());
            mergeTemplate(writer, buildTemplateName(template, getDefaultTemplate()));
        } catch (Exception e) {
            logger.error("error when rendering", e);
        }
        finally {
            popComponentStack();
        }

        return false;
    }

    public void addParameter(String key, Object value) {
        /*
          the parameters added by this method are used in the template. this method is also
          called by Param to add params into ancestestor. This tag needs to keep both set of parameters
          separated (You gotta keep 'em separated!)
         */
        if (processingTagBody) {
            this.urlParameters.put(key, value);
        } else
            super.addParameter(key, value);
    }

	@Override
    public void addAllParameters(Map params) {
        /*
          the parameters added by this method are used in the template. this method is also
          called by Param to add params into ancestestor. This tag needs to keep both set of parameters
          separated (You gotta keep 'em separated!)
         */
        if (processingTagBody) {
            this.urlParameters.putAll(params);
        } else
            super.addAllParameters(params);
    }
    
    private Map<String, Object> encodeParameters() {
    	Map<String, Object> encodedParams = new HashMap<String, Object>();
    	for (Map.Entry<String, Object> entry : urlParameters.entrySet()) {
    		Object value = null;
    		if (entry.getValue() instanceof Iterable) {
    			value = encodeIterable((Iterable) entry.getValue());
    		} else if (entry.getValue() instanceof Object[]) {
    			value = encodeArray((Object[]) entry.getValue());
    		} else if (entry.getValue() != null) {
//    			value = UrlHelper.translateAndEncode(entry.getValue().toString());
    			value = entry.getValue().toString();
    		}
    		if (value != null) {
    			encodedParams.put(entry.getKey(), value);
    		}
    	}
    	return encodedParams;
    }
    
    private List encodeIterable(Iterable iterable) {
    	List encodedItr = new ArrayList();
    	for (Iterator itr = ((Iterable) iterable).iterator(); itr.hasNext();) {
    		Object val = itr.next();
//    		encodedItr.add(UrlHelper.translateAndEncode(val.toString()));
    		encodedItr.add(val.toString());
    	}
    	return encodedItr;
    }
    
    private Object[] encodeArray(Object[] array) {
    	Object[] encodedArr = new Object[array.length];
    	for (int i = 0; i < encodedArr.length; i++) {
//    		encodedArr[i] = UrlHelper.translateAndEncode(array[i].toString());
    		encodedArr[i] = array[i].toString();
    	}
    	
    	return encodedArr;
    }

    @StrutsTagAttribute(description = "The action to generate the URL for.")
	public void setAction(String action) {
		this.action = action;
	}

    @StrutsTagAttribute(description = "The enctype that will be passed to the underlying form tag.")
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

    @StrutsTagAttribute(description = "Whether this will be a post or a get.")
	public void setMethod(String method) {
		this.method = method;
	}

    @StrutsTagAttribute(description = "The namespace to use")
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

    @StrutsTagAttribute(description="The accepted charsets for this form. The values may be comma or blank delimited.")
	public void setAcceptcharset(String acceptcharset) {
		this.acceptcharset = acceptcharset;
	}

    @StrutsTagAttribute(description="Whether or not this sorter should page results.", type = "Boolean", defaultValue = "false")
	public void setEnablePaging(String enablePaging) {
		this.enablePaging = enablePaging;
	}
	
    @StrutsTagAttribute(description="The maximum number of pages to show on screen before truncating.")
	public void setMaxPagesToShow(String maxPagesToShow) {
		this.maxPagesToShow = maxPagesToShow;
	}
	
    @StrutsTagAttribute(description="Whether or not to show the sorter.", type = "Boolean", defaultValue = "true")
	public void setShowSorter(String showSorter) {
		this.showSorter = showSorter;
	}

    @StrutsTagAttribute(description="Whether or not this sorter should use ajax when sorting and paging.", type = "Boolean", defaultValue = "false")
	public void setUseAjax(String useAjax) {
		this.useAjax = useAjax;
	}

	@StrutsTagAttribute(description="The target element to insert the contents into when using ajax sorting or paging.")
	public void setAjaxTarget(String ajaxTarget) {
		this.ajaxTarget = ajaxTarget;
	}

	@StrutsTagAttribute(description="The parameters from the request that should be excluded from being added as a hidden parameter of the form.")
	public void setParamExcludes(String paramExcludes) {
		this.paramExcludes = paramExcludes;
	}
}