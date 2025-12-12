package com.sqe.tests.stepdefinitions;

import com.sqe.framework.base.DriverManager;
import com.sqe.framework.base.TestContext;
import com.sqe.framework.config.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseStepDefinitions {
    
    public static TestContext testContext;
    
    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("=".repeat(50));
        
        // Initialize browser
        DriverManager.initializeDriver();
        
        // Navigate to base URL
        String baseUrl = ConfigManager.getInstance().getBaseUrl();
        DriverManager.getDriver().get(baseUrl);
        System.out.println("├£ Navigated to: " + baseUrl);
        System.out.println("Browser: " + ConfigManager.getInstance().getBrowser());
        System.out.println("Headless: " + ConfigManager.getInstance().isHeadless());
        
        // Initialize test context
        testContext = new TestContext();
        
        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @After(order = 0)
    public void tearDown(Scenario scenario) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Scenario Finished: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());
        System.out.println("=".repeat(50));
        
        // Take screenshot on failure
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
                System.out.println("├Ø Screenshot captured for failed scenario");
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
        
        // Cleanup test context
        if (testContext != null) {
            testContext.cleanup();
            testContext = null;
        }
        
        // Quit driver
        DriverManager.quitDriver();
    }
}
