package gov.utah.dts.det.admin.quartz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author GTEAM LLC
 * Feb 10, 2006 
 */
public class ZipUtil {

    /**
     * Bean will zip the contents of the specified file or directory
     * Note: does not recurse!
     * @param zipFileName - resulting zip file name
     * @param target - base directory where the files exist
     * @param filter - file names suffixes to include
     * @param append - append to an existing zip file
     * @param usePath - include full path w/ zip file entries 
     * @return - the names of all the file(s) that were zipped
     * @throws IOException
     */
    public static String[] zip(String zipFileName, String target, String filter, boolean append, boolean usePath) throws IOException {
        return ZipUtil.zip(zipFileName, target, filter, append, usePath, false);
    }
    
    /**
     * Bean will zip the contents of the specified file or directory
     * Note: does not recurse!
     * @param zipFileName - resulting zip file name
     * @param target - base directory where the files exist
     * @param filter - file names suffixes to include
     * @param append - append to an existing zip file
     * @param usePath - include full path w/ zip file entries 
     * @param recurse - if the target is a directory and there are 
     *          subdirectories, true will cause this to include those
     *          in the zip file
     * @return - the names of all the file(s) that were zipped
     * @throws IOException
     */
    public static String[] zip(String zipFileName, String target, String filter, boolean append, boolean usePath, boolean recurse) throws IOException {
        String [] fileNames = null;
        // validate some stuff
        File existingZipFile = new File(zipFileName);
        if (existingZipFile.exists() && !append) {
            existingZipFile.delete();
        }
        existingZipFile.createNewFile();
        File targetFile = new File(target);
        if (!targetFile.exists()) {
            throw new IOException("target ["+target+"] doesn't exist!");
        }
        if (!targetFile.canRead()) {
            throw new IOException("target ["+target+"] can't be read!");
        }
        // we can't allow usePath (as false) w/ recursion
        if (recurse && !usePath)
            usePath = true;
        Vector includeList = new Vector();
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        includeList = addFiles(out, target, filter, usePath, recurse, includeList);
        out.close();
        fileNames = new String[includeList.size()];
        includeList.toArray(fileNames);
        return fileNames;
    }

    /**
     * Method actually adds the files to the zip container
     * @param out
     * @param target
     * @param filter
     * @param usePath
     * @param recurse
     * @param includeList
     * @return
     * @throws IOException
     */
    private static Vector addFiles(ZipOutputStream out, String target, String filter, boolean usePath, boolean recurse, Vector includeList) throws IOException {
        byte[] buf = new byte[1024];
        File[] fileList = null;
        File targetFile = new File(target);
        if (targetFile.isDirectory()) {
            fileList = targetFile.listFiles();   
        } else {
            fileList = new File[1];
            fileList[0] = new File(target);
        }
        if (fileList != null && fileList.length > 0) {
            for (int i=0; i<fileList.length; i++) {
                if (fileList[i].isFile()) {
                    if (filter != null && filter.length() == 0) {
                        if (!fileList[i].getName().endsWith(filter))
                            continue;
                    }
                    FileInputStream in = new FileInputStream(fileList[i].getAbsolutePath());
                    if (!usePath) {
                        out.putNextEntry(new ZipEntry(fileList[i].getName()));
                    } else {
                        out.putNextEntry(new ZipEntry(fileList[i].getAbsolutePath()));
                    }
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                    in.close();
                    includeList.add(fileList[i].getAbsolutePath());
                } else if (fileList[i].isDirectory() && recurse) {
                    includeList = ZipUtil.addFiles(out, fileList[i].getAbsolutePath(), filter, true, true, includeList);
                }
            }
        }
        return includeList;
    }

    /**
     * Unzips the file(s)
     * @param zipFileName
     * @return
     * @throws IOException
     */
    public static String[] unzip(String zipFileName) throws IOException {
        String[] fileNames = null;
        int BUFFER = 2048;
        String baseDir = "";
        if (zipFileName.indexOf("/") > -1) {
            baseDir = zipFileName.substring(0, zipFileName.lastIndexOf("/"));
        } else {
            baseDir = zipFileName.substring(0, zipFileName.lastIndexOf("\\"));
        }
        baseDir += File.separator;
        //System.out.println(baseDir);
        Vector names = new Vector();
        BufferedOutputStream dest = null;
        FileInputStream fis = new 
        FileInputStream(zipFileName);
        ZipInputStream zis = new 
        ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry = null;
        while((entry = zis.getNextEntry()) != null) {
            String fileName = baseDir+entry.getName();
            // make sure the separators are consistent
            if (File.separatorChar == '/') { 
                fileName = fileName.replace('\\', File.separatorChar);
            } else if (File.separatorChar == '\\') { 
                fileName = fileName.replace('/', File.separatorChar);
            }
            //System.out.println("File: "+fileName);
            File newFile = new File(fileName);
            if (!newFile.exists()) {
                String parent = newFile.getAbsolutePath();
                parent = parent.substring(0, parent.lastIndexOf(File.separator));
                File parentFile = new File(parent);
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                newFile.createNewFile();
            }
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(newFile);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
            names.add(fileName);
        }
        zis.close();
        fileNames = new String[names.size()];
        names.toArray(fileNames);
        return fileNames;
    }
    
    /**
     * Zips the file retaining it's original file name
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean zipFile(String fileName) throws IOException {
        File targetFile = new File(fileName);
        if (targetFile == null || !targetFile.exists()) {
            throw new IOException("target ["+fileName+"] doesn't exist!");
        }
        if (!targetFile.canRead()) {
            throw new IOException("target ["+fileName+"] can't be read!");
        }
        boolean result = false;
        byte[] buf = new byte[1024];
        // validate some stuff
        File zipFile = new File(fileName+".zip");
        if (zipFile.exists()) {
            zipFile.delete();
        }
        zipFile.createNewFile();
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        FileInputStream in = new FileInputStream(targetFile.getAbsolutePath());
        out.putNextEntry(new ZipEntry(targetFile.getName()));
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
        out.close();
        // delete the source
        if (targetFile.delete()) {
            // rename to the original file
            result = zipFile.renameTo(targetFile);
        } else {
            zipFile.delete();
        }
        return result;
    }
    
}
