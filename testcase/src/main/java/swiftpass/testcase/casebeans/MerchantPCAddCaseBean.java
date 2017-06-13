package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-31
 */
public class MerchantPCAddCaseBean extends CaseBean {
	private static final long serialVersionUID = 4967687700913116778L;
	@CaseBeanField(desc="用例名称",index=0) private String   CASE_NAME;
	@CaseBeanField(desc="支付中心名称",index=0) private String   payCenterName;
	@CaseBeanField(desc="支付类型名称",index=1) private String   payTypeName;
	@CaseBeanField(desc="费率占比",index=2) private String   clrRate;
	@CaseBeanField(desc="上级费率占比",index=3) private String   parentClrRate;
	@CaseBeanField(desc="最小费率",index=4) private String   minLimit;
	@CaseBeanField(desc="最大费率",index=5) private String   maxLimit;
	@CaseBeanField(desc="已存在银行编码",index=6) private String   existBankCode;
	@CaseBeanField(desc="银行名称",index=7) private String   bankName;
	@CaseBeanField(desc="银行账号",index=8) private String   bankAccount;
	@CaseBeanField(desc="银行所有者",index=9) private String   bankAccountOwner;
	@CaseBeanField(desc="银行帐号类型",index=10) private String   bankAccountType;
	@CaseBeanField(desc="子银行编码",index=11) private String   subBankCode;
	@CaseBeanField(desc="银行ID",index=12) private String   bankId;
	@CaseBeanField(desc="支付类型ID",index=13) private String   payTypeId;
	@CaseBeanField(desc="支付中心ID",index=14) private String   payCenterId;
	@CaseBeanField(desc="是否点击首行商家数据",index=15) private String   isClickFirstRowMerchant;
	@CaseBeanField(desc="是否选择支付银行",index=16) private String   isSelectPCName;
	@CaseBeanField(desc="是否选择是否类型",index=17) private String   isSelectPT;
	@CaseBeanField(desc="是否确认选择支付银行",index=18) private String   isConfirmSelectPC;
	@CaseBeanField(desc="是否确认选择支付类型",index=19) private String   isConfirmSelectPT;
	@CaseBeanField(desc="是否为新账号",index=20) private String   isSetNewClearAccount;
	@CaseBeanField(desc="是否确认选择已存在银行",index=21) private String   isConfirmSelectExistBank;
	@CaseBeanField(desc="是否选择网点",index=22) private String   isSelectBank;
	@CaseBeanField(desc="是否确认选择网点",index=23) private String   isConfirmSelectBank;
	@CaseBeanField(desc="商户ID",index=24) private String   merchantId;
	@CaseBeanField(desc="是否选择父支付商家",index=25) private String   isParentChnSetPC;
	@CaseBeanField(desc="是否保存",index=26) private String   isSave;
	@CaseBeanField(desc="是否确认保存",index=27) private String   isConfirmSave;
	@CaseBeanField(desc="错误消息",index=28) private String   errorMsg;
	
	public MerchantPCAddCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getPayCenterName() {
		return payCenterName;
	}
	public void setPayCenterName(String payCenterName) {
		this.payCenterName = payCenterName;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getClrRate() {
		return clrRate;
	}
	public void setClrRate(String clrRate) {
		this.clrRate = clrRate;
	}
	public String getParentClrRate() {
		return parentClrRate;
	}
	public void setParentClrRate(String parentClrRate) {
		this.parentClrRate = parentClrRate;
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
	public String getExistBankCode() {
		return existBankCode;
	}
	public void setExistBankCode(String existBankCode) {
		this.existBankCode = existBankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankAccountOwner() {
		return bankAccountOwner;
	}
	public void setBankAccountOwner(String bankAccountOwner) {
		this.bankAccountOwner = bankAccountOwner;
	}
	public String getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	public String getSubBankCode() {
		return subBankCode;
	}
	public void setSubBankCode(String subBankCode) {
		this.subBankCode = subBankCode;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getPayCenterId() {
		return payCenterId;
	}
	public void setPayCenterId(String payCenterId) {
		this.payCenterId = payCenterId;
	}
	public String getIsClickFirstRowMerchant() {
		return isClickFirstRowMerchant;
	}
	public void setIsClickFirstRowMerchant(String isClickFirstRowMerchant) {
		this.isClickFirstRowMerchant = isClickFirstRowMerchant;
	}
	public String getIsSelectPCName() {
		return isSelectPCName;
	}
	public void setIsSelectPCName(String isSelectPCName) {
		this.isSelectPCName = isSelectPCName;
	}
	public String getIsSelectPT() {
		return isSelectPT;
	}
	public void setIsSelectPT(String isSelectPT) {
		this.isSelectPT = isSelectPT;
	}
	public String getIsConfirmSelectPC() {
		return isConfirmSelectPC;
	}
	public void setIsConfirmSelectPC(String isConfirmSelectPC) {
		this.isConfirmSelectPC = isConfirmSelectPC;
	}
	public String getIsConfirmSelectPT() {
		return isConfirmSelectPT;
	}
	public void setIsConfirmSelectPT(String isConfirmSelectPT) {
		this.isConfirmSelectPT = isConfirmSelectPT;
	}
	public String getIsSetNewClearAccount() {
		return isSetNewClearAccount;
	}
	public void setIsSetNewClearAccount(String isSetNewClearAccount) {
		this.isSetNewClearAccount = isSetNewClearAccount;
	}
	public String getIsConfirmSelectExistBank() {
		return isConfirmSelectExistBank;
	}
	public void setIsConfirmSelectExistBank(String isConfirmSelectExistBank) {
		this.isConfirmSelectExistBank = isConfirmSelectExistBank;
	}
	public String getIsSelectBank() {
		return isSelectBank;
	}
	public void setIsSelectBank(String isSelectBank) {
		this.isSelectBank = isSelectBank;
	}
	public String getIsConfirmSelectBank() {
		return isConfirmSelectBank;
	}
	public void setIsConfirmSelectBank(String isConfirmSelectBank) {
		this.isConfirmSelectBank = isConfirmSelectBank;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getIsParentChnSetPC() {
		return isParentChnSetPC;
	}
	public void setIsParentChnSetPC(String isParentChnSetPC) {
		this.isParentChnSetPC = isParentChnSetPC;
	}
	public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	public String getIsConfirmSave() {
		return isConfirmSave;
	}
	public void setIsConfirmSave(String isConfirmSave) {
		this.isConfirmSave = isConfirmSave;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
