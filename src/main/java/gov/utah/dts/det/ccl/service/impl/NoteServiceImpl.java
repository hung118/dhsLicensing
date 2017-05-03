package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.NoteDao;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private NoteDao noteDao;
	
	@Override
	public Note loadById(Long id) {
		Note note = noteDao.load(id);
		securityService.setEditableFlag(note);
		return note;
	}
	
	@Override
	public Note createNote(String text, Facility facility, Long objectId, NoteType noteType) {
		if (StringUtils.isBlank(text)) {
			throw new CclServiceException("Unable to save note", "error.note.create.note-text-required");
		}
		Note note = new Note();
		note.setFacility(facility);
		note.setLinkToId(objectId);
		note.setNoteType(noteType);
		note.setText(text);
		return noteDao.save(note);
	}
	
	@Override
	public Note saveNote(Note note) {
		return noteDao.save(note);
	}
	
	@Override
	public void deleteNote(Note note) {
		noteDao.delete(note);
	}
	
	/*@Override
	public void deleteNotesForObject(Long objectId, NoteType noteType) {
		List<Note> notes = noteDao.getNotesForObject(objectId, noteType, null, null);
		for (Note n : notes) {
			noteDao.delete(n);
		}
	}*/
	
	@Override
	public List<Note> getNotesForObject(Long objectId, NoteType noteType, ListRange listRange, SortBy sortBy) {
		List<Note> notes = noteDao.getNotesForObject(objectId, noteType, listRange, sortBy);
		securityService.setEditableFlag(notes);
		return notes;
	}
	
	@Override
	public List<Note> getNotesForFacility(Long facilityId, NoteType noteType, ListRange listRange, SortBy sortBy) {
		List<Note> notes = noteDao.getNotesForFacility(facilityId, noteType, listRange, sortBy);
		securityService.setEditableFlag(notes);
		return notes;
	}
	
	@Override
	public void evict(final Object entity) {
		noteDao.evict(entity);
	}
}