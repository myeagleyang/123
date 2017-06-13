package swiftpass.testcase.testdata.merchant;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import swiftpass.testcase.casebeans.DepartmentAddCaseBean;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
public class XDepartmentAddTestData {
	private static String[] needKeyMessage = { "请输入部门名称", "请选择所属大商户" };
	static String deptName = "", merchantId = "", merchantId_nopass = "";
	static final String NULL = "";
	static final DepartmentAddCaseBean baseBean = new DepartmentAddCaseBean();

	public static DepartmentAddCaseBean[][] data() {
		// 初始化部门查询需要用的数据
		List<DepartmentAddCaseBean> caseList = new ArrayList<>();
		// 1.正常成功添加,根据大商户编码
		deptName ="AddDept_HJ_"+new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		merchantId = XDepartmentSearchTestData.getMerChantId(1);// 获取审核通过的大商户编码
		merchantId_nopass = XDepartmentSearchTestData.getMerChantId(0);// 获取未审核的大商户编码
		String merchantName = getMerChantNameExamine(merchantId);
		baseBean.setDepartName(deptName);
		baseBean.setIsSelectBigMerchant("true");
		baseBean.setMerchantType("大商户编码");
		baseBean.setBigMerchantCode(merchantId);
		baseBean.setMchNameOrIdItem(merchantId);
		baseBean.setIsConfirmSelectMerChant("true");
		baseBean.setIsSelectParentDepartment("false");
		baseBean.setIsSave("true");
		baseBean.setIsConfirmSave("true");
//		baseBean.setParentDepartment(String.valueOf(String.valueOf(DBUtil.getResultToList("select dept_name from cms_dept where merchant_id = ?", merchantId).get(0).get("dept_name").toString())));
		baseBean.setCASE_NAME("成功添加一个部门 — 根据大商户编码");
		caseList.add(baseBean);

		// 正常成功添加,根据大商户名称;
		stop(1);
		DepartmentAddCaseBean byMerchantName = SwiftPass.copy(baseBean);
		deptName ="AddDept_HJ_"+new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		merchantId = XDepartmentSearchTestData.getMerChantId(1);// 获取审核通过的大商户编码
		merchantId_nopass = XDepartmentSearchTestData.getMerChantId(0);// 获取未审核的大商户编码
		byMerchantName.setDepartName(deptName);
		byMerchantName.setIsSelectBigMerchant("true");
		byMerchantName.setMerchantType("大商户名称");
		byMerchantName.setBigMerchantName(merchantName);
		byMerchantName.setMchNameOrIdItem(merchantName);
		byMerchantName.setIsConfirmSelectMerChant("true");
		byMerchantName.setIsSelectParentDepartment("true");
		byMerchantName.setIsSave("true");
		byMerchantName.setIsConfirmSave("true");
//		byMerchantName.setParentDepartment(String.valueOf(String.valueOf(DBUtil.getResultToList("select dept_name from cms_dept where merchant_id = ?", merchantId).get(0).get("dept_name").toString())));
		byMerchantName.setCASE_NAME("成功添加一个部门 — 根据大商户名称");
		caseList.add(byMerchantName);

		// 2.必填项未填
		DepartmentAddCaseBean noDeptName = SwiftPass.copy(baseBean).setDepartName(NULL)
				.setMessage(needKeyMessage[0]);
		noDeptName.setCASE_NAME("添加部门 - 部门名称为空校验");
		caseList.add(noDeptName);
		
		DepartmentAddCaseBean noSelectMerchant = SwiftPass.copy(baseBean)
				.setIsSelectBigMerchant("false").setMessage(needKeyMessage[1]);
		noSelectMerchant.setCASE_NAME("添加部门 - 大商户未选择校验");
		caseList.add(noSelectMerchant);

		// 3.其他字段校验
		DepartmentAddCaseBean deptNametooLong = SwiftPass.copy(baseBean)
				.setDepartName(RandomStringUtils.randomNumeric(65))
				.setMessage(XDepartmentAddHelper.DEPART_NAME_TOO_LONG);
		deptNametooLong.setCASE_NAME("部门名称长度校验.");
		caseList.add(deptNametooLong);

		stop(1);
		DepartmentAddCaseBean merchantNoPass = SwiftPass.copy(baseBean)
				.setDepartName("AddDept_HJ_"+new SimpleDateFormat("yyMMddHHmmss").format(new Date()))
				.setBigMerchantCode(merchantId_nopass)
				.setBigMerchantName(getMerChantNameExamine(merchantId_nopass))
				.setMchNameOrIdItem(merchantId_nopass)
				.setMessage(XDepartmentAddHelper.NON_PASS_PARENT_BIG_MERCHANT_MSG);
		merchantNoPass.setCASE_NAME("大商户未审核校验.");
		caseList.add(merchantNoPass);

		DepartmentAddCaseBean noSave = SwiftPass.copy(baseBean).setIsSave("false")
				.setMessage(XDepartmentAddHelper.CLOSE_PAGE_MSG)
				.setBigMerchantCode(merchantId_nopass)
				.setBigMerchantName(getMerChantNameExamine(merchantId_nopass))
				.setMchNameOrIdItem(merchantId_nopass);
				 noSave.setCASE_NAME("正常关闭校验..");
		caseList.add(noSave);

		DepartmentAddCaseBean cancelSave = SwiftPass.copy(baseBean)
				.setIsConfirmSave("false")
				.setMessage(XDepartmentAddHelper.CLOSE_PAGE_MSG)
				.setBigMerchantCode(merchantId_nopass)
				.setBigMerchantName(getMerChantNameExamine(merchantId_nopass))
				.setMchNameOrIdItem(merchantId_nopass)
				.setMessage(XDepartmentAddHelper.CANCEL_SAVE_MSG);
		cancelSave.setCASE_NAME("正常取消校验..");
		caseList.add(cancelSave);
		// 遍历caseList，通过相关条件进行sql查询，并将预期结果数量set到对象execpted属性中
		// caseList.forEach(element ->
		// XDepartmentSearchHelper.queryExpectedResult(element));

		// 将返回的caseList转换成DepartmentSearchCaseBean二维数组
		return caseList.stream().map(element -> ArrayUtils.toArray(element)).toArray(DepartmentAddCaseBean[][]::new);
	}

//	private static String initNewDeptData() {
//		String deptName = "", deptID = "", sql = "", merchant_id = "";
//		merchant_id = getMerChantId(1);
//		SimpleDateFormat sd = new SimpleDateFormat("yyMMddHHmmss");
//		deptID = (sd.format(new Date()));
//		deptName = "randomDeptName_" + deptID;
//		sql = "insert into CMS_DEPT (DEPT_ID, DEPT_NAME, MERCHANT_ID, EXAMINE_STATUS, EXAMINE_TIME, "
//				+ " EXAMINE_STATUS_REMARK,ACTIVATE_STATUS, ACTIVATE_TIME, ACTIVATE_STATUS_REMARK, "
//				+ "PHYSICS_FLAG, DATA_SOURCE,Create_User,CREATE_EMP, CREATE_TIME, UPDATE_TIME)  ";
//		sql += ("values ('" + deptID + "', '" + deptName + "', '" + merchant_id
//				+ "', 1, systimestamp, '添加时系统自动审核通过', 1, systimestamp, '添加时系统自动激活成功', 1, 6, 1, 'superadmin', systimestamp, systimestamp)");
//
//		boolean execRes = DBUtil.executeUpdate(sql);
//		return execRes == true ? deptName : "";
//	}

	/**
	 * 根据examine_status审核状态获取商户ID. 0:未审核,1:已审核.
	 * 
	 * @param examine_status
	 * @return
	 */
	public static String getMerChantId(int examine_status) {
		String result = "";
		List<Map<String, Object>> rs = DBUtil.getResultToList(
				("SELECT * FROM (SELECT m.merchant_id FROM cms_merchant m where m.merchant_type =11 and m.examine_status =1 order by dbms_random.value) WHERE rownum =1"));
		if (rs != null && rs.size() > 0) {
			result = rs.get(0).get("merchant_id").toString();
			// System.out.println(rs.get(0).get("merchant_id"));
		} else {
			result = "151580000003"; // 如果当前数据库为查询到数据,给予一个大商默认值
		}
		return result;
	}

	public static String getMerChantNameExamine(String getMerChantId) {
		String result = "";
		List<Map<String, Object>> rs = DBUtil
				.getResultToList("select t.merchant_name from CMS_MERCHANT t where t.merchant_id =?", getMerChantId);
		if (rs != null && rs.size() > 0) {
			result = rs.get(0).get("merchant_name").toString();
			// System.out.println(rs.get(0).get("merchant_id"));
		} else {
			result = "大商100"; // 如果当前数据库为查询到数据,给予一个大商默认值
		}
		return result;
	}
	public static void main(String[] args) {
		for(DepartmentAddCaseBean[] cb : data()){
			System.out.println(cb);
		}
//		DBUtil.closeDBResource();
	}
	public static void stop(double second){
		try {
			Thread.sleep((long) (second * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static class XDepartmentAddHelper {
		public static final String DEPART_NAME_TOO_LONG = "部门名称长度在1~64位之间！";
		public static final String NON_PASS_PARENT_BIG_MERCHANT_MSG = "请先审核所属大商户";
		public static final String CLOSE_PAGE_MSG = "正常关闭";
		public static final String CANCEL_SAVE_MSG = "正常取消";

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