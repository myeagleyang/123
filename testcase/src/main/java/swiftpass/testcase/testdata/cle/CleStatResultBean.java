package swiftpass.testcase.testdata.cle;

import java.lang.reflect.Field;
import java.util.Map;
import swiftpass.testcase.testdata.ResultBean;

public class CleStatResultBean extends ResultBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7769040192899801742L;
	
	private String cleanSuccTotalStroke;
	
	private String cleanSuccTotalAmount;
	
	private String cleanFailTotalStroke;
	
	private String cleanFailTotalAmount;
	
	private String cleanIngTotalStroke;
	
	private String cleanIngTotalAmount;

	private String recordCount;
	
	public CleStatResultBean(){
		init();
	}
	
	public CleStatResultBean(Map<String, String> res){
		cleanSuccTotalStroke = res.get("cleanSuccTotalStroke");
		cleanSuccTotalAmount = res.get("cleanSuccTotalAmount");
		cleanFailTotalStroke = res.get("cleanFailTotalStroke");
		cleanFailTotalAmount = res.get("cleanFailTotalAmount");
		cleanIngTotalStroke = res.get("cleanIngTotalStroke");
		cleanIngTotalAmount = res.get("cleanIngTotalAmount");
		recordCount = res.get("recordCount");
	}
	
	public String getCleanSuccTotalStroke() {
		return cleanSuccTotalStroke;
	}

	public void setCleanSuccTotalStroke(String cleanSuccTotalStroke) {
		this.cleanSuccTotalStroke = cleanSuccTotalStroke;
	}

	public String getCleanSuccTotalAmount() {
		return cleanSuccTotalAmount;
	}

	public void setCleanSuccTotalAmount(String cleanSuccTotalAmount) {
		this.cleanSuccTotalAmount = cleanSuccTotalAmount;
	}

	public String getCleanFailTotalStroke() {
		return cleanFailTotalStroke;
	}

	public void setCleanFailTotalStroke(String cleanFailTotalStroke) {
		this.cleanFailTotalStroke = cleanFailTotalStroke;
	}

	public String getCleanFailTotalAmount() {
		return cleanFailTotalAmount;
	}

	public void setCleanFailTotalAmount(String cleanFailTotalAmount) {
		this.cleanFailTotalAmount = cleanFailTotalAmount;
	}
	
	public String getCleanIngTotalStroke() {
		return cleanIngTotalStroke;
	}

	public void setCleanIngTotalStroke(String cleanIngTotalStroke) {
		this.cleanIngTotalStroke = cleanIngTotalStroke;
	}

	public String getCleanIngTotalAmount() {
		return cleanIngTotalAmount;
	}

	public void setCleanIngTotalAmount(String cleanIngTotalAmount) {
		this.cleanIngTotalAmount = cleanIngTotalAmount;
	}

	private void init(){
		Field[] fs = this.getClass().getDeclaredFields();
		for(Field f : fs){
			f.setAccessible(true);
			try {
				if(f.getType().getSimpleName().equals("String"))
					f.set(this, "");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		this.cleanFailTotalAmount = "0.00";
		this.cleanIngTotalAmount = "0.00";
		this.cleanSuccTotalAmount = "0.00";
		this.cleanFailTotalStroke = "0";
		this.cleanIngTotalStroke = "0";
		this.cleanSuccTotalStroke = "0";
	}
	
	public boolean equals(CleStatResultBean cmpRes){
		Field[] fs = this.getClass().getDeclaredFields();
		for(Field f : fs){
			f.setAccessible(true);
			if(f.getType().getSimpleName().equals("String")){
				try {
					if(!((String)f.get(this)).trim().equals(((String)f.get(cmpRes)).trim()))
						return false;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
}
