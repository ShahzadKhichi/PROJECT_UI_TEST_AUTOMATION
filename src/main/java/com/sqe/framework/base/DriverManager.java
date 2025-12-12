package com.sqe.framework.base;

import com.sqe.framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
    
    public static void initializeDriver() {
        String browserType = ConfigManager.getInstance().getBrowser().toLowerCase();
        WebDriver driver = null;
        
        System.out.println("Initializing " + browserType.toUpperCase() + " browser...");
        
        switch (browserType) {
            case "chrome":
                driver = createChromeDriver();
                break;
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "edge":
                driver = createEdgeDriver();
                break;
            case "safari":
                driver = createSafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        
        configureDriver(driver);
        setDriver(driver);
        System.out.println("✅ " + browserType.toUpperCase() + " browser initialized successfully!");
    }

    private static WebDriver createChromeDriver() {
        // Let WebDriverManager pick the correct driver for the installed Chrome
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Basic and safe arguments
        options.addArguments("--start-maximized");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Optional: reduce "automation" banner – not required, but fine
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // Headless mode if enabled in config
        if (ConfigManager.getInstance().isHeadless()) {
            options.addArguments("--headless=new");
        }

        // If you are NOT explicitly setting chrome.binary.path in config.properties,
        // we can skip that logic to avoid pointing to a wrong binary.
        // If you really need it, keep it but make sure the path is correct.

        return new ChromeDriver(options);
    }


    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        // Simple profile - avoid complex settings
        FirefoxProfile profile = new FirefoxProfile();
        
        // Basic settings only
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        
        options.setProfile(profile);
        
        // Window size
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        // Set binary path if specified
        String firefoxBinaryPath = ConfigManager.getInstance().getProperty("firefox.binary.path");
        if (firefoxBinaryPath != null && !firefoxBinaryPath.isEmpty()) {
            options.setBinary(firefoxBinaryPath);
        }
        
        if (ConfigManager.getInstance().isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    private static WebDriver createEdgeDriver() {
        WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        
        // Set binary path if specified in config
        String edgeBinaryPath = ConfigManager.getInstance().getProperty("edge.binary.path");
        if (edgeBinaryPath != null && !edgeBinaryPath.isEmpty()) {
            options.setBinary(edgeBinaryPath);
        }
        
        if (ConfigManager.getInstance().isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }
    
    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
        int timeout = ConfigManager.getInstance().getTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }
    
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully");
            } catch (Exception e) {
                System.err.println("Error closing browser: " + e.getMessage());
            }
            driverThreadLocal.remove();
        }
    }
}
