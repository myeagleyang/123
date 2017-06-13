package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class EmpSearchCaseBean extends CaseBean {
	private static final long serialVersionUID = 1L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="通用消息",index=1) private String message;
	@CaseBeanField(desc="",index=1) private String empName;
	@CaseBeanField(desc="是否选择",index=0) private String isSelectOrg;
	@CaseBeanField(desc="选择名称还是编号",index=0) private String idOrNameItem;
	@CaseBeanField(desc="名称或编号值",index=0) private String idOrName;
	@CaseBeanField(desc="是否确认选择",index=0) private String isConfirmSelectOrg;
	@CaseBeanField(desc="期望值",index=0) private String expectCount;
	@CaseBeanField(desc="开始时间",index=0) private String beginCT;
	@CaseBeanField(desc="结束时间",index=0) private String endCT;
	@CaseBeanField(desc="",index=0) private String empCode;
	@CaseBeanField(desc="手机号码",index=0) private String phone;
	@CaseBeanField(desc="",index=0) private String orgId;
	@CaseBeanField(desc="",index=0) private String orgName;
	
	public EmpSearchCaseBean(){}

	
	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsSelectOrg() {
		return isSelectOrg;
	}

	public void setIsSelectOrg(String isSelectOrg) {
		this.isSelectOrg = isSelectOrg;
	}

	public String getIdOrNameItem() {
		return idOrNameItem;
	}

	public void setIdOrNameItem(String idOrNameItem) {
		this.idOrNameItem = idOrNameItem;
	}

	public String getIdOrName() {
		return idOrName;
	}

	public void setIdOrName(String idOrName) {
		this.idOrName = idOrName;
	}

	public String getIsConfirmSelectOrg() {
		return isConfirmSelectOrg;
	}

	public void setIsConfirmSelectOrg(String isConfirmSelectOrg) {
		this.isConfirmSelectOrg = isConfirmSelectOrg;
	}

	public String getExpectCount() {
		return expectCount;
	}

	public void setExpectCount(String expectCount) {
		this.expectCount = expectCount;
	}

	public String getBeginCT() {
		return beginCT;
	}

	public void setBeginCT(String beginCT) {
		this.beginCT = beginCT;
	}

	public String getEndCT() {
		return endCT;
	}

	public void setEndCT(String endCT) {
		this.endCT = endCT;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
