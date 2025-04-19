# Sauce_demo
 Overview
This project automates functional testing of the SauceDemo web application using Selenium WebDriver, TestNG, and Maven. The tests simulate a real user's journey — from logging in, sorting products, managing the cart, to completing a purchase.


Prerequisites
Before running the automation tests, ensure you have the following installed on your machine:

Java (JDK 8 or higher)

Apache Maven (for managing dependencies)

Google Chrome (for running tests in Chrome browser)

ChromeDriver (for Selenium WebDriver to interact with the Chrome browser)

IDE (Eclipse, IntelliJ, etc. with Maven support)

TestNG Plugin (if using IDE like Eclipse)


Script Breakdown:
Setup (@BeforeClass):
Initializes the Chrome WebDriver with options to:
Disable password manager popups.
Disable notifications.
Use incognito mode.
Disable the Chrome credentials manager.
Configures implicit wait to avoid delays during element interactions.
Maximizes the browser window for a better view of the webpage.
Login Test (@Test(priority = 1)):
Navigates to the SauceDemo website.
Inputs the valid username (standard_user) and password (secret_sauce).
Clicks the login button.
Verifies that the homepage is displayed by checking if the "inventory_list" element is visible.
Product Filtering (Low to High) (@Test(priority = 2)):
Uses the product sort dropdown to filter the products by price (Low to High).
Extracts the names of the products after sorting and prints them out.
Add Product to Cart (@Test(priority = 3)):
Filters the products by price (Low to High) again.
Adds the first product to the cart using its respective "Add to Cart" button.
Verifies that the product has been added to the cart by checking the visibility of the cart icon and ensuring that it reflects the addition.
Complete Checkout Process (@Test(priority = 4)):
Navigates to the shopping cart.
Proceeds to checkout and fills in required information (name, last name, postal code).
Submits the order and verifies that the order completion message ("THANK YOU FOR YOUR ORDER") is displayed, indicating successful checkout.
Tear Down (@AfterClass):
Closes the browser after all tests have been executed.
Key Points:
Chrome Options: Used to configure the ChromeDriver to disable popups and notifications.
Implicit Waits: Added for elements to load, avoiding exceptions in case of slight delays in element availability.
Explicit Waits: Used when waiting for elements that might take longer to load (e.g., cart update).
Assertions: Assert.assertTrue and Assert.assertEquals are used to validate expected outcomes (like verifying that elements are displayed and order confirmation is shown).
TestNG: The @Test annotation is used to mark test methods with priorities (so that they execute in a specific order). The tests are also marked with @BeforeClass for setup and @AfterClass for cleanup.
