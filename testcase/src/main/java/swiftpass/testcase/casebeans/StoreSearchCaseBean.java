package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class StoreSearchCaseBean extends CaseBean {
	private static final long serialVersionUID = 1L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="大商户名称",index=0) private String bigMchName;
	@CaseBeanField(desc="大商户ID",index=0) private String bigMchId;
	@CaseBeanField(desc="部门名称",index=0) private String departName;
	@CaseBeanField(desc="部门ID",index=0) private String departId;
	@CaseBeanField(desc="门店民称",index=0) private String storeName;
	@CaseBeanField(desc="审核状态",index=0) private String examineStatus;
	@CaseBeanField(desc="激活状态",index=0) private String activateStatus;
	@CaseBeanField(desc="门店编号",index=0) private String storeId;
	@CaseBeanField(desc="期望值",index=0) private String expected;
	@CaseBeanField(desc="通用消息",index=0) private String message;
	
	public StoreSearchCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getBigMchName() {
		return bigMchName;
	}
	public void setBigMchName(String bigMchName) {
		this.bigMchName = bigMchName;
	}
	public String getBigMchId() {
		return bigMchId;
	}
	public void setBigMchId(String bigMchId) {
		this.bigMchId = bigMchId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getExamineStatus() {
		return examineStatus;
	}
	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}
	public String getActivateStatus() {
		return activateStatus;
	}
	public void setActivateStatus(String activateStatus) {
		this.activateStatus = activateStatus;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getExpected() {
		return expected;
	}
	public void setExpected(String expected) {
		this.expected = expected;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
