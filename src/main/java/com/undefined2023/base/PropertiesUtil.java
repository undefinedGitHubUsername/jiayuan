package com.undefined2023.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static void main(String[] args) {
		createProperties("/root/jiayuan/demo.properties");
	}

	public static void createProperties(String destPath) {
		Properties p = new Properties();
		OutputStream os = null;
		try {
			os = new FileOutputStream(destPath);
			p.setProperty("k1", "v1");
			p.setProperty("2", "k2");
			p.store(os, null);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public static Properties readProperties(String fn) {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(fn);
			p.load(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return p;
	}

	// edit & delete

}
