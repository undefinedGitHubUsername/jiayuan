package com.undefined2023.crawler.jiayuan;

import java.io.File;

public class Spiderleg implements Runnable {

	public void run() {
		int[] chunk = { 0, 1 };
		while (chunk[0] < chunk[1]) {
			chunk = Conf.chunk();
			Statistic s = new Statistic();
			String fn = String.valueOf(chunk[0]) + "-"
					+ String.valueOf(chunk[1]);
			File f = new File(Conf.BASEPATH + fn);
			try {
				s.start(chunk[0], chunk[1], f);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
