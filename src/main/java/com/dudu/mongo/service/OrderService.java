package com.dudu.mongo.service;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.dudu.mongo.domin.UpdateRecord_ContractOverview;
import com.dudu.mongo.domin.UpdateRecord_ContractOverview.UpdateRecord_Contract;
import com.dudu.mongo.domin.合同;
import com.dudu.mongo.domin.合同.历史记录;
import com.dudu.mongo.domin.合同.贷款信息;
import com.dudu.mongo.domin.客户;
import com.dudu.mongo.domin.客户.other;
import com.dudu.mongo.domin.房间.roomother;
import com.dudu.mongo.domin.认购;
import com.dudu.mongo.service.ContractService;
import com.dudu.mongo.domin.房间;
import com.dudu.tools.StringUtil;
import com.dudu.util.HttpclientUtil;
import com.mysql.fabric.xmlrpc.base.Array;

@Service("orderService")

public class OrderService {
	static  int retry=5;
	static int totalPage=-1;
	static int totalCount=-1;
    static String initurl="http://crm.yango.com.cn:8060/_grid/griddata.aspx?xml=/Slxt/XSXC/Order_Grid1.xml&gridId=appGrid&sortCol=QSDate&sortDir=descend&vscrollmode=0&multiSelect=1&selectByCheckBox=0&filter=&processNullFilter=1&customFilter=(2=2)&customFilter2=&dependencySQLFilter=&location=&pageNum=1&pageSize=100&showPageCount=1&appName=Default&application=&cp=";   
    static String tempurl="";
    static HashMap<String, String> cookies = null;
    static long lastlogin=0;
	public static List<认购> queryOrder(int days) {
		 
		int  state=login(3, "denglimeng", "brightdlm100");
	     if(state<0) {
	    	System.err.println("以账号denglimeng登陆失败！");
	    	  state=login(3, "hupingping", "qaz123");
	    	 if(state<0) {
	    		 System.err.println("以账号hupingping登陆失败！运行结束。");
	    		 return null;
	    	 }
	     }
	     return getOrder( days);
		//compareAll(args);
        }
	
	public static int login(int retry,String username,String password)  {
		      long startTime=System.currentTimeMillis();
		      if ((startTime-lastlogin)<1000*60*25&&tempurl.length()>0) {
				return 1;
			} 
		      lastlogin=startTime;
		       System.out.println("正在登陆....");
		try {
			    
				String urlLogin = "http://crm.yango.com.cn:8060/Default2.aspx";
				Connection connect = Jsoup.connect(urlLogin);
				// 伪造请求头
			    connect.header("User-Agent",
			            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
			    Response res=null;
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
					return -1;
				}
				HashMap<String, String> data=new HashMap<String, String>();
				data.put("usercode", username);
				data.put("psw", Base64.getEncoder().encodeToString(password.getBytes()));
			    Document doc1=HttpclientUtil.requestUrl(retry, "post", "http://crm.yango.com.cn:8060/Default_Login.aspx", cookies, data);
			    if(doc1==null) {
			    	System.err.println("无法登陆,操作无法继续！");
			    	return -1;
			    }
			   // System.out.println(JSON.toJSONString(cookies));
			    //
			    //String tempurl="http://crm.yango.com.cn:8060/Slxt/XSXC/Contract_Grid.aspx?xml=%2FSlxt%2FXSXC%2FContract_Grid1.xml&filter=%3Centity%20name%3D%22es_Contract%22%20primarykey%3D%22ContractGUID%22%3E%3Cfilter%20type%3D%22and%22%3E%3Cfilter%20type%3D%22and%22%2F%3E%3Cfilter%20type%3D%22and%22%3E%3Ccondition%20operator%3D%22in%22%20attribute%3D%22ProjGUID%22%20value%3D%227e934495-6d31-e611-b025-005056b97936%2C4349594d-6f31-e611-b025-005056b97936%2Ce576f3d0-6d31-e611-b025-005056b97936%2Cea699b76-6f31-e611-b025-005056b97936%2Ca2988109-e4bd-e611-adee-005056b97936%2C56d554a2-6f31-e611-b025-005056b97936%2C3a040780-b11e-4b9f-9a66-9596855ac0a7%2Cd5b48ba5-4c45-450f-a43b-a0f9337c820e%2C384e75c8-8b9d-4b5f-ab76-fa0c108fec46%2C19f8f8cb-9d71-4ad3-be10-07b06ef65e62%2C58180e4c-7852-4d31-9c1c-30c6dd2e0e6c%2Cd2d91470-858c-4828-b15b-6be4b515ad1e%2Cb48f81f6-2f94-45c3-9aa2-00ca8ce8b208%2C1c746419-1dca-46eb-a2c5-1c089150ec5f%2C165c1598-e3bd-e611-adee-005056b97936%2C09c6bcc3-e3bd-e611-adee-005056b97936%2C91c0411a-5da7-49fe-b747-4cde5a709843%2Cd92370e0-1cbd-4921-b631-510ce5cb767e%2Ce9c316ff-dfbd-e611-adee-005056b97936%2C7398ab4f-08b8-413b-a2be-8ea916967a1f%2C6f78bffb-1202-466a-9210-fbae4df451af%2Cd9d0402d-e4bd-e611-adee-005056b97936%2Cef21ae00-274f-4506-9b21-f20acd1752bc%2Ce85d8ba3-67e0-4587-abba-3009d1f2f9cc%2C0e81f72e-2a8b-4ba6-9dd5-8a897eda3612%2C7eb0f4db-5047-4c53-991f-41178d68c576%2C9b5b1527-4646-4199-8f15-75cf6ea6c0aa%2Ccf6e52ec-5b7a-46bb-98e0-9a07492cfc10%2C5a91efd1-9b3e-40e9-8a1b-f3113a87ba6c%2C4da0b5eb-8a4e-49c3-acf9-be21b2cdc788%22%2F%3E%3C%2Ffilter%3E%0D%0A%3C%2Ffilter%3E%3C%2Fentity%3E";
			    String tempValueUrl="http://crm.yango.com.cn:8060/Slxt/XSXC/Order_Grid.aspx?xml=%2FSlxt%2FXSXC%2FOrder_Grid1.xml&filter=%3Centity%20name%3D%22es_Order%22%20primarykey%3D%22OrderGUID%22%3E%3Cfilter%20type%3D%22and%22%3E%3Cfilter%20type%3D%22and%22%2F%3E%3Cfilter%20type%3D%22and%22%3E%3Ccondition%20operator%3D%22in%22%20attribute%3D%22ProjGUID%22%20value%3D%227e934495-6d31-e611-b025-005056b97936%2C4349594d-6f31-e611-b025-005056b97936%2Ce576f3d0-6d31-e611-b025-005056b97936%2Cea699b76-6f31-e611-b025-005056b97936%2Ca2988109-e4bd-e611-adee-005056b97936%2C56d554a2-6f31-e611-b025-005056b97936%2C3a040780-b11e-4b9f-9a66-9596855ac0a7%2Cd5b48ba5-4c45-450f-a43b-a0f9337c820e%2C384e75c8-8b9d-4b5f-ab76-fa0c108fec46%2C19f8f8cb-9d71-4ad3-be10-07b06ef65e62%2C58180e4c-7852-4d31-9c1c-30c6dd2e0e6c%2Cd2d91470-858c-4828-b15b-6be4b515ad1e%2Cb48f81f6-2f94-45c3-9aa2-00ca8ce8b208%2C1c746419-1dca-46eb-a2c5-1c089150ec5f%2C165c1598-e3bd-e611-adee-005056b97936%2C09c6bcc3-e3bd-e611-adee-005056b97936%2C91c0411a-5da7-49fe-b747-4cde5a709843%2Cd92370e0-1cbd-4921-b631-510ce5cb767e%2Ce9c316ff-dfbd-e611-adee-005056b97936%2C7398ab4f-08b8-413b-a2be-8ea916967a1f%2C6f78bffb-1202-466a-9210-fbae4df451af%2Cd9d0402d-e4bd-e611-adee-005056b97936%2Cef21ae00-274f-4506-9b21-f20acd1752bc%2Ce85d8ba3-67e0-4587-abba-3009d1f2f9cc%2C0e81f72e-2a8b-4ba6-9dd5-8a897eda3612%2C7eb0f4db-5047-4c53-991f-41178d68c576%2C9b5b1527-4646-4199-8f15-75cf6ea6c0aa%2Ccf6e52ec-5b7a-46bb-98e0-9a07492cfc10%2C5a91efd1-9b3e-40e9-8a1b-f3113a87ba6c%2C4da0b5eb-8a4e-49c3-acf9-be21b2cdc788%22%2F%3E%3C%2Ffilter%3E%0D%0A%3C%2Ffilter%3E%3C%2Fentity%3E" ;
			    Document doc2=HttpclientUtil.requestUrl(retry, "get", tempValueUrl, cookies, null);
			    if(doc2==null) {
			    	System.err.println("无法获取TempValue,操作无法继续!");
			    	return -1; 
			    }
			    String filter=doc2.getElementById("txtFilter").attr("value");
			    tempurl=initurl.replace("filter=", "filter="+URLEncoder.encode(filter,"utf-8"));
			    //System.out.println(HttpclientUtil.requestUrl(1,"get", listurl, cookies,null));
			    System.out.println("登陆成功！");
				return 1;
		}catch (Exception e) {
			System.err.println("登陆过程中出错！");
			return -1;
		}finally {
			long endTime=System.currentTimeMillis();
			System.out.println("登陆操作结束，用时:"+StringUtil.formatDuring(endTime-startTime));
		}
	}
	
	public static List<认购> getOrder(int days2) {
		List<认购>list=new ArrayList<>();
		getOrderTotal();
		if(totalPage<=0) {
       	 System.err.println("无法获取到总页数,操作无法继续进行...");
            return null ;
        }
		 for(int i=1;i<=totalPage;i++) {
				final int j=i;  
			    String listurl=tempurl.replace("pageNum=1", "pageNum="+j);
		        //System.out.println(listurl);
		        Document doc=HttpclientUtil.requestUrl(retry,"get", listurl, cookies, null);
		        //System.out.println(doc);
		       if(doc==null) {
		    	   System.err.println("-----请求第"+j+"页失败,已放弃:"+listurl);
		    	   //overview.failureLog.add("第"+j+"页获取失败");
		    	   return null;
		       }else {
		    	   //
		    	   //order.pagein=j;
		    	   //order=getBasicInfo(order,doc);
		    	   Elements trs=doc.getElementById("gridBodyTable").select("tr");
		    	  // System.out.println(trs);
		    	   for (Element tr : trs) {
		    		   认购 order=null;
		    		   order=new 认购();
		    		   order.insertDate=new Date();
		    		   order.oid=tr.attr("oid");
		    		   order.projguid=tr.attr("projguid");
		    		   order.tradeguid=tr.attr("tradeguid");
		    		   Elements tds=tr.getElementsByTag("td");
		    		   order.房间=tds.get(1).text();
		    		   order.客户类型=tds.get(3).text();
		    		   order.客户名称=tds.get(2).text();
		    		   order.合同编号=tds.get(4).text();
		    		   order.合同签订日期=tds.get(5).text();
		    		   order.合同总价=tds.get(6).text();
		    		   order.税额=tds.get(8).text();
		    		   order.税率=tds.get(9).text()+"%";
		    		   order.付款方式=tds.get(10).text();
		    		   order.业务员=tds.get(11).text();
		    		   order.订单类型=tds.get(12).text();
		    		   order.审核人=tds.get(13).text();
		    		   order.是否交足定金=tds.get(14).text();
		    		   order.逾期未签约原因=tds.get(15).text();
		    		   Date orderDate=StringUtil.StringToDate(order.合同签订日期);
		    		   int days = (int) ((new Date().getTime()-orderDate.getTime() ) / (1000*3600*24));
		    		   //System.out.println(days);
		    		   
		    		   if(days>=days2) {
		    			  list.add(order);
		    		   }  
			        }
		    	   }
    	     }
		   return list;
       }
	
	
public static void getContractDetail(int j,UpdateRecord_ContractOverview overview, 合同 order, String detailurl) throws Exception {
	    try {
	    //System.out.println("--------------------正在获取第"+j+"页,"+order.客户名称+":"+order.房间+"的合同详情......");
		Document doc=HttpclientUtil.requestUrl(retry,"get",detailurl, cookies, null);
		if(doc==null) {
			System.err.println("第"+j+"页,"+order.客户名称+":"+order.房间+"的合同详情获取失败!");
			overview.failureLog.add("第"+j+"页,"+order.客户名称+":"+order.房间+"的合同详情获取失败!");
		}else {
			processDetail(order,doc,overview.failureLog);
		}
	    }catch (Exception e) {
			throw new Exception("获取合同详情失败");
		}
	}




	
	
	private static void compareWithDB(int j, ContractService contractService, MongoTemplate mongoTemplate, 合同 order, UpdateRecord_ContractOverview overview) throws Exception {
       try { 
    	   合同 existContract=contractService.getContract(order.房间);
    	   if( existContract==null) {
    		    UpdateRecord_Contract updateinfo=new UpdateRecord_Contract(order);
	   			contractService.insert(order);
	   			updateinfo.type="insert";
	   			updateinfo.pagein=j;
	   			overview.updatequeue.add(updateinfo);
	   			overview.totalinsert.incrementAndGet();
	   			System.out.println("(第"+j+"页)"+"成功插入:"+order.客户名称+":"+order.房间+",已向数据库插入"+overview.totalinsert+"条信息,已更新"+overview.totalupdate);
    	   }else if(existContract.联系电话==null||existContract.客户信息==null||existContract.房间信息==null) {
    		    mongoTemplate.remove(existContract);
    		    UpdateRecord_Contract updateinfo=new UpdateRecord_Contract(order);
	   			contractService.insert(order);
	   			updateinfo.type="update";
	   			updateinfo.pagein=j;
	   			overview.updatequeue.add(updateinfo);
	   			overview.totalinsert.incrementAndGet();
	   			System.out.println("成功更新:"+order.客户名称+":"+order.房间+",已向数据库插入"+overview.totalinsert+"条信息,已更新"+overview.totalupdate);
    	   }
    	    
			
		/*if(contractService.getContract(order.房间)==null) {
			UpdateRecord_Contract updateinfo=new UpdateRecord_Contract(order);
			contractService.insert(order);
			updateinfo.type="insert";
			overview.updatequeue.add(updateinfo);
			overview.totalinsert.incrementAndGet();
		}else {
			UpdateRecord_Contract updateinfo=new UpdateRecord_Contract(order);
			contractService.remove(order.房间);
			contractService.insert(order);
			updateinfo.type="insert";
			overview.updatequeue.add(updateinfo);
			overview.totalinsert.incrementAndGet();
		}*/
	   }catch (Exception e) {
		   e.printStackTrace();
		throw new Exception("与数据库比对过程中出错!");
	}
   }



	private static void processDetail(合同 order, Document doc, ConcurrentLinkedQueue<String> failureLog) throws Exception {
		 try {
				/*
		        基本信息
		    */
		order.状态=doc.getElementById("leftStatus").text();
		//Element table11=doc.getElementById("Section11");
		//System.out.println(doc);
		order.联系电话=doc.getElementById("txtLxdh").attr("value");
		if(StringUtil.isNull(order.联系电话)) {
		    failureLog.add("获取联系电话失败:"+order.客户名称+":"+order.房间);
		}
		order.成交途径大类=doc.getElementById("CJTJDL").attr("value");
		order.成交途径子类=doc.getElementById("CJTJZL").attr("value");
		order.房屋结构=doc.select("input[name=appForm_RoomStru]").get(0).attr("value");
		order.面积状态=doc.select("input[name=AreaStatus]").get(0).attr("value");
		order.建筑面积=doc.select("input[name=BldArea]").get(0).attr("value");
		order.套内面积=doc.select("input[name=TnArea]").get(0).attr("value");
		order.建筑单价=doc.select("input[name=Price]").get(0).attr("value");
		order.套内单价=doc.select("input[name=TnPrice]").get(0).attr("value");
		order.计价方式=doc.select("input[name=CalMode]").get(0).attr("value");
		order.标准总价=doc.select("input[name=Total]").get(0).attr("value");
		order.付款方式=doc.select("span[name=PayformName]").get(0).attr("value");
		order.折扣=doc.select("input[name=DiscntValue]").get(0).attr("value")+"%";
		order.建筑成交单价=doc.select("input[name=BldCjPrice]").get(0).attr("value");
		order.套内成交单价=doc.select("input[name=TnCjPrice]").get(0).attr("value");
		order.折扣说明=doc.select("input[name=DiscntRemark]").get(0).attr("value");
		order.房间总价=doc.select("input[name=RoomTotal]").get(0).attr("value");
								/*
					            装修信息
					        */
		//Element SectionZx=doc.getElementById("SectionZx");
		order.装修标准=doc.select("span[name=appForm_ZxBz]").get(0).attr("value");
		order.装修单价=doc.select("input[name=ZxPrice]").get(0).attr("value");
		order.装修是否并入合同=doc.select("input[name=IsZxkbrht]").get(0).attr("value");
		order.装修总价=doc.select("input[name=ZxTotal]").get(0).attr("value");
									/*
						            附属房间
						        */
		//Element tableFsfj=doc.getElementById("SectionFsfj");
		order.附属房间=doc.select("input[name=appForm_FsTs]").get(0).attr("value");
		order.附属房间总价=doc.select("input[name=FsTotal]").get(0).attr("value");
									/*
						            摘要
						        */
		//Element Section4=doc.getElementById("Section4");
		order.合同编号=doc.select("input[name=ContractNO]").get(0).attr("value");
		order.合同总价=doc.select("input[name=RmbHtTotal]").get(0).attr("value");
		order.应收定金=doc.select("input[name=Earnest]").get(0).attr("value");
		order.税额=doc.select("input[name=TaxAmount]").get(0).attr("value");
		order.税率=doc.select("input[name=appForm_TaxRate]").get(0).attr("value")+"%";
		order.合同签订日期=doc.select("input[name=QSDate]").get(0).attr("value");
		order.交房日期=doc.select("input[name=JFDate]").get(0).attr("value");
		order.合同备案号=doc.select("input[name=BaNo]").get(0).attr("value");
		order.备案日期=doc.select("input[name=BaDate]").get(0).attr("value");
		order.违约金=doc.select("input[name=DefaultAmount]").get(0).attr("value");
		
		//Element SectionDk=doc.getElementById("SectionDk");
		贷款信息 dkinfo=new 贷款信息();
		dkinfo.按揭银行=doc.select("input[name=AjBank]").get(0).attr("value");
		dkinfo.按揭贷款=doc.select("input[name=AjTotal]").get(0).attr("value");
		dkinfo.公积金银行=doc.select("input[name=GjjBank]").get(0).attr("value");
		dkinfo.公积金贷款=doc.select("input[name=GjjTotal]").get(0).attr("value");
		order.贷款信息=dkinfo;
		
		//Element Section1=doc.getElementById("Section1");
		历史记录 history=new 历史记录();
		history.创建人=doc.select("input[name=CreatedBy]").get(0).attr("value");
		history.创建时间=doc.select("input[name=CreatedOn]").get(0).attr("value");
		history.审核人=doc.select("input[name=AuditBy]").get(0).attr("value");
		history.审核时间=doc.select("input[name=AuditingDate]").get(0).attr("value");
		history.最后修改人=doc.select("input[name=LastMender]").get(0).attr("value");
		history.最后修改时间=doc.select("input[name=ModiDate]").get(0).attr("value");
		order.历史记录=history;
		
		ConcurrentLinkedQueue <客户> pInfoQueue=new ConcurrentLinkedQueue <客户>();
		Elements purls=doc.getElementById("_td_hylCstAllGUID").getElementsByTag("a");
		for (Element e : purls) {
			客户 pinfo=new 客户();
			pinfo.oid=e.attr("oid");
			pinfo.url="http://crm.yango.com.cn:8060/Slxt/XSZDH/Khgl_Kh_Edit.aspx?mode=3&oid=&funcid=01010203&isrequiredfiled=1".replace("oid=", "oid="+pinfo.oid);
			pinfo.name=e.text();
			//System.out.println("******************************正在读取客户信息:"+order.房间+":"+pinfo.name);
			getPeoPleInfo(pinfo,failureLog);
			pInfoQueue.add(pinfo);
	    }
	order.客户信息=pInfoQueue;
	ConcurrentLinkedQueue <房间> rInfoQueue=new ConcurrentLinkedQueue <房间>();
	Elements rurls=doc.getElementById("_td_hylRoomGUID").getElementsByTag("a");
	for (Element e : rurls) {
		房间 rinfo=new 房间();
		rinfo.oid=e.attr("oid");
		rinfo.url="http://crm.yango.com.cn:8060/Slxt/XMZB/RoomList_Edit.aspx?mode=3&oid=&funcid=01010105".replace("oid=", "oid="+rinfo.oid);
		rinfo.name=e.text();
		//System.out.println("******************************正在读取房间信息:"+order.房间+":"+rinfo.name);
		getRoomInfo(rinfo,failureLog);
		rInfoQueue.add(rinfo);
	  }
	 order.房间信息=rInfoQueue;
		 }catch (Exception e) {
			throw new Exception("获取合同细节失败!");
		}
	
  }



	private static void getRoomInfo(房间 rinfo, ConcurrentLinkedQueue<String> failureLog) {
		Document doc=HttpclientUtil.requestUrl(retry,"get",rinfo.url, cookies, null);
		if(doc==null) {
			failureLog.add("无法获取房间具体信息:"+rinfo.name+","+rinfo.url);
			return;
		}
		roomother other=new roomother();
		rinfo.name=doc.select("input[name=RoomInfo]").get(0).attr("value");
		rinfo.户型=doc.getElementById("HuXing").attr("value");
		rinfo.建筑面积=doc.select("input[name=BldArea]").get(0).attr("value");
		rinfo.套内面积=doc.select("input[name=TnArea]").get(0).attr("value");
		rinfo.type=doc.select("input[name=appForm_ProductType]").get(0).attr("value");
		rinfo.计价方式=doc.getElementById("DjArea").attr("value");
		rinfo.装修标准=doc.getElementById("Zxbz").attr("value");
		rinfo.装修单价=doc.select("input[name=ZxPrice]").get(0).attr("value");
		rinfo.装修款=doc.select("input[name=ZxTotal]").get(0).attr("value");
		rinfo.建筑单价=doc.select("input[name=Price]").get(0).attr("value");
		rinfo.套内单价=doc.select("input[name=TnPrice]").get(0).attr("value");
		rinfo.标准总价=doc.select("input[name=Total]").get(0).attr("value");
		rinfo.租售类型=doc.select("input[name=SaleRentable]").get(0).attr("value");
		rinfo.税额=doc.select("input[name=TaxAmount]").get(0).attr("value");
		rinfo.税率=doc.select("input[name=TaxRate]").get(0).attr("value")+"%";
		
		other.销售状态=doc.select("input[name=Status]").get(0).attr("value");
		other.房管局备案号=doc.select("input[name=PreparedNo]").get(0).attr("value");
		other.景观=doc.getElementById("Sight").attr("value");
		other.朝向=doc.getElementById("West").attr("value");
		other.房间结构=doc.select("input[name=RoomStru]").get(0).attr("value");
		other.面积状态=doc.select("input[name=AreaStatus]").get(0).attr("value");
		other.是否样板房=doc.select("input[name=IsTempletRoom]").get(0).attr("value");
		other.是否附属房产=doc.select("input[name=isAnnexe]").get(0).attr("value");
		other.收盘原因=doc.select("input[name=appForm_reason]").get(0).attr("value");
		
		rinfo.其他信息=other;
		
	
  }



	private static void getPeoPleInfo(客户 pinfo, ConcurrentLinkedQueue<String> failureLog) {
		Document doc=HttpclientUtil.requestUrl(retry,"get",pinfo.url, cookies, null);
		if(doc==null) {
			failureLog.add("无法获取客户具体信息:"+pinfo.name+","+pinfo.url);
			return;
		}
		Element appForm_tab0=doc.getElementById("appForm_tab0");
		other other=new other();
		
		pinfo.name=appForm_tab0.select("input[name=CstName]").get(0).attr("value");
		pinfo.type=appForm_tab0.select("input[name=CstType]").get(0).attr("value");
		pinfo.营业执照=appForm_tab0.select("input[name=BizLicence]").get(0).attr("value");
		pinfo.法人代表=appForm_tab0.select("input[name=Corporation]").get(0).attr("value");
		pinfo.证件类型=appForm_tab0.select("input[name=CardType]").get(0).attr("lastvalue");
		pinfo.证件号码=appForm_tab0.select("input[name=CardID]").get(0).attr("value");
		pinfo.birthday=appForm_tab0.select("input[name=BirthDate]").get(0).attr("value");
		pinfo.sex=appForm_tab0.select("input[name=Gender]").get(0).attr("value");
		pinfo.tel=appForm_tab0.select("input[name=appForm_CurrentMobile]").get(0).attr("value");
		pinfo.address=appForm_tab0.select("input[name=Address]").get(0).attr("value");
		
		other.联络人=appForm_tab0.select("input[name=FirstContact]").get(0).attr("value");
		other.国籍=appForm_tab0.select("input[name=Nationality]").get(0).attr("value");
		other.办公电话=appForm_tab0.select("input[name=OfficeTel]").get(0).attr("value");
		other.传真=appForm_tab0.select("input[name=Fax]").get(0).attr("value");
		other.住宅电话=appForm_tab0.select("input[name=HomeTel]").get(0).attr("value");
		other.email=appForm_tab0.select("input[name=Email]").get(0).attr("value");
		other.最佳联系方式=appForm_tab0.select("input[name=Preferred]").get(0).attr("lastvalue");
		other.籍贯=appForm_tab0.select("input[name=NativePlace]").get(0).attr("value");
		other.是否接受短信=appForm_tab0.getElementById("IsReceiveSMS").attr("IsReceiveSMS");
		other.工作单位=appForm_tab0.select("input[name=WorkAddr]").get(0).attr("value");
		other.公司电话=appForm_tab0.select("input[name=CompanyPhone]").get(0).attr("value");
		other.不接收的资料=appForm_tab0.select("input[name=RefuseInfo]").get(0).attr("value");
		other.锁定状态=appForm_tab0.select("input[name=appForm_IsLockChn]").get(0).attr("value");
		
		Element appForm_tab3=doc.getElementById("appForm_tab3");
		pinfo.工作区域=doc.getElementById("WorkArea").attr("value");
		pinfo.居住区域=doc.getElementById("HomeArea").attr("value");
		pinfo.婚姻状况=doc.getElementById("Marriage").attr("value");
		pinfo.家庭结构=appForm_tab3.select("input[name=Family]").get(0).attr("lastvalue");
		
		other.配偶姓名=appForm_tab3.select("input[name=SpouseName]").get(0).attr("value");
		other.教育程度=doc.getElementById("EduLevel").attr("value");
		other.收入层次=appForm_tab3.select("input[name=Earning]").get(0).attr("lastvalue");
		other.职业=appForm_tab3.select("input[name=Work]").get(0).attr("lastvalue");
		other.兴趣爱好=appForm_tab3.select("input[name=Xqah]").get(0).attr("value");
		other.感兴趣的主题活动=appForm_tab3.select("input[name=InterestTopic]").get(0).attr("lastvalue");
		
		Element appForm_tab4=doc.getElementById("appForm_tab4");
		pinfo.创建人=appForm_tab4.select("input[name=CreatedBy]").get(0).attr("value");
		pinfo.创建时间=appForm_tab4.select("input[name=CreatedOn]").get(0).attr("value");
		pinfo.修改人=appForm_tab4.select("input[name=ModifyBy]").get(0).attr("value");
		pinfo.修改时间=appForm_tab4.select("input[name=ModifyOn]").get(0).attr("value");
		
		pinfo.其他资料=other;
  }



	public static void getOrderTotal() {
		try {
		//String url="http://crm.yango.com.cn:8060/_grid/griddata.aspx?xml=%2fSlxt%2fXSXC%2fContract_Grid1.xml&gridId=appGrid&sortCol=QSDate&sortDir=descend&vscrollmode=0&multiSelect=1&selectByCheckBox=0&filter=%3cfilter+type%3d%22and%22%3e%3ccondition+attribute%3d%22Filter%22+operator%3d%22TempValue%22+value%3d%22c0f29898-089d-e811-97eb-005056b97936%22%2f%3e%3c%2ffilter%3e&processNullFilter=1&customFilter=(2%3d2)&customFilter2=&dependencySQLFilter=&location=&pageNum=1&pageSize=100&showPageCount=1&appName=Default&application=&cp=";
        HashMap<String, String> data=null;
        // map.put("mycrm_company","89c06b03-7430-e611-b025-005056b97936");  
        // map.put("mycrm_isendcompany","1");
        System.out.println("----------正在获取总页数及总记录数...");
        Document  doc=HttpclientUtil.requestUrl(retry,"get", tempurl, cookies, data);
        //System.out.println(doc);
        String rowcount=  doc.getElementById("gridBodyTable").attr("rowcount");
     	String pagecount=  doc.getElementById("gridBodyTable").attr("pagecount");
     	totalCount=Integer.parseInt(rowcount);
     	totalPage=Integer.parseInt(pagecount);
     	System.out.println(totalPage+"页/"+totalCount+"条");
		}catch (Exception e) {
			//e.printStackTrace();
			System.err.println("无法获取到总页数...");
		}
    }
	

}
