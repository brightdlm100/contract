package com.dudu;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.annotation.Contract;
import org.mockito.internal.util.RemoveFirstLine;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.dudu.mongo.domin.合同;
import com.dudu.mongo.service.ContractService;
import com.dudu.tools.StringUtil;
import com.dudu.util.FileUtil;


public class ContractRetry {

	public static void main(String[] args) {
 
		getRestInfo(args);
		//removeAllNull(args);
		 
		 
	}
	
	public static void getRestInfo(String[] args) {
	    ArrayList<String> list=new ArrayList<String>();
        list=FileUtil.getStringFromFile("C:\\Users\\dlm\\Desktop\\temp.txt");
        System.out.println(list.size());
        ArrayList<String> pages=new ArrayList<>();
		 for (String s:list) {
			 //System.out.println(s);
			if(s.contains("页获取失败")) {
				String page=StringUtil.getNumInString(s);
				pages.add(page);
			  }
		  }
		 ContractTest.PagesRetry(pages, args);
	}
	
	public static void removeAllNull(String[] args) {
		ApplicationContext applicationContext= SpringApplication.run(Application.class,args);
		 MongoTemplate mongoTemplate=(MongoTemplate) applicationContext.getBean("mongoTemplate");
		// ContractService contractService=(ContractService) applicationContext.getBean("contractService");  
		 /*mongoTemplate.findAndRemove(new Query(Criteria.where("客户信息").is(null)), 合同.class);
		 List<合同> list=mongoTemplate.find(new Query(Criteria.where("客户信息").is(null)), 合同.class);
		 System.out.println(list.size());*/
		//System.out.println(contractService.getContract("133353535")==null);
		
	}
}
