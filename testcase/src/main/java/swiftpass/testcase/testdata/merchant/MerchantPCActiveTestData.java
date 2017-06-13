package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.PayDBOperations;
import swiftpass.page.enums.ActivateOperate;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.page.exceptions.MerchantPayConfException;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantPCActivateService;
import swiftpass.utils.services.MerchantPCService;
import swiftpass.utils.services.MerchantService;

public class MerchantPCActiveTestData {
	
	public static HashMap<String, String>[][] getMerchantPCActiveTestData(){
		return getAllMerchantPCActiveTestData();
	}
	
	public static String PREMCHEXAMINESTATUS = "preMchExamineStatus",
			PREMCHACTIVATESTATUS = "preMchActiveStatus",
			ACTIVATEOPERATE = "activeOperate",
			MERCHANTPCACTIVATETSTATUS = "merchantPCActivateStatus",//商户支付方式激活状态
			ISCLICKFIRSTROWMERCHANT = "isClickFirstRowMerchant",
			ISCLICKFIRSTROWPT = "isClickFirstRowPT",
			ISCONFIRMACTIVATEOPERATE = "isConfirmActiveOperate",
			PAYTYPENAME = "payTypeName",
			ERRORMSG = "errorMsg";
	
	private static HashMap<String, String> getCaseMapWithCrlParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put(PREMCHEXAMINESTATUS, ExamineStatus.PASS.getSCode()); //默认为审核通过
		data.put(PREMCHACTIVATESTATUS, ActivateStatus.NOPROCESS.getSCode());//默认未激活
		data.put(ACTIVATEOPERATE, ActivateOperate.pass.getText()); //默认激活通过
		
		//新增merchantId用于查询
		data.put(PAYTYPENAME, PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));
		
		//新增控制参数
		data.put(MERCHANTPCACTIVATETSTATUS, ActivateStatus.NOPROCESS.getSCode()); //商户支付方式配置默认激活状态为未激活状态
		data.put(ISCLICKFIRSTROWMERCHANT, "true");
		data.put(ISCLICKFIRSTROWPT, "true");
		data.put(ISCONFIRMACTIVATEOPERATE, "true");
		data.put(ERRORMSG, "");
		
		return data;
	}
	
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllMerchantPCActiveTestData(){
		HashMap<String, String>[][] resultCaseMaps = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		
		String needs[] = {ISCLICKFIRSTROWMERCHANT, ACTIVATEOPERATE, ISCLICKFIRSTROWPT, ISCONFIRMACTIVATEOPERATE};
		String errorMsg[] = {"", "正常关闭", "请选择要激活的记录!", "正常取消"};
		for(int i=0; i<needs.length; i++){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			oneCaseMap.replace(needs[i], "");
			oneCaseMap.replace(ERRORMSG, errorMsg[i]);
			list.add(oneCaseMap);
		}
		
		//商户审核通过-激活4种状态（通-未、通-成功、通-失败、通-冻结）--激活成功用例
		for(ActivateStatus as: ActivateStatus.values()){
			HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
			if(!as.equals(ActivateStatus.NEEDAGAIN)){
				oneCaseMap.replace(PREMCHACTIVATESTATUS, as.getSCode());
				list.add(oneCaseMap);
			}
		}
		//商户非审核通过（3种状态）-未激活（不通-未激活、未审核-未激活、需再次审核-未激活）--激活按钮状态用例
		for(ExamineStatus es: ExamineStatus.values()){
			if(!es.equals(ExamineStatus.PASS)){
				HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
				oneCaseMap.replace(PREMCHEXAMINESTATUS, es.getSCode());
				list.add(oneCaseMap);
			}
		}
		
		//商户支付方式配置为非激活成功状态--操作成功用例(3*3)
		for(ActivateStatus mchActivateStatus: ActivateStatus.values()){
			if(!mchActivateStatus.equals(ActivateStatus.PASS) && !mchActivateStatus.equals(ActivateStatus.NEEDAGAIN)){
				for(ActivateOperate activeOprate: ActivateOperate.values()){
					if(!activeOprate.equals(ActivateOperate.close)){
						HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
						oneCaseMap.replace(MERCHANTPCACTIVATETSTATUS, mchActivateStatus.getSCode());
						oneCaseMap.replace(ACTIVATEOPERATE, activeOprate.getText());
						list.add(oneCaseMap);
					}
				}
			}
		}
		//商户支付方式配置为激活成功状态只能进行冻结和关闭操作
		HashMap<String, String> freezeCaseMap = getCaseMapWithCrlParams();
		freezeCaseMap.replace(MERCHANTPCACTIVATETSTATUS, ActivateStatus.PASS.getSCode());
		freezeCaseMap.replace(ACTIVATEOPERATE, ActivateOperate.freez.getText());
		list.add(freezeCaseMap);
		HashMap<String, String> closeCaseMap = SwiftPass.copy(freezeCaseMap);
		closeCaseMap.replace(ACTIVATEOPERATE, ActivateOperate.close.getText());
		closeCaseMap.replace(ERRORMSG, "正常关闭");
		list.add(closeCaseMap);
		
		//商户支付方式配置为激活状态去进行激活通过和激活不通过时抛出异常
		HashMap<String, String> oneCaseMap = getCaseMapWithCrlParams();
		oneCaseMap.replace(MERCHANTPCACTIVATETSTATUS, ActivateStatus.PASS.getSCode());
		oneCaseMap.replace(ERRORMSG, "已激活的支付方式不显示激活通过按钮");
		list.add(oneCaseMap);
		HashMap<String, String> oneCaseMap_ = SwiftPass.copy(oneCaseMap);
		oneCaseMap_.replace(ACTIVATEOPERATE, ActivateOperate.fail.getText());
		oneCaseMap_.replace(ERRORMSG, "已激活的支付方式不显示激活不通过按钮");
		list.add(oneCaseMap_);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseMaps = ArrayUtils.add(resultCaseMaps, ArrayUtils.toArray(oneCase));
		}
		
		return resultCaseMaps;
	}
	
	/**
	 * 根据商户的审核激活状态及商户支付方式激活状态查找符合条件的商户
	 * @return needMerchant-满足需求的商户Id
	 * */
	public static String getNeedMchId(String es, String as, String mchPCActivateStatus) {
		String needMchId = null;
		String needMchIdSQL = "select merchant_id from cms_merchant t where t.activate_status = $as and t.examine_status = $es"
				.replace("$as", as).replace("$es", es);
		HashMap<Integer, HashMap<String, String>> map = DBUtil.getQueryResultMap(needMchIdSQL);
		if (map.get(1) == null) {
			// --------------1.接口去造一条挂在唯一一个内部机构下的商户
			// 受理机构下的那个唯一的渠道ID
			String uniqueChannelId = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");
			// 接口新增商户
			HashMap<String, String> addMchInfo = MerchantService.addBigMerchantAttachChannel(uniqueChannelId);
			needMchId = addMchInfo.get("MERCHANT_ID");
			// -----------2.接口去生成此商户的支付方式配置
			// 接口请求支付方式配置，给该商户新增支付方式配置
			MerchantPCService.addMerchantPC(needMchId);
			// ----------3.接口去审核激活此商户，以达到满足要求的数据
			//未审核(不需要调用接口)审核通过、不通过、需再次审核需要调用接口
			if(!es.equals("0")) {
				MerchantAAAService.examineMerchant(needMchId, es);
			}
			//未激活(不需要调接口)
			if(!as.equals("0")) {
				MerchantAAAService.activateMerchant(needMchId, as);
			}
		} else {
			needMchId = map.get(1).get("MERCHANT_ID");
			StringBuilder sb = new StringBuilder();
			String needMerchantIdSQL = sb.append("select * from tra_mch_pay_conf where merchant_id = '$needMchId' ".replace("$needMchId", needMchId))
					.append("and pay_type_id = '$payTypeId' ".replace("$payTypeId", PayDBOperations.payTypeInfo.get("PAY_TYPE_ID")))
					.toString();
			HashMap<String, String> mchResult = DBUtil.getQueryResultMap(needMerchantIdSQL).get(1);
			if (mchResult == null) {
				if(!as.equals("4")) {
					MerchantPCService.addMerchantPC(needMchId);
				}else{
					MerchantAAAService.activateMerchant(needMchId, "1");
					MerchantPCService.addMerchantPC(needMchId);//商户激活状态为冻结，不能添加支付方式
					MerchantAAAService.activateMerchant(needMchId, as);
				}
			} else {
				// 根据商户支付方式相应状态调用接口，
				//有一个特殊情况：当商户支付方式为未激活，接口不能产生这样的数据，只能修改数据库
				if(mchPCActivateStatus.equals("0") && !mchResult.get("ACTIVATE_STATUS").equals("0")){
					String updateSql = "update tra_mch_pay_conf set activate_status = 0 where merchant_id = '$merchantId'"
							.replace("$merchantId", needMchId);
					DBUtil.executeUpdateSql(updateSql);
				}else if(mchPCActivateStatus.equals("1") && !mchResult.get("ACTIVATE_STATUS").equals("1")){
					MerchantPCActivateService.ActivateMerchantPass(needMchId);
				}else if(mchPCActivateStatus.equals("2") && !mchResult.get("ACTIVATE_STATUS").equals("2")){
					MerchantPCActivateService.ActivateMerchantFail(needMchId);
				}else if(mchPCActivateStatus.equals("4") && !mchResult.get("ACTIVATE_STATUS").equals("4")){
					MerchantPCActivateService.ActivateMerchantFreeze(needMchId);
				}
			}
		}

		return needMchId;
	}
	
	public static void main(String...strings){
//		HashMap<String, String>[][] maps = getMerchantPCActiveTestData();
//		System.out.println(maps.length);
//		for(HashMap<String, String>[] map: maps){
//			System.out.println(map[0]);
//		}
		String s = getNeedMchId(ExamineStatus.PASS.getSCode(), ActivateStatus.NOPROCESS.getSCode(), "1");
		System.out.println(s);
		DBUtil.closeDBResource();
		}
}
