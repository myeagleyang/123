package swiftpass.testcase.merchant;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.testcase.casebeans.MerchantAddCaseBean;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.Page.XClock;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.exceptions.MerchantException;
import swiftpass.page.exceptions.MerchantPayConfException;
import swiftpass.page.merchant.MerchantAAAPage;
import swiftpass.page.merchant.MerchantActivePage;
import swiftpass.page.merchant.MerchantAddPage;
import swiftpass.page.merchant.MerchantAuditPage;
import swiftpass.page.merchant.MerchantDetailPage;
import swiftpass.page.merchant.MerchantEditPage;
import swiftpass.page.merchant.MerchantPage;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.MerchantAAACaseBean;
import swiftpass.testcase.casebeans.MerchantActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantAuditCaseBean;
import swiftpass.testcase.casebeans.MerchantDetailCaseBean;
import swiftpass.testcase.casebeans.MerchantEditCaseBean;
import swiftpass.testcase.casebeans.MerchantSearchCaseBean;
import swiftpass.testcase.testdata.merchant.MerchantAAATestData;
import swiftpass.testcase.testdata.merchant.MerchantActiveTestData;
import swiftpass.testcase.testdata.merchant.MerchantAuditTestData;
import swiftpass.testcase.testdata.merchant.MerchantSearchTestData;
import swiftpass.testcase.testdata.merchant.XMerchantAAATestData;
import swiftpass.testcase.testdata.merchant.XMerchantActiveTestData;
import swiftpass.testcase.testdata.merchant.XMerchantAddTestData;
import swiftpass.testcase.testdata.merchant.XMerchantAuditTestData;
import swiftpass.utils.HTTPUtils;
import swiftpass.utils.services.SPServiceUrls;

public class MerchantTestCaseImpl extends TestCaseImpl implements MerchantTestCase {
	private MerchantPage page = new MerchantPage(driver);
	private MerchantAddPage addPage = new MerchantAddPage(driver);
	private MerchantEditPage editPage = new MerchantEditPage(driver);
	private MerchantAuditPage auditPage = new MerchantAuditPage(driver);
	private MerchantActivePage activePage = new MerchantActivePage(driver);
	private MerchantAAAPage aAApage = new MerchantAAAPage(driver);
	private MerchantDetailPage detailPage = new MerchantDetailPage(driver);

	public MerchantTestCaseImpl(WebDriver driver) {
		super(driver);
	}

	@Override
	public void searchMerchant(HashMap<String, String> params) {
		logger.info("本次商户管理——商户进件管理查询测试的数据参数是： " + params);
		// 获取测试数据参数
		String beginCT = params.get("beginCT"), endCT = params.get("endCT");
		String isSelectOrg = params.get("isSelectOrg"), orgNameOrIdItem = params.get("orgNameOrIdItem"),
				orgNameOrId = params.get("orgNameOrId"), isConfirmSelectOrg = params.get("isConfirmSelectOrg"),
				ACCEPT_ORG_ID = params.get("ACCEPT_ORG_ID"), ORG_NAME = params.get("ORG_NAME");
		String isSelectPCh = params.get("isSelectPCh"), pChNameOrIdItem = params.get("pChNameOrIdItem"),
				pChNameOrId = params.get("pChNameOrId"), isConfirmSelectPCh = params.get("isConfirmSelectPCh"),
				CHANNEL_ID = params.get("CHANNEL_ID"), CHANNEL_NAME = params.get("CHANNEL_NAME");
		String MERCHANT_NAME = params.get("MERCHANT_NAME");
		String MERCHANT_ID = params.get("MERCHANT_ID");
		String MERCHANT_TYPE = params.get("MERCHANT_TYPE");
		String EXAMINE_STATUS = params.get("EXAMINE_STATUS");
		String ACTIVATE_STATUS = params.get("ACTIVATE_STATUS");
		String isSelectIndustry = params.get("isSelectIndustry"), INDUSTR_ID = params.get("INDUSTR_ID"),
				INDUSTRY_NAME = params.get("INDUSTRY_NAME"),
				firstLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[0],
				secondLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[1],
				thirdLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[2],
				isConfirmSelectIndustry = params.get("isConfirmSelectIndustry");

		// 执行页面查询操作
		try {
			page.setBeginCreateTime(beginCT);
			page.setEndCreateTime(endCT);
			if (isSelectOrg.equals("true"))
				page.selectInstitution(isSelectOrg, orgNameOrIdItem, orgNameOrId, isConfirmSelectOrg);
			else
				page.directSetInstitutionByJS(ACCEPT_ORG_ID, ORG_NAME);
			if (isSelectPCh.equals("true"))
				page.selectChannel(isSelectPCh, pChNameOrIdItem, pChNameOrId, isConfirmSelectPCh);
			else
				page.directSetChannelByJS(CHANNEL_ID, CHANNEL_NAME);
			page.setMerchantName(MERCHANT_NAME);
			page.setMerchantId(MERCHANT_ID);
			page.selectMerchantType(MERCHANT_TYPE);
			page.selectAuditStatus(EXAMINE_STATUS);
			page.selectActiveStatus(ACTIVATE_STATUS);
			if (isSelectIndustry.equals("true"))
				page.selectIndustry(isSelectIndustry, firstLevelName, secondLevelName, thirdLevelName,
						isConfirmSelectIndustry);
			else
				page.directSetIndustry(INDUSTR_ID, INDUSTRY_NAME);

			page.clickSearch(); // 触发商户查询

		} catch (MerchantException | MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(false, true);
		}

		// 验证查询测试结果
		Assert.assertEquals(page.isNoteMsgCorrect(), true);
		String actual = page.getRecordCount();
		String expectedCount = MerchantSearchTestData.expectedCount(params);
		Assert.assertEquals(actual, expectedCount);
	}

	@Override // 商户新增，新增成功后商户数+1
	public void addMerchant(HashMap<String, String> params) {
		logger.info("本次商户进件管理——商户新增使用的测试数据是： " + params);
		// 获取新增测试数据参数
		String big_or_normal = params.get("big_or_normal");
		String isSelectChannel = params.get("isSelectChannel"), channelItem = params.get("channelItem"),
				chnNameOrId = params.get("chnNameOrId"), isConfirmSelectChannel = params.get("isConfirmSelectChannel");
		String merchantName = params.get("merchantName");
		String shortName = params.get("shortName");
		String merchantType = params.get("merchantType");
		String isSelectEmp = params.get("isSelectEmp"), empItem = params.get("empItem"),
				empNameOrId = params.get("empNameOrId"), empName = params.get("empName"),
				isConfirmSelectEmp = params.get("isConfirmSelectEmp");
		String currency = params.get("currency");
		String isSelectIndustry = params.get("isSelectIndustry"), industryIdChain = params.get("industryIdChain"),
				industryNameChain = params.get("industryNameChain"),
				firstLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[0] : "",
				secondLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[1] : "",
				thirdLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[2] : "",
				isConfirmSelectIndustry = params.get("isConfirmSelectIndustry");
		String province = params.get("province"), city = params.get("city");
		String address = params.get("address");
		String tel = params.get("tel");
		String email = params.get("email");
		String website = params.get("website");
		String principal = params.get("principal"), idCode = params.get("idCode"),
				principalPhone = params.get("principalPhone");
		String serviceTel = params.get("serviceTel");
		String fax = params.get("tel");
		String thrMchId = params.get("thrMchId");
		String isClickCheckbox = params.get("isClickCheckbox");
		String isSelectBank = params.get("isSelectBank"), bankName = params.get("bankName"),
				isConfirmSelectBank = params.get("isConfirmSelectBank");
		String account = params.get("account");
		String accountOwner = params.get("accountOwner");
		String accountType = params.get("accountType");
		String subBankName = params.get("subBankName");
		String idType = params.get("idType");
		String subBankNO = params.get("subBankNO");
		String isSave = params.get("isSave"), isConfirmSave = params.get("isConfirmSave");
		String message = params.get("message");

		// 执行新增测试操作
		String preCount = null;
		try {
			page.clickSearch();
			preCount = page.getRecordCount();
			page.clickAddMerchant(big_or_normal);
			if (StringUtils.isEmpty(message))
				addPage.selectChannel(isSelectChannel, channelItem, chnNameOrId, isConfirmSelectChannel,
						params.get("chnId"));
			else
				addPage.directSetParentChannel(params.get("chnId"), params.get("chnName"));
			addPage.setName(merchantName);
			addPage.setShortName(shortName);
			addPage.selectMchType(merchantType);
			addPage.selectEmployee(isSelectEmp, empItem, empNameOrId, empName, isConfirmSelectEmp);
			addPage.selectCurrency(currency);
			if (StringUtils.isEmpty(message))
				addPage.selectIndustry(isSelectIndustry, firstLevel, secondLevel, thirdLevel, isConfirmSelectIndustry);
			else
				addPage.directSetIndustry((!StringUtils.isEmpty(industryIdChain)) ? industryIdChain.split(">")[2] : "",
						!StringUtils.isEmpty(industryNameChain) ? industryNameChain.replace(">", "-") : "");
			addPage.selectProvince(province, city);
			addPage.setAddress(address);
			addPage.setTel(tel);
			addPage.setEmail(email);
			addPage.setWebsite(website);
			addPage.setPrincipal(principal);
			addPage.setPrincipalIdCard(idCode);
			addPage.setPrincipalPhone(principalPhone);
			addPage.setServiceTel(serviceTel);
			addPage.setFax(fax);
			addPage.setThirdMchId(thrMchId);
			if (isClickCheckbox.equals("true")) {
				addPage.clickCheckbox();
				if (StringUtils.isEmpty(message))
					addPage.selectBank(isSelectBank, bankName, isConfirmSelectBank);
				else
					addPage.directSetBank(params.get("bankId"), params.get("bankName"));
				addPage.setAccount(account);
				addPage.setAccountOwner(accountOwner);
				addPage.selectAccountType(accountType);
				addPage.setSubBankName(subBankName);
				addPage.setBankPhone(tel);
				addPage.selectAndSetIdCard(idType, idCode);
				addPage.selectSubBankProvince(province, city);
				addPage.setSubBankNO(subBankNO);
			}

			addPage.lastMerchantAddOperate(isSave, isConfirmSave);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		// 验证结果
		XClock.stop(1.5);
		String postCount = page.getRecordCount();
		Assert.assertEquals(postCount, "" + (Integer.parseInt(preCount) + 1));
		logger.info(message);
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	public void addMerchant(MerchantAddCaseBean merchant) {

	}

	@SuppressWarnings("unused")
	@Override
	public void editMerchant(HashMap<String, String> params) {
		logger.info("本次商户管理——商户进件管理编辑的测试数据是： " + params);
		// 获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String merchantName = params.get("merchantName"), shortName = params.get("shortName"),
				currency = params.get("currency"), isEditIndustry = params.get("isEditIndustry"),
				isConfirmEditIndustry = params.get("isConfirmEditIndustry"), industry = params.get("industry"),
				industryIdChain = params.get("industryIdChain"),
				industryId = StringUtils.isEmpty(industryIdChain) ? "" : industryIdChain.split(">")[2],
				industryNameChain = params.get("industryNameChain"),
				industryNameChain_ = StringUtils.isEmpty(industryNameChain) ? "" : industryNameChain.replace(">", "-"),
				address = params.get("address"), tel = params.get("tel"), email = params.get("email"),
				website = params.get("website"), principal = params.get("principal"), idCode = params.get("idCode"),
				principalPhone = params.get("principalPhone"), serviceTel = params.get("serviceTel"),
				fax = params.get("fax"),
				// dealType = params.get("dealType"),
				thrMchId = params.get("thrMchId"), isMerchantNoAuditPass = params.get("isMerchantNoAuditPass"),
				isEdit = params.get("isEdit"), isConfirmEdit = params.get("isConfirmEdit"),
				message = params.get("message"), isStore = params.get("isStore");
		HashMap<String, String> preEditInfo = null;
		HashMap<String, String> preEditInfoInDB = null;
		// 执行操作
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(ArrayUtils.toArray(text[0]));
			if (isStore.equals("true")) {
				Assert.assertEquals(page.isUneditable(), true);
			}
			page.clickEdit();
			if (StringUtils.isEmpty(message)) {
				Assert.assertEquals(editPage.checkUneditablePart(), true); // 检查页面中不可编辑的部分元素。
				preEditInfo = editPage.getMerchantInfo();
				preEditInfoInDB = MerchantDBOperations.getEditMerchantExpectedPageInfo(text[0]);
				for (String key : preEditInfo.keySet()) {
					logger.debug(
							"比对的Key: " + key + " ________ " + preEditInfo.get(key) + " : " + preEditInfoInDB.get(key));
					Assert.assertEquals(preEditInfo.get(key), preEditInfoInDB.get(key));
				}
			}
			editPage.editMerchantName(merchantName);
			editPage.editMerchantShortName(shortName);
			editPage.editSelectCurrency(currency);
			if (StringUtils.isEmpty(message)) {
				String[] industryNames = industryNameChain.split(">");
				editPage.editIndustry(isEditIndustry, industryNames[0], industryNames[1], industryNames[2],
						isConfirmEditIndustry);
			} else {
				editPage.directEditIndustryByJS(industryNameChain_, industryId);
			}
			editPage.editAddress(address);
			editPage.editTel(tel);
			editPage.editEmail(email);
			editPage.editWebsite(website);
			editPage.editPrincipal(principal);
			editPage.editPrincipalIdCard(idCode);
			editPage.editPrincipalPhone(principalPhone);
			editPage.editServiceTel(serviceTel);
			editPage.editFax(fax);
			editPage.editThirdMchId(thrMchId);

			editPage.lastOperate(isEdit, isConfirmEdit);
		} catch (MerchantException ex) {
			logger.error(ex);
			Assert.assertEquals(ex.getMessage(), message);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals(true, false);
		}

		page.clickMerchantRowByText(ArrayUtils.toArray(text[0]));
		page.clickEdit();
		HashMap<String, String> postEdit = editPage.getMerchantInfo();
		HashMap<String, String> postEditInDB = MerchantDBOperations.getEditMerchantExpectedPageInfo(text[0]);

		Assert.assertEquals(postEdit.get("merchantName"), params.get("merchantName"));
		for (String key : postEdit.keySet()) {
			logger.debug("编辑成功后商户信息对比：" + key + "   " + postEdit.get(key) + " : " + postEditInDB.get(key));
			Assert.assertEquals(postEdit.get(key), postEditInDB.get(key));
		}
		if (isMerchantNoAuditPass.equals("false")) {
			System.err.println(postEditInDB.get("auditStatus"));
			Assert.assertEquals(postEditInDB.get("auditStatus"), ExamineStatus.NEEDAGAIN.getSCode());
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void auditMerchant(HashMap<String, String> params) {
		// 预处理测试用例
		params = MerchantAuditTestData.preCheckProcess(params);
		logger.info("本次商户管理——商户进件管理审核测试使用的测试数据是： " + params);
		// 获取测试数据参数

		String[] text = params.get("TEXT").split("-");
		String auditRemark = params.get("auditRemark"), auditOperate = params.get("auditOperate"),
				isConfirmOperate = params.get("isConfirmOperate"), message = params.get("message");

		// 执行测试操作：1.检查页面 2.检查审核有效后的信息变更
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickAudit();
			Assert.assertEquals(auditPage.checkAuditPage(), true);
			HashMap<String, String> pageInfo = auditPage.merchantInfoOnPage();
			HashMap<String, String> DBInfo = MerchantDBOperations.merchantAuditExpectedInfo(text[0]);
			for (String key : DBInfo.keySet()) {
				// logger.debug(key + ": - page " + pageInfo.get(key) + ", - DB
				// " + DBInfo.get(key));
				Assert.assertEquals(pageInfo.get(key), DBInfo.get(key));
			}
			auditPage.setAuditRemark(auditRemark);

			auditPage.lastOperate(auditOperate, isConfirmOperate);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		HashMap<String, String> mch = MerchantDBOperations.getMerchant(text[0]);
		if (auditOperate.equals("审核通过")) {
			Assert.assertEquals(mch.get("EXAMINE_STATUS"), ExamineStatus.PASS.getSCode());
		} else if (auditOperate.equals("审核不通过")) {
			Assert.assertEquals(mch.get("EXAMINE_STATUS"), ExamineStatus.STOP.getSCode());
		}

		Assert.assertEquals(message, "");
	}

	@Override
	public void activeMerchant(HashMap<String, String> params) {
		// 预处理测试用例
		params = MerchantActiveTestData.preCheckProcessCase(params);
		logger.info("本次商户管理——商户进件管理激活测试使用的测试数据是： " + params);
		// 获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String activateStatus = params.get("M_ActivateStatus"), activeRemark = params.get("activeRemark"),
				activeOperate = params.get("activeOperate"), isConfirmOperate = params.get("isConfirmOperate"),
				message = params.get("message");

		// 执行测试操作
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickActive();
			Assert.assertEquals(activePage.checkAcitvePage(), true);
			HashMap<String, String> pageInfo = activePage.merchantInfoOnPage();
			HashMap<String, String> dbInfo = MerchantDBOperations.merchantAuditExpectedInfo(text[0]);
			for (String key : dbInfo.keySet()) {
				logger.debug(key + ": page -- " + pageInfo.get(key) + ", DB -- " + dbInfo.get(key));
				Assert.assertEquals(pageInfo.get(key), dbInfo.get(key));
			}
			if (activateStatus.equals(ActivateStatus.PASS.getSCode()))
				Assert.assertEquals(activePage.isPassStopButtonsDisappear(), true);
			activePage.setActiveRemark(activeRemark);

			activePage.lastOperate(activeOperate, isConfirmOperate);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		HashMap<String, String> postDBInfo = MerchantDBOperations.getMerchant(text[0]);
		String pas = postDBInfo.get("ACTIVATE_STATUS");
		if (activeOperate.equals("激活通过")) {
			Assert.assertEquals(pas, ActivateStatus.PASS.getSCode());
		} else if (activeOperate.equals("激活不通过")) {
			Assert.assertEquals(pas, ActivateStatus.FAIL.getSCode());
		} else if (activeOperate.equals("冻结")) {
			Assert.assertEquals(pas, ActivateStatus.FREEZE.getSCode());
		}

		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void auditAndActiveMerchant(HashMap<String, String> params) {
		// 预处理测试数据
		params = MerchantAAATestData.preCheckProcessCase(params);

		logger.info("本次商户管理——商户进件管理审核并激活测试使用的测试数据是： " + params);
		// 获取测试数据参数
		String[] text = params.get("TEXT").split("-");

		String activateStatusRemark = params.get("activateStatusRemark"), aaOperate = params.get("aaOperate"),
				isConfirmOperate = params.get("isConfirmOperate"), message = params.get("message");

		// 执行测试操作
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickAuditAndActive();
			Assert.assertEquals(aAApage.checkAAAPage(), true);
			aAApage.setAAARemark(activateStatusRemark);

			aAApage.lastOperate(aaOperate, isConfirmOperate);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}

		HashMap<String, String> postMS = MerchantDBOperations.getMerchant(text[0]);
		String es = postMS.get("EXAMINE_STATUS");
		String as = postMS.get("ACTIVATE_STATUS");
		if (aaOperate.equals("通过")) {
			Assert.assertEquals(es, ExamineStatus.PASS.getSCode());
			Assert.assertEquals(as, ActivateStatus.PASS.getSCode());
		} else if (aaOperate.equals("不通过")) {
			Assert.assertEquals(es, ExamineStatus.STOP.getSCode());
			Assert.assertEquals(as, ActivateStatus.FAIL.getSCode());
		}

		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void detailMerchant(HashMap<String, String> params) {
		logger.info("本次商户管理——商户进件管理详情测试使用的测试数据是: " + params);
		// 获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String message = params.get("message");

		// 开始执行测试操作
		HashMap<String, String> detailOnPage = null;
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickDetail();
			Assert.assertEquals(detailPage.checkDetailPage(), true);
			detailOnPage = detailPage.merchantInfoOnPage();
			detailPage.closeDetailPage();
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		HashMap<String, String> detailInDB = MerchantDBOperations.merchantDetailExpectedPageInfo(text[0]);
		for (String key : detailInDB.keySet()) {
			logger.debug(
					"detailOnPage: " + key + " -- " + detailOnPage.get(key) + ", detailInDB: " + detailInDB.get(key));
			Assert.assertEquals(detailOnPage.get(key), detailInDB.get(key));
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);

	}

	@Override
	public void exportMerchant() {
		// TODO Auto-generated method stub

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * sunhaojie 2017-03-31
	 */
	//---------------------------------CaseBean重构----------------------------
	@Override
	public void runSearch(MerchantSearchCaseBean searchBean) {
		logger.info("本次商户管理——商户进件管理查询测试的数据参数是： " + searchBean);
		// 获取测试数据参数
		String beginCT = searchBean.getBeginCT(), endCT = searchBean.getEndCT();
		String isSelectOrg = searchBean.getIsConfirmSelectOrg(), orgNameOrIdItem = searchBean.getOrgNameOrIdItem(),
				orgNameOrId = searchBean.getOrgNameOrId(), isConfirmSelectOrg = searchBean.getIsConfirmSelectOrg(),
				ACCEPT_ORG_ID = searchBean.getACCEPT_ORG_ID(), ORG_NAME = searchBean.getORG_NAME();
		String isSelectPCh = searchBean.getIsSelectPCh(), pChNameOrIdItem = searchBean.getpChNameOrIdItem(),
				pChNameOrId = searchBean.getpChNameOrId(), isConfirmSelectPCh = searchBean.getIsConfirmSelectPCh(),
				CHANNEL_ID = searchBean.getMERCHANT_ID(), CHANNEL_NAME = searchBean.getMERCHANT_NAME();
		String MERCHANT_NAME = searchBean.getMERCHANT_NAME();
		String MERCHANT_ID = searchBean.getMERCHANT_ID();
		String MERCHANT_TYPE = searchBean.getMERCHANT_TYPE();
		String EXAMINE_STATUS = searchBean.getEXAMINE_STATUS();
		String ACTIVATE_STATUS = searchBean.getACTIVATE_STATUS();
		String isSelectIndustry = searchBean.getIsConfirmSelectIndustry(), INDUSTR_ID = searchBean.getINDUSTR_ID(),
				INDUSTRY_NAME = searchBean.getINDUSTRY_NAME(),
				firstLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[0],
				secondLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[1],
				thirdLevelName = StringUtils.isEmpty(INDUSTRY_NAME) ? "" : INDUSTRY_NAME.split("-")[2],
				isConfirmSelectIndustry = searchBean.getIsConfirmSelectIndustry();

		// 执行页面查询操作
		try {
			page.setBeginCreateTime(beginCT);
			page.setEndCreateTime(endCT);
			if (isSelectOrg.equals("true"))
				page.selectInstitution(isSelectOrg, orgNameOrIdItem, orgNameOrId, isConfirmSelectOrg);
			else
				page.directSetInstitutionByJS(ACCEPT_ORG_ID, ORG_NAME);
			if (isSelectPCh.equals("true"))
				page.selectChannel(isSelectPCh, pChNameOrIdItem, pChNameOrId, isConfirmSelectPCh);
			else
				page.directSetChannelByJS(CHANNEL_ID, CHANNEL_NAME);
			page.setMerchantName(MERCHANT_NAME);
			page.setMerchantId(MERCHANT_ID);
			page.selectMerchantType(MERCHANT_TYPE);
			page.selectAuditStatus(EXAMINE_STATUS);
			page.selectActiveStatus(ACTIVATE_STATUS);
			if (isSelectIndustry.equals("true"))
				page.selectIndustry(isSelectIndustry, firstLevelName, secondLevelName, thirdLevelName,
						isConfirmSelectIndustry);
			else
				page.directSetIndustry(INDUSTR_ID, INDUSTRY_NAME);

			page.clickSearch(); // 触发商户查询

		} catch (MerchantException | MerchantPayConfException e) {
			logger.error(e);
			Assert.assertEquals(false, true);
		}

		// 验证查询测试结果
		Assert.assertEquals(page.isNoteMsgCorrect(), true);
		String actual = page.getRecordCount();
		String expectedCount = MerchantSearchTestData.expectedCount(searchBean);
		Assert.assertEquals(actual, expectedCount);

	}

	@Override
	public void runAdd(MerchantAddCaseBean merchant) {
		logger.info("本次商户进件管理——商户新增使用的测试数据是： " + merchant);
		// 获取新增测试数据参数
		String big_or_normal = merchant.getBig_or_normal();
		String isSelectChannel = merchant.getIsSelectChannel(), channelItem = merchant.getChannelItem(),
				chnNameOrId = merchant.getChnNameOrId(), isConfirmSelectChannel = merchant.getIsConfirmSelectChannel();
		String merchantName = merchant.getMerchantName();
		String shortName = merchant.getShortName();
		String merchantType = merchant.getMerchantType();
		String isSelectEmp = merchant.getIsConfirmSelectEmp(), empItem = merchant.getEmpItem(),
				empNameOrId = merchant.getEmpNameOrId(), empName = merchant.getEmpName(),
				isConfirmSelectEmp = merchant.getIsConfirmSelectEmp();
		String currency = merchant.getCurrency();
		String isSelectIndustry = merchant.getIsConfirmSelectIndustry(),
				industryIdChain = merchant.getIndustryIdChain(), industryNameChain = merchant.getIndustryNameChain(),
				firstLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[0] : "",
				secondLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[1] : "",
				thirdLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[2] : "",
				isConfirmSelectIndustry = merchant.getIsConfirmSelectIndustry();
		String province = merchant.getProvince(), city = merchant.getCity();
		String address = merchant.getAddress();
		String tel = merchant.getTel();
		String email = merchant.getEmail();
		String website = merchant.getWebsite();
		String principal = merchant.getPrincipal(), idCode = merchant.getIdCode(),
				principalPhone = merchant.getPrincipalPhone();
		String serviceTel = merchant.getServiceTel();
		String fax = merchant.getTel();
		String thrMchId = merchant.getThrMchId();
		String isClickCheckbox = merchant.getIsClickCheckbox();
		String isSelectBank = merchant.getIsSelectBank(), bankName = merchant.getBankName(),
				isConfirmSelectBank = merchant.getIsConfirmSelectBank();
		String account = merchant.getAccount();
		String accountOwner = merchant.getAccountOwner();
		String accountType = merchant.getAccountType();
		String subBankName = merchant.getSubBankName();
		String idType = merchant.getIdType();
		String subBankNO = merchant.getSubBankNO();
		String isSave = merchant.getIsSave(), isConfirmSave = merchant.getIsConfirmSave();
		String message = merchant.getMessage();

		// 执行新增测试操作
		String preCount = null;
		try {
			page.clickSearch();
			preCount = page.getRecordCount();
			page.clickAddMerchant(big_or_normal);
			if (StringUtils.isEmpty(message))
				addPage.selectChannel(isSelectChannel, channelItem, chnNameOrId, isConfirmSelectChannel,
						merchant.getChnId());
			else
				addPage.directSetParentChannel(merchant.getChnId(), merchant.getChnName());
			addPage.setName(merchantName);
			addPage.setShortName(shortName);
			addPage.selectMchType(merchantType);
			addPage.selectEmployee(isSelectEmp, empItem, empNameOrId, empName, isConfirmSelectEmp);
			addPage.selectCurrency(currency);
			if (StringUtils.isEmpty(message))
				addPage.selectIndustry(isSelectIndustry, firstLevel, secondLevel, thirdLevel, isConfirmSelectIndustry);
			else
				addPage.directSetIndustry((!StringUtils.isEmpty(industryIdChain)) ? industryIdChain.split(">")[2] : "",
						!StringUtils.isEmpty(industryNameChain) ? industryNameChain.replace(">", "-") : "");
			addPage.selectProvince(province, city);
			addPage.setAddress(address);
			addPage.setTel(tel);
			addPage.setEmail(email);
			addPage.setWebsite(website);
			addPage.setPrincipal(principal);
			addPage.setPrincipalIdCard(idCode);
			addPage.setPrincipalPhone(principalPhone);
			addPage.setServiceTel(serviceTel);
			addPage.setFax(fax);
			addPage.setThirdMchId(thrMchId);
			if (isClickCheckbox.equals("true")) {
				//addPage.clickCheckbox();       修改于2017-04-13,默认为选中状态，无需再次点击.
				if (StringUtils.isEmpty(message))
					addPage.selectBank(isSelectBank, bankName, isConfirmSelectBank);
				else
					addPage.directSetBank(merchant.getBankId(), merchant.getBankName());
				addPage.setAccount(account);
				addPage.setAccountOwner(accountOwner);
				addPage.selectAccountType(accountType);
				addPage.setSubBankName(subBankName);
				addPage.setBankPhone(tel);
				addPage.selectAndSetIdCard(idType, idCode);
				addPage.selectSubBankProvince(province, city);
				addPage.setSubBankNO(subBankNO);
			}
			addPage.lastMerchantAddOperate(isSave, isConfirmSave);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		// 验证结果
		XClock.stop(1.5);
		String postCount = page.getRecordCount();
		Assert.assertEquals(postCount, "" + (Integer.parseInt(preCount) + 1));
		logger.info(message);
		Assert.assertEquals(StringUtils.isEmpty(message), true);

	}
	
	
	public void runAdd_xy(MerchantAddCaseBean merchant){
		logger.info("本次商户进件管理——商户新增使用的测试数据是： " + merchant);
		// 获取新增测试数据参数
		String big_or_normal = merchant.getBig_or_normal();
		String isSelectChannel = merchant.getIsSelectChannel(), channelItem = merchant.getChannelItem(),
				chnNameOrId = merchant.getChnNameOrId(), isConfirmSelectChannel = merchant.getIsConfirmSelectChannel();
		String merchantName = merchant.getMerchantName();
		String shortName = merchant.getShortName();
		String merchantType = merchant.getMerchantType();
		String isSelectEmp = merchant.getIsConfirmSelectEmp(), empItem = merchant.getEmpItem(),
				empNameOrId = merchant.getEmpNameOrId(), empName = merchant.getEmpName(),
				isConfirmSelectEmp = merchant.getIsConfirmSelectEmp();
		String currency = merchant.getCurrency();
		String isSelectIndustry = merchant.getIsConfirmSelectIndustry(),
				industryIdChain = merchant.getIndustryIdChain(), industryNameChain = merchant.getIndustryNameChain(),
				firstLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[0] : "",
				secondLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[1] : "",
				thirdLevel = (!StringUtils.isEmpty(industryNameChain)) ? industryNameChain.split(">")[2] : "",
				isConfirmSelectIndustry = merchant.getIsConfirmSelectIndustry();
		String province = merchant.getProvince(), city = merchant.getCity();
		String address = merchant.getAddress();
		String tel = merchant.getTel();
		String email = merchant.getEmail();
		String website = merchant.getWebsite();
		String principal = merchant.getPrincipal(), idCode = merchant.getIdCode(),
				principalPhone = merchant.getPrincipalPhone();
		String serviceTel = merchant.getServiceTel();
		String fax = merchant.getTel();
		String thrMchId = merchant.getThrMchId();
		String isClickCheckbox = merchant.getIsClickCheckbox();
		String isSelectBank = merchant.getIsSelectBank(), bankName = merchant.getBankName(),
				isConfirmSelectBank = merchant.getIsConfirmSelectBank();
		String account = merchant.getAccount();
		String accountOwner = merchant.getAccountOwner();
		String accountType = merchant.getAccountType();
		String subBankName = merchant.getSubBankName();
		String idType = merchant.getIdType();
		String subBankNO = merchant.getSubBankNO();
		String isSave = merchant.getIsSave(), isConfirmSave = merchant.getIsConfirmSave();
		String message = merchant.getMessage();

		// 执行新增测试操作
		String preCount = null;
		try {
			page.clickSearch();
			preCount = page.getRecordCount();
			page.clickAddMerchant(big_or_normal);
			if (StringUtils.isEmpty(message))
				addPage.selectChannel(isSelectChannel, channelItem, chnNameOrId, isConfirmSelectChannel,
						merchant.getChnId());
			else
				addPage.directSetParentChannel(merchant.getChnId(), merchant.getChnName());
			addPage.setName(merchantName);
			addPage.setShortName(shortName);
			addPage.selectMchType(merchantType);
			addPage.selectEmployee(isSelectEmp, empItem, empNameOrId, empName, isConfirmSelectEmp);
			addPage.selectCurrency(currency);
			if (StringUtils.isEmpty(message))
				addPage.selectIndustry(isSelectIndustry, firstLevel, secondLevel, thirdLevel, isConfirmSelectIndustry);
			else
				addPage.directSetIndustry((!StringUtils.isEmpty(industryIdChain)) ? industryIdChain.split(">")[2] : "",
						!StringUtils.isEmpty(industryNameChain) ? industryNameChain.replace(">", "-") : "");
			addPage.selectProvince(province, city);
			addPage.setAddress(address);
			addPage.setTel(tel);
			addPage.setEmail(email);
			addPage.setWebsite(website);
			addPage.setPrincipal(principal);
			addPage.setPrincipalIdCard(idCode);
			addPage.setPrincipalPhone(principalPhone);
			addPage.setServiceTel(serviceTel);
			addPage.setFax(fax);
			addPage.setThirdMchId(thrMchId);
			if (isClickCheckbox.equals("true")) {
				addPage.clickCheckbox();
				if (StringUtils.isEmpty(message)){
					addPage.selectBank_xy(isSelectBank, bankName, isConfirmSelectBank);
					//merchant.setIsInsideAccount("true");
				}else
				
				addPage.directSetBank(merchant.getBankId(), merchant.getBankName());
				addPage.setAccount(account);
				addPage.setAccountOwner(accountOwner);
				addPage.selectAccountType(accountType);
				addPage.setSubBankName(subBankName);
				addPage.setBankPhone(tel);
				addPage.selectAndSetIdCard(idType, idCode);
				addPage.selectSubBankProvince(province, city);
				addPage.setSubBankNO(subBankNO);
				addPage.selectInsideAccount();
			}
			addPage.lastMerchantAddOperate(isSave, isConfirmSave);
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		// 验证结果
		XClock.stop(1.5);
		String postCount = page.getRecordCount();
		Assert.assertEquals(postCount, "" + (Integer.parseInt(preCount) + 1));
		logger.info(message);
	}

	@Override
	public void checkPhone(MerchantAddCaseBean caseBean) {
		logger.info("本次商户进件管理->手机号码段校验使用的测试数据是： " + caseBean);
		String SUCCESS_MSG = "{\"success\":true,\"msg\":\"添加成功！\",\"status\":0}";
		String FAIL_MSG = "{\"success\":false,\"errorCode\":\"merchant.principalMobile.format.error\",\"msg\":\"手机号码格式不正确\",\"status\":0}";
		if (caseBean != null && caseBean.getPhoneType().equals("0")) {
			String actual_normal = HTTPUtils.executePostRequest(SPServiceUrls.MERCHANT_ADD_URL,
					XMerchantAddTestData.getMerchantByPhone(caseBean.getPrincipalPhone())).replaceAll("\r|\n", "");
			Assert.assertEquals(actual_normal, SUCCESS_MSG);
			logger.info(actual_normal);
		} else if (caseBean != null && caseBean.getPhoneType().equals("1")) {
			String actual_exception = HTTPUtils.executePostRequest(SPServiceUrls.MERCHANT_ADD_URL,
					XMerchantAddTestData.getMerchantByPhone(caseBean.getPrincipalPhone())).replaceAll("\r|\n", "");
			Assert.assertEquals(actual_exception, FAIL_MSG);
			logger.info(actual_exception);
		}
	}

	@Override
	public void runEdit(MerchantEditCaseBean caseBean) {
		logger.info("本次商户管理——商户进件管理编辑的测试数据是： " + caseBean);
		// 获取测试数据参数
		String[] text =caseBean.getTEXT().split("-");
		String merchantName = caseBean.getMerchantName(), shortName = caseBean.getShortName(),
				currency = caseBean.getCurrency(), isEditIndustry = caseBean.getIsEditIndustry(),
				isConfirmEditIndustry = caseBean.getIsConfirmSelectIndustry(), industry = caseBean.getIndustry(),
				industryIdChain = caseBean.getIndustryIdChain(),
				industryId = StringUtils.isEmpty(industryIdChain) ? "" : industryIdChain.split(">")[2],
				industryNameChain = caseBean.getIndustryNameChain(),
				industryNameChain_ = StringUtils.isEmpty(industryNameChain) ? "" : industryNameChain.replace(">", "-"),
				address = caseBean.getAddress(), tel = caseBean.getTel(), email = caseBean.getEmail(),
				website = caseBean.getWebsite(), principal = caseBean.getPrincipal(), idCode = caseBean.getIdCode(),
				principalPhone = caseBean.getPrincipalPhone(), serviceTel = caseBean.getServiceTel(),
				fax = caseBean.getFax(),
				// dealType = params.get("dealType"),
				thrMchId = caseBean.getThrMchId(), isMerchantNoAuditPass = caseBean.getIsMerchantNoAuditPass(),
				isEdit = caseBean.getIsEdit(), isConfirmEdit = caseBean.getIsConfirmEdit(),
				message = caseBean.getMessage(), isStore =caseBean.getIsStore();
		HashMap<String, String> preEditInfo = null;
		HashMap<String, String> preEditInfoInDB = null;
		// 执行操作
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(ArrayUtils.toArray(text[0]));
			if (isStore.equals("true")) {
				Assert.assertEquals(page.isUneditable(), true);
			}
			page.clickEdit();
			if (StringUtils.isEmpty(message)) {
				Assert.assertEquals(editPage.checkUneditablePart(), true); // 检查页面中不可编辑的部分元素。
				preEditInfo = editPage.getMerchantInfo();
				preEditInfoInDB = MerchantDBOperations.getEditMerchantExpectedPageInfo(text[0]);
				for (String key : preEditInfo.keySet()) {
					logger.debug(
							"比对的Key: " + key + " ________ " + preEditInfo.get(key) + " : " + preEditInfoInDB.get(key));
					Assert.assertEquals(preEditInfo.get(key), preEditInfoInDB.get(key));
				}
			}
			editPage.editMerchantName(merchantName);
			editPage.editMerchantShortName(shortName);
			editPage.editSelectCurrency(currency);
			if (StringUtils.isEmpty(message)) {
				String[] industryNames = industryNameChain.split(">");
				editPage.editIndustry(isEditIndustry, industryNames[0], industryNames[1], industryNames[2],
						isConfirmEditIndustry);
			} else {
				editPage.directEditIndustryByJS(industryNameChain_, industryId);
			}
			editPage.editAddress(address);
			editPage.editTel(tel);
			editPage.editEmail(email);
			editPage.editWebsite(website);
			editPage.editPrincipal(principal);
			editPage.editPrincipalIdCard(idCode);
			editPage.editPrincipalPhone(principalPhone);
			editPage.editServiceTel(serviceTel);
			editPage.editFax(fax);
			editPage.editThirdMchId(thrMchId);

			editPage.lastOperate(isEdit, isConfirmEdit);
		} catch (MerchantException ex) {
			logger.error(ex);
			Assert.assertEquals(ex.getMessage(), message);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals(true, false);
		}

		page.clickMerchantRowByText(ArrayUtils.toArray(text[0]));
		page.clickEdit();
		HashMap<String, String> postEdit = editPage.getMerchantInfo();
		HashMap<String, String> postEditInDB = MerchantDBOperations.getEditMerchantExpectedPageInfo(text[0]);

		Assert.assertEquals(postEdit.get("merchantName"), caseBean.getMerchantName());
		for (String key : postEdit.keySet()) {
			logger.debug("编辑成功后商户信息对比：" + key + "   " + postEdit.get(key) + " : " + postEditInDB.get(key));
			Assert.assertEquals(postEdit.get(key), postEditInDB.get(key));
		}
		if (isMerchantNoAuditPass.equals("false")) {
			System.err.println(postEditInDB.get("auditStatus"));
			Assert.assertEquals(postEditInDB.get("auditStatus"), ExamineStatus.NEEDAGAIN.getSCode());
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void runAudit(MerchantAuditCaseBean caseBean) {
		// 预处理测试用例
		caseBean = XMerchantAuditTestData.preCheckProcess(caseBean);
				logger.info("本次商户管理——商户进件管理审核测试使用的测试数据是： " + caseBean);
				// 获取测试数据参数

				String[] text = caseBean.getTEXT().split("-");
				String auditRemark = caseBean.getAuditRemark(), auditOperate =caseBean.getAuditOperate(),
						isConfirmOperate = caseBean.getIsConfirmOperate(), message = caseBean.getMessage();

				// 执行测试操作：1.检查页面 2.检查审核有效后的信息变更
				try {
					page.setMerchantId(text[0]);
					page.clickSearch();
					page.clickMerchantRowByText(text);
					page.clickAudit();
					Assert.assertEquals(auditPage.checkAuditPage(), true);
					HashMap<String, String> pageInfo = auditPage.merchantInfoOnPage();
					HashMap<String, String> DBInfo = MerchantDBOperations.merchantAuditExpectedInfo(text[0]);
					for (String key : DBInfo.keySet()) {
						// logger.debug(key + ": - page " + pageInfo.get(key) + ", - DB
						// " + DBInfo.get(key));
						Assert.assertEquals(pageInfo.get(key), DBInfo.get(key));
					}
					auditPage.setAuditRemark(auditRemark);

					auditPage.lastOperate(auditOperate, isConfirmOperate);
				} catch (MerchantException e) {
					logger.error(e);
					Assert.assertEquals(e.getMessage(), message);
					return;
				}
				HashMap<String, String> mch = MerchantDBOperations.getMerchant(text[0]);
				if (auditOperate.equals("审核通过")) {
					Assert.assertEquals(mch.get("EXAMINE_STATUS"), ExamineStatus.PASS.getSCode());
				} else if (auditOperate.equals("审核不通过")) {
					Assert.assertEquals(mch.get("EXAMINE_STATUS"), ExamineStatus.STOP.getSCode());
				}

				Assert.assertEquals(message, "");
		
	}

	@Override
	public void runActive(MerchantActiveCaseBean caseBean) {
		// 预处理测试用例
		caseBean = XMerchantActiveTestData.preCheckProcessCase(caseBean);
				logger.info("本次商户管理——商户进件管理激活测试使用的测试数据是： " + caseBean);
				// 获取测试数据参数
				String[] text = caseBean.getTEXT().split("-");
				String activateStatus = caseBean.getM_ActivateStatus(), activeRemark = caseBean.getACTIVATE_STATUS_REMARK(),
						activeOperate = caseBean.getActiveOperate(), isConfirmOperate = caseBean.getIsConfirmOperate(),
						 message = caseBean.getMessage();

				// 执行测试操作
				try {
					page.setMerchantId(text[0]);
					page.clickSearch();
					page.clickMerchantRowByText(text);
					page.clickActive();
					Assert.assertEquals(activePage.checkAcitvePage(), true);
					HashMap<String, String> pageInfo = activePage.merchantInfoOnPage();
					HashMap<String, String> dbInfo = MerchantDBOperations.merchantAuditExpectedInfo(text[0]);
					for (String key : dbInfo.keySet()) {
						logger.debug(key + ": page -- " + pageInfo.get(key) + ", DB -- " + dbInfo.get(key));
						Assert.assertEquals(pageInfo.get(key), dbInfo.get(key));
					}
					if (activateStatus.equals(ActivateStatus.PASS.getSCode()))
						Assert.assertEquals(activePage.isPassStopButtonsDisappear(), true);
					activePage.setActiveRemark(activeRemark);

					activePage.lastOperate(activeOperate, isConfirmOperate);
				} catch (MerchantException e) {
					logger.error(e);
					Assert.assertEquals(e.getMessage(), message);
					return;
				}
				HashMap<String, String> postDBInfo = MerchantDBOperations.getMerchant(text[0]);
				String pas = postDBInfo.get("ACTIVATE_STATUS");
				if (activeOperate.equals("激活通过")) {
					Assert.assertEquals(pas, ActivateStatus.PASS.getSCode());
				} else if (activeOperate.equals("激活不通过")) {
					Assert.assertEquals(pas, ActivateStatus.FAIL.getSCode());
				} else if (activeOperate.equals("冻结")) {
					Assert.assertEquals(pas, ActivateStatus.FREEZE.getSCode());
				}

				Assert.assertEquals(StringUtils.isEmpty(message), true);
		
	}

	@Override
	public void runAndActiveMerchant(MerchantAAACaseBean caseBean) {
		// 预处理测试数据
		caseBean = XMerchantAAATestData.preCheckProcessCase(caseBean);

				logger.info("本次商户管理——商户进件管理审核并激活测试使用的测试数据是： " + caseBean);
				// 获取测试数据参数
				String[] text = caseBean.getTEXT().split("-");

				String activateStatusRemark = caseBean.getActivateStatusRemark(), aaOperate = caseBean.getAaOperate(),
						isConfirmOperate = caseBean.getIsConfirmOperate(), message = caseBean.getMessage();

				// 执行测试操作
				try {
					page.setMerchantId(text[0]);
					page.clickSearch();
					page.clickMerchantRowByText(text);
					page.clickAuditAndActive();
					Assert.assertEquals(aAApage.checkAAAPage(), true);
					aAApage.setAAARemark(activateStatusRemark);

					aAApage.lastOperate(aaOperate, isConfirmOperate);
				} catch (MerchantException e) {
					logger.error(e);
					Assert.assertEquals(e.getMessage(), message);
					return;
				}

				HashMap<String, String> postMS = MerchantDBOperations.getMerchant(text[0]);
				String es = postMS.get("EXAMINE_STATUS");
				String as = postMS.get("ACTIVATE_STATUS");
				if (aaOperate.equals("通过")) {
					Assert.assertEquals(es, ExamineStatus.PASS.getSCode());
					Assert.assertEquals(as, ActivateStatus.PASS.getSCode());
				} else if (aaOperate.equals("不通过")) {
					Assert.assertEquals(es, ExamineStatus.STOP.getSCode());
					Assert.assertEquals(as, ActivateStatus.FAIL.getSCode());
				}

				Assert.assertEquals(StringUtils.isEmpty(message), true);
		
	}

	@Override
	public void runDetail_xy(MerchantDetailCaseBean caseBean) {
		logger.info("本次商户管理——商户进件管理详情测试使用的测试数据是: " + caseBean);
		// 获取测试数据参数
		String[] text = caseBean.getTEXT().split("-");
		String message = caseBean.getMessage();

		// 开始执行测试操作
		HashMap<String, String> detailOnPage = null;
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickDetail_xy();
			Assert.assertEquals(detailPage.checkDetailPage(), true);
			detailOnPage = detailPage.merchantInfoOnPage();
			detailPage.closeDetailPage();
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		HashMap<String, String> detailInDB = MerchantDBOperations.merchantDetailExpectedPageInfo(text[0]);
		for (String key : detailInDB.keySet()) {
			logger.debug(
					"detailOnPage: " + key + " -- " + detailOnPage.get(key) + ", detailInDB: " + detailInDB.get(key));
			Assert.assertEquals(String.valueOf(detailOnPage.get(key)), String.valueOf(detailInDB.get(key)));
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
		
	}
	@Override
	public void runDetail(MerchantDetailCaseBean caseBean) {
		logger.info("本次商户管理——商户进件管理详情测试使用的测试数据是: " + caseBean);
		// 获取测试数据参数
		String[] text = caseBean.getTEXT().split("-");
		String message = caseBean.getMessage();

		// 开始执行测试操作
		HashMap<String, String> detailOnPage = null;
		try {
			page.setMerchantId(text[0]);
			page.clickSearch();
			page.clickMerchantRowByText(text);
			page.clickDetail();
			Assert.assertEquals(detailPage.checkDetailPage(), true);
			detailOnPage = detailPage.merchantInfoOnPage();
			detailPage.closeDetailPage();
		} catch (MerchantException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		HashMap<String, String> detailInDB = MerchantDBOperations.merchantDetailExpectedPageInfo(text[0]);
		for (String key : detailInDB.keySet()) {
			logger.debug(
					"detailOnPage: " + key + " -- " + detailOnPage.get(key) + ", detailInDB: " + detailInDB.get(key));
			if(key.contains("activeEmp")||key.contains("activeStatus")){//activeEmp,activeStatus字段页面未显示，修改于2017-04-18			
				continue;
			}else{
				Assert.assertEquals(String.valueOf(detailOnPage.get(key)), String.valueOf(detailInDB.get(key)));
				Assert.assertEquals(StringUtils.isEmpty(message), true);
			}
		}
	}

}
