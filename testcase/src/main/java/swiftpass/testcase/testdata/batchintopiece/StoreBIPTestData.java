package swiftpass.testcase.testdata.batchintopiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.DeptDBOperations;
import swiftpass.elements.batchintopiece.BatchIntoPieceElements;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.batchintopiece.StoreBIPHelp;

public class StoreBIPTestData {
	static String filePath = "." + SwiftPass.getConfiguration("system.conf").getValueOfKey("BIP");
	
	public static HashMap<String, String>[][] getStoreBIPTestData(){
		return getAllStoreBIPCaseData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("fileName", "");
		data.put("parentMerchantId", "");
		data.put("deptName", "");
		data.put("storeName", "");
		data.put("storeType", "");
		return data;
	}
	
	private static HashMap<String, String> getPageWithCtlParams(){
		HashMap<String, String> data = initPageParams();
		String[] storeType = {"加盟商户", "直营商户"};
		String fileName = filePath + "mendian.xls";
		HashMap<String, String> deptInfo = DeptDBOperations.allDeparts().get(1);
		String deptName = deptInfo.get("DEPT_NAME");
		String merchantId = deptInfo.get("MERCHANT_ID");
		String suffix = SwiftPass.getHHmmssSSSString().substring(5);
		String randomStoreName = RandomStringUtils.random(5, "批量导入随机生成的门店");
		String storeName = randomStoreName + suffix;
		data.replace("fileName", fileName);
		data.replace("parentMerchantId", merchantId);
		data.replace("deptName", deptName);
		data.replace("storeName", storeName);
		data.replace("storeType", storeType[RandomUtils.nextInt(0, storeType.length)]);
		
		data.put("errorMsg", "");
		data.put("msgInfo", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreBIPCaseData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> oneCaseMap = getPageWithCtlParams();
		list.add(oneCaseMap);
		
		HashMap<String, String> illegeCaseMap = SwiftPass.copy(oneCaseMap);
		illegeCaseMap.replace("fileName", filePath + "mendian01.xls");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.repeatMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = getPageWithCtlParams();
		illegeCaseMap.replace("fileName", filePath + "mendian02.xlsx");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.errorFileTypeMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = getPageWithCtlParams();
		illegeCaseMap.replace("fileName", filePath + "mendian03.xls");
		illegeCaseMap.replace("parentMerchantId", RandomStringUtils.randomNumeric(13));
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.partialSuccessMsg);
		illegeCaseMap.replace("msgInfo", "父商户：[" + illegeCaseMap.get("parentMerchantId") + "]大商户不存在，"
				+ "部门：[" + illegeCaseMap.get("deptName") + "]部门不存在，");
		list.add(illegeCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			StoreBIPHelp.generateStoreBIP(oneCase);
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
	}
	
	public static void main(String args[]){
		HashMap<String, String>[][] maps = getStoreBIPTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
