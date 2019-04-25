Feature: Test the vehicle details

  @Test
  Scenario: Verify the vehicle details
  	Given I am in the home page of vehicle info
 	When I click start now button and go to vehicle service page
  	Then I enter the vehicle registration numbers from excel and assert the make and color
  	Then I close the browser
   

