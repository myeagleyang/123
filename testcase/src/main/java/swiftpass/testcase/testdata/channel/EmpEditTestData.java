package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.EmpDBOperations;
import swiftpass.page.enums.Enable;
import swiftpass.page.enums.Sex;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.EmpService;

public class EmpEditTestData {
	public static HashMap<String, String>[][] getEmpEditTestData(){
		return getAllEmpEditTestData();
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"empId",
				"empName",
				"sex",
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
				"TEXT",
				"isEdit",
				"isConfirmEdit",
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		map.replace("empId", RandomStringUtils.randomNumeric(12));
		map.replace("empName", DataGenerator.generateZh_CNName());
		map.replace("sex", Sex.values()[RandomUtils.nextInt(0, Sex.values().length)].getSCode());
		map.replace("departName", RandomStringUtils.randomAlphabetic(8).toUpperCase());
		map.replace("position", RandomStringUtils.randomAlphabetic(4).toUpperCase());
		map.replace("phone", DataGenerator.generatePhone());
		map.replace("email", DataGenerator.generateEmail());
		map.replace("idCode", DataGenerator.generateIdCardCode());
		map.replace("isEnable", Enable.values()[RandomUtils.nextInt(0, Enable.values().length)].getSCode());
		map.replace("remark", RandomStringUtils.randomAlphanumeric(32));
		map.replace("isEdit", "true");
		map.replace("isConfirmEdit", "true");
		
		return map;
	}
	
	private static HashMap<Integer, HashMap<String, String>> editableEmps(int expectCount){
		HashMap<Integer, HashMap<String, String>> emps = EmpDBOperations.emps();
		if(emps.size() < expectCount){
			int gap = expectCount - emps.size();
			String parentChannelId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			EmpService.addMultiEmp(gap, parentChannelId);
			emps = EmpDBOperations.emps();
		}
		return emps;
	}
	
	@SuppressWarnings("unused")
	private static final String
								NO_SELECT_EMP_EDIT_MSG = "请选择要编辑的记录!",
								BEYOND_EMP_CODE_LENGTH_MSG = "请填写0到24位字符！",
								BEYOND_EMP_NAME_LENGTH_MSG = "员工姓名长度在1~32位之间！",
								BEYOND_PHONE_LENGTH_MSG = "请填写1到11位数字！",
								WRONG_PHONE_FORMAT_MSG = "手机号码格式错误",
								WRONG_EMAIL_FORMAT_MSG = "邮箱地址格式不对！",
								BEYOND_DEPART_NAME_LENGTH_MSG = "请填写0到64位字符！",
								BEYOND_POSITION_LENGTH_MSG = "请填写0到64位字符！",
								BEYOND_ID_CODE_LENGTH_MSG = "请填写0到128位字符！",
								BEYOND_REMARK_LENGTH_MSG = "请填写0到256位字符！",
								CLOSE_PAGE_MSG = "正常关闭",
								CANCEL_EDIT_MSG = "正常取消",
								EMP_NAME_CONFLICT_MSG = "该员工姓名在该所属机构已存在";
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllEmpEditTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<Integer, HashMap<String, String>> emps = editableEmps(3);
		
		//1.正常成功编辑测试用例数据
		HashMap<String, String> successEditCaseMap = caseMapWithCtrlParams();
		successEditCaseMap.replace("TEXT", String.join("-", emps.get(1).get("EMP_CODE"), emps.get(1).get("EMP_NAME")));
		list.add(successEditCaseMap);
		
		//2.必填字段为空报错测试用例数据
		String[] needs = {
			"empName-请填写员工姓名",
			"phone-请输入手机号码",
			"email-请输入邮箱",
			"isEnable-请选择是否启用"
		};
		for(String need : needs){
			HashMap<String, String> nullCaseMap = caseMapWithCtrlParams();
			nullCaseMap.replace("TEXT", String.join("-", emps.get(2).get("EMP_CODE"), emps.get(2).get("EMP_NAME")));
			nullCaseMap.replace(need.split("-")[0], "");
			nullCaseMap.replace("message", need.split("-")[1]);
			list.add(nullCaseMap);
		}
		
		//3.字段格式、长度非法测试用例数据
		HashMap<String, String> emp = emps.get(3);
		HashMap<String, String> fixed = caseMapWithCtrlParams();
		fixed.replace("TEXT", String.join("-", emp.get("EMP_CODE"), emp.get("EMP_NAME")));
		
		HashMap<String, String> empIdBeyondLengthCaseMap = SwiftPass.copy(fixed);
		empIdBeyondLengthCaseMap.replace("empId", RandomStringUtils.randomNumeric(25));
		empIdBeyondLengthCaseMap.replace("message", BEYOND_EMP_CODE_LENGTH_MSG);
		list.add(empIdBeyondLengthCaseMap);
		HashMap<String, String> empNameBeyondLengthCaseMap = SwiftPass.copy(fixed);
		empNameBeyondLengthCaseMap.replace("empName", RandomStringUtils.randomAlphabetic(33));
		empNameBeyondLengthCaseMap.replace("message", BEYOND_EMP_NAME_LENGTH_MSG);
		list.add(empNameBeyondLengthCaseMap);
		HashMap<String, String> phoneBeyondLengthCaseMap = SwiftPass.copy(fixed);
		phoneBeyondLengthCaseMap.replace("phone", DataGenerator.generatePhone() + "1");
		phoneBeyondLengthCaseMap.replace("message", BEYOND_PHONE_LENGTH_MSG);
		list.add(phoneBeyondLengthCaseMap);
		HashMap<String, String> phoneWrongFormatCaseMap = SwiftPass.copy(fixed);
		phoneWrongFormatCaseMap.replace("phone", DataGenerator.generatePhone().substring(0,10));
		phoneWrongFormatCaseMap.replace("message", WRONG_PHONE_FORMAT_MSG);
		list.add(phoneWrongFormatCaseMap);
		HashMap<String, String> emailWrongFormatCaseMap = SwiftPass.copy(fixed);
		emailWrongFormatCaseMap.replace("email", RandomStringUtils.randomAlphabetic(10));
		emailWrongFormatCaseMap.replace("message", WRONG_EMAIL_FORMAT_MSG);
		list.add(emailWrongFormatCaseMap);
		HashMap<String, String> departNameBeyondLengthCaseMap = SwiftPass.copy(fixed);
		departNameBeyondLengthCaseMap.replace("departName", RandomStringUtils.randomAlphabetic(65).toUpperCase());
		departNameBeyondLengthCaseMap.replace("message", BEYOND_DEPART_NAME_LENGTH_MSG);
		list.add(departNameBeyondLengthCaseMap);
		HashMap<String, String> positionBeyondLengthCaseMap = SwiftPass.copy(fixed);
		positionBeyondLengthCaseMap.replace("position", RandomStringUtils.randomAlphabetic(65).toUpperCase());
		positionBeyondLengthCaseMap.replace("message", BEYOND_POSITION_LENGTH_MSG);
		list.add(positionBeyondLengthCaseMap);
		HashMap<String, String> idCodeBeyondLengthCaseMap = SwiftPass.copy(fixed);
		idCodeBeyondLengthCaseMap.replace("idCode", RandomStringUtils.randomNumeric(129));
		idCodeBeyondLengthCaseMap.replace("message", BEYOND_ID_CODE_LENGTH_MSG);
		list.add(idCodeBeyondLengthCaseMap);
		HashMap<String, String> remarkBeyondLengthCaseMap = SwiftPass.copy(fixed);
		remarkBeyondLengthCaseMap.replace("remark", RandomStringUtils.randomAlphanumeric(257));
		remarkBeyondLengthCaseMap.replace("message", BEYOND_REMARK_LENGTH_MSG);
		list.add(remarkBeyondLengthCaseMap);
		
		//4.正常关闭业务员编辑页面、取消确认编辑并关闭页面测试用例数据
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(fixed);
		closePageCaseMap.replace("isEdit", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelEditCaseMap = SwiftPass.copy(fixed);
		cancelEditCaseMap.replace("isConfirmEdit", "false");
		cancelEditCaseMap.replace("message", CANCEL_EDIT_MSG);
		list.add(cancelEditCaseMap);
		
		//5.未选中业务员记录编辑报错测试用例数据
		HashMap<String, String> noSelectEmpCaseMap = caseMapWithCtrlParams();
		noSelectEmpCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectEmpCaseMap.replace("message", NO_SELECT_EMP_EDIT_MSG);
		list.add(noSelectEmpCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}
}