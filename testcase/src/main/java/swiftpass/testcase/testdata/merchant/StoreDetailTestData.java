package swiftpass.testcase.testdata.merchant;

import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import swiftpass.utils.SwiftPass;

public class StoreDetailTestData {
	public static HashMap<String, String>[][] getStoreDetailTestData(){
		return getAllStoreDetailTestData();
	}
	
	private static HashMap<String, String> getCaseWithCrlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		//新增控制参数
		data.put("isClickFirstRowStore", "true");
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreDetailTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		HashMap<String, String> oneCaseMap = getCaseWithCrlParams();
		HashMap<String, String> oneHashMap_ = SwiftPass.copy(oneCaseMap);
		oneCaseMap.replace("isClickFirstRowStore", "");
		oneCaseMap.replace("errorMsg", "请选择您需要查看的记录!");
		//正常查看门店详情的用例
		
		resultCaseMapArray = ArrayUtils.addAll(resultCaseMapArray, ArrayUtils.toArray(oneCaseMap), ArrayUtils.toArray(oneHashMap_));
		return resultCaseMapArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getStoreDetailTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
