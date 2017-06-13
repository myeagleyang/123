package swiftpass.testcase.merchant;

import java.util.HashMap;
import swiftpass.testcase.casebeans.*;
import swiftpass.testcase.casebeans.MerchantAAACaseBean;
import swiftpass.testcase.casebeans.MerchantActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantAuditCaseBean;
import swiftpass.testcase.casebeans.MerchantDetailCaseBean;
import swiftpass.testcase.casebeans.MerchantEditCaseBean;
import swiftpass.testcase.casebeans.MerchantSearchCaseBean;

public interface MerchantTestCase {
	
	public void searchMerchant(HashMap<String, String> params);
	
	public void addMerchant(HashMap<String, String> params);
	
	public void editMerchant(HashMap<String, String> params);
	
	public void auditMerchant(HashMap<String, String> params);
	
	public void activeMerchant(HashMap<String, String> params);
	
	public void auditAndActiveMerchant(HashMap<String, String> params);
	
	public void detailMerchant(HashMap<String, String> params);
	
	public void exportMerchant();
	
	//CaseBean重构-----------------------------------------
	
	public void runSearch(MerchantSearchCaseBean  searchBean);
	
	public void runAdd(MerchantAddCaseBean  addBean);

	public void checkPhone(MerchantAddCaseBean caseBean);
	
	public void runEdit(MerchantEditCaseBean caseBean);
	
	public void runAudit(MerchantAuditCaseBean caseBean);
	
	public void runActive(MerchantActiveCaseBean caseBean);
	
	public void runAndActiveMerchant(MerchantAAACaseBean caseBean);
	
	public void runDetail(MerchantDetailCaseBean caseBean);
	
	
	
	
	//兴业银行定制版模块
	public void runAdd_xy(MerchantAddCaseBean merchant);

	public void runDetail_xy(MerchantDetailCaseBean caseBean);
	
}
