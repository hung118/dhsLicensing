package gov.utah.dts.det.util;

import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.service.ApplicationService;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class SiteUtil {
	
	private static String version;
	private static String buildNumber;
	private static String buildTimestamp;
	
	private SiteUtil() {
		
	}
	
	private static void init() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String appServerHome = ((HttpServletRequest) request).getSession().getServletContext().getRealPath("/");
	
			File manifestFile = new File(appServerHome, "META-INF/MANIFEST.MF");
			 
			Manifest mf = new Manifest();
			mf.read(new FileInputStream(manifestFile));
	
			Attributes atts = mf.getMainAttributes();
			version = atts.getValue("Implementation-Version");
			buildNumber = atts.getValue("Implementation-Build");
			buildTimestamp = atts.getValue("Build-Timestamp");
		} catch (Exception e) {
			version = "";
			buildNumber = "";
			buildTimestamp = "";
		}
		
	}
	
	public static String getVersion() {
		if (version == null) {
			init();
		}
		
		return version;
	}
	
	public static String getBuildNumber() {
		if (buildNumber == null) {
			init();
		}
		
		return buildNumber;
	}
	
	public static String getBuildTimestamp() {
		if (buildTimestamp == null) {
			init();
		}
		
		return buildTimestamp;
	}
	
	public static String getResourceVersionString() {
		if (StringUtils.isBlank(getBuildTimestamp())) {
			return "";
		} else {
			return "-" + getBuildTimestamp() + ".min";
		}
	}
	
	public static int getCopyrightYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String getEnvironmentHeader() {
		ApplicationService appService = (ApplicationService) AppContext.getApplicationContext().getBean("applicationService");
		return appService.getApplicationPropertyValue(ApplicationPropertyKey.ENVIRONMENT_HEADER.getKey());
	}
}