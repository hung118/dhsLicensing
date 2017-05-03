package gov.utah.dts.det.ccl.documents.reporting.reports;

import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.reporting.ReportRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDimensionReport extends AbstractCclReport {

	protected static final String DATE_RANGE_START_KEY = "dtStart";
	protected static final String DATE_RANGE_END_KEY = "dtEnd";
	protected static final String LICENSE_TYPE_KEY = "licType";
	
	protected static final String DIM_DATE_TABLE = "rpt_dim_date";
	protected static final String DIM_LICENSE_TYPE_TABLE = "rpt_dim_license_type";
	protected static final String DIM_LOCATION_TABLE = "rpt_dim_location";
	
	@SuppressWarnings("unchecked")
	protected Input getLicenseTypeInput(boolean required) {
		Query query = reportRunner.getNativeQuery("select distinct license_type from rpt_dim_license_type order by license_type");
		List<String> results = (List<String>) query.getResultList();
		
		Map<String, String> types = new LinkedHashMap<String, String>();
		if (!required) {
			types.put("All", "All");
		}
		for (String res : results) {
			types.put(res, res);
		}
		
		return new Input(LICENSE_TYPE_KEY, "License Type", required ? results.get(0) : "All", String.class, required, types, InputDisplayType.SELECT);
	}
	
	protected Input getStartDateInput() {
		return new Input(DATE_RANGE_START_KEY, "Start Date", DateUtils.truncate(new Date(), Calendar.MONTH), Date.class, true, InputDisplayType.DATE);
	}
	
	protected Input getEndDateInput() {
		return new Input(DATE_RANGE_END_KEY, "End Date", DateUtils.truncate(new Date(), Calendar.DATE), Date.class, true, InputDisplayType.DATE);
	}
}