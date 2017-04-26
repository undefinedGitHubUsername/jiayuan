package com.undefined2023.base;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {

	public static Document urlToDoc(String url) {
		Document doc = null;
		try {
			doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
					.timeout(100000).ignoreHttpErrors(true).get(); // /
		} catch (IOException e) {
			if (doc == null || doc.equals(null)) {
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				urlToDoc(url);
			}
		}
		return doc;
	}

}
