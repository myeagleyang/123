package swiftpass.testcase.casebeans;

import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;

public class ChannelSearchCaseBean extends CaseBean{
	private static final long serialVersionUID = -8292588008066597490L;
	
	@CaseBeanField(desc="用例名称", index=0) private String CASE_NAME;
	
	@CaseBeanField(desc="创建时间起始时间", index=1) private String beginCreateTime;
	@CaseBeanField(desc="创建时间结束时间", index=2) private String endCreateTime;
	
	@CaseBeanField(desc="是否选择受理机构", index=3) private String isSelectAcceptOrg;
	@CaseBeanField(desc="按受理机构名称/ID", index=3) private String acceptOrgItem;
	@CaseBeanField(desc="受理机构名称", index=3) private String acceptOrgId;
	@CaseBeanField(desc="受理机构编号", index=3) private String acceptOrgName;
	@CaseBeanField(desc="是否确认选择受理机构", index=3) private String isConfirmSelectAcceptOrg;
	
	@CaseBeanField(desc="审核状态", index=4) private String examineStatus;
	@CaseBeanField(desc="激活状态", index=5) private String activateStatus;
	
	@CaseBeanField(desc="是否选择所属渠道", index=6) private String isSelectParentChannel;
	@CaseBeanField(desc="按所属渠道名称/ID", index=6) private String parentChannelItem; 
	@CaseBeanField(desc="所属渠道编号", index=6) private String parentChannelId;
	@CaseBeanField(desc="所属渠道名称", index=6) private String parentChannelName;
	@CaseBeanField(desc="是否确认选择所属渠道", index=6) private String isConfirmSelectParentChannel;
	
	@CaseBeanField(desc="渠道名称", index=8) private String channelName;
	
	@CaseBeanField(desc="渠道编号", index=9) private String channelId;
	
	@CaseBeanField(desc="渠道类型", index=10) private String channelProperty;
	
	@CaseBeanField(desc="结算方式", index=11) private String accWay;

	@CaseBeanField(desc="通用消息", index=12) private String message;
	@CaseBeanField(desc="预期查询结果数", index=13) private String expected;
	
	@CaseBeanField(desc="标注组合查询备注", index=14) private String multiQuery;
	
	public ChannelSearchCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}

	public void setCASE_NAME(String caseName) {
		CASE_NAME = caseName;
	}

	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public ChannelSearchCaseBean setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
		return this;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public ChannelSearchCaseBean setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
		return this;
	}

	public String getAcceptOrgId() {
		return acceptOrgId;
	}

	public ChannelSearchCaseBean setAcceptOrgId(String acceptOrgId) {
		this.acceptOrgId = acceptOrgId;
		return this;
	}

	public String getAcceptOrgName() {
		return acceptOrgName;
	}

	public ChannelSearchCaseBean setAcceptOrgName(String acceptOrgName) {
		this.acceptOrgName = acceptOrgName;
		return this;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public ChannelSearchCaseBean setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
		return this;
	}

	public String getActivateStatus() {
		return activateStatus;
	}

	public ChannelSearchCaseBean setActivateStatus(String activateStatus) {
		this.activateStatus = activateStatus;
		return this;
	}

	public String getParentChannelId() {
		return parentChannelId;
	}

	public ChannelSearchCaseBean setParentChannelId(String parentChannelId) {
		this.parentChannelId = parentChannelId;
		return this;
	}

	public String getParentChannelName() {
		return parentChannelName;
	}

	public ChannelSearchCaseBean setParentChannelName(String parentChannelName) {
		this.parentChannelName = parentChannelName;
		return this;
	}

	public String getChannelName() {
		return channelName;
	}

	public ChannelSearchCaseBean setChannelName(String channelName) {
		this.channelName = channelName;
		return this;
	}

	public String getChannelId() {
		return channelId;
	}

	public ChannelSearchCaseBean setChannelId(String channelId) {
		this.channelId = channelId;
		return this;
	}

	public String getChannelProperty() {
		return channelProperty;
	}

	public ChannelSearchCaseBean setChannelProperty(String channelProperty) {
		this.channelProperty = channelProperty;
		return this;
	}

	public String getAccWay() {
		return accWay;
	}

	public ChannelSearchCaseBean setAccWay(String accWay) {
		this.accWay = accWay;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ChannelSearchCaseBean setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public String getExpected(){
		return expected;
	}
	
	public ChannelSearchCaseBean setExpected(String expected){
		this.expected = expected;
		return this;
	}
	
	public String getMultiQuery(){
		return multiQuery;
	}
	
	public ChannelSearchCaseBean setMultiQuery(String multiQuery){
		this.multiQuery = multiQuery;
		return this;
	}

	public String getIsSelectAcceptOrg() {
		return isSelectAcceptOrg;
	}

	public ChannelSearchCaseBean setIsSelectAcceptOrg(String isSelectAcceptOrg) {
		this.isSelectAcceptOrg = isSelectAcceptOrg;
		return this;
	}

	public String getIsConfirmSelectAcceptOrg() {
		return isConfirmSelectAcceptOrg;
	}

	public ChannelSearchCaseBean setIsConfirmSelectAcceptOrg(String isConfirmSelectAcceptOrg) {
		this.isConfirmSelectAcceptOrg = isConfirmSelectAcceptOrg;
		return this;
	}

	public String getIsSelectParentChannel() {
		return isSelectParentChannel;
	}

	public ChannelSearchCaseBean setIsSelectParentChannel(String isSelectParentChannel) {
		this.isSelectParentChannel = isSelectParentChannel;
		return this;
	}

	public String getIsConfirmSelectParentChannel() {
		return isConfirmSelectParentChannel;
	}

	public ChannelSearchCaseBean setIsConfirmSelectParentChannel(String isConfirmSelectParentChannel) {
		this.isConfirmSelectParentChannel = isConfirmSelectParentChannel;
		return this;
	}

	public String getAcceptOrgItem() {
		return acceptOrgItem;
	}

	public ChannelSearchCaseBean setAcceptOrgItem(String acceptOrgItem) {
		this.acceptOrgItem = acceptOrgItem;
		return this;
	}

	public String getParentChannelItem() {
		return parentChannelItem;
	}

	public ChannelSearchCaseBean setParentChannelItem(String parentChannelItem) {
		this.parentChannelItem = parentChannelItem;
		return this;
	}
	
}
