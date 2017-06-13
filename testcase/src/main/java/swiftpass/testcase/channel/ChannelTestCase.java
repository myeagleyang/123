package swiftpass.testcase.channel;

import java.util.HashMap;

import swiftpass.testcase.casebeans.ChannelAddCaseBean;
import swiftpass.testcase.casebeans.ChannelAddD0CaseBean;
import swiftpass.testcase.casebeans.ChannelEditCaseBean;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;

public interface ChannelTestCase {
	//搜索测试用例模版
	public void searchChannel(ChannelSearchCaseBean data);
	
	//新增测试用例模版
	public void addChannel(ChannelAddCaseBean data);
	
	//新增测试用例模版
	public void addD0Channel(ChannelAddD0CaseBean data);
	
	//编辑测试用例模版
	public void editChannel(ChannelEditCaseBean data);
	
	//审核测试用例模版
	public void auditChannel(HashMap<String, String> params);
	
	//激活测试用例模版
	public void activeChannel(HashMap<String, String> params);
	
	//审核并激活测试用例模版
	public void aAAChannel(HashMap<String, String> params);

	public void scanChannelDetail(HashMap<String, String> params);
}
