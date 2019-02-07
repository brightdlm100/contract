package com.dudu;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dudu.mongo.domin.合同;

public class 获取所有属性 {

	 static class dog{
		 public int m;
		 public String h;
	 }
	
	 public static void main(String[] args) throws Exception, SecurityException {
		    /*Outer b=new Outer();
			b.i=new inner();
			b.i.a=200;
		Field[] out=b.getClass().getDeclaredFields();  
		Field[] in=b.i.getClass().getDeclaredFields();
		
		Class<?> claz =b.i.getClass();
        Field f = null;
        Object fieldValue = null;
            f = claz.getDeclaredField("a");
            f.setAccessible(true);
            fieldValue = f.get(b.i);
            System.out.println(fieldValue);*/
   /* for(int i=0;i<fields.length;i++){  
        infoMap = new HashMap();  
        infoMap.put("type", fields[i].getType().toString());  
        infoMap.put("name", fields[i].getName());  
        infoMap.put("value", getFieldValueByName(fields[i].getName(), o));  
        list.add(infoMap);  
    }  */
		 合同 ht=new 合同();
		 Field[] fields=ht.getClass().getDeclaredFields();
		 String[] fieldNames=new String[fields.length];  
		    for(int i=0;i<fields.length;i++){  
		        //System.out.println(fields[i].getType());  
		        fieldNames[i]=fields[i].getName();  
		    } 
		    //System.out.println(getFieldValue(ht,"房间信息"));
	}
	 
	 public static Object getFieldValue(Object obj, String field) throws Exception {
	        Class<?> claz = obj.getClass();
	        Field f = null;
	        Object fieldValue = null;
	       
            f = claz.getDeclaredField(field);
            f.setAccessible(true);
            fieldValue = f.get(obj);      
	        return fieldValue;
	    }
}
