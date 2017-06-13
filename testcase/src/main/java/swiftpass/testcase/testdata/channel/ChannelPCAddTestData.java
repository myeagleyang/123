package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelPCHelp;

public class ChannelPCAddTestData {
	private static List<HashMap<String, String>> needChannelInfos = ChannelPCHelp.needChannelInfos;
	//唯一内部机构下的渠道（没有添加支付方式）
	private static HashMap<String, String> sonOfUniqueCh = needChannelInfos.get(0);
	public static String sonChName = sonOfUniqueCh.get("CHANNEL_NAME");
	private static String sonChId = sonOfUniqueCh.get("CHANNEL_ID");
	private static double sonOfChPCScope = ChannelPCHelp.getChannelPCScope(sonChId, 0); // 设置为浮动费率时的最小可填入的值【channelFloatMin~1000】
	private static String SONOFCHNPCSCOPE = SwiftPass.convertDTS(sonOfChPCScope);
	
	//唯一内部机构下的渠道及子渠道（均没有添加支付方式）
	private static HashMap<String, String> grandsonOfUniqueCh = needChannelInfos.get(1);
	private static String grandsonChName = grandsonOfUniqueCh.get("CHANNEL_NAME");
	private static String grandsonChId = grandsonOfUniqueCh.get("CHANNEL_ID");
	private static double grandsonOfPCScope = ChannelPCHelp.getChannelPCScope(grandsonChId, 0);
	private static String GRANDSONOFCHNPCSCOPE = SwiftPass.convertDTS(grandsonOfPCScope);
	 
	//内部机构已经配置的支付方式
	private static HashMap<String, String> payCenterInfo = PayDBOperations.payCenterInfo;
	private static HashMap<String, String> payTypeInfo = PayDBOperations.payTypeInfo;

	public static HashMap<String, String>[][] getPayConfAddTestData(){
		return getAllChannelPayConfAddTestData();
	}
	
	public static String SELECTPCNAME = "selectPcName",
			SELECTPTNAME = "selectPtName",
			RATETYPE = "rateType",
			CLRRATE = "clrRate",
			PARENTRATE = "parentRate",
			PAYTYPEID = "payTypeId",
			PAYCENTERID = "payCenterId",
			CHANNELID = "channelId",
			ISCLICKFIRSTROWCHANNEL = "isClickFirstRowChannel",
			ISSELECTPCNAME = "isSelectPcName",
			ISSELECTPTNAME = "isSelectPtName",
			ISCONFIRMSELECTPC = "isConfirmSelectPC",
			ISCONFIRMSELECTPT = "isConfirmSelectPT",
			ISSAVE = "isSave",
			ISCONFIRMSAVE = "isConfirmSave",
			ISPARENTCHSETPT = "isParentChSetPC",
			ERRORMSG = "errorMsg",
			CHNACTIATESTATUS = "chnActivateStatus";  //渠道激活状态
	
	// 返回只包含渠道新增支付方式页面的参数
	private static HashMap<String, String> initPageParamsMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] valueKeys = {SELECTPCNAME, SELECTPTNAME, RATETYPE, CLRRATE};
		for (String valueKey : valueKeys) {
			map.put(valueKey, "");
		}
		return map;
	}


	// 获取新增指定渠道的单个用例的测试数据
	public static HashMap<String, String> getCaseMapWithCtrlParams() {
		HashMap<String, String> data = initPageParamsMap();
		String selectPCName = payCenterInfo.get("CENTER_NAME");
		String payCenterId = payCenterInfo.get("PAY_CENTER_ID");
		String payTypeId = payTypeInfo.get("PAY_TYPE_ID");
		String selectPtName = payTypeInfo.get("PAY_TYPE_NAME");
		data.replace(SELECTPCNAME, selectPCName);
		data.replace(SELECTPTNAME, selectPtName);
		data.replace(RATETYPE, "固定费率");
		data.replace(CLRRATE, String.valueOf(sonOfChPCScope));
		
		data.put(CHNACTIATESTATUS, ActivateStatus.NOPROCESS.getSCode()); //默认为未激活状态
		data.put(PARENTRATE, String.valueOf(sonOfChPCScope)); //父渠道费率

		// 添加额外参数，js注入需要使用到
		data.put(PAYTYPEID, payTypeId);
		data.put(PAYCENTERID, payCenterId);
		//额外添加参数用于查询
		data.put(CHANNELID, sonChId);

		// 新增控制页面参数
		data.put(ISCLICKFIRSTROWCHANNEL, "true");
		data.put(ISSELECTPCNAME, "true");
		data.put(ISSELECTPTNAME, "true");
		data.put(ISCONFIRMSELECTPC, "true");
		data.put(ISCONFIRMSELECTPT, "true");
		data.put(ISSAVE, "true");
		data.put(ISCONFIRMSAVE, "true");
		data.put(ISPARENTCHSETPT, "true");//默认所选渠道的父渠道已设置支付方式--则用唯一内部机构下新建的那个渠道
		data.put(ERRORMSG, "");
		return data;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelPayConfAddTestData() {
		HashMap<String, String>[][] resultCasesMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		// 渠道新增支付方式失败测试用例--必填字段为空，正常关闭、选择支付类型正常关闭
		String needs[] = {ISSELECTPCNAME, ISSELECTPTNAME, CLRRATE, ISSAVE, ISCONFIRMSAVE};
		String errorMsg[] = {"请先选择支付通道", "请选择支付类型", "请输入结算费率", "正常关闭", "正常取消"};
		for (int i = 0; i < needs.length; i++) {
			HashMap<String, String> oneCase = getCaseMapWithCtrlParams();
			oneCase.replace(needs[i], "");
			oneCase.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCase);
		}
		
		/*String needs[] = {ISSELECTPTNAME, CLRRATE, ISSAVE, ISCONFIRMSAVE};
		String errorMsg[] = {"请选择支付类型", "请输入结算费率", "正常关闭", "正常取消"};
		for (int i = 0; i < needs.length; i++) {
			HashMap<String, String> oneCase = getCaseMapWithCtrlParams();
			oneCase.replace(needs[i], "");
			oneCase.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCase);
		}*/
		
		//支付通道和支付类型都不选，提示：请选择支付通道
		HashMap<String, String> illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(ISSELECTPCNAME, "");
		illegleCaseMap.replace(ISSELECTPTNAME, "");
		illegleCaseMap.replace(ERRORMSG, "请选择支付通道");
		list.add(illegleCaseMap);

		// 其它非法异常 1.结算费率<0  2.结算费率小数点后位数大于2 
		illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(CLRRATE, "-1");
		illegleCaseMap.replace(ERRORMSG, "渠道[" + sonChName + "]:成本费率必须为正数");
		list.add(illegleCaseMap);
		illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(CLRRATE, "1.111");
		illegleCaseMap.replace(ERRORMSG, "结算费率只能是数字，且小数点最多两位");
		list.add(illegleCaseMap);
		
		// 非法的结算费率1.固定费率超过允许最大值 2.浮动费率低于允许最小值但是大于0 3.浮动费率最大值大于1000
		illegleCaseMap = getCaseMapWithCtrlParams();
		double channelFixedMax = 1000 - sonOfChPCScope;// 设置为固定费率时的最大可填入的值【0~channelFixedMax】
		String CHNFIXMAX = SwiftPass.convertDTS(channelFixedMax);
		illegleCaseMap.replace(CLRRATE, (channelFixedMax + 1) + "");
		illegleCaseMap.replace(ERRORMSG, "渠道[" + sonChName+"]:固定费率必须在[0‰，" + CHNFIXMAX + "‰]之间。");
		list.add(illegleCaseMap);
		illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(RATETYPE, "成本费率");
		illegleCaseMap.replace(CLRRATE, (sonOfChPCScope - 1) + "");
		illegleCaseMap.replace(ERRORMSG, "渠道[" + sonChName + "]:成本费率必须在[" + SONOFCHNPCSCOPE + "‰，1000‰]之间。");
		list.add(illegleCaseMap);
		illegleCaseMap = SwiftPass.copy(illegleCaseMap);
		illegleCaseMap.replace(CLRRATE, "1001");
		list.add(illegleCaseMap);
		//渠道所属父渠道没有设置支付类型，需要设置父渠道费率1.未设置父渠道费率2.父渠道费率低于最小值3.父渠道费率高于最大值
		//4.父渠道费率设置正常，子渠道费率小于最小值5.父渠道设置正常，子渠道费率大于最大值
		HashMap<String, String> parentChNoPCCase = getCaseMapWithCtrlParams();
		parentChNoPCCase.replace(RATETYPE, "成本费率");
		parentChNoPCCase.replace(ISPARENTCHSETPT, ""); //父渠道未设置支付方式，使用新增的子渠道
		parentChNoPCCase.replace(CHANNELID, grandsonChId);
		parentChNoPCCase.replace(PARENTRATE, "");
		parentChNoPCCase.replace(ERRORMSG, "请设置父渠道相关信息");
		list.add(parentChNoPCCase);
		HashMap<String, String> illegleParentNoPCCase = SwiftPass.copy(parentChNoPCCase);
		illegleParentNoPCCase.replace(PARENTRATE, String.valueOf(sonOfChPCScope-1));
		illegleParentNoPCCase.replace(ERRORMSG, "渠道[" + sonChName + "]:成本费率必须在[" + SONOFCHNPCSCOPE + "‰，1000‰]之间。");
		list.add(illegleParentNoPCCase);
		illegleParentNoPCCase = SwiftPass.copy(parentChNoPCCase);
		illegleParentNoPCCase.replace(PARENTRATE, "1001");
		illegleParentNoPCCase.replace(ERRORMSG, "渠道[" + sonChName + "]:成本费率必须在[" + SONOFCHNPCSCOPE + "‰，1000‰]之间。");
		list.add(illegleParentNoPCCase);
		illegleParentNoPCCase = SwiftPass.copy(parentChNoPCCase);
		illegleParentNoPCCase.replace(PARENTRATE, String.valueOf(sonOfChPCScope));
		illegleParentNoPCCase.replace(CLRRATE, String.valueOf(grandsonOfPCScope-1));
		illegleParentNoPCCase.replace(ERRORMSG, "渠道[" + grandsonChName + "]:成本费率必须在[" + GRANDSONOFCHNPCSCOPE + "‰，1000‰]之间。");
		list.add(illegleParentNoPCCase);
		illegleParentNoPCCase = SwiftPass.copy(parentChNoPCCase);
		illegleParentNoPCCase.replace(PARENTRATE, String.valueOf(sonOfChPCScope));
		illegleParentNoPCCase.replace(CLRRATE, "1001");
		illegleParentNoPCCase.replace(ERRORMSG, "渠道[" + grandsonChName + "]:成本费率必须在[" + GRANDSONOFCHNPCSCOPE + "‰，1000‰]之间。");
		list.add(illegleParentNoPCCase);

		// 渠道新增支付方式成功用例--重复添加支付类型配置
		HashMap<String, String> successPCCaseMap = SwiftPass.copy(parentChNoPCCase);
		successPCCaseMap.replace(PARENTRATE, String.valueOf(sonOfChPCScope + 1));
		successPCCaseMap.replace(CLRRATE,String.valueOf(grandsonOfPCScope + 1));
		successPCCaseMap.replace(ERRORMSG, "");
		list.add(successPCCaseMap);
		HashMap<String, String> repeatPayConfAdd = SwiftPass.copy(successPCCaseMap);
		repeatPayConfAdd.replace(ERRORMSG, "渠道[" + grandsonChName + "]:渠道支付类型配置已存在");
		list.add(repeatPayConfAdd);
		
		//渠道新增支付方式成功用例--重复添加支付类型配置
		HashMap<String, String> successAddPC = getCaseMapWithCtrlParams();
		successAddPC.replace(CLRRATE, String.valueOf(sonOfChPCScope + 1));
		list.add(successAddPC);
		HashMap<String, String> repeatPCadd = SwiftPass.copy(successAddPC);
		repeatPCadd.replace(ERRORMSG, "渠道[" + sonChName + "]:渠道支付类型配置已存在");
		list.add(repeatPCadd);
		
		//新增渠道激活状态为冻结，不能添加支付方式用例
		HashMap<String, String> chnFreedsCaseMap = getCaseMapWithCtrlParams();
		chnFreedsCaseMap.replace(CHNACTIATESTATUS, ActivateStatus.FREEZE.getSCode());
		list.add(chnFreedsCaseMap);
		
		
		for(HashMap<String, String> oneCaseMap: list){
			resultCasesMapArray = ArrayUtils.add(resultCasesMapArray, ArrayUtils.toArray(oneCaseMap));
		}

		return resultCasesMapArray;
	}

	public static void main(String srgs[]) {
		long start = System.currentTimeMillis();
		HashMap<String, String>[][] maps = getPayConfAddTestData();
		for (HashMap<String, String>[] map : maps)
			System.out.println(map[0]);
		System.out.println(System.currentTimeMillis() - start);
		DBUtil.closeDBResource();
		System.err.println(maps.length);
		// int s = getChannelPayConfScope(getNoPayConfChannel());
		// System.out.println(s);
		// String ss = getNoPayConfChannel();
		// System.out.println(ss);
		// String[] sss = getChannelPayTypeId_Name();
		// System.out.println(sss[0] + sss[1] + sss[2]);
		// DatabaseUtil.closeDBResource();
	}

}
