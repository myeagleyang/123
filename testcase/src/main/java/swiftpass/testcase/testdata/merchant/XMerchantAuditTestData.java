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
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.testcase.casebeans.MerchantAuditCaseBean;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class XMerchantAuditTestData {
	public static final MerchantAuditCaseBean tmpCase = new MerchantAuditCaseBean();
	static {
		tmpCase.setAuditOperate("")
		.setC_ExamineStatus("")
		.setIsConfirmOperate("")
		.setIsMerchant("")
		.setM_ExamineStatus("")
		.setMessage("")
		.setPM_ExamineStatus("")
		.setTEXT("")
		.setAuditRemark(RandomStringUtils.randomAlphanumeric(32));
	}

	private static final String NO_SELECT_MERCHANT_AUDIT_MSG = "请选择要审核的记录!",
			SELECT_HAS_PASS_STATUS_MERCHANT_MSG = "审核通过的记录不能再次审核!", PC_NO_OR_FAIL_AUDIT_MSG = "请先审核所属渠道",
			PM_NO_OR_FAIL_AUDIT_MSG = "请先审核所属大商户", CLOSE_PAGE_MSG = "正常关闭", CANCEL_OPERATE_MSG = "正常取消";
	private static String[] auditOperates = { "审核通过", "审核不通过", "关闭" };
	/**
	 * 检测系统是否有符合单条测试用例数据需求，若没有则处理是系统符合需求
	 * 
	 * @param oneCase
	 * @return
	 */
	public static MerchantAuditCaseBean preCheckProcess(MerchantAuditCaseBean oneCase) {
		if (oneCase.getMessage().equals(NO_SELECT_MERCHANT_AUDIT_MSG))
			return oneCase;
		String isMerchant = oneCase.getIsMerchant();
		String pES = oneCase.getC_ExamineStatus();
		String ES = oneCase.getM_ExamineStatus();
		String pmES = oneCase.getPM_ExamineStatus();
		if (isMerchant.equals("true")) {
			HashMap<String, String> mch = getSpecStatusMerchant(ExamineStatus.getStatus(pES),
					ExamineStatus.getStatus(ES));
			oneCase.setTEXT(String.join("-", mch.get("MERCHANT_ID"), mch.get("MERCHANT_NAME")));
		} else {
			HashMap<String, String> store = getSpecStatusStore(ExamineStatus.getStatus(pmES),
					ExamineStatus.getStatus(ES));
			oneCase.setTEXT(String.join("-", store.get("MERCHANT_ID"), store.get("MERCHANT_NAME")));
		}

		return oneCase;
	}

	/**
	 * 检测系统是否有符合单条测试用例数据需求，若没有则处理是系统符合需求
	 * 
	 * @param oneCase
	 * @return
	 */
	public static HashMap<String, String> preCheckProcess(HashMap<String, String> oneCase) {
		if (oneCase.get("message").equals(NO_SELECT_MERCHANT_AUDIT_MSG))
			return oneCase;
		String isMerchant = oneCase.get("isMerchant");
		String pES = oneCase.get("C_ExamineStatus");
		String ES = oneCase.get("M_ExamineStatus");
		String pmES = oneCase.get("PM_ExamineStatus");
		if (isMerchant.equals("true")) {
			HashMap<String, String> mch = getSpecStatusMerchant(ExamineStatus.getStatus(pES),
					ExamineStatus.getStatus(ES));
			oneCase.replace("TEXT", String.join("-", mch.get("MERCHANT_ID"), mch.get("MERCHANT_NAME")));
		} else {
			HashMap<String, String> store = getSpecStatusStore(ExamineStatus.getStatus(pmES),
					ExamineStatus.getStatus(ES));
			oneCase.replace("TEXT", String.join("-", store.get("MERCHANT_ID"), store.get("MERCHANT_NAME")));
		}

		return oneCase;
	}
	public static MerchantAuditCaseBean[][] data() {
		List<MerchantAuditCaseBean> list = new ArrayList<>();
		// 1.大商户、普通商户审核用例
		for (ExamineStatus cStatus : ExamineStatus.values()) {// 所属渠道审核状态
			MerchantAuditCaseBean merchantCase = SwiftPass.copy(tmpCase);
			merchantCase.setIsMerchant("true");
			merchantCase.setC_ExamineStatus(cStatus.getSCode());
			for (ExamineStatus mStatus : ExamineStatus.values()) {// 待审核商户审核状态
				merchantCase.setM_ExamineStatus((mStatus.getSCode()));
				// 所属渠道审核状态为审核通过、需再次审核为一类
				if (cStatus.equals(ExamineStatus.PASS) || cStatus.equals(ExamineStatus.NEEDAGAIN)) {
					if (mStatus.equals(ExamineStatus.PASS)) {
						MerchantAuditCaseBean cpmpCase = SwiftPass.copy(merchantCase);
						cpmpCase.setMessage(SELECT_HAS_PASS_STATUS_MERCHANT_MSG);
						cpmpCase.setCASE_NAME("所属渠道审核状态为审核通过、需再次审核为一类...");
						list.add(cpmpCase);
					} else {
						// 审核操作：审核通过、审核不通过
						for (String operate : ArrayUtils.removeElement(auditOperates, auditOperates[2])) {
							MerchantAuditCaseBean operateCase = SwiftPass.copy(merchantCase);
							operateCase.setIsConfirmOperate("true");
							operateCase.setAuditOperate(operate);
							operateCase.setCASE_NAME("审核操作：审核通过、审核不通过...");
							list.add(operateCase);
						}
					}
				} else if (cStatus.equals(ExamineStatus.NON)) {// 所属渠道未审核一类
					if (mStatus.equals(ExamineStatus.NON) || mStatus.equals(ExamineStatus.STOP)) {
						for (String operate : ArrayUtils.removeElement(auditOperates, auditOperates[2])) {
							MerchantAuditCaseBean operateCaseMap = SwiftPass.copy(merchantCase);
							operateCaseMap.setAuditOperate(operate);
							operateCaseMap.setIsConfirmOperate("true");
							operateCaseMap.setMessage(operate.equals(auditOperates[0]) ? PC_NO_OR_FAIL_AUDIT_MSG : "");
							operateCaseMap.setCASE_NAME("所属渠道未审核一类...");
							list.add(operateCaseMap);
						}
					}
				} else {// 所属渠道审核不通过一类
					if (mStatus.equals(ExamineStatus.PASS)) {// 待审核商户当前已是审核通过
						MerchantAuditCaseBean csmpCaseMap = SwiftPass.copy(merchantCase);
						csmpCaseMap.setMessage(SELECT_HAS_PASS_STATUS_MERCHANT_MSG);
						csmpCaseMap.setCASE_NAME("待审核商户当前已是审核通过...");
						list.add(csmpCaseMap);
					} else {// 待审核商户当前为未审核、审核不通过或需再次审核
						for (String operate : ArrayUtils.removeElement(auditOperates, auditOperates[2])) {
							MerchantAuditCaseBean operateCaseMap = SwiftPass.copy(merchantCase);
							operateCaseMap.setAuditOperate(operate);
							operateCaseMap.setIsConfirmOperate("true");
							operateCaseMap.setMessage(operate.equals(auditOperates[0]) ? PC_NO_OR_FAIL_AUDIT_MSG : "");
							operateCaseMap.setCASE_NAME("审核不通过或需再次审核...");
							list.add(operateCaseMap);
						}
					}
				}
			}
		}

		// 2.门店（直营商户、加盟商虎）审核用例
		List<MerchantAuditCaseBean> l = new ArrayList<>();
		for (MerchantAuditCaseBean caseRes : list)
			l.add(SwiftPass.copy(caseRes));
		for (MerchantAuditCaseBean caseMap : l) {
			caseMap.setIsMerchant("false");
			caseMap.setPM_ExamineStatus(caseMap.getC_ExamineStatus());
			caseMap.setC_ExamineStatus("");
			caseMap.setMessage(caseMap.getMessage().equals(PC_NO_OR_FAIL_AUDIT_MSG) ? PM_NO_OR_FAIL_AUDIT_MSG : caseMap.getMessage());
		}
		list.addAll(l);

		// 3.不选择点击审核；正常关闭、取消确认审核操作用例 *3
		MerchantAuditCaseBean noSelectMerchantCaseMap = SwiftPass.copy(tmpCase);
		noSelectMerchantCaseMap.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$");
		noSelectMerchantCaseMap.setMessage(NO_SELECT_MERCHANT_AUDIT_MSG);
		noSelectMerchantCaseMap.setCASE_NAME("请选择要审核的记录...");
		list.add(noSelectMerchantCaseMap);
		
		MerchantAuditCaseBean closePageCaseMap = SwiftPass.copy(tmpCase);
		closePageCaseMap.setIsMerchant("true");
		closePageCaseMap.setC_ExamineStatus(ExamineStatus.PASS.getSCode());
		closePageCaseMap.setM_ExamineStatus(ExamineStatus.NON.getSCode());
		closePageCaseMap.setAuditOperate(auditOperates[2]);
		closePageCaseMap.setMessage(CLOSE_PAGE_MSG);
		closePageCaseMap.setCASE_NAME("正常关闭...");
		list.add(closePageCaseMap);
		
		MerchantAuditCaseBean cancelOperateCaseMap = SwiftPass.copy(closePageCaseMap);
		cancelOperateCaseMap.setAuditOperate(auditOperates[0]);
		cancelOperateCaseMap.setIsConfirmOperate("false");
		cancelOperateCaseMap.setMessage(CANCEL_OPERATE_MSG);
		closePageCaseMap.setCASE_NAME("正常取消...");
		list.add(cancelOperateCaseMap);

		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantAuditCaseBean[][]::new);
	}
	
	public static void main(String [] ss){
		for (MerchantAuditCaseBean [] s:data()){
			System.out.println(s[0]);
		}
	}
	
	private static HashMap<String, String> getSpecStatusMerchant(ExamineStatus pES, ExamineStatus ES) {
		HashMap<Integer, HashMap<String, String>> mchs = MerchantDBOperations.getMchWithPChannelSpecESBoth(pES, ES);
		HashMap<String, String> mch = null;
		if (mchs.size() < 1) {// 系统中当前没有符合需求的商户
			System.out.println(mchs.size() + " -- MpES: " + pES + ", -- MES: " + ES);
			String grandChId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> parentCh = ChannelService.addMultiInnerChannels(grandChId, 1).get(1);
			String parentChId = parentCh.get("CHANNEL_ID");
			HashMap<String, String> newMch = MerchantService.addBigMerchantAttachChannel(parentChId);
			String mchId = newMch.get("MERCHANT_ID");
			doAuditM(parentChId, mchId, pES, ES);
			mchs = MerchantDBOperations.getMchWithPChannelSpecESBoth(pES, ES);
		}
		mch = mchs.get(RandomUtils.nextInt(1, mchs.size())); // 随机获取一个符合需求的商户

		return mch;
	}

	private static HashMap<String, String> getSpecStatusStore(ExamineStatus pmES, ExamineStatus ES) {
		HashMap<Integer, HashMap<String, String>> stores = StoreDBOperations.childSparentMEachWithSpecES(pmES, ES);
		HashMap<String, String> store = null;
		if (stores.size() < 1) {
			System.err.println("-- SpES: " + pmES + ", -- SES: " + ES);
			String chId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> pMch = MerchantService.addBigMerchantAttachChannel(chId);
			String pMchId = pMch.get("MERCHANT_ID");
			HashMap<String, String> newStore = StoreService.addDirectStoreAttachMerchant(pMchId);
			String newStoreId = newStore.get("MERCHANT_ID");
			doAuditS(pMchId, newStoreId, pmES, ES);
			stores = StoreDBOperations.childSparentMEachWithSpecES(pmES, ES);
		}

		store = stores.get(RandomUtils.nextInt(1, stores.size()));

		return store;
	}

	private static String impossibleExists(ExamineStatus pes, ExamineStatus es) {
		return "不存在所属渠道或所属商户审核状态为： " + pes.getPlainText() + ", 商户审核状态为： " + es.getPlainText() + " 的商户(门店)！";
	}

	private static void doAuditM(String parentChId, String mchId, ExamineStatus pes, ExamineStatus es) {
		SwiftLogger.debug("预期需要获取的待审核商户当前的状态是： 所属渠道审核状态—— " + pes + "， 商户的审核状态—— " + es);
		HashMap<String, String> pCh = ChannelDBOperations.getChannel(parentChId);
		HashMap<String, String> mch = MerchantDBOperations.getMerchant(mchId);
		if (!pCh.get("EXAMINE_STATUS").equals(ExamineStatus.NON.getSCode())
				|| !mch.get("EXAMINE_STATUS").equals(ExamineStatus.NON.getSCode())) {
			throw new SwiftPassException("错误： ", "只审核所属渠道未审核，自身未审核状态的商户！");
		}
		switch (pes) {
		case NON:
			switch (es) {
			case PASS:
				throw new SwiftPassException("错误： ", impossibleExists(pes, es));
			case NEEDAGAIN:
				throw new SwiftPassException("错误： ", impossibleExists(pes, es));
			case NON:
				break; // do nothing
			case STOP:
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
				break;
			}
			break;
		case PASS:
			ChannelAAAService.auditChannel(pes, parentChId);// 先审核所属渠道
			switch (es) {
			case NON:
				break; // 商户未审核，不做任何处理
			default:
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
			}
			break;
		case STOP:
			switch (es) {
			case NON:
				ChannelAAAService.auditChannel(pes, parentChId);
				break; // 商户未审核，不做任何处理
			case PASS:
				ChannelAAAService.auditChannel(ExamineStatus.PASS, parentChId);
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
				ChannelAAAService.auditChannel(pes, parentChId);
				break;
			case STOP:
				ChannelAAAService.auditChannel(pes, parentChId);
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
				break;
			case NEEDAGAIN:
				ChannelAAAService.auditChannel(ExamineStatus.PASS, parentChId);
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
				ChannelAAAService.auditChannel(pes, parentChId);
				break;
			}
			break;
		case NEEDAGAIN:
			ChannelAAAService.auditChannel(pes, parentChId);// 先审核所属渠道
			switch (es) {
			case NON:
				break; // 商户未审核，不做任何处理
			default:
				MerchantAAAService.examineMerchant(mchId, es.getSCode());
				break;
			}
			break;
		}
	}

	private static void doAuditS(String parentMchId, String storeId, ExamineStatus pmES, ExamineStatus ES) {
		SwiftLogger.debug("预期需要获取的待审核门店当前的状态是： 所属大商户审核状态—— " + pmES + "， 门店的审核状态—— " + ES);
		HashMap<String, String> bigMch = MerchantDBOperations.getMerchant(parentMchId);
		HashMap<String, String> store = MerchantDBOperations.getMerchant(storeId);
		if (!bigMch.get("EXAMINE_STATUS").equals(ExamineStatus.NON.getSCode())
				|| !store.get("EXAMINE_STATUS").equals(ExamineStatus.NON.getSCode())) {
			throw new SwiftPassException("错误： ", "只审核所属大商户未审核，自身未审核状态的门店！");
		}
		switch (pmES) {
		case NON:
			switch (ES) {
			case PASS:
				throw new SwiftPassException("错误： ", impossibleExists(pmES, ES));
			case NEEDAGAIN:
				throw new SwiftPassException("错误： ", impossibleExists(pmES, ES));
			case NON:
				break; // do nothing
			case STOP:
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
				break;
			}
			break;
		case PASS:
			MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());// 先审核所属大商户
			switch (ES) {
			case NON:
				break; // 门店未审核，不做任何处理
			default:
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
			}
			break;
		case STOP:
			switch (ES) {
			case NON:
				MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());
				break; // 门店未审核，不做任何处理
			case PASS:
				MerchantAAAService.examineMerchant(parentMchId, ExamineStatus.PASS.getSCode());
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
				MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());
				break;
			case STOP:
				MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
				break;
			case NEEDAGAIN:
				MerchantAAAService.examineMerchant(parentMchId, ExamineStatus.PASS.getSCode());
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
				MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());
				break;
			}
			break;
		case NEEDAGAIN:
			MerchantAAAService.examineMerchant(parentMchId, pmES.getSCode());
			switch (ES) {
			case NON:
				break; // 门店未审核，不做任何处理
			default:
				MerchantAAAService.examineMerchant(storeId, ES.getSCode());
				break;
			}
			break;
		}
	}
}