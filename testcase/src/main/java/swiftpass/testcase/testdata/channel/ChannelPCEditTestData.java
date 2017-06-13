package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.PTPermissionControl;
import swiftpass.page.enums.RateType;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelPCHelp;
import swiftpass.utils.services.ChannelPCService;
import swiftpass.utils.services.ChannelService;

public class ChannelPCEditTestData {
	public static HashMap<String, String> needChannelInfo = getChannelId();
	
	public static HashMap<String, String>[][] getChannelPayConfEditTestData(){
		return getAllChannelPayConfEditTestData();
	}
	
	public static String RATETYPE = "ratetype",
			CLRRATE = "clrRate",
//			PTPERMISSIONCONTROL = "ptPermissionControl",
			ISCLICKFIRSTROWCHANNEL = "isClickFirstRowChannel",
			ISCLICKFIRSTROWPC = "isClickFirstRowPC",
			ISEDIT = "isEdit",
			ISCONFIRMEDIT = "isConfirmEdit",
			CHANNELID = "channelId",
//			ISPTPCONFIRM = "isptpConfirm",
			PAYTYPENAME = "payTypeName",
			ERRORMSG = "errorMsg";
	
	//返回只包含编辑渠道支付方式配置页面参数
	public static HashMap<String, String> initPageParamsMap(){
		HashMap<String, String> map = new HashMap<String, String>();
//		String[] valueKeys = {RATETYPE, CLRRATE, PTPERMISSIONCONTROL};
		String[] valueKeys = {RATETYPE, CLRRATE};
		for(String valueKey: valueKeys){
			map.put(valueKey, "");
		}
		return map;
	}
	
	public static HashMap<String, String> getCaseMapWithCtrlParams(){
		HashMap<String, String> data = initPageParamsMap();
		data.replace(RATETYPE, RateType.FIXED.getRateType());
		data.replace(CLRRATE, "5");
//		data.replace(PTPERMISSIONCONTROL, PTPermissionControl.onlyFR.getPermissionControlText());
		
		//新增渠道ID用于查询
		data.put(CHANNELID, needChannelInfo.get("CHANNEL_ID"));
		data.put(PAYTYPENAME, PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));

		// 新增控制页面参数
		data.put(ISCLICKFIRSTROWCHANNEL, "true");
		data.put(ISCLICKFIRSTROWPC, "true");
		data.put(ISEDIT, "true");
		data.put(ISCONFIRMEDIT, "true");
//		data.put(ISPTPCONFIRM, "true");
		data.put(ERRORMSG, "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelPayConfEditTestData() {
		HashMap<String, String>[][] resultCasesMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		// 渠道新增支付方式失败测试用例--必填字段为空，正常关闭、选择支付类型正常关闭
//		String needs[] = { ISCLICKFIRSTROWPC, CLRRATE, ISEDIT, ISCONFIRMEDIT, ISPTPCONFIRM};
		String needs[] = { ISCLICKFIRSTROWPC, CLRRATE, ISEDIT, ISCONFIRMEDIT};
//		String errorMsg[] = { "请选择要编辑的行!", "请输入结算费率", "正常关闭", "正常取消", "正常取消"};
		String errorMsg[] = { "请选择要编辑的行!", "请输入结算费率", "正常关闭", "正常取消"};
		for (int i = 0; i < needs.length; i++) {
			HashMap<String, String> oneCase = getCaseMapWithCtrlParams();
			oneCase.replace(needs[i], "");
			oneCase.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCase);
		}
		// 其它非法异常 1.结算费率<0 2.结算费率小数点后位数大于2
		HashMap<String, String> illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(CLRRATE, "-1");
		illegleCaseMap.replace(ERRORMSG, "成本费率必须为正数");
		list.add(illegleCaseMap);
		illegleCaseMap = getCaseMapWithCtrlParams();
		illegleCaseMap.replace(CLRRATE, "1.111");
		illegleCaseMap.replace(ERRORMSG, "结算费率只能是数字，且小数点最多两位");
		list.add(illegleCaseMap);

		// 非法的结算费率1.固定费率超过允许最大值 2.成本费率低于允许最小值但是大于0 3.成本费率最大值大于1000
		HashMap<String, String> illegelFixedCostRate = getCaseMapWithCtrlParams();
		HashMap<String, String> illegelFloatCostRate = getCaseMapWithCtrlParams();
		double billRate = ChannelPCHelp.getChannelPCScope(needChannelInfo.get("CHANNEL_ID"), 0);
		double channelFloatMin = billRate; // 设置为成本费率时的最小可填入的值【channelFloatMin~1000】
		String CHNFIXMIN = SwiftPass.convertDTS(channelFloatMin);
		double channelFixedMax = 1000 - channelFloatMin;// 设置为固定费率时的最大可填入的值【0~channelFixedMax】
		String CHNFIXMAX = SwiftPass.convertDTS(channelFixedMax);
		illegelFixedCostRate.replace(CLRRATE, (channelFixedMax + 1) + "");
		illegelFixedCostRate.replace(ERRORMSG, "固定费率必须在[0‰，" + CHNFIXMAX + "‰]之间。");
		list.add(illegelFixedCostRate);
		illegelFloatCostRate.replace(RATETYPE, RateType.FLOW.getRateType());
		illegelFloatCostRate.replace(CLRRATE, (channelFloatMin - 1) + "");
		illegelFloatCostRate.replace(ERRORMSG, "成本费率必须在[" + CHNFIXMIN + "‰，1000‰]之间。");
		list.add(illegelFloatCostRate);
		HashMap<String, String> illegelFloatCostRate_ = SwiftPass.copy(illegelFloatCostRate);
		illegelFloatCostRate_.replace(CLRRATE, "1001");
		list.add(illegelFloatCostRate_);

		// 渠道编辑支付方式成功用例
		HashMap<String, String> successPayConfAdd = getCaseMapWithCtrlParams();
//		successPayConfAdd.replace(PTPERMISSIONCONTROL, PTPermissionControl.enable.getPermissionControlText());
		successPayConfAdd.replace(RATETYPE, RateType.FLOW.getRateType());
		successPayConfAdd.replace(CLRRATE, (int)RandomUtils.nextDouble(billRate, 1000) + "");
		list.add(successPayConfAdd);
		
		//组装用例
		for(HashMap<String, String> oneCase: list){
			resultCasesMapArray = ArrayUtils.add(resultCasesMapArray, ArrayUtils.toArray(oneCase));
		}

		return resultCasesMapArray;
	}
	
	@Deprecated
	//去数据库查找一条添加了支付方式的渠道(而且渠道没有子渠道--没有实现)，如果没有找到就用接口去创建
	public static String getNeedChannelId(){
		String needChannelId = null;
		String needChannelIdSQL = "select * from tra_ch_pay_conf where channel_id in "
				+ "( select channel_id from cms_channel where channel_type = 2 and activate_status != 4)";
		HashMap<String, String> needChannelIdMap = DBUtil.getQueryResultMap(needChannelIdSQL).get(1);
		if(needChannelIdMap == null){
			String sql = "select channel_id from cms_channel where channel_type = 2 and activate_status != 4";
			HashMap<String, String> ChannelIdMap = DBUtil.getQueryResultMap(sql).get(1);//库中肯定会存在一个唯一内部机构
			//接口去给查询到的渠道新增一个支付方式配置
			needChannelId = ChannelIdMap.get("CHANNEL_ID");
			// 接口请求支付方式配置，给该渠道新增支付方式配置
			ChannelPCService.addChannelPC(needChannelId);
		}else{
			needChannelId = needChannelIdMap.get("CHANNEL_ID");
		}
		return needChannelId;
	}
	
	private static HashMap<String, String> getChannelId(){
		String uniqueChannelId = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");
		HashMap<String, String> channelInfo = ChannelService.addChannel(uniqueChannelId);
		String channelId = channelInfo.get("CHANNEL_ID");
		ChannelPCService.addChannelPC(channelId);
		return channelInfo;
	}
	
	public static void main(String args[]){
//		String s = getChannelId();
//		System.out.println(s);
		
		long start = System.currentTimeMillis();
		HashMap<String, String>[][] maps = getChannelPayConfEditTestData();
		System.err.println(maps.length);
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
		DBUtil.closeDBResource();
		System.err.println(System.currentTimeMillis() - start);
	}

}