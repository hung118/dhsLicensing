package gov.utah.dts.det.util;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CompareUtilsTest {
	
	@Test
	public void testCompareNullSafeComparable() {
		String s1 = "test";
		String s2 = "Test";
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(s1, s1, true) == 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(s1, null, true) > 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(s1, null, false) < 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(null, s1, true) < 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(null, s1, false) > 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare((String) null, null, true) == 0);
		Assert.assertTrue(CompareUtils.nullSafeComparableCompare(s1, s2, true) > 0);
	}
}