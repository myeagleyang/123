package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-16
 */
public class DepartmentAddCaseBean extends CaseBean {
	private static final long serialVersionUID = -6044787870578688332L;
	@CaseBeanField(desc = "用例名称", index = 0)
	private String CASE_NAME;
	@CaseBeanField(desc = "部门名称", index = 1)
	private String departName;
	@CaseBeanField(desc = "是否选择大商户", index = 2)
	private String isSelectBigMerchant;
	@CaseBeanField(desc = "大商户名称", index = 3)
	private String bigMerchantName;
	@CaseBeanField(desc = "大商户编码", index = 4)
	private String bigMerchantCode;
	@CaseBeanField(desc = "选择是大商户名称还是编码", index = 5)
	private String mchNameOrIdItem;
	@CaseBeanField(desc = "选择是大商户名称还是编码", index = 6)
	private String merchantType;
	@CaseBeanField(desc = "是否确认选择所选商户", index = 7)
	private String isConfirmSelectMerChant;
	@CaseBeanField(desc = "待新增部门名称", index = 8)
	private String newDepartName;
	@CaseBeanField(desc = "是否选择父部门", index = 9)
	private String isSelectParentDepartment;
	@CaseBeanField(desc = "父部门", index = 10)
	private String parentDepartment;
	@CaseBeanField(desc = "是否保存", index = 11)
	private String isSave;
	@CaseBeanField(desc = "是否确认保存", index = 12)
	private String isConfirmSave;
	@CaseBeanField(desc = "通用消息", index = 13)
	private String message;

	
	
	public String getMerchantType() {
		return merchantType;
	}

	public DepartmentAddCaseBean setMerchantType(String merchantType) {
		this.merchantType = merchantType;
		return this;
	}

	public String getMchNameOrIdItem() {
		return mchNameOrIdItem;
	}

	public DepartmentAddCaseBean setMchNameOrIdItem(String mchNameOrIdItem) {
		this.mchNameOrIdItem = mchNameOrIdItem;
		return this;
	}

	public String getParentDepartment() {
		return parentDepartment;
	}

	public DepartmentAddCaseBean setParentDepartment(String parentDepartment) {
		this.parentDepartment = parentDepartment;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public DepartmentAddCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}

	public DepartmentAddCaseBean() {
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

	public DepartmentAddCaseBean setDepartName(String departName) {
		this.departName = departName;
		return this;
	}

	public String getIsSelectBigMerchant() {
		return isSelectBigMerchant;
	}

	public DepartmentAddCaseBean setIsSelectBigMerchant(String isSelectBigMerchant) {
		this.isSelectBigMerchant = isSelectBigMerchant;
		return this;
	}

	public String getBigMerchantName() {
		return bigMerchantName;
	}

	public DepartmentAddCaseBean setBigMerchantName(String bigMerchantName) {
		this.bigMerchantName = bigMerchantName;
		return this;
	}

	public String getBigMerchantCode() {
		return bigMerchantCode;
	}

	public DepartmentAddCaseBean setBigMerchantCode(String bigMerchantCode) {
		this.bigMerchantCode = bigMerchantCode;
		return this;
	}

	public String getIsConfirmSelectMerChant() {
		return isConfirmSelectMerChant;
	}

	public DepartmentAddCaseBean setIsConfirmSelectMerChant(String isConfirmSelectMerChant) {
		this.isConfirmSelectMerChant = isConfirmSelectMerChant;
		return this;
	}

	public String getNewDepartName() {
		return newDepartName;
	}

	public DepartmentAddCaseBean setNewDepartName(String newDepartName) {
		this.newDepartName = newDepartName;
		return this;
	}

	public String getIsSelectParentDepartment() {
		return isSelectParentDepartment;
	}

	public DepartmentAddCaseBean setIsSelectParentDepartment(String isSelectParentDepartment) {
		this.isSelectParentDepartment = isSelectParentDepartment;
		return this;
	}

	public String getIsSave() {
		return isSave;
	}

	public DepartmentAddCaseBean setIsSave(String isSave) {
		this.isSave = isSave;
		return this;
	}

	public String getIsConfirmSave() {
		return isConfirmSave;
	}

	public DepartmentAddCaseBean setIsConfirmSave(String isConfirmSave) {
		this.isConfirmSave = isConfirmSave;
		return this;
	}
}
