package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.cle.CleResStatTestCase;
import swiftpass.testcase.cle.CleResStatTestCaseImpl;
import swiftpass.testcase.testdata.cle.CleResStatTestData;
import swiftpass.testcase.testdata.cle.CleStatCaseBean;

public class CleStatTestCaseRunner extends CaseRunner{
	@BeforeMethod
	public void reflesh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_CLNM);
		menu.clickElement(MenuType.HM_CLNM_CRS);
	}
	
	@DataProvider(name="cleTestData")
	public CleStatCaseBean[][] cleTestData(Method m){
		CleStatCaseBean[][] cases = null;
		if(m.getName().equals("runSearch")){
			cases = CleResStatTestData.getTestData();
		}
		return cases;
	}
	
	@Test(dataProvider="cleTestData")
	public void runSearch(CleStatCaseBean cb){
		logger.info("开始执行清分管理——清分结果统计测试用例......");
		CleResStatTestCase tc = new CleResStatTestCaseImpl(driver);
		tc.cleResultStatSearch(cb);
		logger.info("清分管理——清分结果统计测试用例执行结束!");
	}
}
