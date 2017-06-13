package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.DBOperations;
import irsy.utils.dboperations.MerchantPCDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.BankAccountType;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantPCHelp;

public class MerchantPCAddTestData {
	static HashMap<String, String> merchantInfo = MerchantPCHelp.getMerchantId("");//默认获取的商户为大商户
	static HashMap<String, String> payCenterInfo = PayDBOperations.payCenterInfo;
	static HashMap<String, String> payTypeInfo = PayDBOperations.payTypeInfo;
	
	public static HashMap<String, String>[][] getMerchantPayConfAddTestData(){
		return getAllMerchantPayConfAddTestData();
	}
	
	public static String PAYCENTERNAME = "payCenterName",
			PAYTYPENAME = "payTypeName",
			CLRRATE = "clrRate",
			PARENTCLRRATE = "parentClrRate",
			MINLIMIT = "minLimit",
			MAXLIMIT = "maxLimit",
			EXISTBANKCODE = "existBankCode",
			BANKNAME = "bankName",
			BANKACCOUNT = "bankAccount",
			BANKACCOUNTOWNER = "bankAccountOwner",
			BANKACCOUNTTYPE = "bankAccountType",
			SUBBANKCODE = "subBankCode",
			BANKID = "bankId",
			PAYTYPEID = "payTypeId",
			PAYCENTERID = "payCenterId",
			ISCLICKFIRSTROWMERCHANT = "isClickFirstRowMerchant",
			ISSELECTPCNAME = "isSelectPCName",
			ISSELECTPT = "isSelectPT",
			ISCONFIRMSELECTPC = "isConfirmSelectPC",
			ISCONFIRMSELECTPT = "isConfirmSelectPT",
			ISSETNEWCLEARACCOUNT = "isSetNewClearAccount",
			ISCONFIRMSELECTEXISTBANK = "isConfirmSelectExistBank",
			ISSELECTBANK = "isSelectBank",
			ISCONFIRMSELECTBANK = "isConfirmSelectBank",
			MERCHANTID = "merchantId",
			ISPARENTCHNSETPC = "isParentChnSetPC",
			ISSAVE = "isSave",
			ISCONFIRMSAVE = "isConfirmSave",
			ERRORMSG = "errorMsg";
			
	
	public static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> data = new HashMap<String, String>();
		String[] valueKeys = {PAYCENTERNAME, PAYTYPENAME, CLRRATE, PARENTCLRRATE, MINLIMIT, MAXLIMIT, EXISTBANKCODE, 
				BANKNAME, BANKACCOUNT, BANKACCOUNTOWNER, BANKACCOUNTTYPE, SUBBANKCODE};
		for(String valueKey: valueKeys){
			data.put(valueKey, "");
		}
		return data;
	}
	
	
	//获取新增商户支付方式配置的单个测试用例
	public static HashMap<String, String> getCaseMapWithCtrlParams(){
		HashMap<String, String> data = initPageParamsMap();
		data.replace(PAYCENTERNAME, payCenterInfo.get("CENTER_NAME"));
		data.replace(PAYTYPENAME, payTypeInfo.get("PAY_TYPE_NAME"));
		data.replace(CLRRATE, "999");
		data.replace(PARENTCLRRATE, "999");
		data.replace(MINLIMIT, "0.0");
		data.replace(MAXLIMIT, "0.0");
		data.replace(EXISTBANKCODE, ""); //需要根据所选商户去数据库查询
		HashMap<String, String> bank = DBOperations.getRandomBank();
		data.replace(BANKNAME, bank.get("BANK_NAME"));
		data.replace(BANKACCOUNT, RandomStringUtils.randomNumeric(12));
		data.replace(BANKACCOUNTOWNER, RandomStringUtils.randomAlphabetic(6));
		BankAccountType[] bankType = BankAccountType.values();
		data.replace(BANKACCOUNTTYPE, bankType[RandomUtils.nextInt(0, bankType.length)].getPlainText());
		data.replace(SUBBANKCODE, RandomStringUtils.randomNumeric(12)); //网点号必须为12位
		
		//js注入需要用到的参数
		data.put(BANKID, bank.get("BANK_ID"));
		data.put(PAYTYPEID, payTypeInfo.get("PAY_TYPE_ID"));
		data.put(PAYCENTERID, payCenterInfo.get("PAY_CENTER_ID"));
		
		//新增一些控制参数
		data.put(ISPARENTCHNSETPC, "true");//商户所属渠道是否设置了支付方式 默认已设置
		data.put(ISCLICKFIRSTROWMERCHANT, "true");
		data.put(ISSELECTPCNAME, "true");
		data.put(ISSELECTPT, "true");
		data.put(ISCONFIRMSELECTPC, "true");
		data.put(ISCONFIRMSELECTPT, "true");
		data.put(ISSETNEWCLEARACCOUNT, "true");
		data.put(ISCONFIRMSELECTEXISTBANK, "true");
		data.put(ISSELECTBANK, "true");
		data.put(ISCONFIRMSELECTBANK, "true");
		//新增商户Id用于定位使用
		data.put(MERCHANTID, merchantInfo.get("MERCHANT_ID"));
		data.put(ISSAVE, "true");
		data.put(ISCONFIRMSAVE, "true");
		data.put(ERRORMSG, "");
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String>[][] getAllMerchantPayConfAddTestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		String[] needs = {ISSELECTPCNAME, ISSELECTPT, CLRRATE, MINLIMIT, MAXLIMIT, ISSELECTBANK, 
				BANKACCOUNT, BANKACCOUNTOWNER, ISSAVE, ISCONFIRMSAVE};
		String[] errorMsg = {"请先选择支付中心", "请选择支付类型", "请输入结算费率", "请输入单笔最小限额", "请输入单笔最大限额",
				"请选择开户银行", "请输入银行卡号", "请输入开户人", "正常关闭", "正常取消"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCaseMap);
		}
		//支付中心、支付类型都为空,提示：请选择支付中心
		HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
		oneCaseMap.replace(ISSELECTPCNAME, "");
		oneCaseMap.replace(ISSELECTPT, "");
		oneCaseMap.replace(ERRORMSG, "请选择支付中心");
		list.add(oneCaseMap);
		
//		//网点号不为12位，提示：联行号必须为12位数字！
//		HashMap<String, String> illegelCaseMap = getCaseMapWithCtrlParams();
//		illegelCaseMap.replace(SUBBANKCODE, RandomStringUtils.randomNumeric(8));
//		illegelCaseMap.replace(ERRORMSG, "联行号必须为12位数字！");
//		list.add(illegelCaseMap);
		
		//结算费率必须在【】之间
		double payConfScope = MerchantPCHelp.getMerchantPayConfScope();
		String PCSCOPE = SwiftPass.convertDTS(payConfScope);
		HashMap<String, String> illegleMaxBillRateCaseMap = getCaseMapWithCtrlParams();
		illegleMaxBillRateCaseMap.replace(CLRRATE, "1001");
		illegleMaxBillRateCaseMap.replace(ERRORMSG, "商户[" + merchantInfo.get("MERCHANT_NAME") + "]:结算费率必须在[" + PCSCOPE + "‰，1000‰]之间。");
		list.add(illegleMaxBillRateCaseMap);
		HashMap<String, String> illegleLessBillRateCaseMap = SwiftPass.copy(illegleMaxBillRateCaseMap);
		illegleLessBillRateCaseMap.replace(CLRRATE, (payConfScope - 1) + "");
		list.add(illegleLessBillRateCaseMap);
		
		//单笔最小额度大于单笔最大额度
		HashMap<String, String> illegleMinMoreThanMaxCaseMap = getCaseMapWithCtrlParams();
		illegleMinMoreThanMaxCaseMap.replace(MINLIMIT, "2");
		illegleMinMoreThanMaxCaseMap.replace(MAXLIMIT, "1");
		illegleMinMoreThanMaxCaseMap.replace(ERRORMSG, "商户[" + merchantInfo.get("MERCHANT_NAME")+ "]:单笔最小限额不能大于单笔最大限额");
		list.add(illegleMinMoreThanMaxCaseMap);
		
		//所选的商户为门店（直营商户），点击新增按钮提示用例
		HashMap<String, String> directMchCaseMap = getCaseMapWithCtrlParams();
		String directStoreId = MerchantPCHelp.getMerchantId(MerchantType.DIRECT.getSCode()).get("MERCHANT_ID");
		directMchCaseMap.replace(MERCHANTID, directStoreId);
		directMchCaseMap.replace(ERRORMSG, "新增直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		list.add(directMchCaseMap);
		
		//所选商户为门店（加盟商户）,点击新增按钮提示用例
		HashMap<String, String> joinMchCaseMap = getCaseMapWithCtrlParams();
		String joinStoreId = MerchantPCHelp.getMerchantId(MerchantType.JOIN.getSCode()).get("MERCHANT_ID");
		joinMchCaseMap.replace(MERCHANTID, joinStoreId);
		joinMchCaseMap.replace(ERRORMSG, "新增加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		list.add(joinMchCaseMap);
		
		
		//新增成功1.新录入结算账户 2.新增失败-重复添加（关联已有卡）
		HashMap<String, String> successAddCaseMap = getCaseMapWithCtrlParams();
		successAddCaseMap.replace(CLRRATE, (payConfScope+1) + "");
		list.add(successAddCaseMap);
		HashMap<String, String> repeatAddCaseMap = SwiftPass.copy(successAddCaseMap);
		String mchIdWithPC = MerchantPCHelp.getMchIdWithPC().get("MERCHANT_ID");
		String mNameWithPC = MerchantPCDBOperations.getMerchantAndDetailInfos(mchIdWithPC).get("MERCHANT_NAME");
		repeatAddCaseMap.replace(ISSETNEWCLEARACCOUNT, "");
		repeatAddCaseMap.replace(MERCHANTID, mchIdWithPC);
		String existBankCode = MerchantPCDBOperations.getBankAccountInfoByMchId(repeatAddCaseMap.get(MERCHANTID)).get("ACCOUNT_CODE");
		repeatAddCaseMap.replace(EXISTBANKCODE, existBankCode);
		repeatAddCaseMap.replace(ERRORMSG, "商户[" + mNameWithPC + "]:商户支付类型配置已存在");
		list.add(repeatAddCaseMap);
		
		//商户父渠道没有设置支付方式
		List<HashMap<String, String>> mchParentChnNoPC = MerchantPCHelp.getPatentChnNoPCMchId();
		HashMap<String, String> parentChnNoPCCaseMap = getCaseMapWithCtrlParams();
		parentChnNoPCCaseMap.replace(ISPARENTCHNSETPC, "");//不为true，商户父渠道未设置支付方式
		parentChnNoPCCaseMap.replace(MERCHANTID, mchParentChnNoPC.get(1).get("MERCHANT_ID"));
		parentChnNoPCCaseMap.replace(PARENTCLRRATE, "");
		parentChnNoPCCaseMap.replace(ERRORMSG, "请设置父渠道相关信息");
		list.add(parentChnNoPCCaseMap);
		HashMap<String, String> parentChnNoPCCaseMap_ = SwiftPass.copy(parentChnNoPCCaseMap);
		parentChnNoPCCaseMap_.replace(PARENTCLRRATE, "1001");
		parentChnNoPCCaseMap_.replace(ERRORMSG, "渠道[" + mchParentChnNoPC.get(0).get("CHANNEL_NAME") + "]:成本费率必须在[" + PCSCOPE + "‰，1000‰]之间。");
		list.add(parentChnNoPCCaseMap_);
		parentChnNoPCCaseMap_ = SwiftPass.copy(parentChnNoPCCaseMap_);
		parentChnNoPCCaseMap_.replace(PARENTCLRRATE, (payConfScope - 1) + "");
		list.add(parentChnNoPCCaseMap_);
		//新增成功用例
		HashMap<String, String> successAddMchCaseMap = getCaseMapWithCtrlParams();
		successAddMchCaseMap.replace(ISPARENTCHNSETPC, "");//不为true，商户父渠道未设置支付方式
		successAddMchCaseMap.replace(MERCHANTID, mchParentChnNoPC.get(1).get("MERCHANT_ID"));
		successAddMchCaseMap.replace(CLRRATE, (payConfScope+1) + "");
		successAddMchCaseMap.replace(PARENTCLRRATE, (payConfScope+1) + "");
		list.add(successAddMchCaseMap);
		
		for(HashMap<String, String> map: list){
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(map));
		}
		return resultCasesMaps;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getMerchantPayConfAddTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}
}

