import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class DebugLogin {
    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        try {
            System.out.println("Navigating to SauceDemo...");
            driver.get("https://www.saucedemo.com");
            
            Thread.sleep(2000);
            System.out.println("Page title: " + driver.getTitle());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Check if login page is displayed
            WebElement loginLogo = driver.findElement(By.className("login_logo"));
            System.out.println("Login logo text: " + loginLogo.getText());
            
            // Enter credentials
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            
            Thread.sleep(3000);
            
            // Check if login was successful
            System.out.println("After login - Title: " + driver.getTitle());
            System.out.println("After login - URL: " + driver.getCurrentUrl());
            
            // Check for error message
            try {
                WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
                System.out.println("Error message: " + errorElement.getText());
            } catch (Exception e) {
                System.out.println("No error message found");
            }
            
            // Check for products page
            try {
                WebElement productsTitle = driver.findElement(By.className("title"));
                System.out.println("Products page title: " + productsTitle.getText());
                System.out.println("✅ Login successful!");
            } catch (Exception e) {
                System.out.println("❌ Products page not found. Login failed.");
                
                // Take screenshot
                System.out.println("Taking screenshot...");
                java.io.File screenshot = ((org.openqa.selenium.TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                java.nio.file.Files.copy(screenshot.toPath(), new java.io.File("login-debug.png").toPath());
                System.out.println("Screenshot saved as login-debug.png");
            }
            
        } finally {
            Thread.sleep(2000);
            driver.quit();
        }
    }
}
