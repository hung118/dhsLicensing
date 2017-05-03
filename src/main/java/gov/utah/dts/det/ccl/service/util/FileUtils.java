package gov.utah.dts.det.ccl.service.util;

import gov.utah.dts.det.filemanager.model.FileType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtils {

	private String rootDirectory = null;
	private String tempDirectory = null;
	
	public FileUtils() {
		
	}
	
	public File saveTempFile(File file, String filename) throws IOException {
		UUID fileId = UUID.randomUUID();
		String fn = fileId.toString();
		int extLoc = filename.lastIndexOf(".");
		if (extLoc != -1) {
			fn = fn + filename.substring(extLoc);
		}
		
		File newFile = new File(getAbsoluteTempDirectoryPath() + File.separator + fn);
		org.apache.commons.io.FileUtils.copyFile(file, newFile);
		
		return newFile;
	}
	
	public File getTempFile(FileType fileType) {
		UUID fileId = UUID.randomUUID();
		String fileName = fileId + "." + fileType.name().toLowerCase();
		File file = new File(getAbsoluteTempDirectoryPath(), fileName);
		return file;
	}
	
	public File getTempFile(String filename) throws FileNotFoundException {
		File file = new File(getAbsoluteTempDirectoryPath(), filename);
		if (!file.exists()) {
			throw new FileNotFoundException("Unable to find temp file " + filename);
		}
		return file;
	}
	
	public String getUniqueFileName(String fileType) {
		UUID fileId = UUID.randomUUID();
		return fileId + "." + fileType;
	}
	
	/*public File getTempDirectory() {
		File javaTmpDir = new File(System.getProperty("java.io.tmpdir"));
		if (!javaTmpDir.exists()) {
			throw new IllegalArgumentException("Unable to find temporary directory");
		}
		
		File cclTmpDir = new File(javaTmpDir, tempDirectory);
		if (!cclTmpDir.exists()) {
			cclTmpDir.mkdir();
		}
		
		return cclTmpDir;
	}*/
	
	/*public InputStream getInputStream(String fileName) throws FileNotFoundException {
		File file = new File(getAbsoluteTempDirectoryPath(), fileName);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		return bis;
	}*/
	
	public File getFile(String path) {
		if (path == null) {
			throw new NullPointerException("Path is null");
		}
		try {
			File file = new File(rootDirectory, path);
	        if (file.getParentFile() != null && file.getParentFile().exists() == false) {
	            if (file.getParentFile().mkdirs() == false) {
	                throw new IOException("Destination '" + file + "' directory cannot be created");
	            }
	        }
			return file;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public InputStream getInputStream(String path) throws FileNotFoundException {
		if (path == null) {
			throw new NullPointerException("Path is null");
		}
		File file = new File(rootDirectory, path);
		return new FileInputStream(file);
	}
	
	public boolean deleteFile(String path) {
		if (path == null) {
			throw new NullPointerException("Path is null");
		}
		File file = new File(rootDirectory, path);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}
	
	public boolean fileExists(String path) {
		if (path == null) {
			throw new NullPointerException("Path is null");
		}
		File file = new File(rootDirectory, path);
		return file.exists();
	}
	
	public FileType getFileType(String path) throws IllegalArgumentException {
		if (path == null) {
			throw new NullPointerException("Path is null");
		}
		
		String type = path.substring(path.lastIndexOf(".") + 1);
		FileType ft = FileType.valueOf(type.toUpperCase());
		return ft;
	}
	
	public String getPathRelativeToRoot(String path) throws NullPointerException, IllegalArgumentException {
		if (path == null) {
			throw new NullPointerException("Path is null.");
		}
// This does not work for windows... fix it		
//		if (!path.startsWith(File.separator)) {
//			throw new IllegalArgumentException("Path is not an absolute path.");
//		}
		if (!path.startsWith(rootDirectory)) {
			throw new IllegalArgumentException("Path is not relative to the root directory.");
		}
	
		return path.replace(rootDirectory + File.separator, "");
	}
	
	/*public File copyFile(String srcFilePath, String dstDirectory) throws FileNotFoundException {
		if (srcFilePath == null) {
			throw new NullPointerException("Source path is null");
		}
		if (dstDirectory == null) {
			throw new NullPointerException("Destination directory is null");
		}
		
		File fromFile = new File(rootDirectory, srcFilePath);
		if (!fromFile.exists()) {
			throw new FileNotFoundException("From file not found");
		}
		
		File toDir = new File(rootDirectory, toDirectory);
		
		org.apache.commons.io.FileUtils.copyFileToDirectory(fromFile, toDir);
		
		File dstFile
	}*/
	
	public String moveFile(String fromPath, String toPath) throws IOException {
		if (fromPath == null) {
			throw new NullPointerException("From path is null");
		}
		if (toPath == null) {
			throw new NullPointerException("To path is null");
		}
		
		File fromFile = new File(rootDirectory, fromPath);
		if (!fromFile.exists()) {
			throw new FileNotFoundException("From file not found");
		}
		
		File toFile = getFile(toPath);
		
		org.apache.commons.io.FileUtils.copyFileToDirectory(fromFile, toFile);
		
		return getPathRelativeToRoot(toFile.getAbsolutePath() + File.separator + fromFile.getName());
	}
	
	private String getAbsoluteTempDirectoryPath() {
		return rootDirectory + File.separator + tempDirectory;
	}
	
	public String getRootDirectory() {
		return rootDirectory;
	}
	
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	public String getTempDirectory() {
		return tempDirectory;
	}

	public void setTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}
}