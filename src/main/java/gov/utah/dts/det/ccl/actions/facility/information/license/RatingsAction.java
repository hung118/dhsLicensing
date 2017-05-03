package gov.utah.dts.det.ccl.actions.facility.information.license;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Rating;
import gov.utah.dts.det.query.ListControls;

import java.util.ArrayList;
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
	@Result(name = "redirect-view", location = "ratings-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "rating_form.jsp"),
	@Result(name = "view", location = "ratings_list.jsp")
})
public class RatingsAction extends BaseFacilityAction implements Preparable {

	private Rating rating;
	
	private List<PickListValue> ratings;
	
	private ListControls lstCtrl = new ListControls();
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "ratings-list")
	public String doSection() {
		loadRatings();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "edit-rating")
	public String doEdit() {
		loadRatings();
		loadRating();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadRatings();
		loadRating();
		facilityService.evict(rating);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "rating", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "rating", message = "Rating start date must be before rating end date.")
		}
	)
	@Action(value = "save-rating")
	public String doSave() {
		if (rating.getFacility() == null) {
			getFacility().addRating(rating);
		}
		
		facilityService.saveFacility(getFacility());
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-rating")
	public String doDelete() {
		getFacility().removeRating(rating.getId());
		facilityService.saveFacility(getFacility());
		
		return REDIRECT_VIEW;
	}
	
	private void loadRating() {
		if (rating != null && rating.getId() != null) {
			rating = getFacility().getRating(rating.getId());
		}
	}
	
	private void loadRatings() {
		List<Rating> results = new ArrayList<Rating>(getFacility().getRatings());
		lstCtrl.setResults(results);
	}
	
	public Rating getRating() {
		return rating;
	}
	
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<PickListValue> getRatings() {
		if (ratings == null) {
			ratings = pickListService.getValuesForPickList("Facility Rating", true);
		}
		return ratings;
	}
}