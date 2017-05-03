package gov.utah.dts.det.query;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

@Test
public class ListControlsTest {

	@Test
	public void testSetResultsPerPage() {
		ListControls lc = new ListControls();
		assertTrue(250 == lc.getResultsPerPage()); //test default
		lc.setResultsPerPage(15);
		//don't allow page increments outside the allowed values
		assertTrue(250 == lc.getResultsPerPage());
		//test setting an acutal value
		lc.setResultsPerPage(25);
		assertTrue(25 == lc.getResultsPerPage());
	}
	
	@Test
	public void testGetNumOfResults() {
	}
	
	@Test
	public void testGetPages() {
		ListControls lc = new ListControls();
		lc.setResultsPerPage(25);
		assertTrue(1 == lc.getPages());
		lc.setNumOfResults(250);
		assertTrue(10 == lc.getPages());
	}
	
	@Test
	public void testSetPage() {
		ListControls lc = new ListControls();
		lc.setPage(15);
		assertTrue(15 == lc.getPage());
		lc.setPage(0);
		assertTrue(1 == lc.getPage());
	}
}