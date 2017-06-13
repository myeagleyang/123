package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.merchant.StoreTestCase;
import swiftpass.testcase.merchant.StoreTestCaseImpl;
import swiftpass.testcase.testdata.merchant.StoreAddTestData;
import swiftpass.testcase.testdata.merchant.StoreDetailTestData;
import swiftpass.testcase.testdata.merchant.StoreEditTestData;
import swiftpass.testcase.testdata.merchant.StoreQRCodeTestData;
import swiftpass.testcase.testdata.merchant.StoreSearchTestData;

public class StoreTestCaseRunner extends CaseRunner{
	public static String path = "/merchant/storeUse/";
	
	@BeforeClass(description="Loading Store-Manage Page......")
	public void loadingTestPage(){
	}
	@BeforeMethod
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_MM);
		menu.clickElement(MenuType.HM_MM_SM);
	}
	
	@DataProvider
	public Object[][] storeTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearch")){
			params = StoreSearchTestData.getStoreSearchTestData();
		} else if(method.getName().equals("runAdd")){
			params = StoreAddTestData.getStoreAddTestData();
		} else if(method.getName().equals("runEdit")){
			params = StoreEditTestData.getStoreEditTestData();
		} else if(method.getName().equals("runScanDetail")){
			params = StoreDetailTestData.getStoreDetailTestData();
		} else if(method.getName().equals("runScanQRCode")){
			params = StoreQRCodeTestData.getStoreQRCodeTestData();
		}
		return params;
	}
	
	@Test(dataProvider = "storeTestData")//
	public void runSearch(HashMap<String, String> params){
		logger.info("开始执行商户管理——门店管理——查询测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.searchStore(params);
		logger.info("商户管理——门店管理——查询测试用例执行完成.");
	}
	
	@Test(dataProvider = "storeTestData")//, priority = 1
	public void runEdit(HashMap<String, String> params){
		logger.info("开始执行商户管理——门店管理——编辑测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.editStore(params);
		logger.info("商户管理——门店管理——编辑测试用例执行完成.");
		
	}
	
	@Test(dataProvider = "storeTestData")//, priority = 2
	public void runAdd(HashMap<String, String> params){
		logger.info("开始执行商户管理——门店管理——新增测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.addStore(params);
		logger.info("商户管理——门店管理——新增测试用例执行完成.");
		
	}
	
	@Test//(enabled = false)
	public void runExport(){
		logger.info("开始执行商户管理——门店管理——导出测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.exportStore();
		logger.info("商户管理——门店管理——导出测试用例执行完成.");
		
	}
	
	@Test(dataProvider = "storeTestData")//(enabled = false)
	public void runScanDetail(HashMap<String, String> params){
		logger.info("开始执行商户管理——门店管理——查看详情测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.scanStoreDetail(params);
		logger.info("商户管理——门店管理——查看详情测试用例执行完成.");
	}
	
	@Test(dataProvider = "storeTestData")//(enabled = false)
	public void runScanQRCode(HashMap<String, String> params){
		logger.info("开始执行商户管理——门店管理——二维码测试用例...");
		StoreTestCase testcase = new StoreTestCaseImpl(driver);
		testcase.scanQRCode(params);
		logger.info("商户管理——门店管理——二维码测试用例执行完成.");
	}
}