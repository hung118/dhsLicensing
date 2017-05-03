package gov.utah.dts.det.ccl.struts2.result;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class JacksonJsonResult extends StrutsResultSupport {

	private static final String CONTENT_TYPE = "application/json";
	
	protected String status;
	protected String objectName;
	
	public JacksonJsonResult() {
		super();
	}
	
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		resolveParamsFromStack(invocation.getStack(), invocation);

		Object responseObject = invocation.getStack().findValue(conditionalParse(objectName, invocation));
		
		int stat = 202;
		if (status != null) {
			stat = Integer.parseInt(conditionalParse(status, invocation));
		}
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
		
		response.setStatus(stat);
		response.setContentType(CONTENT_TYPE);
		
		if (responseObject != null) {
			if (responseObject instanceof JSONObject) {
				((JSONObject) responseObject).write(response.getWriter());
			} else {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
				mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
				mapper.writeValue(response.getWriter(), responseObject);
			}
		}
	}
	
	public String getObjectName() {
		return objectName;
	}
	
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	protected void resolveParamsFromStack(ValueStack stack, ActionInvocation invocation) {
		String status = (String) stack.findString("status");
		if (status != null) {
			setStatus(status);
		}
		
		String objectName = stack.findString("responseObject");
		if (objectName != null) {
			setObjectName(objectName);
		}
	}
}