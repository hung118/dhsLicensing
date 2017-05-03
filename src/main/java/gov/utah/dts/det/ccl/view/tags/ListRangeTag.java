package gov.utah.dts.det.ccl.view.tags;

import gov.utah.dts.det.ccl.view.components.ListRange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

public class ListRangeTag extends AbstractUITag {

	private static final long serialVersionUID = -5258482928159082731L;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ListRange(stack, req, res);
    }
	
	@Override
	protected void populateParams() {
		super.populateParams();	
	}
}