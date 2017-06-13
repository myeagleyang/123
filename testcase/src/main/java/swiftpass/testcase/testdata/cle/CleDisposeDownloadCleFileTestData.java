package swiftpass.testcase.testdata.cle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class CleDisposeDownloadCleFileTestData {
	
	public static HashMap<String, String>[][] getDownloadCleFileTestData(){
		return getAllDownloadCleFileTestData();
	}
	
	private static HashMap<String, String> initPageParams(){
		HashMap<String, String> data = new HashMap<String, String>();
		String fileName = System.getProperty("user.dir") + File.separator + "bzclefile\\20170226001.txt";
		data.put("file", fileName);
		data.put("beginCT", "2017-02-26");
		data.put("endCT", "2017-02-28");
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, String>[][] getAllDownloadCleFileTestData(){
		HashMap<String, String>[][] resultCaseMap = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> oneCaseMap = initPageParams();
		list.add(oneCaseMap);
		for(HashMap<String, String> oneCase: list){
			resultCaseMap = ArrayUtils.add(resultCaseMap, ArrayUtils.toArray(oneCase));
		}
		return resultCaseMap;
	}
	
	public static void main(String ags[]){
		HashMap<String, String>[][] maps = getDownloadCleFileTestData();
		for(HashMap<String, String>[] map: maps){
			System.out.println(map[0]);
		}
	}

}
