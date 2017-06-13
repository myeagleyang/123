package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.CaseBean;
import swiftpass.testcase.casebeans.EmpAddCaseBean;
import swiftpass.testcase.casebeans.EmpEditCaseBean;
import swiftpass.testcase.casebeans.EmpSearchCaseBean;
import swiftpass.testcase.channel.EmpTestCase;
import swiftpass.testcase.channel.EmpTestCaseImpl;
import swiftpass.testcase.testdata.channel.EmpAddTestData;
import swiftpass.testcase.testdata.channel.EmpEditTestData;
import swiftpass.testcase.testdata.channel.EmpSearchTestData;
import swiftpass.testcase.testdata.channel.XEmpAddTestData;
import swiftpass.testcase.testdata.channel.XEmpEditTestData;
import swiftpass.testcase.testdata.channel.XEmpSearchTestData;

public class EmpTestCaseRunner extends CaseRunner{
	@BeforeClass(description="加载业务员管理页面")
	public void loadingTestPage(){
	}
	
	@BeforeMethod
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_CM);
		menu.clickElement(MenuType.HM_CM_SM);
	}
	
	/*@DataProvider
	public Object[][] empData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearch")){
			params = (HashMap<String, String>[][]) new EmpSearchTestData().getAllTestData();
		} else if(method.getName().equals("runAdd")){
			params = EmpAddTestData.getEmpAddTestData();
		} else if(method.getName().equals("runEdit")){
			params = EmpEditTestData.getEmpEditTestData();
		}
		return params;
	}
	
	@Test(dataProvider = "empData")
	public void runSearch(HashMap<String, String> params){
		logger.info("开始执行渠道管理——业务员管理——查询测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.searchEmp(params);
		logger.info("渠道管理——业务员管理——查询测试用例执行完成.");
	}
	
	@Test(dataProvider = "empData")
	public void runAdd(HashMap<String, String> params){
		logger.info("开始执行渠道管理——业务员管理——新增测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.addEmp(params);
		logger.info("渠道管理——业务员管理——新增测试用例执行完成.");
	}
	
	@Test(dataProvider = "empData")
	public void runEdit(HashMap<String, String> params){
		logger.info("开始执行渠道管理——业务员管理——编辑测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.editEmp(params);
		logger.info("渠道管理——业务员管理——编辑测试用例执行完成.");
	}
	
	@Test
	public void runExport(){
		logger.info("开始执行渠道管理——业务员管理——导出测试用例");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.exportEmp();
		logger.info("渠道管理——业务员管理——导出测试用例执行完成.");
	}*/
	
	//------------------------caseBean重构---------------------------
	
	@DataProvider(name="testData")
	public Object[][] empData(Method method){
		CaseBean [][] caseBean = null;
		if(method.getName().equals("runSearch")){
			caseBean = XEmpSearchTestData.data();
		} else if(method.getName().equals("runAdd")){
			caseBean = XEmpAddTestData.data();
		} else if(method.getName().equals("runEdit")){
			caseBean = XEmpEditTestData.data();
		}
		return caseBean;
	}
	
	@Test(dataProvider = "testData")
	public void runSearch(EmpSearchCaseBean caseBean){
		logger.info("开始执行渠道管理——业务员管理——查询测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.searchEmp(caseBean);
		logger.info("渠道管理——业务员管理——查询测试用例执行完成.");
	}
	
	@Test(dataProvider = "testData")
	public void runAdd(EmpAddCaseBean caseBean){
		logger.info("开始执行渠道管理——业务员管理——新增测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.addEmp(caseBean);
		logger.info("渠道管理——业务员管理——新增测试用例执行完成.");
	}
	
	@Test(dataProvider = "testData")
	public void runEdit(EmpEditCaseBean caseBean){
		logger.info("开始执行渠道管理——业务员管理——编辑测试用例...");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.editEmp(caseBean);
		logger.info("渠道管理——业务员管理——编辑测试用例执行完成.");
	}
	
	@Test
	public void runExport(){
		logger.info("开始执行渠道管理——业务员管理——导出测试用例");
		EmpTestCase testcase = new EmpTestCaseImpl(driver);
		testcase.exportEmp();
		logger.info("渠道管理——业务员管理——导出测试用例执行完成.");
	}
	
}