
package com.identity.pages;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.identity.commonUtils.CommonUtils;

public class Page extends CommonUtils {

	// enum service { SHIP_BELONGING };

	public static WebDriver _driver = null;

	public static WebDriverWait webdriverWait;

	public Timestamp tStamp;

	private Logger logger;

	public Page(WebDriver driver) {
		_driver = driver;
		webdriverWait = new WebDriverWait(_driver, 15);

	}

	public String getTitle() {
		return _driver.getTitle();
	}

	public WebElement getElementByJs(String jQuerySelector) {

		JavascriptExecutor js = (JavascriptExecutor) _driver;

		return (WebElement) js.executeScript(jQuerySelector);

	}

	public List<WebElement> getElementsByJs(String jQuerySelector) {

		_driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) _driver;
		List<WebElement> elements = (List<WebElement>) js.executeScript(jQuerySelector);
		return elements;

	}

	public void executeJS(String code) {
		JavascriptExecutor js = (JavascriptExecutor) _driver;
		js.executeScript(code, "");

	}

	public Boolean isJQueryLoaded() {
		JavascriptExecutor js = (JavascriptExecutor) _driver;
		return (Boolean) js.executeScript("if(window.jQuery) {return true} else {return false}");
	}

	public void clickRadioBtn(String JQuerySelector) {

		getElementByJs(JQuerySelector).click();
	}

	public void clickNormalRadioBtn(WebElement e) {
		e.click();
	}

	public void selectCheckbox(WebElement e) {
		if (!e.isSelected()) {
			e.click();
		}

	}

	public void deSelectCheckbox(WebElement e) {
		if (e.isSelected()) {
			e.click();
		}
	}

	public void enterTextToInputField(WebElement e, String text) {
		e.clear();
		e.sendKeys(text);
	}

	public void hoverOverElement(WebElement el) {
		Actions actions = new Actions(_driver);
		actions.moveToElement(el).build().perform();

	}

	public void switchToIframe(String frameLoc) {
		webdriverWait.ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLoc));
	}

	public String getCurrentTimeStamp() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(new Date()).toString();
	}

	public void selectDropDownValue(WebElement e, String val) {

		((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", e);
		e.findElement(By.cssSelector("i")).click();
		WebElement dropdownList = e.findElement(By.cssSelector(".dk_options "));
		webdriverWait
				.until(ExpectedConditions.visibilityOfAllElements(dropdownList.findElements(By.cssSelector("li"))));
		dropdownList.findElement(By.cssSelector("a[data-dk-dropdown-value='" + val + "']")).click();
	}

	// method added for selecting the drop down value based on title
	// attribute--Vamshi
	public void selectDropDownValueByTitle(WebElement e, String title) {

		((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", e);
		e.findElement(By.cssSelector("i")).click();
		WebElement dropdownList = e.findElement(By.cssSelector(".dk_options "));
		webdriverWait
				.until(ExpectedConditions.visibilityOfAllElements(dropdownList.findElements(By.cssSelector("li"))));
		dropdownList.findElement(By.cssSelector("a[title='" + title + "']")).click();
	}

	public void selectDDValueByIndex(WebElement e, int index) {
		((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", e);
		e.findElement(By.cssSelector("i")).click();
		WebElement dropdownList = e.findElement(By.cssSelector(".dk_options "));
		List<WebElement> list = webdriverWait
				.until(ExpectedConditions.visibilityOfAllElements(dropdownList.findElements(By.cssSelector("li"))));
		list.get(index).click();
	}

	public void selectDropDownVersion2(WebElement e, String val) {
		e.findElement(By.cssSelector("i")).click();
		WebElement dropdownList = e.findElement(By.cssSelector(".selectboxControlValues"));
		webdriverWait
				.until(ExpectedConditions.visibilityOfAllElements(dropdownList.findElements(By.cssSelector("div"))));
		dropdownList.findElement(By.cssSelector("div.selectboxControlValue[data-value='" + val + "']")).click();
	}

	public void selectNormalDropDownValue(WebElement e, String val) {

		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(val);
	}

	public void selectDropDownValueByIndex(WebElement e, int index) {

		Select dropdown = new Select(e);
		dropdown.selectByIndex(index);
	}

	public void selectDate(String cssPath, String date) {

		StringBuilder builder = new StringBuilder();
		builder.append("$('").append(cssPath).append("').val('").append(date).append("')");
		executeJS(builder.toString());

	}

	public boolean isElementPresent(String csslocator) {
		if (_driver.findElements(By.cssSelector(csslocator)).size() == 0)
			return false;
		else
			return true;
	}

	public void confirmAlert() {
		webdriverWait.until(ExpectedConditions.alertIsPresent());
		_driver.switchTo().alert().accept();
	}

	public Object navigate(String url, Class className) {

		_driver.navigate().to(url);
		return PageFactory.initElements(_driver, className);
	}

	public boolean IfAlertPresent() {
		try {
			_driver.switchTo().alert();
			return true;

		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public void AcceptAllAlerts() {
		if (IfAlertPresent()) {
			do {
				_driver.switchTo().alert().accept();
			} while (IfAlertPresent());
		}
	}

	public WebElement waitUntil(ExpectedCondition expectedCondition, Class... classes) {
		FluentWait wait = webdriverWait.pollingEvery(1, TimeUnit.SECONDS).withTimeout(20, TimeUnit.SECONDS);
		if (classes != null) {
			for (Class c : classes) {
				wait.ignoring(c);
			}
		}
		return (WebElement) wait.until(expectedCondition);
	}

	public int getNumberOfErrorMessagesOnPage() {
		return _driver.findElements(By.cssSelector(".error")).size();
	}

	// Added on 11/1/2015

	public String getselectNormalDropDownValue(WebElement e) {
		Select dropdown = new Select(e);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getselectNormalDropDownOptionValue(WebElement e) {
		Select dropdown = new Select(e);
		return dropdown.getFirstSelectedOption().getAttribute("value");
	}

	public String getselectNormalDropDownPostion(WebElement e) {
		Select dropdown = new Select(e);
		List<WebElement> list = dropdown.getOptions();
		int postion = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().equals(dropdown.getFirstSelectedOption().getText())) {
				System.out.println("The index of the selected option is: " + i);
				postion = i;
				break;
			}
		}
		return Integer.toString(postion);
	}

	public void selectNormalDropDownByValue(WebElement e, String val) {
		Select dropdown = new Select(e);
		dropdown.selectByValue(val);
	}

	public void selectNormalDropDownByVisibleText(WebElement e, String val) {
		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(val);
	}
	
	public void dragAndDrop(WebElement source, WebElement target) {
		(new Actions(_driver)).dragAndDrop(source, target).perform();
	}

	public void tagPolicyByElement(String xpathForSearchRecord) throws Exception {
		waitUntil(ExpectedConditions.visibilityOf(_driver.findElement(By.xpath(xpathForSearchRecord))));
		String xpathForTagItems = xpathForSearchRecord
				+ "/../td[contains(@class, 'tagCell')]/div/div/ul/li[@class='tagItem']";
		List<WebElement> allListElements = _driver.findElements(By.xpath(xpathForTagItems));
		ArrayList<String> textList = new ArrayList<String>();
		for (WebElement element : allListElements) {
			String text = element.getText();
			textList.add(text);
		}
		String xpathForTagInput = xpathForSearchRecord
				+ "/../td[contains(@class, 'tagCell')]/div/div/ul/li[@class='tagInput']/input";
		TimeUnit.SECONDS.sleep(getShortTime());
		// System.out.println("********" + xpathForTagInput);
		WebElement tagBox = _driver.findElement(By.xpath(xpathForTagInput));

		if (!textList.contains("CanEditPolicyAtInitiation")) {
			TimeUnit.SECONDS.sleep(getShortTime());
			// System.out.println("#####"+tagBox.getText());
			tagBox.click();
			_driver.findElement(By
					.xpath("//ul[@class = 'ui-autocomplete ui-front ui-menu ui-widget ui-widget-content']/li[contains(text(), 'CanEditPolicyAtInitiation')]"))
					.click();
			TimeUnit.SECONDS.sleep(getVeryShortTime());
		}
		if (!textList.contains("DEFAULT")) {
			tagBox.click();
			_driver.findElement(By
					.xpath("//ul[@class = 'ui-autocomplete ui-front ui-menu ui-widget ui-widget-content']/li[contains(text(), 'DEFAULT')]"))
					.click();
			TimeUnit.SECONDS.sleep(getVeryShortTime());
		}
	}

	public static void windowsHandleToChildUsingParent( WebDriver driver, String parentWindowHandle ) {
		Set<String> windowSet = driver.getWindowHandles();
		for ( String winHandle : windowSet ) {
			System.out.println("parent window handles value - " + parentWindowHandle);
			System.out.println("all window handles values - " + winHandle);
			if ( !winHandle.equals( parentWindowHandle ) )
				driver.switchTo().window( winHandle );
		}
	}
	public static void windowsHandleToParentUsingChild( WebDriver driver, String childWindowHandle ) {
		Set<String> windowSet = driver.getWindowHandles();
		for ( String winHandle : windowSet ) {
			if ( !winHandle.equals( childWindowHandle ) )
				driver.switchTo().window( winHandle );
		}

	}

	public void clickElementWithRetry(final WebElement element) {

		FluentWait<WebElement> customWait = new FluentWait<WebElement>(element)

				.withTimeout(10, TimeUnit.SECONDS)

				.pollingEvery(200, TimeUnit.MILLISECONDS)

				.ignoring(StaleElementReferenceException.class);


		customWait

				.until(new Function<WebElement, Boolean>() {

					public Boolean apply(WebElement element) {

						try {

							element.click();

							return true;

						} catch (Exception e) {

							return false;

						}

					}

				});

	}
	public static void rightClickMouse( WebDriver driver, WebElement element, WebElement element1 ) {

		try {



			Actions rightClick = new Actions( driver );

			rightClick.moveToElement( element ).contextClick( element ).moveToElement( element1 ).click().build().perform();



		} catch( Exception e ) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}
	public void openLinkInNewTab(WebElement e,String URL) throws Exception{
		/* String selectLinkOpeninNewTab = Keys.chord(Keys.COMMAND,"t");
		 e.sendKeys(selectLinkOpeninNewTab);*/
		 /* Actions newTab = new Actions(_driver);
		  newTab.keyDown(Keys.COMMAND).click(e).keyUp(Keys.COMMAND).build().perform();
		  Thread.sleep(5000);*/
		_driver.get(URL);


		 /*String currentURL=_driver.getCurrentUrl();
		 System.out.println("current URL is " +currentURL);
		 _driver.executeScript("$(window.open('URL')");*/
		/* WebDriver driver = new ChromeDriver();
		 driver.get(URL);
		 String parentWindowHandle = _driver.getWindowHandle();
	    windowsHandleToChildUsingParent(_driver, parentWindowHandle);
	    TimeUnit.SECONDS.sleep(getVeryShortTime());*/
		//_driver.navigate().to(URL);
		//e.sendKeys("http://demo:aseo-12-demo@beta.test.authoritas.com/en_gb/marketshare");
	}
	
	public String getResults(WebElement webElement) throws Exception{
		TimeUnit.SECONDS.sleep(getShortTime());
		webdriverWait.until(ExpectedConditions.visibilityOf(webElement));
		String fullString = webElement.getText().trim();
		if(fullString.equals("No records to view")) {
			fullString= fullString.replace("No records to view", "0");
			return fullString;
		} else {
			String[] splitString = fullString.split("of");
			//String String = splitString[0]+splitString[1];
			/*if (splitString.length==1)
				return "No records to view";*/
			String actualKeywordCount = splitString[1].replaceAll("\\s+", "").trim();
			//String actualKeywordCount = String.trim();
			int parsed = Integer.parseInt(actualKeywordCount.replaceAll("\\s+", "").trim());
			System.out.println("actual keyword count is " + actualKeywordCount);
			if (null != actualKeywordCount)
				return actualKeywordCount.trim();
			else return null;
			
		}
		
		
		/*public WebElement datePickingFromCalender(WebElement webElement) throws Exception {
			 DateFormat dateFormat2 = new SimpleDateFormat("dd"); 
		     Date date2 = new Date();

		     String today = dateFormat2.format(date2); 

		     //find the calendar
		     WebElement dateWidget = _driver.findElement(By.id(" webElement"));  
		     List columns=dateWidget.findElements(By.tagName("td"));  

		     //comparing the text of cell with today's date and clicking it.
		     for (WebElement cell : columns)
		     {
		        if (cell.getText().equals(today))
		        {
		           cell.click();
		           break;
		        }
		     }

		}
	
	
}
return cell;
}*/
	}
}

