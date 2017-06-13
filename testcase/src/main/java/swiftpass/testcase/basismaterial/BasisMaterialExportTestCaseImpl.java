package swiftpass.testcase.basismaterial;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.ExpectationInDB;
import swiftpass.page.basismaterial.BasisMaterialExportPage;
import swiftpass.page.basismaterial.ExportByMchIdDetailSetPage;
import swiftpass.page.basismaterial.ExportDetailSetPage;
import swiftpass.page.exceptions.BasisMaterialExportException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.ITestData;

public class BasisMaterialExportTestCaseImpl extends TestCaseImpl implements BasisMaterialExportTestCase {
	private BasisMaterialExportPage expPage;
	private ExportDetailSetPage expSetPage;
	private ExportByMchIdDetailSetPage expSetMchIdPage;

	public BasisMaterialExportTestCaseImpl(WebDriver driver) {
		super(driver);
		expPage = new BasisMaterialExportPage(driver);
		expSetPage = new ExportDetailSetPage(driver);
		expSetMchIdPage = new ExportByMchIdDetailSetPage(driver);
	}

	@Override
	public void searchHistoryExportData(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料导出管理查询测试用例的数据是： " + params);
		// 获取测试数据参数
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		// 执行测试操作
		expPage.setBeginCT(beginCT);
		expPage.setEndCT(endCT);
		expPage.clickSearch();
		// 获取实际查询结果数
		String actualCount = expPage.getRecordsCount();
		String expectCount = ExpectationInDB.getBasisExportQueryCount(params);
		Assert.assertEquals(actualCount, expectCount);
	}

	@Override
	public void exportHistoryExportData(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料导出管理导出基础资料测试用例的数据是： " + params);
		// 获取测试数据参数
		String dateTime = params.get("dateTime"), date = StringUtils.isEmpty(dateTime) ? "" : dateTime.split(" ")[0],
				time = StringUtils.isEmpty(dateTime) ? "" : dateTime.split(" ")[1];
		String remark = params.get("remark");
		String isExport = params.get("isExport");

		String message = params.get("message");
		// 执行测试操作
		String preExpCount;
		String postExpCount;
		try {
			preExpCount = expPage.getRecordsCount();
			expPage.clickExportBasisMaterial();
			if (!StringUtils.isEmpty(message))
				Assert.assertEquals(expSetPage.isNoteMsgCorrect(), true);
			expSetPage.setExportDateTime(date, time);
			expSetPage.setPromptRemark(remark);
			expSetPage.lastOperate(isExport);
			expPage.clickSearch();
			postExpCount = expPage.getRecordsCount();
		} catch (BasisMaterialExportException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		// 获取导出结果
		Assert.assertEquals(postExpCount, String.valueOf(Integer.parseInt(preExpCount) + 1));
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void downloadOneHistoryExportData(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料管理基础资料导出流水下载的测试数据是： " + params);
		// 获取测试数据参数
		String batchNO = params.get("batchNO");
		Assert.assertEquals(expPage.downloadRowDataByBatchNO(batchNO), true);
	}

	@Override
	public void exportHistoryExportDataByMchIds(Map<String, String> params, ITestData td) {
		params = td.preCheckProcess(params);
		logger.info("本次基础资料管理按商户号导出基础资料的测试数据是： " + params);
		// 获取测试数据参数
		String remark = params.get("remark");
		String isExport = params.get("isExport");
		String[] mchIds = params.get("mchIds").split("-");

		String message = params.get("message");
		// 执行测试操作
		String preCount;
		String postCount;
		try {
			preCount = expPage.getRecordsCount();
			expPage.clickExportBasisMaterialByMchId();
			expSetMchIdPage.setMchIds(mchIds);
			expSetMchIdPage.setRemark(remark);
			expSetMchIdPage.lastOperate(isExport);
		} catch (BasisMaterialExportException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage().contains(message), true);
			return;
		}
		// 验证结果
		postCount = expPage.getRecordsCount();
		Assert.assertEquals(postCount, String.valueOf(Integer.parseInt(preCount) + 1));
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
}