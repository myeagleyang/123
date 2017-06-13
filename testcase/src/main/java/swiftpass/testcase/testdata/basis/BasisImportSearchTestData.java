package swiftpass.testcase.testdata.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.ExpectationInDB;
import swiftpass.testcase.testdata.ITestData;
import swiftpass.utils.services.BasisImportService;

public class BasisImportSearchTestData implements ITestData{

	@Override
	public Map<String, String> initParamsOnPage() {
		Map<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"beginCT",
				"endCT"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}

	@Override
	public Map<String, String> caseMapWithCtrlParams() {
		Map<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"expectedCount"
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
		
		Map<String, String> singleBeginCTCase = caseMapWithCtrlParams();
		singleBeginCTCase.replace("beginCT", DataGenerator.generateDateBaseOnNow(0, -1, -5));
		list.add(singleBeginCTCase);
		Map<String, String> singleEndCTCase = caseMapWithCtrlParams();
		singleEndCTCase.replace("endCT", DataGenerator.generateDateBaseOnNow(0, 3, 3));
		list.add(singleEndCTCase);
		Map<String, String> bothCTCase = caseMapWithCtrlParams();
		bothCTCase.replace("beginCT", DataGenerator.generateDateBaseOnNow(0, 0, -10));
		bothCTCase.replace("endCT", DataGenerator.generateDateBaseOnNow(0, 1, 0));
		list.add(bothCTCase);
		Map<String, String> defaultCase = caseMapWithCtrlParams();
		list.add(defaultCase);
		
		for(Map<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}

	private static boolean checkAllCount = false;
	@Override
	public Map<String, String> preCheckProcess(Map<String, String> oneCase) {
		if(checkAllCount == false){
			BasisImportService.batchImport(50);
		}
		oneCase.replace("expectedCount", ExpectationInDB.getBasisImportQueryCount(oneCase));
		return oneCase;
	}
	
	public static void main(String...strings){
		ITestData td = new BasisImportSearchTestData();
		for(Map<String, String>[] map : td.getAllTestData())
			System.out.println(td.preCheckProcess(map[0]));
	}
}
