package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.service.NoncompliancePointsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/noncompliance")
@Results({
	@Result(name = "input", location = "ncpoints_form.jsp"),
	@Result(name = "success", location = "edit-points", type = "redirectAction")
})
public class NoncompliancePointsAction extends ActionSupport implements Preparable, ParameterAware {

	private Map<String, String[]> parameterMap;
	
	private PickListService pickListService;
	private NoncompliancePointsService noncompliancePointsService;
	
	private Map<String, Integer> pointsToSave;
	private Map<String, String> noncompliancePoints;
	
	private List<PickListValue> findingsCategories;
	private List<PickListValue> noncomplianceLevels;
	
	@Override
	public void setParameters(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	public void prepareDoSave() throws Exception {
		//put the matrix from the request into the map so if validation fails we maintain the values
		noncompliancePoints = new HashMap<String, String>();
		for (String param : parameterMap.keySet()) {
			if (param.startsWith("pts")) {
				String value = parameterMap.get(param)[0];
				noncompliancePoints.put(getKeyFromFieldName(param), value);
			}
		}
	}
	
	@SkipValidation
	@Action(value = "edit-points")
	public String doEdit() {
		noncompliancePoints = noncompliancePointsService.getPointsMatrix();
		
		return INPUT;
	}
	
	@Action(value = "save-points")
	public String doSave() {
		noncompliancePointsService.savePointsMatrix(pointsToSave);
		addActionMessage("Noncompliance Points successfully saved");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
		pointsToSave = new HashMap<String, Integer>();
		for (String param : parameterMap.keySet()) {
			if (param.startsWith("pts")) {
				String value = parameterMap.get(param)[0];
				try {
					if (StringUtils.isNotBlank(value)) {
						Integer points = Integer.parseInt(value);
						pointsToSave.put(getKeyFromFieldName(param), points);
					}
				} catch (NumberFormatException nfe) {
					addFieldError(getKeyFromFieldName(param), value + " is not a valid point value, it must be a number.");
				}
			}
		}
	}
	
	private String getKeyFromFieldName(String fieldName) {
		return fieldName.substring(4);
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public void setNoncompliancePointsService(NoncompliancePointsService noncompliancePointsService) {
		this.noncompliancePointsService = noncompliancePointsService;
	}
	
	public Map<String, String> getNoncompliancePoints() {
		return noncompliancePoints;
	}
	
	public List<PickListValue> getFindingsCategories() {
		if (findingsCategories == null) {
			findingsCategories = pickListService.getValuesForPickList("Findings Categories", true);
		}
		return findingsCategories;
	}
	
	public List<PickListValue> getNoncomplianceLevels() {
		if (noncomplianceLevels == null) {
			noncomplianceLevels = pickListService.getValuesForPickList("Noncompliance Levels", true);
		}
		return noncomplianceLevels;
	}
}