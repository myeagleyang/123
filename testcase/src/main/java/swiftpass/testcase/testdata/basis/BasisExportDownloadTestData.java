package swiftpass.testcase.testdata.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.BasisExportDBOperations;
import swiftpass.testcase.testdata.ITestData;

public class BasisExportDownloadTestData implements ITestData{

	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"batchNO"
		};
		for(String valueKey : valueKeys){
			map.put(valueKey, "");
		}
		return map;
	}

	@Override
	public Map<String, String> caseMapWithCtrlParams() {
		Map<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"message"
		};
		for(String ctrlKey : ctrlKeys){
			map.put(ctrlKey, "");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String>[][] getAllTestData() {
		Map<String, String>[][] resultCaseMaps = null;
		List<Map<String, String>> list = new ArrayList<>();
		
		Map<String, String> successDownloadCase = caseMapWithCtrlParams();
		successDownloadCase.replace("batchNO", BasisExportDBOperations.getFirstBatchNO());
		list.add(successDownloadCase);
		
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}

	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		return oneCase;
	}
	
}