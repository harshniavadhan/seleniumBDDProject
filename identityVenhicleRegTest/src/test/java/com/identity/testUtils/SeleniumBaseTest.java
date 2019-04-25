
package com.identity.testUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.identity.commonUtils.CommonUtils;





public abstract class SeleniumBaseTest extends CommonUtils {

	protected static WebDriver driver;

	public static Logger logger = null;
	private static Properties prop;
	private static Properties or;

	public static WebDriver getDriver() {
		return driver;
	}

	public static Properties getProp() {
		return prop;
	}

	public static Properties getOr() {
		return or;
	}

	public static void setLogger(Logger log) {
		logger = log;
	}

	public static void info(String info) {
		logger.info(info);
	}

	public static WebDriverWait waitForElement() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		return wait;
	}

	/**
	 *
	 */
	public void parent() {

		loadProperties();
		String browser = getProp().getProperty("browser");
		//setLogger(LoggerFactory.getLogger(this.getClass()));
		driver = getDriverInstance(false, browser);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(getProp().getProperty("implicit_time")),
				TimeUnit.SECONDS);

	}

	public static void loadProperties() {
		try {
			if (prop == null) {
				prop = new Properties();
				prop.load(new FileInputStream("src/main/resources/application.properties"));
			}
			if (or == null) {

				or = new Properties();
				or.load(new FileInputStream("src/resources/or.properties"));
			}

		} catch (IOException e) {
			e.getMessage();
		}
	}

	public WebDriver getDriverInstance(boolean incognitoMode, String browser) {
		DesiredCapabilities capability;
		WebDriver newDriver = null;
		String downloadFilepath = System.getProperty("user.dir") + "/src/main/resources/downloads";
		ChromeOptions options = new ChromeOptions();
		System.out.println("*********system browser name - "+    browser) ;
		
		if(null==browser){
			//browser = "chrome_mac";
			browser = "firefox";
		}

		//switch (getProp().getProperty("browser")) {
		switch (browser) {
			
		case "chrome_mac":
			if (!incognitoMode) {
				
				
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("test-type");
				options.addArguments("--start-maximized");
				capability = DesiredCapabilities.chrome();
				capability.setCapability("nativeEvents", false);
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/chromeForMac/chromedriver");
				newDriver = new ChromeDriver(capability);
			} else {
				options.addArguments("test-type");
				options.addArguments("--incognito");
				options.addArguments("--start-maximized");
				capability = DesiredCapabilities.chrome();
				capability.setCapability("nativeEvents", false);
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/chromeForMac/chromedriver");
				newDriver = new ChromeDriver(capability);

			}
			break;
		
		case "chrome_pc":
			if (!incognitoMode) {
				
				
				
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("test-type");
				options.addArguments("--start-maximized");
				capability = DesiredCapabilities.chrome();
				capability.setCapability("nativeEvents", false);
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/chromedriver/chromedriver.exe");
				newDriver = new ChromeDriver(capability);
			} else {
				options.addArguments("test-type");
				options.addArguments("--incognito");
				options.addArguments("--start-maximized");
				capability = DesiredCapabilities.chrome();
				capability.setCapability("nativeEvents", false);
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/chromedriver/chromedriver.exe");
				newDriver = new ChromeDriver(capability);

			}
			break;
		
		default:
			info("no browser provided. Shutting down the test");
			System.exit(1);
			break;
		}
		return newDriver;
	}

	/**
	 * @param info
	 */
	public void error(String info) {
		logger.error(info);
	}

	/**
	 * @param selector
	 * @return
	 */
	public WebElement getElement(String selector) {
		return getDriver().findElement(By.cssSelector(selector));
	}

	/**
	 *
	 */
	public void refreshPage() {
		getDriver().navigate().refresh();
	}

	/**
	 * @return
	 */
	public boolean IfAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;

		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	/**
	 *
	 */
	public void AcceptAllAlerts() {
		if (IfAlertPresent()) {
			do {
				driver.switchTo().alert().accept();
			} while (IfAlertPresent());
		}
	}

	public boolean AcceptWriteOnAlerts(String keyword) {
		try {
			getDriver().switchTo().alert().sendKeys(keyword);
			return true;

		} catch (NoAlertPresentException ex) {
			return false;
		}
	}
	
}
