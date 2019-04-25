package com.identity.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends Page {
	

	@FindBy(how = How.XPATH, using = "//*[@id='get-started']/a")
	 private WebElement startNowButton;
	 
	
	 public HomePage(WebDriver driver) {
		super(driver);
	 }
	 
	 public void navigateTo(String url) {
	        _driver.get(url);
	 }

	 
	 public VehicleServicesPage clickStartNowButton() throws Exception{
		 
		 clickElementWithRetry(startNowButton);
		 TimeUnit.SECONDS.sleep(getVeryShortTime());
		 VehicleServicesPage vehicleServicesPage = PageFactory.initElements(_driver,
				 VehicleServicesPage.class);
		 return vehicleServicesPage;
	 }
	 
	
	 
	 

}
