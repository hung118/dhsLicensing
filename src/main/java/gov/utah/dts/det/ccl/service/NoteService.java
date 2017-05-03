package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface NoteService {
	
	@PreAuthorize("isAuthenticated() and principal.isInternal()")
	public Note loadById(Long id);
	
	@PreAuthorize("isAuthenticated() and principal.isInternal()")
	public Note createNote(String text, Facility facility, Long objectId, NoteType noteType);
	
	@PreAuthorize("hasPermission(#note, 'save')")
	public Note saveNote(Note note);
	
	@PreAuthorize("hasPermission(#note, 'delete')")
	public void deleteNote(Note note);
	
	public List<Note> getNotesForObject(Long objectId, NoteType noteType, ListRange listRange, SortBy sortBy);
	
	public List<Note> getNotesForFacility(Long facilityId, NoteType noteType, ListRange listRange, SortBy sortBy);
	
	public void evict(final Object entity);
}