package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-15
 */
public class DepartmentSearchCaseBean extends CaseBean {
	private static final long serialVersionUID = 1018955173643765855L;
	@CaseBeanField(desc="用例名称", index =0) private String CASE_NAME;
	@CaseBeanField(desc="部门名称",index = 1) private String departName;
	@CaseBeanField(desc="是否选择大商户",index = 2) private String isSelectBigMerchant;
	@CaseBeanField(desc="选择大商户名称还是编码",index=2)private String merchantType;
	@CaseBeanField(desc="大商户名称",index = 3) private String bigMerchantName;
	@CaseBeanField(desc="大商户编码",index = 3) private String bigMerchantCode;
	@CaseBeanField(desc ="要选择的子查询数据",index = 3) private String mchNameOrId;
	@CaseBeanField(desc="是否确认选择", index= 4)private String isConfirmSelectMerchant;
	@CaseBeanField(desc="通用消息",index=4) private String message;
	@CaseBeanField(desc="预期查询结果数",index =5)private String expected;
	@CaseBeanField(desc="组合查询备注",index =6)private String multiQuery;
	
	
	
	public String getMerchantType() {
		return merchantType;
	}

	public DepartmentSearchCaseBean setMerchantType(String merchantType) {
		this.merchantType = merchantType;
		return this;
	}

	public String getIsConfirmSelectMerchant() {
		return isConfirmSelectMerchant;
	}

	public DepartmentSearchCaseBean setIsConfirmSelectMerchant(String isConfirmSelectMerchant) {
		this.isConfirmSelectMerchant = isConfirmSelectMerchant;
		return this;
	}

	public String getMchNameOrId() {
		return mchNameOrId;
	}

	public DepartmentSearchCaseBean setMchNameOrId(String mchNameOrId) {
		this.mchNameOrId = mchNameOrId;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExpected() {
		return expected;
	}

	public DepartmentSearchCaseBean setExpected(String expected) {
		this.expected = expected;
		return this;
	}

	public String getMultiQuery() {
		return multiQuery;
	}

	public DepartmentSearchCaseBean setMultiQuery(String multiQuery) {
		this.multiQuery = multiQuery;
		return this;
	}

	public DepartmentSearchCaseBean() {}
	
	@Override
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	@Override
	public void setCASE_NAME(String caseName) {
		CASE_NAME =caseName;
	}
	
	public String getDepartName() {
		return departName;
	}
	public DepartmentSearchCaseBean setDepartName(String departName) {
		this.departName = departName;
		return this;
	}
	public String getIsSelectBigMerchant() {
		return isSelectBigMerchant;
	}
	public DepartmentSearchCaseBean setIsSelectBigMerchant(String isSelectBigMerchant) {
		this.isSelectBigMerchant = isSelectBigMerchant;
		return this;
	}
	public String getBigMerchantName() {
		return bigMerchantName;
	}
	public DepartmentSearchCaseBean setBigMerchantName(String bigMerchantName) {
		this.bigMerchantName = bigMerchantName;
		return this;
	}
	public String getBigMerchantCode() {
		return bigMerchantCode;
	}
	public DepartmentSearchCaseBean setBigMerchantCode(String bigMerchantCode) {
		this.bigMerchantCode = bigMerchantCode;
		return this;
	}
}
