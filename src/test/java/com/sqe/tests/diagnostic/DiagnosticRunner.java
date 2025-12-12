package com.sqe.tests.diagnostic;

import com.sqe.framework.base.DriverManager;
import com.sqe.framework.config.ConfigManager;
import com.sqe.framework.pages.LoginPage;
import com.sqe.framework.pages.ProductsPage;
import com.sqe.framework.utils.DebugUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class DiagnosticRunner {
    
    @Test
    public void diagnoseLoginPage() {
        System.out.println("\n=== DIAGNOSTIC TEST: LOGIN PAGE ===\n");
        
        DriverManager.initializeDriver();
        DriverManager.getDriver().get("https://www.saucedemo.com");
        
        DebugUtils.printPageInfo();
        
        // Check login form elements
        System.out.println("Checking login form elements:");
        
        List<WebElement> inputs = DriverManager.getDriver().findElements(By.tagName("input"));
        System.out.println("Total input fields: " + inputs.size());
        
        for (WebElement input : inputs) {
            String id = input.getAttribute("id");
            String placeholder = input.getAttribute("placeholder");
            String type = input.getAttribute("type");
            System.out.println("  Input - ID: " + id + ", Type: " + type + ", Placeholder: " + placeholder);
        }
        
        List<WebElement> buttons = DriverManager.getDriver().findElements(By.tagName("button"));
        System.out.println("Total buttons: " + buttons.size());
        
        for (WebElement button : buttons) {
            System.out.println("  Button - Text: " + button.getText() + ", ID: " + button.getAttribute("id"));
        }
        
        // Try login
        LoginPage loginPage = new LoginPage();
        System.out.println("\nAttempting login...");
        loginPage.login("standard_user", "secret_sauce");
        
        DebugUtils.printPageInfo();
        
        // Check if we're logged in
        ProductsPage productsPage = new ProductsPage();
        boolean isProductsPage = productsPage.isProductsPageDisplayed();
        System.out.println("Is products page displayed: " + isProductsPage);
        
        if (isProductsPage) {
            System.out.println("├£ Login successful!");
            System.out.println("Product count: " + productsPage.getProductCount());
        } else {
            System.out.println("├Ø Login failed or page not detected");
            System.out.println("Current URL: " + DriverManager.getDriver().getCurrentUrl());
            System.out.println("Current Title: " + DriverManager.getDriver().getTitle());
            
            // Check for error
            List<WebElement> errorElements = DriverManager.getDriver().findElements(By.cssSelector("[data-test='error']"));
            if (!errorElements.isEmpty()) {
                System.out.println("Error message: " + errorElements.get(0).getText());
            }
        }
        
        DriverManager.quitDriver();
        System.out.println("\n=== DIAGNOSTIC TEST COMPLETE ===\n");
    }
}
