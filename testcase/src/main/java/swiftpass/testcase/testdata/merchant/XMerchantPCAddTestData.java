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
import swiftpass.testcase.casebeans.MerchantPCAddCaseBean;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantPCHelp;

public class XMerchantPCAddTestData {
	static HashMap<String, String> merchantInfo = MerchantPCHelp.getMerchantId("");// 默认获取的商户为大商户
	static HashMap<String, String> payCenterInfo = PayDBOperations.payCenterInfo;
	static HashMap<String, String> payTypeInfo = PayDBOperations.payTypeInfo;
	private static final MerchantPCAddCaseBean tmpCase = new MerchantPCAddCaseBean();
	static {
		tmpCase.setCASE_NAME("");
		tmpCase.setPayCenterName(payCenterInfo.get("CENTER_NAME"));
		tmpCase.setPayTypeName(payTypeInfo.get("PAY_TYPE_NAME"));
		tmpCase.setClrRate("999");
		tmpCase.setParentClrRate("999");
		tmpCase.setMinLimit("0.0");
		tmpCase.setMaxLimit("0.0");
		tmpCase.setExistBankCode(""); // 需要根据所选商户去数据库查询
		HashMap<String, String> bank = DBOperations.getRandomBank();
		tmpCase.setBankName(bank.get("BANK_NAME"));
		tmpCase.setBankAccount(RandomStringUtils.randomNumeric(12));
		tmpCase.setBankAccountOwner(RandomStringUtils.randomAlphabetic(6));
		BankAccountType[] bankType = BankAccountType.values();
		tmpCase.setBankAccountType(bankType[RandomUtils.nextInt(0, bankType.length)].getPlainText());
		tmpCase.setSubBankCode(RandomStringUtils.randomNumeric(12)); // 联银号必须是12位

		// js注入需要用到的参数
		tmpCase.setBankId(bank.get("BANK_ID"));
		tmpCase.setPayTypeId(payTypeInfo.get("PAY_TYPE_ID"));
		tmpCase.setPayCenterId(payCenterInfo.get("PAY_CENTER_ID"));

		// 新增一些控制参数
		tmpCase.setIsParentChnSetPC("true");// 商户所属渠道是否设置了支付方式 默认已设置
		tmpCase.setIsClickFirstRowMerchant("true");
		tmpCase.setIsSelectPCName("true");
		tmpCase.setIsConfirmSelectPT("true");
		tmpCase.setIsConfirmSelectPC("true");
		tmpCase.setIsConfirmSelectPT("true");
		tmpCase.setIsSetNewClearAccount("true");
		tmpCase.setIsConfirmSelectExistBank("true");
		tmpCase.setIsSelectBank("true");
		tmpCase.setIsConfirmSelectBank("true");
		// 新增商户Id用于定位使用
		tmpCase.setMerchantId(merchantInfo.get("MERCHANT_ID"));
		tmpCase.setIsSave("true");
		tmpCase.setIsConfirmSave("true");
		tmpCase.setIsSelectPT("true");
		tmpCase.setErrorMsg("");
	}

	

	public static MerchantPCAddCaseBean[][] data() {
		List<MerchantPCAddCaseBean> list = new ArrayList<>();
		double payConfScope = MerchantPCHelp.getMerchantPayConfScope();
		String PCSCOPE = SwiftPass.convertDTS(payConfScope);
		String[] needs = { "isSelectPCName", "isSelectPT", "clrRate", "minLimit", "maxLimit", "isSelectBank",
				"bankAccount", "bankAccountOwner", "isSave", "isConfirmSave"};
		String[] errorMsg = { "请先选择支付通道", "请选择支付类型", "请输入结算费率", "请输入单笔最小限额", "请输入单笔最大限额", "请选择开户银行", "请输入银行卡号",
				"请输入开户人", "正常关闭", "正常取消" };
		for (int i = 0; i < needs.length; i++) {
			MerchantPCAddCaseBean oneCase = SwiftPass.copy(tmpCase);
			if(needs[i].equals("isSelectPCName")){
				oneCase.setIsSelectPCName("");
			}else if(needs[i].equals("isSelectPT")){
				oneCase.setIsSelectPT("");
				oneCase.setIsConfirmSelectPT("");
			}else if(needs[i].equals("clrRate")){
				oneCase.setClrRate("");
			}else if(needs[i].equals("minLimit")){
				oneCase.setMinLimit("");
			}else if(needs[i].equals("maxLimit")){
				oneCase.setMaxLimit("");
			}else if(needs[i].equals("isSelectBank")){
				oneCase.setIsSelectBank("");
			}else if(needs[i].equals("bankAccount")){
				oneCase.setBankAccount("");
			}else if(needs[i].equals("bankAccountOwner")){
				oneCase.setBankAccountOwner("");
			}else if(needs[i].equals("subBankCode")){
				oneCase.setSubBankCode("");
			}else if(needs[i].equals("isSave")){
				oneCase.setIsSave("");
			}else if(needs[i].equals("isConfirmSave")){
				oneCase.setIsConfirmSave("");
			}else{
				
			}
			oneCase.setErrorMsg(errorMsg[i]);
			oneCase.setCASE_NAME(errorMsg[i]);
			list.add(oneCase);
		}
		// 支付中心、支付类型都为空,提示：请选择支付中心
		MerchantPCAddCaseBean oneCase = SwiftPass.copy(tmpCase);
		oneCase.setIsSelectPCName("");
		oneCase.setIsSelectPT("");
		oneCase.setErrorMsg("请选择支付通道");
		list.add(oneCase);
//		//新增联银号不为12，提示：联行号必须为12位数字！
//		MerchantPCAddCaseBean illegeCase = SwiftPass.copy(tmpCase);
//		illegeCase.setSubBankCode(RandomStringUtils.randomNumeric(8));
//		illegeCase.setErrorMsg("联行号必须为12位数字！");
//		list.add(illegeCase);

		// 结算费率必须在【】之间
		MerchantPCAddCaseBean illegleMaxBillRateCaseMap =SwiftPass.copy(tmpCase);
		illegleMaxBillRateCaseMap.setClrRate("1001");
		illegleMaxBillRateCaseMap.setErrorMsg(
				"商户[" + merchantInfo.get("MERCHANT_NAME") + "]:结算费率必须在[" + PCSCOPE + "‰，1000‰]之间。");
		illegleMaxBillRateCaseMap.setCASE_NAME("商户[" + merchantInfo.get("MERCHANT_NAME") + "]:结算费率必须在[" + PCSCOPE + "‰，1000‰]之间。");
		list.add(illegleMaxBillRateCaseMap);
		
		
		MerchantPCAddCaseBean illegleLessBillRateCaseMap = SwiftPass.copy(illegleMaxBillRateCaseMap);
		illegleLessBillRateCaseMap.setClrRate((payConfScope - 1) + "");
		illegleLessBillRateCaseMap.setCASE_NAME("结算费率最低值校验...");
		list.add(illegleLessBillRateCaseMap);

		// 单笔最小额度大于单笔最大额度
		MerchantPCAddCaseBean illegleMinMoreThanMaxCaseMap = SwiftPass.copy(tmpCase);
		illegleMinMoreThanMaxCaseMap.setMinLimit("2");
		illegleMinMoreThanMaxCaseMap.setMaxLimit("1");
		illegleMinMoreThanMaxCaseMap.setErrorMsg(
				"商户[" + merchantInfo.get("MERCHANT_NAME") + "]:单笔最小限额不能大于单笔最大限额");
		illegleMinMoreThanMaxCaseMap.setCASE_NAME("单笔最小额度大于单笔最大额度");
		list.add(illegleMinMoreThanMaxCaseMap);

		// 所选的商户为门店（直营商户），点击新增按钮提示用例
		MerchantPCAddCaseBean directMchCaseMap = SwiftPass.copy(tmpCase);
		String directStoreId = MerchantPCHelp.getMerchantId(MerchantType.DIRECT.getSCode()).get("MERCHANT_ID");
		directMchCaseMap.setMerchantId(directStoreId);
		directMchCaseMap.setErrorMsg("新增直营商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		directMchCaseMap.setCASE_NAME("新增直营商户的支付类型配置校验...");
		list.add(directMchCaseMap);

		// 所选商户为门店（加盟商户）,点击新增按钮提示用例
		MerchantPCAddCaseBean joinMchCaseMap = SwiftPass.copy(tmpCase);
		String joinStoreId = MerchantPCHelp.getMerchantId(MerchantType.JOIN.getSCode()).get("MERCHANT_ID");
		joinMchCaseMap.setMerchantId(joinStoreId);
		joinMchCaseMap.setErrorMsg("新增加盟商户的支付类型配置，将不再同步大商户的支付类型配置信息，请谨慎操作！！！");
		joinMchCaseMap.setCASE_NAME("新增加盟商户的支付类型配置校验...");
		list.add(joinMchCaseMap);

		// 新增成功1.新录入结算账户 2.新增失败-重复添加（关联已有卡）
		MerchantPCAddCaseBean successAddCaseMap = SwiftPass.copy(tmpCase);
		successAddCaseMap.setClrRate((payConfScope + 1) + "");
		successAddCaseMap.setCASE_NAME("新录入结算账户...");
		list.add(successAddCaseMap);
		
		MerchantPCAddCaseBean repeatAddCaseMap = SwiftPass.copy(successAddCaseMap);
		String mchIdWithPC = MerchantPCHelp.getMchIdWithPC().get("MERCHANT_ID");
		String mNameWithPC = MerchantPCDBOperations.getMerchantAndDetailInfos(mchIdWithPC).get("MERCHANT_NAME");
		repeatAddCaseMap.setIsSetNewClearAccount("");
		repeatAddCaseMap.setMerchantId(mchIdWithPC);
		String existBankCode = MerchantPCDBOperations.getBankAccountInfoByMchId(repeatAddCaseMap.getMerchantId())
				.get("ACCOUNT_CODE");
		repeatAddCaseMap.setExistBankCode(existBankCode);
		repeatAddCaseMap.setErrorMsg("商户[" + mNameWithPC + "]:商户支付类型配置已存在");
		repeatAddCaseMap.setCASE_NAME("新增失败-重复添加（关联已有卡）...");
		list.add(repeatAddCaseMap);

		// 商户父渠道没有设置支付方式
		List<HashMap<String, String>> mchParentChnNoPC = MerchantPCHelp.getPatentChnNoPCMchId();
		MerchantPCAddCaseBean parentChnNoPCCaseMap = SwiftPass.copy(tmpCase);
		parentChnNoPCCaseMap.setIsParentChnSetPC("");// 不为true，商户父渠道未设置支付方式
		parentChnNoPCCaseMap.setMerchantId(mchParentChnNoPC.get(1).get("MERCHANT_ID"));
		parentChnNoPCCaseMap.setParentClrRate("");
		parentChnNoPCCaseMap.setErrorMsg("请设置父渠道相关信息");
		parentChnNoPCCaseMap.setCASE_NAME("请设置父渠道相关信息...s");
		list.add(parentChnNoPCCaseMap);
		
		MerchantPCAddCaseBean parentChnNoPCCaseMap_ = SwiftPass.copy(parentChnNoPCCaseMap);
		parentChnNoPCCaseMap_.setParentClrRate("1001");
		parentChnNoPCCaseMap_.setErrorMsg(
				"渠道[" + mchParentChnNoPC.get(0).get("CHANNEL_NAME") + "]:成本费率必须在[" + PCSCOPE + "‰，1000‰]之间。");
		parentChnNoPCCaseMap_.setCASE_NAME("渠道成本费率区间校验...");
		list.add(parentChnNoPCCaseMap_);
		
		parentChnNoPCCaseMap_ = SwiftPass.copy(parentChnNoPCCaseMap_);
		parentChnNoPCCaseMap_.setParentClrRate((payConfScope - 1) + "");
		parentChnNoPCCaseMap_.setCASE_NAME("渠道成本费率超出区间校验...");
		list.add(parentChnNoPCCaseMap_);
		
		// 新增成功用例
		MerchantPCAddCaseBean successAddMchCaseMap = SwiftPass.copy(tmpCase);
		successAddMchCaseMap.setIsParentChnSetPC("");// 不为true，商户父渠道未设置支付方式
		successAddMchCaseMap.setMerchantId(mchParentChnNoPC.get(1).get("MERCHANT_ID"));
		successAddMchCaseMap.setClrRate((payConfScope + 1) + "");
		successAddMchCaseMap.setParentClrRate((payConfScope + 1) + "");
		successAddMchCaseMap.setCASE_NAME("新增一条成功用例...");
		list.add(successAddMchCaseMap);
		
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantPCAddCaseBean[][] :: new);
	}

	public static void main(String... strings) {
		for (MerchantPCAddCaseBean [] map : data()) {
			System.out.println(map[0]);
		}
	}
}
