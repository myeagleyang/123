package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

public class ChannelEATestData {
	public static HashMap<String, String>[][] getChannelEATestData(){
		return getAllChannelEATestData();
	}
	
	public static HashMap<String, String> preCheckProcess(HashMap<String, String> oneCase){
		if(!StringUtils.isEmpty(oneCase.get("TEXT"))) return oneCase;
		ExamineStatus parentES = ExamineStatus.getStatus(oneCase.get("P_ExamineStatus"));
		ExamineStatus childES = ExamineStatus.getStatus(oneCase.get("C_ExamineStatus"));
		ActivateStatus childAS = ActivateStatus.getStatus(oneCase.get("C_ActivateStatus"));
		HashMap<Integer, HashMap<String, String>> chs = ChannelDBOperations.queryByStatus(parentES, childES, childAS);
		if(chs.size() < 1){
			String uniqueId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> parent = ChannelService.addOneChannel(uniqueId);
			String parentId = parent.get("CHANNEL_ID");
			HashMap<String, String> child = ChannelService.addOneChannel(parentId);
			String childId = child.get("CHANNEL_ID");
			if(parentES.equals(ExamineStatus.NON)){
				if(childES.equals(ExamineStatus.NON)){
					if(!childAS.equals(ActivateStatus.NOPROCESS))
						ChannelAAAService.activeChannel(childAS, childId);
				} else{
					ChannelAAAService.auditChannel(childES, childId);
					if(!childAS.equals(ActivateStatus.NOPROCESS))
						ChannelAAAService.activeChannel(childAS, childId);
				}
			} else{
				ChannelAAAService.auditChannel(ExamineStatus.PASS, parentId);
				if(childES.equals(ExamineStatus.NON)){
					if(!childAS.equals(ActivateStatus.NOPROCESS))
						ChannelAAAService.activeChannel(childAS, childId);
				} else{
					ChannelAAAService.auditChannel(ExamineStatus.PASS, childId);
					ChannelAAAService.activeChannel(childAS, childId);
					ChannelAAAService.auditChannel(childES, childId);
				}
				ChannelAAAService.auditChannel(parentES, parentId);
			}
			
			chs = ChannelDBOperations.queryByStatus(parentES, childES, childAS);
		}
		String chId = chs.get(1).get("CHANNEL_ID");
		String chName = chs.get(1).get("CHANNEL_NAME");
		oneCase.replace("TEXT", String.join("-", chId, chName));
		return oneCase;
	}
	
	private static String[] eaOperates = {"通过", "不通过", "关闭"};
	private static final String 
		NO_SELECT_CHANNEL_MSG = "请选择要审核并激活的记录!",
		PARENT_NO_STOP_EXAMINE_MSG = "请先审核所属渠道",
		CLOSE_PAGE_MSG = "正常关闭",
		CANCEL_OPERATE_MSG = "正常取消";
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
			"examineStatusRemark"	//激活备注
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		map.replace("examineStatusRemark", RandomStringUtils.randomAlphanumeric(64));
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"P_ExamineStatus",		//当前待操作渠道所属渠道的审核状态
				"C_ExamineStatus",		//当前待操作渠道审核状态
				"C_ActivateStatus",		//当前代操作渠道激活状态
				"TEXT",					//渠道名称-渠道编号
				"eaOperate",			//审核并激活操作（通过、不通过、关闭）
				"isConfirmOperate",		//是否确认操作控制参数
				"message"				//用例执行抛出异常的描述信息
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		map.replace("isConfirmOperate", "true");
		return map;
	}
	
	private static List<HashMap<String, String>> groups(){
		List<HashMap<String, String>> groups = new ArrayList<>();
		for(ExamineStatus pes : ExamineStatus.values())
			for(ExamineStatus es : ExamineStatus.values())
				for(ActivateStatus as : ActivateStatus.values()){
					HashMap<String, String> g = new HashMap<>();
					g.put("P_ExamineStatus", pes.getSCode());
					g.put("C_ExamineStatus", es.getSCode());
					g.put("C_ActivateStatus", as.getSCode());
					groups.add(g);
				}
		//剔除业务逻辑不允许的组合
		List<HashMap<String, String>> effGroups = new ArrayList<>();
		for(HashMap<String, String> group : groups){
			if(group.get("C_ActivateStatus").equals(ActivateStatus.NEEDAGAIN.getSCode()))
				continue;
			if(group.get("P_ExamineStatus").equals(ExamineStatus.NON.getSCode()) && 
					(group.get("C_ExamineStatus").equals(ExamineStatus.PASS.getSCode()) ||
						group.get("C_ExamineStatus").equals(ExamineStatus.NEEDAGAIN.getSCode()) ||
						group.get("C_ActivateStatus").equals(ActivateStatus.PASS.getSCode())))
				continue;
			if(group.get("C_ExamineStatus").equals(ExamineStatus.NON.getSCode()) && 
					group.get("C_ActivateStatus").equals(ActivateStatus.PASS.getSCode()))
				continue;
			effGroups.add(group);
		}
		//添加激活操作
		List<HashMap<String, String>> finalGroups = new ArrayList<>();
		for(String operate : ArrayUtils.removeElement(eaOperates, eaOperates[2])){
			for(HashMap<String, String> g : effGroups){
				HashMap<String, String> gCopy = SwiftPass.copy(g);
				gCopy.put("eaOperate", operate);
				gCopy.put("isConfirmOperate", "true");
				if(operate.equals(eaOperates[0]) && 
						(gCopy.get("P_ExamineStatus").equals(ExamineStatus.NON.getSCode()) || 
							gCopy.get("P_ExamineStatus").equals(ExamineStatus.STOP.getSCode())) &&
						!gCopy.get("C_ExamineStatus").equals(ExamineStatus.PASS.getSCode())){
					gCopy.put("message", PARENT_NO_STOP_EXAMINE_MSG);
				} else{
					gCopy.put("message", "");
				}
				finalGroups.add(gCopy);
			}
		}
		return finalGroups;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelEATestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		//1.异常流程测试用例
		HashMap<String, String> noSelectChannelCaseMap = caseMapWithCtrlParams();
		noSelectChannelCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectChannelCaseMap.replace("message", NO_SELECT_CHANNEL_MSG);
		list.add(noSelectChannelCaseMap);
		HashMap<String, String> closePageCaseMap = caseMapWithCtrlParams();
		closePageCaseMap.replace("P_ExamineStatus", ExamineStatus.PASS.getSCode());
		closePageCaseMap.replace("C_ExamineStatus", ExamineStatus.PASS.getSCode());
		closePageCaseMap.replace("C_ActivateStatus", ActivateStatus.PASS.getSCode());
		closePageCaseMap.replace("eaOperate", eaOperates[2]);
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		HashMap<String, String> cancelOperateCaseMap = SwiftPass.copy(closePageCaseMap);
		cancelOperateCaseMap.replace("eaOperate", eaOperates[0]);
		cancelOperateCaseMap.replace("message", CANCEL_OPERATE_MSG);
		
		//2.所有状态组合并有效操作的用例
		for(HashMap<String, String> group : groups()){
			group.put("TEXT", "");
			group.put("examineStatusRemark", RandomStringUtils.randomAlphanumeric(64));
			list.add(group);
		}
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}
	
	public static void main(String...strings){
		for(HashMap<String, String>[] g : getChannelEATestData()){
			System.out.println(preCheckProcess(g[0]));
		}
	}
}