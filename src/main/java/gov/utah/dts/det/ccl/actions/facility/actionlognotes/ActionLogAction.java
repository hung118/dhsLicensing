package gov.utah.dts.det.ccl.actions.facility.actionlognotes;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.ActionLog;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.ActionLogService;
import gov.utah.dts.det.ccl.service.RegionService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.sort.enums.ActionLogSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Conversion
@Results({
	@Result(name = "input", location = "action_form.jsp"),
	@Result(name = "success", location = "action-log-section", type = "redirectAction", params = {"facilityId", "${facilityId}"})
})
public class ActionLogAction extends BaseFacilityAction implements Preparable {
	
	private ActionLogService actionLogService;
	private UserService userService;
	private RegionService regionService;
	
	private CclListControls lstCtrl;
	
	private ActionLog action;
	private Date actionTime;
	private Date dueDate;
	private Long actionTypeId;
	
	private InputStream inputStream;
	private List<PickListValue> actionTypes;
	private List<Person> actionTakers;
	
	@Override
	public void prepare() {
		//set up the list controls
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ActionLogSortBy.values())));
		lstCtrl.setSortBy(ActionLogSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwelveMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_12_MONTHS);
	}
	
	@Action(value = "action-log-section", results = {
		@Result(name = "success", location = "action_log_section.jsp")
	})
	public String doSection() {
		loadActions();
		return SUCCESS;
	}
	
	@Action(value = "actions-list", results = {
		@Result(name = "success", location = "actions_list.jsp")
	})
	public String doList() {
		loadActions();
		return SUCCESS;
	}
	
	@Action("edit-action")
	public String doEdit() {
		loadActions();
		
		if (action != null && action.getId() != null) {
			action = actionLogService.loadById(action.getId());
		}
		
		if (action == null || action.getActionDate() == null) {
			if (action == null) {
				action = new ActionLog();
			}
			Date now = new Date();
			action.setActionDate(now);
			actionTime = now;
			dueDate = now;
		} else {
			actionTime = action.getActionDate();
		}
		return INPUT;
	}
	
	public void prepareDoSave() {
		if (action != null && action.getId() != null) {
			action = actionLogService.loadById(action.getId());
		}
		
		actionLogService.evict(action);
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "actionTime", message = "Action time is not a valid time. (HH:MM AM/PM)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "actionTime", message = "Action time is required.", shortCircuit = true)
		},
		visitorFields = {
			@VisitorFieldValidator(fieldName = "action", message = "&zwnj;")
		}
	)
	@Action(value = "save-action")
	public String doSave() throws JSONException {
		if (action.getId() == null) {
			action.setFacility(getFacility());
		}
		
		//append the time value onto the actionDate
		updateActionTime();
		
		actionLogService.saveActionLog(action, dueDate);
		
		return SUCCESS;
	}
	
	@Action(value = "delete-action")
	public String doDelete() {
		actionLogService.deleteActionLog(action);
		return SUCCESS;
	}
	
	@Action(value = "get-alerts", results = {
		@Result(name = "success", type = "stream", params = {"contentType", "application/json", "inputName", "inputStream"})
	})
	public String doAlerts() throws UnsupportedEncodingException {
		inputStream = new ByteArrayInputStream(actionLogService.getActionAlertJSON(actionTypeId, getFacility()).getBytes("UTF-8"));
		
		return SUCCESS;
	}
	
	private void loadActions() {
		lstCtrl.setResults(actionLogService.getActionLogsForFacility(getFacility().getId(), null, lstCtrl.getRange(),
				ActionLogSortBy.valueOf(lstCtrl.getSortBy())));
	}
	
	private void updateActionTime() {
		Calendar actionDateCal = Calendar.getInstance();
		actionDateCal.setTime(action.getActionDate());
		
		Calendar actionTimeCal = Calendar.getInstance();
		actionTimeCal.setTime(actionTime);
		
		actionDateCal.set(Calendar.HOUR_OF_DAY, actionTimeCal.get(Calendar.HOUR_OF_DAY));
		actionDateCal.set(Calendar.MINUTE, actionTimeCal.get(Calendar.MINUTE));
		action.setActionDate(actionDateCal.getTime());
	}
	
	public void setActionLogService(ActionLogService actionLogService) {
		this.actionLogService = actionLogService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public ActionLog getAction() {
		return action;
	}
	
	public void setAction(ActionLog action) {
		this.action = action;
	}
	
	@TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.InspectionTimeConverter")
	public Date getActionTime() {
		return actionTime;
	}
	
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	
	@ConversionErrorFieldValidator(message = "Due date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public List<PickListValue> getActionTypes() {
		if (actionTypes == null) {
			actionTypes = pickListService.getValuesForPickList("Current Actions", true);
		}
		return actionTypes;
	}
	
	public List<Person> getActionTakers() {
		if (actionTakers == null) {
			if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) {
				actionTakers = new ArrayList<Person>(userService.getPeople((RoleType) null, false, true, false));
			} else {
				actionTakers = new ArrayList<Person>();
				if (SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER)) {
					actionTakers.addAll(userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING, false, true, false));
				}
				
				Collections.sort(actionTakers);
			}
		}
		return actionTakers;
	}
}