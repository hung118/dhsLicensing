package gov.utah.dts.det.util;

import gov.utah.dts.canary.property.CanaryProperties;
import gov.utah.dts.canary.property.DatabaseProperties;
import gov.utah.dts.canary.property.XmlProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author chwardle
 */
public class CanaryListener implements ServletContextListener{
 
	ServletContext context;
	private String ADDITIONAL_OBJECT = "additionalCanaryServlet";
	
	@Override
	public void contextInitialized(ServletContextEvent ce) {
		Logger.getLogger(CanaryListener.class.getName()).log(Level.INFO, "CanaryListener ServletContextListener started");	
		
		context = ce.getServletContext();
		CanaryProperties cp = new CanaryProperties();

		try {
			buildProperties(cp);
		} catch (Exception e) {
			Logger.getLogger(CanaryListener.class.getName()).log(Level.WARNING, "Problem trying to create Lists in CanaryListener: " , e);
		} finally {
			context.setAttribute(ADDITIONAL_OBJECT, cp);
		}
	}
	
	private void buildProperties(CanaryProperties cp) {
		cp.setEndpoint(getEndpointProperties());
		cp.setDatabase(getDatabaseProperties());
		
		//not usually needed to validate xml files
		cp.setXml(getXmlProperties());
	}

	private List<DatabaseProperties> getDatabaseProperties() {
		List<DatabaseProperties> dbList = new ArrayList();
		
		DatabaseProperties props = new DatabaseProperties();
		props.setJndiName("jdbc/dhsLicensing");
		props.setSql("select count(*) from person");
		dbList.add(props);

//		Add additional jndi		
//		DatabaseProperties props2 = new DatabaseProperties();
//		props2.setJndiName("realJndi-JK-itsFake");
//		props2.setSql("select * from users");
//		dbList.add(props2);
		
		return dbList;
	}

	private List<String> getEndpointProperties() {
		List<String> urlList = new ArrayList();

		urlList.add("http://www.rules.utah.gov");
		//urlList.add("http://applog.at.utah.gov:8080/ApplicationLogging/services/log.wsdl");
		urlList.add("http://168.177.218.80:8080/jodconverter-webapp-2.2.2/");
		urlList.add("https://login2.utah.gov");
		return urlList;
	}

	private List<XmlProperties> getXmlProperties() {
		List<XmlProperties> xmlList = new ArrayList();
		
		XmlProperties xp = new XmlProperties();
		xp.setAlias("tiles.xml");
		xp.setXmlPath("/WEB-INF/tiles.xml");
		
		xmlList.add(xp);
		return xmlList;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent ce) {
		Logger.getLogger(CanaryListener.class.getName()).log(Level.INFO, "CanaryListener ServletContextListener destroyed");	
	}
}
