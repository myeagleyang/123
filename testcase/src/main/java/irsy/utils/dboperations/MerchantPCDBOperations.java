package irsy.utils.dboperations;

import java.util.HashMap;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.utils.DBUtil;
import swiftpass.utils.dboperations.PayDBOperations;

public class MerchantPCDBOperations {
	
	//根据商户ID查询商户支付配置表信息
	public static HashMap<String, String> getMerchantPCInfos(String merchantId){
		String sql = "select * from tra_mch_pay_conf where merchant_id = $merchantId and pay_type_id = $payTypeId"
				.replace("$merchantId", merchantId).replace("$payTypeId", PayDBOperations.payTypeInfo.get("PAY_TYPE_ID"));
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	//根据商户ID查询在某个支付通道下的银行账户信息
	public static HashMap<String, String> getBankAccountInfoByMchId(String merchantId){
		String mchTypeSql = "select * from cms_merchant where merchant_id = '$merchantId'".replace("$merchantId", merchantId);
		HashMap<String, String> mchInfo = DBUtil.getQueryResultMap(mchTypeSql).get(1);
		String mchType = mchInfo.get("MERCHANT_TYPE");
		String issync = mchInfo.get("IS_SYNC");
		if(mchType.equals("13") || mchType.equals("14") && issync.equals("1")){
			merchantId = mchInfo.get("PARENT_MERCHANT");
		}
		String sql = "select * from tra_bank_account where org_id = '$merchantId'".replace("$merchantId", merchantId);
		HashMap<Integer, HashMap<String, String>> bankAccInfo = DBUtil.getQueryResultMap(sql);
		if(bankAccInfo.size() == 0 || bankAccInfo ==null) throw new SwiftPassException("错误：", "所选商户没有设置任何账户信息");
		for(int i: bankAccInfo.keySet()){
			String bankAccId = bankAccInfo.get(i).get("ACCOUNT_ID");
			StringBuilder sb = new StringBuilder();
			sb.append("select * from tra_mch_pay_conf where account_id = '$accountId' ".replace("$accountId", bankAccId))
			.append("and pay_type_id = '$payTypeId' ".replace("$payTypeId", PayDBOperations.payTypeInfo.get("PAY_TYPE_ID")))
			.append("and merchant_id = '$merchantId'".replace("$merchantId", merchantId));
			String pcsql = sb.toString();
			HashMap<String, String> pcInfo = DBUtil.getQueryResultMap(pcsql).get(1);
			if(pcInfo != null) return bankAccInfo.get(i);
		}
		return null;
	}
	
	//根据商户Id查询商户和商户详情信息
	public static HashMap<String, String> getMerchantAndDetailInfos(String merchantId){
		String sql = "select * from cms_merchant a inner join cms_merchant_detail b on a.merchant_id = b.merchant_detail_id and a.merchant_id = $merchantId"
				.replace("$merchantId", merchantId);
		HashMap<String, String> map = DBUtil.getQueryResultMap(sql).get(1);
		if(map != null){
			return DBUtil.getQueryResultMap(sql).get(1);
		}else{
			throw new SwiftPassException("警告:", "商户ID不存在" + merchantId);
		}
	
	}
	
	public static void main(String...strings){
		System.out.println(getBankAccountInfoByMchId("148530000082"));
	}
	
}
