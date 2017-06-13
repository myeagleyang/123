package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.EmpDBOperations;
import swiftpass.page.enums.BankAccountType;
import swiftpass.page.enums.Currency;
import swiftpass.page.enums.IDType;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.SwiftPass;

public class MerchantAddTestData {
	
	public static HashMap<String, String>[][] getMerchantAddTestData(){
		return getAllMerchantAddTestData();
	}
	
	//	异常用例提示消息
	private static final String 
		NO_SET_PARENT_CHANNEL_MSG = "请选择所属渠道",
		NO_SET_MERCHANT_NAME_MSG = "请填写商户名称",
		NO_SET_SHORT_NAME_MSG = "请填写商户简称",
		NO_SET_MERCHANT_TYPE_MSG = "请选择商户类型",
		NO_SET_INDUSTRY_MSG = "请选择行业类别",
		NO_SET_PROVINCE_MSG = "请选择省份",
		NO_SET_CITY_MSG = "请选择城市",
		NO_SET_EMAIL_MSG = "请输入邮箱",
		NO_SET_PRINCIPAL_PHONE_MSG = "请输入负责人手机",
		NO_SET_SERVICE_TEL_MSG = "请输入客服电话",
		NO_SET_CLR_BANK_MSG = "请选择开户银行",
		NO_SET_ACCOUNT_MSG = "请输入银行卡号",
		NO_SET_ACCOUNT_OWNER_MSG = "请输入开户人",
		NO_SET_ACCOUNT_TYPE_MSG = "请输入选择账户类型",
		NO_SET_SUB_BANK_NO_MSG = "请输入网点号(联行号)",	//	必填未填页面提示消息
		
		MERCHANT_NAME_BEYOND_LENGTH_MSG = "商户名称长度在1~64位之间！",
//		SHORT_NAME_BEYOND_LENGTH_MSG = "商户简称长度在1~20位之间！",
		EMAIL_WRONG_FORMAT_MSG = "邮箱格式不正确，不能包含特殊字符",
		PRINCIPAL_PHONE_WRONG_FORMAT_MSG = "手机号码格式不正确",	//手机号码11位数字
		SERVICE_TEL_WRONG_FORMAT_MSG = "客服电话格式不正确",	//客服电话7-15位数字
		CLOSE_PAGE_MSG = "正常关闭",
		CANCEL_SAVE_MSG = "正常取消",
		ACCOUNT_NAME_LESS_LENGTH_ON_ENTERPRISE_MSG = "企业账户开户人长度不得小于3个汉字";
		;
	
	private static HashMap<String, String> initCaseMapParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] paramNames = {
			"big_or_normal",
			"chnNameOrId", "chnName", "chnId", "merchantName", "shortName", 
			"merchantType","empNameOrId", "empId", "empName", "currency", 
			"province", "city", 
			"address", "tel", "email", 
			"website", "principal", "idCode", 
			"principalPhone", "serviceTel", //fax共用tel
			"thrMchId", 
			"LICENSE_PHOTO", "INDENTITY_PHOTO", "PROTOCOL_PHOTO", "ORG_PHOTO",
			"bankId", "bankName", "account", "accountOwner", 
			"accountType", "subBankNO", "subBankName", 
			"idType"
			//	结算账户的手机号码与商户的电话字段共用；所在省、市和商户的省市共用
		};
		for(String key : paramNames){
			map.put(key, "");
		}
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		String suffix = SwiftPass.getHHmmssSSSString().substring(2);
		String merchantName = RandomStringUtils.random(4, "大商户普通商户都是商户") + suffix;
		HashMap<String, String> parentChn = ChannelDBOperations.acceptOrgUniqueChannel();
		HashMap<String, String> map = initCaseMapParamsOnPage();
		//	新增商户类型
		map.replace("big_or_normal", MerchantType.BIG.getPlainText());
		map.replace("merchantType", map.get("big_or_normal"));
		//	所属渠道
		String[] chnItems = {"渠道名称", "渠道编号"};
		map.put("isSelectChannel", "true");
		map.put("channelItem", chnItems[RandomUtils.nextInt(0, chnItems.length)]);
		map.replace("chnId", parentChn.get("CHANNEL_ID"));
		map.replace("chnName", parentChn.get("CHANNEL_NAME"));
		if(map.get("channelItem").equals(chnItems[0]))
			map.put("chnNameOrId", map.get("chnName"));
		else
			map.put("chnNameOrId", map.get("chnId"));
		map.put("isConfirmSelectChannel", "true");
		
		map.replace("merchantName", merchantName);
		map.replace("shortName", merchantName.substring(1));
		//	所属业务员
		String[] empItems = {"业务员名称", "业务员编号"};
		HashMap<String, String> emp = EmpDBOperations.getRandomEmpOfChannel(map.get("chnId"));
		map.put("isSelectEmp", "false");
		map.put("empItem", empItems[RandomUtils.nextInt(0, empItems.length)]);
		map.replace("empId", emp.get("EMP_ID"));
		map.replace("empName", emp.get("EMP_NAME"));
		if(map.get("empItem").equals(empItems[0]))
			map.put("empNameOrId", map.get("empName"));
		else
			map.put("empNameOrId", map.get("empId"));
		map.put("isConfirmSelectEmp", "false");

		map.replace("currency", Currency.values()[RandomUtils.nextInt(0, Currency.values().length)].getPlainText());
		//	行业类别
		String[] industry = DBOperations.getRandomIndustryChain();
		map.put("isSelectIndustry", "true");
		map.put("industryNameChain", industry[0]);
		map.put("industryIdChain", industry[1]);
		map.put("isConfirmSelectIndustry", "true");
		
		map.replace("province", "广东");
		map.replace("city", "深圳");
		
		map.replace("address", "广东省深圳市南山区");
		map.replace("tel", DataGenerator.generateTel());	//电话、传真共用
		map.replace("email", DataGenerator.generateEmail());
		
		map.replace("website", DataGenerator.generateWebsite());
		map.replace("principal", DataGenerator.generateZh_CNName());
		map.replace("idCode", DataGenerator.generateIdCardCode());
		
		map.replace("principalPhone", DataGenerator.generatePhone());
		map.replace("serviceTel", DataGenerator.generateTel());
		map.replace("thrMchId", RandomStringUtils.randomNumeric(12));
		
		//	结算资料
		HashMap<String, String> bank = DBOperations.getRandomBank();
		map.put("isClickCheckbox", "true");
		//	开户银行
		map.put("isSelectBank", "true");
		map.replace("bankId", bank.get("BANK_ID"));
		map.replace("bankName", bank.get("BANK_NAME"));
		map.put("isConfirmSelectBank", "true");
		
		map.replace("account", DataGenerator.generateBankCode());
		map.replace("accountOwner", DataGenerator.generateZh_CNName());
		BankAccountType[] types = BankAccountType.values();
		map.replace("accountType", types[RandomUtils.nextInt(0, types.length)].getPlainText());
		map.replace("subBankName", bank.get("BANK_NAME") + "深圳支行");
		IDType[] ids = IDType.values();
		map.replace("idType", ids[RandomUtils.nextInt(0, ids.length)].getPlainText());
		map.replace("subBankNO", RandomStringUtils.randomNumeric(4));
		
		map.put("isSave", "true");
		map.put("isConfirmSave", "true");
		map.put("message", "");
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantAddTestData(){
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String>[][] resultCasesMaps = null;
		HashMap<String, String> successAddBigCaseMap = caseMapWithCtrlParams();
		HashMap<String, String> successAddNormalCaseMap = caseMapWithCtrlParams();
		successAddNormalCaseMap.replace("big_or_normal", MerchantType.NORMAL.getPlainText());
		successAddNormalCaseMap.replace("merchantType", MerchantType.NORMAL.getPlainText());
		resultCasesMaps = ArrayUtils.addAll(resultCasesMaps, ArrayUtils.toArray(successAddBigCaseMap), ArrayUtils.toArray(successAddNormalCaseMap));
		HashMap<String, String> successWithoutClrAccountBigCaseMap = caseMapWithCtrlParams();
		HashMap<String, String> successWithoutClrAccountNormalCaseMap = caseMapWithCtrlParams();
		successWithoutClrAccountBigCaseMap.replace("isClickCheckbox", "false");
		successWithoutClrAccountNormalCaseMap.replace("isClickCheckbox", "false");
		resultCasesMaps = ArrayUtils.addAll(resultCasesMaps, ArrayUtils.toArray(successWithoutClrAccountBigCaseMap),
				ArrayUtils.toArray(successWithoutClrAccountNormalCaseMap));
		
		//	异常用例——必填字段未填(为节省构造数据的时间，异常用例的先从成功用例拷贝）
		HashMap<String, String> illegalNoSelectChannelCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSelectChannelCaseMap.replace("isSelectChannel", "false");
		illegalNoSelectChannelCaseMap.replace("chnId", "");
		illegalNoSelectChannelCaseMap.replace("chnName", "");
		illegalNoSelectChannelCaseMap.replace("message", NO_SET_PARENT_CHANNEL_MSG);
		list.add(illegalNoSelectChannelCaseMap);
		HashMap<String, String> illegalNoSetMerchantNameCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetMerchantNameCaseMap.replace("merchantName", "");
		illegalNoSetMerchantNameCaseMap.replace("message", NO_SET_MERCHANT_NAME_MSG);
		list.add(illegalNoSetMerchantNameCaseMap);
		HashMap<String, String> illegalNoSetShortNameCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetShortNameCaseMap.replace("shortName", "");
		illegalNoSetShortNameCaseMap.replace("message", NO_SET_SHORT_NAME_MSG);
		list.add(illegalNoSetShortNameCaseMap);
		HashMap<String, String> illegalNoSelectMTypeCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSelectMTypeCaseMap.replace("merchantType", "");
		illegalNoSelectMTypeCaseMap.replace("message", NO_SET_MERCHANT_TYPE_MSG);
		list.add(illegalNoSelectMTypeCaseMap);
		HashMap<String, String> illegalNoSelectIndustryCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSelectIndustryCaseMap.replace("isSelectIndustry", "false");
		illegalNoSelectIndustryCaseMap.replace("industryIdChain", "");
		illegalNoSelectIndustryCaseMap.replace("industryNameChain", "");
		illegalNoSelectIndustryCaseMap.replace("message", NO_SET_INDUSTRY_MSG);
		list.add(illegalNoSelectIndustryCaseMap);
		HashMap<String, String> illegalNoSelectProvinceCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSelectProvinceCaseMap.replace("province", "");
		illegalNoSelectProvinceCaseMap.replace("message", NO_SET_PROVINCE_MSG);
		list.add(illegalNoSelectProvinceCaseMap);
		HashMap<String, String> illegalNoSelectCityCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSelectCityCaseMap.replace("city", "");
		illegalNoSelectCityCaseMap.replace("message", NO_SET_CITY_MSG);
		list.add(illegalNoSelectCityCaseMap);
		HashMap<String, String> illegalNoSetEmailCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetEmailCaseMap.replace("email", "");
		illegalNoSetEmailCaseMap.replace("message", NO_SET_EMAIL_MSG);
		list.add(illegalNoSetEmailCaseMap);
		HashMap<String, String> illegalNoSetPrincipalPhoneCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetPrincipalPhoneCaseMap.replace("principalPhone", "");
		illegalNoSetPrincipalPhoneCaseMap.replace("message", NO_SET_PRINCIPAL_PHONE_MSG);
		list.add(illegalNoSetPrincipalPhoneCaseMap);
		HashMap<String, String> illegalNoSetServieTelCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetServieTelCaseMap.replace("serviceTel", "");
		illegalNoSetServieTelCaseMap.replace("message", NO_SET_SERVICE_TEL_MSG);
		list.add(illegalNoSetServieTelCaseMap);
		HashMap<String, String> illegalNoSetBankCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetBankCaseMap.replace("isSelectBank", "false");
		illegalNoSetBankCaseMap.replace("bankId", "");
		illegalNoSetBankCaseMap.replace("bankName", "");
		illegalNoSetBankCaseMap.replace("message", NO_SET_CLR_BANK_MSG);
		list.add(illegalNoSetBankCaseMap);
		HashMap<String, String> illegalNoSetAccountCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetAccountCaseMap.replace("account", "");
		illegalNoSetAccountCaseMap.replace("message", NO_SET_ACCOUNT_MSG);
		list.add(illegalNoSetAccountCaseMap);
		HashMap<String, String> illegalNoSetAccountOwnerCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetAccountOwnerCaseMap.replace("accountOwner", "");
		illegalNoSetAccountOwnerCaseMap.replace("message", NO_SET_ACCOUNT_OWNER_MSG);
		list.add(illegalNoSetAccountOwnerCaseMap);
		HashMap<String, String> illegalNoSetAccountTypeCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetAccountTypeCaseMap.replace("accountType", "");
		illegalNoSetAccountTypeCaseMap.replace("message", NO_SET_ACCOUNT_TYPE_MSG);
		list.add(illegalNoSetAccountTypeCaseMap);
		HashMap<String, String> illegalNoSetSubBankNOCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalNoSetSubBankNOCaseMap.replace("subBankNO", "");
		illegalNoSetSubBankNOCaseMap.replace("message", NO_SET_SUB_BANK_NO_MSG);
		list.add(illegalNoSetSubBankNOCaseMap);
		
		//	2.其它异常用例——商户名称、简称长度非法；商户重名非法；邮箱格式非法；负责人手机号格式非法；客服电话非法；
		//	企业账户开户人名称长度非法；正常关闭、取消
		HashMap<String, String> illegalMchNameBeyondLengthCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalMchNameBeyondLengthCaseMap.replace("merchantName", RandomStringUtils.randomAlphanumeric(65));
		illegalMchNameBeyondLengthCaseMap.replace("message", MERCHANT_NAME_BEYOND_LENGTH_MSG);
		list.add(illegalMchNameBeyondLengthCaseMap);
		HashMap<String, String> illegalShortNameBeyondLengthCaseMap = SwiftPass.copy(successAddBigCaseMap);
		illegalShortNameBeyondLengthCaseMap.replace("shortName", RandomStringUtils.randomAlphanumeric(21));
//		illegalShortNameBeyondLengthCaseMap.replace("message", SHORT_NAME_BEYOND_LENGTH_MSG);
		list.add(illegalShortNameBeyondLengthCaseMap);
		HashMap<String, String> illegalEmailFormatCaseMap1 = SwiftPass.copy(successAddBigCaseMap);
		illegalEmailFormatCaseMap1.replace("email", DataGenerator.generateEmail().replace("@", ""));
		illegalEmailFormatCaseMap1.replace("message", EMAIL_WRONG_FORMAT_MSG);
		list.add(illegalEmailFormatCaseMap1);
		HashMap<String, String> illegalEmailFormatCaseMap2 = SwiftPass.copy(illegalEmailFormatCaseMap1);
		illegalEmailFormatCaseMap2.replace("email", DataGenerator.generateEmail().replace(".", ""));
		list.add(illegalEmailFormatCaseMap2);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap1 = SwiftPass.copy(successAddBigCaseMap);
		illegalPrincipalPhoneFormatCaseMap1.replace("principalPhone", DataGenerator.generatePhone().substring(1));
		illegalPrincipalPhoneFormatCaseMap1.replace("message", PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		list.add(illegalPrincipalPhoneFormatCaseMap1);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap2 = SwiftPass.copy(illegalPrincipalPhoneFormatCaseMap1);
		illegalPrincipalPhoneFormatCaseMap2.replace("principalPhone", "1" + DataGenerator.generatePhone());
		list.add(illegalPrincipalPhoneFormatCaseMap2);
		HashMap<String, String> illegalPrincipalPhoneFormatCaseMap3 = SwiftPass.copy(illegalPrincipalPhoneFormatCaseMap1);
		illegalPrincipalPhoneFormatCaseMap3.replace("principalPhone", "1" + RandomStringUtils.randomAlphanumeric(9) + "a");
		list.add(illegalPrincipalPhoneFormatCaseMap3);
		HashMap<String, String> illegalServiceTelFormatCaseMap1 = SwiftPass.copy(successAddBigCaseMap);
		illegalServiceTelFormatCaseMap1.replace("serviceTel", RandomStringUtils.randomNumeric(6));
		illegalServiceTelFormatCaseMap1.replace("message", SERVICE_TEL_WRONG_FORMAT_MSG);
		list.add(illegalServiceTelFormatCaseMap1);
		HashMap<String, String> illegalServiceTelFormatCaseMap2 = SwiftPass.copy(illegalServiceTelFormatCaseMap1);
		illegalServiceTelFormatCaseMap2.replace("serviceTel", RandomStringUtils.randomNumeric(16));
		list.add(illegalServiceTelFormatCaseMap2);
//		HashMap<String, String> illegalConflictBigNameCaseMap = SwiftPass.copy(successAddBigCaseMap);
//		illegalConflictBigNameCaseMap.replace("message", CONFLICT_MERCHANT_NAME_MSG);
//		list.add(illegalConflictBigNameCaseMap);
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(successAddBigCaseMap);
		closePageCaseMap.replace("isSave", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelSaveCaseMap = caseMapWithCtrlParams();
		cancelSaveCaseMap.replace("isConfirmSave", "false");
		cancelSaveCaseMap.replace("message", CANCEL_SAVE_MSG);
		list.add(cancelSaveCaseMap);
		HashMap<String, String> illegalLengthEnterpriseAccountNameCaseMap = caseMapWithCtrlParams();
		illegalLengthEnterpriseAccountNameCaseMap.replace("accountType", BankAccountType.ENTERPRISE.getPlainText());
		illegalLengthEnterpriseAccountNameCaseMap.replace("accountOwner", DataGenerator.generateZh_CNName().substring(1));
		illegalLengthEnterpriseAccountNameCaseMap.replace("message", ACCOUNT_NAME_LESS_LENGTH_ON_ENTERPRISE_MSG);
		list.add(illegalLengthEnterpriseAccountNameCaseMap);
		
		for(HashMap<String, String> caseMap : list){
			HashMap<String, String> caseMap_ = SwiftPass.copy(caseMap);
			caseMap_.replace("big_or_normal", MerchantType.NORMAL.getPlainText());
			if(!StringUtils.isEmpty(caseMap_.get("merchantType")))
				caseMap_.replace("merchantType", MerchantType.NORMAL.getPlainText());
			resultCasesMaps = ArrayUtils.addAll(resultCasesMaps, ArrayUtils.toArray(caseMap), ArrayUtils.toArray(caseMap_));
		}
		
		return resultCasesMaps;
	}
}