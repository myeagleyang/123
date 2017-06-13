package swiftpass.testcase.runners;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import swiftpass.page.MenuPage;
import swiftpass.testcase.RunCaseProcessor;
import swiftpass.utils.SwiftLogger;

public class CaseRunner {
	protected WebDriver driver;
	protected Logger logger;
	protected MenuPage menu;
	
	@BeforeClass(description = "测试用例初始化...")
	public void initProcess(){
		this.driver = RunCaseProcessor.driver;
		this.logger = SwiftLogger.getLogger();
		this.menu = new MenuPage(driver);
	}
}
