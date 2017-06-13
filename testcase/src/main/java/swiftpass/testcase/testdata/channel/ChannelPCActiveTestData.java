package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.utils.DBUtil;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelPCService;
import swiftpass.utils.services.ChannelService;

public class ChannelPCActiveTestData {

	public static HashMap<String, String>[][] getChannelPayConfActiveTestData() {
		return getAllChannelPayConfActiveTestData();
	}

	private static String[] activeOperate = {"激活通过", "激活不通过", "冻结", "关闭"};
	public static String ACTIVATEREMARK = "activateRemark",
			CHNEXAMINESTATUS = "chnExamineStatus",
			CHNACTIVATESTATUS = "chnActivateStatus",
			PCACTIVATESTATUS = "pcActivateStatus",
			ACTIVATEOPERATE = "activateOperate",
			ISCLICKFIRSTROWCHANNEL = "isClickFirstRowChannle",
			ISCLICKFIRSTROWPC = "isClickFirstRowPC",
			ISCONNFIRMACTIVATEOPERATE = "isConfirmActivateOperate",
			PAYTYPENAME = "payTypeName",
			ERRORMSG = "errorMsg";
			
	private static HashMap<String, String> getCaseMapWithCtrlParams() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(ACTIVATEREMARK, RandomStringUtils.randomAlphanumeric(32));
		data.put(CHNEXAMINESTATUS, ExamineStatus.PASS.ordinal() + ""); // 渠道审核通过
		data.put(CHNACTIVATESTATUS, ActivateStatus.NOPROCESS.ordinal() + "");// 渠道激活状态--未激活
		data.put(PCACTIVATESTATUS, ActivateStatus.NOPROCESS.getSCode());// 支付方式默认未激活
		data.put(ACTIVATEOPERATE, activeOperate[0]); // 支付方式配置激活操作--激活通过

		data.put(PAYTYPENAME, PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));
		// 新增一些控制参数
		data.put(ISCLICKFIRSTROWCHANNEL, "true");
		data.put(ISCLICKFIRSTROWPC, "true");
		data.put(ISCONNFIRMACTIVATEOPERATE, "true");
		data.put(ERRORMSG, "");

		return data;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllChannelPayConfActiveTestData() {
		HashMap<String, String>[][] resultCasesMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		// 支付方式激活异常用例1.选择已审核渠道但是没有点击支付方式 2.点击关闭 3.取消确认激活
		String[] needs = {ISCLICKFIRSTROWCHANNEL, ISCLICKFIRSTROWPC, ACTIVATEOPERATE, ISCONNFIRMACTIVATEOPERATE };
		String[] errorMsg = {"", "请选择要激活的记录!", "正常关闭", "正常取消"};
		for (int i = 0; i < needs.length; i++) {
			HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCaseMap);
		}

		 //选择的渠道审核通过-激活5种状态（5条用例） 、审核非通过通过（3条）-未激活（3条用例） 
		//通过-未、通-通、通-不、通-需重新、通-冻结--------------未-未、不-未、需再次审核-未
		for(ExamineStatus es: ExamineStatus.values()){ 
			if(es == ExamineStatus.PASS){
				for(ActivateStatus as: ActivateStatus.values()){ 
					HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
					oneCaseMap.replace(CHNEXAMINESTATUS, es.ordinal() + "");
					oneCaseMap.replace(CHNACTIVATESTATUS, as.ordinal() + ""); 
					list.add(oneCaseMap);
					} 
			}else{
				HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
				oneCaseMap.replace(CHNEXAMINESTATUS, es.ordinal() + "");
				oneCaseMap.replace(CHNACTIVATESTATUS, ActivateStatus.NOPROCESS.ordinal() +"");
				list.add(oneCaseMap); 
				}
			}
		//渠道激活不同的操作用例  1.激活不通过  2.冻结
		for(int i=1; i<3; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
			oneCaseMap.replace(ACTIVATEOPERATE, activeOperate[i]);
			list.add(oneCaseMap);
		}
		
		//当支付方式配置为激活状态即isPayConfActive=true,只能进行冻结和关闭操作
		HashMap<String, String> freedsMap = getCaseMapWithCtrlParams();
		freedsMap.replace(PCACTIVATESTATUS, ActivateStatus.PASS.getSCode());
		freedsMap.replace(ACTIVATEOPERATE, activeOperate[2]);
		list.add(freedsMap);
		HashMap<String, String> closeMap = (HashMap<String, String>) freedsMap.clone();
		closeMap.replace(ACTIVATEOPERATE, activeOperate[3]);
		closeMap.replace(ERRORMSG, "正常关闭");
		list.add(closeMap);
	
		//当配置为激活状态，去进行激活通过和不通过操作时抛出异常
		HashMap<String, String> oneCaseMap = getCaseMapWithCtrlParams();
		oneCaseMap.replace(PCACTIVATESTATUS, ActivateStatus.PASS.getSCode());
		oneCaseMap.replace(ACTIVATEOPERATE, activeOperate[0]);
		oneCaseMap.replace(ERRORMSG, "已激活的支付方式不显示激活通过按钮");
		list.add(oneCaseMap);
		HashMap<String, String> oneCaseMap_ = (HashMap<String, String>) oneCaseMap.clone();
		oneCaseMap_.replace(ACTIVATEOPERATE, activeOperate[1]);
		oneCaseMap_.replace(ERRORMSG, "已激活的支付方式不显示激活不通过按钮");
		list.add(oneCaseMap_);
		
		//当支付方式为冻结状态时，编辑按钮不可点击
		HashMap<String, String> freedUnEditableCaseMap = getCaseMapWithCtrlParams();
		freedUnEditableCaseMap.replace(PCACTIVATESTATUS, ActivateStatus.FREEZE.getSCode());
		list.add(freedUnEditableCaseMap);
		
		 //组装测试用例
		for (HashMap<String, String> oneCase : list) {
			HashMap<String, String>[] oneCaseArray = ArrayUtils.toArray(oneCase);
			resultCasesMapArray = ArrayUtils.add(resultCasesMapArray,oneCaseArray);
		}
		return resultCasesMapArray;
	}

	/**
	 * @description 查询数据中满足条件的渠道，如果不存在就在唯一内部机构下新增一个渠道，然后通过审核、激活接口处理成满足需要的数据
	 * @param as
	 *            激活状态
	 * @param es
	 *            审核状态
	 * @return 渠道ID
	 * */
	public static String getNeedChannelId(String es, String as, String pcActivateStatus) {
		String needChannelId = null;
		String needChannelIdSQL = "select channel_id from cms_channel t where t.channel_type = 2 and t.channel_properties = 2 and t.activate_status = $as and t.examine_status = $es"
				.replace("$as", as).replace("$es", es);
		HashMap<Integer, HashMap<String, String>> map = DBUtil.getQueryResultMap(needChannelIdSQL);
		if (map.get(1) == null) {
			// --------------1.接口去造一条挂在唯一一个内部机构下的渠道
			// 受理机构下的那个唯一的渠道ID
			String uniqueChannelId = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");
			// 接口新增渠道
			HashMap<String, String> channelResult = ChannelService.addChannel(uniqueChannelId);
			needChannelId = channelResult.get("CHANNEL_ID");
			// -----------2.接口去生成此渠道的支付方式配置
			// 接口请求支付方式配置，给该渠道新增支付方式配置
			ChannelPCService.addChannelPC(needChannelId);
			// ----------3.接口去审核激活此渠道，以达到满足要求的数据
			//未审核(不需要调用接口)审核通过、不通过、需再次审核需要调用接口
			if(!es.equals("0")) {
				HashMap<String, String> dataForExamineChannelParams = ChannelAAAService.
						getChannelExamineParams(needChannelId, es, as);
				ChannelAAAService.examineChannel(dataForExamineChannelParams);
			}
			//未激活(不需要掉接口)
			if(!as.equals("0")) {
				HashMap<String, String> dataForActivateChannelParams = ChannelAAAService.
						getChannelPCActiveParams(needChannelId, es, as);
				ChannelAAAService.activeChannel(dataForActivateChannelParams);
			}
		} else {
			needChannelId = map.get(1).get("CHANNEL_ID");
			String sql = "select * from $table where channel_id = $channelId and pay_type_id = '$payTypeId'"
					.replace("$table", "tra_ch_pay_conf")
					.replace("$channelId", needChannelId)
					.replace("$payTypeId", PayDBOperations.payTypeInfo.get("PAY_TYPE_ID"));
			HashMap<String, String> channelIdResult = DBUtil.getQueryResultMap(sql).get(1);
			if(!as.equals("4")){//渠道激活状态为冻结，不能添加支付方式
				if (channelIdResult == null) {
					ChannelPCService.addChannelPC(needChannelId);
				} else {
					// 这里还需要加一个支付方式配置激活状态的判断
					// 构造支付方式配置激活接口请求参数
					HashMap<String, String> channelPayConfActivateParams = ChannelPCService
							.getChannelPCActivateParams(needChannelId);
					if(!pcActivateStatus.equals(channelPayConfActivateParams.get("activateStatus"))){
						//如果渠道支付方式激活状态为未激活，则可以进行激活通过、激活不通过，
						//而不能直接通过接口去修改渠道支付方式为未激活，所以把支付方式激活状态置为激活不通过
						pcActivateStatus = pcActivateStatus.equals("0")?"2":pcActivateStatus;
						channelPayConfActivateParams.replace("activateStatus", pcActivateStatus);
						ChannelPCService.activateChannelPayConf(channelPayConfActivateParams);
					}
				}
			}
		
		}

		return needChannelId;
	}

	public static void main(String args[]) {
		 String s = getNeedChannelId("1", "3", "0");
		 System.out.println(s);

//		long start = System.currentTimeMillis();
//		HashMap<String, String>[][] map = getAllChannelPayConfActiveTestData();
//		for (HashMap<String, String>[] m : map) {
//			System.out.println(m[0]);
//		}
//		System.out.println(System.currentTimeMillis() - start);
//		System.err.println(map.length);
	}

}
