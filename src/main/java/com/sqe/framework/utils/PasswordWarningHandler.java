package com.sqe.framework.utils;

import com.sqe.framework.base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PasswordWarningHandler {
    
    public static boolean handlePasswordWarning() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            System.out.println("Checking for password warning popup...");
            
            // Try multiple possible selectors for the password warning
            String[] possibleSelectors = {
                "button:contains('OK')",
                "button:contains('Got it')",
                "button:contains('Continue')",
                "button:contains('Dismiss')",
                "[role='dialog'] button",
                ".password-save-prompt button",
                "[data-test='password-warning'] button",
                "//button[contains(text(), 'OK')]",
                "//button[contains(text(), 'Got it')]"
            };
            
            for (String selector : possibleSelectors) {
                try {
                    if (selector.startsWith("//")) {
                        // XPath selector
                        List<WebElement> elements = driver.findElements(By.xpath(selector));
                        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                            System.out.println("Found password warning with XPath: " + selector);
                            elements.get(0).click();
                            System.out.println("Ō£ģ Clicked dismiss button");
                            Thread.sleep(1000); // Wait for popup to disappear
                            return true;
                        }
                    } else {
                        // CSS selector
                        List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                            System.out.println("Found password warning with CSS: " + selector);
                            elements.get(0).click();
                            System.out.println("Ō£ģ Clicked dismiss button");
                            Thread.sleep(1000);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Continue trying other selectors
                }
            }
            
            // Also check for Chrome's password breach warning
            try {
                List<WebElement> chromeDialogs = driver.findElements(By.cssSelector("div[role='dialog'], .bubble, .infobar"));
                for (WebElement dialog : chromeDialogs) {
                    if (dialog.isDisplayed() && 
                        (dialog.getText().contains("password") || 
                         dialog.getText().contains("breach") ||
                         dialog.getText().contains("Charge your password"))) {
                        System.out.println("Found Chrome password breach warning");
                        
                        // Try to find and click OK/Got it button
                        List<WebElement> buttons = dialog.findElements(By.tagName("button"));
                        for (WebElement button : buttons) {
                            if (button.isDisplayed() && 
                                (button.getText().contains("OK") || 
                                 button.getText().contains("Got it") ||
                                 button.getText().contains("Dismiss"))) {
                                button.click();
                                System.out.println("Ō£ģ Dismissed Chrome password warning");
                                Thread.sleep(1000);
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Ignore
            }
            
            System.out.println("No password warning found");
            return false;
            
        } catch (Exception e) {
            System.out.println("Error handling password warning: " + e.getMessage());
            return false;
        }
    }
    
    public static void waitAndHandlePasswordWarning() {
        try {
            // Wait a moment for any popup to appear
            Thread.sleep(2000);
            
            // Try to handle password warning
            boolean handled = handlePasswordWarning();
            
            if (handled) {
                // Wait for page to stabilize
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
