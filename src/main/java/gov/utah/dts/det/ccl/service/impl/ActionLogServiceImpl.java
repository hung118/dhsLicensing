package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.ActionLogDao;
import gov.utah.dts.det.ccl.dao.PersonDao;
import gov.utah.dts.det.ccl.model.ActionLog;
import gov.utah.dts.det.ccl.model.Alert;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.ActionLogService;
import gov.utah.dts.det.ccl.service.AlertService;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.service.ApplicationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ognl.Ognl;
import ognl.OgnlException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("actionLogService")
public class ActionLogServiceImpl implements ActionLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(ActionLogServiceImpl.class);

	@Autowired
	private SecurityService securityService;

	@Autowired
	private AlertService alertService;
	
	@Autowired
	private ActionLogDao actionLogDao;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PersonDao personDao;
	
	private static final Pattern OGNL_EXPRESSION = Pattern.compile("\\$\\{.*?\\}\\$");
	
	@Override
	public ActionLog loadById(Long id) {
		ActionLog actionLog = actionLogDao.load(id);
		securityService.setEditableFlag(actionLog);
		return actionLog;
	}
	
	@Override
	public ActionLog saveActionLog(ActionLog actionLog, Date dueDate) {
		ActionLog action = actionLogDao.save(actionLog);
		
		try {
			//determine if an alert is required for this action
			ApplicationProperty prop = applicationService.findApplicationPropertyByKey("facility.action.type-" + action.getActionType().getId() + ".alert");
			if (prop != null) {
				//the property's value is a json object - parse it to see who it should go to and what the alert should say
				JSONTokener tokener = new JSONTokener(prop.getValue());
				JSONObject alertsJSON = new JSONObject(tokener);
				JSONArray alertsArray = alertsJSON.getJSONArray("alerts");
				for (int i = 0; i < alertsArray.length(); i++) {
					//get the alert
					JSONObject alertJson = alertsArray.getJSONObject(i);
					
					String type = alertJson.getString("type");
					String alert = evaluateExpressions(alertJson.getString("alert"), actionLog.getFacility());
					
					if ("facility".equalsIgnoreCase(type)) {
						JSONArray rolesArray = alertJson.getJSONArray("roles");
						for (int j = 0; j < rolesArray.length(); j++) {
							String role = rolesArray.getString(j);
							Alert al = new Alert();
							al.setType("FACILITY_" + role);
							al.setObjectId(action.getFacility().getId());
							al.setAlert(alert);
							al.setAlertDate(action.getActionDate());
							al.setActionDueDate(dueDate);
							alertService.saveAlert(al);
						}
					} else if ("role".equalsIgnoreCase(type)) {
						JSONArray rolesArray = alertJson.getJSONArray("roles");
						List<RoleType> roles = new ArrayList<RoleType>();
						for (int j = 0; j < rolesArray.length(); j++) {
							roles.add(RoleType.valueOf(rolesArray.getString(j)));
						}
						alertService.sendAlertToRole(roles, alert, action.getActionDate(), dueDate);
					} else if ("person".equalsIgnoreCase(type)) {
						JSONArray peopleJson = alertJson.getJSONArray("people");
						List<Long> personIds = new ArrayList<Long>();
						for (int j = 0; j < peopleJson.length(); j++) {
							personIds.add(peopleJson.getLong(j));
						}
						alertService.sendAlertToPeople(personIds, alert, action.getActionDate(), dueDate);
					} else if ("self".equalsIgnoreCase(type)) {
						List<Long> personIds = new ArrayList<Long>();
						personIds.add(SecurityUtil.getUser().getPerson().getId());
						alertService.sendAlertToPeople(personIds, alert, action.getActionDate(), dueDate);
					}
				}
			}
		} catch (JSONException je) {
			throw new RuntimeException("Unable to create alert for action log entry!", je);
		}
				
		return action;
	}
	
	@Override
	public void deleteActionLog(ActionLog actionLog) {
		actionLogDao.delete(actionLog);
	}
	
	@Override
	public List<ActionLog> getActionLogsForFacility(Long facilityId, PickListValue actionType, ListRange listRange, SortBy sortBy) {
		List<ActionLog> actions = actionLogDao.getActionLogsForFacility(facilityId, actionType, listRange, sortBy);
		securityService.setEditableFlag(actions);
		return actions;
	}
	
	@Override
	public String getActionAlertJSON(Long actionId, Facility facility) {
		if (actionId != null) {
			try {
				ApplicationProperty prop = applicationService.findApplicationPropertyByKey("facility.action.type-" + actionId + ".alert");
				if (prop != null) {
					//all we want to do is replace the role type enumeration names with their labels instead
					JSONTokener tokener = new JSONTokener(prop.getValue());
					JSONObject alertJSON = new JSONObject(tokener);
					JSONArray alertsArray = alertJSON.getJSONArray("alerts");
					for (int i = 0; i < alertsArray.length(); i++) {
						JSONObject alert = alertsArray.getJSONObject(i);
						//evaluate expressions in the alert text
						alert.put("alert", evaluateExpressions(alert.getString("alert"), facility));
						
						//convert the roles in the array into their display name instead of the enum name
						if (alert.has("roles")) {
							JSONArray rolesArray = alert.getJSONArray("roles");
							for (int j = 0; j < rolesArray.length(); j++) {
								rolesArray.put(j, RoleType.valueOf(rolesArray.getString(j)).getDisplayName());
							}
						}
						//if there are personIds, convert them into the people names instead of their ids
						if (alert.has("people")) {
							JSONArray peopleArray = alert.getJSONArray("people");
							for (int j = 0; j < peopleArray.length(); j++) {
								Person p = personDao.load(peopleArray.getLong(j));
								peopleArray.put(j, p.getFirstAndLastName());
							}
						}
					}
					
					return alertJSON.toString();
				}
			} catch (Exception je) {
				logger.error("Unable to load alert!", je);
			}
		}
		
		return "{\"alerts\": []}";
	}
	
	@Override
	public void evict(final Object entity) {
		actionLogDao.evict(entity);
	}
	
	private static String evaluateExpressions(String input, Object root) {
		StringBuffer outputBuf = new StringBuffer();
		try {
			Matcher m = OGNL_EXPRESSION.matcher(input);
			while (m.find()) {
				String expression = input.substring(m.start() + 2, m.end() - 2);
				String value = Ognl.getValue(expression, root).toString();
				m.appendReplacement(outputBuf, value);
			}
			m.appendTail(outputBuf);
		} catch (OgnlException oe) {
			logger.error("Unable to evaluate expression!", oe);
			outputBuf.replace(0, outputBuf.length() - 1, input);
		}
		return outputBuf.toString();
	}
}