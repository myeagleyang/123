package irsy.utils.dboperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import swiftpass.utils.DBUtil;

public class DBOperations {
	public static HashMap<String, String> getRandomBank(){
		String sql = "select bank_name, bank_id from cms_bank where rownum < 100";
		HashMap<Integer, HashMap<String, String>> banks = DBUtil.getQueryResultMap(sql);
		try{
			return banks.get(RandomUtils.nextInt(1, banks.size()));
		} catch(IllegalArgumentException ex){
			return new HashMap<>();
		}
	}
	
	public static HashMap<Integer, HashMap<String, String>> getAllIndustries(){
		String sql = "select * from cms_industry";
		return DBUtil.getQueryResultMap(sql);
	}
	
	//	返回所有顶级行业<行业id，行业名，所属行业>
	public static HashMap<Integer, HashMap<String, String>> getTopLevelIndustries(){
		StringBuilder sql = new StringBuilder();
		sql.append("select industry_id, industry_name, parent_industry from cms_industry ")
			.append("where parent_industry = 0");
		return DBUtil.getQueryResultMap(sql.toString());
	}
	
	//	返回某个行业的所有子行业<行业id，行业名，所属行业>
	public static List<HashMap<String, String>> getChildrenOfIndustry(String INDUSTRY_ID){
		String sql = "select INDUSTRY_ID, INDUSTRY_NAME, PARENT_INDUSTRY from cms_industry where parent_industry = $id".replace("$id", INDUSTRY_ID);
		List<HashMap<String, String>> childrenList = new ArrayList<>();
		HashMap<Integer, HashMap<String, String>> children = DBUtil.getQueryResultMap(sql);
		for(Map.Entry<Integer, HashMap<String, String>> child : children.entrySet()){
			childrenList.add(child.getValue());
		}
		return childrenList;
	}
	
	/**
	 * 	随机返回某个完整的行业链,用>拼接成字符串{nameChain, idChain}
	 * @return
	 */
	public static String[] getRandomIndustryChain(){
		String[] result = new String[2];
		String name = "INDUSTRY_NAME";
		String id = "INDUSTRY_ID";
		HashMap<String, String> top = getTopLevelIndustries().get(RandomUtils.nextInt(1, 4));
		String topName = top.get(name);
		String topId = top.get(id);
		List<HashMap<String, String>> list = getChildrenOfIndustry(topId);
		HashMap<String, String> middle = list.get(RandomUtils.nextInt(0, list.size()));
		String middleName = middle.get(name);
		String middleId = middle.get(id);
		List<HashMap<String, String>> list_ = getChildrenOfIndustry(middleId);
		HashMap<String, String> last = list_.get(RandomUtils.nextInt(0, list_.size()));
		String lastName = last.get(name);
		String lastId = last.get(id);
		
		result[0] = String.join(">", topName, middleName, lastName);
		result[1] = String.join(">", topId, middleId, lastId);
		return result;
	}
	
	/**
	 * idChain = nameChain
	 * @param industryId
	 * @return
	 */
	public static NameValuePair getIndustryChain(String industryId){
		NameValuePair pair = null;
		StringBuilder nameChain = new StringBuilder();
		StringBuilder idChain = new StringBuilder();
		String currentId = industryId;
		String sql = "select industry_id, industry_name, parent_industry from cms_industry where industry_id = $id";
		while(!currentId.equals("0")){
			HashMap<String, String> i = DBUtil.getQueryResultMap(sql.replace("$id", currentId)).get(1);
			nameChain.insert(0,i.get("INDUSTRY_NAME") + "-");
			idChain.insert(0,i.get("INDUSTRY_ID") + "-");
			currentId = i.get("PARENT_INDUSTRY");
		}
		nameChain.deleteCharAt(nameChain.length() - 1);
		idChain.deleteCharAt(idChain.length() - 1);
		pair = new BasicNameValuePair(idChain.toString(), nameChain.toString());
		return pair;
	}
	
	public static void main(String...strings){
		NameValuePair n = getIndustryChain("10000043");
		System.out.println(n);
		
		DBUtil.closeDBResource();
	}
}
