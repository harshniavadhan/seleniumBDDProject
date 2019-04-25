/*package com.identity.testrunner;

import java.io.File;
import java.util.logging.Logger;

import org.junit.runner.RunWith;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;




@CucumberOptions(
        features = {"classpath:"+ "com/identity" +"/featureFiles"},
        plugin = { "com.cucumber.listener.ExtentCucumberFormatter:" + "output/report.html"},
        glue = {"classpath:" + "com/identity" + "/stepdefinitions"},
		tags = {"@Test"}
)

@RunWith(ExtendedCucumberRunner.class)
public class TestRunner {

	private final static Logger LOGGER = Logger.getLogger(ExtendedCucumberRunner.class.getName());

    @BeforeTestSuite
    public static void setup() {
    	LOGGER.info("This will have implementation after later commits");
    }

    @AfterTestSuite
    public static void exitSuite() {
		LOGGER.info("Suite is exiting now and creating report");

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("travel-report.html");
        htmlReporter.config().setDocumentTitle("Travel_Report/Travel Regression Report");
        htmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss");

		ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Mac OSX");
    }
}
*/
package com.identity.testrunner;
 
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber; 

@RunWith(Cucumber.class) 
@CucumberOptions(
features = {"classpath:"+ "com/identity/featureFiles"},
glue = {"classpath:" + "com/identity/stepdefinitions"}) 

public class TestRunner { }
