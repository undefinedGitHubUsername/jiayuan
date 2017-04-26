package com.undefined2023.crawler.login;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.undefined2023.crawler.jiayuan.Conf;
import com.undefined2023.crawler.jiayuan.User;

public class HandsomePhoto implements Runnable, Job {

	int begin, end;

	public HandsomePhoto(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		int from = Conf.from;
		int threads = Conf.threads;
		int allocate = Conf.allocate;
		int to = (threads * allocate) + from;
		System.out.println("to = " + to);
		for (int i = 1; i <= threads; i++) {
			Thread t = new Thread(new HandsomePhoto(from + 1, from + allocate));
			t.start();
			from += allocate;
		}
	}

	public void run() {
		if (!Conf.runHsp)
			return;
		for (int id = begin; id < end; id++) {
			User u = new User(String.valueOf(id));
			try {
				if (u.available() && u.inBeijing() && u.hasPhoto()
						&& !u.female()) {
					int c = u.charmValue();
					if (c > Conf.praiseAbove) {
						// deal with photos

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

}
