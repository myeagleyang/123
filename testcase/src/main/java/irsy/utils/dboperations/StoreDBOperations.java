package irsy.utils.dboperations;

import java.util.HashMap;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.DBUtil;
import swiftpass.utils.dboperations.ChannelDBOperations;
import swiftpass.utils.dboperations.MerchantDBOperations;
import swiftpass.utils.services.DepartService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class StoreDBOperations {
	public static HashMap<Integer, HashMap<String, String>> storesParentMWithSpecES(ExamineStatus parentES){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant ")
			.append("where merchant_type in (13, 14) and parent_merchant in ")
			.append("(")
			.append("select merchant_id from cms_merchant ")
			.append("where examine_status = '").append(parentES.getSCode()).append("'")
			.append(")");
		return DBUtil.getQueryResultMap(sql.toString());
	}
	
	public static HashMap<Integer, HashMap<String, String>> childSparentMEachWithSpecES(
													ExamineStatus mchS, ExamineStatus strS){
		int index = 1;
		HashMap<Integer, HashMap<String, String>> stores = new HashMap<>();
		HashMap<Integer, HashMap<String, String>> allStores = storesParentMWithSpecES(mchS);
		for(Integer key : allStores.keySet()){
			if(allStores.get(key).get("EXAMINE_STATUS").equals(strS.getSCode()))
				stores.put(index++, allStores.get(key));
		}
		return stores;
	}
	
	public static HashMap<Integer, HashMap<String, String>> childSParentMWithSpecStatus(
			ExamineStatus parentES, ExamineStatus childES, ActivateStatus childAS){
		int index = 1;
		HashMap<Integer, HashMap<String, String>> stores = new HashMap<>();
		HashMap<Integer, HashMap<String, String>> allStores = childSparentMEachWithSpecES(parentES, childES);
		for(Integer key : allStores.keySet())
			if(allStores.get(key).get("ACTIVATE_STATUS").equals(childAS.getSCode()))
				stores.put(index++, allStores.get(key));
		return stores;
	}
	
	public static HashMap<Integer, HashMap<String, String>> directStoresWithStatus(ExamineStatus es, ActivateStatus as){
		return MerchantDBOperations.getMerchantsOfStatus(es, as, MerchantType.DIRECT);
	}
	
	public static HashMap<Integer, HashMap<String, String>> joinStoresWithStatus(ExamineStatus es, ActivateStatus as){
		return MerchantDBOperations.getMerchantsOfStatus(es, as, MerchantType.JOIN);
	}
	
	/**
	 * @description 查询数据库获取一个大商户,如果不存在就在唯一内部机构下新建一个
	 * @return bigMerchantInfo 大商户信息
	 * */
	public static HashMap<String, String> getBigMerchantInfo(){
		HashMap<String, String> bigMerchantInfo = new HashMap<String, String>();
		String queryBigMerchantSQL = "select * from cms_merchant where merchant_type = 11";
		HashMap<String, String> queryBigMerchantResult = DBUtil.getQueryResultMap(queryBigMerchantSQL).get(1);
		if(queryBigMerchantResult == null){
			String childOfAccesChannelId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			//调用新增大商户接口新增大商户
			bigMerchantInfo = MerchantService.addBigMerchantAttachChannel(childOfAccesChannelId);
		}else{
			bigMerchantInfo = queryBigMerchantResult;
		}
		
		return bigMerchantInfo;
	}
	
	/**
	 * @description 查询一个已存在的门店，如果不存在就用接口去创建
	 * @return storeNameAndId 名店名称、Id
	 * */
	
	public static HashMap<String, String> getExitStoreInfo(){
		HashMap<String, String> storeInfo = new HashMap<String, String>();
		String queryStoreNameSQL = "select * from cms_merchant where merchant_type in (13, 14)";
		HashMap<String, String> queryStoreNameResult = DBUtil.getQueryResultMap(queryStoreNameSQL).get(1);
		if(queryStoreNameResult == null){
			// 新建一个大商户
			String bigMerchantId = getBigMerchantInfo().get("MERCHANT_ID");
			//新建门店
			storeInfo = StoreService.addDirectStoreAttachMerchant(bigMerchantId);
		}else{
			storeInfo = queryStoreNameResult;
		}
		
		return storeInfo;
	}
	
	/**
	 * @description 查询数据库中与编辑的门店名称不一样的门店名称，不存在就接口创建
	 * @return storeName 已存在的门店名称
	 * */
	public static HashMap<String, String> getAnotherExitStoreInfo(){
		HashMap<String, String> storeInfo = new HashMap<String, String>();
		String queryStoreNameSQL = "select * from cms_merchant where merchant_type in (13, 14)";
		HashMap<String, String> queryStoreNameResult = DBUtil.getQueryResultMap(queryStoreNameSQL).get(2);
		if(queryStoreNameResult == null){
			// 新建一个大商户、获取商户ID
			String bigMerchantId = getBigMerchantInfo().get("MERCHANT_ID");
			//新建门店
			storeInfo = StoreService.addDirectStoreAttachMerchant(bigMerchantId);
		}else{
			storeInfo = queryStoreNameResult;
		}
		
		return storeInfo;
	}
	
	/**
	 * @description 根据大商户Id查询部门信息
	 * @param merchantId
	 * @return departInfo
	 * */
	public static HashMap<String, String> getDepartInfo(String merchantId){
		HashMap<String, String> departInfo = null;
		String sql = "Select * from cms_dept where merchant_id = $merchantId".replace("$merchantId", merchantId);
		departInfo = DBUtil.getQueryResultMap(sql).get(1);
		if(departInfo == null){
			//调用新增部门接口新增一个部门
			departInfo = DepartService.addDepartWithoutParentDept(merchantId);
		}
		return departInfo;
	}
	
	/**
	 * @description 根据门店名称查询门店信息
	 * */
	public static HashMap<String, String> storeInfoByName(String storeName){
		String sql = "select * from cms_merchant where merchant_Name = '$storeName'".replace("$storeName", storeName);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	public static void main(String...strings){
		HashMap<String, String> map = getExitStoreInfo();
		String s = map.get("DEPT_ID");
		if(s == null){
			System.out.println("+");
		}
		System.out.println("---");
	}
}
