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
import swiftpass.testcase.casebeans.MerchantEditCaseBean;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class XMerchantEditTestData {
	private static final MerchantEditCaseBean tmpCase = new MerchantEditCaseBean();
	 	static{
	 		tmpCase.setCASE_NAME("");
	 		String[] industry = DBOperations.getRandomIndustryChain();
	 		tmpCase.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6))
					.setShortName("sn"+RandomStringUtils.randomAlphanumeric(3))
					.setCurrency(Currency.values()[RandomUtils.nextInt(0, Currency.values().length)].getPlainText())
					.setIndustry(industry[0])
					.setIndustryNameChain(industry[0])
					.setIndustryIdChain(industry[1])
					.setAddress("某个角落")
					.setTel(DataGenerator.generateTel())
					.setEmail(DataGenerator.generateEmail())
					.setWebsite(DataGenerator.generateWebsite())
					.setPrincipal(DataGenerator.generateZh_CNName())
					.setIdCode(DataGenerator.generateIdCardCode())
					.setPrincipalPhone(DataGenerator.generatePhone())
					.setServiceTel(DataGenerator.generateTel())
					.setFax(DataGenerator.generateTel())
					.setThrMchId(RandomStringUtils.randomNumeric(12))
					.setLICENSE_PHOTO("")
					.setINDENTITY_PHOTO("")
					.setPROTOCOL_PHOTO("")
					.setORG_PHOTO("")
					.setIsStore("false")
					.setIsEditIndustry("true")
					.setIsConfirmSelectIndustry("true")
					.setIsEdit("true")
					.setIsConfirmEdit("true")
					.setTEXT("")
					.setMessage("")
					.setIsMerchantNoAuditPass("true");
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
		CANCEL_EDIT_MSG = "正常取消";

	public static MerchantEditCaseBean[][] data(){
		List<MerchantEditCaseBean> list = new ArrayList<>();
		
		//	1.正常编辑商户成功.
		HashMap<String, String> auditPassM = getMerchant("false");	//获取一个审核通过的商户进行编辑
		HashMap<String, String> noAuditPassM = getMerchant("true");	//获取一个非审核通过的商户进行编辑
		SwiftLogger.getLogger().debug("商户编辑使用的审核通过商户： " + auditPassM);
		SwiftLogger.getLogger().debug("商户编辑使用的非审核通过商户：" + noAuditPassM);
		MerchantEditCaseBean successEditCase1 = SwiftPass.copy(tmpCase);
		successEditCase1.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6));
		successEditCase1.setTEXT(String.join("-", noAuditPassM.get("MERCHANT_ID"), noAuditPassM.get("MERCHANT_NAME")));
		successEditCase1.setCASE_NAME("成功编辑一条审核通过的商户...");
		list.add(successEditCase1);
		
		MerchantEditCaseBean successEditCase2 = SwiftPass.copy(tmpCase);
		successEditCase2.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6));
		successEditCase2.setIsMerchantNoAuditPass("false");
		successEditCase2.setTEXT(String.join("-", auditPassM.get("MERCHANT_ID"), auditPassM.get("MERCHANT_NAME")));
		successEditCase2.setCASE_NAME("成功编辑一条未审核通过的商户...");
		list.add(successEditCase2);
		
		//	2.未选中渠道点击编辑，报错
		MerchantEditCaseBean noMerchantEditCase =SwiftPass.copy(tmpCase);
		noMerchantEditCase.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6));
		noMerchantEditCase.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$");
		noMerchantEditCase.setMessage(NO_MERCHANT_EDIT_MSG);
		noMerchantEditCase.setCASE_NAME("未选中渠道点击编辑，报错...");
		list.add(noMerchantEditCase);
		
		//	3.必填字段未填(商户名、简称、行业类别、邮箱、负责人手机、客服电话)
		String postEditName = successEditCase1.getMerchantName();
		MerchantEditCaseBean newFixedCase = SwiftPass.copy(successEditCase1);
		newFixedCase.setTEXT(String.join("-", noAuditPassM.get("MERCHANT_ID"), postEditName));
		MerchantEditCaseBean noSetMerchantNameCase = SwiftPass.copy(newFixedCase);
		noSetMerchantNameCase.setMerchantName("");
		noSetMerchantNameCase.setMessage(NO_SET_MERCHANT_NAME_MSG);
		noSetMerchantNameCase.setCASE_NAME("商户名称为空校验...");
		list.add(noSetMerchantNameCase);
		
		MerchantEditCaseBean noSetShortNameCase = SwiftPass.copy(newFixedCase);
		noSetShortNameCase.setShortName("");
		noSetShortNameCase.setMessage(NO_SET_SHORT_NAME_MSG);
		noSetShortNameCase.setCASE_NAME("商户简称为空校验...");
		list.add(noSetShortNameCase);
		
		MerchantEditCaseBean noSetIndustryCase = SwiftPass.copy(newFixedCase);
		noSetIndustryCase.setIndustry("");
		noSetIndustryCase.setIndustryNameChain("");
		noSetIndustryCase.setIndustryIdChain("");
		noSetIndustryCase.setMessage(NO_SET_INDUSTRY_MSG);
		noSetIndustryCase.setCASE_NAME("行业为空校验...");
		list.add(noSetIndustryCase);
		
		MerchantEditCaseBean noSetEmailCase = SwiftPass.copy(newFixedCase);
		noSetEmailCase.setEmail("");
		noSetEmailCase.setMessage(NO_SET_EMAIL_MSG);
		noSetEmailCase.setCASE_NAME("邮箱为空校验...");
		list.add(noSetIndustryCase);
		
		MerchantEditCaseBean noSetPrincipalPhoneCase = SwiftPass.copy(newFixedCase);
		noSetPrincipalPhoneCase.setPrincipalPhone("");
		noSetPrincipalPhoneCase.setCASE_NAME("负责人手机为空校验...");
		noSetPrincipalPhoneCase.setMessage(NO_SET_PRINCIPAL_PHONE_MSG);
		list.add(noSetPrincipalPhoneCase);
		
		MerchantEditCaseBean noSetServiceTelCase = SwiftPass.copy(newFixedCase);
		noSetServiceTelCase.setServiceTel("");
		noSetServiceTelCase.setMessage(NO_SET_SERVICE_TEL_MSG);
		noSetServiceTelCase.setCASE_NAME("客服电话为空校验...");
		list.add(noSetServiceTelCase);
		
		//	4.非法字段长度或格式（长度：商户名、简称；格式：邮箱、负责人手机、客服电话）
		MerchantEditCaseBean merchantNameBeyondLengthCase = SwiftPass.copy(newFixedCase);
		merchantNameBeyondLengthCase.setMerchantName(RandomStringUtils.randomAlphanumeric(65));
		merchantNameBeyondLengthCase.setMessage(MERCHANT_NAME_BEYOND_LENGTH_MSG);
		merchantNameBeyondLengthCase.setCASE_NAME("商户名称长度大于64位校验...");
		list.add(merchantNameBeyondLengthCase);
		
		MerchantEditCaseBean shortNameBeyondLengthCase = SwiftPass.copy(newFixedCase);
		shortNameBeyondLengthCase.setShortName(RandomStringUtils.randomAlphanumeric(16));
		shortNameBeyondLengthCase.setCASE_NAME("商户简称校验...");
		list.add(shortNameBeyondLengthCase);
		
		MerchantEditCaseBean illegalEmailFormatCase = SwiftPass.copy(newFixedCase);
		illegalEmailFormatCase.setEmail(illegalEmailFormatCase.getEmail().replace("@", ""));
		illegalEmailFormatCase.setMessage(EMAIL_WRONG_FORMAT_MSG);
		illegalEmailFormatCase.setCASE_NAME("邮箱格式校验 - 未包含@字符...");
		list.add(illegalEmailFormatCase);	
		
		MerchantEditCaseBean illegalEmailFormatCase2 = SwiftPass.copy(newFixedCase);
		illegalEmailFormatCase2.setEmail(illegalEmailFormatCase.getEmail().replace("", ""));
		illegalEmailFormatCase2.setMessage(EMAIL_WRONG_FORMAT_MSG);
		illegalEmailFormatCase2.setCASE_NAME("邮箱格式校验 - 未包含.字符...");
		list.add(illegalEmailFormatCase2);
		
		MerchantEditCaseBean illegalPrincipalPhoneFormatCase = SwiftPass.copy(newFixedCase);
		illegalPrincipalPhoneFormatCase.setPrincipalPhone(illegalPrincipalPhoneFormatCase.getPrincipalPhone().substring(1));
		illegalPrincipalPhoneFormatCase.setMessage(PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		illegalPrincipalPhoneFormatCase.setCASE_NAME("手机号码长度校验(小于11位)...");
		list.add(illegalPrincipalPhoneFormatCase);
		
		MerchantEditCaseBean illegalPrincipalPhoneFormatCase2 = SwiftPass.copy(newFixedCase);
		illegalPrincipalPhoneFormatCase2.setPrincipalPhone(illegalPrincipalPhoneFormatCase2.getPrincipalPhone()+"1");
		illegalPrincipalPhoneFormatCase2.setMessage(PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		illegalPrincipalPhoneFormatCase2.setCASE_NAME("手机号码长度校验(大于11位)...");
		list.add(illegalPrincipalPhoneFormatCase2);
		
		MerchantEditCaseBean illegalPrincipalPhoneFormatCase3 = SwiftPass.copy(newFixedCase);
		illegalPrincipalPhoneFormatCase3.setPrincipalPhone(illegalPrincipalPhoneFormatCase3.getPrincipalPhone()+"a");
		illegalPrincipalPhoneFormatCase3.setMessage(PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		illegalPrincipalPhoneFormatCase3.setCASE_NAME("手机号码包含字符校验...");
		list.add(illegalPrincipalPhoneFormatCase3);
		
		MerchantEditCaseBean illegalServiceTelFormatCase1 = SwiftPass.copy(newFixedCase);
		illegalServiceTelFormatCase1.setServiceTel(RandomStringUtils.randomNumeric(6));
		illegalServiceTelFormatCase1.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase1.setCASE_NAME("客服电话长度校验(小于6位数)...");
		list.add(illegalServiceTelFormatCase1);
		
		MerchantEditCaseBean illegalServiceTelFormatCase2 = SwiftPass.copy(newFixedCase);
		illegalServiceTelFormatCase2.setServiceTel(RandomStringUtils.randomNumeric(16));
		illegalServiceTelFormatCase2.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase2.setCASE_NAME("客服电话长度校验(大于15位数)...");
		list.add(illegalServiceTelFormatCase2);
		
		MerchantEditCaseBean illegalServiceTelFormatCase3 = SwiftPass.copy(newFixedCase);
		illegalServiceTelFormatCase3.setServiceTel(RandomStringUtils.randomNumeric(10)+"AT");
		illegalServiceTelFormatCase3.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase3.setCASE_NAME("客服电话格式校验(参差字符)...");
		list.add(illegalServiceTelFormatCase3);
		
		//	5.正常关闭、取消确认编辑并关闭页面
		MerchantEditCaseBean closePageCase = SwiftPass.copy(newFixedCase);
		closePageCase.setIsEdit("false");
		closePageCase.setMessage(CLOSE_PAGE_MSG);
		closePageCase.setCASE_NAME("正常关闭校验...");
		list.add(closePageCase);
		
		MerchantEditCaseBean cancelEditCase = SwiftPass.copy(newFixedCase);
		cancelEditCase.setIsConfirmEdit("false");
		cancelEditCase.setMessage(CANCEL_EDIT_MSG);
		cancelEditCase.setCASE_NAME("正常取消校验...");
		list.add(cancelEditCase);
		
		//	6.门店不可编辑 
		/*MerchantEditCaseBean storeUneditableCase1 = SwiftPass.copy(tmpCase);
		Map<String, String> directStore = getStore(MerchantType.DIRECT);
		storeUneditableCase1.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6));
		storeUneditableCase1.setTEXT(String.join("-", directStore.get("MERCHANT_ID"), directStore.get("MERCHANT_NAME")));
		storeUneditableCase1.setIsStore("true");
		storeUneditableCase1.setCASE_NAME("直营商户编辑...");
		list.add(storeUneditableCase1);
		
		MerchantEditCaseBean storeUneditableCase2 = SwiftPass.copy(tmpCase);
		storeUneditableCase2.setMerchantName("自动化测试" + RandomStringUtils.randomAlphanumeric(6));
		Map<String, String> joinStore = getStore(MerchantType.JOIN);
		storeUneditableCase2.setTEXT(String.join("-", joinStore.get("MERCHANT_ID"), joinStore.get("MERCHANT_NAME")));
		storeUneditableCase2.setIsStore("true");
		storeUneditableCase2.setCASE_NAME("其他类型商户编辑...");
		list.add(storeUneditableCase2);*/
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantEditCaseBean[][]::new );
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
	public static void main(String [] ss){
		for(MerchantEditCaseBean[] res : data()){
			System.out.println(res[0]);
		}
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
