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
import swiftpass.testcase.casebeans.MerchantPCActiveCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantPCActivateService;
import swiftpass.utils.services.MerchantPCService;
import swiftpass.utils.services.MerchantService;

public class XMerchantPCActiveTestData {
	private static final MerchantPCActiveCaseBean tmpCase = new MerchantPCActiveCaseBean();
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setPreMchExamineStatus(ExamineStatus.PASS.getSCode()); //默认为审核通过
		tmpCase.setPreMchActiveStatus(ActivateStatus.NOPROCESS.getSCode());//默认未激活
		tmpCase.setActiveOperate(ActivateOperate.pass.getText()); //默认激活通过
		//新增merchantId用于查询
		tmpCase.setPayTypeName(PayDBOperations.payTypeInfo.get("PAY_TYPE_NAME"));
		//新增控制参数
		tmpCase.setMerchantPCActivateStatus(ActivateStatus.NOPROCESS.getSCode()); //商户支付方式配置默认激活状态为未激活状态
		tmpCase.setIsClickFirstRowMerchant("true");	
		tmpCase.setIsClickFirstRowPT("true");
		tmpCase.setIsConfirmActiveOperate("true");
		tmpCase.setErrorMsg("");
	}
	
	
	public static MerchantPCActiveCaseBean[][] data(){
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
	private static MerchantPCActiveCaseBean[][] getAllMerchantPCActiveTestData(){
		List<MerchantPCActiveCaseBean> list = new ArrayList<>();
		String needs[] = {ISCLICKFIRSTROWMERCHANT, ACTIVATEOPERATE, ISCLICKFIRSTROWPT, ISCONFIRMACTIVATEOPERATE};
		String errorMsg[] = {"", "正常关闭", "请选择要激活的记录!", "正常取消"};
		for(int i=0; i<needs.length; i++){
			MerchantPCActiveCaseBean oneCaseMap = SwiftPass.copy(tmpCase);
			if(needs[i].equals(ISCLICKFIRSTROWMERCHANT)){
				oneCaseMap.setIsClickFirstRowMerchant("");
			}else if(needs[i].equals(ACTIVATEOPERATE)){
				oneCaseMap.setActiveOperate("");
			}else if(needs[i].equals(ISCLICKFIRSTROWPT)){
				oneCaseMap.setIsClickFirstRowPT("");
			}else if(needs[i].equals(ISCONFIRMACTIVATEOPERATE)){
				oneCaseMap.setIsConfirmActiveOperate("");	
			}
			oneCaseMap.setErrorMsg(errorMsg[i]);
			oneCaseMap.setCASE_NAME(ERRORMSG);
			list.add(oneCaseMap);
		}
		
		//商户审核通过-激活4种状态（通-未、通-成功、通-失败、通-冻结）--激活成功用例
		for(ActivateStatus as: ActivateStatus.values()){
			MerchantPCActiveCaseBean oneCaseMap = SwiftPass.copy(tmpCase);
			if(!as.equals(ActivateStatus.NEEDAGAIN)){
				oneCaseMap.setPreMchActiveStatus(as.getSCode());
				oneCaseMap.setCASE_NAME("审核通过"+as.getSCode()+"状态校验...");
				list.add(oneCaseMap);
			}
		}
		//商户非审核通过（3种状态）-未激活（不通-未激活、未审核-未激活、需再次审核-未激活）--激活按钮状态用例
		for(ExamineStatus es: ExamineStatus.values()){
			if(!es.equals(ExamineStatus.PASS)){
				MerchantPCActiveCaseBean oneCaseMap = SwiftPass.copy(tmpCase);
				oneCaseMap.setPreMchExamineStatus(es.getSCode());
				oneCaseMap.setCASE_NAME("非审核通过"+es.getSCode()+"状态校验...");
				list.add(oneCaseMap);
			}
		}
		
		//商户支付方式配置为非激活成功状态--操作成功用例(3*3)
		for(ActivateStatus mchActivateStatus: ActivateStatus.values()){
			if(!mchActivateStatus.equals(ActivateStatus.PASS) && !mchActivateStatus.equals(ActivateStatus.NEEDAGAIN)){
				for(ActivateOperate activeOprate: ActivateOperate.values()){
					if(!activeOprate.equals(ActivateOperate.close)){
						MerchantPCActiveCaseBean oneCaseMap = SwiftPass.copy(tmpCase);
						oneCaseMap.setMerchantPCActivateStatus(mchActivateStatus.getSCode());
						oneCaseMap.setActiveOperate(activeOprate.getText());
						oneCaseMap.setCASE_NAME("非激活成功状态校验...");
						list.add(oneCaseMap);
					}
				}
			}
		}
		//商户支付方式配置为激活成功状态只能进行冻结和关闭操作
		 MerchantPCActiveCaseBean freezeCaseMap = SwiftPass.copy(tmpCase);
		freezeCaseMap.setMerchantPCActivateStatus(ActivateStatus.PASS.getSCode());
		freezeCaseMap.setActiveOperate(ActivateOperate.freez.getText());
		freezeCaseMap.setCASE_NAME("户支付方式配置为激活成功状态只能进行冻结和关闭操作...");
		list.add(freezeCaseMap);
		
		MerchantPCActiveCaseBean closeCaseMap = SwiftPass.copy(freezeCaseMap);
		closeCaseMap.setActiveOperate(ActivateOperate.close.getText());
		closeCaseMap.setErrorMsg("正常关闭");
		closeCaseMap.setCASE_NAME("正常关闭...");
		list.add(closeCaseMap);
		
		//商户支付方式配置为激活状态去进行激活通过和激活不通过时抛出异常
		MerchantPCActiveCaseBean oneCaseMap = SwiftPass.copy(freezeCaseMap);
		oneCaseMap.setMerchantPCActivateStatus(ActivateStatus.PASS.getSCode());
		oneCaseMap.setErrorMsg("已激活的支付方式不显示激活通过按钮");
		oneCaseMap.setCASE_NAME("商户支付方式配置为激活状态去进行激活通过和激活不通过时抛出异常...");
		list.add(oneCaseMap);
		
		MerchantPCActiveCaseBean oneCaseMap_ = SwiftPass.copy(oneCaseMap);
		oneCaseMap_.setActiveOperate(ActivateOperate.fail.getText());
		oneCaseMap_.setErrorMsg("已激活的支付方式不显示激活不通过按钮");
		oneCaseMap_.setCASE_NAME("已激活的支付方式不显示激活不通过按钮...");
		list.add(oneCaseMap_);
		
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantPCActiveCaseBean[][]:: new);
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
					MerchantPCService.addMerchantPC(needMchId);//商户激活状态为冻结，不能添加支付方式
				}else{
					throw new MerchantPayConfException("错误：", "商户被冻结，不能接口添加支付方式");
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
		String s = getNeedMchId(ExamineStatus.PASS.getSCode(), ActivateStatus.FREEZE.getSCode(), "0");
		System.out.println(s);
		}
}
