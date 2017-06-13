package swiftpass.report;

import java.util.Arrays;
import java.util.List;
import org.testng.ITestResult;
import org.testng.internal.Utils;

public class TestCaseDetail {
	private String realClassName;
	private String methodName;
	private String testName;
	private String status;
	private List<Object> params;
	private long startTime;
	private Throwable exception;
	
	public TestCaseDetail(ITestResult testResult){
		init(testResult);
	}
	
	public void init(ITestResult testResult){
		realClassName = testResult.getMethod().getRealClass().getName();
		methodName = testResult.getMethod().getMethodName();
		testName = realClassName + "." + methodName;
		switch(testResult.getStatus()){
		case ITestResult.FAILURE:
			status = "Failure"; break;
		case ITestResult.SUCCESS:
			status = "Pass"; break;
		case ITestResult.SKIP:
			status = "Skip"; break;
		};
		params = Arrays.asList(testResult.getParameters());
		startTime = testResult.getStartMillis();
		exception = testResult.getThrowable();
	}
	
	public String getThrowMessage(){
		if(exception == null) return "";
		List<String> messages = Arrays.asList(Utils.stackTrace(exception, true));
		StringBuilder sb = new StringBuilder();
		for(String message : messages){
			sb.append(message);
		}
		return sb.toString();
	}
	
	public String getRealClassName() {
		return realClassName;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getTestName() {
		return testName;
	}

	public String getStatus() {
		return status;
	}

	public List<Object> getParams() {
		return params;
	}
	
	public long getStartTime(){
		return startTime;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("TestName=" + testName).append(", ")
			.append("RealClass=" + realClassName + ", methodName=" + methodName).append(", ")
			.append("Params=" + params).append(", ")
			.append("Status=" + status).append(", ");
		if(!status.equals("Pass"))
			sb.append("Exception=" + getThrowMessage());
		return sb.toString();
	}
	
}
