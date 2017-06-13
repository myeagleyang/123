package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.page.MenuType;
import swiftpass.testcase.CaseBean;
import swiftpass.testcase.casebeans.ChannelAddCaseBean;
import swiftpass.testcase.casebeans.ChannelAddD0CaseBean;
import swiftpass.testcase.casebeans.ChannelEditCaseBean;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;
import swiftpass.testcase.channel.ChannelPayConfTestCase;
import swiftpass.testcase.channel.ChannelPayConfTestCaseImpl;
import swiftpass.testcase.channel.ChannelTestCase;
import swiftpass.testcase.channel.ChannelTestCaseImpl;
import swiftpass.testcase.testdata.channel.ChannelActiveTestData;
import swiftpass.testcase.testdata.channel.ChannelDetailTestData;
import swiftpass.testcase.testdata.channel.ChannelEATestData;
import swiftpass.testcase.testdata.channel.ChannelExamineTestData;
import swiftpass.testcase.testdata.channel.ChannelPCActiveTestData;
import swiftpass.testcase.testdata.channel.ChannelPCDetailTestData;
import swiftpass.testcase.testdata.channel.ChannelPCEditTestData;
import swiftpass.testcase.testdata.channel.ChannelSearchTestData;
import swiftpass.testcase.testdata.channel.XChannelAddD0TestData;
import swiftpass.testcase.testdata.channel.XChannelAddTestData;
import swiftpass.testcase.testdata.channel.XChannelEditTestData;
import swiftpass.testcase.testdata.channel.XChannelSearchTestData;
import swiftpass.testcase.testdata.channel.ChannelPCAddTestData;
public class ChannelTestCaseRunner extends CaseRunner{
    @BeforeMethod
    public void refresh(){
        driver.navigate().refresh();
        menu.clickElement(MenuType.HM_CM);
        menu.clickElement(MenuType.HM_CM_CA);
    }
    
    @DataProvider(name = "channelTestData")
    public Object[][] channelTestData(Method method){
        HashMap<String, String>[][] params = null;
        if(method.getName().equals("runAudit")){
            params = ChannelExamineTestData.getChannelExamineTestData();
        } else if(method.getName().equals("runActive")){
            params = ChannelActiveTestData.getChannelActiveTestData();
        } else if(method.getName().equals("runAAA")){
            params = ChannelEATestData.getChannelEATestData();
        } else if(method.getName().equals("runScanDetail")){
            params = ChannelDetailTestData.getChannelDetailTestData();
        } else if(method.getName().equals("runPCAdd")){
            params = ChannelPCAddTestData.getPayConfAddTestData();
        } else if(method.getName().equals("runPCEdit")){
            params = ChannelPCEditTestData.getChannelPayConfEditTestData();
        } else if(method.getName().equals("runPCActive")){
            params = ChannelPCActiveTestData.getChannelPayConfActiveTestData();
        } else if(method.getName().equals("runPCDetail")){
            params = ChannelPCDetailTestData.getChannelPayConfDetailTestData();
        }else if(method.getName().equals("runXYPCAdd")){
        	params = swiftpass.testcase.testdata.channel.xychannel.ChannelPCAddTestData.getChannelPCAddTestData();
        }else if(method.getName().equals("runXYPCEdit")){
        	params = swiftpass.testcase.testdata.channel.xychannel.ChannelPCEditTestData.getChannelPCEditTestData();
        }
        return params;
    }
    
    @Test(dataProvider = "channelTestData")//
    public void runAudit(HashMap<String, String> params){
        logger.info("开始执行渠道管理——渠道进件管理——审核测试用例......");
        ChannelTestCase testcase = new ChannelTestCaseImpl(this.driver);
        testcase.auditChannel(params);
        logger.info("渠道管理——渠道进件管理——审核测试用例执行结束.");
    }
    
    @Test(dataProvider = "channelTestData")
    public void runActive(HashMap<String, String> params){
        logger.info("开始执行渠道管理——渠道进件管理——激活测试用例......");
        ChannelTestCase testcase = new ChannelTestCaseImpl(this.driver);
        testcase.activeChannel(params);
        logger.info("渠道管理——渠道进件管理——激活测试用例执行结束.");
    }
    
    @Test(dataProvider = "channelTestData")//
    public void runAAA(HashMap<String, String> params){
        logger.info("开始执行渠道管理——渠道进件管理——审核并激活测试用例......");
        ChannelTestCase testcase = new ChannelTestCaseImpl(this.driver);
        testcase.aAAChannel(params);
        logger.info("渠道管理——渠道进件管理——审核并激活测试用例执行结束.");
    }
    
    @Test(dataProvider = "channelTestData")//
    public void runScanDetail(HashMap<String, String> params){
        logger.info("执行渠道管理——渠道进件管理——查看详情页面测试用例......");
        ChannelTestCase testcase = new ChannelTestCaseImpl(driver);
        testcase.scanChannelDetail(params);
        logger.info("渠道管理——渠道进件管理——查看详情页面测试用例执行结束.");
    }
    /**
     * 渠道进件——渠道支付类型配置管理测试用例执行
     */
    @Test(dataProvider = "channelTestData")//
    public void runPCAdd(HashMap<String, String> params){
        
        logger.info("开始执行渠道管理——渠道进件管理支付类型配置新增测试用例......");
        ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
        tc.addChannelPayConf(params);
        logger.info("渠道管理——渠道进件管理支付类型配置新增测试用例执行完成！");
    }
    
    @Test(dataProvider = "channelTestData")//
    public void runPCEdit(HashMap<String, String> params){
        
        logger.info("开始执行渠道管理——渠道进件支付类型配置编辑测试用例......");
        ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
        tc.editChannelPayConf(params);
        logger.info("渠道管理——渠道进件管理支付类型配置编辑测试用例执行完成！");
    }
    
    @Test(dataProvider = "channelTestData")//
    public void runPCActive(HashMap<String, String> params){
        
        logger.info("开始执行渠道管理——渠道进件管理支付类型配置激活测试用例......");
        ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
        tc.activeChannelPayConf(params);	
        logger.info("渠道管理——渠道进件管理支付类型配置激活测试用例执行完成！");
    }
    
    @Test(dataProvider = "channelTestData")
    public void runPCDetail(HashMap<String, String> params){
        
        logger.info("开始执行渠道管理——渠道进件管理支付类型配置详情测试用例......");
        ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
        tc.detailChannelPayConf(params);
        logger.info("渠道管理——渠道进件管理支付类型配置详情测试用例执行完成！");
    }
    
    @Test(dataProvider = "channelTestData")
    public void runXYPCAdd(HashMap<String, String> params){
    	 logger.info("开始执行渠道管理——渠道进件管理支付类型配置新增测试用例......");
         ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
         tc.addXYChannelPayConf(params);
         logger.info("渠道管理——渠道进件管理支付类型配置新增测试用例执行完成！");
    }
    
    @Test(dataProvider = "channelTestData")
    public void runXYPCEdit(HashMap<String, String> params){
    	 logger.info("开始执行渠道管理——渠道进件管理支付类型配置编辑测试用例......");
         ChannelPayConfTestCase tc = new ChannelPayConfTestCaseImpl(this.driver);
         tc.EditXYChannelPayConf(params);
         logger.info("渠道管理——渠道进件管理支付类型配置编辑测试用例执行完成！");
    }
    
    //-----------------CaseBean重构--------------------
    
  @Test(dataProvider = "runSearchData",dataProviderClass=ChannelTestCaseBeanData.class)
  public void runSearch(ChannelSearchCaseBean cases){
      logger.info("开始执行渠道管理——渠道进件管理——查询测试用例.....");
      ChannelTestCase testcase = new ChannelTestCaseImpl(driver);
      testcase.searchChannel(cases);
      logger.info("渠道管理——渠道进件管理——查询测试用例执行结束.");
  }
  
  @Test(dataProvider = "runAddData",dataProviderClass=ChannelTestCaseBeanData.class)//
  public void runAdd(CaseBean cases){
      logger.info("开始执行渠道管理——渠道进件管理——新增测试用例......");
      ChannelTestCase testcase = new ChannelTestCaseImpl(driver);
      testcase.addChannel((ChannelAddCaseBean)cases);
      logger.info("渠道管理——渠道进件管理——新增测试用例执行结束.");
  }
  
  @Test(dataProvider = "runEditData",dataProviderClass=ChannelTestCaseBeanData.class)//
  public void runEdit(CaseBean data){
      logger.info("开始执行渠道管理——渠道进件管理——编辑测试用例......");
      ChannelTestCase testcase = new ChannelTestCaseImpl(this.driver);
      testcase.editChannel((ChannelEditCaseBean)data);
      logger.info("渠道管理——渠道进件管理——编辑测试用例执行结束.");
  }
  @Test(dataProvider = "runAddD0Data",dataProviderClass=ChannelTestCaseBeanData.class)//
  public void runAddD0(ChannelAddD0CaseBean cases){
      logger.info("开始执行渠道管理——渠道进件管理——新增D0渠道测试用例......");
      ChannelTestCase testcase = new ChannelTestCaseImpl(this.driver);
      testcase.addD0Channel(cases);
      logger.info("渠道管理——渠道进件管理——新增D0渠道策划用例执行结束.");
  }
}