package gov.utah.dts.det.ccl.actions.facility.information.license;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Exemption;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.sort.enums.ExemptionSortBy;
import gov.utah.dts.det.query.GenericPropertyComparator;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "exemptions-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "exemption_form.jsp"),
	@Result(name = "view", location = "exemptions_list.jsp")
})
public class ExemptionAction extends BaseFacilityAction implements Preparable {

	private Exemption exemption;
	
	private ListControls lstCtrl = new ListControls();
	
	private List<PickListValue> exemptions;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ExemptionSortBy.values())));
		if (lstCtrl.getSortBy() == null) {
			lstCtrl.setSortBy(ExemptionSortBy.getDefaultSortBy().name());
		}
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "exemptions-list")
	public String doList() {
		loadExemptions();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}

	@Action(value = "edit-exemption")
	public String doEdit() {
		loadExemptions();
		loadExemption();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadExemptions();
		loadExemption();
		facilityService.evict(exemption);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "exemption", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "exemption", message = "Exemption start date must be before exemption expiration date.")
		}
	)
	@Action(value = "save-exemption")
	public String doSave() {
		try {
			facilityService.saveExemption(getFacility(), exemption);
		} catch (CclServiceException cse) {
			if (cse.getErrors() != null && !cse.getErrors().isEmpty()) {
				for (String error : cse.getErrors()) {
					addActionError(error);
				}
			}
			
			return INPUT;
		}
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-exemption")
	public String doDelete() {
		getFacility().removeExemption(exemption.getId());
		facilityService.saveFacility(getFacility());
		
		return REDIRECT_VIEW;
	}
	
	private void loadExemption() {
		if (exemption != null && exemption.getId() != null) {
			exemption = getFacility().getExemption(exemption.getId());
		}
	}
	
	private void loadExemptions() {
		List<Exemption> results = new ArrayList<Exemption>(getFacility().getExemptions());
		Collections.sort(results, new GenericPropertyComparator<Exemption>(lstCtrl.getSortBy() == null ? ExemptionSortBy.getDefaultSortBy().name() : ExemptionSortBy.valueOf(lstCtrl.getSortBy()).getOrderByString()));
		lstCtrl.setResults(results);
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public Exemption getExemption() {
		return exemption;
	}
	
	public void setExemption(Exemption exemption) {
		this.exemption = exemption;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<PickListValue> getExemptions() {
		if (exemptions == null) {
			exemptions = pickListService.getValuesForPickList("Exemption", true);
		}
		return exemptions;
	}
}