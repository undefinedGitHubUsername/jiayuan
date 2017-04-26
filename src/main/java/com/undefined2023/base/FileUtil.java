package com.undefined2023.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileUtil {

	public static void idToUrlFormater() throws IOException {
		File folder = new File(System.getProperty("user.dir"));
		FileWriter fw = null;
		for (File f : folder.listFiles()) {
			String fn = f.getName();
			if (fn.startsWith("1")) {
				File out = new File(System.getProperty("user.dir") + "/" + fn
						+ "_");
				FileReader fr = null;
				fw = new FileWriter(out);
				try {
					fr = new FileReader(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedReader br = new BufferedReader(fr);
				String l = "";
				while ((l = br.readLine()) != null) {
					if (!l.startsWith("http")) {
						l = "http://www.jiayuan.com/" + l;
						fw.write(l + "\n");
						fw.flush();
					}
				}
			}
		}
	}

	public static void removeNohup() {
		try {
			Runtime.getRuntime().exec("rm nohup.out");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// thread-safe writer
	public static synchronized void writeLine(String input, File f,
			boolean append) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(f, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter writer = new BufferedWriter(fw);
		try {
			writer.write(input);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLine(String input, File f) {
		writeLine(input, f, true);
	}

	public static String readLastLine(File f) throws FileNotFoundException {
		FileReader fr = null;
		if (!f.exists())
			throw new FileNotFoundException();
		fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		String result = "";
		try {
			while ((line = br.readLine()) != null) {
				result = line;
			}
			br.close();
		} catch (IOException e) {
		}
		return result;
	}

	public static String readLine(int lineNumber, File f) throws IOException {
		FileReader fr = null;
		fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		int count = 1;
		String line = "";
		while (((line = br.readLine()) != null) && (count++ != lineNumber)) {
		}
		br.close();
		return line;
	}

	// ############# ABSOLUTE PATH ###############

	public static File[] getFilesInAscendingOrder(String folderPath) {
		File folder = new File(folderPath);
		File[] fs = folder.listFiles();
		Arrays.sort(fs);
		return fs;
	}

}
