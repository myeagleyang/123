package testframe;

public void configDriver(ConfigDriverParameters cp) throws Exception {  
    testCaseDeclaringClass = cp.getTestMethod().getDeclaringClass().getName();  
    String website = cp.getTargetWebSite();  
    if (!success) {  
        System.out.println("\n=======测试用例准备重试=======");  
        reTryCount++;  
        success = true;  
    }  
    System.out.println("\n======测试用例: " + cp.getTestMethod().getName() + " 开始执行======" + "\n===测试用例运行的浏览器类型："  
            + browserType + " ===" + "\n测试网站地址: " + website);  
    webDriver = DriverUtil.getWebDriver(browserType);  
    <strong><span style="color:#ff0000;">new initPageObject(this); //关键点在此  其他忽略</span></strong>  
    webDriver.manage().timeouts().implicitlyWait(cp.getSerachElementTime(), TimeUnit.SECONDS);  
    webDriver.manage().window().maximize();  
    webDriver.manage().timeouts().pageLoadTimeout(cp.getPageLoadTime(), TimeUnit.SECONDS);  
    if (CookiesUtil.getCk() != null) {  
        webDriver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);  
        try {  
            webDriver.get(website);  
        } catch (Exception e) {  
        }  
        webDriver.manage().addCookie(CookiesUtil.getCk());  
    }  
    try {  
        webDriver.get(website);  
    } catch (Exception e) {  
    }  
    webDriver.manage().timeouts().pageLoadTimeout(-1, TimeUnit.SECONDS);  
}  
  