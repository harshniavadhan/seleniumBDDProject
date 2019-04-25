package com.identity.pages;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VehicleResultsPage extends Page {
	

	@FindBy(how = How.XPATH, using = "//*[@class='reg-mark']")
	private WebElement registrationNumber;
	
	@FindBy(how = How.XPATH, using = "//*[@id='pr3']/div/ul/li[2]/span[2]/strong")
	private WebElement make;
	
	@FindBy(how = How.XPATH, using = "//*[@id='pr3']/div/ul/li[3]/span[2]/strong")
	private WebElement colour;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"content\"]/div[@class=\"back\"]/a")
	private WebElement backButton;
	
	
	

	public VehicleResultsPage(WebDriver driver) {
		super(driver);
	}
	
	public HashMap<String, String> returnVehicleDetails (){
		webdriverWait.until(ExpectedConditions.visibilityOf(registrationNumber));		
		HashMap<String, String> vehicleDetailsMap = new HashMap<String,String>();
		vehicleDetailsMap.put("registrationNumber", registrationNumber.getText());
		vehicleDetailsMap.put("make", make.getText());
		vehicleDetailsMap.put("colour", colour.getText());
		 return vehicleDetailsMap;
		
	}
	
	public VehicleServicesPage clickBackButton() throws Exception{
		 TimeUnit.SECONDS.sleep(getVeryShortTime());
		 clickElementWithRetry(backButton);
		 TimeUnit.SECONDS.sleep(getVeryShortTime());
		 VehicleServicesPage vehicleServicesPage = PageFactory.initElements(_driver,
				 VehicleServicesPage.class);
			 return vehicleServicesPage;
		
	 }
	
	
	

}
