package irsy.utils.dboperations;

import java.util.HashMap;

import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.utils.DBUtil;
import swiftpass.utils.dboperations.ChannelDBOperations;

public class PayDBOperations {
	public static HashMap<String, String> payCenterInfo = uniquePayCenterInfo(); 
	public static HashMap<String, String> payTypeInfo = uniquePayTypeInfo();
	/**
	 * 获取数据库中配置的支付中心
	 * 返回第一条数据
	 * */
	public static HashMap<String, String> getPayCenterInfo(){
		String SQL = "select * from tra_pay_center";
		HashMap<String, String> payCenterData = DBUtil.getQueryResultMap(SQL).get(1);
		if(payCenterData != null){
			return payCenterData;
		}else{
			throw new SwiftPassException("警告:", "数据库没有配置支付中心");
		}
	}
	
	/**
	 * 根据支付中心ID获取支付方式信息
	 * 返回第一条数据
	 * */
	public static HashMap<String, String> getPayTypeInfo(){
		String payCenterId = getPayCenterInfo().get("PAY_CENTER_ID");
		String SQL = "select * from tra_pay_type where pay_center_id = '$payCenterId'".replace("$payCenterId", payCenterId);
		HashMap<String, String> payTyeData = DBUtil.getQueryResultMap(SQL).get(1);
		if(payTyeData != null){
			return payTyeData;
		}else{
			throw new SwiftPassException("警告:", "数据库没有配置支付方式");
		}
	}
	
	/**
	 * @description 获取唯一内部机构支付方式的支付类型信息
	 * 			后续只需要使用这个支付方式配置就可以了
	 * */
	public static HashMap<String, String> getUniqueChannelPTInfo(){
		HashMap<String, String> uniqueChannelInfo = ChannelDBOperations.uniqueChannleInfo;
		String uniqueChId = uniqueChannelInfo.get("CHANNEL_ID");
		String uniqueChPCInfoSql = "select * from tra_ch_pay_conf where channel_id = '$channleId'".replace("$channleId", uniqueChId);
		return DBUtil.getQueryResultMap(uniqueChPCInfoSql).get(1);
	}
	
	//根据唯一内部机构的支付方式Id获取支付方式信息
	private static HashMap<String, String> uniquePayTypeInfo(){
		HashMap<String, String> payTypeInfo = getUniqueChannelPTInfo();
		String payTypeId = payTypeInfo.get("PAY_TYPE_ID");
		String sql = "select * from tra_pay_type where pay_type_id = $payTypeId".replace("$payTypeId", payTypeId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	//根据唯一内部机构的支付方式Id获取支付中心信息
	private static HashMap<String, String> uniquePayCenterInfo(){
		String payCenterId = uniquePayTypeInfo().get("PAY_CENTER_ID");
		String sql = "select * from tra_pay_center where pay_center_id = $payCenterId".replace("$payCenterId", payCenterId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	public static void main(String...strings){
		System.out.println(uniquePayTypeInfo());
		System.out.println(uniquePayCenterInfo());
	}
	
}
