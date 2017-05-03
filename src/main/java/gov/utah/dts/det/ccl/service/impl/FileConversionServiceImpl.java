package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.service.FileConversionService;
import gov.utah.dts.det.service.ApplicationService;

import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileConversionService")
public class FileConversionServiceImpl implements FileConversionService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileConversionServiceImpl.class);
	
	@Autowired
	private ApplicationService applicationService;
	
	@Override
	public byte[] convertToPDF(InputStream originalStream) {
		ApplicationProperty prop = applicationService.findApplicationPropertyByKey(ApplicationPropertyKey.FILE_CONVERSION_URL.getKey());
		HttpClient client = new HttpClient();
		PostMethod httpPost = new PostMethod(prop.getValue());
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(originalStream);
		httpPost.setRequestEntity(requestEntity);
		Header contentType = new Header("Content-Type", "application/vnd.oasis.opendocument.text");
		Header accept = new  Header("Accept","application/pdf");
		httpPost.setRequestHeader(contentType);
		httpPost.setRequestHeader(accept);
		try {
			client.executeMethod(httpPost);
			if (httpPost.getStatusCode() == HttpStatus.SC_OK) {
				return httpPost.getResponseBody();// success, return pdf bytes from converter service
			}
		} catch(Exception e){
			logger.error("Error converting file via open office service ", e);
		} finally{
			httpPost.releaseConnection();
		}
		return null;
	}
}
