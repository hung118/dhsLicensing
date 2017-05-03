package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.NoteDao;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("noteDao")
public class NoteDaoImpl extends AbstractBaseDaoImpl<Note, Long> implements NoteDao {
	
	private static final String NOTES_FOR_OBJECT_QUERY = "from Note n left join fetch n.modifiedBy join fetch n.createdBy where n.linkToId = :objectId and n.noteType = :noteType ";
	private static final String NOTES_FOR_FACILITY_QUERY = "from Note n left join fetch n.modifiedBy join fetch n.createdBy where n.facility.id = :facilityId ";
	private static final String NOTES_FOR_FACILITY_TYPE_CHECK = " and n.noteType = :noteType ";
	private static final String NOTE_DATE_FIELD = "n.creationDate";

	@PersistenceContext
	private EntityManager em;

	public NoteDaoImpl() {
		super(Note.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<Note> getNotesForObject(Long objectId, NoteType noteType, ListRange listRange, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(NOTES_FOR_OBJECT_QUERY);
		ServiceUtils.addIntervalClause(NOTE_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("objectId", objectId);
		query.setParameter("noteType", noteType);
		
		return (List<Note>) query.getResultList();
	}
	
	@Override
	public List<Note> getNotesForFacility(Long facilityId, NoteType noteType, ListRange listRange, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(NOTES_FOR_FACILITY_QUERY);
		if (noteType != null) {
			sb.append(NOTES_FOR_FACILITY_TYPE_CHECK);
		}
		ServiceUtils.addIntervalClause(NOTE_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		if (noteType != null) {
			query.setParameter("noteType", noteType);
		}
		
		return (List<Note>) query.getResultList();
	}
}