package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-31
 */
public class MerchantPCDetailCaseBean extends CaseBean {
	private static final long serialVersionUID = 970572758669916009L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="通用消息",index=1) private String errorMsg;
	@CaseBeanField(desc="是否选择首行商家数据",index=2) private String isClickFirstRowMerchant;
	@CaseBeanField(desc="是否选择首行是福类型",index=3) private String isClickFirstRowPT;
	
	public MerchantPCDetailCaseBean(){}

	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}

	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getIsClickFirstRowMerchant() {
		return isClickFirstRowMerchant;
	}

	public void setIsClickFirstRowMerchant(String isClickFirstRowMerchant) {
		this.isClickFirstRowMerchant = isClickFirstRowMerchant;
	}

	public String getIsClickFirstRowPT() {
		return isClickFirstRowPT;
	}

	public void setIsClickFirstRowPT(String isClickFirstRowPT) {
		this.isClickFirstRowPT = isClickFirstRowPT;
	}
	
}
