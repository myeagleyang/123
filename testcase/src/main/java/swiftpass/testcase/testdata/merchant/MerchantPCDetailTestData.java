package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import swiftpass.testcase.casebeans.MerchantPCDetailCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.MerchantPCService;
import swiftpass.utils.services.MerchantService;

public class MerchantPCDetailTestData {
	private static final MerchantPCDetailCaseBean tmpCase = new MerchantPCDetailCaseBean();
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setIsClickFirstRowMerchant("true");
		tmpCase.setIsClickFirstRowPT("true");
		tmpCase.setErrorMsg("");
	}
	
	public static MerchantPCDetailCaseBean[][] data(){
		List<MerchantPCDetailCaseBean> list = new ArrayList<>();
		MerchantPCDetailCaseBean noselectMerchant =SwiftPass.copy(tmpCase);
		noselectMerchant.setErrorMsg("请选择您需要查看的记录");
		noselectMerchant.setCASE_NAME("未选择记录(点击商家)...");
		list.add(noselectMerchant);
		
		MerchantPCDetailCaseBean noselectPTt =SwiftPass.copy(tmpCase);
		noselectPTt.setErrorMsg("请选择您需要查看的记录");
		noselectPTt.setCASE_NAME("未选择记录(点击支付类型)...");
		list.add(noselectPTt);
		
		
		//查看详情成功用例
		MerchantPCDetailCaseBean successCaseMap =SwiftPass.copy(tmpCase);
		successCaseMap.setCASE_NAME("成功查看详情用例...");
		list.add(successCaseMap);
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(MerchantPCDetailCaseBean[][] :: new);
	}
	
	/**
	 * @description 获取需要的商户ID
	 * @return merchantId
	 * 
	 * */
	public static String getNeedMerchantId(){
		String needMerchantId = null;
		String childOfAccessOrg = ChannelDBOperations.uniqueChannleInfo.get("CHANNEL_ID");
		String getNeedMerchantIdSQL = "select * from tra_mch_pay_conf where merchant_id in (select merchant_id from cms_merchant)";
		HashMap<String, String> needMerchantIdResult = DBUtil.getQueryResultMap(getNeedMerchantIdSQL).get(1);
		String getMerchantWithoutPCSQL = "select * from cms_merchant";
		HashMap<String, String> merchantWithoutPC = DBUtil.getQueryResultMap(getMerchantWithoutPCSQL).get(1);
		if(needMerchantIdResult == null){
			if(merchantWithoutPC == null){
				//调用新增支付商户接口新增一个大商户
				HashMap<String, String> bigMerchant = MerchantService.addBigMerchantAttachChannel(childOfAccessOrg);
				needMerchantId = bigMerchant.get("MERCHANT_ID");
			}else{
				needMerchantId = merchantWithoutPC.get("MERCHANT_ID");
			}
			//调用商户新增支付方式接口新增支付方式
			MerchantPCService.addMerchantPC(needMerchantId);
		}else{
			needMerchantId = needMerchantIdResult.get("MERCHANT_ID");
		}
		return needMerchantId;
	}
	
	public static void main(String...strings){
		String s = getNeedMerchantId();
		System.out.println(s);
	}

}
