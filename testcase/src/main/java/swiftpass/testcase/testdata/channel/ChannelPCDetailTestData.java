package swiftpass.testcase.testdata.channel;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

public class ChannelPCDetailTestData {
	
	public static HashMap<String, String>[][] getChannelPayConfDetailTestData(){
		return getAllChannelPayConfDetailTestData();
	}
	
	public static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("isClickFirstRowChannel", "true");
		data.put("isClickFirstRowPC", "true");
		data.put("errorMsg", "");
		
		return data;
	}
	
	public static HashMap<String, String>[][] getAllChannelPayConfDetailTestData(){
		HashMap<String, String>[][] resultCasesMapArray = null;
		HashMap<String, String>[] casesArray = null;
		
		String[] needs = {"isClickFirstRowChannel", "isClickFirstRowPC"};
		String[] errorMsg = {"请选择您需要查看的记录!", "请选择您需要查看的记录!"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = initPageParamsMap();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace("errorMsg", errorMsg[i]);
			casesArray = ArrayUtils.add(casesArray, oneCaseMap);
		}
		
		//正常关闭的用例
		HashMap<String, String> oneCaseMap = initPageParamsMap();
		casesArray = ArrayUtils.add(casesArray, oneCaseMap);
		
		//组装用例
		for(HashMap<String, String> oneCase: casesArray){
			@SuppressWarnings("unchecked")
			HashMap<String, String>[] oneCaseArray = ArrayUtils.toArray(oneCase);
			resultCasesMapArray = ArrayUtils.add(resultCasesMapArray, oneCaseArray);
		}
		
		return resultCasesMapArray;
	}
	
	public static void main(String args[]){
		HashMap<String, String>[][] maps = getAllChannelPayConfDetailTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
