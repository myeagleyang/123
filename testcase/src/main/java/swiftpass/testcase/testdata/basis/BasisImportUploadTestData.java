package swiftpass.testcase.testdata.basis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.BasisExportDBOperations;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.testcase.testdata.ITestData;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.BasisExportService;

public class BasisImportUploadTestData implements ITestData{
	@SuppressWarnings("unused")
	private static final String 
					UPLOAD_FAIL_MESSAGE = "基础资料包处理失败!",
					UPLOAD_REPEAT_MESSAGE = "该文件已上传过，不能重复上传！！！",
					UPLOAD_WRONG_FORMAT_MESSAGE = "文件类型必须是zip",
					UPLOAD_SUCCESS_MESSAGE = "文件上传成功，数据处理成功！！！";
	
	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"uploadFilePath"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}

	@Override
	public Map<String, String> caseMapWithCtrlParams() {
		Map<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String>[][] getAllTestData() {
		String fileSep = System.getProperty("file.separator");
		String fileDir = System.getProperty("user.dir") + "/resource/BasisImport".replace("/", fileSep);
		Map<String, String>[][] resultCaseMaps = null;
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> uploadSuccessCase = caseMapWithCtrlParams();
		uploadSuccessCase.replace("uploadFilePath", fileDir + "/uploadSuccessFile/success.zip".replace("/", fileSep));
		list.add(uploadSuccessCase);
		@SuppressWarnings({"rawtypes"})
		Map<String, String> uploadRepeatCase = SwiftPass.copy((HashMap)uploadSuccessCase);
		uploadRepeatCase.replace("message", UPLOAD_REPEAT_MESSAGE);
		list.add(uploadRepeatCase);
		Map<String, String> wrongFormatCase = caseMapWithCtrlParams();
		wrongFormatCase.replace("uploadFilePath", fileDir + "/uploadFailFiles/wrong_format_file.txt".replace("/", fileSep));
		wrongFormatCase.replace("message", UPLOAD_WRONG_FORMAT_MESSAGE);
		list.add(wrongFormatCase);
		Map<String, String> uploadFailCase = caseMapWithCtrlParams();
		uploadFailCase.replace("uploadFilePath", fileDir + "/uploadFailFiles/lackoffile.zip".replace("/", fileSep));
		uploadFailCase.replace("message", UPLOAD_FAIL_MESSAGE);
		list.add(uploadFailCase);
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}

	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		String message = oneCase.get("message");
		if(StringUtils.isEmpty(message)){
			String fileSep = System.getProperty("file.separator");
			String fileDir = System.getProperty("user.dir") + fileSep + "resource" + 
								fileSep + "BasisImport" + fileSep + "uploadSuccessFile";
			//调用接口下载一个文件到——资源文件目录中
			//1.先删除目录中的所有文件
			try {
				FileUtils.cleanDirectory(new File(fileDir));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//2.调用导出文件接口先生成导出记录
			BasisExportService.exportMultiBasis(1, 0, -3, -5);
			String expTaskId = BasisExportDBOperations.getFirstBatchNO();
			//3.下载这条最新生成的记录的文件
			BasisExportService.downloadBasisByExpTaskId(expTaskId, fileDir);
			//4.重命名文件为success.zip
			File[] file = FileUtils.getFile(fileDir).listFiles();
			if(file.length == 1){
				file[0].renameTo(new File(file[0].getParent() + fileSep + "success.zip"));
			}
			else
				throw new SwiftPassException("基础资料导入用例重命名文件： ", "失败！");
		}
		return oneCase;
	}
	
	public static void main(String...strings){
		for(Map<String, String>[] map : new BasisImportUploadTestData().getAllTestData()){
			System.err.println(map[0]);
			System.out.println(new BasisImportUploadTestData().preCheckProcess(map[0]));
		}

	}
}