package swiftpass.report;

import org.testng.ISuiteResult;
import org.testng.ITestContext;

public class SuiteTestSummary {
	private ISuiteResult x_result;
	private String suiteTestName;
	private int all;
	private int fail;
	private int pass;
	private int skip;
	private long startTime;
	
	public SuiteTestSummary(ISuiteResult suiteResult){
		x_result = suiteResult;
		suiteTestName = x_result.getTestContext().getName();
		init(x_result);
		startTime = x_result.getTestContext().getStartDate().getTime();
	}
	
	private void init(ISuiteResult suiteResult){
		ITestContext context = suiteResult.getTestContext();
		fail = context.getFailedTests().size();
		pass = context.getPassedTests().size();
		skip = context.getSkippedTests().size();
		all = fail + pass + skip;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SuiteTestName=" + suiteTestName).append(", ")
			.append("All=" + all).append(", ")
			.append("Pass=" + pass).append(", ")
			.append("Fail=" + fail).append(", ")
			.append("Skip=" + skip);
		return sb.toString();
	}

	public ISuiteResult getX_result() {
		return x_result;
	}

	public String getSuiteTestName() {
		return suiteTestName;
	}

	public int getAll() {
		return all;
	}

	public int getFail() {
		return fail;
	}

	public int getPass() {
		return pass;
	}

	public int getSkip() {
		return skip;
	}
	
	public long getStartTime(){
		return startTime;
	}
}
