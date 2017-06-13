package irsy.testcase.data.cooperate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import irsy.utils.dboperateions.CoopearteDBOperations;
import irsy.utils.dboperations.DataGenerator;
public class CooperateTestData {

	//内部机构已经配置的支付方式


//	private static HashMap<String, String> payTypeInfo = CoopearteDBOperations.payTypeInfo;
	private static HashMap<Integer, HashMap<String,String>> payTypeInfo = CoopearteDBOperations.payTypeInfo;

	
	public static HashMap<String, String>[][] getCooperateAddTestData(){
		return getAllCooperateAddTestData();
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
			ERRORMSG = "error",
			CONAME="cooperateName",
			COPASSWORD="password",
			COCONTACTS="contacts",
			COMOBILE="mobilePhone",
			COTYPE="",
			ADDSUC="addSuc",
			CHNACTIATESTATUS = "chnActivateStatus";  //渠道激活状态
	
	
	// 返回只包含渠道新增支付方式页面的参数
	private static HashMap<String, String> initPageParamsMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] valueKeys = {CONAME, COCONTACTS, COMOBILE};
		for (String valueKey : valueKeys) {
			map.put(valueKey, "");
		}
		return map;
	}


	// 获取新增指定渠道的单个用例的测试数据
	public static HashMap<String, String> getCaseMapWithCtrlParams() {
//		HashMap<String, String> data = initPageParamsMap();
//		String selectPtName = payTypeInfo.get("PAY_TYPE_NAME");
		HashMap<String, String> data=initPageParamsMap();
		data.put(CONAME, DataGenerator.generateZh_CNName());
		data.put(COCONTACTS,DataGenerator.generateZh_CNName() );
		data.put(COMOBILE,DataGenerator.generatePhone() );
		data.put(ERRORMSG, "");
//		System.out.println(data);
		
		return data;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllCooperateAddTestData() {
		HashMap<String, String>[][] resultCasesMapArray = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		// 渠道新增支付方式失败测试用例--必填字段为空，正常关闭、选择支付类型正常关闭
		String needs[] = {CONAME, COCONTACTS, COMOBILE}; 
		String errorMsg[] = {"请输入资金合作方名称", "请输入联系人", "请输入联系人电话"};
		for (int i = 0; i < needs.length; i++) {
			HashMap<String, String> oneCase = getCaseMapWithCtrlParams();
			oneCase.replace(needs[i], "");
			oneCase.replace(ERRORMSG, errorMsg[i]);
			System.out.println(oneCase);
			list.add(oneCase);
		}
		
		HashMap<String, String> oneCase = getCaseMapWithCtrlParams();
		oneCase.put(ADDSUC, "添加成功");
		oneCase.replace(ERRORMSG, "");
		list.add(oneCase);
		
		for(HashMap<String, String> oneCaseMap: list){
			resultCasesMapArray = ArrayUtils.add(resultCasesMapArray, ArrayUtils.toArray(oneCaseMap));
		}
			
		return resultCasesMapArray;
	}

	public static void main(String srgs[]) {
//		long start = System.currentTimeMillis();
//		HashMap<String, String>[][] maps = getCooperateAddTestData();
//		for (HashMap<String, String>[] map : maps)
//			System.out.println(map[0]);
//		System.out.println(System.currentTimeMillis() - start);
//		DBUtil.closeDBResource();
//		System.err.println(maps.length);
		
		System.out.println(CooperateTestData.payTypeInfo);
		
		// int s = getChannelPayConfScope(getNoPayConfChannel());
		// System.out.println(s);
		// String ss = getNoPayConfChannel();
		// System.out.println(ss);
		// String[] sss = getChannelPayTypeId_Name();
		// System.out.println(sss[0] + sss[1] + sss[2]);
//		 DatabaseUtil.closeDBResource();
	}

}
