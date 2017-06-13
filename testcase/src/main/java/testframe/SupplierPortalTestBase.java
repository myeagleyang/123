package testframe;


import java.lang.reflect.Method;  
 
import org.testng.ITestContext;  
import org.testng.annotations.AfterSuite;  
import org.testng.annotations.BeforeMethod;  
import org.testng.xml.XmlTest;  
 
import ec.qa.autotest.ui.constants.PropertiesKeys;  
import ec.qa.autotest.ui.utility.CookiesUtil;  
import ec.qa.autotest.ui.utility.PropertiesUtil;  
 
/**  
* @author xin.wang 供应商门户网站测试基类  
*/  
public class SupplierPortalTestBase extends TestBase {  
   @BeforeMethod(alwaysRun = true)  
   public void initDriver(ITestContext tc, Method m, XmlTest xt) throws Exception {  
       ConfigDriverParameters cp = new ConfigDriverParameters();  
       cp.setPageLoadTime(Long.parseLong(PropertiesUtil.getProValue(PropertiesKeys.SUPPLIER_PORTAL_PAGELOAD_TIME)));  
       cp.setTargetWebSite(PropertiesUtil.getProValue(PropertiesKeys.SUPPLIER_PORTAL_AUTO_TEST_WEBSITE));  
       cp.setSerachElementTime(Long.parseLong(PropertiesUtil.getProValue(PropertiesKeys.IMPLICITLYWAIT_TIME)));  
       cp.setTestMethod(m);  
       preCondition(cp);  
   }  
 
   @AfterSuite(alwaysRun = true)  
   public void terminTestSuite(XmlTest xt, ITestContext tc) {  
       CookiesUtil.setCk(null);  
   }  
 
   public void beforeClass(XmlTest xt, ITestContext tc) {  
       // TODO Auto-generated method stub  
 
   }  
 
   public void afterClass(XmlTest xt, ITestContext tc) {  
       // TODO Auto-generated method stub  
 
   }  
 
   public void beforeTest(XmlTest xt, ITestContext tc) {  
       // TODO Auto-generated method stub  
 
   }  
 
   public void afterTest(XmlTest xt, ITestContext tc) {  
       // TODO Auto-generated method stub  
 
   }  
}  