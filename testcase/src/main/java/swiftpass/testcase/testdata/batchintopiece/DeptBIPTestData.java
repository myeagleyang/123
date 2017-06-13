package swiftpass.testcase.testdata.batchintopiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.DeptDBOperations;
import swiftpass.elements.batchintopiece.BatchIntoPieceElements;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.batchintopiece.DeptBIPHelp;

public class DeptBIPTestData {
	static String filePath = "." + SwiftPass.getConfiguration("system.conf").getValueOfKey("BIP");
	
	public static HashMap<String, String>[][] getDeptBIPTestData(){
		return getAllDeptBIPTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("fileName", "");
		data.put("parentMerchantId", "");
		data.put("parentDept", "");
		data.put("deptName", "");
		return data;
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = initPageParams();
		String fileName = filePath + "bumen.xls";
		HashMap<String, String> deptInfo = DeptDBOperations.allDeparts().get(1);
		String parentDept = deptInfo.get("DEPT_NAME");
		String merchantId = deptInfo.get("MERCHANT_ID");
		String suffix = SwiftPass.getHHmmssSSSString().substring(5);
		String randomDeptName = RandomStringUtils.random(5, "批量导入随机生成的部门");
		String deptName = randomDeptName + suffix;
		data.replace("fileName", fileName);
		data.replace("parentMerchantId", merchantId);
		data.replace("parentDept", parentDept);
		data.replace("deptName", deptName);
		
		data.put("errorMsg", "");
		data.put("msgInfo", "");
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllDeptBIPTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
		list.add(oneCaseMap);
		
		HashMap<String, String> illegeCaseMap = SwiftPass.copy(oneCaseMap);
		illegeCaseMap.replace("fileName", filePath + "bumen01.xls");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.repeatMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = getCaseMapWithCrlParams();
		illegeCaseMap.replace("fileName", filePath + "bumen02.xlsx");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.errorFileTypeMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = getCaseMapWithCrlParams();
		illegeCaseMap.replace("fileName", filePath + "bumen03.xls");
		illegeCaseMap.replace("parentMerchantId", RandomStringUtils.randomNumeric(13));
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.partialSuccessMsg);
		illegeCaseMap.replace("msgInfo", "所属大商户：[" + illegeCaseMap.get("parentMerchantId") + "]大商户不存在，父部门：[" 
		+ illegeCaseMap.get("parentDept") + "]父级部门不存在，");
		list.add(illegeCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			DeptBIPHelp.generateDeptBIP(oneCase);
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCase));
		}
		
		return resultCaseMapArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getDeptBIPTestData();
		for(HashMap<String, String>[] map: maps){
			System.err.println(map[0]);
		}
	}

}
