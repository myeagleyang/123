package swiftpass.testcase.testdata.tasklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import swiftpass.utils.SwiftPass;

public class TaskListAddTestData {
	public static HashMap<String, String>[][] getTaskListAddTestData(){
		return getAllTaskListAddTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("taskId", "");
		data.put("taskName", "");
		data.put("groupName", "");
		data.put("timeExpress", "");
		data.put("interfaceUrl", "");
		data.put("threadCount", "");
		data.put("extraParam", "");
		data.put("isEnable", "");
		
		return data;
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = initPageParams();
		String randomTask = RandomStringUtils.randomAlphanumeric(4) + SwiftPass.getHHmmssSSSString().substring(4);
		data.replace("taskId", randomTask);
		data.replace("taskName", randomTask);
		data.replace("groupName", randomTask);
		data.replace("timeExpress", RandomStringUtils.randomNumeric(6));
		data.replace("interfaceUrl", "http://www.baidu.com");
		data.replace("threadCount", "1");
		data.replace("extraParam", RandomStringUtils.randomAlphabetic(6));
		data.replace("isEnable", "启用");
		
		//新增控制参数
		data.put("isSelectEnable", "true");
		data.put("isSave", "true");
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllTaskListAddTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		String needs[] = {"taskId", "taskName", "timeExpress", "interfaceUrl", "threadCount", "isSelectEnable", "isSave"};
		String errorMsg[] = {"请输入任务编号", "请输入任务名称", "请输入任务执行时间", "请输入任务调用接口url", "请输入任务调用线程数",
				"请选择是否启用", "正常关闭"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace("errorMsg", errorMsg[i]);
			list.add(oneCaseMap);
		}
		//异常用例  1.任务编号的长度为6-32位 2.任务名称的长度为6-32位 3.分组名称的长度为6-32位
		//		  4.时间表达式的长度为6-32位(2条用例) 5.接口url格式错误或长度不为1-128位 6.线程数过多 
		String randomLongString = RandomStringUtils.randomAlphanumeric(33);
		HashMap<String, String> illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("taskId", randomLongString);
		illegelCaseMap.replace("errorMsg", "任务编号的长度为6-32位");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("taskName", randomLongString);
		illegelCaseMap.replace("errorMsg", "任务名称的长度为6-32位");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("groupName", randomLongString);
		illegelCaseMap.replace("errorMsg", "分组名称的长度为6-32位");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("timeExpress", randomLongString);
		illegelCaseMap.replace("errorMsg", "时间表达式的长度为6-32位");
		list.add(illegelCaseMap);
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("timeExpress", RandomUtils.nextInt(1, 6) + "");
		illegelCaseMap.replace("errorMsg", "时间表达式的长度为6-32位");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("interfaceUrl", RandomStringUtils.randomAlphabetic(6));
		illegelCaseMap.replace("errorMsg", "接口url格式错误或长度不为1-128位");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("threadCount", RandomUtils.nextInt(10, 1000) + "");
		illegelCaseMap.replace("errorMsg", "线程数过多");
		list.add(illegelCaseMap);
		
		//新增成功用例及异常用例 1.任务编号已经存在 2.任务名称已经存在 
		HashMap<String, String> successCaseMap = getCaseMapWithCrlParams();
		list.add(successCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("taskId", successCaseMap.get("taskId"));
		illegelCaseMap.replace("errorMsg", "任务编号已经存在");
		list.add(illegelCaseMap);
		
		illegelCaseMap = getCaseMapWithCrlParams();
		illegelCaseMap.replace("taskName", successCaseMap.get("taskName"));
		illegelCaseMap.replace("errorMsg", "任务名称已经存在");
		list.add(illegelCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getTaskListAddTestData();
		System.out.println(maps.length);
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
