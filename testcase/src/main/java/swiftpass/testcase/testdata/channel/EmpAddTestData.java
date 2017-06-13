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
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

public class EmpAddTestData {
	public static HashMap<String, String>[][] getEmpAddTestData(){
		return getAllEmpAddTestData();
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"empId",
				"empName",
				"loginAccount",
				"sex",
				"parentChannelId", "parentChannelName",
				"departName",
				"position",
				"phone",
				"email",
				"idCode",
				"isEnable",
				"remark"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isSelectParentChannel",
				"nameOrIdItem",
				"nameOrId",
				"isConfirmSelectParentChannel",
				"isSave",
				"isConfirmSave",
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		return map;
	}
	
	private static HashMap<String, String> successCase(){
		HashMap<String, String> sc = caseMapWithCtrlParams();
		String[] parentCh = parentChannel(ExamineStatus.PASS);
		String[] channelItems = {"渠道编号", "渠道名称"};
		sc.replace("empId", RandomStringUtils.randomNumeric(24));
		sc.replace("empName", DataGenerator.generateZh_CNName());
		sc.replace("loginAccount", RandomStringUtils.randomAlphanumeric(32));
		sc.replace("sex", Sex.values()[RandomUtils.nextInt(0, Sex.values().length)].getSCode());
		sc.replace("isSelectParentChannel", "true");
		sc.replace("nameOrIdItem", channelItems[RandomUtils.nextInt(0, channelItems.length)]);
		sc.replace("nameOrId", sc.get("nameOrIdItem").equals(channelItems[0]) ? parentCh[0]: parentCh[1]);
		sc.replace("isConfirmSelectParentChannel", "true");
		sc.replace("parentChannelId", parentCh[0]);
		sc.replace("parentChannelName", parentCh[1]);
		sc.replace("departName", RandomStringUtils.randomAlphabetic(5).toUpperCase());
		sc.replace("position", RandomStringUtils.randomAlphabetic(4).toUpperCase());
		sc.replace("phone", DataGenerator.generatePhone());
		sc.replace("email", DataGenerator.generateEmail());
		sc.replace("idCode", DataGenerator.generateIdCardCode());
		sc.replace("isEnable", Enable.values()[RandomUtils.nextInt(0, Enable.values().length)].getSCode());
		sc.replace("remark", RandomStringUtils.randomAlphanumeric(24).toUpperCase());
		sc.replace("isSave", "true");
		sc.replace("isConfirmSave", "true");
		sc.replace("message", "");
		
		return sc;
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
	
	private static String[] needs = {
			"empName-请填写员工姓名",
			"loginAccount-请填写登陆帐号",
			"isSelectParentChannel-请选择所属渠道",
			"phone-请输入手机号码",
			"email-请输入邮箱",
			"isEnable-请选择是否启用"
	};
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
						EMP_NAME_CONFLICT_MSG = "该员工姓名在该所属机构已存在";
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllEmpAddTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		//1.成功新增业务员测试用例数据
		HashMap<String, String> successAddCaseMap = successCase();
		list.add(successAddCaseMap);
		
		//2.必填未填异常测试用例数据
		for(String need : needs){
			HashMap<String, String> noSetNeedCaseMap = successCase();
			noSetNeedCaseMap.replace("empName", DataGenerator.generateZh_CNName() + SwiftPass.getHHmmssSSSString().substring(5));
			noSetNeedCaseMap.replace(need.split("-")[0], "");
			noSetNeedCaseMap.replace("message", need.split("-")[1]);
			if(need.split("-")[0].equals("isSelectParentChannel")){
				noSetNeedCaseMap.replace("parentChannelId", "");
				noSetNeedCaseMap.replace("parentChannelName", "");
			}
			list.add(noSetNeedCaseMap);
		}
		
		//3.其他异常测试用例数据
		HashMap<String, String> exCaseMap = successCase();
		HashMap<String, String> empIdLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		empIdLengthBeyondCaseMap.replace("empId", RandomStringUtils.randomNumeric(25));
		empIdLengthBeyondCaseMap.replace("message", BEYOND_EMP_ID_LENGTH_MSG);
		list.add(empIdLengthBeyondCaseMap);
		HashMap<String, String> empNameLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		empNameLengthBeyondCaseMap.replace("empName", RandomStringUtils.randomAlphanumeric(33));
		empNameLengthBeyondCaseMap.replace("message", BEYOND_EMP_NAME_LENGTH_MSG);
		list.add(empNameLengthBeyondCaseMap);
		HashMap<String, String> loginAccountLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		loginAccountLengthBeyondCaseMap.replace("loginAccount", RandomStringUtils.randomNumeric(33));
		loginAccountLengthBeyondCaseMap.replace("message", BEYOND_LOGIN_ACCOUT_LENGTH_MSG);
		list.add(loginAccountLengthBeyondCaseMap);
		HashMap<String, String> departNameLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		departNameLengthBeyondCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(65));
		departNameLengthBeyondCaseMap.replace("message", BEYOND_DEPART_NAME_LENGTH_MSG);
		HashMap<String, String> positionLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		positionLengthBeyondCaseMap.replace("position", RandomStringUtils.randomAlphanumeric(65));
		positionLengthBeyondCaseMap.replace("message", BEYOND_POSITION_LENGTH_MSG);
		list.add(positionLengthBeyondCaseMap);
		HashMap<String, String> phoneLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		phoneLengthBeyondCaseMap.replace("phone", DataGenerator.generatePhone() + 1);
		phoneLengthBeyondCaseMap.replace("message", BEYOND_PHONE_LENGTH_MSG);
		list.add(phoneLengthBeyondCaseMap);
		HashMap<String, String> wrongFormatPhoneCaseMap = SwiftPass.copy(exCaseMap);
		wrongFormatPhoneCaseMap.replace("phone", RandomStringUtils.randomNumeric(10));
		wrongFormatPhoneCaseMap.replace("message", WRONG_PHONE_FORMAT_MSG);
		list.add(wrongFormatPhoneCaseMap);
		HashMap<String, String> wrongFormatEmailCaseMap = SwiftPass.copy(exCaseMap);
		wrongFormatEmailCaseMap.replace("email", "df@d");
		wrongFormatEmailCaseMap.replace("message", WRONG_EMAIL_FORMAT_MSG);
		list.add(wrongFormatEmailCaseMap);
		HashMap<String, String> idCodeLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		idCodeLengthBeyondCaseMap.replace("idCode", RandomStringUtils.randomNumeric(129));
		idCodeLengthBeyondCaseMap.replace("message", BEYOND_ID_CODE_LENGTH_MSG);
		list.add(idCodeLengthBeyondCaseMap);
		HashMap<String, String> remarkLengthBeyondCaseMap = SwiftPass.copy(exCaseMap);
		remarkLengthBeyondCaseMap.replace("remark", RandomStringUtils.randomAlphanumeric(257));
		remarkLengthBeyondCaseMap.replace("message", BEYOND_REMARK_LENGTH_MSG);
		list.add(remarkLengthBeyondCaseMap);
		HashMap<String, String> nameConflictUnderSameChannel = SwiftPass.copy(successAddCaseMap);
		nameConflictUnderSameChannel.replace("message", EMP_NAME_CONFLICT_MSG);
		list.add(nameConflictUnderSameChannel);
		
		//4.正常关闭新增页面、取消新增保存操作并关闭页面测试用例数据
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(exCaseMap);
		closePageCaseMap.replace("isSave", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelSaveCaseMap = SwiftPass.copy(exCaseMap);
		cancelSaveCaseMap.replace("isConfirmSave", "false");
		cancelSaveCaseMap.replace("message", CANCEL_SAVE_MSG);
		list.add(cancelSaveCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		return resultCaseMaps;
	}
}