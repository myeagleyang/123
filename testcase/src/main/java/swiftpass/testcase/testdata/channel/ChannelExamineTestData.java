package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

public class ChannelExamineTestData {
	
	public static HashMap<String, String>[][] getChannelExamineTestData(){
		return getAllChannelAuditTestData();
	};
	
	/**
	 * 预处理单条渠道审核用例
	 * @param oneCase
	 * @return
	 */
	public static HashMap<String, String> preCheckProcessCaseMap(HashMap<String, String> oneCase){
		SwiftLogger.debug("预处理的待审核渠道的用例参数是： " + oneCase);
		if(!StringUtils.isEmpty(oneCase.get("TEXT")))
			return oneCase;
		ExamineStatus parentES = ExamineStatus.getStatus(oneCase.get("P_ExamineStatus"));
		ExamineStatus childES = ExamineStatus.getStatus(oneCase.get("C_ExamineStatus"));
		HashMap<Integer, HashMap<String, String>> chs = 
				ChannelDBOperations.queryByStatus(parentES, childES, null);
		if(chs.size() < 1){
			String uniquId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> parent = ChannelService.addOneChannel(uniquId);
			String parentId = parent.get("CHANNEL_ID");
			HashMap<String, String> child = ChannelService.addOneChannel(parentId);
			String childId = child.get("CHANNEL_ID");
			if(parentES.equals(ExamineStatus.NON)){
				if(!childES.equals(ExamineStatus.NON))
					ChannelAAAService.auditChannel(childES, childId);
			} else{
				ChannelAAAService.auditChannel(ExamineStatus.PASS, parentId);
				if(!childES.equals(ExamineStatus.NON))
					ChannelAAAService.auditChannel(childES, childId);
				ChannelAAAService.auditChannel(parentES, parentId);
			}
			chs = ChannelDBOperations.queryByStatus(parentES, childES, null);
		}
		HashMap<String, String> chn = chs.get(RandomUtils.nextInt(1, chs.size()));
		oneCase.replace("TEXT", String.join("-", chn.get("CHANNEL_ID"), chn.get("CHANNEL_NAME")));
		return oneCase;
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"examineStatusRemark",	//审核备注
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"TEXT",					//标识页面渠道记录
				"P_ExamineStatus", 		//所属渠道审核状态
				"C_ExamineStatus",		//渠道审核状态
				"auditOperate",			//审核操作
				"isConfirmOperate",		//确认控制参数（true、false）
				"message"				//页面提示信息（如果有）
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		//默认参数值（部分）
		map.replace("examineStatusRemark", RandomStringUtils.randomAlphanumeric(32));
		map.replace("isConfirmOperate", "true");	
		return map;
	}
	
	private static String[] auditOperates = {"审核通过", "审核不通过", "关闭"};
	private static String
		CAN_NOT_OPERATE_MSG = "审核通过的记录不能再次审核!",
		PARENT_NON_STOP_STATUS_MSG = "请先审核所属渠道",
		NO_SELECT_CHANNEL_AUDIT_MSG = "请选择要审核的记录!",
		CLOSE_PAGE_MSG = "正常关闭",
		CANCEL_OPERATE_MSG = "正常取消";
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelAuditTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> initMap = caseMapWithCtrlParams();
		//	1.所属渠道及渠道所有可能存在的审核状态组合用例
		for(HashMap<String, String> statusGroup : examineStatusGroups()){
			HashMap<String, String> caseMap = SwiftPass.copy(initMap);
			caseMap.replace("P_ExamineStatus", statusGroup.get("P_ExamineStatus"));
			caseMap.replace("C_ExamineStatus", statusGroup.get("C_ExamineStatus"));
			list.add(caseMap);
		}
		list = addOperateAndMessage(list);
		
		//	2.未选中渠道记录审核、正常关闭页面、正常取消确认操作并关闭页面
		HashMap<String, String> noSelectChannelAuditCaseMap = SwiftPass.copy(initMap);
		noSelectChannelAuditCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectChannelAuditCaseMap.replace("message", NO_SELECT_CHANNEL_AUDIT_MSG);
		list.add(noSelectChannelAuditCaseMap);
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(initMap);
		closePageCaseMap.replace("P_ExamineStatus", ExamineStatus.PASS.getSCode());
		closePageCaseMap.replace("C_ExamineStatus", ExamineStatus.NON.getSCode());
		closePageCaseMap.replace("auditOperate", auditOperates[2]);
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelOperateCaseMap = SwiftPass.copy(initMap);
		cancelOperateCaseMap.replace("P_ExamineStatus", ExamineStatus.PASS.getSCode());
		cancelOperateCaseMap.replace("C_ExamineStatus", ExamineStatus.NON.getSCode());
		cancelOperateCaseMap.replace("auditOperate", auditOperates[0]);
		cancelOperateCaseMap.replace("isConfirmOperate", "false");
		cancelOperateCaseMap.replace("message", CANCEL_OPERATE_MSG);
		list.add(cancelOperateCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}
	
	private static List<HashMap<String, String>> examineStatusGroups(){
		String pes = "P_ExamineStatus", ces = "C_ExamineStatus";
		List<HashMap<String, String>> groups = new ArrayList<>();
		//所有组合
		for(ExamineStatus p : ExamineStatus.values()){
			for(ExamineStatus c : ExamineStatus.values()){
				HashMap<String, String> group = new HashMap<>();
				group.put(pes, p.getSCode());
				group.put(ces, c.getSCode());
				groups.add(group);
			}
		}
		//剔除不符合系统业务的组合
		List<HashMap<String, String>> effGroups = new ArrayList<>();
		for(HashMap<String, String> group : groups){
			if(group.get("P_ExamineStatus").equals(ExamineStatus.NON.getSCode())){
				if(group.get("C_ExamineStatus").equals(ExamineStatus.PASS.getSCode()))
					continue;
				else if(group.get("C_ExamineStatus").equals(ExamineStatus.NEEDAGAIN.getSCode()))
					continue;
				else
					effGroups.add(group);
			} else{
				effGroups.add(group);
			}
		}
		return effGroups;
	}
	
	private static List<HashMap<String, String>> addOperateAndMessage(List<HashMap<String, String>> old){
		List<HashMap<String, String>> result = new ArrayList<>();
		for(HashMap<String, String> map : old){
			for(String operate : ArrayUtils.removeElement(auditOperates, "关闭")){
				HashMap<String, String> postMap = SwiftPass.copy(map);
				postMap.replace("auditOperate", operate);
				if(postMap.get("C_ExamineStatus").equals(ExamineStatus.PASS.getSCode())){
					postMap.replace("message", CAN_NOT_OPERATE_MSG);	//渠道当前审核通过，都将报错
				} else{
					if(operate.equals(auditOperates[0]) && 
							(postMap.get("P_ExamineStatus").equals(ExamineStatus.NON.getSCode()) || 
									postMap.get("P_ExamineStatus").equals(ExamineStatus.STOP.getSCode()))){
						//所属渠道未审核、审核不通过，审核通过渠道都将报错
						postMap.replace("message", PARENT_NON_STOP_STATUS_MSG);	
					}
				}
				result.add(postMap);
			}
		}
		return result;
	}
}