package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DeptDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.DepartService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;

public class DepartEditTestData {
	public static HashMap<String, String>[][] getDepartEditTestData(){
		return getAllDepartEditTestData();
	}
	
	private static final String 
								NULL_DEPART_NAME_MSG = "请输入部门名称",
								BEYOND_NAME_LENGTH_MSG = "部门名称长度在1~64位之间！",
								NO_SELECT_EDTI_MSG = "请选择要编辑的记录!",
								CLOSE_PAGE_MSG = "正常关闭",
								CANCEL_EDIT_MSG = "正常取消";
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"departName"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		map.replace("departName", RandomStringUtils.randomAlphabetic(24));
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"TEXT",
				"isEdit",
				"isConfirmEdit",
				"message"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		map.replace("isEdit", "true");
		map.replace("isConfirmEdit", "true");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllDepartEditTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		HashMap<Integer, HashMap<String, String>> departs = getEditableDepart();
		HashMap<String, String> depart1 = departs.get(1);
		HashMap<String, String> depart2 = departs.get(2);
		HashMap<String, String> depart3 = departs.get(3);
		//1.成功编辑
		HashMap<String, String> successEditCaseMap = caseMapWithCtrlParams();
		successEditCaseMap.replace("TEXT", String.join("-", depart1.get("DEPT_ID"), depart1.get("DEPT_NAME")));
		successEditCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(32));
		list.add(successEditCaseMap);
		
		//2.部门名非法用例（空、非法长度）
		HashMap<String, String> nullDepartNameCaseMap = caseMapWithCtrlParams();
		nullDepartNameCaseMap.replace("TEXT", String.join("-", depart2.get("DEPT_ID"), depart2.get("DEPT_NAME")));
		nullDepartNameCaseMap.replace("departName", "");
		nullDepartNameCaseMap.replace("message", NULL_DEPART_NAME_MSG);
		list.add(nullDepartNameCaseMap);
		HashMap<String, String> beyondLengthCaseMap = caseMapWithCtrlParams();
		beyondLengthCaseMap.replace("TEXT", String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")));
		beyondLengthCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(65));
		beyondLengthCaseMap.replace("message", BEYOND_NAME_LENGTH_MSG);
		list.add(beyondLengthCaseMap);
		
		//3.未选中部门编辑
		HashMap<String, String> noSelectEditCaseMap = caseMapWithCtrlParams();
		noSelectEditCaseMap.replace("TEXT", "$$$$$$$$$$$$-$$$$$$$$$$$$");
		noSelectEditCaseMap.replace("message", NO_SELECT_EDTI_MSG);
		list.add(noSelectEditCaseMap);
		
		//4.关闭或取消编辑
		HashMap<String, String> closePageCaseMap = caseMapWithCtrlParams();
		closePageCaseMap.replace("TEXT", String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")));
		closePageCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(24));
		closePageCaseMap.replace("isEdit", "false");
		closePageCaseMap.replace("message", CLOSE_PAGE_MSG);
		list.add(closePageCaseMap);
		HashMap<String, String> cancelOperateCaseMap = caseMapWithCtrlParams();
		cancelOperateCaseMap.replace("TEXT", String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")));
		cancelOperateCaseMap.replace("departName", RandomStringUtils.randomAlphanumeric(24));
		cancelOperateCaseMap.replace("isConfirmEdit", "false");
		cancelOperateCaseMap.replace("message", CANCEL_EDIT_MSG);
		list.add(cancelOperateCaseMap);	
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));	
		return resultCaseMaps;
	}
	
	private static HashMap<Integer, HashMap<String, String>> getEditableDepart(){
		int expectedCount = 3;
		HashMap<Integer, HashMap<String, String>> departs = DeptDBOperations.allDeparts();
		if(departs.size() < expectedCount){
			HashMap<String, String> unique = ChannelDBOperations.acceptOrgUniqueChannel();
			String uniqueId = unique.get("CHANNEL_ID");
			if(!unique.get("EXAMINE_STATUS").equals(ExamineStatus.PASS))
				ChannelAAAService.aaaChannel(uniqueId, ExamineStatus.PASS, ActivateStatus.PASS);
			HashMap<String, String> bigParent = MerchantService.addBigMerchantAttachChannel(uniqueId);
			String bigParentId = bigParent.get("MERCHANT_ID");
			MerchantAAAService.AAAMerchant(ExamineStatus.PASS, ActivateStatus.PASS, bigParentId);
			for(int i = 0; i < expectedCount - departs.size(); i++)
				DepartService.addDepartWithoutParentDept(bigParentId);
			departs = DeptDBOperations.allDeparts();
		}
		return departs;
	}
}