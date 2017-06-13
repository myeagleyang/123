package swiftpass.testcase.testdata.cle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class CleResStatTestData {
	public static CleStatCaseBean[][] getTestData(){
		List<CleStatCaseBean> list = new ArrayList<>();
		CleStatCaseBean[][] cases = null;
		
		CleStatCaseBean specBothCleTime = new CleStatCaseBean();
		specBothCleTime.setCASE_NAME("仅指定清分时间查询清分结果的统计数据.");
		specBothCleTime.setCleBeginTime("2017-2-26 00:00:00");
		specBothCleTime.setCleEndTime("2017-2-26 23:59:59");
		list.add(specBothCleTime);
		
		for(CleStatCaseBean cb : list){
			cases = ArrayUtils.add(cases, ArrayUtils.toArray(cb));
		}
		
		return cases;
	}
}
