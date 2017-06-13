package irsy.utils.dboperations;

import java.util.HashMap;
import org.apache.commons.lang3.RandomUtils;
import swiftpass.utils.DBUtil;

public class EmpDBOperations {
	public static HashMap<Integer, HashMap<String, String>> emps(){
		String sql = "select * from cms_emp where org_id in (select org_id from cms_org_relation)";
		return DBUtil.getQueryResultMap(sql);
	}
	
	public static HashMap<String, String> getRandomEmpOfChannel(String channelId){
		String sql = "Select * From CMS_EMP Where org_id = '$channelId'".replace("$channelId", channelId);
		HashMap<Integer, HashMap<String, String>> emps = DBUtil.getQueryResultMap(sql);
		try{
			return emps.get(RandomUtils.nextInt(1, emps.size()));
		} catch(IllegalArgumentException ex){
			return new HashMap<>();
		}
	}
	
	public static HashMap<String, String> getEmp(String empId){
		String sql = "select * from cms_emp where emp_id = '$id'".replace("$id", empId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
}
