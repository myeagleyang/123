package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-30
 */
public class MerchantAAACaseBean extends CaseBean {
	private static final long serialVersionUID = 1652203310717087172L;
	@CaseBeanField (desc ="用例名称",index = 0) private String CASE_NAME;
	@CaseBeanField (desc ="页面文本信息",index = 0) private String TEXT;
	@CaseBeanField (desc ="所属渠道审核状态",index = 1) private String PC_ExamineStatus;
	@CaseBeanField (desc ="所属大商户审核状态",index = 2) private String PM_ExamineStatus;
	@CaseBeanField (desc ="商户（门店）审核状态",index = 3) private String M_ExamineStatus;
	@CaseBeanField (desc ="商户（门店）激活状态",index = 4) private String M_ActivateStatus;
	@CaseBeanField (desc ="是否选择商户",index = 5) private String isMerchant;
	@CaseBeanField (desc ="是否审核",index = 6) private String aaOperate;
	@CaseBeanField (desc ="是否确认审核",index = 7) private String isConfirmOperate;
	@CaseBeanField (desc ="通宵消息s",index = 8) private String message;
	@CaseBeanField (desc ="激活状态备注",index = 9) private String activateStatusRemark;
	
	
	
	
	public MerchantAAACaseBean(){}
	
	
	public String getActivateStatusRemark() {
		return activateStatusRemark;
	}


	public MerchantAAACaseBean setActivateStatusRemark(String activateStatusRemark) {
		this.activateStatusRemark = activateStatusRemark;
		return this;
	}


	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getTEXT() {
		return TEXT;
	}
	public MerchantAAACaseBean setTEXT(String tEXT) {
		TEXT = tEXT;
		return this;
	}
	public String getPC_ExamineStatus() {
		return PC_ExamineStatus;
	}
	public MerchantAAACaseBean setPC_ExamineStatus(String pC_ExamineStatus) {
		PC_ExamineStatus = pC_ExamineStatus;
		return this;
	}
	public String getPM_ExamineStatus() {
		return PM_ExamineStatus;
	}
	public MerchantAAACaseBean setPM_ExamineStatus(String pM_ExamineStatus) {
		PM_ExamineStatus = pM_ExamineStatus;
		return this;
	}
	public String getM_ExamineStatus() {
		return M_ExamineStatus;
	}
	public MerchantAAACaseBean setM_ExamineStatus(String m_ExamineStatus) {
		M_ExamineStatus = m_ExamineStatus;
		return this;
	}
	public String getM_ActivateStatus() {
		return M_ActivateStatus;
	}
	public MerchantAAACaseBean setM_ActivateStatus(String m_ActivateStatus) {
		M_ActivateStatus = m_ActivateStatus;
		return this;
	}
	public String getIsMerchant() {
		return isMerchant;
	}
	public MerchantAAACaseBean setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
		return this;
	}
	public String getAaOperate() {
		return aaOperate;
	}
	public MerchantAAACaseBean setAaOperate(String aaOperate) {
		this.aaOperate = aaOperate;
		return this;
	}
	public String getIsConfirmOperate() {
		return isConfirmOperate;
	}
	public MerchantAAACaseBean setIsConfirmOperate(String isConfirmOperate) {
		this.isConfirmOperate = isConfirmOperate;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public MerchantAAACaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
}
