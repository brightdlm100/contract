package com.dudu;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;
import com.dudu.mongo.domin.合同;
import com.dudu.mongo.domin.客户;
import com.dudu.util.FileUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class mongodbTest {
	public static void main(String[] args) {
		  //String id="长沙阳光城尚东湾-2号地块二期-9栋-804";
		     ApplicationContext applicationContext= SpringApplication.run(Application.class,args); 
		    
		     //ContractService contractService=(ContractService) applicationContext.getBean("contractService"); 
			 MongoTemplate mongoTemplate=(MongoTemplate) applicationContext.getBean("mongoTemplate");	
			// String query=".*?"+"唐建军"+".*?";
		   //  System.out.println(JSON.toJSONString(mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("房间").regex(query),Criteria.where("客户名称").regex(query))), 合同.class)));

			// AgeDistance(mongoTemplate);
			 //RemoveError(mongoTemplate);
			  //getDiffName(mongoTemplate);
			// String Customer="邹志宇,刘家政,王松林,黄竹,王鹃,刘红英,谭小凤,罗院富,冀维,肖志红,李莺歌,彭玲丽,秦莉,孙轺军,贺小叶";
			 //getSeller(mongoTemplate,Customer);
			 getSum(mongoTemplate,true);
			 
		     ExitCodeGenerator exitCodeGenerators =new ExitCodeGenerator() {
	         @Override
				public int getExitCode() {
					// TODO Auto-generated method stub
					return 0;
				}
			} ;
			SpringApplication.exit(applicationContext, exitCodeGenerators);
	}

	public static void getSum(MongoTemplate template,boolean isExcludeCqr) {
		System.out.println("start..");
		Aggregation aggregation1=null;
		if(isExcludeCqr) {
			 aggregation1 = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("房间").regex("^((?!车位).)*$")),
				Aggregation.group("客户名称").count().as("买房数"),
			    Aggregation.sort(new Sort(Sort.Direction.DESC,"买房数")),
			    Aggregation.limit(50)
			    );
			
		}else {
			    aggregation1 = Aggregation.newAggregation(
					Aggregation.group("客户名称").count().as("买房数"),
				    Aggregation.sort(new Sort(Sort.Direction.DESC,"买房数")),
				    Aggregation.limit(50)
				    );
		}
		
		AggregationResults<BasicDBObject> outputTypeCount1 =
		        template.aggregate(aggregation1, "contract", BasicDBObject.class);
		for (Iterator<BasicDBObject> iterator = outputTypeCount1.iterator(); iterator.hasNext(); ) {
		    DBObject obj = iterator.next();
		    System.out.println(JSON.toJSONString(obj));
		}
		//System.out.println(JSON.toJSON(outputTypeCount1));
        System.out.println("end");
	}
	
	private static void getSeller(MongoTemplate template, String customer) {
		String[] names=customer.split(",");
		for (String string : names) {
			Query query2=new Query(Criteria.where("房间").regex("^((?!车位).)*$").andOperator(Criteria.where("客户名称").regex(".*?"+string+".*?")));
			List<合同> list2=template.find(query2, 合同.class);
						if(list2.isEmpty()) {
					       System.out.println(string);
							//System.out.println(c.房间+"\t"+c.客户名称+"\t"+c.业务员);
						}else {
							if(list2.size()>1) {
								System.out.println(list2.size()+"个结果");
								System.out.println(JSON.toJSONString(list2));
							}else {
						    	System.out.println(list2.get(0).业务员+"\t"+list2.get(0).房间);
							}
						}
		}
		
	}
	public static void RemoveError(MongoTemplate mongoTemplate) {
		//Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("房间").count().as("total"));
		Query query=new Query(Criteria.where("业务员").regex(".*?车位.*?"));
		List<合同> list2=mongoTemplate.find(query, 合同.class);
		 System.out.println(list2.size());
		 //System.out.println(1);
	}

	public static void getDiffName(MongoTemplate template) {
		  Query query=new Query(Criteria.where("房间").regex(".*?车位.*?"));
		  List<合同> list=template.find(query, 合同.class);
		  int s=0;
		  for (合同 c : list) {
			String name=c.客户名称;
			//System.out.println(name);
			Query query2=new Query(Criteria.where("房间").regex("^((?!车位).)*$").andOperator(Criteria.where("客户名称").regex(".*?"+name+".*?")));
			List<合同> list2=template.find(query2, 合同.class);
			
			if(list2.isEmpty()) {
				s++;
				System.out.println(c.房间+"\t"+c.客户名称+"\t"+c.业务员);
			}else {
				//System.out.println(list2.get(0).房间+"="+list2.get(0).客户名称+"----------"+name);
			}
		}
		  System.out.println(s);
	}
	
	public static void RemoveDouble(MongoTemplate mongoTemplate) {
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("房间").count().as("total"));
		 AggregationResults<BasicDBObject> outputTypeCount1 =
		         mongoTemplate.aggregate(aggregation, "contract", BasicDBObject.class);

		 for (Iterator<BasicDBObject> iterator = outputTypeCount1.iterator(); iterator.hasNext(); ) {
		     DBObject obj = iterator.next();
		     if((int)obj.get("total")>1) {
		     System.out.println(JSON.toJSONString(obj));
		     //mongoTemplate.findAllAndRemove(new Query(Criteria.where("房间").is((String)obj.get("_id"))), 合同.class);
		     }
		 }
		 //System.out.println(1);
	}
	
	public static void SumByName(MongoTemplate mongoTemplate) {
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("客户名称").count().as("total"));
		 AggregationResults<BasicDBObject> outputTypeCount1 =
		         mongoTemplate.aggregate(aggregation, "contract", BasicDBObject.class);

		 for (Iterator<BasicDBObject> iterator = outputTypeCount1.iterator(); iterator.hasNext(); ) {
		     DBObject obj = iterator.next();
		    /* if((int)obj.get("total")>1) {
		     System.out.println(JSON.toJSONString(obj));
		     //mongoTemplate.findAllAndRemove(new Query(Criteria.where("房间").is((String)obj.get("_id"))), 合同.class);
		     }*/
		 }
		 //System.out.println(1);
	}
	public static void excludeCar(MongoTemplate mongoTemplate) {
		Query query=new Query(Criteria.where("房间").regex("^((?!车位).)*$"));
		  List<合同> list=mongoTemplate.find(query, 合同.class);
		  System.out.println(list.size());
		  //System.out.println(JSON.toJSONString(list));
		  FileUtil.makefile("D:/", "a.json",JSON.toJSONString(list));
	}
	public static void AgeDistance(MongoTemplate mongoTemplate) {
		//Query query=new Query(Criteria.where("房间").regex("^((?!车位).)*$"));
		  List<合同> list=mongoTemplate.findAll(合同.class);
		  List<AgeDis> dis=new ArrayList<>();
		  for (合同 c : list) {
			if(c.客户信息.size()>1) {
				if(c.客户信息.size()>2) {
					System.out.println("大于2人共有，无法比对！");
					continue;
				}
				try {
				客户  info1= c.客户信息.poll();
				int year1=Integer.parseInt(info1.证件号码.substring(6,10));
				客户  info2= c.客户信息.poll();
				int year2=Integer.parseInt(info2.证件号码.substring(6,10));
				//System.out.println("证件号码："+info1.证件号码+"||"+info2.证件号码);
				//System.out.println("出生年份："+year1+"||"+year2);
				AgeDis ageDis=new AgeDis(Math.abs(year1-year2),"客户("+year1+"):"+info1.name+"与其房屋共有人"+info2.name+"("+year2+")"+"---年龄相差"+Math.abs(year1-year2)+"年");
				dis.add(ageDis);
				}catch (Exception e) {
					continue;
				}			
			}
		}
		  Collections.sort(dis);
		  for (AgeDis ageDis : dis) {
			System.out.println(ageDis.detail);
		}
		 // System.out.println(list.size());
		  //System.out.println(JSON.toJSONString(list));
		 // FileUtil.makefile("D:/", "a.json",JSON.toJSONString(list));
	}
	
	static class AgeDis implements Comparable<AgeDis>{
		public int dis;
		public String detail;
		public AgeDis(int dis,String d) {
			// TODO Auto-generated constructor stub
			this.dis=dis;
			this.detail=d;
		}
		@Override
		public int compareTo(AgeDis o) {
			if(this.dis > o.dis){
	            return -1;
	        } 
	        if(this.dis <o.dis){
	            return 1;
	        }
			return dis;
		}
	}
}
