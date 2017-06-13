package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DataGenerator;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.Enable;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.Sex;
import swiftpass.testcase.casebeans.EmpAddCaseBean;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

public class XEmpAddTestData {
	private static final EmpAddCaseBean tmpCase  = new EmpAddCaseBean();
	private static final String
	BEYOND_EMP_ID_LENGTH_MSG = "请填写0到24位字符！",
	BEYOND_EMP_NAME_LENGTH_MSG = "员工姓名长度在1~32位之间！",
	BEYOND_LOGIN_ACCOUT_LENGTH_MSG = "登陆帐号长度在1~32位之间！",
	BEYOND_PHONE_LENGTH_MSG = "请填写1到11位数字！",
	WRONG_PHONE_FORMAT_MSG = "手机号码格式错误",
	WRONG_EMAIL_FORMAT_MSG = "邮箱地址格式不对！",
	BEYOND_DEPART_NAME_LENGTH_MSG = "请填写0到64位字符！",
	BEYOND_POSITION_LENGTH_MSG = "请填写0到64位字符！",
	BEYOND_ID_CODE_LENGTH_MSG = "请填写0到128位字符！",
	BEYOND_REMARK_LENGTH_MSG = "请填写0到256位字符！",
	CLOSE_PAGE_MSG = "正常关闭",
	CANCEL_SAVE_MSG = "正常取消",
	EMP_NAME_CONFLICT_MSG = "该员工姓名在该所属机构已存在",
	NULL_EMPNAME = "请填写员工姓名",
	NULL_LOGINACCOUNT ="请填写登陆帐号",
	NULL_ISSELECTPARENTCHANNEL ="请选择所属渠道",
	NULL_PHONE ="请输入手机号码",
	NULL_EMAIL ="请输入邮箱",
	NULL_ISENABLE = "请选择是否启用";
	static{
		String[] parentCh = parentChannel(ExamineStatus.PASS);
		String[] channelItems = {"渠道编号", "渠道名称"};
		tmpCase.setEmpId(RandomStringUtils.randomNumeric(24));
		tmpCase.setEmpName(DataGenerator.generateZh_CNName());
		tmpCase.setLoginAccount(RandomStringUtils.randomAlphanumeric(32));
		tmpCase.setSex(Sex.values()[RandomUtils.nextInt(0, Sex.values().length)].getSCode());
		tmpCase.setIsSelectParentChannel("true");
		tmpCase.setNameOrIdItem(channelItems[RandomUtils.nextInt(0, channelItems.length)]);
		tmpCase.setNameOrId(tmpCase.getNameOrIdItem().equals(channelItems[0]) ? parentCh[0]: parentCh[1]);
		tmpCase.setIsConfirmSelectParentChannel("true");
		tmpCase.setParentChannelId(parentCh[0]);
		tmpCase.setParentChannelName(parentCh[1]);
		tmpCase.setDepartName(RandomStringUtils.randomAlphabetic(5).toUpperCase());
		tmpCase.setPosition(RandomStringUtils.randomAlphabetic(4).toUpperCase());
		tmpCase.setPhone(DataGenerator.generatePhone());
		tmpCase.setEmail(DataGenerator.generateEmail());
		tmpCase.setIdCode(DataGenerator.generateIdCardCode());
		tmpCase.setIsEnable(Enable.values()[RandomUtils.nextInt(0, Enable.values().length)].getSCode());
		tmpCase.setRemark(RandomStringUtils.randomAlphanumeric(24).toUpperCase());
		tmpCase.setIsSave("true");
		tmpCase.setIsConfirmSave("true");
		tmpCase.setMessage("");
	}
	private static String[] parentChannel(ExamineStatus es){
		String[] idName = null;
		HashMap<Integer, HashMap<String, String>> chs = ChannelDBOperations.queryByStatus(null, es, null);
		if(chs.size() < 1){
			HashMap<String, String> unique = ChannelDBOperations.acceptOrgUniqueChannel();
			String uniqueId = unique.get("CHANNEL_ID");
			HashMap<String, String> ch = ChannelService.addOneChannel(uniqueId);
			if(es.equals(ExamineStatus.NON))
				;
			else{
				if(!unique.get("EXAMINE_STATUS").equals(ExamineStatus.PASS.getSCode()) && 
						!unique.get("EXAMINE_STATUS").equals(ExamineStatus.NEEDAGAIN.getSCode())){
					ChannelAAAService.aaaChannel(uniqueId, ExamineStatus.PASS, ActivateStatus.PASS);
				}
				ChannelAAAService.auditChannel(es, ch.get("CHANNEL_ID"));
			}
			idName = ArrayUtils.toArray(ch.get("CHANNEL_ID"), ch.get("CHANNEL_NAME"));
		}
		HashMap<String, String> randomCh = chs.get(RandomUtils.nextInt(1, chs.size() + 1));
		idName = ArrayUtils.toArray(randomCh.get("CHANNEL_ID"), randomCh.get("CHANNEL_NAME"));
		return idName;
	}
	
	public static EmpAddCaseBean[][] data(){
		List<EmpAddCaseBean> list = new ArrayList<>();
		//1.成功新增业务员测试用例数据
		EmpAddCaseBean successAddCase = SwiftPass.copy(tmpCase);
		list.add(successAddCase);
		
		//2.必填未填异常测试用例数据
		EmpAddCaseBean noSetEmpName = SwiftPass.copy(tmpCase);
		noSetEmpName.setEmpName("");
		noSetEmpName.setCASE_NAME(NULL_EMPNAME);
		noSetEmpName.setMessage(NULL_EMPNAME);
		list.add(noSetEmpName);//未填写员工姓名
		
		//更新于2017-04-13 新版本取消登陆账号项.
		/*EmpAddCaseBean noSetLoginAccount = SwiftPass.copy(tmpCase);
		noSetLoginAccount.setEmpName(DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
		noSetLoginAccount.setLoginAccount("");
		noSetLoginAccount.setMessage(NULL_LOGINACCOUNT);
		noSetLoginAccount.setCASE_NAME(NULL_LOGINACCOUNT);//未填写登录帐号
		list.add(noSetLoginAccount);*/
		
		EmpAddCaseBean noSetParentChannel = SwiftPass.copy(tmpCase);
		noSetParentChannel.setEmpName(DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
		noSetParentChannel.setParentChannelId("");
		noSetParentChannel.setParentChannelName("");
		noSetParentChannel.setMessage(NULL_ISSELECTPARENTCHANNEL);
		noSetParentChannel.setCASE_NAME(NULL_ISSELECTPARENTCHANNEL);
		list.add(noSetParentChannel);//未选择所属渠道
		
		EmpAddCaseBean noSetPhone = SwiftPass.copy(tmpCase);
		noSetPhone.setEmpName(DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
		noSetPhone.setPhone("");
		noSetPhone.setMessage(NULL_PHONE);
		noSetPhone.setCASE_NAME(NULL_PHONE);//未填写手机
		list.add(noSetPhone);
		
		EmpAddCaseBean noSetEmail = SwiftPass.copy(tmpCase);
		noSetEmail.setEmpName(DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
		noSetEmail.setEmail("");
		noSetEmail.setMessage(NULL_EMAIL);
		noSetEmail.setCASE_NAME(NULL_EMAIL);
		list.add(noSetEmail);
		
		
		EmpAddCaseBean noSetEnable = SwiftPass.copy(tmpCase);
		noSetEnable.setEmpName(DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
		noSetEnable.setIsEnable("");
		noSetEnable.setMessage(NULL_ISENABLE);
		noSetEnable.setCASE_NAME(NULL_ISENABLE);
		list.add(noSetEnable);
		
		
		//3.其他异常测试用例数据
		EmpAddCaseBean exCase = SwiftPass.copy(tmpCase);
		EmpAddCaseBean empIdLengthBeyondCase = SwiftPass.copy(exCase);
		empIdLengthBeyondCase.setEmpId(RandomStringUtils.randomNumeric(25));
		empIdLengthBeyondCase.setMessage(BEYOND_EMP_ID_LENGTH_MSG);
		empIdLengthBeyondCase.setCASE_NAME(BEYOND_EMP_ID_LENGTH_MSG);
		list.add(empIdLengthBeyondCase);
		
		EmpAddCaseBean empNameLengthBeyondCase = SwiftPass.copy(exCase);
		empNameLengthBeyondCase.setEmpName(RandomStringUtils.randomAlphanumeric(33));
		empNameLengthBeyondCase.setMessage(BEYOND_EMP_NAME_LENGTH_MSG);
		empNameLengthBeyondCase.setCASE_NAME(BEYOND_EMP_NAME_LENGTH_MSG);
		list.add(empNameLengthBeyondCase);
		
		//需求变更，登陆账号改为序列号，无需人工录入，修改于2017-04-13
	/*	EmpAddCaseBean loginAccountLengthBeyondCase = SwiftPass.copy(exCase);
		loginAccountLengthBeyondCase.setLoginAccount(RandomStringUtils.randomNumeric(33));
		loginAccountLengthBeyondCase.setMessage(BEYOND_LOGIN_ACCOUT_LENGTH_MSG);
		list.add(loginAccountLengthBeyondCase);*/
		
		EmpAddCaseBean departNameLengthBeyondCase = SwiftPass.copy(exCase);
		departNameLengthBeyondCase.setDepartName(RandomStringUtils.randomAlphanumeric(65));
		departNameLengthBeyondCase.setMessage(BEYOND_DEPART_NAME_LENGTH_MSG);
		EmpAddCaseBean positionLengthBeyondCase = SwiftPass.copy(exCase);
		positionLengthBeyondCase.setPosition(RandomStringUtils.randomAlphanumeric(65));
		positionLengthBeyondCase.setMessage(BEYOND_POSITION_LENGTH_MSG);
		list.add(positionLengthBeyondCase);
		
		EmpAddCaseBean phoneLengthBeyondCase = SwiftPass.copy(exCase);
		phoneLengthBeyondCase.setPhone(DataGenerator.generatePhone() + 1);
		phoneLengthBeyondCase.setMessage(BEYOND_PHONE_LENGTH_MSG);
		list.add(phoneLengthBeyondCase);
		
		EmpAddCaseBean wrongFormatPhoneCase = SwiftPass.copy(exCase);
		wrongFormatPhoneCase.setPhone(RandomStringUtils.randomNumeric(10));
		wrongFormatPhoneCase.setMessage(WRONG_PHONE_FORMAT_MSG);
		list.add(wrongFormatPhoneCase);
		EmpAddCaseBean wrongFormatEmailCase = SwiftPass.copy(exCase);
		wrongFormatEmailCase.setEmail("df@d");
		wrongFormatEmailCase.setMessage(WRONG_EMAIL_FORMAT_MSG);
		list.add(wrongFormatEmailCase);
		
		EmpAddCaseBean idCodeLengthBeyondCase = SwiftPass.copy(exCase);
		idCodeLengthBeyondCase.setIdCode(RandomStringUtils.randomNumeric(129));
		idCodeLengthBeyondCase.setMessage(BEYOND_ID_CODE_LENGTH_MSG);
		list.add(idCodeLengthBeyondCase);
		
		EmpAddCaseBean remarkLengthBeyondCase = SwiftPass.copy(exCase);
		remarkLengthBeyondCase.setRemark(RandomStringUtils.randomAlphanumeric(257));
		remarkLengthBeyondCase.setMessage(BEYOND_REMARK_LENGTH_MSG);
		list.add(remarkLengthBeyondCase);
		
		EmpAddCaseBean nameConflictUnderSameChannel = SwiftPass.copy(successAddCase);
		nameConflictUnderSameChannel.setMessage(EMP_NAME_CONFLICT_MSG);
		list.add(nameConflictUnderSameChannel);
		
		//4.正常关闭新增页面、取消新增保存操作并关闭页面测试用例数据
		EmpAddCaseBean closePageCase = SwiftPass.copy(exCase);
		closePageCase.setIsSave("false");
		closePageCase.setMessage(CLOSE_PAGE_MSG);
		closePageCase.setCASE_NAME(CLOSE_PAGE_MSG);
		list.add(closePageCase);
		
		EmpAddCaseBean cancelSaveCase = SwiftPass.copy(exCase);
		cancelSaveCase.setIsConfirmSave("false");
		cancelSaveCase.setMessage(CANCEL_SAVE_MSG);
		cancelSaveCase.setCASE_NAME(CANCEL_SAVE_MSG);
		list.add(cancelSaveCase);
		
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(EmpAddCaseBean [][] ::new );
	}
}