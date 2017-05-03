package gov.utah.dts.det.ccl.view.tags;

import gov.utah.dts.det.ccl.view.components.Address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

public class AddressTag extends AbstractUITag {

	private static final long serialVersionUID = -9068968551151007990L;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Address(stack, req, res);
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
	}
}