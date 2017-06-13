package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.basismaterial.BasisMaterialExportTestCase;
import swiftpass.testcase.basismaterial.BasisMaterialExportTestCaseImpl;
import swiftpass.testcase.testdata.ITestData;
import swiftpass.testcase.testdata.basis.BasisExportByMchIdTestData;
import swiftpass.testcase.testdata.basis.BasisExportDownloadTestData;
import swiftpass.testcase.testdata.basis.BasisExportSearchTestData;
import swiftpass.testcase.testdata.basis.BasisExportTestData;

public class BasisExportTestCaseRunner extends CaseRunner{
	private ITestData searchTD = new BasisExportSearchTestData();
	private ITestData exportTD = new BasisExportTestData();
	private ITestData exportMTD = new BasisExportByMchIdTestData();
	private ITestData expDownloadTD = new BasisExportDownloadTestData();
	
	@BeforeMethod(description = "刷新页面.....")
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_BMM);
		menu.clickElement(MenuType.HM_BMM_EXP);
	}
	
	@DataProvider(name="basisData")
	public Object[][] basisData(Method method){
		Map<String, String>[][] params = null;
		if(method.getName().equals("runSearch")){
			params = searchTD.getAllTestData();
		} else if(method.getName().equals("runExportBasis")){
			params = exportTD.getAllTestData();
		} else if(method.getName().equals("runExportBasisByMchIds")){
			params = exportMTD.getAllTestData();
		} else if(method.getName().equals("runDownload")){
			params = expDownloadTD.getAllTestData();
		}
		return params;
	}
	
	@Test(description = "基础资料导出查询测试......", dataProvider = "basisData")
	public void runSearch(Map<String, String> params){
		logger.info("开始执行基础资料管理——基础资料导出查询测试用例......");
		BasisMaterialExportTestCase tc = new BasisMaterialExportTestCaseImpl(driver);
		tc.searchHistoryExportData(params, searchTD);
		logger.info("基础资料管理——基础资料导出查询测试用例执行结束！");
	}
	
	@Test(description = "基础资料导出导出基础资料测试......", dataProvider = "basisData")
	public void runExportBasis(Map<String, String> params){
		logger.info("开始执行基础资料管理——导出基础资料测试用例......");
		BasisMaterialExportTestCase tc = new BasisMaterialExportTestCaseImpl(driver);
		tc.exportHistoryExportData(params, exportTD);
		logger.info("基础资料管理——导出基础资料测试用例结束！");
	}
	
	@Test(description = "基础资料导出按商户号导出基础资料导出测试......", dataProvider = "basisData")
	public void runExportBasisByMchIds(Map<String, String> params){
		logger.info("开始执行基础资料管理——按商户号导出基础资料测试用例......");
		BasisMaterialExportTestCase tc = new BasisMaterialExportTestCaseImpl(driver);
		tc.exportHistoryExportDataByMchIds(params, exportMTD);
		logger.info("基础资料管理——按商户号导出基础资料测试用例执行结束！");
	}
	
	@Test(description = "基础资料导出下载测试......", dataProvider = "basisData")
	public void runDownload(Map<String, String> params){
		logger.info("开始执行基础资料管理——下载导出记录测试用例......");
		BasisMaterialExportTestCase tc = new BasisMaterialExportTestCaseImpl(driver);
		tc.downloadOneHistoryExportData(params, expDownloadTD);
		logger.info("基础资料下载测试用例结束！");
	}
}
