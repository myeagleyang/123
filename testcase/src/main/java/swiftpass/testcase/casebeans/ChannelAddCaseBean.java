package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author Xie_Liang_ji
 * 时间：2017-3-7
 */
public class ChannelAddCaseBean extends CaseBean{
	private static final long serialVersionUID = -442320604078717792L;
	
	@CaseBeanField(desc="用例标题", index=0) private String CASE_NAME;
	
	@CaseBeanField(desc="是否选择所属渠道", index=1) private String isSelectParentChannel;
	@CaseBeanField(desc="按渠道名称/编号选择所属渠道", index=1) private String parentNameIdItem;
	@CaseBeanField(desc="所属渠道名称", index=1) private String parentChannelName;
	@CaseBeanField(desc="所属渠道编号", index=1) private String parentChannelId;
	@CaseBeanField(desc="是否确认选择所属渠道", index=1) private String isConfirmSelectParentChannel;
	
	@CaseBeanField(desc="待新增渠道名称", index=2) private String newChannelName;
	
	@CaseBeanField(desc="省份", index=3) private String province;
	@CaseBeanField(desc="城市", index=4) private String city;
	
	@CaseBeanField(desc="地址", index=5) private String address;
	@CaseBeanField(desc="邮箱", index=6) private String email;
	
	@CaseBeanField(desc="负责人", index=7) private String principal;
	@CaseBeanField(desc="电话", index=8) private String tel;
	
	@CaseBeanField(desc="备注", index=9) private String remark;
	@CaseBeanField(desc="外部渠道号", index=10) private String thirdChannelId;
	
	@CaseBeanField(desc="是否选择开户银行", index=11) private String isSelectBank;
	@CaseBeanField(desc="开户银行名称", index=11) private String bankName;
	@CaseBeanField(desc="开户银行编号", index=11) private String bankId;
	@CaseBeanField(desc="是否确认选择开户银行", index=11) private String isConfirmSelectBank;
	
	@CaseBeanField(desc="银行卡号", index=12) private String bankAccount;
	
	@CaseBeanField(desc="开户名称", index=13) private String bankAccountName;
	@CaseBeanField(desc="账户类型", index=14) private String bankAccountType;
	
	@CaseBeanField(desc="开户省份", index=15) private String bankAccountProvince;
	@CaseBeanField(desc="开户城市", index=16) private String bankAccountCity;
	
	@CaseBeanField(desc="证件类型", index=17) private String idType;
	@CaseBeanField(desc="证件号码", index=18) private String idCode;
	
	@CaseBeanField(desc="开户支行", index=19) private String bankSubBranch;
	@CaseBeanField(desc="手机号码", index=20) private String bankAccountPhone;
	
	@CaseBeanField(desc="网点号（联行号）", index=21) private String siteNO;
	
	@CaseBeanField(desc="是否保存", index=22) private String isSave;
	@CaseBeanField(desc="是否确认保存", index=23) private String isConfirmSave;
	
	@CaseBeanField(desc="通用消息", index=24) private String message;
	@CaseBeanField(desc="新增内部/外部", index=25) private String newChannelType;
	@CaseBeanField(desc="是否选择行内账号", index=26) private String isSelectHNAcc;
	@CaseBeanField(desc="是否行内账号", index=27) private String yesOrNoHNAcc;

	public static void main(String...strings){
		System.out.println(new ChannelAddCaseBean());
	}
	
	public ChannelAddCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String caseName) {
		CASE_NAME = caseName;
	}

	public String getIsSelectParentChannel() {
		return isSelectParentChannel;
	}

	public ChannelAddCaseBean setIsSelectParentChannel(String isSelectParentChannel) {
		this.isSelectParentChannel = isSelectParentChannel;
		return this;
	}

	public String getParentNameIdItem() {
		return parentNameIdItem;
	}

	public ChannelAddCaseBean setParentNameIdItem(String parentNameIdItem) {
		this.parentNameIdItem = parentNameIdItem;
		return this;
	}

	public String getParentChannelName() {
		return parentChannelName;
	}

	public ChannelAddCaseBean setParentChannelName(String parentChannelName) {
		this.parentChannelName = parentChannelName;
		return this;
	}

	public String getParentChannelId() {
		return parentChannelId;
	}

	public ChannelAddCaseBean setParentChannelId(String parentChannelId) {
		this.parentChannelId = parentChannelId;
		return this;
	}

	public String getIsConfirmSelectParentChannel() {
		return isConfirmSelectParentChannel;
	}

	public ChannelAddCaseBean setIsConfirmSelectParentChannel(String isConfirmSelectParentChannel) {
		this.isConfirmSelectParentChannel = isConfirmSelectParentChannel;
		return this;
	}

	public String getNewChannelName() {
		return newChannelName;
	}

	public ChannelAddCaseBean setNewChannelName(String newChannelName) {
		this.newChannelName = newChannelName;
		return this;
	}

	public String getProvince() {
		return province;
	}

	public ChannelAddCaseBean setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getCity() {
		return city;
	}

	public ChannelAddCaseBean setCity(String city) {
		this.city = city;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public ChannelAddCaseBean setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public ChannelAddCaseBean setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPrincipal() {
		return principal;
	}

	public ChannelAddCaseBean setPrincipal(String principal) {
		this.principal = principal;
		return this;
	}

	public String getTel() {
		return tel;
	}

	public ChannelAddCaseBean setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public ChannelAddCaseBean setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getThirdChannelId() {
		return thirdChannelId;
	}

	public ChannelAddCaseBean setThirdChannelId(String thirdChannelId) {
		this.thirdChannelId = thirdChannelId;
		return this;
	}

	public String getIsSelectBank() {
		return isSelectBank;
	}

	public ChannelAddCaseBean setIsSelectBank(String isSelectBank) {
		this.isSelectBank = isSelectBank;
		return this;
	}

	public String getBankName() {
		return bankName;
	}

	public ChannelAddCaseBean setBankName(String bankName) {
		this.bankName = bankName;
		return this;
	}

	public String getBankId() {
		return bankId;
	}

	public ChannelAddCaseBean setBankId(String bankId) {
		this.bankId = bankId;
		return this;
	}

	public String getIsConfirmSelectBank() {
		return isConfirmSelectBank;
	}

	public ChannelAddCaseBean setIsConfirmSelectBank(String isConfirmSelectBank) {
		this.isConfirmSelectBank = isConfirmSelectBank;
		return this;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public ChannelAddCaseBean setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
		return this;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public ChannelAddCaseBean setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
		return this;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public ChannelAddCaseBean setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
		return this;
	}

	public String getBankAccountProvince() {
		return bankAccountProvince;
	}

	public ChannelAddCaseBean setBankAccountProvince(String bankAccountProvince) {
		this.bankAccountProvince = bankAccountProvince;
		return this;
	}

	public String getBankAccountCity() {
		return bankAccountCity;
	}

	public ChannelAddCaseBean setBankAccountCity(String bankAccountCity) {
		this.bankAccountCity = bankAccountCity;
		return this;
	}

	public String getIdType() {
		return idType;
	}

	public ChannelAddCaseBean setIdType(String idType) {
		this.idType = idType;
		return this;
	}

	public String getIdCode() {
		return idCode;
	}

	public ChannelAddCaseBean setIdCode(String idCode) {
		this.idCode = idCode;
		return this;
	}

	public String getBankSubBranch() {
		return bankSubBranch;
	}

	public ChannelAddCaseBean setBankSubBranch(String bankSubBranch) {
		this.bankSubBranch = bankSubBranch;
		return this;
	}

	public String getBankAccountPhone() {
		return bankAccountPhone;
	}

	public ChannelAddCaseBean setBankAccountPhone(String bankAccountPhone) {
		this.bankAccountPhone = bankAccountPhone;
		return this;
	}

	public String getSiteNO() {
		return siteNO;
	}

	public ChannelAddCaseBean setSiteNO(String siteNO) {
		this.siteNO = siteNO;
		return this;
	}
	
	public String getIsSave() {
		return isSave;
	}

	public ChannelAddCaseBean setIsSave(String isSave) {
		this.isSave = isSave;
		return this;
	}

	public String getIsConfirmSave() {
		return isConfirmSave;
	}

	public ChannelAddCaseBean setIsConfirmSave(String isConfirmSave) {
		this.isConfirmSave = isConfirmSave;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ChannelAddCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public String getNewChannelType(){
		return newChannelType;
	}
	
	public ChannelAddCaseBean setNewChannelType(String newChannelType){
		this.newChannelType = newChannelType;
		return this;
	}

	public String getIsSelectHNAcc() {
		return isSelectHNAcc;
	}

	public ChannelAddCaseBean setIsSelectHNAcc(String isSelectHNAcc) {
		this.isSelectHNAcc = isSelectHNAcc;
		return this;
	}

	public String getYesOrNoHNAcc() {
		return yesOrNoHNAcc;
	}

	public ChannelAddCaseBean setYesOrNoHNAcc(String yesOrNoHNAcc) {
		this.yesOrNoHNAcc = yesOrNoHNAcc;
		return this;
	}
	
	
}
