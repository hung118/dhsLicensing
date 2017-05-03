package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.logging.Application;
import gov.utah.dts.det.ccl.logging.LogData;
import gov.utah.dts.det.ccl.logging.WriteLogRequest;
import gov.utah.dts.det.ccl.service.LogService;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class LogServiceImpl extends WebServiceGatewaySupport implements LogService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	
	private static final String APPLICATION_USERNAME = "ccl_web";
	private static final String SECURE_TOKEN = "chi1dcar3";
	
 	public LogServiceImpl(WebServiceMessageFactory messageFactory) {
		super(messageFactory);
	}
 	
 	protected void initGateway() throws Exception {
 		super.initGateway();
    }
 	
 	@Override
	public void writeLog(String userId, String logTypeCode, String ipAddress, String logEntry, String userComment) {
 		try {
 			
			WriteLogRequest writeLogRequest = new WriteLogRequest();
			
			Application application = new Application();
			application.setUsername(APPLICATION_USERNAME);
			application.setSecureToken(SECURE_TOKEN);
			writeLogRequest.setApplication(application);
			
			LogData logData = new LogData();
			logData.setUserId(userId);
			logData.setLogTypeCode(logTypeCode);
			logData.setIPAddress(ipAddress);
			logData.setLogEntry(logEntry);
			logData.setUserComment(userComment);
			writeLogRequest.setLogData(logData);
			
			getWebServiceTemplate().marshalSendAndReceive(writeLogRequest);
 		} catch (Exception e) {
 			logger.error("Unable to write to log [userId=" + userId + ", logTypeCode=" + logTypeCode + ",ipAddress=" + ipAddress + ",logEntry=" + logEntry + ",userComment=" + userComment + "]", e);
 		}
	}
	
	/*public ReadLogResponse readLog(Date logDateStart, Date logDateEnd, String userIdFilter, String logTypeCodeFilter, String ipAddressFilter) {
		ReadLogRequest readLogRequest = new ReadLogRequest();
		
		Application application = new Application();
		application.setUsername(APPLICATION_USERNAME);
		application.setSecureToken(SECURE_TOKEN);
		readLogRequest.setApplication(application);
		
		LogDataFilter logDataFilter = new LogDataFilter();
		logDataFilter.setStartDate(getCalendarFromDate(logDateStart));
		logDataFilter.setEndDate(getCalendarFromDate(logDateEnd));
		logDataFilter.setUserId(userIdFilter);
		logDataFilter.setLogTypeCode(logTypeCodeFilter);
		logDataFilter.setIPAddress(ipAddressFilter);
		readLogRequest.setLogDataFilter(logDataFilter);
		
		ReadLogResponse response = (ReadLogResponse) getWebServiceTemplate().marshalSendAndReceive(readLogRequest);
		return response;
	}*/
	
	protected XMLGregorianCalendar getCalendarFromDate(Date d) {
		if (d == null) {
			return null;
		}
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		try {
			XMLGregorianCalendar newDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			return newDate;
		} catch(Exception e) {
			logger.error("Error converting date to XMLGregorianCalendar: err: " + e.getMessage(), e);
		}
		return null;	
	}
	
	
	protected Date getDateFromCalendar(XMLGregorianCalendar cal){
		if (cal == null) {
			return null;
		}
		//GregorianCalendar(int year, int month, int date) 
		GregorianCalendar c = new GregorianCalendar(cal.getYear(), cal.getMonth(), cal.getDay());
		return new Date(c.getTimeInMillis());
	}
}