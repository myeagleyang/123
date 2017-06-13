package irsy.utils.dboperations;

import java.util.HashMap;

import swiftpass.utils.DBUtil;

public class TaskListDBOperations {
	
	public static HashMap<String, String> getTasks(){
		String sql = "select * from task_manage";
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	public static HashMap<String, String> getTaskInfo(String taskId){
		String sql = "select * from task_manage where task_id = '$taskId'".replace("$taskId", taskId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}

}
