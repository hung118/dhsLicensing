package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.CmpTransactionDao;
import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.service.CmpService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cmpService")
public class CmpServiceImpl implements CmpService {
	
	@Autowired
	private CmpTransactionDao cmpDao;
	
	@Override
	public CmpTransaction loadById(Long id) {
		return cmpDao.load(id);
	}
	
	@Override
	public CmpTransaction saveCmpTransaction(CmpTransaction cmpTransaction) {
		return cmpDao.save(cmpTransaction);
	}
	
	@Override
	public void deleteCmpTransaction(CmpTransaction cmpTransaction) {
		cmpDao.delete(cmpTransaction);
	}
	
	public List<CmpTransaction> getCmpsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean approval) {
		return cmpDao.getCmpsForFacility(facilityId, listRange, sortBy, approval);
	}
	
}