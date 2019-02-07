package com.dudu.mongo.domin;

public class 房间 {
    public String name;
	public String type;
	public String 户型;
	public String 建筑面积;
	public String 套内面积;
	public String 计价方式;
	public String 装修标准;
	public String 装修单价;
	public String 装修款;
	public String 建筑单价;
	public String 套内单价;
	public String 标准总价;
	public String 租售类型;
	public String 税额;
	public String 税率;
	public roomother 其他信息;
	public String url;
	public String oid;
    public static  class roomother{
		public String 面积状态;
		public String 销售状态;
		public String 房管局备案号;
		public String 房间结构;
		public String 景观;
		public String 朝向;
		public String 收盘原因;
		public String 是否样板房;
		public String 是否附属房产;
	}
	
}
