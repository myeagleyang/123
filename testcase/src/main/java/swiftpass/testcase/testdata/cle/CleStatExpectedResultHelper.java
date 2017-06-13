package swiftpass.testcase.testdata.cle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import swiftpass.page.enums.CleanStatus;
import swiftpass.page.enums.CleanType;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;

public class CleStatExpectedResultHelper {
	/**
	 * 初步筛选出目标查询表
	 */
	public static final String QUERY_TARGET_TABLE;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("Select ")
			.append("b.acc_date").append(", ")
			.append("b.org_name").append(", ")
			.append("b.org_id").append(", ")
			.append("b.cleaning_type").append(", ")
			.append("l.cleaning_process_time").append(", ")
			.append("b.is_inline").append(", ")
			.append("b.payee_account_code").append(", ")
			.append("b.export_amount").append(", ")
			.append("b.pay_type_id").append(", ")
			.append("p.pay_type_name").append(", ")
			.append("b.cleaning_status").append(", ")
			.append("b.return_msg").append(", ")
			.append("b.serial_number").append(", ")
			.append("b.cleaning_date")
			.append(" From cle_cleaning_bill b, cle_cleaning_process_log l, tra_pay_type p")
			.append(" Where b.acc_task_id = l.acc_task_id And b.pay_type_id = p.pay_type_id And b.physics_flag = 1");
		QUERY_TARGET_TABLE = sb.toString();
	}
	public static void main(String...args){
		CleStatCaseBean b = new CleStatCaseBean();
		b.setChannelId("151590000001");//151590000001	--151520000002
//		b.setIsOnlyQueryChannel("true");
		CleStatResultBean c = queryCleResult(b);
		System.err.println(c);
		
	}
	public static CleStatResultBean queryCleResult(CleStatCaseBean td){
		CleStatResultBean result = new CleStatResultBean();
		String queryConditionStatement = queryConditions(
											td.getCleBeginTime(), 
											td.getCleEndTime(),
											CleanStatus.indexOf(StringUtils.isEmpty(td.getCleStatus()) ? "0" : td.getCleStatus()), 
											CleanType.indexOf(StringUtils.isEmpty(td.getCleType()) ? "0" : td.getCleType()), 
											td.getPayTypeId(), 
											td.getChannelId(), 
											td.getIsOnlyQueryChannel(), 
											td.getMerchantId(), 
											td.getIncomeAccount(), 
											td.getSerialNO(), 
											td.getFailReason());
		StringBuilder countSql = new StringBuilder("select count(*) from (");
		countSql.append(QUERY_TARGET_TABLE).append(") ").append(queryConditionStatement);
		StringBuilder statSql = new StringBuilder("select cleaning_status, count(*) 笔数, to_char(sum(export_amount), '9,999.99') 总金额 from (");
		statSql.append(QUERY_TARGET_TABLE).append(") ").append(queryConditionStatement)
				.append(" group by cleaning_status");
		SwiftLogger.debug("查询清分结果统计表的总条数： " + countSql.toString());
		Map<Integer, HashMap<String, String>> queryResult = DBUtil.getQueryResultMap(countSql.toString());
		result.setRecordCount(queryResult.get(1).get("COUNT(*)"));
		SwiftLogger.debug("查询清分结果的统计结果： " + statSql);
		queryResult = DBUtil.getQueryResultMap(statSql.toString());
		for(Entry<Integer, HashMap<String, String>> row : queryResult.entrySet()){
			switch(row.getValue().get("CLEANING_STATUS")){
			case "2":
				result.setCleanIngTotalStroke(row.getValue().get("笔数"));
				result.setCleanIngTotalAmount(row.getValue().get("总金额"));
				break;
			case "3":
				result.setCleanSuccTotalStroke(row.getValue().get("笔数"));
				result.setCleanSuccTotalAmount(row.getValue().get("总金额"));
				break;
			case "4":
				result.setCleanFailTotalStroke(row.getValue().get("笔数"));
				result.setCleanFailTotalAmount(row.getValue().get("总金额"));
				break;
			default: break;
			}
		}

		return result;
	}
	
	private static String queryConditions(String begin_cleaning_date, String end_cleaning_date, 
				CleanStatus clean_status, CleanType clean_type,
				String pay_type_id, String channel_id, String isOnlyChannel, String merchant_id, 
				String income_account, String serial_number, String fail_reason){
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(begin_cleaning_date)){
			sb.append("cleaning_date >= to_date('" + begin_cleaning_date + "', 'yyyy-mm-dd hh24:mi:ss') And ");
		}
		if(!StringUtils.isEmpty(end_cleaning_date)){
			sb.append("cleaning_date <= to_date('" + end_cleaning_date + "', 'yyyy-mm-dd hh24:mi:ss') And ");
		}
		if(null != clean_status && !clean_status.equals(CleanStatus.ALL)){
			sb.append("cleaning_status = " + clean_status.getSCode() + " And ");
		}
		if(null != clean_type && !clean_type.equals(CleanType.ALL)){
			sb.append("cleaning_type = " + clean_type.getSCode() + " And ");
		}
		if(!StringUtils.isEmpty(pay_type_id)){
			sb.append("pay_type_id = '" + pay_type_id + "' And ");
		}
		if(!StringUtils.isEmpty(channel_id)){
			sb.append("org_id in (" + getAllDescendantsOfChannel(channel_id, Boolean.parseBoolean(isOnlyChannel)) + ") And ");
		}
		if(!StringUtils.isEmpty(merchant_id)){
			sb.append("org_id = ')" + merchant_id + "' And ");
		}
		if(sb.length() > 0){
			sb.insert(0, " Where ");
			sb.delete(sb.lastIndexOf("And "), sb.length());
		}
		return sb.toString();
	}
	/**
	 * 获取某个渠道的所有子（孙）渠道（商户）
	 * @param channelId
	 * @param isOnlyChannel
	 * @return
	 */
	private static String getAllDescendantsOfChannel(String channelId, boolean isOnlyChannel){
		String query_all_channel_sql = "Select channel_id, parent_channel from cms_channel";
		String query_all_merchant_sql = "Select merchant_id, channel_id from cms_merchant";
		Map<Integer, HashMap<String, String>> channelIds = DBUtil.getQueryResultMap(query_all_channel_sql);
		Map<Integer, HashMap<String, String>> merchantIds = DBUtil.getQueryResultMap(query_all_merchant_sql);
		List<String> orgIds = new ArrayList<>();
		orgIds.add(channelId);
		List<String> currentParent = new ArrayList<>();
		currentParent.add(channelId);
		while(channelIds.size() > 0){
			List<String> tmp = new ArrayList<>();
			for(Entry<Integer, HashMap<String, String>> rowEntry : channelIds.entrySet()){
				if(currentParent.contains(rowEntry.getValue().get("PARENT_CHANNEL"))){
					tmp.add(rowEntry.getValue().get("CHANNEL_ID"));
				}
			}
			if(tmp.size() != 0){
				orgIds.addAll(tmp);
				currentParent.clear();
				currentParent.addAll(tmp);
			} else{
				break;
			}
		}
		/**
		 * 包括子（孙）商户
		 */
		if(!isOnlyChannel){
			List<String> mchIds = new ArrayList<>();
			for(Entry<Integer, HashMap<String, String>> rowEntry : merchantIds.entrySet()){
				if(orgIds.contains(rowEntry.getValue().get("CHANNEL_ID"))){
					mchIds.add(rowEntry.getValue().get("MERCHANT_ID"));
				}
			}
			orgIds.addAll(mchIds);
		}
		StringBuilder sb = new StringBuilder();
		for(String orgId : orgIds){
			sb.append(orgId);
			if(orgIds.indexOf(orgId) != orgIds.size() - 1)
				sb.append(", ");
		}
		return sb.toString();
	}
}
