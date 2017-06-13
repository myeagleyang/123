package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author sunhaojie
 * date 2017-03-30
 */
public class MerchantActiveCaseBean extends CaseBean {
	private static final long serialVersionUID = -3550183065117501237L;
	@CaseBeanField(desc="用例名称",index =0)									private String 	CASE_NAME;
	@CaseBeanField(desc="通用消息",index =1)									private String	message;
	@CaseBeanField(desc="待激活操作商户的操作前审核状态",index =2) 			private String 	M_ExamineStatus;	
	@CaseBeanField(desc="待激活操作商户的操作前激活状态",index =3) 			private String 	M_ActivateStatus;	
	@CaseBeanField(desc="是否商户（区分门店)",index =5)						private String	isMerchant;
	@CaseBeanField(desc="页面信息",index =6)									private String	TEXT;
	@CaseBeanField(desc="是否激活",index =7)									private String	activeOperate;
	@CaseBeanField(desc="是否确认激活",index =8)								private String	isConfirmOperate;
	@CaseBeanField(desc="激活备注",index =9)									private String	ACTIVATE_STATUS_REMARK;
	
	public MerchantActiveCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getMessage() {
		return message;
	}
	public MerchantActiveCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
	public String getM_ExamineStatus() {
		return M_ExamineStatus;
	}
	public MerchantActiveCaseBean setM_ExamineStatus(String m_ExamineStatus) {
		M_ExamineStatus = m_ExamineStatus;
		return this;
	}
	public String getM_ActivateStatus() {
		return M_ActivateStatus;
	}
	public MerchantActiveCaseBean setM_ActivateStatus(String m_ActivateStatus) {
		M_ActivateStatus = m_ActivateStatus;
		return this;
	}
	public String getIsMerchant() {
		return isMerchant;
	}
	public MerchantActiveCaseBean setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
		return this;
	}
	public String getTEXT() {
		return TEXT;
	}
	public MerchantActiveCaseBean setTEXT(String tEXT) {
		TEXT = tEXT;
		return this;
	}

	
	public String getActiveOperate() {
		return activeOperate;
	}

	public MerchantActiveCaseBean setActiveOperate(String activeOperate) {
		this.activeOperate = activeOperate;
		return this;
	}

	public String getIsConfirmOperate() {
		return isConfirmOperate;
	}
	public MerchantActiveCaseBean setIsConfirmOperate(String isConfirmOperate) {
		this.isConfirmOperate = isConfirmOperate;
		return this;
	}
	public String getACTIVATE_STATUS_REMARK() {
		return ACTIVATE_STATUS_REMARK;
	}
	public MerchantActiveCaseBean setACTIVATE_STATUS_REMARK(String aCTIVATE_STATUS_REMARK) {
		ACTIVATE_STATUS_REMARK = aCTIVATE_STATUS_REMARK;
		return this;
	}
	
}
