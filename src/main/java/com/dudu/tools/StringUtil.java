package com.dudu.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author ouzhb
 */
public class StringUtil {

	public static String formatDuring(long mss) {  
	    long days = mss / (1000 * 60 * 60 * 24);  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000;  
	    String d=(days==0?"":days+"天");
	    String h=(hours==0?"":hours+"小时");
	    String m=(minutes==0?"":minutes+"分钟");
	    String s=(seconds==0?"":seconds+"秒");
	    long ms=mss-1000*60*60*24*days-1000*60*60*hours-1000*60*minutes-1000*seconds;
	    String msString=ms!=0?ms+"毫秒":"";
	    //System.out.println(mss);
	    //String ms=days==0&&hours==0&&minutes==0&&seconds==0?mss+"毫秒":"";
	    return d+h+m+s+msString;  
	}  
	
	public static String getNumInString(String str) {
		str=str.trim();
		String str2="";
		if(str != null && !"".equals(str)){
			for(int i=0;i<str.length();i++){
			if(str.charAt(i)>=48 && str.charAt(i)<=57){
			str2+=str.charAt(i);
			}
			} 
		}
		return str2;
		}
	/**
	 * 判断字符串是否为null、“ ”、“null”
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		if (obj == null){
			return true;
		}else if (obj.toString().trim().equals("")){
			return true;
		}else if(obj.toString().trim().toLowerCase().equals("null")){
			return true;
		}
		
		return false;
	}

	/**
	 * 正则验证是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");
		Matcher match = pattern.matcher(str);
		
		return match.matches();
	}
	
	public static Date StringToDate(String str) {			 
			  Date date=null; 
			  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
			  try {
				date=formatter.parse(str);
			} catch (ParseException e) {
				return null;
			} 
              return date;
			} 
    /** 
     * 将一个长整数转换位字节数组(8个字节)，b[0]存储高位字符，大端 
     *  
     * @param l 
     *            长整数 
     * @return 代表长整数的字节数组 
     */  
    public static byte[] longToBytes(long l) {  
        byte[] b = new byte[8];  
        b[0] = (byte) (l >>> 56);  
        b[1] = (byte) (l >>> 48);  
        b[2] = (byte) (l >>> 40);  
        b[3] = (byte) (l >>> 32);  
        b[4] = (byte) (l >>> 24);  
        b[5] = (byte) (l >>> 16);  
        b[6] = (byte) (l >>> 8);  
        b[7] = (byte) (l);  
        return b;  
    } 
}
