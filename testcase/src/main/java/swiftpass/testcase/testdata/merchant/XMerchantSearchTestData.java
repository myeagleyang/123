package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.NameValuePair;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.ExpectationInDB;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.enums.MerchantType;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.testcase.casebeans.MerchantSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;

public class XMerchantSearchTestData {
	public static final MerchantSearchCaseBean templateCase = new MerchantSearchCaseBean();
	private static HashMap<Integer, HashMap<String, String>> mchs = MerchantDBOperations.allMerchants();
	public static MerchantSearchCaseBean[][] data(){
		preCheckProcess();
		List<MerchantSearchCaseBean> list = new ArrayList<>();
		//1.单条件查询
		MerchantSearchCaseBean byBeginCTCase = SwiftPass.copy(templateCase);
		byBeginCTCase.setBeginCT(beginCT());
		byBeginCTCase.setCASE_NAME("单条件查询 -> 开始时间...");
		list.add(byBeginCTCase);
		
		MerchantSearchCaseBean byEndCTCase = SwiftPass.copy(templateCase);
		byEndCTCase.setEndCT(endCT());
		byEndCTCase.setCASE_NAME("单条件查询 -> 结束始时间...");
		list.add(byEndCTCase);
		
		MerchantSearchCaseBean byOrgCase = SwiftPass.copy(templateCase);
		String[] orgItems = {"受理机构编号", "受理机构名称"};
		int select = RandomUtils.nextInt(0, orgItems.length);
		String[] org = org();
		byOrgCase.setIsSelectOrg("true");
		byOrgCase.setOrgNameOrIdItem(orgItems[select]);
		byOrgCase.setOrgNameOrId(org[select]);
		byOrgCase.setACCEPT_ORG_ID(org[0]);
		byOrgCase.setORG_NAME(org[1]);
		byOrgCase.setIsConfirmSelectOrg("true");
		byOrgCase.setCASE_NAME("单条件查询 -> 根据受理机构...");
		list.add(byOrgCase);
		
		MerchantSearchCaseBean byPChCase = SwiftPass.copy(templateCase);
		String[] pChItems = {"渠道编号", "渠道名称"};
		select = RandomUtils.nextInt(0, pChItems.length);
		String[] pCh = parentCh();
		byPChCase.setIsSelectPCh("true");
		byPChCase.setpChNameOrIdItem(pChItems[select]);
		byPChCase.setpChNameOrId(pCh[select]);
		byPChCase.setMERCHANT_ID(pCh[0]);
		byPChCase.setMERCHANT_NAME(pCh[1]);
		byPChCase.setIsConfirmSelectPCh("true");
		byPChCase.setCASE_NAME("单条件查询 -> 根据渠道...");
		list.add(byPChCase);
		
		MerchantSearchCaseBean byMerchantNameCase = SwiftPass.copy(templateCase);
		byMerchantNameCase.setMERCHANT_NAME(merchantName());
		byMerchantNameCase.setCASE_NAME("单条件查询 -> 根据商户名称...");
		list.add(byMerchantNameCase);
		
		MerchantSearchCaseBean byPartMerchantNameCase =SwiftPass.copy(templateCase);
		byPartMerchantNameCase.setMERCHANT_NAME(maxMerchantNameChar());
		byPartMerchantNameCase.setCASE_NAME("单条件查询 -> 根据出现频率最高的商户名称...");
		list.add(byPartMerchantNameCase);
		
		MerchantSearchCaseBean byMerchantIdCase = SwiftPass.copy(templateCase);
		byMerchantIdCase.setMERCHANT_ID("MERCHANT_ID");
		byMerchantIdCase.setCASE_NAME("单条件查询 -> 根据商户编码...");
		list.add(byMerchantIdCase);
		
		MerchantSearchCaseBean byMerchantTypeCase = SwiftPass.copy(templateCase);
		byMerchantTypeCase.setMERCHANT_TYPE(merchantType());
		byMerchantTypeCase.setCASE_NAME("单条件查询 -> 根据商户类型...");
		list.add(byMerchantTypeCase);
		
		MerchantSearchCaseBean byExamineStatusCase = SwiftPass.copy(templateCase);
		byExamineStatusCase.setEXAMINE_STATUS(randomES());
		byExamineStatusCase.setCASE_NAME("单条件查询 -> 根据审核状态...");
		list.add(byExamineStatusCase);
		
		MerchantSearchCaseBean byActivateStatusCase = SwiftPass.copy(templateCase);
		byActivateStatusCase.setACTIVATE_STATUS(randomAS());
		byActivateStatusCase.setCASE_NAME("单条件查询 -> 根据激活状态...");
		list.add(byActivateStatusCase);
		
		MerchantSearchCaseBean byIndustryCase = SwiftPass.copy(templateCase);
		String[] industry = industry();
		byIndustryCase.setIsSelectIndustry("true");
		byIndustryCase.setINDUSTR_ID(industry[0].split("-")[industry[0].split("-").length - 1]);
		byIndustryCase.setINDUSTRY_NAME(industry[1]);
		byIndustryCase.setIsConfirmSelectIndustry("true");
		byIndustryCase.setCASE_NAME("单条件查询 -> 根据行业类别...");
		list.add(byIndustryCase);
		
		//2.组合查询
		int count=0;
		for(int i = 0; i < 12; i++){
			String seed = RandomStringUtils.randomNumeric(10);
			MerchantSearchCaseBean combineCase = SwiftPass.copy(templateCase);
			if(seed.charAt(0) % 2 != 0)
				combineCase.setBeginCT(beginCT());
			if(seed.charAt(1) % 2 != 0)
				combineCase.setEndCT(endCT());
			if(seed.charAt(2) % 2 != 0){
				combineCase.setACCEPT_ORG_ID(org[0]);
				combineCase.setORG_NAME(org[1]);
			}
			if(seed.charAt(3) % 2 != 0){
				combineCase.setMERCHANT_ID(pCh[0]);
				combineCase.setMERCHANT_NAME(pCh[1]);
			}
			if(seed.charAt(4) % 2 != 0)
				combineCase.setMERCHANT_NAME(merchantName());
			if(seed.charAt(5) % 2 != 0)
				combineCase.setMERCHANT_ID(merchantId());
			if(seed.charAt(6) % 2 != 0)
				combineCase.setMERCHANT_TYPE(merchantType());
			if(seed.charAt(7) % 2 != 0)
				combineCase.setEXAMINE_STATUS(randomES());
			if(seed.charAt(8) % 2 != 0)
				combineCase.setACTIVATE_STATUS(randomAS());
			if(seed.charAt(9) % 2 != 0){
				combineCase.setINDUSTR_ID(industry[0].split("-")[industry[0].split("-").length - 1]);
				combineCase.setINDUSTRY_NAME(industry[1]);
			}
			count++;
			combineCase.setCASE_NAME("组合查询 - "+count+"...");
			list.add(combineCase);
		}	
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantSearchCaseBean[][]::new);
	}
	
	public static void main(String...strings){
		for(MerchantSearchCaseBean[] cases : data()){
			System.out.println(cases[0]);
			DBUtil.closeDBResource();
		}	
	}
	
	//................................................................................................
	
	private static String[] org(){
		HashMap<String, String> mch = mchs.get(RandomUtils.nextInt(1, mchs.size()));
		HashMap<String, String> org = ChannelDBOperations.getChannel(mch.get("ACCEPT_ORG_ID"));
		return ArrayUtils.toArray(org.get("CHANNEL_ID"), org.get("CHANNEL_NAME"));
	}
	
	private static String[] parentCh(){
		HashMap<String, String> mch = mchs.get(RandomUtils.nextInt(1, mchs.size()));
		HashMap<String, String> ch = ChannelDBOperations.getChannel(mch.get("CHANNEL_ID"));
		return ArrayUtils.toArray(ch.get("CHANNEL_ID"), ch.get("CHANNEL_NAME"));
	}
	
	private static String merchantName(){
		return mchs.get(RandomUtils.nextInt(1, mchs.size())).get("MERCHANT_NAME");
	}
	
	private static String merchantId(){
		return mchs.get(RandomUtils.nextInt(1, mchs.size())).get("MERCHANT_ID");

	}
	
	private static String merchantType(){
		MerchantType[] type = MerchantType.values();
		return type[RandomUtils.nextInt(0, type.length)].getSCode();
	}
	
	private static String randomES(){
		ExamineStatus[] ess = ExamineStatus.values();
		return ess[RandomUtils.nextInt(0, ess.length)].getSCode();
	}
	
	private static String randomAS(){
		ActivateStatus[] ass = ArrayUtils.removeElement(ActivateStatus.values(), ActivateStatus.NEEDAGAIN);
		return ass[RandomUtils.nextInt(0, ass.length)].getSCode();
	}
	
	private static String maxMerchantNameChar(){
		StringBuilder sb = new StringBuilder();
		for(Integer key : mchs.keySet())
			sb.append(mchs.get(key).get("MERCHANT_NAME"));
		return String.valueOf(SwiftPass.getAppearMaxCountChar(sb.toString()));
	}
	
	private static String[] industry(){
		HashMap<String, String> mch = mchs.get(RandomUtils.nextInt(1, mchs.size()));
		HashMap<String, String> mchDetail = MerchantDBOperations.getMerchantDetail(mch.get("MERCHANT_DETAIL_ID"));
		NameValuePair ins = DBOperations.getIndustryChain(mchDetail.get("INDUSTR_ID"));
		return ArrayUtils.toArray(ins.getName(), ins.getValue());
	}
	
	private static String beginCT(){
		return DataGenerator.generateDateBaseOnNow(0, 0, -5);
	}
	
	private static String endCT(){
		return DataGenerator.generateDateBaseOnNow(0, 0, 5);
	}
	
	public static String expectedCount(HashMap<String, String> oneCase){
		return String.valueOf(ExpectationInDB.getMerchantQueryCount(oneCase));
	}
	//...............................................................................................

	private static void preCheckProcess(){
		int mchCount = ExpectationInDB.getMerchantQueryCount(new HashMap<>());
		SwiftLogger.debug("当前系统已有商户数量为：" + mchCount);
		if(mchCount < 50){
			String uniqueId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> ch = ChannelService.addOneChannel(uniqueId);
			if(ch!=null && ch.size()>1){
				ChannelAAAService.aaaChannel(ch.get("CHANNEL_ID"), ExamineStatus.PASS, ActivateStatus.PASS);
				HashMap<Integer, HashMap<String, String>> chs = ChannelDBOperations.allDBChannels();
				for(int i = 0; i < Math.min((50 - mchCount)/4, 8); i++){
					String chId = chs.get(RandomUtils.nextInt(1, chs.size() + 1)).get("CHANNEL_ID");
					HashMap<Integer, HashMap<String, String>> mchs = MerchantService.addMultiMerchant(chId, 4);
					for(Integer key : mchs.keySet()){
						try{
							ExamineStatus es = ExamineStatus.values()[RandomUtils.nextInt(0, 4)];
							ActivateStatus as = ArrayUtils.removeElement(ActivateStatus.values(),
										ActivateStatus.NEEDAGAIN)[RandomUtils.nextInt(0, 4)];
							MerchantAAAService.AAAMerchant(es, as, mchs.get(key).get("MERCHANT_ID"));
						} catch(SwiftPassException e){
						
						}
					}
					SwiftLogger.debug("随机审核商户成功......");
				}
			}
		}
	}
}