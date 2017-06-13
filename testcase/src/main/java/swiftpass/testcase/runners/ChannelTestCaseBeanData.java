package swiftpass.testcase.runners;
import org.testng.annotations.DataProvider;
import swiftpass.testcase.casebeans.ChannelAddCaseBean;
import swiftpass.testcase.casebeans.ChannelAddD0CaseBean;
import swiftpass.testcase.casebeans.ChannelEditCaseBean;
import swiftpass.testcase.casebeans.ChannelSearchCaseBean;
import swiftpass.testcase.testdata.channel.XChannelAddD0TestData;
import swiftpass.testcase.testdata.channel.XChannelAddTestData;
import swiftpass.testcase.testdata.channel.XChannelEditTestData;
import swiftpass.testcase.testdata.channel.XChannelSearchTestData;
public class ChannelTestCaseBeanData{
   
	@DataProvider(name="runAddData")
	public static ChannelAddCaseBean[][] channelAdd(){
		return XChannelAddTestData.data();
	}
	@DataProvider(name="runEditData")
	public static ChannelEditCaseBean[][] channelEdit(){
		return XChannelEditTestData.data();
	}
	@DataProvider(name="runSearchData")
	public static ChannelSearchCaseBean[][] channelSearch(){
		return XChannelSearchTestData.data();
	}
	@DataProvider(name="runAddD0Data")
	public static ChannelAddD0CaseBean[][] channelAddD0(){
		return XChannelAddD0TestData.data();
	}
}