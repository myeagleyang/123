package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-30
 */
public class MerchantPCActiveCaseBean extends CaseBean {
	private static final long serialVersionUID = 4665880466608199799L;
	@CaseBeanField(desc= "审核状态",index=0) private String preMchExamineStatus;
	@CaseBeanField(desc= "激活状态",index=1) private String preMchActiveStatus;
	@CaseBeanField(desc= "激活人",index=2) private String activeOperate;
	@CaseBeanField(desc= "商家激活状态",index=3) private String merchantPCActivateStatus;
	@CaseBeanField(desc= "是否确认选择首行数据",index=4) private String isClickFirstRowMerchant;
	@CaseBeanField(desc= "是否点击首行数据",index=5) private String isClickFirstRowPT;
	@CaseBeanField(desc= "是否确认激活",index=6) private String isConfirmActiveOperate;
	@CaseBeanField(desc= "支付类型",index=7) private String payTypeName;
	@CaseBeanField(desc= "错误信息",index=8) private String errorMsg;
	@CaseBeanField(desc= "用例名称",index=9) private String CASE_NAME;
	public MerchantPCActiveCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getPreMchExamineStatus() {
		return preMchExamineStatus;
	}
	public MerchantPCActiveCaseBean setPreMchExamineStatus(String preMchExamineStatus) {
		this.preMchExamineStatus = preMchExamineStatus;
		return this;
	}
	public String getPreMchActiveStatus() {
		return preMchActiveStatus;
	}
	public MerchantPCActiveCaseBean setPreMchActiveStatus(String preMchActiveStatus) {
		this.preMchActiveStatus = preMchActiveStatus;
		return this;
	}
	public String getActiveOperate() {
		return activeOperate;
	}
	public MerchantPCActiveCaseBean setActiveOperate(String activeOperate) {
		this.activeOperate = activeOperate;
		return this;
	}
	public String getMerchantPCActivateStatus() {
		return merchantPCActivateStatus;
	}
	public MerchantPCActiveCaseBean setMerchantPCActivateStatus(String merchantPCActivateStatus) {
		this.merchantPCActivateStatus = merchantPCActivateStatus;
		return this;
	}
	public String getIsClickFirstRowMerchant() {
		return isClickFirstRowMerchant;
	}
	public MerchantPCActiveCaseBean setIsClickFirstRowMerchant(String isClickFirstRowMerchant) {
		this.isClickFirstRowMerchant = isClickFirstRowMerchant;
		return this;
	}
	public String getIsClickFirstRowPT() {
		return isClickFirstRowPT;
	}
	public MerchantPCActiveCaseBean setIsClickFirstRowPT(String isClickFirstRowPT) {
		this.isClickFirstRowPT = isClickFirstRowPT;
		return this;
	}
	public String getIsConfirmActiveOperate() {
		return isConfirmActiveOperate;
	}
	public MerchantPCActiveCaseBean setIsConfirmActiveOperate(String isConfirmActiveOperate) {
		this.isConfirmActiveOperate = isConfirmActiveOperate;
		return this;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public MerchantPCActiveCaseBean setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
		return this;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public MerchantPCActiveCaseBean setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}
}
