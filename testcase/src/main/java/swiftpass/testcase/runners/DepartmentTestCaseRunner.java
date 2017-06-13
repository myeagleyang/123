package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swiftpass.page.MenuType;
import swiftpass.testcase.CaseBean;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;
import swiftpass.testcase.casebeans.DepartmentAddCaseBean;
import swiftpass.testcase.casebeans.DepartmentEditCaseBean;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;
import swiftpass.testcase.merchant.DepartmentTestCase;
import swiftpass.testcase.merchant.DepartmentTestCaseImpl;
import swiftpass.testcase.testdata.merchant.DepartAddTestData;
import swiftpass.testcase.testdata.merchant.DepartEditTestData;
import swiftpass.testcase.testdata.merchant.DepartSearchTestData;
import swiftpass.testcase.testdata.merchant.XDepartmentAddTestData;
import swiftpass.testcase.testdata.merchant.XDepartmentEditTestData;
import swiftpass.testcase.testdata.merchant.XDepartmentSearchTestData;

public class DepartmentTestCaseRunner extends CaseRunner {

	@BeforeMethod(description = "加载部门管理界面")
	public void selectMenu() {
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_MM);
		menu.clickElement(MenuType.HM_MM_DM);

	}

	@DataProvider(name = "testData")
	public CaseBean[][] testData(Method method) {
		CaseBean[][] cases = null;
		 if (method.getName().equals("runAdd")) {
			cases = XDepartmentAddTestData.data();
		} else if (method.getName().equals("runSearch")) {
			cases = XDepartmentSearchTestData.data();
		}  else if (method.getName().equals("runEdit")) {
			cases = XDepartmentEditTestData.data();
		}
		return cases;
	}

	@Test(dataProvider = "testData")
	public void runAdd(CaseBean cases) {
		logger.info("开始商户管理——部门管理——新增测试用例......");
		DepartmentTestCase testcase = new DepartmentTestCaseImpl(driver);
		testcase.addDepartment((DepartmentAddCaseBean) cases);
	}
	
	@Test(dataProvider = "testData")
	public void runSearch(DepartmentSearchCaseBean cases) {
		logger.info("开始商户管理——部门管理——查询测试用例......");
		DepartmentTestCase testcase = new DepartmentTestCaseImpl(driver);
		testcase.searchDepartment(cases);
	}



	@Test(dataProvider = "testData")
	public void runEdit(CaseBean cases) {
		logger.info("开始商户管理——部门管理——编辑测试用例......");
		DepartmentTestCase testcase = new DepartmentTestCaseImpl(driver);
		testcase.editDepartment((DepartmentEditCaseBean) cases);
	}
}
