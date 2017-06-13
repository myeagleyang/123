package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.DataGenerator;
import swiftpass.page.channel.ChannelAddPage.SelectChannelItem;
import swiftpass.page.enums.BankAccountType;
import swiftpass.page.enums.ChannelProperties;
import swiftpass.page.enums.IDType;
import swiftpass.testcase.casebeans.ChannelAddD0CaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;

/**
 * 
 * @author sunhaojie
 * 2017-4-10
 */
public class XChannelAddD0TestData {
	private static final ChannelAddD0CaseBean successAddCase = new ChannelAddD0CaseBean();
	private static final String ESCAPE = "";
	static{
		successAddCase.setCASE_NAME("成功新增一个渠道");
		Map<String, String> parentChannel = ChannelAddD0Helper.uniqueMainBranch();
		Map<String, String> randBank = ChannelAddD0Helper.randBank();
		successAddCase.setIsSelectParentChannel("true")
			.setParentNameIdItem(SelectChannelItem.values()[RandomUtils.nextInt(0, 2)].getDescText())
			.setParentChannelId(parentChannel.get("CHANNEL_ID"))
			.setParentChannelName(parentChannel.get("CHANNEL_NAME"))
			.setIsConfirmSelectParentChannel("true")
			.setNewChannelName(DataGenerator.generateNewChannelName())
			.setProvince("广东")
			.setCity("深圳")
			.setAddress("南山区中科大厦25楼")
			.setPrincipal(DataGenerator.generateZh_CNName())
			.setTel(DataGenerator.generateTel())
			.setEmail(DataGenerator.generateEmail())
			.setRemark(RandomStringUtils.randomAlphanumeric(32))
			.setThirdChannelId(RandomStringUtils.randomNumeric(12))
			.setIsSelectBank("true")
			.setBankId(randBank.get("BANK_ID"))
			.setBankName(randBank.get("BANK_NAME"))
			.setIsConfirmSelectBank("true")
			.setBankAccount(DataGenerator.generateBankCode())
			.setBankAccountName(DataGenerator.generateZh_CNName())
			.setBankAccountProvince("广东")
			.setBankAccountCity("深圳")
			.setBankAccountType(BankAccountType.PERSONAL.getSCode())
			.setIdType(IDType.IDCARD.getScode())
			.setIdCode(DataGenerator.generateIdCardCode())
			.setBankSubBranch("高新园支行")
			.setBankAccountPhone(DataGenerator.generatePhone())
			.setSiteNO(RandomStringUtils.randomNumeric(4))
			.setIsSave("true")
			.setIsConfirmSave("true")
			.setMessage(ESCAPE)
			.setNewChannelType(ChannelProperties.D0.getSCode())
			.setIsSelectHNAcc("true")
			.setYesOrNoHNAcc("是");
	}
	
	public static ChannelAddD0CaseBean[][] data(){
		List<ChannelAddD0CaseBean> caseList = new ArrayList<>();
		
		// 1.成功新增一个D0渠道\同名D0渠道
		ChannelAddD0CaseBean addInnerSuccess1 = SwiftPass.copy(successAddCase);
		addInnerSuccess1.setCASE_NAME("成功新增一个D0渠道.");
		ChannelAddD0CaseBean addInnerSuccess2 = SwiftPass.copy(successAddCase);
		addInnerSuccess2.setCASE_NAME("成功新增一个重名D0渠道.");
		caseList.add(addInnerSuccess1);
		caseList.add(addInnerSuccess2);
		
		// 2.必填未填要报错误信息
		ChannelAddD0CaseBean nullParentChannel = SwiftPass.copy(successAddCase);
		nullParentChannel.setIsSelectParentChannel("false").setParentChannelId(ESCAPE).setParentChannelName(ESCAPE)
			.setMessage(ChannelAddD0Helper.NULL_PARENT_CHANNEL_MESSAGE).setCASE_NAME("D0渠道新增所属渠道为空.");
		caseList.add(nullParentChannel);
		
		ChannelAddD0CaseBean nullNewChannelName = SwiftPass.copy(successAddCase);
		nullNewChannelName.setNewChannelName(ESCAPE).setMessage(ChannelAddD0Helper.NULL_NEW_CHANNEL_NAME_MESSAGE).setCASE_NAME("D0渠道新增渠道名为空.");
		caseList.add(nullNewChannelName);
		
		ChannelAddD0CaseBean nullProvince = SwiftPass.copy(successAddCase);
		nullProvince.setProvince(ESCAPE).setMessage(ChannelAddD0Helper.NULL_PROVINCE_MESSAGE).setCASE_NAME("D0渠道新增省份为空.");
		caseList.add(nullProvince);
		
		ChannelAddD0CaseBean nullCity = SwiftPass.copy(successAddCase);
		nullCity.setCity(ESCAPE).setMessage(ChannelAddD0Helper.NULL_CITY_MESSAGE).setCASE_NAME("D0渠道新增城市为空.");
		caseList.add(nullCity);
		
		ChannelAddD0CaseBean nullAddress = SwiftPass.copy(successAddCase);
		nullAddress.setAddress(ESCAPE).setMessage(ChannelAddD0Helper.NULL_ADDRESS_MESSAGE).setCASE_NAME("D0渠道新增地址为空.");
		caseList.add(nullAddress);
		
		ChannelAddD0CaseBean nullEmail = SwiftPass.copy(successAddCase);
		nullEmail.setEmail(ESCAPE).setMessage(ChannelAddD0Helper.NULL_EMAIL_MESSAGE).setCASE_NAME("D0渠道新增邮箱为空.");
		caseList.add(nullEmail);
		
		ChannelAddD0CaseBean nullPrincipal = SwiftPass.copy(successAddCase);
		nullPrincipal.setPrincipal(ESCAPE).setMessage(ChannelAddD0Helper.NULL_PRINCIPAL_MESSAGE).setCASE_NAME("D0渠道新增负责人为空.");
		caseList.add(nullPrincipal);
		
		ChannelAddD0CaseBean nullTel = SwiftPass.copy(successAddCase);
		nullTel.setTel(ESCAPE).setMessage(ChannelAddD0Helper.NULL_TEL_MESSAGE).setCASE_NAME("D0渠道新增电话为空.");
		caseList.add(nullTel);
		
		ChannelAddD0CaseBean nullBank = SwiftPass.copy(successAddCase);
		nullBank.setIsSelectBank("false").setBankName(ESCAPE).setBankId(ESCAPE).setIsConfirmSelectBank("false")
				.setMessage(ChannelAddD0Helper.NULL_BANK_MESSAGE).setCASE_NAME("D0渠道新增开户银行为空.");
		caseList.add(nullBank);
		
		ChannelAddD0CaseBean nullBankAccount = SwiftPass.copy(successAddCase);
		nullBankAccount.setBankAccount(ESCAPE).setMessage(ChannelAddD0Helper.NULL_BANK_ACCOUNT_MESSAGE).setCASE_NAME("D0渠道新增银行卡号为空.");
		caseList.add(nullBankAccount);
		
		ChannelAddD0CaseBean nullBankAccountName = SwiftPass.copy(successAddCase);
		nullBankAccountName.setBankAccountName(ESCAPE).setMessage(ChannelAddD0Helper.NULL_ACCOUNT_NAME_MESSAGE).setCASE_NAME("D0渠道新增开户名称为空.");
		caseList.add(nullBankAccountName);
		
		ChannelAddD0CaseBean nullBankAccountType = SwiftPass.copy(successAddCase);
		nullBankAccountType.setBankAccountType(ESCAPE).setMessage(ChannelAddD0Helper.NULL_BANK_ACCOUNT_TYPE_MESSAGE).setCASE_NAME("D0渠道新增账户类型为空.");
		caseList.add(nullBankAccountType);
		
		//	3.其它
		ChannelAddD0CaseBean notSave = SwiftPass.copy(successAddCase);
		notSave.setIsSave("false").setMessage(ChannelAddD0Helper.NOT_SAVE_MESSAGE).setCASE_NAME("D0渠道新增正常关闭新增页面.");
		caseList.add(notSave);
		
		ChannelAddD0CaseBean notConfirmSave = SwiftPass.copy(successAddCase);
		notConfirmSave.setIsConfirmSave("false").setMessage(ChannelAddD0Helper.NOT_CONFIRM_SAVE_MESSAGE).setCASE_NAME("D0渠道新增正常取消确认新增并关闭新增页面.");
		caseList.add(notConfirmSave);
		
		ChannelAddD0CaseBean newChannelNameBeyondLength = SwiftPass.copy(successAddCase);
		newChannelNameBeyondLength.setNewChannelName(RandomStringUtils.randomAlphabetic(33))
			.setMessage(ChannelAddD0Helper.BEYOND_NEW_CHANNEL_NAME_LENGTH_MESSAGE).setCASE_NAME("D0渠道新增渠道名称长度非法.");;
		caseList.add(newChannelNameBeyondLength);
		
		ChannelAddD0CaseBean illegalBankAccount = SwiftPass.copy(successAddCase);
		illegalBankAccount.setBankAccount(RandomStringUtils.randomAlphabetic(16).concat("gg"))
			.setMessage(ChannelAddD0Helper.ILLEGAL_BANK_CODE_MESSAGE).setCASE_NAME("D0渠道新增银行卡号包含非数字字符.");;
		caseList.add(illegalBankAccount);
		
		ChannelAddD0CaseBean enterpriseAccountNameLessThan3 = SwiftPass.copy(successAddCase);
		enterpriseAccountNameLessThan3.setBankAccountType(BankAccountType.ENTERPRISE.getSCode()).setBankAccountName("企业")
			.setMessage(ChannelAddD0Helper.ENTERPRISE_ACCOUNT_NAME_LENGTH_LESS_THAN_3_MESSAGE).setCASE_NAME("D0渠道新增企业账户类型开户人名字小于3.");
		caseList.add(enterpriseAccountNameLessThan3);
		
		ChannelAddD0CaseBean acceptOrgAttachBeyond1Channel = SwiftPass.copy(successAddCase);
		Map<String, String> acceptOrg = ChannelAddD0Helper.acceptOrg();
		acceptOrgAttachBeyond1Channel.setNewChannelName(DataGenerator.generateNewChannelName())
			.setParentChannelId(acceptOrg.get("CHANNEL_ID")).setParentChannelName(acceptOrg.get("CHANNEL_NAME"))
			.setMessage(ChannelAddD0Helper.ACCEPT_ORG_ATTACH_MORE_THAN_1_INNER_MESSAGE).setCASE_NAME("D0渠道新增在受理机构下挂新渠道失败.");
		caseList.add(acceptOrgAttachBeyond1Channel);
		
		List<ChannelAddD0CaseBean> outList = new ArrayList<>(caseList.size());
		caseList.forEach(element -> outList.add(SwiftPass.copy(element)));
		outList.forEach(
				outListElement -> { outListElement.setNewChannelType(ChannelProperties.OUTTER.getSCode()).setCASE_NAME(outListElement.getCASE_NAME().replace("D0渠道", "外部渠道"));});
		caseList.addAll(outList);
		return caseList.stream().map(listElement -> ArrayUtils.toArray(listElement)).toArray(ChannelAddD0CaseBean[][]::new);
	}
	public static void main(String...args){
		for(ChannelAddD0CaseBean[] cb : data()){
			System.out.println(cb[0]);
		}
		DBUtil.closeDBResource();
	}
}

class ChannelAddD0Helper{
	public static final String NULL_PARENT_CHANNEL_MESSAGE = "请选择所属渠道";
	public static final String NULL_NEW_CHANNEL_NAME_MESSAGE = "请填写渠道名称";
	public static final String NULL_PROVINCE_MESSAGE = "请选择省份";
	public static final String NULL_CITY_MESSAGE = "请选择城市";
	public static final String NULL_ADDRESS_MESSAGE = "请输入地址";
	public static final String NULL_EMAIL_MESSAGE = "请输入邮箱";
	public static final String NULL_PRINCIPAL_MESSAGE = "请输入负责人";
	public static final String NULL_TEL_MESSAGE = "请输入电话";
	public static final String NULL_BANK_MESSAGE = "请选择开户银行";
	public static final String NULL_BANK_ACCOUNT_MESSAGE = "请输入银行卡号";
	public static final String NULL_ACCOUNT_NAME_MESSAGE = "请输入开户人";
	public static final String NULL_BANK_ACCOUNT_TYPE_MESSAGE = "请选择账户类型";
	public static final String NOT_SAVE_MESSAGE = "正常关闭";
	public static final String NOT_CONFIRM_SAVE_MESSAGE = "正常取消";
	public static final String ILLEGAL_BANK_CODE_MESSAGE = "银行卡号只能包含数字";
	public static final String BEYOND_NEW_CHANNEL_NAME_LENGTH_MESSAGE = "渠道名称长度在1~32位之间！";
	public static final String ACCEPT_ORG_ATTACH_MORE_THAN_1_INNER_MESSAGE = "受理机构下只允许挂一个渠道节点";
	public static final String ENTERPRISE_ACCOUNT_NAME_LENGTH_LESS_THAN_3_MESSAGE = "企业账户开户人长度不得小于3个汉字";
	
	public static Map<String, String> acceptOrg(){
		String sql = "select channel_id, channel_name from cms_channel where parent_channel = '000000000000'";
		return DBUtil.getXQueryResultMap(sql).get(1);
	}
	
	public static Map<String, String> uniqueMainBranch(){
		String acceptOrgSql = "(select channel_id from cms_channel where parent_channel = '000000000000')";
		StringBuilder sql = new StringBuilder("select channel_id, channel_name from cms_channel where parent_channel = ");
		sql.append(acceptOrgSql);
		return DBUtil.getXQueryResultMap(sql.toString()).get(1);
	}
	
	public static Map<String, String> randBank(){
		String sql = "select bank_id, bank_name from cms_bank where rownum < 100";
		return DBUtil.getXQueryResultMap(sql).get(RandomUtils.nextInt(1, 100));
	}
}