package irsy.utils.dboperations;

import swiftpass.utils.DBUtil;

public class BasisImportDBOperations {
	public static int getAllCount(){
		String sql = "select count(*) from die_imp_task";
		return Integer.parseInt(DBUtil.getQueryResultMap(sql).get(1).get("COUNT(*)"));
	}
	
	public static String getFirstBatchNO(){
		String sql = "select imp_task_id from die_imp_task order by create_time desc";
		return DBUtil.getQueryResultMap(sql).get(1).get("IMP_TASK_ID");
	}
	
	public static int getErrorDetailCount(String impTaskId){
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) from die_imp_task_erroe_detail where imp_task_id = '").append(impTaskId).append("'");
		return Integer.parseInt(DBUtil.getQueryResultMap(sql.toString()).get(1).get("COUNT(*)"));
	}
	
}
