package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-31
 */
public class MerchantPCEditCaseBean extends CaseBean {
	private static final long serialVersionUID = 2538670065718503193L;
	@CaseBeanField(desc="费率占比",index=0) private String clrRate;
	@CaseBeanField(desc="最小费率",index=1) private String minLimit;
	@CaseBeanField(desc="最大费率",index=2) private String maxLimit;
	@CaseBeanField(desc="第三方商户",index=3) private String thirdMchId;
	@CaseBeanField(desc="商户ID",index=4) private String merchantId;
	@CaseBeanField(desc="是否点击首行商户",index=5) private String isClickFirstRowMerchant;
	@CaseBeanField(desc="是否点击首行支付类型",index=6) private String isClickFirstRowPT;
	@CaseBeanField(desc="支付类型",index=7) private String payTypeName;
	@CaseBeanField(desc="是否修改",index=8) private String isEdit;
	@CaseBeanField(desc="是否确认修改",index=9) private String isConfirmEdit;
	@CaseBeanField(desc="错误消息",index=10) private String errorMsg;
	@CaseBeanField(desc="用例名称",index=11) private String CASE_NAME;
	
	public MerchantPCEditCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getClrRate() {
		return clrRate;
	}
	public void setClrRate(String clrRate) {
		this.clrRate = clrRate;
	}
	public String getMinLimit() {
		return minLimit;
	}
	public void setMinLimit(String minLimit) {
		this.minLimit = minLimit;
	}
	public String getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(String maxLimit) {
		this.maxLimit = maxLimit;
	}
	public String getThirdMchId() {
		return thirdMchId;
	}
	public void setThirdMchId(String thirdMchId) {
		this.thirdMchId = thirdMchId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
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
