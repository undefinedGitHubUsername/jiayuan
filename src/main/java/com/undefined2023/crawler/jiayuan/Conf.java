package com.undefined2023.crawler.jiayuan;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.undefined2023.base.PropertiesUtil;

public class Conf {

	// for all
	static final String BASEPATH = System.getProperty("user.dir")
			+ File.separator;

	// for intraday
	private static final String INTRAYDAY_PROPERTIES_FILENAME = "config.properties";
	static int c;
	static int a;
	static int h;

	// for onlinepeeker
	static Map<String, String> TARGETS = new HashMap<String, String>();

	// for handsomephoto
	public static boolean runHsp;
	public static int from;
	public static int praiseAbove;
	public static int threads;
	public static int allocate;

	static {
		// for User class
		// read values from .properties or User class doesn't function well
		Properties p = PropertiesUtil
				.readProperties(INTRAYDAY_PROPERTIES_FILENAME);
		c = Integer.parseInt((String) p.get("charmValueUnder"));
		a = Integer.parseInt((String) p.get("ageUnder"));
		h = Integer.parseInt((String) p.get("heightAbove"));

		// for handsomephoto
		runHsp = Boolean.parseBoolean((String) p.get("runHsp"));
		from = Integer.parseInt((String) p.get("from"));
		praiseAbove = Integer.parseInt((String) p.get("praiseAbove"));
		threads = Integer.parseInt((String) p.get("threads"));
		allocate = Integer.parseInt((String) p.get("allocate"));
	}

	static void loadTargets() {
		// for onlinepeeker
		Properties p = PropertiesUtil
				.readProperties(INTRAYDAY_PROPERTIES_FILENAME);
		for (Object k : p.keySet()) {
			String key = k.toString();
			if (key.startsWith("OP"))
				TARGETS.put(key, p.get(key).toString().trim());
		}
	}

	public static final String HOMEPAGE_URL = "http://www.jiayuan.com/";
	public static final int THREADS = 500; // DUE TO MACHINE PRESSURE
	private static int BEGIN = 160000000;
	private static int END = 140000000;
	private static int CHUNK_SIZE = (BEGIN - END) / THREADS;
	public static final long SLEEP = 1000;

	public synchronized static int[] chunk() {
		if (BEGIN - CHUNK_SIZE > END) {
			int[] chunk = { BEGIN, BEGIN - CHUNK_SIZE };
			BEGIN -= (CHUNK_SIZE + 1);
			return chunk;
		} else {
			int[] chunk = { BEGIN, END };
			BEGIN = END = 0;
			return chunk;
		}
	}

}
