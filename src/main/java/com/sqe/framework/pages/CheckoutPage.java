package com.sqe.framework.pages;

import com.sqe.framework.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    // Correct locators for SauceDemo checkout
    // Step 1: Your Information
    @FindBy(className = "title")
    private WebElement checkoutTitle;
    
    @FindBy(id = "first-name")
    private WebElement firstNameField;
    
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;
    
    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(id = "cancel")
    private WebElement cancelButton;
    
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;
    
    // Step 2: Overview
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;
    
    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;
    
    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;
    
    @FindBy(id = "finish")
    private WebElement finishButton;
    
    @FindBy(id = "cancel")
    private WebElement cancelCheckoutButton;
    
    // Step 3: Complete
    @FindBy(className = "complete-header")
    private WebElement thankYouMessage;
    
    @FindBy(className = "complete-text")
    private WebElement orderDispatchMessage;
    
    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;
    
    // Constructor
    public CheckoutPage() {
        PageFactory.initElements(driver, this);
    }
    
    // Page Actions - FIXED
    public boolean isCheckoutStepOneDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-one"));
            boolean titleVisible = wait.until(ExpectedConditions.visibilityOf(checkoutTitle)).isDisplayed();
            String titleText = checkoutTitle.getText();
            System.out.println("Checkout step 1 title: " + titleText);
            return titleVisible && titleText.contains("Checkout");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void enterFirstName(String firstName) {
        waitForVisibility(firstNameField);
        sendKeys(firstNameField, firstName);
    }
    
    public void enterLastName(String lastName) {
        waitForVisibility(lastNameField);
        sendKeys(lastNameField, lastName);
    }
    
    public void enterPostalCode(String postalCode) {
        waitForVisibility(postalCodeField);
        sendKeys(postalCodeField, postalCode);
    }
    
    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }
    
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        click(continueButton);
    }
    
    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        click(cancelButton);
    }
    
    public boolean isCheckoutStepTwoDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
            return wait.until(ExpectedConditions.visibilityOf(itemTotalLabel)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getItemTotal() {
        try {
            return getText(itemTotalLabel);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getTax() {
        try {
            return getText(taxLabel);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getTotal() {
        try {
            return getText(totalLabel);
        } catch (Exception e) {
            return "";
        }
    }
    
    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        click(finishButton);
    }
    
    public boolean isOrderCompleteDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-complete"));
            return wait.until(ExpectedConditions.visibilityOf(thankYouMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getThankYouMessage() {
        try {
            return getText(thankYouMessage);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getOrderDispatchMessage() {
        try {
            return getText(orderDispatchMessage);
        } catch (Exception e) {
            return "";
        }
    }
    
    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(backHomeButton));
        click(backHomeButton);
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }
    
    public void completeCheckout(String firstName, String lastName, String postalCode) {
        fillCheckoutForm(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
    }
}
