package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.service.FacilityService;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

//@Test
//@ContextConfiguration("classpath:applicationContext-test.xml")
//@Transactional (propagation = Propagation.REQUIRED)
public class FacilityIntegrationTest  extends AbstractTransactionalTestNGSpringContextTests  {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private FacilityService facilityService;
	
	//@Test
	public void testAssociations() {
		Facility f1 = facilityService.loadById(7037l);
		Set<FacilityAssociation> faSet = f1.getAssociatedFacilities();
		assertNotNull(faSet);
		
		for (FacilityAssociation associatedFacility : faSet) {
				logger.debug("id: " + associatedFacility.getId());
				verifyNull(associatedFacility.getChild());
				verifyNotNull(associatedFacility.getParent());
				Facility parent = associatedFacility.getParent();
				Facility child = associatedFacility.getChild();
				logger.debug("parent id: " +parent.getId());
				logger.debug("child id: " +child.getId());
			}
		
		Assert.assertEquals(f1.getId(), new Long(7037l));
		//assertEquals(f1.getIdNumber(), "F10-12500");
	}

	private void verifyNull(Object obj) {
		if(obj == null) {
			logger.debug("Object null");
		}
	}

	private void verifyNotNull(Object obj) {
		if(obj != null) {
			logger.debug("Object not null");
		}
	}
	
	
}