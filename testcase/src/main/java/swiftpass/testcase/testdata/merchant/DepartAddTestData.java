package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.DeptDBOperations;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.DepartService;

public class DepartAddTestData {
	
	public static HashMap<String, String>[][] getDepartAddTestData(){
		return getAllDepartAddTestData();
	}
	
	private static String[] needKeyMessage = {
			"departName-请输入部门名称",
			"parentBigMchName-请选择所属大商户"
	};
	private static String 
			DEPART_NAME_BEYOND_LENGTH_MSG = "部门名称长度在1~64位之间！",
			NON_PASS_PARENT_BIG_MERCHANT_MSG = "请先审核所属大商户",
			CLOSE_PAGE_MSG = "正常关闭",
			CANCEL_SAVE_MSG = "正常取消";
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllDepartAddTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> initMap = caseMapWithCtrlParams();
		
		// 1.成功新增一个部门
		HashMap<String, String> parentMch = DepartService.getHasChildDepartMerchant(ExamineStatus.PASS);
		HashMap<String, String> depart = DeptDBOperations
											.getFirstLevelDepart(parentMch.get("MERCHANT_ID")).get(1);
		HashMap<String, String> successAddDepartCaseMap1 = caseMapWithCtrlParams();
		successAddDepartCaseMap1.replace("parentBigMchName", parentMch.get("MERCHANT_NAME"));
		successAddDepartCaseMap1.replace("parentBigMchId", parentMch.get("MERCHANT_ID"));
		successAddDepartCaseMap1.replace("parentDepart", depart.get("DEPT_NAME"));
		list.add(successAddDepartCaseMap1);
		HashMap<String, String> successAddDepartCaseMap2 = caseMapWithCtrlParams();
		parentMch = DepartService.getHasChildDepartMerchant(ExamineStatus.NEEDAGAIN);
		depart = DeptDBOperations.getFirstLevelDepart(parentMch.get("MERCHANT_ID")).get(1);
		successAddDepartCaseMap2.replace("parentBigMchName", parentMch.get("MERCHANT_NAME"));
		successAddDepartCaseMap2.replace("parentBigMchId", parentMch.get("MERCHANT_ID"));
		successAddDepartCaseMap2.replace("parentDepart", depart.get("DEPT_NAME"));
		list.add(successAddDepartCaseMap2);
		
		//	2.必填字段为空
		HashMap<String, String> success = SwiftPass.copy(successAddDepartCaseMap1);
		success.replace("parentDepart", "");
		for(String keyMessage : needKeyMessage){
			String[] km = keyMessage.split("-");
			HashMap<String, String> noSetValueCaseMap = SwiftPass.copy(success);
			noSetValueCaseMap.replace(km[0], "");
			noSetValueCaseMap.replace("message", km[1]);
			list.add(noSetValueCaseMap);
		}
		
		//	3.其它新增部门失败用例
		HashMap<String, String> nameBeyondLengthCaseMap = SwiftPass.copy(success);
		nameBeyondLengthCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(65));
		nameBeyondLengthCaseMap.replace("message", DEPART_NAME_BEYOND_LENGTH_MSG);//部门名称长度非法
		list.add(nameBeyondLengthCaseMap);
		HashMap<String, String> nonESBigMch = DepartService.getBigMerchantWithES(ExamineStatus.NON);
		HashMap<String, String> nonESParentCaseMap = SwiftPass.copy(initMap);
		nonESParentCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(10));
		nonESParentCaseMap.replace("parentBigMchName", nonESBigMch.get("MERCHANT_NAME"));
		nonESParentCaseMap.replace("parentBigMchId", nonESBigMch.get("MERCHANT_ID"));
		nonESParentCaseMap.replace("message", NON_PASS_PARENT_BIG_MERCHANT_MSG);//父商户未审核
		list.add(nonESParentCaseMap);
		HashMap<String, String> stopESBigMch = DepartService.getBigMerchantWithES(ExamineStatus.STOP);
		HashMap<String, String> stopESParentCaseMap = SwiftPass.copy(initMap);
		stopESParentCaseMap.replace("parentBigMchName", stopESBigMch.get("MERCHANT_NAME"));
		stopESParentCaseMap.replace("parentBigMchId", stopESBigMch.get("MERCHANT_ID"));
		stopESParentCaseMap.replace("message", NON_PASS_PARENT_BIG_MERCHANT_MSG);//父商户审核不通过
		list.add(stopESParentCaseMap);
		HashMap<String, String> closePageCaseMap = SwiftPass.copy(success);
		closePageCaseMap.replace("isSave", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelSaveCaseMap = SwiftPass.copy(success);
		cancelSaveCaseMap.replace("isConfirmSave", "false");
		cancelSaveCaseMap.replace("message", CANCEL_SAVE_MSG);
		list.add(cancelSaveCaseMap);
//		HashMap<String, String> departNameConflictCaseMap = SwiftPass.copy(success);
//		departNameConflictCaseMap.replace("message", DEPART_NAME_CONFLICT_MSG);
//		list.add(departNameConflictCaseMap);
		
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		return resultCaseMaps;
	}
	
	public static void main(String...strings){
		for(HashMap<String, String>[] m : getAllDepartAddTestData())
			System.err.println(m[0]);
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"departName", 	//部门名称
				"parentBigMchName",	//所属大商户名称
				"parentBigMchId",	//所属大商户ID
				"parentDepart",	//父部门
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		String suffix = SwiftPass.getHHmmssSSSString();
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isSelectBigMerchant",
				"mchNameOrIdItem",
				"mchNameOrId",
				"isConfirmSelectBigMerchant",
				"isSave", 			//控制点击保存
				"isConfirmSave",	//控制确认保存
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		//默认参数值
		String[] items = {"大商户名称", "大商户编码"};
		map.replace("departName", RandomStringUtils.random(3, "别人的小目标让人心累") + suffix);
		map.replace("isSelectBigMerchant", "true");
		map.replace("mchNameOrIdItem", items[RandomUtils.nextInt(0, items.length)]);
		map.replace("mchNameOrId", map.get("parentBigMchName"));
		map.replace("isConfirmSelectBigMerchant", "true");
		map.put("isSave", "true");
		map.put("isConfirmSave", "true");
		
		return map;
	}
}
