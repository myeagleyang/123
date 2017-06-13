package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.utils.DBUtil;

public class ChannelDetailTestData {
	public static HashMap<String, String>[][] getChannelDetailTestData(){
		return getAllChannelDetailTestData();
	}
	
	private static String[] fields = {
		"PARENT_CHANNEL", "CHANNEL_NAME", "CHANNEL_ID", "INVITE_CODE", "CHANNEL_PROPERTIES", "PROVINCE", "CITY", "ADDRESS", "PRINCIPAL", "TEL",
		"EMAIL", "REMARK", "EXAMINE_EMP", DBUtil.getTo_Char("EXAMINE_TIME"), "ACTIVATE_EMP", DBUtil.getTo_Char("ACTIVATE_TIME"), "THR_CHANNEL_ID", "EXAMINE_STATUS_REMARK", "ACTIVATE_STATUS_REMARK"
	};
	
	public static HashMap<String, String> getChannelDetailFromDB(String CHANNEL_ID){
		HashMap<String, String> result = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(String.join(", ", fields))
			.append(" from cms_channel where channel_id = '").append(CHANNEL_ID).append("'");
		result = DBUtil.getQueryResultMap(sql.toString()).get(1);
		String parentChannel = result.get("PARENT_CHANNEL");
		sql.delete(0, sql.length());
		sql.append("select channel_name from cms_channel where channel_id = '").append(parentChannel).append("'");
		result.replace("PARENT_CHANNEL", DBUtil.getQueryResultMap(sql.toString()).get(1).get("CHANNEL_NAME"));
		return result;
	}
	
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> map = new HashMap<>();
		//	特么的没呢
		
		return map;
	}
	
	private static HashMap<String, String> getCaseMapWithCtrlParams(){
		HashMap<String, String> data = initPageParamsMap();
		HashMap<Integer, HashMap<String, String>> waitScanChannels = ChannelDBOperations.allDBChannels();
		HashMap<String, String> waitScanChannel = waitScanChannels.get(RandomUtils.nextInt(1, waitScanChannels.size()));
		data.put("CHANNEL_ID", waitScanChannel.get("CHANNEL_ID"));
		data.put("CHANNEL_NAME", waitScanChannel.get("CHANNEL_NAME"));
		data.put("TEXT", String.join("-", data.get("CHANNEL_ID"), data.get("CHANNEL_NAME")));
		data.put("message", "");
		
		return data;
	}
	
	private static String NO_SELECT_CHANNEL_SCAN_DETAIL_MSG = "请选择您需要查看的记录!";
	@SuppressWarnings("unchecked")
	public static HashMap<String, String>[][] getAllChannelDetailTestData(){
		HashMap<String, String>[][] resultCasesMap = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		//	1.正常查看渠道详情。
		HashMap<String, String> successDetailMap = getCaseMapWithCtrlParams();
		list.add(successDetailMap);
		
		//	2.未选中渠道提示操作错误信息
		HashMap<String, String> noSelectChannelCaseMap = getCaseMapWithCtrlParams();
		noSelectChannelCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectChannelCaseMap.replace("message", NO_SELECT_CHANNEL_SCAN_DETAIL_MSG);
		list.add(noSelectChannelCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCasesMap = ArrayUtils.add(resultCasesMap, ArrayUtils.toArray(caseMap));
		
		return resultCasesMap;
	}
}