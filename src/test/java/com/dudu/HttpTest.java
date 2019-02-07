package com.dudu;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;



import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.dudu.tools.StringUtil;
import com.dudu.util.HttpclientUtil;

public class HttpTest {
public static void main(String[] args) throws Exception {
	login(3, "denglimeng","brightdlm100");
}

public static void getTempValue() {
	
}

public static void getPage() {
	String url="http://crm.yango.com.cn:8060/_grid/griddata.aspx?xml=%2fSlxt%2fXSXC%2fContract_Grid1.xml&gridId=appGrid&sortCol=QSDate&sortDir=descend&vscrollmode=0&multiSelect=1&selectByCheckBox=0&filter=%3cfilter+type%3d%22and%22%3e%3ccondition+attribute%3d%22Filter%22+operator%3d%22TempValue%22+value%3d%22c4bf5186-40a0-e811-97eb-005056b97936%22%2f%3e%3c%2ffilter%3e&processNullFilter=1&customFilter=(2%3d2)&customFilter2=&dependencySQLFilter=&location=&pageNum=463&pageSize=10&showPageCount=1&appName=Default&application=&cp=";
	System.out.println(StringUtil.getNumInString(url.substring(url.indexOf("pageNum=")+8,url.indexOf("pageNum=")+11)));
}

public static void login(int retry,String username,String password) throws Exception {
	String urlLogin = "http://crm.yango.com.cn:8060/Default2.aspx";
	Connection connect = Jsoup.connect(urlLogin);
	// 伪造请求头
    connect.header("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
    Response res=null;
    HashMap<String, String> cookies=null;
	for(int i=1;i<=retry;i++) {
		res = connect.ignoreContentType(true).method(Method.POST).execute();// 执行请求
		if(res==null) {
			System.err.println("第"+i+"次访问登陆页面失败！"+urlLogin);  
		}else {
			// 获取返回的cookie
		     cookies = (HashMap<String, String>) res.cookies();
		     break;
		}
	}
	
	if(cookies==null) {
		System.err.println("无法请求登陆cookies，操作无法继续进行！");
	}
	HashMap<String, String> data=new HashMap<String, String>();
	data.put("usercode", username);
	data.put("psw", Base64.getEncoder().encodeToString(password.getBytes()));
    Document document=HttpclientUtil.requestUrl(retry, "post", "http://crm.yango.com.cn:8060/Default_Login.aspx", cookies, data);
    System.out.println(JSON.toJSONString(cookies));
    //
    String tempurl="http://crm.yango.com.cn:8060/Slxt/XSXC/Contract_Grid.aspx?xml=%2FSlxt%2FXSXC%2FContract_Grid1.xml&filter=%3Centity%20name%3D%22es_Contract%22%20primarykey%3D%22ContractGUID%22%3E%3Cfilter%20type%3D%22and%22%3E%3Cfilter%20type%3D%22and%22%2F%3E%3Cfilter%20type%3D%22and%22%3E%3Ccondition%20operator%3D%22in%22%20attribute%3D%22ProjGUID%22%20value%3D%227e934495-6d31-e611-b025-005056b97936%2C4349594d-6f31-e611-b025-005056b97936%2Ce576f3d0-6d31-e611-b025-005056b97936%2Cea699b76-6f31-e611-b025-005056b97936%2Ca2988109-e4bd-e611-adee-005056b97936%2C56d554a2-6f31-e611-b025-005056b97936%2C3a040780-b11e-4b9f-9a66-9596855ac0a7%2Cd5b48ba5-4c45-450f-a43b-a0f9337c820e%2C384e75c8-8b9d-4b5f-ab76-fa0c108fec46%2C19f8f8cb-9d71-4ad3-be10-07b06ef65e62%2C58180e4c-7852-4d31-9c1c-30c6dd2e0e6c%2Cd2d91470-858c-4828-b15b-6be4b515ad1e%2Cb48f81f6-2f94-45c3-9aa2-00ca8ce8b208%2C1c746419-1dca-46eb-a2c5-1c089150ec5f%2C165c1598-e3bd-e611-adee-005056b97936%2C09c6bcc3-e3bd-e611-adee-005056b97936%2C91c0411a-5da7-49fe-b747-4cde5a709843%2Cd92370e0-1cbd-4921-b631-510ce5cb767e%2Ce9c316ff-dfbd-e611-adee-005056b97936%2C7398ab4f-08b8-413b-a2be-8ea916967a1f%2C6f78bffb-1202-466a-9210-fbae4df451af%2Cd9d0402d-e4bd-e611-adee-005056b97936%2Cef21ae00-274f-4506-9b21-f20acd1752bc%2Ce85d8ba3-67e0-4587-abba-3009d1f2f9cc%2C0e81f72e-2a8b-4ba6-9dd5-8a897eda3612%2C7eb0f4db-5047-4c53-991f-41178d68c576%2C9b5b1527-4646-4199-8f15-75cf6ea6c0aa%2Ccf6e52ec-5b7a-46bb-98e0-9a07492cfc10%2C5a91efd1-9b3e-40e9-8a1b-f3113a87ba6c%2C4da0b5eb-8a4e-49c3-acf9-be21b2cdc788%22%2F%3E%3C%2Ffilter%3E%0D%0A%3C%2Ffilter%3E%3C%2Fentity%3E";
    Document doc=HttpclientUtil.requestUrl(retry, "get", tempurl, cookies, null);
    String filter=doc.getElementById("txtFilter").attr("value");
    String listurl="http://crm.yango.com.cn:8060/_grid/griddata.aspx?xml=/Slxt/XSXC/Contract_Grid1.xml&gridId=appGrid&sortCol=QSDate&sortDir=descend&vscrollmode=0&multiSelect=1&selectByCheckBox=0&filter=&processNullFilter=1&customFilter=(2=2)&customFilter2=&dependencySQLFilter=&location=&pageNum=1&pageSize=10&showPageCount=1&appName=Default&application=&cp=";    listurl=listurl.replace("filter=", "filter="+URLEncoder.encode(filter));
    System.out.println(listurl);
    System.out.println(HttpclientUtil.requestUrl(1,"get", listurl, cookies,null));
    
}
}
