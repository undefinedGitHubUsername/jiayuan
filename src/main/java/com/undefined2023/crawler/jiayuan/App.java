package com.undefined2023.crawler.jiayuan;

public class App {

	public static void multithread() {
		int count = 0;
		while (count++ != Conf.THREADS) {
			Thread t = new Thread(new Spiderleg());
			t.start();
		}
	}
}
