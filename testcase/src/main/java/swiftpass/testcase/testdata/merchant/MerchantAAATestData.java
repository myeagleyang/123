package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class MerchantAAATestData {
	public static HashMap<String, String>[][] getMerchantAAATestData(){
		return getAllMerchantAAATestData();
	}
	private static final String 
						PARENT_MERCHANT_HAS_NO_AUDIT_MSG = "请先审核所属大商户",
						PARENT_CHANNEL_HAS_NO_AUDIT_MSG = "请先审核所属渠道",
						NO_SELECT_MERCHANT_OPERATE_MSG = "请选择要审核并激活的记录!",
						CLOSE_PAGE_MSG = "正常关闭",
						CANCEL_OPERATE_MSG = "正常取消";
	private static String[] aaOperates = {"通过", "不通过", "关闭"};
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
			"activateStatusRemark"	//激活备注
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		map.replace("activateStatusRemark", RandomStringUtils.randomAlphanumeric(32));
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
			"TEXT",
			"PC_ExamineStatus",		//所属渠道审核状态
			"PM_ExamineStatus",		//所属大商户审核状态
			"M_ExamineStatus",		//商户（门店）审核状态
			"M_ActivateStatus",		//商户（门店）激活状态
			"isMerchant",
			"aaOperate",
			"isConfirmOperate",
			"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		
		return map;
	}
	
	private static List<HashMap<String, String>> mchStatusGroups(){
		String pces = "PC_ExamineStatus", mes = "M_ExamineStatus", mas = "M_ActivateStatus";
		List<HashMap<String, String>> groups = new ArrayList<>();
		for(ExamineStatus ces : ExamineStatus.values())
			for(ExamineStatus es : ExamineStatus.values())
				for(ActivateStatus as : ArrayUtils.removeElement(ActivateStatus.values(), 
																	ActivateStatus.NEEDAGAIN)){
					HashMap<String, String> group = new HashMap<>();
					group.put(pces, ces.getSCode());
					group.put(mes, es.getSCode());
					group.put(mas, as.getSCode());
					groups.add(group);
				}
		//剔除不可能的组合
		List<HashMap<String, String>> effGroups = new ArrayList<>();
		for(HashMap<String, String> group : groups){
			if(group.get(pces).equals(ExamineStatus.NON.getSCode()) && 
				(group.get(mes).equals(ExamineStatus.PASS.getSCode()) ||
					group.get(mes).equals(ExamineStatus.NEEDAGAIN.getSCode()))){
				//	do nothing.
			} else if(group.get(mes).equals(ExamineStatus.NON.getSCode()) && 
						group.get(mas).equals(ActivateStatus.PASS.getSCode())){
				//	do nothing.
			} else if(group.get(pces).equals(ExamineStatus.NON.getSCode()) && 
						group.get(mas).equals(ActivateStatus.PASS.getSCode())){
				//	do nothing.
			} 
			else{
				effGroups.add(group);
			}
		}
		return effGroups;
	}
	
	private static List<HashMap<String, String>> addOperate(List<HashMap<String, String>> old){
		List<HashMap<String, String>> result = new ArrayList<>();
		for(HashMap<String, String> e : old){
			for(String aaOperate : ArrayUtils.removeElement(aaOperates, aaOperates[2])){
				HashMap<String, String> oneCase = SwiftPass.copy(e);
				oneCase.replace("aaOperate", aaOperate);
				if(aaOperate.equals(aaOperates[0])){
					if(oneCase.get("PC_ExamineStatus").equals(ExamineStatus.NON.getSCode()) ||
							(oneCase.get("PC_ExamineStatus").equals(ExamineStatus.STOP.getSCode()) &&
								!oneCase.get("M_ExamineStatus").equals(ExamineStatus.PASS.getSCode()))){
							oneCase.replace("message", PARENT_CHANNEL_HAS_NO_AUDIT_MSG);
					}
				}
				result.add(oneCase);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantAAATestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> initCaseMap = caseMapWithCtrlParams();
		
		//	1.商户审核并激活用例
		List<HashMap<String, String>> statusGroups = mchStatusGroups();
		HashMap<String, String> mchFixedMap = SwiftPass.copy(initCaseMap);
		mchFixedMap.replace("isMerchant", "true");
		mchFixedMap.replace("isConfirmOperate", "true");
		for(HashMap<String, String> statusGroup : statusGroups){
			HashMap<String, String> oneCase = SwiftPass.copy(mchFixedMap);
			for(String key : statusGroup.keySet()){
				oneCase.replace(key, statusGroup.get(key));
			}
			list.add(oneCase);
		}
		list = addOperate(list);
		
		//	2.门店审核并激活用例——只需替换商户审核的上级机构对象为大商户
		List<HashMap<String, String>> stores = new ArrayList<>();
		for(HashMap<String, String> mch : list){
			HashMap<String, String> store = SwiftPass.copy(mch);
			store.replace("isMerchant", "false");
			store.replace("PM_ExamineStatus", store.get("PC_ExamineStatus"));
			store.replace("PC_ExamineStatus", "");
			if(store.get("message").equals(PARENT_CHANNEL_HAS_NO_AUDIT_MSG))
				store.replace("message", PARENT_MERCHANT_HAS_NO_AUDIT_MSG);
			stores.add(store);
		}
		list.addAll(stores);
		
		//	不选中商户操作；正常关闭页面、取消确认操作并关闭页面
		HashMap<String, String> noSelectMerchantOperateCase = SwiftPass.copy(initCaseMap);
		noSelectMerchantOperateCase.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectMerchantOperateCase.replace("message", NO_SELECT_MERCHANT_OPERATE_MSG);
		list.add(noSelectMerchantOperateCase);
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(mchFixedMap);
		closePageCaseMap.replace("PC_ExamineStatus", ExamineStatus.PASS.getSCode());
		closePageCaseMap.replace("M_ExamineStatus", ExamineStatus.PASS.getSCode());
		closePageCaseMap.replace("M_ActivateStatus", ActivateStatus.PASS.getSCode());
		closePageCaseMap.replace("aaOperate", aaOperates[2]);
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelOperateCaseMap = SwiftPass.copy(closePageCaseMap);
		cancelOperateCaseMap.replace("aaOperate", aaOperates[0]);
		cancelOperateCaseMap.replace("isConfirmOperate", "false");
		cancelOperateCaseMap.replace("message", CANCEL_OPERATE_MSG);
		list.add(cancelOperateCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(caseMap));
		
		return resultCasesMaps;
	}
	
	public static HashMap<String, String> preCheckProcessCase(HashMap<String, String> oneCase){
		String isMerchant = oneCase.get("isMerchant");
		String TEXT = oneCase.get("TEXT");
		if(TEXT.contains("$$$")) return oneCase;
		SwiftLogger.debug("预处理的商户审核并激活操作的用例数据是：" + oneCase);
		ExamineStatus parentcES = isMerchant.equals("true") ? ExamineStatus.getStatus(oneCase.get("PC_ExamineStatus")) : null;
		ExamineStatus parentmES = isMerchant.equals("false") ? ExamineStatus.getStatus(oneCase.get("PM_ExamineStatus")) : null;
		ExamineStatus childES = ExamineStatus.getStatus(oneCase.get("M_ExamineStatus"));
		ActivateStatus childAS = ActivateStatus.getStatus(oneCase.get("M_ActivateStatus"));
		HashMap<String, String> ms = null;
		String childOfOrgId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
		if(isMerchant.equals("true")){
			HashMap<Integer, HashMap<String, String>> mchs = MerchantDBOperations
							.getMchWithParentChildSpceStatus(parentcES, childES, childAS);
			if(mchs.size() < 1){
				HashMap<String, String> pch = ChannelService.addMultiInnerChannels(childOfOrgId, 1).get(1);
				String parentId = pch.get("CHANNEL_ID");
				HashMap<String, String> mch = MerchantService.addBigMerchantAttachChannel(parentId);
				String childId = mch.get("MERCHANT_ID");
				updateParentChildStatus(childId, parentcES, childES, childAS);
			}
			mchs = MerchantDBOperations.getMchWithParentChildSpceStatus(parentcES, childES, childAS);
			ms = mchs.get(RandomUtils.nextInt(1, mchs.size()));
		} else{
			HashMap<Integer, HashMap<String, String>> stores = StoreDBOperations
					.childSParentMWithSpecStatus(parentmES, childES, childAS);
			if(stores.size() < 1){
				HashMap<String, String> bigMch = MerchantService.addBigMerchantAttachChannel(childOfOrgId);
				String parentId = bigMch.get("MERCHANT_ID");
				HashMap<String, String> store = StoreService.addDirectStoreAttachMerchant(parentId);
				String childId = store.get("MERCHANT_ID");
				updateParentchildStatus(childId, parentmES, childES, childAS);
			}
			stores = StoreDBOperations.childSParentMWithSpecStatus(parentmES, childES, childAS);
			ms = stores.get(RandomUtils.nextInt(1, stores.size()));
		}
		oneCase.replace("TEXT", String.join("-", ms.get("MERCHANT_ID"), ms.get("MERCHANT_NAME")));
		return oneCase;
	}
	
	/**
	 * 对初始审核状态、激活状态的商户进行状态更新
	 * @param merchantId
	 * @param parentES
	 * @param merchantES
	 * @param merchantAS
	 */
	private static void updateParentChildStatus(String merchantId, ExamineStatus parentES,
											ExamineStatus merchantES, ActivateStatus merchantAS){
		SwiftLogger.debug("预处理商户审核并激活更新到的状态为：所属渠道- " + parentES + ", 商户- " + merchantES + ", " + merchantAS);
		if(!checkInit(merchantId, true))
			throw new SwiftPassException("错误： ", "拒绝支持处理非初始状态的商户及其所属渠道！");
		String parentId = MerchantDBOperations.getMerchant(merchantId).get("CHANNEL_ID");
		if(parentES.equals(ExamineStatus.NON)){
			if(merchantES.equals(ExamineStatus.PASS) || merchantES.equals(ExamineStatus.NEEDAGAIN))
				throw new SwiftPassException("错误： ", impossible(parentES, merchantES));
			else if(merchantES.equals(ExamineStatus.NON)){
				updateChildMerchantStatus(true, merchantId, merchantES, merchantAS);
			} else{
				if(merchantAS.equals(ActivateStatus.PASS) || merchantAS.equals(ActivateStatus.NEEDAGAIN))
					throw new SwiftPassException("错误： 父渠道未审核状态时——", impossible(merchantES, merchantAS));
				else
					updateChildMerchantStatus(true, merchantId, merchantES, merchantAS);
			}

		} else{
			ChannelAAAService.aaaChannel(parentId, ExamineStatus.PASS, ActivateStatus.PASS);
			updateChildMerchantStatus(false, merchantId, merchantES, merchantAS);
			ChannelAAAService.aaaChannel(parentId, parentES, ActivateStatus.FAIL);
		}
	}
	
	private static void updateChildMerchantStatus(boolean isParentInit, String merchantId, ExamineStatus es, ActivateStatus as){
		if(es.equals(ExamineStatus.NON)){
			if(as.equals(ActivateStatus.PASS) || as.equals(ActivateStatus.NEEDAGAIN))
				throw new SwiftPassException("错误： ", impossible(es, as));
			else if(as.equals(ActivateStatus.NOPROCESS))
				;
			else
				MerchantAAAService.activateMerchant(merchantId, as.getSCode());
		} else{
			if(!isParentInit)
				MerchantAAAService.examineMerchant(merchantId, ExamineStatus.PASS.getSCode());
			if(!as.equals(ActivateStatus.NOPROCESS) && !as.equals(ActivateStatus.NEEDAGAIN)){
				MerchantAAAService.activateMerchant(merchantId, as.getSCode());
			} else{
				if(as.equals(ActivateStatus.NEEDAGAIN))
					throw new SwiftPassException("错误： ", impossible(es, as));
				else
					;
			}
			MerchantAAAService.examineMerchant(merchantId, es.getSCode());
		}
	}
	
	private static void updateParentchildStatus(String storeId, ExamineStatus parentES, 
											ExamineStatus childES, ActivateStatus childAS){
		if(!checkInit(storeId, false))
			throw new SwiftPassException("错误： ", "拒绝支持处理非初始状态的门店及其所属大商户！");
		String parentId = MerchantDBOperations.getMerchant(storeId).get("PARENT_MERCHANT");
		if(parentES.equals(ExamineStatus.NON)){
			if(childES.equals(ExamineStatus.PASS) || childES.equals(ExamineStatus.NEEDAGAIN))
				throw new SwiftPassException("错误： ", impossible(parentES, childES));
			else if(childES.equals(ExamineStatus.NON)){
				updateChildStoreStatus(true, storeId, childES, childAS);
			} else{
				if(childAS.equals(ActivateStatus.PASS) || childAS.equals(ActivateStatus.NEEDAGAIN))
					throw new SwiftPassException("错误：", "所属大商户为未审核时，" + impossible(childES, childAS));
				else
					updateChildStoreStatus(true, storeId, childES, childAS);
			}
		} else{
			MerchantAAAService.AAAMerchant(ExamineStatus.PASS, ActivateStatus.PASS, parentId);
			updateChildStoreStatus(false, storeId, childES, childAS);
			MerchantAAAService.examineMerchant(parentId, parentES.getSCode());
		}
	}
	
	private static void updateChildStoreStatus(boolean isParentInit, String storeId, ExamineStatus es, ActivateStatus as){
		updateChildMerchantStatus(isParentInit, storeId, es, as);
	}
	
	private static String impossible(ExamineStatus pes, ExamineStatus mes){
		return "系统不存在子对象的审核状态为： " + mes + ", 父对象的审核状态为： " + pes + "的组合！";
	}
	
	private static String impossible(ExamineStatus mes, ActivateStatus mas){
		return "系统不粗在对象的审核状态为： " + mes + ", 激活状态为： " + mas + "的组合！";
	}
	
	private static boolean checkInit(String merchantId, boolean isMerchant){
		HashMap<String, String> mch = MerchantDBOperations.getMerchant(merchantId);
		HashMap<String, String> parent = null;
		String parentId = null;
		if(isMerchant){
			parentId = mch.get("CHANNEL_ID");
			parent = ChannelDBOperations.getChannel(parentId);
		} else{
			parentId = mch.get("PARENT_MERCHANT");
			parent = MerchantDBOperations.getMerchant(parentId);
		}
		String currentPES = parent.get("EXAMINE_STATUS");
		String currentMES = mch.get("EXAMINE_STATUS");
		String currentMAS = mch.get("ACTIVATE_STATUS");
		return currentPES.equals(ExamineStatus.NON.getSCode()) && 
				currentMES.equals(ExamineStatus.NON.getSCode()) &&
				currentMAS.equals(ActivateStatus.NOPROCESS.getSCode());
	}
	public static void main(String...strings){
		for( HashMap<String,String>  ss[] : getAllMerchantAAATestData()){
			System.out.println(ss[0]);
		}
	}
}