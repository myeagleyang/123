package testframe;


import org.testng.AssertJUnit;  
import org.testng.annotations.Test;  
import ec.qa.autotest.ui.admin.portal.pageobject.AdminPortalLoginPage;  
import ec.qa.autotest.ui.admin.portal.pageobject.NavigationMenu;  
import ec.qa.autotest.ui.custom.annotation.AutoInject;  
import ec.qa.autotest.ui.testbase.AdminPortalTestBase;  
  
/** 
 *  
 * @auther xin.wang 
 *登录后台管理系统 
 */  
public class LoginAdminPortal extends AdminPortalTestBase{  
      
    @AutoInject  
    private AdminPortalLoginPage ecHomePage;  
      
    @AutoInject  
    private NavigationMenu ctPage;  
      
    @Test  
    public void loginAdminPortal() throws Exception{  
        ecHomePage.loginAdminPortal("admin", "111111");  
        AssertJUnit.assertEquals(ctPage.getWelcomeContent(), "欢迎您,");  
        AssertJUnit.assertEquals(ctPage.getCurLoginUser(), "超级管理员");  
        ctPage.goToProdcutLibPage();  
    }  
}  