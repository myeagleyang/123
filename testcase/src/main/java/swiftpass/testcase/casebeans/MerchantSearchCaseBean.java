package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-22
 */
public class MerchantSearchCaseBean extends CaseBean {
	private static final long serialVersionUID = 7346838228140287494L;
	@CaseBeanField(desc = "用例名称", index = 0)                private String CASE_NAME ="";
	@CaseBeanField(desc = "创建开始时间", index =1)				private String beginCT ="";
	@CaseBeanField(desc = "创建结束时间", index = 3)				private String endCT ="";
	@CaseBeanField(desc = "是否选择受理机构", index = 4)			private String isSelectOrg ="";
	@CaseBeanField(desc = "选择编号还是名称", index = 4)			private String orgNameOrIdItem ="";
	@CaseBeanField(desc = "选择编号or名称的具体值", index = 4)	private String orgNameOrId ="";
	@CaseBeanField(desc = "受理机构编码", index = 5)				private String ACCEPT_ORG_ID ="";
	@CaseBeanField(desc = "受理机构名称", index = 6)				private String ORG_NAME ="";
	@CaseBeanField(desc = "是否确认选择受理机构", index = 7)		private String isConfirmSelectOrg ="";
	@CaseBeanField(desc = "是否选择渠道", index = 8)				private String isSelectPCh ="";
	@CaseBeanField(desc = "选择渠道名称或编码", index = 9)		private String pChNameOrIdItem ="";
	@CaseBeanField(desc = "填写遇到名称或ID", index = 10)		private String pChNameOrId ="";
	@CaseBeanField(desc = "是否确认选择渠道", index = 11)		private String isConfirmSelectPCh ="";
	@CaseBeanField(desc = "商户名称", index = 12)				private String MERCHANT_NAME ="";
	@CaseBeanField(desc = "商户编码", index = 13)				private String MERCHANT_ID ="";
	@CaseBeanField(desc = "商户类型", index = 14)				private String MERCHANT_TYPE ="";
	@CaseBeanField(desc = "审核状态", index = 15)				private String EXAMINE_STATUS ="";
	@CaseBeanField(desc = "激活状态", index = 16)				private String ACTIVATE_STATUS ="";
	@CaseBeanField(desc = "是否选择行业", index = 17)			private String isSelectIndustry ="";
	@CaseBeanField(desc = "业务员ID", index = 18)				private String INDUSTR_ID ="";
	@CaseBeanField(desc = "业务员名称", index =19)				private String INDUSTRY_NAME ="";
	@CaseBeanField(desc = "是否确认选择行业", index = 20)		private String isConfirmSelectIndustry ="";
	@CaseBeanField(desc = "通用消息", index = 21)				private String message ="";
	
	public MerchantSearchCaseBean(){}
	
	

	public MerchantSearchCaseBean(String cASE_NAME, String beginCT, String endCT, String isSelectOrg,
			String orgNameOrIdItem, String orgNameOrId, String aCCEPT_ORG_ID, String oRG_NAME,
			String isConfirmSelectOrg, String isSelectPCh, String pChNameOrIdItem, String pChNameOrId,
			String isConfirmSelectPCh, String mERCHANT_NAME, String mERCHANT_ID, String mERCHANT_TYPE,
			String eXAMINE_STATUS, String aCTIVATE_STATUS, String isSelectIndustry, String iNDUSTR_ID,
			String iNDUSTRY_NAME, String isConfirmSelectIndustry, String message) {
		super();
		CASE_NAME = cASE_NAME;
		this.beginCT = beginCT;
		this.endCT = endCT;
		this.isSelectOrg = isSelectOrg;
		this.orgNameOrIdItem = orgNameOrIdItem;
		this.orgNameOrId = orgNameOrId;
		ACCEPT_ORG_ID = aCCEPT_ORG_ID;
		ORG_NAME = oRG_NAME;
		this.isConfirmSelectOrg = isConfirmSelectOrg;
		this.isSelectPCh = isSelectPCh;
		this.pChNameOrIdItem = pChNameOrIdItem;
		this.pChNameOrId = pChNameOrId;
		this.isConfirmSelectPCh = isConfirmSelectPCh;
		MERCHANT_NAME = mERCHANT_NAME;
		MERCHANT_ID = mERCHANT_ID;
		MERCHANT_TYPE = mERCHANT_TYPE;
		EXAMINE_STATUS = eXAMINE_STATUS;
		ACTIVATE_STATUS = aCTIVATE_STATUS;
		this.isSelectIndustry = isSelectIndustry;
		INDUSTR_ID = iNDUSTR_ID;
		INDUSTRY_NAME = iNDUSTRY_NAME;
		this.isConfirmSelectIndustry = isConfirmSelectIndustry;
		this.message = message;
	}



	public String getOrgNameOrIdItem() {
		return orgNameOrIdItem;
	}


	public MerchantSearchCaseBean setOrgNameOrIdItem(String orgNameOrIdItem) {
		this.orgNameOrIdItem = orgNameOrIdItem;
		return this;
	}



	public String getOrgNameOrId() {
		return orgNameOrId;
	}



	public MerchantSearchCaseBean setOrgNameOrId(String orgNameOrId) {
		this.orgNameOrId = orgNameOrId;
		return this;
	}



	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void  setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
		
	}
	public String getBeginCT() {
		return beginCT;
	}
	public MerchantSearchCaseBean  setBeginCT(String beginCT) {
		this.beginCT = beginCT;
		return this;
	}
	public String getEndCT() {
		return endCT;
	}
	public MerchantSearchCaseBean  setEndCT(String endCT) {
		this.endCT = endCT;
		return this;
	}
	public String getIsSelectOrg() {
		return isSelectOrg;
	}
	public MerchantSearchCaseBean  setIsSelectOrg(String isSelectOrg) {
		this.isSelectOrg = isSelectOrg;
		return this;
	}
	public String getACCEPT_ORG_ID() {
		return ACCEPT_ORG_ID;
	}
	public MerchantSearchCaseBean  setACCEPT_ORG_ID(String aCCEPT_ORG_ID) {
		ACCEPT_ORG_ID = aCCEPT_ORG_ID;
		return this;
	}
	public String getORG_NAME() {
		return ORG_NAME;
	}
	public MerchantSearchCaseBean  setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
		return this;
	}
	public String getIsConfirmSelectOrg() {
		return isConfirmSelectOrg;
	}
	public MerchantSearchCaseBean  setIsConfirmSelectOrg(String isConfirmSelectOrg) {
		this.isConfirmSelectOrg = isConfirmSelectOrg;
		return this;
	}
	public String getIsSelectPCh() {
		return isSelectPCh;
	}
	public MerchantSearchCaseBean  setIsSelectPCh(String isSelectPCh) {
		this.isSelectPCh = isSelectPCh;
		return this;
	}
	public String getpChNameOrIdItem() {
		return pChNameOrIdItem;
	}
	public MerchantSearchCaseBean  setpChNameOrIdItem(String pChNameOrIdItem) {
		this.pChNameOrIdItem = pChNameOrIdItem;
		return this;
	}
	public String getpChNameOrId() {
		return pChNameOrId;
	}
	public MerchantSearchCaseBean  setpChNameOrId(String pChNameOrId) {
		this.pChNameOrId = pChNameOrId;
		return this;
	}
	public String getIsConfirmSelectPCh() {
		return isConfirmSelectPCh;
	}
	public MerchantSearchCaseBean  setIsConfirmSelectPCh(String isConfirmSelectPCh) {
		this.isConfirmSelectPCh = isConfirmSelectPCh;
		return this;
	}
	public String getMERCHANT_NAME() {
		return MERCHANT_NAME;
	}
	public MerchantSearchCaseBean  setMERCHANT_NAME(String mERCHANT_NAME) {
		MERCHANT_NAME = mERCHANT_NAME;
		return this;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public MerchantSearchCaseBean  setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
		return this;
	}
	public String getMERCHANT_TYPE() {
		return MERCHANT_TYPE;
	}
	public MerchantSearchCaseBean  setMERCHANT_TYPE(String mERCHANT_TYPE) {
		MERCHANT_TYPE = mERCHANT_TYPE;
		return this;
	}
	public String getEXAMINE_STATUS() {
		return EXAMINE_STATUS;
	}
	public MerchantSearchCaseBean  setEXAMINE_STATUS(String eXAMINE_STATUS) {
		EXAMINE_STATUS = eXAMINE_STATUS;
		return this;
	}
	public String getACTIVATE_STATUS() {
		return ACTIVATE_STATUS;
	}
	public MerchantSearchCaseBean  setACTIVATE_STATUS(String aCTIVATE_STATUS) {
		ACTIVATE_STATUS = aCTIVATE_STATUS;
		return this;
	}
	public String getIsSelectIndustry() {
		return isSelectIndustry;
	}
	public MerchantSearchCaseBean  setIsSelectIndustry(String isSelectIndustry) {
		this.isSelectIndustry = isSelectIndustry;
		return this;
	}
	public String getINDUSTR_ID() {
		return INDUSTR_ID;
	}
	public MerchantSearchCaseBean  setINDUSTR_ID(String iNDUSTR_ID) {
		INDUSTR_ID = iNDUSTR_ID;
		return this;
	}
	public String getINDUSTRY_NAME() {
		return INDUSTRY_NAME;
	}
	public MerchantSearchCaseBean  setINDUSTRY_NAME(String iNDUSTRY_NAME) {
		INDUSTRY_NAME = iNDUSTRY_NAME;
		return this;
	}
	public String getIsConfirmSelectIndustry() {
		return isConfirmSelectIndustry;
	}
	public MerchantSearchCaseBean  setIsConfirmSelectIndustry(String isConfirmSelectIndustry) {
		this.isConfirmSelectIndustry = isConfirmSelectIndustry;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public MerchantSearchCaseBean  setMessage(String message) {
		this.message = message;
		return this;
	}
	
	
}
