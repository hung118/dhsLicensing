package gov.utah.dts.det.ccl.view.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;

import com.opensymphony.xwork2.util.ValueStack;

public class FacilityDetail extends UIBean {

	public static final String TEMPLATE = "facilitydetail";
	
	protected String linkToFacility;
	protected String showDirectors;
	protected String showOwners;
	protected String showLicenseHolder;
	protected String showFacilityType;
	protected String showPhone;
	protected String showAddress;
	
	public FacilityDetail(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
	
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}

    @SuppressWarnings("unchecked")
	@Override
	protected Class getValueClassType() {
        return null; // don't convert nameValue to anything, we need the raw value
    }
	
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		
		if (linkToFacility != null) {
			addParameter("linkToFacility", findValue(linkToFacility, Boolean.class));
		}
		
		if (showDirectors != null) {
			addParameter("showDirectors", findValue(showDirectors, Boolean.class));
		}
		if (showOwners != null) {
			addParameter("showOwners", findValue(showOwners, Boolean.class));
		}
		if (showLicenseHolder != null) {
			addParameter("showLicenseHolder", findValue(showLicenseHolder, Boolean.class));
		}
		if (showFacilityType != null) {
			addParameter("showFacilityType", findValue(showFacilityType, Boolean.class));
		}
		if (showPhone != null) {
			addParameter("showPhone", findValue(showPhone, Boolean.class));
		}
		if (showAddress != null) {
			addParameter("showAddress", findValue(showAddress, Boolean.class));
		}
	}
	
	public void setLinkToFacility(String linkToFacility) {
		this.linkToFacility = linkToFacility;
	}

	public void setShowDirectors(String showDirectors) {
		this.showDirectors = showDirectors;
	}

	public void setShowOwners(String showOwners) {
		this.showOwners = showOwners;
	}

	public void setShowLicenseHolder(String showLicenseHolder) {
		this.showLicenseHolder = showLicenseHolder;
	}

	public void setShowFacilityType(String showFacilityType) {
		this.showFacilityType = showFacilityType;
	}

	public void setShowPhone(String showPhone) {
		this.showPhone = showPhone;
	}

	public void setShowAddress(String showAddress) {
		this.showAddress = showAddress;
	}
}