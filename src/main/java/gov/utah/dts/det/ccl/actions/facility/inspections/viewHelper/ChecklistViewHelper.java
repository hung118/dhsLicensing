package gov.utah.dts.det.ccl.actions.facility.inspections.viewHelper;

import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.RuleSection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ChecklistViewHelper implements Iterator<ChecklistRowHelper> {
	
	private InspectionChecklist checklist;
	private TreeMap<Long, RuleSection> sectionList;
	private boolean editMode = false;
	private int count;
	private int max;
	
	@SuppressWarnings("unused")
	private ChecklistViewHelper(){
		// force them to supply underlying data 
	}
	
	/**
	 * @param checklist - the underlying data set
	 * @param editMode - whether to render individual results as read-only (false) or editable (true)
	 */
	public ChecklistViewHelper(InspectionChecklist checklist, boolean editMode) {
		this.checklist = checklist;
		this.editMode = editMode;
		
		/*
		 * NOTE: let's build a section list and work from that rather than the results themselves
		 */
		sectionList = new TreeMap<Long, RuleSection>();
		for (InspectionChecklistResult icr : checklist.getResults()) {
			if (icr.getSection() != null) { 
				sectionList.put(icr.getSection().getId(), icr.getSection());
			} else {
				sectionList.put(icr.getSubSection().getSection().getId(), icr.getSubSection().getSection());
			}
		}
		this.max = sectionList.size();
	}


	public InspectionChecklist getChecklist() {
		return checklist;
	}

	@Override
	public boolean hasNext() {
		return (count < max);
	}

	@Override
	public ChecklistRowHelper next() {
		
		ChecklistRowHelper row = new ChecklistRowHelper();

		// determine which section we're on
		RuleSection section = (RuleSection) sectionList.values().toArray()[count];
		row.setSection(section);
		
		// build a list of results based on the current section
		List<InspectionChecklistResult> icrList = new ArrayList<InspectionChecklistResult>();
		for (InspectionChecklistResult icr : checklist.getResults()) {
			if (icr.getSection() != null && icr.getSection().getId().longValue() == section.getId().longValue()) {
				icrList.add(icr);
			} else if (icr.getSubSection() != null && icr.getSubSection().getSection().getId().longValue() == section.getId().longValue()) {
				icrList.add(icr);
			}
		}
		
		row.setResults(icrList);
		
		count++;
		
		return row;
		
	}

	@Override
	public void remove() {
		// ignore - do nothing
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setChecklist(InspectionChecklist checklist) {
		this.checklist = checklist;
	}


}
