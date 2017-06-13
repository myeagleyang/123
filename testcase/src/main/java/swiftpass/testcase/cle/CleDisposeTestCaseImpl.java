package swiftpass.testcase.cle;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.CleDBOperations;
import swiftpass.page.cle.CleDisposePage;
import swiftpass.page.exceptions.cleException;
import swiftpass.testcase.TestCaseImpl;

public class CleDisposeTestCaseImpl extends TestCaseImpl implements CleDisposeTestCase{
	private CleDisposePage cleDisposePage;

	public CleDisposeTestCaseImpl(WebDriver driver) {
		super(driver);
		cleDisposePage = new CleDisposePage(driver);
	}

	@Override
	public void searchCleData(HashMap<String, String> params) {
		logger.info("本次测试清分处理查询使用的测试数据是：" + params);
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		String errorMsg = params.get("errorMsg");
		//执行测试
		try{
			cleDisposePage.setBeginCTAndEndCT(beginCT, endCT);
			cleDisposePage.clickSearch();
			Assert.assertEquals(cleDisposePage.checkCleTotalcount(), true);
			Assert.assertEquals(cleDisposePage.checkCleTotalAcc(), true);
			Assert.assertEquals(cleDisposePage.checkChCleCount(), true);
			Assert.assertEquals(cleDisposePage.checkChCleAcc(), true);
			Assert.assertEquals(cleDisposePage.checkMchClecount(), true);
			Assert.assertEquals(cleDisposePage.checkMchCleAcc(), true);
			Assert.assertEquals(cleDisposePage.checkCleData(), true);
			logger.info("清分处理页面检查完成！");
		}catch(cleException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			Assert.assertEquals(true, false);
			return;
		}
		//检查cle_cleaning_bill中清分状态
		HashMap<String, String> cleChnAccTask = CleDBOperations.getCleChnAccTask();
		HashMap<Integer, HashMap<String, String>> cleCleaningBill = CleDBOperations.getCleCleaningBill(cleChnAccTask.get("ACC_TASK_ID"));
		for(int i: cleCleaningBill.keySet()){
			HashMap<String, String>  cleStatus = cleCleaningBill.get(i);
			Assert.assertEquals(cleStatus.get("CLEANING_STATUS"), "1");
		}
	}

	@Override
	public void disposeCleaning(HashMap<String, String> params) {
		logger.info("本次测试清分处理发起清分使用的测试数据是：" + params);
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		String errorMsg = params.get("errorMsg");
		//执行测试
		try{
			cleDisposePage.setBeginCTAndEndCT(beginCT, endCT);
			cleDisposePage.clickSearch();
			cleDisposePage.clickInitiateCle();
		}catch(cleException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		
		//检查cle_cleaning_bill中清分状态
		HashMap<String, String> cleChnAccTask = CleDBOperations.getCleChnAccTask();
		HashMap<Integer, HashMap<String, String>> cleCleaningBill = CleDBOperations.getCleCleaningBill(cleChnAccTask.get("ACC_TASK_ID"));
		for(int i: cleCleaningBill.keySet()){
			HashMap<String, String>  cleStatus = cleCleaningBill.get(i);
			Assert.assertEquals(cleStatus.get("CLEANING_STATUS"), "3");
		}
		//检查cle_cleaning_process_log中清分状态
		HashMap<String, String> cleCleaningLog = CleDBOperations.getCleCleaningLog(cleChnAccTask.get("ACC_TASK_ID"));
		Assert.assertEquals(cleCleaningLog.get("CLEANING_STATE"), "2");
		logger.info("发起清分操作成功！");
		
	}

	@Override
	public void downloadCleFile(HashMap<String, String> params) {
		logger.info("本次测试清分处理下载清分文件所使用的数据是：" + params);
		String file = params.get("file");
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		//执行用例
		cleDisposePage.setBeginCTAndEndCT(beginCT, endCT);
		cleDisposePage.clickDownloadcleFile();
		Assert.assertEquals(cleDisposePage.checkDownloadFile(file), true);
		logger.info("下载清分文件比对完毕！");
	}

}
