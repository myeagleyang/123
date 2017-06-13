package swiftpass.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class ReportInfo {
	private List<ISuite> x_suites;
	private List<SuiteSummary> suiteSummarys = new ArrayList<>();
	private List<TestCaseDetail> caseDetails = new ArrayList<>();
	private Map<String, List<SuiteTestSummary>> suiteTestSummarys = new HashMap<>();
	
	public ReportInfo(List<XmlSuite> xmlSuites, List<ISuite> suites){
		x_suites = suites;
		stat(xmlSuites, suites);
	}
		
	public List<ISuite> getX_suites() {
		return x_suites;
	}

	public List<SuiteSummary> getSuiteSummarys() {
		return suiteSummarys;
	}

	public List<TestCaseDetail> getCaseDetails() {
		return caseDetails;
	}

	public Map<String, List<SuiteTestSummary>> getSuiteTestSummarys() {
		return suiteTestSummarys;
	}

	private void stat(List<XmlSuite> xmlSuites, List<ISuite> suites){
		statCaseDetails();
		statSuiteTestSummarys();
		statSuiteSummarys();
	}
	
	private void statCaseDetails(){
		for(ISuite suite : x_suites){
			for(Entry<String, ISuiteResult> suiteResult : suite.getResults().entrySet()){
				ITestContext testContext = suiteResult.getValue().getTestContext();
				for(ITestResult testResult : testContext.getFailedTests().getAllResults())
					caseDetails.add(new TestCaseDetail(testResult));
				for(ITestResult testResult : testContext.getPassedTests().getAllResults())
					caseDetails.add(new TestCaseDetail(testResult));
				for(ITestResult testResult : testContext.getSkippedTests().getAllResults())
					caseDetails.add(new TestCaseDetail(testResult));
			}
		}
	}
	
	private void statSuiteTestSummarys(){
		for(ISuite suite : x_suites){
			List<SuiteTestSummary> testSummarys = new ArrayList<>();
			for(Entry<String, ISuiteResult> suiteResult : suite.getResults().entrySet()){
				SuiteTestSummary testSummary = new SuiteTestSummary(suiteResult.getValue());
				testSummarys.add(testSummary);
			}
			suiteTestSummarys.put(suite.getName(), testSummarys);
		}
	}
	
	private void statSuiteSummarys(){
		for(ISuite suite : x_suites){
			suiteSummarys.add(new SuiteSummary(suite));
		}
	}

}
