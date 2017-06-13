package swiftpass.testcase.testdata.channel.xychannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class ChannelPCEditTestData {
	
	public static HashMap<String, String>[][] getChannelPCEditTestData(){
		return getAllChannelPCEditTestData();
	}
	
	public static String ALLOWSETMINMCHCLRRATE = "allowSetMinMchClrRate",  //允许设置最小商户费率
			ISALLOWQCFAST = "isAllowQRFast", //是否允许二维码快速进件
			SINGLEMAX = "singleMax",  //单笔最大限额
			SINGLEMIN = "singleMin"   //单笔最小限额
			;  
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = swiftpass.testcase.testdata.channel.ChannelPCEditTestData.getCaseMapWithCtrlParams();
		data.put(ISALLOWQCFAST, "否");
		data.put(ALLOWSETMINMCHCLRRATE, 
				String.valueOf(Double.parseDouble(data.get(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.CLRRATE))+1));
		data.put(SINGLEMAX, "0.0");
		data.put(SINGLEMIN, "0.0");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelPCEditTestData(){
		HashMap<String, String>[][] bzChannelPCEditTestData = swiftpass.testcase.testdata.channel.ChannelPCEditTestData.getChannelPayConfEditTestData();
		HashMap<String, String>[][] data = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		//异常用例 1.未输入最低商户费率 2.最低商户费率低于结算费率
		HashMap<String, String> illegleCaseMap = getCaseMapWithCrlParams();
		illegleCaseMap.replace(ALLOWSETMINMCHCLRRATE, "");
		illegleCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.ERRORMSG, "请输入最低商户费率");
		list.add(illegleCaseMap);
		illegleCaseMap = getCaseMapWithCrlParams();
		illegleCaseMap.replace(ALLOWSETMINMCHCLRRATE, 
				String.valueOf(Double.parseDouble(illegleCaseMap.get(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.CLRRATE))-1));
		illegleCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.ERRORMSG, "最低商户费率不能低于结算费率");
		list.add(illegleCaseMap);
		//是否允许二维码快速进件
		HashMap<String, String> isAllowQRFastCaseMap = getCaseMapWithCrlParams();
		isAllowQRFastCaseMap.replace(ISALLOWQCFAST, "是");
		isAllowQRFastCaseMap.replace(SINGLEMAX, "");
		isAllowQRFastCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.ERRORMSG, "请输入单笔最大限额");
		list.add(isAllowQRFastCaseMap);
		isAllowQRFastCaseMap = getCaseMapWithCrlParams();
		isAllowQRFastCaseMap.replace(ISALLOWQCFAST, "是");
		isAllowQRFastCaseMap.replace(SINGLEMIN, "");
		isAllowQRFastCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.ERRORMSG, "请输入单笔最小限额");
		list.add(isAllowQRFastCaseMap);
		isAllowQRFastCaseMap = getCaseMapWithCrlParams();
		isAllowQRFastCaseMap.replace(ISALLOWQCFAST, "是");
		isAllowQRFastCaseMap.replace(SINGLEMIN, "1");
		isAllowQRFastCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.ERRORMSG, "单笔最大限额小于单笔最小限额");
		list.add(isAllowQRFastCaseMap);
		
		//同步标准版所有用例
		for(HashMap<String, String>[] map: bzChannelPCEditTestData){
			HashMap<String, String> oneCaseMap = map[0];
			String clrRate = oneCaseMap.get(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.CLRRATE);
			oneCaseMap.put(ALLOWSETMINMCHCLRRATE, String.valueOf(Double.parseDouble(clrRate.equals("")?"0":clrRate) + 1));
			oneCaseMap.put(ISALLOWQCFAST, "否");
			oneCaseMap.put(SINGLEMAX, "0.0");
			oneCaseMap.put(SINGLEMIN, "0.0");
			if(oneCaseMap.get(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.RATETYPE).equals("成本费率")){
				oneCaseMap.replace(swiftpass.testcase.testdata.channel.ChannelPCEditTestData.RATETYPE, "浮动费率");
			}
			list.add(oneCaseMap);
		}
				
		for(HashMap<String, String> oneCase: list){
			data = ArrayUtils.add(data, ArrayUtils.toArray(oneCase));
		}
		return data;
	}
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getChannelPCEditTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
