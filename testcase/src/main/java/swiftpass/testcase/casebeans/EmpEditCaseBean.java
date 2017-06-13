package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class EmpEditCaseBean extends CaseBean {
	private static final long serialVersionUID = 1L;
	@CaseBeanField(desc="用例名称",index=0) private String  CASE_NAME;
	@CaseBeanField(desc="通用消息",index=1) private String  message;
	@CaseBeanField(desc="",index=2) private String  empId;
	@CaseBeanField(desc="",index=3) private String  empName;
	@CaseBeanField(desc="性别",index=4) private String  sex;
	@CaseBeanField(desc="部门名称",index=5) private String  departName;
	@CaseBeanField(desc="地址",index=6) private String  position;
	@CaseBeanField(desc="手机号码",index=7) private String  phone;
	@CaseBeanField(desc="邮箱",index=8) private String  email;
	@CaseBeanField(desc="身份证号码",index=9) private String  idCode;
	@CaseBeanField(desc="是否可用",index=10) private String  isEnable;
	@CaseBeanField(desc="备注",index=11) private String  remark;
	@CaseBeanField(desc="",index=12) private String  TEXT;
	@CaseBeanField(desc="是否编辑",index=13) private String  isEdit;
	@CaseBeanField(desc="是否确认修改",index=14) private String  isConfirmEdit;
	
	public EmpEditCaseBean (){}

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

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTEXT() {
		return TEXT;
	}

	public void setTEXT(String tEXT) {
		TEXT = tEXT;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsConfirmEdit() {
		return isConfirmEdit;
	}

	public void setIsConfirmEdit(String isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
	}
	
	
}
