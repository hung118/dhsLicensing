package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.actions.facility.inspections.reports.InpsectionChecklistBlankReport;
import gov.utah.dts.det.ccl.actions.facility.inspections.reports.InpsectionChecklistReport;
import gov.utah.dts.det.ccl.actions.facility.inspections.viewHelper.ChecklistViewHelper;
import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.model.InspectionChecklistHeader;
import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.RuleService;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "edit-inspection-record", type = "redirectAction", params = {"facilityId", "${facilityId}", "inspectionId", "${inspectionId}"}),
	@Result(name = "view", location = "inspection_detail.jsp"),
	@Result(name = "input", location = "inspection_form.jsp"),
	@Result(name = "checklistSections", location = "inspection_checklist_sections.jsp"),
	@Result(name = "checklist", location = "inspection_checklist.jsp"),
	@Result(name = "success", location = "view-inspection", type = "redirectAction", params = {"facilityId", "${facilityId}", "inspectionId", "${inspectionId}", "refreshFindings", "${refreshFindings}"})
})
public class InspectionAction extends BaseInspectionEditAction implements Preparable, ParameterAware, SessionAware {
	
	private boolean refreshFindings = false;
	
	private Map<String, Object> responseMap;
	private Map<String, String[]> params;
	private Map<String, Object> session;
	
	private List<String[]> sectionList;
	private List<String[]> checklistHeader;
	
	private List<RuleSection> ruleSectionList;
	private ChecklistViewHelper checklistView;
	
	private List<PickListValue> programTypes;
	
	@Autowired
	private RuleService ruleService;

	@Autowired
	private PickListService pickListService;

	@Override
	public void prepare() throws Exception {
		
	}
	
	public ChecklistViewHelper getChecklistView() {
		return checklistView;
	}

	public void setChecklistView(ChecklistViewHelper checklistView) {
		this.checklistView = checklistView;
	}

	public boolean isRefreshFindings() {
		return refreshFindings;
	}
	
	public void setRefreshFindings(boolean refreshFindings) {
		this.refreshFindings = refreshFindings;
	}
	
	public Map<String, Object> getResponse() {
		return responseMap;
	}
	
	public List<String[]> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<String[]> sectionList) {
		this.sectionList = sectionList;
	}
	
	public List<RuleSection> getRuleSectionList() {
		return this.ruleSectionList;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		params = parameters;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@SkipValidation
	@Action(value = "edit-inspection-record", results = {
		@Result(name = "success", location = "inspection_base.jsp")
	})
	public String doEditRecord() {
		loadInspection();
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "view-inspection")
	public String doView() {
		loadInspection();
		inspection.getInspectionChecklist().size();
		return VIEW;
	}
	
	@SkipValidation
	@Action(value = "edit-inspection")
	public String doEdit() {
		loadInspection();
		/* CKS [Jun 6, 2013] The edit screen bombs in certain situations (RM 23835) b/c the inspection types 
		 * are null when the screen builds. So I added this call.  
		 */
		getInspectionTypes();  
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadInspection();
		nonPrimaryTypes.clear();
		inspection.getFindings().size();
		inspection.getFollowUps().size();
		inspectionService.evict(inspection);
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "inspection.license", message = "License is required."),
			@RequiredFieldValidator(fieldName = "primarySpecialist", message = "Primary specialist is required."),
			@RequiredFieldValidator(fieldName = "primaryType", message = "Primary inspection type is required.")
		},
		visitorFields = {
			@VisitorFieldValidator(fieldName = "inspection", message = "&zwnj;")
		},
		requiredStrings = {
				@RequiredStringValidator(fieldName = "inspection.findingsComment", message = "Findings are required.")
		}
	)
	@Action(value = "save-inspection")
	public String doSave() {
		updateInspection();
		inspectionService.saveInspection(inspection);
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
	}
	
	public void prepareDoSetCmpDueDate() {
		loadInspection();
	}
	
	@SkipValidation
	@Action(value = "set-cmp-due-date")
	public String doSetCmpDueDate() {
		inspectionService.saveInspection(inspection);
		return REDIRECT_VIEW;
	}

	@SkipValidation
	@Action(value = "inspection-checklist-sections")
	public String showChecklistSections() {
		sectionList = ruleService.getSectionTitles("501");
		return "checklistSections";
	}

	@SkipValidation
	@Action(value = "inspection-checklist-build")
	public String buildChecklist() {
		
		loadInspection();
		
		// get the sections the user selected
		Vector<Integer> selections = new Vector<Integer>();
		for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
			String p = iterator.next();
			if (p.startsWith("section_")) { 
				String v = params.get(p)[0];
				selections.add(new Integer(v.substring(8)));
			}
		}
		
		if (selections.size() < 1) {
			return showChecklistSections();
		}
		
		// build the list of items 
		ruleSectionList = ruleService.getSections(selections);
		checklistHeader = inspectionService.getChecklistHeader(inspection);
		programTypes = pickListService.getValuesForPickList("Program Type", true);
		
		session.put("ruleSectionList", ruleSectionList);
		session.put("checklistHeader", checklistHeader);
		
		return "checklist";
	}
	
	@SuppressWarnings("unchecked")
	@SkipValidation
	@Action(value = "inspection-checklist-save", results = {
			@Result(name = "success", location = "inspection_base.jsp")
		})
	public String saveChecklist() {
		
		loadInspection();

		if (ruleSectionList == null) {
			ruleSectionList = (List<RuleSection>) session.get("ruleSectionList");
		}
		if (checklistHeader == null) {
			checklistHeader = (List<String[]>) session.get("checklistHeader");
		}
		
		InspectionChecklist checkList = new InspectionChecklist();
		if (params.get("comments") != null) 
			checkList.setComments(params.get("comments")[0]);
		// refresh the bean so it's in the current context...
		checkList.setInspection(inspection);
		checkList.setResultDate(new java.util.Date());
		checkList.setUser(SecurityUtil.getUser());
		
		/*
		 * Get the header data
		 */
		InspectionChecklistHeader headerItem = null;
		for (Iterator<String[]> iterator2 = checklistHeader.iterator(); iterator2.hasNext();) {
			headerItem = new InspectionChecklistHeader();
			String[] type =  iterator2.next();
			headerItem.setItemName(type[0]);
			headerItem.setItemLabel(type[1]);
			if (params.get("header_"+type[0]) != null) { 
				headerItem.setItemValue(params.get("header_"+type[0])[0]);
			}
			checkList.addHeader(headerItem);
		}
		
		/*
		 * Doing it this way will maintain the order that was shown on the screen
		 */
		int cnt = 0;
		for (RuleSection sec : ruleSectionList) {
			if (sec.isActive()) {
				if (sec.getSubSectionCount() > 0) {
					for (RuleSubSection sub : sec.getSubSections()) {
						if (sub.getCategory().getCharacter() == 'A') {
							InspectionChecklistResult icr = new InspectionChecklistResult();
							if (params.get("subCmt_"+sub.getId()) != null) 
								icr.setComments(params.get("subCmt_"+sub.getId())[0]);

							icr.setResult(params.get("sub_"+sub.getId())[0]);
							// refresh the bean so it's in the current context...
							sub = (RuleSubSection) inspectionService.getEntity(RuleSubSection.class, sub.getId());
							icr.setSubSection(sub);
							icr.setSortOrder(cnt++);
							checkList.addResult(icr);
						}
					}
				} else {
					InspectionChecklistResult icr = new InspectionChecklistResult();
					if (params.get("secCmt_"+sec.getId()) != null) 
						icr.setComments(params.get("secCmt_"+sec.getId())[0]);
					icr.setResult(params.get("sec_"+sec.getId())[0]);
					// refresh the bean so it's in the current context...
					sec = (RuleSection) inspectionService.getEntity(RuleSection.class, sec.getId());
					icr.setSection(sec);
					icr.setSortOrder(cnt++);
					checkList.addResult(icr);
				}
			}
		}

		inspectionService.saveCheckList(checkList);
		 
		inspectionService.refresh(inspection);
		
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "inspection-checklist-update", results = {
			@Result(name = "success", location = "inspection_base.jsp")
		})
	public String updateChecklist() {
		
		loadInspection();
		
		long checklistId = Long.parseLong(params.get("checklistId")[0]);
		
		InspectionChecklist checkList = inspection.getCheckList(checklistId);
		
		/*
		 * Get the header data
		 */
		for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
			String p = iter.next();
			if (p.startsWith("header_")) {
				String h = p.substring(7);
				InspectionChecklistHeader headerItem = null;
				// find the header item to update 
				for (Iterator<InspectionChecklistHeader> iter2 = checkList.getHeaders().iterator(); iter2.hasNext();) {
					InspectionChecklistHeader item = iter2.next();
					if (h.equals(item.getItemName())) {
						headerItem = item;
						break;
					}
				}
				headerItem.setItemName(h);
				if (params.get(p) != null) { 
					headerItem.setItemValue(params.get(p)[0]);
				}
			}
		}
		
		for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
			String p = iterator.next();
			long targetId = 0;
			String targetCmt = null;
			String targetResult = null;
			if (p.startsWith("icrSub_")) {
				targetId = Long.parseLong(p.substring(7));
				targetResult = params.get(p)[0];
				if (params.get("icrSubCmt_"+targetId) != null) { 
					targetCmt = params.get("icrSubCmt_"+targetId)[0];
				}
			} else if (p.startsWith("icrSec_")) {
				targetId = Long.parseLong(p.substring(7));
				targetResult = params.get(p)[0];
				if (params.get("icrSecCmt_"+targetId) != null) { 
					targetCmt = params.get("icrSecCmt_"+targetId)[0];
				}
			}
			if (targetId > 0 && targetCmt != null) {
				// now find the ICR bean and set the properties
				InspectionChecklistResult icr = null;
				for (Iterator<InspectionChecklistResult> iterator2 = checkList.getResults().iterator(); iterator2.hasNext();) {
					InspectionChecklistResult bean = iterator2.next();
					if (bean.getId().longValue() == targetId) {
						icr = bean;
						break;
					}
				}
				icr.setResult(targetResult);
				icr.setComments(targetCmt);
			}
		}
		
		
		if (params.get("comments") != null) 
			checkList.setComments(params.get("comments")[0]);
		checkList.setUser(SecurityUtil.getUser());
		
		inspectionService.saveCheckList(checkList);

		return VIEW;
	}

	@SuppressWarnings("unchecked")
	@SkipValidation
	@Action(value = "inspection-checklist-view", results = {
			@Result(name = "success", location = "inspection_checklist_view.jsp")
		})
	public String viewChecklist() {
		
		if (super.getInspectionId() == null) {
			if (params.get("objectId") != null) {
				super.setInspectionId(new Long(params.get("objectId")[0]));
			}
		}
		
		loadInspection();
		
		/* CKSmith:Jan 24, 2013 
		 * 	The session related stuff is here b/c of a problem that I couldn't easily solve:
		 * 	when the user attempts to upload the checklist file (see UploadAction.doSave) if the
		 * 	interceptor based validation (file type and size) encountered errors the errors
		 * 	were not carried over to the redirect that re-displays the checklist screen ...
		 * 	so the user wouldn't know why they upload didn't work.  So this is a HACK!!!...
		 */
		long checklistId = 0;
		if (params.get("checklistId") != null && params.get("checklistId")[0] != null && !"".equals(params.get("checklistId")[0])) {
			checklistId = Long.parseLong(params.get("checklistId")[0]);	
			session.put("checklistId", checklistId);
		} else {
			checklistId = (Long)session.get("checklistId");
			if (session.get("checklistFileErrors") != null) {
				Map<String, String> errors = (Map<String, String>) session.get("checklistFileErrors");
				for (Iterator<String> iterator = errors.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					super.addFieldError(key, errors.get(key));
				}
				session.remove("checklistFileErrors");
				session.remove("checklistId");
			}
		}
		
		programTypes = pickListService.getValuesForPickList("Program Type", true);
		
		InspectionChecklist checkList = inspection.getCheckList(checklistId);
		
		checklistView = new ChecklistViewHelper(checkList, false);
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "inspection-checklist-delete")
	public String deleteChecklist() {
		
		if (super.getInspectionId() == null) {
			if (params.get("objectId") != null) {
				super.setInspectionId(new Long(params.get("objectId")[0]));
			}
		}
		
		loadInspection();
		
		long checklistId = Long.parseLong(params.get("checklistId")[0]);

		InspectionChecklist checkList = inspection.getCheckList(checklistId);

		inspectionService.deleteChecklist(checkList);
		
		loadInspection();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@SkipValidation
	@Action(value = "inspection-checklist-blank-print")
	public String printBlankChecklist() {
		
		if (super.getInspectionId() == null) {
			if (params.get("objectId") != null) {
				super.setInspectionId(new Long(params.get("objectId")[0]));
			}
		}
		
		loadInspection();
		
		if (ruleSectionList == null) {
			ruleSectionList = (List<RuleSection>) session.get("ruleSectionList");
		}
		if (checklistHeader == null) {
			checklistHeader = (List<String[]>) session.get("checklistHeader");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		ByteArrayOutputStream ba = null;
		try {
	       	// Do the notification letter generation processing.
			ba = (new InpsectionChecklistBlankReport()).generate(checklistHeader, ruleSectionList);
			if (ba != null) {
				String filename = "InspectionChecklist_"+sdf.format(new java.util.Date())+".pdf";
				response.setContentType("application/pdf");
				response.setContentLength(ba.size());
				response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				ServletOutputStream out = response.getOutputStream();
				ba.writeTo(out);
				out.flush();
				
				return null;
			} else {
				addActionError("An error occurred while generating the document.");
			}
		} catch (Exception e) {
			addActionError("An error occurred while generating the document.");
		}
		return INPUT;
	}
		
	@SkipValidation
	@Action(value = "inspection-checklist-print")
	public String printChecklist() {

		if (super.getInspectionId() == null) {
			if (params.get("objectId") != null) {
				super.setInspectionId(new Long(params.get("objectId")[0]));
			}
		}
		
		loadInspection();
		
		long checklistId = Long.parseLong(params.get("checklistId")[0]);

		InspectionChecklist checkList = inspection.getCheckList(checklistId);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		ByteArrayOutputStream ba = null;
		try {
	       	// Do the notification letter generation processing.
			InpsectionChecklistReport checkListReport = new InpsectionChecklistReport();
			checkListReport.setPickListService(pickListService);
			ba = checkListReport.generate(checkList);
			if (ba != null) {
				String filename = "InspectionChecklist_"+sdf.format(new java.util.Date())+".pdf";
				response.setContentType("application/pdf");
				response.setContentLength(ba.size());
				response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				ServletOutputStream out = response.getOutputStream();
				ba.writeTo(out);
				out.flush();
				
				return null;
			} else {
				addActionError("An error occurred while generating the document.");
			}
		} catch (Exception e) {
			addActionError("An error occurred while generating the document.");
		}
		return INPUT;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public List<String[]> getChecklistHeader() {
		return checklistHeader;
	}

	public void setChecklistHeader(List<String[]> checklistHeader) {
		this.checklistHeader = checklistHeader;
	}

	public PickListService getPickListService() {
		return pickListService;
	}

	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}

	public List<PickListValue> getProgramTypes() {
		return programTypes;
	}

	public void setProgramTypes(List<PickListValue> programTypes) {
		this.programTypes = programTypes;
	}
}