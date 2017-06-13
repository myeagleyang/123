package testframe;

import java.lang.annotation.Documented;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
  
  
/** 
 * @author xin.wang 
 * @see 标识当前类是否是页面对象 
 */  
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface PageObject {  
    boolean lazyLoad() default false;  
}  