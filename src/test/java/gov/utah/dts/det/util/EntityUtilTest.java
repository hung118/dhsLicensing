package gov.utah.dts.det.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import gov.utah.dts.det.ccl.model.Inspection;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

@Test
public class EntityUtilTest {

	@Test
	public void testCollectionContains() {
		Inspection ins = new Inspection();
		ins.setPk(100l);
		
		Inspection ins1 = new Inspection();
		ins1.setPk(101l);
		
		Inspection ins2 = new Inspection();
		ins2.setPk(102l);
		
		List<Inspection> inspections = new ArrayList<Inspection>();
		
		assertFalse(EntityUtil.collectionContains(ins, inspections));
		assertFalse(EntityUtil.collectionContains(ins1, inspections));
		assertFalse(EntityUtil.collectionContains(ins2, inspections));
		
		inspections.add(ins);
		assertTrue(EntityUtil.collectionContains(ins, inspections));
		assertFalse(EntityUtil.collectionContains(ins1, inspections));
		assertFalse(EntityUtil.collectionContains(ins2, inspections));
		
		inspections.add(ins1);
		assertTrue(EntityUtil.collectionContains(ins, inspections));
		assertTrue(EntityUtil.collectionContains(ins1, inspections));
		assertFalse(EntityUtil.collectionContains(ins2, inspections));
		
		inspections.add(ins2);
		assertTrue(EntityUtil.collectionContains(ins, inspections));
		assertTrue(EntityUtil.collectionContains(ins1, inspections));
		assertTrue(EntityUtil.collectionContains(ins2, inspections));
		assertFalse(EntityUtil.collectionContains(new Inspection(), inspections));
		
		inspections.add(new Inspection());
		assertFalse(EntityUtil.collectionContains(new Inspection(), inspections));
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void testcollectionContainsNullItem() {
		EntityUtil.collectionContains(null, null);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void testcollectionContainsNullList() {
		EntityUtil.collectionContains(new Inspection(), null);
	}
}