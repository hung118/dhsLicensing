package gov.utah.dts.det.ccl.service.util;

import static org.testng.Assert.assertEquals;
import gov.utah.dts.det.filemanager.model.FileType;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class FileUtilsTest {
	
	private FileUtils util;
	
	@BeforeClass
	public void setUp() {
		util = new FileUtils();
		util.setRootDirectory("/hosts/ccl/home");
		util.setTempDirectory("temp");
	}
	
	@DataProvider(name = "relPathExceptionData")
	public Object[][] getRelPathExceptionData() {
		return new Object[][] {
			{null}, {"test"}, {"/this/is/not/the/root"}
		};
	}

//	@Test
//	public void testGetPathRelativeToRoot() {
//		String relPath = util.getPathRelativeToRoot("/hosts/ccl/home/test/doc.txt");
//		assertEquals(relPath, "test/doc.txt");
//		relPath = util.getPathRelativeToRoot("/hosts/ccl/home/test");
//		assertEquals(relPath, "test");
//		relPath = util.getPathRelativeToRoot("/hosts/ccl/home/test.txt");
//		assertEquals(relPath, "test.txt");
//	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class, NullPointerException.class}, dataProvider = "relPathExceptionData")
	public void testGetPathRelativeToRootExceptions(String path) {
		util.getPathRelativeToRoot(path);
	}
	
	@Test
	public void testGetFileType() {
		for (FileType type : FileType.values()) {
			assertEquals(util.getFileType("test." + type.name().toLowerCase()), type);
			assertEquals(util.getFileType("test." + type.name()), type);
		}
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testGetUnknownFileType() {
		util.getFileType("test.jpg");
	}
}