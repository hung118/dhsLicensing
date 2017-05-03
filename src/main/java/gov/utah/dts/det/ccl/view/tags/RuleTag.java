package gov.utah.dts.det.ccl.view.tags;

import gov.utah.dts.det.ccl.view.components.Rule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class RuleTag extends ComponentTagSupport {
	
	private static final long serialVersionUID = 8110728209582961053L;
	
	protected String ruleId;
	protected String format;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Rule(stack);
    }
	
	@Override
	protected void populateParams() {
		super.populateParams();
		
		Rule rule = ((Rule) component);
		rule.setRuleId(ruleId);
		rule.setFormat(format);
	}
	
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
}