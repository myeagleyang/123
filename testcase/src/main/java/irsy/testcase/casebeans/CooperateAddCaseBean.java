package irsy.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import irsy.testcase.annotations.CaseBeanField;

/**
 * 
 * @author simon
 * date 2017-03-27
 */
public class CooperateAddCaseBean extends CaseBean {
	private static final long serialVersionUID = 7346838228140287494L;
	@CaseBeanField(desc = "用例名称", index = 0)                private String CASE_NAME;
	@CaseBeanField(desc = "新增大商户or普通商户", index = 0)		private String big_or_normal;
	@CaseBeanField(desc = "所属渠道", index = 1)					private String chnNameOrId;
	@CaseBeanField(desc = "渠道选择", index = 1)					private String channelItem;
	@CaseBeanField(desc = "是否选择渠道", index = 1)				private String isSelectChannel;
	@CaseBeanField(desc = "所属渠道名称", index = 2)				private String chnName;
	@CaseBeanField(desc = "所属渠道编码", index = 3)				private String chnId;
	@CaseBeanField(desc = "是否确认选择渠道", index = 3)			private String isConfirmSelectChannel;
	@CaseBeanField(desc = "商户名称", index = 4)					private String CooperateName;
	@CaseBeanField(desc = "商户简称", index = 5)      			private String shortName;
	@CaseBeanField(desc = "商户类型", index = 6)      			private String CooperateType;
	@CaseBeanField(desc = "是否选择业务员", index = 7)     		private String isSelectEmp;
	@CaseBeanField(desc = "选择业务员下拉搜索框", index = 7)     	private String empItem;
	@CaseBeanField(desc = "输入业务员编号或者名称", index = 7)   private String empNameOrId;
	@CaseBeanField(desc = "业务员名称", index = 9)				private String empName;
	@CaseBeanField(desc = "是否确认选择业务员", index = 9)		private String isConfirmSelectEmp;
	@CaseBeanField(desc = "币种", index = 10)					private String currency;
	@CaseBeanField(desc = "是否选择行业类别", index = 11)		private String isSelectIndustry;
	@CaseBeanField(desc = "行业类别名称", index = 11)			private String industryNameChain;
	@CaseBeanField(desc = "行业类别ID", index = 11)				private String industryIdChain;
	@CaseBeanField(desc = "是否选确认择行业类别", index = 11)		private String isConfirmSelectIndustry;
	@CaseBeanField(desc = "省份", index = 11)					private String province;
	@CaseBeanField(desc = "城市", index = 12)					private String city;
	@CaseBeanField(desc = "地址", index = 13)					private String address;
	@CaseBeanField(desc = "电话", index = 14)					private String tel;
	@CaseBeanField(desc = "邮箱", index = 15)					private String email;
	@CaseBeanField(desc = "网址", index = 16)					private String website;
	@CaseBeanField(desc = "负责人", index = 17)					private String principal;
	@CaseBeanField(desc = "负责人身份证", index = 18)			private String idCode;
	@CaseBeanField(desc = "负责人手机号码", index = 19)			private String principalPhone;
	@CaseBeanField(desc = "客服电话", index = 20)				private String serviceTel;
	@CaseBeanField(desc = "行内商户号", index = 21)				private String thrMchId;
	@CaseBeanField(desc = "是否点击结算资料checkbox", index = 21)private String isClickCheckbox;
	@CaseBeanField(desc = "是否选择银行", index = 21)			private String isSelectBank;
	@CaseBeanField(desc = "是否确认选择银行", index = 21)		private String isConfirmSelectBank;
	@CaseBeanField(desc = "营业执照图片", index = 22)			private String LICENSE_PHOTO;
	@CaseBeanField(desc = "商户负责人身份证", index = 23)		private String INDENTITY_PHOTO;
	@CaseBeanField(desc = "组织机构代码证", index = 24)			private String PROTOCOL_PHOTO;
	@CaseBeanField(desc = "商户协议", index = 25)				private String ORG_PHOTO;
	@CaseBeanField(desc = "开户银行", index = 26)				private String bankId;
	@CaseBeanField(desc = "银行名称", index = 27)				private String bankName;
	@CaseBeanField(desc = "银行卡号", index = 28)				private String account;
	@CaseBeanField(desc = "开户名称", index = 29)				private String accountOwner;
	@CaseBeanField(desc = "账户类型", index = 30)				private String accountType;
	@CaseBeanField(desc = "网点号", index = 31)					private String subBankNO;
	@CaseBeanField(desc = "开户支行", index = 32)				private String subBankName;
	@CaseBeanField(desc = "证件类型", index = 33)				private String idType;
	@CaseBeanField(desc = "是否保存", index = 34)				private String isSave;
	@CaseBeanField(desc = "是否确认保存", index = 35)			private String isConfirmSave;
	@CaseBeanField(desc = "通用消息", index = 36)				private String message;
	@CaseBeanField(desc = "是否行内账号", index = 37)			private String isInsideAccount; //是否为行内账号
	@CaseBeanField(desc = "手机号码类型", index = 38)			private String phoneType; //0:正常手机号码, 1:异常手机号
	
	@CaseBeanField(desc = "合作方姓名", index = 39)			private String coName; 
	@CaseBeanField(desc = "合作方密码", index = 40)			private String coPassowrd;
	@CaseBeanField(desc = "合作方类型", index = 4１)			private String coType;
	
	
	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getCoPassowrd() {
		return coPassowrd;
	}

	public void setCoPassowrd(String coPassowrd) {
		this.coPassowrd = coPassowrd;
	}

	public String getCoType() {
		return coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}
	
	
	
	public String getIsInsideAccount() {
		return isInsideAccount;
	}

	public CooperateAddCaseBean setIsInsideAccount(String isInsideAccount) {
		this.isInsideAccount = isInsideAccount;
		return this;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public CooperateAddCaseBean setPhoneType(String phoneType) {
		this.phoneType = phoneType;
		return this;
	}

	public String getEmpItem() {
		return empItem;
	}

	public CooperateAddCaseBean setEmpItem(String empItem) {
		this.empItem = empItem;
		return this;
	}

	public String getIsSave() {
		return isSave;
	}

	public CooperateAddCaseBean setIsSave(String isSave) {
		this.isSave = isSave;
		return this;
	}

	public String getIsConfirmSave() {
		return isConfirmSave;
	}

	public CooperateAddCaseBean setIsConfirmSave(String isConfirmSave) {
		this.isConfirmSave = isConfirmSave;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public CooperateAddCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getIsSelectBank() {
		return isSelectBank;
	}

	public CooperateAddCaseBean setIsSelectBank(String isSelectBank) {
		this.isSelectBank = isSelectBank;
		return this;
		
	}

	public String getIsConfirmSelectBank() {
		return isConfirmSelectBank;
	}

	public CooperateAddCaseBean setIsConfirmSelectBank(String isConfirmSelectBank) {
		this.isConfirmSelectBank = isConfirmSelectBank;
		return this;
	}

	public String getIsClickCheckbox() {
		return isClickCheckbox;
	}

	public CooperateAddCaseBean setIsClickCheckbox(String isClickCheckbox) {
		this.isClickCheckbox = isClickCheckbox;
		return this;
	}

	public String getIsConfirmSelectEmp() {
		return isConfirmSelectEmp;
	}

	public CooperateAddCaseBean setIsConfirmSelectEmp(String isConfirmSelectEmp) {
		this.isConfirmSelectEmp = isConfirmSelectEmp;
		return this;
	}

	public String getIsConfirmSelectChannel() {
		return isConfirmSelectChannel;
	}

	public CooperateAddCaseBean setIsConfirmSelectChannel(String isConfirmSelectChannel) {
		this.isConfirmSelectChannel = isConfirmSelectChannel;
		return this;
	}

	public String getChannelItem() {
		return channelItem;
	}

	public CooperateAddCaseBean setChannelItem(String channelItem) {
		this.channelItem = channelItem;
		return this;
	}

	public CooperateAddCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		this.CASE_NAME = cASE_NAME;
	}
	public String getBig_or_normal() {
		return big_or_normal;
	}
	public CooperateAddCaseBean setBig_or_normal(String big_or_normal) {
		this.big_or_normal = big_or_normal;
		return this;
	}
	
	public String getIsSelectEmp() {
		return isSelectEmp;
	}
	public CooperateAddCaseBean setIsSelectEmp(String isSelectEmp) {
		this.isSelectEmp = isSelectEmp;
		return this;
	}
	public String getIsSelectIndustry() {
		return isSelectIndustry;
	}
	public CooperateAddCaseBean setIsSelectIndustry(String isSelectIndustry) {
		this.isSelectIndustry = isSelectIndustry;
		return this;
	}
	public String getIndustryNameChain() {
		return industryNameChain;
	}
	public CooperateAddCaseBean setIndustryNameChain(String industryNameChain) {
		this.industryNameChain = industryNameChain;
		return this;
	}
	public String getIndustryIdChain() {
		return industryIdChain;
	}
	public CooperateAddCaseBean setIndustryIdChain(String industryIdChain) {
		this.industryIdChain = industryIdChain;
		return this;
	}
	public String getIsConfirmSelectIndustry() {
		return isConfirmSelectIndustry;
	}
	public CooperateAddCaseBean setIsConfirmSelectIndustry(String isConfirmSelectIndustry) {
		this.isConfirmSelectIndustry = isConfirmSelectIndustry;
		return this;
	}
	public String getChnNameOrId() {
		return chnNameOrId;
	}
	public CooperateAddCaseBean setChnNameOrId(String chnNameOrId) {
		this.chnNameOrId = chnNameOrId;
		return this;
	}
	public String getIsSelectChannel() {
		return isSelectChannel;
	}
	public CooperateAddCaseBean setIsSelectChannel(String isSelectChannel) {
		this.isSelectChannel = isSelectChannel;
		return this;
	}
	public String getChnName() {
		return chnName;
	}
	public CooperateAddCaseBean setChnName(String chnName) {
		this.chnName = chnName;
		return this;
	}
	public String getChnId() {
		return chnId;
	}
	public CooperateAddCaseBean setChnId(String chnId) {
		this.chnId = chnId;
		return this;
	}
	public String getCooperateName() {
		return CooperateName;
	}
	public CooperateAddCaseBean setCooperateName(String CooperateName) {
		this.CooperateName = CooperateName;
		return this;
	}
	public String getShortName() {
		return shortName;
	}
	public CooperateAddCaseBean setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}
	public String getCooperateType() {
		return CooperateType;
	}
	public CooperateAddCaseBean setCooperateType(String CooperateType) {
		this.CooperateType = CooperateType;
		return this;
	}
	public String getEmpNameOrId() {
		return empNameOrId;
	}
	public CooperateAddCaseBean setEmpNameOrId(String empNameOrId) {
		this.empNameOrId = empNameOrId;
		return this;
	}
	public String getEmpName() {
		return empName;
	}
	public CooperateAddCaseBean setEmpName(String empName) {
		this.empName = empName;
		return this;
	}
	public String getCurrency() {
		return currency;
	}
	public CooperateAddCaseBean setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	public String getProvince() {
		return province;
	}
	public CooperateAddCaseBean setProvince(String province) {
		this.province = province;
		return this;
	}
	public String getCity() {
		return city;
	}
	public CooperateAddCaseBean setCity(String city) {
		this.city = city;
		return this;
	}
	public String getAddress() {
		return address;
	}
	public CooperateAddCaseBean setAddress(String address) {
		this.address = address;
		return this;
	}
	public String getTel() {
		return tel;
	}
	public CooperateAddCaseBean setTel(String tel) {
		this.tel = tel;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public CooperateAddCaseBean setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getWebsite() {
		return website;
	}
	public CooperateAddCaseBean setWebsite(String website) {
		this.website = website;
		return this;
	}
	public String getPrincipal() {
		return principal;
	}
	public CooperateAddCaseBean setPrincipal(String principal) {
		this.principal = principal;
		return this;
	}
	public String getIdCode() {
		return idCode;
	}
	public CooperateAddCaseBean setIdCode(String idCode) {
		this.idCode = idCode;
		return this;
	}
	public String getPrincipalPhone() {
		return principalPhone;
	}
	public CooperateAddCaseBean setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
		return this;
	}
	public String getServiceTel() {
		return serviceTel;
	}
	public CooperateAddCaseBean setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
		return this;
	}
	public String getThrMchId() {
		return thrMchId;
	}
	public CooperateAddCaseBean setThrMchId(String thrMchId) {
		this.thrMchId = thrMchId;
		return this;
	}
	public String getLICENSE_PHOTO() {
		return LICENSE_PHOTO;
	}
	public CooperateAddCaseBean setLICENSE_PHOTO(String lICENSE_PHOTO) {
		LICENSE_PHOTO = lICENSE_PHOTO;
		return this;
	}
	public String getINDENTITY_PHOTO() {
		return INDENTITY_PHOTO;
	}
	public CooperateAddCaseBean setINDENTITY_PHOTO(String iNDENTITY_PHOTO) {
		INDENTITY_PHOTO = iNDENTITY_PHOTO;
		return this;
	}
	public String getPROTOCOL_PHOTO() {
		return PROTOCOL_PHOTO;
	}
	public CooperateAddCaseBean setPROTOCOL_PHOTO(String pROTOCOL_PHOTO) {
		PROTOCOL_PHOTO = pROTOCOL_PHOTO;
		return this;
	}
	public String getORG_PHOTO() {
		return ORG_PHOTO;
	}
	public CooperateAddCaseBean setORG_PHOTO(String oRG_PHOTO) {
		ORG_PHOTO = oRG_PHOTO;
		return this;
	}
	public String getBankId() {
		return bankId;
	}
	public CooperateAddCaseBean setBankId(String bankId) {
		this.bankId = bankId;
		return this;
	}
	public String getBankName() {
		return bankName;
	}
	public CooperateAddCaseBean setBankName(String bankName) {
		this.bankName = bankName;
		return this;
	}
	public String getAccount() {
		return account;
	}
	public CooperateAddCaseBean setAccount(String account) {
		this.account = account;
		return this;
	}
	public String getAccountOwner() {
		return accountOwner;
	}
	public CooperateAddCaseBean setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
		return this;
	}
	public String getAccountType() {
		return accountType;
	}
	public CooperateAddCaseBean setAccountType(String accountType) {
		this.accountType = accountType;
		return this;
	}
	public String getSubBankNO() {
		return subBankNO;
	}
	public CooperateAddCaseBean setSubBankNO(String subBankNO) {
		this.subBankNO = subBankNO;
		return this;
	}
	public String getSubBankName() {
		return subBankName;
	}
	public CooperateAddCaseBean setSubBankName(String subBankName) {
		this.subBankName = subBankName;
		return this;
	}
	public String getIdType() {
		return idType;
	}
	public CooperateAddCaseBean setIdType(String idType) {
		this.idType = idType;
		return this;
	}
}
