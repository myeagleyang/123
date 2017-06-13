package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ExpectationInDB;
import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;

public class StoreSearchTestData {
	public static HashMap<String, String>[][] getStoreSearchTestData(){
		return getAllStoreSearchTestData();
	}
	
	private static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> data = new HashMap<String, String>();
		String[] valueKeys = {
				"bigMchName",             //大商户名称
				"bigMchId",               //大商户id
				"departName",             //部门名称
				"departId",               //部门Id
				"storeName",              //门店名称
				"examineStatus",          //审核状态
				"activateStatus",         //激活状态
				"storeId"                 //门店编号
		};
		for(String valueKey: valueKeys){
			data.put(valueKey, "");
		}
		return data;
	}
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = initPageParamsMap();
		//新增控制参数
		data.put("expected", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllStoreSearchTestData(){
		HashMap<String, String>[][] resultCaseMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> bigMerchantInfo = StoreDBOperations.getBigMerchantInfo();
		String bigMerchantName = bigMerchantInfo.get("MERCHANT_NAME");
		String bigMerchantId = bigMerchantInfo.get("MERCHANT_ID");
		HashMap<String, String> deptInfo = StoreDBOperations.getDepartInfo(bigMerchantId);
		String deptName = deptInfo.get("DEPT_NAME");
		String deptId = deptInfo.get("DEPT_ID");
		String[] storeNames = getStoreNames();
		String storeId = StoreDBOperations.getExitStoreInfo().get("MERCHANT_ID");
		String[] examineStatus = {"0", "1", "2", "3"};
		String[] activateStatus = {"0", "1", "2", "3", "4"};
		//查询全部门店
		HashMap<String, String> searchAllStoreCaseMap = getCaseMapWithCrlParams();
		list.add(searchAllStoreCaseMap);
		//单条件查询
		HashMap<String, String> singleSearchStoreCaseMap = getCaseMapWithCrlParams();//根据大商户查询
		singleSearchStoreCaseMap.replace("bigMchName", bigMerchantName);
		singleSearchStoreCaseMap.replace("bigMchId", bigMerchantId);
		list.add(singleSearchStoreCaseMap);
		singleSearchStoreCaseMap = getCaseMapWithCrlParams();//根据部门查询
		singleSearchStoreCaseMap.replace("departName", deptName);
		singleSearchStoreCaseMap.replace("departId", deptId);
		list.add(singleSearchStoreCaseMap);
		singleSearchStoreCaseMap = getCaseMapWithCrlParams(); //根据门店查询
		singleSearchStoreCaseMap.replace("storeName", storeNames[RandomUtils.nextInt(0, storeNames.length)]);
		list.add(singleSearchStoreCaseMap);
		singleSearchStoreCaseMap = getCaseMapWithCrlParams();//根据审核状态查询
		singleSearchStoreCaseMap.replace("examineStatus", examineStatus[RandomUtils.nextInt(0, examineStatus.length)]);
		list.add(singleSearchStoreCaseMap);
		singleSearchStoreCaseMap = getCaseMapWithCrlParams();//根据激活状态查询
		singleSearchStoreCaseMap.replace("activateStatus", activateStatus[RandomUtils.nextInt(0, activateStatus.length)]);
		list.add(singleSearchStoreCaseMap);
		singleSearchStoreCaseMap = getCaseMapWithCrlParams();
		singleSearchStoreCaseMap.replace("storeId", storeId);
		list.add(singleSearchStoreCaseMap);
		//多条件随机组合 10条用例
		for(int i=0; i<10; i++){
			String random = RandomStringUtils.randomNumeric(6);//生成6位随机数，每位代表一个参数是否填，奇数为代表填，偶数代表不填
			HashMap<String, String> multiSearchStoreCaseMap = getCaseMapWithCrlParams();
			if(Integer.parseInt(random.substring(0, 1)) % 2 != 0){
				multiSearchStoreCaseMap.replace("bigMchName", bigMerchantName);
				multiSearchStoreCaseMap.replace("bigMchId", bigMerchantId);
			}
			if(Integer.parseInt(random.substring(1, 2)) % 2 != 0){
				multiSearchStoreCaseMap.replace("departName", deptName);
				multiSearchStoreCaseMap.replace("departId", deptId);
			}
			if(Integer.parseInt(random.substring(2, 3)) % 2 != 0){
				multiSearchStoreCaseMap.replace("storeName", storeNames[RandomUtils.nextInt(0, storeNames.length)]);
			}
			if(Integer.parseInt(random.substring(3, 4)) % 2 != 0){
				multiSearchStoreCaseMap.replace("examineStatus", examineStatus[RandomUtils.nextInt(0, examineStatus.length)]);
			}
			if(Integer.parseInt(random.substring(4, 5)) % 2 != 0){
				multiSearchStoreCaseMap.replace("activateStatus", activateStatus[RandomUtils.nextInt(0, activateStatus.length)]);
			}
			if(Integer.parseInt(random.substring(5, 6)) % 2 != 0){
				multiSearchStoreCaseMap.replace("storeId", storeId);
			}
			list.add(multiSearchStoreCaseMap);
		}
		//调用查询，并把实际结果压到相应用例
		for(HashMap<String, String> map: list){
			map.replace("expected", ExpectationInDB.getStoreQueryCount(map) + "");
		}
		
		for(HashMap<String, String> oneCaseMap: list){
			resultCaseMapArray = ArrayUtils.add(resultCaseMapArray, ArrayUtils.toArray(oneCaseMap));
		}
		return resultCaseMapArray;
	}
	
	/**
	 * @description 返回一个门店中出现频率最高的字符作为模糊查询字符，以及随机获取一个门店全名作为查询门店
	 * 
	 * */
	private static String[] getStoreNames(){
		String sql = "Select * from cms_merchant where merchant_type in (13, 14)";
		HashMap<Integer, HashMap<String, String>> names = DBUtil.getQueryResultMap(sql);
		StringBuffer sb = new StringBuffer("");
		for(Integer key: names.keySet()){
			sb.append(names.get(key).get("MERCHANT_NAME"));
		}
		String maxChar = String.valueOf(SwiftPass.getAppearMaxCountChar(sb.toString()));
		return ArrayUtils.toArray(names.get(RandomUtils.nextInt(1, names.size()+1)).get("MERCHANT_NAME"), maxChar);
	}
	
	public static void main(String arg[]){
//		String[] array = getStoreNames();
//		for(String s: array){
//			System.err.println(s);
//		}
		HashMap<String, String>[][] maps = getStoreSearchTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
