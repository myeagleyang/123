package swiftpass.testcase;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import swiftpass.testcase.annotations.CaseBeanField;

public abstract class CaseBean implements Serializable{
	private static final long serialVersionUID = -9093415385047772079L;
	
	public abstract String getCASE_NAME();

	public abstract void setCASE_NAME(String caseName);
	
	public CaseBean(){
		init();
	}
	
	private void init(){
		Field[] fs = this.getClass().getDeclaredFields();
		for(Field f : fs){
			if(f.getType().getSimpleName().equals("String")){
				f.setAccessible(true);
				try {
					f.set(this, "");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String toString(){
		Field[] fs = this.getClass().getDeclaredFields();
		List<Field> newFsList = new ArrayList<>();
		for(Field f : fs){
			f.setAccessible(true);
			if(f.getModifiers() != 26 && f.getDeclaredAnnotation(CaseBeanField.class) != null)
				newFsList.add(f);
		}
		newFsList.sort((f1, f2) ->{
			return f1.getDeclaredAnnotation(CaseBeanField.class).index() - f2.getDeclaredAnnotation(CaseBeanField.class).index();
		});
		StringBuilder sb = new StringBuilder();
		for(Field f : newFsList){
			f.setAccessible(true);
			try {
				sb.append(f.getName() + "(" + f.getDeclaredAnnotation(CaseBeanField.class).desc() +  ")=" + f.get(this)).append(", ");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
}
