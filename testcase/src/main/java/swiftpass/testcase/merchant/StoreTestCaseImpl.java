package swiftpass.testcase.merchant;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import swiftpass.page.exceptions.StoreException;
import swiftpass.page.merchant.StoreAddPage;
import swiftpass.page.merchant.StoreDetailPage;
import swiftpass.page.merchant.StoreEditPage;
import swiftpass.page.merchant.StorePage;
import swiftpass.page.merchant.StoreQRPage;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.StoreAddCaseBean;
import swiftpass.testcase.casebeans.StoreDetailCaseBean;
import swiftpass.testcase.casebeans.StoreEditCaseBean;
import swiftpass.testcase.casebeans.StoreQRCodeCaseBean;
import swiftpass.testcase.casebeans.StoreSearchCaseBean;
import swiftpass.utils.DBUtil;

public class StoreTestCaseImpl extends TestCaseImpl implements StoreTestCase{
	//主页
	StorePage page;
	StoreAddPage addPage;
	StoreEditPage editPage;
	StoreDetailPage detailPage;
	StoreQRPage qrPage;
	
	public StoreTestCaseImpl(WebDriver driver) {
		super(driver);
		this.page = new StorePage(this.driver);
		this.addPage = new StoreAddPage(this.driver);
		this.editPage = new StoreEditPage(this.driver);
		this.detailPage = new StoreDetailPage(this.driver);
		this.qrPage = new StoreQRPage(this.driver);
	}

	public void searchStore(HashMap<String, String> params) {
		logger.info("本次商户管理——门店管理——查询测试使用的数据是： " + params);
		//获取测试数据
		String bigMchName = params.get("bigMchName");
		String bigMchId = params.get("bigMchId");
		String departName = params.get("departName");
		String departId = params.get("departId");
		String storeName = params.get("storeName");
		String examineStatus = params.get("examineStatus");
		String activeStatus = params.get("activateStatus");
		String storeId = params.get("storeId");
		//获取预期结果
		String expected = params.get("expected");
		
		//执行查询操作
		page.clickSearch();
		Assert.assertEquals(page.isNoteCorrect(), true);
		if(!bigMchName.equals("") && !bigMchId.equals("")){
			page.directSetBigMch(bigMchName, bigMchId);
		}
		if(!departName.equals("") && !departId.equals("")){
			page.directSetDepart(departName, departId);
		}
		page.setStoreName(storeName);
		page.selectExamineStatus(examineStatus);
		page.selectActiveStatus(activeStatus);
		page.setStoreId(storeId);
		page.clickSearch();
		//获取实际查询结果
		String actual = page.getStoreCount();
		//验证比对实际结果
		Assert.assertEquals(actual, expected);
	}

	public void addStore(HashMap<String, String> params) {
		logger.info("本次商户管理——门店管理——新增测试使用的测试数据是： " + params);
		//获取测试数据
		String isSelectBigMch = params.get("isSelectBigMch");
		String bigMchItem = params.get("bigMchItem");
		String bigMchNameOrId = params.get("bigMchNameOrId");
		String isConfirmSelectBigMch = params.get("isConfirmSelectBigMch");
		String departName = params.get("departName");
		String storeName = params.get("storeName");
		String storeType = params.get("storeType");
		String isSave = params.get("isSave");
		
		String errorMsg = params.get("errorMsg");
		
		//获取新增部门前的部门数
		page.clickSearch();
		String preStoreCount = page.getStoreCount();
		System.out.println("preStoreCount:" + preStoreCount);
		//新增页面操作
		try{
			page.clickAdd();
			addPage.selectParentMch(isSelectBigMch, bigMchItem, bigMchNameOrId, isConfirmSelectBigMch);
			addPage.selectDepart(departName);
			addPage.setStoreName(storeName);
			addPage.selectStoreType(storeType);
			addPage.lastAddOperate(isSave);
		} catch(StoreException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		//新增后获取结果
		page.clickSearch();
		String postStoreCount = page.getStoreCount();
		//验证比对结果
		Assert.assertEquals(postStoreCount, "" + (Integer.parseInt(preStoreCount) + 1));
		
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	public void editStore(HashMap<String, String> params) {
		logger.info("本次商户管理——门店管理——编辑测试的测试数据是：" + params);
		//获取编辑测试数据
		String isClickFirstRowStore = params.get("isClickFirstRowStore");
		String storeName = params.get("storeName");
		String storeId = params.get("storeId");
		String isEdit = params.get("isEdit");
		String isConfirmEdit = params.get("isConfirmEdit");
		
		String errorMsg = params.get("errorMsg");
		
		//输入测试数据操作
		try{
			page.setStoreId(storeId);
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickEdit();
			Assert.assertEquals(editPage.checkUneditablePart(), true);
			editPage.editStoreName(storeName);
			editPage.lastEditOperate(isEdit, isConfirmEdit);
		} catch(StoreException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		//验证编辑成功后门店名称是否同步数据库
		String sql = "select * from cms_merchant where merchant_id = $storeId".replace("$storeId", storeId);
		String newStoreName = DBUtil.getQueryResultMap(sql).get(1).get("MERCHANT_NAME");
		Assert.assertEquals(newStoreName, storeName);
	}

	public void scanStoreDetail(HashMap<String, String> params) {
		logger.info("本次门店详情测试数据是： " + params);
		//获取测试数据参数
		String isClickFirstRowStore = params.get("isClickFirstRowStore");
		String errorMsg = params.get("errorMsg");
		
		try{
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickDetail();
			Assert.assertEquals(detailPage.isStoreDetailPage(), true);
			detailPage.clickClose();
		} catch(StoreException e){
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	public void scanQRCode(HashMap<String, String> params) {
		logger.info("本次门店二维码测试数据是： " + params);
		//获取测试数据参数
		String isClickFirstRowStore = params.get("isClickFirstRowStore");
		String errorMsg = params.get("errorMsg");
				
		try{
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickQR();
			qrPage.takeScreenshot(driver, "_门店详情页面");
			Assert.assertEquals(qrPage.checkQRImage(), true);
		} catch(StoreException e){
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	public void exportStore() {
		logger.info("本次商户管理——门店管理——导出文件的测试数据是：" + null);
		page.clickExport();
		page.takeScreenshot(driver, "_导出门店");
	}

	
	
	
	
	/**
	 * sunhaojie 2017-04-01 
	 * CaseBean方法重构
	 */
	public void runSearch(StoreSearchCaseBean caseBean) {
		logger.info("本次商户管理——门店管理——查询测试使用的数据是： " + caseBean);
		//获取测试数据
		String bigMchName = caseBean.getBigMchName();
		String bigMchId = caseBean.getBigMchId();
		String departName = caseBean.getDepartName();
		String departId = caseBean.getDepartId();
		String storeName = caseBean.getStoreName();
		String examineStatus = caseBean.getExamineStatus();
		String activeStatus = caseBean.getActivateStatus();
		String storeId = caseBean.getStoreId();
		//获取预期结果
		String expected = caseBean.getExpected();
		
		//执行查询操作
		page.clickSearch();
		Assert.assertEquals(page.isNoteCorrect(), true);
		if(!bigMchName.equals("") && !bigMchId.equals("")){
			page.directSetBigMch(bigMchName, bigMchId);
		}
		if(!departName.equals("") && !departId.equals("")){
			page.directSetDepart(departName, departId);
		}
		page.setStoreName(storeName);
		page.selectExamineStatus(examineStatus);
		page.selectActiveStatus(activeStatus);
		page.setStoreId(storeId);
		page.clickSearch();
		//获取实际查询结果
		String actual = page.getStoreCount();
		//验证比对实际结果
		Assert.assertEquals(actual, expected);
	}

	public void runAdd(StoreAddCaseBean caseBean) {
		logger.info("本次商户管理——门店管理——新增测试使用的测试数据是： " + caseBean);
		//获取测试数据
		String isSelectBigMch = caseBean.getIsSelectBigMch();
		String bigMchItem = caseBean.getBigMchItem();
		String bigMchNameOrId = caseBean.getBigMchNameOrId();
		String isConfirmSelectBigMch = caseBean.getIsConfirmSelectBigMch();
		String departName = caseBean.getDepartName();
		String storeName = caseBean.getStoreName();
		String storeType = caseBean.getStoreType();
		String isSave = caseBean.getIsSave();
		
		String errorMsg = caseBean.getErrorMsg();
		
		//获取新增部门前的部门数
		page.clickSearch();
		String preStoreCount = page.getStoreCount();
		System.out.println("preStoreCount:" + preStoreCount);
		//新增页面操作
		try{
			page.clickAdd();
			addPage.selectParentMch(isSelectBigMch, bigMchItem, bigMchNameOrId, isConfirmSelectBigMch);
			addPage.selectDepart(departName);
			addPage.setStoreName(storeName);
			addPage.selectStoreType(storeType);
			addPage.lastAddOperate(isSave);
		} catch(StoreException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		//新增后获取结果
		page.clickSearch();
		String postStoreCount = page.getStoreCount();
		//验证比对结果
		Assert.assertEquals(postStoreCount, "" + (Integer.parseInt(preStoreCount) + 1));
		
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	public void runEdit(StoreEditCaseBean caseBean) {
		logger.info("本次商户管理——门店管理——编辑测试的测试数据是：" + caseBean);
		//获取编辑测试数据
		String isClickFirstRowStore = caseBean.getIsClickFirstRowStore();
		String storeName = caseBean.getStoreName();
		String storeId = caseBean.getStoreId();
		String isEdit = caseBean.getIsEdit();
		String isConfirmEdit = caseBean.getIsConfirmEdit();	
		String errorMsg = caseBean.getErrorMsg();
		
		//输入测试数据操作
		try{
			page.setStoreId(storeId);
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickEdit();
			Assert.assertEquals(editPage.checkUneditablePart(), true);
			editPage.editStoreName(storeName);
			editPage.lastEditOperate(isEdit, isConfirmEdit);
		} catch(StoreException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		//验证编辑成功后门店名称是否同步数据库
		String sql = "select * from cms_merchant where merchant_id = $storeId".replace("$storeId", storeId);
		String newStoreName = DBUtil.getQueryResultMap(sql).get(1).get("MERCHANT_NAME");
		Assert.assertEquals(newStoreName, storeName);
	}

	public void runDetail(StoreDetailCaseBean caseBean) {
		logger.info("本次门店详情测试数据是： " + caseBean);
		//获取测试数据参数
		String isClickFirstRowStore = caseBean.getIsClickFirstRowStore();
		String errorMsg = caseBean.getErrorMsg();
		
		try{
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickDetail();
			Assert.assertEquals(detailPage.isStoreDetailPage(), true);
			detailPage.clickClose();
		} catch(StoreException e){
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	public void runQRCode(StoreQRCodeCaseBean caseBean) {
		logger.info("本次门店二维码测试数据是： " + caseBean);
		//获取测试数据参数
		String isClickFirstRowStore = caseBean.getIsClickFirstRowStore();
		String errorMsg = caseBean.getErrorMsg();
				
		try{
			page.clickSearch();
			page.clickFirstRow(isClickFirstRowStore);
			page.clickQR();
			qrPage.takeScreenshot(driver, "_门店详情页面");
			Assert.assertEquals(qrPage.checkQRImage(), true);
		} catch(StoreException e){
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}
}
