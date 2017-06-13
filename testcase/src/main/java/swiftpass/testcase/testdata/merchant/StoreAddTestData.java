package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.page.enums.MerchantType;
import swiftpass.utils.SwiftPass;

public class StoreAddTestData {
	private static final HashMap<String, String> bigMerchantInfo = StoreDBOperations.getBigMerchantInfo();
	
	public static HashMap<String, String>[][] getStoreAddTestData(){
		return getAllStoreAddTestData();
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("bigMchItem", "大商户名称"); //或者大商户编号
		data.put("bigMchNameOrId", bigMerchantInfo.get("MERCHANT_NAME"));
		data.put("departName", "");
		data.put("storeName", RandomStringUtils.randomAlphanumeric(6) + "门店");
		data.put("storeType", MerchantType.DIRECT.getPlainText());
		
		//新增一些控制参数
		data.put("isSelectBigMch", "true");
		data.put("isConfirmSelectBigMch", "true");
		data.put("isSave", "true");
		data.put("errorMsg", "");
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreAddTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		String needs[] = {"isSelectBigMch", "storeName", "isSave"};
		String errorMsg[] = {"请选择父商户", "请输入门店名称", "正常关闭"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace("errorMsg", errorMsg[i]);
			list.add(oneCaseMap);
		}

		//1.新增成功用例-直营商户 2.门店名称已存在用例添加成功 3.新增成功-加盟商户
		HashMap<String, String> successCaseMap = getCaseMapWithCrlParams();
		list.add(successCaseMap);
		HashMap<String, String> illegleStoreNameExistCaseMap = SwiftPass.copy(successCaseMap);
		list.add(illegleStoreNameExistCaseMap);
		HashMap<String, String> successCaseMap_ = getCaseMapWithCrlParams();
		successCaseMap_.replace("storeType", MerchantType.JOIN.getPlainText());
		list.add(successCaseMap_);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCase));
		}
		
		return resultCaseMapArray;
	}
	
	public static void main(String...strings){
		long start = System.currentTimeMillis();
		HashMap<String, String>[][] maps = getStoreAddTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
		System.out.println(System.currentTimeMillis() - start);
	}

}
