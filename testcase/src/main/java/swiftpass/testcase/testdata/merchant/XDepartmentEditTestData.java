package swiftpass.testcase.testdata.merchant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;

import irsy.utils.dboperations.ChannelDBOperations;
import irsy.utils.dboperations.DeptDBOperations;
import irsy.utils.dboperations.MerchantDBOperations;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.testcase.casebeans.DepartmentEditCaseBean;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.DepartService;
import swiftpass.utils.services.MerchantAAAService;
import swiftpass.utils.services.MerchantService;

public class XDepartmentEditTestData {
	static String deptName = "";
	private static final String NULL_DEPART_NAME_MSG = "请输入部门名称", BEYOND_NAME_LENGTH_MSG = "部门名称长度在1~64位之间！",
			NO_SELECT_EDTI_MSG = "请选择要编辑的记录!", CLOSE_PAGE_MSG = "正常关闭", CANCEL_EDIT_MSG = "正常取消";
	static DepartmentEditCaseBean CaseTemplate = new DepartmentEditCaseBean().setIsEdit("true").setIsConfirmEdit("true");
	public static DepartmentEditCaseBean[][] data() {
		
		List<DepartmentEditCaseBean> caseList = new ArrayList<>();
		HashMap<Integer, HashMap<String, String>> departs = getEditableDepart();
		HashMap<String, String> depart1 = departs.get(1);
		HashMap<String, String> depart2 = departs.get(2);
		HashMap<String, String> depart3 = departs.get(3);
		//1.成功编辑
		DepartmentEditCaseBean successEditCase = SwiftPass.copy(CaseTemplate)
		.setTEXT(String.join("-", depart1.get("DEPT_ID"), depart1.get("DEPT_NAME")))
		.setDepartName(RandomStringUtils.randomAlphanumeric(32));
		successEditCase.setCASE_NAME("成功编辑一条记录");
		caseList.add(successEditCase);
		
		//2.部门名非法用例（空、非法长度）
		DepartmentEditCaseBean nullDepartNameCaseMap = SwiftPass.copy(CaseTemplate)
		.setTEXT(String.join("-", depart2.get("DEPT_ID"), depart2.get("DEPT_NAME")))
		.setDepartName("")
		.setMessage(NULL_DEPART_NAME_MSG);//部门名称为空
		nullDepartNameCaseMap.setCASE_NAME("部门为空校验..");
		caseList.add(nullDepartNameCaseMap);
		
		
		DepartmentEditCaseBean beyondLengthCase = SwiftPass.copy(CaseTemplate)
		.setTEXT(String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")))
		.setDepartName(RandomStringUtils.randomAlphanumeric(65))//部门名称长度在1~64位之间！
		.setMessage(BEYOND_NAME_LENGTH_MSG);
		beyondLengthCase.setCASE_NAME("部门名称过长校验..");
		caseList.add(beyondLengthCase);
		
		//3.未选中部门编辑
		DepartmentEditCaseBean noSelectEditCase = SwiftPass.copy(CaseTemplate)
		.setTEXT("$$$$$$$$$$$$-$$$$$$$$$$$$")
		.setMessage(NO_SELECT_EDTI_MSG);//请选择要编辑的记录!
		noSelectEditCase.setCASE_NAME("未选择记录校验..");
		caseList.add(noSelectEditCase);
		
		//4.关闭或取消编辑
		DepartmentEditCaseBean closePageCase =SwiftPass.copy(CaseTemplate)
		.setTEXT(String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")))
		.setDepartName(RandomStringUtils.randomAlphanumeric(24))
		.setIsEdit("false")
		.setMessage(CLOSE_PAGE_MSG);//正常关闭
		closePageCase.setCASE_NAME("正常关闭校验..");
		caseList.add(closePageCase);
		
		DepartmentEditCaseBean cancelPageCase =SwiftPass.copy(CaseTemplate)
		.setTEXT(String.join("-", depart3.get("DEPT_ID"), depart3.get("DEPT_NAME")))
		.setDepartName(RandomStringUtils.randomAlphanumeric(24))
		.setIsEdit("true")
		.setIsConfirmEdit("false")
		.setMessage(CANCEL_EDIT_MSG);//正常取消
		cancelPageCase.setCASE_NAME("正常取消校验..");
		caseList.add(closePageCase);
		// 遍历caseList，通过相关条件进行sql查询，并将预期结果数量set到对象execpted属性中
		//caseList.forEach(element -> XDepartmentSearchHelper.queryExpectedResult(element));
		// 将返回的caseList转换成DepartmentSearchCaseBean二维数组
		return caseList.stream().map(element -> ArrayUtils.toArray(element)).toArray(DepartmentEditCaseBean[][]::new);
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

	private static String initNewDeptData() {
		String deptName = "", deptID = "", sql = "", merchant_id = "";
		merchant_id = getMerChantId(1);
		SimpleDateFormat sd = new SimpleDateFormat("yyMMddHHmmss");
		deptID = (sd.format(new Date()));
		deptName = "randomDeptName_" + deptID;
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
		// System.out.println(initNewDeptData());
		// HashMap<Integer, HashMap<String, String>> rs =
		HashMap<Integer, HashMap<String, String>> rs = DBUtil.getQueryResultMap(
				"SELECT * FROM (SELECT m.merchant_id FROM cms_merchant m order by dbms_random.value) WHERE rownum =1");
		System.out.println(rs);
		// System.out.println(new SimpleDateFormat("yyMMddHHmmss").format(new
		// Date()));

	}
	private static HashMap<Integer, HashMap<String, String>> getEditableDepart(){
		int expectedCount = 3;
		HashMap<Integer, HashMap<String, String>> departs = DeptDBOperations.allDeparts();
		if(departs.size() < expectedCount){
			HashMap<String, String> unique = ChannelDBOperations.acceptOrgUniqueChannel();
			String uniqueId = unique.get("CHANNEL_ID");
			if(!unique.get("EXAMINE_STATUS").equals(ExamineStatus.PASS))
				ChannelAAAService.aaaChannel(uniqueId, ExamineStatus.PASS, ActivateStatus.PASS);
			HashMap<String, String> bigParent = MerchantService.addBigMerchantAttachChannel(uniqueId);
			String bigParentId = bigParent.get("MERCHANT_ID");
			MerchantAAAService.AAAMerchant(ExamineStatus.PASS, ActivateStatus.PASS, bigParentId);
			for(int i = 0; i < expectedCount - departs.size(); i++)
				DepartService.addDepartWithoutParentDept(bigParentId);
			departs = DeptDBOperations.allDeparts();
		}
		return departs;
	}
}