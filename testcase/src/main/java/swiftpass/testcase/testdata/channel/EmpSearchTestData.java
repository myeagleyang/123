package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.EmpDBOperations;
import irsy.utils.dboperations.ExpectationInDB;

public class EmpSearchTestData{

	public HashMap<String, String> initParamsOnPage() {
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"beginCT",
				"endCT",
				"empCode",
				"empName",
				"phone",
				"orgId", "orgName"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}
	
	String[] items = {"渠道编号", "渠道名称"};
	public HashMap<String, String> caseMapWithCtrlParams() {
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isSelectOrg",
				"idOrNameItem",
				"idOrName",
				"isConfirmSelectOrg",
				"expectCount"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		
		return map;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String>[][] getAllTestData() {
		HashMap<Integer, HashMap<String, String>> emps = EmpDBOperations.emps();
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		//1.默认查询
		HashMap<String, String> defaultSearchCaseMap = caseMapWithCtrlParams();
		list.add(defaultSearchCaseMap);
		
		//2.单条件查询
		HashMap<String, String> byBeginCTCaseMap = caseMapWithCtrlParams();
		byBeginCTCaseMap.replace("beginCT", DataGenerator.generateDateBaseOnNow(0, 0, -5));
		list.add(byBeginCTCaseMap);
		HashMap<String, String> byEndCTCaseMap = caseMapWithCtrlParams();
		byEndCTCaseMap.replace("endCT", DataGenerator.generateDateBaseOnNow(0, 0, -5));
		list.add(byEndCTCaseMap);
		HashMap<String, String> byEmpCodeCaseMap = caseMapWithCtrlParams();
		String empCode = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_CODE");
		byEmpCodeCaseMap.replace("empCode", empCode);
		list.add(byEmpCodeCaseMap);
		HashMap<String, String> byEmpNameCaseMap = caseMapWithCtrlParams();
		String empName = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_NAME");
		byEmpNameCaseMap.replace("empName", empName);
		list.add(byEmpNameCaseMap);
		HashMap<String, String> byPhoneCaseMap = caseMapWithCtrlParams();
		String phone = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("MOBILE");
		byPhoneCaseMap.replace("phone", phone);
		list.add(byPhoneCaseMap);
		HashMap<String, String> byOrgCaseMap = caseMapWithCtrlParams();
		String orgId = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("ORG_ID");
		HashMap<String, String> org = ChannelDBOperations.getChannel(orgId);
		String orgName = org.get("CHANNEL_NAME");
		byOrgCaseMap.replace("isSelectOrg", "true");
		byOrgCaseMap.replace("isConfirmSelectOrg", "true");
		byOrgCaseMap.replace("idOrNameItem", items[RandomUtils.nextInt(0, items.length)]);
		byOrgCaseMap.replace("idOrName", byOrgCaseMap.get("idOrNameItem").equals(items[0]) ? orgId : orgName);
		byOrgCaseMap.replace("orgId", orgId);
		byOrgCaseMap.replace("orgName", orgName);
		list.add(byOrgCaseMap);
		
		//3.组合条件查询
		for(int i = 0; i < 12; i++){
			HashMap<String, String> groupCaseMap = caseMapWithCtrlParams();
			String seed = RandomStringUtils.randomNumeric(6);
			if(seed.charAt(0) % 2 == 0){
				groupCaseMap.replace("beginCT", DataGenerator.generateDateBaseOnNow(0, 0, -3));
			}
			if(seed.charAt(1) % 2 == 0){
				groupCaseMap.replace("endCT", DataGenerator.generateDateBaseOnNow(0, 0, 3));
			}
			if(seed.charAt(2) % 2 == 0){
				groupCaseMap.replace("empCode", emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_CODE"));
			}
			if(seed.charAt(3) % 2 == 0){
				groupCaseMap.replace("empName", emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_NAME"));
			}
			if(seed.charAt(4) % 2 == 0){
				groupCaseMap.replace("phone", emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("MOBILE"));
			}
			if(seed.charAt(5) % 2 == 0){
				groupCaseMap.replace("orgId", orgId);
				groupCaseMap.replace("orgName", orgName);
			}
			list.add(groupCaseMap);
		}
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(preCheckProcess(caseMap)));
		
		return resultCaseMaps;
	}

	public HashMap<String, String> preCheckProcess(HashMap<String, String> oneCase) {
		oneCase.replace("expectCount", String.valueOf(ExpectationInDB.getEmpQueryCount(oneCase)));
		return oneCase;
	}
}