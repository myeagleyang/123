package swiftpass.testcase.merchant;

import java.util.HashMap;

import swiftpass.testcase.casebeans.StoreAddCaseBean;
import swiftpass.testcase.casebeans.StoreDetailCaseBean;
import swiftpass.testcase.casebeans.StoreEditCaseBean;
import swiftpass.testcase.casebeans.StoreQRCodeCaseBean;
import swiftpass.testcase.casebeans.StoreSearchCaseBean;

public interface StoreTestCase {
	//商户管理——门店管理-查询测试用例
	public void searchStore(HashMap<String, String> params);
	//商户管理——门店管理-新增测试用例
	public void addStore(HashMap<String, String> params);
	//商户管理——门店管理-编辑测试用例
	public void editStore(HashMap<String, String> params);
	//商户管理——门店管理-查看详情测试用例
	public void scanStoreDetail(HashMap<String, String> params);
	//商户管理——门店管理-查看二维码测试用例
	public void scanQRCode(HashMap<String, String> params);
	//商户管理——门店管理-导出测试用例
	public void exportStore();
	
	
	//------------------caseBean重构-------------------
	//商户管理——门店管理-查询测试用例
		public void runSearch(StoreSearchCaseBean caseBean);
		//商户管理——门店管理-新增测试用例
		public void runAdd(StoreAddCaseBean caseBean);
		//商户管理——门店管理-编辑测试用例
		public void runEdit(StoreEditCaseBean caseBean);
		//商户管理——门店管理-查看详情测试用例
		public void runDetail(StoreDetailCaseBean caseBean);
		//商户管理——门店管理-查看二维码测试用例
		public void runQRCode(StoreQRCodeCaseBean caseBean);
}
