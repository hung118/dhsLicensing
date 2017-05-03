package gov.utah.dts.det.view.tags;

import gov.utah.dts.det.view.component.ListControls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

import com.opensymphony.xwork2.util.ValueStack;

public class ListControlsTag extends AbstractClosingTag {

	private static final long serialVersionUID = 7035808585971844321L;
	
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
	
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new ListControls(stack, req, res);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		
		ListControls listControls = ((ListControls) component);
		listControls.setAction(action);
		listControls.setEnctype(enctype);
		listControls.setMethod(method);
		listControls.setNamespace(namespace);
		listControls.setAcceptcharset(acceptcharset);
		listControls.setEnablePaging(enablePaging);
		listControls.setMaxPagesToShow(maxPagesToShow);
		listControls.setShowSorter(showSorter);
		listControls.setUseAjax(useAjax);
		listControls.setAjaxTarget(ajaxTarget);
		listControls.setParamExcludes(paramExcludes);
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setAcceptcharset(String acceptcharset) {
		this.acceptcharset = acceptcharset;
	}

	public void setEnablePaging(String enablePaging) {
		this.enablePaging = enablePaging;
	}
	
	public void setMaxPagesToShow(String maxPagesToShow) {
		this.maxPagesToShow = maxPagesToShow;
	}
	
	public void setShowSorter(String showSorter) {
		this.showSorter = showSorter;
	}

	public void setUseAjax(String useAjax) {
		this.useAjax = useAjax;
	}

	public void setAjaxTarget(String ajaxTarget) {
		this.ajaxTarget = ajaxTarget;
	}
	
	public void setParamExcludes(String paramExcludes) {
		this.paramExcludes = paramExcludes;
	}
}