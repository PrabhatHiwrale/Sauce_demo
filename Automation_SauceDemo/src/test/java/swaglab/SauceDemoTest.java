package swaglab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SauceDemoTest {

    WebDriver driver;

    @SuppressWarnings({ "serial", "deprecation" })
	@BeforeClass
    public void setup() {
        
        // ✅ Chrome options to disable password manager popup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        driver.get("https://www.saucedemo.com/v1/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify login success
        boolean isHomePageDisplayed = driver.findElement(By.className("inventory_list")).isDisplayed();
        Assert.assertTrue(isHomePageDisplayed, "Login failed or homepage not displayed.");
    }

    @Test(priority = 2)
    public void filterProductsLowToHigh() {
        Select dropdown = new Select(driver.findElement(By.className("product_sort_container")));
        dropdown.selectByVisibleText("Price (low to high)");

        // Wait briefly and print product names
        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        System.out.println("Products (Price Low to High):");
        for (WebElement product : productNames) {
            System.out.println(product.getText());
        }
    }

    @Test(priority = 3)
    public void addProductToCart() {
        Select dropdown = new Select(driver.findElement(By.className("product_sort_container")));
        dropdown.selectByVisibleText("Price (low to high)");


        WebElement firstAddToCart = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button"));
        firstAddToCart.click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[2]/div[3]/button")).click();


        // Wait for the cart to update and check if the product is added
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/a/span")));
        
        if (cartItem.isDisplayed()) {System.out.println();
            System.out.println("Product is successfully added to the cart.");
        } else {
            System.out.println("Product is NOT added to the cart.");
        }


        System.out.println();
        
    }


    @Test(priority = 4)
    public void completeCheckout(){
    	
    	driver.findElement(By.id("shopping_cart_container")).click();
    	driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div[2]/a[2]")).click();
       
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/form/div[2]/input")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div[2]/div[8]/a[2]")).click();

        String confirmation = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(confirmation, "THANK YOU FOR YOUR ORDER");
     
        System.out.println();
        System.out.println("Checkout is Done");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
