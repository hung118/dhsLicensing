package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface CmpTransactionDao extends AbstractBaseDao<CmpTransaction, Long> {

	public List<CmpTransaction> getCmpsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean approval);
}