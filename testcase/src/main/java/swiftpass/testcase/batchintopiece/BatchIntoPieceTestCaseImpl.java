package swiftpass.testcase.batchintopiece;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DeptDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.page.TimePane;
import swiftpass.page.batchintopiece.BatchIntoPiecePage;
import swiftpass.page.exceptions.BatchIntoPieceException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.batchintopiece.BIPRehandleTestData;
import swiftpass.utils.WebFileUploader;

public class BatchIntoPieceTestCaseImpl extends TestCaseImpl implements BatchIntoPieceTestCase{
	private BatchIntoPiecePage page;
	private TimePane tp;

	public BatchIntoPieceTestCaseImpl(WebDriver driver) {
		super(driver);
		page = new BatchIntoPiecePage(driver);
		tp = new TimePane(driver);
	}

	@Override
	public void doSearch(HashMap<String, String> params) {
		logger.info("本次批量进件——查询测试用例使用的数据是：" + params);
		String beginCT = params.get("beginCT");
		String endCT = params.get("endCT");
		String expected = params.get("expected");
		//执行查询
		page.clickSearch();
		if(!beginCT.equals("")) {
			page.clickBeginCT();
			tp.setDate_Time(beginCT);
		}
		if(!endCT.equals("")) {
			page.clickEndCT();
			tp.setDate_Time(endCT);
		}
		page.clickSearch();
		
		//验证查询结果
//		String actual = page.getTotalcount();
		String actual = page.getTotalcount();
		Assert.assertEquals(actual, expected);
		
	}

	@Override
	public void doMerchantBIP(HashMap<String, String> params) {
		logger.info("本次商户批量进件使用的测试数据是：" + params);
		String fileName = params.get("fileName");
		File f = new File(fileName.substring(2));
		fileName = f.getAbsolutePath();
		String errorMsg = params.get("errorMsg");
		String msgInfo = params.get("msgInfo");
		String channelId = params.get("channelId");
		String merchantName = params.get("merchantName");
		String isSure = params.get("isSureESAndAS");
		try{
			boolean flag = page.checkTipMessage();
			Assert.assertEquals(flag, true);
			page.clickMerchantBIP();
			page.clickConfirmOrCancel(isSure);
			WebFileUploader.uploadFile(fileName);
			page.lastOprate();
		}catch(BatchIntoPieceException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			Assert.assertEquals(page.checkFailUpload(msgInfo), true);
			return;
		}
		//判断批量进件成功商户审核激活状态
		HashMap<String, String> merchantInfo = MerchantDBOperations.getMerchantByName(merchantName);
		String es = merchantInfo.get("EXAMINE_STATUS");
		String as = merchantInfo.get("ACTIVATE_STATUS");
		if(isSure.equals("true")){
			Assert.assertEquals(es, "1");
			Assert.assertEquals(as, "1");
		}else{
			Assert.assertEquals(es, "0");
			Assert.assertEquals(as, "0");
		}
		//判断进件商户ACC_WAY是否插入正确(巨丽要求的)
		HashMap<String, String> channelInfo = ChannelDBOperations.getChannel(channelId);
		String accWay = channelInfo.get("ACC_WAY");
		String acc_way = merchantInfo.get("ACC_WAY");
		System.out.println(accWay + ">>>>" + acc_way);
		if(accWay == null || accWay.equals("")){
			Assert.assertEquals(acc_way, "1");
		}else{
			Assert.assertEquals(accWay, acc_way);
		}
	}

	@Override
	public void doDeptBIP(HashMap<String, String> params) {
		logger.info("本次部门批量进件使用的测试数据是：" + params);
		String fileName = params.get("fileName");
		File f = new File(fileName.substring(2));
		fileName = f.getAbsolutePath();
		String deptName = params.get("deptName");
		String errorMsg = params.get("errorMsg");
		String msgInfo = params.get("msgInfo");
		try{
			page.clickDeptBIP();
			WebFileUploader.uploadFile(fileName);
			page.lastOprate();
		}catch(BatchIntoPieceException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			Assert.assertEquals(page.checkFailUpload(msgInfo), true);
			return;
		}
		//查询数据库验证批量进件是否成功
		HashMap<String, String> deptInfo = DeptDBOperations.getDeptByName(deptName);
		if(deptInfo != null){
			Assert.assertEquals(true, true);
		}else{
			Assert.assertEquals(true, false);
		}
		
	}

	@Override
	public void doStoreBIP(HashMap<String, String> params) {
		logger.info("本次门店批量进件使用的测试数据是：" + params);
		String fileName = params.get("fileName");
		File f = new File(fileName.substring(2));
		fileName = f.getAbsolutePath();
		String errorMsg = params.get("errorMsg");
		String msgInfo = params.get("msgInfo");
		String storeName = params.get("storeName");
		try{
			page.clickStoreBIP();
			WebFileUploader.uploadFile(fileName);
			page.lastOprate();
		}catch(BatchIntoPieceException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			Assert.assertEquals(page.checkFailUpload(msgInfo), true);
			return;
		}
		//判断是否批量进件成功
		HashMap<String, String> storeInfo = StoreDBOperations.storeInfoByName(storeName);
		if(storeInfo != null){
			Assert.assertEquals(true, true);
		}else{
			Assert.assertEquals(true, false);
		}
	}

	@Override
	public void doMerchantTemplatDownload() {
		page.clickMerchantTemplateDownload();
		Assert.assertEquals(page.CheckDownloadMchTemp(), true);
		logger.info("商户批量进件模板下载比对完成！");
		
	}

	@Override
	public void doDeptTemplateDownload() {
		page.clickDeptTemplateDownload();
		Assert.assertEquals(page.checkDownloadDeptTemp(), true);
		logger.info("部门批量进件模板下载比对完成！");
	}

	@Override
	public void doStoreTemplateDownload() {
		page.clickStoreTemplateDownload();
		Assert.assertEquals(page.checkStoreDownloadTemp(), true);
		logger.info("门店批量进件模板下载比对完成！");
		
	}

	@Override
	public void doRehandle(HashMap<String, String> params) {
		logger.info("本次批量进件——重新执行测试用例使用的数据是：" + params);
		String isClickFirstRow = params.get("isClickFirstRow");
		String isConfirm = params.get("isConfirm");
		String processStatus = params.get("processStatus");
		String errorMsg = params.get("errorMsg");
		//获取需要的数据
		BIPRehandleTestData.getNeedData(processStatus);
		try{	
			page.clickSearch();
			page.clickFirstRow(isClickFirstRow);
			page.clickRehandle(processStatus);
			page.isConfirm(isConfirm);
		}catch(BatchIntoPieceException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		//判断是否重新处理成功
		
	}

}
