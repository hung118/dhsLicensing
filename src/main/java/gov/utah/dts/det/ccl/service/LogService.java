package gov.utah.dts.det.ccl.service;


public interface LogService {

	public void writeLog(String userId, String logTypeCode, String ipAddress, String logEntry, String userComment);
    
//    public ReadLogResponse readLog(Date logDateStart, Date logDateEnd, String userIdFilter, String logTypeCodeFilter, String ipAddressFilter);
}