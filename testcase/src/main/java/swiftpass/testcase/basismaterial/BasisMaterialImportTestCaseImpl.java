package swiftpass.testcase.basismaterial;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.ExpectationInDB;
import swiftpass.page.basismaterial.BasisImportFailDetailPage;
import swiftpass.page.basismaterial.BasisMaterialImportPage;
import swiftpass.page.exceptions.BasisMaterialImportException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.ITestData;

public class BasisMaterialImportTestCaseImpl extends TestCaseImpl implements BasisMaterialImportTestCase {
	private BasisMaterialImportPage impPage;
	private BasisImportFailDetailPage failPage;
	
	public BasisMaterialImportTestCaseImpl(WebDriver driver) {
		super(driver);
		impPage = new BasisMaterialImportPage(this.driver);
		failPage = new BasisImportFailDetailPage(this.driver);
	}

	@Override
	public void searchImport(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料管理——基础资料导入测试使用的测试数据参数是： " + params);
		//	获取参数
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		
		//	执行测试操作
		impPage.setBeginCT(beginCT);
		impPage.setEndCT(endCT);
		impPage.clickSearch();
		
		//	获取查询结果记录数
		String actualCount = impPage.getRowCount();
		String expectCount = ExpectationInDB.getBasisImportQueryCount(params);
		
		//	验证结果
		Assert.assertEquals(actualCount, expectCount);
	}

	@Override
	public void doImport(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料管理——基础资料导入要导入的文件是： " + params);
		//	获取测试数据参数
		String filePath = params.get("uploadFilePath");
		
		String message = params.get("message");
		//	执行测试操作
		String preCount;
		String postCount;
		try{
			preCount = impPage.getRowCount();
			impPage.clickUpload(filePath);
		} catch(BasisMaterialImportException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		impPage.clickSearch();
		postCount = impPage.getRowCount();
		Assert.assertEquals(postCount, String.valueOf(Integer.parseInt(preCount) + 1));
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void scanFailImportDetail(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料管理——基础资料导入查看导入明细测试数据为： " + params);
		//	获取测试数据参数
		String batchNO = params.get("batchNO");
		String expectedCount = params.get("expectedCount");
		
		String message = params.get("message");
		//	执行测试操作
		String actualCount;
		try{
			impPage.clickProcessFailCell(batchNO);
			actualCount = failPage.getRowCount();
			failPage.clickClose();
		} catch(BasisMaterialImportException ex){
			logger.error(ex);
			Assert.assertEquals(ex.getMessage(), message);
			return;
		}
		Assert.assertEquals(actualCount, expectedCount);
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
	
}