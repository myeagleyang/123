package irsy.testcase;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;

public class FailTakeScreenshotListener extends TestListenerAdapter{
	@Override
	public void onTestFailure(ITestResult tr){
		super.onTestFailure(tr);
		String savedName = "_fail_" + tr.getInstanceName() + "-" + tr.getName();
		SwiftPass.seleniumTakeScreenshot(RunCaseProcessor.driver, savedName, true);
		SwiftLogger.error(savedName + ">>>>>>>" + tr.toString());
	}
}
