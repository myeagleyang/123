package swiftpass.testcase.testdata.batchintopiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import swiftpass.page.enums.ProcessStatus;
import swiftpass.utils.DBUtil;

public class BIPRehandleTestData {
	public static HashMap<String, String>[][] getBIPRehandleTestData(){
		return getAllBIPRehandleTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("isClickFirstRow", "");
		data.put("processStatus", "");
		data.put("isConfirm", "");
		return data;
	}
	
	private static HashMap<String, String> getPageWithCrlParams(){
		HashMap<String, String> data = initPageParams();
		data.replace("isClickFirstRow", "true");
		data.replace("processStatus", ProcessStatus.processSuccess.getProcessCode());//默认处理成功
		data.replace("isConfirm", "true");
		
		data.put("errorMsg", "");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllBIPRehandleTestData(){
		HashMap<String, String>[][] resultCaseArray = null;
		List<HashMap<String, String>> list = new ArrayList<>();
		String[] errorMsg = {"未处理的任务不能重新处理!", "待处理的任务不能重新处理!",
				"处理中的任务不能重新处理!", "处理成功的任务不能重新处理!", "部分成功的任务不能重新处理!"};
		for(ProcessStatus ps: ProcessStatus.values()){
			if(!ps.equals(ProcessStatus.failProcess)){
				HashMap<String, String> oneCaseMap = getPageWithCrlParams();
				oneCaseMap.replace("processStatus", ps.getProcessCode());
				oneCaseMap.replace("errorMsg", errorMsg[ps.ordinal()]);
				list.add(oneCaseMap);
			}
		}
		
		HashMap<String, String> illegeCaseMap = getPageWithCrlParams();
		illegeCaseMap.replace("isClickFirstRow", "");
		illegeCaseMap.replace("errorMsg", "请选择要重新处理的行!");
		list.add(illegeCaseMap);
		
		illegeCaseMap = getPageWithCrlParams();
		illegeCaseMap.replace("processStatus", ProcessStatus.failProcess.getProcessCode());
		illegeCaseMap.replace("isConfirm", "");
		illegeCaseMap.replace("errorMsg", "正常取消");
		list.add(illegeCaseMap);
		
		//重新处理成功用例
		HashMap<String, String> successCaseMap = getPageWithCrlParams();
		successCaseMap.replace("processStatus", ProcessStatus.failProcess.getProcessCode());
		list.add(successCaseMap);
		
		for(HashMap<String, String> oneCase: list){
			resultCaseArray = ArrayUtils.add(resultCaseArray, ArrayUtils.toArray(oneCase));
		}
		return resultCaseArray;
		
	}
	
	//根据processStatus去更改数据库得到需要的数据
	public static HashMap<String, String> getNeedData(String processStatus){
		//这里sql必须排序才能保证点击页面中的第一个
		String sql = "select t.*, t.rowid from die_imp_task t where imp_type in (5, 6, 7) order by t.create_time desc";
		HashMap<String, String> needData = DBUtil.getQueryResultMap(sql).get(1);
		String ps = needData.get("PROCESS_STATUS");
		String impTaskId = needData.get("IMP_TASK_ID");
		if(!ps.equals(processStatus)){
			String updataSql = "update die_imp_task set process_status = $processStatus where imp_task_id = '$impTaskId'"
					.replace("$processStatus", processStatus).replace("$impTaskId", impTaskId);
			DBUtil.executeUpdateSql(updataSql);
			needData = DBUtil.getQueryResultMap(sql).get(1);
		}
		return needData;
	}
	
	
	public static void main(String...strings){
		HashMap<String, String>[][] maps = getBIPRehandleTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
