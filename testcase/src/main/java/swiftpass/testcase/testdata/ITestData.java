package swiftpass.testcase.testdata;

import java.util.Map;

public interface ITestData {
	Map<String, String> initParamsOnPage();
	
	Map<String, String> caseMapWithCtrlParams();
	
	Map<String, String>[][] getAllTestData();
	
	Map<String, String> preCheckProcess(Map<String, String> oneCase);
}
