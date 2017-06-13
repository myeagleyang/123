package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DataGenerator;
import irsy.utils.dboperations.EmpDBOperations;
import irsy.utils.dboperations.ExpectationInDB;
//import oracle.net.aso.d;
import swiftpass.testcase.casebeans.EmpSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;

public class XEmpSearchTestData{
	public static final EmpSearchCaseBean tmpCase = new EmpSearchCaseBean();
	static String[] items = {"渠道编号", "渠道名称"};
	public static EmpSearchCaseBean[][] data() {
		HashMap<Integer, HashMap<String, String>> emps = EmpDBOperations.emps();
		List<EmpSearchCaseBean> list = new ArrayList<>();
		
		//1.默认查询
		EmpSearchCaseBean defaultSearchCase = SwiftPass.copy(tmpCase);
		defaultSearchCase.setCASE_NAME("默认查询...");
		list.add(defaultSearchCase);
			
		//2.单条件查询
		EmpSearchCaseBean byBeginCTCase = SwiftPass.copy(tmpCase);
		byBeginCTCase.setBeginCT(DataGenerator.generateDateBaseOnNow(0, 0, -5));
		byBeginCTCase.setCASE_NAME("根据开始时间查询...");
		list.add(byBeginCTCase);
		
		
		EmpSearchCaseBean byEndCTCase = SwiftPass.copy(tmpCase);
		byEndCTCase.setEndCT(DataGenerator.generateDateBaseOnNow(0, 0, -5));
		byEndCTCase.setCASE_NAME("根据结束时间查询...");
		list.add(byEndCTCase);
		
		EmpSearchCaseBean byEmpCodeCase = SwiftPass.copy(tmpCase);
		String empCode = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_CODE");
		byEmpCodeCase.setCASE_NAME("根据业务员编号查询...");
		byEmpCodeCase.setEmpCode(empCode);
		list.add(byEmpCodeCase);
		
		EmpSearchCaseBean byEmpNameCase = SwiftPass.copy(tmpCase);
		String empName = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_NAME");
		byEmpNameCase.setEmpName(empName);
		byEmpNameCase.setCASE_NAME("根据业务员名称查询...");
		list.add(byEmpNameCase);
		
		EmpSearchCaseBean byPhoneCase = SwiftPass.copy(tmpCase);
		String phone = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("MOBILE");
		byPhoneCase.setPhone(phone);
		byPhoneCase.setCASE_NAME("根据手机号码查询...");
		list.add(byPhoneCase);
		
		EmpSearchCaseBean byOrgCase = SwiftPass.copy(tmpCase);
		String orgId = emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("ORG_ID");
		HashMap<String, String> org = ChannelDBOperations.getChannel(orgId);
		String orgName = org.get("CHANNEL_NAME");
		byOrgCase.setIsSelectOrg("true");
		byOrgCase.setIsConfirmSelectOrg("true");
		byOrgCase.setIdOrNameItem(items[RandomUtils.nextInt(0, items.length)]);
		byOrgCase.setIdOrName(byOrgCase.getIdOrNameItem().equals(items[0]) ? orgId : orgName);
		byOrgCase.setOrgId(orgId);
		byOrgCase.setOrgName(orgName);
		list.add(byOrgCase);
		
		//3.组合条件查询
		for(int i = 0; i < 12; i++){
			EmpSearchCaseBean groupCase = SwiftPass.copy(tmpCase);
			String seed = RandomStringUtils.randomNumeric(6);
			if(seed.charAt(0) % 2 == 0){
				groupCase.setBeginCT(DataGenerator.generateDateBaseOnNow(0, 0, -3));
			}
			if(seed.charAt(1) % 2 == 0){
				groupCase.setEndCT(DataGenerator.generateDateBaseOnNow(0, 0, 3));
			}
			if(seed.charAt(2) % 2 == 0){
				groupCase.setEmpCode(emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_CODE"));
			}
			if(seed.charAt(3) % 2 == 0){
				groupCase.setEmpName(emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("EMP_NAME"));
			}
			if(seed.charAt(4) % 2 == 0){
				groupCase.setPhone(emps.get(RandomUtils.nextInt(1, emps.size() + 1)).get("MOBILE"));
			}
			if(seed.charAt(5) % 2 == 0){
				groupCase.setOrgId(orgId);
				groupCase.setOrgName(orgName);
			}
			groupCase.setCASE_NAME("组合查询:"+i+"--");
			list.add(groupCase);
		}
		list.forEach(element -> getEmpQueryCount(element) );
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(EmpSearchCaseBean [][] ::new );
	}

	public static void main(String ...strings){
		for(EmpSearchCaseBean  d[]: data()){
			System.out.println(d[0]);
		}
	}
	
	
	public HashMap<String, String> preCheckProcess(HashMap<String, String> oneCase) {
		oneCase.replace("expectCount", String.valueOf(ExpectationInDB.getEmpQueryCount(oneCase)));
		return oneCase;
	}
	public static int getEmpQueryCount(EmpSearchCaseBean oneCase){
		int result = 0;
		String sql = "Select count(*) From CMS_EMP Where 1 = 1 $CONDITIONS";
		String defaultSql = sql.replace("$CONDITIONS", "");
		StringBuilder conditions = new StringBuilder();
		if(oneCase == null)
			result = DBUtil.getQueryResultRowCount(defaultSql);
		else{
			String ctCondition = getCreateTimeCondition(oneCase.getBeginCT(), oneCase.getEndCT());
			String eiCondition = getEqualCondition("EMP_CODE", oneCase.getEmpCode());
			String enCondition = getLikeCondition("EMP_NAME", oneCase.getEmpName());
			String pnCondition = getEqualCondition("MOBILE", oneCase.getPhone());
			String pcCondition = getEqualCondition("ORG_ID", oneCase.getOrgId());
			if(!StringUtils.isEmpty(ctCondition))
				conditions.append(" And ").append(ctCondition);
			if(!StringUtils.isEmpty(eiCondition))
				conditions.append(" And ").append(eiCondition);
			if(!StringUtils.isEmpty(enCondition))
				conditions.append(" And ").append(enCondition);
			if(!StringUtils.isEmpty(pnCondition))
				conditions.append(" And ").append(pnCondition);
			if(!StringUtils.isEmpty(pcCondition))
				conditions.append(" And ").append(pcCondition);
			result = Integer.parseInt(DBUtil.getQueryResultMap(sql.replace("$CONDITIONS", conditions.toString())).get(1).get("COUNT(*)"));
		}
		oneCase.setExpectCount(String.valueOf(result));
		return result;
	}
	/**
	 * @param beginCT	yyyy-mm-dd hh:mi:ss格式
	 * @param endCT
	 * @return
	 */
	public static String getCreateTimeCondition(String beginCT, String endCT){
		String str = "to_date('$date','yyyy-mm-dd HH24:mi:ss')";
		if(!StringUtils.isEmpty(beginCT) && StringUtils.isEmpty(endCT)){
			return "CREATE_TIME >= " + str.replace("$date", beginCT);
		} else if(!StringUtils.isEmpty(endCT) && StringUtils.isEmpty(beginCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT);
		} else if(!StringUtils.isEmpty(beginCT) && !StringUtils.isEmpty(endCT)){
			return "CREATE_TIME <= " + str.replace("$date", endCT) + " And " + 
					"CREATE_TIME >= " + str.replace("$date", beginCT);
		} else{
			return "";
		}
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
}