package swiftpass.testcase.testdata.cle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class CleDisposeCleaningTestData {
	public static HashMap<String, String>[][] getCleDisposeCleaningTestData(){
		return getAllCleDisposeTesrData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("beginCT", "");
		data.put("endCT", "");
		
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllCleDisposeTesrData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> data = initPageParams();
		data.replace("beginCT", "2017-02-26");
		data.replace("endCT", "2017-02-28");
		list.add(data);
		data = initPageParams();
		data.replace("beginCT", "2017-02-26");
		data.replace("endCT", "2017-03-02");
		data.replace("errorMsg", "查询日期间隔不能超过3天 ！");
		list.add(data);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getCleDisposeCleaningTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
