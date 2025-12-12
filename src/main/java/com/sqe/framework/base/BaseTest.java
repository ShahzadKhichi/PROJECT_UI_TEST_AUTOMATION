package com.sqe.framework.base;

import com.sqe.framework.config.ConfigManager;
import com.sqe.framework.pages.*;
import com.sqe.framework.utils.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

public class BaseTest {

    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected CommonPage commonPage;
    protected ExcelReader excelReader;
    protected DatabaseManager dbManager;

    @BeforeMethod
    public void setUp(Method method) {
        // Initialize driver
        DriverManager.initializeDriver();

        // Initialize pages
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        commonPage = new CommonPage();

        // Initialize utilities
        excelReader = new ExcelReader("src/main/resources/testdata/testdata.xlsx");
        dbManager = new DatabaseManager();

        // Navigate to base URL
        DriverManager.getDriver().get(ConfigManager.getInstance().getBaseUrl());

        // Add test name to Allure
        Allure.description("Test: " + method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Take screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE &&
                ConfigManager.getInstance().takeScreenshotOnFailure()) {
            takeScreenshotForAllure(result.getName());
        }

        // Close connections
        if (excelReader != null) {
            excelReader.close();
        }
        if (dbManager != null) {
            dbManager.close();
        }

        // Quit driver
        DriverManager.quitDriver();
    }

    private void takeScreenshotForAllure(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(testName + " - Failure Screenshot",
                    new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper methods for common operations
    public void loginWithCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    public void addProductToCart(int productIndex) {
        productsPage.addProductToCart(productIndex);
    }

    public void goToCart() {
        productsPage.clickCartIcon();
    }

    public void completePurchase(String firstName, String lastName, String postalCode) {
        cartPage.clickCheckout();
        checkoutPage.fillCheckoutForm(firstName, lastName, postalCode);
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();
    }
}