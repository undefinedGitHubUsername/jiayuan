package com.undefined2023.crawler.jiayuan;

import java.io.File;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.undefined2023.base.FileUtil;

/*
 * remote push to github notes:
 * 	don't forget to add github.com domain
 * 	input password along with the username "undefinedGitHubUsername"
 * 
 */
public class Quartz {

	private static Scheduler s = null;

	public static void main(String[] args) throws SchedulerException {
		s = StdSchedulerFactory.getDefaultScheduler();
		s.start();

		scheJob(Intraday.class, "intraday_trigger", "0 0 18 1/1 * ? *");
		scheJob(OnlinePeeker.class, "onlinepeeker_trigger",
				"0 0,10,20,30,40,50 * 1/1 * ? *");
		// scheJob(HandsomePhoto.class, "handsome_trigger", "0 0 8 1/1 * ? *");

		// one-time execute
		int v = 20;
		File file = new File(Conf.BASEPATH + "/shortGirls");
		File f = new File(Conf.BASEPATH + "charmingGuys");
		for (int id = 160000000; id < 163500000; id++) {
			User u = new User(String.valueOf(id));
			try {
				if (!u.hasPhoto())
					continue;
				// find all height-above-159-below-165 girls from 161 - 163
				if (u.female()) {
					if (u.charmValueUnder(v) && u.ageUnder(Conf.a)
							&& u.unmarried() && u.inBeijing()
							&& u.lessEducated() && u.rootNonbeijing()) {
						if (u.plainCharmValue() > v) {
							FileUtil.writeLine(
									Conf.HOMEPAGE_URL + String.valueOf(id),
									file, true);
						}
					}
				}
				// find charm guys
				else {
					if (u.available() && u.inBeijing() && u.ageUnder(30)) {
						if (u.plainCharmValue() > v) {
							FileUtil.writeLine(
									Conf.HOMEPAGE_URL + String.valueOf(id), f,
									true);
						}
					}
				}
			} catch (NullPointerException npe) {
				id--;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
		}

	}

	private static void scheJob(Class<?> clazz, String identity, String cron)
			throws SchedulerException {
		@SuppressWarnings("unchecked")
		JobDetail jd = JobBuilder.newJob((Class<? extends Job>) clazz).build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity(identity)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		s.scheduleJob(jd, t);
	}
}
