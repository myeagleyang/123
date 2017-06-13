package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.MerchantType;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.testcase.casebeans.MerchantActiveCaseBean;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;
import swiftpass.utils.services.StoreService;

public class XMerchantActiveTestData {
	private static final MerchantActiveCaseBean tmpCase = new MerchantActiveCaseBean();
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setACTIVATE_STATUS_REMARK(RandomStringUtils.randomAlphanumeric(32))
		.setActiveOperate("")
		.setIsConfirmOperate("")
		.setIsMerchant("")
		.setM_ActivateStatus("")
		.setM_ExamineStatus("")
		.setMessage("")
		.setTEXT("");
	}
	private static String
		NO_SELECT_MERCHANT_ACTIVE_MSG = "请选择要激活的记录!",
		MERCHANT_NOT_AUDIT_PASS_MSG = "请先审核",
		CLOSE_PAGE_MSG = "正常关闭",
		CANCEL_OPERATE_MSG = "正常取消";
	private static String[] activeOperates = {"激活通过", "激活不通过", "冻结", "关闭"};
	
	public static MerchantActiveCaseBean[][] data(){
		List<MerchantActiveCaseBean> list = new ArrayList<>();
		MerchantActiveCaseBean fixedCaseMap = SwiftPass.copy(tmpCase);
		
		//	1.商户激活
		for(ExamineStatus mchES : ExamineStatus.values()){
			for(ActivateStatus mchAS : ArrayUtils.removeElement(ActivateStatus.values(), ActivateStatus.NEEDAGAIN)){
				MerchantActiveCaseBean merchantCaseMap = SwiftPass.copy(fixedCaseMap);
				merchantCaseMap.setIsMerchant("true")
				.setM_ExamineStatus(mchES.getSCode())
				.setIsConfirmOperate("true")
				.setM_ActivateStatus(mchAS.getSCode());
				if(mchES.equals(ExamineStatus.PASS) || mchES.equals(ExamineStatus.NEEDAGAIN)){
					if(mchAS.equals(ActivateStatus.PASS)){
						//已审核通过、需再次审核——激活成功的商户只有冻结这个激活操作
						MerchantActiveCaseBean one = SwiftPass.copy(merchantCaseMap);
						one.setActiveOperate(activeOperates[2]);
						one.setCASE_NAME("已审核通过、需再次审核——激活成功的商户...");
						list.add(one);
					} else{
						for(String operate : ArrayUtils.removeElement(activeOperates, activeOperates[3])){
							MerchantActiveCaseBean one = SwiftPass.copy(merchantCaseMap);
							one.setActiveOperate(operate);
							one.setCASE_NAME("冻结商户...");
							list.add(one);
						}
					}
				} else if(mchES.equals(ExamineStatus.NON)){
					//未审核状态的商户不可能是激活成功的,当然也不存在需再次激活这种
					if(!mchAS.equals(ActivateStatus.PASS)){
						for(String operate : ArrayUtils.removeElement(activeOperates, activeOperates[3])){
							MerchantActiveCaseBean one = SwiftPass.copy(merchantCaseMap);
							one.setActiveOperate(operate);
							if(operate.equals(activeOperates[0]))
								one.setMessage(MERCHANT_NOT_AUDIT_PASS_MSG);
							one.setCASE_NAME("请先审核...");
							list.add(one);
						}
					}
				} else{
					//待激活操作商户审核状态为失败
					if(mchAS.equals(ActivateStatus.PASS)){
						MerchantActiveCaseBean one = SwiftPass.copy(merchantCaseMap);
						one.setActiveOperate(activeOperates[2]);
						one.setCASE_NAME("商户冻结...");
						list.add(one);
					} else{
						for(String operate : ArrayUtils.removeElement(activeOperates, activeOperates[3])){
							MerchantActiveCaseBean one = SwiftPass.copy(merchantCaseMap);
							one.setActiveOperate(operate);
							if(operate.equals(activeOperates[0]))
								one.setMessage(MERCHANT_NOT_AUDIT_PASS_MSG);
							one.setCASE_NAME("请先审核...");
							list.add(one);
						}
					}
				}
			}
		}
		
		//	2.门店激活
		List<MerchantActiveCaseBean> l = new ArrayList<>();
		for(MerchantActiveCaseBean caseMap : list){
			MerchantActiveCaseBean storeCaseMap = SwiftPass.copy(caseMap);
			storeCaseMap.setIsMerchant("false");
			storeCaseMap.setCASE_NAME("门店激活...");
			l.add(storeCaseMap);
		}
		list.addAll(l);
		
		//	3.未选中商户点击激活报错、正常关闭页面及取消确认激活操作并关闭页面
		MerchantActiveCaseBean noSelectMerchantCaseMap = SwiftPass.copy(fixedCaseMap);
		noSelectMerchantCaseMap.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectMerchantCaseMap.setMessage(NO_SELECT_MERCHANT_ACTIVE_MSG);
		noSelectMerchantCaseMap.setCASE_NAME("未选择记录...");
		list.add(noSelectMerchantCaseMap);
		
		MerchantActiveCaseBean closePageCaseMap = SwiftPass.copy(fixedCaseMap);
		closePageCaseMap.setIsMerchant("true");
		closePageCaseMap.setM_ExamineStatus(ExamineStatus.PASS.getSCode());
		closePageCaseMap.setM_ActivateStatus(ActivateStatus.NOPROCESS.getSCode());
		closePageCaseMap.setActiveOperate(activeOperates[3]);
		closePageCaseMap.setMessage(CLOSE_PAGE_MSG);
		closePageCaseMap.setCASE_NAME("正常关闭...");
		list.add(closePageCaseMap);
		
		MerchantActiveCaseBean cancelOperateCaseMap = SwiftPass.copy(closePageCaseMap);
		cancelOperateCaseMap.setActiveOperate(activeOperates[2]);
		cancelOperateCaseMap.setIsConfirmOperate("false");
		cancelOperateCaseMap.setMessage(CANCEL_OPERATE_MSG);
		cancelOperateCaseMap.setCASE_NAME("正常取消...");
		list.add(cancelOperateCaseMap);
		
		return list.stream().map(element ->ArrayUtils.toArray(element)).toArray(MerchantActiveCaseBean[][] :: new );
	}
	/**
	 * 处理商户审核单条用例；根据审核、激活状态及是否为商户来获取数据
	 * @param oneCase
	 * @return
	 */
	public static MerchantActiveCaseBean preCheckProcessCase(MerchantActiveCaseBean oneCase){
		String mes = oneCase.getM_ExamineStatus();
		String mas = oneCase.getM_ActivateStatus();
		if(oneCase.getTEXT().contains("$$$$"))
			return oneCase;
		else{
			boolean isMerchant = Boolean.parseBoolean(oneCase.getIsMerchant());
			HashMap<String, String> mch = getSpecStatusMerchant(isMerchant, 
					ExamineStatus.getStatus(mes), ActivateStatus.getStatus(mas));
			oneCase.setTEXT(String.join("-", mch.get("MERCHANT_ID"), mch.get("MERCHANT_NAME")));
			return oneCase;
		}
	}
	
	private static HashMap<String, String> getSpecStatusMerchant(boolean isMerchant, ExamineStatus es, ActivateStatus as){
		HashMap<String, String> mch = null;
		HashMap<Integer, HashMap<String, String>> mchs = null;
		if(isMerchant){
			mchs = MerchantDBOperations.getMerchantsOfStatus(es, as, MerchantType.BIG, MerchantType.NORMAL);
		} else{
			mchs = MerchantDBOperations.getMerchantsOfStatus(es, as, MerchantType.DIRECT, MerchantType.JOIN);
		}
		if(mchs.size() < 1){
			HashMap<String, String> unique = ChannelDBOperations.acceptOrgUniqueChannel();
			String pChId = unique.get("CHANNEL_ID");
			HashMap<String, String> bigMch = MerchantService.addBigMerchantAttachChannel(pChId);
			String pMchId = bigMch.get("MERCHANT_ID");
			ChannelAAAService.aaaChannel(pChId, ExamineStatus.PASS, ActivateStatus.PASS);
			if(isMerchant){
				doActiveMerchant(bigMch, es, as);
				mch = MerchantDBOperations.getMerchant(pMchId);
			} else{
				MerchantAAAService.AAAMerchant(ExamineStatus.PASS, ActivateStatus.PASS, pMchId);
				HashMap<String, String> store = StoreService.addDirectStoreAttachMerchant(pMchId);
				String storeId = store.get("MERCHANT_ID");
				doActiveMerchant(store, es, as);
				mch = MerchantDBOperations.getMerchant(storeId);
			}
		} else{
			mch = mchs.get(RandomUtils.nextInt(1, mchs.size()));
		}
		
		return mch;
	}
	
	private static String impossible(ExamineStatus es, ActivateStatus as){
		return "系统不可能存在审核状态为： " + es + ", 激活状态为： " + as + "的商户或门店";
	}
	
	private static void doActiveMerchant(HashMap<String, String> m, ExamineStatus es, ActivateStatus as){
		SwiftLogger.debug("要获取的商户或门店的审核状态为： " + es + ", 激活状态： " + as);
		String mId = m.get("MERCHANT_ID");
		String mes = m.get("EXAMINE_STATUS");
		String mas = m.get("ACTIVATE_STATUS");
		if(!mes.equals(ExamineStatus.NON.getSCode()) || !mas.equals(ActivateStatus.NOPROCESS.getSCode())){
			throw new SwiftPassException("错误： ", "只处理初始审核、激活状态的商户或门店！");
		}
		switch(es){
		case NON:
			switch(as){
			case NOPROCESS:	break;
			case PASS:	throw new SwiftPassException("错误： ", impossible(es, as));
			case FAIL:
				MerchantAAAService.activateMerchant(mId, as.getSCode());
				break;
			case FREEZE:
				MerchantAAAService.activateMerchant(mId, as.getSCode());
				break;
			default:	break;
			}
			break;
		case PASS:
			MerchantAAAService.examineMerchant(mId, es.getSCode());
			switch(as){
			case NOPROCESS:	break;
			default:
				MerchantAAAService.activateMerchant(mId, as.getSCode());
			}
			break;
		case STOP:
			MerchantAAAService.examineMerchant(mId, ExamineStatus.PASS.getSCode());
			switch(as){
			case NOPROCESS:	
				break;
			default:
				MerchantAAAService.activateMerchant(mId, as.getSCode());
				break;
			}
			MerchantAAAService.examineMerchant(mId, es.getSCode());
			break;
		case NEEDAGAIN:
			MerchantAAAService.examineMerchant(mId, es.getSCode());
			switch(as){
			case NOPROCESS:	break;
			default:
				MerchantAAAService.activateMerchant(mId, as.getSCode());
				break;
			}
			break;
		}
	}
	
	public static void main(String [] strings){
		for(MerchantActiveCaseBean[] map : data()){
			System.out.println(preCheckProcessCase(map[0]));
		}
	}
}
