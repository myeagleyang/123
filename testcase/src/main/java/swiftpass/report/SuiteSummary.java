package swiftpass.report;

import java.util.Map;
import java.util.Map.Entry;
import org.testng.ISuite;
import org.testng.ISuiteResult;

public class SuiteSummary {
	private String suiteName;
	private int all;
	private int pass;
	private int failure;
	private int skip;
	private ISuite x_suite;
	
	public SuiteSummary(ISuite suite){
		x_suite = suite;
		stat();
	}
	
	public String getSuiteName() {
		return suiteName;
	}

	public int getAll() {
		return all;
	}

	public int getPass() {
		return pass;
	}

	public int getFailure() {
		return failure;
	}

	public int getSkip() {
		return skip;
	}

	public ISuite getX_suite() {
		return x_suite;
	}

	private void stat(){
		Map<String, ISuiteResult> results = x_suite.getResults();
		for(Entry<String, ISuiteResult> result : results.entrySet()){
			SuiteTestSummary testSummary = new SuiteTestSummary(result.getValue());
			pass += testSummary.getPass();
			failure += testSummary.getFail();
			skip += testSummary.getSkip();
		}
		all = pass + failure + skip;
		suiteName = x_suite.getName();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SuiteName=" + suiteName).append(", ")
			.append("All=" + all).append(", ")
			.append("Pass=" + pass).append(", ")
			.append("Fail=" + failure).append(", ")
			.append("Skip=" + skip);
		return sb.toString();
	}
}
