package irsy.utils.dboperations;

import java.util.HashMap;

import org.apache.commons.lang3.RandomUtils;

import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.DBUtil;

public class ChannelDBOperations {
	
	public static final HashMap<String, String> uniqueChannleInfo = acceptOrgUniqueChannel();
	
	/**
	 * 获取受理机构
	 * @return
	 */
	public static HashMap<String, String> acceptOrg(){
		String sql = "select * from cms_channel where parent_channel = '000000000000'";
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	/**
	 * 获取受理机构下的那个唯一的内部渠道
	 * @return
	 */
	public static HashMap<String, String> acceptOrgUniqueChannel(){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_channel where parent_channel = ")
			.append("(select channel_id from cms_channel where parent_channel = '000000000000')");
		return DBUtil.getQueryResultMap(sql.toString()).get(1);
	}

	/**
	 * 指定渠道ID返回渠道信息
	 * @param channelId
	 * @return
	 */
	public static HashMap<String, String> getChannel(String channelId){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_channel where channel_id = '").append(channelId).append("'");
		return DBUtil.getQueryResultMap(sql.toString()).get(1);
	}
	
	public static HashMap<Integer, HashMap<String, String>> allDBChannels(){
		String sql = "select * from cms_channel where channel_type = 2";
		return DBUtil.getQueryResultMap(sql);
	}
	
	/**
	 * 查询所有非unique的指定所属渠道审核状态、指定审核状态、激活状态的渠道
	 * @param parentES
	 * @param childES
	 * @param childAS
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> queryByStatus(ExamineStatus parentES,
			ExamineStatus childES, ActivateStatus childAS){
		String uniqueId = acceptOrgUniqueChannel().get("CHANNEL_ID");
		StringBuilder sql = new StringBuilder();
		StringBuilder peC = new StringBuilder();
		StringBuilder ceC = new StringBuilder();
		StringBuilder caC = new StringBuilder();
		sql.append("select * from cms_channel where channel_type = 2 and channel_id <> '")
																	.append(uniqueId).append("'");
		if(null != parentES)
			peC.append(" and parent_channel in (select channel_id from cms_channel ")
				.append("where examine_status = '").append(parentES.getSCode()).append("')");
		if(null != childES)
			ceC.append(" and examine_status = '").append(childES.getSCode()).append("'");
		if(null != childAS)
			caC.append(" and activate_status = '").append(childAS.getSCode()).append("'");
		sql.append(ceC).append(caC).append(peC);
		return DBUtil.getQueryResultMap(sql.toString());
	}
	/**
	 * 查询数据库任一渠道信息
	 * */
	public static HashMap<String, String> getChannelInfo(){
		String sql = "select * from cms_channel where channel_type = 2";
		HashMap<Integer, HashMap<String, String>> channelInfos = DBUtil.getQueryResultMap(sql);
		return channelInfos.get(RandomUtils.nextInt(1, channelInfos.size()+1));
	}
}