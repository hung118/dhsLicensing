package gov.utah.dts.det.ccl.actions.trackingrecordscreening.main;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMainService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({ 
	@Result(name = "redirect-edit", location = "edit-notes", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "success", location = "notes_detail.jsp"), 
	@Result(name = "input", location = "notes_form.jsp") 
})
public class NotesAction extends BaseTrackingRecordScreeningAction implements Preparable {

	private final String REDIRECT_EDIT = "redirect-edit";

	private TrackingRecordScreeningMainService trackingRecordScreeningMainService;
	private TrackingRecordScreeningMain screeningMain;
	private String formSection;
	private String notes;
	private String comments;
	private Boolean editable;

	@Override
	public void prepare() throws Exception {
		if (screeningId != null) {
			screeningMain = trackingRecordScreeningMainService.load(screeningId);
		}
	}

	@Action(value = "administer-notes")
	public String doAdminister() {
		if (isEditable()) {
			return doEdit();
		}
		return doView();
	}

	@Action(value = "view-notes")
	public String doView() {
		notes = screeningMain == null ? "" : screeningMain.getNotes();
		comments = screeningMain == null ? "" : screeningMain.getComments();
		return SUCCESS;
	}

	@Action(value = "edit-notes")
	public String doEdit() {
		notes = screeningMain == null ? "" : screeningMain.getNotes();
		comments = screeningMain == null ? "" : screeningMain.getComments();
		return INPUT;
	}

	@Action(value = "save-notes")
	public String doSave() {
		// Save comments and notes
		if (screeningMain == null) {
			screeningMain = new TrackingRecordScreeningMain();
			screeningMain.setId(screeningId);
		}
		screeningMain.setNotes(notes);
		screeningMain.setComments(comments);
		trackingRecordScreeningMainService.save(screeningMain);
		trackingRecordScreeningMainService.evict(screeningMain);
		
		return REDIRECT_EDIT;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getFormSection() {
		return formSection;
	}

	public void setFormSection(String formSection) {
		this.formSection = formSection;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public TrackingRecordScreeningMain getScreeningMain() {
		return screeningMain;
	}

	public void setScreeningMain(TrackingRecordScreeningMain screeningMain) {
		this.screeningMain = screeningMain;
	}

	public void setTrackingRecordScreeningMainService(TrackingRecordScreeningMainService trackingRecordScreeningMainService) {
		this.trackingRecordScreeningMainService = trackingRecordScreeningMainService;
	}

}
