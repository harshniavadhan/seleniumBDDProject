
package com.identity.testUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.identity.pages.HomePage;



 
public abstract class BaseTest extends SeleniumBaseTest {
	



	/*************************** ENUMERATIONS **********************/


	/***********************************************************************/

	/**
	 * @param username
	 * @param password
	 * @param dash
	 * @return
	 * @throws Exception
	 */
	public static HomePage navigateToHomePage() throws Exception {

		HomePage homepage = PageFactory.initElements(getDriver(), HomePage.class);
		homepage.navigateTo(getProp().getProperty("site_url"));
		TimeUnit.SECONDS.sleep(getVeryShortTime());
		return homepage;
	}
	

	/**
	 * @param e
	 */
	protected static void printStackTrace(Exception e) {
		takeScreenShot(e);
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("|       Cause     |  " + e.getCause());
		System.out.println("|       Class     |  " + e.getClass().getSimpleName());
		System.out.println("|       Message   |  " + WordUtils.wrap(e.getMessage(), 70));
		System.out.println("-----------------------------------------------------------------------");
		StackTraceElement[] elementList = e.getStackTrace();
		System.out.println("ATTENTION ! Below are the lines of code where the test fails");
		System.out.println("------------------------------------------------------------------------");
		for (int j = 0; j < elementList.length; j++) {
			if (elementList[j].getClassName().contains("com.mg")) {
				System.out.println(elementList[j]);
			}
		}
		System.out.println("------------------------------------------------------------------------");

		Assert.fail("Oops !!! Some exception occurred.");
	}

	protected static void printStackTrace(Throwable t) {
		takeScreenShot(t);
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("|       cause     |  " + t.getCause());
		System.out.println("|       message   |  " + WordUtils.wrap(t.getMessage(), 70));
		System.out.println("-----------------------------------------------------------------------");
		if (t instanceof SkipException) {
			throw new SkipException("Test Skipped.");
		}
		StackTraceElement[] elementList = t.getStackTrace();
		System.out.println("ATTENTION ! Below are the lines of code where the test fails");
		System.out.println("------------------------------------------------------------------------");
		for (int j = 0; j < elementList.length; j++) {
			if (elementList[j].getClassName().contains("com.mg")) {
				System.out.println(elementList[j]);
			}
		}
		System.out.println("------------------------------------------------------------------------");
		Assert.fail("Some Exception Occurred ..... Please check the error messages.");

	}

	@BeforeSuite
	public void setPropertiesForAll() {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		System.setProperty("org.uncommons.reportng.title", "MoveGuides Regression Report");

	}

	/**
	 *
	 * @param t
	 */
	protected static void takeScreenShot(Throwable t) {
		try {

			String screenshotName = "snapshot_" + System.currentTimeMillis() + ".jpg";
			File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String pathName = System.getProperty("user.dir") + "/src/screenshots/" + screenshotName;
			FileUtils.copyFile(screenshot, new File(pathName));
			System.out.println("SnapshotName -> " + screenshotName);
			String targetPath = "../target/downloads/" + screenshotName;
			Reporter.log("<a href=" + targetPath + " target='_blank' >" + screenshotName + "</a>");
			Reporter.log(t.getMessage());
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
	}


	
	public static void cleanUp() {

		if (getDriver() != null) {
			getDriver().close();
			getDriver().quit();
		}
	}
	
	
	


}
