package swiftpass.testcase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.stream.events.EndDocument;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import swiftpass.page.Page.XClock;
import swiftpass.page.exceptions.SwiftPassException;
import swiftpass.utils.DBUtil;
import swiftpass.utils.SwiftLogger;
import swiftpass.utils.SwiftPass;
import swiftpass.utils.services.ChannelPCService;
import swiftpass.utils.services.ChannelService;
import swiftpass.utils.services.Login2Service;

/**
 * 这个类完成非登录用例测试的准备工作
 * 
 * @author Ab - xieliangji
 */
public class RunCaseProcessor {
	public static WebDriver driver;
	private Logger logger;
	private String driverType;
	private boolean local;
	private String spServer;
	private String loginPath;
	private boolean isSuperAdmin;
	private String userName;
	private String password;
	private String loginApiUrl;
	private String homePath;
	private String downloadPath;
	private static long startTime;
	private static long endTime;

	@BeforeTest(description = "在测试前完成登录操作", alwaysRun = true)
	public void preTestProcess() {
		/*
		 * startTime = System.currentTimeMillis(); initSystemParams();
		 * cleanDir(downloadPath); cleanDir("./testScreenshots"); for(Cookie
		 * cookie : Login2Service.getSPCookies()){
		 * driver.manage().addCookie(cookie); } //确保受理机构下的那个渠道是审核通过、激活成功的
		 * ,并且添加了最少一种支付方式
		 * if(!ChannelService.checkAddChannelPreConditionAndProcess() &&
		 * !ChannelPCService.checkUniqueInnerChannelPC()) throw new
		 * SwiftPassException("检查受理机构及其子渠道", "失败！"); driver.get(spServer +
		 * homePath); logger.info("非登录测试的初始化工作完成，当前在威富通登录后的主页上...");
		 */

		String URL = "http://10.0.0.80:9580/irsy-web-manager/a/login;jsessionid=7D03833FA9019E7213B0B2E167D625A0";
		System.setProperty("webdriver.chrome.driver",
				"E:/Automatic/automatic workspace/privateCloud/privateCloud/testcase/drivers/chromedriver.exe");

		driver = new ChromeDriver();
//		driver.manage().window().maximize();
		driver.get(URL);
		/*try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().window().maximize();*/
		
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.xpath("//*[@type='submit']")).click();

	}

	// 删除截图目录中的旧有截图
	@SuppressWarnings("unused")
	private void deleteHistoryScreenshots() {
		try {
			if (new File("./testScreenshots").exists())
				FileUtils.forceDelete(new File("./testScreenshots"));
			FileUtils.forceMkdir(new File("./testScreenshots"));
			File download = new File(downloadPath);
			if (download.exists())
				FileUtils.forceDelete(download);
			FileUtils.forceMkdir(download);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cleanDir(String dir) {
		try {
			File download = new File(dir);
			if (download.exists())
				FileUtils.forceDelete(download);
			FileUtils.forceMkdir(download);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 读取配置并完成初始化操作
	private void initSystemParams() {
		logger = SwiftLogger.getLogger();
		swiftpass.utils.Configuration conf = SwiftPass.getConfiguration("system.conf");
		driverType = conf.getValueOfKey("browserType");
		local = Boolean.parseBoolean(conf.getValueOfKey("local"));
		spServer = conf.getValueOfKey("spServer");
		loginPath = conf.getValueOfKey("loginPath");
		homePath = conf.getValueOfKey("homePath");
		loginApiUrl = conf.getValueOfKey("loginApiUrl");
		downloadPath = System.getProperty("user.dir") + conf.getValueOfKey("downLoadPath");
		driver = swiftpass.utils.SPDriverFactory.create(driverType, local);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		isSuperAdmin = Boolean.parseBoolean(conf.getValueOfKey("isSuperAdmin"));
		if (isSuperAdmin) {
			userName = conf.getValueOfKey("superUserName");
			password = conf.getValueOfKey("superPassword");
		} else {
			userName = conf.getValueOfKey("userName");
			password = conf.getValueOfKey("password");
		}
		driver.get(spServer + loginPath);
	}

	@AfterTest(description = "测试完成后，确保数据库链接关闭，关闭浏览器", alwaysRun = true)
	public void postTestProcess() {
		XClock.stop(2);
		DBUtil.closeDBResource();
		driver.close();
		driver.quit();
		endTime = System.currentTimeMillis();
	}

	public static long time() {
		return endTime - startTime;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private List<Cookie> getCookiesByLogin2() {
		List<Cookie> cookies = new ArrayList<Cookie>();
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost req = new HttpPost(spServer + loginApiUrl);
			List<NameValuePair> loginParams = new ArrayList<>();
			loginParams.add(new BasicNameValuePair("userName", this.userName));
			loginParams.add(new BasicNameValuePair("password", this.password));
			loginParams.add(new BasicNameValuePair("randCode", ""));
			HttpEntity entityReq = new UrlEncodedFormEntity(loginParams);
			req.setEntity(entityReq);

			CloseableHttpResponse resp = client.execute(req);
			Header[] headers = resp.getAllHeaders();
			HttpEntity entityPost = resp.getEntity();
			String entityStr = EntityUtils.toString(entityPost);
			if (entityStr.contains("\"success\":true")) {
				for (Header header : headers) {
					Cookie c = getCookieFromHeader(header);
					if (null != c) {
						cookies.add(c);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cookies;
	}

	private Cookie getCookieFromHeader(Header header) {
		String name = null;
		String value = null;
		String domain = null;
		String path = null;
		java.util.Date date = null;
		boolean isSecure = false;
		boolean isHttpOnly = false;

		if (header.getName().equals("Set-Cookie")) {
			String[] cookieItems = header.getValue().split(";");
			name = cookieItems[0].split("=")[0].trim();
			value = cookieItems[0].split("=")[1].trim();
			for (String cookieItem : cookieItems) {
				if (cookieItem.toLowerCase().contains("domain")) {
					domain = cookieItem.split("=")[1].trim();
				} else if (cookieItem.toLowerCase().contains("path")) {
					path = cookieItem.split("=")[1].trim();
				} else if (cookieItem.toLowerCase().contains("httponly")) {
					isHttpOnly = true;
				}
			}
			return new Cookie(name, value, domain, path, date, isSecure, isHttpOnly);
		}
		return null;
	}
}