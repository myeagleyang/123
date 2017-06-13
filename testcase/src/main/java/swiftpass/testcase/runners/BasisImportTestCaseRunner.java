package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.basismaterial.BasisMaterialImportTestCase;
import swiftpass.testcase.basismaterial.BasisMaterialImportTestCaseImpl;
import swiftpass.testcase.testdata.ITestData;
import swiftpass.testcase.testdata.basis.BasisImportErrorDetailTestData;
import swiftpass.testcase.testdata.basis.BasisImportSearchTestData;
import swiftpass.testcase.testdata.basis.BasisImportUploadTestData;

public class BasisImportTestCaseRunner extends CaseRunner{
	private ITestData searchTD = new BasisImportSearchTestData();
	private ITestData impUploadTD = new BasisImportUploadTestData();
	private ITestData impDetailTD = new BasisImportErrorDetailTestData();
	
	@BeforeMethod(description = "刷新页面.....")
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_BMM);
		menu.clickElement(MenuType.HM_BMM_IMP);
	}
	
	@DataProvider(name="basisData")
	public Object[][] basisData(Method method){
		Map<String, String>[][] params = null;
		if(method.getName().equals("runSearch")){
			params = searchTD.getAllTestData();
		} else if(method.getName().equals("runUpload")){
			params = impUploadTD.getAllTestData();
		} else if(method.getName().equals("runDetail")){
			params = impDetailTD.getAllTestData();
		}
		return params;
	}
	
	@Test(description = "基础资料导入查询测试......", dataProvider = "basisData")
	public void runSearch(Map<String, String> params){
		logger.info("开始执行基础资料管理——基础资料导入查询测试用例......");
		BasisMaterialImportTestCase tc = new BasisMaterialImportTestCaseImpl(driver);
		tc.searchImport(params, searchTD);
		logger.info("基础资料管理——基础资料导入查询测试用例执行结束！");
	}
	
	@Test(description = "基础资料导入上传基础资料测试......", dataProvider = "basisData")
	public void runUpload(Map<String, String> params){
		logger.info("开始执行基础资料管理——导入基础资料上传测试用例......");
		BasisMaterialImportTestCase tc = new BasisMaterialImportTestCaseImpl(driver);
		tc.doImport(params, impUploadTD);
		logger.info("基础资料管理——导入基础资料上传测试用例结束！");
	}
	
	@Test(description = "基础资料导入导入详情测试......", dataProvider = "basisData")
	public void runDetail(Map<String, String> params){
		logger.info("开始执行基础资料管理——查看导入基础资料详情测试用例......");
		BasisMaterialImportTestCase tc = new BasisMaterialImportTestCaseImpl(driver);
		tc.scanFailImportDetail(params, impDetailTD);
		logger.info("基础资料管理——导入基础资料详情测试用例结束！");
	}
}