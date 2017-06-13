package testframe;

public class LoginAdminPortal extends AdminPortalTestBase{  
    
    @AutoInject  
    private NavigationMenu ctPage;  
      
    @Test(invocationCount = 1)  
    public void loginAdminPortal() throws Exception{  
        AssertJUnit.assertEquals("欢迎您,",ctPage.getWelcomeContent());  
        AssertJUnit.assertEquals("超级管理员1",ctPage.getCurLoginUser());  
        ctPage.goToProdcutLibPage();  
          
        ctPage =PageObjectUtil.getPageObject("NavigationMenu");  
        ctPage.clickProdcutCenterMenu();  
    }  
}  