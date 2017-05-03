package gov.utah.dts.det.service.impl;

import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.repository.ApplicationPropertyRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

//@Test
//@ContextConfiguration("classpath:applicationContext-test.xml")
public class ApplicationServiceIntegrationTest extends AbstractTestNGSpringContextTests {

//	@Test
	public void testRepository() {
		ApplicationPropertyRepository repo = (ApplicationPropertyRepository) applicationContext.getBean("applicationPropertyRepository");
		ApplicationProperty prop = new ApplicationProperty();
		prop.setName("test");
		prop.setValue("val");
		repo.save(prop);
		
		assertNotNull(repo.findOne("test"));
	}
}