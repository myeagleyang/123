package irsy.utils.dboperations;

import java.util.HashMap;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.dboperations.ChannelDBOperations;
import swiftpass.utils.dboperations.DBOperations;
import swiftpass.utils.dboperations.DeptDBOperations;
import swiftpass.utils.dboperations.EmpDBOperations;

public class MerchantDBOperations {
	public static HashMap<Integer, HashMap<String, String>> allMerchants(){
		String sql = "select * from cms_merchant";
		return DBUtil.getQueryResultMap(sql);
	}
	
	/**
	 * 返回指定渠道ID下的一个随机商户
	 * @param channelId
	 * @return
	 */
	public static HashMap<String, String> getRandomMerchantOfChannel(String channelId){
		String sql = "select * from cms_merchant where channel_id = $cid".replace("$cid", channelId);
		HashMap<Integer, HashMap<String, String>> mchs = DBUtil.getQueryResultMap(sql);
		try{
			return mchs.get(RandomUtils.nextInt(1, mchs.size()));
		} catch(IllegalArgumentException ex){
			return new HashMap<>();
		}
	}
	/**
	 * 获取受理机构下的那个内部机构（渠道）的所有商户
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> getMerchantsOfTheUniqueChannel(){
		StringBuilder sql = new StringBuilder();
		String parentId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
		sql.append("select * from cms_merchant where merchant_type in (11, 12) ")
			.append("and channel_id = '").append(parentId).append("'");
		return DBUtil.getQueryResultMap(sql.toString());
	}
	
	public static HashMap<String, String> getMerchantDetail(String merchantDetailId){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant_detail where merchant_detail_id = '").append(merchantDetailId).append("'");
		return DBUtil.getQueryResultMap(sql.toString()).get(1);
	}
	
	public static HashMap<String, String> getMerchant(String merchantId){
		String sql = "select * from cms_merchant where merchant_id = '$id'".replace("$id", merchantId);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	
	public static HashMap<String, String> getMerchantByName(String merchantName){
		String sql = "select * from cms_merchant where merchant_name = '$merchantName'".replace("$merchantName", merchantName);
		return DBUtil.getQueryResultMap(sql).get(1);
	}
	/**
	 * 商户编辑页面预期信息的查询
	 * @param merchantId
	 * @return
	 */
	public static HashMap<String, String> getEditMerchantExpectedPageInfo(String merchantId){
		HashMap<String, String> merchant = getMerchant(merchantId);
		HashMap<String, String> merchantDetail = getMerchantDetail(merchantId);
		HashMap<String, String> channel = ChannelDBOperations.getChannel(merchant.get("CHANNEL_ID"));
		HashMap<String, String> emp = EmpDBOperations.getEmp(merchant.get("SALESMAN_ID"));
		NameValuePair industry = DBOperations.getIndustryChain(merchantDetail.get("INDUSTR_ID"));
		HashMap<String, String> info = new HashMap<>();
		info.put("parentChannel", channel.get("CHANNEL_NAME"));
		info.put("parentChannelId", merchant.get("CHANNEL_ID"));
		info.put("merchantName", merchant.get("MERCHANT_NAME"));
		info.put("shortName", merchantDetail.get("MERCHANT_SHORT_NAME"));
		info.put("merchantType", merchant.get("MERCHANT_TYPE"));
		info.put("dealType", merchant.get("MCH_DEAL_TYPE"));
		info.put("empId", null == emp ? "" : emp.get("EMP_ID"));
		info.put("empName", null == emp ? "" : emp.get("EMP_NAME"));
		info.put("currency", merchant.get("FEE_TYPE"));
		info.put("industryNameChain", industry.getValue());
		info.put("industryIdChain", industry.getName());
		info.put("industryId", industry.getName().split("-")[industry.getName().split("-").length - 1]);
		info.put("province", merchantDetail.get("PROVINCE"));
		info.put("city", merchantDetail.get("CITY"));
		info.put("address", merchantDetail.get("ADDRESS"));
		info.put("tel", merchantDetail.get("TEL"));
		info.put("email", merchantDetail.get("EMAIL"));
		info.put("website", merchantDetail.get("WEB_SITE"));
		info.put("principal", merchantDetail.get("PRINCIPAL"));
		info.put("idCode", merchantDetail.get("ID_CODE"));
		info.put("principalPhone", merchantDetail.get("PRINCIPAL_MOBILE"));
		info.put("serviceTel", merchantDetail.get("CUSTOMER_PHONE"));
		info.put("fax", merchantDetail.get("FAX"));
		info.put("thrMchId", merchant.get("OUT_MERCHANT_ID"));
		info.put("LICENCE_PHOTO", merchantDetail.get("LICENCE_PHOTO"));
		info.put("INDETITY_PHOTO", merchantDetail.get("INDETITY_PHOTO"));
		info.put("PROTOCOL_PHOTO", merchantDetail.get("PROTOCOL_PHOTO"));
		info.put("ORG_PHOTO", merchantDetail.get("ORG_PHOTO"));
		info.put("auditStatus", merchant.get("EXAMINE_STATUS"));
		
		return info;
	}
	
	public static HashMap<Integer, HashMap<String, String>> bigMerchantsWithStatus(ExamineStatus es, ActivateStatus as){
		return getMerchantsOfStatus(es, as, MerchantType.BIG);
	}
	
	public static HashMap<Integer, HashMap<String, String>> normalMerchantsWithStatus(ExamineStatus es, ActivateStatus as){
		return getMerchantsOfStatus(es, as, MerchantType.NORMAL);
	}
	
	public static HashMap<Integer, HashMap<String, String>> getMerchantsOfStatus(ExamineStatus es, ActivateStatus as, MerchantType...types){
		String sql = "select * from cms_merchant Where merchant_type in ($types) $STATUS";
		StringBuilder type = new StringBuilder();
		if(null == types || types.length == 0){
			MerchantType[] ts = MerchantType.values();
			for(MerchantType t : ts){
				type.append("'").append(t.getSCode()).append("'").append(t.ordinal() == ts.length - 1 ? "" : ", ");
			}
			sql = sql.replace("$types", type.toString());
		} else{
			for(MerchantType t : types){
				type.append("'").append(t.getSCode()).append("'").append(t.equals(types[types.length - 1]) ? "" : ", ");
			}
			sql = sql.replace("$types", type.toString());
		}
		if(null == es && null == as){
			System.out.println(sql.replace("$STATUS", ""));
			return DBUtil.getQueryResultMap(sql.replace("$STATUS", ""));
		}
		StringBuilder sb = new StringBuilder("And ");
		if(null == es && null != as){
			sb.append("ACTIVATE_STATUS = '").append(as.ordinal()).append("'");
			sql = sql.replace("$STATUS", sb);
		} else if(null != es && null == as){
			sb.append("ACTIVATE_STATUS = '").append(es.ordinal()).append("'");
			sql = sql.replace("$STATUS", sb);
		} else{
			sb.append("ACTIVATE_STATUS = '").append(as.ordinal())
			.append("' And EXAMINE_STATUS = '").append(es.ordinal()).append("'");
			sql = sql.replace("$STATUS", sb);
		}
		SwiftLogger.getLogger().debug("查询商户： " + sql);
		
		return DBUtil.getQueryResultMap(sql);
	}
	
	/**
	 * 商户详情页面信息的查询
	 * @param merchantId
	 * @return
	 */
	public static HashMap<String, String> merchantDetailExpectedPageInfo(String merchantId){
		HashMap<String, String> detail = new HashMap<>();
		HashMap<String, String> merchant = getMerchant(merchantId);
		HashMap<String, String> parentMerchant = getMerchant(merchant.get("PARENT_MERCHANT"));
		HashMap<String, String> merchantDetail = getMerchantDetail(merchant.get("MERCHANT_DETAIL_ID"));
		HashMap<String, String> channel = ChannelDBOperations.getChannel(merchant.get("CHANNEL_ID"));
		HashMap<String, String> emp = EmpDBOperations.getEmp(merchant.get("SALESMAN_ID"));
		HashMap<String, String> depart = DeptDBOperations.getDepart(merchant.get("DEPT_ID"));
		NameValuePair industry = DBOperations.getIndustryChain(merchantDetail.get("INDUSTR_ID"));
		detail.put("parentChannel", channel.get("CHANNEL_NAME"));
		detail.put("merchantName", merchant.get("MERCHANT_NAME"));
		detail.put("shortName", merchantDetail.get("MERCHANT_SHORT_NAME"));
		detail.put("merchantType", merchant.get("MERCHANT_TYPE"));
		detail.put("empName", null == emp ? "" : emp.get("EMP_NAME"));
		detail.put("currency", merchant.get("FEE_TYPE"));
		detail.put("industry", industry.getValue().split("-")[industry.getValue().split("-").length - 1]);
		detail.put("parentMerchant", null == parentMerchant ? "" : parentMerchant.get("MERCHANT_NAME"));
		detail.put("depart", null == depart ? "" : depart.get("DEPT_NAME"));
		detail.put("createTime", merchant.get("CREATE_TIME").substring(0, merchant.get("CREATE_TIME").lastIndexOf(".")));
		detail.put("province", merchantDetail.get("PROVINCE"));
		detail.put("city", merchantDetail.get("CITY"));
		detail.put("address", merchantDetail.get("ADDRESS"));
		detail.put("tel", merchantDetail.get("TEL"));
		detail.put("email", merchantDetail.get("EMAIL"));
		detail.put("website", merchantDetail.get("WEB_SITE"));
		detail.put("principal", merchantDetail.get("PRINCIPAL"));
		detail.put("idCode", merchantDetail.get("ID_CODE"));
		detail.put("principalPhone", merchantDetail.get("PRINCIPAL_MOBILE"));
		detail.put("serviceTel", merchantDetail.get("CUSTOMER_PHONE"));
		detail.put("fax", merchantDetail.get("FAX"));
		detail.put("dealType", merchant.get("MCH_DEAL_TYPE"));
		detail.put("auditStatus", merchant.get("EXAMINE_STATUS"));
		detail.put("auditEmp", merchant.get("EXAMINE_EMP"));
		String auditTime = merchant.get("EXAMINE_TIME");
		String activeTime = merchant.get("ACTIVATE_TIME");
		detail.put("auditTime", StringUtils.isEmpty(auditTime) ? "" : auditTime.substring(0, merchant.get("EXAMINE_TIME").lastIndexOf(".")));
		detail.put("activeStatus", merchant.get("ACTIVATE_STATUS"));
		detail.put("activeTime", StringUtils.isEmpty(activeTime)? "" : activeTime.substring(0, merchant.get("ACTIVATE_TIME").lastIndexOf(".")));
		detail.put("activeEmp", merchant.get("ACTIVATE_EMP"));
		detail.put("isRefundAudit", merchantDetail.get("INTERFACE_REFUND_AUDIT"));
		detail.put("thrMchId", merchant.get("OUT_MERCHANT_ID"));
		detail.put("auditRemark", merchant.get("EXAMINE_STATUS_REMARK"));
		detail.put("activeRemark", merchant.get("ACTIVATE_STATUS_REMARK"));
		
		return detail;
	}
	
	/**
	 * 获取指定审核状态的所属渠道
	 * @param status
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> getMchWithPChannelSpecES(ExamineStatus status){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant where merchant_type in (11, 12) and ")
			.append("channel_id in (select channel_id from cms_channel where examine_status = '$st')");
		String sql_ = sql.toString().replace("$st", status.getSCode());
		return DBUtil.getQueryResultMap(sql_);
	}
	
	/**
	 * 获取指定所属渠道、商户审核状态的商户
	 * @param parentS
	 * @param mchS
	 * @return
	 */
	public static HashMap<Integer, HashMap<String, String>> getMchWithPChannelSpecESBoth(ExamineStatus parentS, ExamineStatus mchS){
		int index = 1;
		HashMap<Integer, HashMap<String, String>> mchs = new HashMap<>();
		HashMap<Integer, HashMap<String, String>> allMchs = getMchWithPChannelSpecES(parentS);
		for(Integer key : allMchs.keySet()){
			if(allMchs.get(key).get("EXAMINE_STATUS").equals(mchS.getSCode()))
				mchs.put(index++, allMchs.get(key));
		}
		return mchs;
	}

	public static HashMap<Integer, HashMap<String, String>> getMchWithParentChildSpceStatus(
			ExamineStatus parentES, ExamineStatus childES, ActivateStatus childAS){
		SwiftLogger.debug("查询商户，商户所属渠道审核状态为 - " + parentES + ", 商户的审核-激活状态为："	+ childES + "-" + childAS);
		StringBuilder sql = new StringBuilder();
		sql.append("select * from cms_merchant where examine_status = '")
			.append(childES.getSCode()).append("' ")
			.append("and activate_status = '").append(childAS.getSCode()).append("' ")
			.append("and merchant_type in (11, 12) ")
			.append("and channel_id in ")
			.append("(select channel_id from cms_channel where examine_status = '")
			.append(parentES.getSCode()).append("'")
			.append(")");
		SwiftLogger.debug(sql.toString());
		return DBUtil.getQueryResultMap(sql.toString());
	}
	public static void main(String...strings){
		getMchWithParentChildSpceStatus(ExamineStatus.PASS, ExamineStatus.PASS, ActivateStatus.PASS);
		DBUtil.closeDBResource();
	}
	public static HashMap<String, String> merchantAuditExpectedInfo(String merchantId){
		HashMap<String, String> info = new HashMap<>();
		HashMap<String, String> mch = getMerchant(merchantId);
		HashMap<String, String> mchDetail = getMerchantDetail(mch.get("MERCHANT_DETAIL_ID"));
		HashMap<String, String> pCh = ChannelDBOperations.getChannel(mch.get("CHANNEL_ID"));
		HashMap<String, String> emp = EmpDBOperations.getEmp(mch.get("SALESMAN_ID"));
		
		//	1
		info.put("parentChannelName", pCh.get("CHANNEL_NAME"));
		info.put("parentChannelId", pCh.get("CHANNEL_ID"));
		info.put("merchantName", mch.get("MERCHANT_NAME"));
		info.put("shortName", mchDetail.get("MERCHANT_SHORT_NAME"));
		//	2
		info.put("merchantType", mch.get("MERCHANT_TYPE"));
		info.put("empName", null != emp ? emp.get("EMP_NAME") : "");
		info.put("currency", mch.get("FEE_TYPE"));
		//	3
		info.put("industry", mchDetail.get("INDUSTR_ID"));
		info.put("province", mchDetail.get("PROVINCE"));
		info.put("city", mchDetail.get("CITY"));
		//	4
		info.put("address", mchDetail.get("ADDRESS"));
		info.put("tel", mchDetail.get("TEL"));
		info.put("email", mchDetail.get("EMAIL"));
		//	5
		info.put("website", mchDetail.get("WEB_SITE"));
		info.put("principal", mchDetail.get("PRINCIPAL"));
		info.put("idCode", mchDetail.get("ID_CODE"));
		//	6
		info.put("dealType", mch.get("MCH_DEAL_TYPE"));
		
		return info;
	}
	
	public static HashMap<Integer, HashMap<String, String>> getMerchantByType(MerchantType merchantType){
		String sql = "select * from cms_merchant where merchant_type = '" + merchantType.getSCode() + "' and rownum < 3";
		return DBUtil.getQueryResultMap(sql);
	}
}
