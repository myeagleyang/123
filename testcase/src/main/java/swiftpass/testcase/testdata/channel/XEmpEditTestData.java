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
import swiftpass.testcase.casebeans.EmpEditCaseBean;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.EmpService;

public class XEmpEditTestData {
	public static final EmpEditCaseBean tmpCase = new EmpEditCaseBean();
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
	EMP_NAME_CONFLICT_MSG = "该员工姓名在该所属机构已存在",
	NULL_EMPNAME ="请填写员工姓名",
	NULL_PHONE = "请输入手机号码",
	NULL_EMAIL ="请输入邮箱",
	NULL_ISENABLE ="请选择是否启用";
	static{
		tmpCase.setEmpId(RandomStringUtils.randomNumeric(12));
		tmpCase.setEmpName(DataGenerator.generateZh_CNName());
		tmpCase.setSex(Sex.values()[RandomUtils.nextInt(0, Sex.values().length)].getSCode());
		tmpCase.setDepartName(RandomStringUtils.randomAlphabetic(8).toUpperCase());
		tmpCase.setPosition(RandomStringUtils.randomAlphabetic(4).toUpperCase());
		tmpCase.setPhone(DataGenerator.generatePhone());
		tmpCase.setEmail(DataGenerator.generateEmail());
		tmpCase.setIdCode(DataGenerator.generateIdCardCode());
		tmpCase.setIsEnable(Enable.values()[RandomUtils.nextInt(0, Enable.values().length)].getSCode());
		tmpCase.setRemark(RandomStringUtils.randomAlphanumeric(32));
		tmpCase.setIsEdit("true");
		tmpCase.setIsConfirmEdit("true");
		tmpCase.setMessage("");
		tmpCase.setTEXT("");
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
	public static EmpEditCaseBean[][] data(){
		List<EmpEditCaseBean> list = new ArrayList<>();
		HashMap<Integer, HashMap<String, String>> emps = editableEmps(3);
		
		//1.正常成功编辑测试用例数据
		EmpEditCaseBean successEditCase = SwiftPass.copy(tmpCase);
		successEditCase.setTEXT(String.join("-", emps.get(1).get("EMP_CODE"), emps.get(1).get("EMP_NAME")));
		successEditCase.setMessage("");
		successEditCase.setCASE_NAME("正常成功编辑测试用例数据");
		list.add(successEditCase);
		
		//2.必填字段为空报错测试用例数据
		EmpEditCaseBean noSetEmpName = SwiftPass.copy(tmpCase);
		noSetEmpName.setEmpName("");
		noSetEmpName.setTEXT(String.join("-", emps.get(2).get("EMP_CODE"), emps.get(2).get("EMP_NAME")));
		noSetEmpName.setCASE_NAME(NULL_EMPNAME);
		noSetEmpName.setMessage(NULL_EMPNAME);
		list.add(noSetEmpName);//员工姓名不能为空
		
		EmpEditCaseBean noSetPhone = SwiftPass.copy(tmpCase);
		noSetPhone.setPhone("");
		noSetPhone.setTEXT(String.join("-", emps.get(2).get("EMP_CODE"), emps.get(2).get("EMP_NAME")));
		noSetPhone.setCASE_NAME(NULL_PHONE);
		noSetPhone.setMessage(NULL_PHONE);
		list.add(noSetPhone);
		
		EmpEditCaseBean noSetEmail = SwiftPass.copy(tmpCase);
		noSetEmail.setEmail("");
		noSetEmail.setTEXT(String.join("-", emps.get(2).get("EMP_CODE"), emps.get(2).get("EMP_NAME")));
		noSetEmail.setCASE_NAME(NULL_EMAIL);
		noSetEmail.setMessage(NULL_EMAIL);
		list.add(noSetEmail);
		
		EmpEditCaseBean noSetISENABLE = SwiftPass.copy(tmpCase);
		noSetISENABLE.setIsEnable("");
		noSetISENABLE.setTEXT(String.join("-", emps.get(2).get("EMP_CODE"), emps.get(2).get("EMP_NAME")));
		noSetISENABLE.setCASE_NAME(NULL_ISENABLE);
		noSetISENABLE.setMessage(NULL_ISENABLE);
		list.add(noSetISENABLE);
		
		//3.字段格式、长度非法测试用例数据
		HashMap<String, String> emp = emps.get(3);
		EmpEditCaseBean fixed = SwiftPass.copy(tmpCase);
		fixed.setTEXT(String.join("-", emp.get("EMP_CODE"), emp.get("EMP_NAME")));
		
		EmpEditCaseBean empIdBeyondLengthCase = SwiftPass.copy(fixed);
		empIdBeyondLengthCase.setEmpId(RandomStringUtils.randomNumeric(25));
		empIdBeyondLengthCase.setMessage(BEYOND_EMP_CODE_LENGTH_MSG);
		empIdBeyondLengthCase.setCASE_NAME(BEYOND_EMP_CODE_LENGTH_MSG);
		list.add(empIdBeyondLengthCase);
		
		EmpEditCaseBean empNameBeyondLengthCase = SwiftPass.copy(fixed);
		empNameBeyondLengthCase.setEmpName(RandomStringUtils.randomAlphabetic(33));
		empNameBeyondLengthCase.setMessage(BEYOND_EMP_NAME_LENGTH_MSG);
		empNameBeyondLengthCase.setCASE_NAME(BEYOND_EMP_NAME_LENGTH_MSG);
		list.add(empNameBeyondLengthCase);
		
		EmpEditCaseBean phoneBeyondLengthCase = SwiftPass.copy(fixed);
		phoneBeyondLengthCase.setPhone(DataGenerator.generatePhone() + "1");
		phoneBeyondLengthCase.setMessage(BEYOND_PHONE_LENGTH_MSG);
		phoneBeyondLengthCase.setCASE_NAME(BEYOND_PHONE_LENGTH_MSG);
		list.add(phoneBeyondLengthCase);
		
		EmpEditCaseBean phoneWrongFormatCase = SwiftPass.copy(fixed);
		phoneWrongFormatCase.setPhone(DataGenerator.generatePhone().substring(0,10));
		phoneWrongFormatCase.setMessage(WRONG_PHONE_FORMAT_MSG);
		phoneWrongFormatCase.setCASE_NAME(WRONG_PHONE_FORMAT_MSG);
		list.add(phoneWrongFormatCase);
		
		EmpEditCaseBean emailWrongFormatCase = SwiftPass.copy(fixed);
		emailWrongFormatCase.setEmail(RandomStringUtils.randomAlphabetic(10));
		emailWrongFormatCase.setMessage(WRONG_EMAIL_FORMAT_MSG);
		emailWrongFormatCase.setCASE_NAME(WRONG_EMAIL_FORMAT_MSG);
		list.add(emailWrongFormatCase);
		
		EmpEditCaseBean departNameBeyondLengthCase = SwiftPass.copy(fixed);
		departNameBeyondLengthCase.setDepartName(RandomStringUtils.randomAlphabetic(65).toUpperCase());
		departNameBeyondLengthCase.setMessage(BEYOND_DEPART_NAME_LENGTH_MSG);
		departNameBeyondLengthCase.setCASE_NAME(BEYOND_DEPART_NAME_LENGTH_MSG);
		list.add(departNameBeyondLengthCase);
		
		EmpEditCaseBean positionBeyondLengthCase = SwiftPass.copy(fixed);
		positionBeyondLengthCase.setPosition(RandomStringUtils.randomAlphabetic(65).toUpperCase());
		positionBeyondLengthCase.setMessage(BEYOND_POSITION_LENGTH_MSG);
		positionBeyondLengthCase.setCASE_NAME(BEYOND_POSITION_LENGTH_MSG);
		list.add(positionBeyondLengthCase);
		
		EmpEditCaseBean idCodeBeyondLengthCase = SwiftPass.copy(fixed);
		idCodeBeyondLengthCase.setIdCode(RandomStringUtils.randomNumeric(129));
		idCodeBeyondLengthCase.setMessage(BEYOND_ID_CODE_LENGTH_MSG);
		idCodeBeyondLengthCase.setCASE_NAME(BEYOND_ID_CODE_LENGTH_MSG);
		list.add(idCodeBeyondLengthCase);
		
		EmpEditCaseBean remarkBeyondLengthCase = SwiftPass.copy(fixed);
		remarkBeyondLengthCase.setRemark(RandomStringUtils.randomAlphanumeric(257));
		remarkBeyondLengthCase.setMessage(BEYOND_REMARK_LENGTH_MSG);
		remarkBeyondLengthCase.setCASE_NAME(BEYOND_REMARK_LENGTH_MSG);
		list.add(remarkBeyondLengthCase);
		
		//4.正常关闭业务员编辑页面、取消确认编辑并关闭页面测试用例数据
		EmpEditCaseBean closePageCase = SwiftPass.copy(fixed);
		closePageCase.setIsEdit("false");
		closePageCase.setMessage(CLOSE_PAGE_MSG);
		closePageCase.setCASE_NAME(CLOSE_PAGE_MSG);
		list.add(closePageCase);
		
		EmpEditCaseBean cancelEditCase = SwiftPass.copy(fixed);
		cancelEditCase.setIsConfirmEdit("false");
		cancelEditCase.setMessage(CANCEL_EDIT_MSG);
		cancelEditCase.setCASE_NAME(CANCEL_EDIT_MSG);
		list.add(cancelEditCase);
		
		//5.未选中业务员记录编辑报错测试用例数据
		EmpEditCaseBean noSelectEmpCase = SwiftPass.copy(fixed);
		noSelectEmpCase.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectEmpCase.setMessage(NO_SELECT_EMP_EDIT_MSG);
		noSelectEmpCase.setCASE_NAME(NO_SELECT_EMP_EDIT_MSG);
		list.add(noSelectEmpCase);
		
		return list.stream().map(element ->ArrayUtils.toArray(element)).toArray(EmpEditCaseBean [][]::new);
	}
}