package swiftpass.testcase.channel;

import java.util.HashMap;

public interface ChannelPayConfTestCase {
	
	public void addChannelPayConf(HashMap<String, String> params);
	
	public void editChannelPayConf(HashMap<String, String> params);
	
	public void activeChannelPayConf(HashMap<String, String> params);
	
	public void detailChannelPayConf(HashMap<String, String> params);
	
	void addXYChannelPayConf(HashMap<String, String> params);
	
	void EditXYChannelPayConf(HashMap<String, String> params);
}
