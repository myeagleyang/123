package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.merchant.DepartTestCase;


import swiftpass.testcase.merchant.DepartTestCaseImpl;
import swiftpass.testcase.testdata.merchant.DepartAddTestData;
import swiftpass.testcase.testdata.merchant.DepartEditTestData;
import swiftpass.testcase.testdata.merchant.DepartSearchTestData;

public class DepartTestCaseRunner extends CaseRunner{
	
	@BeforeClass(description="加载部门管理页面")
	public void loadingTestPage(){
	}
	
	@BeforeMethod
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_MM);
		menu.clickElement(MenuType.HM_MM_DM);
	}
	
	@DataProvider
	public Object[][] departTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearchDepart")){
			params = DepartSearchTestData.getDepartSearchTestData();
		} else if(method.getName().equals("runAddDepart")){
			params = DepartAddTestData.getDepartAddTestData();
		} else if(method.getName().equals("runEditDepart")){
			params = DepartEditTestData.getDepartEditTestData();
		}
		return params;
	}
	
	@Test(dataProvider = "departTestData")//
	public void runSearchDepart(HashMap<String, String> params){
		logger.info("开始执行商户管理——部门管理——查询测试...");
		DepartTestCase testcase = new DepartTestCaseImpl(driver);
		testcase.searchDepart(params);
		logger.info("商户管理——部门管理——查询测试结束.");
	}
	
	@Test(dataProvider = "departTestData")//
	public void runAddDepart(HashMap<String, String> params){
		logger.info("开始执行商户管理——部门管理——查询测试...");
		DepartTestCase testcase = new DepartTestCaseImpl(driver);
		testcase.addDepart(params);
		logger.info("商户管理——部门管理——查询测试结束.");
	}
	
	@Test(dataProvider = "departTestData")//
	public void runEditDepart(HashMap<String, String> params){
		logger.info("开始执行商户管理——部门管理——查询测试...");
		DepartTestCase testcase = new DepartTestCaseImpl(driver);
		testcase.editDepart(params);
		logger.info("商户管理——部门管理——查询测试结束.");
	}
}
