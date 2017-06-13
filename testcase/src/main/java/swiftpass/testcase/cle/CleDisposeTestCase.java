package swiftpass.testcase.cle;

import java.util.HashMap;

public interface CleDisposeTestCase {
	
	void searchCleData(HashMap<String, String> params);
	
	void disposeCleaning(HashMap<String, String> params);
	
	void downloadCleFile(HashMap<String, String> params);

}
