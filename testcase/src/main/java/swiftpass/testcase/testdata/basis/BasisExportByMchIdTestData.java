package swiftpass.testcase.testdata.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import swiftpass.testcase.testdata.ITestData;

public class BasisExportByMchIdTestData implements ITestData{
	private final String NO_SET_IDS_MESSAGE = "请输入商户号";
	private final String BEYOND_IDS_MESSAGE = "FUCK.";
	private final String NORMAL_CLOSE_PAGE_MESSAGE = "正常关闭";
	
	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"mchIds",
				"remark"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		
		return map;
	}

	@Override
	public Map<String, String> caseMapWithCtrlParams() {
		Map<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isExport",
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String>[][] getAllTestData() {
		Map<String, String>[][] resultCaseMaps = null;
		List<Map<String, String>> list = new ArrayList<>();

		Map<String, String> successExportCase = caseMapWithCtrlParams();
		successExportCase.replace("mchIds", getMchIds(100));
		successExportCase.replace("remark", RandomStringUtils.randomAlphanumeric(32));
		successExportCase.replace("isExport", "true");
		list.add(successExportCase);
		Map<String, String> beyondMchCountCase = caseMapWithCtrlParams();
		beyondMchCountCase.replace("mchIds", getMchIds(101));
		beyondMchCountCase.replace("isExport", "true");
		beyondMchCountCase.replace("message", BEYOND_IDS_MESSAGE);
		list.add(beyondMchCountCase);
		Map<String, String> noSetIdsCase = caseMapWithCtrlParams();
		noSetIdsCase.replace("mchIds", getMchIds(0));
		noSetIdsCase.replace("isExport", "true");
		noSetIdsCase.replace("message", NO_SET_IDS_MESSAGE);
		list.add(noSetIdsCase);
		Map<String, String> normalCloseCase = caseMapWithCtrlParams();
		normalCloseCase.replace("isExport", "false");
		normalCloseCase.replace("message", NORMAL_CLOSE_PAGE_MESSAGE);
		list.add(normalCloseCase);
		
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}

	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		return oneCase;
	}
	
	private static String getMchIds(int count){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < count; i++){
			sb.append(RandomStringUtils.randomNumeric(12));
			if(i != count - 1)
				sb.append("-");
		}
		return sb.toString();
	}
	
	public static void main(String...strings){
		for(Map<String, String>[] map : new BasisExportByMchIdTestData().getAllTestData())
			System.out.println(new BasisExportByMchIdTestData().preCheckProcess(map[0]));
	}
}
