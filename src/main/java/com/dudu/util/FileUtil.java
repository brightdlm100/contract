package com.dudu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.util.ResourceUtils;

public class FileUtil {

	public static ArrayList<String> getStringFromFile(String path) {
		StringBuffer sb= new StringBuffer("");
		   try {

	            // read file content from file

	            FileReader reader = new FileReader(ResourceUtils.getFile(path));

	            BufferedReader br = new BufferedReader(reader);

	            String str = null;      
	            ArrayList<String> list=new ArrayList<>();
	            while((str = br.readLine()) != null) {
	               list.add(str);
	            }
	            br.close();
	            reader.close();
	            
	            return list;
	       }
	      catch(Exception e) {

	                  e.printStackTrace();
	          		  return null;
	            }	          
	  }
	
	
	public static String makefile(String path,String name,String context) {
	    FileWriter writer;
	    String filepath=null;
	    try {
	      filepath=path+name;
	      writer = new FileWriter(path+name);
	      writer.write(context);
	      writer.flush();
	      writer.close();
	      
	      System.out.println("html文件创建成功："+filepath);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return filepath;
	}
}
