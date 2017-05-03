package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.Gender;
import gov.utah.dts.det.ccl.view.YesNoChoice;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class BaseIncidentEditAction extends BaseIncidentAction {

	private List<PickListValue> childAges;
	private List<PickListValue> injuryTypes;
	private List<PickListValue> bodyParts;
	private List<PickListValue> incidentLocations;
	
	public List<PickListValue> getChildAges() {
		if (childAges == null) {
			childAges = pickListService.getValuesForPickList("Age of Child", true);
		}
		return childAges;
	}
	
	public List<PickListValue> getInjuryTypes() {
		if (injuryTypes == null) {
			injuryTypes = pickListService.getValuesForPickList("Injury Types", true);
		}
		return injuryTypes;
	}
	
	public List<PickListValue> getBodyParts() {
		if (bodyParts == null) {
			bodyParts = pickListService.getValuesForPickList("Injured Body Parts", true);
		}
		return bodyParts;
	}
	
	public List<PickListValue> getIncidentLocations() {
		if (incidentLocations == null) {
			incidentLocations = pickListService.getValuesForPickList("Incident/Injury Locations", true);
		}
		return incidentLocations;
	}
	
	public List<Gender> getGenders() {
		return Arrays.asList(Gender.values());
	}
	
	public List<YesNoChoice> getYesNoChoices() {
		return Arrays.asList(YesNoChoice.values());
	}
}