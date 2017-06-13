package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-16
 */
public class DepartmentEditCaseBean extends CaseBean {
	private static final long serialVersionUID = 6865227277324407137L;
	@CaseBeanField(desc = "用例名称", index = 0)
	private String CASE_NAME;
	@CaseBeanField(desc = "部门名称", index = 1)
	private String departName;
	@CaseBeanField(desc = "是否选择大商户", index = 2)
	private String isSelectBigMerchant;
	@CaseBeanField(desc = "大商户名称", index = 3)
	private String bigMerchantName;
	@CaseBeanField(desc = "大商户编码", index = 3)
	private String bigMerchantCode;
	@CaseBeanField(desc = "是否确认选择所选商户", index = 3)
	private String isConfirmSelectMerChant;
	@CaseBeanField(desc = "待新增部门名称", index = 4)
	private String newDepartName;
	@CaseBeanField(desc = "是否选择父部门", index = 4)
	private String isSelectParentDepartment;
	@CaseBeanField(desc = "是否编辑", index = 5)
	private String isEdit;
	@CaseBeanField(desc = "是否确认编辑", index = 6)
	private String isConfirmEdit;
	@CaseBeanField(desc = "通用消息", index = 7)
	private String message;
	@CaseBeanField(desc = "通用消息", index = 8)
	private String TEXT;

	
	
	public String getIsEdit() {
		return isEdit;
	}

	public DepartmentEditCaseBean setIsEdit(String isEdit) {
		this.isEdit = isEdit;
		return this;
	}

	public String getIsConfirmEdit() {
		return isConfirmEdit;
	}

	public DepartmentEditCaseBean setIsConfirmEdit(String isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public DepartmentEditCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getTEXT() {
		return TEXT;
	}

	public DepartmentEditCaseBean setTEXT(String tEXT) {
		this.TEXT = tEXT;
		return this;
	}

	public DepartmentEditCaseBean() {
	}

	@Override
	public String getCASE_NAME() {
		return CASE_NAME;
	}

	@Override
	public void setCASE_NAME(String caseName) {
		CASE_NAME = caseName;
	}

	public String getDepartName() {
		return departName;
	}

	public DepartmentEditCaseBean setDepartName(String departName) {
		this.departName = departName;
		return this;
	}

	public String getIsSelectBigMerchant() {
		return isSelectBigMerchant;
	}

	public DepartmentEditCaseBean setIsSelectBigMerchant(String isSelectBigMerchant) {
		this.isSelectBigMerchant = isSelectBigMerchant;
		return this;
	}

	public String getBigMerchantName() {
		return bigMerchantName;
	}

	public DepartmentEditCaseBean setBigMerchantName(String bigMerchantName) {
		this.bigMerchantName = bigMerchantName;
		return this;
	}

	public String getBigMerchantCode() {
		return bigMerchantCode;
	}

	public DepartmentEditCaseBean setBigMerchantCode(String bigMerchantCode) {
		this.bigMerchantCode = bigMerchantCode;
		return this;
	}

	public String getIsConfirmSelectMerChant() {
		return isConfirmSelectMerChant;
	}

	public DepartmentEditCaseBean setIsConfirmSelectMerChant(String isConfirmSelectMerChant) {
		this.isConfirmSelectMerChant = isConfirmSelectMerChant;
		return this;
	}

	public String getNewDepartName() {
		return newDepartName;
	}

	public DepartmentEditCaseBean setNewDepartName(String newDepartName) {
		this.newDepartName = newDepartName;
		return this;
	}

	public String getIsSelectParentDepartment() {
		return isSelectParentDepartment;
	}

	public DepartmentEditCaseBean setIsSelectParentDepartment(String isSelectParentDepartment) {
		this.isSelectParentDepartment = isSelectParentDepartment;
		return this;
	}
}
