package swiftpass.testcase.testdata.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.BasisImportDBOperations;
import swiftpass.testcase.testdata.ITestData;
import swiftpass.utils.services.BasisImportService;

public class BasisImportErrorDetailTestData implements ITestData{

	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"batchNO"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}

	@Override
	public Map<String, String> caseMapWithCtrlParams() {
		Map<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"expectedCount",
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String>[][] getAllTestData() {
		checkSystemImportCountAndProcess();
		Map<String, String>[][] resultCaseMaps = null;
		List<Map<String, String>> list = new ArrayList<>();
		
		Map<String, String> successLookupDetailCase = caseMapWithCtrlParams();
		String batchNO = BasisImportDBOperations.getFirstBatchNO();
		successLookupDetailCase.replace("batchNO", batchNO);
		list.add(successLookupDetailCase);
		
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}

	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		String expectedCount = String.valueOf(BasisImportDBOperations.getErrorDetailCount(oneCase.get("batchNO")));
		oneCase.replace("expectedCount", expectedCount);
		return oneCase;
	}
	
	private static void checkSystemImportCountAndProcess(){
		BasisImportService.batchImport(3);
	}
	
	public static void main(String...strings){
		for(Map<String, String>[] map : new BasisImportErrorDetailTestData().getAllTestData())
			System.out.println(new BasisImportErrorDetailTestData().preCheckProcess(map[0]));
	}
}