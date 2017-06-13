package swiftpass.testcase.casebeans;

import org.apache.commons.lang3.RandomStringUtils;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie 
 * date 2017-04-01
 *
 */
public class StoreEditCaseBean extends CaseBean {
	private static final long serialVersionUID = 4376369080601734901L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="门店名称",index=1) private String storeName = RandomStringUtils.randomAlphanumeric(7) + "门店";
	@CaseBeanField(desc="门店名称",index=1) private String storeId;
	@CaseBeanField(desc="门店名称",index=1) private String isClickFirstRowStore ="true";
	@CaseBeanField(desc="门店名称",index=1) private String isEdit ="true";
	@CaseBeanField(desc="门店名称",index=1) private String isConfirmEdit ="true";
	@CaseBeanField(desc="门店名称",index=1) private String errorMsg;
	
	public StoreEditCaseBean (){}

	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getIsClickFirstRowStore() {
		return isClickFirstRowStore;
	}

	public void setIsClickFirstRowStore(String isClickFirstRowStore) {
		this.isClickFirstRowStore = isClickFirstRowStore;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
