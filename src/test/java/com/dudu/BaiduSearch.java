package com.dudu;

import java.io.IOException;
import java.util.HashMap;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dudu.util.HttpclientUtil;

public class BaiduSearch {

	static String url="https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=9b4af15a00021f51&wd=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&pn=750&oq=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&ie=utf-8&rsv_pq=9b4af15a00021f51&rsv_t=c54f5RH7ipu4oBiNm7jNJe4zeWJ5IQ3v8AkpeyoqoyMqSsxohGK7R0RCYBM&bs=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&rsv_sid=undefined&_ss=1&clist=&hsug=&f4s=1&csor=8&_cr1=28204";
	
	public static void main(String[] args) throws IOException {
	
		int retry=3;
		HashMap<String, String>cookies=new HashMap<>();
		/*cookies.put("BAIDUID", "BA56FA9282B8726C9D3E2A49FC64E209:FG=1");
		cookies.put("BIDUPSID", "BA56FA9282B8726C9D3E2A49FC64E209");
		cookies.put("PSTM", "1533293322");
		cookies.put("BD_UPN", "12314753");
		cookies.put("BDUSS", "pxSTk5N2JVLWJmS2Z1cG5rMS1BdklQbUtuM1JFTTZyQ2FTUG9WWHkwTUpwS2xiQUFBQUFBJCQAAAAAAAAAAAEAAAAPGWckaGFwcHlkeWgxMDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAkXglsJF4Jbdm");
		Connection con = Jsoup.connect(url);
		String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" ;	
		con.header("User-Agent", userAgent).cookies(cookies);*/

		Document doc =HttpclientUtil.requestUrl(retry, "get", url, cookies, null);
		//System.out.println(doc);
		int total=Integer.parseInt(doc.select("strong>span.pc").get(0).text());
		System.out.println(total);
		// doc = con.get();
		//total=1;
		int m=0;
		//String url2="https://www.baidu.com/s?wd=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&pn=10&oq=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&ie=utf-8&usm=1&rsv_pq=d1ed2df900033f3a&rsv_t=06deufpJn8ZNSx4GHm8h8GXRAyVsDBcelr6AM0%2FhrEOsqPuZNEGRZGvkbhI";
		for(int i=0;i<total;i++) {
			System.out.println("=====================================================第"+(i+1)+"页-------------------------------------------------------------------------------------");
			String contexturl=url.replace("750",i+"0");
			//System.out.println(contexturl);
			//contexturl="https://www.baidu.com/s?ie=utf-8&mod=1&isbd=1&isid=9b4af15a00021f51&wd=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&pn=00&oq=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&ie=utf-8&rsv_pq=9b4af15a00021f51&rsv_t=c54f5RH7ipu4oBiNm7jNJe4zeWJ5IQ3v8AkpeyoqoyMqSsxohGK7R0RCYBM&bs=%E9%95%BF%E5%A1%98%E9%87%8C%E4%BA%8C%E5%B0%8F%20%E5%88%BA%E9%BC%BB&rsv_sid=undefined&_ss=1&clist=&hsug=&f4s=1&csor=8&_cr1=28204";
			Document context=HttpclientUtil.requestUrl(retry,"get", contexturl, cookies, null);
			Elements list=context.getElementById("content_left").getAllElements();
			for (Element element : list) {
				if(element.hasClass("c-container")) {
				System.out.println("-----------------------------------------------------第"+(m+1)+"条--------------------------------------------------------");
				m++;
				}
				System.out.println(element.html());
			}
			
			
		}
		
		
	}


}
