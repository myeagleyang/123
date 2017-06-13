package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.StoreDBOperations;

public class StoreEditTestData {
	private static final HashMap<String, String> exitStoreInfo = StoreDBOperations.getExitStoreInfo();
	
	public static HashMap<String, String>[][] getStoreEditTestData(){
		return getAllStoreEditTestData();
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("storeName", exitStoreInfo.get("MERCHANT_NAME"));
		data.put("storeId", exitStoreInfo.get("MERCHANT_ID"));
		//新增控制参数
		data.put("isClickFirstRowStore", "true");
		data.put("isEdit", "true");
		data.put("isConfirmEdit", "true");
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreEditTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		String needs[] = {"storeName", "isClickFirstRowStore", "isEdit", "isConfirmEdit"};
		String errorMsg[] = {"请输入门店名称", "请选择要编辑的记录!", "正常关闭", "正常取消"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace("errorMsg", errorMsg[i]);
			list.add(oneCaseMap);
		}
		//编辑输入的门店名称已存在报系统异常错误
		HashMap<String, String> illegleStoreNameExit = getCaseMapWithCrlParams();
		illegleStoreNameExit.replace("storeName", StoreDBOperations.getAnotherExitStoreInfo().get("MERCHANT_NAME"));
		illegleStoreNameExit.replace("errorMsg", "系统异常");
		list.add(illegleStoreNameExit);
		//编辑成功用例
		HashMap<String, String> successCaseMap = getCaseMapWithCrlParams();
		successCaseMap.replace("storeName", RandomStringUtils.randomAlphanumeric(7) + "门店");
		list.add(successCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseMapArray;
	}
	
	public static void main(String args[]){
		HashMap<String, String>[][] maps = getStoreEditTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
