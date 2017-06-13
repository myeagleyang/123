package irsy.utils.dboperations;

import swiftpass.utils.DBUtil;

public class BasisExportDBOperations {
	public static int getAllCount(){
		String sql = "select count(*) from die_exp_task";
		String count = DBUtil.getQueryResultMap(sql).get(1).get("COUNT(*)");
		return Integer.parseInt(count);
	}
	
	public static String getFirstBatchNO(){
		String sql = "select exp_task_id from die_exp_task order by create_time desc";
		return DBUtil.getQueryResultMap(sql).get(1).get("EXP_TASK_ID");
	}
	

	public static String getFileName(String expTaskId){
		StringBuilder sql = new StringBuilder();
		sql.append("select exp_file_path from die_exp_task where exp_task_id = '").append(expTaskId).append("'");
		return DBUtil.getQueryResultMap(sql.toString()).get(1).get("EXP_FILE_PATH");
	}
}
