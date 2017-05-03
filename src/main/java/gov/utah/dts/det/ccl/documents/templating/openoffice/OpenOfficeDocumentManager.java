package gov.utah.dts.det.ccl.documents.templating.openoffice;

public class OpenOfficeDocumentManager { //implements DocumentManager {

//	private static final String KEY = "OO";
//	private static final String SYSTEM_CATEGORY = "System";
//	
//	@Autowired
//	private DataSourceManager dataSourceManager;
//	
//	@Autowired
//	private DocumentDao documentDao;
//	
//	@Autowired
//	private OpenOfficeUtil openOfficeUtil;
//	
//	@Override
//	public List<Document> getDocumentsInContext(Map<String, String> documentContext) {
//		//get the types of documents to pull
//		List<String> dataSources = new ArrayList<String>();
//		dataSources.add(DataSourceManager.NO_DATA_SOURCE);
//		for (DataSource dataSource : dataSourceManager.getDataSources()) {
//			if (dataSource.isDataSourceInContext(documentContext)) {
//				dataSources.add(dataSource.getDataSourceType());
//			}
//		}
//		
//		List<gov.utah.dts.det.documentgeneration.model.Document> documents = documentDao.getDocumentsForDataSources(dataSources);
//		List<Document> documentsInContext = new ArrayList<Document>();
//		
//		for (gov.utah.dts.det.documentgeneration.model.Document document : documents) {
//			//TODO: add a condition check if the document has a condition
//			
//			//do not add documents from the system cateogry - those are templates used for generating documents that will later be available
//			//to users are pre-rendered documents;
//			String category = document.getCategory().getValue();
//			if (!category.equalsIgnoreCase(SYSTEM_CATEGORY)) {
//				DocumentImpl doc = new DocumentImpl();
//				doc.setManagerKey(getKey());
//				doc.setName(document.getName());
//				doc.setDescription(document.getDescription());
//				doc.setCategory(document.getCategory().getValue());
//				doc.setSortOrder(document.getSortOrder());
//				if ("true".equals(document.getMetadataValue("promptUser"))) {
//					doc.setPromptUser(true);
//					doc.setPreRendered(false);
//					doc.setKey(document.getId().toString());
//				} else if (!document.getDataSource().equals(DataSourceManager.NO_DATA_SOURCE)) {
//					doc.setPreRendered(false);
//					doc.setKey(document.getId().toString());
//				} else {
//					doc.setKey(document.getFile().getId().toString());
//				}
//				
//				documentsInContext.add(doc);
//			}
//		}
//		
//		return documentsInContext;
//	}
//
//	@Override
//	public String getKey() {
//		return KEY;
//	}
//	
//	@Override
//	public List<Prompt> getPrompts(String key, Map<String, String> documentContext) {
//		OpenOfficeTemplateRenderer template = getTemplateByKey(key, documentContext, null, null);
//		return template.getPrompts();
//	}
//	
//	@Override
//	public String render(String key, OutputStream outputStream, FileType outputFileType, Map<String, String> documentContext) {
//		OpenOfficeTemplateRenderer template = getTemplateByKey(key, documentContext, null, null);
//		template.render();
//		openOfficeUtil.storeDocument(template, outputStream, outputFileType);
//		return template.getFileName();
//	}
//	
//	@Override
//	public String render(String key, OutputStream outputStream, FileType outputFileType, Map<String, String> documentContext,
//			Map<String, Object> elContext, Object root) {
//		OpenOfficeTemplateRenderer template = getTemplateByKey(key, documentContext, elContext, root);
//		template.render();
//		openOfficeUtil.storeDocument(template, outputStream, outputFileType);
//		return template.getFileName();
//	}
//	
//	private OpenOfficeTemplateRenderer getTemplateByKey(String key, Map<String, String> documentContext, Map<String, Object> elContext, Object root) {
//		Long id = new Long(key);
//		gov.utah.dts.det.documentgeneration.model.Document document = documentDao.load(id);
//		return new OpenOfficeTemplateRenderer(document, documentContext, elContext, root);
//	}
}