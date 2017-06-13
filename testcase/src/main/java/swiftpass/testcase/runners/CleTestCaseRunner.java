package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swiftpass.page.MenuType;
import swiftpass.testcase.cle.CleDisposeTestCase;
import swiftpass.testcase.cle.CleDisposeTestCaseImpl;
import swiftpass.testcase.testdata.cle.CleDisposeCleaningTestData;
import swiftpass.testcase.testdata.cle.CleDisposeDownloadCleFileTestData;
import swiftpass.testcase.testdata.cle.CleDisposeSearchTestData;
import swiftpass.utils.services.CleService;

public class CleTestCaseRunner extends CaseRunner{
	
	@BeforeClass(description = "初始化清分数据.......")
	public void initCleData(){
		CleService.deleteCleChnAccTask();
		CleService.againRunChAcc();
	}
	@BeforeMethod
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_CLNM);
		menu.clickElement(MenuType.HM_CLNM_CD);
	}
	
	@DataProvider
	public Object[][] cleTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearchCleData")){
			params = CleDisposeSearchTestData.getCleDisposeTestData();
		}
		if(method.getName().equals("runDisposeCleaning")){
			params = CleDisposeCleaningTestData.getCleDisposeCleaningTestData();
		}
		if(method.getName().equals("runDownloadCleFile")){
			params = CleDisposeDownloadCleFileTestData.getDownloadCleFileTestData();
		}
		return params;
	}
	
	@Test(dataProvider = "cleTestData")
	public void runSearchCleData(HashMap<String, String> params){
		logger.info("开始执行清分管理——清分处理查询测试用例！");
		CleDisposeTestCase cd = new CleDisposeTestCaseImpl(driver);
		cd.searchCleData(params);
		logger.info("清分管理——清分处理查询测试用例执行完成！");
	}
	
	@Test(dataProvider = "cleTestData")
	public void runDisposeCleaning(HashMap<String, String> params){
		logger.info("开始执行清分管理——清分处理发起清分测试用例！");
		CleDisposeTestCase cd = new CleDisposeTestCaseImpl(driver);
		cd.disposeCleaning(params);
		logger.info("清分管理——清分处理发起清分测试用例执行完成！");
	}
	
	@Test(dataProvider = "cleTestData")
	public void runDownloadCleFile(HashMap<String, String> params){
		logger.info("开始执行清分管理——清分处理下载清分文件用例！");
		CleDisposeTestCase cd = new CleDisposeTestCaseImpl(driver);
		cd.downloadCleFile(params);
		logger.info("清分管理——清分处理下载清分文件测试用例执行完成！");
	}
	

}
