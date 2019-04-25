package com.identity.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VehicleServicesPage extends Page {
	

	@FindBy(how = How.XPATH, using = "//*[@id='Vrm']")
	private WebElement registrationNumberTextBox;
	
	@FindBy(how = How.XPATH, using = "//*[@name='Continue']")
	private WebElement continueButton;
	

	
	
	

	public VehicleServicesPage(WebDriver driver) {
		super(driver);
	}
	
	public VehicleResultsPage enterVehicleRegistrationAndSubmit(String vehicleRegNumber) throws InterruptedException{
		webdriverWait.until(ExpectedConditions.visibilityOf(registrationNumberTextBox));
		registrationNumberTextBox.sendKeys(vehicleRegNumber);
		webdriverWait.until(ExpectedConditions.visibilityOf(continueButton));
		clickElementWithRetry(continueButton);
		TimeUnit.SECONDS.sleep(getVeryShortTime());
		VehicleResultsPage vehicleResultsPage = PageFactory.initElements(_driver,
				VehicleResultsPage.class);
		 return vehicleResultsPage;
		
	}
	
	
	
	

}
