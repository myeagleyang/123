package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import irsy.testcase.casebeans.MerchantAddCaseBean;
import swiftpass.page.MenuType;
import swiftpass.testcase.CaseBean;
import swiftpass.testcase.casebeans.MerchantAAACaseBean;
import swiftpass.testcase.casebeans.MerchantActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantAuditCaseBean;
import swiftpass.testcase.casebeans.MerchantDetailCaseBean;
import swiftpass.testcase.casebeans.MerchantEditCaseBean;
import swiftpass.testcase.casebeans.MerchantPCActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantPCAddCaseBean;
import swiftpass.testcase.casebeans.MerchantPCDetailCaseBean;
import swiftpass.testcase.casebeans.MerchantPCEditCaseBean;
import swiftpass.testcase.casebeans.MerchantSearchCaseBean;
import swiftpass.testcase.merchant.MerchantPCTestCase;
import swiftpass.testcase.merchant.MerchantPCTestCaseImpl;
import swiftpass.testcase.merchant.MerchantTestCase;
import swiftpass.testcase.merchant.MerchantTestCaseImpl;
import swiftpass.testcase.testdata.merchant.XMerchantAAATestData;
import swiftpass.testcase.testdata.merchant.XMerchantActiveTestData;
import swiftpass.testcase.testdata.merchant.XMerchantAddTestData;
import swiftpass.testcase.testdata.merchant.XMerchantAuditTestData;
import swiftpass.testcase.testdata.merchant.XMerchantDetailTestData;
import swiftpass.testcase.testdata.merchant.XMerchantEditTestData;
import swiftpass.testcase.testdata.merchant.XMerchantPCActiveTestData;
import swiftpass.testcase.testdata.merchant.XMerchantPCAddTestData;
import swiftpass.testcase.testdata.merchant.XMerchantPCDetailTestData;
import swiftpass.testcase.testdata.merchant.XMerchantPCEditTestData;
import swiftpass.testcase.testdata.merchant.XMerchantSearchTestData;

public class MerchantTestCaseRunner extends CaseRunner{
	@BeforeMethod(description = "刷新页面......")
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_MM);
		menu.clickElement(MenuType.HM_MM_MA);
	}
	
/*	@DataProvider
	public Object[][] merchantTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearch")){
			params = MerchantSearchTestData.getMerchantSearchTestData();
		} else if(method.getName().equals("runAdd")){
			params = MerchantAddTestData.getMerchantAddTestData();
		} else if(method.getName().equals("runEdit")){
			params = MerchantEditTestData.getMerchantEditTestData();
		} else if(method.getName().equals("runAudit")){
			params = MerchantAuditTestData.getMerchantAuditTestData();
		} else if(method.getName().equals("runActive")){
			params = MerchantActiveTestData.getMerchantActiveTestData();
		} else if(method.getName().equals("runAAA")){
			params = MerchantAAATestData.getMerchantAAATestData();
		} else if(method.getName().equals("runDetail")){
			params = MerchantDetailTestData.getMerchantDetailTestData();
		} else if(method.getName().equals("runAddPC")){
			params = MerchantPCAddTestData.getMerchantPayConfAddTestData();
		} else if(method.getName().equals("runEditPC")){
			params = MerchantPCEditTestData.getMerchantPCEditTestData();
		} else if(method.getName().equals("runActivePC")){
			params = MerchantPCActiveTestData.getMerchantPCActiveTestData();
		} else if(method.getName().equals("runDetailPC")){
			params = MerchantPCDetailTestData.getMerchantPCDetailTestData();
		}
		return params;
	}

	@Test(dataProvider = "merchantTestData")//
	public void runSearch(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理查询测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.searchMerchant(params);
		logger.info("商户管理——商户进件管理查询测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runAdd(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理新增测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.addMerchant(params);
		logger.info("商户管理——商户进件管理新增测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runEdit(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理编辑测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.editMerchant(params);
		logger.info("商户管理——商户进件管理编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runAudit(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理审核测试用例......");
		MerchantTestCase testcase = new MerchantTestCaseImpl(driver);
		testcase.auditMerchant(params);
		logger.info("商户管理——商户进件管理审核测试用例执行完成！");
	}

	@Test(dataProvider = "merchantTestData")//
	public void runActive(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理激活测试用例.....");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.activeMerchant(params);
		logger.info("商户管理——商户进件管理激活测试用例结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runAAA(HashMap<String, String> params){
		logger.info("开始执行商户进件审核并激活测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.auditAndActiveMerchant(params);
		logger.info("开始执行商户管理——商户审核并激活测试用例.....");
	}

	@Test(dataProvider = "merchantTestData")//
	public void runDetail(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户详情测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.detailMerchant(params);
		logger.info("商户管理——商户详情测试用例结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runAddPC(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置新增测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.addMerchantPC(params);
		logger.info("商户管理——商户进件管理支付类型配置新增测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runEditPC(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置编辑测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.editMerchantPC(params);
		logger.info("商户管理——商户进件管理支付类型配置编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runActivePC(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户进件管理支付类型配置激活测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.activeMerchantPC(params);
		logger.info("商户管理——商户进件管理支付类型配置激活测试用例执行结束！");
	}
	
	@Test(dataProvider = "merchantTestData")//
	public void runDetailPC(HashMap<String, String> params){
		logger.info("开始执行商户管理——商户支付类型配置详情测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.detailMerchantPC(params);
		logger.info("商户管理——商户支付类型配置详情测试用例执行结束！");
	}
	*/
	

	// ----------------------------CaseBean数据驱动重构-------------------------------
	@DataProvider(name = "testData")
	public CaseBean[][] testData(Method method){
		CaseBean[][] cases = null;
		if(method.getName().equals("runSearch")){
			cases = XMerchantSearchTestData.data();
		} else if(method.getName().equals("runAdd")){
			cases = XMerchantAddTestData.data();
		}else if(method.getName().equals("runCheckPhone")){
			cases = XMerchantAddTestData.checkPhoneData();
		}else if(method.getName().equals("runEdit")){
			cases = XMerchantEditTestData.data();
		} else if(method.getName().equals("runAudit")){
			cases = XMerchantAuditTestData.data();
		}else if(method.getName().equals("runActive")){
			cases = XMerchantActiveTestData.data();
		} else if(method.getName().equals("runAAA")){
			cases = XMerchantAAATestData.data();
		}else if(method.getName().equals("runDetail")){
			cases = XMerchantDetailTestData.data();
		}else if(method.getName().equals("runAddPC")){
			cases = XMerchantPCAddTestData.data();
		} else if(method.getName().equals("runEditPC")){
			cases = XMerchantPCEditTestData.data();
		} else if(method.getName().equals("runActivePC")){
			cases = XMerchantPCActiveTestData.data();
		} else if(method.getName().equals("runDetailPC")){
			cases = XMerchantPCDetailTestData.data();
		}
		return cases;
	}
	

	@Test(dataProvider = "testData")//
	public void runSearch(MerchantSearchCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理查询测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runSearch(caseBean);
		logger.info("商户管理——商户进件管理查询测试用例执行结束！");
	}
	@Test(dataProvider = "testData")//
	public void runAdd(MerchantAddCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理新增测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runAdd(caseBean);
		logger.info("商户管理——商户进件管理新增测试用例执行结束！");
	}
	@Test(dataProvider = "testData")//
	public void runCheckPhone(MerchantAddCaseBean caseBean){
		logger.info("开始执行商户管理——手机号码段校验测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.checkPhone(caseBean);
		logger.info("商户进件管理——手机号码段校验——测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runEdit(MerchantEditCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理编辑测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runEdit(caseBean);
		logger.info("商户管理——商户进件管理编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runAudit(MerchantAuditCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理审核测试用例......");
		MerchantTestCase testcase = new MerchantTestCaseImpl(driver);
		testcase.runAudit(caseBean);
		logger.info("商户管理——商户进件管理审核测试用例执行完成！");
	}

	@Test(dataProvider = "testData")//
	public void runActive(MerchantActiveCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理激活测试用例.....");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runActive(caseBean);
		logger.info("商户管理——商户进件管理激活测试用例结束！");
	}
	@Test(dataProvider = "testData")//
	public void runAAA(MerchantAAACaseBean caseBean){
		logger.info("开始执行商户进件审核并激活测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runAndActiveMerchant(caseBean);
		logger.info("开始执行商户管理——商户审核并激活测试用例.....");
	}

	@Test(dataProvider = "testData")//
	public void runDetail(MerchantDetailCaseBean caseBean){
		logger.info("开始执行商户管理——商户详情测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runDetail(caseBean);
		logger.info("商户管理——商户详情测试用例结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runAddPC(MerchantPCAddCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置新增测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runAddPC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置新增测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runEditPC(MerchantPCEditCaseBean caseBean){	
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置编辑测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runEditPC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runActivePC(MerchantPCActiveCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理支付类型配置激活测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runActivePC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置激活测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runDetailPC(MerchantPCDetailCaseBean caseBean){
		logger.info("开始执行商户管理——商户支付类型配置详情测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runDetailPC(caseBean);
		logger.info("商户管理——商户支付类型配置详情测试用例执行结束！");
	}
	
	
/*//	  -------------------- 兴业银行模块-----------------------
	@DataProvider(name = "testData")
	public CaseBean[][] testData(Method method){
		CaseBean[][] cases = null;
		if(method.getName().equals("runSearch")){
			cases = XMerchantSearchTestData.data();
		} else if(method.getName().equals("runAdd_xy")){
			cases = XMerchantAddTestData.data();
		}else if(method.getName().equals("runCheckPhone")){
			cases = XMerchantAddTestData.checkPhoneData();
		}else if(method.getName().equals("runEdit")){
			cases = XMerchantEditTestData.data();
		} else if(method.getName().equals("runAudit")){
			cases = XMerchantAuditTestData.data();
		}else if(method.getName().equals("runActive")){
			cases = XMerchantActiveTestData.data();
		} else if(method.getName().equals("runAAA")){
			cases = XMerchantAAATestData.data();
		}else if(method.getName().equals("runDetail_xy")){
			cases = XMerchantDetailTestData.data();
		} else if(method.getName().equals("runAddPC")){
			cases = XMerchantPCAddTestData.data();
		}else if(method.getName().equals("runEditPC")){
			cases = XMerchantPCEditTestData.data();
		} else if(method.getName().equals("runActivePC")){
			cases = XMerchantPCActiveTestData.data();
		} else if(method.getName().equals("runDetailPC")){
			cases = XMerchantPCDetailTestData.data();
		}
		return cases;
	}
	
	@Test(dataProvider = "testData")//
	public void runSearch(MerchantSearchCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理查询测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runSearch(caseBean);
		logger.info("商户管理——商户进件管理查询测试用例执行结束！");
	}
	@Test(dataProvider = "testData")//
	public void runAdd_xy(MerchantAddCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理新增测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runAdd_xy(caseBean);
		logger.info("商户管理——商户进件管理新增测试用例执行结束！");
	}
	@Test(dataProvider = "testData")//
	public void runCheckPhone(MerchantAddCaseBean caseBean){
		logger.info("开始执行商户管理——手机号码段校验测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.checkPhone(caseBean);
		logger.info("商户进件管理——手机号码段校验——测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runEdit(MerchantEditCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理编辑测试用例......");
		MerchantTestCase mc = new MerchantTestCaseImpl(driver);
		mc.runEdit(caseBean);
		logger.info("商户管理——商户进件管理编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runAudit(MerchantAuditCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理审核测试用例......");
		MerchantTestCase testcase = new MerchantTestCaseImpl(driver);
		testcase.runAudit(caseBean);
		logger.info("商户管理——商户进件管理审核测试用例执行完成！");
	}

	@Test(dataProvider = "testData")//
	public void runActive(MerchantActiveCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理激活测试用例.....");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runActive(caseBean);
		logger.info("商户管理——商户进件管理激活测试用例结束！");
	}
	@Test(dataProvider = "testData")//
	public void runAAA(MerchantAAACaseBean caseBean){
		logger.info("开始执行商户进件审核并激活测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runAndActiveMerchant(caseBean);
		logger.info("开始执行商户管理——商户审核并激活测试用例.....");
	}

	@Test(dataProvider = "testData")//
	public void runDetail_xy(MerchantDetailCaseBean caseBean){
		logger.info("开始执行商户管理——商户详情测试用例......");
		MerchantTestCase tc = new MerchantTestCaseImpl(driver);
		tc.runDetail_xy(caseBean);
		logger.info("商户管理——商户详情测试用例结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runAddPC(MerchantPCAddCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置新增测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runAddPC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置新增测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runEditPC(MerchantPCEditCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理商户支付类型配置编辑测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runEditPC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置编辑测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runActivePC(MerchantPCActiveCaseBean caseBean){
		logger.info("开始执行商户管理——商户进件管理支付类型配置激活测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runActivePC(caseBean);
		logger.info("商户管理——商户进件管理支付类型配置激活测试用例执行结束！");
	}
	
	@Test(dataProvider = "testData")//
	public void runDetailPC(MerchantPCDetailCaseBean caseBean){
		logger.info("开始执行商户管理——商户支付类型配置详情测试用例......");
		MerchantPCTestCase tc = new MerchantPCTestCaseImpl(driver);
		tc.runDetailPC(caseBean);
		logger.info("商户管理——商户支付类型配置详情测试用例执行结束！");
	}*/
}
