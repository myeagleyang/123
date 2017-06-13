package swiftpass.testcase.channel;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.page.Page.XClock;
import swiftpass.page.channel.ChannelAAAPage;
import swiftpass.page.channel.ChannelActivePage;
import swiftpass.page.channel.ChannelAddPage;
import swiftpass.page.channel.ChannelAddPage.SelectChannelItem;
import swiftpass.page.channel.ChannelAuditPage;
import swiftpass.page.channel.ChannelDetailPage;
import swiftpass.page.channel.ChannelEditPage;
import swiftpass.page.channel.ChannelPage;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ChannelProperties;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.exceptions.ChannelException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.ChannelAddCaseBean;
import swiftpass.testcase.casebeans.ChannelAddD0CaseBean;
import swiftpass.testcase.casebeans.ChannelEditCaseBean;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;
import swiftpass.testcase.testdata.channel.ChannelActiveTestData;
import swiftpass.testcase.testdata.channel.ChannelDetailTestData;
import swiftpass.testcase.testdata.channel.ChannelEATestData;
import swiftpass.testcase.testdata.channel.ChannelExamineTestData;
import swiftpass.utils.DBUtil;

public class ChannelTestCaseImpl extends TestCaseImpl implements ChannelTestCase{
	//使用的页面
	private ChannelPage page;
	private ChannelAddPage addPage;
	private ChannelEditPage editPage;
	private ChannelAuditPage auditPage;
	private ChannelActivePage activePage;
	private ChannelAAAPage aAAPage;
	private ChannelDetailPage detailPage;
	
	public ChannelTestCaseImpl(WebDriver driver) {
		super(driver);
		this.page = new ChannelPage(this.driver);
		this.addPage = new ChannelAddPage(this.driver);
		this.editPage = new ChannelEditPage(this.driver);
		this.auditPage = new ChannelAuditPage(this.driver);
		this.activePage = new ChannelActivePage(this.driver);
		this.aAAPage = new ChannelAAAPage(this.driver);
		this.detailPage = new ChannelDetailPage(this.driver);
	}
	
	public void searchChannel(ChannelSearchCaseBean data) {
		logger.info("本次渠道管理——渠道进件管理——查询测试使用的测试数据是： " + data);
		
		//执行查询操作
		Assert.assertEquals(page.isNoteInfoVisible(), true);
		page.setBeginCT(data.getBeginCreateTime())
			.setEndCT(data.getEndCreateTime())
			.selectAcceptOrg(data.getIsSelectAcceptOrg(), 
						data.getAcceptOrgItem(), 
						data.getAcceptOrgId(), 
						data.getAcceptOrgName(), 
						data.getIsConfirmSelectAcceptOrg())
			.selectAuditStatus(data.getExamineStatus())
			.selectActiveStatus(data.getActivateStatus())
			.clickSelectChannel(data.getIsConfirmSelectParentChannel(), 
						data.getParentChannelItem(), 
						data.getParentChannelId(), 
						data.getParentChannelName(), 
						data.getIsConfirmSelectParentChannel())
			.setChannelId(data.getChannelId())
			.setChannelName(data.getChannelName())
			.selectChannelType(data.getChannelProperty())
			.selectAccWay(data.getAccWay())
			.clickSearch();
		if(data.getCASE_NAME().equals("渠道默认查询"))
			Assert.assertEquals(page.isRecordsOrderCorrect(), true);
		//获取实际的查询结果
		String actual = page.getChannelRecords();
		
		//验证和比对
		Assert.assertEquals(actual, data.getExpected());
	}

	public void addChannel(ChannelAddCaseBean caseBean) {
		logger.info("本次渠道管理——渠道进件管理——新增测试使用的测试数据是： " + caseBean);
		
		//执行新增操作
		String preRecord = page.getChannelRecords();
		if (caseBean.getNewChannelType().equals(ChannelProperties.INNER.getSCode())) {
			page.clickAddInnerInstitution();
		} else if(caseBean.getNewChannelType().equals(ChannelProperties.OUTTER.getSCode())){
			page.clickAddOutterChannel();
		}
		try{
			if(StringUtils.isEmpty(caseBean.getMessage())){
				addPage.selectParentChannel(
					caseBean.getIsSelectParentChannel(), 
					caseBean.getParentNameIdItem(), 
					caseBean.getParentNameIdItem().equals(SelectChannelItem.ID.getDescText()) ? 
						caseBean.getParentChannelId() : caseBean.getParentChannelName(), 
					caseBean.getIsConfirmSelectParentChannel());
				addPage.selectAccountBank(
					caseBean.getIsSelectBank(), 
					caseBean.getBankName(), 
					caseBean.getIsConfirmSelectBank());
			}
			else{//执行异常用例时，直接调用js设置所属渠道、开户银行
				addPage.directSetParentChannel(
					caseBean.getParentChannelName(), 
					caseBean.getParentChannelId());
				addPage.directSetAccountBank(
					caseBean.getBankId(), 
					caseBean.getBankName());
			}
			addPage.setChannelName(caseBean.getNewChannelName());
			addPage.selectChannelProvince(caseBean.getProvince(), caseBean.getCity());
			addPage.setChannelAddress(caseBean.getAddress());
			addPage.setChannelEmail(caseBean.getEmail());
			addPage.setChannelPrincipal(caseBean.getPrincipal());
			addPage.setChannelTel(caseBean.getTel());
			addPage.setRemark(caseBean.getRemark());
			addPage.setThirdChannelId(caseBean.getThirdChannelId());
			addPage.setBankAccount(caseBean.getBankAccount());
			addPage.setAccountOwner(caseBean.getBankAccountName());
			addPage.selectAccountType(caseBean.getBankAccountType());
			addPage.setsubBankName(caseBean.getBankSubBranch());
			addPage.setBankLeftPhone(caseBean.getBankAccountPhone());
			addPage.selectIdCardType(caseBean.getIdType(), caseBean.getIdCode());
			addPage.selectSubBankProvince(caseBean.getBankAccountProvince(), caseBean.getBankAccountCity());
			addPage.setSubBankCode(caseBean.getSiteNO());
			//设置是否行内账号
//			addPage.setIsHNAccount(caseBean.getIsSelectHNAcc(), caseBean.getYesOrNoHNAcc());
			addPage.lastAddOperate(caseBean.getIsSave(), caseBean.getIsConfirmSave());	//最后事件响应操作
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), caseBean.getMessage());
			return;		//异常被捕捉、判断后就应该返回！！！
		}
		//新增后的渠道数
		XClock.stop(2);
		String postRecord = page.getChannelRecords();
		Assert.assertEquals(postRecord, "" + (Integer.parseInt(preRecord) + 1));
		Assert.assertEquals(StringUtils.isEmpty(caseBean.getMessage()), true);
	}

	public void editChannel(ChannelEditCaseBean data) {
		logger.info("本次渠道管理——渠道进件管理使用的测试数据是： " + data);
		//执行编辑操作
		try{
			page.setChannelId(data.getChannelId());
			page.clickSearch();
			page.clickChannelRowByText(ArrayUtils.toArray(data.getChannelId()));
			if(data.getIsNotFreeze().equals("false"))
				Assert.assertEquals(page.isEditButtonUnclickable(), true);
			page.clickEdit();
			//检查渠道页面的不可编辑部分——只验证一次
			if(StringUtils.isEmpty(data.getMessage())){
				Assert.assertEquals(editPage.checkNonEditable(), true);
			}
			editPage.editChannelName(data.getChannelName())
					.editChannelAddress(data.getAddress())
					.editChannelEmail(data.getEmail())
					.editChannelPrincipal(data.getPrincipal())
					.editChannelTel(data.getTel())
					.editRemark(data.getRemark())
					.editThirdChannelId(data.getThirdChannelId())
					.lastEditOperate(data.getIsEdit(), data.getIsConfirmEdit());
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), data.getMessage());	//抛了异常，则要判断是否和预期的错误消息一样
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(data.getMessage()), true);
		Map<String, String> postEditChannel = DBUtil.getXQueryResultMap("select * from cms_channel where channel_id = " + data.getChannelId()).get(1);
		Assert.assertEquals(postEditChannel.get("CHANNEL_NAME"), data.getChannelName());
		if(data.getIsExaminePass().equals("true")){
			Assert.assertEquals(postEditChannel.get("EXAMINE_STATUS"), ExamineStatus.NEEDAGAIN.getSCode());
		}
		Assert.assertEquals(postEditChannel.get("REMARK"), data.getRemark());
		Assert.assertEquals(postEditChannel.get("TEL"), data.getTel());
 	}
	
	public void scanChannelDetail(HashMap<String, String> params) {
		logger.info("本次渠道管理——渠道进件管理——查看详情的测试数据是： " + params);
		String[] text = params.get("TEXT").split("-");
		String message = params.get("message");
		String CHANNEL_ID = params.get("CHANNEL_ID");
		HashMap<String, String> detailInfoInDB = ChannelDetailTestData.getChannelDetailFromDB(CHANNEL_ID);
		for(String key : detailInfoInDB.keySet())//将从数据库中查出的渠道字段为null值替换成""
			detailInfoInDB.replace(key, (detailInfoInDB.get(key) == null)? "": detailInfoInDB.get(key));
			
		try{
			page.setChannelId(CHANNEL_ID);
			page.clickSearch();
			page.clickChannelRowByText(text);
			page.clickDetail();
			Assert.assertEquals(detailPage.isPageCorrect(), true);
			HashMap<String, String> detailInfoOnPage = detailPage.getDetailInfo();
			Assert.assertEquals(detailInfoOnPage, detailInfoInDB);
			detailPage.closePage();
		} catch(ChannelException e){
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	public void auditChannel(HashMap<String, String> params){
		//	预处理测试数据参数
		params = ChannelExamineTestData.preCheckProcessCaseMap(params);
		
		logger.info("本次渠道管理——渠道进件管理——审核使用的测试数据是: " + params);
		//获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String EXAMINE_STATUS_REMARK = params.get("EXAMINE_STATUS_REMARK");
		String auditOperate = params.get("auditOperate");
		String isConfirmOperate = params.get("isConfirmOperate");
		String message = params.get("message");
		
		String C_ExamineStatus = params.get("C_ExamineStatus");
		//测试操作
		try{
			page.selectAuditStatus(C_ExamineStatus);
			page.setChannelId(text[0]);
			page.clickSearch();
			page.clickChannelRowByText(text);
			page.clickAudit();
			auditPage.setRemarkArea(EXAMINE_STATUS_REMARK);
			auditPage.lastAuditOperate(auditOperate, isConfirmOperate);
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		String auditedChannelSql = "Select EXAMINE_STATUS From CMS_CHANNEL Where CHANNEL_ID = '$ID'".replace("$ID", text[0]);
		String actualExamineStatus = DBUtil.getQueryResultMap(auditedChannelSql).get(1).get("EXAMINE_STATUS");
		if(auditOperate.equals("审核通过"))
			Assert.assertEquals(actualExamineStatus, "1");
		else if(auditOperate.equals("审核不通过"))
			Assert.assertEquals(actualExamineStatus, "2");
	}

	public void activeChannel(HashMap<String, String> params) {
		//	预处理测试数据
		params = ChannelActiveTestData.preCheckProcess(params);

		logger.info("本次渠道管理——渠道进件管理——激活使用的测试数据是：" + params);
		//获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String channelActivateStatus = params.get("C_ActivateStatus");
		String ACTIVATE_STATUS_REMARK = params.get("ACTIVATE_STATUS_REMARK");
		String activeOperate = params.get("activeOperate");
		String isConfirmOperate = params.get("isConfirmOperate");
		String message = params.get("message");
		//测试操作
		try{
			page.selectAuditStatus(params.get("C_ExamineStatus"));
			page.selectActiveStatus(channelActivateStatus);
			page.setChannelId(text[0]);
			page.clickSearch();
			page.clickChannelRowByText(text);
			page.clickActive();
			HashMap<String, String> pageInfo = activePage.getInfoOnActivePage();
			HashMap<String, String> dbInfo = ChannelDBOperations.getChannel(text[0]);
			for(String key : pageInfo.keySet())
				Assert.assertEquals(pageInfo.get(key), dbInfo.get(key));
			Assert.assertEquals(activePage.isPageCorrect(channelActivateStatus), true);
			activePage.setActiveRemark(ACTIVATE_STATUS_REMARK);
			activePage.lastActiveOperate(activeOperate, isConfirmOperate);
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		
		HashMap<String, String> channel = ChannelDBOperations.getChannel(text[0]);
		String postActiveStatus = channel.get("ACTIVATE_STATUS");
		if(postActiveStatus.equals(ActivateStatus.NOPROCESS.getSCode())){
			Assert.assertEquals(activeOperate, "未激活");
		} else if(postActiveStatus.equals(ActivateStatus.PASS.getSCode())){
			Assert.assertEquals(activeOperate, "激活通过");
		} else if(postActiveStatus.equals(ActivateStatus.FAIL.getSCode())){
			Assert.assertEquals(activeOperate, "激活不通过");
		} else if(postActiveStatus.equals(ActivateStatus.FREEZE.getSCode())){
			Assert.assertEquals(activeOperate, "冻结");
		} else {
			Assert.assertEquals(activeOperate, "需再次激活");
		}
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
		
	public void aAAChannel(HashMap<String, String> params){
		//预处理测试用例数据
		params = ChannelEATestData.preCheckProcess(params);
		
		logger.info("本次渠道管理——渠道进件管理——审核并激活的测试数据是： " + params);
		//获取测试数据参数
		String[] text = params.get("TEXT").split("-");
		String eaOperate = params.get("eaOperate");
		String isConfirmOperate = params.get("isConfirmOperate");
		String message = params.get("message");
		String ACTIVATE_STATUS_REMARK = params.get("ACTIVATE_STATUS_REMARK");
		
		//测试操作
		Map<String, String> pageInfo;
		try{
			page.setChannelId(text[0]);
			page.clickSearch();
			page.clickChannelRowByText(text);
			page.clickAuditAndActive();
			Assert.assertEquals(aAAPage.isPageCorrect(), true);
			pageInfo = aAAPage.getChannelInfoOnPage();
			aAAPage.setEARemark(ACTIVATE_STATUS_REMARK);
			aAAPage.lastEAOperate(eaOperate, isConfirmOperate);
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		
		HashMap<String, String> channel = ChannelDBOperations.getChannel(text[0]);
		String postAuditStatus = channel.get("EXAMINE_STATUS");
		String postActiveStatus = channel.get("ACTIVATE_STATUS");
		logger.debug("操作后的审核状态：" + postAuditStatus + ",  操作后的激活状态：" + postActiveStatus);
		if(!postAuditStatus.equals(postActiveStatus)){
			Assert.assertEquals(false, true);//	要么同时通过、要么同时不通过
		} else{
			if(postAuditStatus.equals("1")){
				Assert.assertEquals(eaOperate, "通过");
			} else if(postAuditStatus.equals("2")){
				Assert.assertEquals(eaOperate, "不通过");
			}
		}
		for(String key : pageInfo.keySet()){
			Assert.assertEquals(pageInfo.get(key), channel.get(key));
		}
		
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
	public void addD0Channel(ChannelAddD0CaseBean caseBean) {
		logger.info("本次渠道管理——渠道进件管理——新增测试使用的测试数据是： " + caseBean);
		
		//执行新增操作
		String preRecord = page.getChannelRecords();
		if (caseBean.getNewChannelType().equals(ChannelProperties.INNER.getSCode())) {
			page.clickAddInnerInstitution();
		} else if(caseBean.getNewChannelType().equals(ChannelProperties.OUTTER.getSCode())){
			page.clickAddOutterChannel();
		} else if(caseBean.getNewChannelType().equals(ChannelProperties.D0.getSCode())){
			page.clickAddD0Channel();
		}
		try{
			if(StringUtils.isEmpty(caseBean.getMessage())){
				addPage.selectParentChannel(
					caseBean.getIsSelectParentChannel(), 
					caseBean.getParentNameIdItem(), 
					caseBean.getParentNameIdItem().equals(SelectChannelItem.ID.getDescText()) ? 
						caseBean.getParentChannelId() : caseBean.getParentChannelName(), 
					caseBean.getIsConfirmSelectParentChannel());
				addPage.selectAccountBank(
					caseBean.getIsSelectBank(), 
					caseBean.getBankName(), 
					caseBean.getIsConfirmSelectBank());
			}
			else{//执行异常用例时，直接调用js设置所属渠道、开户银行
				addPage.directSetParentChannel(
					caseBean.getParentChannelName(), 
					caseBean.getParentChannelId());
				addPage.directSetAccountBank(
					caseBean.getBankId(), 
					caseBean.getBankName());
			}
			addPage.setChannelName(caseBean.getNewChannelName());
			addPage.selectChannelProvince(caseBean.getProvince(), caseBean.getCity());
			addPage.setChannelAddress(caseBean.getAddress());
			addPage.setChannelEmail(caseBean.getEmail());
			addPage.setChannelPrincipal(caseBean.getPrincipal());
			addPage.setChannelTel(caseBean.getTel());
			addPage.setRemark(caseBean.getRemark());
			addPage.setThirdChannelId(caseBean.getThirdChannelId());
			addPage.setBankAccount(caseBean.getBankAccount());
			addPage.setAccountOwner(caseBean.getBankAccountName());
			addPage.selectAccountType(caseBean.getBankAccountType());
			addPage.setsubBankName(caseBean.getBankSubBranch());
			addPage.setBankLeftPhone(caseBean.getBankAccountPhone());
			addPage.selectIdCardType(caseBean.getIdType(), caseBean.getIdCode());
			addPage.selectSubBankProvince(caseBean.getBankAccountProvince(), caseBean.getBankAccountCity());
			addPage.setSubBankCode(caseBean.getSiteNO());
			//设置是否行内账号
//			addPage.setIsHNAccount(caseBean.getIsSelectHNAcc(), caseBean.getYesOrNoHNAcc());
			addPage.lastAddOperate(caseBean.getIsSave(), caseBean.getIsConfirmSave());	//最后事件响应操作
		} catch(ChannelException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), caseBean.getMessage());
			return;		//异常被捕捉、判断后就应该返回！！！
		}
		//新增后的渠道数
		XClock.stop(2);
		String postRecord = page.getChannelRecords();
		Assert.assertEquals(postRecord, "" + (Integer.parseInt(preRecord) + 1));
		Assert.assertEquals(StringUtils.isEmpty(caseBean.getMessage()), true);
	}

}