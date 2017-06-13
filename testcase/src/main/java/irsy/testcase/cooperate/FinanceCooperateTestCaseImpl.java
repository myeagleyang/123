package irsy.testcase.cooperate;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import irsy.page.cocoperate.CooperatePage;
import irsy.testcase.casebeans.CooperateAddCaseBean;
import irsy.testcase.data.cooperate.CooperateTestData;
import swiftpass.page.exceptions.MerchantException;
import swiftpass.page.exceptions.MerchantPayConfException;
import swiftpass.page.merchant.MerchantPage;
import swiftpass.page.merchant.MerchantPayConfActivePage;
import swiftpass.page.merchant.MerchantPayConfAddPage;
import swiftpass.page.merchant.MerchantPayConfDetailPage;
import swiftpass.page.merchant.MerchantPayConfEditPage;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.MerchantPCActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantPCAddCaseBean;
import swiftpass.testcase.casebeans.MerchantPCEditCaseBean;
import swiftpass.testcase.testdata.merchant.MerchantPCActiveTestData;
import swiftpass.testcase.testdata.merchant.MerchantPCAddTestData;
import swiftpass.testcase.testdata.merchant.MerchantPCDetailTestData;
import swiftpass.testcase.testdata.merchant.MerchantPCEditTestData;

//public class FinanceCooperateTestCaseImpl extends TestCaseImpl implements FinanceCooperateTestCase {
	public class FinanceCooperateTestCaseImpl extends TestCaseImpl  {
	public FinanceCooperateTestCaseImpl(WebDriver driver) {
		super(driver);
	}

	private MerchantPage page = new MerchantPage(driver);
	private CooperatePage page1 = new CooperatePage(driver);
	private MerchantPayConfAddPage pcAddPage = new MerchantPayConfAddPage(driver);
	private MerchantPayConfEditPage pcEditPage = new MerchantPayConfEditPage(driver);
	private MerchantPayConfActivePage pcActivePage = new MerchantPayConfActivePage(driver);
	private MerchantPayConfDetailPage pcDetailPage = new MerchantPayConfDetailPage(driver);

	/*@Override
	public void addMerchantPC(HashMap<String, String> params) {
		logger.info("本次商户管理——商户进件管理——支付类型配置测试使用的测试数据是: " + params);
		// 获取测试数据参数
		String isClickFirstRowMerchant = params.get(MerchantPCAddTestData.ISCLICKFIRSTROWMERCHANT);
		String isSelectPCName = params.get(MerchantPCAddTestData.ISSELECTPCNAME);
		String payCenterName = params.get(MerchantPCAddTestData.PAYCENTERNAME);
		String isSelectPT = params.get(MerchantPCAddTestData.ISSELECTPT);
		String payTypeName = params.get(MerchantPCAddTestData.PAYTYPENAME);
		String payTypeId = params.get(MerchantPCAddTestData.PAYTYPEID);
		String payCenterId = params.get(MerchantPCAddTestData.PAYCENTERID);
		String isConfirmSelectPC = params.get(MerchantPCAddTestData.ISCONFIRMSELECTPC);
		String isConfirmSelectPT = params.get(MerchantPCAddTestData.ISCONFIRMSELECTPT);
		String clrRate = params.get(MerchantPCAddTestData.CLRRATE);
		String parentClrRate = params.get(MerchantPCAddTestData.PARENTCLRRATE);
		String minLimit = params.get(MerchantPCAddTestData.MINLIMIT);
		String maxLimit = params.get(MerchantPCAddTestData.MAXLIMIT);
		String isSetNewClearAccount = params.get(MerchantPCAddTestData.ISSETNEWCLEARACCOUNT);
		String isSelectBank = params.get(MerchantPCAddTestData.ISSELECTBANK);
		String bankName = params.get(MerchantPCAddTestData.BANKNAME);
		String bankId = params.get(MerchantPCAddTestData.BANKID);
		String isConfirmSelectBank = params.get(MerchantPCAddTestData.ISCONFIRMSELECTBANK);
		String bankAccount = params.get(MerchantPCAddTestData.BANKACCOUNT);
		String bankAccountOwner = params.get(MerchantPCAddTestData.BANKACCOUNTOWNER);
		String bankAccountType = params.get(MerchantPCAddTestData.BANKACCOUNTTYPE);
		String subBankCode = params.get(MerchantPCAddTestData.SUBBANKCODE);
		String isSave = params.get(MerchantPCAddTestData.ISSAVE);
		String isConfirmSave = params.get(MerchantPCAddTestData.ISCONFIRMSAVE);
		String existedBankCode = params.get(MerchantPCAddTestData.EXISTBANKCODE);
		String isConfirmSelectExistBank = params.get(MerchantPCAddTestData.ISCONFIRMSELECTEXISTBANK);
		String errorMsg = params.get(MerchantPCAddTestData.ERRORMSG);
		String merchantId = params.get(MerchantPCAddTestData.MERCHANTID);
		String isParentChnSetPC = params.get(MerchantPCAddTestData.ISPARENTCHNSETPC);

		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			logger.debug("选择的商户Id为：" + merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return; // 在没选中商户时，验证支付类型操作的新增、编辑、激活按钮的状态
			}
			page.clickAddPT();
			// 支付中心为弹框类型
			// if(!StringUtils.isEmpty(errorMsg) &&
			// !errorMsg.contains("商户支付类型配置已存在")
			// && !errorMsg.equals("请先选择支付通道") &&
			// isParentChnSetPC.equals("true")){
			// pcAddPage.directSelectPayCenter(isSelectPCName, payCenterId,
			// payCenterName);
			// pcAddPage.directSelectPayType(isSelectPT, payTypeName,
			// payTypeId);
			// }else{
			// pcAddPage.selectPayCenter(isSelectPCName, payCenterName,
			// isConfirmSelectPC);
			// pcAddPage.selectPayType(isSelectPT, payTypeName,
			// isConfirmSelectPT);
			// }

			// 支付中心为下拉框类型
			if (!StringUtils.isEmpty(errorMsg) && !errorMsg.contains("商户支付类型配置已存在") && !errorMsg.equals("请先选择支付中心")
					&& isParentChnSetPC.equals("true")) {
				pcAddPage.directSelectPayType(isSelectPT, payTypeName, payTypeId);
			} else {
				pcAddPage.selectPayCenter2(isSelectPCName, payCenterName);
				pcAddPage.selectPayType(isSelectPT, payTypeName, isConfirmSelectPT);
			}

			pcAddPage.setClrRate(clrRate);
			pcAddPage.setMerchantSingleMinLimit(minLimit);
			pcAddPage.setMerchantSingleMaxLimit(maxLimit);
			pcAddPage.setMerchantThirdMchId();
			pcAddPage.setZhiFuBaoPID();
			if (errorMsg.contains("商户支付类型配置已存在")) {
				Assert.assertEquals(pcAddPage.checkNewAccountIsVisible(), false);
			} else {
				if (isSetNewClearAccount.equals("true")) {
					pcAddPage.selectBindClrBankType("新录入结算账户");
					if (StringUtils.isEmpty(errorMsg)) {
						pcAddPage.selectBank(isSelectBank, bankName, isConfirmSelectBank);
					} else {
						pcAddPage.directSelectBank(isSelectBank, bankName, bankId);
					}
					pcAddPage.setBankAccount(bankAccount);
					pcAddPage.setBankAccountOwner(bankAccountOwner);
					pcAddPage.selectBankAccountType(bankAccountType);
					pcAddPage.selectSubBankProvince();
					pcAddPage.setSubBnakName();
					pcAddPage.setSubBankPhone();
					pcAddPage.selectIdType();
					pcAddPage.setIdCode();

					pcAddPage.setSubBankCode(subBankCode);
				} else {
					pcAddPage.selectBindClrBankType("关联已有卡");
					pcAddPage.selectExistBank(existedBankCode, isConfirmSelectExistBank);
				}
			}
			if (!isParentChnSetPC.equals("true")) {
				Assert.assertEquals(pcAddPage.checkTipMsg(), true);
				pcAddPage.setParentClrRate(isParentChnSetPC, parentClrRate);
			}
			pcAddPage.lastOperate(isSave, isConfirmSave);
		} catch (MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}

		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}
*/
	
//	@Override
	public void runAdd(HashMap<String, String> params) {
		try {
			// String[] valueKeys = {CONAME, COCONTACTS, COMOBILE};
			String userName = params.get(CooperateTestData.CONAME);
			String contact = params.get(CooperateTestData.COCONTACTS);
			String mobile = params.get(CooperateTestData.COMOBILE);

			driver.findElement(By.partialLinkText("资金方管理")).click();
			new WebDriverWait(driver, 3)
					.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("资金合作方管理")));
			driver.findElement(By.partialLinkText("资金合作方管理")).click();
			driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='curholder']/iframe")));
			driver.findElement(By.partialLinkText("资金合作方信息添加")).click();

			String errormsg = params.get(CooperateTestData.ERRORMSG);

			driver.findElement(By.id("cooperateName")).sendKeys(userName);
			driver.findElement(By.id("contacts")).sendKeys(contact);
			driver.findElement(By.id("mobilePhone")).sendKeys(mobile);

			List<WebElement> ss = driver
					.findElements(By.xpath("//*[@class='control-group']/div/label[@class='error']"));
			for (WebElement s : ss) {
				String r_error = s.getText();
				if (errorIsExist(r_error)) {
					System.out.println("good");
					continue;
				}
			}

			driver.findElement(By.id("btnSubmit")).click();
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static boolean errorIsExist(String error) {
		boolean flag = false;

		String arr[] = { "请输入资金合作方名称", "请输入联系人", "请输入联系人电话" };
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(error)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/*
	@Override
	public void editMerchantPC(HashMap<String, String> params) {
		logger.info("本次商户管理——商户进件管理支付类型配置编辑测试的测试数据是： " + params);
		// 获取测试数据参数
		String thirdMchId = params.get(MerchantPCEditTestData.THIRDMCHID);
		String isClickFirstRowMerchant = params.get(MerchantPCEditTestData.ISCLICKFIRSTROWMERCHANT);
		String isClickFirstRowPT = params.get(MerchantPCEditTestData.ISCLICKFIRSTROWPT);
		String clrRate = params.get(MerchantPCEditTestData.CLRRATE);
		String minLimit = params.get(MerchantPCEditTestData.MINLIMIT);
		String maxLimit = params.get(MerchantPCEditTestData.MAXLIMIT);
		String isEdit = params.get(MerchantPCEditTestData.ISEDIT);
		String isConfirmEdit = params.get(MerchantPCEditTestData.ISCONFIRMEDIT);
		String errorMsg = params.get(MerchantPCEditTestData.ERRORMSG);
		String merchantId = params.get(MerchantPCEditTestData.MERCHANTID);
		String payTypeName = params.get(MerchantPCEditTestData.PAYTYPENAME);

		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return;
			}
			page.clickRowByPTName(isClickFirstRowPT, payTypeName);
			page.clickEditPT();
			Assert.assertEquals(pcEditPage.checkUneditable(), true);
			pcEditPage.editClrRate(clrRate);
			pcEditPage.editMerchantSingleMinLimit(minLimit);
			pcEditPage.editMerchantSingleMaxLimit(maxLimit);
			pcEditPage.editMerchantThirdMchId(thirdMchId);
			pcEditPage.editZhiFuBaoPID();
			pcEditPage.lastOperate(isEdit, isConfirmEdit);
		} catch (MerchantPayConfException | MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		logger.info("编辑成功！");
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}
*/

/*	
	@Override
	public void activeMerchantPC(HashMap<String, String> params) {
		logger.info("本次商户管理——支付类型激活测试使用的数据是： " + params);
		// 获取测试数据参数
		String es = params.get(MerchantPCActiveTestData.PREMCHEXAMINESTATUS);
		String as = params.get(MerchantPCActiveTestData.PREMCHACTIVATESTATUS);
		String isClickFirstRowMerchant = params.get(MerchantPCActiveTestData.ISCLICKFIRSTROWMERCHANT);
		String isClickFirstRowPT = params.get(MerchantPCActiveTestData.ISCLICKFIRSTROWPT);
		String activateOperate = params.get(MerchantPCActiveTestData.ACTIVATEOPERATE);
		String isConfirmActivateOperate = params.get(MerchantPCActiveTestData.ISCONFIRMACTIVATEOPERATE);
		String mchPCActivateStatus = params.get(MerchantPCActiveTestData.MERCHANTPCACTIVATETSTATUS);
		String errorMsg = params.get(MerchantPCActiveTestData.ERRORMSG);
		String payTypeName = params.get(MerchantPCActiveTestData.PAYTYPENAME);
		String merchantId = MerchantPCActiveTestData.getNeedMchId(es, as, mchPCActivateStatus);
		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			logger.info("使用的商户ID为：" + merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				logger.warn("未选择商户，新增、编辑、激活按钮均不可点");
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return;
			}
			page.clickRowByPTName(isClickFirstRowPT, payTypeName);
			if (!es.equals("1")) {// 所选商户为非审核通过状态
				logger.warn("商户未审核通过，激活按钮不可点击");
				Assert.assertEquals(page.checkPTAcitveButtonIsEnabled(), false);
				return;
			}
			page.clickAcitvePT();
			Assert.assertEquals(pcActivePage.checkPage(), true);
			pcActivePage.activateOperate(activateOperate, isConfirmActivateOperate, mchPCActivateStatus);
		} catch (MerchantException | MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		// if(activeOperate.equals("激活通过")){
		// } else if(activeOperate.equals("激活不通过")){
		// } else if(activeOperate.equals("冻结")){
		// }
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}*/

	
/*	
	@SuppressWarnings("deprecation")
	@Override
	public void detailMerchantPC(HashMap<String, String> params) {
		logger.info("本次商户管理——商户支付类型配置详情测试使用的测试数据是: " + params);
		// 获取测试数据参数
		String isClickFirstRowMerchant = params.get("isClickFirstRowMerchant");
		String isClickFirstRowPT = params.get("isClickFirstRowPT");

		String errorMsg = params.get("errorMsg");
		String needMerchantId = MerchantPCDetailTestData.getNeedMerchantId();

		// 执行测试操作
		try {
			page.setMerchantId(needMerchantId);
			page.clickSearch();
			page.clickMerchantFirstRow(isClickFirstRowMerchant);
			if (!isClickFirstRowMerchant.equals("true")) {
				page.clickDetailPT();
			}
			page.clickFirstPTRow(isClickFirstRowPT);
			page.clickDetailPT();
			Assert.assertEquals(pcDetailPage.checkMerchantPayConfDetailPage(), true);
		} catch (MerchantException | MerchantPayConfException e) {
			logger.error(e.getMessage());
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}

		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}
*/
	
	
	/**
	 * sunhaojie 2017-03-31
	 */
	// ---------------------------CaseBean重构------------------------
/*
	public void runAddPC(MerchantPCAddCaseBean caseBean) {
		logger.info("本次商户管理——商户进件管理——支付类型配置测试使用的测试数据是: " + caseBean);
		// 获取测试数据参数
		String isClickFirstRowMerchant = caseBean.getIsClickFirstRowMerchant();
		String isSelectPCName = caseBean.getIsSelectPCName();
		String payCenterName = caseBean.getPayCenterName();
		String isSelectPT = caseBean.getIsSelectPT();
		String payTypeName = caseBean.getPayTypeName();
		String payTypeId = caseBean.getPayTypeId();
		String payCenterId = caseBean.getPayCenterId();
		String isConfirmSelectPC = caseBean.getIsConfirmSelectPC();
		String isConfirmSelectPT = caseBean.getIsConfirmSelectPT();
		String clrRate = caseBean.getClrRate();
		String parentClrRate = caseBean.getParentClrRate();
		String minLimit = caseBean.getMinLimit();
		String maxLimit = caseBean.getMaxLimit();
		String isSetNewClearAccount = caseBean.getIsSetNewClearAccount();
		String isSelectBank = caseBean.getIsSelectBank();
		String bankName = caseBean.getBankName();
		String bankId = caseBean.getBankId();
		String isConfirmSelectBank = caseBean.getIsConfirmSelectBank();
		String bankAccount = caseBean.getBankAccount();
		String bankAccountOwner = caseBean.getBankAccountOwner();
		String bankAccountType = caseBean.getBankAccountType();
		String subBankCode = caseBean.getSubBankCode();
		String isSave = caseBean.getIsSave();
		String isConfirmSave = caseBean.getIsConfirmSave();
		String existedBankCode = caseBean.getExistBankCode();
		String isConfirmSelectExistBank = caseBean.getIsConfirmSelectExistBank();
		String errorMsg = caseBean.getErrorMsg();
		String merchantId = caseBean.getMerchantId();
		String isParentChnSetPC = caseBean.getIsParentChnSetPC();

		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			logger.debug("选择的商户Id为：" + merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return; // 在没选中商户时，验证支付类型操作的新增、编辑、激活按钮的状态
			}
			page.clickAddPT();
			// 支付通道为弹框
			if (!StringUtils.isEmpty(errorMsg) && !errorMsg.contains("商户支付类型配置已存在") && !errorMsg.equals("请先选择支付通道")
					&& isParentChnSetPC.equals("true")) {
				pcAddPage.directSelectPayCenter(isSelectPCName, payCenterId, payCenterName);
				pcAddPage.directSelectPayType(isSelectPT, payTypeName, payTypeId);
			} else {
				pcAddPage.selectPayCenter(isSelectPCName, payCenterName, isConfirmSelectPC);
				pcAddPage.selectPayType(isSelectPT, payTypeName, isConfirmSelectPT);
			}
			
			 * //支付通道为下拉框 pcAddPage.selectPayCenter2(isSelectPCName,
			 * payCenterName); if(!StringUtils.isEmpty(errorMsg) &&
			 * !errorMsg.contains("商户支付类型配置已存在") && !errorMsg.equals("请先选择支付中心")
			 * && isParentChnSetPC.equals("true")){
			 * pcAddPage.directSelectPayType(isSelectPT, payTypeName,
			 * payTypeId); }else{ pcAddPage.selectPayType(isSelectPT,
			 * payTypeName, isConfirmSelectPT); }
			 

			pcAddPage.setClrRate(clrRate);
			pcAddPage.setMerchantSingleMinLimit(minLimit);
			pcAddPage.setMerchantSingleMaxLimit(maxLimit);
			pcAddPage.setMerchantThirdMchId();
			pcAddPage.setZhiFuBaoPID();
			if (errorMsg.contains("商户支付类型配置已存在")) {
				Assert.assertEquals(pcAddPage.checkNewAccountIsVisible(), false);
			} else {
				if (isSetNewClearAccount.equals("true")) {
					pcAddPage.selectBindClrBankType("新录入结算账户");
					if (StringUtils.isEmpty(errorMsg)) {
						pcAddPage.selectBank(isSelectBank, bankName, isConfirmSelectBank);
					} else {
						pcAddPage.directSelectBank(isSelectBank, bankName, bankId);
					}
					pcAddPage.setBankAccount(bankAccount);
					pcAddPage.setBankAccountOwner(bankAccountOwner);
					pcAddPage.selectBankAccountType(bankAccountType);
					pcAddPage.selectSubBankProvince();
					pcAddPage.setSubBnakName();
					pcAddPage.setSubBankPhone();
					pcAddPage.selectIdType();
					pcAddPage.setIdCode();

					pcAddPage.setSubBankCode(subBankCode);
				} else {
					pcAddPage.selectBindClrBankType("关联已有卡");
					pcAddPage.selectExistBank(existedBankCode, isConfirmSelectExistBank);
				}
			}
			if (!isParentChnSetPC.equals("true")) {
				Assert.assertEquals(pcAddPage.checkTipMsg(), true);
				pcAddPage.setParentClrRate(isParentChnSetPC, parentClrRate);
			}
			pcAddPage.lastOperate(isSave, isConfirmSave);
		} catch (MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}

		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}*/

	/*
	public void runEditPC(MerchantPCEditCaseBean caseBean) {
		logger.info("本次商户管理——商户进件管理支付类型配置编辑测试的测试数据是： " + caseBean);
		// 获取测试数据参数
		String thirdMchId = caseBean.getThirdMchId();
		String isClickFirstRowMerchant = caseBean.getIsClickFirstRowMerchant();
		String isClickFirstRowPT = caseBean.getIsClickFirstRowPT();
		String clrRate = caseBean.getClrRate();
		String minLimit = caseBean.getMinLimit();
		String maxLimit = caseBean.getMaxLimit();
		String isEdit = caseBean.getIsEdit();
		String isConfirmEdit = caseBean.getIsConfirmEdit();
		String errorMsg = caseBean.getErrorMsg();
		String merchantId = caseBean.getMerchantId();
		String payTypeName = caseBean.getPayTypeName();

		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return;
			}
			page.clickRowByPTName(isClickFirstRowPT, payTypeName);
			page.clickEditPT();
			Assert.assertEquals(pcEditPage.checkUneditable(), true);
			pcEditPage.editClrRate(clrRate);
			pcEditPage.editMerchantSingleMinLimit(minLimit);
			pcEditPage.editMerchantSingleMaxLimit(maxLimit);
			pcEditPage.editMerchantThirdMchId(thirdMchId);
			pcEditPage.editZhiFuBaoPID();
			pcEditPage.lastOperate(isEdit, isConfirmEdit);
		} catch (MerchantPayConfException | MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		logger.info("编辑成功！");
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}*/

	
	/*
	@Override
	public void runActivePC(MerchantPCActiveCaseBean caseBean) {
		logger.info("本次商户管理——支付类型激活测试使用的数据是： " + caseBean);
		// 获取测试数据参数
		String es = caseBean.getPreMchExamineStatus();
		String as = caseBean.getPreMchActiveStatus();
		String isClickFirstRowMerchant = caseBean.getIsClickFirstRowMerchant();
		String isClickFirstRowPT = caseBean.getIsClickFirstRowPT();
		String activateOperate = caseBean.getActiveOperate();
		String isConfirmActivateOperate = caseBean.getIsConfirmActiveOperate();
		String mchPCActivateStatus = caseBean.getMerchantPCActivateStatus();
		String errorMsg = caseBean.getErrorMsg();
		String payTypeName = caseBean.getPayTypeName();
		String merchantId = MerchantPCActiveTestData.getNeedMchId(es, as, mchPCActivateStatus);
		// 执行测试操作
		try {
			page.setMerchantId(merchantId);
			logger.info("使用的商户ID为：" + merchantId);
			page.clickSearch();
			page.clickMerchantFirstRowByMchId(isClickFirstRowMerchant, merchantId);
			if (!isClickFirstRowMerchant.equals("true")) {
				logger.warn("未选择商户，新增、编辑、激活按钮均不可点");
				Assert.assertEquals(page.checkNoSelectMerchantPTButtonsIsEnabled(), true);
				return;
			}
			page.clickRowByPTName(isClickFirstRowPT, payTypeName);
			if (!es.equals("1")) {// 所选商户为非审核通过状态
				logger.warn("商户未审核通过，激活按钮不可点击");
				Assert.assertEquals(page.checkPTAcitveButtonIsEnabled(), false);
				return;
			}
			page.clickAcitvePT();
			Assert.assertEquals(pcActivePage.checkPage(), true);
			pcActivePage.activateOperate(activateOperate, isConfirmActivateOperate, mchPCActivateStatus);
		} catch (MerchantException | MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		// if(activeOperate.equals("激活通过")){
		// } else if(activeOperate.equals("激活不通过")){
		// } else if(activeOperate.equals("冻结")){
		// }
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}
*/
/*
	@Override
	public void runAdd(CooperateAddCaseBean caseBean) {
		// TODO Auto-generated method stub

	}
*/
//	@Override
	public void runCooperateAdd(HashMap<String, String> params) {
		// TODO Auto-generated method stub

	}
	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Override public void runDetailPC(MerchantPCDetailCaseBean caseBean) {
	 * logger.info("本次商户管理——商户支付类型配置详情测试使用的测试数据是: " + caseBean); // 获取测试数据参数
	 * String isClickFirstRowMerchant = caseBean.getIsClickFirstRowMerchant();
	 * String isClickFirstRowPT = caseBean.getIsClickFirstRowPT();
	 * 
	 * String errorMsg = caseBean.getErrorMsg(); String needMerchantId =
	 * MerchantPCDetailTestData.getNeedMerchantId();
	 * 
	 * // 执行测试操作 try{ page.setMerchantId(needMerchantId); page.clickSearch();
	 * page.clickMerchantFirstRow(isClickFirstRowMerchant);
	 * if(!isClickFirstRowMerchant.equals("true")){ page.clickDetailPT(); }
	 * page.clickFirstPTRow(isClickFirstRowPT); page.clickDetailPT();
	 * Assert.assertEquals(pcDetailPage.checkMerchantPayConfDetailPage(), true);
	 * } catch(MerchantException | MerchantPayConfException e){
	 * logger.error(e.getMessage()); Assert.assertEquals(e.getMessage(),
	 * errorMsg); return; }
	 * 
	 * Assert.assertEquals(StringUtils.isEmpty(errorMsg), true); }
	 */
}
