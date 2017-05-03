package gov.utah.dts.det.ccl.documents.templating.datasources;

import gov.utah.dts.det.ccl.dao.ComplaintDao;
import gov.utah.dts.det.ccl.dao.NoteDao;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.sort.enums.NoteSortBy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplaintDataSource extends AbstractDataSource implements DataSource {
	
	private static final String DATA_SOURCE_TYPE = "complaint";

	@Autowired
	private ComplaintDao complaintDao;
	
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
		Complaint complaint = complaintDao.load(getObjectId(templateContext, DATA_SOURCE_TYPE));
		templateContext.getExpressionContext().put(DATA_SOURCE_TYPE, complaint);
		templateContext.getExpressionContext().put("notes", noteDao.getNotesForObject(complaint.getId(), NoteType.COMPLAINT, null, NoteSortBy.DATE));
	}
}