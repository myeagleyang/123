package irsy.testcase.cooperate;

import java.util.HashMap;

import irsy.testcase.casebeans.CooperateAddCaseBean;
import swiftpass.testcase.casebeans.MerchantPCActiveCaseBean;
import swiftpass.testcase.casebeans.MerchantPCAddCaseBean;
import swiftpass.testcase.casebeans.MerchantPCEditCaseBean;

public interface FinanceCooperateTestCase {
	
//	public void addMerchantPC(HashMap<String, String> params);
//	
//	public void editMerchantPC(HashMap<String, String> params);
//	
//	public void activeMerchantPC(HashMap<String, String> params);
//	
//	public void detailMerchantPC(HashMap<String, String> params);

	public void runAdd(HashMap<String, String> params);
	
	public void runCooperateAdd(HashMap<String, String> params);
	
	//---------------------CaseBean数据驱动重构--------------------
	
//	public void runAddPC(MerchantPCAddCaseBean caseBean);
//	
//	public void runEditPC(MerchantPCEditCaseBean caseBean);
//	
//	public void runActivePC(MerchantPCActiveCaseBean caseBean);
//	
//	public void runAdd(CooperateAddCaseBean caseBean);

	
	
//	public void runDetailPC(MerchantPCDetailCaseBean caseBean);
//	public void runDetailPC(MerchantPCDetailCaseBean caseBean);
//	public void runDetailPC(MerchantPCDetailCaseBean caseBean);

}
