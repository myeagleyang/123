package swiftpass.testcase.basismaterial;

import java.util.Map;

import swiftpass.testcase.testdata.ITestData;

public interface BasisMaterialExportTestCase {
	void searchHistoryExportData(Map<String, String> params, ITestData td);
	
	void exportHistoryExportData(Map<String, String> params, ITestData td);
	
	void downloadOneHistoryExportData(Map<String, String> params, ITestData td);
	
	void exportHistoryExportDataByMchIds(Map<String, String> params, ITestData td);
}