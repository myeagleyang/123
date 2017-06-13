package swiftpass.testcase.testdata.batchintopiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.ExpectationInDB;

public class BIPSearchTestData {
	public static HashMap<String, String>[][] getBIPSearchTestData(){
		return getAllBIPSearchTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("beginCT", "");
		data.put("endCT", "");
		return data;
	}
	
	private static HashMap<String, String> getPageWithCrlParams(){
		HashMap<String, String> data = initPageParams();
		data.put("expected", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllBIPSearchTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		String startTime = getStartTime();
		String endTime = getEndTime();
		//查询所有数据
		HashMap<String, String> searchAllDataCaseMap = getPageWithCrlParams();
		list.add(searchAllDataCaseMap);
		//单条件查询
		HashMap<String, String> oneCaseMap = getPageWithCrlParams();
		oneCaseMap.replace("beginCT", startTime);
		list.add(oneCaseMap);
		oneCaseMap = getPageWithCrlParams();
		oneCaseMap.replace("endCT", endTime);
		list.add(oneCaseMap);
		//组合查询
		oneCaseMap = getPageWithCrlParams();
		oneCaseMap.replace("beginCT", startTime);
		oneCaseMap.replace("endCT", endTime);
		list.add(oneCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			oneCase.replace("expected", ExpectationInDB.getBatchIntoPieceQueryCount(oneCase));
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
	}
	
	private static String getStartTime(){
		return DataGenerator.generateDateBaseOnNow(0, 0, -5);
	}
	
	private static String getEndTime(){
		return DataGenerator.generateDateBaseOnNow(0, 0, 5);
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getBIPSearchTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
