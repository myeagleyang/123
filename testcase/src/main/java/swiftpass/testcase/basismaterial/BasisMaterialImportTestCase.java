package swiftpass.testcase.basismaterial;

import java.util.Map;

import swiftpass.testcase.testdata.ITestData;

public interface BasisMaterialImportTestCase {
	//	查询
	public void searchImport(Map<String, String> params, ITestData td);
	//	上传文件
	public void doImport(Map<String, String> params, ITestData td);
	//	废弃导入记录
//	public void destroyImport(Map<String, String> params);
	//	查看导入有处理失败的记录
	public void scanFailImportDetail(Map<String, String> params, ITestData td);
}
