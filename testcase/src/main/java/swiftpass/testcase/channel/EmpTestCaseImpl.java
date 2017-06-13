package swiftpass.testcase.channel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import swiftpass.page.Page;
import swiftpass.page.Page.XClock;
import swiftpass.page.channel.EmpAddPage;
import swiftpass.page.channel.EmpEditPage;
import swiftpass.page.channel.EmpPage;
import swiftpass.page.exceptions.EmpException;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.EmpAddCaseBean;
import swiftpass.testcase.casebeans.EmpEditCaseBean;
import swiftpass.testcase.casebeans.EmpSearchCaseBean;

public class EmpTestCaseImpl extends TestCaseImpl implements EmpTestCase{
	//主页
	EmpPage empPage;
	EmpAddPage empAddPage;
	EmpEditPage empEditPage;
	
	public EmpTestCaseImpl(WebDriver driver) {
		super(driver);
		this.empPage = new EmpPage(this.driver);
		this.empAddPage = new EmpAddPage(this.driver);
		this.empEditPage = new EmpEditPage(this.driver);
	}
	
	@Override
	public void searchEmp(HashMap<String, String> params) {
		this.logger.info("本次业务员管理查询测试使用的测试数据是: " + params);
		//获取查询的参数
		String 	beginCT = params.get("beginCT"),
				endCT = params.get("endCT"),
				empCode = params.get("empCode"),
				empName = params.get("empName"),
				phone = params.get("phone");
		String 	isSelectOrg = params.get("isSelectOrg"),//是否点击选择所属渠道
				idOrNameItem = params.get("idOrNameItem"),
				idOrName = params.get("idOrName"),
				isConfirmSelectOrg = params.get("isConfirmSelectOrg"),
				orgId = params.get("orgId"),
				orgName = params.get("orgName");//是否确认选择所属渠道
		//预期结果
		String expectCount = params.get("expectCount");	//预期结果
		//输入测试数据
		empPage.setBeginCT(beginCT);
		empPage.setEndCT(endCT);
		empPage.setEmpName(empName);
		empPage.setEmpId(empCode);
		empPage.setEmpPhone(phone);
		if(isSelectOrg.equals("true"))
			empPage.clickSelechChannel(isSelectOrg, idOrNameItem, idOrName, isConfirmSelectOrg);
		else
			empPage.directSetChannelByJS(orgId, orgName);
		empPage.clickSearch();
		Page.XClock.stop(1);
		//获取实际搜索结果
		String actual = empPage.getRecordCount();
		//验证比对实际结果和预期结果
		Assert.assertEquals(actual, expectCount);
	}

	@Override
	public void editEmp(HashMap<String, String> params) {
		logger.info("本次业务员管理编辑业务员测试用例使用的数据是： " + params);
		//获取测试数据
		String[] text = params.get("TEXT").split("-");
		String 	empId = params.get("empId"),
				empName = params.get("empName"),
				sex = params.get("sex"),
				departName = params.get("departName"),
				position = params.get("position"),
				phone = params.get("phone"),
				email = params.get("email"),
				idCode = params.get("idCode"),
				isEnable = params.get("isEnable"),
				remark = params.get("remark"),
				isEdit = params.get("isEdit"),
				isConfirmEdit = params.get("isConfirmEdit");
		
		String message = params.get("message");
		
		//编辑操作
		try{
			empPage.setEmpId(text[0]);
			empPage.setEmpName(text[1]);
			empPage.clickSearch();
			empPage.clickEmpRowByText(text);
			empPage.clickEmpEdit();
			//编辑前的信息		
			empEditPage.editEmpId(empId);
			empEditPage.editEmpName(empName);
			empEditPage.editEmpSex(sex);
			empEditPage.editEmpDepart(departName);
			empEditPage.editEmpPosition(position);
			empEditPage.editEmpPhone(phone);
			empEditPage.editEmpEmail(email);
			empEditPage.editEmpIdCard(idCode);
			empEditPage.editIsEnable(isEnable);
			empEditPage.editRemark(remark);
			empEditPage.lastEditOperate(isEdit, isConfirmEdit);
		} catch(EmpException e){
			Assert.assertEquals(e.getMessage(), message);
			logger.error(e);
			return;
		}
		String[] postText = ArrayUtils.toArray(empId, empName);
		empPage.setEmpId(empId);
		empPage.setEmpName(empName);
		empPage.clickSearch();
		empPage.clickEmpRowByText(postText);
		empPage.clickEmpEdit();
		HashMap<String, String> postEmpInfo = empEditPage.getEmpInfo();
		for(String key : postEmpInfo.keySet()){
			logger.info("post[" + postEmpInfo.get(key) + "] : params[" + params.get(key) + "]");
			Assert.assertEquals(postEmpInfo.get(key), params.get(key));
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void addEmp(HashMap<String, String> params) {
		logger.info("本次业务员管理新增测试用例使用的测试数据是： " + params);
		//获取测试数据参数
		String 	empId = params.get("empId"), 
				empName = params.get("empName"), 
				loginAccount = params.get("loginAccount"), 
				sex = params.get("sex");
		String 	isSelectParentChannel = params.get("isSelectParentChannel"), 
				nameOrIdItem = params.get("nameOrIdItem"),
				nameOrId = params.get("nameOrId"), 
				isConfirmSelectParentChannel = params.get("isConfirmSelectParentChannel"),
				parentChannelId = params.get("parentChannelId"),
				parentChannelName = params.get("parentChannelName");
		String  departName = params.get("departName"), 
				position = params.get("position"), 
				phone = params.get("phone"), 
				email = params.get("email"), 
				idCode = params.get("idCode"), 
				isEnable = params.get("isEnable"), 
				remark = params.get("remark");
		String isSave = params.get("isSave"), 
				isConfirmSave = params.get("isConfirmSave");
		
		String message = params.get("message");
		//获取新增前的业务员记录数
		empPage.clickSearch();
		String preCount = empPage.getRecordCount();
		//测试操作
		try{
			empPage.clickEmpAdd();
			empAddPage.setEmpIdBox(empId);
			empAddPage.setEmpNameBox(empName);
			empAddPage.setEmpAccountBox(loginAccount);
			empAddPage.selectEmpSex(sex);
			if(StringUtils.isEmpty(message))
				empAddPage.clickSelectChannel(isSelectParentChannel, nameOrIdItem, nameOrId, isConfirmSelectParentChannel);
			else
				empAddPage.directSetParentChannelByJS(parentChannelId, parentChannelName);
			empAddPage.setEmpDepartNameBox(departName);
			empAddPage.setEmpPositionBox(position);
			empAddPage.setEmpPhoneBox(phone);
			empAddPage.setEmpEmailBox(email);
			empAddPage.setEmpIdCardBox(idCode);
			empAddPage.selectIsEnable(isEnable);
			empAddPage.setRemarkBox(remark);
			empAddPage.lastOperate(isSave, isConfirmSave);
		} catch(EmpException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}

		empPage.clickSearch();
		Assert.assertEquals(empPage.getRecordCount(), "" + (Integer.parseInt(preCount) + 1));
		
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
	
	@Override
	public void exportEmp() {
		String fileName = "业务员信息.xls";
		String downloadPath = conf.getValueOfKey("downloadPath");
		empPage.clickExport();
		try {
			boolean isDownload = false;
			int checkTimes = 0;
			while(checkTimes < 3){
				XClock.stop(3);
				isDownload = FileUtils.directoryContains(new File(downloadPath), new File(fileName));
				checkTimes += 1;
				if(isDownload == true)
					break;
				else{
					if(checkTimes == 3)
						throw new SwiftPassException("业务员信息导出： ", "失败！");
				}
			}
		} catch (IOException e) {
			Assert.assertEquals(true, false);
			e.printStackTrace();
		}
	}

	@Override
	public void searchEmp(EmpSearchCaseBean caseBean) {
		this.logger.info("本次业务员管理查询测试使用的测试数据是: " + caseBean);
		//获取查询的参数
		String 	beginCT = caseBean.getBeginCT(),
				endCT = caseBean.getEndCT(),
				empCode = caseBean.getEmpCode(),
				empName = caseBean.getEmpName(),
				phone = caseBean.getPhone();
		String 	isSelectOrg = caseBean.getIsSelectOrg(),//是否点击选择所属渠道
				idOrNameItem = caseBean.getIdOrNameItem(),
				idOrName = caseBean.getIdOrName(),
				isConfirmSelectOrg = caseBean.getIsConfirmSelectOrg(),
				orgId = caseBean.getOrgId(),
				orgName = caseBean.getOrgName();//是否确认选择所属渠道
		//预期结果
		String expectCount = caseBean.getExpectCount();	//预期结果
		//输入测试数据
		empPage.setBeginCT(beginCT);
		empPage.setEndCT(endCT);
		empPage.setEmpName(empName);
		empPage.setEmpId(empCode);
		empPage.setEmpPhone(phone);
		if(isSelectOrg.equals("true"))
			empPage.clickSelechChannel(isSelectOrg, idOrNameItem, idOrName, isConfirmSelectOrg);
		else
			empPage.directSetChannelByJS(orgId, orgName);
		empPage.clickSearch();
		Page.XClock.stop(1);
		//获取实际搜索结果
		String actual = empPage.getRecordCount();
		//验证比对实际结果和预期结果
		Assert.assertEquals(actual, expectCount);
		
	}

	@Override
	public void addEmp(EmpAddCaseBean caseBean) {
		logger.info("本次业务员管理新增测试用例使用的测试数据是： " + caseBean);
		//获取测试数据参数
		String 	empId = caseBean.getEmpId(), 
				empName = caseBean.getEmpName(), 
				loginAccount = caseBean.getLoginAccount(), 
				sex = caseBean.getSex();
		String 	isSelectParentChannel = caseBean.getIsSelectParentChannel(), 
				nameOrIdItem = caseBean.getNameOrIdItem(),
				nameOrId = caseBean.getNameOrId(), 
				isConfirmSelectParentChannel = caseBean.getIsConfirmSelectParentChannel(),
				parentChannelId = caseBean.getParentChannelId(),
				parentChannelName = caseBean.getParentChannelName();
		String  departName = caseBean.getDepartName(), 
				position = caseBean.getPosition(), 
				phone = caseBean.getPhone(), 
				email = caseBean.getEmail(), 
				idCode = caseBean.getIdCode(), 
				isEnable = caseBean.getIsEnable(), 
				remark = caseBean.getRemark();
		String isSave = caseBean.getIsSave(), 
				isConfirmSave = caseBean.getIsConfirmSave();
		
		String message = caseBean.getMessage();
		//获取新增前的业务员记录数
		empPage.clickSearch();
		String preCount = empPage.getRecordCount();
		//测试操作
		try{
			empPage.clickEmpAdd();
			empAddPage.setEmpIdBox(empId);
			empAddPage.setEmpNameBox(empName);	
			 //empAddPage.setEmpAccountBox(loginAccount);//新版本迭代后取消了登陆账号必填项 2017-04-13
			empAddPage.selectEmpSex(sex);
			if(StringUtils.isEmpty(message))
				empAddPage.clickSelectChannel(isSelectParentChannel, nameOrIdItem, nameOrId, isConfirmSelectParentChannel);
			else
				empAddPage.directSetParentChannelByJS(parentChannelId, parentChannelName);
			empAddPage.setEmpDepartNameBox(departName);
			empAddPage.setEmpPositionBox(position);
			empAddPage.setEmpPhoneBox(phone);
			empAddPage.setEmpEmailBox(email);
			empAddPage.setEmpIdCardBox(idCode);
			empAddPage.selectIsEnable(isEnable);
			empAddPage.setRemarkBox(remark);
			empAddPage.lastOperate(isSave, isConfirmSave);
		} catch(EmpException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}

		empPage.clickSearch();
		Assert.assertEquals(empPage.getRecordCount(), "" + (Integer.parseInt(preCount) + 1));
		
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void editEmp(EmpEditCaseBean caseBean) {
		logger.info("本次业务员管理编辑业务员测试用例使用的数据是： " + caseBean);
		//获取测试数据
		String[] text = caseBean.getTEXT().split("-");
		String 	empId = caseBean.getEmpId(),
				empName = caseBean.getEmpName(),
				sex = caseBean.getSex(),
				departName = caseBean.getDepartName(),
				position = caseBean.getPosition(),
				phone = caseBean.getPhone(),
				email = caseBean.getEmail(),
				idCode = caseBean.getIdCode(),
				isEnable = caseBean.getIsEnable(),
				remark = caseBean.getRemark(),
				isEdit = caseBean.getIsEdit(),
				isConfirmEdit = caseBean.getIsConfirmEdit();
		
		String message = caseBean.getMessage();
		
		//编辑操作
		try{
			empPage.setEmpId(text[0]);
			empPage.setEmpName(text[1]);
			empPage.clickSearch();
			empPage.clickEmpRowByText(text);
			empPage.clickEmpEdit();
			//编辑前的信息		
			empEditPage.editEmpId(empId);
			empEditPage.editEmpName(empName);
			empEditPage.editEmpSex(sex);
			empEditPage.editEmpDepart(departName);
			empEditPage.editEmpPosition(position);
			empEditPage.editEmpPhone(phone);
			empEditPage.editEmpEmail(email);
			empEditPage.editEmpIdCard(idCode);
			empEditPage.editIsEnable(isEnable);
			empEditPage.editRemark(remark);
			empEditPage.lastEditOperate(isEdit, isConfirmEdit);
		} catch(EmpException e){
			Assert.assertEquals(e.getMessage(), message);
			logger.error(e);
			return;
		}
		String[] postText = ArrayUtils.toArray(empId, empName);
		empPage.setEmpId(empId);
		empPage.setEmpName(empName);
		empPage.clickSearch();
		empPage.clickEmpRowByText(postText);
		empPage.clickEmpEdit();
		HashMap<String, String> postEmpInfo = empEditPage.getEmpInfo();
//		for(String key : postEmpInfo.keySet()){
//			logger.info("post[" + postEmpInfo.get(key) + "] : params[" + params.get(key) + "]");
//			Assert.assertEquals(postEmpInfo.get(key), caseBean.get(key));
//		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);	
		
	}
}