package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-04-01
 */
public class StoreQRCodeCaseBean extends CaseBean {
	private static final long serialVersionUID = 3295591448830649342L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="是否点击首行数据",index=1) private String isClickFirstRowStore ="true";
	@CaseBeanField(desc="错误消息",index=2) private String errorMsg;
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getIsClickFirstRowStore() {
		return isClickFirstRowStore;
	}
	public void setIsClickFirstRowStore(String isClickFirstRowStore) {
		this.isClickFirstRowStore = isClickFirstRowStore;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
