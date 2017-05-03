package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface NoteDao extends AbstractBaseDao<Note, Long> {

	public List<Note> getNotesForObject(Long objectId, NoteType noteType, ListRange listRange, SortBy sortBy);
	
	public List<Note> getNotesForFacility(Long facilityId, NoteType noteType, ListRange listRange, SortBy sortBy);
}