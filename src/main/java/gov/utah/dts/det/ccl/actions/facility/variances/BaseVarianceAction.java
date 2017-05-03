package gov.utah.dts.det.ccl.actions.facility.variances;

import com.opensymphony.xwork2.Preparable;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.service.VarianceService;

@SuppressWarnings("serial")
public class BaseVarianceAction extends BaseFacilityAction implements Preparable {

	protected VarianceService varianceService;
	
	protected Variance variance;
	protected Long varianceId;
	
	@Override
	public void prepare() throws Exception {
		if (varianceId != null) {
			variance = varianceService.loadById(varianceId);
		}
		if (variance == null) {
			variance = new Variance();
		}
		variance.setFacility(super.getFacility());
	}
	
	public void setVarianceService(VarianceService varianceService) {
		this.varianceService = varianceService;
	}

	public Variance reloadVariance() {
		if (varianceId != null) {
			varianceService.evict(variance);
			variance = varianceService.loadById(varianceId);
			variance.setFacility(super.getFacility());
		}
		return variance;
	}

	public Variance getVariance() {
		return variance;
	}
	
	public void setVariance(Variance variance) {
		this.variance = variance;
	}
	
	public Long getVarianceId() {
		return varianceId;
	}
	
	public void setVarianceId(Long varianceId) {
		this.varianceId = varianceId;
	}

}