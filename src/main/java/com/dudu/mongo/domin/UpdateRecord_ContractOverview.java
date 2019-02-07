package com.dudu.mongo.domin;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UpdateRecord_ContractOverview_guiyang")
public class UpdateRecord_ContractOverview {
	public Date recDate;
	public final  AtomicInteger totalinsert;                        //用于记录总共新插入了多少条到数据库
	public final  AtomicInteger totalupdate;                        //用于记录总共更新了多少条到数据库
	public final ConcurrentLinkedQueue<String> failureLog;          //用于记录错误信息的多线程安全queue
	public final ConcurrentLinkedQueue<UpdateRecord_Contract> updatequeue;          //用于保存contract的多线程安全queue
	public final AtomicInteger totalignore;
	public  String timeused;
	public UpdateRecord_ContractOverview() {
		this.recDate=new Date();
		this.totalinsert=new AtomicInteger(0);
		this.totalupdate=new AtomicInteger(0);
		this.updatequeue=new ConcurrentLinkedQueue<UpdateRecord_Contract>();
		this.failureLog=new ConcurrentLinkedQueue<String>(); 
		this.totalignore=new AtomicInteger(0);
	}
	
	
	public static class UpdateRecord_Contract{
		public String room;
		public String name;
		public String telephone;
		public 合同 contract;
		public String type;
		public int pagein;
		
		public UpdateRecord_Contract(合同 contract) {
			this.room=contract.房间;
			this.name=contract.客户名称;
			this.telephone=contract.联系电话;
		}
	}
		
	}