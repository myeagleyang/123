package swiftpass.testcase.testdata;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class ResultBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4488051643714678941L;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			if(field.getModifiers() != 26){
				try {
					sb.append(field.getName() + "=" + field.get(this) + ", ");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
