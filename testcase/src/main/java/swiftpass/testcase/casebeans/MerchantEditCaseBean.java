package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-28
 */
public class MerchantEditCaseBean extends CaseBean {
	
	private static final long serialVersionUID = 7346838228140287494L;
	@CaseBeanField(desc = "用例名称", index = 0)                private String CASE_NAME;
	@CaseBeanField(desc = "父渠道", index = 0)					private String parentChannel;
	@CaseBeanField(desc = "父渠道编码", index = 1)				private String parentChannelId;
	@CaseBeanField(desc = "父渠道名称", index = 2)				private String parentChannelName;
	@CaseBeanField(desc = "商户名称", index = 3)					private String merchantName;
	@CaseBeanField(desc = "商户简称", index = 4)					private String shortName;
	@CaseBeanField(desc = "商户类型", index = 5)					private String merchantType;
	@CaseBeanField(desc = "输入业务员编号或者名称", index = 6)   private String empNameOrId;
	@CaseBeanField(desc = "业务员名称", index =7)				private String empName;
	@CaseBeanField(desc = "是否确认选择业务员", index = 8)		private String isConfirmSelectEmp;
	@CaseBeanField(desc = "币种", index = 9)						private String currency;
	@CaseBeanField(desc = "行业类别", index = 10)				private String industry;
	@CaseBeanField(desc = "行业类别名称", index = 11)			private String industryNameChain;
	@CaseBeanField(desc = "行业类别ID", index = 12)				private String industryIdChain;
	@CaseBeanField(desc = "是否选确认择行业类别", index = 13)		private String isConfirmSelectIndustry;
	@CaseBeanField(desc = "省份", index = 14)					private String province;
	@CaseBeanField(desc = "城市", index = 15)					private String city;
	@CaseBeanField(desc = "地址", index = 16)					private String address;
	@CaseBeanField(desc = "电话", index = 17)					private String tel;
	@CaseBeanField(desc = "邮箱", index = 18)					private String email;
	@CaseBeanField(desc = "网址", index = 19)					private String website;
	@CaseBeanField(desc = "负责人", index = 20)					private String principal;
	@CaseBeanField(desc = "负责人身份证", index = 21)			private String idCode;
	@CaseBeanField(desc = "负责人手机号码", index = 22)			private String principalPhone;
	@CaseBeanField(desc = "客服电话", index = 23)				private String serviceTel;
	@CaseBeanField(desc = "传真", index = 23)					private String fax;
	@CaseBeanField(desc = "行内商户号", index = 24)				private String thrMchId;
	@CaseBeanField(desc = "是否点击结算资料checkbox", index = 25)private String isClickCheckbox;
	@CaseBeanField(desc = "是否选择银行", index = 26)			private String isSelectBank;
	@CaseBeanField(desc = "是否确认选择银行", index = 27)		private String isConfirmSelectBank;
	@CaseBeanField(desc = "营业执照图片", index = 28)			private String LICENSE_PHOTO;
	@CaseBeanField(desc = "商户负责人身份证", index = 29)		private String INDENTITY_PHOTO;
	@CaseBeanField(desc = "组织机构代码证", index = 30)			private String PROTOCOL_PHOTO;
	@CaseBeanField(desc = "商户协议", index = 31)				private String ORG_PHOTO;
	@CaseBeanField(desc = "是否保存", index = 32)				private String isEdit;
	@CaseBeanField(desc = "是否确认保存", index = 33)			private String isConfirmEdit;
	@CaseBeanField(desc = "是否存储", index = 33)				private String isStore;
	@CaseBeanField(desc = "是否存储", index = 34)				private String isEditIndustry;
	@CaseBeanField(desc = "页面TEXT信息", index = 35)			private String TEXT;
	@CaseBeanField(desc = "是否存储", index = 36)				private String isMerchantNoAuditPass;
	@CaseBeanField(desc = "通用消息", index = 37)				private String message;
	
	public MerchantEditCaseBean (){}

	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}

	public String getParentChannel() {
		return parentChannel;
	}

	
	public String getIsEditIndustry() {
		return isEditIndustry;
	}

	public MerchantEditCaseBean setIsEditIndustry(String isEditIndustry) {
		this.isEditIndustry = isEditIndustry;
		return this;
	}

	public String getTEXT() {
		return TEXT;
	}

	public MerchantEditCaseBean setTEXT(String tEXT) {
		TEXT = tEXT;
		return this;
	}

	public String getIsMerchantNoAuditPass() {
		return isMerchantNoAuditPass;
	}

	public MerchantEditCaseBean setIsMerchantNoAuditPass(String isMerchantNoAuditPass) {
		this.isMerchantNoAuditPass = isMerchantNoAuditPass;
		return this;
	}

	public MerchantEditCaseBean setParentChannel(String parentChannel) {
		this.parentChannel = parentChannel;
		return this;
	}

	public String getParentChannelId() {
		return parentChannelId;
	}

	public MerchantEditCaseBean setParentChannelId(String parentChannelId) {
		this.parentChannelId = parentChannelId;
		return this;
	}
	
	
	

	public String getIsStore() {
		return isStore;
	}

	public MerchantEditCaseBean setIsStore(String isStore) {
		this.isStore = isStore;
		return this;
	}

	public String getFax() {
		return fax;
	}

	public MerchantEditCaseBean setFax(String fax) {
		this.fax = fax;
		return this;
	}

	public String getParentChannelName() {
		return parentChannelName;
	}

	public MerchantEditCaseBean setParentChannelName(String parentChannelName) {
		this.parentChannelName = parentChannelName;
		return this;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public MerchantEditCaseBean setMerchantName(String merchantName) {
		this.merchantName = merchantName;
		return this;
	}

	public String getShortName() {
		return shortName;
	}

	public MerchantEditCaseBean setShortName(String shortName) {
		this.shortName = shortName;
		return this;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public MerchantEditCaseBean setMerchantType(String merchantType) {
		this.merchantType = merchantType;
		return this;
	}

	public String getEmpNameOrId() {
		return empNameOrId;
	}

	public MerchantEditCaseBean setEmpNameOrId(String empNameOrId) {
		this.empNameOrId = empNameOrId;
		return this;
	}

	public String getEmpName() {
		return empName;
	}

	public MerchantEditCaseBean setEmpName(String empName) {
		this.empName = empName;
		return this;
	}

	public String getIsConfirmSelectEmp() {
		return isConfirmSelectEmp;
	}

	public MerchantEditCaseBean setIsConfirmSelectEmp(String isConfirmSelectEmp) {
		this.isConfirmSelectEmp = isConfirmSelectEmp;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public MerchantEditCaseBean setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	

	public String getIndustry() {
		return industry;
	}

	public MerchantEditCaseBean setIndustry(String industry) {
		this.industry = industry;
		return this;
	}

	public String getIndustryNameChain() {
		return industryNameChain;
	}

	public MerchantEditCaseBean setIndustryNameChain(String industryNameChain) {
		this.industryNameChain = industryNameChain;
		return this;
	}

	public String getIndustryIdChain() {
		return industryIdChain;
	}

	public MerchantEditCaseBean setIndustryIdChain(String industryIdChain) {
		this.industryIdChain = industryIdChain;
		return this;
	}

	public String getIsConfirmSelectIndustry() {
		return isConfirmSelectIndustry;
	}

	public MerchantEditCaseBean setIsConfirmSelectIndustry(String isConfirmSelectIndustry) {
		this.isConfirmSelectIndustry = isConfirmSelectIndustry;
		return this;
	}

	public String getProvince() {
		return province;
	}

	public MerchantEditCaseBean setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getCity() {
		return city;
	}

	public MerchantEditCaseBean setCity(String city) {
		this.city = city;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public MerchantEditCaseBean setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getTel() {
		return tel;
	}

	public MerchantEditCaseBean setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public MerchantEditCaseBean setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public MerchantEditCaseBean setWebsite(String website) {
		this.website = website;
		return this;
	}

	public String getPrincipal() {
		return principal;
	}

	public MerchantEditCaseBean setPrincipal(String principal) {
		this.principal = principal;
		return this;
	}

	public String getIdCode() {
		return idCode;
	}

	public MerchantEditCaseBean setIdCode(String idCode) {
		this.idCode = idCode;
		return this;
	}

	public String getPrincipalPhone() {
		return principalPhone;
	}

	public MerchantEditCaseBean setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
		return this;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public MerchantEditCaseBean setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
		return this;
	}

	public String getThrMchId() {
		return thrMchId;
	}

	public MerchantEditCaseBean setThrMchId(String thrMchId) {
		this.thrMchId = thrMchId;
		return this;
	}

	public String getIsClickCheckbox() {
		return isClickCheckbox;
	}

	public MerchantEditCaseBean setIsClickCheckbox(String isClickCheckbox) {
		this.isClickCheckbox = isClickCheckbox;
		return this;
	}

	public String getIsSelectBank() {
		return isSelectBank;
	}

	public MerchantEditCaseBean setIsSelectBank(String isSelectBank) {
		this.isSelectBank = isSelectBank;
		return this;
	}

	public String getIsConfirmSelectBank() {
		return isConfirmSelectBank;
	}

	public MerchantEditCaseBean setIsConfirmSelectBank(String isConfirmSelectBank) {
		this.isConfirmSelectBank = isConfirmSelectBank;
		return this;
	}

	public String getLICENSE_PHOTO() {
		return LICENSE_PHOTO;
	}

	public MerchantEditCaseBean setLICENSE_PHOTO(String lICENSE_PHOTO) {
		LICENSE_PHOTO = lICENSE_PHOTO;
		return this;
	}

	public String getINDENTITY_PHOTO() {
		return INDENTITY_PHOTO;
	}

	public MerchantEditCaseBean setINDENTITY_PHOTO(String iNDENTITY_PHOTO) {
		INDENTITY_PHOTO = iNDENTITY_PHOTO;
		return this;
	}

	public String getPROTOCOL_PHOTO() {
		return PROTOCOL_PHOTO;
	}

	public MerchantEditCaseBean setPROTOCOL_PHOTO(String pROTOCOL_PHOTO) {
		PROTOCOL_PHOTO = pROTOCOL_PHOTO;
		return this;
	}

	public String getORG_PHOTO() {
		return ORG_PHOTO;
	}

	public MerchantEditCaseBean setORG_PHOTO(String oRG_PHOTO) {
		ORG_PHOTO = oRG_PHOTO;
		return this;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public MerchantEditCaseBean setIsEdit(String isEdit) {
		this.isEdit = isEdit;
		return this;
	}

	public String getIsConfirmEdit() {
		return isConfirmEdit;
	}

	public MerchantEditCaseBean setIsConfirmEdit(String isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public MerchantEditCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
}
