package gov.utah.dts.det.ccl.actions.facility.information.board;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.query.ListControls;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "board-members-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "board_member_form.jsp"),
	@Result(name = "view", location = "board_members_list.jsp")
})
public class BoardMembersAction extends BaseFacilityAction implements Preparable {

	private FacilityPerson boardMember;
	
	private ListControls lstCtrl = new ListControls();
		
	private JsonResponse response;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
		if (boardMember != null && boardMember.getId() != null) {
			boardMember = facilityService.loadFacilityBoardMember(getFacility().getId(), boardMember.getId(), getBoardMemberPersonTypeId());
		}
	}
	
	@Action(value = "board-members-list")
	public String doList() {
		loadBoardMembers();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "edit-board-member")
	public String doEdit() {
		loadBoardMembers();
		loadBoardMember();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadBoardMembers();
		loadBoardMember();
		facilityService.evict(boardMember);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "boardMember", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "boardMember", message = "Start date must be before End date")
		}
	)
	@Action(value = "save-board-member")
	public String doSave() {		
		if (boardMember.getId() == null) {
			boardMember.setFacility(getFacility());
		}
			
		facilityService.saveBoardMember(boardMember);
		return REDIRECT_VIEW;
	}

	private void loadBoardMember() {
		if (boardMember != null && boardMember.getId() != null) {
			boardMember = facilityService.loadFacilityBoardMember(getFacility().getId(), boardMember.getId(), getBoardMemberPersonTypeId());
		}
	}
	
	private void loadBoardMembers() {
		lstCtrl.setResults(facilityService.getBoardMembers(getFacility().getId()));
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public FacilityPerson getBoardMember() {
		return boardMember;
	}
	
	public void setBoardMember(FacilityPerson boardMember) {
		this.boardMember = boardMember;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public JsonResponse getResponse() {
		return response;
	}

	public void setResponse(JsonResponse response) {
		this.response = response;
	}

	private Long getBoardMemberPersonTypeId() {
		return facilityService.getBoardMemberPersonType().getId();
	}

}