package com.undefined2023.crawler.jiayuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Scanner;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.undefined2023.base.FileUtil;

public class OnlinePeeker implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Conf.loadTargets();
		if (Conf.TARGETS.size() == 0)
			return;

		SimpleDateFormat yymmdd = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat hhmm = new SimpleDateFormat("HHmm");
		Date date = new Date();
		String today = yymmdd.format(date);
		String now = hhmm.format(date);

		for (Entry<String, String> e : Conf.TARGETS.entrySet()) {
			String name = e.getKey();
			try {
				Runtime.getRuntime().exec("mkdir " + name);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			String id = e.getValue();
			User u = new User(id);
			if (u.available()) {
				boolean online = u.online();
				String dest = Conf.BASEPATH + name + "/" + today;
				File f = new File(dest);
				if (online) {
					FileUtil.writeLine("online " + now, f, true);
				} else {
					FileUtil.writeLine("offline " + now, f, true);
				}
				if (now.equals("2350")) { // the end of the day
					if (everOnline(f)) {
						FileUtil.writeLine(calcOnlineIndex(f), f, true);
					} else {
						FileUtil.writeLine("0.00", f, true);
					}
				}
			}
		}
	}

//	private String last7daysIndexAvg(String folder) {
//		File f = new File(System.getProperty("user.dir") + "/" + folder);
//		String[] filenames = f.list();
//		float index = (float) 0.0;
//		int count = 0;
//		// gen 170128(including today) 170127 170126 170125 170124 170123 170122
//		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//		Calendar c = Calendar.getInstance();
//		Calendar c_pivot = Calendar.getInstance();
//		c_pivot.setTime(new Date());
//		c_pivot.roll(Calendar.DATE, -7);
//		c.setTime(new Date());
//		for (String fn : filenames) {
//			try {
//				Date date = sdf.parse(fn.substring(0, 6));
//				c.setTime(date);
//				if (c.after(c_pivot)) {
//					if (!fn.endsWith("on"))
//						continue;
//					String curr = Conf.BASEPATH + folder + "/" + fn;
//					File currFile = new File(curr);
//					try {
//						index += Float.parseFloat(FileUtil
//								.readLastLine(currFile));
//					} catch (Exception e) {
//						continue; // ignore corrupt data
//					}
//					count++;
//				}
//			} catch (ParseException pe) {
//				pe.printStackTrace();
//			}
//		}
//		DecimalFormat df = new DecimalFormat("0.00");
//		return df.format(index / count);
//	}

	public static String calcOnlineIndex(File f) {
		int online = 0, line = 0;
		try {
			while (true) {
				line++;
				try {
					if (FileUtil.readLine(line, f).startsWith("on")) {
						online++;
					}
				} catch (IOException e) {
					System.out.println("file not found or ioexception");
				}
			}
		} catch (NullPointerException npe) {
			line--;
		}
		float onlineIndex = (float) online / line;
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(onlineIndex);
	}

	private boolean everOnline(File f) {
		try {
			int i = 1;
			while (true) {
				if (FileUtil.readLine(i++, f).startsWith("online")) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	// ##################### STATIC - UTILITIES #######################

//	private static void calcAllOnlineIndex() throws FileNotFoundException {
//		String target = scanInput();
//		String folderPath = System.getProperty("user.dir") + File.separator
//				+ target;
//		for (File f : FileUtil.getFilesInAscendingOrder(folderPath)) {
//			String fn = f.getName();
//			System.out.println(fn);
//			if (FileUtil.readLastLine(f).endsWith("2350")) {
//				String oi = calcOnlineIndex(f);
//				System.out.println(oi);
//				FileUtil.writeLine(oi, f, true);
//			}
//		}
//	}

	public static void main(String[] args) throws FileNotFoundException {
		OnlinePeeker.readAllOnlineIndex();
	}

	private static String scanInput() {
		Scanner s = new Scanner(System.in);
		System.out.println("please input :");
		String input = s.next();
		s.close();
		return input;
	}

	private static void readAllOnlineIndex() throws FileNotFoundException {
		String target = scanInput();
		File[] files = FileUtil.getFilesInAscendingOrder(System
				.getProperty("user.dir") + File.separator + target);
		Arrays.sort(files);
		for (File f : files) {
			String last = FileUtil.readLastLine(f);
			String date = f.getName();
			if (last.startsWith("o"))
				continue;
			else
				System.out.println(date + " " + last);
		}
	}

}
