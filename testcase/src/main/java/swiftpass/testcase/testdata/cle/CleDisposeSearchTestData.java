package swiftpass.testcase.testdata.cle;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

public class CleDisposeSearchTestData {
	public static HashMap<String, String>[][] getCleDisposeTestData(){
		return getAllCleDisposeTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("beginCT", "");
		data.put("endCT", "");
		
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllCleDisposeTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		HashMap<String, String> data = initPageParams();
		data.replace("beginCT", "2017-02-26");
		data.replace("endCT", "2017-02-28");
		data.replace("errorMsg", "没有生成清分数据或生成的清分记录不为8条！");
		resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(data));
		return resultCaseArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getCleDisposeTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
