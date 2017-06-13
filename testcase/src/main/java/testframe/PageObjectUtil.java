package testframe;


import java.util.HashMap;  
  
/** 
 * @author xin.wang 
 * 获取页面对象的工具类 
 */  
public class PageObjectUtil {  
      
    private static HashMap<String, Object> pageobjs = null;  
      
    public static void setPageObjMap(HashMap<String, Object> Map){  
        pageobjs = Map;  
    }  
      
    @SuppressWarnings("unchecked")  
    public static <T> T getPageObject(String pageObjectClassName){    
        return (T)pageobjs.get(pageObjectClassName);    
    }  
      
}  