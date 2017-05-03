package gov.utah.dts.det.admin.quartz;

import gov.utah.dts.det.ccl.dao.RuleDao;
import gov.utah.dts.det.ccl.model.Rule;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class QuartzJobInitializer implements InitializingBean {

	protected final Log log = LogFactory.getLog(getClass());
	private StdScheduler quartzScheduler;

	private String cronExpressionDaily = "0 0 4 ? * MON-FRI *";					// every day at 6am
	private String cronExpressionQuarterly = "0 0 6 1 JAN,APR,JUL,OCT ?"; 		// the first day of every quarter at 6am
	private String cronExpressionYearly = "0 0 6 1 JUL ?";					// first day of every month at 6am

	@Autowired
	private RuleDao ruleDao;

	public StdScheduler getQuartzScheduler() {
		return quartzScheduler;
	}

	@Autowired
	public void setQuartzScheduler(StdScheduler quartzScheduler) throws SchedulerException, ParseException {
		this.quartzScheduler = quartzScheduler;
	}
	
	private void scheduleJobs() throws ParseException, SchedulerException {

		if (true) {
			System.out.println("QuartzJobInitializer: cronExpressionDaily="+cronExpressionDaily); 
			System.out.println("QuartzJobInitializer: cronExpressionWeekly="+cronExpressionQuarterly);
			System.out.println("QuartzJobInitializer: cronExpressionYearly="+cronExpressionYearly);
		}

		List<Rule> rules = ruleDao.getAllRules();
		for (Rule rule : rules) {
			scheduleJob(rule);
		}

	}
	
	private String getJobName(Rule rule) {
		return "RulesImportJob_"+rule.getNumber();
	}

	public boolean deleteJob(Rule rule) throws SchedulerException, ParseException {
		quartzScheduler.deleteJob(getJobName(rule), getJobName(rule));
		return true;
	}

	public boolean scheduleJob(Rule rule) throws SchedulerException, ParseException {
		
		deleteJob(rule);
		
		if (rule.isActive() && rule.getDownloadFrequency() != null && rule.getDownloadUrl() != null) {

			JobDetail jobDetail = new JobDetail(getJobName(rule), getJobName(rule), ERulesImportJob.class);
			jobDetail.setDescription(rule.getId()+"");

			String _freq = cronExpressionYearly;
			if (rule.getDownloadFrequency().equals("D")) {
				_freq = cronExpressionDaily;
			} else if (rule.getDownloadFrequency().equals("Q")) {
				_freq = cronExpressionQuarterly;
			}

			CronTrigger trigger = new CronTrigger(getJobName(rule), getJobName(rule), _freq);
			quartzScheduler.scheduleJob(jobDetail, trigger);

			log.info("QuartzJobInitializer: scheduled: "+getJobName(rule)+
					" ruleId: "+jobDetail.getDescription()+
					" frequency["+rule.getDownloadFrequency()+" ("+_freq+")]");

			return true;
		}

		return false;
	}

	public RuleDao getRuleDao() {
		return ruleDao;
	}

	@Autowired
	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public String getCronExpressionDaily() {
		return cronExpressionDaily;
	}

	public void setCronExpressionDaily(String cronExpressionDaily) {
		this.cronExpressionDaily = cronExpressionDaily;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduleJobs();
	}

	public String getCronExpressionQuarterly() {
		return cronExpressionQuarterly;
	}

	public void setCronExpressionQuarterly(String cronExpressionQuarterly) {
		this.cronExpressionQuarterly = cronExpressionQuarterly;
	}

	public String getCronExpressionYearly() {
		return cronExpressionYearly;
	}

	public void setCronExpressionYearly(String cronExpressionYearly) {
		this.cronExpressionYearly = cronExpressionYearly;
	}

}