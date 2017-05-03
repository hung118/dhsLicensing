/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import org.testng.annotations.Test;

/**
 * @author Dan Olsen
 * 
 */
@Test(groups = { "main", "complaint" })
public class ComplaintTest {

	/*private User mockUser;
	private PickListValue mockPLV;
	private Complaint c;

	@BeforeClass
	public void setUp() {
		c = new Complaint();
		mockPLV = createMock(PickListValue.class);
	}

	@Test(groups = { "main", "complaint" })
	public void testCompaintDateReceived() {
		//Complaint c = new Complaint();
		assertNotNull(c.getDateReceived());
		Date newDate = new Date();
		c.setDateReceived(newDate);
		assertSame(c.getDateReceived(), newDate);
		assertEquals(c.getDateReceived().getTime(), newDate.getTime());
	}

	@Test(groups = { "main", "complaint" })
	public void testDisclosureStatement() {
		//Complaint c = new Complaint();
		c.setDisclosureStatementRead(true);
		assertTrue(c.getDisclosureStatementRead());
		c.setDisclosureStatementRead(false);
		assertFalse(c.getDisclosureStatementRead());
	}

	@Test(groups = { "main", "complaint" })
	public void testAnonymousStatement() {
		//Complaint c = new Complaint();
		c.setAnonymousStatementRead(true);
		assertTrue(c.getAnonymousStatementRead());
		c.setAnonymousStatementRead(false);
		assertFalse(c.getAnonymousStatementRead());
	}

	@Test(groups = { "main", "complaint" })
	public void testIntakePerson() {
		mockUser = createMock(User.class);
		//Complaint c = new Complaint();
		c.setIntakePerson(mockUser);
		// mockUser.
		assertSame(c.getIntakePerson(), mockUser);
	}

	@Test(groups = { "main", "complaint" })
	public void testNarrative() {
		//Complaint c = new Complaint();
		String narrative = "This is the narrative";
		c.setNarrative(narrative);
		assertSame(c.getNarrative(), narrative);
		assertEquals(c.getNarrative(), narrative);
		assertEquals(c.getNarrative(), "This is the narrative");
	}
	
	public void testComplaintent() {
		Person mockPerson = createMock(Person.class);
		c.setComplainant(mockPerson);
		assertSame(c.getComplainant(), mockPerson);
	}
	
	public void testComplaintRelationship() {
		c.setComplainantRelationship(mockPLV);
		assertSame(c.getComplainantRelationship(), mockPLV);
	}
	
	public void testComplaintentNameUsed() {
		c.setUseComplainantName(true);
		assertTrue(c.getUseComplainantName());
		c.setUseComplainantName(false);
		assertFalse(c.getUseComplainantName());
	}
	
	public void testBestTimeToCall() {
		c.setBestTimeToCall("Now");
		assertEquals(c.getBestTimeToCall(), "Now");
		String bestTime = "Never";
		c.setBestTimeToCall(bestTime);
		assertSame(c.getBestTimeToCall(), bestTime);
		assertEquals(c.getBestTimeToCall(), bestTime);
	}
	
	public void testScreeningDate() {
		Date testDate = new Date();
		c.setScreeningDate(testDate);
		assertSame(c.getScreeningDate(), testDate);
		assertEquals(c.getScreeningDate(), testDate);
	}
	
	public void testProceedType() {
		mockPLV = createMock(PickListValue.class);
		c.setProceedType(mockPLV);
		assertSame(c.getProceedType(), mockPLV);
	}
	
	public void testResponseTime() {
		mockPLV = createMock(PickListValue.class);
		c.setResponseTime(mockPLV);
		assertSame(c.getResponseTime(), mockPLV);
	}
	
	public void testResponseTimeOther() {
		String testString = "Other Response Time String";
		c.setResponseTimeOther(testString);
		assertSame(c.getResponseTimeOther(), testString);
		assertEquals(c.getResponseTimeOther(), testString);
		assertEquals(c.getResponseTimeOther(), "Other Response Time String");
		assertNotNull(c.getResponseTimeOther());
	}
	
	public void testInvestigationType() {
		mockPLV = createMock(PickListValue.class);
		c.setInvestigationType(mockPLV);
		assertSame(c.getInvestigationType(), mockPLV);
	}
	
	public void testInvestigationTypeOther() {
		String testString = "Other Investigation Type String";
		c.setInvestigationTypeOther(testString);
		assertSame(c.getInvestigationTypeOther(), testString);
		assertEquals(c.getInvestigationTypeOther(), testString);
		assertEquals(c.getInvestigationTypeOther(), "Other Investigation Type String");
		assertNotNull(c.getInvestigationTypeOther());
	}
	
	/*public void testLicensingSpecialist() {
		mockUser = createMock(User.class);
		c.setLicensingSpecialist(mockUser);
		assertSame(c.getLicensingSpecialist(), mockUser);
	}*/
	
	/*public void testOtherInstructions() {
		String testString = "Here are your other instructions.";
		c.setOtherInstructions(testString);
		assertSame(c.getOtherInstructions(), testString);
		assertEquals(c.getOtherInstructions(), testString);
		assertNotNull(c.getOtherInstructions());
		assertEquals(c.getOtherInstructions(), "Here are your other instructions.");
	}
	
	public void testSubmittedToManagerDate() {
		Date testDate = new Date();
		c.setSubmittedToManagerDate(testDate);
		assertSame(c.getSubmittedToManagerDate(), testDate);
		assertEquals(c.getSubmittedToManagerDate().getTime(), testDate.getTime());
	}
	
	public void testManagementApprover() {
		mockUser = createMock(User.class);
		c.setManagementApprover(mockUser);
		assertSame(c.getManagementApprover(), mockUser);
	}
	
	public void testApprovalDate() {
		Date testDate = new Date();
		c.setApprovalDate(testDate);
		assertSame(c.getApprovalDate(), testDate);
		assertEquals(c.getApprovalDate().getTime(), testDate.getTime());
	}*/
}
