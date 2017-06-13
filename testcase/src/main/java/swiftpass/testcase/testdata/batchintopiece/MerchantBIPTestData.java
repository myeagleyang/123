package swiftpass.testcase.testdata.batchintopiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.elements.batchintopiece.BatchIntoPieceElements;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.batchintopiece.MerchantBIPHelp;
import swiftpass.utils.services.MerchantPCHelp;

public class MerchantBIPTestData {
	static String filePath = "." + SwiftPass.getConfiguration("system.conf").getValueOfKey("BIP");
	
	public static HashMap<String, String>[][] getMerchantBIPTestData(){
		return getAllMerchantBIPTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		String fileName = filePath + "shanghu.xls";
		String channelId = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");//唯一内部机构渠道
		String payTypeName = PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME");
		double billRate = MerchantPCHelp.getMerchantPayConfScope();
		String suffix = SwiftPass.getHHmmssSSSString().substring(5);
		String randomMchName = RandomStringUtils.random(5, "批量进件随机生成的商户");
		String merchantName = randomMchName + suffix;
		String merchantShotName = randomMchName.substring(3) + suffix;
		String[] merchantType = {"大商户", "普通商户"};
		data.put("fileName", fileName);
		data.put("channelId", channelId);
		data.put("payTypeName", payTypeName);
		data.put("billRate", billRate+"");
		data.put("merchantName", merchantName);
		data.put("merchantShotName", merchantShotName);
		data.put("merchantType", merchantType[RandomUtils.nextInt(0, merchantType.length)]);
		data.put("errorMsg", "");
		data.put("msgInfo", "");
		data.put("isSureESAndAS", "true");//是否批量审核与激活此批商户，默认是
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantBIPTestData(){
		HashMap<String, String>[][] resultArrayCaseMap = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> successCaseMap = initPageParams();
		list.add(successCaseMap);
		
		HashMap<String, String> illegeCaseMap = SwiftPass.copy(successCaseMap);
//		illegeCaseMap.replace("fileName", filePath + "shanghu01.xls");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.repeatMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = initPageParams();
		illegeCaseMap.replace("fileName", filePath + "shanghu.xlsx");
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.errorFileTypeMsg);
		list.add(illegeCaseMap);
		
		illegeCaseMap = initPageParams();
		illegeCaseMap.replace("fileName", filePath + "shanghu02.xls");
		illegeCaseMap.replace("channelId", RandomStringUtils.randomNumeric(13));
		illegeCaseMap.replace("errorMsg", BatchIntoPieceElements.partialSuccessMsg);
		illegeCaseMap.replace("msgInfo", "所属渠道：[" + illegeCaseMap.get("channelId") + "]商户所属渠道不存在，");
		list.add(illegeCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			MerchantBIPHelp.generateMerchantBIP(oneCase);
			resultArrayCaseMap = ArrayUtils.add(resultArrayCaseMap, ArrayUtils.toArray(oneCase));
		}
		return resultArrayCaseMap;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getMerchantBIPTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
