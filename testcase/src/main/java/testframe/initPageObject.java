package testframe;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import ec.qa.autotest.ui.custom.annotation.AutoInject;
//import ec.qa.autotest.ui.custom.annotation.PageObject;
//import ec.qa.autotest.ui.testbase.TestBase;

/**
 * @author xin.wang 1.模拟IOC容器 通过反射扫描配置好的pageobject包并将 pageobject实例存放到全局静态MAP中。
 *         2.实现了依赖注入功能 DI
 */

public class initPageObject {

	private static Field[] fields = null;
	private Object pageobj;
	private String packAgePref = "src\\main\\java\\";

	public initPageObject(Object ob) {  
        this.pageobj = ob;  
        PageObjectUtil.setPageObjMap(initPageObjMap()); //将页面对象存放到MAP中，MAP充当一个bean容器</span></strong>  
    }

	private HashMap<String, Object> initPageObjMap() {  
        HashMap<String, Object> pageobjs = new HashMap<String, Object>();  
        HashSet<String> fieldSet = new HashSet<String>();  
        String curClassName = TestBase.getTestCaseDeclaringClass();  
  
        try {  
            String[] packagePath = PropertiesUtil.getProValue("init.pageobj.Package").split(",");  
            fields = Class.forName(curClassName).getDeclaredFields();  
            fillFieldNameSet(fields, fieldSet);  
            for (String pp : packagePath) {  
                ArrayList<String> classNames = new ArrayList<String>();  
                getClassName(packAgePref + pp.replace(".", "\\"), classNames, pp);  
                for (String className : classNames) {  
                    Class<?> classObj = Class.forName(className);  
                    if (classObj.getAnnotation(PageObject.class) != null  
                            && fieldSet.contains(classObj.getSimpleName())) {  
                        pageobjs.put(classObj.getSimpleName(), classObj.newInstance());  
                    }  
                }  
            }  
  
            injectPageObj(fields, pageobjs);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return pageobjs;  
    }

	/**
	 * @author xin.wang
	 * @param fields
	 * @param pageobjs
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *             实现向测试用例类自动注入页面对象的功能
	 */
	private void injectPageObj(Field[] fields, HashMap<String, Object> pageobjs)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field f : fields) {
			f.setAccessible(true);
			f.set(pageobj, pageobjs.get(f.getType().getSimpleName()));
			f.setAccessible(false);
		}
	}

	
	private void fillFieldNameSet(Field[] fields, HashSet<String> fieldSet) {  
        for (Field f : fields) {  
            if (f.getAnnotation(AutoInject.class) != null) {  
                fieldSet.add(f.getType().getSimpleName());  
            }  
        }  
    }

	private ArrayList<String> getClassName(String folderPath, ArrayList<String> className, String packageName)
			throws Exception {
		File files = new File(folderPath);
		for (File f : files.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".java")) {
				className.add(packageName + "." + f.getName().substring(0, f.getName().indexOf(".")));
			}
		}
		return null;
	}
}