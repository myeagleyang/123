package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ExpectationInDB;
import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.testcase.casebeans.StoreSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;

public class XStoreSearchTestData {
	private static final StoreSearchCaseBean tmpCase= new StoreSearchCaseBean();
	
	
	public static StoreSearchCaseBean[][] data(){
		List<StoreSearchCaseBean> list = new ArrayList<>();
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
		StoreSearchCaseBean searchAllCase = SwiftPass.copy(tmpCase);
		searchAllCase.setCASE_NAME("默认查询-全部门店...");
		list.add(searchAllCase);
		
		//单条件查询
		StoreSearchCaseBean singleSearchStoreCase = SwiftPass.copy(tmpCase);//根据大商户查询
		singleSearchStoreCase.setBigMchName(bigMerchantName);
		singleSearchStoreCase.setBigMchId(bigMerchantId);
		singleSearchStoreCase.setCASE_NAME("根据大商户查询...");
		list.add(singleSearchStoreCase);
		
		
		StoreSearchCaseBean searchByDeptId = SwiftPass.copy(tmpCase);//根据部门查询
		searchByDeptId.setDepartName(deptName);
		searchByDeptId.setDepartId(deptId);
		searchByDeptId.setCASE_NAME("根据部门查询...");
		list.add(searchByDeptId);
		
		StoreSearchCaseBean searchByStoreName = SwiftPass.copy(tmpCase); //根据门店查询
		searchByStoreName.setStoreName(storeNames[RandomUtils.nextInt(0, storeNames.length)]);
		searchByStoreName.setCASE_NAME("根据门店查询");
		list.add(searchByStoreName);
		
		StoreSearchCaseBean searchByExamineStatus = SwiftPass.copy(tmpCase);//根据审核状态查询
		searchByExamineStatus.setExamineStatus(examineStatus[RandomUtils.nextInt(0, examineStatus.length)]);
		searchByExamineStatus.setCASE_NAME("根据审核状态查询...");
		list.add(searchByExamineStatus);
		
		StoreSearchCaseBean searchByActivateStatus = SwiftPass.copy(tmpCase);//根据激活状态查询
		searchByActivateStatus.setActivateStatus(activateStatus[RandomUtils.nextInt(0, activateStatus.length)]);
		searchByActivateStatus.setCASE_NAME("根据激活状态查询...");
		list.add(searchByActivateStatus);
		
		StoreSearchCaseBean searchByStoreId = SwiftPass.copy(tmpCase);
		searchByStoreId.setStoreId(storeId);
		searchByStoreId.setCASE_NAME("根据门店编号查询...");
		list.add(searchByStoreId);
		
		//多条件随机组合 10条用例
		for(int i=0; i<10; i++){
			String random = RandomStringUtils.randomNumeric(6);//生成6位随机数，每位代表一个参数是否填，奇数为代表填，偶数代表不填
			StoreSearchCaseBean multiSearchStoreCase = SwiftPass.copy(tmpCase);
			if(Integer.parseInt(random.substring(0, 1)) % 2 != 0){
				multiSearchStoreCase.setBigMchName(bigMerchantName);
				multiSearchStoreCase.setBigMchId(bigMerchantId);
			}
			if(Integer.parseInt(random.substring(1, 2)) % 2 != 0){
				multiSearchStoreCase.setDepartName(deptName);
				multiSearchStoreCase.setDepartId(deptId);
			}
			if(Integer.parseInt(random.substring(2, 3)) % 2 != 0){
				multiSearchStoreCase.setStoreName(storeNames[RandomUtils.nextInt(0, storeNames.length)]);
			}
			if(Integer.parseInt(random.substring(3, 4)) % 2 != 0){
				multiSearchStoreCase.setExamineStatus(examineStatus[RandomUtils.nextInt(0, examineStatus.length)]);
			}
			if(Integer.parseInt(random.substring(4, 5)) % 2 != 0){
				multiSearchStoreCase.setActivateStatus(activateStatus[RandomUtils.nextInt(0, activateStatus.length)]);
			}
			if(Integer.parseInt(random.substring(5, 6)) % 2 != 0){
				multiSearchStoreCase.setStoreId(storeId);
			}
			list.add(multiSearchStoreCase);
		}
		//调用查询，并把实际结果压到相应用例
		for(StoreSearchCaseBean caseBean: list){
			caseBean.setExpected(getStoreQueryCount(caseBean) + "");
		}
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(StoreSearchCaseBean [][]::new);
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
	
	
	public static int getStoreQueryCount(StoreSearchCaseBean caseBean){
		int result = 0;
		String sql = "Select count(*) From CMS_MERCHANT Where MERCHANT_TYPE IN (13, 14) $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder("And ");
		if(caseBean == null)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String bmCondition = getEqualCondition("PARENT_MERCHANT", caseBean.getBigMchId());
			String dpCondition = getEqualCondition("DEPT_ID", caseBean.getDepartId());
			String snCondition = getLikeCondition("MERCHANT_NAME", caseBean.getStoreName());
			String asCondition = getEqualCondition("ACTIVATE_STATUS", caseBean.getActivateStatus());
			String esCondition = getEqualCondition("EXAMINE_STATUS", caseBean.getExamineStatus());
			String siCondition = getEqualCondition("MERCHANT_ID", caseBean.getStoreId());
			if(!StringUtils.isEmpty(bmCondition))
				conditions.append(bmCondition).append(" And ");
			if(!StringUtils.isEmpty(dpCondition))
				conditions.append(dpCondition).append(" And ");
			if(!StringUtils.isEmpty(snCondition))
				conditions.append(snCondition).append(" And ");
			if(!StringUtils.isEmpty(asCondition))
				conditions.append(asCondition).append(" And ");
			if(!StringUtils.isEmpty(esCondition))
				conditions.append(esCondition).append(" And ");
			if(!StringUtils.isEmpty(siCondition))
				conditions.append(siCondition).append(" And ");
			String c = conditions.delete(conditions.lastIndexOf("And "), conditions.length()).toString();
			result = DBUtil.getQueryResultRowCount(sql.replace("$CONDITIONS", c));
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		return result;
	}
	/**
	 * 给定列名及精确查询值，组装成精确查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getEqualCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" = '").append(value).append("' ");
		return res.toString();
	}
	
	/**
	 * 给定列名及模糊查询值，组装成模糊查询条件字符串
	 * @param colLabel
	 * @param value
	 * @return
	 */
	public static String getLikeCondition(String colLabel, String value){
		if(StringUtils.isEmpty(colLabel) || StringUtils.isEmpty(value))
			return "";
		StringBuilder res = new StringBuilder(colLabel);
		res.append(" Like '%").append(value).append("%'");
		return res.toString();
	}
	
	public static void main(String arg[]){
//		String[] array = getStoreNames();
//		for(String s: array){
//			System.err.println(s);
//		}
		for(StoreSearchCaseBean [] map: data()){
			System.out.println(map[0]);
		}
	}

}
