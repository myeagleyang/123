package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swiftpass.page.MenuType;
import swiftpass.testcase.batchintopiece.BatchIntoPieceTestCase;
import swiftpass.testcase.batchintopiece.BatchIntoPieceTestCaseImpl;
import swiftpass.testcase.testdata.batchintopiece.BIPRehandleTestData;
import swiftpass.testcase.testdata.batchintopiece.BIPSearchTestData;
import swiftpass.testcase.testdata.batchintopiece.DeptBIPTestData;
import swiftpass.testcase.testdata.batchintopiece.MerchantBIPTestData;
import swiftpass.testcase.testdata.batchintopiece.StoreBIPTestData;

public class BatchIntoPieceTestCaseRunner extends CaseRunner{
	@BeforeMethod(description = "刷新页面")
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_MM);
		menu.clickElement(MenuType.HM_MM_BA);
	}
	
	@DataProvider
	public HashMap<String, String>[][] batchIntoPieceTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("rundoSearch")){
			params = BIPSearchTestData.getBIPSearchTestData();
		}else if(method.getName().equals("rundoMerchantBIP")){
			params = MerchantBIPTestData.getMerchantBIPTestData();
		}else if(method.getName().equals("rundoDeptBIP")){
			params = DeptBIPTestData.getDeptBIPTestData();
		}else if(method.getName().equals("rundoStoreBIP")){
			params = StoreBIPTestData.getStoreBIPTestData();
		}else if(method.getName().equals("rundoMerchantTemplatDownload")){
			
		}else if(method.getName().equals("rundoDeptTemplateDownload")){
			
		}else if(method.getName().equals("rundoStoreTemplateDownload")){
			
		}else if(method.getName().equals("rundoRehandle")){
			params = BIPRehandleTestData.getBIPRehandleTestData();
		}
		return params;
	}
	
	@Test(dataProvider = "batchIntoPieceTestData")
	public void rundoMerchantBIP(HashMap<String, String> params){
		logger.info("开始执行批量进件——商户批量进件测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doMerchantBIP(params);
		logger.info("批量进件——商户批量进件测试用例执行完成！");
	}
	
	@Test(dataProvider = "batchIntoPieceTestData")
	public void rundoDeptBIP(HashMap<String, String> params){
		logger.info("开始执行批量进件——部门批量进件测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doDeptBIP(params);
		logger.info("批量进件——部门批量进件测试用例执行完成！");
	}
	
	@Test(dataProvider = "batchIntoPieceTestData")
	public void rundoStoreBIP(HashMap<String, String> params){
		logger.info("开始执行批量进件——门店批量进件测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doStoreBIP(params);
		logger.info("批量进件——门店批量进件测试用例执行完成！");
	}
	
	@Test(dataProvider = "batchIntoPieceTestData")
	public void rundoSearch(HashMap<String, String> params){
		logger.info("开始执行批量进件——查询测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doSearch(params);
		logger.info("批量进件——查询测试用例执行完成！");
	}
	
	@Test(dataProvider = "batchIntoPieceTestData")
	public void rundoRehandle(HashMap<String, String> params){
		logger.info("开始执行批量进件——重新处理测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doRehandle(params);
		logger.info("批量进件——重新处理测试用例执行完成！");
	}
	
	@Test
	public void runDoMchTemplatDownload(){
		logger.info("开始执行批量进件——商户批量进件模板下载测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doMerchantTemplatDownload();
		logger.info("批量进件——商户批量进件模板下载测试用例完成！");
	}
	
	@Test
	public void runDoDeptTemplateDownload(){
		logger.info("开始执行批量进件——部门批量进件模板下载测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doDeptTemplateDownload();
		logger.info("批量进件——部门批量进件模板下载测试用例完成！");
	}
	
	@Test
	public void runDoStoreTemplateDownload(){
		logger.info("开始执行批量进件——门店批量进件模板下载测试用例！");
		BatchIntoPieceTestCase bip = new BatchIntoPieceTestCaseImpl(driver);
		bip.doStoreTemplateDownload();
		logger.info("批量进件——门店批量进件模板下载测试用例完成！");
	}

}
