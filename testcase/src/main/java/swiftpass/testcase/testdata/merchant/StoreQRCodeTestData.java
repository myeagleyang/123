package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import swiftpass.utils.SwiftPass;

public class StoreQRCodeTestData {
	
	public static HashMap<String, String>[][] getStoreQRCodeTestData(){
		return getAllStoreQRCodeTestData();
	}
	
	private static HashMap<String, String> getInitPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("isClickFirstRowStore", "true");
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreQRCodeTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		HashMap<String, String> successCaseMap = getInitPageParams();
		list.add(successCaseMap);
		HashMap<String, String> IllegleCaseMap = SwiftPass.copy(successCaseMap);
		IllegleCaseMap.replace("isClickFirstRowStore", "");
		IllegleCaseMap.replace("errorMsg", "请选择要查看二维码的记录!");
		list.add(IllegleCaseMap);
		
		for(HashMap<String, String> oneCaseMap: list){
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCaseMap));
		}
		
		return resultCaseMapArray;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getStoreQRCodeTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
