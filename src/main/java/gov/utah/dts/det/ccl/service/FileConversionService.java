package gov.utah.dts.det.ccl.service;

import java.io.InputStream;

public interface FileConversionService {

	public byte[] convertToPDF(InputStream originalStream);
}