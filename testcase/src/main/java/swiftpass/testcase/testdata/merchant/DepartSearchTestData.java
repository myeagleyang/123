package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DeptDBOperations;
import irsy.utils.dboperations.ExpectationInDB;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.DepartService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;

public class DepartSearchTestData {
	public static HashMap<String, String>[][] getDepartSearchTestData(){
		return getAllDepartTestData();
	}
	
	private static HashMap<String, String> initParamsOnPage(){
		HashMap<String, String> map = new HashMap<>();
		String[] valueKeys = {
				"departName",
				"parentBigMerchant", "parentBigMerchantId", "parentBigMerchantName"
		};
		for(String valueKey : valueKeys)
			map.put(valueKey, "");
		return map;
	}
	
	private static HashMap<String, String> caseMapWithCtrlParams(){
		HashMap<String, String> map = initParamsOnPage();
		String[] ctrlKeys = {
				"isSelectMerchant",
				"mchNameOrIdItem",
				"mchNameOrId",
				"isConfirmSelectMerchant",
				
				"expectedCount"
		};
		for(String ctrlKey : ctrlKeys)
			map.put(ctrlKey, "");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllDepartTestData(){
		//预处理数据
		preCheckProcess();
		
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		//1.默认查询
		HashMap<String, String> defaultQueryCase = caseMapWithCtrlParams();
		defaultQueryCase.replace("expectedCount", expectedCount(defaultQueryCase));
		list.add(defaultQueryCase);
		
		//2.单条件查询（按部门名称精确查询、部门名称模糊查询；按所属大商户查询）
		HashMap<String, String> byFullDepartNameCase = caseMapWithCtrlParams();
		byFullDepartNameCase.replace("departName", randomDepartName());
		byFullDepartNameCase.replace("expectedCount", expectedCount(byFullDepartNameCase));
		list.add(byFullDepartNameCase);
		HashMap<String, String> byPartDepartNameCase = caseMapWithCtrlParams();
		byPartDepartNameCase.replace("departName", maxCountDeptNameChar());
		byPartDepartNameCase.replace("expectedCount", expectedCount(byPartDepartNameCase));
		list.add(byPartDepartNameCase);
		String[] items = {"大商户编码", "大商户名称"};
		int select = RandomUtils.nextInt(0, items.length);
		String[] parentBigMerchant = parentBigMerchant();
		HashMap<String, String> byBigMerchantCase = caseMapWithCtrlParams();
		byBigMerchantCase.replace("isSelectMerchant", "true");
		byBigMerchantCase.replace("mchNameOrIdItem", items[select]);
		byBigMerchantCase.replace("mchNameOrId", parentBigMerchant[select]);
		byBigMerchantCase.replace("parentBigMerchant", parentBigMerchant[select]);
		byBigMerchantCase.replace("parentBigMerchantId", parentBigMerchant[0]);
		byBigMerchantCase.replace("paerntBigMerchantName", parentBigMerchant[1]);
		byBigMerchantCase.replace("isConfirmSelectMerchant", "true");
		byBigMerchantCase.replace("expectedCount", expectedCount(byBigMerchantCase));
		list.add(byBigMerchantCase);
		
		//3.组合查询
		HashMap<String, String> byCombineCase = caseMapWithCtrlParams();
		byCombineCase.replace("departName", maxCountDeptNameChar());
		byCombineCase.replace("isSelectMerchant", "true");
		byCombineCase.replace("mchNameOrIdItem", items[select]);
		byCombineCase.replace("mchNameOrId", parentBigMerchant[select]);
		byCombineCase.replace("parentBigMerchant", parentBigMerchant[select]);
		byCombineCase.replace("parentBigMerchantId", parentBigMerchant[0]);
		byCombineCase.replace("paerntBigMerchantName", parentBigMerchant[1]);
		byCombineCase.replace("isConfirmSelectMerchant", "true");
		byCombineCase.replace("expectedCount", expectedCount(byCombineCase));
		list.add(byCombineCase);
		
		for(HashMap<String, String> caseMap : list)
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(caseMap));
		
		return resultCaseMaps;
	}
	
	private static String expectedCount(Map<String, String> params){
		params.put("DEPT_NAME", params.get("departName"));
		params.put("MERCHANT_ID", params.get("parentBigMerchantId"));
		return String.valueOf(ExpectationInDB.getDepartQueryCount((HashMap<String, String>)params));
	}
	
	private static String maxCountDeptNameChar(){
		StringBuilder sb = new StringBuilder();
		Map<Integer, HashMap<String, String>> depts = DeptDBOperations.allDeparts();
		for(Integer key : depts.keySet())
			sb.append(depts.get(key).get("DEPT_NAME"));
		
		return String.valueOf(SwiftPass.getAppearMaxCountChar(sb.toString()));
	}
	
	private static String randomDepartName(){
		Map<Integer, HashMap<String, String>> depts = DeptDBOperations.allDeparts();
		return depts.get(RandomUtils.nextInt(1, depts.size())).get("DEPT_NAME");
	}
	
	private static String[] parentBigMerchant(){
		String merchantId = DeptDBOperations.allDeparts().get(1).get("MERCHANT_ID");
		String merchantName = MerchantDBOperations.getMerchant(merchantId).get("MERCHANT_NAME");
		return ArrayUtils.toArray(merchantId, merchantName);
	}
	
	private static void preCheckProcess(){
		int expected = 30;
		Map<Integer, HashMap<String, String>> sys = DeptDBOperations.allDeparts();
		if(sys.size() < expected){
			String channelId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> bigM = MerchantService.addBigMerchantAttachChannel(channelId);
			String parentBigMchId = bigM.get("MERCHANT_ID");
			MerchantAAAService.examineMerchant(parentBigMchId, ExamineStatus.PASS.getSCode());
			for(int i = 0; i < expected - sys.size(); i++){
				DepartService.addDepartWithoutParentDept(parentBigMchId);
			}
		}
	}
}