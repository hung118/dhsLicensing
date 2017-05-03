package gov.utah.dts.det.ccl.documents.templating.datasources;

import java.util.ArrayList;
import java.util.List;

import gov.utah.dts.det.ccl.dao.FacilityDao;
import gov.utah.dts.det.ccl.dao.NoteDao;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.sort.enums.NoteSortBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacilityDataSource extends AbstractDataSource implements DataSource {
	
	public static final String DATA_SOURCE_TYPE = "facility";

	@Autowired
	private FacilityDao facilityDao;
	
	@Autowired
	private NoteDao noteDao;
	
	@Override
	public String getDataSourceKey() {
		return DATA_SOURCE_TYPE;
	}
	
	@Override
	public List<Input> getInputs(TemplateContext templateContext) {
		List<Input> inputs = new ArrayList<Input>();
		inputs.add(getInput(templateContext, DATA_SOURCE_TYPE));
		return inputs;
	}
	
	@Override
	public void initializeDataSource(TemplateContext templateContext) {
		Facility fac = facilityDao.loadFacilityWithSearchViewById(getObjectId(templateContext, DATA_SOURCE_TYPE));
		templateContext.getExpressionContext().put(DATA_SOURCE_TYPE, fac);
		templateContext.getExpressionContext().put("notes", noteDao.getNotesForObject(fac.getId(), NoteType.FACILITY, null, NoteSortBy.DATE));
	}
}