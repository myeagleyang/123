package swiftpass.testcase.runners;

import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swiftpass.testcase.login.LoginTestCase;
import swiftpass.testcase.login.LoginTestCaseImpl;
import swiftpass.utils.Configuration;
import swiftpass.utils.SPDriverFactory;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;

public class LoginTestCaseRunner{
	private WebDriver driver;
	private Logger logger;
	private Configuration conf;
	private String spServer;
	private String loginPath;
	
	@BeforeTest
	public void loginPreProcess(){
		this.logger = SwiftLogger.getLogger();
		logger.info("登录测试初始化...");
		this.conf = SwiftPass.getConfiguration("system.conf");
		this.spServer = conf.getValueOfKey("spServer");
		this.loginPath = conf.getValueOfKey("loginPath");
		this.driver = SPDriverFactory.create(conf.getValueOfKey("browserType"), Boolean.parseBoolean(conf.getValueOfKey("local")));
		this.driver.manage().window().maximize();
	}
	
	@BeforeMethod
	public void intoLoginPage(){
		this.driver.get(spServer + loginPath);
	}
	
	@DataProvider
	public Object[][] loginTestData(){
		HashMap<String, String>[][] params = null;
		//TODO
		return params;
	}
	
	@Test(dataProvider = "loginTestData")
	public void runLogin(HashMap<String, String> params){
		logger.info("开始执行登录测试......");
		LoginTestCase testcase = new LoginTestCaseImpl(driver);
		testcase.Login(params);
		logger.info("登录执行测试完成.");
	}
	
	@AfterTest
	public void tearDown(){
		this.driver.close();
		this.driver.quit();
	}
}
