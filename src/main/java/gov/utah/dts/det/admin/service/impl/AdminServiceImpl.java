package gov.utah.dts.det.admin.service.impl;

import gov.utah.dts.det.admin.service.AdminService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void clearCache() {
		Session session = (Session) em.getDelegate();
		SessionFactory factory = session.getSessionFactory();
		Cache cache = factory.getCache();
		
		cache.evictEntityRegions();
		cache.evictCollectionRegions();
		cache.evictQueryRegions();
		
		/*Map classMetadata = factory.getAllClassMetadata();
		for (Object classMetadataObj : classMetadata.values()) {
			EntityPersister ep = (EntityPersister) classMetadataObj;
			if (ep.hasCache()) {
				factory.evictEntity(ep.getCache().getRegionName());
			}
		}
		
		Map collMetadata = factory.getAllCollectionMetadata();
		for (Object collMetadataObj : collMetadata.values()) {
			AbstractCollectionPersister acp = (AbstractCollectionPersister) collMetadataObj;
			if (acp.hasCache()) {
				factory.evictCollection(acp.getCache().getRegionName());
			}
		}

		factory.evictQueries();*/
		
		/*Map<String, CollectionMetadata> roleMap = factory.getAllCollectionMetadata();
		for (String roleName : roleMap.keySet()) {
			factory.evictCollection(roleName);
		}
		
		Map<String, ClassMetadata> entityMap = factory.getAllClassMetadata();
		for (String entityName : entityMap.keySet()) {
			factory.evictEntity(entityName);
		}
		
		factory.evictQueries();*/
	}
}