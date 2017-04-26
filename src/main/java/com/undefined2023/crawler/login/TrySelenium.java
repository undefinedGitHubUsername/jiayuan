package com.undefined2023.crawler.login;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.undefined2023.base.FileUtil;

public class TrySelenium {

	private static WebDriver d = null;
	private static Navigation n = null;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		fin("170403");
		fin("170404");
		fin("170405");
		
//		String fn = calDate();
//		System.out.println(fn);
//		fin(fn);
	}
	
	private static String calDate() {
		// today's date
		
		// backward 14 days
		
		return null;
	}

	private static void fin(String fn) throws IOException, InterruptedException {

		String driverLoc = "/home/chrome/Downloads/chromedriver";
		// driverLoc = "C:\\Users\\hpuser\\Desktop\\chromedriver";
		System.setProperty("webdriver.chrome.driver", driverLoc);
		d = new ChromeDriver();

		String loginPage = "http://login.jiayuan.com/";
		n = d.navigate();
		n.to(loginPage);

		d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		try {
			WebElement email = d.findElement(By.cssSelector("#login_email"));
			email.clear();
			email.sendKeys("15801691173");// /////////////
			WebElement pass = d.findElement(By.cssSelector("#login_password"));
			pass.sendKeys("1234567890");
			d.findElement(By.cssSelector("#login_btn")).click();
		} catch (NoSuchElementException nsee) {
			System.out.println("already loged in");
		}

		Thread.sleep(5000); // wait to log, also react to anti-crawler strategy
		int n = 1;
		File f = new File(System.getProperty("user.dir") + File.separator + fn);
		while (n++ > 0) {
			String line = FileUtil.readLine(n, f);
			try {
				if (!line.startsWith("http"))
					continue;
			} catch (NullPointerException npe) {
				break; // end of line
			}
			if (navigateAndSendMes(line)) {
				System.out.println(line + " sent");
			} else {
				System.out.println(line);
			}

		}

		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		d.close();
		d.quit();

		System.out.println("fin");

	}

	private static boolean stillActive() {
		try {
			WebElement online = d
					.findElement(By
							.cssSelector("body > div.main_1000.bg_white.mt15 > div > div.member_box > div.pic_box > div.on_line.yh"));
			String on = online.getText();
			if (on.startsWith("目前")) {
				System.out.println("online");
				return true;
			} else
				return false;
		} catch (NoSuchElementException nsee) {
			WebElement offline = d
					.findElement(By
							.cssSelector("body > div.main_1000.bg_white.mt15 > div > div.member_box > div.pic_box > div.my_info.yh.not_online"));
			String off = offline.getText();
			if (off.contains("最近登录时间：2017-")) {
				System.out.println("online this year");
				return true;
			} else
				return false;
		}
	}

	private static boolean alreadySent() {
		WebElement span = d.findElement(By
				.cssSelector("#form_msg > div > p > span"));
		String text = span.getText();
		if (text.startsWith("您和")) {
			System.out.println("sent");
			return true;
		} else
			return false;
	}

	private static boolean navigateAndSendMes(String urlOrId) {
		String url;
		if (urlOrId.startsWith("http"))
			url = urlOrId;
		else
			url = "http://www.jiayuan.com/" + urlOrId;
		try {
			n.to(url);
		} catch(TimeoutException te) { // in case of slow connection
			navigateAndSendMes(urlOrId);
		}
		// should i use get() ?
		// n.refresh();

		try {
			if (!stillActive())
				return false;
		} catch (NoSuchElementException nsee) {
			return false;
		}

		d.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement sendEle = d
				.findElement(By
						.cssSelector("body > div.main_1000.bg_white.mt15 > div > div.member_box > div.member_info_r.yh > div.fn-clear.mt15 > a.member_btn2"));
		String js = sendEle.getAttribute("onclick");

		String regex = "http.+',";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(js);
		if (m.find())
			url = m.group(0);
		url = url.substring(0, url.length() - 2);

		try {
			n.to(url);
		} catch(TimeoutException te) { // in case of slow connection
			navigateAndSendMes(urlOrId);
		}
		
		if (alreadySent())
			return false;

		WebElement send;
		try {
			send = d.findElement(By.id("anniu_2"));
		} catch (NoSuchElementException nsee) {
			send = d.findElement(By.id("anniu_1"));
		}
		send.click();
		return true;
	}

}
