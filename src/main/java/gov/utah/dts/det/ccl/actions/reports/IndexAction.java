package gov.utah.dts.det.ccl.actions.reports;

import gov.utah.dts.det.ccl.security.SecurityUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "reports", type = "tiles")
})
public class IndexAction extends ActionSupport {

    private String destination;
    
    @Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@Action(value = "forward-report", 
	    results = {
	        @Result(name = "input", location = "reportBase", type = "tiles")
	    }
    )
	public String doForwardReport() {
	    return INPUT;
	}

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public Long getUserId() {
        return SecurityUtil.getUser().getPerson().getId();
    }

}