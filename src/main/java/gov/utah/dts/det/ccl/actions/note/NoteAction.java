package gov.utah.dts.det.ccl.actions.note;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.sort.enums.NoteSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.ViewUtils;
//import gov.utah.dts.det.query.SortBy;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "note_form.jsp"),
	//redmine 18959@Result(name = "view", location = "/WEB-INF/jsps/note/notes_list.jsp"),
	@Result(name = "success", location = "notes-list", type = "redirectAction", params = {"facilityId", "${facilityId}", "objectId", "${objectId}", "noteType", "${noteType}", "disableRange", "${disableRange}"})
})
public class NoteAction extends BaseFacilityAction implements Preparable {
	
	private NoteService noteService;
	
	private CclListControls lstCtrl;
	private Note note;
	private Long objectId;
	private NoteType noteType;
	private boolean disableRange = false;
	
	private Date cutoffDate;

	private Map<String, Object> response;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		//lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(NoteSortBy.values())));
		//lstCtrl.setSortBy(NoteSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwelveMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_12_MONTHS);
	}
	
	@Action(value = "notes-list")
	public String doList() {
		/* redmine 18959
		loadNotes();
		return VIEW;
		 */
		return null;
	}
	
	@Action(value = "edit-note")
	public String doEdit() {
		loadNote();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadNote();
		noteService.evict(note);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "note", message = "&zwnj;")
		}
	)
	@Action(value = "save-note")
	public String doSave() {
		if (note.getId() == null) {
			noteService.createNote(note.getText(), getFacility(), objectId, noteType);
		} else {
			noteService.saveNote(note);
		}
		
		return SUCCESS;
	}
	
	@Action(value = "delete-note")
	public String doDelete() {
		try {
			noteService.deleteNote(note);
			return SUCCESS;
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
		} catch (AccessDeniedException ade) {
			addActionError(ade.getMessage());
		}
		
		loadNotes();
		return VIEW;
	}
	
	private void loadNote() {
		if (note != null && note.getId() != null) {
			note = noteService.loadById(note.getId());
		}
	}
	
	private void loadNotes() {
		if (disableRange) {
			lstCtrl.setRange(ListRange.SHOW_ALL);
		}
		
		if (objectId.equals(getFacility().getId())) {
			lstCtrl.setResults(noteService.getNotesForFacility(objectId, noteType, lstCtrl.getRange(), NoteSortBy.valueOf(lstCtrl.getSortBy())));
		} else {
			lstCtrl.setResults(noteService.getNotesForObject(objectId, noteType, lstCtrl.getRange(), NoteSortBy.valueOf(lstCtrl.getSortBy())));
		}
	}
	
	public void setNoteService(NoteService noteService) {
		this.noteService = noteService;
	}
	
	public CclListControls getlstCtrl() {
		return lstCtrl;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public Note getNote() {
		return note;
	}
	
	public void setNote(Note note) {
		this.note = note;
	}
	
	public Long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public NoteType getNoteType() {
		return noteType;
	}
	
	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}
	
	public boolean isDisableRange() {
		return disableRange;
	}
	
	public void setDisableRange(boolean disableRange) {
		this.disableRange = disableRange;
	}
	
	public Date getCutoffDate() {
		if (cutoffDate == null) {
			cutoffDate = new Date();
			cutoffDate = DateUtils.addDays(cutoffDate, -4);
		}
		return cutoffDate;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
}