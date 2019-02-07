package com.dudu;



import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.dudu.mongo.domin.合同;
import com.dudu.tools.StringUtil;

public class MongoToExcel {

	public static void main(String[] args) throws Exception {
		//getMongoData(args);
		//String s="C059,H131,J102,J128,K167,H134,H148,J147,D015,D016,J184,H123,H122,H125,H124,H126,H194,H145,H094,J168,D039,H021,C037,C034,K180,K110,J090,J084,J218,J164,J162,J105,J192,K020,C021,H116,H070,A033,H105,K178,J189,K115,H096,C030,L039,J163,J190,H057,J184,J221,H168,H100,H078,H091,K127,K022,J170,H068,H153,K171,H001,K077,H129,J178,H139,J175,H149,H026,B052,H147,H197,J053,H146,H093,K007,K061,H137,J177,H156,K076,H162,H193,H192,H084,H011,H160,H213,J067,J186,H099,J069,H119,H226,J046,J264,H210,H013,K133,J140,J110,J219,H018,D028,B055,C013,C016,H215,F194,B004,B049,C004,D004,D048,B022,C044,B040,C017,D019,B028,B025,C027,C022,D043,D011,C026,B045,B018,D003,B051,D027,B014,C047,C008,C005,C025,C062,B021,B008,B042,C051,C033,B039,D035,C032,D002,C023,C009,B024,C015,C053,B050,B048,C001,C003,K033,B036,D036,B007";
		//String s="H020,H022,K185,J074,K126,C029,J111,C038,J054,C028,H104,K124,H002,J124,J171,S2栋-112,S1栋-111,H225,C020,K144,H177,J009,K162,H076,K179";
		//String s="3栋-905,S2-110,S1-109,1栋-3105";
		String  s="D029,F219,E045,F073,C019,K064,J044,E148,A114,J083,K174,G047,J015";
		List<String>list=Arrays.asList(s.split(","));
		getDataByNO(args,list);
		//System.out.println(UUID.randomUUID().toString().hashCode());
	}
	
	public static List<合同> getDataByNO(String[] args,List<String> list) throws Exception {
	    List<合同> contracts=new ArrayList<>();
		ApplicationContext applicationContext= SpringApplication.run(Application.class,args);
		 MongoTemplate mongoTemplate=(MongoTemplate) applicationContext.getBean("mongoTemplate");
		 合同 cc=null;
		 List<合同>c1=null;
		//System.out.println(JSON.toJSONString(c));
			 for (String s :list) {
				 String[] key=s.split("-");
				 if(key.length==1) {
					  cc=mongoTemplate.findOne(new Query(Criteria.where("房间").regex("^.*?" +key[0]+ ".*$")), 合同.class);
				 }else {
					  c1=mongoTemplate.find(new Query((new Criteria().andOperator(Criteria.where("房间").regex("^.*?" +key[0]+ ".*$"),Criteria.where("房间").regex("^.*?" +key[1]+ ".*$"),Criteria.where("房间").regex("^((?!车位).)*$")))), 合同.class);

				 }
				 if (cc==null&&c1==null) {
					System.out.println(s);
				}else if(cc!=null) {
				 contracts.add(cc);
				}else {
					contracts.addAll(c1);
				}
			 }
		// createExcel("房间,客户姓名", contracts);
			 for (合同 c : contracts) {
				System.out.println(c.房间+"\t"+c.客户名称);
			}
		 
		 
		 ExitCodeGenerator exitCodeGenerators =new ExitCodeGenerator() {
	         @Override
				public int getExitCode() {
					// TODO Auto-generated method stub
					return 0;
				}
			} ;
		  SpringApplication.exit(applicationContext, exitCodeGenerators);
		 return contracts;
	}
	public static void createExcel(String title,List<合同> contrats) throws Exception {
		//定义一个新的工作簿
		 Workbook wb=new HSSFWorkbook(); 
		 //创建sheet1
		 Sheet sheet = wb.createSheet("sheet1"); 
		 //创建行
		 Row row = sheet.createRow(0);                                                                                   //todo排除车位
		 String[] titles=title.split(","); 
		 //创建单元格
		 for (int i=0;i<titles.length;i++) {
			 row.createCell(i).setCellValue(titles[i]);
		}
		  int r=1;
		 for(int i=0;i<contrats.size();i++) {
			 合同 con=contrats.get(i);
			 System.out.println("正在写入第"+r+"行数据");
			 Row row1= sheet.createRow(r); 
			 ArrayList<String> list=new ArrayList<>();
			 String[] m=con.房间.split("-");
			 String room=null;
			 if(m[m.length-2].contains("车位")) {
				 room=m[m.length-1];
			 }else {
				 room=m[m.length-2]+"-"+m[m.length-1];
			 }
			// System.out.println(room);
			 list.add(room);
			 list.add(con.客户名称);
			// list.add(con.联系电话); 
			// list.add(con.交房日期);
			// list.add((StringUtil.isNull(con.装修标准))?con.房间信息.peek().装修标准:con.装修标准);
			// list.add(StringUtil.isNull(con.装修单价)?con.房间信息.peek().装修单价:con.装修单价);
			 //list.add(StringUtil.isNull(con.装修总价)?con.房间信息.peek().装修款:con.装修总价);
			 //list.add(con.建筑面积);
			 //list.add(con.套内面积);
			 //list.add(con.建筑单价);
			 //list.add(con.合同总价);
			 
			 //list.add(con.房间信息.peek().装修标准);
			 //list.add(con.房间信息.peek().装修单价);
			 r++;
			 for (int j=0;j<list.size();j++) {
				row1.createCell(j).setCellValue(list.get(j));
			}
		 }
		        //创建一个输入流
		 FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\dlm\\Desktop\\temp.xls");
		 //写入
		 wb.write(fileOutputStream);
	}
	
	public static List<合同> getMongoData(String[] args) throws Exception {
		ApplicationContext applicationContext= SpringApplication.run(Application.class,args);
		 MongoTemplate mongoTemplate=(MongoTemplate) applicationContext.getBean("mongoTemplate");
		 List<合同> contrats=mongoTemplate.findAll(合同.class);
		//定义一个新的工作簿
		 Workbook wb=new HSSFWorkbook(); 
		 //创建sheet1
		 Sheet sheet = wb.createSheet("sheet1"); 
		 //创建行
		 Row row = sheet.createRow(0);                                                                                   //todo排除车位
		 String[] title="房间,客户姓名,手机号,交房日期,装修标准,装修单价,装修款,建筑面积,套内面积,建筑单价,总价".split(","); 
		 //创建单元格
		 for (int i=0;i<title.length;i++) {
			 row.createCell(i).setCellValue(title[i]);
		}
		  int r=1;
		 for(int i=0;i<contrats.size();i++) {
			 合同 con=contrats.get(i);
			 if(con.房间.contains("车位")) {
				 continue;
			 }
			 System.out.println("正在写入第"+r+"行数据");
			 Row row1= sheet.createRow(r); 
			 ArrayList<String> list=new ArrayList<>();
			 list.add(con.房间);
			 list.add(con.客户名称);
			 list.add(con.联系电话); 
			 list.add(con.交房日期);
			 list.add((StringUtil.isNull(con.装修标准))?con.房间信息.peek().装修标准:con.装修标准);
			 list.add(StringUtil.isNull(con.装修单价)?con.房间信息.peek().装修单价:con.装修单价);
			 list.add(StringUtil.isNull(con.装修总价)?con.房间信息.peek().装修款:con.装修总价);
			 list.add(con.建筑面积);
			 list.add(con.套内面积);
			 list.add(con.建筑单价);
			 list.add(con.合同总价);
			 
			 //list.add(con.房间信息.peek().装修标准);
			 //list.add(con.房间信息.peek().装修单价);
			 r++;
			 for (int j=0;j<list.size();j++) {
				row1.createCell(j).setCellValue(list.get(j));
			}
		 }
		        //创建一个输入流
		 FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\dlm\\Desktop\\temp.xls");
		 //写入
		 wb.write(fileOutputStream);

		 ExitCodeGenerator exitCodeGenerators =new ExitCodeGenerator() {
	         @Override
				public int getExitCode() {
					// TODO Auto-generated method stub
					return 0;
				}
			} ;
		  SpringApplication.exit(applicationContext, exitCodeGenerators);
		 return contrats;
	}
}
