package gov.utah.dts.det.ccl.view.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.ContainUtil;

import com.opensymphony.xwork2.util.ValueStack;

public class ListRange extends UIBean {

	public static final String TEMPLATE = "listrange";
	
	public ListRange(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
	
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
	}

    public boolean contains(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    @SuppressWarnings("unchecked")
	protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }
}