package testframe;


import org.openqa.selenium.WebElement;  
import org.openqa.selenium.support.FindBy;  
import org.openqa.selenium.support.How;  
import org.openqa.selenium.support.PageFactory;  
//import ec.qa.autotest.ui.custom.annotation.PageObject;  
//import ec.qa.autotest.ui.testbase.TestBase;  

/** 
* @author xin.wang 
* @see 登录页面 
*/  
@PageObject  
public class AdminPortalLoginPage {  
    
  @FindBy(how = How.ID, using = "loginname")  
  private WebElement userNameInputBox;  

  @FindBy(how = How.ID, using = "password")  
  private WebElement passwordInputBox;  

  @FindBy(how = How.NAME, using = "loginForm")  
  private WebElement submitButton;  

  public void setUserNameContent(String username) {  
      userNameInputBox.click();  
      userNameInputBox.sendKeys(username);  
  }  
    
  public void setPwdContent(String pwd) {  
      passwordInputBox.click();  
      passwordInputBox.sendKeys(pwd);  
  }  
    
  public void getUserNameContent(String username) {  
      userNameInputBox.getText();  
  }  

  public String getUserNameContent() {  
      return userNameInputBox.getText();  
  }  

  public AdminPortalLoginPage(){  
      PageFactory.initElements(TestBase.getWebDriver(), this);  
  }  
    
  /** 
   * @author xin.wang 
   * @see 登录后台管理系统 
   */  

  public void loginAdminPortal(String username, String pwd) {  
      setUserNameContent(username);  
      setPwdContent(pwd);  
      submitButton.submit();  
  }  
}  