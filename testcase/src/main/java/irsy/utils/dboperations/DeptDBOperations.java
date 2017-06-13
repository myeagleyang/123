package irsy.utils.dboperations;

import java.util.HashMap;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.DBUtil;
import swiftpass.utils.dboperations.MerchantDBOperations;

public class DeptDBOperations {
	public static HashMap<Integer, HashMap<String, String>> allDeparts(){
		String sql = "select * from cms_dept";
		return DBUtil.getQueryResultMap(sql);
	}
	
	public static HashMap<String, String> parentBigMerchant(String deptId){
		HashMap<String, String> depart = getDepart(deptId);
		String merchantId = depart.get("MERCHANT_ID");
		return MerchantDBOperations.getMerchant(merchantId);
	}
	
	public static HashMap<String, String> getDepart(String deptId){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_dept where dept_id = '").append(deptId).append("'");	
		return DBUtil.getQueryResultMap(sql.toString()).get(1);
	}
	
	public static HashMap<String, String> getDeptByName(String deptName){
		String sql = "select * from cms_dept where dept_name = '$deptName'".replace("$deptName", deptName);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	public static HashMap<Integer, HashMap<String, String>> queryBigMerchantWithES(ExamineStatus es){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant where merchant_type = 11 ")
			.append("and examine_status = '").append(es.getSCode()).append("'");
		return DBUtil.getQueryResultMap(sql.toString());
	}
	
	public static HashMap<Integer, HashMap<String, String>> attachedDepartMerchantWithES(ExamineStatus es){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant where merchant_id in ").append("(")
			.append("select distinct merchant_id from cms_dept").append(") ");
		if(null != es)
			sql.append("and examine_status = '").append(es.getSCode()).append("'");
		return DBUtil.getQueryResultMap(sql.toString());
	}
	/**
	 * 返回指定大商户ID的所挂靠的所有一级部门
	 * @param merchantId
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> getFirstLevelDepart(String merchantId){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_dept where parent_dept is null and merchant_id = '").append(merchantId).append("'");
		return DBUtil.getQueryResultMap(sql.toString());
	}
}
