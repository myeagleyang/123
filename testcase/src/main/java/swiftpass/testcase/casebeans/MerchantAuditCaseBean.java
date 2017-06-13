package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-28
 */
public class MerchantAuditCaseBean extends CaseBean {
	private static final long serialVersionUID = 344842814365864989L;
	@CaseBeanField(desc="用例名称",index =0)						private String 	CASE_NAME;
	@CaseBeanField(desc="通用消息",index =1)						private String	message;
	@CaseBeanField(desc="所属渠道的审核状态",index =2) 			private String 	C_ExamineStatus;	//
	@CaseBeanField(desc="所属商户的审核状态",index =3) 			private String 	PM_ExamineStatus;	//
	@CaseBeanField(desc="当前待审核的商户的审核状态",index =4)	private String	M_ExamineStatus;
	@CaseBeanField(desc="是否商户（区分门店)",index =5)			private String	isMerchant;
	@CaseBeanField(desc="页面信息",index =6)						private String	TEXT;
	@CaseBeanField(desc="是否审核",index =7)						private String	auditOperate;
	@CaseBeanField(desc="是否确认审核",index =8)					private String	isConfirmOperate;
	@CaseBeanField(desc="审核备注",index =9)						private String	auditRemark;

	
	
	public String getAuditRemark() {
		return auditRemark;
	}

	public MerchantAuditCaseBean setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
		return this;
	}

	public MerchantAuditCaseBean (){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getMessage() {
		return message;
	}
	public MerchantAuditCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
	public String getC_ExamineStatus() {
		return C_ExamineStatus;
	}
	public MerchantAuditCaseBean setC_ExamineStatus(String c_ExamineStatus) {
		C_ExamineStatus = c_ExamineStatus;
		return this;
	}
	public String getPM_ExamineStatus() {
		return PM_ExamineStatus;
	}
	public MerchantAuditCaseBean setPM_ExamineStatus(String pM_ExamineStatus) {
		PM_ExamineStatus = pM_ExamineStatus;
		return this;
	}
	public String getM_ExamineStatus() {
		return M_ExamineStatus;
	}
	public MerchantAuditCaseBean setM_ExamineStatus(String m_ExamineStatus) {
		M_ExamineStatus = m_ExamineStatus;
		return this;
	}
	public String getIsMerchant() {
		return isMerchant;
	}
	public MerchantAuditCaseBean setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
		return this;
	}
	public String getTEXT() {
		return TEXT;
	}
	public MerchantAuditCaseBean setTEXT(String tEXT) {
		TEXT = tEXT;
		return this;
	}
	public String getAuditOperate() {
		return auditOperate;
	}
	public MerchantAuditCaseBean setAuditOperate(String auditOperate) {
		this.auditOperate = auditOperate;
		return this;
	}
	public String getIsConfirmOperate() {
		return isConfirmOperate;
	}
	public MerchantAuditCaseBean setIsConfirmOperate(String isConfirmOperate) {
		this.isConfirmOperate = isConfirmOperate;
		return this;
	}
}
