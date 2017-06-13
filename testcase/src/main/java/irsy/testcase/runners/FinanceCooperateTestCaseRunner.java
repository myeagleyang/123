package irsy.testcase.runners;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import irsy.testcase.cooperate.FinanceCooperateTestCase;
import irsy.testcase.cooperate.FinanceCooperateTestCaseImpl;
import irsy.testcase.data.cooperate.CooperateTestData;
import swiftpass.testcase.runners.CaseRunner;
public class FinanceCooperateTestCaseRunner extends CaseRunner{
    @BeforeMethod
    public void refresh(){
//        driver.navigate().refresh();
//        menu.clickElement(MenuType.HM_IRSY);
//        menu.clickElement(MenuType.HM_CM_CA);
    	new WebDriverWait(driver, 3)
		.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("i融税银")));
        driver.findElement(By.partialLinkText("i融税银")).click();
        
       
        
    }
    
    @DataProvider(name = "channelTestData")
    public Object[][] channelTestData(Method method){
        HashMap<String, String>[][] params = null;
        if(method.getName().equals("runAudit")){
            
        } else if(method.getName().equals("runActive")){
        	
        }else if (method.getName().equals("runAdd"))
        {
        	params = CooperateTestData.getCooperateAddTestData();
        	
        }
        
        return params;
    }
    
/*    @Test(dataProvider = "channelTestData")//
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
    }*/
    @Test(dataProvider = "channelTestData")
    public void runAdd(HashMap<String, String> params){
    	logger.info("开始执行商户管理——部门管理——查询测试...");
//    	FinanceCooperateTestCase testcase = new FinanceCooperateTestCaseImpl(driver);
    	FinanceCooperateTestCaseImpl testcase = new FinanceCooperateTestCaseImpl(driver);
//		testcase.addMerchantPC(params);
//		testcase.addMerchantPC(params);
    	System.out.println(params);
    	testcase.runAdd(params);
		logger.info("商户管理——部门管理——查询测试结束.");
		
    }
    
}