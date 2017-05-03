package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.view.VarianceAlertView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface VarianceDao extends AbstractBaseDao<Variance, Long> {

	public List<Variance> getVariancesForFacility(Long facilityId, ListRange listRange, SortBy sortBy);
	
	public List<Variance> getVariancesExpiring(Long personId, boolean showWholeRegion, SortBy sortBy);

	public List<VarianceAlertView> getLicensorVariances(SortBy sortBy);

	public List<VarianceAlertView> getManagerVariances(SortBy sortBy);

	public List<VarianceAlertView> getDirectorVariances(SortBy sortBy);
}