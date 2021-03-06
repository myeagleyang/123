package swiftpass.testcase.testdata.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.DataGenerator;
import swiftpass.page.enums.ActivateStatus;
import swiftpass.page.enums.ExamineStatus;
import swiftpass.testcase.casebeans.ChannelEditD0CaseBean;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelAAAService;
import swiftpass.utils.services.ChannelService;

/**
 * 
 * @author sunhaojie
 * date：2017-4-17
 */
public class XChannelEditD0TestData {
	private static final ChannelEditD0CaseBean successEditCase = new ChannelEditD0CaseBean();
	private static final String ESCAPE = "";
	static{
		successEditCase.setCASE_NAME("渠道编辑成功编辑一个非冻结状态的渠道.");
		Map<String, String> passASChannel = XChannelEditD0Helper.channelOf(ExamineStatus.PASS, ActivateStatus.PASS);
		successEditCase.setChannelName(DataGenerator.generateNewChannelName())
			.setChannelId(passASChannel.get("CHANNEL_ID"))
			.setIsNotFreeze("true")
			.setPrincipal(DataGenerator.generateZh_CNName())
			.setTel(DataGenerator.generateTel())
			.setAddress("后海大道1024号.")
			.setEmail(DataGenerator.generateEmail())
			.setRemark(RandomStringUtils.randomAlphanumeric(32))	
			.setThirdChannelId(RandomStringUtils.randomNumeric(12))
			.setIsEdit("true")
			.setIsConfirmEdit("true")
			.setMessage(ESCAPE)
			.setIsNotFreeze("true")
			.setIsExaminePass("true");
	}

	public static void main(String...strings){
		for(ChannelEditD0CaseBean[] a : data()){
			System.out.println(a[0]);
		}
		DBUtil.closeDBResource();
	}
	
	public static ChannelEditD0CaseBean[][] data(){
		List<ChannelEditD0CaseBean> caseList = new ArrayList<>();
		// 1.成功编辑4中组状态渠道
		Map<String, String> nonASChannel = XChannelEditD0Helper.channelOf(ExamineStatus.NON, ActivateStatus.NOPROCESS);
		Map<String, String> failASChannel = XChannelEditD0Helper.channelOf(ExamineStatus.STOP, ActivateStatus.FAIL);
		Map<String, String> freezASChannel = XChannelEditD0Helper.channelOf(ExamineStatus.NEEDAGAIN, ActivateStatus.FREEZE);
		ChannelEditD0CaseBean editASPass = SwiftPass.copy(successEditCase);
		caseList.add(editASPass);
		ChannelEditD0CaseBean editASFail = SwiftPass.copy(successEditCase);
		editASFail.setChannelId(failASChannel.get("CHANNEL_ID")).setIsExaminePass("false").setCASE_NAME("渠道编辑成功编辑一个激活不通过的渠道.");
		caseList.add(editASFail);
		ChannelEditD0CaseBean editASNon = SwiftPass.copy(successEditCase);
		editASNon.setChannelId(nonASChannel.get("CHANNEL_ID")).setIsExaminePass("false").setCASE_NAME("渠道编辑成功编辑一个未激活的渠道");;
		caseList.add(editASNon);
		ChannelEditD0CaseBean editASFreeze = SwiftPass.copy(successEditCase);
		editASFreeze.setChannelId(freezASChannel.get("CHANNEL_ID")).setIsExaminePass("false").setIsNotFreeze("false").setCASE_NAME("渠道编辑编辑冻结渠道不可编辑.");;
		caseList.add(editASFreeze);
		
		//	2.必填字段未填报错误信息
		ChannelEditD0CaseBean nullChannelName = SwiftPass.copy(successEditCase);
		nullChannelName.setChannelName(ESCAPE).setMessage(XChannelEditD0Helper.NULL_CHANNEL_NAME_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑渠道名称为空.");;
		caseList.add(nullChannelName);
		
		ChannelEditD0CaseBean nullAddress = SwiftPass.copy(successEditCase);
		nullAddress.setAddress(ESCAPE).setMessage(XChannelEditD0Helper.NULL_ADDRESS_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑地址为空.");;
		caseList.add(nullAddress);
		
		ChannelEditD0CaseBean nullEmail = SwiftPass.copy(successEditCase);
		nullEmail.setEmail(ESCAPE).setMessage(XChannelEditD0Helper.NULL_EMAIL_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑邮箱为空.");;
		caseList.add(nullEmail);
		
		ChannelEditD0CaseBean nullPrincipal = SwiftPass.copy(successEditCase);
		nullPrincipal.setPrincipal(ESCAPE).setMessage(XChannelEditD0Helper.NULL_PRINCIPAL_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑负责人为空.");;
		caseList.add(nullPrincipal);
		
		ChannelEditD0CaseBean nullTel = SwiftPass.copy(successEditCase);
		nullTel.setTel(ESCAPE).setMessage(XChannelEditD0Helper.NULL_TEL_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑电话为空.");;
		caseList.add(nullTel);
		
		//	3.其他
		ChannelEditD0CaseBean notEdit = SwiftPass.copy(successEditCase);
		notEdit.setIsEdit("false").setMessage(XChannelEditD0Helper.NOT_EDIT_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑取消编辑关闭页面.");;
		caseList.add(notEdit);
		
		ChannelEditD0CaseBean notConfirmEdit = SwiftPass.copy(successEditCase);
		notConfirmEdit.setIsConfirmEdit("false").setMessage(XChannelEditD0Helper.NOT_CONFIRM_EDIT_MESSAGE).setIsExaminePass("false").setCASE_NAME("渠道编辑取消确认编辑关闭页面.");;
		caseList.add(notConfirmEdit);
		
		ChannelEditD0CaseBean noSelectEdit = SwiftPass.copy(successEditCase);
		noSelectEdit.setChannelId("$$$$$$$$$$$$").setMessage(XChannelEditD0Helper.NO_SELECT_CHANNEL_EDIT_MSG).setIsExaminePass("false").setCASE_NAME("渠道编辑未选中渠道编辑.");;
		caseList.add(noSelectEdit);
		
		ChannelEditD0CaseBean beyondChannelNameLength = SwiftPass.copy(successEditCase);
		beyondChannelNameLength.setChannelName(RandomStringUtils.randomAlphanumeric(33)).setMessage(XChannelEditD0Helper.CHANNEL_NAME_BEYOND_LENGTH_MSG)
			.setIsExaminePass("false").setCASE_NAME("渠道编辑渠道名称超过合法长度.");;
		caseList.add(beyondChannelNameLength);
		
		return caseList.stream().map(caseListElement -> ArrayUtils.toArray(caseListElement)).toArray(ChannelEditD0CaseBean[][]::new);
	}
}

class XChannelEditD0Helper{
	public static final String NULL_CHANNEL_NAME_MESSAGE = "请填写渠道名称";
	public static final String NULL_ADDRESS_MESSAGE = "请输入地址";
	public static final String NULL_EMAIL_MESSAGE = "请输入邮箱";
	public static final String NULL_PRINCIPAL_MESSAGE = "请输入负责人";
	public static final String NULL_TEL_MESSAGE = "请输入电话";
	public static final String NOT_EDIT_MESSAGE = "正常关闭";
	public static final String NOT_CONFIRM_EDIT_MESSAGE = "正常取消";
	public static final String CHANNEL_NAME_BEYOND_LENGTH_MSG = "渠道名称长度在1~32位之间！";
	public static final String NO_SELECT_CHANNEL_EDIT_MSG = "请选择要编辑的记录!";
	/**
	 * 从系统中获取一个指定激活状态的渠道.
	 * @param status
	 * @return
	 */
	public static Map<String, String> channelOf(ExamineStatus es, ActivateStatus as){
		StringBuilder sb = new StringBuilder();
		String mainBranchSql = "select channel_id from cms_channel where parent_channel in (select channel_id from cms_channel where parent_channel = '000000000000')";
		String sqlPart = "select * from cms_channel where channel_type = 2 and physics_flag = 1 and activate_status = " + as.getSCode() + " and examine_status = " + es.getSCode();
		sb.append(sqlPart).append(" and channel_id not in (").append(mainBranchSql).append(")");
		Map<Integer, Map<String, String>> channels = DBUtil.getXQueryResultMap(sb.toString());
		if(channels.size() == 0){
			String mainBranchId = DBUtil.getXQueryResultMap(mainBranchSql).get(1).get("CHANNEL_ID");
			Map<String, String> newChannel = ChannelService.addChannel(mainBranchId);
			if(!es.equals(ExamineStatus.NON)){
				ChannelAAAService.auditChannel(es, newChannel.get("CHANNEL_ID"));
			}
			if(!as.equals(ActivateStatus.NOPROCESS)){
				ChannelAAAService.activeChannel(as, newChannel.get("CHANNEL_ID"));
			}
			channels = DBUtil.getXQueryResultMap(sb.toString());
		}
		return channels.get(1);
	}
}