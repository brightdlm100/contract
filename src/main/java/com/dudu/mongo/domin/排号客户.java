package com.dudu.mongo.domin;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="PaiHao")      
public class 排号客户 {
	
	public String peopleId;
	public String PaiHaoid;
	public String name;
	public String type;
	public String 证件类型;
	public String 证件号码;
	public String tel;
	public String addr;
	public String sex;
	public String 预约日期;
	public String 失效日期;
	@Id
	public String number;
	public String 预约金;
	public String 业务员;
	public String status;
	public String things;

}
