package swiftpass.testcase.testdata.merchant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DeptDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.DepartService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;

public class XDepartmentSearchTestData {
	//static String deptName = "";

	public static DepartmentSearchCaseBean[][] data() {
		// 初始化部门查询需要用的数据
		preCheckProcess();
		//deptName = initNewDeptData();
		// String deptName ="randomDeptName_";
		List<DepartmentSearchCaseBean> caseList = new ArrayList<>();

		// 1.默认查询
		DepartmentSearchCaseBean defaultQuery = new DepartmentSearchCaseBean();
		defaultQuery.setCASE_NAME("部门默认查询");
		caseList.add(defaultQuery);

		// 2.单条件查询,包括部门名称精确查询，模糊查询,所属大商户查询
		DepartmentSearchCaseBean byDeptName = new DepartmentSearchCaseBean().setDepartName(randomDepartName());
		byDeptName.setCASE_NAME("单条件部门查询-部门名称.");
		caseList.add(byDeptName);
		// 单条件根据大商户名称模糊查询
		String getMchNameOrId = getMerChantNameExamine(getMerChantId(1));
		DepartmentSearchCaseBean byMerchantName = new DepartmentSearchCaseBean().setIsSelectBigMerchant("true")
				.setIsSelectBigMerchant("true").setBigMerchantName(getMchNameOrId).setMerchantType("大商户名称")
				.setMchNameOrId(getMchNameOrId).setIsConfirmSelectMerchant("true");
		byMerchantName.setCASE_NAME("单条件部门查询-大商户名称.");
		caseList.add(byMerchantName);
		// 单条件根据大商户编码查询
		String chantId = getMerChantId(1);
		getMchNameOrId = getMerChantNameExamine(chantId);
		DepartmentSearchCaseBean byMerchantCode = new DepartmentSearchCaseBean().setIsSelectBigMerchant("true")
				.setBigMerchantCode(chantId).setMerchantType("大商户编码").setMchNameOrId(chantId)
				.setIsConfirmSelectMerchant("true");
		byMerchantCode.setCASE_NAME("单条件部门查询-大商户ID.");
		caseList.add(byMerchantCode);

		// 3.组合条件查询
		// 根据部门名称 + 大商户名称
		String chantName = getMerChantNameExamine(getMerChantId(1));
		DepartmentSearchCaseBean multiQuery = new DepartmentSearchCaseBean().setDepartName(maxCountDeptNameChar())
				.setIsSelectBigMerchant("true").setMerchantType("大商户名称").setBigMerchantName(chantName)
				.setMchNameOrId(chantName).setIsConfirmSelectMerchant("true");
		caseList.add(multiQuery);
		multiQuery.setCASE_NAME("组合查询:部门名称 + 大商户名称..");

		// 根据部门名称 + 大商户ID
		chantId = getMerChantId(1);
		multiQuery = new DepartmentSearchCaseBean().setDepartName(maxCountDeptNameChar()).setIsSelectBigMerchant("true")
				.setBigMerchantCode(chantId).setMerchantType("大商户编码").setMchNameOrId(chantId)
				.setIsConfirmSelectMerchant("true");
		caseList.add(multiQuery);
		multiQuery.setCASE_NAME("组合查询:部门名称 + 大商户ID..");
		// 遍历caseList，通过相关条件进行sql查询，并将预期结果数量set到对象execpted属性中
		caseList.forEach(element -> XDepartmentSearchHelper.queryExpectedResult(element));

		// 将返回的caseList转换成DepartmentSearchCaseBean二维数组
		return caseList.stream().map(element -> ArrayUtils.toArray(element)).toArray(DepartmentSearchCaseBean[][]::new);
	}

	private static void preCheckProcess() {
		int expected = 30;
		Map<Integer, HashMap<String, String>> sys = DeptDBOperations.allDeparts();
		if (sys.size() < expected) {
			String channelId = ChannelDBOperations.acceptOrgUniqueChannel().get("CHANNEL_ID");
			HashMap<String, String> bigM = MerchantService.addBigMerchantAttachChannel(channelId);
			String parentBigMchId = bigM.get("MERCHANT_ID");
			MerchantAAAService.examineMerchant(parentBigMchId, ExamineStatus.PASS.getSCode());
			for (int i = 0; i < expected - sys.size(); i++) {
				DepartService.addDepartWithoutParentDept(parentBigMchId);
			}
		}
	}

	public static String initNewDeptData() {
		String deptName = "", deptID = "", sql = "", merchant_id = "";
		merchant_id = getMerChantId(1);
		SimpleDateFormat sd = new SimpleDateFormat("yyMMddHHmmss");
		deptID = (sd.format(new Date()));
		deptName = "RandomDept_HJ_" + deptID;
		sql = "insert into CMS_DEPT (DEPT_ID, DEPT_NAME, MERCHANT_ID, EXAMINE_STATUS, EXAMINE_TIME, "
				+ " EXAMINE_STATUS_REMARK,ACTIVATE_STATUS, ACTIVATE_TIME, ACTIVATE_STATUS_REMARK, "
				+ "PHYSICS_FLAG, DATA_SOURCE,Create_User,CREATE_EMP, CREATE_TIME, UPDATE_TIME)  ";
		sql += ("values ('" + deptID + "', '" + deptName + "', '" + merchant_id
				+ "', 1, systimestamp, '添加时系统自动审核通过', 1, systimestamp, '添加时系统自动激活成功', 1, 6, 1, 'superadmin', systimestamp, systimestamp)");

		boolean execRes = DBUtil.executeUpdate(sql);
		return execRes == true ? deptName : "";
	}

	/**
	 * 根据examine_status审核状态获取商户ID. 0:未审核,1:已审核.
	 * 
	 * @param examine_status
	 * @return
	 */
	public static String getMerChantId(int examine_status) {
		String result = "";
		List<Map<String, Object>> rs = DBUtil.getResultToList(
				("SELECT * FROM (SELECT m.merchant_id FROM cms_merchant m where m.merchant_type =11 and m.examine_status =? order by dbms_random.value) WHERE rownum =1"),examine_status);
		if (rs != null && rs.size() > 0) {
			result = rs.get(0).get("merchant_id").toString();
			// System.out.println(rs.get(0).get("merchant_id"));
		} else {
			if(examine_status ==1){
				result = "151580000003";
			}else{
				result = "151580000002";
			}
			 // 如果当前数据库未查询到数据,给予一个大商默认值
		}
		return result;
	}
	private static String maxCountDeptNameChar(){
		StringBuilder sb = new StringBuilder();
		Map<Integer, HashMap<String, String>> depts = DeptDBOperations.allDeparts();
		for(Integer key : depts.keySet())
			sb.append(depts.get(key).get("DEPT_NAME"));
		
		return String.valueOf(SwiftPass.getAppearMaxCountChar(sb.toString()));
	}

	public static String getMerChantNameExamine(String getMerChantId) {
		String result = "";
		List<Map<String, Object>> rs = DBUtil
				.getResultToList("select t.merchant_name from CMS_MERCHANT t where t.merchant_id =?", getMerChantId);
		if (rs != null && rs.size() > 0) {
			result = rs.get(0).get("merchant_name").toString();
			// System.out.println(rs.get(0).get("merchant_id"));
		} else {
			result = "大商100"; // 如果当前数据库未查询到数据,给予一个大商默认值
		}
		return result;
	}
	private static String randomDepartName(){
		Map<Integer, HashMap<String, String>> depts = DeptDBOperations.allDeparts();
		return depts.get(RandomUtils.nextInt(1, depts.size())).get("DEPT_NAME");
	}
	public static void main(String[] args) {
		// System.out.println(initNewDeptData());
		// HashMap<Integer, HashMap<String, String>> rs =
		HashMap<Integer, HashMap<String, String>> rs = DBUtil.getQueryResultMap(
				"SELECT * FROM (SELECT m.merchant_id FROM cms_merchant m order by dbms_random.value) WHERE rownum =1");
		System.out.println(rs);
		// System.out.println(new SimpleDateFormat("yyMMddHHmmss").format(new
		// Date()));

	}

	static class XDepartmentSearchHelper {
		public static void queryExpectedResult(DepartmentSearchCaseBean d) {
			String sql = "select count(*) expected from cms_dept where 1 = 1 ";
			if (!StringUtils.isEmpty(d.getDepartName())) {
				sql += " and dept_name like '%" + d.getDepartName() + "%' ";
			}
			if (!StringUtils.isEmpty(d.getBigMerchantCode())) {
				sql += " and merchant_id ='" + d.getBigMerchantCode() + "' ";
			}
			if (!StringUtils.isEmpty(d.getBigMerchantName())) {
				sql += " and merchant_id =(select  merchant_id from cms_merchant where merchant_name like '%"
						+ d.getBigMerchantName() + "%') ";
			}
			if (!StringUtils.isEmpty(d.getBigMerchantName())) {
				sql += " and  merchant_id = '"
						+ DBUtil.getResultToList("select merchant_id from cms_merchant where merchant_name =?",
								new Object[] { d.getBigMerchantName() }).get(0).get("merchant_id").toString()
						+ "' ";
			}
			SwiftLogger.debug("部门查询预期结果查询语句为： ".concat(sql).concat("  ---------------"));
			d.setExpected(DBUtil.getResultToList(sql).get(0).get("expected").toString());
		}
	}
}