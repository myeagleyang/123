package swiftpass.testcase.merchant;

import swiftpass.testcase.casebeans.DepartmentAddCaseBean;
import swiftpass.testcase.casebeans.DepartmentEditCaseBean;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;

public interface DepartmentTestCase {
	
	/**
	 * 部门查询
	 */
	public void searchDepartment(DepartmentSearchCaseBean caseBean);
	
	/**
	 * 部门添加
	 */
	public void addDepartment(DepartmentAddCaseBean caseBean);
	
	/**
	 * 部门编辑
	 */
	
	public void editDepartment(DepartmentEditCaseBean caseBean);
}
