package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.service.LogService;

import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceMock implements LogService {

	@Override
	public void writeLog(String userId, String logTypeCode, String ipAddress,
			String logEntry, String userComment) {
		
	}
}