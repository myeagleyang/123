package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantPCHelp;
import swiftpass.utils.services.MerchantPCService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class MerchantPCEditTestData {
	static final String merchantId = getNeedMerchantId("");//默认大商户
	static double clrRate = MerchantPCHelp.getMerchantPayConfScope();
	static String CLRRATESCOPE = SwiftPass.convertDTS(clrRate);
	
	public static HashMap<String, String>[][] getMerchantPCEditTestData(){
		return getAllMerchantPCEditTestData();
	}
	
	public static String CLRRATE = "clrRate",
			MINLIMIT = "minLimit",
			MAXLIMIT = "maxLimit",
			THIRDMCHID = "thirdMchId",
			MERCHANTID = "merchantId",
			ISCLICKFIRSTROWMERCHANT = "isClickFirstRowMerchant",
			ISCLICKFIRSTROWPT = "isClickFirstRowPT",
			PAYTYPENAME = "payTypeName",
			ISEDIT = "isEdit",
			ISCONFIRMEDIT = "isConfirmEdit",
			ERRORMSG = "errorMsg";
	
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put(CLRRATE, "");
		data.put(MINLIMIT, "");
		data.put(MAXLIMIT, "");
		data.put(THIRDMCHID, "");
		return data;
	} 
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = initPageParamsMap();
		data.replace(CLRRATE, String.valueOf(clrRate));
		data.replace(MINLIMIT, "0.0");
		data.replace(MAXLIMIT, "0.0");
		data.replace(THIRDMCHID, RandomStringUtils.randomNumeric(6));
		
		//新增商户Id进行查询
		data.put(MERCHANTID, merchantId);
		data.put(PAYTYPENAME, PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));
		//新增一些控制参数
		data.put(ISCLICKFIRSTROWMERCHANT, "true");
		data.put(ISCLICKFIRSTROWPT, "true");
		data.put(ISEDIT, "true");
		data.put(ISCONFIRMEDIT, "true");
		data.put(ERRORMSG, "");
		
		return data;
		
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantPCEditTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		String needs[] = {CLRRATE, MINLIMIT, MAXLIMIT, ISCLICKFIRSTROWPT, ISEDIT, ISCONFIRMEDIT};
		String errorMsg[] = {"请输入结算费率", "请输入单笔最小限额", "请输入单笔最大限额", "请选择要编辑的行!", "正常关闭", "正常取消"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCaseMap);
		}
		//单笔最小限额不能大于单笔最大限额
		HashMap<String, String> illegleMinMoreThanMaxCaseMap = getCaseMapWithCrlParams();
		illegleMinMoreThanMaxCaseMap.replace(MINLIMIT, "2");
		illegleMinMoreThanMaxCaseMap.replace(MAXLIMIT, "1");
		illegleMinMoreThanMaxCaseMap.replace(ERRORMSG, "单笔最小限额不能大于单笔最大限额");
		list.add(illegleMinMoreThanMaxCaseMap);
		//结算费率超过允许范围 1.小于最小值 2.大于最大值
		HashMap<String, String> illegleLessCrlRate = getCaseMapWithCrlParams();
		HashMap<String, String> illegleMoreCrlRate = SwiftPass.copy(illegleLessCrlRate);
		illegleLessCrlRate.replace(CLRRATE, String.valueOf(clrRate-1));
		illegleLessCrlRate.replace(ERRORMSG, "结算费率必须在[" + CLRRATESCOPE + "‰，1000‰]之间。");
		list.add(illegleLessCrlRate);
		illegleMoreCrlRate.replace(CLRRATE, "1001");
		illegleMoreCrlRate.replace(ERRORMSG, "结算费率必须在[" + CLRRATESCOPE + "‰，1000‰]之间。");
		list.add(illegleMoreCrlRate);
		//编辑成功用例
		HashMap<String, String> successCaseMap = getCaseMapWithCrlParams();
		list.add(successCaseMap);
		//编辑门店类型的商户提示：编辑直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！
		HashMap<String, String> directStoreCaseMap = getCaseMapWithCrlParams();
		String storeType = MerchantType.DIRECT.getSCode();
		directStoreCaseMap.replace(MERCHANTID, getNeedMerchantId(storeType));
		directStoreCaseMap.replace("errorMsg", "编辑直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		list.add(directStoreCaseMap);
		//编辑门店类型的商户提示：编辑加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！
		HashMap<String, String> joinStoreCaseMap = getCaseMapWithCrlParams();
		storeType = MerchantType.JOIN.getSCode();
		joinStoreCaseMap.replace(MERCHANTID, getNeedMerchantId(storeType));
		joinStoreCaseMap.replace("errorMsg", "编辑加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		list.add(joinStoreCaseMap);
		
		
		//组装用例
		for(HashMap<String, String> oneCaseMap: list){
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(oneCaseMap));
		}
		return resultCaseMaps;
	}
	
	/**
	 * @description 获取已配置支付方式的大商户，如果没有这样大商户就去唯一内部机构下去新增一个大商户
	 * @param isStoreMerchant true--门店类型的商户
	 * */
	public static String getNeedMerchantId(String storeType){
		String needMerchantId = null;
		String childOfAccept = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");
		String payTypeId = PayDBOperations.payTypeInfo.get("PAY_TYPE_ID");
		StringBuilder sb = new StringBuilder("");
		String getNeedMerchantIdSQL = 
				sb.append("select * from tra_mch_pay_conf where merchant_id in ")
				.append("(select merchant_id from cms_merchant where merchant_type = 11) ")
				.append("and pay_type_id = $payTypeId".replace("$payTypeId", payTypeId))
				.toString();
		HashMap<String, String> needMerchantIdResult = DBUtil.getQueryResultMap(getNeedMerchantIdSQL).get(1);
		String bigMchsql = "select * from cms_merchant where merchant_type = 11";
		HashMap<String, String> merchantWithOutPCresult = DBUtil.getQueryResultMap(bigMchsql).get(1);
		StringBuilder sbl = new StringBuilder();
		if(needMerchantIdResult == null){
			if(merchantWithOutPCresult == null){
				//调用商户新增接口新增一个大商户
				HashMap<String, String> bigMerchant = MerchantService.addBigMerchantAttachChannel(childOfAccept);
				needMerchantId = bigMerchant.get("MERCHANT_ID");
			}else{
				needMerchantId = merchantWithOutPCresult.get("MERCHANT_ID");
			}
			//调用商户新增支付方式配置接口新增支付方式
			MerchantPCService.addMerchantPC(needMerchantId);
		}else{
			needMerchantId = needMerchantIdResult.get("MERCHANT_ID");
		}
		//判断商户是否是门店类型商户
		if(!StringUtils.isEmpty(storeType)){
			String getNeedStoreIdSQL = sbl.append("select * from tra_mch_pay_conf where merchant_id in ")
					.append("(select merchant_id from cms_merchant where merchant_type = $storeType) ".replace("$storeType", storeType))
					.append("and pay_type_id = $payTypeId".replace("$payTypeId", payTypeId))
					.toString();
			HashMap<String, String> needStoreIdResult = DBUtil.getQueryResultMap(getNeedStoreIdSQL).get(1);
			if(needStoreIdResult == null){
				if(storeType.equals(MerchantType.DIRECT.getSCode())){
					//调用商户新增门店接口新增直营门店
					HashMap<String, String> directStore = StoreService.addDirectStoreAttachMerchant(needMerchantId);
					needMerchantId = directStore.get("MERCHANT_ID");
				}
				if(storeType.equals(MerchantType.JOIN.getSCode())){
					//新增加盟商户
					HashMap<String, String> joinStore = StoreService.addJoinStoreAttachMerchant(needMerchantId);
					needMerchantId = joinStore.get("MERCHANT_ID");
				}
				
			}else{
				needMerchantId = needStoreIdResult.get("MERCHANT_ID");
			}
		}
		return needMerchantId;
	}
	
	public static void main(String...strings){
		long start = System.currentTimeMillis();
		HashMap<String, String>[][] maps = getMerchantPCEditTestData();
		for(HashMap<String, String>[] map : maps){
			System.err.println(map[0]);
		}
		System.out.println(System.currentTimeMillis()-start);
//		String s = getNeedMerchantId("13");
//		System.err.println(s);
	}

}