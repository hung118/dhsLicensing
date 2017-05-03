package gov.utah.dts.det.ccl.actions.facility.information.details;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityAssociation;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
    @Result(name = "input", location = "associated_facility_form.jsp"),
    @Result(name = "view", location = "associated_facilities_list.jsp"),
    @Result(name = "redirect-view", location = "associated-facilities-list", type = "redirectAction", params = {"facilityId", "${facilityId}"})
})
public class AssociatedFacilitiesAction extends BaseFacilityAction implements Preparable {

    private FacilityAssociation associatedFacility;
    private boolean showControls = false;
    private Set<FacilityAssociation> associatedFacilities;

    @Override
    public void prepare() throws Exception {
    }

    @Action(value = "associated-facilities-list")
    public String doList() {
        showControls = true;
        return VIEW;
    }

    @Action(value = "edit-associated-facility")
    public String doEdit() {
        if (associatedFacility != null) {
            associatedFacility = getFacility().getAssociatedFacility(associatedFacility.getId());
        }

        return INPUT;
    }

    public void prepareDoSave() {
        associatedFacility = getFacility().getAssociatedFacility(associatedFacility.getId());
        // facilityService.evict(associatedFacility);
    }

    @Validations(
		visitorFields = {
        @VisitorFieldValidator(fieldName = "associatedFacility", message = "&zwnj;")
    },
    customValidators = {
        @CustomValidator(type = "dateRange", fieldName = "associatedFacility", message = "Date associated must be before date unassociated")
    })
    @Action(value = "save-associated-facility")
    public String doSave() {
        if (associatedFacility.getParent() == null) {
            getFacility().addAssociatedFacility(associatedFacility);
        }

        facilityService.saveFacility(getFacility());
        facilityService.evict(getFacility());

        doSaveOtherWay();

        return REDIRECT_VIEW;
    }

    private void doSaveOtherWay() {

        Facility parentTemp = associatedFacility.getParent();
        Facility childTemp = associatedFacility.getChild();
        Facility ow = null;
        FacilityAssociation af = null;

        ow = facilityService.loadById(childTemp.getId());
        if (ow != null) {
            // Look for existing associated facility to be edited.
            af = ow.getAssociatedFacilityWithParentChild(childTemp.getId(), parentTemp.getId());
        }

        if (af == null) {
            // If existing is not found create empty facility association to be inserted.
            af = new FacilityAssociation();
        }

        af.setChild(associatedFacility.getParent());
        af.setParent(childTemp);

        af.setBeginDate(associatedFacility.getBeginDate());
        af.setEndDate(associatedFacility.getEndDate());
        ow.addAssociatedFacilityOtherWay(af);

        facilityService.saveFacility(ow);
        //evict to avoid persist error.
        facilityService.evict(af);
        facilityService.evict(ow);
    }

    @Action(value = "delete-associated-facility")
    public String doDelete() {

        //save associated facility info
        FacilityAssociation ff = getFacility().getAssociatedFacility(associatedFacility.getId());
        getFacility().removeAssociatedFacility(associatedFacility.getId());

        facilityService.saveFacility(getFacility());
        doDeleteOtherWay(ff);
        return REDIRECT_VIEW;
    }

    private void doDeleteOtherWay(FacilityAssociation ff) {

        Facility child = ff.getParent();
        Facility parent = ff.getChild();

        Facility ow = facilityService.loadById(parent.getId());
        FacilityAssociation af = ow.getAssociatedFacilityWithParentChild(parent.getId(), child.getId());

        ow.removeAssociatedFacility(af.getId());

        facilityService.saveFacility(getFacility());
    }

    @Override
    public Facility getFacility() {
    	return super.getFacility();
    }
    
    public FacilityAssociation getAssociatedFacility() {
        return associatedFacility;
    }

    public void setAssociatedFacility(FacilityAssociation associatedFacility) {
        this.associatedFacility = associatedFacility;
    }

    public boolean isShowControls() {
        return showControls;
    }

    public Set<FacilityAssociation> getAssociatedFacilities() {
        if (associatedFacilities == null) {
        	if (getFacility() != null) {
                associatedFacilities = getFacility().getAssociatedFacilities();
        	}
            if (associatedFacilities == null) {
            	associatedFacilities = new HashSet<FacilityAssociation>();
            }
        }
        return associatedFacilities;
    }
    
}