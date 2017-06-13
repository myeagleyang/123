package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

/**
 * 
 * @author xieliangji
 * 时间：2017-3-8
 */
public class ChannelEditCaseBean extends CaseBean{
	private static final long serialVersionUID = 5786234382820356769L;

	@CaseBeanField(desc="用例名称", index=0) private String CASE_NAME;
		
	@CaseBeanField(desc="渠道名称", index=1) private String channelName;
	@CaseBeanField(desc="渠道编号（查询用)", index=1) private String channelId;
	
	@CaseBeanField(desc="所属渠道名称（验证用）", index=2) private String parentChannelName;
	@CaseBeanField(desc="所属渠道编号（验证用）", index=2) private String parentChannelId;

	@CaseBeanField(desc="省份（验证用）", index=3) private String province;
	@CaseBeanField(desc="城市（验证用）", index=4) private String city;
	
	@CaseBeanField(desc="地址", index=5) private String address;
	@CaseBeanField(desc="邮箱", index=6) private String email;
	
	@CaseBeanField(desc="负责人", index=7) private String principal;
	@CaseBeanField(desc="电话", index=8) private String tel;
	
	@CaseBeanField(desc="备注", index=9) private String remark;
	@CaseBeanField(desc="外部渠道号", index=10) private String thirdChannelId;
	
	@CaseBeanField(desc="是否编辑", index=11) private String isEdit;
	@CaseBeanField(desc="是否确认编辑", index=12) private String isConfirmEdit;
	
	@CaseBeanField(desc="通用消息", index=13) private String message;
	@CaseBeanField(desc="编辑渠道是否冻结", index=14) private String isNotFreeze;
	@CaseBeanField(desc="编辑渠道是否审核通过", index=15) private String isExaminePass;
	
	
	public ChannelEditCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String caseName) {
		CASE_NAME = caseName;
	}

	public String getChannelName() {
		return channelName;
	}

	public ChannelEditCaseBean setChannelName(String channelName) {
		this.channelName = channelName;
		return this;
	}
	
	public String getChannelId(){
		return channelId;
	}

	public ChannelEditCaseBean setChannelId(String channelId){
		this.channelId = channelId;
		return this;
	}
	
	public String getAddress() {
		return address;
	}

	public ChannelEditCaseBean setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public ChannelEditCaseBean setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPrincipal() {
		return principal;
	}

	public ChannelEditCaseBean setPrincipal(String principal) {
		this.principal = principal;
		return this;
	}

	public String getTel() {
		return tel;
	}

	public ChannelEditCaseBean setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public ChannelEditCaseBean setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getThirdChannelId() {
		return thirdChannelId;
	}

	public ChannelEditCaseBean setThirdChannelId(String thirdChannelId) {
		this.thirdChannelId = thirdChannelId;
		return this;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public ChannelEditCaseBean setIsEdit(String isEdit) {
		this.isEdit = isEdit;
		return this;
	}

	public String getIsConfirmEdit() {
		return isConfirmEdit;
	}

	public ChannelEditCaseBean setIsConfirmEdit(String isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ChannelEditCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public String getIsNotFreeze(){
		return isNotFreeze;
	}
	
	public ChannelEditCaseBean setIsNotFreeze(String isNotFreeze){
		this.isNotFreeze = isNotFreeze;
		return this;
	}
	
	public String getIsExaminePass(){
		return isExaminePass;
	}
	
	public ChannelEditCaseBean setIsExaminePass(String isExaminePass){
		this.isExaminePass = isExaminePass;
		return this;
	}
}
