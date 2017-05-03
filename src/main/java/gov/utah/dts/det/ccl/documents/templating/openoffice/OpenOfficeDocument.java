package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.ccl.documents.DocumentException;
import gov.utah.dts.det.filemanager.model.FileType;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.InputStream;

import com.sun.star.text.XTextDocument;

public class OpenOfficeDocument {

	protected OpenOfficeUtil openOfficeUtil;
	
//	protected String fileName;
	protected FileType fileType;
	protected XTextDocument xTextDocument;
	
	public OpenOfficeDocument(InputStream inputStream, FileType fileType) throws DocumentException {
//		if (file == null) {
//			throw new NullPointerException("File to load is null");
//		}
		
		initOpenOfficeUtil();
		
//		fileName = file.getName();
		this.fileType = fileType;
		xTextDocument = openOfficeUtil.loadDocument(inputStream, openOfficeUtil.getDocumentFormat(fileType));
	}
	
	public OpenOfficeDocument(XTextDocument xTextDocument, FileType fileType) {
		this.xTextDocument = xTextDocument;
		this.fileType = fileType;
	}
	
	private void initOpenOfficeUtil() {
		openOfficeUtil = (OpenOfficeUtil) AppContext.getApplicationContext().getBean("openOfficeUtil");
	}
	
	public XTextDocument getTextDocument() {
		return xTextDocument;
	}
	
//	public String getFileName() {
//		return fileName;
//	}

	public FileType getFileType() {
		return fileType;
	}
	
	@Override
	public String toString() {
		return xTextDocument.getText().getString();
	}
}