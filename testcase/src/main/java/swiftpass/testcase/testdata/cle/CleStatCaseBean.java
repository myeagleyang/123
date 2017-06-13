package swiftpass.testcase.testdata.cle;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class CleStatCaseBean extends CaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8116337134403193043L;
	
	@CaseBeanField(desc = "用例名称", index = 0)
	private String CASE_NAME;

	@CaseBeanField(desc = "清分起始时间", index = 1)
	private String cleBeginTime;
	
	@CaseBeanField(desc = "清分结束时间", index = 2)
	private String cleEndTime;
	
	@CaseBeanField(desc = "清分状态", index = 3)
	private String cleStatus;
	
	@CaseBeanField(desc = "清分类型", index = 4)
	private String cleType;
	
	@CaseBeanField(desc = "是否选择支付类型", index = 5)
	private String isSelectPayType;
	
	@CaseBeanField(desc = "所按属性搜索支付类型", index = 5)
	private String ptNameOrIdItem;
	
	@CaseBeanField(desc = "支付类型-名称", index = 5)
	private String payTypeName;
	
	@CaseBeanField(desc = "支付类型-编号", index = 5)
	private String payTypeId;
	
	@CaseBeanField(desc = "是否确认选择支付类型", index = 5)
	private String isConfirmSelectPayType;
	
	@CaseBeanField(desc = "是否选择渠道", index = 6)
	private String isSelectChannel;
	
	@CaseBeanField(desc = "所按属性选择渠道", index = 6)
	private String chNameOrIdItem;
	
	@CaseBeanField(desc = "渠道——名称", index = 6)
	private String channelName;
	
	@CaseBeanField(desc = "渠道-编号", index = 6)
	private String channelId;
	
	@CaseBeanField(desc = "是否确认选择渠道", index = 6)
	private String isConfirmSelectChannel;
	
	@CaseBeanField(desc = "仅查询渠道", index = 7)
	private String isOnlyQueryChannel;
	
	@CaseBeanField(desc = "是否选择商户", index = 8)
	private String isSelectMerchant;
	
	@CaseBeanField(desc = "所按属性选择商户", index = 8)
	private String mchNameOrIdItem;
	
	@CaseBeanField(desc = "商户-名称", index = 8)
	private String merchantName;
	
	@CaseBeanField(desc = "商户-编号", index = 8)
	private String merchantId;
	
	@CaseBeanField(desc = "收款账户", index = 9)
	private String incomeAccount;
	
	@CaseBeanField(desc = "流水号", index = 10)
	private String serialNO;
	
	@CaseBeanField(desc = "失败原因", index = 11)
	private String failReason;
	
	@CaseBeanField(desc = "通用消息字段", index = 12)
	private String message;
		
	public CleStatCaseBean(){}
	
	@Override
	public String getCASE_NAME() {
		return CASE_NAME;
	}

	@Override
	public void setCASE_NAME(String caseName) {
		CASE_NAME = caseName;
	}
	
	public String getCleBeginTime() {
		return cleBeginTime;
	}

	public void setCleBeginTime(String cleBeginTime) {
		this.cleBeginTime = cleBeginTime;
	}

	public String getCleEndTime() {
		return cleEndTime;
	}

	public void setCleEndTime(String cleEndTime) {
		this.cleEndTime = cleEndTime;
	}

	public String getCleStatus() {
		return cleStatus;
	}

	public void setCleStatus(String cleStatus) {
		this.cleStatus = cleStatus;
	}

	public String getCleType() {
		return cleType;
	}

	public void setCleType(String cleType) {
		this.cleType = cleType;
	}

	public String getIsSelectPayType() {
		return isSelectPayType;
	}

	public void setIsSelectPayType(String isSelectPayType) {
		this.isSelectPayType = isSelectPayType;
	}

	public String getPtNameOrIdItem() {
		return ptNameOrIdItem;
	}

	public void setPtNameOrIdItem(String ptNameOrIdItem) {
		this.ptNameOrIdItem = ptNameOrIdItem;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getPayTypeId() {
		return payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	public String getIsConfirmSelectPayType() {
		return isConfirmSelectPayType;
	}

	public void setIsConfirmSelectPayType(String isConfirmSelectPayType) {
		this.isConfirmSelectPayType = isConfirmSelectPayType;
	}

	public String getIsSelectChannel() {
		return isSelectChannel;
	}

	public void setIsSelectChannel(String isSelectChannel) {
		this.isSelectChannel = isSelectChannel;
	}

	public String getChNameOrIdItem() {
		return chNameOrIdItem;
	}

	public void setChNameOrIdItem(String chNameOrIdItem) {
		this.chNameOrIdItem = chNameOrIdItem;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIsConfirmSelectChannel() {
		return isConfirmSelectChannel;
	}

	public void setIsConfirmSelectChannel(String isConfirmSelectChannel) {
		this.isConfirmSelectChannel = isConfirmSelectChannel;
	}

	public String getIsOnlyQueryChannel() {
		return isOnlyQueryChannel;
	}

	public void setIsOnlyQueryChannel(String isOnlyQueryChannel) {
		this.isOnlyQueryChannel = isOnlyQueryChannel;
	}

	public String getIsSelectMerchant() {
		return isSelectMerchant;
	}

	public void setIsSelectMerchant(String isSelectMerchant) {
		this.isSelectMerchant = isSelectMerchant;
	}

	public String getMchNameOrIdItem() {
		return mchNameOrIdItem;
	}

	public void setMchNameOrIdItem(String mchNameOrIdItem) {
		this.mchNameOrIdItem = mchNameOrIdItem;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getIncomeAccount() {
		return incomeAccount;
	}

	public void setIncomeAccount(String incomeAccount) {
		this.incomeAccount = incomeAccount;
	}

	public String getSerialNO() {
		return serialNO;
	}

	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
