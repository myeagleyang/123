package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-31
 */
public class MerchantDetailCaseBean extends CaseBean {
	private static final long serialVersionUID = 2304419801495449649L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="通用消息",index=0) private String message;
	@CaseBeanField(desc="页面信息",index=0) private String TEXT;
	
	public MerchantDetailCaseBean(){}

	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}

	public String getMessage() {
		return message;
	}

	public MerchantDetailCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getTEXT() {
		return TEXT;
	}

	public MerchantDetailCaseBean setTEXT(String tEXT) {
		TEXT = tEXT;
		return this;
	}
	
	
}
