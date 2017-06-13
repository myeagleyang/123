package swiftpass.testcase.batchintopiece;

import java.util.HashMap;

public interface BatchIntoPieceTestCase {
	public void doSearch(HashMap<String, String> params);
	
	public void doMerchantBIP(HashMap<String, String> params);
	
	public void doDeptBIP(HashMap<String, String> params);
	
	public void doStoreBIP(HashMap<String, String> params);
	
	public void doMerchantTemplatDownload();
	
	public void doDeptTemplateDownload();
	
	public void doStoreTemplateDownload();
	
	public void doRehandle(HashMap<String, String> params);

}
