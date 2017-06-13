package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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

public class MerchantSearchTestData {
	public static HashMap<String, String>[][] getMerchantSearchTestData(){
		preCheckProcess();
		mchs = MerchantDBOperations.allMerchants();
		return getAllMerchantSearchTestData();
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"beginCT",
				"endCT",
				"ACCEPT_ORG_ID", "ORG_NAME",
				"CHANNEL_ID", "CHANNEL_NAME",
				"MERCHANT_NAME",
				"MERCHANT_ID",
				"MERCHANT_TYPE",
				"EXAMINE_STATUS",
				"ACTIVATE_STATUS",
				"INDUSTR_ID", "INDUSTRY_NAME"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isSelectOrg", "orgNameOrIdItem", "orgNameOrId", "isConfirmSelectOrg",
				"isSelectPCh", "pChNameOrIdItem", "pChNameOrId", "isConfirmSelectPCh",
				"isSelectIndustry", "isConfirmSelectIndustry",
		};
		for(String ctrlKey : ctrlKeys)
				map.put(ctrlKey, "");
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantSearchTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		//1.单条件查询
		HashMap<String, String> byBeginCTCase = caseMapWithCtrlParams();
		byBeginCTCase.replace("beginCT", beginCT());
		list.add(byBeginCTCase);
		HashMap<String, String> byEndCTCase = caseMapWithCtrlParams();
		byEndCTCase.replace("endCT", endCT());
		list.add(byEndCTCase);
		HashMap<String, String> byOrgCase = caseMapWithCtrlParams();
		String[] orgItems = {"受理机构编号", "受理机构名称"};
		int select = RandomUtils.nextInt(0, orgItems.length);
		String[] org = org();
		byOrgCase.replace("isSelectOrg", "true");
		byOrgCase.replace("orgNameOrIdItem", orgItems[select]);
		byOrgCase.replace("orgNameOrId", org[select]);
		byOrgCase.replace("ACCEPT_ORG_ID", org[0]);
		byOrgCase.replace("ORG_NAME", org[1]);
		byOrgCase.replace("isConfirmSelectOrg", "true");
		list.add(byOrgCase);
		HashMap<String, String> byPChCase = caseMapWithCtrlParams();
		String[] pChItems = {"渠道编号", "渠道名称"};
		select = RandomUtils.nextInt(0, pChItems.length);
		String[] pCh = parentCh();
		byPChCase.replace("isSelectPCh", "true");
		byPChCase.replace("pChNameOrIdItem", pChItems[select]);
		byPChCase.replace("pChNameOrId", pCh[select]);
		byPChCase.replace("CHANNEL_ID", pCh[0]);
		byPChCase.replace("CHANNEL_NAME", pCh[1]);
		byPChCase.replace("isConfirmSelectPCh", "true");
		list.add(byPChCase);
		HashMap<String, String> byMerchantNameCase = caseMapWithCtrlParams();
		byMerchantNameCase.replace("MERCHANT_NAME", merchantName());
		list.add(byMerchantNameCase);
		HashMap<String, String> byPartMerchantNameCase = caseMapWithCtrlParams();
		byPartMerchantNameCase.replace("MERCHANT_NAME", maxMerchantNameChar());
		list.add(byPartMerchantNameCase);
		HashMap<String, String> byMerchantIdCase = caseMapWithCtrlParams();
		byMerchantIdCase.replace("MERCHANT_ID", merchantId());
		list.add(byMerchantIdCase);
		HashMap<String, String> byMerchantTypeCase = caseMapWithCtrlParams();
		byMerchantTypeCase.replace("MERCHANT_TYPE", merchantType());
		list.add(byMerchantTypeCase);
		HashMap<String, String> byExamineStatusCase = caseMapWithCtrlParams();
		byExamineStatusCase.replace("EXAMINE_STATUS", randomES());
		list.add(byExamineStatusCase);
		HashMap<String, String> byActivateStatusCase = caseMapWithCtrlParams();
		byActivateStatusCase.replace("ACTIVATE_STATUS", randomAS());
		list.add(byActivateStatusCase);
		HashMap<String, String> byIndustryCase = caseMapWithCtrlParams();
		String[] industry = industry();
		byIndustryCase.replace("isSelectIndustry", "true");
		byIndustryCase.replace("INDUSTR_ID", industry[0].split("-")[industry[0].split("-").length - 1]);
		byIndustryCase.replace("INDUSTRY_NAME", industry[1]);
		byIndustryCase.replace("isConfirmSelectIndustry", "true");
		list.add(byIndustryCase);
		
		//2.组合查询
		for(int i = 0; i < 12; i++){
			String seed = RandomStringUtils.randomNumeric(10);
			HashMap<String, String> combineCase = caseMapWithCtrlParams();
			if(seed.charAt(0) % 2 != 0)
				combineCase.replace("beginCT", beginCT());
			if(seed.charAt(1) % 2 != 0)
				combineCase.replace("endCT", endCT());
			if(seed.charAt(2) % 2 != 0){
				combineCase.replace("ACCEPT_ORG_ID", org[0]);
				combineCase.replace("ORG_NAME", org[1]);
			}
			if(seed.charAt(3) % 2 != 0){
				combineCase.replace("CHANNEL_ID", pCh[0]);
				combineCase.replace("CHANNEL_NAME", pCh[1]);
			}
			if(seed.charAt(4) % 2 != 0)
				combineCase.replace("MERCHANT_NAME", merchantName());
			if(seed.charAt(5) % 2 != 0)
				combineCase.replace("MERCHANT_ID", merchantId());
			if(seed.charAt(6) % 2 != 0)
				combineCase.replace("MERCHANT_TYPE", merchantType());
			if(seed.charAt(7) % 2 != 0)
				combineCase.replace("EXAMINE_STATUS", randomES());
			if(seed.charAt(8) % 2 != 0)
				combineCase.replace("ACTIVATE_STATUS", randomAS());
			if(seed.charAt(9) % 2 != 0){
				combineCase.replace("INDUSTR_ID", industry[0].split("-")[industry[0].split("-").length - 1]);
				combineCase.replace("INDUSTRY_NAME", industry[1]);
			}
			list.add(combineCase);
		}	
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}
	
	public static void main(String...strings){
		for(HashMap<String, String>[] map : getAllMerchantSearchTestData())
			System.out.println(map[0]);
		DBUtil.closeDBResource();
	}
	
	//................................................................................................
	private static HashMap<Integer, HashMap<String, String>> mchs = null;
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
	public static String expectedCount(MerchantSearchCaseBean searchBean){
			int result = 0;
			String sql = "Select count(*) From CMS_MERCHANT Where 1 =1 $CONDITIONS";
			String defaultSql = sql.replace("$CONDITIONS", "");
			StringBuilder conditions = new StringBuilder();
			if(searchBean == null || "".equals(searchBean))
				result = DBUtil.getQueryResultRowCount(defaultSql);
			else{
				String ctCondition = getCreateTimeCondition(searchBean.getBeginCT(), searchBean.getEndCT());
				String aoCondition = getEqualCondition("ACCEPT_ORG_ID", searchBean.getACCEPT_ORG_ID());
				String pcCondition = getEqualCondition("CHANNEL_ID", searchBean.getMERCHANT_ID());
				String mnCondition = getLikeCondition("MERCHANT_NAME", searchBean.getMERCHANT_NAME());
				String miCondition = getEqualCondition("MERCHANT_ID", searchBean.getMERCHANT_ID());
				String mtCondition = getEqualCondition("MERCHANT_TYPE", searchBean.getMERCHANT_TYPE());
				String esCondition = getEqualCondition("EXAMINE_STATUS", searchBean.getEXAMINE_STATUS());
				String asCondition = getEqualCondition("ACTIVATE_STATUS", searchBean.getACTIVATE_STATUS());
				String itCondition = getEqualCondition("INDUSTR_ID", searchBean.getINDUSTR_ID());
				if(!StringUtils.isEmpty(ctCondition))
					conditions.append(" And ").append(ctCondition);
				if(!StringUtils.isEmpty(aoCondition))
					conditions.append(" And ").append(aoCondition);
				if(!StringUtils.isEmpty(pcCondition))
					conditions.append(" And ").append(pcCondition);
				if(!StringUtils.isEmpty(mnCondition))
					conditions.append(" And ").append(mnCondition);
				if(!StringUtils.isEmpty(miCondition))
					conditions.append(" And ").append(miCondition);
				if(!StringUtils.isEmpty(mtCondition))
					conditions.append(" And ").append(mtCondition);
				if(!StringUtils.isEmpty(esCondition))
					conditions.append(" And ").append(esCondition);
				if(!StringUtils.isEmpty(asCondition))
					conditions.append(" And ").append(asCondition);
				if(!StringUtils.isEmpty(itCondition)){
					String cmd = "And MERCHANT_DETAIL_ID IN (Select MERCHANT_DETAIL_ID From CMS_MERCHANT_DETAIL Where "
						+ itCondition + ")";
					conditions.append(cmd);
				}
				SwiftLogger.debug("商户查询语句为： " + sql.replace("$CONDITIONS", conditions.toString()));
				result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
			}
			return String.valueOf(result);
	}
	
	/**
	 * 给定列名及模糊查询值，组装成模糊查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getLikeCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" Like '%").append(value).append("%'");
		return res.toString();
	}

	/**
	 * 给定列名及精确查询值，组装成精确查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getEqualCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" = '").append(value).append("' ");
		return res.toString();
	}
	/**
	 * @param beginCT	yyyy-mm-dd hh:mi:ss格式
	 * @param endCT
	 * @return
	 */
	public static String getCreateTimeCondition(String beginCT, String endCT){
		String str = "to_date('$date','yyyy-mm-dd HH24:mi:ss')";
		if(!StringUtils.isEmpty(beginCT) && StringUtils.isEmpty(endCT)){
			return "CREATE_TIME >= " + str.replace("$date", beginCT);
		} else if(!StringUtils.isEmpty(endCT) && StringUtils.isEmpty(beginCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT);
		} else if(!StringUtils.isEmpty(beginCT) && !StringUtils.isEmpty(endCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT) + " And " + 
					"CREATE_TIME >= " + str.replace("$date", beginCT);
		} else{
			return "";
		}
	}
	//...............................................................................................

	private static void preCheckProcess(){
		int mchCount = ExpectationInDB.getMerchantQueryCount(new HashMap<>());
		SwiftLogger.debug("当前系统已有商户数量为：" + mchCount);
		if(mchCount < 50){
			String uniqueId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> ch = ChannelService.addOneChannel(uniqueId);
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