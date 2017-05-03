package gov.utah.dts.det.admin.quartz;

import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.AlertService;
import gov.utah.dts.det.ccl.service.RuleService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

public class ERulesImportJob extends QuartzJobBean {

	private int BUFFER = 2048;
	protected final Log log = LogFactory.getLog(getClass());
	private JobExecutionContext context;
	private RuleService ruleService;
	private String rulesDownloadWorkingDir;
	private String rulesDownloadUrl;
	private String rulesProcessedDir;
	
	private AlertService alertService;

	private Object getBean(String beanName) {
		try {
			ApplicationContext appContext = 
					(ApplicationContext) context.getScheduler().getContext().get("applicationContext");
			return appContext.getBean(beanName);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		this.context = context;
		
		String ruleId = context.getJobDetail().getDescription();

		ruleService = (RuleService) getBean("ruleService");
		
		Rule rule = ruleService.loadRuleById(new Long(ruleId));
		String ruleNumber = rule.getNumber();
		
		String workingDir = System.getProperty("java.io.tmpdir");
		if (!workingDir.endsWith(File.separator)) {
			workingDir += File.separator;
		}
		workingDir += "dhsLicensing/downloads/";
		File fileWorkingDir = new File(workingDir);
		
		if (!fileWorkingDir.exists()) {
			if (fileWorkingDir.mkdirs()) {
				log.info("download working directory created: "+fileWorkingDir.getAbsolutePath());
			} else {
				log.error("download working directory creation failed: "+fileWorkingDir.getAbsolutePath());
				throw new JobExecutionException("Could not create a download working directory in: "+
						System.getenv("java.io.tmpdir"));
			}
		}
		rulesDownloadWorkingDir = fileWorkingDir.getAbsolutePath();
		
		File fileProcessedDir = new File(fileWorkingDir.getAbsolutePath()+File.separator+"processed");
		if (!fileProcessedDir.exists()) {
			if (fileProcessedDir.mkdirs()) {
				log.info("processed directory created: "+fileProcessedDir.getAbsolutePath());
			} else {
				log.error("processed directory creation failed: "+fileProcessedDir.getAbsolutePath());
				throw new JobExecutionException("Could not create a download processed directory in: "+
						System.getenv("java.io.tmpdir"));
			}
		}
		rulesProcessedDir = fileProcessedDir.getAbsolutePath();
		
		rulesDownloadUrl = rule.getDownloadUrl();
		
		alertService = (AlertService) getBean("alertService");

		log.info("Starting...\nrulesDownloadUrl="+rulesDownloadUrl);
		log.info("rulesDownloadWorkingDir="+rulesDownloadWorkingDir);
		log.info("rulesProcessedDir="+rulesProcessedDir);
		
		if (!rulesDownloadWorkingDir.endsWith(File.separator))
			rulesDownloadWorkingDir += File.separator;
		if (!rulesProcessedDir.endsWith(File.separator))
			rulesProcessedDir += File.separator;
		
		String[] split = rulesDownloadUrl.split("/");
		ruleNumber = split[split.length-1];
		if (ruleNumber.toLowerCase().startsWith("r"))
			ruleNumber = ruleNumber.substring(1);
		if (ruleNumber.toLowerCase().endsWith(".zip"))
			ruleNumber = ruleNumber.substring(0, ruleNumber.length()-4);

		try {
			
			// the rules objects are repository beans (cacheble) so we need to wipe the slate before we start...
			ruleService.clearCache();
			
			reprocessFiles(rulesDownloadWorkingDir);
			
			cleanWorkingDir(rulesDownloadWorkingDir);

			String zipFile = downloadDataFile(rulesDownloadUrl, rulesDownloadWorkingDir);

			unpackDataFile(zipFile);
			
			Map<String, Object> procRecults = processRtfFiles(rulesDownloadWorkingDir, ruleNumber);
			
			// send a message to the sys admin so they know the import happened
			String msg = "Rules import processing results ...\n";
			Iterator<String> iterator = procRecults.keySet().iterator();
			while (iterator.hasNext()) {
				String file = iterator.next();
				Object _r = procRecults.get(file);
				if (_r instanceof int[]) {
					int[] r = (int[]) _r;
					msg += "<br> "+file + " - Sec/Sub: "+r[0]+"/"+r[1];
				} else {
					msg += "<br> "+file + " - "+_r;
				}
			}
			
			List<RoleType> roles = new ArrayList<RoleType>();
			roles.add(RoleType.ROLE_ADMIN_MANAGER);
			roles.add(RoleType.ROLE_SUPER_ADMIN);
			alertService.sendAlertToRole(roles, msg, new java.util.Date(), new java.util.Date());
			
			rule.setLastDownload(new java.util.Date());
			ruleService.saveRule(rule);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
			e.printStackTrace(System.err);
		}
	}
	
	private int reprocessFiles(String rulesDownloadWorkingDir) throws IOException {
		int r = 0;
		
		File dFile = new File(rulesDownloadWorkingDir+File.separator+"reprocess");
		
		if (!dFile.exists()) {
			dFile.mkdirs();
		}
		
		log.debug("starting RTF file re-processing for: "+dFile.getAbsolutePath());
		
		File[] list = dFile.listFiles();
		if (list == null)
			return r;
		for (int i = 0; i < list.length; i++) {
			if (list[i].getName().toLowerCase().endsWith(".rtf")) {
				String ruleNumber = list[i].getName();
				if (ruleNumber.startsWith("r")) 
					ruleNumber = ruleNumber.substring(1);
				ruleNumber = ruleNumber.substring(0, ruleNumber.indexOf("-"));
				int[] results = null;
				try {
					results = ruleService.processRuleFile(list[i], ruleNumber);
				} catch (Exception e) {
					System.err.println("Error ["+e.getMessage()+"] processing file ["+list[i].getAbsolutePath()+"]");
					e.printStackTrace(System.err);
					log.error(e);
				}
				archiveFile(list[i], results);
				r++;
			}
		}		
		
		log.info("re-processed "+r+" file(s)");
		
		return r;
	}
	
	@Transactional
	private Map<String, Object> processRtfFiles(String rulesDownloadWorkingDir, String ruleNumber) throws IOException {
		
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		File dFile = new File(rulesDownloadWorkingDir);
		if (dFile.exists() && dFile.canRead()) {
			File[] list = dFile.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].getName().toLowerCase().endsWith(".rtf")) {
					int[] _results = null;
					try {
						_results = ruleService.processRuleFile(list[i], ruleNumber);
						results.put(list[i].getName(), _results);
					} catch (Exception e) {
						log.error("Error ["+e.getMessage()+"] processing file ["+list[i].getAbsolutePath()+"]", e);
						e.printStackTrace(System.err);
						String msg = e.getMessage();
						if (e.getCause() != null) {
							msg = e.getCause().getMessage();
						}
						if (msg.length() > 100) 
							msg = msg.substring(0, 100)+"...";
						results.put(list[i].getName(), msg);
					}
					archiveFile(list[i], _results);
				}
			}
		}
		
		log.info("processed "+results.size()+" file(s)");
		
		return results;
	}
	
	private int unpackDataFile(String filePath) throws IOException {
		log.debug("unpacking zip file ["+filePath+"]");
		
		String[] fileList = ZipUtil.unzip(filePath);
		if (fileList != null) { 
			int r = fileList.length;
			log.info("unpacked "+r+" file(s) from ["+filePath+"]");
			return r;
		}
		return 0;
	}

	private String downloadDataFile(String targetUrl, String rulesDownloadWorkingDir) {

		String filePath = rulesDownloadWorkingDir + 
				(new SimpleDateFormat("yyyy-MM-dd_hh:ss")).format(new java.util.Date()) +
				".zip";
		
		log.info("calling target URL["+targetUrl+"] and writing download file ["+filePath+"]");

		try {
			URL url = new URL(targetUrl);

			FileOutputStream fos = new FileOutputStream(filePath);
			BufferedOutputStream output = new BufferedOutputStream(fos);
			InputStream in = new BufferedInputStream(url.openStream());

			byte[] buffer = new byte[BUFFER];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				output.write(buffer, 0, n);
			}

			in.close();
			output.flush();
			output.close();
			
			log.info("file download succeeded and processed w/o error");

			return filePath;

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return null;
	}

	/**
	 * Removes and RTF files from the supplied directory
	 * @param dir
	 * @return - the number of RTF files deleted
	 */
	private int cleanWorkingDir(String dir) {
		int r = 0;
		
		log.debug("starting working directory clean process...");

		File dFile = new File(dir);
		if (dFile.exists() && dFile.canRead()) {
			File[] list = dFile.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].getName().toLowerCase().endsWith(".rtf")) {
					if (list[i].delete()) {
						r++;
						log.debug("\tfile delete succeeded:"+list[i].getAbsolutePath());
					} else {
						log.debug("\tfile delete failed:"+list[i].getAbsolutePath());
					}
				}
			}
		}
		
		log.info("removed "+r+" files");

		return r;
	}
	
	private boolean archiveFile(File file, int[] results) throws IOException {
		
		if (results != null)
			log.debug("attempting to archive file ["+file+"] process success ? "+"_"+results[0]+"-"+results[1]);
		else 
			log.debug("attempting to archive file ["+file+"] process success ? error");

		// make sure we have somewhere to put the processed files
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File processedDir = new File(rulesProcessedDir+sdf.format(new java.util.Date()));
		if (!processedDir.exists()) {
			if (processedDir.mkdirs()) {
				log.debug("new directory created for archiving: "+processedDir);
			} else {
				log.error("new directory creation failed for archiving: "+processedDir);
				throw new IOException("Could not create archive direcotry: "+processedDir);
			}
		}
		
		// actually move the file
		File newFile = null;
		if (results == null) 
			newFile = new File(processedDir.getAbsolutePath()+File.separator+file.getName()+"_error");
		else {
			newFile = new File(processedDir.getAbsolutePath()+File.separator+file.getName()+"_"+results[0]+"-"+results[1]);
		}
		
		return file.renameTo(newFile);
	}
}
