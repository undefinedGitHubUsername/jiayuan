package com.undefined2023.crawler.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpUrlConnectionExample {

	private List<String> cookies;
	private HttpsURLConnection conn;
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception {
		
		String url = "http://login.jiayuan.com/";
		String usercp = "http://usercp.jiayuan.com/";
		
		HttpUrlConnectionExample http = new HttpUrlConnectionExample();
		
		// make sure cookies is turned on
		CookieHandler.setDefault(new CookieManager());
		
		// 1. Send a "GET" request to extract the form's data
		String page = http.GetPageContent(url);
		String postParams = http.getFormParams(page, "18210565690", "1234567890");
		
		// 2. Construct above post's content 
		// and then send a POST request for authentication
//		http.sendPost(url, postParams);
		
		
		
	}
	
	private String getFormParams(String html, String username, String password) throws UnsupportedEncodingException {
		
		System.out.println("extract form's data");
		
		Document doc = Jsoup.parse(html);
		
		// form id 
//		Element loginform = doc
		
		return null;
	}

	private String GetPageContent(String url) throws Exception {
		
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
		
		conn.setRequestMethod("GET");
		
		conn.setUseCaches(false);
		
		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		if (cookies != null) {
			for (String c : this.cookies) {
				conn.addRequestProperty("Cookie", c.split(";", 1)[0]);
			}
		}
		int responseCode = conn.getResponseCode();
		System.out.println("/nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// Get the response cookies
		setCookies(conn.getHeaderFields().get("Set-Cookie"));
		
		return response.toString();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getCookies() {
		return cookies;
	}
	
	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}
}
