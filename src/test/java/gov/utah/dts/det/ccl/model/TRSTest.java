package gov.utah.dts.det.ccl.model;

import static org.testng.Assert.assertTrue;
import gov.utah.dts.det.ccl.service.RuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration("classpath:/applicationContext-test.xml")
@Transactional
/**
 * 
 * @author CKSmith
 *
 */
public class TRSTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private RuleService ruleService;

	@AfterClass
	public void tearDown() {
		ruleService = null;
	}

	@Test()
	public void saveTRSTest() {
//		RuleService service = (RuleService) super.applicationContext.getBean("ruleService");
//		assertTrue(service != null);
//		
//		assertTrue(ruleService != null);
//		
//		int r = -1;
//		try {
//			r = ruleService.processRuleFiles();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("TRSTest: ??? "+r); //CKS:WIP Oct 22, 2012
//		
//		assert(r > -1);
	}
}
