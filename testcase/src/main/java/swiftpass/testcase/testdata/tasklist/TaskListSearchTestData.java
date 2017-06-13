package swiftpass.testcase.testdata.tasklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ExpectationInDB;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;

public class TaskListSearchTestData {
	
	public static HashMap<String, String>[][] getSearchTaskListCaseMap(){
		return getAllTaskListSearchTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("taskName", "");
		data.put("taskGroup", "");
		return data;
	}
	
	private static HashMap<String, String> getCaseMapWithCtlParams(){
		HashMap<String, String> data = initPageParams();
		data.put("expect", "");
		return data;
	}
	
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllTaskListSearchTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		String[] taskNamesAndGroupNames = getTaskNamesAndGroupNames();
		//查询所有任务
		HashMap<String, String> searchAllCaseMap = getCaseMapWithCtlParams();
		list.add(searchAllCaseMap);
		//单条件查询
		HashMap<String, String> singleSearchCaseMap = getCaseMapWithCtlParams();
		singleSearchCaseMap.replace("taskName", taskNamesAndGroupNames[RandomUtils.nextInt(0, 2)]);
		list.add(singleSearchCaseMap);
		singleSearchCaseMap = getCaseMapWithCtlParams();
		singleSearchCaseMap.replace("taskGroup", taskNamesAndGroupNames[RandomUtils.nextInt(2, 4)]);
		list.add(singleSearchCaseMap);
		//组合查询
		HashMap<String, String> multiSearchCaseMap = getCaseMapWithCtlParams();
		multiSearchCaseMap.replace("taskName", taskNamesAndGroupNames[RandomUtils.nextInt(0, 2)]);
		multiSearchCaseMap.replace("taskGroup", taskNamesAndGroupNames[RandomUtils.nextInt(2, 4)]);
		list.add(multiSearchCaseMap);
		//把期望结果压入参数except
		for(HashMap<String, String> oneCaseMap: list){
			oneCaseMap.replace("expect", ExpectationInDB.getTaskListQueryCount(oneCaseMap)+"");
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCaseMap));
		}
			
		return resultCaseArray;
	}
	
	/**
	 * 从数据库获取所有任务名称中出现频率最高的一个字符以及一个随机的任务名称
	 * 从数据库获取所有分组名称中出现频率最高的一个字符以及一个随机的分组名称
	 * @return [随机任务名称，任务名称中出现频率最高字符，随机分组名称，分组名称中出现频率最高的字符]
	 * */
	private static String[] getTaskNamesAndGroupNames(){
		String getTaskNameSql = "select * from task_manage";
		HashMap<Integer, HashMap<String, String>> taskNameResults = DBUtil.getQueryResultMap(getTaskNameSql);
		StringBuffer sbTn = new StringBuffer("");
		StringBuffer sbGn = new StringBuffer("");
		for(Integer key: taskNameResults.keySet()){
			sbTn.append(taskNameResults.get(key).get("TASK_NAME"));
		}
		for(Integer key: taskNameResults.keySet()){
			sbGn.append(taskNameResults.get(key).get("GROUP_NAME"));
		}
		String maxCharTn = String.valueOf(SwiftPass.getAppearMaxCountChar(sbTn.toString()));
		String maxCharGn = String.valueOf(SwiftPass.getAppearMaxCountChar(sbGn.toString()));
		return ArrayUtils.toArray(taskNameResults.get(RandomUtils.nextInt(1, taskNameResults.size()+1)).get("TASK_NAME"), maxCharTn,
				taskNameResults.get(RandomUtils.nextInt(1, taskNameResults.size()+1)).get("GROUP_NAME"), maxCharGn);
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getSearchTaskListCaseMap();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}
}
