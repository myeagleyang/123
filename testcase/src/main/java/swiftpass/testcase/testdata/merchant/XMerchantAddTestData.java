package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import irsy.testcase.casebeans.MerchantAddCaseBean;
import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.EmpDBOperations;
import swiftpass.elements.merchant.MerchantAddElements;
import swiftpass.page.enums.BankAccountType;
import swiftpass.page.enums.Currency;
import swiftpass.page.enums.DealType;
import swiftpass.page.enums.IDType;
import swiftpass.page.enums.MerchantType;
import swiftpass.testcase.RunCaseProcessor;
import swiftpass.testcase.casebeans.ChannelAddCaseBean;
import swiftpass.utils.HTTPUtils;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.SPServiceUrls;

public class XMerchantAddTestData {
	private static final MerchantAddCaseBean tmpCase = new MerchantAddCaseBean();
	private static final String ESCAPE = "";
	static {
		String suffix = SwiftPass.getHHmmssSSSString().substring(2);
		String merchantName = RandomStringUtils.random(4, "大商户普通商户都是商户") + suffix;
		HashMap<String, String> parentChn = ChannelDBOperations.acceptOrgUniqueChannel();
		// HashMap<String, String> map = initCaseMapParamsOnPage();
		// map.replace("big_or_normal", MerchantType.BIG.getPlainText());
		tmpCase.setBig_or_normal(MerchantType.BIG.getPlainText());
		tmpCase.setMerchantType(MerchantType.BIG.getPlainText());
		// 所属渠道
		String[] chnItems = { "渠道名称", "渠道编号" };
		tmpCase.setIsSelectChannel("true");
		tmpCase.setChannelItem(chnItems[RandomUtils.nextInt(0, chnItems.length)]);
		tmpCase.setChnId(parentChn.get("CHANNEL_ID"));
		tmpCase.setChnName(parentChn.get("CHANNEL_NAME"));
		if (tmpCase.getChannelItem().equals(chnItems[0]))
			tmpCase.setChnNameOrId(tmpCase.getChnName());
		else
			tmpCase.setChnNameOrId(tmpCase.getChnId());

		tmpCase.setIsConfirmSelectChannel("true");
		tmpCase.setMerchantName(merchantName);
		tmpCase.setShortName(merchantName.substring(1));
		// 所属业务员
		String[] empItems = { "业务员名称", "业务员编号" };
		HashMap<String, String> emp = EmpDBOperations.getRandomEmpOfChannel(tmpCase.getChnId());
		tmpCase.setIsSelectEmp("false");
		tmpCase.setEmpItem(empItems[RandomUtils.nextInt(0, empItems.length)]);
		if (tmpCase.getEmpItem().equals(empItems[0]))
			tmpCase.setEmpNameOrId(emp.get("EMP_NAME"));
		else
			tmpCase.setEmpNameOrId(emp.get("EMP_ID"));
		tmpCase.setIsConfirmSelectEmp("false");

		tmpCase.setCurrency(Currency.values()[RandomUtils.nextInt(0, Currency.values().length)].getPlainText());
		// 行业类别
		String[] industry = DBOperations.getRandomIndustryChain();
		tmpCase.setIsSelectIndustry("true");
		tmpCase.setIndustryNameChain(industry[0]);
		tmpCase.setIndustryIdChain(industry[1]);
		tmpCase.setIsConfirmSelectIndustry("true");

		tmpCase.setProvince("广东");
		tmpCase.setCity("深圳");
		tmpCase.setAddress("广东省深圳市南山区");
		tmpCase.setTel(DataGenerator.generateTel()); // 电话、传真共用
		tmpCase.setEmail(DataGenerator.generateEmail());

		tmpCase.setWebsite(DataGenerator.generateWebsite());
		tmpCase.setPrincipal(DataGenerator.generateZh_CNName());
		tmpCase.setIdCode(DataGenerator.generateIdCardCode());

		tmpCase.setPrincipalPhone(DataGenerator.generatePhone());
		tmpCase.setServiceTel(DataGenerator.generateTel());
		tmpCase.setThrMchId(RandomStringUtils.randomNumeric(12));

		// 结算资料
		HashMap<String, String> bank = DBOperations.getRandomBank();
		tmpCase.setIsClickCheckbox("true");
		// 开户银行
		tmpCase.setIsSelectBank("true");
		tmpCase.setBankId(bank.get("BANK_ID"));
		tmpCase.setBankName(bank.get("BANK_NAME"));
		tmpCase.setIsConfirmSelectBank("true");

		tmpCase.setAccount(DataGenerator.generateBankCode());
		tmpCase.setAccountOwner(DataGenerator.generateZh_CNName());
		BankAccountType[] types = BankAccountType.values();
		tmpCase.setAccountType(types[RandomUtils.nextInt(0, types.length)].getPlainText());
		tmpCase.setSubBankName(bank.get("BANK_NAME") + "深圳支行");
		IDType[] ids = IDType.values();
		tmpCase.setIdType(ids[RandomUtils.nextInt(0, ids.length)].getPlainText());
		tmpCase.setSubBankNO(RandomStringUtils.randomNumeric(12));

		tmpCase.setIsInsideAccount("");
		tmpCase.setIsSave("true");
		tmpCase.setIsConfirmSave("true");
		tmpCase.setMessage("");
	}
	// 异常用例提示消息
	private static final String NO_SET_PARENT_CHANNEL_MSG = "请选择所属渠道", NO_SET_MERCHANT_NAME_MSG = "请填写商户名称",
			NO_SET_SHORT_NAME_MSG = "请填写商户简称", NO_SET_MERCHANT_TYPE_MSG = "请选择商户类型", NO_SET_INDUSTRY_MSG = "请选择行业类别",
			NO_SET_PROVINCE_MSG = "请选择省份", NO_SET_CITY_MSG = "请选择城市", NO_SET_EMAIL_MSG = "请输入邮箱",
			NO_SET_PRINCIPAL_PHONE_MSG = "请输入负责人手机", NO_SET_SERVICE_TEL_MSG = "请输入客服电话",
			NO_SET_CLR_BANK_MSG = "请选择开户银行", NO_SET_ACCOUNT_MSG = "请输入银行卡号", NO_SET_ACCOUNT_OWNER_MSG = "请输入开户人",
			NO_SET_ACCOUNT_TYPE_MSG = "请输入选择账户类型", NO_SET_SUB_BANK_NO_MSG = "请输入网点号(联行号)", // 必填未填页面提示消息

			MERCHANT_NAME_BEYOND_LENGTH_MSG = "商户名称长度在1~64位之间！",
			// SHORT_NAME_BEYOND_LENGTH_MSG = "商户简称长度在1~20位之间！",
			EMAIL_WRONG_FORMAT_MSG = "邮箱格式不正确，不能包含特殊字符", PRINCIPAL_PHONE_WRONG_FORMAT_MSG = "手机号码格式不正确", // 手机号码11位数字
			CLOSE_PAGE_MSG = "正常关闭", SERVICE_TEL_WRONG_FORMAT_MSG = "客服电话格式不正确", // 客服电话7-15位数字
			CANCEL_SAVE_MSG = "正常取消", ACCOUNT_NAME_LESS_LENGTH_ON_ENTERPRISE_MSG = "企业账户开户人长度不得小于3个汉字";

	public static MerchantAddCaseBean[][] data() {
		List<MerchantAddCaseBean> caseList = new ArrayList<>();
		MerchantAddCaseBean successCase = SwiftPass.copy(tmpCase);
		String suffix = SwiftPass.getHHmmssSSSString().substring(2);
		String merchantName = RandomStringUtils.random(4, "大商户普通商户都是商户") + suffix;
		tmpCase.setMerchantName(merchantName);
		successCase.setCASE_NAME("成功添加一条数据");
		caseList.add(successCase);

		// 异常用例——必填字段未填(为节省构造数据的时间，异常用例的先从成功用例拷贝）
		MerchantAddCaseBean illegalNoSelectBean = SwiftPass.copy(tmpCase);
		illegalNoSelectBean.setIsSelectChannel("false");
		illegalNoSelectBean.setChnId("");
		illegalNoSelectBean.setChnName("");
		illegalNoSelectBean.setMessage(NO_SET_PARENT_CHANNEL_MSG);
		illegalNoSelectBean.setCASE_NAME("所属渠道非空校验...");
		caseList.add(illegalNoSelectBean);

		MerchantAddCaseBean illegalNoSetMerchantNameCase = SwiftPass.copy(tmpCase);
		illegalNoSetMerchantNameCase.setMerchantName("");
		illegalNoSetMerchantNameCase.setMessage(NO_SET_MERCHANT_NAME_MSG);
		illegalNoSetMerchantNameCase.setCASE_NAME("商户名称非空校验...");
		caseList.add(illegalNoSetMerchantNameCase);

		MerchantAddCaseBean illegalNoSetShortNameCase = SwiftPass.copy(tmpCase);
		illegalNoSetShortNameCase.setShortName("");
		illegalNoSetShortNameCase.setMessage(NO_SET_SHORT_NAME_MSG);
		illegalNoSetShortNameCase.setCASE_NAME("商户简称非空校验...");
		caseList.add(illegalNoSetShortNameCase);

		MerchantAddCaseBean illegalNoSelectMTypeCase = SwiftPass.copy(tmpCase);
		illegalNoSelectMTypeCase.setMerchantType("");
		illegalNoSelectMTypeCase.setMessage(NO_SET_MERCHANT_TYPE_MSG);
		illegalNoSelectMTypeCase.setCASE_NAME("商户类型非空校验...");
		caseList.add(illegalNoSelectMTypeCase);

		MerchantAddCaseBean illegalNoSelectIndustryCase = SwiftPass.copy(tmpCase);
		illegalNoSelectIndustryCase.setIsSelectIndustry("false");
		illegalNoSelectIndustryCase.setIndustryIdChain("");
		illegalNoSelectIndustryCase.setIndustryNameChain("");
		illegalNoSelectIndustryCase.setMessage(NO_SET_INDUSTRY_MSG);
		illegalNoSelectIndustryCase.setCASE_NAME("行业类别非空校验...");
		caseList.add(illegalNoSelectIndustryCase);

		MerchantAddCaseBean illegalNoSelectProvinceCase = SwiftPass.copy(tmpCase);
		illegalNoSelectProvinceCase.setProvince("");
		illegalNoSelectProvinceCase.setMessage(NO_SET_PROVINCE_MSG);
		illegalNoSelectProvinceCase.setCASE_NAME("省份非空校验...");
		caseList.add(illegalNoSelectProvinceCase);

		MerchantAddCaseBean illegalNoSelectCityCase = SwiftPass.copy(tmpCase);
		illegalNoSelectCityCase.setCity("");
		illegalNoSelectCityCase.setMessage(NO_SET_CITY_MSG);
		illegalNoSelectCityCase.setCASE_NAME("城市非空校验...");
		caseList.add(illegalNoSelectCityCase);

		MerchantAddCaseBean illegalNoSetEmailCase = SwiftPass.copy(tmpCase);
		illegalNoSetEmailCase.setEmail("");
		illegalNoSetEmailCase.setMessage(NO_SET_EMAIL_MSG);
		illegalNoSetEmailCase.setCASE_NAME("邮箱非空校验...");
		caseList.add(illegalNoSetEmailCase);

		MerchantAddCaseBean illegalNoSetPrincipalPhoneCase = SwiftPass.copy(tmpCase);
		illegalNoSetPrincipalPhoneCase.setPrincipalPhone("");
		illegalNoSetPrincipalPhoneCase.setMessage(NO_SET_PRINCIPAL_PHONE_MSG);
		illegalNoSetPrincipalPhoneCase.setCASE_NAME("负责人手机为空校验...");
		caseList.add(illegalNoSetPrincipalPhoneCase);

		MerchantAddCaseBean illegalNoSetServieTelCase = SwiftPass.copy(tmpCase);
		illegalNoSetServieTelCase.setServiceTel("");
		illegalNoSetServieTelCase.setMessage(NO_SET_SERVICE_TEL_MSG);
		illegalNoSetServieTelCase.setCASE_NAME("客服电话为空校验...");
		caseList.add(illegalNoSetServieTelCase);

		MerchantAddCaseBean illegalNoSetBankCase = SwiftPass.copy(tmpCase);
		illegalNoSetBankCase.setIsSelectBank("false");
		illegalNoSetBankCase.setBankId("");
		illegalNoSetBankCase.setBankName("");
		illegalNoSetBankCase.setMessage(NO_SET_CLR_BANK_MSG);
		illegalNoSetBankCase.setCASE_NAME("开户银行为空校验...");
		caseList.add(illegalNoSetBankCase);

		MerchantAddCaseBean illegalNoSetAccountCase = SwiftPass.copy(tmpCase);
		illegalNoSetAccountCase.setAccount("");
		illegalNoSetAccountCase.setMessage(NO_SET_ACCOUNT_MSG);
		illegalNoSetAccountCase.setCASE_NAME("银行卡号为空校验...");
		caseList.add(illegalNoSetAccountCase);

		MerchantAddCaseBean illegalNoSetAccountOwnerCase = SwiftPass.copy(tmpCase);
		illegalNoSetAccountOwnerCase.setAccountOwner("");
		illegalNoSetAccountOwnerCase.setMessage(NO_SET_ACCOUNT_OWNER_MSG);
		illegalNoSetAccountOwnerCase.setCASE_NAME("开户人为空校验...");
		caseList.add(illegalNoSetAccountOwnerCase);

		MerchantAddCaseBean illegalNoSetAccountTypeCase = SwiftPass.copy(tmpCase);
		illegalNoSetAccountTypeCase.setAccountType("");
		illegalNoSetAccountTypeCase.setMessage(NO_SET_ACCOUNT_TYPE_MSG);
		illegalNoSetAccountTypeCase.setCASE_NAME("账户类型为空校验...");
		caseList.add(illegalNoSetAccountTypeCase);

		
		//修改于2017-04-12，改为非必填项
/*		MerchantAddCaseBean illegalNoSetSubBankNOCase = SwiftPass.copy(tmpCase);
		illegalNoSetSubBankNOCase.setSubBankNO("");
		illegalNoSetSubBankNOCase.setMessage(NO_SET_SUB_BANK_NO_MSG);
		illegalNoSetSubBankNOCase.setCASE_NAME("网点号为空校验...");
		caseList.add(illegalNoSetSubBankNOCase);*/

		// 2.其它异常用例——商户名称、简称长度非法；商户重名非法；邮箱格式非法；负责人手机号格式非法；客服电话非法；
		// 企业账户开户人名称长度非法；正常关闭、取消
		MerchantAddCaseBean illegalMchNameBeyondLengthCase = SwiftPass.copy(tmpCase);
		illegalMchNameBeyondLengthCase.setMerchantName(RandomStringUtils.randomAlphanumeric(65));
		illegalMchNameBeyondLengthCase.setMessage(MERCHANT_NAME_BEYOND_LENGTH_MSG);
		illegalMchNameBeyondLengthCase.setCASE_NAME("商户名称长度校验...");
		caseList.add(illegalMchNameBeyondLengthCase);

		// MerchantAddCaseBean illegalShortNameBeyondLengthCase =
		// SwiftPass.copy(tmpCase);
		// illegalShortNameBeyondLengthCase.setShortName(RandomStringUtils.randomAlphanumeric(21));
		// illegalShortNameBeyondLengthCase.setMessage(SHORT_NAME_BEYOND_LENGTH_MSG);
		// illegalShortNameBeyondLengthCase.setCASE_NAME("商户简称长度校验...");
		// caseList.add(illegalShortNameBeyondLengthCase);

		MerchantAddCaseBean illegalEmailFormatCase1 = SwiftPass.copy(tmpCase);
		illegalEmailFormatCase1.setEmail(DataGenerator.generateEmail().replace("@", ""));
		illegalEmailFormatCase1.setMessage(EMAIL_WRONG_FORMAT_MSG);
		illegalEmailFormatCase1.setCASE_NAME("邮箱未包含@格式校验...");
		caseList.add(illegalEmailFormatCase1);

		MerchantAddCaseBean illegalEmailFormatCase2 = SwiftPass.copy(illegalEmailFormatCase1);
		illegalEmailFormatCase2.setEmail(DataGenerator.generateEmail().replace(".", ""));
		illegalEmailFormatCase2.setCASE_NAME("邮箱未包含.格式校验...");
		caseList.add(illegalEmailFormatCase2);

		MerchantAddCaseBean illegalPrincipalPhoneFormatCase1 = SwiftPass.copy(tmpCase);
		illegalPrincipalPhoneFormatCase1.setPrincipalPhone(DataGenerator.generatePhone().substring(1));
		illegalPrincipalPhoneFormatCase1.setMessage(PRINCIPAL_PHONE_WRONG_FORMAT_MSG);
		illegalPrincipalPhoneFormatCase1.setCASE_NAME("手机号码长度校验 - 10位数...");
		caseList.add(illegalPrincipalPhoneFormatCase1);

		MerchantAddCaseBean illegalPrincipalPhoneFormatCase2 = SwiftPass.copy(illegalPrincipalPhoneFormatCase1);
		illegalPrincipalPhoneFormatCase2.setPrincipalPhone("1" + DataGenerator.generatePhone());
		illegalPrincipalPhoneFormatCase2.setCASE_NAME("手机号码长度校验 - 12位数...");
		caseList.add(illegalPrincipalPhoneFormatCase2);

		MerchantAddCaseBean illegalPrincipalPhoneFormatCase3 = SwiftPass.copy(illegalPrincipalPhoneFormatCase1);
		illegalPrincipalPhoneFormatCase3.setPrincipalPhone("1" + RandomStringUtils.randomAlphanumeric(9) + "a");
		illegalPrincipalPhoneFormatCase3.setCASE_NAME("手机号码格式校验 - 包含字符...");
		caseList.add(illegalPrincipalPhoneFormatCase3);

		MerchantAddCaseBean illegalServiceTelFormatCase = SwiftPass.copy(tmpCase);
		illegalServiceTelFormatCase.setServiceTel(RandomStringUtils.randomNumeric(6));
		illegalServiceTelFormatCase.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase.setCASE_NAME("客服电话长度校验 - 6位数...");
		caseList.add(illegalServiceTelFormatCase);

		MerchantAddCaseBean illegalServiceTelFormatCase2 = SwiftPass.copy(illegalServiceTelFormatCase);
		illegalServiceTelFormatCase2.setServiceTel(RandomStringUtils.randomNumeric(16));
		illegalServiceTelFormatCase2.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase2.setCASE_NAME("客服电话长度校验 - 16位数...");
		caseList.add(illegalServiceTelFormatCase2);

		MerchantAddCaseBean illegalServiceTelFormatCase3 = SwiftPass.copy(illegalServiceTelFormatCase);
		illegalServiceTelFormatCase3.setServiceTel("a" + RandomStringUtils.randomNumeric(14));
		illegalServiceTelFormatCase3.setMessage(SERVICE_TEL_WRONG_FORMAT_MSG);
		illegalServiceTelFormatCase3.setCASE_NAME("客服电话格式校验 - 包含字符...");
		caseList.add(illegalServiceTelFormatCase3);

		MerchantAddCaseBean closePageCase = SwiftPass.copy(tmpCase);
		closePageCase.setIsSave("false");
		closePageCase.setMessage(CLOSE_PAGE_MSG);
		closePageCase.setCASE_NAME("正常关闭校验...");
		caseList.add(closePageCase);

		MerchantAddCaseBean cancelSaveCase = SwiftPass.copy(tmpCase);
		cancelSaveCase.setIsConfirmSave("false");
		cancelSaveCase.setMessage(CANCEL_SAVE_MSG);
		cancelSaveCase.setCASE_NAME("正常取消校验...");
		caseList.add(cancelSaveCase);

		MerchantAddCaseBean illegalLengthEnterpriseAccountNameCase = SwiftPass.copy(tmpCase);
		illegalLengthEnterpriseAccountNameCase.setAccountType(BankAccountType.ENTERPRISE.getPlainText());
		illegalLengthEnterpriseAccountNameCase.setAccountOwner(DataGenerator.generateZh_CNName().substring(1));
		illegalLengthEnterpriseAccountNameCase.setMessage(ACCOUNT_NAME_LESS_LENGTH_ON_ENTERPRISE_MSG);
		illegalLengthEnterpriseAccountNameCase.setCASE_NAME("企业开户人名称长度校验...");
		caseList.add(illegalLengthEnterpriseAccountNameCase);

		List<MerchantAddCaseBean> outList = new ArrayList<>(caseList.size());
		caseList.forEach(element -> outList.add(SwiftPass.copy(element)));
		outList.forEach(outListElement -> {
			if (!StringUtils.isEmpty(outListElement.getMerchantType()))
				outListElement.setBig_or_normal(MerchantType.NORMAL.getPlainText())
						.setMerchantType(MerchantType.NORMAL.getPlainText());
			else
				outListElement.setBig_or_normal(MerchantType.NORMAL.getPlainText());
		});
		caseList.addAll(outList);
		return caseList.stream().map(listElement -> ArrayUtils.toArray(listElement))
				.toArray(MerchantAddCaseBean[][]::new);
	}

	public static void main(String[] args) {
		for ( MerchantAddCaseBean[] s : data()){
			System.out.println(s[0]);
		}
//		Logger logger = SwiftLogger.getLogger();
//		swiftpass.utils.Configuration conf = SwiftPass.getConfiguration("system.conf");
//		String driverType = conf.getValueOfKey("browserType");
//		boolean local = Boolean.parseBoolean(conf.getValueOfKey("local"));
//		String spServer = conf.getValueOfKey("spServer");
//		String loginPath = conf.getValueOfKey("loginPath");
//		String homePath = conf.getValueOfKey("homePath");
//		String loginApiUrl = conf.getValueOfKey("loginApiUrl");
//		String downloadPath = System.getProperty("user.dir") + conf.getValueOfKey("downLoadPath");
//		WebDriver driver = swiftpass.utils.SPDriverFactory.create(driverType, local);
//		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		boolean isSuperAdmin = Boolean.parseBoolean(conf.getValueOfKey("isSuperAdmin"));
//		String userName;
//		String password;
//		if(isSuperAdmin){
//			userName = conf.getValueOfKey("superUserName");
//			password = conf.getValueOfKey("superPassword");
//		} else{
//			userName = conf.getValueOfKey("userName");
//			password = conf.getValueOfKey("password");
//		}
//		driver.get(spServer + loginPath);
//		List<WebElement> rows = driver.findElements(MerchantAddElements.selectBankRowByName);
//		for(WebElement row : rows){
//			if(row.getText().equals("瑞典北欧斯安银行")){
//				row.click();
//			}
//		}	
	}

	public static  MerchantAddCaseBean[][] checkPhoneData(){
		List<MerchantAddCaseBean> phoneList = new ArrayList<>();
		MerchantAddCaseBean casePhone = new MerchantAddCaseBean();
		//所有正常号码段校验
		String[] phoneItems = {"134","135","136","137","138","139","147","150","151","152","157","158","159","178","182","183","184","187","188","130","131","132","145","155","156","171","175","176","185","186","133","149","153","173","177","180","181","189","170"};	
		for(int i =0;i<phoneItems.length;i++){
			casePhone.setPhoneType("0");
			casePhone.setPrincipalPhone(phoneItems[i] + RandomStringUtils.randomNumeric(8));
			casePhone.setMessage("{\"success\":true,\"msg\":\"添加成功！\",\"status\":0}");
			casePhone.setCASE_NAME("手机正常号码段校验...");
			phoneList.add(casePhone);
		}
		//异常手机号码段校验
		String[] exceptionPhoneItems = {"140","141","142","143","144","146","148","154","160","161","172","174","190","200"};
		for(int i =0;i<exceptionPhoneItems.length;i++){
			casePhone.setPhoneType("1");
			casePhone.setPrincipalPhone(exceptionPhoneItems[i] + RandomStringUtils.randomNumeric(8));
			casePhone.setMessage("{\"success\":false,\"errorCode\":\"merchant.principalMobile.format.error\",\"msg\":\"手机号码格式不正确\",\"status\":0}");
			casePhone.setCASE_NAME("手机异常号码段校验...");
			phoneList.add(casePhone);
		}
		return phoneList.stream().map(listElement -> ArrayUtils.toArray(listElement))
				.toArray(MerchantAddCaseBean[][]::new);
	}
	public static HashMap<String,String> getMerchantByPhone(String phoneNo){
		HashMap<String, String> params = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> chs = ChannelDBOperations.allDBChannels();
		String channelId = chs.get(RandomUtils.nextInt(1, chs.size() + 1)).get("CHANNEL_ID");
		BankAccountType accountType = BankAccountType.values()[RandomUtils.nextInt(0, BankAccountType.values().length)]; 
		MerchantType mchType = MerchantType.values()[RandomUtils.nextInt(0, 2)]; 
		DealType dealType = DealType.values()[RandomUtils.nextInt(0, DealType.values().length)];
		String suffix = SwiftPass.getMMddHHmmssString().substring(2);
		params.put("channelId", channelId);
		params.put("channelId_tmp", channelId);
		params.put("feeType", Currency.CNY.toString());	//人民币CNY
		params.put("mchDealType", dealType.getSCode());
		params.put("merchantName", RandomStringUtils.random(5, "二院画师闪亮登场啦") + suffix.substring(5));
		params.put("merchantType", mchType.getSCode());
		params.put("outMerchantId", suffix);
		params.put("salesmanId", "");
		params.put("salesmanId_tmp", "");
		params.put("merchantDetailDto.address", "深圳市南山区中科大厦");
		params.put("merchantDetailDto.city", "010100");	//北京市
		params.put("merchantDetailDto.customerPhone", DataGenerator.generateTel());
		params.put("merchantDetailDto.email", DataGenerator.generateEmail());
		params.put("merchantDetailDto.fax", DataGenerator.generateTel());
		params.put("merchantDetailDto.idCode", "");
		params.put("merchantDetailDto.indentityPhoto", "");
		String[] ind = DBOperations.getRandomIndustryChain();
		params.put("merchantDetailDto.industrId", ind[1].split(">")[2]);
		params.put("merchantDetailDto.industrId_tmp", ind[1].split(">")[2]);
		params.put("merchantDetailDto.licensePhoto", "");
		params.put("merchantDetailDto.merchantShortName", RandomStringUtils.randomAlphabetic(4).toUpperCase());
		params.put("merchantDetailDto.orgPhoto", "");
		params.put("merchantDetailDto.principal", "");
		params.put("merchantDetailDto.principalMobile", phoneNo);
		params.put("merchantDetailDto.protocolPhoto", "");
		params.put("merchantDetailDto.protocolPhotos", "");//TODO WTF
		params.put("merchantDetailDto.province", "010000");
		params.put("merchantDetailDto.tel", DataGenerator.generateTel());
		params.put("merchantDetailDto.webSite", DataGenerator.generateWebsite());
		params.put("inputAccountInfoFlag", "true");
		params.put("bankAccountDto.accountCode", "622202" + RandomStringUtils.randomNumeric(12));
		params.put("bankAccountDto.accountName", RandomStringUtils.randomAlphabetic(5));
		params.put("bankAccountDto.accountType", accountType.getSCode());
		HashMap<String, String> bank = DBOperations.getRandomBank();
		params.put("bankAccountDto.bankId", bank.get("BANK_ID"));//包商银行
		params.put("bankAccountDto.bankId_tmp", bank.get("BANK_ID"));
		params.put("bankAccountDto.bankName", bank.get("BANK_NAME"));
		params.put("bankAccountDto.contactLine", suffix.substring(4));
		params.put("bankAccountDto.idCard", DataGenerator.generateIdCardCode());
		params.put("bankAccountDto.idCardType", IDType.values()[RandomUtils.nextInt(0, IDType.values().length)].ordinal() + 1 + "");
		params.put("bankAccountDto.tel", DataGenerator.generatePhone());
		return params;
	}
}