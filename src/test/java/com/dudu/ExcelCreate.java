package com.dudu;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class ExcelCreate {

	public static void main(String[] args) {
		String s="A015,E086,G141,B030,K069,J066,J065,E068,J031,H219,H216,F043,C035,B015,E042,F098,J048,K184,E091,H010,A061,E168,E192,E210,A099,E066,E198,E197,G079,K040,E041,F096";
		List<String>list=Arrays.asList(s.split(","));
        System.out.println(list.size());
		System.out.println(selectneeded(list));
	}
	
	
	
	public static String selectneeded(List<String> list) {
		String a="=IF(OR(";
		for (String string : list) {
			a+=String.format("(IF(ISNUMBER(FIND(%s,B3)),TRUE,FALSE)),", string);
		}
		a+="),TRUE,FALSE)";
		  return a;
	 }
}
