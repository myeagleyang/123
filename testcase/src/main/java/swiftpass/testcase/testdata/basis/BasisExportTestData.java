package swiftpass.testcase.testdata.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.DataGenerator;
import swiftpass.testcase.testdata.ITestData;

public class BasisExportTestData implements ITestData{
	
	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"isSelectDateTime",
				"dateTime",
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
		successExportCase.replace("dateTime", DataGenerator.generateDateBaseOnNow(-1, 0, 0));
		successExportCase.replace("remark", RandomStringUtils.randomAlphanumeric(32));
		successExportCase.replace("isExport", "true");
		list.add(successExportCase);
		Map<String, String> defaultExportCase = caseMapWithCtrlParams();
		defaultExportCase.replace("remark", RandomStringUtils.randomAlphanumeric(32));
		defaultExportCase.replace("isExport", "true");
		list.add(defaultExportCase);
		Map<String, String> closePageCase = caseMapWithCtrlParams();
		closePageCase.replace("dateTime", DataGenerator.generateDateBaseOnNow(0, -3, -3));
		closePageCase.replace("isExport", "false");
		closePageCase.replace("message", "正常关闭");
		list.add(closePageCase);
		
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		return resultCaseMaps;
	}

	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		return oneCase;
	}
	
	public static void main(String...strings){
		for(Map<String, String>[] map : new BasisExportTestData().getAllTestData())
			System.err.println(new BasisExportTestData().preCheckProcess(map[0]));
	}
}
