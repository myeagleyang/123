package swiftpass.testcase;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import irsy.utils.Configuration;
import irsy.utils.SwiftLogger;
import irsy.utils.SwiftPass;

public class TestCaseImpl {
	protected WebDriver driver;
	protected Logger logger;
	protected Configuration conf;
	
	public TestCaseImpl(WebDriver driver){
		this.driver = driver;
		this.logger = SwiftLogger.getLogger();
		this.conf = SwiftPass.getConfiguration("system.conf");
	}
	
	public String getConfValue(String key){
		return this.conf.getValueOfKey(key);
	}

}
