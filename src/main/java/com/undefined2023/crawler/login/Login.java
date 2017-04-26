package com.undefined2023.crawler.login;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Login {

	public static void main(String[] args) {
		
	}
	
	public static void tryLogin() {
		// String cookies =
		// "ip_loc=11; save_jy_login_name=15801691173; stadate1=150517557; myloc=11%7C1108; myage=23; mysex=m; myuid=150517557; myincome=30; jr_ident=1; PHPSESSID=c9c68b2388574abfe6dd523770019ecd; SESSION_HASH=15a2b1ea59d7ae110ee2fb50c047f379a7733581; view_m=1; REG_REF_URL=; mylevel=4; sl_jumper=%26cou%3D17; last_login_time=1482768017; main_search:151517557=%7C%7C%7C00; date_pop_151517557=1; IM_S=%7B%22IM_CID%22%3A6023563%2C%22svc%22%3A%7B%22code%22%3A0%2C%22nps%22%3A0%2C%22unread_count%22%3A%220%22%2C%22ocu%22%3A0%2C%22ppc%22%3A0%2C%22jpc%22%3A0%2C%22regt%22%3A%221460554936%22%2C%22using%22%3A%2233%2C2%2C40%2C%22%2C%22user_type%22%3A%2210%22%2C%22uid%22%3A151517557%7D%2C%22m%22%3A0%2C%22f%22%3A0%2C%22omc%22%3A0%7D; IM_CS=2; IM_ID=11; IM_TK=1482768228762; IM_M=%5B%5D; IM_CON=%7B%22IM_TM%22%3A1482768140231%2C%22IM_SN%22%3A11%7D; pop_time=1482767976995; __tkist=0; DATE_SHOW_LOC=11; DATE_SHOW_SHOP=1; DATE_FROM=tuichu; PROFILE=151517557%3Aundefined%3Am%3Aa2.jyimg.com%2Fd3%2Fdb%2Fe7dab8bea372fa1e4a2b551e7949%3A1%3A2%3A1%3Ae7dab8bea_7_avatar_p.jpg%3A1%3A1%3A750%3A10; RAW_HASH=w80pKAOlNVB1t3olidwTpB7HuJhCDVnFehofJxvaNZr8847-v5Oo9MOLreLhI8xqeYgu6Y7243QdY6O2sDsBMS-k31%2ArKhP9vkN3vDVD4I8b09Q.; COMMON_HASH=d3e7dab8bea372fa1e4a2b551e7949db; upt=Ayvh3c2GWqhtJ4nT0yzIftzG8%2Ahdj0dmHk8Sw45MMSXSUFcVdHS9DpCfu-fxm%2A%2AGavH16Nc4dcXaypkFKB7GzSWIBw..; user_access=1; pclog=%7B%22151517557%22%3A%221482768079821%7C1%7C0%22%7D";
		// HashMap<String, String> cookies = new HashMap<String, String>();
		// cookies.put("PHPSESSID", "c9c68b2388574abfe6dd523770019ecd");

		String url = "http://login.jiayuan.com/";
		String body = "name=15801691173&password=1234567890&validate_code=82787&remem_pass=on&_s_x_id=0ada6c8531891cb53cf5ce161818f3cc&ljg_login=1&m_p_l=1&channel=0&position=0";
		url += body;
		Document doc = null;
		try {
			doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
					.header("Accept-Language", "en-US")
					.header("Accept-Encoding", "gzip,deflate,sdch")
					.referrer("http://login.jiayuan.com/").post();
			// .cookies(cookies).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(doc.toString());

	}
}
