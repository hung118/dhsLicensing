package gov.utah.dts.det.ccl.actions.unlicensedcomplaints;

import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.sort.enums.StatewideUnlicensedComplaintsSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "unlicensed-complaints.statewide-complaints", type = "tiles")
})
public class StatewideUnlicensedComplaintsAction extends ActionSupport implements Preparable {

	private ComplaintService complaintService;
	
	private CclListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(StatewideUnlicensedComplaintsSortBy.values())));
		lstCtrl.setSortBy(StatewideUnlicensedComplaintsSortBy.getDefaultSortBy().name());
		lstCtrl.setResultsPerPage(25);
	}
	
	public String execute() {
		lstCtrl.setResults(complaintService.getStatewideUnlicensedComplaints(lstCtrl.getRange(),
				StatewideUnlicensedComplaintsSortBy.valueOf(lstCtrl.getSortBy()), lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage()));
		lstCtrl.setNumOfResults(complaintService.getStatewideUnlicensedComplaintsCount(lstCtrl.getRange()));
		
		return SUCCESS;
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}