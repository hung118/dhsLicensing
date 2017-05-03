package gov.utah.dts.det.ccl.documents.templating.openoffice;

import gov.utah.dts.det.filemanager.model.FileType;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.sun.star.beans.PropertyValue;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.XInputStream;
import com.sun.star.lang.XComponent;
import com.sun.star.lib.uno.adapter.ByteArrayToXInputStreamAdapter;
import com.sun.star.lib.uno.adapter.OutputStreamToXOutputStreamAdapter;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XCloseable;

@SuppressWarnings("unchecked")
@Component("openOfficeUtil")
public class OpenOfficeUtil {
	
	public static final String BEAN_NAME = "openOfficeUtil";
	
	@Autowired
	private OpenOfficeConnection connection;
	
	@Autowired
	private DocumentFormatRegistry documentFormatRegistry;
	
	public void storeDocument(OpenOfficeDocument document, OutputStream outputStream, FileType outputFileType) throws OpenOfficeServiceException {
		storeDocumentToOutputStream(document, outputStream, getDocumentFormat(outputFileType));
	}
	
	public XTextDocument loadDocument(InputStream inputStream, DocumentFormat inputFormat) throws OpenOfficeConnectException, OpenOfficeServiceException {
		XComponentLoader desktop = getComponentLoader();
		
		try {
			Map<String, Object> loadProperties = new HashMap<String, Object>();
			loadProperties.putAll(inputFormat.getImportOptions());
			loadProperties.put("Hidden", Boolean.TRUE);
			loadProperties.put("ReadOnly", Boolean.TRUE);
			byte[] data = IOUtils.toByteArray(inputStream);
			loadProperties.put("InputStream", new ByteArrayToXInputStreamAdapter(data));
	//			loadProperties.put("InputStream", new InputStreamToXInputStreamAdapter(inputStream));
			
			XComponent xComponent = desktop.loadComponentFromURL("private:stream", "_blank", 0, toPropertyValues(loadProperties));
			XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
			return xTextDocument;
		} catch (Exception e) {
			//catching exception here because all exceptions should be turned into a DocumentException
			throw new OpenOfficeServiceException("Unable to load the requested file.", e);
		}
	}
	
	public XTextDocument loadDocument(byte[] binaryData, DocumentFormat inputFormat) throws OpenOfficeServiceException {
		XComponentLoader desktop = getComponentLoader();
		
		try {
			Map<String, Object> loadProperties = new HashMap<String, Object>();
			loadProperties.putAll(inputFormat.getImportOptions());
			loadProperties.put("Hidden", Boolean.TRUE);
			loadProperties.put("ReadOnly", Boolean.TRUE);
			loadProperties.put("InputStream", new ByteArrayToXInputStreamAdapter(binaryData));
			
			XComponent xComponent = desktop.loadComponentFromURL("private:stream", "_blank", 0, toPropertyValues(loadProperties));
			XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
			return xTextDocument;
		} catch (Exception e) {
			throw new OpenOfficeServiceException("Unable to load the requested file.", e);
		}
	}
	
	public void insertDocument(XDocumentInsertable insertingInto, DocumentFormat insertingIntoFormat, OpenOfficeDocument toInsert) throws OpenOfficeServiceException {
		try {
			byte[] templateToInsert = getDocumentBytes(toInsert, insertingIntoFormat);
			
			XInputStream isAdapter = new ByteArrayToXInputStreamAdapter(templateToInsert);
			
			Map<String, Object> loadProperties = new HashMap<String, Object>();
			loadProperties.putAll(insertingIntoFormat.getExportOptions(getDocumentFormat(toInsert.getFileType()).getFamily()));
			loadProperties.put("Hidden", Boolean.TRUE);
			loadProperties.put("ReadOnly", Boolean.TRUE);
			loadProperties.put("InputStream", isAdapter);
		
			insertingInto.insertDocumentFromURL("private:stream", toPropertyValues(loadProperties));
		} catch (Exception e) {
			throw new OpenOfficeServiceException("Unable to insert template.", e);
		}
	}
	
	public OpenOfficeDocument getNewDocument(DocumentFormat documentFormat) throws OpenOfficeServiceException {
		XComponentLoader desktop = getComponentLoader();
		
		try {
			//create a new blank document
			Map<String, Object> loadProps = new HashMap<String, Object>();
			loadProps.put("Hidden", Boolean.TRUE);
	        String url = null;
	        if (documentFormat.getFileExtension().equals("odt")) {
	        	url = "private:factory/swriter";
	        } else {
	        	throw new OpenOfficeServiceException("On the fly document creation not supported for format: " + documentFormat.getFileExtension());
	        }
	        
			XComponent dstComponent = desktop.loadComponentFromURL(url, "_blank", 0, OpenOfficeUtil.toPropertyValues(loadProps));
			XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, dstComponent);
			return new OpenOfficeDocument(xTextDocument, getFileType(documentFormat));
		} catch (Exception e) {
			throw new OpenOfficeServiceException("Unable to create new document.", e);
		}
	}
	
	public Map<String, Object> getInputPropertiesForDocument(FileType type, byte[] binaryData) {
		DocumentFormat inputFormat = documentFormatRegistry.getFormatByFileExtension(type.name());
		
		Map<String, Object> loadProperties = new HashMap<String, Object>();
		loadProperties.putAll(inputFormat.getImportOptions());
		loadProperties.put("Hidden", Boolean.TRUE);
		loadProperties.put("ReadOnly", Boolean.TRUE);
		loadProperties.put("InputStream", new ByteArrayToXInputStreamAdapter(binaryData));
		
		return loadProperties;
	}
	
	public byte[] getDocumentBytes(OpenOfficeDocument document, DocumentFormat outputFormat) throws OpenOfficeServiceException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		storeDocumentToOutputStream(document, baos, outputFormat);
		return baos.toByteArray();
	}
	
	public void storeDocumentToOutputStream(OpenOfficeDocument document, OutputStream outputStream, DocumentFormat outputFormat) throws OpenOfficeServiceException {
		try {
			Map<String, Object> storeProperties = outputFormat.getExportOptions(getDocumentFormat(document.getFileType()).getFamily());
			storeProperties.put("OutputStream", new OutputStreamToXOutputStreamAdapter(outputStream));
			XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document.getTextDocument());
			xStorable.storeToURL("private:stream", OpenOfficeUtil.toPropertyValues(storeProperties));
			
			XCloseable xclosable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document.getTextDocument());
	        xclosable.close(true);
		} catch (Exception e) {
			throw new OpenOfficeServiceException("Unable to store the document.", e);
		}
	}
	
	public XComponentLoader getComponentLoader() throws OpenOfficeConnectException {
		try {
			return connection.getDesktop();
		} catch (Exception e) {
			//catching exception is not recommended but this method doesn't declare what it throws and pretty much can throw all kinds of exceptions
			throw new OpenOfficeConnectException("Unable to contact the rendering service.  Please contact the super admin.", e);
		}
	}
	
	public DocumentFormat getDocumentFormat(FileType fileType) {
		return documentFormatRegistry.getFormatByFileExtension(fileType.name());
	}
	
	public FileType getFileType(DocumentFormat documentFormat) {
		return FileType.valueOf(documentFormat.getFileExtension().toUpperCase());
	}
	
	public static PropertyValue getProperty(String name, Object value) {
		PropertyValue property = new PropertyValue();
    	property.Name = name;
    	property.Value = value;
    	return property;
	}
	
	public static PropertyValue[] toPropertyValues(Map<String, Object> properties) {
		PropertyValue[] propertyValues = new PropertyValue[properties.size()];
		int i = 0;
		for (Iterator<Entry<String, Object>> itr = properties.entrySet().iterator(); itr.hasNext();) {
			Map.Entry<String, Object> entry = itr.next();
			if (entry.getValue() instanceof Map) {
				Map<String, Object> subProperties = (Map<String, Object>) entry.getValue();
				entry.setValue(toPropertyValues(subProperties));
			}
			propertyValues[i++] = getProperty((String) entry.getKey(), entry.getValue());
		}
		return propertyValues;
	}
}