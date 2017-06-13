package irsy.utils.dboperations;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;

public class ExpectationInDB {
	/**
	 * @param beginCT	yyyy-mm-dd hh:mi:ss格式
	 * @param endCT
	 * @return
	 */
	public static String getCreateTimeCondition(String beginCT, String endCT){
		String str = "to_date('$date','yyyy-mm-dd HH24:mi:ss')";
		if(!StringUtils.isEmpty(beginCT) && StringUtils.isEmpty(endCT)){
			return "CREATE_TIME >= " + str.replace("$date", beginCT);
		} else if(!StringUtils.isEmpty(endCT) && StringUtils.isEmpty(beginCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT);
		} else if(!StringUtils.isEmpty(beginCT) && !StringUtils.isEmpty(endCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT) + " And " + 
					"CREATE_TIME >= " + str.replace("$date", beginCT);
		} else{
			return "";
		}
	}
	
	/**
	 * 给定列名及精确查询值，组装成精确查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getEqualCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" = '").append(value).append("' ");
		return res.toString();
	}
	/**
	 * 给定列名及模糊查询值，组装成模糊查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getLikeCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" Like '%").append(value).append("%'");
		return res.toString();
	}
	
	public static int getChannelQueryCount(Map<String, String> params){
		int result = 0;
		String sql = "Select count(*) From CMS_CHANNEL Where CHANNEL_TYPE = 2 $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder("And ");
		if(params.size() == 0)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
			String asCondition = getEqualCondition("ACTIVATE_STATUS", params.get("ACTIVATE_STATUS"));
			String esCondition = getEqualCondition("EXAMINE_STATUS", params.get("EXAMINE_STATUS"));
			String pcCondition = "";
			if(NumberUtils.isNumber(params.get("PARENT_CHANNEL")))
				pcCondition = getEqualCondition("PARENT_CHANNEL", params.get("PARENT_CHANNEL"));
			else if(!NumberUtils.isNumber(params.get("PARENT_CHANNEL")) && !StringUtils.isEmpty(params.get("PARENT_CHANNEL"))){
				String sql_ = "Select CHANNEL_ID From CMS_CHANNEL Where CHANNEL_NAME = '$N'".replace("$N", params.get("PARENT_CHANNEL"));
				String C_ID = DBUtil.getQueryResultMap(sql_).get(1).get("CHANNEL_ID");
				pcCondition = getEqualCondition("PARENT_CHANNEL", C_ID);
			}
			String cnCondition = getLikeCondition("CHANNEL_NAME", params.get("CHANNEL_NAME"));
			String ciCondition = getEqualCondition("CHANNEL_ID", params.get("CHANNEL_ID"));
			String ct_Condition = getEqualCondition("CHANNEL_PROPERTIES", params.get("CHANNEL_PROPERTIES"));
			if(!StringUtils.isEmpty(ctCondition))
				conditions.append(ctCondition).append(" And ");
			if(!StringUtils.isEmpty(asCondition))
				conditions.append(asCondition).append(" And ");
			if(!StringUtils.isEmpty(esCondition))
				conditions.append(esCondition).append(" And ");
			if(!StringUtils.isEmpty(pcCondition))
				conditions.append(pcCondition).append(" And ");
			if(!StringUtils.isEmpty(cnCondition))
				conditions.append(cnCondition).append(" And ");
			if(!StringUtils.isEmpty(ciCondition))
				conditions.append(ciCondition).append(" And ");
			if(!StringUtils.isEmpty(ct_Condition))
				conditions.append(ct_Condition).append(" And ");
			String c = conditions.delete(conditions.lastIndexOf("And "), conditions.length()).toString();
			result = DBUtil.getQueryResultRowCount(sql.replace("$CONDITIONS", c));
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", c)).get(1).get("COUNT(*)"));
		}
		return result;
	}

	public static int getEmpQueryCount(Map<String, String> params){
		int result = 0;
		String sql = "Select count(*) From CMS_EMP Where 1 = 1 $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder();
		if(params.size() == 0)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
			String eiCondition = getEqualCondition("EMP_CODE", params.get("empCode"));
			String enCondition = getLikeCondition("EMP_NAME", params.get("empName"));
			String pnCondition = getEqualCondition("MOBILE", params.get("phone"));
			String pcCondition = getEqualCondition("ORG_ID", params.get("orgId"));
			if(!StringUtils.isEmpty(ctCondition))
				conditions.append(" And ").append(ctCondition);
			if(!StringUtils.isEmpty(eiCondition))
				conditions.append(" And ").append(eiCondition);
			if(!StringUtils.isEmpty(enCondition))
				conditions.append(" And ").append(enCondition);
			if(!StringUtils.isEmpty(pnCondition))
				conditions.append(" And ").append(pnCondition);
			if(!StringUtils.isEmpty(pcCondition))
				conditions.append(" And ").append(pcCondition);
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		return result;
	}

	public static int getMerchantQueryCount(Map<String, String> params){
		int result = 0;
		String sql = "Select count(*) From CMS_MERCHANT Where 1 =1 $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder();
		if(params.size() == 0)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
			String aoCondition = getEqualCondition("ACCEPT_ORG_ID", params.get("ACCEPT_ORG_ID"));
			String pcCondition = getEqualCondition("CHANNEL_ID", params.get("CHANNEL_ID"));
			String mnCondition = getLikeCondition("MERCHANT_NAME", params.get("MERCHANT_NAME"));
			String miCondition = getEqualCondition("MERCHANT_ID", params.get("MERCHANT_ID"));
			String mtCondition = getEqualCondition("MERCHANT_TYPE", params.get("MERCHANT_TYPE"));
			String esCondition = getEqualCondition("EXAMINE_STATUS", params.get("EXAMINE_STATUS"));
			String asCondition = getEqualCondition("ACTIVATE_STATUS", params.get("ACTIVATE_STATUS"));
			String itCondition = getEqualCondition("INDUSTR_ID", params.get("INDUSTR_ID"));
			if(!StringUtils.isEmpty(ctCondition))
				conditions.append(" And ").append(ctCondition);
			if(!StringUtils.isEmpty(aoCondition))
				conditions.append(" And ").append(aoCondition);
			if(!StringUtils.isEmpty(pcCondition))
				conditions.append(" And ").append(pcCondition);
			if(!StringUtils.isEmpty(mnCondition))
				conditions.append(" And ").append(mnCondition);
			if(!StringUtils.isEmpty(miCondition))
				conditions.append(" And ").append(miCondition);
			if(!StringUtils.isEmpty(mtCondition))
				conditions.append(" And ").append(mtCondition);
			if(!StringUtils.isEmpty(esCondition))
				conditions.append(" And ").append(esCondition);
			if(!StringUtils.isEmpty(asCondition))
				conditions.append(" And ").append(asCondition);
			if(!StringUtils.isEmpty(itCondition)){
				String cmd = "And MERCHANT_DETAIL_ID IN (Select MERCHANT_DETAIL_ID From CMS_MERCHANT_DETAIL Where "
					+ itCondition + ")";
				conditions.append(cmd);
			}
			SwiftLogger.debug("商户查询语句为： " + sql.replace("$CONDITIONS", conditions.toString()));
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		return result;
	}

	public static int getDepartQueryCount(Map<String, String> params){
		SwiftLogger.debug("部门查询参数为： " + params);
		int result = 0;
		String sql = "Select count(*) From CMS_DEPT Where 1 = 1 $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder();
		if(params.size() == 0)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String dnCondition = getLikeCondition("DEPT_NAME", params.get("DEPT_NAME"));
			String bmCondition = getEqualCondition("MERCHANT_ID", params.get("MERCHANT_ID"));
			if(!StringUtils.isEmpty(dnCondition))
				conditions.append(" And ").append(dnCondition);
			if(!StringUtils.isEmpty(bmCondition))
				conditions.append(" And ").append(bmCondition);
			SwiftLogger.debug(conditions.toString());
			result = DBUtil.getQueryResultRowCount(sql.replace("$CONDITIONS", conditions.toString()));
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		
		return result;
	}

	public static int getStoreQueryCount(Map<String, String> params){
		int result = 0;
		String sql = "Select count(*) From CMS_MERCHANT Where MERCHANT_TYPE IN (13, 14) $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder("And ");
		if(params.size() == 0)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String bmCondition = getEqualCondition("PARENT_MERCHANT", params.get("bigMchId"));
			String dpCondition = getEqualCondition("DEPT_ID", params.get("departId"));
			String snCondition = getLikeCondition("MERCHANT_NAME", params.get("storeName"));
			String asCondition = getEqualCondition("ACTIVATE_STATUS", params.get("activateStatus"));
			String esCondition = getEqualCondition("EXAMINE_STATUS", params.get("examineStatus"));
			String siCondition = getEqualCondition("MERCHANT_ID", params.get("storeId"));
			if(!StringUtils.isEmpty(bmCondition))
				conditions.append(bmCondition).append(" And ");
			if(!StringUtils.isEmpty(dpCondition))
				conditions.append(dpCondition).append(" And ");
			if(!StringUtils.isEmpty(snCondition))
				conditions.append(snCondition).append(" And ");
			if(!StringUtils.isEmpty(asCondition))
				conditions.append(asCondition).append(" And ");
			if(!StringUtils.isEmpty(esCondition))
				conditions.append(esCondition).append(" And ");
			if(!StringUtils.isEmpty(siCondition))
				conditions.append(siCondition).append(" And ");
			String c = conditions.delete(conditions.lastIndexOf("And "), conditions.length()).toString();
			result = DBUtil.getQueryResultRowCount(sql.replace("$CONDITIONS", c));
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		return result;
	}
	
	public static int getTaskListQueryCount(Map<String, String> params){
		int result = 0;
		String sql = "select * from task_manage where 1=1 $CONDITION";
		StringBuilder conditions = new StringBuilder("And ");
		String tnCondition = getLikeCondition("TASK_NAME", params.get("taskName"));
		String gnCondition = getLikeCondition("GROUP_NAME", params.get("taskGroup"));
		if(!StringUtils.isEmpty(tnCondition))
			conditions.append(tnCondition).append(" And ");
		if(!StringUtils.isEmpty(gnCondition))
			conditions.append(gnCondition).append(" And ");
		String c = conditions.delete(conditions.lastIndexOf("And "), conditions.length()).toString();
		result = DBUtil.getQueryResultRowCount(sql.replace("$CONDITION", c));
		return result;
	}
	
	public static String getBasisExportQueryCount(Map<String, String> params){
		String sql = "select count(*) from die_exp_task where 1 = 1 $CONDITIONS";
		StringBuilder conditions = new StringBuilder("and ");
		String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
		if(!StringUtils.isEmpty(ctCondition)){
			conditions.append(ctCondition);
			sql = sql.replace("$CONDITIONS", conditions.toString());
		} else{
			sql = sql.replace("$CONDITIONS", "");
		}
		String sResult = DBUtil.getQueryResultMap(sql).get(1).get("COUNT(*)");
		return sResult;
	}
	
	public static String getBasisImportQueryCount(Map<String, String> params){
		String sql = "select count(*) from die_imp_task where imp_type = 1 $CONDITIONS";
		StringBuilder conditions = new StringBuilder("and ");
		String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
		if(!StringUtils.isEmpty(ctCondition)){
			conditions.append(ctCondition);
			sql = sql.replace("$CONDITIONS", conditions);
		} else{
			sql = sql.replace("$CONDITIONS", "");
		}
		String sResult = DBUtil.getQueryResultMap(sql).get(1).get("COUNT(*)");
		return sResult;
	}
	
	public static String getBatchIntoPieceQueryCount(HashMap<String, String> params){
		String sql = "select count(*) from die_imp_task where imp_type in (5, 6, 7) $CONDITION";
		StringBuilder condition = new StringBuilder("and ");
		String ctCondition = getCreateTimeCondition(params.get("beginCT"), params.get("endCT"));
		if(!StringUtils.isEmpty(ctCondition)){
			condition.append(ctCondition);
			sql = sql.replace("$CONDITION", condition);
		}else{
			sql = sql.replace("$CONDITION", "");
		}
		System.err.println(sql);
		String result = DBUtil.getQueryResultMap(sql).get(1).get("COUNT(*)");
		return result;
	}
}