package irsy.testcase;

public class IdNamePair {
	private String x_name;
	private String x_id;
	
	public IdNamePair(String id, String name){
		x_name = name;
		x_id = id;
	}
	
	public String getName(){
		return x_name;
	}
	
	public String getId(){
		return x_id;
	}
}
