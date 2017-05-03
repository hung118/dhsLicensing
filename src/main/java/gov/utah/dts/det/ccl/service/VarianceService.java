package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.view.VarianceAlertView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface VarianceService {
	
	@PreAuthorize(value = "principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')")
	public Variance loadById(Long id);
	
	@PreAuthorize(value = "hasPermission(#variance, 'save-new')")
	public Variance saveNewVariance(Variance variance);

	@PreAuthorize(value = "hasPermission(#variance, 'save')")
	public Variance saveVariance(Variance variance);
	
	@PreAuthorize(value = "hasPermission(#variance, 'save-licensor')")
	public Variance saveVarianceLicensorOutcome(Variance variance);

	@PreAuthorize(value = "hasPermission(#variance, 'save-manager')")
	public Variance saveVarianceSupervisorOutcome(Variance variance);

	@PreAuthorize(value = "hasPermission(#variance, 'save-outcome')")
	public Variance saveVarianceOutcome(Variance variance);

	@PreAuthorize(value = "hasPermission(#variance, 'save-revoke')")
	public Variance saveVarianceRevoke(Variance variance);

	@PreAuthorize(value = "hasPermission(#variance, 'delete')")
	public void deleteVariance(Variance variance);
	
	@PreAuthorize(value = "principal.isInternal() or hasAnyRole('ROLE_FACILITY_PROVIDER')")
	public List<Variance> getVariancesForFacility(Long facilityId, ListRange listRange, SortBy sortBy);
	
	public List<Variance> getVariancesExpiring(Long personId, boolean showWholeRegion, SortBy sortBy);

	@PreAuthorize(value = "hasAnyRole('ROLE_LICENSOR_SPECIALIST')")
	public List<VarianceAlertView> getLicensorVariances(SortBy sortBy);

	@PreAuthorize(value = "hasAnyRole('ROLE_LICENSOR_MANAGER')")
	public List<VarianceAlertView> getManagerVariances(SortBy sortBy);

	@PreAuthorize(value = "hasAnyRole('ROLE_LICENSING_DIRECTOR')")
	public List<VarianceAlertView> getDirectorVariances(SortBy sortBy);

	public void evict(final Object entity);
}