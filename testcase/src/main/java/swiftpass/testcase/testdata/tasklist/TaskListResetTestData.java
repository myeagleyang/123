package swiftpass.testcase.testdata.tasklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.TaskListDBOperations;
import swiftpass.page.enums.TaskRunStatus;
import swiftpass.utils.DBUtil;
import swiftpass.utils.services.TaskListService;

public class TaskListResetTestData {
	public static HashMap<String, String>[][] getResetTaskTestData(){
		return getAllResetTaskTestData();
	}
	
	private static HashMap<String, String> getCaseMapWithCtlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("isClickFirstRow", "true");
		data.put("isConfirm", "true");
		data.put("runStatus", TaskRunStatus.first.getScode()); //默认运行状态为初始状态
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllResetTaskTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> oneCaseMap = getCaseMapWithCtlParams();
		oneCaseMap.replace("isClickFirstRow", "");
		oneCaseMap.replace("errorMsg", "请选择要执行的任务!");
		list.add(oneCaseMap);
		oneCaseMap = getCaseMapWithCtlParams();
		oneCaseMap.replace("isConfirm", "");
		oneCaseMap.replace("errorMsg", "正常取消");
		list.add(oneCaseMap);
		//运行状态为初始状态
		oneCaseMap = getCaseMapWithCtlParams();
		oneCaseMap.replace("errorMsg", "当前任务运行状态已为初始状态");
		list.add(oneCaseMap);
		//运行状态为执行中
		oneCaseMap = getCaseMapWithCtlParams();
		oneCaseMap.replace("runStatus", TaskRunStatus.two.getScode());
		list.add(oneCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
	}
	
	//根据任务运行状态获取任务信息
	public static HashMap<String, String> getTaskInfo(String runStatus){
		String sql = "select * from task_manage where run_status = $CONDITION".replace("$CONDITION", runStatus);
		HashMap<String, String> taskInfo = DBUtil.getQueryResultMap(sql).get(1);
		if(taskInfo == null){
			String taskId = TaskListDBOperations.getTasks().get("TASK_ID");
			if(runStatus.equals("0")){
				//调用修改任务运行状态接口生成需要的任务
				taskInfo = TaskListService.initTaskRunStatus(taskId, runStatus);
			}else{//数据库中没有执行中的任务
				//直接操作数据库修改任务为执行中
				String sql_ = "update task_manage set run_status = $runStatus where task_id = '$taskId'"
						.replace("$runStatus", runStatus).replace("$taskId", taskId);
				DBUtil.executeUpdateSql(sql_);
				taskInfo = TaskListDBOperations.getTaskInfo(taskId);
			}
			
		}
		return taskInfo;
	}
	
	public static void main(String args[]){
//		HashMap<String, String>[][] maps = getreSetTaskTestData();
//		for(HashMap<String, String>[] map: maps){
//			System.out.println(map[0]);
//		}
		
		HashMap<String, String> task = getTaskInfo("1");
		System.out.println(task.get("TASK_ID"));
	}

}
