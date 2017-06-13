package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.ExpectationInDB;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ChannelProperties;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftPass;

public class ChannelSearchTestData {
	public static HashMap<String, String>[][] getChannelSearchTestData(){
		return getAllChannelSearchTestData();
	}
	
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] valueKeys = {
			"beginCT",			//创建时间起始时间
			"endCT",			//创建时间结束时间
			"EXAMINE_STATUS",	//审核状态
			"ACTIVATE_STATUS",	//激活状态
			"PARENT_CHANNEL",	//所属渠道
			"CHANNEL_NAME",		//渠道名称
			"CHANNEL_ID",		//渠道编号
			"CHANNEL_PROPERTIES"//渠道类型
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		
		return map;
	}
	
	private static HashMap<String, String> getDefaultCaseMap(){
		HashMap<String, String> defaultCaseMap = initPageParamsMap();
		defaultCaseMap.put("expectedCount", "");//预期结果
		return defaultCaseMap;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelSearchTestData(){
		HashMap<String, String>[][] resultCasesMaps = null;
		HashMap<String, String> parentChannel = ChannelDBOperations.acceptOrgUniqueChannel();
		List<HashMap<String, String>> list = new ArrayList<>();
		String beginCT = DataGenerator.generateDateBaseOnNow(0, 0, -5);
		String endCT = DataGenerator.generateDateBaseOnNow(0, 0, 1);
		ExamineStatus[] ESs = ExamineStatus.values();
		ActivateStatus[] ASs = ArrayUtils.removeElement(ActivateStatus.values(), ActivateStatus.NEEDAGAIN);
		String[] CHANNEL_NAME = getChannelNames();
		String CHANNEL_ID = getChannelId();
		ChannelProperties[] CPs = ChannelProperties.values();
		//	1.无参数默认查询
		HashMap<String, String> defaultSearchCaseMap = getDefaultCaseMap();
		list.add(defaultSearchCaseMap);
		
		//	2.非默认查询——单条件查询
		HashMap<String, String> singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("beginCT", beginCT);
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("endCT", endCT);
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("EXAMINE_STATUS", ESs[RandomUtils.nextInt(0, ESs.length)].getSCode());
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("ACTIVATE_STATUS", ASs[RandomUtils.nextInt(0, ASs.length)].getSCode());
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("PARENT_CHANNEL", parentChannel.get("CHANNEL_NAME"));//验证选择所属渠道页面，仅此一次
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("CHANNEL_NAME", CHANNEL_NAME[RandomUtils.nextInt(0, CHANNEL_NAME.length)]);
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("CHANNEL_ID", CHANNEL_ID);
		list.add(singleParamSearchCaseMap);
		singleParamSearchCaseMap = getDefaultCaseMap();
		singleParamSearchCaseMap.replace("CHANNEL_PROPERTIES", CPs[RandomUtils.nextInt(0, CPs.length)].getSCode());
		list.add(singleParamSearchCaseMap);
		
		//	3.组合条件查询——随机选择（12条测试用例）
		for(int i = 0; i < 12; i++){
			String seed = RandomStringUtils.randomNumeric(8);//	生成八位随机数，每位数字代表一个参数是否填，偶数代表不填，奇数代表填
			HashMap<String, String> multiParamsSearchCaseMap = getDefaultCaseMap();
			if(Integer.parseInt(seed.substring(0, 1)) % 2 != 0){
				multiParamsSearchCaseMap.replace("beginCT", beginCT);
			}
			if(Integer.parseInt(seed.substring(1, 2)) % 2 != 0){
				multiParamsSearchCaseMap.replace("endCT", endCT);
			}
			if(Integer.parseInt(seed.substring(2, 3)) % 2 != 0){
				multiParamsSearchCaseMap.replace("EXAMINE_STATUS", ESs[RandomUtils.nextInt(0, ESs.length)].getSCode());
			}
			if(Integer.parseInt(seed.substring(3, 4)) % 2 != 0){
				multiParamsSearchCaseMap.replace("ACTIVATE_STATUS", ASs[RandomUtils.nextInt(0, ASs.length)].getSCode());
			}
			if(Integer.parseInt(seed.substring(4, 5)) % 2 != 0){
				multiParamsSearchCaseMap.replace("PARENT_CHANNEL", parentChannel.get("CHANNEL_ID"));//组合查询时全用渠道id来进行注入，不操作渠道选择页面
			}
			if(Integer.parseInt(seed.substring(5, 6)) % 2 != 0){
				multiParamsSearchCaseMap.replace("CHANNEL_NAME", CHANNEL_NAME[RandomUtils.nextInt(0, CHANNEL_NAME.length)]);
			}
			if(Integer.parseInt(seed.substring(6, 7)) % 2 != 0){
				multiParamsSearchCaseMap.replace("CHANNEL_ID", CHANNEL_ID);
			}
			if(Integer.parseInt(seed.substring(7, 8)) % 2 != 0){
				multiParamsSearchCaseMap.replace("CHANNEL_PROPERTIES", CPs[RandomUtils.nextInt(0, CPs.length)].getSCode());
			}
			list.add(multiParamsSearchCaseMap);
		}
		for(HashMap<String, String> caseMap : list)
			resultCasesMaps = ArrayUtils.add(resultCasesMaps, ArrayUtils.toArray(caseMap));
		//	调用查询，并把实际数据库结果压到相应的用例当中去
		for(HashMap<String, String>[] map : resultCasesMaps)
			for(HashMap<String, String> c : map){
				c.replace("expectedCount", ExpectationInDB.getChannelQueryCount(c) + "");
			}
		return resultCasesMaps;
	}
	
	//获取系统中任一渠道编号
	private static String getChannelId(){
		HashMap<Integer, HashMap<String, String>> allChannels = ChannelDBOperations.allDBChannels();
		return allChannels.get(RandomUtils.nextInt(1, allChannels.size())).get("CHANNEL_ID");
	}
	
	//返回所有渠道名中出现频率最高的一个字符（作为渠道名称模糊查询的参数）和随机的一个全名渠道作为精确查询的参数
	private static String[] getChannelNames(){
		HashMap<Integer, HashMap<String, String>> allChannels = ChannelDBOperations.allDBChannels();
		StringBuilder names = new StringBuilder("");
		for(Integer key : allChannels.keySet()){
			names.append(allChannels.get(key).get("CHANNEL_NAME"));
		}
		String maxCountChar = String.valueOf(SwiftPass.getAppearMaxCountChar(names.toString()));
		HashMap<String,String> randChannel = 
				allChannels.get(RandomUtils.nextInt(1, allChannels.size() + 1));
		return ArrayUtils.toArray(randChannel.get("CHANNEL_NAME"), maxCountChar);
	}
}