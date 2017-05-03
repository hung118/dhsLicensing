package gov.utah.dts.det.util.spring;

import gov.utah.dts.det.ccl.model.interceptor.DelegatingInterceptor;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.EmptyInterceptor;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;

@SuppressWarnings("unchecked")
public class ConfigurableHibernatePersistence extends HibernatePersistence {

	private DelegatingInterceptor interceptor;
	
	public ConfigurableHibernatePersistence(DelegatingInterceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		Ejb3Configuration cfg = new Ejb3Configuration();
		Ejb3Configuration configured = cfg.configure( info, map );
		postprocessConfiguration(info, map, configured);
		return configured != null ? configured.buildEntityManagerFactory() : null;
	}
	
	protected void postprocessConfiguration(PersistenceUnitInfo info, Map map, Ejb3Configuration configured) {  
		if (this.interceptor != null) {  
			if (configured.getInterceptor() == null || EmptyInterceptor.class.equals(configured.getInterceptor().getClass())) {  
				configured.setInterceptor(this.interceptor);
			} else {
				throw new IllegalStateException("Hibernate interceptor already set in persistence.xml (" + configured.getInterceptor() + ")");
			}
		}
	} 
}