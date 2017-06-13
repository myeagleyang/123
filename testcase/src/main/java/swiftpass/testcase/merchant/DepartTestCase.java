package swiftpass.testcase.merchant;

import java.util.HashMap;

public interface DepartTestCase {
	//部门管理——查询测试
	public void searchDepart(HashMap<String, String> params);
	
	//部门管理——新增测试
	public void addDepart(HashMap<String, String> params);
	
	//部门管理——编辑测试
	public void editDepart(HashMap<String, String> params);
}
