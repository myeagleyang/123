package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class EmpAddCaseBean extends CaseBean {
	@CaseBeanField(desc="用例名称",index =0) private String CASE_NAME;
	@CaseBeanField(desc="用例名称",index =0) private String empId;
	@CaseBeanField(desc="",index =0) private String empName;
	@CaseBeanField(desc="登录名",index =0) private String loginAccount;
	@CaseBeanField(desc="性别",index =0) private String sex;
	@CaseBeanField(desc="父渠道名称",index =0) private String parentChannelName;
	@CaseBeanField(desc="部门名称",index =0) private String departName;
	@CaseBeanField(desc="用父渠道Id",index =0) private String parentChannelId;
	@CaseBeanField(desc="地址",index =0) private String position;
	@CaseBeanField(desc="手机",index =0) private String phone;
	@CaseBeanField(desc="邮箱",index =0) private String email;
	@CaseBeanField(desc="id编码",index =0) private String idCode;
	@CaseBeanField(desc="是否可用",index =0) private String isEnable;
	@CaseBeanField(desc="备注",index =0) private String remark;
	@CaseBeanField(desc="是否选择父渠道",index =0) private String isSelectParentChannel;
	@CaseBeanField(desc="选择name还是Id",index =0) private String nameOrIdItem;
	@CaseBeanField(desc="选择具体的name或Id值",index =0) private String nameOrId;
	@CaseBeanField(desc="是否确认选择父渠道",index =0) private String isConfirmSelectParentChannel;
	@CaseBeanField(desc="是否保存",index =0) private String isSave;
	@CaseBeanField(desc="是否确认保存",index =0) private String isConfirmSave;
	@CaseBeanField(desc="通用消息",index =0) private String message;
	
	
	public	 EmpAddCaseBean(){}


	public String getCASE_NAME() {
		return CASE_NAME;
	}


	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
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


	public String getLoginAccount() {
		return loginAccount;
	}


	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getParentChannelName() {
		return parentChannelName;
	}


	public void setParentChannelName(String parentChannelName) {
		this.parentChannelName = parentChannelName;
	}


	public String getDepartName() {
		return departName;
	}


	public void setDepartName(String departName) {
		this.departName = departName;
	}


	public String getParentChannelId() {
		return parentChannelId;
	}


	public void setParentChannelId(String parentChannelId) {
		this.parentChannelId = parentChannelId;
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


	public String getIsSelectParentChannel() {
		return isSelectParentChannel;
	}


	public void setIsSelectParentChannel(String isSelectParentChannel) {
		this.isSelectParentChannel = isSelectParentChannel;
	}


	public String getNameOrIdItem() {
		return nameOrIdItem;
	}


	public void setNameOrIdItem(String nameOrIdItem) {
		this.nameOrIdItem = nameOrIdItem;
	}


	public String getNameOrId() {
		return nameOrId;
	}


	public void setNameOrId(String nameOrId) {
		this.nameOrId = nameOrId;
	}


	public String getIsConfirmSelectParentChannel() {
		return isConfirmSelectParentChannel;
	}


	public void setIsConfirmSelectParentChannel(String isConfirmSelectParentChannel) {
		this.isConfirmSelectParentChannel = isConfirmSelectParentChannel;
	}


	public String getIsSave() {
		return isSave;
	}


	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}


	public String getIsConfirmSave() {
		return isConfirmSave;
	}


	public void setIsConfirmSave(String isConfirmSave) {
		this.isConfirmSave = isConfirmSave;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
