package swiftpass.testcase.testdata.tasklist;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

public class TaskListRunTestData {
	public static HashMap<String, String>[][] getTaskListRunTestData(){
		return getAllTaskListRunTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		//新增控制参数
		data.put("isClickFirstRow", "true");
		data.put("isConfirm", "true");
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllTaskListRunTestData(){
		HashMap<String, String>[][] resultCaseMapArray= null;
		HashMap<String, String>[] oneCaseMapArray= null;
		String needs[] = {"isClickFirstRow", "isConfirm"};
		String errorMsg[] = {"请选择要执行的任务!", "正常取消"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = initPageParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace("errorMsg", errorMsg[i]);
			oneCaseMapArray = ArrayUtils.add(oneCaseMapArray, oneCaseMap);
		}
		//执行成功用例
		HashMap<String, String> successCaseMap = initPageParams();
		oneCaseMapArray = ArrayUtils.add(oneCaseMapArray, successCaseMap);
		
		for(HashMap<String, String> oneCase: oneCaseMapArray){
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseMapArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getTaskListRunTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
