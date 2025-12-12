package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    
    @FindBy(id = "user-name")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;
    
    @FindBy(className = "login_logo")
    private WebElement loginLogo;
    
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToLoginPage() {
        navigateTo("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.titleContains("Swag Labs"));
    }
    
    public void enterUsername(String username) {
        waitForVisibility(usernameField);
        sendKeys(usernameField, username);
    }
    
    public void enterPassword(String password) {
        waitForVisibility(passwordField);
        sendKeys(passwordField, password);
    }
    
    public void clickLogin() {
        click(loginButton);
    }
    
    public void login(String username, String password) {
        System.out.println("Logging in with: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        
        // Simple wait for login to complete
        wait.until(d -> {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("inventory") || isErrorMessageDisplayed();
        });
    }
    
    public boolean isLoginPageDisplayed() {
        try {
            return loginLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
