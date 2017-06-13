package swiftpass.testcase.channel;

import java.util.HashMap;

import swiftpass.testcase.casebeans.EmpAddCaseBean;
import swiftpass.testcase.casebeans.EmpEditCaseBean;
import swiftpass.testcase.casebeans.EmpSearchCaseBean;

public interface EmpTestCase {
	//业务员查询
	public void searchEmp(HashMap<String, String> params);
	
	//业务员新增
	public void addEmp(HashMap<String, String> params);
	
	//业务员编辑
	public void editEmp(HashMap<String, String> params);
	
	//导出测试用例
	public void exportEmp();
	
	// ------------------caseBean重构-------------------
	//业务员查询
	public void searchEmp(EmpSearchCaseBean caseBean);
	
	//业务员新增
	public void addEmp(EmpAddCaseBean caseBean);
	
	//业务员编辑
	public void editEmp(EmpEditCaseBean caseBean);
}
