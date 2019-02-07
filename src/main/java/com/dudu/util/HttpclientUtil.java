package com.dudu.util;


import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.dudu.tools.StringUtil;

public class HttpclientUtil {
	
	public static Document requestUrl(int retry,String method,String url, HashMap<String, String> cookies,HashMap<String, String> data) {
		Connection con = Jsoup.connect(url);
		String userAgent="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E)" ;
		if(data!=null) {
		    con.header("User-Agent", userAgent).cookies(cookies).data(data);
		}
		else {
			con.header("User-Agent", userAgent).cookies(cookies);
		}
		Document doc = null;
	    	 for(int i=1;i<=retry;i++) {
	    		 if(doc==null) {
	    			 try {
				    		 if (method.equals("get")) {
					    		 doc = con.get();
							} else if(method.equals("post")) {
								 doc=con.ignoreContentType(true).post();
							}
	    			 } catch (IOException e) {
	    				 //e.printStackTrace();
	    				  doc=null;
	    				  String page="";
	    				  if(url.contains("pageNum")) {
	    					  page=StringUtil.getNumInString(url.substring(url.indexOf("pageNum=")+8,url.indexOf("pageNum=")+11));
	    				  }
	    		    	  System.err.println(page+":===============================================================第"+i+"次请求过程中出错.."+url);
	    		    	  continue;
	    		     }
	    		 }
	    		 if(doc!=null) {
	    			 //System.out.println("=====================================================================第"+i+"次请求成功!"+url);
	    			 return doc;
	    		 }
	    	 }	     
	     return doc;
		
	}

}
