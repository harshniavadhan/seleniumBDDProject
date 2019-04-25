package com.identity.stepdefinitions.vehicleTest;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.identity.pages.HomePage;
import com.identity.pages.VehicleResultsPage;
import com.identity.pages.VehicleServicesPage;
import com.identity.service.FileService;
import com.identity.testUtils.BaseTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;



public class VehicleDetailCheck extends BaseTest {
	private static String browser;
	HomePage homePage = null;
	VehicleServicesPage vehicleServicesPage = null;

	

	@Given("^I am in the home page of vehicle info$")
	public void gotoHomePage() {
		try {
			parent();
			homePage = navigateToHomePage();		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@When("^I click start now button and go to vehicle service page$")
	public void gotoVehileServicePage() {
		//clicking start button
		try {
			 vehicleServicesPage = homePage.clickStartNowButton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	  
	  public static Object[][] credentials() {
	 
	     // For the number of rows of data present in the excel, test will be executed the same no. of times
		 FileService fileService = new FileService();
		
		 Object[][] testObjArray = null;
		
		 try {
			testObjArray = fileService.readExcel(System.getProperty("user.dir") + "/src/main/resources","vehicleTestdata.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
         return testObjArray;
	      
	  }
	
	
	 @Then("^I enter the vehicle registration numbers from excel and assert the make and color$")
	 
	public void assertVehicleInfo(){
		try {
			
			//read the excel data to get the inputs and expected outputs
			Object[][] testObjArray = credentials();
			
			for(Object[] o : testObjArray) {
				String vehicleRegistrationNum = (String) o[0];
				String make = (String) o[1];
				String colour = (String) o[2];
				
				System.out.println("values from the Excel are : make = "+ make + ", color = " + colour);
				
			//enter the vehicle Registration Number to get the details
			VehicleResultsPage vehicleResultsPage = vehicleServicesPage.enterVehicleRegistrationAndSubmit(vehicleRegistrationNum);
			
			HashMap<String, String>vehicleDetailsMap = vehicleResultsPage.returnVehicleDetails();
			
			System.out.println("values from the UI are : make = "+ vehicleDetailsMap.get("make") + ", color = " + vehicleDetailsMap.get("colour"));
			//asserting the vehicle make 
			Assert.assertEquals( "values from UI and excel for make are not equal" , make, vehicleDetailsMap.get("make"));
			
			//asserting the vehicle colour 
			Assert.assertEquals( "values from UI and excel for color are not equal", colour, vehicleDetailsMap.get("colour"));
			
			vehicleServicesPage = vehicleResultsPage.clickBackButton();
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	 @Then("^I close the browser$")
	public void tearDown() {
		driver.quit();
	}

}
