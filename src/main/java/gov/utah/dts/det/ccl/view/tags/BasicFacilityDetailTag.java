package gov.utah.dts.det.ccl.view.tags;

import gov.utah.dts.det.ccl.view.components.BasicFacilityDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

public class BasicFacilityDetailTag extends AbstractUITag {
	
	private static final long serialVersionUID = 8110728209582961053L;
	
	protected String linkToFacility;
	protected String showDirectors;
	protected String showOwners;
	protected String showFacilityType;
	protected String showPhone;
	protected String showAddress;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new BasicFacilityDetail(stack, req, res);
    }
	
	@Override
	protected void populateParams() {
		super.populateParams();
		
		BasicFacilityDetail facilityDetail = ((BasicFacilityDetail) component);
		facilityDetail.setLinkToFacility(linkToFacility);
		facilityDetail.setShowDirectors(showDirectors);
		facilityDetail.setShowOwners(showOwners);
		facilityDetail.setShowFacilityType(showFacilityType);
		facilityDetail.setShowPhone(showPhone);
		facilityDetail.setShowAddress(showAddress);
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