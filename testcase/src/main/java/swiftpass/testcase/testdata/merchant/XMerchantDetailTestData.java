package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.testcase.casebeans.MerchantDetailCaseBean;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantService;

/**
 * 商户进件管理查看记录详情用例
 * caseBean数据驱动重构
 * @author sunhaojie 
 * 2017-3-30
 */
public class XMerchantDetailTestData {
	private static final MerchantDetailCaseBean tmpCase = new MerchantDetailCaseBean();
	private static String NO_MERCHANT_CLICK_DETAIL_MSG = "请选择您需要查看的记录!";
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setMessage("");
		tmpCase.setTEXT("");
	}
	public static MerchantDetailCaseBean[][] data(){
		if(preCheckProcess()){
			return getAllMerchantDetailTestData();
		}
		return null;
	}
	private static MerchantDetailCaseBean[][] getAllMerchantDetailTestData(){
		List<MerchantDetailCaseBean> list = new ArrayList<>();
		HashMap<String, String> merchant = MerchantDBOperations.getMerchantsOfTheUniqueChannel().get(1);
		
		//	1.未选中商户点击详情，报错
		MerchantDetailCaseBean noSelectMerchantClickCaseMap = SwiftPass.copy(tmpCase);
		noSelectMerchantClickCaseMap.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectMerchantClickCaseMap.setMessage(NO_MERCHANT_CLICK_DETAIL_MSG);
		noSelectMerchantClickCaseMap.setCASE_NAME("未选中商户点击详情，报错...");
		list.add(noSelectMerchantClickCaseMap);
		
		//	2.查看一个正常的商户数据
		String merchantId = merchant.get("MERCHANT_ID");
		String merchantName = merchant.get("MERCHANT_NAME");
		MerchantDetailCaseBean successDetailCaseMap = SwiftPass.copy(tmpCase);
		successDetailCaseMap.setTEXT(String.join("-", merchantId, merchantName));
		successDetailCaseMap.setCASE_NAME("查看一个正常的商户数据...");
		list.add(successDetailCaseMap);		
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantDetailCaseBean[][]::new );
	}
	public static void main(String...strings){
		for(MerchantDetailCaseBean ss[]:data()){
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