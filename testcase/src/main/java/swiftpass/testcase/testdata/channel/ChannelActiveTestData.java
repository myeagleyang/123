package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

public class ChannelActiveTestData {
	public static HashMap<String, String>[][] getChannelActiveTestData() {
		return getAllChannelActiveTestData();
	}

	private static String[] activeOperates = {"激活通过", "激活不通过", "冻结", "关闭"};
	private static String 
						NO_ES_PASS_NEEDAGAIN_MSG = "请先审核",
						NO_SELECT_CHANNEL_MSG = "请选择要激活的记录!",
						CLOSE_PAGE_MSG = "正常关闭",
						CANCEL_OPERATE_MSG = "正常取消";
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"ACTIVATE_STATUS_REMARK"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		map.replace("ACTIVATE_STATUS_REMARK", RandomStringUtils.randomAlphanumeric(32));	//页面输入参数
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initPageParamsMap();
		map.replace("ACTIVATE_STATUS_REMARK", RandomStringUtils.randomAlphanumeric(32));
		//	渠道选择文本（渠道ID－渠道名称）
		map.put("TEXT", "");
		map.put("activeOperate", "");
		map.put("isConfirmOperate", "true");	//默认都确认激活操作
		map.put("message", "");
		map.put("C_ExamineStatus", "");	//	当前欲激活操作渠道的审核状态
		map.put("C_ActivateStatus", "");//	当前欲激活操作渠道的激活状态
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelActiveTestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		// 	1.未选中渠道点击激活报错
		HashMap<String, String> noSelectChannelActiveCase = caseMapWithCtrlParams();
		noSelectChannelActiveCase.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectChannelActiveCase.replace("message", NO_SELECT_CHANNEL_MSG);
		list.add(noSelectChannelActiveCase);
		//	2正常关闭激活页面、取消确认激活操作 2条用例
		HashMap<String, String> closePageCase = caseMapWithCtrlParams();
		closePageCase.replace("C_ExamineStatus", String.valueOf(ExamineStatus.PASS.ordinal()));
		closePageCase.replace("C_ActivateStatus", String.valueOf(ActivateStatus.PASS.ordinal()));
		closePageCase.replace("activeOperate", activeOperates[3]);
		closePageCase.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCase);
		HashMap<String, String> cancelOperateCase = SwiftPass.copy(closePageCase);
		cancelOperateCase.replace("activeOperate", activeOperates[2]);
		cancelOperateCase.replace("isConfirmOperate", "false");
		cancelOperateCase.replace("message", CANCEL_OPERATE_MSG);
		list.add(cancelOperateCase);
		//	3.所有审核、激活状态组合用例
		for(HashMap<String, String> group : statusGroupsAndOperate()){
			HashMap<String, String> groupMap = caseMapWithCtrlParams();
			groupMap.replace("isConfirmOperate", "true");
			for(String key : group.keySet())
				groupMap.replace(key, group.get(key));
			list.add(groupMap);
		}
		for(HashMap<String, String> caseMap : list)
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(caseMap));
		
		return resultCasesMaps;
	}
	
	public static HashMap<String, String> preCheckProcess(HashMap<String, String> oneCase){
		SwiftLogger.debug("渠道激活测试数据预处理： " + oneCase);
		if(!StringUtils.isEmpty(oneCase.get("TEXT")))
			return oneCase;
		ExamineStatus es = ExamineStatus.getStatus(oneCase.get("C_ExamineStatus"));
		ActivateStatus as = ActivateStatus.getStatus(oneCase.get("C_ActivateStatus"));
		HashMap<Integer, HashMap<String, String>> chs = ChannelDBOperations.queryByStatus(null, es, as);
		if(chs.size() < 1){
			//激活操作只跟所操作渠道的审核状态有关,一直保持着所属渠道的是审核通过、激活成功的
			String uniqueId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> channel = ChannelService.addOneChannel(uniqueId);
			String channelId = channel.get("CHANNEL_ID");
			if(es.equals(ExamineStatus.NON)){
				if(!as.equals(ActivateStatus.NOPROCESS))
					ChannelAAAService.activeChannel(as, channelId);
			} else{
				ChannelAAAService.auditChannel(ExamineStatus.PASS, channelId);
				if(!as.equals(ActivateStatus.NOPROCESS))
					ChannelAAAService.activeChannel(as, channelId);
				ChannelAAAService.auditChannel(es, channelId);
			}
			chs = ChannelDBOperations.queryByStatus(null, es, as);
		}
		HashMap<String, String> ch = chs.get(RandomUtils.nextInt(1, chs.size()));
		String chId = ch.get("CHANNEL_ID");
		String chName = ch.get("CHANNEL_NAME");
		oneCase.replace("TEXT", String.join("-", chId, chName));
		
		return oneCase;
	}
	
	private static List<HashMap<String, String>> statusGroupsAndOperate(){
		List<HashMap<String, String>> groups = new ArrayList<>();
		//先生成审核状态、激活状态的所有组合
		for(ExamineStatus es : ExamineStatus.values()){
			for(ActivateStatus as : ArrayUtils.removeElement(ActivateStatus.values(), ActivateStatus.NEEDAGAIN)){
				HashMap<String, String> group = new HashMap<>();
				group.put("C_ExamineStatus", es.getSCode());
				group.put("C_ActivateStatus", as.getSCode());
				groups.add(group);
			}
		}
		//去除与业务相悖的审核、激活状态组合
		List<HashMap<String, String>> effGroups = new ArrayList<>();
		for(HashMap<String, String> group : groups){
			if(group.get("C_ExamineStatus").equals(ExamineStatus.NON.getSCode()) && 
				group.get("C_ActivateStatus").equals(ActivateStatus.PASS.getSCode())){
				continue;
			} else{
				effGroups.add(group);
			}
		}
		//添加操作
		List<HashMap<String, String>> resultGroups = new ArrayList<>();
		for(String operate : ArrayUtils.removeElement(activeOperates, activeOperates[3])){
			for(HashMap<String, String> group : effGroups){
				HashMap<String, String> g = SwiftPass.copy(group);
					g.put("activeOperate", operate);
				if((group.get("C_ExamineStatus").equals(ExamineStatus.NON.getSCode()) || 
					group.get("C_ExamineStatus").equals(ExamineStatus.STOP.getSCode())) && 
						operate.equals(activeOperates[0])){
					g.put("message", NO_ES_PASS_NEEDAGAIN_MSG);
				}
				if(!((operate.equals(activeOperates[0]) || operate.equals(activeOperates[1])) && 
						g.get("C_ActivateStatus").equals(ExamineStatus.PASS.getSCode())))
				resultGroups.add(g);
			}
		}
		return resultGroups;
	}
}