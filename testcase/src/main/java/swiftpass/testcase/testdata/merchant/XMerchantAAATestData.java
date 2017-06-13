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
import swiftpass.testcase.casebeans.DepartmentAddCaseBean;
import swiftpass.testcase.casebeans.MerchantAAACaseBean;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class XMerchantAAATestData {
	
	public final static MerchantAAACaseBean successBean = new MerchantAAACaseBean();
	
	
	private static final String 
						PARENT_MERCHANT_HAS_NO_AUDIT_MSG = "请先审核所属大商户",
						PARENT_CHANNEL_HAS_NO_AUDIT_MSG = "请先审核所属渠道",
						NO_SELECT_MERCHANT_OPERATE_MSG = "请选择要审核并激活的记录!",
						CLOSE_PAGE_MSG = "正常关闭",
						CANCEL_OPERATE_MSG = "正常取消";
	private static String[] aaOperates = {"通过", "不通过", "关闭"};
	
	
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
	
	private static List<MerchantAAACaseBean> addOperateByCaseBean(List<MerchantAAACaseBean> old){
		List<MerchantAAACaseBean> result = new ArrayList<>();
		for(MerchantAAACaseBean e : old){
			for(String aaOperate : ArrayUtils.removeElement(aaOperates, aaOperates[2])){
				MerchantAAACaseBean oneCase = SwiftPass.copy(e);
				oneCase.setAaOperate(aaOperate);
				if(aaOperate.equals(aaOperates[0])){
					if(oneCase.getPC_ExamineStatus().equals(ExamineStatus.NON.getSCode()) ||
							(oneCase.getPC_ExamineStatus().equals(ExamineStatus.STOP.getSCode()) &&
								!oneCase.getM_ExamineStatus().equals(ExamineStatus.PASS.getSCode()))){
							oneCase.setMessage(PARENT_CHANNEL_HAS_NO_AUDIT_MSG);
					}
				}
				result.add(oneCase);
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		for(MerchantAAACaseBean[] ss:data()){
			System.out.println(ss[0]);
		}
	}
	
	public static MerchantAAACaseBean [][] data(){
		//HashMap<String, String>[][] resultCasesMaps = null;
		List<MerchantAAACaseBean> list = new ArrayList<>();
		
		//MerchantAAACaseBean defaultCase =SwiftPass.copy(successBean);
		//HashMap<String, String> initCaseMap = caseMapWithCtrlParams();
		
		//	1.商户审核并激活用例
		List<HashMap<String, String>> statusGroups = mchStatusGroups();
		MerchantAAACaseBean mchFixed = SwiftPass.copy(successBean);
		mchFixed.setIsMerchant("true");
		mchFixed.setIsConfirmOperate("true");
		for(HashMap<String, String> statusGroup : statusGroups){
			MerchantAAACaseBean oneCase = SwiftPass.copy(mchFixed);
			for(String key : statusGroup.keySet()){
				//oneCase.replace(key, statusGroup.get(key));
				if(key.equals("PC_ExamineStatus")){
					oneCase.setPC_ExamineStatus(statusGroup.get("PC_ExamineStatus"));
				}else if(key.equals("M_ExamineStatus")){
					oneCase.setM_ExamineStatus(statusGroup.get("M_ExamineStatus"));
				}else if(key.equals("M_ActivateStatus")){
					oneCase.setM_ActivateStatus(statusGroup.get("M_ActivateStatus"));
				}	
			}
		
			oneCase.setCASE_NAME("根据渠道，商户状态...");
			list.add(oneCase);
		}
		list = addOperateByCaseBean(list);
		
		//	2.门店审核并激活用例——只需替换商户审核的上级机构对象为大商户
		List<MerchantAAACaseBean> stores = new ArrayList<>();
		for(MerchantAAACaseBean mch : list){
			MerchantAAACaseBean tempcase = SwiftPass.copy(mch);
			tempcase.setIsMerchant("false");
			tempcase.setPM_ExamineStatus(tempcase.getPC_ExamineStatus());
			tempcase.setPC_ExamineStatus("");
			if(tempcase.getMessage().equals(PARENT_CHANNEL_HAS_NO_AUDIT_MSG)){
				tempcase.setMessage(PARENT_MERCHANT_HAS_NO_AUDIT_MSG);
			}	
			stores.add(tempcase);
		}
		
	
		list.addAll(stores);
		
		//	不选中商户操作；正常关闭页面、取消确认操作并关闭页面
		MerchantAAACaseBean noSelectMerchantOperateCase = SwiftPass.copy(successBean);
		noSelectMerchantOperateCase.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectMerchantOperateCase.setMessage(NO_SELECT_MERCHANT_OPERATE_MSG);
		noSelectMerchantOperateCase.setCASE_NAME(NO_SELECT_MERCHANT_OPERATE_MSG);
		list.add(noSelectMerchantOperateCase);
		
		 MerchantAAACaseBean closePageCase= SwiftPass.copy(successBean);
		 closePageCase.setPC_ExamineStatus(ExamineStatus.PASS.getSCode());
		 closePageCase.setM_ExamineStatus(ExamineStatus.PASS.getSCode());
		 closePageCase.setM_ActivateStatus(ExamineStatus.PASS.getSCode());
		 closePageCase.setAaOperate(aaOperates[2]);
		 closePageCase.setIsMerchant("true");
		 closePageCase.setIsConfirmOperate("true");
		 closePageCase.setMessage(CLOSE_PAGE_MSG);
		 closePageCase.setCASE_NAME("正常关闭验证...");
		 list.add(closePageCase);
		
		MerchantAAACaseBean cancelOperateCase = SwiftPass.copy(closePageCase);
		cancelOperateCase.setAaOperate(aaOperates[0]);
		cancelOperateCase.setIsMerchant("true");
		cancelOperateCase.setIsConfirmOperate("false");
		cancelOperateCase.setMessage(CANCEL_OPERATE_MSG);
		cancelOperateCase.setCASE_NAME("正常取消验证...");
		list.add(cancelOperateCase);
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantAAACaseBean[][]::new);
		
	}
	
	public static MerchantAAACaseBean preCheckProcessCase(MerchantAAACaseBean oneCase){
		String isMerchant = oneCase.getIsMerchant();
		String TEXT = oneCase.getTEXT();
		if(TEXT.contains("$$$")) return oneCase;
		SwiftLogger.debug("预处理的商户审核并激活操作的用例数据是：" + oneCase);
		ExamineStatus parentcES = isMerchant.equals("true") ? ExamineStatus.getStatus(oneCase.getPC_ExamineStatus()) : null;
		ExamineStatus parentmES = isMerchant.equals("false") ? ExamineStatus.getStatus(oneCase.getPM_ExamineStatus()) : null;
		ExamineStatus childES = ExamineStatus.getStatus(oneCase.getM_ExamineStatus());
		ActivateStatus childAS = ActivateStatus.getStatus(oneCase.getM_ActivateStatus());
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
		oneCase.setTEXT(String.join("-", ms.get("MERCHANT_ID"), ms.get("MERCHANT_NAME")));
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
}