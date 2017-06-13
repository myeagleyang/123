package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.DataGenerator;
import swiftpass.page.enums.AccWay;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ChannelProperties;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.testcase.IdNamePair;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;

public class XChannelSearchTestData {
	public static ChannelSearchCaseBean[][] data(){
		List<ChannelSearchCaseBean> caseList = new ArrayList<>();
		//	1.单条件渠道查询
		List<IdNamePair> acceptOrgs = XChannelSearchHelper.acceptOrgs();
		List<IdNamePair> channels = XChannelSearchHelper.channels();
		ChannelSearchCaseBean byBeginCreateTime = new ChannelSearchCaseBean().setBeginCreateTime(DataGenerator.generateDateBaseOnNow(0, -1, -5));
		byBeginCreateTime.setCASE_NAME("单条件渠道查询-创建时间起始时间.");
		caseList.add(byBeginCreateTime);
		ChannelSearchCaseBean byEndCreateTime = new ChannelSearchCaseBean().setEndCreateTime(DataGenerator.generateDateBaseOnNow(0, 0, 5));
		byEndCreateTime.setCASE_NAME("单条件渠道查询-创建时间结束时间.");
		caseList.add(byEndCreateTime);
		String[] orgItems = "受理机构名称-受理机构编号".split("-");
		IdNamePair acceptOrg = acceptOrgs.get(RandomUtils.nextInt(0, acceptOrgs.size()));
		ChannelSearchCaseBean byAcceptOrg = new ChannelSearchCaseBean().setIsSelectAcceptOrg("true")
				.setAcceptOrgItem(orgItems[RandomUtils.nextInt(0, orgItems.length)]).setAcceptOrgId(acceptOrg.getId())
				.setAcceptOrgName(acceptOrg.getName()).setIsConfirmSelectAcceptOrg("true");
		byAcceptOrg.setCASE_NAME("单条件渠道查询-受理机构.");
		caseList.add(byAcceptOrg);
		ChannelSearchCaseBean byExamineStatus = new ChannelSearchCaseBean().setExamineStatus(ExamineStatus.random().getSCode());
		byExamineStatus.setCASE_NAME("单条件渠道查询-审核状态.");
		caseList.add(byExamineStatus);
		ChannelSearchCaseBean byActivateStatus = new ChannelSearchCaseBean().setActivateStatus(ActivateStatus.random().getSCode());
		byActivateStatus.setCASE_NAME("单条件渠道查询-激活状态.");
		caseList.add(byActivateStatus);
		String[] channelItems = "机构名称-机构ID".split("-");
		IdNamePair parentChannel = channels.get(RandomUtils.nextInt(0, channels.size()));
		ChannelSearchCaseBean byParentChannel = new ChannelSearchCaseBean().setIsSelectParentChannel("true")
				.setParentChannelItem(channelItems[RandomUtils.nextInt(0, channelItems.length)])
				.setParentChannelId(parentChannel.getId()).setParentChannelName(parentChannel.getName())
				.setIsConfirmSelectParentChannel("true");
		byParentChannel.setCASE_NAME("单条件渠道查询-所属渠道.");
		caseList.add(byParentChannel);
		ChannelSearchCaseBean byChannelId = new ChannelSearchCaseBean().setChannelId(channels.get(RandomUtils.nextInt(0, channels.size())).getId());
		byChannelId.setCASE_NAME("单条件渠道查询-渠道编号.");
		caseList.add(byChannelId);
		ChannelSearchCaseBean byChannelName = new ChannelSearchCaseBean().setChannelName(channels.get(RandomUtils.nextInt(0, channels.size())).getName());
		byChannelName.setCASE_NAME("单条件渠道查询-渠道名称.");
		caseList.add(byChannelName);
		ChannelSearchCaseBean byChannelProperty = new ChannelSearchCaseBean().setChannelProperty(ChannelProperties.random().getSCode());
		byChannelProperty.setCASE_NAME("单条件渠道查询-渠道类型.");
		caseList.add(byChannelProperty);
		AccWay way = AccWay.random();//	TODO 后续可能需要扩展.
		ChannelSearchCaseBean byAccWay = new ChannelSearchCaseBean().setAccWay((way == AccWay.ALL ? "" : way.getSCode()));
		byAccWay.setCASE_NAME("单条件渠道查询-结算方式.");
		caseList.add(byAccWay);
		
		//	2.多条件组合查询
		int count = 1;
		for(int i = 0; i < 12; i++){
			IdNamePair acceptOrg0 = acceptOrgs.get(RandomUtils.nextInt(0, acceptOrgs.size()));
			IdNamePair parentChannel0 = channels.get(RandomUtils.nextInt(0, channels.size()));
			String seed = RandomStringUtils.randomNumeric(10);
			ChannelSearchCaseBean multiQuery = new ChannelSearchCaseBean()
					.setBeginCreateTime(seed.charAt(0) % 2 == 0 ? "" : DataGenerator.generateDateBaseOnNow(0, 0, -15))
					.setEndCreateTime(seed.charAt(1) % 2 == 0 ? "" : DataGenerator.generateDateBaseOnNow(0, 0, 10))
					.setAcceptOrgId(seed.charAt(2) % 2 == 0 ? "" : acceptOrg0.getId()).setAcceptOrgName(seed.charAt(2) % 2 == 0 ? "" : acceptOrg0.getName())
					.setExamineStatus(seed.charAt(3) % 2 == 0 ? "" : ExamineStatus.random().getSCode())
					.setActivateStatus(seed.charAt(4) % 2 == 0 ? "" : ActivateStatus.random().getSCode())
					.setParentChannelId(seed.charAt(5) % 2 == 0 ? "" : parentChannel0.getId()).setParentChannelName(seed.charAt(5) % 2 == 0 ? "" : parentChannel0.getName())
					.setChannelName(seed.charAt(6) % 2 == 0 ? "" : channels.get(RandomUtils.nextInt(0, channels.size())).getName())
					.setChannelId(seed.charAt(7) % 2 == 0 ? "" : channels.get(RandomUtils.nextInt(0, channels.size())).getId())
					.setChannelProperty(seed.charAt(8) % 2 == 0 ? "" : ChannelProperties.random().getSCode())
					.setAccWay(seed.charAt(9) % 2 == 0 ? "" : "")// TODO 后续这个字段放开后需要扩展.
					.setMultiQuery("true");
			multiQuery.setCASE_NAME("组合查询".concat(String.valueOf(count++)).concat("."));
			caseList.add(multiQuery);
		}
		
		//	3.默认查询
		ChannelSearchCaseBean defaultQuery = new ChannelSearchCaseBean();
		defaultQuery.setCASE_NAME("渠道默认查询");
		caseList.add(defaultQuery);
		
		caseList.forEach(element -> XChannelSearchHelper.queryExpectedResult(element));
		return caseList.stream().map(element -> ArrayUtils.toArray(element)).toArray(ChannelSearchCaseBean[][]::new);
	}
}

class XChannelSearchHelper{
	public static List<IdNamePair> acceptOrgs(){
		List<IdNamePair> pairs = new ArrayList<>();
		String sql = "select channel_id, channel_name from cms_channel where physics_flag = 1 and channel_type = 1";
		DBUtil.getXQueryResultMap(sql).entrySet().forEach(entry -> 
			pairs.add(new IdNamePair(entry.getValue().get("CHANNEL_ID"), entry.getValue().get("CHANNEL_NAME"))));
		return pairs;
	}
	
	public static List<IdNamePair> channels(){
		List<IdNamePair> pairs = new ArrayList<>();
		String sql = "select channel_id, channel_name from cms_channel where physics_flag = 1 and channel_type = 2";
		DBUtil.getXQueryResultMap(sql).entrySet().forEach(entry -> 
			pairs.add(new IdNamePair(entry.getValue().get("CHANNEL_ID"), entry.getValue().get("CHANNEL_NAME"))));
		return pairs;
	}
	
	public static void queryExpectedResult(ChannelSearchCaseBean c){
		String sql = "select count(*) from cms_channel where channel_type = 2 and physics_flag = 1";
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(c.getBeginCreateTime())){
			sb.append(" create_time >= to_date('" + c.getBeginCreateTime() + "', 'yyyy-mm-dd hh24:mi:ss') and");
		}
		if(!StringUtils.isEmpty(c.getEndCreateTime())){
			sb.append(" create_time <= to_date('" + c.getEndCreateTime() + "', 'yyyy-mm-dd hh24:mi:ss') and");
		}
		if(!StringUtils.isEmpty(c.getAcceptOrgId())){
			sb.append(" channel_id in (select org_id from cms_org_relation start with org_id = '" + c.getAcceptOrgId() + "'")
				.append(" connect by prior org_id = parent_org) and");
		}
		if(!StringUtils.isEmpty(c.getExamineStatus())){
			sb.append(" examine_status = '" + c.getExamineStatus() + "' and");
		}
		if(!StringUtils.isEmpty(c.getActivateStatus())){
			sb.append(" activate_status = '" + c.getActivateStatus() + "' and");
		}
		if(!StringUtils.isEmpty(c.getParentChannelId())){
			sb.append(" parent_channel = '" + c.getParentChannelId() + "' and");
		}
		if(!StringUtils.isEmpty(c.getChannelName())){
			sb.append(" channel_name like '%" + c.getChannelName() + "%' and");
		}
		if(!StringUtils.isEmpty(c.getChannelId())){
			sb.append(" channel_id = '" + c.getChannelId() + "' and");
		}
		if(!StringUtils.isEmpty(c.getChannelProperty())){
			sb.append(" channel_properties = '" + c.getChannelProperty() + "' and");
		}
		if(!StringUtils.isEmpty(c.getAccWay())){
			sb.append(" acc_way = '" + c.getAccWay() + "' and");
		}
		if(!StringUtils.isEmpty(sb.toString())){
			sql = sql.concat(" and ").concat(sb.delete(sb.lastIndexOf("and"), sb.length()).toString());
		}
		SwiftLogger.debug("渠道查询预期结果查询语句为： ".concat(sql).concat("  ---------------"));
		c.setExpected(DBUtil.getXQueryResultMap(sql).get(1).get("COUNT(*)"));
	}
}