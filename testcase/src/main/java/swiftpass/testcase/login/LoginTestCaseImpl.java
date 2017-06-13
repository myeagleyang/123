package swiftpass.testcase.login;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import swiftpass.page.Page;
import swiftpass.page.login.LoginPage;
import swiftpass.testcase.TestCaseImpl;

public class LoginTestCaseImpl extends TestCaseImpl implements LoginTestCase{
	//页面
	private LoginPage page;

	public LoginTestCaseImpl(WebDriver driver) {
		super(driver);
		page = new LoginPage(this.driver);
	}

	@Override
	public void Login(HashMap<String, String> params) {
		logger.info("本地登录使用的测试数据是： " + params);
		String userName = params.get("userName");
		String password = params.get("password");
		String errorMessage = params.get("errorMessage");
		String expected = params.get("expected");
		
		//输入操作
		page.login(userName, password);
		if(expected.equals("fail")){
			Page.XClock.stop(2);
			Assert.assertEquals(page.getErrorMessage(), errorMessage);
		} else{
			Assert.assertEquals(page.getSuccessMessage(), userName);
		}
	}

}
