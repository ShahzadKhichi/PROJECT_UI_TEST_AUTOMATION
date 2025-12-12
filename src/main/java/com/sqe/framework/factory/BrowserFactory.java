package com.sqe.framework.factory;

import com.sqe.framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
        SAFARI
    }
    
    public static WebDriver createDriver(BrowserType browserType, boolean headless) {
        switch (browserType) {
            case CHROME:
                return createChromeDriver(headless);
            case FIREFOX:
                return createFirefoxDriver(headless);
            case EDGE:
                return createEdgeDriver(headless);
            case SAFARI:
                return createSafariDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }
    
    public static WebDriver createDriverFromConfig() {
        String browserName = ConfigManager.getInstance().getBrowser().toUpperCase();
        boolean headless = ConfigManager.getInstance().isHeadless();
        
        try {
            BrowserType browserType = BrowserType.valueOf(browserName);
            return createDriver(browserType, headless);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid browser type in config: " + browserName);
            System.err.println("Defaulting to FIREFOX");
            return createDriver(BrowserType.FIREFOX, headless);
        }
    }
    
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--start-maximized");
        
        if (headless) {
            options.addArguments("--headless=new");
        }
        
        return new ChromeDriver(options);
    }
    
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", System.getProperty("user.dir") + "/downloads");
        
        options.setProfile(profile);
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    private static WebDriver createSafariDriver() {
        // Note: Safari requires manual setup on macOS
        return new org.openqa.selenium.safari.SafariDriver();
    }
}
