package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.services.MerchantService;

/**
 * 说明：用例（数据）生成步骤
 * 1.	获取到与指定页面直接相关的一张map，只保存页面的相关输入字段参数
 * 2.	丰富map，给map加入控制参数
 * 3.	生成具体用例数据
 * 4.	对外提供一个获取用例数据的调用接口方法，这个方法要完成测试数据是否符合需求，并在不符合的情况下使得测试数据达到需求的工作。
 */
public class MerchantDetailTestData {
	public static HashMap<String, String>[][] getMerchantDetailTestData(){
		if(preCheckProcess()){
			return getAllMerchantDetailTestData();
		}
		return null;
	}
	
	private static String
		NO_MERCHANT_CLICK_DETAIL_MSG = "请选择您需要查看的记录!";
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
			//	商户详情页面没有要输入的字段参数
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
			"TEXT",	//store the related-text to search merchant-row for clicking.
			"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantDetailTestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> merchant = MerchantDBOperations.getMerchantsOfTheUniqueChannel().get(1);
		
		//	1.未选中商户点击详情，报错
		HashMap<String, String> noSelectMerchantClickCaseMap = caseMapWithCtrlParams();
		noSelectMerchantClickCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectMerchantClickCaseMap.replace("message", NO_MERCHANT_CLICK_DETAIL_MSG);
		list.add(noSelectMerchantClickCaseMap);
		
		//	2.查看一个正常的商户数据
		String merchantId = merchant.get("MERCHANT_ID");
		String merchantName = merchant.get("MERCHANT_NAME");
		HashMap<String, String> successDetailCaseMap = caseMapWithCtrlParams();
		successDetailCaseMap.replace("TEXT", String.join("-", merchantId, merchantName));
		list.add(successDetailCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(caseMap));
		return resultCasesMaps;
	}
	public static void main(String ...strings){
		for(HashMap<String,String> ss[]:getAllMerchantDetailTestData()){
			System.out.println(ss[0]);
		}
	}
	
	//	检测系统当前数据是否符合测试进入需求，不符合便加以处理
	private static boolean preCheckProcess(){
		HashMap<Integer, HashMap<String, String>> mchs = MerchantDBOperations.getMerchantsOfTheUniqueChannel();
		if(mchs.size() < 1){//没有商户的话那必须调用接口去造商户数据
			String parentChannelId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			SwiftLogger.getLogger().debug("商户详情新增商户所属渠道的渠道id是： " + parentChannelId);
			MerchantService.addMultiMerchant(parentChannelId, 2);
		}
		
		return true;
	}
}