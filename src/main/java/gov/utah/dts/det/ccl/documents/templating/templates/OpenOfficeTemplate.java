package gov.utah.dts.det.ccl.documents.templating.templates;

import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.templating.AbstractTemplate;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSource;
import gov.utah.dts.det.ccl.documents.templating.openoffice.DataSourceManager;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeTemplateRenderer;
import gov.utah.dts.det.ccl.documents.templating.openoffice.OpenOfficeUtil;
import gov.utah.dts.det.ccl.model.Document;
import gov.utah.dts.det.ccl.service.FileService;
import gov.utah.dts.det.filemanager.model.FileType;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenOfficeTemplate extends AbstractTemplate implements DataSourceManager {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private OpenOfficeUtil openOfficeUtil;
	
	protected Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	
	@Override
	public String getTemplateKey() {
		return "open-office";
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		return ((Document) context.get("document")).getName() + " " + sdf.format(new Date()) + ".pdf";
	}
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) throws TemplateException {
		Document document = (Document) context.get("document");
		if (document == null) {
			throw new TemplateException("Unable to load document.");
		}
		
		try {
			OpenOfficeTemplateRenderer renderer = new OpenOfficeTemplateRenderer(fileService.getInputStream(document.getFile()), FileType.ODT, this, context, null);
			List<Input> inputs = renderer.getInputs(context);
			//inputs.add(new Input("docId", "Doc Id", docId, Long.class, true, InputDisplayType.HIDDEN));
			
			return inputs;
		} catch (FileNotFoundException fnfe) {
			throw new TemplateException("Unable to render template because the template file could not be found.", fnfe);
		}
	}
	
	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException {
		Document document = (Document) context.get("document");
		if (document == null) {
			throw new TemplateException("Unable to load document.");
		}
		
		setFileName(context, descriptor);
		
		try {
			OpenOfficeTemplateRenderer renderer = new OpenOfficeTemplateRenderer(fileService.getInputStream(document.getFile()), FileType.ODT, this, context, null);
			renderer.render();
	
			openOfficeUtil.storeDocument(renderer, outputStream, FileType.PDF);
		} catch (FileNotFoundException fnfe) {
			throw new TemplateException("Unable to render template because the template file could not be found.", fnfe);
		}
	}
	
	public DataSource getDataSource(String key) {
		return dataSources.get(key);
	}

	@Autowired(required = true)
	public void setDataSources(Set<DataSource> dataSources) {
		for (DataSource ds : dataSources) {
			this.dataSources.put(ds.getDataSourceKey(), ds);
		}
	}
}