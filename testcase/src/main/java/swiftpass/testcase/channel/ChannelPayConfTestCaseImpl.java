package swiftpass.testcase.channel;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import swiftpass.page.channel.ChannelPage;
import swiftpass.page.channel.ChannelPayConfActivePage;
import swiftpass.page.channel.ChannelPayConfAddPage;
import swiftpass.page.channel.ChannelPayConfDetailPage;
import swiftpass.page.channel.ChannelPayConfEditPage;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.exceptions.ChannelException;
import swiftpass.page.exceptions.ChannelPayConfException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.channel.ChannelPCActiveTestData;
import swiftpass.testcase.testdata.channel.ChannelPCAddTestData;
import swiftpass.testcase.testdata.channel.ChannelPCEditTestData;
import swiftpass.utils.DBUtil;
import swiftpass.utils.services.ChannelAAAService;

@SuppressWarnings("deprecation")
public class ChannelPayConfTestCaseImpl extends TestCaseImpl implements ChannelPayConfTestCase{
    //页面
    private ChannelPage page;
    private ChannelPayConfAddPage pcAddPage;
    private ChannelPayConfEditPage pcEditPage;
    private ChannelPayConfActivePage pcActivePage;
    private ChannelPayConfDetailPage pcDetailPage;
    
    public ChannelPayConfTestCaseImpl(WebDriver driver) {
        super(driver);
        this.page = new ChannelPage(this.driver);
        this.pcAddPage = new ChannelPayConfAddPage(this.driver);
        this.pcEditPage = new ChannelPayConfEditPage(this.driver);
        this.pcActivePage = new ChannelPayConfActivePage(this.driver);
        this.pcDetailPage = new ChannelPayConfDetailPage(this.driver);
    }

    @Override
    public void addChannelPayConf(HashMap<String, String> params) {
        logger.info("本次渠道支付类型配置新增的测试数据是： " + params);
        // 获取测试数据参数
        String selectPcName = params.get(ChannelPCAddTestData.SELECTPCNAME);
        String selectPtName = params.get(ChannelPCAddTestData.SELECTPTNAME);
        String rateType = params.get(ChannelPCAddTestData.RATETYPE);
        String clrRate = params.get(ChannelPCAddTestData.CLRRATE);
        String parentClrRate = params.get(ChannelPCAddTestData.PARENTRATE);
        String payTypeId = params.get(ChannelPCAddTestData.PAYTYPEID);
        String payCenterId = params.get(ChannelPCAddTestData.PAYCENTERID);
        String channelId = params.get(ChannelPCAddTestData.CHANNELID);
        String isClickFirstRowChannel = params.get(ChannelPCAddTestData.ISCLICKFIRSTROWCHANNEL);
        String isSelectPcName = params.get(ChannelPCAddTestData.ISSELECTPCNAME);
        String isSelectPtName = params.get(ChannelPCAddTestData.ISSELECTPTNAME);
        String isConfirmSelectPC = params.get(ChannelPCAddTestData.ISCONFIRMSELECTPC);
        String isConfirmSelectPT = params.get(ChannelPCAddTestData.ISCONFIRMSELECTPT);
        String isSave = params.get(ChannelPCAddTestData.ISSAVE);
        String isConfirmSave = params.get(ChannelPCAddTestData.ISCONFIRMSAVE);
        String isParentChSetPT = params.get(ChannelPCAddTestData.ISPARENTCHSETPT);
        String errorMsg = params.get(ChannelPCAddTestData.ERRORMSG);
        String chnActivateStatus = params.get(ChannelPCAddTestData.CHNACTIATESTATUS);
        // 执行测试操作
        try{
        	page.clickSearch();
			page.setChannelId(channelId);
            page.clickSearch();
            //验证未选中渠道时，支付配置新增按钮的可点击状态
            Assert.assertEquals(page.isPrePCTitleCorrect(), true);
            page.clickChannelFirstRowByChannelId(isClickFirstRowChannel, channelId);
            if(!isClickFirstRowChannel.equals("true")){
                Assert.assertEquals(page.checkIsPCAddButtonEnable(), false);
                return;
            }
            //验证渠道为冻结状态时新增编辑按钮不可点击
            if(chnActivateStatus.equals("4")){
            	//调用接口冻结渠道
            	ChannelAAAService.activeChannel(ActivateStatus.FREEZE, channelId);
            	page.clickSearch();//这里一定要重新查询才能显示最新的状态
            	logger.info("渠道被冻结，不能新增、编辑支付方式！");
            	Assert.assertEquals(page.checkIsPCAddButtonEnable(), false);
            	Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
            	return;
            }
            page.clickPCAddButton();
            Assert.assertEquals(pcAddPage.checkChannelUneditable(), true);
            if(!StringUtils.isEmpty(errorMsg) && isParentChSetPT.equals("true")){
            	pcAddPage.directSelectPayCenter(isSelectPcName, payCenterId, selectPcName);
            }else{
            	pcAddPage.selectPayCenter(isSelectPcName, selectPcName, isConfirmSelectPC);
            }
            if(!StringUtils.isEmpty(errorMsg) && !errorMsg.equals("请先选择支付通道") && isParentChSetPT.equals("true")){
            	pcAddPage.directSelectPayType(isSelectPtName, payTypeId, selectPtName);
            }else{
            	pcAddPage.selectPayType(isSelectPtName, selectPtName, isConfirmSelectPT);
            }
            /*if(!StringUtils.isEmpty(errorMsg)){
            	pcAddPage.directSelectPayType(isSelectPtName, payTypeId, selectPtName);
            } else {
            	pcAddPage.selectPayType(isSelectPtName, selectPtName, isConfirmSelectPT);
            }*/
            pcAddPage.selectRateType(rateType);
            pcAddPage.setClrRate(clrRate);
            if(!isParentChSetPT.equals("true") && !errorMsg.contains("渠道支付类型配置已存在")){
            	Assert.assertEquals(pcAddPage.checkTipMsg(), true);
            	pcAddPage.setParentClrRate(parentClrRate);
            }
            pcAddPage.lastAddOperate(isSave, isConfirmSave);
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
        String records = page.getPCRecords();
		Assert.assertEquals(Integer.parseInt(records), 1);
    }
    @Override
    public void editChannelPayConf(HashMap<String, String> params) {
        logger.info("本次渠道支付类型配置编辑的测试数据是： " + params);
        // 获取测试数据参数
        String isClickFirstRowChannel = params.get(ChannelPCEditTestData.ISCLICKFIRSTROWCHANNEL);
        String isClickFirstRowPC = params.get(ChannelPCEditTestData.ISCLICKFIRSTROWPC);
        String rateType = params.get(ChannelPCEditTestData.RATETYPE);
        String clrRate = params.get(ChannelPCEditTestData.CLRRATE);
        String isEdit = params.get(ChannelPCEditTestData.ISEDIT);
        String isConfirmEdit = params.get(ChannelPCEditTestData.ISCONFIRMEDIT);
//        String ptPermissionControl = params.get(ChannelPCEditTestData.PTPERMISSIONCONTROL);
        String channelId = params.get(ChannelPCEditTestData.CHANNELID);
//        String isptpConfirm = params.get(ChannelPCEditTestData.ISPTPCONFIRM);
        String payTypeName = params.get(ChannelPCEditTestData.PAYTYPENAME);
        String errorMsg = params.get(ChannelPCEditTestData.ERRORMSG);
        
        // 执行测试操作
        try{
        	page.setChannelId(channelId);
            page.clickSearch();
            page.clickChannelFirstRowByChannelId(isClickFirstRowChannel, channelId);
            if(!isClickFirstRowChannel.equals("true")){
                Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
                return;
            }
            page.clickRowByPTName(isClickFirstRowPC, payTypeName);
            page.clickPCEditButton();
            Assert.assertEquals(pcEditPage.checkUneditablePart(), true);
            pcEditPage.editRateType(rateType);
            pcEditPage.editClrRate(clrRate);
//            pcEditPage.editPTPermissionControl(ptPermissionControl, isptpConfirm);
            pcEditPage.lastEditOperate(isEdit, isConfirmEdit);
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        String SQL = "select * from tra_ch_pay_conf where channel_id = '$channelId'".replace("$channelId", channelId);
        String billRate = DBUtil.getQueryResultMap(SQL).get(1).get("BILL_RATE");
        Assert.assertEquals(billRate, Integer.parseInt(clrRate)*1000 + "");
    }
    @Override
    public void activeChannelPayConf(HashMap<String, String> params) {
        logger.info("本次渠道支付类型配置激活的测试数据是： " + params);
        //	获取测试数据参数
        String es = params.get(ChannelPCActiveTestData.CHNEXAMINESTATUS);
        String as = params.get(ChannelPCActiveTestData.CHNACTIVATESTATUS);
        String isClickFirstRowChannel = params.get(ChannelPCActiveTestData.ISCLICKFIRSTROWCHANNEL);
        String isClickFirstRowPC = params.get(ChannelPCActiveTestData.ISCLICKFIRSTROWPC);
        String activeRemark = params.get(ChannelPCActiveTestData.ACTIVATEREMARK);
        String activeOperate = params.get(ChannelPCActiveTestData.ACTIVATEOPERATE);
        String isConfirmActiveOperate = params.get(ChannelPCActiveTestData.ISCONNFIRMACTIVATEOPERATE);
        String chnExamineStatus = params.get(ChannelPCActiveTestData.CHNEXAMINESTATUS);
        String chnActiveStatus = params.get(ChannelPCActiveTestData.CHNACTIVATESTATUS);
        String pcActivateStatus = params.get(ChannelPCActiveTestData.PCACTIVATESTATUS);
        String payTypeName = params.get(ChannelPCActiveTestData.PAYTYPENAME);
        String errorMsg = params.get(ChannelPCActiveTestData.ERRORMSG);
        String channelId = ChannelPCActiveTestData.getNeedChannelId(es, as, pcActivateStatus);
        //	执行测试操作
        try{
        	logger.info("本次测试使用的渠道ID:" + channelId);
        	page.setChannelId(channelId);
            page.clickSearch();
            page.clickChannelFirstRowByChannelId(isClickFirstRowChannel, channelId);
            if(!isClickFirstRowChannel.equals("true")){
                Assert.assertEquals(page.checkIsPCActiveButtonEnable(), false);
                return;
            }
            //验证渠道被冻结新增、编辑按钮不可点击
            if(chnActiveStatus.equals("4")){
            	Assert.assertEquals(page.checkIsPCAddButtonEnable(), false);
            	Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
            	return;
            }
            page.clickRowByPTName(isClickFirstRowPC, payTypeName);
            if(!chnExamineStatus.equals("1")){//不为审核通过
            	logger.error("所选渠道为非审核通过状态，激活按钮不可点击");
                Assert.assertEquals(page.checkIsPCActiveButtonEnable(), false);
                return;
            }
            //验证支付方式激活状态为冻结，编辑按钮不可点击
            if(pcActivateStatus.equals("4")){
            	logger.info("渠道支付方式冻结，编辑按钮不可点击");
            	Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
            	return;
            }
            page.clickPCActiveButton();
            pcActivePage.setActiveRemark(activeRemark);
            pcActivePage.activateOperate(activeOperate, isConfirmActiveOperate, pcActivateStatus);
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        String sql = "Select * From tra_ch_pay_conf Where channel_id = $channelId".replace("$channelId", channelId);
        String dbActiveStatus = DBUtil.getQueryResultMap(sql).get(1).get("ACTIVATE_STATUS");
        if(dbActiveStatus.equals("1")){
            Assert.assertEquals(activeOperate, "激活通过");
        } else if(dbActiveStatus.equals("2")){
            Assert.assertEquals(activeOperate, "激活不通过");
        } else if(dbActiveStatus.equals("3")){
            Assert.assertEquals(activeOperate, "需再次激活");
        } else{
            Assert.assertEquals(activeOperate, "冻结");
        }
        
        Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
    }
    @Override
    public void detailChannelPayConf(HashMap<String, String> params) {
        logger.info("本次渠道支付类型配置详情的测试数据是： " + params);
        //	获取测试数据参数
        String isClickFirstRowChannel = params.get("isClickFirstRowChannel");
        String isClickFirstRowPC = params.get("isClickFirstRowPC");
        String errorMsg = params.get("errorMsg");
        
        //	执行测试操作
        try{
        	page.setChannelId(ChannelPCEditTestData.needChannelInfo.get("CHANNEL_ID"));
            page.clickSearch();
            page.clickChannelFirstRow(isClickFirstRowChannel);
            Assert.assertEquals(page.checkIsPCDetailButtonEnable(), true);
            page.clickPCFirstRow(isClickFirstRowPC);
            page.clickPCDetailButton();
            Assert.assertEquals(pcDetailPage.checkIsDetailPage(), true);
            pcDetailPage.closePCDetailPage();
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        
        Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
    }

	@Override
	public void addXYChannelPayConf(HashMap<String, String> params) {
		logger.info("本次渠道支付类型配置新增的测试数据是： " + params);
        // 获取测试数据参数
        String selectPcName = params.get(ChannelPCAddTestData.SELECTPCNAME);
        String selectPtName = params.get(ChannelPCAddTestData.SELECTPTNAME);
        String rateType = params.get(ChannelPCAddTestData.RATETYPE);
        String clrRate = params.get(ChannelPCAddTestData.CLRRATE);
        String parentClrRate = params.get(ChannelPCAddTestData.PARENTRATE);
        String payTypeId = params.get(ChannelPCAddTestData.PAYTYPEID);
        String payCenterId = params.get(ChannelPCAddTestData.PAYCENTERID);
        String channelId = params.get(ChannelPCAddTestData.CHANNELID);
        String isClickFirstRowChannel = params.get(ChannelPCAddTestData.ISCLICKFIRSTROWCHANNEL);
        String isSelectPcName = params.get(ChannelPCAddTestData.ISSELECTPCNAME);
        String isSelectPtName = params.get(ChannelPCAddTestData.ISSELECTPTNAME);
        String isConfirmSelectPC = params.get(ChannelPCAddTestData.ISCONFIRMSELECTPC);
        String isConfirmSelectPT = params.get(ChannelPCAddTestData.ISCONFIRMSELECTPT);
        String isSave = params.get(ChannelPCAddTestData.ISSAVE);
        String isConfirmSave = params.get(ChannelPCAddTestData.ISCONFIRMSAVE);
        String isParentChSetPT = params.get(ChannelPCAddTestData.ISPARENTCHSETPT);
        String errorMsg = params.get(ChannelPCAddTestData.ERRORMSG);
        String chnActivateStatus = params.get(ChannelPCAddTestData.CHNACTIATESTATUS);
        String isAllowQRFast = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCAddTestData.ISALLOWQCFAST);
        String allowSetMinMchClrRate = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCAddTestData.ALLOWSETMINMCHCLRRATE);
        String singleMax = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCAddTestData.SINGLEMAX);
        String singleMin = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCAddTestData.SINGLEMIN);
        // 执行测试操作
        try{
        	page.clickSearch();
			page.setChannelId(channelId);
            page.clickSearch();
            //验证未选中渠道时，支付配置新增按钮的可点击状态
            Assert.assertEquals(page.isPrePCTitleCorrect(), true);
            page.clickChannelFirstRowByChannelId(isClickFirstRowChannel, channelId);
            if(!isClickFirstRowChannel.equals("true")){
                Assert.assertEquals(page.checkIsPCAddButtonEnable(), false);
                return;
            }
            //验证渠道为冻结状态时新增编辑按钮不可点击
            if(chnActivateStatus.equals("4")){
            	//调用接口冻结渠道
            	ChannelAAAService.activeChannel(ActivateStatus.FREEZE, channelId);
            	page.clickSearch();//这里一定要重新查询才能显示最新的状态
            	logger.info("渠道被冻结，不能新增、编辑支付方式！");
            	Assert.assertEquals(page.checkIsPCAddButtonEnable(), false);
            	Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
            	return;
            }
            page.clickPCAddButton();
            Assert.assertEquals(pcAddPage.checkChannelUneditable(), true);
            if(!StringUtils.isEmpty(errorMsg) && isParentChSetPT.equals("true")){
            	pcAddPage.directSelectPayCenter(isSelectPcName, payCenterId, selectPcName);
            }else{
            	pcAddPage.selectPayCenter(isSelectPcName, selectPcName, isConfirmSelectPC);
            }
            if(!StringUtils.isEmpty(errorMsg) && !errorMsg.equals("请先选择支付通道") && isParentChSetPT.equals("true")){
            	pcAddPage.directSelectPayType(isSelectPtName, payTypeId, selectPtName);
            }else{
            	pcAddPage.selectPayType(isSelectPtName, selectPtName, isConfirmSelectPT);
            }
            pcAddPage.selectRateType(rateType);
            pcAddPage.setClrRate(clrRate);
            pcAddPage.setAllowSetMinMchClrRate(allowSetMinMchClrRate);
            pcAddPage.selectIsAllowQRFastAdd(isAllowQRFast, singleMax, singleMin);
            if(!isParentChSetPT.equals("true") && !errorMsg.contains("渠道支付类型配置已存在")){
            	Assert.assertEquals(pcAddPage.checkTipMsg(), true);
            	pcAddPage.setParentClrRate(parentClrRate);
            }
            pcAddPage.lastAddOperate(isSave, isConfirmSave);
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
        String records = page.getPCRecords();
		Assert.assertEquals(Integer.parseInt(records), 1);
		
	}

	@Override
	public void EditXYChannelPayConf(HashMap<String, String> params) {
        logger.info("本次渠道支付类型配置编辑的测试数据是： " + params);
        // 获取测试数据参数
        String isClickFirstRowChannel = params.get(ChannelPCEditTestData.ISCLICKFIRSTROWCHANNEL);
        String isClickFirstRowPC = params.get(ChannelPCEditTestData.ISCLICKFIRSTROWPC);
        String rateType = params.get(ChannelPCEditTestData.RATETYPE);
        String clrRate = params.get(ChannelPCEditTestData.CLRRATE);
        String isEdit = params.get(ChannelPCEditTestData.ISEDIT);
        String isConfirmEdit = params.get(ChannelPCEditTestData.ISCONFIRMEDIT);
//        String ptPermissionControl = params.get(ChannelPCEditTestData.PTPERMISSIONCONTROL);
        String channelId = params.get(ChannelPCEditTestData.CHANNELID);
//        String isptpConfirm = params.get(ChannelPCEditTestData.ISPTPCONFIRM);
        String payTypeName = params.get(ChannelPCEditTestData.PAYTYPENAME);
        String errorMsg = params.get(ChannelPCEditTestData.ERRORMSG);
        String AllowSetMinMchClrRate = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCEditTestData.ALLOWSETMINMCHCLRRATE);
        String isAllowQRFastAdd = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCEditTestData.ISALLOWQCFAST);
        String singleMaxLimit = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCEditTestData.SINGLEMAX);
        String singleMinLimit = params.get(swiftpass.testcase.testdata.channel.xychannel.ChannelPCEditTestData.SINGLEMIN);
        
        // 执行测试操作
        try{
        	page.setChannelId(channelId);
            page.clickSearch();
            page.clickChannelFirstRowByChannelId(isClickFirstRowChannel, channelId);
            if(!isClickFirstRowChannel.equals("true")){
                Assert.assertEquals(page.checkIsPCEditButtonEnable(), false);
                return;
            }
            page.clickRowByPTName(isClickFirstRowPC, payTypeName);
            page.clickPCEditButton();
            Assert.assertEquals(pcEditPage.checkUneditablePart(), true);
            pcEditPage.editRateType(rateType);
            pcEditPage.editClrRate(clrRate);
//            pcEditPage.editPTPermissionControl(ptPermissionControl, isptpConfirm);
            //兴业特有的操作
            pcEditPage.editAllowSetMinMchClrRate(AllowSetMinMchClrRate);
            pcEditPage.editIsAllowQRFastAdd(isAllowQRFastAdd, singleMinLimit, singleMaxLimit);
            pcEditPage.lastEditOperate(isEdit, isConfirmEdit);
        } catch(ChannelException | ChannelPayConfException e){
            logger.error(e);
            Assert.assertEquals(e.getMessage(), errorMsg);
            return;
        }
        String SQL = "select * from tra_ch_pay_conf where channel_id = '$channelId'".replace("$channelId", channelId);
        String billRate = DBUtil.getQueryResultMap(SQL).get(1).get("BILL_RATE");
        Assert.assertEquals(billRate, Integer.parseInt(clrRate)*1000 + "");
    }
}