package swiftpass.testcase.testdata.tasklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.TaskListDBOperations;
import swiftpass.page.enums.TaskStatus;
import swiftpass.utils.DBUtil;
import swiftpass.utils.services.TaskListService;

public class TaskListStartOrShutDownTestData {
	public static HashMap<String, String>[][] getTaskListStartOrShutDownTestData(){
		return getAllTaskListStartOrShutDownTestData();
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("isClickFirstRow", "true");
		data.put("isConfirm", "true");
		data.put("usingStatus", TaskStatus.unUsing.getScode());  //标记任务是否启用，默认为不启用
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllTaskListStartOrShutDownTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
		oneCaseMap.replace("isClickFirstRow", "");
		oneCaseMap.replace("errorMsg", "请选择要执行的任务!");
		list.add(oneCaseMap);
		oneCaseMap = getCaseMapWithCrlParams();
		oneCaseMap.replace("isConfirm", "");
		oneCaseMap.replace("errorMsg", "正常取消");
		list.add(oneCaseMap);
		//任务状态-未启用
		oneCaseMap = getCaseMapWithCrlParams();
		list.add(oneCaseMap);
		//任务状态-启用
		oneCaseMap = getCaseMapWithCrlParams();
		oneCaseMap.replace("usingStatus", TaskStatus.using.getScode());
		list.add(oneCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		
		return resultCaseArray;
	}
	
	//根据任务状态生成需要的数据
	public static HashMap<String, String> getTaskInfo(String usingStatus){
		String sql = "select * from task_manage where status = $CONDITION".replace("$CONDITION", usingStatus);
		HashMap<String, String> taskInfo = DBUtil.getQueryResultMap(sql).get(1);
		if(taskInfo == null){
			String taskId = TaskListDBOperations.getTasks().get("TASK_ID");
			//调用修改任务状态接口生成需要的任务
			taskInfo = TaskListService.changeTaskStatus(taskId, usingStatus);
		}
		return taskInfo;
	}
	
	public static void main(String args[]){
		HashMap<String, String>[][] maps = getTaskListStartOrShutDownTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
		
//		HashMap<String, String> taskInfo = getTaskInfo("0");
//		System.err.println(taskInfo.get("TASK_ID"));
	}

}
