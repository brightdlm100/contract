package com.dudu.mongo.domin;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="contract_guiyang")      
public class 贵阳合同 {
	@Id
	private String _id;
    @Indexed
	public String 房间;
	public String 客户名称;
	public String 客户类别;
	@Indexed
	public String 联系电话;
	public String 付款方式;
	public String 建筑面积;
	public String 建筑单价;
	public String 建筑成交单价;
	public String 房间总价;
	public String 装修标准;
	public String 装修单价;
	public String 装修总价;
	public String 装修是否并入合同;
	public String 合同编号;
	public String 合同总价;
	public String 应收定金;
	public String 合同签订日期;
	public String 交房日期;
	public String  套内面积;
	public String 套内单价;
	public String 计价方式;
	public String 套内成交单价;;
	public String 标准总价;
	public String 折扣;
	public String 折扣说明;
	public String 面积状态;
	public String 房屋结构;
	public String 附属房间;
	public String 附属房间总价;
	public String 业务员;
	public String 成交途径大类;
	public String 成交途径子类;
	public String 税额;
	public String 税率;
	public String 审核人;
	public String 状态;
	public String 合同备案号;
	public String 备案日期;
	public String 违约金;
	public ConcurrentLinkedQueue <房间> 房间信息;
	public ConcurrentLinkedQueue <客户> 客户信息;
	public 贷款信息 贷款信息;
	public 历史记录 历史记录;
	@Indexed
	public String oid;
	public String projguid;
	public String tradeguid;
	public String contracturl;
	public Date insertDate;
	public Date updateDate;
	public static class 贷款信息{
		public String 按揭银行;
		public String 按揭贷款;
		public String 公积金银行;
		public String 公积金贷款;

	}
	public static class 历史记录{
		public String 创建人;
		public String 创建时间;
		public String 审核人;
		public String 审核时间;
		public String 最后修改人;
		public String 最后修改时间;
	}
	
	
}
