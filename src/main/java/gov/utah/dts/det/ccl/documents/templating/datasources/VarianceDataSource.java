package gov.utah.dts.det.ccl.documents.templating.datasources;

import java.util.ArrayList;
import java.util.List;

import gov.utah.dts.det.ccl.dao.NoteDao;
import gov.utah.dts.det.ccl.dao.VarianceDao;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.sort.enums.NoteSortBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VarianceDataSource extends AbstractDataSource implements DataSource {
	
	private static final String DATA_SOURCE_TYPE = "variance";

	@Autowired
	private VarianceDao varianceDao;
	
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
		Variance variance = varianceDao.load(getObjectId(templateContext, DATA_SOURCE_TYPE));
		templateContext.getExpressionContext().put(DATA_SOURCE_TYPE, variance);
		templateContext.getExpressionContext().put("notes", noteDao.getNotesForObject(variance.getId(), NoteType.VARIANCE, null, NoteSortBy.DATE));
	}
}