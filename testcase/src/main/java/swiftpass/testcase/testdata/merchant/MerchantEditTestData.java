package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.Currency;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class MerchantEditTestData {
	public static HashMap<String, String>[][] getMerchantEditTestData(){
		return getAllMerchantEditTestData();
	}
	
	private static final String 
		NO_MERCHANT_EDIT_MSG = "请选择要编辑的记录!",
		NO_SET_MERCHANT_NAME_MSG = "请填写商户名称",
		NO_SET_SHORT_NAME_MSG = "请填写商户简称",
		NO_SET_INDUSTRY_MSG = "请选择行业类别",
		NO_SET_EMAIL_MSG = "请输入邮箱",
		NO_SET_PRINCIPAL_PHONE_MSG = "请输入负责人手机",
		NO_SET_SERVICE_TEL_MSG = "请输入客服电话",
//		NO_SET_DEAL_TYPE_MSG = "",
		MERCHANT_NAME_BEYOND_LENGTH_MSG = "商户名称长度在1~64位之间！",
//		SHORT_NAME_BEYOND_LENGTH_MSG = "商户简称长度在1~15位之间！",
		EMAIL_WRONG_FORMAT_MSG = "邮箱格式不正确，不能包含特殊字符",
		PRINCIPAL_PHONE_WRONG_FORMAT_MSG = "手机号码格式不正确",
		SERVICE_TEL_WRONG_FORMAT_MSG = "客服电话格式不正确",
		CLOSE_PAGE_MSG = "正常关闭",
		CANCEL_EDIT_MSG = "正常取消"
		;
	
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
			"parentChannel", "parentChannelId", "parentChannelName", "merchantName", "shortName",
			"merchantType", "emp", "empId", "empName", "currency",
			"industry", "industryIdChain", "industryNameChain", "province", "city",
			"address", "tel", "email",
			"website", "principal", "idCode",
			"principalPhone", "serviceTel", "fax",
			"dealType", "thrMchId",
			"LICENSE_PHOTO", "INDENTITY_PHOTO", "PROTOCOL_PHOTO", "ORG_PHOTO",
			"isStore"
		};
		for(String key : valueKeys)
			map.put(key, "");
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		String suffix = SwiftPass.getHHmmssSSSString().substring(4);
		HashMap<String, String> map = initPageParamsMap();
		String[] ctrlKeys = {
			"isEditIndustry", "isConfirmEditIndustry", "isEdit", "isConfirmEdit",
			//	MERCHANT_ID--MERCHANT_NAME
			"TEXT",//	store the merchant-row related text on page for searching.
			"message",
			"isMerchantNoAuditPass",	//	if EXAMINE_STATUS is Pass, so after successfully edit. it'll turn to be Need-Again.
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, ctrlKey.startsWith("is") ? "true" : "");
		
		//	set initialized values except the uneditable elements' fields.
		map.replace("merchantName", "编辑商户" + suffix);
		map.replace("shortName", map.get("merchantName").substring(2) + suffix.substring(2));
		map.replace("currency", Currency.values()[RandomUtils.nextInt(0, Currency.values().length)].getPlainText());
		String[] industry = DBOperations.getRandomIndustryChain();
		map.replace("industry", industry[0]);	//	default same with industry-name-chain.
		map.replace("industryNameChain", industry[0]);
		map.replace("industryIdChain", industry[1]);
		map.replace("address", "美国南加州大学附属养老院");
		map.replace("tel", DataGenerator.generateTel());
		map.replace("email", DataGenerator.generateEmail());
		map.replace("website", DataGenerator.generateWebsite());
		map.replace("principal", DataGenerator.generateZh_CNName());
		map.replace("idCode", DataGenerator.generateIdCardCode());
		map.replace("principalPhone", DataGenerator.generatePhone());
		map.replace("serviceTel", DataGenerator.generateTel());
		map.replace("fax", map.get("tel"));
//		map.replace("dealType", DealType.values()[RandomUtils.nextInt(1, DealType.values().length)].getPlainText());
		map.replace("thrMchId", RandomStringUtils.randomNumeric(12));
		//	TODO	should add in the future.
		map.replace("LICENCE_PHOTO", "");
		map.replace("INDENTITY_PHOTO", "");
		map.replace("PROTOCOL_PHOTO", "");
		map.replace("ORG_PHOTO", "");
		map.replace("isStore", "false");
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantEditTestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		//	1.正常编辑商户成功.
		HashMap<String, String> auditPassM = getMerchant("false");	//获取一个审核通过的商户进行编辑
		HashMap<String, String> noAuditPassM = getMerchant("true");	//获取一个非审核通过的商户进行编辑
		SwiftLogger.getLogger().debug("商户编辑使用的审核通过商户： " + auditPassM);
		SwiftLogger.getLogger().debug("商户编辑使用的非审核通过商户：" + noAuditPassM);
		HashMap<String, String> successEditCase1 = caseMapWithCtrlParams();
		successEditCase1.replace("TEXT", String.join("-", noAuditPassM.get("MERCHANT_ID"), noAuditPassM.get("MERCHANT_NAME")));
		HashMap<String, String> successEditCase2 = caseMapWithCtrlParams();
		successEditCase2.replace("isMerchantNoAuditPass", "false");
		successEditCase2.replace("TEXT", String.join("-", auditPassM.get("MERCHANT_ID"), auditPassM.get("MERCHANT_NAME")));
		list.add(successEditCase1);
		list.add(successEditCase2);
		
		//	2.未选中渠道点击编辑，报错
		HashMap<String, String> noMerchantEditCaseMap = caseMapWithCtrlParams();
		noMerchantEditCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noMerchantEditCaseMap.replace("message", NO_MERCHANT_EDIT_MSG);
		
		//	3.必填字段未填(商户名、简称、行业类别、邮箱、负责人手机、客服电话)
		String postEditName = successEditCase1.get("merchantName");
		HashMap<String, String> newFixedCaseMap = SwiftPass.copy(successEditCase1);
		newFixedCaseMap.replace("TEXT", String.join("-", noAuditPassM.get("MERCHANT_ID"), postEditName));
		HashMap<String, String> noSetMerchantNameCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetMerchantNameCaseMap.replace("merchantName", "");
		noSetMerchantNameCaseMap.replace("message", NO_SET_MERCHANT_NAME_MSG);
		list.add(noSetMerchantNameCaseMap);
		HashMap<String, String> noSetShortNameCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetShortNameCaseMap.replace("shortName", "");
		noSetShortNameCaseMap.replace("message", NO_SET_SHORT_NAME_MSG);
		list.add(noSetShortNameCaseMap);
		HashMap<String, String> noSetIndustryCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetIndustryCaseMap.replace("industry", "");
		noSetIndustryCaseMap.replace("industryNameChain", "");
		noSetIndustryCaseMap.replace("industryIdChain", "");
		noSetIndustryCaseMap.replace("message", NO_SET_INDUSTRY_MSG);
		list.add(noSetIndustryCaseMap);
		HashMap<String, String> noSetEmailCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetEmailCaseMap.replace("email", "");
		noSetEmailCaseMap.replace("message", NO_SET_EMAIL_MSG);
		list.add(noSetEmailCaseMap);
		HashMap<String, String> noSetPrincipalPhoneCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetPrincipalPhoneCaseMap.replace("principalPhone", "");
		noSetPrincipalPhoneCaseMap.replace("message", NO_SET_PRINCIPAL_PHONE_MSG);
		list.add(noSetPrincipalPhoneCaseMap);
		HashMap<String, String> noSetServiceTelCaseMap = SwiftPass.copy(newFixedCaseMap);
		noSetServiceTelCaseMap.replace("serviceTel", "");
		noSetServiceTelCaseMap.replace("message", NO_SET_SERVICE_TEL_MSG);
		list.add(noSetServiceTelCaseMap);
		
		//	4.非法字段长度或格式（长度：商户名、简称；格式：邮箱、负责人手机、客服电话）
		HashMap<String, String> merchantNameBeyondLengthCaseMap = SwiftPass.copy(newFixedCaseMap);
		merchantNameBeyondLengthCaseMap.replace("merchantName", RandomStringUtils.randomAlphanumeric(65));
		merchantNameBeyondLengthCaseMap.replace("message", MERCHANT_NAME_BEYOND_LENGTH_MSG);
		list.add(merchantNameBeyondLengthCaseMap);
		HashMap<String, String> shortNameBeyondLengthCaseMap = SwiftPass.copy(newFixedCaseMap);
		shortNameBeyondLengthCaseMap.replace("shortName", RandomStringUtils.randomAlphanumeric(16));
//		shortNameBeyondLengthCaseMap.replace("message", SHORT_NAME_BEYOND_LENGTH_MSG);
		list.add(shortNameBeyondLengthCaseMap);
		HashMap<String, String> illegalEmailFormatCaseMap1 = SwiftPass.copy(newFixedCaseMap);
		illegalEmailFormatCaseMap1.replace("email", illegalEmailFormatCaseMap1.get("email").replace("@", ""));
		illegalEmailFormatCaseMap1.replace("message", EMAIL_WRONG_FORMAT_MSG);
		list.add(illegalEmailFormatCaseMap1);
		HashMap<String, String> illegalEmailFormatCaseMap2 = SwiftPass.copy(newFixedCaseMap);
		illegalEmailFormatCaseMap2.replace("email", illegalEmailFormatCaseMap2.get("email").replace(".", ""));
		illegalEmailFormatCaseMap2.replace("message", EMAIL_WRONG_FORMAT_MSG);
		list.add(illegalEmailFormatCaseMap2);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap1 = SwiftPass.copy(newFixedCaseMap);
		illegalPrincipalPhoneFormatCaseMap1.replace("principalPhone", illegalPrincipalPhoneFormatCaseMap1.get("principalPhone").substring(1));
		illegalPrincipalPhoneFormatCaseMap1.replace("message", PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		list.add(illegalPrincipalPhoneFormatCaseMap1);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap2 = SwiftPass.copy(newFixedCaseMap);
		illegalPrincipalPhoneFormatCaseMap2.replace("principalPhone", illegalPrincipalPhoneFormatCaseMap2.get("principalPhone") + "1");
		illegalPrincipalPhoneFormatCaseMap2.replace("message", PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		list.add(illegalPrincipalPhoneFormatCaseMap2);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap3 = SwiftPass.copy(newFixedCaseMap);
		illegalPrincipalPhoneFormatCaseMap3.replace("principalPhone", illegalPrincipalPhoneFormatCaseMap3.get("principalPhone") + "a");
		illegalPrincipalPhoneFormatCaseMap3.replace("message", PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		list.add(illegalPrincipalPhoneFormatCaseMap3);
		HashMap<String, String> illegalServiceTelFormatCaseMap1 = SwiftPass.copy(newFixedCaseMap);
		illegalServiceTelFormatCaseMap1.replace("serviceTel", RandomStringUtils.randomNumeric(6));
		illegalServiceTelFormatCaseMap1.replace("message", SERVICE_TEL_WRONG_FORMAT_MSG);
		list.add(illegalServiceTelFormatCaseMap1);
		HashMap<String, String> illegalServiceTelFormatCaseMap2 = SwiftPass.copy(newFixedCaseMap);
		illegalServiceTelFormatCaseMap2.replace("serviceTel", RandomStringUtils.randomNumeric(16));
		illegalServiceTelFormatCaseMap2.replace("message", SERVICE_TEL_WRONG_FORMAT_MSG);
		list.add(illegalServiceTelFormatCaseMap2);
		HashMap<String, String> illegalServiceTelFormatCaseMap3 = SwiftPass.copy(newFixedCaseMap);
		illegalServiceTelFormatCaseMap3.replace("serviceTel", RandomStringUtils.randomNumeric(10) + "AA");
		illegalServiceTelFormatCaseMap3.replace("message", SERVICE_TEL_WRONG_FORMAT_MSG);
		list.add(illegalServiceTelFormatCaseMap3);
		
		//	5.正常关闭、取消确认编辑并关闭页面
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(newFixedCaseMap);
		closePageCaseMap.replace("isEdit", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelEditCaseMap = SwiftPass.copy(newFixedCaseMap);
		cancelEditCaseMap.replace("isConfirmEdit", "false");
		cancelEditCaseMap.replace("message", CANCEL_EDIT_MSG);
		list.add(cancelEditCaseMap);
		
		//	6.门店不可编辑
		HashMap<String, String> storeUneditableCaseMap1 = caseMapWithCtrlParams();
		Map<String, String> directStore = getStore(MerchantType.DIRECT);
		storeUneditableCaseMap1.replace("TEXT", String.join("-", directStore.get("MERCHANT_ID"), directStore.get("MERCHANT_NAME")));
		storeUneditableCaseMap1.replace("isStore", "true");
		list.add(storeUneditableCaseMap1);
		HashMap<String, String> storeUneditableCaseMap2 = caseMapWithCtrlParams();
		Map<String, String> joinStore = getStore(MerchantType.JOIN);
		storeUneditableCaseMap2.replace("TEXT", String.join("-", joinStore.get("MERCHANT_ID"), joinStore.get("MERCHANT_NAME")));
		storeUneditableCaseMap2.replace("isStore", "true");
		list.add(storeUneditableCaseMap2);
		
		for(HashMap<String, String> caseMap : list){//	组装符合testng用的测试数据
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(caseMap));
		}
		return resultCasesMaps;
	}
	
	private static Map<String, String> getStore(MerchantType type){
		HashMap<Integer, HashMap<String, String>> stores = MerchantDBOperations.getMerchantByType(type);
		if(stores.size() < 1){
			String uniqueId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			String bigMerchantId = MerchantService.addBigMerchantAttachChannel(uniqueId).get("MERCHANT_ID");
			if(type.equals(MerchantType.DIRECT)){
				return StoreService.addDirectStoreAttachMerchant(bigMerchantId);
			} else{
				return StoreService.addJoinStoreAttachMerchant(bigMerchantId);
			}
		}
		return stores.get(1);
	}
	
	private static HashMap<String, String> getMerchant(String isMerchantNoAuditPass){
		HashMap<String, String> m = null;
		HashMap<String, String> parentChannel = ChannelDBOperations.acceptOrgUniqueChannel();
		HashMap<Integer, HashMap<String, String>> ms = MerchantDBOperations.getMerchantsOfTheUniqueChannel();
		String status = isMerchantNoAuditPass.equals("true")? "" : ExamineStatus.PASS.getSCode();
		for(Integer key : ms.keySet()){
			if(!isMerchantNoAuditPass.equals("true") && ms.get(key).get("EXAMINE_STATUS").equals(status)
					&& !ms.get(key).get("ACTIVATE_STATUS").equals(ActivateStatus.FREEZE.getSCode())){
				m = ms.get(key);
			} else if(isMerchantNoAuditPass.equals("true") && !ms.get(key).get("EXAMINE_STATUS").equals(status)
					&& !ms.get(key).get("ACTIVATE_STATUS").equals(ActivateStatus.FREEZE.getSCode())){
				m = ms.get(key);
			}
			if(null != m) break;
		}
		if(null == m){
			if(isMerchantNoAuditPass.equals("true"))	//返回一个未审核的商户
				m = MerchantService.addMultiMerchant(parentChannel.get("CHANNEL_ID"), 1).get(1);
			else{	//返回一个已审核的商户
				HashMap<String, String> unique = ChannelAAAService.getWaitAAAChannelParams(ExamineStatus.PASS, ActivateStatus.PASS, parentChannel);
				ChannelAAAService.AAAChannel(unique);	//确保新增这个商户时，渠道是审核并激活的
				HashMap<String, String> merchant = MerchantService.addMultiMerchant(parentChannel.get("CHANNEL_ID"), 1).get(1);
				String merchantId = merchant.get("MERCHANT_ID");
				MerchantAAAService.examineMerchant(merchantId, ExamineStatus.PASS.getSCode());
				m = MerchantDBOperations.getMerchant(merchant.get("MERCHANT_ID"));
			}
		}
		return m;
	}
}
