package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface CmpService {
	
	public CmpTransaction loadById(Long id);
	
	public CmpTransaction saveCmpTransaction(CmpTransaction cmpTransaction);
	
	public void deleteCmpTransaction(CmpTransaction cmpTransaction);
	
	public List<CmpTransaction> getCmpsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean approval);
}