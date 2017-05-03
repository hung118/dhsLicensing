package gov.utah.dts.det.ccl.actions.includes;

import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@InterceptorRefs({
    @InterceptorRef("internalStack")
})
public class SiteUtilsAction extends ActionSupport {

	public static final String VERSION;
	
	static {
		String version;
		try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("appinfo");
            version = (String) resourceBundle.getObject("program.VERSION");
        } catch (Exception e) {
        	version = "ERROR";
        }
        VERSION = version;
        
	}
	
	private String usersName;
	
	@Action(value = "footer", results = {
		@Result(name = "success", location = "footer.jsp")
	})
	public String doFooter() {
		return SUCCESS;
	}
	
	public String getVersion() {
		return VERSION;
	}
	
	public int getCopyrightYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public String getUsersName() {
		return usersName;
	}
}