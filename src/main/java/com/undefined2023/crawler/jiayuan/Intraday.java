package com.undefined2023.crawler.jiayuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.undefined2023.base.FileUtil;

public class Intraday implements Job {

	private static final int NONEXISTENT_TRIGGER = 15;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String signupdateFilename = "signup_date";
		File f = new File(Conf.BASEPATH + signupdateFilename);
		String endId;
		try {
			endId = FileUtil.readLastLine(f).split(" ")[0];
		} catch (FileNotFoundException fnfe) {
			return;
		}
		int beginId = Integer.parseInt(endId) + 1;

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String today = Conf.BASEPATH + String.valueOf(sdf.format(new Date()));
		File todayFile = new File(today);
		
		File signupDateFile = new File ("signup_date");

		int nonexistent = 0;
		for (int id = beginId;; id++) {
			System.out.println(id);
			String strId = String.valueOf(id);
			User u = new User(strId);
			try {
				if (u.available()) {
					if (u.matched()) {
						String url = "http://www.jiayuan.com/" + strId;
						FileUtil.writeLine(url, todayFile);
					}
				} else {
					if (u.nonexistent())
						nonexistent++;
					if (nonexistent > NONEXISTENT_TRIGGER) {
						endId = String.valueOf(id - 15);
						FileUtil.writeLine(endId + " " + today, signupDateFile,
								true);
						return;
					} else {
						continue;
					}
				}
			} catch (NullPointerException npe) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				id--;
			}
		}
	}

}
