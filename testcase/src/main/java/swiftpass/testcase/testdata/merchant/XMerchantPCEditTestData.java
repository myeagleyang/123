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
import swiftpass.testcase.casebeans.MerchantPCEditCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantPCHelp;
import swiftpass.utils.services.MerchantPCService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class XMerchantPCEditTestData {
	static final String merchantId = getNeedMerchantId("");//默认大商户
	static double clrRate = MerchantPCHelp.getMerchantPayConfScope();
	static String CLRRATESCOPE = SwiftPass.convertDTS(clrRate);
	private static final MerchantPCEditCaseBean tmpCase = new MerchantPCEditCaseBean();
	static{
		tmpCase.setClrRate((String.valueOf(clrRate)));
		tmpCase.setMinLimit("0.0");
		tmpCase.setMaxLimit("0.0");
		tmpCase.setThirdMchId(RandomStringUtils.randomNumeric(6));
		
		//新增商户Id进行查询
		tmpCase.setMerchantId(merchantId);
		tmpCase.setPayTypeName(PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));
		//新增一些控制参数
		tmpCase.setIsClickFirstRowMerchant("true");
		tmpCase.setIsClickFirstRowPT("true");
		tmpCase.setIsEdit("true");
		tmpCase.setIsConfirmEdit("true");
		tmpCase.setErrorMsg("");
	}
	@SuppressWarnings("unchecked")
	public static MerchantPCEditCaseBean[][] data(){
		List<MerchantPCEditCaseBean> list = new ArrayList<>();
		
		String needs[] = {"clrRate", "minLimit", "maxLimit", "isClickFirstRowPT", "isEdit", "isConfirmEdit"};
		String errorMsg[] = {"请输入结算费率", "请输入单笔最小限额", "请输入单笔最大限额", "请选择要编辑的行!", "正常关闭", "正常取消"};
		for(int i=0; i<needs.length; i++){
			MerchantPCEditCaseBean oneCaseMap = SwiftPass.copy(tmpCase);
			if(needs[i].equals("clrRate")){
				oneCaseMap.setClrRate("");
			}else if(needs[i].equals("minLimit")){
				oneCaseMap.setMinLimit("");
			}else if(needs[i].equals("maxLimit")){
				oneCaseMap.setMaxLimit("");
			}else if(needs[i].equals("isClickFirstRowPT")){
				oneCaseMap.setIsClickFirstRowPT("");
			}else if(needs[i].equals("isEdit")){
				oneCaseMap.setIsEdit("");
			}else if(needs[i].equals("isConfirmEdit")){
				oneCaseMap.setIsConfirmEdit("");
			}else {}
			oneCaseMap.setErrorMsg(errorMsg[i]);
			oneCaseMap.setCASE_NAME(errorMsg[i]);
			list.add(oneCaseMap);
		}
		//单笔最小限额不能大于单笔最大限额
		MerchantPCEditCaseBean illegleMinMoreThanMaxCaseMap = SwiftPass.copy(tmpCase);
		illegleMinMoreThanMaxCaseMap.setMinLimit("2");
		illegleMinMoreThanMaxCaseMap.setMaxLimit("1");
		illegleMinMoreThanMaxCaseMap.setErrorMsg("单笔最小限额不能大于单笔最大限额");
		illegleMinMoreThanMaxCaseMap.setCASE_NAME("单笔最小限额不能大于单笔最大限额");
		list.add(illegleMinMoreThanMaxCaseMap);
		
		
		//结算费率超过允许范围 1.小于最小值 2.大于最大值
		MerchantPCEditCaseBean illegleLessCrlRate = SwiftPass.copy(tmpCase);
		MerchantPCEditCaseBean illegleMoreCrlRate = SwiftPass.copy(illegleLessCrlRate);
		illegleLessCrlRate.setClrRate(String.valueOf(clrRate-1));
		illegleLessCrlRate.setErrorMsg("结算费率必须在[" + CLRRATESCOPE + "‰，1000‰]之间。");
		illegleLessCrlRate.setCASE_NAME("结算费率超过允许范围 - 1.小于最小值");
		list.add(illegleLessCrlRate);
		
		illegleMoreCrlRate.setClrRate("1001");
		illegleMoreCrlRate.setErrorMsg("结算费率必须在[" + CLRRATESCOPE + "‰，1000‰]之间。");
		illegleMoreCrlRate.setCASE_NAME("结算费率超过允许范围 - 2.大于最小值");
		list.add(illegleMoreCrlRate);
		
		//编辑成功用例
		MerchantPCEditCaseBean successCaseMap = SwiftPass.copy(tmpCase);
		successCaseMap.setCASE_NAME("编辑成功...");
		list.add(successCaseMap);
		
		//编辑门店类型的商户提示：编辑直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！
		MerchantPCEditCaseBean directStoreCaseMap = SwiftPass.copy(tmpCase);
		String storeType = MerchantType.DIRECT.getSCode();
		directStoreCaseMap.setMerchantId(getNeedMerchantId(storeType));
		directStoreCaseMap.setErrorMsg("编辑直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		directStoreCaseMap.setCASE_NAME("编辑直营商户的支付类型配置限制...");
		list.add(directStoreCaseMap);
		
		//编辑门店类型的商户提示：编辑加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！
		MerchantPCEditCaseBean joinStoreCaseMap =SwiftPass.copy(tmpCase); 
		storeType = MerchantType.JOIN.getSCode();
		joinStoreCaseMap.setMerchantId(getNeedMerchantId(storeType));
		joinStoreCaseMap.setErrorMsg("编辑加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		joinStoreCaseMap.setCASE_NAME("编辑加盟商户的支付类型配置限制...");
		list.add(joinStoreCaseMap);
		
		
		//组装用例数据
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantPCEditCaseBean [][] ::new );
		
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
//		HashMap<String, String>[][] maps = getMerchantPCEditTestData();
//		for(HashMap<String, String>[] map : maps){
//			System.err.println(map[0]);
//		}
		
		for( MerchantPCEditCaseBean[] rs :data()){
			System.out.println(rs[0]);
		}
		System.out.println(System.currentTimeMillis()-start);
//		String s = getNeedMerchantId("13");
//		System.err.println(s);
	}

}