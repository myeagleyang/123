package irsy.utils.dboperations;

import java.util.HashMap;

import swiftpass.utils.DBUtil;

public class CleDBOperations {
	
	//获取数据库最新的的渠道日结任务
	public static HashMap<String, String> getCleChnAccTask(){
		String sql = "select * from CLE_CHANNEL_ACC_TASK t where t.acc_begin_time = to_date('2017-02-26','yyyy-mm-dd HH24:mi:ss') order by t.acc_task_id desc";
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	//根据taskId去获取cle_cleaning_bill信息
	public static HashMap<Integer, HashMap<String, String>> getCleCleaningBill(String taskId){
		String sql = "select * from cle_cleaning_bill where acc_task_id = '$taskId'".replace("$taskId", taskId);
		return DBUtil.getQueryResultMap(sql);
	}
	
	//根据taskId获取cle_cleaning_process_log信息
	public static HashMap<String, String> getCleCleaningLog(String taskId){
		String sql = "select * from cle_cleaning_process_log where acc_task_id = '$taskId'".replace("$taskId", taskId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	//预处理渠道日结
	public static HashMap<String, String> initChnAccTask(){
		HashMap<String, String> cleChnAccTask = getCleChnAccTask();
		if(cleChnAccTask != null){
			String taskId = cleChnAccTask.get("ACC_TASK_ID");
			String accStatus = cleChnAccTask.get("ACC_STATUS");
			if(accStatus.equals("2")){
				//删除cle_cleaning_bill及cle_cleaning_process_log表中相关数据
				String deleteCleBillSql = "delete cle_cleaning_bill where acc_task_id = '$taskId'"
						.replace("$taskId", taskId);
				DBUtil.executeUpdateSql(deleteCleBillSql);
				String deleteCleLog = "delete cle_cleaning_process_log where acc_task_id = '$taskId'"
						.replace("$taskId", taskId);
				DBUtil.executeUpdateSql(deleteCleLog);
			}
		}
		return cleChnAccTask;
	}

}
